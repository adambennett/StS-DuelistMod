package duelistmod.cards.pools.machine;

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

public class OniTankT34 extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("OniTankT34");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("OniTankT34.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public OniTankT34() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 11;
        this.tributes = this.baseTributes = 2;
        this.isSummon = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ZOMBIE);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.PHARAOH_SERVANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m);
    }

    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	 if (!upgraded) 
         {
         	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
 	    	else { this.upgradeName(NAME + "+"); }
         	if (DuelistMod.hasUpgradeBuffRelic)
         	{
         		this.upgradeSummons(1);
         		this.upgradeDamage(3);
         	}
         	else
         	{
         		this.upgradeDamage(3);
         	}
             this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
             this.initializeDescription();
         }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		zombieSynTrib(tributingCard);
		machineSynTrib(tributingCard);
	}
	


    @Override
    public void onResummonThisCard()
    {
    	fetch(player().exhaustPile, false);
    }

	@Override
	public void onResummon(int summons) 
	{
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new OniTankT34(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
