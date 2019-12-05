package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class TsunamiHelperAction extends AbstractGameAction 
{
	private final ArrayList<AbstractCard> handRef;
	public TsunamiHelperAction(ArrayList<AbstractCard> originalHand) 
	{
		this.setValues(this.target, this.source, this.amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.handRef = originalHand;
	}
	
	@Override
	public void update() 
	{
		Map<UUID, AbstractCard> map = new HashMap<>();
		for (AbstractCard c : handRef) { map.put(c.uuid, c); }
		for (AbstractCard c : AbstractDungeon.player.hand.group) 
		{ 
			if (!map.containsKey(c.uuid) && c instanceof DuelistCard)
			{
				DuelistCard dc = (DuelistCard)c;
				if (dc.checkMagicNum() > 0 && dc.hasTag(Tags.IS_OVERFLOW))
				{
					dc.triggerOverflowEffect();
					Util.log("Tsunami should be triggering the overflow effect for " + dc.name);
				}
				else
				{
					Util.log("Tsunami found that " + dc.name + " was drawn by the effect, but it had no overflows remaining or it was not an overflow card");
				}
			}
			else
			{
				Util.log("Tsunami found a card that was not a DuelistCard or it was already in the players hand before drawing cards");
			}
		}
		this.isDone = true;
	}
	
}
