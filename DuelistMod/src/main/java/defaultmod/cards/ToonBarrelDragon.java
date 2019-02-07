package defaultmod.cards;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class ToonBarrelDragon extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = defaultmod.DefaultMod.makeID("ToonBarrelDragon");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DefaultMod.makePath(DefaultMod.TOON_BARREL_DRAGON);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
	private static final int COST = 2;
	private static final int DAMAGE = 0;
	private static final int TRIBUTES = 2;
	private static int MIN_DMG = 3;
	private static int MAX_DMG = 15;
	// /STAT DECLARATION/

	public ToonBarrelDragon() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = DAMAGE;
		this.isMultiDamage = true;
		this.multiDamage = new int[]{0, 0, 0, 0, 0};
		this.magicNumber = this.baseMagicNumber = 20;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int[] test = new int[5];
		for (int i = 0; i < 5; i++)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(MIN_DMG, MAX_DMG + 1);
			test[i] = randomNum;
		}
		this.multiDamage = test;
		tribute(p, TRIBUTES, false);
		AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
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
			this.upgradeBaseCost(1);
			MIN_DMG = 8;
			MAX_DMG = 20;
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
    	
    	// Check for Toon World
    	else if (!p.hasPower(ToonWorldPower.POWER_ID)) { this.cantUseMessage = "You need Toon World"; return false; }
    	
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= TRIBUTES) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = "Not enough Summons";
    	return false;
    }

}