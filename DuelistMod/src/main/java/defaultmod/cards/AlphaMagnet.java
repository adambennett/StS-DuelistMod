package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.AlphaMagPower;
import defaultmod.powers.PotGenerosityPower;
import defaultmod.powers.SummonPower;

public class AlphaMagnet extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In order to understand how image paths work, go to defaultmod/DefaultMod.java, Line ~140 (Image path section).
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("AlphaMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // Yes, you totally can use "defaultModResources/images/cards/Attack.png" instead and that would work.
    // It might be easier to use that while testing.
    // Using makePath is good practice once you get the hand of things, as it prevents you from
    // having to change *every single card/file/path* if the image path changes due to updates or your personal preference.

    public static final String IMG = DefaultMod.makePath(DefaultMod.ALPHA_MAGNET);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int SUMMONS = 1;

    // /STAT DECLARATION/

    public AlphaMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int startSummons = p.getPower(SummonPower.POWER_ID).amount;
    	System.out.println("ALPHA MAGNET WARRIOR HAS JUST LOOKED AT YOUR SUMMONS! YOU HAD: " + startSummons);
    	
    	// Summon
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, SUMMONS), SUMMONS));

    	int endSummons = p.getPower(SummonPower.POWER_ID).amount;
    	int potSummons = endSummons - startSummons;
    	
    	System.out.println("ALPHA MAGNET WARRIOR HAS JUST LOOKED AT YOUR SUMMONS (AGAIN)! YOU HAD: " + endSummons);
    	System.out.println("ALPHA MAGNET WARRIOR HAS JUST LOOKED AT YOUR SUMMONS (FOR POT)! YOU HAD: " + potSummons);
    	
    	// Check for Pot of Generosity
    	if (p.hasPower(PotGenerosityPower.POWER_ID)) 
    	{
    		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(SUMMONS));
    	}
    	
    	// Gain Alpha Magnet
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new AlphaMagPower(p, p)));
    	
    	// Deal damage to target enemy
    	AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AlphaMagnet();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.exhaust = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}