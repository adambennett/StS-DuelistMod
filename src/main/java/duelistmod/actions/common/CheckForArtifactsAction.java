package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class CheckForArtifactsAction extends AbstractGameAction 
{
	private boolean incDamage = true;
	private AbstractCard cardToMod;
	
	public CheckForArtifactsAction(boolean incDamage, int incAmt, AbstractCard card) 
	{
		this.setValues(this.target, this.source, this.amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.incDamage = incDamage;
		this.amount = incAmt;
		this.cardToMod = card;
	}
	
	@Override
	public void update() 
	{
		if (AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID))
		{
			if (this.incDamage)
			{
				this.addToBot(new ModifyDamageAction(this.cardToMod.uuid, this.amount));
			}
			else
			{
				this.addToBot(new ModifyBlockAction(this.cardToMod.uuid, this.amount));
			}
		}
		this.isDone = true;
	}	
}
