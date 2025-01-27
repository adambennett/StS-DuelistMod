package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.pools.dragons.CyberPhoenixEnergy;
import duelistmod.cards.pools.dragons.CyberPhoenixTribute;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.SelectScreenHelper;
import java.util.ArrayList;

public class CyberPhoenixAction extends AbstractGameAction {

	private final AnyDuelist duelist;
	private final ArrayList<AbstractCard> cards;
	private AbstractCard enemySelection;

	public CyberPhoenixAction(AnyDuelist duelist, ArrayList<AbstractCard> cardsToChooseFrom, int amount) {
		this.duelist = duelist;
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = Math.max(amount, 0);
		this.cards = cardsToChooseFrom == null ? new ArrayList<>() : cardsToChooseFrom;
	}

	public void update() {
		if (this.duration == Settings.ACTION_DUR_MED) {
			if (this.duelist.player() && !this.cards.isEmpty()) {
				CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
				for (AbstractCard card : this.cards) {
					AbstractCard gridCard = card.makeStatEquivalentCopy();
					gridCard.initializeDescription();
					tmp.addToTop(gridCard);
				}

				if (tmp.group.size() > 0) {
					SelectScreenHelper.open(tmp, 1, "Choose an effect");
					tickDuration();
					return;
				}
			} else if (this.duelist.getEnemy() != null && !this.cards.isEmpty()) {
				CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
				for (AbstractCard card : this.cards) {
					AbstractCard gridCard = card.makeStatEquivalentCopy();
					gridCard.initializeDescription();
					tmp.addToTop(gridCard);
				}
                this.enemySelection = tmp.getRandomCard(true);
				tickDuration();
				return;
			}
		}

		if (this.duelist.player() && AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
				AbstractCard selection = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
				boolean energyReduce = false;
				boolean tributeReduce = false;
				if (selection instanceof CyberPhoenixEnergy) {
					energyReduce = true;
					selection.unhover();
					selection.stopGlowing();
				} else if (selection instanceof CyberPhoenixTribute) {
					tributeReduce = true;
					selection.unhover();
					selection.stopGlowing();
				}

				if (energyReduce) {
					this.duelist.hand().forEach(c -> c.setCostForTurn(this.amount));
				} else if (tributeReduce) {
					this.duelist.hand().forEach(c -> {
						if (c instanceof DuelistCard) {
							((DuelistCard)c).setTributesForTurn(this.amount);
						}
					});
				}

				AbstractDungeon.gridSelectScreen.selectedCards.clear();
				this.duelist.getPlayer().hand.refreshHandLayout();

			this.isDone = true;
			return;
		} else if (this.duelist.getEnemy() != null && this.enemySelection != null) {
			boolean energyReduce = false;
			boolean tributeReduce = false;
			if (this.enemySelection instanceof CyberPhoenixEnergy) {
				energyReduce = true;
			} else if (this.enemySelection instanceof CyberPhoenixTribute) {
				tributeReduce = true;
			}

			if (energyReduce) {
				this.duelist.hand().forEach(c -> c.setCostForTurn(this.amount));
			} else if (tributeReduce) {
				this.duelist.hand().forEach(c -> {
					if (c instanceof DuelistCard) {
						((DuelistCard)c).setTributesForTurn(this.amount);
					}
				});
			}
			this.enemySelection = null;
			this.isDone = true;
			return;
		}
		tickDuration();
	}

}
