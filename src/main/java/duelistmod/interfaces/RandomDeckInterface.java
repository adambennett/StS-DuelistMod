package duelistmod.interfaces;

import com.megacrit.cardcrawl.cards.*;

import java.util.*;

@FunctionalInterface
public interface RandomDeckInterface {
	ArrayList<AbstractCard> getDeck();
}
