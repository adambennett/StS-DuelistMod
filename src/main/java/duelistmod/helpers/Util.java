package duelistmod.helpers;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.PatternSyntaxException;

import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.eventUtil.util.Condition;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.map.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.shop.*;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.CardPoolRelicFilter;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.ExplodingTokenDamageResult;
import duelistmod.dto.OrbConfigData;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.dto.RandomizedOptions;
import duelistmod.dto.TwoNums;
import duelistmod.enums.ConfigOpenSource;
import duelistmod.enums.MonsterType;
import duelistmod.enums.StartingDeck;
import duelistmod.enums.VinesLeavesMod;
import duelistmod.events.AknamkanonTomb;
import duelistmod.events.BattleCity;
import duelistmod.events.CardTrader;
import duelistmod.events.EgyptVillage;
import duelistmod.events.MillenniumItems;
import duelistmod.events.RelicDuplicator;
import duelistmod.events.TombNameless;
import duelistmod.events.TombNamelessPuzzle;
import duelistmod.events.VisitFromAnubis;
import duelistmod.interfaces.BoosterRewardRelic;
import duelistmod.interfaces.CardRewardRelic;
import duelistmod.interfaces.InfiniteLoopTributeModificationCheckCard;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.interfaces.NamelessTombCard;
import duelistmod.interfaces.ShopDupeRelic;
import duelistmod.patches.ExceptionHandlerPatch;
import duelistmod.patches.MainMenuPatchEnums;
import duelistmod.patches.ShopScreenPatches;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.persistence.data.GeneralSettings;
import duelistmod.ui.CharacterSelectHelper;
import duelistmod.ui.GenericCancelButton;
import duelistmod.ui.configMenu.pages.ColorlessShop;
import duelistmod.variables.Strings;
import org.apache.logging.log4j.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import basemod.*;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.cards.holiday.birthday.*;
import duelistmod.cards.holiday.christmas.*;
import duelistmod.cards.holiday.fourtwenty.WeedOut;
import duelistmod.cards.holiday.halloween.*;
import duelistmod.cards.incomplete.HourglassLife;
import duelistmod.cards.nameless.greed.*;
import duelistmod.cards.nameless.magic.*;
import duelistmod.cards.nameless.power.*;
import duelistmod.cards.nameless.war.*;
import duelistmod.cards.other.bookOfLifeOptions.CustomResummonCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.cards.other.tokens.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.LichLord;
import duelistmod.characters.TheDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.powers.enemyPowers.*;
import duelistmod.powers.incomplete.*;
import duelistmod.relics.*;
import duelistmod.relics.ElectricToken;
import duelistmod.relics.MachineToken;
import duelistmod.relics.SpellcasterToken;
import duelistmod.variables.Tags;

import static duelistmod.variables.Tags.FERAL;
import static duelistmod.variables.Tags.TERRITORIAL;

public class Util
{
    public static final Logger Logger = LogManager.getLogger(Util.class.getName());
	private static String lastLogMessage = null;

    public static void log()
    {
    	log("Generic Debug Statement");
    }
    
    public static void log(String s)
    {
    	log(s, false);
    }
    
    public static void log(String s, boolean toDevConsole)
    {
		if (lastLogMessage != null && lastLogMessage.equals(s)) {
			return;
		}
		lastLogMessage = s;
		if (DuelistMod.debug) {
			DuelistMod.logger.info(s);
			if (toDevConsole) {
				DevConsole.log(s);
			}
		}
    }

	public static void logError(String message, Exception ex) {
		logError(message, ex, false);
	}

	public static void logError(String message, Exception ex, boolean sendToServer) {
		StringBuilder st  = ex != null ? new StringBuilder(ex.getMessage() + "\nStack Trace:\n") : new StringBuilder();
		if (ex != null) {
			for (StackTraceElement e : ex.getStackTrace()) {
				st.append(e.toString()).append("\n");
			}
		}
		st = st.toString().isEmpty() ? st : new StringBuilder(st + "\n\n");
		log(message + "\n" + st, false);
		if (sendToServer) {
			ExceptionHandlerPatch.HandlerPatches.sendExceptionRequestToServer(ex, message);
		}
	}

