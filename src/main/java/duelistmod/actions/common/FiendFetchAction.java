package duelistmod.actions.common;

import java.util.List;
import java.util.function.*;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FiendFetchAction extends FiendMoveCardsAction
{
    public FiendFetchAction(CardGroup source, Predicate<AbstractCard> predicate, int amount, Consumer<List<AbstractCard>> callback)
    {
        super(AbstractDungeon.player.hand, source, predicate, amount, callback);
    }

    public FiendFetchAction(CardGroup source, Predicate<AbstractCard> predicate, Consumer<List<AbstractCard>> callback)
    {
        this(source, predicate, 1, callback);
    }

    public FiendFetchAction(CardGroup source, int amount, Consumer<List<AbstractCard>> callback)
    {
        this(source, c -> true, amount, callback);
    }

    public FiendFetchAction(CardGroup source, Consumer<List<AbstractCard>> callback)
    {
        this(source, c -> true, 1, callback);
    }

    public FiendFetchAction(CardGroup source, Predicate<AbstractCard> predicate, int amount)
    {
        this(source, predicate, amount, null);
    }

    public FiendFetchAction(CardGroup source, Predicate<AbstractCard> predicate)
    {
        this(source, predicate, 1);
    }

    public FiendFetchAction(CardGroup source, int amount)
    {
        this(source, c -> true, amount);
    }

    public FiendFetchAction(CardGroup source)
    {
        this(source, c -> true, 1);
    }
}