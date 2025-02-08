package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.TreatMagicianCard;
import duelistmod.cards.pools.toon.treat.AppleTreat;
import duelistmod.patches.AbstractCardEnum;

public class ToonAppleMagicianGirl extends TreatMagicianCard {

	public static final String ID = DuelistMod.makeID("ToonAppleMagicianGirl");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("ToonAppleMagicianGirl.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;

	public ToonAppleMagicianGirl() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, new AppleTreat(), UPGRADE_DESCRIPTION);
		this.magicNumber = this.baseMagicNumber = 5;
		this.baseTributes = this.tributes = 2;
		this.baseSummons = this.summons = 1;
	}

	@Override
	public void tributeSummon() {
		tribute();
		summon();
	}

	@Override
	public AbstractCard makeCopy() {
		return new ToonAppleMagicianGirl();
	}
}
