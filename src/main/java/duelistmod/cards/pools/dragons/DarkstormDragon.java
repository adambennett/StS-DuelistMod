package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class DarkstormDragon extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DarkstormDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkstormDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public DarkstormDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	for (AbstractCard c : player().hand.group)
    	{
    		if (c.hasTag(Tags.DRAGON) && !c.uuid.equals(this.uuid))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.isTributeCard()) { dC.modifyTributes(-this.magicNumber); }
    		}
    	}    
    	
    	for (AbstractCard c : player().drawPile.group)
    	{
    		if (c.hasTag(Tags.DRAGON) && !c.uuid.equals(this.uuid))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.isTributeCard()) { dC.modifyTributes(-this.magicNumber); }
    		}
    	} 
    	
    	for (AbstractCard c : player().discardPile.group)
    	{
    		if (c.hasTag(Tags.DRAGON) && !c.uuid.equals(this.uuid))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.isTributeCard()) { dC.modifyTributes(-this.magicNumber); }
    		}
    	} 
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkstormDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {        
    	if (!upgraded)
    	{
	    	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
	        this.upgradeMagicNumber(1);
	        this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
	        this.initializeDescription();       
    	}
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		dragonSynTrib(tributingCard);
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
