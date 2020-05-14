package duelistmod.cards.pools.insects;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class PoisonChain extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("PoisonChain");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PoisonChain.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 4;
    // /STAT DECLARATION/

    public PoisonChain() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.misc = 0;
        this.tags.add(Tags.SPELL);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower summPow = (SummonPower)p.getPower(SummonPower.POWER_ID);
    		int insects = summPow.getNumberOfTypeSummoned(Tags.INSECT);
    		if (insects > 0)
    		{
    			ArrayList<AbstractMonster> mons = new ArrayList<AbstractMonster>();
    			for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
    			{
    				if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead) { mons.add(mon); }
    			}
    			
    			if (mons.size() > 0)
    			{
    				for (int j = 0; j < mons.size(); j++)
    				{
    					int poisonCounter = 1;
        				double poisCount = 0.0;
    					for (int i = 0; i < insects; i++)
    					{
    						AbstractMonster randTarg = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1));
    						applyPower(new PoisonPower(randTarg, p, poisonCounter), randTarg);
    						poisCount += 0.5;
    						if (poisCount % 1 == 0) { poisonCounter++; }
    					}
    				}
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
        	this.upgradeBaseCost(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
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
    public AbstractCard makeCopy() { return new PoisonChain(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}