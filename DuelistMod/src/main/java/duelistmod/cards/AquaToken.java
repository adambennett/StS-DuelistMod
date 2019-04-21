package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;

public class AquaToken extends DuelistCard 
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
    }
    @Override public AbstractCard makeCopy() { return new AquaToken(); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		if (tributingCard.hasTag(Tags.AQUA))
		{
			DuelistCard randomAqua = (DuelistCard) returnTrulyRandomFromSets(Tags.AQUA, Tags.MONSTER).makeCopy();
			AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomAqua, false, true, false, false, false, randomAqua.baseSummons > 0, false, false, 1, 4, 0, 0, 0, 2));
			if (DuelistMod.debug) { DuelistMod.logger.info("Calling RandomizedAction from: " + this.originalName); }
		}
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