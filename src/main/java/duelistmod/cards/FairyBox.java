package duelistmod.cards;

import java.util.ArrayList;

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
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class FairyBox extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("FairyBox");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FairyBox.png");
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

    public FairyBox() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.EXODIA_DECK);
		this.exodiaDeckCopies = 1;
		this.setupStartingCopies();
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
		this.originalName = this.name;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		if (this.magicNumber < 1) {
			return;
		}
		ArrayList<AbstractCard> orbsToChooseFrom = DuelistCardLibrary.orbCardsForGeneration();
		if (!upgraded)
		{
			if (orbsToChooseFrom.size() > this.magicNumber)
			{
				ArrayList<AbstractCard> orbs = new ArrayList<AbstractCard>();
				for (int i = 0; i < this.magicNumber; i++)
				{
					AbstractCard orbCard = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1));
					while (orbs.contains(orbCard)) { orbCard = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)); }
					orbs.add(orbCard);
				}
				
				for (AbstractCard c : orbs)
				{
					AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(c, false, true, true, true, false, false, false, false, 0, 0, 0, 0, 0, 0, true));
					if (DuelistMod.debug) { DuelistMod.logger.info("Calling RandomizedAction from: " + this.originalName); }
				}
			}		
		}
		else
		{
			if (orbsToChooseFrom.size() > this.magicNumber)
			{
				ArrayList<AbstractCard> orbs = new ArrayList<AbstractCard>();
		    	ArrayList<String> orbNames = new ArrayList<String>();
				for (int i = 0; i < 5; i++)
				{
					AbstractCard random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1));
					while (orbNames.contains(random.name)) { random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)); }
					orbs.add((DuelistCard) random.makeCopy());
					orbNames.add(random.name);
					Util.log("Fairy Box generated an orb card for " + random.name);
				}
				AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(orbs, false, this.magicNumber, false, true, true, true, false, false, true, 0, 0, 0, 0, 0, 0, true));
			}
		}
	}

	@Override
	public AbstractCard makeCopy() { return new FairyBox(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			this.upgradeName();
			if (this.magicNumber < 5 && timesUpgraded > 1) { this.upgradeMagicNumber(1); }
			else { this.upgraded = true; }
			if (timesUpgraded == 0 || this.magicNumber == 5) { exodiaDeckCardUpgradeDesc(DESCRIPTION); }
			else { exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); }			
			this.initializeDescription();
		}
	}

	
	











}
