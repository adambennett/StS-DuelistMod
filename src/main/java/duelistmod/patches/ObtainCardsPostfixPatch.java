package duelistmod.patches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.*;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.relics.*;


@SpirePatch(
		clz = Soul.class,
		method = "obtain"
		)

public class ObtainCardsPostfixPatch 
{
	public static void Postfix(Soul soul, final AbstractCard card)
	{
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		boolean exodiaDeck = (StartingDeck.currentDeck == StartingDeck.EXODIA && config.getCannotObtainCards() != null && config.getCannotObtainCards() && AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID));
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
					onObtain(dc);
				}
				return;
			}

			if (card instanceof DuelistCard) { ((DuelistCard)card).onPostObtainTrigger(); }
		} else if (isGambler && !isCurse) {
			Util.log("Gambler Chip -- rolling to see if we will skip this card");
			if (!chip.skippedLastCard() && dc != null) {
				onObtain(dc);
			}
		} else if (dc != null) {
			onObtain(dc);
		}
	}

	private static void onObtain(DuelistCard card) {
		card.onPostObtainTrigger();
		if (AbstractDungeon.player != null && AbstractDungeon.player.relics != null && card instanceof MillenniumItem) {
			for (AbstractRelic relic : AbstractDungeon.player.relics) {
				if (relic instanceof MillenniumCoin) {
					((MillenniumCoin)relic).gainGold();
					break;
				}
			}
		}
	}
}

