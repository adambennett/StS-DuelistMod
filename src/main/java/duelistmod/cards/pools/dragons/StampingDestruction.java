package duelistmod.cards.pools.dragons;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.Boot;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class StampingDestruction extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("StampingDestruction");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("StampingDestruction.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public StampingDestruction() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.baseAFX = AttackEffect.BLUNT_HEAVY;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// This is intentionally just like a whole lotta shit
    	int maxDmgCap = 90 + (this.magicNumber * 10);
    	if (maxDmgCap == 190) { maxDmgCap = 200; }
    	int maxDmg = AbstractDungeon.cardRandomRng.random(15, maxDmgCap);
    	int maxWeak = AbstractDungeon.cardRandomRng.random(1, 5);
    	ArrayList<AbstractMonster> mons = getAllMons();
    	maxWeak = maxWeak * mons.size();
    	int dmgCap = 5;
    	if (AbstractDungeon.cardRandomRng.random(1, 6) == 1) { dmgCap += this.magicNumber; }
    	else if (AbstractDungeon.cardRandomRng.random(1, 6) == 1) { maxWeak += this.magicNumber; }
    	int dmgRoll = AbstractDungeon.cardRandomRng.random(1, dmgCap);
    	if (p.hasRelic(Boot.ID)) { dmgRoll = AbstractDungeon.cardRandomRng.random(5, dmgCap); }
    	int maxDmgTimes = (int) ((float)maxDmg / (float)dmgRoll);
    	int minDmgTimes = 1;
    	while (minDmgTimes * dmgRoll < 15) { minDmgTimes++; }
    	if (minDmgTimes > maxDmgTimes) { maxDmgTimes = minDmgTimes + 1; }
    	int dmgTimesRoll = AbstractDungeon.cardRandomRng.random(minDmgTimes, maxDmgTimes);
    	Util.log("Stamping Destruction intends to deal " + (dmgRoll * dmgTimesRoll) + " total damage (split between enemies randomly) and apply " + maxWeak + " total Weak.");
    	while (maxWeak > 0 || dmgTimesRoll > 0)
    	{
    		if (maxWeak > 0 && dmgTimesRoll > 0)
    		{
    			int actionRoll = AbstractDungeon.cardRandomRng.random(1, 2);
    			if (actionRoll == 1)
    			{
    				AbstractMonster rand = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() -1 ));
            		applyPower(new WeakPower(rand, 1, false), rand);
            		maxWeak--;
    			}
    			else
    			{
    				AbstractMonster rand = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() -1 ));
            		if (mons.size() == 1) 
            		{ 
            			attack(rand, this.baseAFX, dmgRoll * dmgTimesRoll);
            			dmgTimesRoll = 0;
            		}
            		else 
            		{ 
            			attack(rand, this.baseAFX, dmgRoll);
            			dmgTimesRoll--; 
            		}
    				
    			}
    		}
    		else if (maxWeak > 0)
    		{
    			AbstractMonster rand = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() -1 ));
        		applyPower(new WeakPower(rand, 1, false), rand);
        		maxWeak--;
    		}
    		
    		else if (dmgTimesRoll > 0)
    		{
    			AbstractMonster rand = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() -1 ));
    			if (mons.size() == 1) 
        		{ 
        			attack(rand, this.baseAFX, dmgRoll * dmgTimesRoll);
        			dmgTimesRoll = 0;
        		}
        		else 
        		{ 
        			attack(rand, this.baseAFX, dmgRoll);
        			dmgTimesRoll--; 
        		}
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new StampingDestruction();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (canUpgrade()) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription(); 
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 10) { return true; }
    	else { return false; }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard)
	{
		
	}

	

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
   
}
