package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayRandomFromDiscardAction extends AbstractGameAction {
	private final AbstractPlayer p;
	private boolean upgrade = false;
	private final UUID callingCard;
	private final AbstractMonster m;
	private int copies = 1;


	public PlayRandomFromDiscardAction(int amount, int copies, UUID callingCard) {
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = AbstractDungeon.getRandomMonster();
		this.callingCard = callingCard;
		this.copies = copies;
	}
	

	public PlayRandomFromDiscardAction(int amount, boolean upgraded, AbstractMonster m, UUID callingCard) {
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.m = m;
		this.callingCard = callingCard;
	}

	public static List<AbstractCard> getCardsFromDiscard(int amount, AnyDuelist duelist, UUID callingCard) {
		if (amount < 1 || duelist == null || callingCard == null) {
			return new ArrayList<>();
		}

		List<AbstractCard> output = new ArrayList<>();
		CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		for (AbstractCard c : duelist.discardPile()) {
			if (DuelistCard.allowResummonsWithExtraChecks(c) && !c.uuid.equals(callingCard)) {
				tmp.addToRandomSpot(c);
			}
		}

		if (tmp.size() == 0) {
			return output;
		}

		if (tmp.size() == 1) {
			AbstractCard card = tmp.getTopCard();
			if (DuelistCard.allowResummonsWithExtraChecks(card)) {
				for (int i = 0; i < amount; i++) {
					output.add(card);
				}
			}
		} else if (tmp.size() <= amount) {
			ArrayList<AbstractCard> cardsToPlayFrom = new ArrayList<>();
			for (int i = 0; i < tmp.size(); i++) {
				AbstractCard card = tmp.getNCardFromTop(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
				while (cardsToPlayFrom.contains(card)) {
					card = tmp.getNCardFromTop(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
				}
				cardsToPlayFrom.add(card);
			}
            output.addAll(cardsToPlayFrom);
		} else if (tmp.size() >= amount) {
			ArrayList<AbstractCard> cardsToPlayFrom = new ArrayList<>();
			for (int i = 0; i < amount; i++) {
				AbstractCard card = tmp.getNCardFromTop(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
				while (cardsToPlayFrom.contains(card)) {
					card = tmp.getNCardFromTop(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
				}
				cardsToPlayFrom.add(card);
			}

			output.addAll(cardsToPlayFrom);
		}
		return output;
	}

	public void update() {
		if (this.duration == Settings.ACTION_DUR_MED) {
			List<AbstractCard> toPlay = getCardsFromDiscard(this.amount, AnyDuelist.from(this.p), callingCard);
			for (AbstractCard play : toPlay) {
				DuelistCard.resummon(play, m, this.copies, this.upgrade, false);
			}
			this.isDone = true;
		}
		tickDuration();
	}
}


