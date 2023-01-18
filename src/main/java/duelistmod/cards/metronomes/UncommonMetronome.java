package duelistmod.cards.metronomes;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class UncommonMetronome extends MetronomeCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("UncommonMetronome");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Metronome.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public UncommonMetronome() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.EXEMPT);
        this.tags.add(Tags.NEVER_GENERATE);
        this.tags.add(Tags.METRONOME);
        this.tags.add(Tags.ALLOYED);
        this.baseMagicNumber = this.magicNumber = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	metronomeAction(m);
    }
    
    public AbstractCard returnCard()
    {
    	ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
		for (DuelistCard c : DuelistMod.myCards) { if (!c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c) && !c.hasTag(Tags.NO_METRONOME) && c.rarity.equals(CardRarity.UNCOMMON)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
		if (cardsToPullFrom.size() > 0)
		{
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else
		{
			return new CancelCard();
		}	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new UncommonMetronome();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded)
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	//this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }


	









}
