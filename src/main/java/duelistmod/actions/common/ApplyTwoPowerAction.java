package duelistmod.actions.common;



import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ApplyTwoPowerAction extends ApplyPowerAction
{
	public ApplyTwoPowerAction(AbstractCreature target, AbstractCreature source, TwoAmountPower powerToApply, int stackAmount, int stackAmount2)
	{
		super(target, source, powerToApply, stackAmount, true);
		powerToApply.amount2++;
	}
}