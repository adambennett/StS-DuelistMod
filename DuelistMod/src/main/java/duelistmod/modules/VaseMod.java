package duelistmod.modules;

import static basemod.BaseMod.logger;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.interfaces.ISubscriber;
import duelistmod.interfaces.PostObtainCardSubscriber;

// Parody of BaseMod; implements some fixed/missing interfaces.
// Copied from the Gatherer
@SpireInitializer
public class VaseMod 
{
	private static ArrayList<PostObtainCardSubscriber> postObtainCardSubscribers = new ArrayList();

	public static void publishPostObtainCard(AbstractCard c) {
		logger.info("publish on post obtain card");
		Iterator var1 = postObtainCardSubscribers.iterator();

		while (var1.hasNext()) {
			PostObtainCardSubscriber sub = (PostObtainCardSubscriber) var1.next();
			sub.receivePostObtainCard(c);
		}
	}

	// not actually unchecked because we do an isInstance check at runtime
	@SuppressWarnings("unchecked")
	private static <T> void subscribeIfInstance(ArrayList<T> list, ISubscriber sub, Class<T> clazz) {
		if (clazz.isInstance(sub)) {
			list.add((T) sub);
		}
	}

	public static void subscribe(ISubscriber sub) {
		subscribeIfInstance(postObtainCardSubscribers, sub, PostObtainCardSubscriber.class);
	}
}
