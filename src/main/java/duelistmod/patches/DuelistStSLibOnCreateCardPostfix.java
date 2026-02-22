package duelistmod.patches;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.MagnetCard;
import duelistmod.actions.utility.RetryMagnetReplaceAction;
import duelistmod.helpers.MagnetTransformHelper;

@SpirePatch2(clz = StSLib.class, method = "onCreateCard")
public class DuelistStSLibOnCreateCardPostfix {

    @SpirePostfixPatch
    public static void postfix(AbstractCard c) {
        if (!(c instanceof MagnetCard)) return;
        if (AbstractDungeon.player == null) return;

        MagnetCard original = (MagnetCard) c;
        MagnetCard replacement = MagnetTransformHelper.normalizeIfNeeded(original);
        if (replacement == null || replacement == original) return;

        // Try immediately (works if the card is already in a group)
        boolean replacedNow = MagnetTransformHelper.replaceInAllPlayerGroups(original, replacement);
        MagnetTransformHelper.replaceInCardPopupIfViewing(original, replacement);

        // If it hasn't been inserted into a group yet, try again next tick.
        if (!replacedNow) {
            AbstractDungeon.actionManager.addToBottom(
                    new RetryMagnetReplaceAction(
                            original,
                            replacement,
                            6,      // attempts
                            0.02f,  // initial delay ~ 1 frame
                            1.8f,   // backoff multiplier
                            0.35f   // cap delay
                    )
            );
        }
    }
}
