package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;


public class DarknessNeospherePower extends TwoAmountPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("DarknessNeospherePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("DarknessNeospherePower.png");
    public int turnDmg = 3;

    public DarknessNeospherePower(AbstractCreature owner, AbstractCreature source, int tribInc, int strLoss) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.source = source;
        this.owner = owner;
        this.amount = tribInc;
        this.amount2 = strLoss;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.updateDescription();
    }
    
    @Override
	public void onCardDraw(final AbstractCard card) 
	{
		if (card.hasTag(Tags.MONSTER) && card instanceof DuelistCard) 
		{ 
			DuelistCard dc = (DuelistCard)card;
			if (dc.isTributeCard(true))
			{
				this.flash(); 
				dc.modifyTributesForTurn(this.amount);
				for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
				{
		    		if (!m.isDeadOrEscaped() && !m.isDying && !m.isDead && !m.halfDead)
		    		{
		    			DuelistCard.applyPower(new StrengthDownPower(m, AbstractDungeon.player, 1, this.amount2), m);
		    		}
				}
			}
		}
    }

    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }
}
