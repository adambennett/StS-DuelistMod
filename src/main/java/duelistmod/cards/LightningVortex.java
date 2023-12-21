package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class LightningVortex extends DuelistCard {
    public static final String ID = DuelistMod.makeID("LightningVortex");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("LightningVortex.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    public LightningVortex() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
		this.tags.add(Tags.X_COST);
        this.baseDamage = this.damage = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	attack(m);
    	int tokens = xCostTribute();
	    	
    	if (tokens > 0) {
	    	// Find monster of that many tributes, or the next highest available if there are none of that tribute cost
	    	int highestTrib = -1;																// Keeps track of highest tribute cost that exists that is less than the number of aquas summoned
	    	ArrayList<DuelistCard> possibleTributeMonsters = new ArrayList<>();		// All tribute monsters that match either the number of aquas summoned or the highest trib value after the first iteration
	    	ArrayList<DuelistCard> allTributeMonsters = new ArrayList<>();			// Every tribute monster that exists, for last ditch effort to just resummon completely randomly if the other logic fails somehow
	    	
	    	// Loop through duelist cards, figure out if there are any monsters with tribute cost = aquas summoned
	    	// Simultaneously check to see what the next highest tribute cost that exists for any monster is (so if we are looking for ex: 7 tribute cost and there are no 7 tribute monsters, we look until we discover that there are 5 tribute monsters, so we save 5 as the next highest
	    	for (DuelistCard c : DuelistMod.myCards) {
	    		// Only look at monsters
	    		if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.NOT_ADDED) && DuelistCard.allowResummonsWithExtraChecks(c)) {
	    			// Only look at monsters with tribute costs
	    			if (c.isTributeCard()) {
	    				// If tribute cost = aquas summoned, save to list
	    				if (c.baseTributes == tokens) { possibleTributeMonsters.add((DuelistCard) c.makeStatEquivalentCopy()); }
	    				
	    				// Otherwise, check to see if we want to update the next highest tributes value
	    				else if (c.baseTributes < tokens && c.baseTributes > highestTrib) { highestTrib = c.baseTributes; } 
	    				
	    				// Add all monsters with tribute cost to second list
	    				allTributeMonsters.add((DuelistCard) c.makeStatEquivalentCopy());
	    			}
	    		}
	    	}
	    	
	    	// Now that we have checked for all tribute monsters with tribute cost = aquas summoned, lets see if there are any
	    	if (possibleTributeMonsters.size() > 0) {
				DuelistCard randomChoice = possibleTributeMonsters.size() == 1
						? possibleTributeMonsters.get(0)
						: possibleTributeMonsters.get(AbstractDungeon.cardRandomRng.random(possibleTributeMonsters.size() - 1));
	    		DuelistCard.resummon(randomChoice, m, false, this.upgraded);
	    	}
	    	
	    	// If not, loop through again, and save all tribute monsters with the previously found tribute cost (that we know exists for at least 1 monster)
	    	else {
				if (highestTrib > -1) {
					for (DuelistCard c : DuelistMod.myCards) {
						if (c.hasTag(Tags.MONSTER)) {
							if (c.isTributeCard()) {
								if (c.baseTributes == highestTrib) {
									possibleTributeMonsters.add((DuelistCard) c.makeStatEquivalentCopy());
								}
							}
						}
					}
				}

	    		if (possibleTributeMonsters.size() > 0) {
	    			DuelistCard randomChoice = possibleTributeMonsters.get(AbstractDungeon.cardRandomRng.random(possibleTributeMonsters.size() - 1));
		    		DuelistCard.resummon(randomChoice, m, false, this.upgraded);
	    		} else if (allTributeMonsters.size() > 0) {
					DuelistCard randomChoice = allTributeMonsters.size() == 1
							? allTributeMonsters.get(0)
							: allTributeMonsters.get(AbstractDungeon.cardRandomRng.random(allTributeMonsters.size() - 1));
		    		DuelistCard.resummon(randomChoice, m, false, this.upgraded);
	    		}
	    	}
	    	// END Find monster
    	}
    }

    @Override
    public AbstractCard makeCopy() {
        return new LightningVortex();
    }
    
    @Override
	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(5);			 
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
