package defaultmod.cards;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class OjamaBlack extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("OjamaBlack");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.OJAMA_BLACK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 0;
    private static final int SUMMONS = 1;
    private static int MIN_TURNS_ROLL = 3;
    private static int MAX_TURNS_ROLL = 8;
    // /STAT DECLARATION/

    public OjamaBlack() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.OJAMA);
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Summon
		summon(p, SUMMONS);
		
		// Get random monster target
		AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
		
		// Get number of debuffs
		int randomDebuffNum = ThreadLocalRandom.current().nextInt(1, 2 + 1); 
    	int randomDebuffNumU = ThreadLocalRandom.current().nextInt(2, 4 + 1); 
    	
    	// Set number of debuffs to right number (based on upgrade status)
    	int primary = 4;
    	if (this.upgraded) { primary = randomDebuffNumU; }
    	else { primary = randomDebuffNum; }
    	
    	// For each debuff to apply, apply a random debuff with a new random turn number
    	for (int i = 0; i < primary; i++)
    	{
    		int randomTurnNum = ThreadLocalRandom.current().nextInt(MIN_TURNS_ROLL, MAX_TURNS_ROLL + 1);
    		applyPower(getRandomDebuff(p, targetMonster, randomTurnNum), targetMonster);
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new OjamaBlack();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            MIN_TURNS_ROLL = 5;
            MAX_TURNS_ROLL = 10;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}