package duelistmod.patches;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

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
		else if (AbstractDungeon.player.hasRelic(GamblerChip.ID) && !card.type.equals(CardType.CURSE))
		{
			AbstractRelic chip = AbstractDungeon.player.getRelic(GamblerChip.ID);
			Util.log("Gambler Chip -- rolling to see if we will skip this card");
			int roll = AbstractDungeon.cardRandomRng.random(1, 2);
			if (roll == 1) { Util.log("Gambler Chip - Skipped Card"); chip.flash(); return SpireReturn.Return(null); }
			else { Util.log("Gambler Chip - Obtained Card"); return SpireReturn.Continue(); }
		}
		else
		{
			Util.log("No Exodia Deck -- normal card obtain");
			return SpireReturn.Continue();
		}
	}
}

