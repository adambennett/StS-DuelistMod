package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class ChooseSpidersCard extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ChooseSpidersCard");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GroundSpider.png");
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
    // /STAT DECLARATION/

    public ChooseSpidersCard(int magic) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.baseMagicNumber = this.magicNumber = magic;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> bugs = findAllOfTypeForResummon(Tags.SPIDER, this.magicNumber);
    	for (AbstractCard c : bugs) { DuelistCard.resummon(c, m); }
    }
    @Override public AbstractCard makeCopy() { return new ChooseSpidersCard(this.magicNumber); }

    
    
	
	@Override public void upgrade() {}
	


}
