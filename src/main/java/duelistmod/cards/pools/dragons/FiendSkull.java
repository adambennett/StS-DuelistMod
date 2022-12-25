package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class FiendSkull extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("FiendSkull");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.FIEND_SKULL);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.FIRE;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public FiendSkull() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = 15;
		this.tributes = this.baseTributes = 3;
		this.baseMagicNumber = this.magicNumber = 3;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.DRAGON);
		this.tags.add(Tags.FIEND);
		this.tags.add(Tags.LEGACY_DARKNESS);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.REDUCED);
		this.misc = 0;
		this.originalName = this.name;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		attack(m, AFX, this.damage);
		tribute(p, this.tributes, false, this);		
		applyPower(new VulnerablePower(m, this.magicNumber, true), m);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new FiendSkull();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(5);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}



	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		dragonSynTrib(tributingCard);		
		fiendSynTrib(tributingCard);
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
