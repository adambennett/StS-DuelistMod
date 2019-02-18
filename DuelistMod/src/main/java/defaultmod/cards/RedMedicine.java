package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;


public class RedMedicine extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("RedMedicine");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.RED_MEDICINE);
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
    private static int MIN_TURNS_ROLL = 1;
    private static int MAX_TURNS_ROLL = 6;
    private static final int BUFFS = 1;
    // /STAT DECLARATION/

    public RedMedicine() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = BUFFS;
        this.tags.add(DefaultMod.SPELL);
		this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Get random number of turns for buff to apply for
		int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
		int randomTurnNumB = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);

		/*
		// Get two random buffs
		AbstractPower randomBuff = getRandomBuffSmall(p, randomTurnNum);
		AbstractPower randomBuffB = getRandomBuffSmall(p, randomTurnNumB);

		// Apply random buff(s)
		applyPowerToSelf(randomBuff);
		*/
		applyRandomBuffPlayer(p, randomTurnNum, false);
		System.out.println("theDuelist:RedMedicine --- > Generated buff: " );
		//if (this.upgraded) { applyPowerToSelf(randomBuffB); }
		if (this.upgraded) { applyRandomBuffPlayer(p, randomTurnNumB, false); System.out.println("theDuelist:RedMedicine(Upgrade) --- > Generated buff: " ); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new RedMedicine();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(0);
            MIN_TURNS_ROLL = 3;
            MAX_TURNS_ROLL = 7;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}