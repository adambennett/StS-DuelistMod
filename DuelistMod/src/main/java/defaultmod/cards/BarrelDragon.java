package defaultmod.cards;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.LanguidPower;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.abstracts.CustomCard;
import blackrusemod.powers.MatrixPower;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.ObeliskPower;
import defaultmod.powers.SummonPower;

public class BarrelDragon extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In order to understand how image paths work, go to defaultmod/DefaultMod.java, Line ~140 (Image path section).
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("BarrelDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // Yes, you totally can use "defaultModResources/images/cards/Attack.png" instead and that would work.
    // It might be easier to use that while testing.
    // Using makePath is good practice once you get the hand of things, as it prevents you from
    // having to change *every single card/file/path* if the image path changes due to updates or your personal preference.

    public static final String IMG = DefaultMod.makePath(DefaultMod.BARREL_DRAGON);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int TRIBUTES = 2;
    private static final int MIN_TURNS_ROLL = 1;
    private static final int MAX_TURNS_ROLL = 6;
    private static final int RANDOM_ENEMIES = 3;
    
    // /STAT DECLARATION/

    public BarrelDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = RANDOM_ENEMIES;
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		// Tribute Summon
    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, SummonPower.POWER_ID, TRIBUTES));
		
		// Check for Obelisk after tributing
    	if (p.hasPower(ObeliskPower.POWER_ID))
    	{
    		int[] temp = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
			for (int i : temp) { i = i * TRIBUTES; }
    		AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
    	}
    	
    	// Deal damage
		AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    
		// Get number of enemies
		int monsters = AbstractDungeon.getMonsters().monsters.size();
		
		// If number of enemies < debuff targets, set debuff targets # to number of enemies
		if (monsters < this.magicNumber) { this.magicNumber = this.baseMagicNumber = monsters; }
		
		// 3-4 times, apply 1 or 2 random debuffs to a random enemy
		for (int i = 0; i < this.magicNumber; i++)
		{
			// Get random number of turns for debuff to apply for (1-10)
			int randomTurnNum = ThreadLocalRandom.current().nextInt(MIN_TURNS_ROLL, MAX_TURNS_ROLL + 1);
			int randomTurnNumB = ThreadLocalRandom.current().nextInt(MIN_TURNS_ROLL, MAX_TURNS_ROLL + 1);
			
			// Get random monster target
			AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
			
			// Setup powers array for random debuff selection
			AbstractPower slow = new SlowPower(targetMonster, randomTurnNum);
			AbstractPower vulnerable = new VulnerablePower(p, randomTurnNum, false);
			AbstractPower poison = new PoisonPower(p, p, randomTurnNum);
			AbstractPower nPoison = new NecroticPoisonPower(p, p, randomTurnNum);
			AbstractPower weak = new WeakPower(p, randomTurnNum, false);
			AbstractPower languid = new LanguidPower(p, randomTurnNum, false);
			AbstractPower matrix = new MatrixPower(p, randomTurnNum);
			AbstractPower[] debuffs = new AbstractPower[] {slow, vulnerable, poison, nPoison, weak, languid, matrix};
			
			// Setup second powers array for random debuff #2 selection (for upgraded card)
			AbstractPower slowB = new SlowPower(targetMonster, randomTurnNumB);
			AbstractPower vulnerableB = new VulnerablePower(p, randomTurnNumB, false);
			AbstractPower poisonB = new PoisonPower(p, p, randomTurnNumB);
			AbstractPower nPoisonB = new NecroticPoisonPower(p, p, randomTurnNumB);
			AbstractPower weakB = new WeakPower(p, randomTurnNumB, false);
			AbstractPower languidB = new LanguidPower(p, randomTurnNumB, false);
			AbstractPower matrixB = new MatrixPower(p, randomTurnNumB);
			AbstractPower[] debuffsB = new AbstractPower[] {slowB, vulnerableB, poisonB, nPoisonB, weakB, languidB, matrixB};
			
			// Get two randomized debuffs
			int randomDebuffNum = ThreadLocalRandom.current().nextInt(0, debuffs.length);
			int randomDebuffNumB = ThreadLocalRandom.current().nextInt(0, debuffs.length);
			AbstractPower randomDebuffA = debuffs[randomDebuffNum];
			AbstractPower randomDebuffB = debuffsB[randomDebuffNumB];
	    
			// Apply random debuff(s)
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(targetMonster, p, randomDebuffA));
			if (this.upgraded) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(targetMonster, p, randomDebuffB)); }
		}
    
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BarrelDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // If player doesn't have enough summons, can't play card
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }
    
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= TRIBUTES) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = "Not enough Summons";
    	return false;
    }
}