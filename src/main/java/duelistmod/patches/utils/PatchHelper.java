package duelistmod.patches.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.variables.Tags;

public class PatchHelper {

    public static boolean isToken(AbstractCard c)
    {
        return c.hasTag(Tags.TOKEN);
    }

    public static boolean isMonster(AbstractCard c)
    {
        return c.hasTag(Tags.MONSTER);
    }

    public static boolean isSpell(AbstractCard c)
    {
        return c.hasTag(Tags.SPELL);
    }

    public static boolean isTrap(AbstractCard c)
    {
        return c.hasTag(Tags.TRAP);
    }

    public static boolean isArchetype(AbstractCard c)
    {
        return c.hasTag(Tags.ARCHETYPE);
    }

    public static boolean isOrbCard(AbstractCard c)
    {
        return c.hasTag(Tags.ORB_CARD);
    }

    public static boolean isBooster(AbstractCard c)
    {
        return c.hasTag(Tags.BOOSTER);
    }

}
