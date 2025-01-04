package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.DynamicTypeCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.powers.SummonPower;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OopsAction extends AbstractGameAction {

	private final AnyDuelist duelist;
	private final ArrayList<AbstractCard> cards;
	private DynamicTypeCard enemySelection;

	public OopsAction(AnyDuelist duelist, ArrayList<AbstractCard> cardsToChooseFrom, int amount) {
		this.duelist = duelist;
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount > 0 ? amount : 1;
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

				if (this.amount > 0 && tmp.group.size() > 0) {
					SelectScreenHelper.open(tmp, 1, "Choose a type to Tribute");
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
				AbstractCard randomCard = tmp.getRandomCard(true);
				if (randomCard instanceof DynamicTypeCard) {
					this.enemySelection = (DynamicTypeCard) randomCard;
				}
				tickDuration();
				return;
			}
		}

		if (this.duelist.player() && AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
			SummonPower summonPower = null;
			if (this.duelist.hasPower(SummonPower.POWER_ID)) {
				summonPower = (SummonPower)this.duelist.getPower(SummonPower.POWER_ID);
			}
			if (summonPower != null) {
				HashSet<CardTags> typesToTribute = new HashSet<>();
				DynamicTypeCard ref = null;
				for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
					c.unhover();
					c.stopGlowing();
					if (c instanceof DynamicTypeCard) {
						ref = (DynamicTypeCard)c;
						typesToTribute.add(ref.getTagSave());
					}
				}

				if (ref != null) {
					List<DuelistCard> tributes = ref.tributeAllOfTypes(typesToTribute, summonPower);
					int draw = tributes.size()/this.amount;
					if (draw > 0) {
						this.duelist.draw(draw);
					}
				}
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
				this.duelist.getPlayer().hand.refreshHandLayout();
			}
			this.isDone = true;
			return;
		} else if (this.duelist.getEnemy() != null && this.enemySelection != null) {
			SummonPower summonPower = null;
			if (this.duelist.hasPower(SummonPower.POWER_ID)) {
				summonPower = (SummonPower)this.duelist.getPower(SummonPower.POWER_ID);
			}
			if (summonPower != null) {
				HashSet<CardTags> typesToTribute = new HashSet<>();
				typesToTribute.add(this.enemySelection.getTagSave());
				List<DuelistCard> tributes = this.enemySelection.tributeAllOfTypes(typesToTribute, summonPower);
				int draw = tributes.size()/this.amount;
				if (draw > 0) {
					this.duelist.draw(draw);
				}
			}
			this.enemySelection = null;
			this.isDone = true;
			return;
		}
		tickDuration();
	}

}
