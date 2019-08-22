package duelistmod.cards;

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

public class LostBlueBreaker extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("LostBlueBreaker");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("LostBlueBreaker.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    //private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public LostBlueBreaker() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tributes = this.baseTributes = 10;
        this.baseMagicNumber = this.magicNumber = 10;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
		this.tags.add(Tags.ARCANE);
        this.tags.add(Tags.EXEMPT);
        this.misc = 0;
		this.originalName = this.name;
		this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	for (int i = 0; i < this.magicNumber; i++)
    	{
    		m = getRandomMonster();
    		if (m != null)
    		{
		    	DuelistCard extraDragA = (DuelistCard) returnTrulyRandomFromSet(Tags.AQUA);    	
		    	while (extraDragA.hasTag(Tags.EXEMPT) || extraDragA.cardID.equals(this.cardID)) { extraDragA = (DuelistCard) returnTrulyRandomFromSet(Tags.AQUA); }
		    	String cardNameA = extraDragA.originalName;    	
		    	if (DuelistMod.debug) { System.out.println("theDuelist:LostBlueBreaker --- > Generated: " + cardNameA); }
		    	fullResummon(extraDragA, this.upgraded, m, false);
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new LostBlueBreaker();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            if (this.tributes > 1) { this.upgradeTributes(-1); }
            else { this.upgraded = true; }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.tributes > 1) { return true; }
    	else { return false; }
    }
    
    // If player doesn't have enough summons, can't play card
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }
    	
    	// Pumpking & Princess
  		else if (this.misc == 52) { return true; }
    	
    	// Mausoleum check
    	else if (p.hasPower(EmperorPower.POWER_ID))
		{
			EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
			if (!empInstance.flag)
			{
				return true;
			}
			
			else
			{
				if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } }
			}
		}
    	
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = this.tribString;
    	return false;
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		aquaSynTrib(tributingCard);
	}
	
	@Override
	public void onResummon(int summons) 
	{
		
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