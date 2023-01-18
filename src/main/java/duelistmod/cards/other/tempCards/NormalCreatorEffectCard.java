package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.TheCreatorAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;

public class NormalCreatorEffectCard extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("NormalCreatorEffectCard");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.THE_CREATOR);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private ArrayList<AbstractCard> poolCards;
    // /STAT DECLARATION/

    public NormalCreatorEffectCard(ArrayList<AbstractCard> pool) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.poolCards = pool;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	this.addToBot(new TheCreatorAction(p, p, this.poolCards, 1, true, false, false)); 
    }
    @Override public AbstractCard makeCopy() { return new NormalCreatorEffectCard(this.poolCards); }

    
    
	
	@Override public void upgrade() {}
	


}
