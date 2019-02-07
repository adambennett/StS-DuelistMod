package defaultmod.cards;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.actions.common.ModifyMagicNumberAction;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.PotGenerosityPower;
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

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int SUMMONS = 1;
    private static final int COUNTERS = 6;
    private static final int OVERFLOW_AMT = 3;
    private static final int U_OVERFLOW = 2;
    private static int MIN_TURNS_ROLL = 1;
    private static int MAX_TURNS_ROLL = 5;
    
    // /STAT DECLARATION/

    public ToonDarkMagicianGirl() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = OVERFLOW_AMT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Pot of Generosity setup
    	int potSummons = 0;
    	if (p.hasPower(PotGenerosityPower.POWER_ID)) 
    	{
	    	int startSummons = p.getPower(SummonPower.POWER_ID).amount;
	    	SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
	    	int maxSummons = summonsInstance.MAX_SUMMONS;
	    	if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
	    	else { potSummons = SUMMONS; }
    	}
    	
    	// Summon
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, SUMMONS), SUMMONS));

    	// Check for Pot of Generosity
    	if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(potSummons)); }

        // Apply Spell Counters to target
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new SpellCounterPower(p, p, COUNTERS)));
    }
    
    @Override
    public void triggerOnEndOfPlayerTurn() 
    {
    	// If overflows remaining
        if (this.magicNumber > 0) 
        {
        	// Get player reference
        	AbstractPlayer p = AbstractDungeon.player;
        	
        	// Remove 1 overflow
            AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this, -1));
            
            // Get random number of turns for buff to apply for (1-5)
    		int randomTurnNum = ThreadLocalRandom.current().nextInt(MIN_TURNS_ROLL, MAX_TURNS_ROLL + 1);
    		
    		// Setup powers array for random buff selection
    		AbstractPower str = new StrengthPower(p, randomTurnNum);
    		AbstractPower dex = new DexterityPower(p, randomTurnNum);
    		AbstractPower summons = new SummonPower(p, randomTurnNum);
    		AbstractPower art = new ArtifactPower(p, randomTurnNum);
    		AbstractPower plate = new PlatedArmorPower(p, randomTurnNum);
    		AbstractPower intan = new IntangiblePower(p, 1);
    		AbstractPower regen = new RegenPower(p, randomTurnNum);
    		AbstractPower energy = new EnergizedPower(p, randomTurnNum);
    		AbstractPower thorns = new ThornsPower(p, randomTurnNum);
    		AbstractPower barricade = new BarricadePower(p);
    		AbstractPower blur = new BlurPower(p, randomTurnNum);
    		AbstractPower burst = new BurstPower(p, randomTurnNum);
    		AbstractPower creative = new CreativeAIPower(p, randomTurnNum);
    		AbstractPower darkEmb = new DarkEmbracePower(p, randomTurnNum);
    		AbstractPower doubleTap = new DoubleTapPower(p, randomTurnNum);
    		AbstractPower equal = new EquilibriumPower(p, randomTurnNum);
    		AbstractPower noPain = new FeelNoPainPower(p, randomTurnNum);
    		AbstractPower fire = new FireBreathingPower(p, randomTurnNum);
    		AbstractPower jugger = new JuggernautPower(p, randomTurnNum);
    		AbstractPower metal = new MetallicizePower(p, randomTurnNum);
    		AbstractPower penNib = new PenNibPower(p, 1);
    		AbstractPower sadistic = new SadisticPower(p, randomTurnNum);
    		AbstractPower storm = new StormPower(p, randomTurnNum);
    		AbstractPower[] buffs = new AbstractPower[] {str, dex, summons, art, plate, intan, regen, energy, thorns, barricade, blur, burst, creative, darkEmb, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm };
 
    		// Get randomized buff
    		int randomBuffNum = ThreadLocalRandom.current().nextInt(0, buffs.length);
    		AbstractPower randomBuff = buffs[randomBuffNum];

    		// Apply random buff
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, randomBuff));
        }
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
            this.upgradeMagicNumber(U_OVERFLOW);
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