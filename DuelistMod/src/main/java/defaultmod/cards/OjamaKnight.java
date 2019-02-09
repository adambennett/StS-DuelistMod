package defaultmod.cards;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.SummonPower;

public class OjamaKnight extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("OjamaKnight");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.OJAMA_KNIGHT);
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
    private static int TRIBUTES = 1;
    private static int MIN_BUFF_TURNS_ROLL = 1;
    private static int MAX_BUFF_TURNS_ROLL = 2;
    private static int MIN_DEBUFF_TURNS_ROLL = 1;
    private static int MAX_DEBUFF_TURNS_ROLL = 4;
    // /STAT DECLARATION/

    public OjamaKnight() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.OJAMA);
        this.misc = 0;
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
		tribute(p, TRIBUTES, false, this);

		// Get number of buffs & debuffs
		int randomDebuffNum = ThreadLocalRandom.current().nextInt(1, 2 + 1); 
    	int randomDebuffNumU = ThreadLocalRandom.current().nextInt(2, 4 + 1); 
    	int randomBuffNum = ThreadLocalRandom.current().nextInt(1, 2 + 1); 
    	int randomBuffNumU = ThreadLocalRandom.current().nextInt(2, 4 + 1); 
    	
    	// Set number of buffs & debuffs to right number (based on upgrade status)
    	int primary = 4;	int primaryB = 4;
    	if (this.upgraded) { primary = randomDebuffNumU; primaryB = randomBuffNumU;  }
    	else { primary = randomDebuffNum; primaryB = randomBuffNum; }
 
		// Give self 'primaryB' random buffs
		for (int i = 0; i < primaryB; i++)
		{
			int randomTurnNum = ThreadLocalRandom.current().nextInt(MIN_BUFF_TURNS_ROLL, MAX_BUFF_TURNS_ROLL + 1);
			applyPower(getRandomBuff(p, randomTurnNum), p);
		}
		
		// Give 'primary' random debuffs to enemy
		for (int i = 0; i < primary; i++)
		{
			int randomTurnNum = ThreadLocalRandom.current().nextInt(MIN_DEBUFF_TURNS_ROLL, MAX_DEBUFF_TURNS_ROLL + 1);
			applyPower(getRandomDebuff(p, m, randomTurnNum), m);
		}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new OjamaKnight();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            MAX_DEBUFF_TURNS_ROLL = 6;
            MAX_BUFF_TURNS_ROLL = 4;
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