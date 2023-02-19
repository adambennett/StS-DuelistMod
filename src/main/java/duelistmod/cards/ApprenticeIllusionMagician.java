package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

import java.util.List;

public class ApprenticeIllusionMagician extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("ApprenticeIllusionMagician");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("ApprenticeIllusionMagician.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 1;
	private boolean upgradeAvail = true;
	// /STAT DECLARATION/

	public ApprenticeIllusionMagician() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = 11;
		this.tributes = this.baseTributes = 3;
		this.magicNumber = this.baseMagicNumber = 1;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.ORB_DECK);
		this.tags.add(Tags.SPELLCASTER);
		this.tags.add(Tags.ARCANE);
		this.orbDeckCopies = 1;
		this.misc = 0;
		this.originalName = this.name;
		this.setupStartingCopies();
		this.exhaust = true;
		this.enemyIntent = AbstractMonster.Intent.ATTACK_BUFF;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		tribute();
		if (targets.size() > 0) {
			attack(targets.get(0), AFX, this.damage);
		}
		AnyDuelist duelist = AnyDuelist.from(this);
		duelist.applyPowerToSelf(new FocusPower(duelist.creature(), this.magicNumber));
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new ApprenticeIllusionMagician();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (canUpgrade()) 
		{
			// Name
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			
			// Upgrade Effects
			if (DuelistMod.hasUpgradeBuffRelic)
			{
				if      (timesUpgraded == 1) 	{ this.upgradeDamage(5);							}
				else if (timesUpgraded == 2) 	{ this.upgradeMagicNumber(1); 						}
				else if (timesUpgraded == 3) 	{ this.upgradeDamage(6); 							}
				else if (timesUpgraded == 4) 	{ this.upgradeMagicNumber(1); 						}
				else if (timesUpgraded == 5) 	{ this.exhaust = false;								}
			}
			
			else
			{
				if      (timesUpgraded == 1) 	{ this.upgradeDamage(3);							}
				else if (timesUpgraded == 2) 	{ this.upgradeMagicNumber(1); 						}
				else if (timesUpgraded == 3) 	{ this.upgradeDamage(4); 							}
				else if (timesUpgraded == 4) 	{ this.upgradeTributes(-1);							}
				else if (timesUpgraded == 5) 	{ this.exhaust = false; 							}
			}

			// Description
        	if (timesUpgraded == 5) { this.rawDescription = EXTENDED_DESCRIPTION[0]; }
        	else { this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc(); }
			this.initializeDescription();
			
			if (timesUpgraded == 5)
			{
				this.upgradeAvail = false;
			}
		}
	}
	
	@Override
	public boolean canUpgrade()
	{
		if (upgradeAvail) { return true; }
		else { return false; }
	}



	











}
