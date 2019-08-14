package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.incomplete.RockSunrisePower;
import duelistmod.variables.Tags;

public class RockSunrise extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("RockSunrise");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RockSunrise.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public RockSunrise() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {    
    	if (!p.hasPower(RockSunrisePower.POWER_ID))
    	{
	    	ArrayList<DuelistCard> types = generateTypeCards(0, true);
	    	AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(types, 1, false, false, false, true));
    	}
    	
    	else
    	{
    		AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, p.getPower(RockSunrisePower.POWER_ID), p.getPower(RockSunrisePower.POWER_ID).amount));
    		ArrayList<DuelistCard> types = generateTypeCards(0, true);
	    	AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(types, 1, false, false, false, true));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new RockSunrise();
    }

    @Override
    public void upgrade() {
        if (!upgraded) 
        {
        	// Name
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.isInnate = true;
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
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
   
}