	public static void addDuelistScore(int amount, boolean trueScore) {
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
			config.load();
			int duelistScore = config.getInt("duelistScore");
			int newScore = duelistScore + amount;
			config.setInt("duelistScore", newScore);
			DuelistMod.duelistScore = newScore;
			if (trueScore) {
				int trueDuelistScore = config.getInt("trueDuelistScore");
				int trueVersionScore = config.getInt("trueDuelistScore" + DuelistMod.trueVersion);
				int newTrueScore = trueDuelistScore + amount;
				int newVersionScore = trueVersionScore + amount;
				config.setInt("trueDuelistScore", newTrueScore);
				config.setInt("trueDuelistScore" + DuelistMod.trueVersion, newVersionScore);
				PuzzleConfigData deckConfig = StartingDeck.currentDeck.getActiveConfig();
				int oldDeckScore = deckConfig.getStats().getScore();
				deckConfig.getStats().setScore(oldDeckScore + amount);
				StartingDeck.currentDeck.updateConfigSettings(deckConfig);
				DuelistMod.trueDuelistScore = newTrueScore;
				DuelistMod.trueVersionScore = newVersionScore;
			}
			config.save();
		} catch(IOException ex) {
			Util.logError("Did not update duelistScore due to IOException", ex, true);
		}
	}

	public static void updateCharacterSelectScreenPuzzleDescription() {
		if (BaseMod.getAllCustomRelics() == null) return;

		try {
			AbstractRelic r = BaseMod.getCustomRelic(MillenniumPuzzle.ID);
			if (r instanceof MillenniumPuzzle) {
				MillenniumPuzzle puzzle = (MillenniumPuzzle) r;
				puzzle.getDeckDesc();
			}
		} catch (Exception ex) {
			Util.logError("Exception while attempting to update the Millennium Puzzle description on the character select screen", ex);
		}

		try {
			AbstractRelic r = BaseMod.getCustomRelic(ChallengePuzzle.ID);
			if (r instanceof ChallengePuzzle) {
				ChallengePuzzle puzzle = (ChallengePuzzle) r;
				puzzle.getDesc("");
			}
		} catch (Exception ex) {
			Util.logError("Exception while attempting to update the Challenge Puzzle description on the character select screen", ex);
		}
	}

	private static GenericCancelButton configCancelButton(ConfigOpenSource source) {
		return new GenericCancelButton(() -> {
			DuelistMod.settingsPanel.isUp = false;
			DuelistMod.settingsPanel.currentSource = ConfigOpenSource.BASE_MOD;
			DuelistMod.openedModSettings = false;
			DuelistMod.paginator.resetToPageOne();
			DuelistMod.lastSource = ConfigOpenSource.BASE_MOD;
			if (source == ConfigOpenSource.CHARACTER_SELECT) {
				DuelistMod.characterSelectScreen.cancelButton.show(CharacterSelectScreen.TEXT[5]);
				DuelistMod.characterSelectScreen.confirmButton.show();
			} else if (source == ConfigOpenSource.MID_RUN) {
				AbstractDungeon.isScreenUp = false;
				AbstractDungeon.screen = DuelistMod.settingsPanel.lastScreen;
				if (DuelistMod.settingsPanel.blackScreenShown) {
					AbstractDungeon.overlayMenu.hideBlackScreen();
					DuelistMod.settingsPanel.blackScreenShown = false;
				}
				if (DuelistMod.settingsPanel.combatPanelsHidden) {
					AbstractDungeon.overlayMenu.showCombatPanels();
					DuelistMod.settingsPanel.combatPanelsHidden = false;
				}
				if (DuelistMod.settingsPanel.proceedButtonHidden) {
					AbstractDungeon.overlayMenu.proceedButton.show();
					DuelistMod.settingsPanel.proceedButtonHidden = false;
				}
			} else if (source == ConfigOpenSource.MAIN_MENU) {
				CardCrawlGame.mainMenuScreen.lighten();
				CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
			}
		});
	}

	private static boolean roomAllowedToOpenConfig(ConfigOpenSource source) {
		if (source != ConfigOpenSource.MID_RUN) {
			return true;
		}
		AbstractRoom room = AbstractDungeon.getCurrRoom();
		if (room instanceof TreasureRoomBoss) {
			return false;
		}
		if (room instanceof EventRoom) {
			return false;
		}
		return true;
	}

	public static boolean canOpenModSettings(ConfigOpenSource source) {
		if (DuelistMod.settingsPanel != null) {
			return roomAllowedToOpenConfig(source) && (source != ConfigOpenSource.MID_RUN || (!AbstractDungeon.isScreenUp && !(AbstractRoom.waitTimer > 0.0f)));
		}
		return false;
	}

	public static void openModSettings(ConfigOpenSource source) {
		if (!canOpenModSettings(source)) {
			return;
		}

		if (!DuelistMod.openedModSettings) {
			DuelistMod.configCancelButton = configCancelButton(source);
			DuelistMod.configCancelButton.show("Close");
			DuelistMod.settingsPanel.isUp = true;
			DuelistMod.openedModSettings = true;
			DuelistMod.lastSource = source;
			if (source == ConfigOpenSource.CHARACTER_SELECT) {
				DuelistMod.characterSelectScreen.cancelButton.hide();
				DuelistMod.characterSelectScreen.confirmButton.hide();
			} else if (source == ConfigOpenSource.MID_RUN) {
				AbstractDungeon.player.releaseCard();
				AbstractDungeon.isScreenUp = true;
				DuelistMod.settingsPanel.lastScreen = AbstractDungeon.screen;
				AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NO_INTERACT;
				if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
					DuelistMod.settingsPanel.isSomethingSelectedRestRoom = ((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI.somethingSelected;
				}
				boolean isProceedHidden = ReflectionHacks.getPrivate(AbstractDungeon.overlayMenu.proceedButton, ProceedButton.class, "isHidden");
				if (!isProceedHidden) {
					if (AbstractDungeon.overlayMenu != null && AbstractDungeon.overlayMenu.proceedButton != null) {
						AbstractDungeon.overlayMenu.proceedButton.hide();
					}
					DuelistMod.settingsPanel.proceedButtonHidden = true;
				}
				float blackScreenCheck = ReflectionHacks.getPrivate(AbstractDungeon.overlayMenu, OverlayMenu.class, "blackScreenTarget");
				if (blackScreenCheck == 0) {
					AbstractDungeon.overlayMenu.showBlackScreen();
					DuelistMod.settingsPanel.blackScreenShown = true;
				}
				if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
					AbstractDungeon.overlayMenu.hideCombatPanels();
					DuelistMod.settingsPanel.combatPanelsHidden = true;
				}
			} else if (source == ConfigOpenSource.MAIN_MENU) {
				CardCrawlGame.mainMenuScreen.darken();
				CardCrawlGame.mainMenuScreen.hideMenuButtons();
				CardCrawlGame.mainMenuScreen.screen = MainMenuPatchEnums.MAIN_MENU_CONFIG_SCREEN;
			}
		} else if (source != ConfigOpenSource.CHARACTER_SELECT && source != ConfigOpenSource.BASE_MOD) {
			DuelistMod.configCancelButton.closeFunction.run();
		}
	}

	public static void leavesVinesCommonOptionHandler(VinesLeavesMod optionToCheck, AnyDuelist duelist) {
		switch (optionToCheck) {
			case GAIN_1_GOLD:
				if (duelist.player()) {
					DuelistCard.gainGold(1, AbstractDungeon.player, true);
				}
				break;
			case GAIN_5_GOLD:
				if (duelist.player()) {
					DuelistCard.gainGold(5, AbstractDungeon.player, true);
				}
				break;
			case GAIN_10_GOLD:
				if (duelist.player()) {
					DuelistCard.gainGold(10, AbstractDungeon.player, true);
				}
				break;
			case LOSE_ALL_TEMP_HP:
				AbstractDungeon.actionManager.addToBottom(new RemoveAllTemporaryHPAction(duelist.creature(), duelist.creature()));
				break;
			case LOSE_1_HP:
				duelist.damageSelf(1);
				break;
			case LOSE_5_HP:
				duelist.damageSelf(5);
				break;
			case LOSE_1_BLOCK:
				duelist.creature().loseBlock(1);
				break;
			case LOSE_5_BLOCK:
				duelist.creature().loseBlock(5);
				break;
		}
	}

	public static AbstractPower vinesPower(int amount, AnyDuelist duelist) {
		VinesLeavesMod vinesOption = VinesLeavesMod.vinesOption();
		boolean isLeavesInstead =
				vinesOption == VinesLeavesMod.GAIN_THAT_MANY_LEAVES_INSTEAD ||
				vinesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_LEAVES_INSTEAD ||
				vinesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_LEAVES_INSTEAD;
		boolean halfAsMuch =
				vinesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_LEAVES_INSTEAD ||
				vinesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_LEAVES_AS_WELL ||
				vinesOption == VinesLeavesMod.GAIN_HALF;
		boolean twiceAsMuch =
				vinesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_LEAVES_INSTEAD ||
				vinesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_LEAVES_AS_WELL ||
				vinesOption == VinesLeavesMod.GAIN_TWICE_AS_MANY;
		amount = halfAsMuch ? amount / 2 : twiceAsMuch ? amount * 2 : amount;
		return isLeavesInstead ? new LeavesPower(duelist.creature(), amount) : new VinesPower(duelist.creature(), amount);
	}

	public static AbstractPower leavesPower(int amount, AnyDuelist duelist) {
		return leavesPower(amount, false, duelist);
	}

	public static AbstractPower leavesPower(int amount, boolean skipConfigChecks, AnyDuelist duelist) {
		VinesLeavesMod leavesOption = VinesLeavesMod.leavesOption();
		boolean isVinesInstead =
				leavesOption == VinesLeavesMod.GAIN_THAT_MANY_VINES_INSTEAD ||
						leavesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_VINES_INSTEAD ||
						leavesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_VINES_INSTEAD;
		boolean halfAsMuch =
				leavesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_VINES_INSTEAD ||
						leavesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_VINES_AS_WELL ||
						leavesOption == VinesLeavesMod.GAIN_HALF;
		boolean twiceAsMuch =
				leavesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_VINES_INSTEAD ||
						leavesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_VINES_AS_WELL ||
						leavesOption == VinesLeavesMod.GAIN_TWICE_AS_MANY;
		amount = halfAsMuch ? amount / 2 : twiceAsMuch ? amount * 2 : amount;
		return isVinesInstead ? new VinesPower(duelist.creature(), amount, skipConfigChecks) : new LeavesPower(duelist.creature(), amount, skipConfigChecks);
	}

	public static DuelistCard getRandomMagnetCard(boolean allowSuperMagnets) {
		ArrayList<DuelistCard> magnets = new ArrayList<>();
		magnets.add(new AlphaMagnet());
		magnets.add(new BetaMagnet());
		magnets.add(new GammaMagnet());
		magnets.add(new DeltaMagnet());
		if (allowSuperMagnets) {
			magnets.add(new AlphaElectro());
			magnets.add(new BetaElectro());
			magnets.add(new GammaElectro());
			magnets.add(new Berserkion());
		}
		int roll = AbstractDungeon.cardRandomRng.random(0, magnets.size() - 1);
		return magnets.get(roll);
	}

	public static OrbConfigData getOrbConfiguration(String orb) {
		return DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().getOrDefault(orb, new OrbConfigData(0, 0));
	}

	public static int getOrbConfiguredPassive(String orb) {
		OrbConfigData configData = getOrbConfiguration(orb);
		return configData.getConfigPassive();
	}

	public static int getOrbConfiguredEvoke(String orb) {
		OrbConfigData configData = getOrbConfiguration(orb);
		return configData.getConfigEvoke();
	}

	public static boolean getOrbConfiguredPassiveDisabled(String orb) {
		if (DuelistMod.persistentDuelistData.OrbConfigurations.getDisableAllOrbPassives()) return true;

		OrbConfigData configData = getOrbConfiguration(orb);
		return configData.getPassiveDisabled();
	}

	public static boolean getOrbConfiguredEvokeDisabled(String orb) {
		if (DuelistMod.persistentDuelistData.OrbConfigurations.getDisableAllOrbEvokes()) return true;

		OrbConfigData configData = getOrbConfiguration(orb);
		return configData.getEvokeDisabled();
	}

	private static OrbConfigData generateOrbConfigData(int passive, int evoke) {
		return new OrbConfigData(passive, evoke);
	}

	public static HashMap<String, OrbConfigData> generateDefaultConfigurationsMap() {
		HashMap<String, OrbConfigData> orbConfigs = new HashMap<>();

		orbConfigs.put("theDuelist:Air", generateOrbConfigData(1, 1));
		orbConfigs.put("theDuelist:Alien", generateOrbConfigData(1, 0));
		orbConfigs.put("theDuelist:Anticrystal", generateOrbConfigData(4, 2));
		orbConfigs.put("theDuelist:Black", generateOrbConfigData(2, 2));
		orbConfigs.put("theDuelist:Blaze", generateOrbConfigData(1, 3));
		orbConfigs.put("theDuelist:Buffer", generateOrbConfigData(1, 1));
		orbConfigs.put("theDuelist:Consumer", generateOrbConfigData(0, 2));
		orbConfigs.put("theDuelist:DarkMillennium", generateOrbConfigData(6, 0));
		orbConfigs.put("theDuelist:DragonOrb", generateOrbConfigData(2, 1));
		orbConfigs.put("theDuelist:DragonPlusOrb", generateOrbConfigData(4, 1));
		orbConfigs.put("theDuelist:CrystalOrb", generateOrbConfigData(2, 4));
		orbConfigs.put("theDuelist:GlassOrb", generateOrbConfigData(0, 0));
		orbConfigs.put("theDuelist:HellfireOrb", generateOrbConfigData(2, 1));
		orbConfigs.put("theDuelist:LightOrb", generateOrbConfigData(1, 2));
		orbConfigs.put("theDuelist:Earth", generateOrbConfigData(1, 1));
		orbConfigs.put("theDuelist:FireOrb", generateOrbConfigData(2, 1));
		orbConfigs.put("theDuelist:Gadget", generateOrbConfigData(2, 5));
		orbConfigs.put("theDuelist:Gate", generateOrbConfigData(4, 2));
		orbConfigs.put("theDuelist:Glitch", generateOrbConfigData(1, 1));
		orbConfigs.put("theDuelist:Lava", generateOrbConfigData(2, 4));
		orbConfigs.put("theDuelist:LightMillennium", generateOrbConfigData(6, 0));
		orbConfigs.put("theDuelist:Metal", generateOrbConfigData(3, 2));
		orbConfigs.put("theDuelist:MillenniumOrb", generateOrbConfigData(0, 1));
		orbConfigs.put("theDuelist:Mist", generateOrbConfigData(1, 3));
		orbConfigs.put("theDuelist:MonsterOrb", generateOrbConfigData(1, 2));
		orbConfigs.put("theDuelist:Moon", generateOrbConfigData(0, 10));
		orbConfigs.put("theDuelist:Mud", generateOrbConfigData(1, 1));
		orbConfigs.put("theDuelist:ReducerOrb", generateOrbConfigData(1, 1));
		orbConfigs.put("theDuelist:Sand", generateOrbConfigData(4, 8));
		orbConfigs.put("theDuelist:Shadow", generateOrbConfigData(3, 1));
		orbConfigs.put("theDuelist:Smoke", generateOrbConfigData(4, 2));
		orbConfigs.put("theDuelist:Splash", generateOrbConfigData(2, 2));
		orbConfigs.put("theDuelist:Storm", generateOrbConfigData(1, 2));
		orbConfigs.put("theDuelist:Summoner", generateOrbConfigData(1, 2));
		orbConfigs.put("theDuelist:Sun", generateOrbConfigData(0, 10));
		orbConfigs.put("theDuelist:Surge", generateOrbConfigData(2, 2));
		orbConfigs.put("theDuelist:TokenOrb", generateOrbConfigData(1, 2));
		orbConfigs.put("theDuelist:VoidOrb", generateOrbConfigData(2, 1));
		orbConfigs.put("theDuelist:WaterOrb", generateOrbConfigData(1, 2));
		orbConfigs.put("theDuelist:WhiteOrb", generateOrbConfigData(0, 0));

		return orbConfigs;
	}

	public static void setupOrbConfigSettingsMap() {
		if (!DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().isEmpty()) {
			return;
		}
		HashMap<String, OrbConfigData> orbConfigs = generateDefaultConfigurationsMap();
		DuelistMod.persistentDuelistData.OrbConfigurations.setOrbConfigurations(orbConfigs);
		DuelistMod.configSettingsLoader.save();
	}

	@FunctionalInterface
	public interface ConfigMenuObjectDescriptionLineBreakFunction {
		void run(int extra);
	}

	public static void formatConfigMenuObjectDescription(ArrayList<IUIElement> settingElements, String input, int linebreakExtra, int maxWidth, int maxLines, ConfigMenuObjectDescriptionLineBreakFunction func) {
		String[] paragraph = formatParagraphForConfigMenuObjects(input, maxWidth, maxLines);
		for (String line : paragraph) {
			settingElements.add(new ModLabel(line, (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
			if (func != null) {
				func.run(linebreakExtra);
			}
		}
	}

	private static String[] formatParagraphForConfigMenuObjects(String text, int maxWidth, int maxLines)
	{
		text = text.replaceAll(" NL ", " ").replaceAll("#y", "").replaceAll("#b", "").replaceAll("#r", "");
		String[] words = text.split("\\s+");

		int lines = 0;
		StringBuilder pp = new StringBuilder();
		StringBuilder line = new StringBuilder();
		for (String w : words) {
			if (lines >= maxLines) {
				break;
			}
			if (line.length() + w.length() + 1 > maxWidth) {
				if (pp.length() > 0) {
					pp.append(System.lineSeparator());
				}
				pp.append(line);
				lines++;
				line.setLength(0);
			}
			if (line.length() > 0) {
				line.append(' ');
			}
			line.append(w);
		}
		if (line.length() > 0) {
			if (lines < maxLines) {
				if (pp.length() > 0)
					pp.append(System.lineSeparator());
				pp.append(line);
			} else {
				pp.append("...");
			}
		}
		return pp.toString().split("\\n");
	}

	public static void logMetricsFromBattleCity(String eventName, String playerChoice, List<String> cardsObtained, List<String> cardsRemoved, List<String> cardsTransformed, List<String> cardsUpgraded, List<String> relicsObtained, List<String> potionsObtained, List<String> relicsLost, int damageTaken, int damageHealed, int hpLoss, int hpGain, int goldGain, int goldLoss) {
		HashMap<String, Object> choice = new HashMap<>();
		choice.put("event_name", eventName);
		choice.put("player_choice", playerChoice);
		choice.put("floor", AbstractDungeon.floorNum);
		choice.put("cards_obtained", cardsObtained);
		choice.put("cards_removed", cardsRemoved);
		choice.put("cards_transformed", cardsTransformed);
		choice.put("cards_upgraded", cardsUpgraded);
		choice.put("relics_obtained", relicsObtained);
		choice.put("potions_obtained", potionsObtained);
		choice.put("relics_lost", relicsLost);
		choice.put("damage_taken", damageTaken);
		choice.put("damage_healed", damageHealed);
		choice.put("max_hp_loss", hpLoss);
		choice.put("max_hp_gain", hpGain);
		choice.put("gold_gain", goldGain);
		choice.put("gold_loss", goldLoss);
		choice.put("duelist", true);
		CardCrawlGame.metricData.event_choices.add(choice);
	}

	public static void addEventsToGame() {
		List<AbstractEvent> duelistEvents = new ArrayList<>();
		duelistEvents.add(new MillenniumItems());
		duelistEvents.add(new AknamkanonTomb());
		duelistEvents.add(new EgyptVillage());
		duelistEvents.add(new TombNameless());
		duelistEvents.add(new TombNamelessPuzzle());
		duelistEvents.add(new CardTrader());
		duelistEvents.add(new RelicDuplicator());
		duelistEvents.add(new VisitFromAnubis());
		duelistEvents.add(new BattleCity());

		DuelistMod.allDuelistEvents.addAll(duelistEvents);

		boolean wasEmpty = DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().isEmpty();
		for (AbstractEvent event : duelistEvents) {
			Condition spawnCondition = null;
			Condition bonusCondition = null;
			String eventId = null;
			boolean duelistOnly = true;
			String dungeonId = null;
			EventUtils.EventType type = null;
			DuelistConfigurationData config = null;
			if (event instanceof DuelistEvent) {
				DuelistEvent de = (DuelistEvent) event;
				spawnCondition = de.spawnCondition;
				bonusCondition = de.bonusCondition;
				eventId = de.duelistEventId;
				duelistOnly = de.duelistOnly;
				dungeonId = de.dungeonId;
				type = de.type;
				config = de.getConfigurations();
				if (wasEmpty) {
					DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().put(eventId, de.getDefaultConfig());
				}
			} else if (event instanceof CombatDuelistEvent) {
				CombatDuelistEvent ce = (CombatDuelistEvent) event;
				spawnCondition = ce.spawnCondition;
				bonusCondition = ce.bonusCondition;
				eventId = ce.duelistEventId;
				duelistOnly = ce.duelistOnly;
				dungeonId = ce.dungeonId;
				type = ce.type;
				config = ce.getConfigurations();
				if (wasEmpty) {
					DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().put(eventId, ce.getDefaultConfig());
				}
			}

			AddEventParams.Builder builder = new AddEventParams.Builder(eventId, event.getClass());
			if (type != null) builder.eventType(type);
			if (spawnCondition != null) builder.spawnCondition(spawnCondition);
			if (bonusCondition != null) builder.bonusCondition(bonusCondition);
			if (duelistOnly) builder.playerClass(TheDuelistEnum.THE_DUELIST);
			if (dungeonId != null) builder.dungeonID(dungeonId);
			BaseMod.addEvent(builder.create());

			if (config != null) {
				DuelistMod.eventConfigurations.add(config);
			}

		}
		DuelistMod.configSettingsLoader.save();
	}

	public static AbstractRoom getCurrentRoom()
	{
		MapRoomNode mapNode = AbstractDungeon.currMapNode;
		if (mapNode == null)
		{
			return null;
		}
		else
		{
			return mapNode.getRoom();
		}
	}

	public static boolean inBattle()
	{
		AbstractRoom room = getCurrentRoom();
		if (room != null && !room.isBattleOver)
		{
			return room.phase == AbstractRoom.RoomPhase.COMBAT || (room.monsters != null && !room.monsters.areMonstersBasicallyDead());
		}

		return false;
	}

	public static boolean deckIs(String ...deckNames) {
		for (String deckName : deckNames) {
			if (deckIs(deckName)) {
				return true;
			}
		}
		return false;
	}
    
    public static boolean deckIs(String deckName)
    {
    	if (StartingDeck.currentDeck.getDeckName().equals(deckName)) { return true; }
    	else if (DuelistMod.addedAquaSet && deckName.equals("Aqua Deck")) { return true; }
    	else if (DuelistMod.addedDragonSet && deckName.equals("Dragon Deck")) { return true; }
    	else if (DuelistMod.addedFiendSet && deckName.equals("Fiend Deck")) { return true; }
    	else if (DuelistMod.addedIncrementSet && deckName.equals("Increment Deck")) { return true; }
    	else if (DuelistMod.addedInsectSet && deckName.equals("Insect Deck")) { return true; }
    	else if (DuelistMod.addedMachineSet && deckName.equals("Machine Deck")) { return true; }
    	else if (DuelistMod.addedNaturiaSet && deckName.equals("Naturia Deck")) { return true; }
    	else if (DuelistMod.addedBeastSet && deckName.equals("Beast Deck")) { return true; }
    	else if (DuelistMod.addedPlantSet && deckName.equals("Plant Deck")) { return true; }
    	else if (DuelistMod.addedSpellcasterSet && deckName.equals("Spellcaster Deck")) { return true; }
    	else if (DuelistMod.addedStandardSet && deckName.equals("Standard Deck")) { return true; }
    	else if (DuelistMod.addedToonSet && deckName.equals("Toon Deck")) { return true; }
    	else if (DuelistMod.addedWarriorSet && deckName.equals("Warrior Deck")) { return true; }
    	return DuelistMod.addedZombieSet && deckName.equals("Zombie Deck");
	}

	public static boolean isSummoningZonesRestricted() {
		return (
			DuelistMod.persistentDuelistData.GameplaySettings.getRestrictSummonZones() ||
			Util.getChallengeLevel() >= 20 ||
			Util.isCustomModActive("theDuelist:SummonersChallenge")
		);
	}

	public static int getChallengeDiffIndex()
	{
		if (isCustomModActive("challengethespire:Bronze Difficulty")) { return 1; }
		else if (isCustomModActive("challengethespire:Silver Difficulty")) { return 2; }
		else if (isCustomModActive("challengethespire:Gold Difficulty")) { return 3; }
		else if (isCustomModActive("challengethespire:Platinum Difficulty")) { return 4; }
		else { return -1; }
	}

    public static boolean isCustomModActive(String ID) {
        return (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ID)) || ModHelper.isModEnabled(ID);
    }
    
    public static int factorial(int n) 
    {
    	if (n > 19) { n = 19; }
    	DuelistMod.logger.info("Factorial iteration value: " + n);
    	return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);
    }

	public static TwoNums getLowHigh(int initialLow, int initialHigh) {
		int low = initialLow;
		int high = initialHigh;
		if (low > high) {
			int t = low;
			low = high;
			high = t;
		}
		if (low < 0) {
			low = 0;
		}
		if (high <= 0) {
			return new TwoNums(0, 0);
		}
		return new TwoNums(low, high);
	}

	public static void empowerResummon(AbstractCard card, AbstractMonster target)
	{
		card.unhover();
        card.untip();
        card.stopGlowing();
        card.shrink();
        TheDuelist.duelistSouls = new DuelistSoulGroup(target);
        boolean needMoreSouls = true;
        for (final DuelistSoul s : TheDuelist.duelistSouls.souls) {
            if (s.isReadyForReuse) {
                card.untip();
                card.unhover();
                s.empowerResummon(card);
                needMoreSouls = false;
                break;
            }
        }
        if (needMoreSouls) {
        	log("Not enough DuelistSouls, creating...");
            final DuelistSoul s2 = new DuelistSoul(target);
            s2.empowerResummon(card);
            TheDuelist.duelistSouls.souls.add(s2);
        }
	}

	public static void modifySouls(int add)
	{
		if (add > 0 && AbstractDungeon.player.hasPower(NoSoulGainPower.POWER_ID)) { add = 0; }
		DuelistMod.currentZombieSouls += add;
		if (DuelistMod.currentZombieSouls < 0) { DuelistMod.currentZombieSouls = 0; }
		DuelistCard.handleOnSoulChangeForAllAbstracts(DuelistMod.currentZombieSouls, add);
		Util.log("Modified zombie souls! Added: " + add);
	}

	public static void setSouls(int set)
	{
		if (set > DuelistMod.currentZombieSouls && AbstractDungeon.player.hasPower(NoSoulGainPower.POWER_ID))
		{
			return;
		}
		int change = set - DuelistMod.currentZombieSouls;
		modifySouls(change);
	}

	public static boolean checkSouls(int lossAmt)
	{
		return DuelistMod.currentZombieSouls >= lossAmt;
	}

	public static ArrayList<MutateCard> getMutateOptions(int optionsNeeded, ArrayList<AbstractCard> mutatePool)
	{
		ArrayList<MutateCard> mcards = new ArrayList<>();
		ArrayList<MutateCard> options = new ArrayList<>();
		MutateCard dmgA = new MutateDamage(3, CardRarity.COMMON);
		MutateCard dmgB = new MutateDamage(5, CardRarity.UNCOMMON);
		MutateCard dmgC = new MutateDamage(10, CardRarity.RARE);
		MutateCard blkA = new MutateBlock(3, CardRarity.COMMON);
		MutateCard blkB = new MutateBlock(5, CardRarity.UNCOMMON);
		MutateCard blkC = new MutateBlock(10, CardRarity.RARE);
		MutateCard magA = new MutateMagic(1, CardRarity.UNCOMMON);
		MutateCard magB = new MutateMagic(2, CardRarity.RARE);
		MutateCard costA = new MutateCost(1, CardRarity.UNCOMMON);
		MutateCard costB = new MutateCost(2, CardRarity.RARE);
		MutateCard tribA = new MutateTrib(1, CardRarity.COMMON);
		MutateCard tribB = new MutateTrib(2, CardRarity.UNCOMMON);
		MutateCard tribC = new MutateTrib(3, CardRarity.RARE);
		MutateCard dupeA = new MutateDupeA(CardRarity.UNCOMMON);
		MutateCard dupeB = new MutateDupeB(CardRarity.RARE);
		if (dmgA.canSpawnInOptions(mutatePool)) { mcards.add(dmgA); }
		if (dmgB.canSpawnInOptions(mutatePool)) { mcards.add(dmgB); }
		if (dmgC.canSpawnInOptions(mutatePool)) { mcards.add(dmgC); }
		if (blkA.canSpawnInOptions(mutatePool)) { mcards.add(blkA); }
		if (blkB.canSpawnInOptions(mutatePool)) { mcards.add(blkB); }
		if (blkC.canSpawnInOptions(mutatePool)) { mcards.add(blkC); }
		if (magA.canSpawnInOptions(mutatePool)) { mcards.add(magA); }
		if (magB.canSpawnInOptions(mutatePool)) { mcards.add(magB); }
		if (costA.canSpawnInOptions(mutatePool)) { mcards.add(costA); }
		if (costB.canSpawnInOptions(mutatePool)) { mcards.add(costB); }
		if (tribA.canSpawnInOptions(mutatePool)) { mcards.add(tribA); }
		if (tribB.canSpawnInOptions(mutatePool)) { mcards.add(tribB); }
		if (tribC.canSpawnInOptions(mutatePool)) { mcards.add(tribC); }
		mcards.add(dupeA);
		mcards.add(dupeB);
		if (optionsNeeded >= mcards.size()) { return mcards; }
		else
		{
			boolean loopAllowed = true;
			while (options.size() < optionsNeeded && loopAllowed)
			{
				boolean commons = false;
				boolean uncommons = false;
				boolean rares = false;

				for (MutateCard m : mcards)
				{
					if (m.rarity.equals(CardRarity.COMMON)) { commons = true; }
					else if (m.rarity.equals(CardRarity.UNCOMMON)) { uncommons = true; }
					else if (m.rarity.equals(CardRarity.RARE)) { rares = true; }
				}

				if (!commons && !uncommons && !rares)
				{
					loopAllowed = false;
					Util.log("Mutate Options generation couldn't find anymore options");
				}
				else
				{
					CardRarity roll = getRarity(commons, uncommons, rares);
					if (!roll.equals(CardRarity.SPECIAL))
					{
						int index = AbstractDungeon.cardRandomRng.random(mcards.size() - 1);
						while (!mcards.get(index).rarity.equals(roll))
						{
							index = AbstractDungeon.cardRandomRng.random(mcards.size() - 1);
						}

						options.add(mcards.get(index));
						mcards.remove(index);
						Util.log("Mutate Options generation added a new option");
					}
					else { Util.log("Generating mutate effects is returning a Special card rarity for rarityRoll.. bad"); }
				}
			}
		}
		return options;
	}

	private static CardRarity getRarity(boolean c, boolean u, boolean r)
	{
		if (!c && !u && r) { return CardRarity.RARE; }
		else if (!c && u && !r) { return CardRarity.UNCOMMON; }
		else if (c && !u && !r) { return CardRarity.COMMON; }
		else if (c && u && !r)
		{
			if (AbstractDungeon.cardRandomRng.random(1, 100) < 40) { return CardRarity.UNCOMMON; }
			else { return CardRarity.COMMON; }
		}
		else if (c && !u && r)
		{
			if (AbstractDungeon.cardRandomRng.random(1, 100) < 10) { return CardRarity.RARE; }
			else { return CardRarity.COMMON; }
		}
		else if (!c && u && r)
		{
			if (AbstractDungeon.cardRandomRng.random(1, 100) < 10) { return CardRarity.RARE; }
			else { return CardRarity.UNCOMMON; }
		}
		else if (c && u && r)
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, 100);
			if (roll < 10) { return CardRarity.RARE; }
			else if (roll < 40) { return CardRarity.UNCOMMON; }
			else { return CardRarity.UNCOMMON; }
		}
		else {
			return CardRarity.SPECIAL;
		}
	}

	public static boolean isSpawningBombCasingOnDetonate() {
		return !(Util.getChallengeLevel() > 3 && Util.deckIs("Machine Deck"));
	}

	public static int getChallengeLevel()
	{
		if (DuelistMod.playingChallenge) { return DuelistMod.challengeLevel; }
		else { return -1; }
	}

	public static void setChallengeLevel(int newLevel) {
		if (newLevel != DuelistMod.challengeLevel) {
			Util.log("Setting challenge level to " + newLevel);
		}
    	DuelistMod.challengeLevel = newLevel;
		Util.updateCharacterSelectScreenPuzzleDescription();
    	//DuelistMod.topPanelChallengeIcon.setChallengeLevel(newLevel);
	}

	public static void updateRelicListForSelectScreen(ArrayList<String> relicIds) {
		CardPoolRelicFilter filter = getCardPoolStartingRelicFilter();
		for (String s : filter.getToAdd()) {
			if (!relicIds.contains(s)) {
				if (s.equals(BoosterPackPoolRelic.ID)) {
					int index = relicIds.contains(ChallengePuzzle.ID) ? 2 : 1;
					relicIds.add(index, s);
				} else {
					relicIds.add(s);
				}

			}
		}
		relicIds.removeAll(filter.getToRemove());

		if (relicIds.contains(BoosterPackPoolRelic.ID) && relicIds.contains(CardPoolRelic.ID)) {
			relicIds.remove(BoosterPackPoolRelic.ID);
			relicIds.add(relicIds.contains(ChallengePuzzle.ID) ? 4 : 3, BoosterPackPoolRelic.ID);
		}
	}

	public static void updateSelectScreenRelicList() {
		CharSelectInfo info = CharacterSelectHelper.getInfo();
		if (info != null) {
			if (info.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
				updateRelicListForSelectScreen(info.relics);
			}
		}
	}

	public static CardPoolRelicFilter getCardPoolStartingRelicFilter() {
		ArrayList<String> toAdd = new ArrayList<>();
		ArrayList<String> toRemove = new ArrayList<>();
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		if (StartingDeck.currentDeck != StartingDeck.EXODIA || config.getCannotObtainCards() == null || !config.getCannotObtainCards()) {
			if ((DuelistMod.persistentDuelistData.CardPoolSettings.getAllowBoosters() || DuelistMod.persistentDuelistData.CardPoolSettings.getAlwaysBoosters())) {
				toAdd.add(BoosterPackPoolRelic.ID);
			} else {
				toRemove.add(BoosterPackPoolRelic.ID);
			}

			if (DuelistMod.persistentDuelistData.GameplaySettings.getCardPoolRelics()) {
				toAdd.add(CardPoolRelic.ID);
				toAdd.add(CardPoolBasicRelic.ID);
				toAdd.add(CardPoolAddRelic.ID);
				toAdd.add(CardPoolMinusRelic.ID);
				toAdd.add(CardPoolSaveRelic.ID);
				toAdd.add(CardPoolOptionsRelic.ID);
			} else {
				toRemove.add(CardPoolRelic.ID);
				toRemove.add(CardPoolBasicRelic.ID);
				toRemove.add(CardPoolAddRelic.ID);
				toRemove.add(CardPoolMinusRelic.ID);
				toRemove.add(CardPoolSaveRelic.ID);
				toRemove.add(CardPoolOptionsRelic.ID);
			}
		} else {
			toRemove.add(CardPoolRelic.ID);
			toRemove.add(CardPoolBasicRelic.ID);
			toRemove.add(BoosterPackPoolRelic.ID);
			toRemove.add(CardPoolAddRelic.ID);
			toRemove.add(CardPoolMinusRelic.ID);
			toRemove.add(CardPoolSaveRelic.ID);
			toRemove.add(CardPoolOptionsRelic.ID);
		}
		return new CardPoolRelicFilter(toAdd, toRemove);
	}

	public static boolean isMillenniumItem(AbstractRelic r, boolean includePuzzle) {
		return (includePuzzle || (!(r instanceof MillenniumPuzzle) && !(r instanceof MillenniumPuzzleShared))) && r instanceof MillenniumItem;
	}

	public static ArrayList<AbstractRelic> getMillenniumItemsForEvent(boolean includePuzzle)
	{
		ArrayList<AbstractRelic> items = new ArrayList<>();
		items.add(new MillenniumCoin());
		items.add(new MillenniumRing());
		items.add(new MillenniumRod());
		items.add(new MillenniumSymbol());
		items.add(new ResummonBranch());
		items.add(new MillenniumScale());
		items.add(new MillenniumNecklace());
		items.add(new MillenniumPeriapt());
		items.add(new MillenniumPrayerbook());
		items.add(new MillenniumArmor());
		if (includePuzzle) { items.add(new MillenniumPuzzle()); }
		//Collections.shuffle(items);
		return items;
	}

	public static AbstractCard getRandomBambooSword()
	{
		ArrayList<AbstractCard> swords = new ArrayList<>();
		swords.add(new BambooSwordBroken());
		swords.add(new BambooSwordBurning());
		swords.add(new BambooSwordCursed());
		swords.add(new BambooSwordGolden());
		swords.add(new BambooSwordSoul());
		return swords.get(AbstractDungeon.cardRandomRng.random(swords.size() - 1));
	}

	public static AbstractCard getRandomBambooSword(boolean upgraded)
	{
		ArrayList<AbstractCard> swords = new ArrayList<>();
		swords.add(new BambooSwordBroken());
		swords.add(new BambooSwordBurning());
		swords.add(new BambooSwordCursed());
		swords.add(new BambooSwordGolden());
		swords.add(new BambooSwordSoul());
		AbstractCard sword = swords.get(AbstractDungeon.cardRandomRng.random(swords.size() - 1));
		if (upgraded && sword.canUpgrade()) { sword.upgrade(); }
		return sword;
	}

	public static AbstractCard getSpecialGreedCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialGreedCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}

	public static ArrayList<DuelistCard> getSpecialGreedCardsForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<>();
		specialCards.add(new AncientGearBoxNamelessGreed());
		specialCards.add(new BerserkerCrushNamelessGreed());
		specialCards.add(new GracefulCharityNamelessGreed());
		specialCards.add(new ChimeraFusionNamelessGreed());
		specialCards.add(new MagnumShieldNamelessGreed());
		if (TombNamelessPuzzle.isNamelessCardsDisabled()) {
			ArrayList<DuelistCard> replaced = new ArrayList<>();
			for (DuelistCard c : specialCards) {
				if (c instanceof NamelessTombCard) {
					replaced.add(((NamelessTombCard) c).getStandardVersion());
				} else {
					replaced.add(c);
				}
			}
			return replaced;
		}
		return specialCards;
	}

	public static AbstractCard getSpecialPowerCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialPowerCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}

	public static ArrayList<DuelistCard> getSpecialPowerCardsForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<>();
		specialCards.add(new AllyJusticeNamelessPower());
		specialCards.add(new AssaultArmorNamelessPower());
		specialCards.add(new BeatraptorNamelessPower());
		specialCards.add(new BerserkerCrushNamelessPower());
		specialCards.add(new ForbiddenLanceNamelessPower());
		specialCards.add(new EnragedBattleOxNamelessPower());
		specialCards.add(new KamionTimelordNamelessPower());
		specialCards.add(new MaskedDragonNamelessPower());
		specialCards.add(new SpiralSpearStrikeNamelessPower());
		if (TombNamelessPuzzle.isNamelessCardsDisabled()) {
			ArrayList<DuelistCard> replaced = new ArrayList<>();
			for (DuelistCard c : specialCards) {
				if (c instanceof NamelessTombCard) {
					replaced.add(((NamelessTombCard) c).getStandardVersion());
				} else {
					replaced.add(c);
				}
			}
			return replaced;
		}
		return specialCards;
	}

	public static AbstractCard getSpecialWarCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialWarCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}

	public static ArrayList<DuelistCard> getSpecialWarCardsForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<>();
		specialCards.add(new AllyJusticeNamelessWar());
		specialCards.add(new AssaultArmorNamelessWar());
		specialCards.add(new BerserkerCrushNamelessWar());
		specialCards.add(new ForbiddenLanceNamelessWar());
		specialCards.add(new GravityBehemothNamelessWar());
		specialCards.add(new MaskedDragonNamelessWar());
		specialCards.add(new SpiralSpearStrikeNamelessWar());
		specialCards.add(new FortressWarriorNamelessWar());
		specialCards.add(new BlueEyesNamelessWar());
		specialCards.add(new Gandora());
		if (TombNamelessPuzzle.isNamelessCardsDisabled()) {
			ArrayList<DuelistCard> replaced = new ArrayList<>();
			for (DuelistCard c : specialCards) {
				if (c instanceof NamelessTombCard) {
					replaced.add(((NamelessTombCard) c).getStandardVersion());
				} else {
					replaced.add(c);
				}
			}
			return replaced;
		}
		return specialCards;
	}

	public static AbstractCard getRandomRarePowerForNamelessTomb()
	{
		ArrayList<AbstractCard> rarePow = new ArrayList<>();
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (c.rarity.equals(CardRarity.RARE) && c.type.equals(CardType.POWER) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL))
			{
				while (c.canUpgrade()) { c.upgrade(); }
				rarePow.add(c.makeStatEquivalentCopy());
			}
		}

		if (rarePow.size() > 0) { return rarePow.get(AbstractDungeon.cardRandomRng.random(rarePow.size() - 1)); }
		else { return new Token(); }
	}

	public static AbstractCard getSpecialCardForMiracleDescent()
	{
		ArrayList<DuelistCard> specialCards = getSpecialCardsForMiracleDescent();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}

	public static AbstractCard getSpecialMagicCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialMagicCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}

	public static ArrayList<DuelistCard> getSpecialCardsForMiracleDescent()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<>();
		specialCards.add(new MDSpecialCardA());
		specialCards.add(new MDSpecialCardB());
		specialCards.add(new MDSpecialCardC());
		specialCards.add(new MDSpecialCardD());
		specialCards.add(new MDSpecialCardE());
		return specialCards;
	}

	public static AbstractCard getSpecialSparksCardForNamelessTomb() {
		ArrayList<DuelistCard> specialCards = getSpecialSparks();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}

	public static AbstractCard getSpecialSparksCard()
	{
		switch (DuelistMod.selectedSparksStrategy) {
			case RANDOM_WEIGHTED:
				return getWeightedSpecialSparks();
			case GOLDEN:
				return new GoldenSparks();
			case BLOOD:
				return new BloodSparks();
			case MAGIC:
				return new MagicSparks();
			case STORM:
				return new StormSparks();
			case DARK:
				return new DarkSparks();
			default:
				return getSpecialSparksCardForNamelessTomb();	// fully random
		}
	}

	private static AbstractCard getWeightedSpecialSparks() {
		// dark or magic (upgrade random card / upgrade random spell)
		// gold (gain gold)
		// storm (channel lightning)
		// blood (gain max hp)

		int roll = ThreadLocalRandom.current().nextInt(1, 5);
		switch (roll) {
			default:
				boolean secondRoll = ThreadLocalRandom.current().nextBoolean();
				return secondRoll ? new DarkSparks() : new MagicSparks();
			case 2: return new GoldenSparks();
			case 3: return new StormSparks();
			case 4: return new BloodSparks();
		}
	}


	public static ArrayList<DuelistCard> getSpecialSparks()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<>();
		specialCards.add(new GoldenSparks());
		specialCards.add(new BloodSparks());
		specialCards.add(new MagicSparks());
		specialCards.add(new StormSparks());
		specialCards.add(new DarkSparks());
		return specialCards;
	}

	public static AbstractCard getAnyNamelessTombCard(boolean rng)
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<>();
		specialCards.addAll(getSpecialMagicCardsForNamelessTomb());
		specialCards.addAll(getSpecialGreedCardsForNamelessTomb());
		specialCards.addAll(getSpecialPowerCardsForNamelessTomb());
		specialCards.addAll(getSpecialWarCardsForNamelessTomb());
		if (rng) { return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1)); }
		else { return specialCards.get(ThreadLocalRandom.current().nextInt(specialCards.size())); }
	}

	public static ArrayList<DuelistCard> getSpecialMagicCardsForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<>();
		specialCards.add(new AllyJusticeNameless());
		specialCards.add(new CaninetaurNameless());
		specialCards.add(new DragonTreasureNameless());
		specialCards.add(new AncientGearBoxNameless());
		specialCards.add(new AssaultArmorNameless());
		specialCards.add(new AxeDespairNameless());
		specialCards.add(new BerserkerCrushNameless());
		specialCards.add(new BigWhaleNameless());
		specialCards.add(new DarkworldThornsNameless());
		specialCards.add(new ForbiddenLanceNameless());
		specialCards.add(new GoldenApplesNameless());
		specialCards.add(new GracefulCharityNameless());
		specialCards.add(new GravityLashNameless());
		specialCards.add(new GridRodNameless());
		specialCards.add(new HappyLoverNameless());
		specialCards.add(new ImperialOrderNameless());
		specialCards.add(new InsectQueenNameless());
		specialCards.add(new KamionTimelordNameless());
		specialCards.add(new MagnumShieldNameless());
		//specialCards.add(new MaskedDragonNameless());
		specialCards.add(new ObeliskTormentorNameless());
		specialCards.add(new OilmanNameless());
		specialCards.add(new PotDualityNameless());
		specialCards.add(new PotGenerosityNameless());
		specialCards.add(new PredaplantSarraceniantNameless());
		specialCards.add(new SpiralSpearStrikeNameless());
		specialCards.add(new YamiFormNameless());
		specialCards.add(new HourglassLife());
		if (Util.deckIs("Naturia Deck"))
		{
			specialCards.add(new NaturalDisasterNameless());
		}
		if (TombNamelessPuzzle.isNamelessCardsDisabled()) {
			ArrayList<DuelistCard> replaced = new ArrayList<>();
			for (DuelistCard c : specialCards) {
				if (c instanceof NamelessTombCard) {
					replaced.add(((NamelessTombCard) c).getStandardVersion());
				} else {
					replaced.add(c);
				}
			}
			return replaced;
		}
		return specialCards;
	}

	public static ArrayList<DuelistCard> getStanceChoices(boolean allowMeditative, boolean allowDivinity, boolean allowChaotic, boolean allowDuelist, boolean allowBaseGame)
	{
		ArrayList<DuelistCard> stances = new ArrayList<>();
		if (allowDuelist)
		{
			stances.add(new ChooseSpectralCard());
			stances.add(new ChooseSamuraiCard());
			stances.add(new ChooseGuardedCard());
			stances.add(new ChooseForsakenCard());
			stances.add(new ChooseEntrenchedCard());
			stances.add(new ChooseNimbleCard());
			if (allowMeditative) { stances.add(new ChooseMeditativeCard()); }
			if (allowChaotic) { stances.add(new ChooseChaoticCard()); }
		}
		if (allowBaseGame)
		{
			stances.add(new ChooseWrathCard());
			stances.add(new ChooseCalmCard());
			if (allowDivinity) { stances.add(new ChooseDivinityCard()); }
		}
		return stances;
	}

	public static ArrayList<DuelistCard> getStanceChoices(boolean allowMeditative, boolean allowDivinity, boolean allowChaotic)
	{
		return getStanceChoices(allowMeditative, allowDivinity, allowChaotic, true, true);
	}

	public static ArrayList<DuelistCard> getStanceChoices(boolean allowDivinity, boolean allowChaotic)
	{
		return getStanceChoices(true, allowDivinity, allowChaotic, true, true);
	}

	public static ArrayList<DuelistCard> getStanceChoices()
	{
		return getStanceChoices(true, false, false, true, true);
	}

	public static void genesisDragonHelper()
	{
		ArrayList<AbstractCard> genesisDragsToAdd = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof GenesisDragon)
			{
				int genesisRoll = AbstractDungeon.cardRandomRng.random(1, 10);
				if (genesisRoll < 4 && !c.upgraded) { genesisDragsToAdd.add(c.makeStatEquivalentCopy()); }
				else if (genesisRoll == 1) { genesisDragsToAdd.add(c.makeStatEquivalentCopy()); }
			}
		}
		if (genesisDragsToAdd.size() > 0) { AbstractDungeon.player.masterDeck.group.addAll(genesisDragsToAdd); }
	}

	public static void unlockAllRelics(ArrayList<AbstractRelic> relics)
	{
		for (AbstractRelic r : relics) { UnlockTracker.markRelicAsSeen(r.relicId); }
		/*UnlockTracker.markRelicAsSeen(MillenniumPuzzle.ID);
		UnlockTracker.markRelicAsSeen(MillenniumRing.ID);
		UnlockTracker.markRelicAsSeen(MillenniumKey.ID);
		UnlockTracker.markRelicAsSeen(MillenniumRod.ID);
		UnlockTracker.markRelicAsSeen(MillenniumCoin.ID);
		UnlockTracker.markRelicAsSeen(ResummonBranch.ID);
		UnlockTracker.markRelicAsSeen(AeroRelic.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicA.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicB.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicC.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicD.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicE.ID);
		UnlockTracker.markRelicAsSeen(InversionRelic.ID);
		UnlockTracker.markRelicAsSeen(InversionEvokeRelic.ID);
		UnlockTracker.markRelicAsSeen(InsectRelic.ID);
		UnlockTracker.markRelicAsSeen(NaturiaRelic.ID);
		UnlockTracker.markRelicAsSeen(MachineToken.ID);
		UnlockTracker.markRelicAsSeen(StoneExxod.ID);
		UnlockTracker.markRelicAsSeen(GiftAnubis.ID);
		UnlockTracker.markRelicAsSeen(DragonRelic.ID);
		UnlockTracker.markRelicAsSeen(SummonAnchor.ID);
		UnlockTracker.markRelicAsSeen(SpellcasterToken.ID);
		UnlockTracker.markRelicAsSeen(SpellcasterOrb.ID);
		UnlockTracker.markRelicAsSeen(AquaRelic.ID);
		UnlockTracker.markRelicAsSeen(AquaRelicB.ID);
		UnlockTracker.markRelicAsSeen(NatureRelic.ID);
		UnlockTracker.markRelicAsSeen(ZombieRelic.ID);
		UnlockTracker.markRelicAsSeen(DragonRelicB.ID);
		UnlockTracker.markRelicAsSeen(ShopToken.ID);
		UnlockTracker.markRelicAsSeen(MillenniumScale.ID);
		UnlockTracker.markRelicAsSeen(MachineTokenB.ID);
		UnlockTracker.markRelicAsSeen(MillenniumNecklace.ID);
		UnlockTracker.markRelicAsSeen(MillenniumToken.ID);
		UnlockTracker.markRelicAsSeen(DragonRelicC.ID);
		//UnlockTracker.markRelicAsSeen(RandomTributeMonsterRelic.ID);
		UnlockTracker.markRelicAsSeen(YugiMirror.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicF.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicG.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicH.ID);
		UnlockTracker.markRelicAsSeen(TributeEggRelic.ID);
		UnlockTracker.markRelicAsSeen(ZombieResummonBuffRelic.ID);
		UnlockTracker.markRelicAsSeen(ToonRelic.ID);
		UnlockTracker.markRelicAsSeen(HauntedRelic.ID);
		UnlockTracker.markRelicAsSeen(SpellcasterStone.ID);
		UnlockTracker.markRelicAsSeen(OrbCardRelic.ID);		
		UnlockTracker.markRelicAsSeen(BoosterAlwaysBonusRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterAlwaysSillyRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterBetterBoostersRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterExtraAllRaresRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterBonusPackIncreaseRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterPackEggRelic.ID);
		UnlockTracker.markRelicAsSeen(SpellMaxHPRelic.ID);
		UnlockTracker.markRelicAsSeen(WhiteBowlRelic.ID);
		UnlockTracker.markRelicAsSeen(SummonAnchorRare.ID);
		UnlockTracker.markRelicAsSeen(GamblerChip.ID);
		UnlockTracker.markRelicAsSeen(MerchantPendant.ID);
		UnlockTracker.markRelicAsSeen(MerchantSword.ID);
		UnlockTracker.markRelicAsSeen(MerchantTalisman.ID);
		UnlockTracker.markRelicAsSeen(MerchantRugbox.ID);
		UnlockTracker.markRelicAsSeen(Monsterbox.ID);
		//UnlockTracker.markRelicAsSeen(Spellbox.ID);
		//UnlockTracker.markRelicAsSeen(Trapbox.ID);
		UnlockTracker.markRelicAsSeen(Spellheart.ID);
		UnlockTracker.markRelicAsSeen(TrapVortex.ID);
		UnlockTracker.markRelicAsSeen(MonsterEggRelic.ID);
		UnlockTracker.markRelicAsSeen(MagnetRelic.ID);
		UnlockTracker.markRelicAsSeen(MerchantNecklace.ID);
		UnlockTracker.markRelicAsSeen(KaibaToken.ID);
		UnlockTracker.markRelicAsSeen(AknamkanonsEssence.ID);
		UnlockTracker.markRelicAsSeen(MarkExxod.ID);
		UnlockTracker.markRelicAsSeen(DuelistCoin.ID);
		UnlockTracker.markRelicAsSeen(MetronomeRelicA.ID);
		UnlockTracker.markRelicAsSeen(MetronomeRelicB.ID);
		UnlockTracker.markRelicAsSeen(MetronomeRelicC.ID);
		UnlockTracker.markRelicAsSeen(MetronomeRelicD.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicI.ID);
		UnlockTracker.markRelicAsSeen(NamelessPowerRelicA.ID);
		UnlockTracker.markRelicAsSeen(NamelessPowerRelicB.ID);
		UnlockTracker.markRelicAsSeen(NamelessGreedRelic.ID);
		UnlockTracker.markRelicAsSeen(NamelessHungerRelic.ID);
		UnlockTracker.markRelicAsSeen(NamelessWarRelicA.ID);
		UnlockTracker.markRelicAsSeen(NamelessWarRelicB.ID);
		UnlockTracker.markRelicAsSeen(NamelessWarRelicC.ID);
		UnlockTracker.markRelicAsSeen(Leafblower.ID);
		UnlockTracker.markRelicAsSeen(NatureOrb.ID);
		UnlockTracker.markRelicAsSeen(MarkOfNature.ID);
		UnlockTracker.markRelicAsSeen(CursedHealer.ID);
		UnlockTracker.markRelicAsSeen(MillenniumSymbol.ID);
		UnlockTracker.markRelicAsSeen(DragonBurnRelic.ID);
		UnlockTracker.markRelicAsSeen(GoldenScale.ID);
		UnlockTracker.markRelicAsSeen(ConfusionGoldRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolAddRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolMinusRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolSaveRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolOptionsRelic.ID);*/
	}

	public static void setupDuelistTombRelics()
	{
		DuelistMod.duelistRelicsForTombEvent.add(new AeroRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicA());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicB());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicC());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicD());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicE());
		DuelistMod.duelistRelicsForTombEvent.add(new InversionRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new InversionEvokeRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new InsectRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new NaturiaRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new MachineToken());
		DuelistMod.duelistRelicsForTombEvent.add(new MachineOrb());
		DuelistMod.duelistRelicsForTombEvent.add(new Wirebundle());
		DuelistMod.duelistRelicsForTombEvent.add(new Fluxrod());
		DuelistMod.duelistRelicsForTombEvent.add(new DragonRelic());
		//DuelistMod.duelistRelicsForTombEvent.add(new SummonAnchor());
		DuelistMod.duelistRelicsForTombEvent.add(new SpellcasterToken());
		DuelistMod.duelistRelicsForTombEvent.add(new SpellcasterOrb());
		DuelistMod.duelistRelicsForTombEvent.add(new AquaRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new AquaRelicB());
		DuelistMod.duelistRelicsForTombEvent.add(new NatureRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new ZombieRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new DragonRelicB());
		DuelistMod.duelistRelicsForTombEvent.add(new ShopToken());
		DuelistMod.duelistRelicsForTombEvent.add(new StoneExxod());
		DuelistMod.duelistRelicsForTombEvent.add(new DragonRelicC());
		DuelistMod.duelistRelicsForTombEvent.add(new YugiMirror());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicF());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicG());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicH());
		DuelistMod.duelistRelicsForTombEvent.add(new TributeEggRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new ToonRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new SpellcasterStone());
		DuelistMod.duelistRelicsForTombEvent.add(new OrbCardRelic());
		//DuelistMod.duelistRelicsForTombEvent.add(new BoosterBetterBoostersRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterPackHealer());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterExtraAllRaresRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterPackMonsterEgg());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterPackSpellEgg());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterPackTrapEgg());
		DuelistMod.duelistRelicsForTombEvent.add(new SpellMaxHPRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new WhiteBowlRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new SummonAnchorRare());
		DuelistMod.duelistRelicsForTombEvent.add(new MonsterEggRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new MutatorToken());
		DuelistMod.duelistRelicsForTombEvent.add(new MerchantNecklace());
		DuelistMod.duelistRelicsForTombEvent.add(new KaibaToken());
		DuelistMod.duelistRelicsForTombEvent.add(new AknamkanonsEssence());
		DuelistMod.duelistRelicsForTombEvent.add(new MetronomeRelicA());
		DuelistMod.duelistRelicsForTombEvent.add(new MetronomeRelicB());
		DuelistMod.duelistRelicsForTombEvent.add(new MetronomeRelicC());
		DuelistMod.duelistRelicsForTombEvent.add(new MetronomeRelicD());
		DuelistMod.duelistRelicsForTombEvent.add(new Leafblower());
		DuelistMod.duelistRelicsForTombEvent.add(new NatureOrb());
		DuelistMod.duelistRelicsForTombEvent.add(new MarkOfNature());
		DuelistMod.duelistRelicsForTombEvent.add(new MillenniumSymbol());
		DuelistMod.duelistRelicsForTombEvent.add(new DragonBurnRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new GoldenScale());
		DuelistMod.duelistRelicsForTombEvent.add(new TokenUpgradeRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistPrismaticShard());
		DuelistMod.duelistRelicsForTombEvent.add(new BlessingAnubis());
		DuelistMod.duelistRelicsForTombEvent.add(new MillenniumPeriapt());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistTeaSet());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistOrichalcum());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistLetterOpener());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistUrn());
		DuelistMod.duelistRelicsForTombEvent.add(new MillenniumPrayerbook());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageA());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageB());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageC());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageD());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageE());
		DuelistMod.duelistRelicsForTombEvent.add(new MillenniumArmor());
		DuelistMod.duelistRelicsForTombEvent.add(new TokenArmor());
		DuelistMod.duelistRelicsForTombEvent.add(new BrazeToken());
		DuelistMod.duelistRelicsForTombEvent.add(new RouletteWheel());
		DuelistMod.duelistRelicsForTombEvent.add(new EngineeringToken());
		DuelistMod.duelistRelicsForTombEvent.add(new Bombchain());
		DuelistMod.duelistRelicsForTombEvent.add(new LoadedDice());
		DuelistMod.duelistRelicsForTombEvent.add(new TokenfestPendant());
		DuelistMod.duelistRelicsForTombEvent.add(new ZoneToken());
		DuelistMod.duelistRelicsForTombEvent.add(new SolderToken());
		DuelistMod.duelistRelicsForTombEvent.add(new ElectricToken());
		DuelistMod.duelistRelicsForTombEvent.add(new SailingToken());
		DuelistMod.duelistRelicsForTombEvent.add(new Flowstate());
		DuelistMod.duelistRelicsForTombEvent.add(new NileToken());
		DuelistMod.duelistRelicsForTombEvent.add(new FlowToken());
		DuelistMod.duelistRelicsForTombEvent.add(new WavemastersBlessing());
		DuelistMod.duelistRelicsForTombEvent.add(new GoldenSail());
		DuelistMod.duelistRelicsForTombEvent.add(new Splashbox());
		DuelistMod.duelistRelicsForTombEvent.add(new CoralToken());
		DuelistMod.duelistRelicsForTombEvent.add(new ResummonerFury());
		DuelistMod.duelistRelicsForTombEvent.add(new ResummonerBane());
		DuelistMod.duelistRelicsForTombEvent.add(new ResummonerMight());
		DuelistMod.duelistRelicsForTombEvent.add(new VampiricPendant());
		DuelistMod.duelistRelicsForTombEvent.add(new FusionToken());
		DuelistMod.duelistRelicsForTombEvent.add(new NuclearDecay());
		DuelistMod.duelistRelicsForTombEvent.add(new GhostToken());
		DuelistMod.duelistRelicsForTombEvent.add(new GraveToken());
		DuelistMod.duelistRelicsForTombEvent.add(new FlameMedallion());
		DuelistMod.duelistRelicsForTombEvent.add(new ApexToken());
		DuelistMod.duelistRelicsForTombEvent.add(new ClawedCodex());
		DuelistMod.duelistRelicsForTombEvent.add(new EruptionToken());
		DuelistMod.duelistRelicsForTombEvent.add(new VolcanoToken());
		DuelistMod.duelistRelicsForTombEvent.add(new ChronicleOfElders());
		DuelistMod.duelistRelicsForTombEvent.add(new SphinxInsight());
		//DuelistMod.duelistRelicsForTombEvent.add(new RandomTributeMonsterRelic());
		/*if (DuelistMod.debug)
		{
			ArrayList<AbstractRelic> comm = new ArrayList<>();
			ArrayList<AbstractRelic> uncomm = new ArrayList<>();
			ArrayList<AbstractRelic> rare = new ArrayList<>();
			ArrayList<AbstractRelic> shop = new ArrayList<>();
			ArrayList<AbstractRelic> boss = new ArrayList<>();
			ArrayList<AbstractRelic> special = new ArrayList<>();
			ArrayList<AbstractRelic> other = new ArrayList<>();
			for (AbstractRelic r : DuelistMod.duelistRelicsForTombEvent)
			{
				if (r.tier.equals(RelicTier.COMMON)) { comm.add(r); }
				else if (r.tier.equals(RelicTier.UNCOMMON)) { uncomm.add(r); }
				else if (r.tier.equals(RelicTier.RARE)) { rare.add(r); }
				else if (r.tier.equals(RelicTier.SHOP)) { shop.add(r); }
				else if (r.tier.equals(RelicTier.BOSS)) { boss.add(r); }
				else if (r.tier.equals(RelicTier.SPECIAL)) { special.add(r); }
				else  { other.add(r); }
			}
			
			Util.log("DUELIST TOMB EVENT DEBUG LOGGER");
			Util.log("Common Relics in Tomb Pool: " + comm.size());
			Util.log("Uncommon Relics in Tomb Pool: " + uncomm.size());
			Util.log("Rare Relics in Tomb Pool: " + rare.size());
			Util.log("Shop Relics in Tomb Pool: " + shop.size());
			Util.log("Boss Relics in Tomb Pool: " + boss.size());
			Util.log("Special Relics in Tomb Pool: " + special.size());
			Util.log("Other? Relics in Tomb Pool: " + other.size());
			
			if (comm.size() > 0)
			{
				Util.log("--- COMMON ---");
				for (AbstractRelic r : comm) { Util.log(r.name); }
			}
			
			if (uncomm.size() > 0)
			{
				Util.log("--- UNCOMMON ---");
				for (AbstractRelic r : uncomm) { Util.log(r.name); }
			}
			
			if (rare.size() > 0)
			{
				Util.log("--- RARE ---");
				for (AbstractRelic r : rare) { Util.log(r.name); }
			}
			
			if (shop.size() > 0)
			{
				Util.log("--- SHOP ---");
				for (AbstractRelic r : shop) { Util.log(r.name); }
			}

			if (boss.size() > 0)
			{
				Util.log("--- BOSS ---");
				for (AbstractRelic r : boss) { Util.log(r.name); }
			}
			
			if (special.size() > 0)
			{
				Util.log("--- SPECIAL ---");
				for (AbstractRelic r : special) { Util.log(r.name); }
			}
			
			if (other.size() > 0)
			{
				Util.log("--- OTHER? ---");
				for (AbstractRelic r : other) { Util.log(r.name); }
			}
		}*/
	}

	public static ArrayList<AbstractCard> allHolidayCardsNoDateCheck()
	{
		ArrayList<AbstractCard> holidayCards = new ArrayList<>();
		holidayCards.add(new Hallohallo());
		holidayCards.add(new PumpkinCarriage());
		holidayCards.add(new HalloweenManor());
		holidayCards.add(new BalloonParty());
		holidayCards.add(new CocoonParty());
		holidayCards.add(new DinnerParty());
		holidayCards.add(new ElephantGift());
		holidayCards.add(new FairyGift());
		holidayCards.add(new GiftCard());
		holidayCards.add(new HeroicGift());
		holidayCards.add(new WeedOut());
		return holidayCards;
	}

	public static ArrayList<AbstractCard> holidayCardRandomizedList()
	{
		ArrayList<AbstractCard> holidayCards = new ArrayList<>();
		if (Util.halloweenCheck())
		{
			holidayCards.add(new Hallohallo());
			holidayCards.add(new PumpkinCarriage());
			holidayCards.add(new HalloweenManor());
			DuelistMod.addedHalloweenCards = true;
		}
		else { DuelistMod.addedHalloweenCards = false; }
		if (Util.birthdayCheck())
		{
			holidayCards.add(new BalloonParty());
			holidayCards.add(new CocoonParty());
			holidayCards.add(new DinnerParty());
			DuelistMod.addedBirthdayCards = true;
		}
		else { DuelistMod.addedBirthdayCards = false; }
		if (Util.xmasCheck())
		{
			holidayCards.add(new ElephantGift());
			holidayCards.add(new FairyGift());
			holidayCards.add(new GiftCard());
			holidayCards.add(new HeroicGift());
			DuelistMod.addedXmasCards = true;
		}
		else { DuelistMod.addedXmasCards = false; }
		if (Util.weedCheck())
		{
			holidayCards.add(new WeedOut());
			DuelistMod.addedWeedCards = true;
		}
		else { DuelistMod.addedWeedCards = false; }
		Collections.shuffle(holidayCards, new java.util.Random(AbstractDungeon.cardRandomRng.randomLong()));
		return holidayCards;
	}

	public static boolean weedCheck()
	{
		boolean isXmas = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2019, Calendar.APRIL, 20);
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isXmas = true; }
		return isXmas;
	}

	public static boolean xmasCheck()
	{
		boolean isXmas = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2019, Calendar.DECEMBER, 25);
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isXmas = true; }
		return isXmas;
	}

	public static boolean halloweenCheck()
	{
		boolean isHalloween = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2019, Calendar.OCTOBER, 31);
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isHalloween = true; }
		return isHalloween;
	}

	public static int whichBirthday() {
		if (!birthdayCheck()) {
			return -1;
		} else {
			boolean isNyoxideBirthday = false;
			boolean playerBirthday = false;
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			cal2.set(2019, Calendar.OCTOBER, 3);

			if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
				isNyoxideBirthday = true;
			}

			GeneralSettings settings = DuelistMod.persistentDuelistData.GeneralSettings;
			if (!settings.neverChangedBirthday && settings.getBirthdayMonthInt() != null && settings.getBirthdayDayInt() != null) {
				Calendar cal3 = Calendar.getInstance();
				cal3.set(2019, settings.getBirthdayMonthInt() - 1, settings.getBirthdayDayInt());
				if (cal1.get(Calendar.DAY_OF_MONTH) == cal3.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal3.get(Calendar.MONTH)) {
					playerBirthday = true;
				}
			}

			if (isNyoxideBirthday) { return 1; }
			else if (playerBirthday) { return 2; }
			else { return 3; }
		}
	}

	public static boolean birthdayCheck()
	{
		boolean isBirthday = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();

		cal2.set(2019, Calendar.OCTOBER, 3);
		cal3.set(2019, Calendar.MARCH, 4);

		if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isBirthday = true; }
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal3.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal3.get(Calendar.MONTH)) { isBirthday = true; }

		GeneralSettings settings = DuelistMod.persistentDuelistData.GeneralSettings;
		if (!settings.neverChangedBirthday && settings.getBirthdayMonthInt() != null && settings.getBirthdayDayInt() != null) {
			Calendar cal4 = Calendar.getInstance();
			cal4.set(2019, settings.getBirthdayMonthInt() - 1, settings.getBirthdayDayInt());
			if (cal1.get(Calendar.DAY_OF_MONTH) == cal4.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal4.get(Calendar.MONTH)) { isBirthday = true; }
		}
		return isBirthday;
	}

	public static String getChallengeDifficultyDesc(boolean showDisabledText)
	{
		if (Util.getChallengeLevel() == 0) { return "#bMillennium #bPuzzle: Typed tokens become Puzzle Tokens."; }
		else if (Util.getChallengeLevel() == 1) { return "#bMillennium #bPuzzle: Deck effects are weakened or reduced."; }
		else if (Util.getChallengeLevel() == 2) { return "Start each combat with #b4 #yMax #ySummons."; }
		else if (Util.getChallengeLevel() == 3) { return "Enemy Duelists have #b1 random #yRelic each battle, and draw #b1 additional card per turn."; }
		else if (Util.getChallengeLevel() == 4) {
			return StartingDeck.currentDeck.getChallengeDescription();
		}
		else if (Util.getChallengeLevel() == 5) { return "Enemy Duelists have #b1 extra random #yRelic each battle. Relics have a chance to be #yEnergy relics."; }
		else if (Util.getChallengeLevel() == 6) { return "At the start of each of your turns, all enemies have a #b10% chance to gain #yBlock."; }
		else if (Util.getChallengeLevel() == 7) { return "#bMillennium #bPuzzle: NL No deck effects."; }
		else if (Util.getChallengeLevel() == 8) { return "Whenever you open a non-Boss chest, lose all of your Potions."; }
		else if (Util.getChallengeLevel() == 9) { return "#bMillennium #bPuzzle: NL Puzzle Tokens become #rExplosive Tokens."; }
		else if (Util.getChallengeLevel() == 10) { return "Start each run #rCursed."; }
		else if (Util.getChallengeLevel() == 11) { return "#b1% chance to become #rFrozen at the start of each turn."; }
		else if (Util.getChallengeLevel() == 12) { return "Start each combat with #b3 #rBurning."; }
		else if (Util.getChallengeLevel() == 13) { return "#y? rooms are more likely to contain fights."; }
		else if (Util.getChallengeLevel() == 14) { return "Booster pack rewards contain #b1 less card."; }
		else if (Util.getChallengeLevel() == 15) { return "Whenever you #ySmith, lose #b35 #yGold."; }
		else if (Util.getChallengeLevel() == 16) { return "#bMillennium #bPuzzle: NL #rExplosive Tokens become #rSuper #rExplosive Tokens."; }
		else if (Util.getChallengeLevel() == 17) { return "While at Rest Sites, randomly disable either #yRest or #ySmith."; }
		else if (Util.getChallengeLevel() == 18) { return "At the start of Elite combats, add a random #rCurse into your discard pile."; }
		else if (Util.getChallengeLevel() == 19) { return "All Bosses and Elites start combat with a random #yBuff."; }
		else if (Util.getChallengeLevel() == 20) { return "#ySummoning is restricted."; }
		else if (showDisabledText) { return "Challenge Mode disabled!"; }
		return "";
	}

	public static void resetCardsPlayedThisRunLists()
	{
		DuelistMod.loadedUniqueMonstersThisRunList = "";
		DuelistMod.loadedSpellsThisRunList = "";
		DuelistMod.loadedTrapsThisRunList = "";
		DuelistMod.entombedCardsThisRunList = "";
		DuelistMod.entombedCustomCardProperites = "";
		DuelistMod.uniqueMonstersThisRun.clear();
		DuelistMod.uniqueSpellsThisRun.clear();
		DuelistMod.uniqueTrapsThisRun.clear();
		DuelistMod.entombedCards.clear();
	}

	public static void fillCardsPlayedThisRunLists()
	{
		if (!DuelistMod.loadedUniqueMonstersThisRunList.equals(""))
		{
			DuelistMod.uniqueMonstersThisRun.clear();
			String[] savedStrings = DuelistMod.loadedUniqueMonstersThisRunList.split("~");
			//Map<String, String> map = new HashMap<>();
			List<String> cards = Arrays.asList(savedStrings);
			for (String s : cards) {
				if (DuelistMod.mapForRunCardsLoading.containsKey(s))
				{
					if (DuelistMod.mapForRunCardsLoading.get(s) instanceof DuelistCard)
					{
						DuelistMod.uniqueMonstersThisRunMap.put(DuelistMod.mapForRunCardsLoading.get(s).cardID, DuelistMod.mapForRunCardsLoading.get(s));
						DuelistMod.uniqueMonstersThisRun.add((DuelistCard) DuelistMod.mapForRunCardsLoading.get(s).makeStatEquivalentCopy());
					}
					else { Util.log("fillCardsPlayedThisRunLists found " + s + " in the map, but it was not a DuelistCard!"); }
				}
				else
				{
					Util.log("fillCardsPlayedThisRunLists did not find " + s + " in the map!");
				}
			}
		}

		if (!DuelistMod.loadedSpellsThisRunList.equals(""))
		{
			DuelistMod.uniqueSpellsThisRun.clear();
			String[] savedStrings = DuelistMod.loadedSpellsThisRunList.split("~");
			for (String s : savedStrings) {
				if (DuelistMod.mapForRunCardsLoading.containsKey(s))
				{
					if (DuelistMod.mapForRunCardsLoading.get(s) instanceof DuelistCard)
					{
						DuelistMod.uniqueSpellsThisRunMap.put(DuelistMod.mapForRunCardsLoading.get(s).cardID, DuelistMod.mapForRunCardsLoading.get(s));
						DuelistMod.uniqueSpellsThisRun.add((DuelistCard) DuelistMod.mapForRunCardsLoading.get(s).makeStatEquivalentCopy());
					}
					else { Util.log("fillCardsPlayedThisRunLists found " + s + " in the map, but it was not a DuelistCard!"); }
				}
				else
				{
					Util.log("fillCardsPlayedThisRunLists did not find " + s + " in the map!");
				}
			}
		}

		if (!DuelistMod.loadedTrapsThisRunList.equals(""))
		{
			DuelistMod.uniqueTrapsThisRun.clear();
			String[] savedStrings = DuelistMod.loadedTrapsThisRunList.split("~");
			for (String s : savedStrings) {
				if (DuelistMod.mapForRunCardsLoading.containsKey(s))
				{
					if (DuelistMod.mapForRunCardsLoading.get(s) instanceof DuelistCard)
					{
						DuelistMod.uniqueTrapsThisRunMap.put(DuelistMod.mapForRunCardsLoading.get(s).cardID, DuelistMod.mapForRunCardsLoading.get(s));
						DuelistMod.uniqueTrapsThisRun.add((DuelistCard) DuelistMod.mapForRunCardsLoading.get(s).makeStatEquivalentCopy());
					}
					else { Util.log("fillCardsPlayedThisRunLists found " + s + " in the map, but it was not a DuelistCard!"); }
				}
				else
				{
					Util.log("fillCardsPlayedThisRunLists did not find " + s + " in the map!");
				}
			}
		}

		if (!DuelistMod.entombedCardsThisRunList.equals(""))
		{
			DuelistMod.entombedCards.clear();
			try
			{
				String[] savedStrings = DuelistMod.entombedCardsThisRunList.split("~");
				Map<String, Integer> mapp = new HashMap<>();
				for (String s : savedStrings)
				{
					try
					{
						String[] splitt = s.split("@");
						try
						{
							Integer i = Integer.parseInt(splitt[1]);
							mapp.put(splitt[0], i);
						} catch (NumberFormatException e) { e.printStackTrace(); Util.log("Util.fillCardsPlayedThisRunLists() is getting a NumberFormatException. Entombed cards probably are not loading properly."); }
					} catch (PatternSyntaxException e) { e.printStackTrace(); Util.log("Util.fillCardsPlayedThisRunLists() is getting a PatternSyntaxException. Entombed cards probably are not loading properly."); }
				}
				for (Entry<String, Integer> i : mapp.entrySet())
				{
					if (DuelistMod.mapForRunCardsLoading.containsKey(i.getKey()))
					{
						AbstractCard ra = DuelistMod.mapForRunCardsLoading.get(i.getKey()).makeStatEquivalentCopy();
						if (ra instanceof CustomResummonCard && !DuelistMod.entombedCustomCardProperites.equals("")) { ((CustomResummonCard)ra).loadAttributes(DuelistMod.entombedCustomCardProperites); Util.log("Attempting to reload the proper Custom Special Summon Card when filling the entomb list..."); }
						for (int j = 0; j < i.getValue(); j++)
						{
							if (ra.canUpgrade()) { ra.upgrade(); }
						}
						DuelistMod.entombedCards.add(ra);
						Util.log("Adding " + ra.cardID + " to EntombedCards list properly");
					}
					else
					{
						Util.log("Entombed Cards Load skipped " + i.getKey() + " because it was not found in the map!");
					}
				}
			} catch (PatternSyntaxException e) { e.printStackTrace(); Util.log("Util.fillCardsPlayedThisRunLists() is getting a PatternSyntaxException for the entire string of Entombed cards. Entombed cards probably are not loading properly."); }
		}
	}

	public static boolean canRevive(int amt, boolean noSoulCost)
	{
		if (DuelistMod.entombedCardsCombat.size() > 0 && ((DuelistMod.currentZombieSouls >= (DuelistCard.getCurrentReviveCost() * amt)) || noSoulCost))
		{
			boolean amtInc = true;
			AbstractPlayer p = AbstractDungeon.player;
			for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc = ((DuelistPotion)pot).allowRevive(); if (!amtInc) { return false; }}}
			for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc = ((DuelistRelic)r).allowRevive(); if (!amtInc) { return false; }}}
			for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc = ((DuelistOrb)o).allowRevive(); if (!amtInc) { return false; }}}
			for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc = ((DuelistPower)pow).allowRevive(); if (!amtInc) { return false; }}}
			for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowReviveWhileInHand(); if (!amtInc) { return false; }}}
			for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowReviveWhileInDiscard(); if (!amtInc) { return false; }}}
			for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowReviveWhileInDraw(); if (!amtInc) { return false; }}}
			for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowReviveWhileInExhaust(); if (!amtInc) { return false; }}}
			for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowReviveWhileInGraveyard(); if (!amtInc) { return false; }}}
			if (p.hasPower(SummonPower.POWER_ID)) {
				SummonPower pow = (SummonPower)p.getPower(SummonPower.POWER_ID);
				for (DuelistCard c : pow.getCardsSummoned()) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowReviveWhileSummoned(); if (!amtInc) { return false; }}}
			}
			return amtInc;
		}
		else
		{
			return false;
		}
	}

	public static boolean canEntomb(AbstractCard c)
	{
		if (c instanceof LichLord) { return false; }
		boolean entombedListContains = false;
		if (!(c instanceof ZombieCorpse)) {
			for (AbstractCard card : DuelistMod.entombedCards)
			{
				if (card.cardID.equals(c.cardID)) { entombedListContains = true; break; }
			}
		}
		if (!Util.isExempt(c) && c.hasTag(Tags.ZOMBIE) && !entombedListContains)
		{
			return true;
		}
		else if (AbstractDungeon.player.hasRelic(GraveToken.ID) && !entombedListContains && !Util.isExempt(c) && c.hasTag(Tags.MONSTER))
		{
			return true;
		}
		return false;
	}

	public static void entombCard(AbstractCard c)
	{
		if (!Util.isExempt(c))
		{
			if (c instanceof ZombieCorpse) {
				DuelistMod.corpsesEntombed++;
			}
			else {
				DuelistMod.battleEntombedList += c.cardID + "@" + c.timesUpgraded + "~";
			}
			DuelistMod.entombedCards.add(c.makeStatEquivalentCopy());
			AbstractDungeon.player.masterDeck.removeCard(c);
			if (!c.hasTag(Tags.CORPSE)) {
				AbstractDungeon.player.masterDeck.addToBottom(new ZombieCorpse());
			}
		}
		else { Util.log("Attempted to Entomb a non-Zombie or an Exempt card, so we skipped it."); }
	}

	public static void removeRelicFromPools(String relicID)
	{
		AbstractDungeon.commonRelicPool.remove(relicID);
		AbstractDungeon.uncommonRelicPool.remove(relicID);
		AbstractDungeon.rareRelicPool.remove(relicID);
		AbstractDungeon.shopRelicPool.remove(relicID);
		AbstractDungeon.bossRelicPool.remove(relicID);
	}

	public static boolean refreshShop()
	{
		if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() instanceof ShopRoom)
		{
			ShopScreen shopScreen = AbstractDungeon.shopScreen;
			if (shopScreen == null) { return false; }
			boolean remove = shopScreen.purgeAvailable;
			try {
				Field colorlessCards = ShopScreen.class.getDeclaredField("colorlessCards");
				colorlessCards.setAccessible(true);
				boolean shouldReset = false;
				ArrayList<AbstractCard> grey = new ArrayList<>((ArrayList<AbstractCard>)colorlessCards.get(shopScreen));
				for (AbstractCard c : grey) {
					if (c instanceof DuelistCard || !c.color.equals(CardColor.COLORLESS)) {
						shouldReset = true;
						break;
					}
				}
				if (!shouldReset) { return false; }

				ArrayList<AbstractCard> newColored = new ArrayList<AbstractCard>();
				ArrayList<AbstractCard> newColorless = new ArrayList<AbstractCard>();

				// 4 Regular Card Slots
				if (DuelistMod.nonPowers.size() > 0) { for (int i = 0; i < 4; i++) { newColored.add(DuelistMod.nonPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.nonPowers.size() - 1)).makeCopy()); }}
				else { for (int i = 0; i < 4; i++) { newColored.add(DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)).makeCopy()); }}

				// Power Slot
				if (DuelistMod.merchantPendantPowers.size() > 0) { newColored.add(DuelistMod.merchantPendantPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.merchantPendantPowers.size() - 1)).makeCopy()); }
				else { newColored.add(DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)).makeCopy()); }

				// Colorless Slots
				AbstractCard left = ColorlessShop.getCard(true);
				AbstractCard right = ColorlessShop.getCard(false);
				ShopScreenPatches.PurchaseCardPrefixPatch.setPrice(left);
				ShopScreenPatches.PurchaseCardPrefixPatch.setPrice(right);
				DuelistMod.colorlessShopCardUUIDs.clear();
				DuelistMod.colorlessShopCardUUIDs.add(left.uuid);
				DuelistMod.colorlessShopCardUUIDs.add(right.uuid);
				DuelistMod.colorlessShopSlotLeft = left.uuid;
				DuelistMod.colorlessShopSlotRight = right.uuid;
				for (final AbstractRelic r : AbstractDungeon.player.relics) {
					r.onPreviewObtainCard(left);
					r.onPreviewObtainCard(right);
				}
				newColorless.add(left);
				newColorless.add(right);

				colorlessCards.set(shopScreen, newColorless);
				Field coloredCards = ShopScreen.class.getDeclaredField("coloredCards");
				coloredCards.setAccessible(true);
				coloredCards.set(shopScreen, newColored);
				Method initCards = ShopScreen.class.getDeclaredMethod("initCards");
				initCards.setAccessible(true);
				initCards.invoke(shopScreen, new Object[] {});
				Field shopRelics = ShopScreen.class.getDeclaredField("relics");
				shopRelics.setAccessible(true);
				ArrayList<StoreRelic> relics = new ArrayList<>((ArrayList<StoreRelic>) shopRelics.get(shopScreen));
				// Add rerolled Items back to relicPool and shuffle them
				for (StoreRelic sr : relics) {
					AbstractRelic relic = sr.relic;
					if (relic != null && !AbstractDungeon.player.hasRelic(relic.relicId)) {
						ArrayList<String> tmp = new ArrayList<>();
						switch (relic.tier) {
							case COMMON:
								tmp.add(relic.relicId);
								tmp.addAll(AbstractDungeon.commonRelicPool);
								AbstractDungeon.commonRelicPool = tmp;
								Collections.shuffle(AbstractDungeon.commonRelicPool, new java.util.Random(AbstractDungeon.merchantRng.randomLong()));
								break;
							case UNCOMMON:
								tmp.add(relic.relicId);
								tmp.addAll(AbstractDungeon.uncommonRelicPool);
								AbstractDungeon.uncommonRelicPool = tmp;
								Collections.shuffle(AbstractDungeon.uncommonRelicPool, new java.util.Random(AbstractDungeon.merchantRng.randomLong()));
								break;
							case RARE:
								tmp.add(relic.relicId);
								tmp.addAll(AbstractDungeon.rareRelicPool);
								AbstractDungeon.rareRelicPool = tmp;
								Collections.shuffle(AbstractDungeon.rareRelicPool, new java.util.Random(AbstractDungeon.merchantRng.randomLong()));
								break;
							case SHOP:
								tmp.add(relic.relicId);
								tmp.addAll(AbstractDungeon.shopRelicPool);
								AbstractDungeon.shopRelicPool = tmp;
								Collections.shuffle(AbstractDungeon.shopRelicPool, new java.util.Random(AbstractDungeon.merchantRng.randomLong()));
								break;
							default:
								Util.log("Unexpected Relic Tier: " + relic.tier);
								break;
						}
					}
				}
				Method initRelics = ShopScreen.class.getDeclaredMethod("initRelics");
				initRelics.setAccessible(true);
				initRelics.invoke(shopScreen);

				Method potions = ShopScreen.class.getDeclaredMethod("initPotions");
				potions.setAccessible(true);
				potions.invoke(shopScreen);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

	    	shopScreen.purgeAvailable = remove;
	    	return true;
		}
		else { return false; }
	}

	// NATURIA - Resistance to Vines Helpers
	// These functions are run at the start of each battle
	// Nothing should be applied unless the player is using Naturia deck or has Naturia cards

	// Bosses
	// Always get resistance to Vines
	// On A17+, 10 or 20% extra resistance is added on to each combat
	// Otherwise, resistance percentage is (act num * 10) + 30
	public static void handleBossResistNature(boolean wasBossCombat)
	{
		boolean naturia = Util.deckIs("Naturia Deck");
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}

		wasBossCombat = wasBossCombat || AbstractDungeon.lastCombatMetricKey.equals("Mind Bloom Boss Battle");

		// For Naturia deck or if player has Naturia cards in deck
		if (wasBossCombat && naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (mons.type.equals(EnemyType.BOSS))
				{
					int roll = AbstractDungeon.actNum + 3;
					if (AbstractDungeon.ascensionLevel > 16) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
					if (Util.getChallengeLevel() > 3) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
				else if (!mons.hasPower(MinionPower.POWER_ID)) { Util.log("Found non-minion, non-boss enemy in a boss room. Should this have Resistance? Enemy=" + mons.name); }
			}
		}
	}

	// Elites
	// Can get Resistance to Vines on A17+
	// On A20, 10 or 20% extra resistance is added on to each combat
	// Otherwise, resistance percentage is (act num * 10) + 10
	public static void handleEliteResistNature(boolean wasEliteCombat)
	{
		if (AbstractDungeon.ascensionLevel < 17 && Util.getChallengeLevel() < 0) { return; }
		boolean naturia = Util.deckIs("Naturia Deck");
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}

		// For Naturia deck or if player has Naturia cards in deck
		if (wasEliteCombat && naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (mons.type.equals(EnemyType.ELITE))
				{
					int roll = AbstractDungeon.actNum + 1;
					if (AbstractDungeon.ascensionLevel > 19) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
					if (Util.getChallengeLevel() > 3) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
			}
		}
	}

	// Hallways
	// Can get Resistance to Vines on A19+
	// On A20, Resistance is always applied
	// Otherwise, resistance is applied to 25% of all non-Elite/non-Boss room enemies
	// Resistance percentage for hallways is randomly chosen to be 10, 20, or 30%
	public static void handleHallwayResistNature()
	{
		if (AbstractDungeon.ascensionLevel < 19 && Util.getChallengeLevel() < 0) { return; }
		boolean naturia = Util.deckIs("Naturia Deck");
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}

		// For Naturia deck or if player has Naturia cards in deck
		if (naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (AbstractDungeon.ascensionLevel > 19)
				{
					int roll = AbstractDungeon.cardRandomRng.random(1, AbstractDungeon.actNum);
					if (Util.getChallengeLevel() > 3) { roll++; }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
				else if (AbstractDungeon.cardRandomRng.random(1, 4) == 1)
				{
					int roll = AbstractDungeon.cardRandomRng.random(1, AbstractDungeon.actNum);
					if (Util.getChallengeLevel() > 3) { roll++; }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
			}
		}
	}

	public static boolean notHasBoosterRewardRelic() {
		if (AbstractDungeon.player == null || AbstractDungeon.player.relics == null) return true;

		for (AbstractRelic r : AbstractDungeon.player.relics) {
			if (r instanceof BoosterRewardRelic) {
				return false;
			}
		}
		return true;
	}

	public static boolean hasCardRewardRelic() {
		if (AbstractDungeon.player == null || AbstractDungeon.player.relics == null) return true;

		for (AbstractRelic r : AbstractDungeon.player.relics) {
			if (r instanceof CardRewardRelic) {
				return false;
			}
		}
		return true;
	}

	public static boolean notHasShopDupeRelic() {
		if (AbstractDungeon.player == null || AbstractDungeon.player.relics == null) return false;

		for (AbstractRelic r : AbstractDungeon.player.relics) {
			if (r instanceof ShopDupeRelic) {
				return true;
			}
		}
		return false;
	}

	public static ExplodingTokenDamageResult getExplodingTokenDamageInfo(boolean superExploding) {
		try {
			int low = DuelistMod.persistentDuelistData.CardConfigurations.getExplosiveDamageLow();
			int high = DuelistMod.persistentDuelistData.CardConfigurations.getExplosiveDamageHigh();
			if (AbstractDungeon.player != null && AbstractDungeon.player.relics != null) {
				for (AbstractRelic r : AbstractDungeon.player.relics) {
					if (r instanceof MachineOrb) {
						low += 2;
						high += 3;
					}
				}
			}
			if (low <= 0 && high <= 0) {
				return new ExplodingTokenDamageResult();
			}
			if (low == high && !superExploding) {
				return new ExplodingTokenDamageResult(low, high, low);
			}
			if (low > high) {
				int t = low;
				low = high;
				high = t;
			}
			if (!superExploding) {
				int roll = AbstractDungeon.cardRandomRng.random(low, high);
				return new ExplodingTokenDamageResult(low, high, roll);
			}

			int lowMod = low * (DuelistMod.persistentDuelistData.CardConfigurations.getSuperExplosiveLowMultiplier());
			int highMod = high * (DuelistMod.persistentDuelistData.CardConfigurations.getSuperExplosiveHighMultiplier());
			if (lowMod <= 0 && highMod <= 0) {
				return new ExplodingTokenDamageResult();
			}
			if (lowMod == highMod) {
				return new ExplodingTokenDamageResult(lowMod, highMod, lowMod);
			}
			if (lowMod > highMod) {
				int t = lowMod;
				lowMod = highMod;
				highMod = t;
			}
			int roll = AbstractDungeon.cardRandomRng.random(lowMod, highMod);
			return new ExplodingTokenDamageResult(lowMod, highMod, roll);
		} catch (Exception ignored) {}
		return new ExplodingTokenDamageResult();
	}

	public static int getExplodingTokenDamage(boolean superExploding) {
		return getExplodingTokenDamageInfo(superExploding).damage();
	}

	public static AbstractCard randomize(AbstractCard gridCard, RandomizedOptions options) {
		if (options.isUpgrade()) { gridCard.upgrade(); }
		if (!gridCard.isEthereal && options.isEtherealCheck() && !gridCard.selfRetain)
		{
			gridCard.isEthereal = true;
			gridCard.rawDescription = Strings.etherealForCardText + gridCard.rawDescription;
		}

		if (!gridCard.exhaust && options.isExhaustCheck())
		{
			gridCard.exhaust = true;
			gridCard.rawDescription = gridCard.rawDescription + DuelistMod.exhaustForCardText;
		}

		if (options.isCostChangeCheck() && gridCard.cost > -1)
		{
			int randomNum = AbstractDungeon.cardRandomRng.random(options.getLowCostRoll(), options.getHighCostRoll());
			int gridCardCost = gridCard.cost;
			if (options.isCostChangeCombatCheck())
			{
				gridCard.modifyCostForCombat(-gridCard.cost + randomNum);
				if (randomNum != gridCardCost) { gridCard.isCostModified = true; }
			}
			else
			{
				gridCard.setCostForTurn(-gridCard.cost + randomNum);
				if (randomNum != gridCardCost) { gridCard.isCostModifiedForTurn = true; }
			}
		}

		if (options.isSummonCheck() && gridCard instanceof DuelistCard)
		{
			int randomNum = AbstractDungeon.cardRandomRng.random(options.getLowSummonRoll(), options.getHighSummonRoll());
			DuelistCard dC = (DuelistCard)gridCard;
			if (dC.isSummonCard())
			{
				if (options.isSummonChangeCombatCheck())
				{
					dC.modifySummons(randomNum);
				}
				else
				{
					dC.modifySummonsForTurn(randomNum);
				}
			}
		}

		if (options.isTributeCheck() && gridCard instanceof DuelistCard)
		{
			int randomNum = AbstractDungeon.cardRandomRng.random(options.getLowTributeRoll(), options.getHighTributeRoll());
			DuelistCard dC = (DuelistCard)gridCard;
			if (dC.isTributeCard())
			{
				if (options.isTributeChangeCombatCheck())
				{
					dC.modifyTributes(-randomNum);
				}
				else
				{
					dC.modifyTributesForTurn(-randomNum);
				}
			}
		}

		if (options.isDamageBlockRandomize())
		{
			if (gridCard.damage > 0)
			{
				int low = gridCard.damage * -1;
				int high = gridCard.damage + 6;
				int roll = AbstractDungeon.cardRandomRng.random(low, high);
				AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(gridCard.uuid, roll));
				gridCard.isDamageModified = true;
			}

			if (gridCard.block > 0)
			{
				int low = gridCard.block * -1;
				int high = gridCard.block + 6;
				int roll = AbstractDungeon.cardRandomRng.random(low, high);
				AbstractDungeon.actionManager.addToTop(new ModifyBlockAction(gridCard.uuid, roll));
				gridCard.isBlockModified = true;
			}
		}

		if (options.isDontTrig())
		{
			gridCard.dontTriggerOnUseCard = false;
		}
		if (gridCard instanceof DuelistCard) {
			((DuelistCard)gridCard).fixUpgradeDesc();
		}
		gridCard.initializeDescription();
		return gridCard;
	}

	public static int checkBeastTagAndIncrement(int startSummons, int maxSummons, DuelistCard c, AnyDuelist duelist) {
		Integer check = isAnyBeastEffectTrigger(startSummons, maxSummons, c);
		if (check != null) {
			DuelistCard.incMaxSummons(check, duelist);
			for (AbstractRelic relic : duelist.relics()) {
				if (relic instanceof DuelistRelic) {
					((DuelistRelic)relic).onBeastIncrement(check);
				}
			}
			return check;
		}
		return 0;
	}

	public static int checkBeastTag(int startSummons, int maxSummons, DuelistCard c) {
		Integer check = isAnyBeastEffectTrigger(startSummons, maxSummons, c);
		return check != null ? check : 0;
	}

	private static Integer isAnyBeastEffectTrigger(int startSummons, int maxSummons, DuelistCard c) {
		AnyDuelist duelist = AnyDuelist.from(c);
		boolean summonCheck = startSummons == maxSummons;
		boolean summonAmountCheck = c.summons > 0;
		boolean tagCheck = c.hasTag(Tags.BEAST);
		boolean relicCheck = duelist.hasRelic(NaturesGift.ID);
		boolean finalCheck = summonCheck && summonAmountCheck && (tagCheck || relicCheck);
		int amt = tagCheck ? DuelistMod.getMonsterSetting(MonsterType.BEAST, MonsterType.beastIncKey) : 0;
		NaturesGift giftRelic = relicCheck ? (NaturesGift) duelist.getRelic(NaturesGift.ID) : null;
		if (giftRelic != null) {
			amt += giftRelic.getIncrementAmount();
		}
		return finalCheck ? amt : null;
	}

	public static int modifyTributesForApexFeralTerritorial(AnyDuelist duelist, AbstractCard card, int tributes) {
		boolean hasFeralCard = false;
		boolean hasTerritorialCard = false;
		for (AbstractCard c : duelist.hand()) {
			if (c.hasTag(FERAL)) {
				hasFeralCard = true;
			}
			if (c.hasTag(TERRITORIAL) && c instanceof DuelistCard && ((DuelistCard)c).isTerritorial()) {
				hasTerritorialCard = true;
			}
		}
		if (hasFeralCard && !card.hasTag(Tags.BEAST)) {
			tributes += DuelistMod.beastFeralBump;
		}
		boolean cardIsTerritorial = card.hasTag(TERRITORIAL) && card instanceof DuelistCard && ((DuelistCard)card).isTerritorial();
		if (hasTerritorialCard && !cardIsTerritorial) {
			tributes *= DuelistMod.beastTerritorialMultiplier;
		}

		if (apexLogicCheck(card)) {
			tributes = 0;
		}

		if (card instanceof InfiniteLoopTributeModificationCheckCard) {
			if (card instanceof CyberEndDragon) {
				CyberEndDragon cyberEndDragon = (CyberEndDragon) card;
				if (cyberEndDragon.tributeCondition()) {
					return Math.max(card.magicNumber, 0);
				}
			}

			if (card instanceof AtomicScrapDragon) {
				AtomicScrapDragon atomicScrapDragon = (AtomicScrapDragon) card;
				tributes -= atomicScrapDragon.tributeReduction();
			}
		}

		if (duelist.hasPower(UnicornBeaconPower.POWER_ID) && duelist.getPower(UnicornBeaconPower.POWER_ID).amount > 0) {
			return 0;
		}

		return Math.max(tributes, 0);
	}

	public static boolean apexLogicCheck(AbstractCard card) {
		AnyDuelist duelist = AnyDuelist.from(card);
		boolean isApex = (card.hasTag(Tags.APEX) && card instanceof DuelistCard && ((DuelistCard)card).isApex()) || (duelist.hasRelic(ApexToken.ID) && card.hasTag(Tags.BEAST));
		boolean finalApexLogicCheck = isApex && (AbstractDungeon.actionManager.cardsPlayedThisTurn == null || AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty() || AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().allMatch(c -> c.uuid.equals(card.uuid)));
		if (finalApexLogicCheck && Util.deckIs("Beast Deck") && Util.getChallengeLevel() > 3) {
			return GameActionManager.turn < 3;
		}
		return finalApexLogicCheck;
	}

	public static boolean isExempt(AbstractCard card) {
		return card.hasTag(Tags.EXEMPT) || card.hasTag(Tags.GIANT) || (card instanceof DuelistCard && !((DuelistCard)card).isApex());
	}

	public static boolean revengeActive(AbstractCard card) {
		AnyDuelist duelist = AnyDuelist.from(card);
		return (duelist.player() && DuelistMod.unblockedDamageTakenLastTurn) || (duelist.getEnemy() != null && DuelistMod.enemyDuelistUnblockedDamageTakenLastTurn);
	}

	public static void registerCustomPowers()
	{
		BaseMod.addPower(AerodynamicsPower.class, AerodynamicsPower.POWER_ID);
		BaseMod.addPower(AlphaMagPower.class, AlphaMagPower.POWER_ID);
		BaseMod.addPower(AnteaterPower.class, AnteaterPower.POWER_ID);
		BaseMod.addPower(ArtifactSanctumPower.class, ArtifactSanctumPower.POWER_ID);
		BaseMod.addPower(AutorokketDragonPower.class, AutorokketDragonPower.POWER_ID);
		BaseMod.addPower(BadReactionPower.class, BadReactionPower.POWER_ID);
		BaseMod.addPower(BerserkScalesPower.class, BerserkScalesPower.POWER_ID);
		BaseMod.addPower(BeserkingDebuffA.class, BeserkingDebuffA.POWER_ID);
		BaseMod.addPower(BeserkingDebuffB.class, BeserkingDebuffB.POWER_ID);
		BaseMod.addPower(BeserkingDebuffC.class, BeserkingDebuffC.POWER_ID);
		BaseMod.addPower(BetaMagPower.class, BetaMagPower.POWER_ID);
		BaseMod.addPower(BlackPendantPower.class, BlackPendantPower.POWER_ID);
		BaseMod.addPower(BlastingFusePower.class, BlastingFusePower.POWER_ID);
		BaseMod.addPower(BlizzardDragonPower.class, BlizzardDragonPower.POWER_ID);
		BaseMod.addPower(BloomingDarkRosePower.class, BloomingDarkRosePower.POWER_ID);
		BaseMod.addPower(BoosterDragonPower.class, BoosterDragonPower.POWER_ID);
		BaseMod.addPower(BugMatrixPower.class, BugMatrixPower.POWER_ID);
		BaseMod.addPower(BurningDebuff.class, BurningDebuff.POWER_ID);
		BaseMod.addPower(CallGravePower.class, CallGravePower.POWER_ID);
		BaseMod.addPower(CannonPower.class, CannonPower.POWER_ID);
		BaseMod.addPower(CanyonPower.class, CanyonPower.POWER_ID);
		BaseMod.addPower(CardSafePower.class, CardSafePower.POWER_ID);
		BaseMod.addPower(CastleDragonSoulsPower.class, CastleDragonSoulsPower.POWER_ID);
		BaseMod.addPower(CastlePower.class, CastlePower.POWER_ID);
		BaseMod.addPower(CatapultPower.class, CatapultPower.POWER_ID);
		BaseMod.addPower(CatapultZonePower.class, CatapultZonePower.POWER_ID);
		BaseMod.addPower(CocoonPower.class, CocoonPower.POWER_ID);
		BaseMod.addPower(CommanderSwordsPower.class, CommanderSwordsPower.POWER_ID);
		BaseMod.addPower(ConvulsionNaturePower.class, ConvulsionNaturePower.POWER_ID);
		BaseMod.addPower(CorrosiveScalesPower.class, CorrosiveScalesPower.POWER_ID);
		BaseMod.addPower(CrystalForestPower.class, CrystalForestPower.POWER_ID);
		BaseMod.addPower(CrystalTreePower.class, CrystalTreePower.POWER_ID);
		BaseMod.addPower(CubicKarmaPower.class, CubicKarmaPower.POWER_ID);
		BaseMod.addPower(CyberDragonSiegerPower.class, CyberDragonSiegerPower.POWER_ID);
		BaseMod.addPower(CyberEltaninPower.class, CyberEltaninPower.POWER_ID);
		BaseMod.addPower(CyberLaserDragonPower.class, CyberLaserDragonPower.POWER_ID);
		BaseMod.addPower(CyberneticOverflowDebuffA.class, CyberneticOverflowDebuffA.POWER_ID);
		BaseMod.addPower(CyberneticOverflowDebuffB.class, CyberneticOverflowDebuffB.POWER_ID);
		BaseMod.addPower(DarkMirrorPower.class, DarkMirrorPower.POWER_ID);
		BaseMod.addPower(DarknessNeospherePower.class, DarknessNeospherePower.POWER_ID);
		BaseMod.addPower(DemiseLandPower.class, DemiseLandPower.POWER_ID);
		BaseMod.addPower(DepoweredPower.class, DepoweredPower.POWER_ID);
		BaseMod.addPower(DespairPower.class, DespairPower.POWER_ID);
		BaseMod.addPower(DoomDonutsPower.class, DoomDonutsPower.POWER_ID);
		BaseMod.addPower(DoomdogPower.class, DoomdogPower.POWER_ID);
		BaseMod.addPower(DragonMasteryPower.class, DragonMasteryPower.POWER_ID);
		BaseMod.addPower(DragonMirrorPower.class, DragonMirrorPower.POWER_ID);
		BaseMod.addPower(DragonRavinePower.class, DragonRavinePower.POWER_ID);
		BaseMod.addPower(DragonTreasurePower.class, DragonTreasurePower.POWER_ID);
		BaseMod.addPower(Dragonscales.class, Dragonscales.POWER_ID);
		BaseMod.addPower(DrillBarnaclePower.class, DrillBarnaclePower.POWER_ID);
		BaseMod.addPower(EmperorPower.class, EmperorPower.POWER_ID);
		BaseMod.addPower(EnemyBoosterDragonPower.class, EnemyBoosterDragonPower.POWER_ID);
		BaseMod.addPower(EnemyEnergyPower.class, EnemyEnergyPower.POWER_ID);
		BaseMod.addPower(EnemyExodiaPower.class, EnemyExodiaPower.POWER_ID);
		BaseMod.addPower(EnemyHandPower.class, EnemyHandPower.POWER_ID);
		BaseMod.addPower(EnemyDrawPilePower.class, EnemyDrawPilePower.POWER_ID);
		BaseMod.addPower(EnemyMiraclePower.class, EnemyMiraclePower.POWER_ID);
		BaseMod.addPower(EnemySummonsPower.class, EnemySummonsPower.POWER_ID);
		BaseMod.addPower(EnemyTotemPower.class, EnemyTotemPower.POWER_ID);
		BaseMod.addPower(EvokeSicknessPower.class, EvokeSicknessPower.POWER_ID);
		BaseMod.addPower(ExodiaPower.class, ExodiaPower.POWER_ID);
		BaseMod.addPower(ExodiaRenewalPower.class, ExodiaRenewalPower.POWER_ID);
		BaseMod.addPower(FightingSpiritPower.class, FightingSpiritPower.POWER_ID);
		BaseMod.addPower(FlameTigerPower.class, FlameTigerPower.POWER_ID);
		BaseMod.addPower(FocusDownPower.class, FocusDownPower.POWER_ID);
		BaseMod.addPower(FocusLossPower.class, FocusLossPower.POWER_ID);
		BaseMod.addPower(ForestPower.class, ForestPower.POWER_ID);
		BaseMod.addPower(FrozenDebuff.class, FrozenDebuff.POWER_ID);
		BaseMod.addPower(FrozenRosePower.class, FrozenRosePower.POWER_ID);
		BaseMod.addPower(FutureFusionPower.class, FutureFusionPower.POWER_ID);
		BaseMod.addPower(GalaxySoldierPower.class, GalaxySoldierPower.POWER_ID);
		BaseMod.addPower(GammaMagPower.class, GammaMagPower.POWER_ID);
		BaseMod.addPower(GatesDarkPower.class, GatesDarkPower.POWER_ID);
		BaseMod.addPower(GoblinRemedyPower.class, GoblinRemedyPower.POWER_ID);
		BaseMod.addPower(GravityAxePower.class, GravityAxePower.POWER_ID);
		BaseMod.addPower(GreedShardPower.class, GreedShardPower.POWER_ID);
		BaseMod.addPower(GridRodPower.class, GridRodPower.POWER_ID);
		BaseMod.addPower(HauntedDebuff.class, HauntedDebuff.POWER_ID);
		BaseMod.addPower(HauntedPower.class, HauntedPower.POWER_ID);
		BaseMod.addPower(HealGoldPower.class, HealGoldPower.POWER_ID);
		BaseMod.addPower(HeartUnderdogPower.class, HeartUnderdogPower.POWER_ID);
		BaseMod.addPower(HeartUnderspellPower.class, HeartUnderspellPower.POWER_ID);
		BaseMod.addPower(HeartUndertrapPower.class, HeartUndertrapPower.POWER_ID);
		BaseMod.addPower(HeartUndertributePower.class, HeartUndertributePower.POWER_ID);
		BaseMod.addPower(IgnisHeatPower.class, IgnisHeatPower.POWER_ID);
		BaseMod.addPower(IlBludPower.class, IlBludPower.POWER_ID);
		BaseMod.addPower(ImperialPower.class, ImperialPower.POWER_ID);
		BaseMod.addPower(InariFirePower.class, InariFirePower.POWER_ID);
		BaseMod.addPower(InfernityDoomPower.class, InfernityDoomPower.POWER_ID);
		BaseMod.addPower(JinzoPower.class, JinzoPower.POWER_ID);
		BaseMod.addPower(JumboDrillPower.class, JumboDrillPower.POWER_ID);
		BaseMod.addPower(JurassicImpactPower.class, JurassicImpactPower.POWER_ID);
		BaseMod.addPower(LairWirePower.class, LairWirePower.POWER_ID);
		BaseMod.addPower(LeavesPower.class, LeavesPower.POWER_ID);
		BaseMod.addPower(LeodrakeManePower.class, LeodrakeManePower.POWER_ID);
		BaseMod.addPower(LostWorldPower.class, LostWorldPower.POWER_ID);
		BaseMod.addPower(MagiciansRobePower.class, MagiciansRobePower.POWER_ID);
		BaseMod.addPower(MagiciansRodPower.class, MagiciansRodPower.POWER_ID);
		BaseMod.addPower(MagickaPower.class, MagickaPower.POWER_ID);
		BaseMod.addPower(MagneticFieldPower.class, MagneticFieldPower.POWER_ID);
		BaseMod.addPower(MillenniumSpellbookPower.class, MillenniumSpellbookPower.POWER_ID);
		BaseMod.addPower(MiraculousDescentPower.class, MillenniumSpellbookPower.POWER_ID);
		BaseMod.addPower(MirrorForcePower.class, MirrorForcePower.POWER_ID);
		BaseMod.addPower(MortalityPower.class, MortalityPower.POWER_ID);
		BaseMod.addPower(MountainPower.class, MountainPower.POWER_ID);
		BaseMod.addPower(NaturalDisasterPower.class, NaturalDisasterPower.POWER_ID);
		BaseMod.addPower(NatureReflectionPower.class, NatureReflectionPower.POWER_ID);
		BaseMod.addPower(NaturiaForestPower.class, NaturiaForestPower.POWER_ID);
		BaseMod.addPower(NaturiaLeodrakePower.class, NaturiaLeodrakePower.POWER_ID);
		BaseMod.addPower(NaturiaStagBeetlePower.class, NaturiaStagBeetlePower.POWER_ID);
		BaseMod.addPower(NaturiaVeinPower.class, NaturiaVeinPower.POWER_ID);
		BaseMod.addPower(ObeliskPower.class, ObeliskPower.POWER_ID);
		BaseMod.addPower(OniPower.class, OniPower.POWER_ID);
		BaseMod.addPower(OrbEvokerPower.class, OrbEvokerPower.POWER_ID);
		BaseMod.addPower(OrbHealerPower.class, OrbHealerPower.POWER_ID);
		BaseMod.addPower(OutriggerExtensionPower.class, OutriggerExtensionPower.POWER_ID);
		BaseMod.addPower(ParasitePower.class, ParasitePower.POWER_ID);
		BaseMod.addPower(PoseidonWavePower.class, PoseidonWavePower.POWER_ID);
		BaseMod.addPower(PotGenerosityPower.class, PotGenerosityPower.POWER_ID);
		BaseMod.addPower(RadiantMirrorPower.class, RadiantMirrorPower.POWER_ID);
		BaseMod.addPower(RainbowCapturePower.class, RainbowCapturePower.POWER_ID);
		BaseMod.addPower(RainbowRefractionPower.class, RainbowRefractionPower.POWER_ID);
		BaseMod.addPower(RainbowRuinsPower.class, RainbowRuinsPower.POWER_ID);
		BaseMod.addPower(RedMirrorPower.class, RedMirrorPower.POWER_ID);
		BaseMod.addPower(ReducerPower.class, ReducerPower.POWER_ID);
		BaseMod.addPower(ReinforceTruthPower.class, ReinforceTruthPower.POWER_ID);
		BaseMod.addPower(ReinforcementsPower.class, ReinforcementsPower.POWER_ID);
		BaseMod.addPower(ReleaseFromStonePower.class, ReleaseFromStonePower.POWER_ID);
		BaseMod.addPower(ResistNatureEnemyPower.class, ResistNatureEnemyPower.POWER_ID);
		BaseMod.addPower(ResummonBonusPower.class, ResummonBonusPower.POWER_ID);
		BaseMod.addPower(RetainForTurnsPower.class, RetainForTurnsPower.POWER_ID);
		BaseMod.addPower(SacredTreePower.class, SacredTreePower.POWER_ID);
		BaseMod.addPower(SarraceniantPower.class, SarraceniantPower.POWER_ID);
		BaseMod.addPower(SeedCannonPower.class, SeedCannonPower.POWER_ID);
		BaseMod.addPower(SilverWingPower.class, SilverWingPower.POWER_ID);
		BaseMod.addPower(SliferSkyPower.class, SliferSkyPower.POWER_ID);
		BaseMod.addPower(SogenPower.class, SogenPower.POWER_ID);
		BaseMod.addPower(SolidarityDiscardPower.class, SolidarityDiscardPower.POWER_ID);
		BaseMod.addPower(SolidarityExhaustPower.class, SolidarityExhaustPower.POWER_ID);
		BaseMod.addPower(SoulBonePower.class, SoulBonePower.POWER_ID);
		BaseMod.addPower(SpellbookKnowledgePower.class, SpellbookKnowledgePower.POWER_ID);
		BaseMod.addPower(SpellbookLifePower.class, SpellbookLifePower.POWER_ID);
		BaseMod.addPower(SpellbookMiraclePower.class, SpellbookMiraclePower.POWER_ID);
		BaseMod.addPower(SpellbookPowerPower.class, SpellbookPowerPower.POWER_ID);
		BaseMod.addPower(SphereKuribohPower.class, SphereKuribohPower.POWER_ID);
		BaseMod.addPower(SpikedGillmanPower.class, SpikedGillmanPower.POWER_ID);
		BaseMod.addPower(SpiritForcePower.class, SpiritForcePower.POWER_ID);
		BaseMod.addPower(SpiritualForestPower.class, SpiritualForestPower.POWER_ID);
		BaseMod.addPower(SplendidRosePower.class, SplendidRosePower.POWER_ID);
		BaseMod.addPower(StatueAnguishPatternPower.class, StatueAnguishPatternPower.POWER_ID);
		BaseMod.addPower(StormingMirrorPower.class, StormingMirrorPower.POWER_ID);
		BaseMod.addPower(StrengthDownPower.class, StrengthDownPower.POWER_ID);
		BaseMod.addPower(SummonPower.class, SummonPower.POWER_ID);
		BaseMod.addPower(SummonSicknessPower.class, SummonSicknessPower.POWER_ID);
		BaseMod.addPower(SuperheavyDexGainPower.class, SuperheavyDexGainPower.POWER_ID);
		BaseMod.addPower(SurvivalFittestPower.class, SurvivalFittestPower.POWER_ID);
		BaseMod.addPower(SwordDeepPower.class, SwordDeepPower.POWER_ID);
		BaseMod.addPower(SwordsBurnPower.class, SwordsBurnPower.POWER_ID);
		BaseMod.addPower(SwordsConcealPower.class, SwordsConcealPower.POWER_ID);
		BaseMod.addPower(SwordsRevealPower.class, SwordsRevealPower.POWER_ID);
		BaseMod.addPower(TimeWizardPower.class, TimeWizardPower.POWER_ID);
		BaseMod.addPower(TombLooterPower.class, TombLooterPower.POWER_ID);
		BaseMod.addPower(ToonBriefcasePower.class, ToonBriefcasePower.POWER_ID);
		BaseMod.addPower(ToonCannonPower.class, ToonCannonPower.POWER_ID);
		BaseMod.addPower(ToonKingdomPower.class, ToonKingdomPower.POWER_ID);
		BaseMod.addPower(ToonRollbackPower.class, ToonRollbackPower.POWER_ID);
		BaseMod.addPower(ToonWorldPower.class, ToonWorldPower.POWER_ID);
		BaseMod.addPower(TotemDragonPower.class, TotemDragonPower.POWER_ID);
		BaseMod.addPower(TrapHolePower.class, TrapHolePower.POWER_ID);
		BaseMod.addPower(TributeSicknessPower.class, TributeSicknessPower.POWER_ID);
		BaseMod.addPower(TributeToonPower.class, TributeToonPower.POWER_ID);
		BaseMod.addPower(TributeToonPowerB.class, TributeToonPowerB.POWER_ID);
		BaseMod.addPower(TurretWarriorPower.class, TurretWarriorPower.POWER_ID);
		BaseMod.addPower(TwoJamPower.class, TwoJamPower.POWER_ID);
		BaseMod.addPower(TyrantWingPower.class, TyrantWingPower.POWER_ID);
		BaseMod.addPower(UltimateOfferingPower.class, UltimateOfferingPower.POWER_ID);
		BaseMod.addPower(UmiPower.class, UmiPower.POWER_ID);
		BaseMod.addPower(VanDragPower.class, VanDragPower.POWER_ID);
		BaseMod.addPower(VinesPower.class, VinesPower.POWER_ID);
		BaseMod.addPower(VioletCrystalPower.class, VioletCrystalPower.POWER_ID);
		BaseMod.addPower(VoidExpansionPower.class, VoidExpansionPower.POWER_ID);
		BaseMod.addPower(VoidVanishmentPower.class, VoidVanishmentPower.POWER_ID);
		BaseMod.addPower(WallThornsPower.class, WallThornsPower.POWER_ID);
		BaseMod.addPower(WeaponChangePower.class, WeaponChangePower.POWER_ID);
		BaseMod.addPower(WhiteHornDragonPower.class, WhiteHornDragonPower.POWER_ID);
		BaseMod.addPower(WhiteHowlingPower.class, WhiteHowlingPower.POWER_ID);
		BaseMod.addPower(WonderWandPower.class, WonderWandPower.POWER_ID);
		BaseMod.addPower(WorldTreePower.class, WorldTreePower.POWER_ID);
		BaseMod.addPower(YamiFormPower.class, YamiFormPower.POWER_ID);
		BaseMod.addPower(YamiPower.class, YamiPower.POWER_ID);
		BaseMod.addPower(FluxPower.class, FluxPower.POWER_ID);
		BaseMod.addPower(DoublePlayFirstCardPower.class, DoublePlayFirstCardPower.POWER_ID);
		BaseMod.addPower(GeartownPower.class, GeartownPower.POWER_ID);
		BaseMod.addPower(GreasedDebuff.class, GreasedDebuff.POWER_ID);
		BaseMod.addPower(JinzoLordPower.class, JinzoLordPower.POWER_ID);
		BaseMod.addPower(MetalholdMovingBlockadePower.class, MetalholdMovingBlockadePower.POWER_ID);
		BaseMod.addPower(OverworkedPower.class, OverworkedPower.POWER_ID);
		BaseMod.addPower(RevolvingSwitchyardPower.class, RevolvingSwitchyardPower.POWER_ID);
		BaseMod.addPower(RoboticKnightPower.class, RoboticKnightPower.POWER_ID);
		BaseMod.addPower(UnionHangarPower.class, UnionHangarPower.POWER_ID);
		BaseMod.addPower(WonderGaragePower.class, WonderGaragePower.POWER_ID);
		BaseMod.addPower(MagicCylinderPower.class, MagicCylinderPower.POWER_ID);
		BaseMod.addPower(ElectricityPower.class, ElectricityPower.POWER_ID);
		BaseMod.addPower(FishscalesPower.class, FishscalesPower.POWER_ID);
		BaseMod.addPower(BloodPower.class, BloodPower.POWER_ID);
		BaseMod.addPower(FocusUpPower.class, FocusUpPower.POWER_ID);
		BaseMod.addPower(StrengthUpPower.class, StrengthUpPower.POWER_ID);
		BaseMod.addPower(TricksPower.class, TricksPower.POWER_ID);
		BaseMod.addPower(ArcanaPower.class, ArcanaPower.POWER_ID);
		BaseMod.addPower(MegaconfusionPower.class, MegaconfusionPower.POWER_ID);
		BaseMod.addPower(SeaDwellerPower.class, SeaDwellerPower.POWER_ID);
		BaseMod.addPower(ZONEPower.class, ZONEPower.POWER_ID);
		BaseMod.addPower(BookTaiyouPower.class, BookTaiyouPower.POWER_ID);
		BaseMod.addPower(NoResummoningAttacksCombatPower.class, NoResummoningAttacksCombatPower.POWER_ID);
		BaseMod.addPower(NoResummoningAttacksPower.class, NoResummoningAttacksPower.POWER_ID);
		BaseMod.addPower(NoResummoningMonstersCombatPower.class, NoResummoningMonstersCombatPower.POWER_ID);
		BaseMod.addPower(NoResummoningMonstersPower.class, NoResummoningMonstersPower.POWER_ID);
		BaseMod.addPower(NoResummoningPower.class, NoResummoningPower.POWER_ID);
		BaseMod.addPower(NoResummoningPowersCombatPower.class, NoResummoningPowersCombatPower.POWER_ID);
		BaseMod.addPower(NoResummoningPowersPower.class, NoResummoningPowersPower.POWER_ID);
		BaseMod.addPower(NoResummoningSkillsCombatPower.class, NoResummoningSkillsCombatPower.POWER_ID);
		BaseMod.addPower(NoResummoningSkillsPower.class, NoResummoningSkillsPower.POWER_ID);
		BaseMod.addPower(NoResummoningSpellsCombatPower.class, NoResummoningSpellsCombatPower.POWER_ID);
		BaseMod.addPower(NoResummoningSpellsPower.class, NoResummoningSpellsPower.POWER_ID);
		BaseMod.addPower(NoResummoningTrapsCombatPower.class, NoResummoningTrapsCombatPower.POWER_ID);
		BaseMod.addPower(NoResummoningTrapsPower.class, NoResummoningTrapsPower.POWER_ID);
		BaseMod.addPower(NoSpellsPower.class, NoSpellsPower.POWER_ID);
		BaseMod.addPower(NoTrapsPower.class, NoTrapsPower.POWER_ID);
		BaseMod.addPower(MonsterRestrictionsPower.class, MonsterRestrictionsPower.POWER_ID);
		BaseMod.addPower(ColdEnchanterPower.class, ColdEnchanterPower.POWER_ID);
		BaseMod.addPower(IronChainDragonPower.class, IronChainDragonPower.POWER_ID);
		BaseMod.addPower(RedRisingDragonPower.class, RedRisingDragonPower.POWER_ID);
		BaseMod.addPower(BeastFrenzyPower.class, BeastFrenzyPower.POWER_ID);
		BaseMod.addPower(BeastRisingPower.class, BeastRisingPower.POWER_ID);
	}

}
