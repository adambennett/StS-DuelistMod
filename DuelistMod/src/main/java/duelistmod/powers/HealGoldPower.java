package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.BloodyIdol;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

@SuppressWarnings("unused")
public class HealGoldPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("HealGoldPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.IMPERIAL_POWER);
    public int DAMAGE = 1;
    public int HP_GAIN_TRIGGER = 1;
    
	private static boolean triggered = false;
    public HealGoldPower(final AbstractCreature owner, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;     
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.amount = newAmount;
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	triggered = false;
    	updateDescription();
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	triggered = false;
    	updateDescription();
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	updateDescription();
    }
    
    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) 
    {
    	triggered = false;
    	updateDescription();
    }
    
    @Override
    public void onEvokeOrb(AbstractOrb orb) 
    {
    	triggered = false;
    	updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	triggered = false;
    	updateDescription();
	}

    @Override
    public int onHeal(int healAmount)
    {
    	if (!triggered) { DuelistCard.gainGold(healAmount * this.amount, AbstractDungeon.player, true); }
    	triggered = true;
    	return healAmount;
    }

    @Override
	public void updateDescription() 
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
