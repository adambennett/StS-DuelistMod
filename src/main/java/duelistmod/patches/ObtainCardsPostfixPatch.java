package duelistmod.patches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.*;
import duelistmod.relics.*;


@SpirePatch(
		clz = Soul.class,
		method = "obtain"
		)

public class ObtainCardsPostfixPatch 
{
	public static void Postfix(Soul soul, final AbstractCard card)
	{
		boolean exodiaDeck = (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck") && AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID));
		boolean isCurse = card.type.equals(CardType.CURSE);
		boolean isMarked = AbstractDungeon.player.hasRelic(MarkExxod.ID);
		boolean isGambler = AbstractDungeon.player.hasRelic(GamblerChip.ID);
		DuelistCard dc = card instanceof DuelistCard ? (DuelistCard)card : null;
		GamblerChip chip = isGambler ? (GamblerChip)AbstractDungeon.player.getRelic(GamblerChip.ID) : null;

		if (exodiaDeck && !isCurse) {
			return;
		}

		if (isMarked) {
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c.cardID.equals(card.cardID))
				{
					Util.log("Mark of Exxod -- returning early from Soul.obtain() -- matching cards: " + card.cardID + ", " + c.cardID);
					return;
				}
			}

			if (isGambler && !isCurse)
			{
				Util.log("Gambler Chip -- rolling to see if we will skip this card");
				if (!chip.skippedLastCard() && dc != null) {
					dc.onPostObtainTrigger();
				}
				return;
			}

			if (card instanceof DuelistCard) { ((DuelistCard)card).onPostObtainTrigger(); }
		} else if (isGambler && !isCurse) {
			Util.log("Gambler Chip -- rolling to see if we will skip this card");
			if (!chip.skippedLastCard() && dc != null) {
				dc.onPostObtainTrigger();
			}
		} else if (dc != null) {
			dc.onPostObtainTrigger();
		}
	}
}

