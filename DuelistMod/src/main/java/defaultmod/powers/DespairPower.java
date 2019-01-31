package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.actions.SpecificCardDiscardToDeckAction;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class DespairPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("DespairPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.DESPAIR_POWER);
    public static CustomCard ATTACHED_AXE = null;
       
    public DespairPower(final AbstractCreature owner, final AbstractCreature source, final CustomCard card) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = 1;
        ATTACHED_AXE = card;

    }

    // At the end of the turn, remove gained Strength.
    @Override
    public void atEndOfTurn(final boolean isPlayer) 
    {
    	if (this.owner.hasPower(SummonPower.POWER_ID))
    	{
           AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, SummonPower.POWER_ID, 1));
    	}
    	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Strength", this.amount));
    	AbstractDungeon.actionManager.addToBottom(new SpecificCardDiscardToDeckAction(this.owner, ATTACHED_AXE));
    	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, DespairPower.POWER_ID, 1));
    }
    
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
