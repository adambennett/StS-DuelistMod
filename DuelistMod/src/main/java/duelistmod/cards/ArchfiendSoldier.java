package duelistmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;

public class ArchfiendSoldier extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("ArchfiendSoldier");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("ArchfiendSoldier.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public ArchfiendSoldier() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = 6;
		this.summons = this.baseSummons = 1;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FIEND);
		this.tags.add(Tags.FIEND_DECK);
		this.fiendDeckCopies = 3;
		this.misc = 0;
		this.originalName = this.name;
		this.setupStartingCopies();
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon();
		attack(m, AFX, this.damage);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new ArchfiendSoldier();
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
	public void onTribute(DuelistCard tributingCard)
	{
		// Fiend Tribute
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower(DoomdogPower.POWER_ID)) { int dmgAmount = p.getPower(DoomdogPower.POWER_ID).amount; damageAllEnemiesThornsNormal(dmgAmount); }
		if (p.hasPower(RedMirrorPower.POWER_ID)) { for (AbstractCard c : p.discardPile.group) { if (c.cost > 0)	{ c.modifyCostForTurn(-p.getPower(RedMirrorPower.POWER_ID).amount);	c.isCostModifiedForTurn = true;	}}}
		if (tributingCard.hasTag(Tags.FIEND)) { AbstractDungeon.actionManager.addToBottom(new FetchAction(p.discardPile, DuelistMod.fiendDraw)); }
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