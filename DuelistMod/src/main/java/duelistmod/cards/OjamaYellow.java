package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.*;
import duelistmod.helpers.Util;
import duelistmod.patches.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class OjamaYellow extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("OjamaYellow");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.OJAMA_YELLOW);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public OjamaYellow() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = 2;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.OJAMA);
		this.tags.add(Tags.INVASION_CHAOS);
		this.tags.add(Tags.OJAMA_DECK);
        this.tags.add(Tags.EXODIA_DECK);
		this.exodiaDeckCopies = 1;
		this.ojamaDeckCopies = 2;
		this.originalName = this.name;
		this.exhaust = true;
		this.summons = this.baseSummons = 1;
		this.isSummon = true;
		this.setupStartingCopies();
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		// Add random cards to hand
		for (int i = 0; i < this.magicNumber; i++)
		{
			DuelistCard randomMonster = (DuelistCard) returnTrulyRandomInCombatFromSet(Tags.MONSTER);
			if (upgraded) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, this.upgraded, true, false, true, randomMonster.baseTributes > 0, randomMonster.baseSummons > 0, true, true, 1, 2, 0, 2, 0, 2)); }
			else { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, this.upgraded, true, false, true, randomMonster.baseTributes > 0, randomMonster.baseSummons > 0, false, false, 1, 3, 0, 1, 0, 1)); }
			if (DuelistMod.debug) { DuelistMod.logger.info("Calling RandomizedAction from: " + this.originalName); }
		}
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new OjamaYellow();
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

    // Checking for Monster Zones if the challenge is enabled
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }

    	if (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeMode)
    	{
    		if ((DuelistMod.getChallengeDiffIndex() < 3) && this.misc == 52) { return true; }
    		if (p.hasPower(SummonPower.POWER_ID))
    		{
    			int sums = DuelistCard.getSummons(p); int max = DuelistCard.getMaxSummons(p);
    			if (sums + this.summons <= max) 
    			{ 
    				return true; 
    			}
    			else 
    			{ 
    				if (sums < max) 
    				{ 
    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    					
    				}
    				else { this.cantUseMessage = "No monster zones remaining"; }
    				return false; 
    			}
    		}
    		else
    		{
    			return true;
    		}
    	}
    	
    	else
    	{
    		return true;
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
	public void summonThis(int summons, DuelistCard c, int var)
	{
	
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {

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