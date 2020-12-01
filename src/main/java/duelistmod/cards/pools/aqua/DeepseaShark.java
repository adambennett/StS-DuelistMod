package duelistmod.cards.pools.aqua;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.TsunamiAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class DeepseaShark extends DuelistCard 
{
    // TEXT DECLARATION
    private static final CardStrings cardStrings = getCardStrings();
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public DeepseaShark() {
        super(getCARDID(), NAME, getIMG(), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
        this.misc = 0;
        this.specialCanUseLogic = true;
        this.useTributeCanUse = true;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 6;
        this.magicNumber = this.baseMagicNumber = 4;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	this.addToBot(new TsunamiAction(this.magicNumber));
    	if (p.hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower pow = (SummonPower)p.getPower(SummonPower.POWER_ID);
    		if (pow.actualCardSummonList.size() > 0)
    		{
    			DuelistCard bottomSummon = pow.actualCardSummonList.get(0);
    			if (allowResummonsWithExtraChecks(bottomSummon))
    			{
    				resummon(bottomSummon, m, false, this.upgraded);
    			}
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DeepseaShark();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
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
		return getCARDID();
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	// AUTOSETUP - ID/IMG - Id, Img name, and class name all must match to use this
    public static String getCARDID()
    {
    	return DuelistMod.makeID(getCurClassName());
    }
    
	public static CardStrings getCardStrings()
    {
    	return CardCrawlGame.languagePack.getCardStrings(getCARDID());
    }
    
    public static String getIMG()
    {
    	return DuelistMod.makeCardPath(getCurClassName() + ".png");
    }
    
    public static String getCurClassName()
    {
    	return (new CurClassNameGetter()).getClassName();
    }

    public static class CurClassNameGetter extends SecurityManager{
    	public String getClassName(){
    		return getClassContext()[1].getSimpleName();
    	}
    }
    // END AUTOSETUP
}
