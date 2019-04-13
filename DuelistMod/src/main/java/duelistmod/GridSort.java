package duelistmod;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class GridSort 
{
	private static List<Comparator<AbstractCard>> comparators = new ArrayList<>();

	static 
	{
		// alpha sort
		comparators.add((a,b) -> (a.originalName).compareTo(b.originalName)); 
	}

	public static Comparator<AbstractCard> getComparator() 
	{
		Comparator<AbstractCard> retVal = comparators.get(0);
		return retVal;
	}
}
