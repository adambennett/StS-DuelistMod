package duelistmod.cards.holiday.birthday;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.TooltipInfo;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.pools.insects.*;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class CocoonParty extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CocoonParty");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CocoonParty.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public CocoonParty() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.makeFleeting();
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        //retVal.addAll(super.getCustomTooltips());
        if (Util.whichBirthday() == 2) { retVal.add(new TooltipInfo("Happy Birthday!", "Nyoxide wants to wish you a very happy birthday! I hope your day is going well, and may your runs be blessed with only the best RNG!!")); }
        retVal.add(new TooltipInfo("Birthday", "Cards with this keyword only appear in the game on Nyoxide's birthday, March 4th (DuelistMod first Steam Release date), or on your birthday! Set your birthday in the config menu."));
        return retVal;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	AbstractCard pm = new PetitMoth();
    	AbstractCard co = new CocoonEvolution();
    	if (upgraded)
    	{
    		pm.upgrade();
    		co.upgrade();
    	}
    	p.masterDeck.group.add(pm);
    	p.masterDeck.group.add(co);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CocoonParty();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
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
