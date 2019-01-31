package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.SpellCounterPower;
import defaultmod.powers.SummonPower;
import defaultmod.powers.ToonWorldPower;

public class ToonDarkMagicianGirl extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In order to understand how image paths work, go to defaultmod/DefaultMod.java, Line ~140 (Image path section).
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("ToonDarkMagicianGirl");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // Yes, you totally can use "defaultModResources/images/cards/Attack.png" instead and that would work.
    // It might be easier to use that while testing.
    // Using makePath is good practice once you get the hand of things, as it prevents you from
    // having to change *every single card/file/path* if the image path changes due to updates or your personal preference.

    public static final String IMG = DefaultMod.makePath(DefaultMod.TOON_DARK_MAGICIAN_GIRL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int SUMMONS = 1;
    private static final int COUNTERS = 6;
    private static final int U_COUNTERS = 3;
    
    // /STAT DECLARATION/

    public ToonDarkMagicianGirl() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = COUNTERS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Summon
    	if (!p.hasPower(SummonPower.POWER_ID)) 
    	{
    		AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new SummonPower(p, SUMMONS), SUMMONS));
    	}
    	else
    	{
    		this.misc = (p.getPower(SummonPower.POWER_ID).amount);
    		if (!(this.misc > 5 - SUMMONS)) 
    		{
    			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new SummonPower(p, SUMMONS), SUMMONS));
    		}
    		else
    		{
    			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new SummonPower(p, 5 - this.misc), 5 - this.misc));
    		}
    	}

        // Apply Spell Counters to target
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SpellCounterPower(p, p, this.magicNumber)));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ToonDarkMagicianGirl();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(U_COUNTERS);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    // If player doesn't have Toon World, can't be played
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	if (p.hasPower(ToonWorldPower.POWER_ID))
    	{
    		return true;
    	}
    	return false;
    }

}