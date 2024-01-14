package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.*;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class ToonDarkMagician extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = duelistmod.DuelistMod.makeID("ToonDarkMagician");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.TOON_DARK_MAGICIAN);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_DIAGONAL;
	private static final int COST = 2;
	private static final int DAMAGE = 24;
	// /STAT DECLARATION/

	public ToonDarkMagician() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.toon = true;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.TOON_WORLD);
		this.tags.add(Tags.TOON_POOL);
		this.tags.add(Tags.SPELLCASTER);
		this.tags.add(Tags.FULL);
		this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 2;
		this.magicNumber = this.baseMagicNumber = 1;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute(p, this.tributes, false, this);
		damageThroughBlock(m, p, this.damage, AFX);
		AbstractOrb summoner = new Summoner();
		channel(summoner);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new ToonDarkMagician();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}



}
