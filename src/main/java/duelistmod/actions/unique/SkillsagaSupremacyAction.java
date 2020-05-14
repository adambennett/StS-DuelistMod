package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.QueueCardDuelistAction;
import duelistmod.cards.other.tempCards.SupremacyChecker;

public class SkillsagaSupremacyAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int check = 0;
    private AbstractMonster targ = null;

    public SkillsagaSupremacyAction(int amt, AbstractMonster targ)
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        this.check = amt;
        this.targ = targ;
    }

    @Override
    public void update()
    {
    	// Check for >(amt) block
    	// If player has too much block already, this is done
    	if (duration == DURATION && AbstractDungeon.player.currentBlock < this.check) 
        {
    		
    		// If this is not targeting a monster, target a random monster
        	if (targ == null) { targ = AbstractDungeon.getRandomMonster(); }
    		if (targ != null)
    		{
    			// Roll a number 1 - 4 (seeded by cardRng)
    			int roll = AbstractDungeon.cardRandomRng.random(1, 4);
    			
    			// If 1 is rolled:
    			if (roll == 1)
    			{
    				// Get a random Skill that can be Resummoned (looks through the card pool, then ALL Duelist cards if none found)
    				ArrayList<AbstractCard> skills = DuelistCard.findAllOfCardTypeForResummon(CardType.SKILL, 1);
        			
    				// If a Skill is found, Resummon it on the target enemy
    				for (AbstractCard c : skills) { DuelistCard.resummon(c, targ); }
    				
    				// Queue a Block Check card (you will see this token if you watch the Resummon animations)
        			this.addToBot(new QueueCardDuelistAction(new SupremacyChecker(this.check, targ), targ));
    			}
    			
    			// If 2, 3, 4 are rolled:
    			else
    			{
    				// Exactly the same as above, except in this case an additional check is run on the cards
    				// Only cards with a block value > 0 are selected from
    				ArrayList<AbstractCard> skills = DuelistCard.findAllOfCardTypeForResummonWithBlock(CardType.SKILL, 1);
        			for (AbstractCard c : skills) { DuelistCard.resummon(c, targ); }
        			this.addToBot(new QueueCardDuelistAction(new SupremacyChecker(this.check, targ), targ));
    			}    			
    		}
        }
        tickDuration();
    }
}
