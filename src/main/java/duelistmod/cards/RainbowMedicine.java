package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.RedMedicineAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;


public class RainbowMedicine extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("RainbowMedicine");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RainbowMedicine.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public RainbowMedicine() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.NEVER_GENERATE);
		this.tags.add(Tags.ARCANE);
		this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		if (!this.upgraded)
		{
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(1, 6);
			AbstractPower a = applyRandomBuffPlayer(p, randomTurnNum, false);
			Util.log("theDuelist:RainbowMedicine --- > Generated buff: " + a.name ); 
			randomTurnNum = AbstractDungeon.cardRandomRng.random(1, 6);
			AbstractPower b = applyRandomBuffPlayer(p, randomTurnNum, false);
			Util.log("theDuelist:RainbowMedicine --- > Generated buff: " + b.name ); 
		}
		else
		{
			int lowRoll = AbstractDungeon.cardRandomRng.random(2, 4);
			int highRoll = AbstractDungeon.cardRandomRng.random(4, 8);
			AbstractDungeon.actionManager.addToTop(new RedMedicineAction(2, m, this.magicNumber, lowRoll, highRoll));
		}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new RainbowMedicine();
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
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 10) { return true; }
    	else { return false; }
    }

	


	








}
