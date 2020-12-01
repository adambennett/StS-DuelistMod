package duelistmod.cards.incomplete;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.Black;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class DarkTinker extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DarkTinker");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkTinker.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public DarkTinker() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 14;
        this.tributes = this.baseTributes = 2;
        this.baseMagicNumber = this.magicNumber = 1;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
    }
    
	@Override
	public void update()
	{
		super.update();
		this.showEvokeOrbCount = this.magicNumber;
	}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m);
    	for (int i = 0; i < this.magicNumber; i++)
    	{
    		AbstractOrb orb = new Black();
    		channel(orb);
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
        	this.upgradeMagicNumber(1);
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
    public AbstractCard makeCopy() { return new DarkTinker(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
