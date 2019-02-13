package defaultmod.cards;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.orbs.CrystalOrb;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.actions.common.ModifyMagicNumberAction;
import defaultmod.patches.*;
import defaultmod.powers.ToonWorldPower;

public class ToonDarkMagicianGirl extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("ToonDarkMagicianGirl");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.TOON_DARK_MAGICIAN_GIRL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 1;
    private static final int SUMMONS = 2;
    private static final int OVERFLOW_AMT = 3;
    private static final int U_OVERFLOW = 2;
    private static int MIN_TURNS_ROLL = 4;
    private static int MAX_TURNS_ROLL = 8;
    // /STAT DECLARATION/

    public ToonDarkMagicianGirl() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = OVERFLOW_AMT;
        this.toon = true;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.TOON);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, SUMMONS);
    	//applyPower(new SpellCounterPower(p, p, COUNTERS), m);
    	AbstractOrb crystal = new CrystalOrb();
    	channel(crystal);
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

        	// Get random number of turns for buff to apply for
        	int randomTurnNum = ThreadLocalRandom.current().nextInt(MIN_TURNS_ROLL, MAX_TURNS_ROLL + 1);

        	// Get random buff with random tumber of turns
        	AbstractPower randomBuff = getRandomBuff(p, randomTurnNum);

        	// Apply random buff
        	//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, randomBuff));
        	applyPower(randomBuff, p);
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
    	// Pumpking & Princess
  		if (this.misc == 52) { return true; }
  		
  		// Toon World
    	if (p.hasPower(ToonWorldPower.POWER_ID)) { return true; }
    	
    	// Otherwise
    	this.cantUseMessage = "You need Toon World";
    	return false;
    }

}