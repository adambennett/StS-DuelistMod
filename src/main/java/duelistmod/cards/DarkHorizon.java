package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class DarkHorizon extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("DarkHorizon");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("DarkHorizon.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
	private static final int COST = 0;
	// /STAT DECLARATION/

	public DarkHorizon() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.originalName = this.name;
		this.tags.add(Tags.TRAP);
		this.tags.add(Tags.ORB_DECK);
		this.orbDeckCopies = 1;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
		this.tributes = this.baseTributes = 1;
		this.baseMagicNumber = this.magicNumber = 2;
		this.setupStartingCopies();
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		ArrayList<DuelistCard> tribList = tribute(p, this.tributes, false, this);
		// Upgraded
		if (upgraded)
		{
			// Check tribute(s) for fiend(s)
			boolean foundFiend = false;
			if (tribList.size() > 0)
			{
				for (DuelistCard c : tribList)
				{
					if (c.hasTag(Tags.SPELLCASTER))
					{
						foundFiend = true;
						break;
					}
				}
			}
			
			// If fiend tribute
			if (foundFiend)
			{
				for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
				{
					evokeMult(2, AbstractDungeon.player.orbs.get(i));
				}
				AbstractDungeon.actionManager.addToTop(new RemoveAllOrbsAction());
			}
			
			// No fiend tribute
			else
			{
				evokeMult(this.magicNumber);
			}
		}
		
		// Un-upgraded
		else
		{
			evokeMult(this.magicNumber);
		}
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new DarkHorizon();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
	


	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub

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
