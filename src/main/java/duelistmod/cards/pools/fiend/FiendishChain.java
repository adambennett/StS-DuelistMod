package duelistmod.cards.pools.fiend;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class FiendishChain extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("FiendishChain");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.FIENDISH_CHAIN);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public FiendishChain() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.originalName = this.name;
		this.tags.add(Tags.TRAP);
		this.tags.add(Tags.ALLOYED);
		this.tributes = this.baseTributes = 3;
		this.baseSecondMagic = this.secondMagic = 9;
		this.baseMagicNumber = this.magicNumber = 2;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute();
		gainTempHP(this.secondMagic);
		for (AbstractCard c : p.hand.group)
		{
			if (c instanceof DuelistCard && !c.uuid.equals(this.uuid) && c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.TOKEN))
			{
				DuelistCard dC = (DuelistCard)c;
				if (dC.isTributeCard())
				{
					AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dC, this.magicNumber, true));
				}
			}
		}
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new FiendishChain();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeSecondMagic(3);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
	

	



	






}
