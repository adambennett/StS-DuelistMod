package duelistmod.templates;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class AttackTrapCardTemplate extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("AttackTrapCardTemplate");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AttackTrapCardTemplate.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 100;
    // /STAT DECLARATION/

    public AttackTrapCardTemplate() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        
        // Dmg / Blk / Magic
        this.baseDamage = this.damage = 6000;
        this.baseBlock = this.block = 6000;
        this.baseMagicNumber = this.magicNumber = 6000;
        
        // Summons
        this.summons = this.baseSummons = 1;
        this.isSummon = true;
        
        // Tribute
        this.tributes = this.baseTributes = 1;
        this.misc = 0;
       
        // Card Type
        this.tags.add(Tags.TRAP);
        
        // Attribute
        this.tags.add(Tags.AQUA);

        // Starting Deck
        this.tags.add(Tags.WARRIOR_DECK);
		this.superheavyDeckCopies = 1;
		this.setupStartingCopies();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	tribute();
    	//applyPowerToSelf(new PowerTemplate(p, p, 1));
    	attack(m);
    }

    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	return true;
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
    public AbstractCard makeCopy() { return new AttackTrapCardTemplate(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
