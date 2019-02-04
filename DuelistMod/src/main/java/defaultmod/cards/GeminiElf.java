package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.PotGenerosityPower;
import defaultmod.powers.SummonPower;

public class GeminiElf extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In order to understand how image paths work, go to defaultmod/DefaultMod.java, Line ~140 (Image path section).
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("GeminiElf");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // Yes, you totally can use "defaultModResources/images/cards/Attack.png" instead and that would work.
    // It might be easier to use that while testing.
    // Using makePath is good practice once you get the hand of things, as it prevents you from
    // having to change *every single card/file/path* if the image path changes due to updates or your personal preference.

    public static final String IMG = DefaultMod.makePath(DefaultMod.GEMINI_ELF);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int SUMMONS = 2;
    private static final int UPGRADE_SUMMONS = 1;

    // /STAT DECLARATION/

    public GeminiElf() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = SUMMONS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    	int startSummons = p.getPower(SummonPower.POWER_ID).amount;
    	System.out.println("GEMINI ELF HAS JUST LOOKED AT YOUR SUMMONS! YOU HAD: " + startSummons);
    	
    	// Summon
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, this.magicNumber), this.magicNumber));

    	int endSummons = p.getPower(SummonPower.POWER_ID).amount;
    	int potSummons = endSummons - startSummons;
    	
    	System.out.println("GEMINI ELF HAS JUST LOOKED AT YOUR SUMMONS (AGAIN)! YOU HAD: " + endSummons);
    	System.out.println("GEMINI ELF HAS JUST LOOKED AT YOUR SUMMONS (FOR POT)! YOU HAD: " + potSummons);
    	
    	
    	
    	// Summon
    	//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, this.magicNumber, 5, false), this.magicNumber));
    	
    	// Check for Pot of Generosity
    	if (p.hasPower(PotGenerosityPower.POWER_ID)) 
    	{
    		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(SUMMONS));
    	}
    }
 
    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GeminiElf();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_SUMMONS);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}