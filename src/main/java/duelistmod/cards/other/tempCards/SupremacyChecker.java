package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.SkillsagaSupremacyAction;
import duelistmod.patches.AbstractCardEnum;

public class SupremacyChecker extends HiddenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("SupremacyChecker");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ShiranuiSkillsagaSupremacy.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private AbstractMonster enemy;
    // /STAT DECLARATION/

    public SupremacyChecker(int check, AbstractMonster targ) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.purgeOnUse = true;
    	this.entomb = this.baseEntomb = check;
    	this.enemy = targ;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.currentBlock < this.entomb) { this.addToBot(new SkillsagaSupremacyAction(this.baseEntomb, this.enemy));}
    }
    @Override public AbstractCard makeCopy() { return new SupremacyChecker(this.baseEntomb, this.enemy); }

    
    

	@Override public void upgrade() {}
	


}
