package duelistmod.cards.pools.aqua;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.Splash;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class OhFish extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("OhFish");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.OH_FISH);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/


	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public OhFish() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.TRAP);
		this.originalName = this.name;
		this.baseBlock = this.block = 7;
		this.baseMagicNumber = this.magicNumber = 2;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		fish(this.magicNumber);
		block();
		channel(new Splash());
	}

	@Override
	public AbstractCard makeCopy() { return new OhFish(); }

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) 
		{
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
			else { this.upgradeName(NAME + "+"); }
			this.upgradeBlock(4);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription(); 
		}
	}


}
