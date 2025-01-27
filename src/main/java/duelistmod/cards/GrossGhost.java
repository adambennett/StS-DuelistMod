package duelistmod.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GrossGhost extends DuelistCard {

	public static final String ID = DuelistMod.makeID("GrossGhost");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("GrossGhost.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;

	public GrossGhost() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tributes = this.baseTributes = 2;
		this.magicNumber = this.baseMagicNumber = 2;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FIEND);
		this.tags.add(Tags.NEVER_EXHAUST);
        this.tags.add(Tags.BAD_MAGIC);
		this.tags.add(Tags.IMMORTAL);
		this.misc = 0;
		this.originalName = this.name;
		this.isEthereal = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		preDuelistUseCard(owner, targets);
		AnyDuelist duelist = AnyDuelist.from(this);
		tribute();
		ArrayList<DuelistCard> handTribs = new ArrayList<DuelistCard>();
		for (AbstractCard c : duelist.hand()) {
			if (c instanceof DuelistCard) {
				DuelistCard dC = (DuelistCard)c;
				if (dC.isTributeCard()) {
					handTribs.add(dC);
				}
			}
		}

		if (handTribs.size() > 0 && this.magicNumber > 0) {
			for (DuelistCard pick : handTribs) {
				pick.modifyTributesForCombat(this.magicNumber);
			}
		}
		postDuelistUseCard(owner, targets);
	}

	@Override
	public AbstractCard makeCopy() {
		return new GrossGhost();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(0);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}

}
