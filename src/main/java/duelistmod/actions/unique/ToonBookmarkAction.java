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

import java.util.ArrayList;
import java.util.List;

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
				this.addCardToHand(card, true);
				source.removeCard(card);
				this.isDone = true;
				return; 
			}
			
			// Not enough cards in draw to satisfy requested # cards, but some cards in draw
			if (tmp.size() <= this.amount) {
				int roll = AbstractDungeon.cardRng.random(0, tmp.size() - 1);
				for (int i = 0; i < tmp.size(); i++) {
					AbstractCard card = tmp.getNCardFromTop(i);
					this.addCardToHand(card, roll == i);
					source.removeCard(card);
				}
				this.isDone = true;
				return;
			}

			if (duelist.getEnemy() != null && !duelist.player()) {
				int counter = this.amount;
				List<AbstractCard> chosen = new ArrayList<>();
				while (tmp.size() > 0 && counter > 0) {
					AbstractCard random = tmp.getRandomCard(true);
					tmp.removeCard(random);
					chosen.add(random);
					counter--;
				}
				int roll = chosen.size() == 1 ? 0 : AbstractDungeon.cardRng.random(0, chosen.size() - 1);
				for (int i = 0; i < chosen.size(); i++) {
					this.addCardToHand(chosen.get(i), roll == i);
				}
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
			int roll = AbstractDungeon.cardRng.random(0, AbstractDungeon.gridSelectScreen.selectedCards.size() - 1);
            ArrayList<AbstractCard> selectedCards = AbstractDungeon.gridSelectScreen.selectedCards;
            for (int i = 0; i < selectedCards.size(); i++) {
                AbstractCard c = selectedCards.get(i);
                c.unhover();
                c.stopGlowing();
                this.addCardToHand(c, roll == i);
                source.removeCard(c);
            }
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
		tickDuration();
	}

	private void addCardToHand(AbstractCard card, boolean reduce) {
		if (reduce) {
			card.modifyCostForCombat(-this.costReduction);
		}
		this.duelist.addCardToHand(card);
	}
}


