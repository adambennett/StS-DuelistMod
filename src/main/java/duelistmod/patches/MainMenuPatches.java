package duelistmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import duelistmod.*;
import duelistmod.enums.MainMenuPatchEnums;
import duelistmod.ui.*;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

import static duelistmod.enums.MainMenuPatchEnums.DUELIST_PANEL_MENU;

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

	@SpirePatch(clz = MenuPanelScreen.class, method = "refresh")
	public static class MenuRefreshPatch {
		public static SpireReturn<Void> Prefix() {
			if (DuelistMod.mainMenuPanelScreen.isShowing) {
				DuelistMod.mainMenuPanelScreen.refresh();
				return SpireReturn.Return();
			}
			return SpireReturn.Continue();
		}
	}

	@SpirePatch(clz = MainMenuScreen.class, method = "update")
	public static class MainMenuUpdatePatches {
		@SpireInsertPatch(locator = Locator.class)
		public static SpireReturn<Void> Insert(MainMenuScreen __instance) {
			if (CardCrawlGame.mainMenuScreen.screen == DUELIST_PANEL_MENU && DuelistMod.mainMenuPanelScreen.isShowing) {
				DuelistMod.mainMenuPanelScreen.updateMenuPanelController();
				DuelistMod.mainMenuPanelScreen.update();
			}
			return SpireReturn.Continue();
		}

		private static class Locator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws Exception
			{
				Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen", "saveSlotScreen");
				return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
			}
		}
	}

	@SpirePatch(clz = MainMenuScreen.class, method = "render")
	public static class MainMenuRenderPatches {
		@SpireInsertPatch(locator = Locator.class)
		public static SpireReturn<Void> Insert(MainMenuScreen __instance, SpriteBatch sb) {
			if (CardCrawlGame.mainMenuScreen.screen == DUELIST_PANEL_MENU) {
				if (CardCrawlGame.displayVersion) {
					FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, CardCrawlGame.VERSION_NUM, 20.0f * Settings.scale - 700.0f * __instance.bg.slider, 30.0f * Settings.scale, 10000.0f, 32.0f * Settings.scale, new Color(1.0f, 1.0f, 1.0f, 0.3f));
				}
				DuelistMod.mainMenuPanelScreen.render(sb);
				__instance.saveSlotScreen.render(sb);
				__instance.syncMessage.render(sb);
				return SpireReturn.Return();
			}
			return SpireReturn.Continue();
		}

		private static class Locator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws Exception
			{
				Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen", "confirmButton");
				int[] tmp = LineFinder.findAllInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
				return tmp.length > 1 ? new int[] { tmp[1]} : new int[]{};
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
			insertButtonAt(__instance, MainMenuPatchEnums.DUELIST_MENU, 2, "DuelistMod");
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

	@SpirePatch(clz = AbstractOrb.class, method = "update")
	public static class HideOrbTooltipsWhenConfigOpenPatch {
		public static SpireReturn<Void> Prefix(AbstractOrb __instance) {
			if (DuelistMod.openedModSettings) {
				__instance.hb.update();
				float fs = ReflectionHacks.getPrivate(__instance, AbstractOrb.class, "fontScale");
				float newVal = MathHelper.scaleLerpSnap(fs, 0.7f);
				ReflectionHacks.setPrivate(__instance, AbstractOrb.class, "fontScale", newVal);
				return SpireReturn.Return();
			}
			return SpireReturn.Continue();
		}
	}

	@SpirePatch(clz = SeedPanel.class, method = "show", paramtypez = {})
	public static class ShowSeedPanelPatch {
		public static SpireReturn<Void> Prefix() {
			if (DuelistMod.openedModSettings) {
				return SpireReturn.Return();
			}
			DuelistMod.seedPanelOpen = true;
			return SpireReturn.Continue();
		}
	}

	@SpirePatch(clz = SeedPanel.class, method = "close")
	public static class CloseSeedPanelPatch {
		public static void Postfix() {
			DuelistMod.seedPanelOpen = false;
		}
	}

	@SpirePatch(clz = CharacterSelectScreen.class, method = "renderSeedSettings")
	public static class HideSeedButtonPatch {
		public static SpireReturn<Void> Prefix() {
			if (DuelistMod.openedModSettings) {
				return SpireReturn.Return();
			}
			return SpireReturn.Continue();
		}
	}

	@SpirePatch(clz = CharacterSelectScreen.class, method = "renderAscensionMode")
	public static class HideAscensionModePatch {
		public static SpireReturn<Void> Prefix() {
			if (DuelistMod.openedModSettings) {
				return SpireReturn.Return();
			}
			return SpireReturn.Continue();
		}
	}

	@SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
	public static class CharacterSelectButtonPatch {
		public static SpireReturn<Void> Prefix() {
			if (DuelistMod.openedModSettings) {
				return SpireReturn.Return();
			}
			return SpireReturn.Continue();
		}
	}

	@SpirePatch(clz = CharacterOption.class, method = "renderRelics")
	public static class SkipRelicRenderingWithConfigOpenPatch {
		public static SpireReturn<Void> Prefix() {
			if (DuelistMod.openedModSettings) {
				return SpireReturn.Return();
			}
			return SpireReturn.Continue();
		}
	}
}
