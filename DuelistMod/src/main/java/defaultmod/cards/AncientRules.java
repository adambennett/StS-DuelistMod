package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class AncientRules extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("AncientRules");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.ANCIENT_RULES);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public AncientRules() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.summons = 2;
        this.upgradeSummons = 1;
        this.tags.add(DefaultMod.SPELL);
        this.tags.add(DefaultMod.LIMITED);
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, new Token("Ancient Token"));
    	for (AbstractCard c : p.drawPile.group)
    	{
    		if (c.hasTag(DefaultMod.MONSTER))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.tributes > 0)
    			{
    				dC.originalDescription = dC.rawDescription;
    				dC.tributes = 1;
    				dC.gotPipered = true;
    				int indexOfTribText = c.rawDescription.indexOf("Tribute");
    				if (indexOfTribText > 0)
    				{
    					String newDesc = c.rawDescription.substring(0, indexOfTribText) + "Tribute 1. NL " + c.rawDescription.substring(indexOfTribText + 14);
    					
    					c.rawDescription = newDesc;
    					if (DefaultMod.debug) { System.out.println("Ancient Rules made a string: " + newDesc); }
    					c.initializeDescription();
    				}
    				else
    				{
    					String newDesc = "Tribute 1. NL " + c.rawDescription.substring(indexOfTribText + 14);
    					
    					c.rawDescription = newDesc;
    					if (DefaultMod.debug) { System.out.println("Ancient Rules made a string: " + newDesc); }
    					c.initializeDescription();
    				}
    			}
    		}
    	}
    	
    	for (AbstractCard c : p.hand.group)
    	{
    		if (c.hasTag(DefaultMod.MONSTER))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.tributes > 0)
    			{
    				dC.originalDescription = dC.rawDescription;
    				dC.tributes = 1;
    				dC.gotPipered = true;
    				int indexOfTribText = c.rawDescription.indexOf("Tribute");
    				if (indexOfTribText > 0)
    				{
    					String newDesc = c.rawDescription.substring(0, indexOfTribText) + "Tribute 1. NL " + c.rawDescription.substring(indexOfTribText + 14);
    					
    					c.rawDescription = newDesc;
    					if (DefaultMod.debug) { System.out.println("Ancient Rules made a string: " + newDesc); }
    					c.initializeDescription();
    				}
    				else
    				{
    					String newDesc = "Tribute 1. NL " + c.rawDescription.substring(indexOfTribText + 14);
    					
    					c.rawDescription = newDesc;
    					if (DefaultMod.debug) { System.out.println("Ancient Rules made a string: " + newDesc); }
    					c.initializeDescription();
    				}
    			}
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AncientRules();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
        	this.upgradeName();
        	//this.upgradeMagicNumber(this.upgradeSummons);
        	this.isInnate = true;
        	this.exhaust = false;
        	this.rawDescription = UPGRADE_DESCRIPTION;
        	this.initializeDescription();
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
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
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