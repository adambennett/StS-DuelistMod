package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class ToonSummonedSkull extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("ToonSummonedSkull");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.TOON_SUMMONED_SKULL);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 2;
	private static final int DAMAGE = 15;
	// /STAT DECLARATION/

	public ToonSummonedSkull() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.toon = true;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FIEND);
		this.tags.add(Tags.TOON_DECK);
		this.tags.add(Tags.TOON_WORLD);
		this.tags.add(Tags.TOON_POOL);
		this.toonDeckCopies = 1;
		this.misc = 0;
		this.originalName = this.name;
		this.setupStartingCopies();
		this.tributes = this.baseTributes = 2;
		this.baseMagicNumber = this.magicNumber = 2;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		damageThroughBlock(m, p, this.damage, AFX);
		tribute(p, this.tributes, false, this);		
		applyPowerToSelf(new StrengthPower(p, this.magicNumber));
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new ToonSummonedSkull();
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



	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		toonSynTrib(tributingCard);
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
