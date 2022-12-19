package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

public class CardGlowEffectsPatches {
	@SpirePatch(clz = AbstractCard.class, method = "triggerOnGlowCheck")
	public static class CardGlowPatches {
		public static void Postfix(AbstractCard __instance)
		{
			if (!(__instance instanceof DuelistCard) && DuelistMod.currentlyHaunted.contains(__instance)) {
				__instance.glowColor = DuelistMod.hauntedGlowColor;
			}
		}
	}
}
