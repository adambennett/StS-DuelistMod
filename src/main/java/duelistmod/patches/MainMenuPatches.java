package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import duelistmod.*;
import duelistmod.ui.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MainMenuPatches
{
	@SpirePatch(clz = MainMenuScreen.class, method = "updateSettings")
	public static class ConfigCancelButtonPatch {
		public static void Postfix() {
			if (DuelistMod.openedModSettings && DuelistMod.configCancelButton != null) {
				DuelistMod.configCancelButton.update();
			}
		}
	}

	@SpirePatch(clz = OverlayMenu.class, method = "update")
	public static class ConfigOverlayCancelButtonPatch {
		public static void Prefix() {
			if (DuelistMod.openedModSettings && DuelistMod.configCancelButton != null) {
				DuelistMod.configCancelButton.update();
			}
		}
	}

	@SpirePatch(clz = MainMenuScreen.class, method = "setMainMenuButtons")
	public static class AddMenuButtonsPatch {
		public static void Postfix(MainMenuScreen __instance) {
			insertButtonAt(__instance, MainMenuPatchEnums.DUELIST_CONFIG, 2, "DuelistMod");
		}

		private static void insertButtonAt(MainMenuScreen __instance, MenuButton.ClickResult newButton, int index, String label) {
			int originalSize = __instance.buttons.size();
			if (index >= originalSize) {
				__instance.buttons.add(new MenuButton(newButton, index));
				return;
			}
			HashMap<Integer, MenuButton.ClickResult> oldIndices = new HashMap<>();
			for (int i = 0; i < __instance.buttons.size(); i++) {
				MenuButton btn = __instance.buttons.get(i);
				oldIndices.put(i, btn.result);
			}
			ArrayList<MenuButton> newButtons = new ArrayList<>();
			for (int i = 0; i < originalSize + 1; i++) {
				if (i < index) {
					newButtons.add(i, new MenuButton(oldIndices.get(i), i));
				} else if (i == index) {
					newButtons.add(index, new DuelistMainMenuButton(newButton, index, label));
				} else {
					newButtons.add(i, new MenuButton(oldIndices.get(i - 1), i));
				}
			}
			__instance.buttons = newButtons;
		}
	}

}
