package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.options.AbandonConfirmPopup;

import duelistmod.DuelistMod;

public class AbandonRunMainMenuHook {
	@SpirePatch(clz = AbandonConfirmPopup.class, method = "effect")
	public static class AbandonRunMainMenu
	{
		public static void Postfix()
		{
			DuelistMod.onAbandonRunFromMainMenu();
		}
	}
}
