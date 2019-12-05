package duelistmod.abstracts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;

public abstract class DuelistEvent extends AbstractImageEvent
{

	public DuelistEvent(String title, String body, String imgUrl) {
		super(title, body, imgUrl);
	}
	
	protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

}
