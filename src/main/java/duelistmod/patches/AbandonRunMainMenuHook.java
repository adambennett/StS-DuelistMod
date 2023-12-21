package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.options.*;

import duelistmod.DuelistMod;

public class AbandonRunMainMenuHook {
	@SpirePatch(clz = ConfirmPopup.class, method = "abandonRunFromMainMenu")
	public static class AbandonRunMainMenu
	{
		public static void Postfix(ConfirmPopup __instance, AbstractPlayer player)
		{
			DuelistMod.onAbandonRunFromMainMenu(player);
		}
	}
}
