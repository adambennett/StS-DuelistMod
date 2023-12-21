package duelistmod.patches;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.*;
import duelistmod.relics.*;
import duelistmod.variables.Tags;


@SpirePatch(
		clz = Soul.class,
		method = "obtain"
		)

public class ObtainCardsPrefixPatch 
{
	@SuppressWarnings("rawtypes")
	public static SpireReturn Prefix(Soul soul, final AbstractCard card)
	{
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		boolean exodiaDeck = (StartingDeck.currentDeck == StartingDeck.EXODIA && config.getCannotObtainCards() != null && config.getCannotObtainCards() && AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID));
		boolean isCurse = card.type.equals(CardType.CURSE);
		boolean isMarked = AbstractDungeon.player.hasRelic(MarkExxod.ID);
		boolean isGambler = AbstractDungeon.player.hasRelic(GamblerChip.ID);
		DuelistCard dc = card instanceof DuelistCard ? (DuelistCard)card : null;
		GamblerChip chip = isGambler ? (GamblerChip)AbstractDungeon.player.getRelic(GamblerChip.ID) : null;

		if (exodiaDeck && !isCurse) {
			return SpireReturn.Return(null);
		}

		if (isMarked) {
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c.cardID.equals(card.cardID))
				{
					Util.log("Mark of Exxod -- returning early from Soul.obtain() -- matching cards: " + card.cardID + ", " + c.cardID);
					return SpireReturn.Return(null);
				}
			}

			if (isGambler && !isCurse)
			{
				Util.log("Gambler Chip -- rolling to see if we will skip this card");
				int roll = AbstractDungeon.cardRandomRng.random(1, 100);
				if (roll < 34) {
					Util.log("Gambler Chip - Skipped Card");
					chip.skipped();
					chip.flash();
					return SpireReturn.Return(null);
				}
			}
		} else if (isGambler && !isCurse) {
			Util.log("Gambler Chip -- rolling to see if we will skip this card");
			int roll = AbstractDungeon.cardRandomRng.random(1, 100);
			if (roll < 34) {
				Util.log("Gambler Chip - Skipped Card");
				chip.skipped();
				chip.flash();
				return SpireReturn.Return(null);
			}
		}

		Util.log("No Special Triggers -- normal card obtain");
		handleNamelessGreedRelic(card);
		if (card.hasTag(Tags.MONSTER)) { DuelistMod.monstersObtained++; }
		if (card.hasTag(Tags.SPELL)) { DuelistMod.spellsObtained++; }
		if (card.hasTag(Tags.TRAP)) { DuelistMod.trapsObtained++; }
		if (dc != null) { dc.onObtainTrigger(); }
		return SpireReturn.Continue();

	}
	
	private static void handleNamelessGreedRelic(AbstractCard obtained)
	{
		if (AbstractDungeon.player.hasRelic(NamelessGreedRelic.ID) && DuelistMod.lastCardObtained != null)
		{
			if (DuelistMod.lastCardObtained.originalName.equals(obtained.originalName)) { AbstractDungeon.player.gainGold(25 + 15); }
			else { AbstractDungeon.player.gainGold(15); }
		}
		DuelistMod.lastCardObtained = obtained.makeCopy();
	}
}

