package duelistmod.actions.unique;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import duelistmod.variables.Tags;

@SuppressWarnings("unused")
public class JinzoAction extends AbstractGameAction
{
	private static final float DURATION_PER_CARD = 0.35F;
	private AbstractCard c;
	private static final float PADDING = 25.0F * Settings.scale;
	private boolean isOtherCardInCenter = true;

	public JinzoAction(int amount) 
	{
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.amount = amount;
		this.duration = 0.35F;
	}

	@Override
	public void update() 
	{
		if (this.amount == 0) {
			this.isDone = true;
			return;
		}

		runAction();
		
		if (this.amount > 0) {
			AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
		}

		this.isDone = true;

	}
	
	public void runAction()
	{
		// Create empty list of cards
    	ArrayList<AbstractCard> modCards = new ArrayList<AbstractCard>();
    	
    	// Add all spells and traps from hand to list
    	for (AbstractCard c : AbstractDungeon.player.hand.group) { if ((c.tags.contains(Tags.TRAP))) { modCards.add(c); } }
    	
    	// Remove all 0 cost spells and traps from list
    	if (modCards.size() > 0) { for (int i = 0; i < modCards.size(); i++) { if (modCards.get(i).cost == 0) { modCards.remove(i); } } }
    	
    	// For the amount of times equal to power stacks, grab a random card from the remaining list and set cost to 0
    	// Do this until no cards remain in list, or iterations = power stacks
    	for (AbstractCard trap : modCards)
    	{
    		trap.setCostForTurn(-trap.cost);
    		trap.isCostModifiedForTurn = true;
    	}
    	
    	// Set amount to 0 so update() knows to return
    	amount = 0;
	}
}