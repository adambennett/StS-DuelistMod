package duelistmod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GridSort {
	private static final List<Comparator<AbstractCard>> comparators = new ArrayList<>();

	static {
		comparators.add(Comparator.comparing(a -> (a.originalName)));
	}

	public static Comparator<AbstractCard> getComparator() {
        return comparators.get(0);
	}
}
