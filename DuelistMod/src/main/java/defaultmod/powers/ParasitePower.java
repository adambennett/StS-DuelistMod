package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.cards.Parasite;
import defaultmod.interfaces.RandomEffectsHelper;

// Passive no-effect power, just lets Toon Monsters check for playability

public class ParasitePower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ParasitePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.PARASITE_POWER);
    
    private static int debuffChance = 10;
    private static int turnChance = 2;

    public ParasitePower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        updateChances();
        updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	updateChances();
    	updateDescription();
    	int randomTurnNum = AbstractDungeon.cardRandomRng.random(1, turnChance);
    	int chance = AbstractDungeon.cardRandomRng.random(0, debuffChance + 1);
    	//System.out.println("theDuelist:ParasitePower --- > Rolled: " + chance);
    	if (chance < 2) 
    	{
    		this.flash();
    		
    		// Get number of enemies
    		int monsters = AbstractDungeon.getMonsters().monsters.size();
    		
    		// Get random number of debuffs to apply randomly (1 - # monsters)
    		int enemiesToDebuff = AbstractDungeon.cardRandomRng.random(1, monsters);
    		if (enemiesToDebuff < 1) { enemiesToDebuff = 1; }
    		
    		// Debuff enemies randomly for the number of times generated above
    		for (int i = 0; i < enemiesToDebuff; i++)
    		{
    			AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
    			AbstractPower randomDebuff = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, targetMonster, randomTurnNum);
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(targetMonster, AbstractDungeon.player, randomDebuff));
    		}
    	}
    	
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
    public void onAfterUseCard(AbstractCard card, UseCardAction action) 
    {
    	updateChances();
    	updateDescription();
    }
    
    public static void updateChances()
    {
    	if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
    	{
	    	double maxSummons = Parasite.getMaxSummons(AbstractDungeon.player);
	    	double summons = Parasite.getSummons(AbstractDungeon.player);
	    	double percent = summons / maxSummons;
	    	if (percent == 1) { debuffChance = 1; turnChance = 5; }
	    	else if (percent >= 0.8) { debuffChance = 2; turnChance = 4; }
	    	else if (percent >= 0.6) { debuffChance = 3; turnChance = 4; }
	    	else if (percent >= 0.5) { debuffChance = 4; turnChance = 3; }
	    	else if (percent >= 0.4) { debuffChance = 5; turnChance = 3; }
	    	else if (percent >= 0.25) { debuffChance = 6; turnChance = 2; }
	    	else if (percent >= 0.2) { debuffChance = 7; turnChance = 2; }
	    	else { debuffChance = 8;  turnChance = 2; }
	    	//System.out.println("theDuelist:ParasitePower --- > Debuff chance changed: " + debuffChance);
	    	//System.out.println("theDuelist:ParasitePower --- > Percent was: " + percent);
    	}
    	else
    	{
    		debuffChance = 9; turnChance = 2;
    	}
    }

    @Override
	public void updateDescription() 
    {
    	int chanceMod = 0;
    	switch (debuffChance)
    	{
	    	case 1:
	    		chanceMod = 10;
	    	case 2:
	    		chanceMod = 9;
	    	case 3:
	    		chanceMod = 8;
	    	case 4:
	    		chanceMod = 7;
	    	case 5:
	    		chanceMod = 6;
	    	case 6:
	    		chanceMod = 5;
	    	case 7:
	    		chanceMod = 4;
	    	case 8:
	    		chanceMod = 3;
	    	case 9:
	    		chanceMod = 2;
    		default:
    			chanceMod = 1;
    	}
        this.description = DESCRIPTIONS[0] + chanceMod;
    }
}
