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
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class Polymerization extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Polymerization");
    public static final String IDB = DuelistMod.makeID("PredaPolymerization");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardStrings cardStringsB = CardCrawlGame.languagePack.getCardStrings(IDB);
    public static final String IMG = DuelistMod.makePath(Strings.POLYMERIZATION);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String DESCRIPTIONB = cardStringsB.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String UPGRADE_DESCRIPTIONB = cardStringsB.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    private boolean preda = false;
    // /STAT DECLARATION/

    public Polymerization() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.EXODIA_DECK);
        this.exodiaDeckCopies = 1;
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 2;
        this.exhaust = true;
        this.selfRetain = true;
        this.setupStartingCopies();
    }
    
    // For Predaplant Chimerafflesia
    public Polymerization(boolean predaCham) 
    {
        super(ID, NAME, IMG, COST,  DESCRIPTIONB, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 2;
        this.preda = true;
        this.exhaust = true;
        this.selfRetain = true;
        this.modifyCostForCombat(-cost);
        this.isCostModified = true;   
        this.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> handCards = new ArrayList<>();
		for (AbstractCard a : p.hand.group) { if (a.hasTag(Tags.MONSTER) && !a.hasTag(Tags.EXEMPT)) { handCards.add((DuelistCard) a.makeStatEquivalentCopy()); }}
		if (handCards.size() > 0)
		{
			if (handCards.size() < this.magicNumber) { AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(handCards, handCards.size(), false, false, m, false)); }
			else { AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(handCards, this.magicNumber, false, false, m, false)); }    		
		}    
    	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new Polymerization();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            if (this.preda) { exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTIONB); }
            else { exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); }
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

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
   
}