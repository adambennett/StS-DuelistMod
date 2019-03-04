package defaultmod.actions.unique;

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

import defaultmod.DefaultMod;

@SuppressWarnings("unused")
public class ImperialOrderAction extends AbstractGameAction
{
	private static final float DURATION_PER_CARD = 0.35F;
	private AbstractCard c;
	private static final float PADDING = 25.0F * Settings.scale;
	private boolean isOtherCardInCenter = true;

	public ImperialOrderAction(int amount) 
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
    	for (AbstractCard c : AbstractDungeon.player.hand.group) { if ((c.tags.contains(DefaultMod.TRAP)) || (c.tags.contains(DefaultMod.SPELL))) { modCards.add(c); } }
    	
    	for (AbstractCard c : modCards)
    	{
    		System.out.println("theDuelist:ImperialOrderAction:runAction():before remove ---> modCards card: " + c + " /// Cost: "+ c.costForTurn);
    	}
    	
    	System.out.println("theDuelist:ImperialOrderAction:runAction():between ---> finished looping over modcards before removing any cards");
    	
    	// Remove all 0 cost spells and traps from list
    	if (modCards.size() > 0) { for (int i = 0; i < modCards.size(); i++) { if (modCards.get(i).costForTurn == 0) { System.out.println("theDuelist:ImperialOrderAction:runAction() ---> removed: " + modCards.get(i).name); modCards.remove(i); i = 0;  } } }
    	
    	for (AbstractCard c : modCards)
    	{
    		System.out.println("theDuelist:ImperialOrderAction:runAction():after remove ---> modCards card: " + c + " /// Cost: "+ c.costForTurn);
    	}
    	
    	// For the amount of times equal to power stacks, grab a random card from the remaining list and set cost to 0
    	// Do this until no cards remain in list, or iterations = power stacks
    	for (int i = 0; i < amount; i++)
    	{
    		if (modCards.size() > 0)
    		{
	    		int randomNum = AbstractDungeon.cardRandomRng.random(modCards.size() - 1);
	        	modCards.get(randomNum).setCostForTurn(-9);
	        	modCards.remove(randomNum);
    		}
    	}
    	
    	// Set amount to 0 so update() knows to return
    	amount = 0;
	}
}