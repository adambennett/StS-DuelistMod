package duelistmod.helpers;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.enums.StartingDeck;

public class BonusDeckUnlockHelper {

	public static boolean challengeUnlocked(StartingDeck deck) {
		if (DuelistMod.persistentDuelistData.GameplaySettings.getUnlockChallengeMode()) return true;
		return deck.getActiveConfig().getStats().getChallengeLevel() != null && deck.getActiveConfig().getStats().getChallengeLevel() > -1;
	}

	public static int challengeLevel(StartingDeck deck) {
		if (DuelistMod.persistentDuelistData.GameplaySettings.getUnlockChallengeMode()) return 20;
		return deck.getActiveConfig().getStats().getChallengeLevel() != null ?  deck.getActiveConfig().getStats().getChallengeLevel() : 0;
	}

	public static void beatHeart() {
		if (!Settings.isStandardRun()) {
			Util.log("Non-standard run, not saving heart kill");
			return;
		}

		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		config.getStats().getHeartKills().increment(AbstractDungeon.ascensionLevel, Util.getChallengeLevel());

		if (AbstractDungeon.ascensionLevel > 4) {
			DuelistMod.persistentDuelistData.deckUnlockProgress.setAscendedDeckOneUnlocked(true);
		}
		if (AbstractDungeon.ascensionLevel > 9) {
			DuelistMod.persistentDuelistData.deckUnlockProgress.setAscendedDeckTwoUnlocked(true);
		}
		if (AbstractDungeon.ascensionLevel > 14) {
			DuelistMod.persistentDuelistData.deckUnlockProgress.setAscendedDeckThreeUnlocked(true);
		}

		StartingDeck.currentDeck.updateConfigSettings(config);
	}

	public static void onWin() {
		if (!Settings.isStandardRun()) {
			Util.log("Non-standard run, not saving victory");
			return;
		}

		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		config.getStats().getVictories().increment(AbstractDungeon.ascensionLevel, Util.getChallengeLevel());
		int currentChallenge = config.getStats().getChallengeLevel();
		if (currentChallenge <= Util.getChallengeLevel() && AbstractDungeon.ascensionLevel >= 20) {
			config.getStats().setChallengeLevel(currentChallenge + 1);
		}

		if (Util.getChallengeLevel() > 0) {
			DuelistMod.persistentDuelistData.deckUnlockProgress.setPharaohDeckOneUnlocked(true);
		}
		if (AbstractDungeon.ascensionLevel > 4 && Util.deckIs(StartingDeck.PHARAOH_I.getDeckName())) {
			DuelistMod.persistentDuelistData.deckUnlockProgress.setPharaohDeckTwoUnlocked(true);
		}
		boolean anyPharaohDeck = Util.deckIs(StartingDeck.PHARAOH_I.getDeckName(), StartingDeck.PHARAOH_II.getDeckName(), StartingDeck.PHARAOH_III.getDeckName(), StartingDeck.PHARAOH_IV.getDeckName(), StartingDeck.PHARAOH_V.getDeckName());
		if (AbstractDungeon.ascensionLevel > 9 && anyPharaohDeck) {
			DuelistMod.persistentDuelistData.deckUnlockProgress.setPharaohDeckThreeUnlocked(true);
		}
		if (AbstractDungeon.ascensionLevel > 14 && anyPharaohDeck) {
			DuelistMod.persistentDuelistData.deckUnlockProgress.setPharaohDeckFourUnlocked(true);
		}

		boolean p1ClearedA15 = StartingDeck.PHARAOH_I.getActiveConfig().getStats().getVictories().getAscension().entrySet().stream().anyMatch(a -> a.getKey() > 14 && a.getValue() > 0);
		boolean p2ClearedA15 = StartingDeck.PHARAOH_II.getActiveConfig().getStats().getVictories().getAscension().entrySet().stream().anyMatch(a -> a.getKey() > 14 && a.getValue() > 0);
		boolean p3ClearedA15 = StartingDeck.PHARAOH_III.getActiveConfig().getStats().getVictories().getAscension().entrySet().stream().anyMatch(a -> a.getKey() > 14 && a.getValue() > 0);
		boolean p4ClearedA15 = StartingDeck.PHARAOH_IV.getActiveConfig().getStats().getVictories().getAscension().entrySet().stream().anyMatch(a -> a.getKey() > 14 && a.getValue() > 0);

		if (p1ClearedA15 && p2ClearedA15 && p3ClearedA15 && p4ClearedA15) {
			DuelistMod.persistentDuelistData.deckUnlockProgress.setPharaohDeckFiveUnlocked(true);
		}

		StartingDeck.currentDeck.updateConfigSettings(config);
	}
}
