package duelistmod.cards.pools.zombies;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.enums.StartingDeck;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class CallMummy extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CallMummy");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CallMummy.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public CallMummy() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 1;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		// Metronome Deck
		if (StartingDeck.currentDeck == StartingDeck.METRONOME) {
			ArrayList<AbstractCard> zombs = DuelistCard.findAllOfTypeForCallMummy(Tags.ZOMBIE, 20);
			int roll = AbstractDungeon.cardRandomRng.random(3, 6);
			for (int i = 0; i < roll; i++) {
				runResummonLogic(zombs);
			}
		}

		// Otherwise
		else {
			int mult = 100;
			if (DuelistMod.bookEclipseThisCombat) { mult = 3; }
			int loopMax = DuelistMod.currentZombieSouls * mult;
			if (loopMax > 999) loopMax = 999;
			ArrayList<AbstractCard> zombs = DuelistCard.findAllOfTypeForCallMummy(Tags.ZOMBIE, 20);
			if (zombs.size() > 0)
			{
				while (DuelistMod.currentZombieSouls > 0 && loopMax > 0)
				{
					runResummonLogic(zombs);
					loopMax--;
				}
			}
		}
    }

	private void runResummonLogic(ArrayList<AbstractCard> zombs) {
		if (zombs.size() < 1) return;
		AbstractCard randZomb = zombs.get(AbstractDungeon.cardRandomRng.random(zombs.size() - 1));
		AbstractMonster rand = AbstractDungeon.getRandomMonster();
		if (rand != null) resummon(randZomb, rand);
	}

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new CallMummy();
    }

    @Override
    public void upgrade() {
        if (!upgraded) 
        {
        	// Name
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.selfRetain = true;
        	this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }        
    }
    













   
}
