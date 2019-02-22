package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class JudgeMan extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = defaultmod.DefaultMod.makeID("JudgeMan");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DefaultMod.makePath(DefaultMod.JUDGE_MAN);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 1;
	private static final int DAMAGE = 8;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int TRIBUTES = 1;
	private static final int SUMMONS = 1;
	// /STAT DECLARATION/

	public JudgeMan() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.tags.add(DefaultMod.MONSTER);
		this.misc = 0;
		this.originalName = this.name;
		this.summons = SUMMONS;
		this.isSummon = true;
		this.isTribute = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute(p, TRIBUTES, false, this);
		summon(p, SUMMONS, this);
		attack(m, AFX, this.damage);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new JudgeMan();
	}

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
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

		// Pumpking & Princess
		else if (this.misc == 52) { return true; }

		// Mausoleum check
		else if (p.hasPower(EmperorPower.POWER_ID))
		{
			EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
			if (!empInstance.flag)
			{
				return true;
			}
		}

		// Check for # of summons >= tributes
		else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= TRIBUTES) { return true; } } }

		// Player doesn't have something required at this point
		this.cantUseMessage = "Not enough Summons";
		return false;
	}

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub

	}



	@Override
	public void onSummon(int summons) 
	{
		summon(AbstractDungeon.player, summons, this);
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		attack(m, AFX, 15);
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		AbstractPlayer p = AbstractDungeon.player;
		//tribute(p, TRIBUTES, false, this);
		summon(p, summons, this);
		attack(m, AFX, this.damage);

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		//tribute(p, TRIBUTES, false, this);
		summon(p, summons, this);
		attack(m, AFX, this.damage);
		
	}

	@Override
	public String getID() {
		return ID;
	}
}