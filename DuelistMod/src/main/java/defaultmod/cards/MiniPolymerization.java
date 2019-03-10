package defaultmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class MiniPolymerization extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("MiniPolymerization");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.POLYMERIZATION);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public MiniPolymerization() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.SPELL);
        this.tags.add(DefaultMod.GENERATION_DECK);
        this.tags.add(DefaultMod.RANDOMONLY);
		this.startingGenDeckCopies = 1;
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 1;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> handCards = new ArrayList<AbstractCard>();
    	for (AbstractCard a : p.hand.group) { if (a.hasTag(DefaultMod.MONSTER)) { handCards.add(a); }}    	
    	if (handCards.size() > 0)
    	{
			for (int i = 0; i < this.magicNumber; i++)
			{
	    		AbstractCard summon = returnRandomFromArrayAbstract(handCards);
	    		DuelistCard cardCopy = DuelistCard.newCopyOfMonster(summon.originalName);
				if (cardCopy != null)
				{
					if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
					if (summon.upgraded) { cardCopy.upgrade(); }
					cardCopy.freeToPlayOnce = true;
					cardCopy.applyPowers();
					cardCopy.purgeOnUse = true;
					AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
					cardCopy.onResummon(1);
				}
			}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new MiniPolymerization();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
	
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
   
}