package duelistmod.patches;

import basemod.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import duelistmod.*;
import duelistmod.enums.*;
import duelistmod.ui.*;

public class MainMenuPatches
{
	public static DuelistMenuScreen duelistMenuScreen = null;

    /*@SpirePatch(clz = MainMenuScreen.class, method = "setMainMenuButtons")
    public static class AddMenuButton
    {
        @SpirePostfixPatch
        public static void Postfix(MainMenuScreen __instance)
        {
        	if (DuelistMod.modMode == Mode.DEV) {
				int originalSize = __instance.buttons.size();
				for (int i = 0; i < originalSize; i++) {
					MenuButton btn = __instance.buttons.get(i);
					if (btn.result == MenuButton.ClickResult.ABANDON_RUN || btn.result == MenuButton.ClickResult.PLAY || btn.result == MenuButton.ClickResult.RESUME_GAME) {
						if (btn.result == MenuButton.ClickResult.ABANDON_RUN) {
							__instance.buttons.remove(i + 1);
							MenuButton duelistBtn = new MenuButton(MainMenuPatchEnums.DUELIST_MENU, i);
							MenuButton newAbandon = new MenuButton(MenuButton.ClickResult.ABANDON_RUN, i + 1);
							MenuButton newResume = new MenuButton(MenuButton.ClickResult.RESUME_GAME, i + 2);
							__instance.buttons.set(i, duelistBtn);
							__instance.buttons.add(newAbandon);
							__instance.buttons.add(newResume);
							break;

						} else if (btn.result == MenuButton.ClickResult.PLAY) {
							MenuButton duelistBtn = new MenuButton(MainMenuPatchEnums.DUELIST_MENU, i);
							MenuButton newPlay = new MenuButton(MenuButton.ClickResult.PLAY, i + 1);
							__instance.buttons.set(i, duelistBtn);
							__instance.buttons.add(newPlay);
							break;
						}
					}
				}
			}
        }
    }

    @SpirePatch(clz = MenuButton.class, method = "setLabel")
    public static class SetButtonLabel
    {
        @SpirePostfixPatch
        public static void Postfix(MenuButton __instance)
        {
			if (DuelistMod.modMode == Mode.DEV) {
				if (__instance.result == MainMenuPatchEnums.DUELIST_MENU) {
					ReflectionHacks.setPrivate(__instance, __instance.getClass(), "label", "The Duelist");
				}
			}
        }
    }


	@SpirePatch(clz = MenuButton.class, method = "buttonEffect")
	public static class SetButtonEffect
	{
		@SpirePostfixPatch
		public static void Postfix(MenuButton __instance)
		{
			if (DuelistMod.modMode == Mode.DEV) {
				if (__instance.result == MainMenuPatchEnums.DUELIST_MENU) {
					if (duelistMenuScreen == null) {
						duelistMenuScreen = new DuelistMenuScreen();
					}
					new DuelistMenuScreen().open();
				}
			}
		}
	}

	@SpirePatch(clz = MainMenuScreen.class, method = "update")
	public static class Update
	{
		public static void Postfix(MainMenuScreen __instance)
		{
			if (DuelistMod.modMode == Mode.DEV) {
				if (__instance.screen == MainMenuPatchEnums.DUELIST_SCREEN) {
					duelistMenuScreen.update();
				}
			}
		}
	}

	@SpirePatch(clz = MainMenuScreen.class, method = "render")
	public static class Render
	{
		public static void Postfix(MainMenuScreen __instance, SpriteBatch sb)
		{
			if (DuelistMod.modMode == Mode.DEV) {
				if (__instance.screen == MainMenuPatchEnums.DUELIST_SCREEN) {
					duelistMenuScreen.render(sb);
				}
			}
		}
	}*/

}
