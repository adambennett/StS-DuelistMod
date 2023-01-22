package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.*;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.RefreshHandGlowAction;

public class FrozenDebuff extends DuelistPower implements HealthBarRenderPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("FrozenDebuff");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("FrozenDebuff.png");
	
	public FrozenDebuff(AbstractCreature owner, AbstractCreature source) 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = 0;
		updateDescription(); 
	}
	
	@Override
	public void atEndOfRound()
	{
		DuelistCard.removePower(this, this.owner);
		AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction());
	}
	
	@Override
    public float atDamageReceive(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }
        return damage * 1.50f;
    }
	
	@Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }
        return damage * 0.90f;
    }

	@Override
	public void updateDescription()
	{
		this.amount = 0;
		this.description = DESCRIPTIONS[0]; 
	}
	
	@Override
	public void playApplyPowerSfx()
	{
		CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.05f);
	}
	
	@Override
	public Color getColor() {
		return Color.SKY;
	}

	@Override
	public int getHealthBarAmount() {
		return this.owner.currentHealth;
	}

}
