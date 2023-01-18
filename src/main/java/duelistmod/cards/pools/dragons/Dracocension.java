package duelistmod.cards.pools.dragons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class Dracocension extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Dracocension");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Dracocension.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Dracocension() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber 	= 2;
        this.baseSecondMagic = this.secondMagic 	= 1;
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower pow = (SummonPower)p.getPower(SummonPower.POWER_ID);
    		boolean allDrags = pow.isEveryMonsterCheck(Tags.DRAGON, true);
    		boolean allDinos = pow.isEveryMonsterCheck(Tags.DINOSAUR, true);
    		if (allDrags) { applyPowerToSelf(new FocusPower(p, this.magicNumber)); }
    		if (allDinos) { applyPowerToSelf(new IntangiblePlayerPower(p, this.secondMagic)); }
    	}
    }
    
    @Override
    public void triggerOnGlowCheck() 
    {
    	super.triggerOnGlowCheck();
    	if (player().hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
    		boolean allDrags = pow.isEveryMonsterCheck(Tags.DRAGON, true);
    		boolean allDinos = pow.isEveryMonsterCheck(Tags.DINOSAUR, true);
    		if (allDrags || allDinos)
    		{
    			this.glowColor = Color.GOLD;
    		}
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Dracocension();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(1);
            this.upgradeSecondMagic(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }
    




	










   
}
