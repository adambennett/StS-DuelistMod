package duelistmod.actions.common;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;

public class ResummonModAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{
	AbstractCard card;
	boolean flight = false;
	boolean thorns = false;

	public ResummonModAction(AbstractCard c, boolean flight, boolean thornsForResummon)
	{
		setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.card = c;
		this.flight = flight;
		this.thorns = thornsForResummon;
	}

	public void update()
	{
		if (flight) { card.baseDamage = card.baseDamage / 2; }
		if (thorns) { card.damageTypeForTurn = DamageType.THORNS; }
		this.isDone = true;
	}
}
