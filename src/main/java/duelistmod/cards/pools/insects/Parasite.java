package duelistmod.cards.pools.insects;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.ParasitePower;
import duelistmod.variables.*;

public class Parasite extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("Parasite");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.PARASITE);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public Parasite() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.PHARAOH_SERVANT);
		this.tags.add(Tags.INSECT);
		this.tags.add(Tags.GOOD_TRIB);
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
		this.magicNumber = this.baseMagicNumber = 3;
		this.isSummon = false;

	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute();
		applyPowerToSelf(new ParasitePower(this.magicNumber));
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new Parasite();
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
