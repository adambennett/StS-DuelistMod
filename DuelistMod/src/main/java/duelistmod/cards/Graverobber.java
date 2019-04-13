package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;

public class Graverobber extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Graverobber");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GRAVEROBBER);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    //private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    private static ArrayList<DuelistCard> drawPowers = new ArrayList<DuelistCard>();
    // /STAT DECLARATION/

    public Graverobber() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.ALL);
        this.misc = 0;
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 1;
		this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		drawPowers = new ArrayList<DuelistCard>();
		ArrayList<AbstractCard> chooseCards = new ArrayList<AbstractCard>();
		for (DuelistCard c : DuelistMod.spellsThisRun) { drawPowers.add(c); }
		for (DuelistCard c : DuelistMod.trapsThisRun) { drawPowers.add(c); }

		if (drawPowers.size() >= 0)
		{
			while (chooseCards.size() < this.magicNumber)
			{
				DuelistCard drawCard = returnRandomFromArray(drawPowers);
				chooseCards.add(drawCard);
				if (DuelistMod.debug) { System.out.println("graverobber added : " + drawCard.originalName + " to chooseCards in the while loop"); }
			}
			
			if (chooseCards.size() > 0) 
			{ 
				int roll = 1;
				int lowRoll = 1;
				int highRoll = 4;
				if (upgraded) { lowRoll = 0; highRoll = 0; roll = 2; }
				if (DuelistMod.debug) { System.out.println("graverobber found choose cards to be > 0"); }
				AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(chooseCards, false, roll, false, !this.upgraded, false, true, false, false, false, lowRoll, highRoll, 0, 0, 0, 0));
			}
		}
		else
		{
			if (DuelistMod.debug) { System.out.println("graverobber found draw powers size was 0"); }
		}
		
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Graverobber();
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
	public void onTribute(DuelistCard tributingCard)
	{
		
	}


	@Override
	public void onResummon(int summons) 
	{
		
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
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) 
	{
		if (DuelistMod.debug) { System.out.println("theDuelist:Invigoration:optionSelected() ---> can I see the card we selected? the card is: " + drawPowers.get(arg2).originalName); }
	}
}