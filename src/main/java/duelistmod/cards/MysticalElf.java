package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class MysticalElf extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("MysticalElf");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.MYSTICAL_ELF);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public MysticalElf() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseBlock = this.block = 7;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.LEGEND_BLUE_EYES);
		this.tags.add(Tags.SPELLCASTER);
		this.tags.add(Tags.SPELLCASTER_DECK);
		this.tags.add(Tags.OP_SPELLCASTER_DECK);
		this.tags.add(Tags.ASCENDED_ONE_DECK);
        this.tags.add(Tags.EXODIA_DECK);
		this.exodiaDeckCopies = 1;
        this.a1DeckCopies = 1;
        this.startingOPSPDeckCopies = 1;
        this.spellcasterDeckCopies = 2;
		this.summons = this.baseSummons = 1;
		this.originalName = this.name;
		this.isSummon = true;
		this.setupStartingCopies();
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		block(this.block);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new MysticalElf();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.upgradeBaseCost(0);
			this.upgradeBlock(3);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
		}
	}

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		spellcasterSynTrib(tributingCard);
	}




	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		block(this.block);

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
