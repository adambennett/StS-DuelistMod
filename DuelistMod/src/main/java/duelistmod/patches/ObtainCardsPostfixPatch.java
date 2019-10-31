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
		if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck") && AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID))
		{
			return;
		}
		else if (AbstractDungeon.player.hasRelic(MarkExxod.ID))
		{
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c.makeCopy().name.equals(card.makeCopy().name)) 
				{ 
					Util.log("Mark of Exxod -- returning early from Soul.obtain() -- matching cards: " + card.makeCopy().name + ", " + c.makeCopy().name);
					return;
				}
			}
			
			if (AbstractDungeon.player.hasRelic(GamblerChip.ID) && !card.type.equals(CardType.CURSE))
			{
				GamblerChip chip = (GamblerChip)AbstractDungeon.player.getRelic(GamblerChip.ID);
				Util.log("Gambler Chip -- rolling to see if we will skip this card");
				if (chip.skippedLastCard()) { return; }
				else 
				{ 
					if (card instanceof DuelistCard) { ((DuelistCard)card).onPostObtainTrigger(); }
					return;
				}
			}
			
			if (card instanceof DuelistCard) { ((DuelistCard)card).onPostObtainTrigger(); }
			return;
		}
		else if (AbstractDungeon.player.hasRelic(GamblerChip.ID) && !card.type.equals(CardType.CURSE))
		{
			GamblerChip chip = (GamblerChip)AbstractDungeon.player.getRelic(GamblerChip.ID);
			Util.log("Gambler Chip -- rolling to see if we will skip this card");
			if (chip.skippedLastCard()) { return; }
			else 
			{ 
				if (card instanceof DuelistCard) { ((DuelistCard)card).onPostObtainTrigger(); }
				return;
			}
		}
		else
		{
			if (card instanceof DuelistCard) { ((DuelistCard)card).onPostObtainTrigger(); }
			return;
		}
	}
}

