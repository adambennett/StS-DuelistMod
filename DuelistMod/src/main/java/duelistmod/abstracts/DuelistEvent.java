package duelistmod.abstracts;

import com.megacrit.cardcrawl.events.AbstractImageEvent;

public abstract class DuelistEvent extends AbstractImageEvent
{

	public DuelistEvent(String title, String body, String imgUrl) {
		super(title, body, imgUrl);
	}

}
