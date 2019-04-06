package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.patches.*;

public class BeastFangs extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BeastFangs");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.BEAST_FANGS);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public BeastFangs() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.purgeOnUse = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (!DuelistMod.gotBeastStr)
    	{
    		DuelistMod.gotBeastStr = true; 
    		DuelistMod.beastStrSummons = (int)Math.floor(DuelistMod.summonCombatCount/2);
    		if (DuelistMod.debug)
    		{
    			System.out.println("theDuelist:BeastFangs:use() ---> set beastStrSummons to: " + (int)Math.floor(DuelistMod.summonCombatCount/2));
    			System.out.println("theDuelist:BeastFangs:use() ---> summons this combat so far is: " + DuelistMod.summonCombatCount);
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BeastFangs();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {        
    	if (this.canUpgrade())
    	{
    		if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
    		if (timesUpgraded == 1) { this.upgradeBaseCost(1); }
    		else if (timesUpgraded == 2) { this.upgradeBaseCost(0); }
	        this.rawDescription = UPGRADE_DESCRIPTION;
	        this.initializeDescription();       
    	}
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.cost > 0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
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