package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import basemod.abstracts.CustomCard;
import duelistmod.*;
import duelistmod.actions.common.SpecificCardDiscardToDeckAction;
import duelistmod.cards.AxeDespair;
import duelistmod.patches.DuelistCard;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class DespairPower extends AbstractPower 
{
	public AbstractCreature source;

	public static final String POWER_ID = duelistmod.DuelistMod.makeID("DespairPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.DESPAIR_POWER);
	public static CustomCard ATTACHED_AXE = null;
	
	private static int TRIBUTES = 2;
	private static int STR_LOSS = 9;
	private static int DAMAGE = 30;

	public DespairPower(final AbstractCreature owner, final AbstractCreature source, final CustomCard card, int strLoss, int newAmount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;		
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = newAmount;
		ATTACHED_AXE = card;
		STR_LOSS = strLoss;
		this.updateDescription();
	}
	
	@Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
	
	@Override
    public void atStartOfTurn() 
    {
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
	// At the end of the turn, remove gained Strength.
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		boolean triggeredDamage = false;
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		else if (AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID))
		{
			GravityAxePower power = (GravityAxePower) AbstractDungeon.player.getPower(GravityAxePower.POWER_ID);
			power.onSpecificTrigger();
			DuelistCard.removePower(this, this.owner);
		}
		else
		{
			for (int i = 0; i < this.amount; i++)
			{
				if (this.amount > 0)
				{
					int tribs = AxeDespair.powerTribute(AbstractDungeon.player, TRIBUTES, false);
					if (tribs < TRIBUTES) { triggeredDamage = true; }
			
					// Lose Strength
					DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, -1 * STR_LOSS));
					
					// Take the Axe of Despair we just played out of the discard and put on top of the deck
					AbstractDungeon.actionManager.addToTop(new SpecificCardDiscardToDeckAction(this.owner, ATTACHED_AXE));
					AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
				}
			}
			
			if (triggeredDamage) { AxeDespair.damageSelfNotHP(DAMAGE); }
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + STR_LOSS * this.amount + DESCRIPTIONS[1] + 2 * this.amount + DESCRIPTIONS[2];
	}
}
