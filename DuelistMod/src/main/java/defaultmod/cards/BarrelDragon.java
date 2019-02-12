package defaultmod.cards;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.SummonPower;

public class BarrelDragon extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("BarrelDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
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
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 0;
    private static final int DAMAGE = 12;
    private static final int TRIBUTES = 3;
    private static int MIN_TURNS_ROLL = 1;
    private static int MAX_TURNS_ROLL = 10;
    private static final int RANDOM_ENEMIES = 3;
    // /STAT DECLARATION/

    public BarrelDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = RANDOM_ENEMIES;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.DRAGON);
        this.misc = 0;
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		tribute(p, TRIBUTES, false, this);
		attack(m, AFX, this.damage);
    
		// Get number of enemies
		//int monsters = AbstractDungeon.getMonsters().monsters.size();
		
		// If number of enemies < debuff targets, set debuff targets # to number of enemies
		//if (monsters < this.magicNumber) { this.magicNumber = this.baseMagicNumber = monsters; }
		
		// 3-4 times, apply 1 or 2 random debuffs to a random enemy
		for (int i = 0; i < this.magicNumber; i++)
		{
			// Get random number of turns for debuff to apply for (1-10)
			int randomTurnNum = ThreadLocalRandom.current().nextInt(MIN_TURNS_ROLL, MAX_TURNS_ROLL + 1);
			int randomTurnNumB = ThreadLocalRandom.current().nextInt(MIN_TURNS_ROLL, MAX_TURNS_ROLL + 1);
			
			// Get random monster target
			AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();

			// Get two random debuffs
			AbstractPower randomDebuffA = getRandomDebuff(p, targetMonster, randomTurnNum);
			AbstractPower randomDebuffB = getRandomDebuff(p, targetMonster, randomTurnNumB);
	    
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
            MIN_TURNS_ROLL = 4;
            MAX_TURNS_ROLL = 12;
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
    	
    	// Pumpking & Princess
  		else if (this.misc == 52) { return true; }
    
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= TRIBUTES) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = "Not enough Summons";
    	return false;
    }
}