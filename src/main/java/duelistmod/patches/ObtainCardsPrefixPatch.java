package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.MagnetCard;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.MagnetTransformHelper;
import duelistmod.helpers.Util;
import duelistmod.relics.GamblerChip;
import duelistmod.relics.MarkExxod;
import duelistmod.relics.MillenniumPuzzle;
import duelistmod.relics.NamelessGreedRelic;
import duelistmod.variables.Tags;

@SpirePatch(clz = Soul.class, method = "obtain")
public class ObtainCardsPrefixPatch {

	public static SpireReturn<Void> Prefix(Soul soul, @ByRef AbstractCard[] card) {
		if (card == null || card.length == 0 || card[0] == null) {
			return SpireReturn.Continue();
		}

		if (card[0] instanceof MagnetCard) {
			MagnetCard original = (MagnetCard) card[0];
			MagnetCard replacement = MagnetTransformHelper.normalizeIfNeeded(original);

			if (replacement != null && replacement != original) {
				card[0] = replacement;
			}
		}

		AbstractCard c = card[0];
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		boolean exodiaDeck =
				(StartingDeck.currentDeck == StartingDeck.EXODIA
						&& config.getCannotObtainCards() != null
						&& config.getCannotObtainCards()
						&& AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID));

		boolean isCurse = c.type.equals(CardType.CURSE);
		boolean isMarked = AbstractDungeon.player.hasRelic(MarkExxod.ID);
		boolean isGambler = AbstractDungeon.player.hasRelic(GamblerChip.ID);
		DuelistCard dc = c instanceof DuelistCard ? (DuelistCard) c : null;
		GamblerChip chip = isGambler ? (GamblerChip) AbstractDungeon.player.getRelic(GamblerChip.ID) : null;

		if (exodiaDeck && !isCurse) {
			return SpireReturn.Return(null);
		}

		if (isMarked) {
			for (AbstractCard deckCard : AbstractDungeon.player.masterDeck.group) {
				if (deckCard.cardID.equals(c.cardID)) {
					Util.log("Mark of Exxod -- returning early from Soul.obtain() -- matching cards: " + c.cardID + ", " + deckCard.cardID);
					return SpireReturn.Return(null);
				}
			}

			if (isGambler && !isCurse) {
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
		handleNamelessGreedRelic(c);
		if (c.hasTag(Tags.MONSTER)) DuelistMod.monstersObtained++;
		if (c.hasTag(Tags.SPELL)) DuelistMod.spellsObtained++;
		if (c.hasTag(Tags.TRAP)) DuelistMod.trapsObtained++;
		if (dc != null) dc.onObtainTrigger();

		return SpireReturn.Continue();
	}

	private static void handleNamelessGreedRelic(AbstractCard obtained) {
		if (AbstractDungeon.player.hasRelic(NamelessGreedRelic.ID) && DuelistMod.lastCardObtained != null) {
			if (DuelistMod.lastCardObtained.originalName.equals(obtained.originalName)) {
				AbstractDungeon.player.gainGold(25 + 15);
			} else {
				AbstractDungeon.player.gainGold(15);
			}
		}
		DuelistMod.lastCardObtained = obtained.makeCopy();
	}
}
