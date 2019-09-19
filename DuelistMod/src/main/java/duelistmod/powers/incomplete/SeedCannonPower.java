package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.actions.unique.SeedCannonAction;
import duelistmod.cards.tempCards.*;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class SeedCannonPower extends TwoAmountPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("SeedCannonPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("SeedCannonPower.png");
	private boolean finished = false;
	
	public SeedCannonPower(final AbstractCreature owner, final AbstractCreature source, int damage, int seeds) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = seeds;
		this.amount2 = damage;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0] + (this.amount * this.amount2) + DESCRIPTIONS[1] + this.amount;
	}
	
	
	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		AbstractDungeon.actionManager.addToTop(new SeedCannonAction(new SeedCannonConfirm(this.amount2 * this.amount)));
	}
}
