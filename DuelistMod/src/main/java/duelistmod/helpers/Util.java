package duelistmod.helpers;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.relics.*;

public class Util
{
    public static final Logger Logger = LogManager.getLogger(Util.class.getName());
    
    public static void log()
    {
    	log("Generic Debug Statement");
    }
    
    public static void log(String s)
    {
    	if (DuelistMod.debug)
    	{
    		DuelistMod.logger.info(s);
    	}
    }
    
    public static boolean isCustomModActive(String ID) {
        return (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ID)) || ModHelper.isModEnabled(ID);
    }
    
    public static int factorial(int n) 
    {
    	if (n > 19) { n = 19; }
    	DuelistMod.logger.info("Factorial iteration value: " + n);
    	return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);
    } 

    public static <T> T SafeCast(Object o, Class<T> type)
    {
        return type.isInstance(o) ? type.cast(o) : null;
    }

    public static <T> T GetRandomElement(ArrayList<T> list, com.megacrit.cardcrawl.random.Random rng)
    {
        int size = list.size();
        if (size > 0)
        {
            return list.get(rng.random(list.size() - 1));
        }

        return null;
    }

    public static <T> T GetRandomElement(ArrayList<T> list)
    {
        int size = list.size();
        if (size > 0)
        {
            return list.get(MathUtils.random(list.size() - 1));
        }

        return null;
    }

    public static <T> ArrayList<T> Where(ArrayList<T> list, Predicate<T> predicate)
    {
        ArrayList<T> res = new ArrayList<>();
        for (T t : list)
        {
            if (predicate.test(t))
            {
                res.add(t);
            }
        }

        return res;
    }

	public static String titleCase(String text) {
	    if (text == null || text.isEmpty()) {
	        return text;
	    }
	
	    StringBuilder converted = new StringBuilder();
	
	    boolean convertNext = true;
	    for (char ch : text.toCharArray()) {
	        if (Character.isSpaceChar(ch)) {
	            convertNext = true;
	        } else if (convertNext) {
	            ch = Character.toTitleCase(ch);
	            convertNext = false;
	        } else {
	            ch = Character.toLowerCase(ch);
	        }
	        converted.append(ch);
	    }
	
	    return converted.toString();
	}
	
	public static boolean isMillenniumItem(AbstractRelic r, boolean includePuzzle)
	{
		ArrayList<String> items = new ArrayList<String>();
		items.add(new MillenniumCoin().name);
		items.add(new MillenniumRing().name);
		items.add(new MillenniumRod().name);
		items.add(new MillenniumKey().name);
		items.add(new MillenniumEye().name);
		items.add(new ResummonBranch().name);
		items.add(new MillenniumScale().name);
		items.add(new MillenniumNecklace().name);
		items.add(new MillenniumToken().name);
		if (includePuzzle) { items.add(new MillenniumPuzzle().name); }
		if (items.contains(r.name)) { return true; }
		else { return false; }		
	}
	
	public static ArrayList<AbstractRelic> getAllMillenniumItems(boolean includePuzzle)
	{
		ArrayList<AbstractRelic> items = new ArrayList<AbstractRelic>();
		items.add(new MillenniumCoin());
		items.add(new MillenniumRing());
		items.add(new MillenniumRod());
		items.add(new MillenniumKey());
		items.add(new MillenniumEye());
		items.add(new ResummonBranch());
		items.add(new MillenniumScale());
		items.add(new MillenniumNecklace());
		items.add(new MillenniumToken());
		if (includePuzzle) { items.add(new MillenniumPuzzle()); }
		return items;
	}

	public static AbstractRelic getRandomMillenniumItem()
	{
		ArrayList<AbstractRelic> items = new ArrayList<AbstractRelic>();
		items.add(new MillenniumCoin());
		items.add(new MillenniumRing());
		items.add(new MillenniumRod());
		items.add(new MillenniumKey());
		items.add(new MillenniumEye());
		items.add(new ResummonBranch());
		items.add(new MillenniumScale());
		items.add(new MillenniumNecklace());
		items.add(new MillenniumToken());
		return items.get(AbstractDungeon.relicRng.random(items.size() - 1));
	}
	
}
