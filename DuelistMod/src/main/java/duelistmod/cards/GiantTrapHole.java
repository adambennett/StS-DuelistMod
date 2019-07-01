package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;

public class GiantTrapHole extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("GiantTrapHole");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GiantTrapHole.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public GiantTrapHole() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.SPELL);
		this.originalName = this.name;
		this.exhaust = true;
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		// Add random cards to hand
    	ArrayList<DuelistCard> randomCards = new ArrayList<DuelistCard>();
		for (int i = 0; i < this.magicNumber; i++)
		{
			DuelistCard randomMonster = (DuelistCard) returnTrulyRandomFromSet(Tags.GIANTS);
			while (randomCards.contains(randomMonster)) { randomMonster = (DuelistCard) returnTrulyRandomFromSet(Tags.GIANTS); }
			randomCards.add(randomMonster);
		}
		
		for (DuelistCard randomMonster : randomCards)
		{
			addCardToHand(randomMonster);
		}
		
		if (this.cost != 3)
    	{
    		this.modifyCostForCombat(-this.cost + 3);
    		this.isCostModified = false;
    	}
    }
    
    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) 
    {
    	if (c.type.equals(CardType.POWER))
    	{
    		this.modifyCostForCombat(-this.magicNumber);
    		this.isCostModified = true;
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GiantTrapHole();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeMagicNumber(2); }
            else { this.upgradeMagicNumber(1); }
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