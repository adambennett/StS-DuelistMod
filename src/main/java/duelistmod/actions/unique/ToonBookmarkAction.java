package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.variables.Strings;

public class ToonBookmarkAction extends AbstractGameAction {

	private final AnyDuelist duelist;
	private final CardTags searchTag;
	private final CardGroup source;
	private final int costReduction;

	public ToonBookmarkAction(int cardsToFetch, int costReduction, CardTags tag, AnyDuelist duelist) {
		setValues(duelist.creature(), duelist.creature(), cardsToFetch);
		this.duelist = duelist;
		this.costReduction = costReduction;
		this.amount = cardsToFetch;
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.searchTag = tag;
		this.source = duelist.drawPileGroup();
	}

	public void update() {
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) {
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : source.group) {
				if (c.hasTag(this.searchTag)) {
					tmp.addToRandomSpot(c);
				}
			}

			// No cards in discard pile
			if (tmp.size() == 0) {
				this.isDone = true;
				return; 
			}
			
			// Only 1 card in draw pile
			if (tmp.size() == 1) {
				AbstractCard card = tmp.getTopCard();
				this.addCardToHand(card);
				source.removeCard(card);
				this.isDone = true;
				return; 
			}
			
			// Not enough cards in draw to satisfy requested # cards, but some cards in draw
			if (tmp.size() <= this.amount) {
				for (int i = 0; i < tmp.size(); i++) {
					AbstractCard card = tmp.getNCardFromTop(i);
					this.addCardToHand(card);
					source.removeCard(card);
				}
				this.isDone = true;
				return;
			}

			if (duelist.getEnemy() != null && !duelist.player()) {
				AbstractCard random = tmp.getRandomCard(true);
				this.addCardToHand(random);
				this.isDone = true;
            } else {
				// Open card selection window
				if (this.amount == 1) {
					SelectScreenHelper.open(tmp, this.amount, Strings.configChooseAString + searchTag.name().toLowerCase() + Strings.configAddCardHandString);
				} else {
					SelectScreenHelper.open(tmp, this.amount, Strings.configChooseString + this.amount + " " + searchTag.name().toLowerCase() + Strings.configAddCardHandPluralString);
				}
				tickDuration();
            }
			return;
        }

		if (duelist.player() && AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				c.unhover();
				c.stopGlowing();
				this.addCardToHand(c);
				source.removeCard(c);
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
		tickDuration();
	}

	private void addCardToHand(AbstractCard card) {
		int originalCost = card.cost;
		card.cost -= this.costReduction;
		if (card.cost < 0) card.cost = 0;
		if (card.cost != originalCost) card.isCostModified = true;
		this.duelist.addCardToHand(card);
	}
}


