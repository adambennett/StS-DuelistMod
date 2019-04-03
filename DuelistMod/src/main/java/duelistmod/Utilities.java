package duelistmod;

import com.badlogic.gdx.math.MathUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Utilities
{
    public static final Logger Logger = LogManager.getLogger(Utilities.class.getName());

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
}
