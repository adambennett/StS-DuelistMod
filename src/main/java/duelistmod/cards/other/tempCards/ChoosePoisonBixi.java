package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;

public class ChoosePoisonBixi extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ChoosePoisonBixi");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Basic_Insect.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private AbstractMonster targ;
    // /STAT DECLARATION/

    public ChoosePoisonBixi(int magic, AbstractMonster target) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.baseMagicNumber = this.magicNumber = magic;
    	this.targ = target;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	applyPower(new PoisonPower(m, p, this.magicNumber), m);
    }
    @Override public AbstractCard makeCopy() { return new ChoosePoisonBixi(this.magicNumber, this.targ); }

    
    
	
	@Override public void upgrade() {}
	


}
