package duelistmod.cards.other.tokens;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class OrbToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("OrbToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.POWER_KAISHIN);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/a

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public OrbToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
    	this.magicNumber = this.baseMagicNumber = 1;
    	this.baseSummons = this.summons = 1;
    }
    public OrbToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);  
    	this.purgeOnUse = true;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
    	this.magicNumber = this.baseMagicNumber = 1;
    	this.baseSummons = this.summons = 1;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (roulette() && this.magicNumber > 0) { 
    		ArrayList<AbstractCard> orbs = new ArrayList<>();
        	ArrayList<AbstractCard> orbsToChooseFrom = DuelistCardLibrary.orbCardsForGeneration();
        	int extra = this.magicNumber + 2;
        	if (extra >= orbsToChooseFrom.size() || DuelistMod.addTokens) 
        	{
        		extra = orbsToChooseFrom.size();  
        		for (AbstractCard c : DuelistCardLibrary.orbCardsForGeneration()) 
        		{ orbs.add((DuelistCard) c.makeCopy()); }
        		AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(orbs, this.magicNumber, false, false, false, true));
        	}
        	else
        	{
	        	for (AbstractCard c : orbsToChooseFrom) { if (c instanceof DuelistCard) { orbs.add((DuelistCard)c); }}
	        	while (orbs.size() > extra) { orbs.remove(AbstractDungeon.cardRandomRng.random(orbs.size() - 1)); }
	        	AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(orbs, this.magicNumber, false, false, false, true));
        	}
    	}
    }
    @Override public AbstractCard makeCopy() { return new OrbToken(); }

    
    
	

	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	


}
