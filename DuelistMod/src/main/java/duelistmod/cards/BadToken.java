package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class BadToken extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BadToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GREEDPOT_AVATAR);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public BadToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.damage = this.baseDamage = 1;
    	this.baseMagicNumber = this.magicNumber = 3;
    	this.tags.add(Tags.NEVER_GENERATE);
    	//makeMegatyped();
    }
    public BadToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.damage = this.baseDamage = 1; 
    	this.baseMagicNumber = this.magicNumber = 3;
    	this.tags.add(Tags.NEVER_GENERATE);
    	//makeMegatyped();
    }
    
    
    @Override public void use (AbstractPlayer p, AbstractMonster m) 
    {
    	
    	if (!p.hasAnyPotions())
		{
			for (int i = 0; i < p.potionSlots; i++)
			{
				int loopCount = 0;
				AbstractPotion pot = AbstractDungeon.returnRandomPotion();
				while (!(pot instanceof DuelistPotion || pot instanceof OrbPotion)) 
				{
					if (loopCount > 3) { pot = DuelistMod.allDuelistPotions.get(AbstractDungeon.cardRandomRng.random(DuelistMod.allDuelistPotions.size() - 1)); loopCount = 0; }
					else { pot = AbstractDungeon.returnRandomPotion(); }
					loopCount++;
				}
				Util.log("BT generated " + pot.name + " in the loop that indicated you DID HAVE all empty potion slots before playing the card");
				p.obtainPotion(pot);
			}
		}
		
		else
		{
			int pots = 0;
			for (AbstractPotion pot : p.potions) { if (!(pot instanceof PotionSlot)) { pots++; }}
			if (p.potionSlots - pots > 0)
			{
    			for (int i = 0; i < p.potionSlots - pots; i++)
    			{
    				int loopCount = 0;
    				AbstractPotion pot = AbstractDungeon.returnRandomPotion();
    				while (!(pot instanceof DuelistPotion || pot instanceof OrbPotion)) 
    				{
    					if (loopCount > 3) { pot = DuelistMod.allDuelistPotions.get(AbstractDungeon.cardRandomRng.random(DuelistMod.allDuelistPotions.size() - 1)); loopCount = 0; }
    					else { pot = AbstractDungeon.returnRandomPotion(); }
    					loopCount++;
    				}
    				Util.log("BT generated " + pot.name + " in the loop that indicated you did not have all empty slots before playing the card");
    				p.obtainPotion(pot);
    			}
			}
			else
			{
				Util.log("No potion slots were open for BT");
			}
		}
    	
    	if (DuelistMod.debug)
    	{
    		Debug.printTributeInfo();
        	Debug.printRarityInfo();
        	Debug.printTypedRarityInfo();
        	BoosterPackHelper.debugCheckLists();
    	}
    }
   

    @Override public AbstractCard makeCopy() { return new BadToken(); }
	@Override public void onTribute(DuelistCard tributingCard) {}
	@Override public void onResummon(int summons) { }
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() {}
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}