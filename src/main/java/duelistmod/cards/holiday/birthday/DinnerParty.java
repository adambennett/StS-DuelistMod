package duelistmod.cards.holiday.birthday;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.TooltipInfo;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class DinnerParty extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DinnerParty");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DinnerParty.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public DinnerParty() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.magicNumber = this.baseMagicNumber = 2;
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        //retVal.addAll(super.getCustomTooltips());
        if (Util.whichBirthday() == 2) { retVal.add(new TooltipInfo("Happy Birthday!", "Nyoxide wants to wish you a very happy birthday! I hope your day is going well, and may your runs be blessed with only the best RNG!!")); }
        retVal.add(new TooltipInfo("Birthday", "Cards with this keyword only appear in the game on Nyoxide's birthday, March 4th (DuelistMod first Steam Release date), or on your birthday! Set your birthday in the config menu."));
        return retVal;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> powerWalls = new ArrayList<AbstractCard>();
    	ArrayList<AbstractCard> holiday = Util.allHolidayCardsNoDateCheck();
    	while (powerWalls.size() < this.magicNumber)
    	{
    		powerWalls.add(holiday.get(AbstractDungeon.cardRandomRng.random(holiday.size() - 1)));
    	}
    	this.addToBot(new CardSelectScreenIntoHandAction(powerWalls, this.magicNumber, true, false));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DinnerParty();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }
    




	










   
}
