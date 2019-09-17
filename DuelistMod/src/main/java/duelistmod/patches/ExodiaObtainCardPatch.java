package duelistmod.patches;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.helpers.*;
import duelistmod.relics.*;


@SpirePatch(
		clz = Soul.class,
		method = "obtain"
		)

public class ExodiaObtainCardPatch 
{
	@SuppressWarnings("rawtypes")
	public static SpireReturn Prefix(Soul soul, final AbstractCard card)
	{
		if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck") && AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID))
		{
			Util.log("Exodia Deck -- returning early from Soul.obtain()");
			return SpireReturn.Return(null);
		}
		else if (AbstractDungeon.player.hasRelic(MarkExxod.ID))
		{
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c.makeCopy().name.equals(card.makeCopy().name)) 
				{ 
					Util.log("Mark of Exxod -- returning early from Soul.obtain() -- matching cards: " + card.makeCopy().name + ", " + c.makeCopy().name);
					return SpireReturn.Return(null);
				}
			}
			return SpireReturn.Continue();
			
		}
		else if (AbstractDungeon.player.hasRelic(GamblerChip.ID) && !card.type.equals(CardType.CURSE))
		{
			AbstractRelic chip = AbstractDungeon.player.getRelic(GamblerChip.ID);
			Util.log("Gambler Chip -- rolling to see if we will skip this card");
			int roll = AbstractDungeon.cardRandomRng.random(1, 2);
			if (roll == 1) { Util.log("Gambler Chip - Skipped Card"); chip.flash(); return SpireReturn.Return(null); }
			else { Util.log("Gambler Chip - Obtained Card"); handleNamelessGreedRelic(card); return SpireReturn.Continue(); }
		}
		else
		{
			Util.log("No Special Triggers -- normal card obtain");
			handleNamelessGreedRelic(card);
			return SpireReturn.Continue();
		}
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

