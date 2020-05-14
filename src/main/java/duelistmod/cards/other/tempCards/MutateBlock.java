package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;

public class MutateBlock extends MutateCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MutateBlock");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GENERIC_TOKEN);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    // /STAT DECLARATION/

    public MutateBlock(int magic, CardRarity rarity) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, rarity, TARGET); 
    	this.baseMagicNumber = this.magicNumber = magic;    	
    }
    
    @Override
    public boolean canSpawnInOptions(ArrayList<AbstractCard> mutatePool) 
    { 
    	boolean can = false;
    	for (AbstractCard c : mutatePool)
    	{
    		if (c.block > 0 || c.baseBlock > 0)
    		{
    			can = true;
    			break;
    		}
    	}
    	return can; 
    }
    
    @Override
    public void runMutation(AbstractCard c) 
    {
    	this.addToBot(new ModifyBlockAction(c.uuid, this.magicNumber));
    }
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    }
    
    @Override public AbstractCard makeCopy() { return new MutateBlock(this.baseMagicNumber, this.rarity); }

    
    
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