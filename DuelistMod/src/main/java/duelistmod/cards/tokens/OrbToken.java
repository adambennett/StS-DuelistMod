package duelistmod.cards.tokens;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.cards.typecards.TokenCard;
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
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public OrbToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.isEthereal = true;
    }
    public OrbToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);  
    	this.purgeOnUse = true;
    	this.isEthereal = true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, 1, this);
    	int roll = AbstractDungeon.cardRandomRng.random(1,2);
    	if (roll == 1)
    	{
    		ArrayList<DuelistCard> orbs = new ArrayList<DuelistCard>();
        	ArrayList<String> orbNames = new ArrayList<String>();
        	ArrayList<AbstractCard> orbsToChooseFrom = DuelistCardLibrary.orbCardsForGeneration();
    		for (int i = 0; i < 3; i++)
    		{
    			AbstractCard random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1));
    			while (orbNames.contains(random.name)) { random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)); }
    			orbs.add((DuelistCard) random.makeCopy());
    			orbNames.add(random.name);
    		}
        	//AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(orbs, 1, false, false, false, true));
    		if (DuelistMod.addTokens) { orbs.clear(); for (AbstractCard c : DuelistCardLibrary.orbCardsForGeneration()) { orbs.add((DuelistCard) c.makeCopy()); }AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(orbs, 1, false, false, false, true)); }
    		else { AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(orbs, 1, false, false, false, true)); }
    	}
    }
    @Override public AbstractCard makeCopy() { return new OrbToken(); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() 
	{
		if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
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