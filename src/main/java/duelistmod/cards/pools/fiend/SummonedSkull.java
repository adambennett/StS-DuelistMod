package duelistmod.cards.pools.fiend;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.variables.*;

import java.util.List;

public class SummonedSkull extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("SummonedSkull");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.SUMMONED_SKULL);
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
	private static final int DAMAGE = 11;
	// /STAT DECLARATION/

	public SummonedSkull() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.tributes = this.baseTributes = 2;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.METAL_RAIDERS);
		this.tags.add(Tags.FIEND);
		this.tags.add(Tags.EXODIA_DECK);
		this.tags.add(Tags.ORIGINAL_DECK);    
		this.tags.add(Tags.FIEND_DECK);
		this.tags.add(Tags.ASCENDED_TWO_DECK);
        this.a2DeckCopies = 1;
        this.zombieDeckCopies = 1;
        this.startingOriginalDeckCopies = 1;
        this.exodiaDeckCopies = 1;
		this.misc = 0;
		this.originalName = this.name;
		this.setupStartingCopies();
		this.enemyIntent = AbstractMonster.Intent.ATTACK;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
		if (targets.size() > 0) {
			attack(targets.get(0));
		}
		tribute();
		postDuelistUseCard(owner, targets);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new SummonedSkull();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(4);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
		}
	}


	











}
