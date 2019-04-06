package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import duelistmod.*;
import duelistmod.patches.*;

public class SuperheavySwordsman extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("SuperheavySwordsman");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_SWORDSMAN);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public SuperheavySwordsman() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = 3;
		this.summons = this.baseSummons = 1;
		this.dex = 1;
		//this.exhaust = true;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.SUPERHEAVY);
		this.originalName = this.name;
		this.isSummon = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		if (upgraded) { attack(m, AFX, this.damage); }
		applyPowerToSelf(new DexterityPower(p, this.dex));
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new SuperheavySwordsman();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.target = CardTarget.ENEMY;
			//this.upgradeDamage(3);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	@Override
	public void onTribute(DuelistCard tributingCard) {
		if (tributingCard.hasTag(Tags.SUPERHEAVY))
		{
			applyPowerToSelf(new DexterityPower(AbstractDungeon.player, DuelistMod.superheavyDex));
		}

	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		if (upgraded) { attack(m, AFX, this.damage); }
		applyPowerToSelf(new DexterityPower(p, this.dex));

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		if (upgraded) { attack(m, AFX, this.damage); }
		applyPowerToSelf(new DexterityPower(p, this.dex));
		
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