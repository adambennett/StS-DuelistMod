package duelistmod.cards;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class Kuriboh extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("Kuriboh");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.KURIBOH);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// private static final int U_INC_SUMMONS = 1;
	// /STAT DECLARATION/

	public Kuriboh() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = 1;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.METAL_RAIDERS);
		this.originalName = this.name;
		this.summons = this.baseSummons = 1;
		this.isSummon = true;
		this.exhaust = true;
		this.cardsToPreview = new KuribohToken();
		this.enemyIntent = AbstractMonster.Intent.BUFF;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		if (upgraded) {
			incMaxSummons(owner, this.magicNumber);
		}
		DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new KuribohToken());
		summon(owner, this.summons, tok);
	}

	@Override
	public int incrementGeneratedIfPlayed() { return this.upgraded ? this.magicNumber : 0; }

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new Kuriboh();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}


	

}
