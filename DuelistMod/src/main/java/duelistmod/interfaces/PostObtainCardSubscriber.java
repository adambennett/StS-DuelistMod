package duelistmod.interfaces;

import basemod.interfaces.ISubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface PostObtainCardSubscriber extends ISubscriber {
	void receivePostObtainCard(AbstractCard p);
}