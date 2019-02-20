package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class OjamaGreen extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("OjamaGreen");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.OJAMA_GREEN);
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
    private static final int SUMMONS = 1;
    private static int MIN_TURNS_ROLL = 3;
    private static int MAX_TURNS_ROLL = 5;
    // /STAT DECLARATION/

    public OjamaGreen() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.OJAMA);
        this.tags.add(DefaultMod.DARK_CRISIS);
        this.baseBlock = this.block = 5;
        this.exhaust = true;
		this.originalName = this.name;
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Summon
		summon(p, SUMMONS, this);
		
		// Get number of buffs
		int randomBuffNum = AbstractDungeon.cardRandomRng.random(1, 2); 
    	int randomBuffNumU = AbstractDungeon.cardRandomRng.random(2, 4); 
    	
    	// Set number of buffs to right number (based on upgrade status)
    	int primary = 4;
    	if (this.upgraded) { primary = randomBuffNumU; }
    	else { primary = randomBuffNum; }
    	
    	// For each buff to apply, apply a random buff with a new random turn number
    	for (int i = 0; i < primary; i++)
    	{
    		int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
    		applyRandomBuffPlayer(p, randomTurnNum, false);
    	}
    	
    	//block(this.block);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new OjamaGreen();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeBlock(3);
            MIN_TURNS_ROLL = 4;
            MAX_TURNS_ROLL = 6;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSummon(int summons) {
		// TODO Auto-generated method stub
		
	}
}