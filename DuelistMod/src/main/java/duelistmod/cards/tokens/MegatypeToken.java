package duelistmod.cards.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.cards.typecards.TokenCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class MegatypeToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MegatypeToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GENERIC_TOKEN);
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

    public MegatypeToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.magicNumber = this.baseMagicNumber = 1;
    	this.summons = this.baseSummons = 1;
    	this.makeMegatyped();
    	
    }
    public MegatypeToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.magicNumber = this.baseMagicNumber = 1;
    	this.summons = this.baseSummons = 1;
    	this.makeMegatyped();
    	
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	for (int i = 0; i < this.magicNumber; i++)
    	{
	    	CardTags randomTypeSelection = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
	    	DuelistCard randTypeMon = (DuelistCard) DuelistCard.returnTrulyRandomFromSets(randomTypeSelection, Tags.MONSTER);
	    	AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randTypeMon, true));  
    	}
    }
    @Override public AbstractCard makeCopy() { return new MegatypeToken(); }

    
    
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