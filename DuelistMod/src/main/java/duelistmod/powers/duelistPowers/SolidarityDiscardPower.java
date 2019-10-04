package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;

public class SolidarityDiscardPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SolidarityDiscardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("SolidarityDiscardPower.png");
	
    private CardTags currentType = Tags.ALL;		// Tags.ALL is a null flag in this context
    private boolean noTyped = false;
    
	public SolidarityDiscardPower() 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = 4;
		updateDescription(); 
	}

	// This is a bad idea so I took a lot of precautions to try to exit early from this as often as possible
	// Handling:
		// No cards in discard
		// No monsters in discard
		// No typed monsters in discard
		// Many types of monsters in discard
		// Two monsters with different types at opposite ends of discard
	public CardTags solidarity()
	{
		// If empty discard, return
		if (AbstractDungeon.player.discardPile.group.size() < 1) { updateDescription(); this.noTyped = true; return Tags.ALL; }
		
		// Otherwise setup vars for later
		ArrayList<DuelistCard> typedMonsters = new ArrayList<DuelistCard>();
		int counter = 0;
		CardTags theOneTypeToRuleThemAll = Tags.ALL;
		
		// One initial loop through discard to check for typed monsters
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) { if (c.hasTag(Tags.MONSTER) && c instanceof DuelistCard) { typedMonsters.add((DuelistCard) c); }}
		
		// If we find one monster with a type, and it has only one type, lets simply return that type
		
		// If we find multiple monsters with types, gotta find out whats what (unfortunately)
		if (typedMonsters.size() > 0)
		{
			// Check each typed monster against all monster types
			for (CardTags type : DuelistMod.monsterTypes)
			{
				for (DuelistCard c : typedMonsters)
				{
					// If we find the first match on a type, lets get out of this type loop and check another type
					// Keep track that we found a match, and what the match was in case this is the only match
					if (c.hasTag(type) && counter == 0) { theOneTypeToRuleThemAll = type; counter++; break; }
					
					// If we then find another match, we are done, this is too much types for anyone to type
					else if (c.hasTag(type)) { counter++; break; }
				}
				
				// We were done early here if counter is >1, so we skip the rest of the monster types
				if (counter > 1) { break; }
			}
			
			// Update power description and fields
			if (counter == 1) { this.currentType = theOneTypeToRuleThemAll; }
			else { this.currentType = Tags.ALL; }
			updateDescription();
			
			// Return the proper result depending on matches
			// Tags.ALL is basically used as a boolean 'FALSE' since thats not a monster type
			if (counter > 1) { this.noTyped = false; return Tags.ALL; }
			else if (counter == 1) { this.noTyped = false; return theOneTypeToRuleThemAll; }
			else { this.noTyped = true; return Tags.ALL; }
		}
		else { updateDescription(); this.noTyped = true; return Tags.ALL; }
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		if (!this.currentType.equals(Tags.ALL)) { this.description = DESCRIPTIONS[1] + DuelistMod.typeCardMap_NAME.get(this.currentType) + DESCRIPTIONS[2] + (this.amount * 10) + DESCRIPTIONS[3]; }
		else if (!noTyped) { this.description = DESCRIPTIONS[0]; }
		else if (noTyped) { this.description = DESCRIPTIONS[4]; }
		
	}

}
