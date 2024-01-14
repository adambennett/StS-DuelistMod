package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class ToonBarrelDragon extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("ToonBarrelDragon");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.TOON_BARREL_DRAGON);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;
	private static final int DAMAGE = 0;
	private static int MIN_DMG = 10;
	private static int MAX_DMG = 20;
	private static int MIN_DMG_U = 14;
	private static int MAX_DMG_U = 24;
	// /STAT DECLARATION/

	public ToonBarrelDragon() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.isMultiDamage = true;
		this.multiDamage = new int[]{0, 0, 0, 0, 0};
		this.magicNumber = this.baseMagicNumber = 25;
		this.toon = true;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.TOON_WORLD);
		this.tags.add(Tags.TOON_POOL);
		this.tags.add(Tags.DRAGON);
		this.tags.add(Tags.MACHINE);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.FULL);
		this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int[] damageArray = new int[AbstractDungeon.getMonsters().monsters.size()];
		for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++)
		{
			int randomNum = AbstractDungeon.cardRandomRng.random(MIN_DMG, MAX_DMG );
			int randomNumU = AbstractDungeon.cardRandomRng.random(MIN_DMG_U, MAX_DMG_U);
			if (upgraded) { damageArray[i] = randomNum; }
			else { damageArray[i] = randomNumU; }
		}
		
		this.multiDamage = damageArray;
		tribute(p, this.tributes, false, this);
		for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++)
		{
			AbstractMonster mon = AbstractDungeon.getMonsters().monsters.get(i);
			damageThroughBlock(mon, p, damageArray[i], AbstractGameAction.AttackEffect.FIRE);
		}
		
		
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new ToonBarrelDragon();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.upgradeBaseCost(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}



}
