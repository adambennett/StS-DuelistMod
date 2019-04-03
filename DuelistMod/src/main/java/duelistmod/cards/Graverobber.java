package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
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
		this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int loopMax = 4;
    	int loopCheck = 0;
		drawPowers = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> chooseCards = new ArrayList<DuelistCard>();
		for (DuelistCard c : DuelistMod.spellsThisRun) { drawPowers.add(c); }
		for (DuelistCard c : DuelistMod.trapsThisRun) { drawPowers.add(c); }
		
		if (!upgraded)
		{
			if (drawPowers.size() > 0)
			{
				DuelistCard randomSpell = drawPowers.get(AbstractDungeon.cardRandomRng.random(drawPowers.size() - 1));
				if (randomSpell != null)
				{
					AbstractCard cardCopy = randomSpell.makeCopy();
					//if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
					cardCopy.freeToPlayOnce = true;
					cardCopy.applyPowers();
					cardCopy.purgeOnUse = true;
					AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
					//cardCopy.onResummon(1);
					//cardCopy.checkResummon();
				}
			}
		}
		else
		{
			if (drawPowers.size() >= 0)
			{
				while (chooseCards.size() < 3)
				{
					DuelistCard drawCard = returnRandomFromArray(drawPowers);
					while (chooseCards.contains(drawCard) && loopCheck < loopMax) { drawCard = returnRandomFromArray(drawPowers); loopCheck++; }
					chooseCards.add(drawCard);
				}
				
				if (chooseCards.size() > 0) { openRandomCardChoice(3, chooseCards); }
			}
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