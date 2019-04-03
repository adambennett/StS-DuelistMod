package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.patches.*;


public class RedMedicine extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = duelistmod.DuelistMod.makeID("RedMedicine");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.RED_MEDICINE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;
    private static int MIN_TURNS_ROLL = 1;
    private static int MAX_TURNS_ROLL = 5;
    private static final int BUFFS = 1;
    // /STAT DECLARATION/

    public RedMedicine() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = BUFFS;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.REDUCED);
        this.tags.add(Tags.GENERATION_DECK);
		this.startingGenDeckCopies = 1;
		this.originalName = this.name;
		this.exhaust = true;
		this.setupStartingCopies();
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
		AbstractPower a = applyRandomBuffPlayer(p, randomTurnNum, false);
		if (DuelistMod.debug) { System.out.println("theDuelist:RedMedicine --- > Generated buff: " + a.name ); }
		//if (this.upgraded) { applyPowerToSelf(randomBuffB); }
		if (this.upgraded) 
		{ 
			int roll = AbstractDungeon.cardRandomRng.random(1, 2);
			if (roll == 1)
			{
				applyRandomBuffPlayer(p, randomTurnNumB, false); 
				if (DuelistMod.debug) { System.out.println("theDuelist:RedMedicine(Upgrade) --- > Generated buff: " + a.name ); }
			}
		}
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
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}