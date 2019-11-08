package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.variables.Tags;

@SuppressWarnings("unused")
public class HauntedReductionAction extends AbstractGameAction
{
	private static final float DURATION_PER_CARD = 0.35F;
	private AbstractCard c;
	private static final float PADDING = 25.0F * Settings.scale;
	private boolean isOtherCardInCenter = true;
	private CardTags hauntTag = Tags.DRAGON;
	private CardType hauntType = CardType.CURSE;

	public HauntedReductionAction(int amount, CardTags tag) 
	{
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.amount = amount;
		this.duration = 0.35F;
		this.hauntTag = tag;
	}
	
	public HauntedReductionAction(int amount, CardType tag) 
	{
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.amount = amount;
		this.duration = 0.35F;
		this.hauntType = tag;
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
    	if (!(this.hauntTag.equals(Tags.DRAGON)))
    	{
    		for (AbstractCard c : AbstractDungeon.player.hand.group) { if ((c.tags.contains(this.hauntTag)) && c.costForTurn != 0) { modCards.add(c); } }
    	}
    	else if (!(this.hauntType.equals(CardType.CURSE)))
    	{
    		for (AbstractCard c : AbstractDungeon.player.hand.group) { if ((c.type.equals(this.hauntType)) && c.costForTurn != 0) { modCards.add(c); } }
    	}
    	
    	// For the amount of times equal to power stacks, grab a random card from the remaining list and set cost to 0
    	// Do this until no cards remain in list, or iterations = power stacks
    	for (AbstractCard trap : modCards)
    	{
    		trap.setCostForTurn(-trap.cost);
    		trap.isCostModifiedForTurn = true;
    		AbstractDungeon.player.hand.glowCheck();
    	}
    	
    	// Set amount to 0 so update() knows to return
    	amount = 0;
	}
}