package duelistmod.cards.pools.aqua;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.powers.duelistPowers.FrozenDebuff;
import duelistmod.variables.Tags;

public class BlizzardDefender extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BlizzardDefender");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BlizzardDefender.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public BlizzardDefender() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 12;
        this.summons = this.baseSummons = 1;
        this.baseMagicNumber = this.magicNumber = 4;
        this.isSummon = true;       
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
        this.tags.add(Tags.TIDAL);
        this.tags.add(Tags.BAD_MAGIC);
    }
    
    @Override
    public void statBuffOnTidal()
    {
    	this.upgradeDamage(3);
    	if (this.magicNumber > 0)
    	{
    		int roll = AbstractDungeon.cardRandomRng.random(1, 6);
	    	if (roll == 1)
	    	{
	    		this.upgradeMagicNumber(-1);
	    	}
	    	else if (roll == 5)
	    	{
	    		this.upgradeSummons(1);
	    	}
    	}
    }
    
    @Override
    public void triggerOnGlowCheck()
    {
    	super.triggerOnGlowCheck();
    	for (AbstractCard c : player().hand.group)
    	{
    		if (c instanceof DuelistCard && !c.uuid.equals(this.uuid))
    		{
    			DuelistCard dc = (DuelistCard)c;
    			if (dc.summons >= this.magicNumber)
    			{
    				this.glowColor = Color.GOLD;
    				break;
    			}
    		}
    	}
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	attack(m);
    	for (AbstractCard c : p.hand.group)
    	{
    		if (c instanceof DuelistCard && !c.uuid.equals(this.uuid))
    		{
    			DuelistCard dc = (DuelistCard)c;
    			if (dc.summons >= this.magicNumber)
    			{
    				applyPower(new FrozenDebuff(m, p), m);
    				break;
    			}
    		}
    	}
    }

    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(-1);
        	this.upgradeDamage(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public void customOnTribute(DuelistCard tc)
    {
    	
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
	}
	
	@Override
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new BlizzardDefender(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
