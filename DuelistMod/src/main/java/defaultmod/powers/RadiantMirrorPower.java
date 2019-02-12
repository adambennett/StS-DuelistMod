package defaultmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.cards.DarkMirrorForce;


public class RadiantMirrorPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DefaultMod.makeID("RadiantMirrorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.RADIANT_POWER);
    
    public boolean upgrade = false;
    public int damage = 5;

    public RadiantMirrorPower(AbstractCreature owner, boolean upgrade) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.upgrade = upgrade;
        this.type = PowerType.BUFF;
    }

    @Override
    public int onLoseHp(int damageAmount)
    {
    	ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
    	for (AbstractMonster m : monsters)
    	{
    		if (m.currentBlock > 0) { AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(m, m)); }
    		if (upgrade) { AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH)); }
    	}
    	DarkMirrorForce.removePower(this, AbstractDungeon.player);
    	return damageAmount;
    }
    
    @Override
	public void updateDescription() 
    {
    	if (!upgrade) { this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2]; }
    }
}
