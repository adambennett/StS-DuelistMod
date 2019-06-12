package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import duelistmod.DuelistMod;

@SpirePatch(
        cls="com.megacrit.cardcrawl.actions.GameActionManager",
        method="incrementDiscard"
)
public class IncrementDiscardPatch {

    public static void Postfix(boolean endOfTurn) {
        DuelistMod.incrementDiscardHook(endOfTurn);
    }
}