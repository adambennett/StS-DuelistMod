package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class ForbiddenChalice extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ForbiddenChalice");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ForbiddenChalice.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ForbiddenChalice() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 3;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.NEVER_GENERATE);
        this.makeFleeting();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<String> deckCards = new ArrayList<String>();
    	boolean foundDupe = false;
    	for (AbstractCard c : p.masterDeck.group)
    	{
    		String name = c.makeCopy().name;
    		if (deckCards.contains(name)) { foundDupe = true; Util.log("Forbidden Chalice found DUPLICATE of " + name); }
    		else { deckCards.add(name); Util.log("Forbidden Chalice found only 1 copy so far of " + name); }
    	}
    	if (!foundDupe) 
    	{ 
    		p.increaseMaxHp(this.magicNumber, true);
    		
    		if (!p.hasAnyPotions())
    		{
    			for (int i = 0; i < p.potionSlots; i++)
    			{
    				AbstractPotion pot = AbstractDungeon.returnRandomPotion(PotionRarity.RARE, false);
    				Util.log("Forbidden Chalice generated " + pot.name + " in the loop that indicated you DID HAVE all empty potion slots before playing the card");
    				p.obtainPotion(pot);
    			}
    		}
    		
    		else
    		{
    			int pots = 0;
    			for (AbstractPotion pot : p.potions) { if (!(pot instanceof PotionSlot)) { pots++; }}
    			if (p.potionSlots - pots > 0)
    			{
	    			for (int i = 0; i < p.potionSlots - pots; i++)
	    			{
	    				AbstractPotion pot = AbstractDungeon.returnRandomPotion(PotionRarity.RARE, false);
	    				Util.log("Forbidden Chalice generated " + pot.name + " in the loop that indicated you did not have all empty slots before playing the card");
	    				p.obtainPotion(pot);
	    			}
    			}
    			else
    			{
    				Util.log("No potion slots were open for Forbidden Chalice");
    			}
    		}
    	}
    	else { Util.log("Forbidden Chalice found duplicates of something, so you didn't gain max hp or get some potions. Upgrading a copy of a card doesn't get you around this! NO DUPLICATES"); }
    }
    
    
    @Override
    public void triggerOnGlowCheck()
    {
    	super.triggerOnGlowCheck();
    	ArrayList<String> deckCards = new ArrayList<String>();
    	boolean foundDupe = false;
    	for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
    	{
    		String name = c.makeCopy().name;
    		if (deckCards.contains(name)) { foundDupe = true;  }
    		else { deckCards.add(name);  }
    	}
        if (!foundDupe) {
        	 this.glowColor = Color.GOLD;
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ForbiddenChalice();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }


	









}
