package duelistmod.cards.other.tokens;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class DragonToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DragonToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.BABY_DRAGON);
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

    public DragonToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.tags.add(Tags.DRAGON);
    	this.baseMagicNumber = this.magicNumber = 1;
    	this.summons = this.baseSummons = 1;
    	this.purgeOnUse = true;
    }
    public DragonToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.DRAGON);
    	this.baseMagicNumber = this.magicNumber = 1;
    	this.summons = this.baseSummons = 1;
    	this.purgeOnUse = true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (this.magicNumber > 0)
    	{
	    	ArrayList<AbstractCard> handDrags = new ArrayList<AbstractCard>();
	    	for (AbstractCard c : player().hand.group)
	    	{
	    		if (!c.uuid.equals(this.uuid) && c instanceof DuelistCard && c.hasTag(Tags.MONSTER))
	    		{
	    			DuelistCard dC = (DuelistCard)c;
	    			if (dC.isTributeCard()) { handDrags.add(c); }
	    		}
	    	}
	    	
	    	if (handDrags.size() > 0)
	    	{
	    		DuelistCard card = (DuelistCard) handDrags.get(AbstractDungeon.cardRandomRng.random(handDrags.size() - 1));
	    		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(card, -this.magicNumber, false));
	    		Util.log("Dragon Token is modifying " + card.name);
	    	}
    	}
    }
    @Override public AbstractCard makeCopy() { return new DragonToken(); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		dragonSynTrib(tributingCard);
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }

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
	
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
