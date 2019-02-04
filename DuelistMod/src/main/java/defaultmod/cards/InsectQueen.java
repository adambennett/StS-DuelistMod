package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.ObeliskPower;
import defaultmod.powers.SummonPower;

public class InsectQueen extends CustomCard {

	/*
	 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
	 *
	 * In order to understand how image paths work, go to defaultmod/DefaultMod.java, Line ~140 (Image path section).
	 *
	 * Strike Deal 7(9) damage.
	 */

	// TEXT DECLARATION

	public static final String ID = defaultmod.DefaultMod.makeID("InsectQueen");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// Yes, you totally can use "defaultModResources/images/cards/Attack.png" instead and that would work.
	// It might be easier to use that while testing.
	// Using makePath is good practice once you get the hand of things, as it prevents you from
	// having to change *every single card/file/path* if the image path changes due to updates or your personal preference.

	public static final String IMG = DefaultMod.makePath(DefaultMod.INSECT_QUEEN);

	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	// /TEXT DECLARATION/


	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

	private static final int COST = 2;
	private static final int POISON_MULT = 3;

	// /STAT DECLARATION/

	public InsectQueen() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = POISON_MULT;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		// Get # of summons
		int playerSummons = (p.getPower(SummonPower.POWER_ID).amount);
		
		// Tribute Summon (all summons)
		AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, SummonPower.POWER_ID, playerSummons));

		// Check for Obelisk after tributing
		if (p.hasPower(ObeliskPower.POWER_ID))
		{
			int[] temp = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
			for (int i : temp) { i = i * playerSummons; }
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
		}

		// Apply poison to all enemies
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			flash();
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if ((!monster.isDead) && (!monster.isDying)) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new PoisonPower(monster, p, playerSummons * POISON_MULT), playerSummons * POISON_MULT));
				}
			}
		}
		
		// If unupgraded, reduce max summons by 1.
		if (!this.upgraded)
		{
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, -1, true), 0));
		}
		
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new InsectQueen();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();			
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	// If player doesn't have enough summons, can't play card
	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m)
	{
		if (p.energy.energy >= COST)
		{
			if (p.hasPower(SummonPower.POWER_ID)) 
			{
				int temp = (p.getPower(SummonPower.POWER_ID).amount);
				if (temp >= 1)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}

		return false;
	}

}