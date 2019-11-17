package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.StormToken;
import duelistmod.variables.Strings;

public class StormingMirrorPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = duelistmod.DuelistMod.makeID("StormingMirrorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.STORMING_POWER);

    public StormingMirrorPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;       
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.updateDescription();
    }
    
    @Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
    	if ((info.type != DamageInfo.DamageType.THORNS) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner) && (!this.owner.hasPower("Buffer")))
    	{
    		DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new StormToken());
			DuelistCard.summon(AbstractDungeon.player, 1, tok);
    	}
    	return damageAmount;
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { this.amount = 0; }
	}

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
