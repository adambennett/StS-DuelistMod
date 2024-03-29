package duelistmod.cards.pools.machine;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class RedGadget extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("RedGadget");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("RedGadget.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public RedGadget() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.MACHINE);
		this.originalName = this.name;
		this.summons = this.baseSummons = 2;
		this.isSummon = true;
		this.baseDamage = this.damage = 12;
		this.magicNumber = this.baseMagicNumber = 2;
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon();
		attack(m);		
		ArrayList<DuelistCard> tokens = DuelistCardLibrary.getTokensForCombat();
		ArrayList<AbstractCard> abTokens = new ArrayList<AbstractCard>();
		int iterations = this.magicNumber;
		abTokens.addAll(tokens);
		if (!(iterations >= tokens.size())) { for (int i = 0; i < tokens.size() - iterations; i++) { abTokens.remove(AbstractDungeon.cardRandomRng.random(abTokens.size() - 1)); }}
		AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(false, false, 1, abTokens));
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() 
	{
		return new RedGadget();
	}

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) 
		{
			this.upgradeName();
			this.upgradeDamage(4);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
	



	





	








}
