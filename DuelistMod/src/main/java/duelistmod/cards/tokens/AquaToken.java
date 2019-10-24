package duelistmod.cards.tokens;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.actions.common.ModifySummonAction;
import duelistmod.interfaces.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class AquaToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("AquaToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.ISLAND_TURTLE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public AquaToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.tags.add(Tags.AQUA);
    	this.purgeOnUse = true;
    	
    }
    public AquaToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.tags.add(Tags.AQUA);
    	this.purgeOnUse = true;
    	
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, 1, this);
    	ArrayList<AbstractCard> handDrags = new ArrayList<AbstractCard>();
    	for (AbstractCard c : player().hand.group)
    	{
    		if (!c.uuid.equals(this.uuid) && c instanceof DuelistCard)
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.summons > 0) { handDrags.add(c); }
    		}
    	}
    	
    	if (handDrags.size() > 0)
    	{
    		DuelistCard card = (DuelistCard) handDrags.get(AbstractDungeon.cardRandomRng.random(handDrags.size() - 1));
    		AbstractDungeon.actionManager.addToTop(new ModifySummonAction(card, 1, false));
    	}
    }
    @Override public AbstractCard makeCopy() { return new AquaToken(); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		aquaSynTrib(tributingCard);
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
            this.upgradeBlock(2);
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