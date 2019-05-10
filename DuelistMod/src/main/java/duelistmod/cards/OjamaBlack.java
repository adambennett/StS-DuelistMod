package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.*;
import duelistmod.interfaces.*;
import duelistmod.orbs.Black;
import duelistmod.patches.*;

public class OjamaBlack extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("OjamaBlack");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.OJAMA_BLACK);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 0;
	private static final int SUMMONS = 1;
	private static int MIN_TURNS_ROLL = 3;
	private static int MAX_TURNS_ROLL = 6;
	// /STAT DECLARATION/

	public OjamaBlack() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.OJAMA);
		this.tags.add(Tags.INVASION_CHAOS);
		this.tags.add(Tags.OJAMA_DECK);
		this.ojamaDeckCopies = 2;
		this.baseBlock = this.block = 5;
		this.originalName = this.name;
		this.summons = this.baseSummons = SUMMONS;
		this.isSummon = true;
		this.setupStartingCopies();
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		// Summon
		summon(p, this.summons, this);
		AbstractOrb black = new Black();
		channel(black);
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
			this.upgradeSummons(1);
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
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(1, 2); 
		int randomDebuffNumU = AbstractDungeon.cardRandomRng.random(2, 4); 
		int primary = 4;
		if (this.upgraded) { primary = randomDebuffNumU; }
		else { primary = randomDebuffNum; }
		for (int i = 0; i < primary; i++)
		{
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
			applyPower(RandomEffectsHelper.getRandomDebuff(p, targetMonster, randomTurnNum), targetMonster);
		}
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(1, 2); 
		int randomDebuffNumU = AbstractDungeon.cardRandomRng.random(2, 4); 
		int primary = 4;
		if (this.upgraded) { primary = randomDebuffNumU; }
		else { primary = randomDebuffNum; }
		for (int i = 0; i < primary; i++)
		{
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
			applyPower(RandomEffectsHelper.getRandomDebuff(p, targetMonster, randomTurnNum), targetMonster);
		}
		
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