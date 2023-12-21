package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

@SuppressWarnings("unused")
public class DragonOrbEvokeAction extends AbstractGameAction
{
	private static final float DURATION_PER_CARD = 0.35F;
	private AbstractCard c;
	private static final float PADDING = 25.0F * Settings.scale;
	private final boolean isOtherCardInCenter = true;
	private final CardTags typeSave;
	private final int costToSet;
	private final AnyDuelist duelist;

	public DragonOrbEvokeAction(int amount) {
		this(amount, Tags.DRAGON, 0, AnyDuelist.from(AbstractDungeon.player));
	}
	
	public DragonOrbEvokeAction(int amount, int costToSet, AnyDuelist duelist) {
		this(amount, Tags.DRAGON, costToSet, duelist);
	}
	
	public DragonOrbEvokeAction(int amount, CardTags type) {
		this(amount, type, 0, AnyDuelist.from(AbstractDungeon.player));
	}
	
	public DragonOrbEvokeAction(int amount, CardTags type, int costSet, AnyDuelist duelist) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.amount = amount;
		this.duration = 0.35F;
		this.typeSave = type;
		this.costToSet = costSet;
		this.duelist = duelist;
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
    	ArrayList<AbstractCard> modCards = new ArrayList<>();
    	
    	// Add all spells and traps from hand to list
    	for (AbstractCard c : this.duelist.hand()) {
			if (c.tags.contains(typeSave) && c.costForTurn != costToSet) {
				modCards.add(c);
			}
		}
    	
    	// For the amount of times equal to power stacks, grab a random card from the remaining list and set cost to 0
    	// Do this until no cards remain in list, or iterations = power stacks
    	for (int i = 0; i < amount; i++) {
    		if (modCards.size() > 0) {
    			int randomNum = AbstractDungeon.cardRandomRng.random(modCards.size() - 1);
	        	modCards.get(randomNum).setCostForTurn(costToSet);
	        	modCards.remove(randomNum);
    		}
    	}
    	this.duelist.handGroup().glowCheck();
    	
    	// Set amount to 0 so update() knows to return
    	amount = 0;
	}
}
