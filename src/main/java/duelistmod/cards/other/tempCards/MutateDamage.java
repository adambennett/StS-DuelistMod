package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;

public class MutateDamage extends MutateCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MutateDamage");
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

    public MutateDamage(int magic, CardRarity rarity) 
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
    		if (c.damage > 0 || c.baseDamage > 0)
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
    	this.addToBot(new ModifyDamageAction(c.uuid, this.magicNumber));
    }
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    }
    
    @Override public AbstractCard makeCopy() { return new MutateDamage(this.baseMagicNumber, this.rarity); }

    
    

	@Override public void upgrade() 
	{
		if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	


}
