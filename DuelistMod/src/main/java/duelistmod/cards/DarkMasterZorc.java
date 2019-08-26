package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class DarkMasterZorc extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DarkMasterZorc");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkMasterZorc.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public DarkMasterZorc() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tributes = this.baseTributes = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.tags.add(Tags.EXODIA_DECK);
		this.exodiaDeckCopies = 1;
		this.setupStartingCopies();
        this.originalName = this.name;
        this.baseDamage = this.damage = 20;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	attack(m, this.baseAFX, this.damage);
    	tribute(p, this.tributes, false, this);    	
    	for (int i = 0; i < 3; i++)
    	{
    		AbstractOrb dark = new Dark();
        	channel(dark);    	
    	}    	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkMasterZorc();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.upgradeDamage(5);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		fiendSynTrib(tributingCard);
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
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		
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