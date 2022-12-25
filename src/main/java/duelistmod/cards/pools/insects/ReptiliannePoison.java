package duelistmod.cards.pools.insects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import basemod.ReflectionHacks;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.statuses.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class ReptiliannePoison extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ReptiliannePoison");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ReptiliannePoison.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ReptiliannePoison() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 6;
        this.secondMagic = this.baseSecondMagic = 3;
        this.misc = 0;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.ARCANE);
        this.cardsToPreview = new ColdBlooded();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	applyPower(new PoisonPower(m, p, this.magicNumber), m);
    	this.addToBot(new MakeTempCardInDrawPileAction(new ColdBlooded(), this.secondMagic, true, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(2);
        	if (this.timesUpgraded == 1) { this.upgradeSecondMagic(-1); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 12) { return true; }
    	else { return false; }
    }
    
    

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
			
	}

	@Override
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new ReptiliannePoison(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
