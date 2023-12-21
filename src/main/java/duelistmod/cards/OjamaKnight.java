package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class OjamaKnight extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("OjamaKnight");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.OJAMA_KNIGHT);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    private static int MIN_BUFF_TURNS_ROLL = 1;
    private static int MAX_BUFF_TURNS_ROLL = 6;
    // /STAT DECLARATION/

    public OjamaKnight() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.OJAMA);
        this.tags.add(Tags.BEAST);
		this.tags.add(Tags.ARCANE);
        this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 2;
		this.showInvertValue = true;
		this.showEvokeValue = true;
		this.showInvertOrbs = 1;
		this.showEvokeOrbCount = 1;		
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
		tribute(p, this.tributes, false, this);
		int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_BUFF_TURNS_ROLL, MAX_BUFF_TURNS_ROLL);
		applyRandomBuffPlayer(p, randomTurnNum, false);
		invert(1);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new OjamaKnight();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	if (this.canUpgrade())
    	{
    		if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
	        if (this.tributes > 0) { this.upgradeTributes(-1); }
	        else if (this.cost > 0) { this.upgradeBaseCost(this.cost - 1); }
	        this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
	        this.initializeDescription();
    	}
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.tributes > 0 || this.cost > 0) { return true; }
    	else { return false; }
    }
    

	















}
