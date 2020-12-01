package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.options.*;

import duelistmod.DuelistMod;

public class AbandonRunMainMenuHook {
	@SpirePatch(clz = ConfirmPopup.class, method = "abandonRunFromMainMenu")
	public static class AbandonRunMainMenu
	{
		public static void Postfix()
		{
			DuelistMod.onAbandonRunFromMainMenu();
		}
	}
}
