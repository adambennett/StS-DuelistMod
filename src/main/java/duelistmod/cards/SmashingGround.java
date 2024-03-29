package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class SmashingGround extends DynamicDamageCard
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("SmashingGround");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.SMASHING_GROUND);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public SmashingGround() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = 4;
		this.isMultiDamage = true;
		this.tags.add(Tags.SPELL);
		this.tags.add(Tags.ALL);
		this.originalName = this.name;
		this.exhaust = true;
		this.baseAFX = AttackEffect.BLUNT_HEAVY;
	}
	
	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		m = AbstractDungeon.getRandomMonster();
		if (m != null) {
			attack(m);
		}
	}

	@Override
	public int damageFunction() {
		return this.magicNumber * DuelistMod.summonCombatCount;
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new SmashingGround();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(2);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}

	


	






}
