package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.SteroidPotion;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class IronCall extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("IronCall");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("IronCall.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public IronCall() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 2;
        this.tags.add(Tags.SPELL);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Add random cards to hand
    	ArrayList<DuelistCard> randomCards = new ArrayList<DuelistCard>();
    	DuelistCard machineMon = (DuelistCard) returnTrulyRandomFromSets(Tags.MACHINE, Tags.MONSTER);
    	randomCards.add(machineMon);
    	if (this.magicNumber > 1)
    	{
			for (int i = 0; i < this.magicNumber - 1; i++)
			{
				DuelistCard randomMonster = (DuelistCard) returnTrulyRandomFromSet(Tags.MACHINE, false);
				while (randomCards.contains(randomMonster)) { randomMonster = (DuelistCard) returnTrulyRandomFromSet(Tags.MACHINE, false); }
				randomCards.add(randomMonster);
			}
    	}
		
		for (DuelistCard randomMonster : randomCards)
		{
			AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, this.upgraded, true, false, false, false, randomMonster.baseSummons > 0, false, false, 1, 3, 0, 0, 0, 1));
			if (DuelistMod.debug) { DuelistMod.logger.info("Calling RandomizedAction from: " + this.originalName); }
		}
		
		getPotion(new SteroidPotion());
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new IronCall();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
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
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		
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