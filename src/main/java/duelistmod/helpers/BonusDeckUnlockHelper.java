package duelistmod.helpers;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;

public class BonusDeckUnlockHelper 
{
	private static Properties propertyList = new Properties();
	public static boolean played_all_gods_in_combat = false;
	
	public static boolean beatHeartA10 = false;
	public static boolean beatHeartA15 = false;
	public static boolean beatHeartA20 = false;
	public static boolean beatHeartC1 = false;
	public static boolean beatHeartC5 = false;
	public static boolean beatHeartC10 = false;
	public static boolean beatHeartC20 = false;
	public static boolean beatHeartAscendedI = false;
	public static boolean beatHeartAscendedII = false;
	public static boolean beatHeartAscendedIII = false;
	public static boolean beatHeartPharaohI = false;
	public static boolean beatHeartPharaohII = false;
	public static boolean beatHeartPharaohIII = false;
	public static boolean beatHeartPharaohIV = false;
	
	private static int challengeLevel_standard = 0;
	private static int challengeLevel_dragon = 0;
	private static int challengeLevel_naturia = 0;
	private static int challengeLevel_spellcaster = 0;
	private static int challengeLevel_toon = 0;
	private static int challengeLevel_zombie = 0;
	private static int challengeLevel_aqua = 0;
	private static int challengeLevel_fiend = 0;
	private static int challengeLevel_machine = 0;
	private static int challengeLevel_warrior = 0;
	private static int challengeLevel_megatype = 0;
	private static int challengeLevel_insect = 0;
	private static int challengeLevel_plant = 0;
	private static int challengeLevel_predaplant = 0;
	private static int challengeLevel_giant = 0;
	private static int challengeLevel_increment = 0;
	private static int challengeLevel_creator = 0;
	private static int challengeLevel_ojama = 0;
	private static int challengeLevel_exodia = 0;
	private static int challengeLevel_a1 = 0;
	private static int challengeLevel_a2 = 0;
	private static int challengeLevel_a3 = 0;
	private static int challengeLevel_p1 = 0;
	private static int challengeLevel_p2 = 0;
	private static int challengeLevel_p3 = 0;
	private static int challengeLevel_p4 = 0;
	private static int challengeLevel_p5 = 0;
	private static int challengeLevel_randomSmall = 0;
	private static int challengeLevel_randomBig = 0;
	private static int challengeLevel_upgrade = 0;
	private static int challengeLevel_metronome = 0;
	private static int a20_heart_kills_standard_deck = 0;
	private static int a20_heart_kills_dragon_deck = 0;
	private static int a20_heart_kills_nature_deck = 0;
	private static int a20_heart_kills_spellcaster_deck = 0;
	private static int a20_heart_kills_toon_deck = 0;
	private static int a20_heart_kills_zombie_deck = 0;
	private static int a20_heart_kills_aqua_deck = 0;
	private static int a20_heart_kills_fiend_deck = 0;
	private static int a20_heart_kills_machine_deck = 0;
	private static int a20_heart_kills_insect_deck = 0;
	private static int a20_heart_kills_plant_deck = 0;
	private static int a20_heart_kills_predaplant_deck = 0;
	private static int a20_heart_kills_warrior_deck = 0;
	private static int a20_heart_kills_megatype_deck = 0;
	private static int a20_heart_kills_increment_deck = 0;
	private static int a20_heart_kills_creator_deck = 0;
	private static int a20_heart_kills_ojama_deck = 0;
	private static int a20_heart_kills_exodia_deck = 0;
	private static int a20_heart_kills_giants_deck = 0;
	private static int a20_heart_kills_a1_deck = 0;
	private static int a20_heart_kills_a2_deck = 0;
	private static int a20_heart_kills_a3_deck = 0;
	private static int a20_heart_kills_p1_deck = 0;
	private static int a20_heart_kills_p2_deck = 0;
	private static int a20_heart_kills_p3_deck = 0;
	private static int a20_heart_kills_p4_deck = 0;
	private static int a20_heart_kills_p5_deck = 0;
	private static int a20_heart_kills_random_small_deck = 0;
	private static int a20_heart_kills_random_big_deck = 0;
	private static int a20_heart_kills_upgrade_deck = 0;
	private static int a20_heart_kills_metronome_deck = 0;
	private static int heartKillsRandomDecks = 0;
	private static int heartKillsTotal = 0;
	private static ArrayList<Integer> heartKills = new ArrayList<Integer>();
	private static ArrayList<Integer> heartKillsForA1 = new ArrayList<Integer>();
	private static ArrayList<Integer> heartKillsForP5 = new ArrayList<Integer>();
	
	private static boolean a1_Unlocked = false;
	private static boolean a2_Unlocked = false;
	private static boolean a3_Unlocked = false;
	private static boolean p1_Unlocked = false;
	private static boolean p2_Unlocked = false;
	private static boolean p3_Unlocked = false;
	private static boolean p4_Unlocked = false;
	private static boolean p5_Unlocked = false;
	private static boolean extraRandomsUnlocked = false;
	private static boolean playOnce = false;
	

	public static boolean challengeUnlocked(String deckName)
	{
		if (deckName.equals("Standard Deck") && a20_heart_kills_standard_deck > 0) { return true; }
		if (deckName.equals("Dragon Deck") && a20_heart_kills_dragon_deck > 0) { return true; }
		if (deckName.equals("Naturia Deck") && a20_heart_kills_nature_deck > 0) { return true; }
		if (deckName.equals("Spellcaster Deck") && a20_heart_kills_spellcaster_deck > 0) { return true; }
		if (deckName.equals("Toon Deck") && a20_heart_kills_toon_deck > 0) { return true; }
		if (deckName.equals("Zombie Deck") && a20_heart_kills_zombie_deck > 0) { return true; }
		if (deckName.equals("Aqua Deck") && a20_heart_kills_aqua_deck > 0) { return true; }
		if (deckName.equals("Fiend Deck") && a20_heart_kills_fiend_deck > 0) { return true; }
		if (deckName.equals("Machine Deck") && a20_heart_kills_machine_deck > 0) { return true; }
		if (deckName.equals("Insect Deck") && a20_heart_kills_insect_deck > 0) { return true; }
		if (deckName.equals("Plant Deck") && a20_heart_kills_plant_deck > 0) { return true; }
		if (deckName.equals("Predaplant Deck") && a20_heart_kills_predaplant_deck > 0) { return true; }
		if (deckName.equals("Warrior Deck") && a20_heart_kills_warrior_deck > 0) { return true; }
		if (deckName.equals("Megatype Deck") && a20_heart_kills_megatype_deck > 0) { return true; }
		if (deckName.equals("Increment Deck") && a20_heart_kills_increment_deck > 0) { return true; }
		if (deckName.equals("Creator Deck") && a20_heart_kills_creator_deck > 0) { return true; }
		if (deckName.equals("Ojama Deck") && a20_heart_kills_ojama_deck > 0) { return true; }
		if (deckName.equals("Exodia Deck") && a20_heart_kills_exodia_deck > 0) { return true; }
		if (deckName.equals("Giant Deck") && a20_heart_kills_giants_deck > 0) { return true; }
		if (deckName.equals("Ascended I") && a20_heart_kills_a1_deck > 0) { return true; }
		if (deckName.equals("Ascended II") && a20_heart_kills_a2_deck > 0) { return true; }
		if (deckName.equals("Ascended III") && a20_heart_kills_a3_deck > 0) { return true; }
		if (deckName.equals("Pharaoh I") && a20_heart_kills_p1_deck > 0) { return true; }
		if (deckName.equals("Pharaoh II") && a20_heart_kills_p2_deck > 0) { return true; }
		if (deckName.equals("Pharaoh III") && a20_heart_kills_p3_deck > 0) { return true; }
		if (deckName.equals("Pharaoh IV") && a20_heart_kills_p4_deck > 0) { return true; }
		if (deckName.equals("Pharaoh V") && a20_heart_kills_p5_deck > 0) { return true; }
		if (deckName.equals("Random Deck (Small)") && a20_heart_kills_random_small_deck > 0) { return true; }
		if (deckName.equals("Random Deck (Big)") && a20_heart_kills_random_big_deck > 0) { return true; }
		if (deckName.equals("Upgrade Deck") && a20_heart_kills_upgrade_deck > 0) { return true; }
		if (deckName.equals("Metronome Deck") && a20_heart_kills_metronome_deck > 0) { return true; }
		else { return false; }
	}

	public static int challengeLevel(String deckName)
	{
		if (deckName.equals("Standard Deck") && a20_heart_kills_standard_deck > 0) { return challengeLevel_standard; }
		if (deckName.equals("Dragon Deck") && a20_heart_kills_dragon_deck > 0) { return challengeLevel_dragon; }
		if (deckName.equals("Naturia Deck") && a20_heart_kills_nature_deck > 0) { return challengeLevel_naturia; }
		if (deckName.equals("Spellcaster Deck") && a20_heart_kills_spellcaster_deck > 0) { return challengeLevel_spellcaster; }
		if (deckName.equals("Toon Deck") && a20_heart_kills_toon_deck > 0) { return challengeLevel_toon; }
		if (deckName.equals("Zombie Deck") && a20_heart_kills_zombie_deck > 0) { return challengeLevel_zombie; }
		if (deckName.equals("Aqua Deck") && a20_heart_kills_aqua_deck > 0) { return challengeLevel_aqua; }
		if (deckName.equals("Fiend Deck") && a20_heart_kills_fiend_deck > 0) { return challengeLevel_fiend; }
		if (deckName.equals("Machine Deck") && a20_heart_kills_machine_deck > 0) { return challengeLevel_machine; }
		if (deckName.equals("Insect Deck") && a20_heart_kills_insect_deck > 0) { return challengeLevel_insect; }
		if (deckName.equals("Plant Deck") && a20_heart_kills_plant_deck > 0) { return challengeLevel_plant; }
		if (deckName.equals("Predaplant Deck") && a20_heart_kills_predaplant_deck > 0) { return challengeLevel_predaplant; }
		if (deckName.equals("Warrior Deck") && a20_heart_kills_warrior_deck > 0) { return challengeLevel_warrior; }
		if (deckName.equals("Megatype Deck") && a20_heart_kills_megatype_deck > 0) { return challengeLevel_megatype; }
		if (deckName.equals("Increment Deck") && a20_heart_kills_increment_deck > 0) { return challengeLevel_increment; }
		if (deckName.equals("Creator Deck") && a20_heart_kills_creator_deck > 0) { return challengeLevel_creator; }
		if (deckName.equals("Ojama Deck") && a20_heart_kills_ojama_deck > 0) { return challengeLevel_ojama; }
		if (deckName.equals("Exodia Deck") && a20_heart_kills_exodia_deck > 0) { return challengeLevel_exodia; }
		if (deckName.equals("Giant Deck") && a20_heart_kills_giants_deck > 0) { return challengeLevel_giant; }
		if (deckName.equals("Ascended I") && a20_heart_kills_a1_deck > 0) { return challengeLevel_a1; }
		if (deckName.equals("Ascended II") && a20_heart_kills_a2_deck > 0) { return challengeLevel_a2; }
		if (deckName.equals("Ascended III") && a20_heart_kills_a3_deck > 0) { return challengeLevel_a3; }
		if (deckName.equals("Pharaoh I") && a20_heart_kills_p1_deck > 0) { return challengeLevel_p1; }
		if (deckName.equals("Pharaoh II") && a20_heart_kills_p2_deck > 0) { return challengeLevel_p2; }
		if (deckName.equals("Pharaoh III") && a20_heart_kills_p3_deck > 0) { return challengeLevel_p3; }
		if (deckName.equals("Pharaoh IV") && a20_heart_kills_p4_deck > 0) { return challengeLevel_p4; }
		if (deckName.equals("Pharaoh V") && a20_heart_kills_p5_deck > 0) { return challengeLevel_p5; }
		if (deckName.equals("Random Deck (Small)") && a20_heart_kills_random_small_deck > 0) { return challengeLevel_randomSmall; }
		if (deckName.equals("Random Deck (Big)") && a20_heart_kills_random_big_deck > 0) { return challengeLevel_randomBig; }
		if (deckName.equals("Upgrade Deck") && a20_heart_kills_upgrade_deck > 0) { return challengeLevel_upgrade; }
		if (deckName.equals("Metronome Deck") && a20_heart_kills_metronome_deck > 0) { return challengeLevel_metronome; }
		else { return 0; }
	}
	
	public String logMetrics()
	{
		loadProperties();
		String toLog = " {";
		toLog += "(played_all_gods::" + Boolean.toString(played_all_gods_in_combat) + "), ";
		toLog += "(a20_heart_kills_standard_deck - " + Integer.toString(a20_heart_kills_standard_deck) + "), ";
		toLog += "(a20_heart_kills_dragon_deck - " + Integer.toString(a20_heart_kills_dragon_deck) + "), ";
		toLog += "(a20_heart_kills_nature_deck - " + Integer.toString(a20_heart_kills_nature_deck) + "), ";
		toLog += "(a20_heart_kills_spellcaster_deck - " + Integer.toString(a20_heart_kills_spellcaster_deck) + "), ";
		toLog += "(a20_heart_kills_toon_deck - " + Integer.toString(a20_heart_kills_toon_deck) + "), ";
		toLog += "(a20_heart_kills_zombie_deck - " + Integer.toString(a20_heart_kills_zombie_deck) + "), ";
		toLog += "(a20_heart_kills_aqua_deck - " + Integer.toString(a20_heart_kills_aqua_deck) + "), ";
		toLog += "(a20_heart_kills_fiend_deck - " + Integer.toString(a20_heart_kills_fiend_deck) + "), ";
		toLog += "(a20_heart_kills_machine_deck - " + Integer.toString(a20_heart_kills_machine_deck) + "), ";
		toLog += "(a20_heart_kills_insect_deck - " + Integer.toString(a20_heart_kills_insect_deck) + "), ";
		toLog += "(a20_heart_kills_plant_deck - " + Integer.toString(a20_heart_kills_plant_deck) + "), ";
		toLog += "(a20_heart_kills_predaplant_deck - " + Integer.toString(a20_heart_kills_predaplant_deck) + "), ";
		toLog += "(a20_heart_kills_warrior_deck - " + Integer.toString(a20_heart_kills_warrior_deck) + "), ";
		toLog += "(a20_heart_kills_megatype_deck - " + Integer.toString(a20_heart_kills_megatype_deck) + "), ";
		toLog += "(a20_heart_kills_increment_deck - " + Integer.toString(a20_heart_kills_increment_deck) + "), ";
		toLog += "(a20_heart_kills_creator_deck - " + Integer.toString(a20_heart_kills_creator_deck) + "), ";
		toLog += "(a20_heart_kills_ojama_deck - " + Integer.toString(a20_heart_kills_ojama_deck) + "), ";
		toLog += "(a20_heart_kills_exodia_deck - " + Integer.toString(a20_heart_kills_exodia_deck) + "), ";
		toLog += "(a20_heart_kills_giants_deck - " + Integer.toString(a20_heart_kills_giants_deck) + "), ";
		toLog += "(a20_heart_kills_a1_deck - " + Integer.toString(a20_heart_kills_a1_deck) + "), ";
		toLog += "(a20_heart_kills_a2_deck - " + Integer.toString(a20_heart_kills_a2_deck) + "), ";
		toLog += "(a20_heart_kills_a3_deck - " + Integer.toString(a20_heart_kills_a3_deck) + "), ";
		toLog += "(a20_heart_kills_p1_deck - " + Integer.toString(a20_heart_kills_p1_deck) + "), ";
		toLog += "(a20_heart_kills_p2_deck - " + Integer.toString(a20_heart_kills_p2_deck) + "), ";
		toLog += "(a20_heart_kills_p3_deck - " + Integer.toString(a20_heart_kills_p3_deck) + "), ";
		toLog += "(a20_heart_kills_p4_deck - " + Integer.toString(a20_heart_kills_p4_deck) + "), ";
		toLog += "(a20_heart_kills_p5_deck - " + Integer.toString(a20_heart_kills_p5_deck) + "), ";
		toLog += "(a20_heart_kills_random_small_deck - " + Integer.toString(a20_heart_kills_random_small_deck) + "), ";
		toLog += "(a20_heart_kills_random_big_deck - " + Integer.toString(a20_heart_kills_random_big_deck) + "), ";
		toLog += "(a20_heart_kills_upgrade_deck - " + Integer.toString(a20_heart_kills_upgrade_deck) + "), ";
		toLog += "(a20_heart_kills_metronome_deck - " + Integer.toString(a20_heart_kills_metronome_deck) + "), ";		
		toLog += "(challengeLevel_standard - " + Integer.toString(challengeLevel_standard) + "), ";
		toLog += "(challengeLevel_dragon - " + Integer.toString(challengeLevel_dragon) + "), ";
		toLog += "(challengeLevel_naturia - " + Integer.toString(challengeLevel_naturia) + "), ";
		toLog += "(challengeLevel_spellcaster - " + Integer.toString(challengeLevel_spellcaster) + "), ";
		toLog += "(challengeLevel_toon - " + Integer.toString(challengeLevel_toon) + "), ";
		toLog += "(challengeLevel_zombie - " + Integer.toString(challengeLevel_zombie) + "), ";
		toLog += "(challengeLevel_aqua - " + Integer.toString(challengeLevel_aqua) + "), ";
		toLog += "(challengeLevel_fiend - " + Integer.toString(challengeLevel_fiend) + "), ";
		toLog += "(challengeLevel_machine - " + Integer.toString(challengeLevel_machine) + "), ";
		toLog += "(challengeLevel_insect - " + Integer.toString(challengeLevel_insect) + "), ";
		toLog += "(challengeLevel_plant - " + Integer.toString(challengeLevel_plant) + "), ";
		toLog += "(challengeLevel_predaplant - " + Integer.toString(challengeLevel_predaplant) + "), ";
		toLog += "(challengeLevel_warrior - " + Integer.toString(challengeLevel_warrior) + "), ";
		toLog += "(challengeLevel_megatype - " + Integer.toString(challengeLevel_megatype) + "), ";
		toLog += "(challengeLevel_increment - " + Integer.toString(challengeLevel_increment) + "), ";
		toLog += "(challengeLevel_creator - " + Integer.toString(challengeLevel_creator) + "), ";
		toLog += "(challengeLevel_ojama - " + Integer.toString(challengeLevel_ojama) + "), ";
		toLog += "(challengeLevel_exodia - " + Integer.toString(challengeLevel_exodia) + "), ";
		toLog += "(challengeLevel_giant - " + Integer.toString(challengeLevel_giant) + "), ";
		toLog += "(challengeLevel_a1 - " + Integer.toString(challengeLevel_a1) + "), ";
		toLog += "(challengeLevel_a2 - " + Integer.toString(challengeLevel_a2) + "), ";
		toLog += "(challengeLevel_a3 - " + Integer.toString(challengeLevel_a3) + "), ";
		toLog += "(challengeLevel_p1 - " + Integer.toString(challengeLevel_p1) + "), ";
		toLog += "(challengeLevel_p2 - " + Integer.toString(challengeLevel_p2) + "), ";
		toLog += "(challengeLevel_p3 - " + Integer.toString(challengeLevel_p3) + "), ";
		toLog += "(challengeLevel_p4 - " + Integer.toString(challengeLevel_p4) + "), ";
		toLog += "(challengeLevel_p5 - " + Integer.toString(challengeLevel_p5) + "), ";
		toLog += "(challengeLevel_randomSmall - " + Integer.toString(challengeLevel_randomSmall) + "), ";
		toLog += "(challengeLevel_randomBig - " + Integer.toString(challengeLevel_randomBig) + "), ";
		toLog += "(challengeLevel_upgrade - " + Integer.toString(challengeLevel_upgrade) + "), ";
		toLog += "(challengeLevel_metronome - " + Integer.toString(challengeLevel_metronome) + "), ";
		toLog += "(heartKills - " + Integer.toString(heartKillsTotal) + "), ";
		toLog += "(heartKillsRandomDecks - " + Integer.toString(heartKillsRandomDecks) + ")} ";
		return toLog;
	}
	
	public BonusDeckUnlockHelper()
	{
		setupProperties();
		loadProperties();
		setupNumberLists();
		unlockDecks();
		unlockDecksGlobally();
		saveProperties();
	}
	
	public void allGodsPlayed(boolean allThree)
	{
		/*if (allThree)
		{
			this.played_all_gods_in_combat = true;
			if (!playOnce) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Pharaoh II Unlocked!", 1.0F, 2.0F)); playOnce = true; }
			checkUnlocks();
		}
		else
		{
			this.played_all_gods_in_combat = false;
			checkUnlocks();
		}*/
		//AbstractDungeon.victoryScreen.stats.add(new GameOverStat("", null, "10"));
	}
	
	public void checkUnlocks()
	{
		setupNumberLists();
		unlockDecks();
		unlockDecksGlobally();
		saveProperties();
	}
	
	private void unlockDecksGlobally()
	{
		if (a1_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isAscendedDeckOneUnlocked = true; }
		if (a2_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isAscendedDeckTwoUnlocked = true; }
		//if (a3_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isAscendedDeckThreeUnlocked = true; }
		if (extraRandomsUnlocked) { DuelistMod.isExtraRandomDecksUnlocked = true; }
		//if (p1_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isPharaohDeckOneUnlocked = true; }
		//if (p2_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isPharaohDeckTwoUnlocked = true; }
		//if (p3_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isPharaohDeckThreeUnlocked = true; }
		//if (p4_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isPharaohDeckFourUnlocked = true; }
		//if (p5_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isPharaohDeckFiveUnlocked = true; }
	}
	
	
	
	private void unlockDecks()
	{
		// Extra Random Decks
		if (heartKillsRandomDecks > 0) { extraRandomsUnlocked = true; }
		
		// Ascended 1
		boolean beatAllAscended = beatHeartAscendedI && beatHeartAscendedII && beatHeartAscendedIII;
		boolean beatAllPharaoh = beatHeartPharaohI && beatHeartPharaohII && beatHeartPharaohIII && beatHeartPharaohIV;
		if (beatHeartA10) { a1_Unlocked = true; }
		if (beatHeartA15) { a2_Unlocked = true; }
		if (beatHeartA20) { a3_Unlocked = true; }
		if (beatHeartC1 && beatAllAscended) { p1_Unlocked = true; }
		if (beatHeartC5 && beatAllAscended) { p2_Unlocked = true; }
		if (beatHeartC10 && beatAllAscended) { p3_Unlocked = true; }
		if (beatHeartC20 && beatAllAscended) {  p4_Unlocked = true; }
		if (beatHeartC20 && beatAllPharaoh) { p5_Unlocked = true; }
	}

	private void setupNumberLists()
	{
		heartKills = new ArrayList<Integer>();
		heartKillsForA1 = new ArrayList<Integer>();
		heartKillsForP5 = new ArrayList<Integer>();
		heartKills.add(a20_heart_kills_standard_deck);
		heartKills.add(a20_heart_kills_dragon_deck);
		heartKills.add(a20_heart_kills_nature_deck);
		heartKills.add(a20_heart_kills_spellcaster_deck);
		heartKills.add(a20_heart_kills_toon_deck);
		heartKills.add(a20_heart_kills_zombie_deck);
		heartKills.add(a20_heart_kills_aqua_deck);
		heartKills.add(a20_heart_kills_fiend_deck);
		heartKills.add(a20_heart_kills_machine_deck);
		heartKills.add(a20_heart_kills_insect_deck);
		heartKills.add(a20_heart_kills_plant_deck);
		heartKills.add(a20_heart_kills_predaplant_deck);
		heartKills.add(a20_heart_kills_warrior_deck);
		heartKills.add(a20_heart_kills_megatype_deck);
		heartKills.add(a20_heart_kills_increment_deck);
		heartKills.add(a20_heart_kills_creator_deck);
		heartKills.add(a20_heart_kills_ojama_deck);
		//heartKills.add(a20_heart_kills_exodia_deck);
		heartKills.add(a20_heart_kills_giants_deck);
		heartKills.add(a20_heart_kills_a1_deck);
		heartKills.add(a20_heart_kills_a2_deck);
		//heartKills.add(a20_heart_kills_a3_deck);
		//heartKills.add(a20_heart_kills_p1_deck);
		//heartKills.add(a20_heart_kills_p2_deck);
		//heartKills.add(a20_heart_kills_p3_deck);
		//heartKills.add(a20_heart_kills_p4_deck);
		//heartKills.add(a20_heart_kills_p5_deck);
		//heartKills.add(a20_heart_kills_random_small_deck);
		//heartKills.add(a20_heart_kills_random_big_deck);
		heartKillsForP5.add(a20_heart_kills_standard_deck);
		heartKillsForP5.add(a20_heart_kills_dragon_deck);
		heartKillsForP5.add(a20_heart_kills_nature_deck);
		heartKillsForP5.add(a20_heart_kills_spellcaster_deck);
		heartKillsForP5.add(a20_heart_kills_toon_deck);
		heartKillsForP5.add(a20_heart_kills_zombie_deck);
		heartKillsForP5.add(a20_heart_kills_aqua_deck);
		heartKillsForP5.add(a20_heart_kills_fiend_deck);
		heartKillsForP5.add(a20_heart_kills_machine_deck);
		heartKillsForP5.add(a20_heart_kills_insect_deck);
		heartKillsForP5.add(a20_heart_kills_plant_deck);
		heartKillsForP5.add(a20_heart_kills_predaplant_deck);
		heartKillsForP5.add(a20_heart_kills_warrior_deck);
		heartKillsForP5.add(a20_heart_kills_megatype_deck);
		heartKillsForP5.add(a20_heart_kills_increment_deck);
		heartKillsForP5.add(a20_heart_kills_creator_deck);
		heartKillsForP5.add(a20_heart_kills_ojama_deck);
		//heartKillsForP5.add(a20_heart_kills_exodia_deck);
		heartKillsForP5.add(a20_heart_kills_giants_deck);
		heartKillsForP5.add(a20_heart_kills_a1_deck);
		heartKillsForP5.add(a20_heart_kills_a2_deck);
		heartKillsForP5.add(a20_heart_kills_a3_deck);
		heartKillsForP5.add(a20_heart_kills_p1_deck);
		heartKillsForP5.add(a20_heart_kills_p2_deck);
		heartKillsForP5.add(a20_heart_kills_p3_deck);
		heartKillsForP5.add(a20_heart_kills_p4_deck);
		heartKillsForA1.add(a20_heart_kills_standard_deck);
		heartKillsForA1.add(a20_heart_kills_dragon_deck);
		heartKillsForA1.add(a20_heart_kills_nature_deck);
		heartKillsForA1.add(a20_heart_kills_spellcaster_deck);
		heartKillsForA1.add(a20_heart_kills_toon_deck);
		heartKillsForA1.add(a20_heart_kills_zombie_deck);
		heartKillsForA1.add(a20_heart_kills_aqua_deck);
		heartKillsForA1.add(a20_heart_kills_fiend_deck);
		heartKillsForA1.add(a20_heart_kills_machine_deck);
		heartKillsForA1.add(a20_heart_kills_insect_deck);
		heartKillsForA1.add(a20_heart_kills_plant_deck);
		heartKillsForA1.add(a20_heart_kills_predaplant_deck);
		heartKillsForA1.add(a20_heart_kills_warrior_deck);
		heartKillsForA1.add(a20_heart_kills_megatype_deck);
		heartKillsForA1.add(a20_heart_kills_increment_deck);
		heartKillsForA1.add(a20_heart_kills_creator_deck);
		heartKillsForA1.add(a20_heart_kills_ojama_deck);
		heartKillsForA1.add(a20_heart_kills_exodia_deck);
		heartKillsForA1.add(a20_heart_kills_giants_deck);
		heartKillsForA1.add(a20_heart_kills_random_small_deck);
		heartKillsForA1.add(a20_heart_kills_random_big_deck);
		heartKillsForA1.add(a20_heart_kills_upgrade_deck);
		heartKillsForA1.add(a20_heart_kills_metronome_deck);
	}

	
	public void saveProperties()
	{
		try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",propertyList);
        	config.setBool("played_all_gods_in_combat", played_all_gods_in_combat);
        	config.setBool("a1_Unlocked", a1_Unlocked);
        	config.setBool("a2_Unlocked", a2_Unlocked);
        	config.setBool("a3_Unlocked", a3_Unlocked);
        	config.setBool("p1_Unlocked", p1_Unlocked);
        	config.setBool("p2_Unlocked", p2_Unlocked);
        	config.setBool("p3_Unlocked", p3_Unlocked);
        	config.setBool("p4_Unlocked", p4_Unlocked);
        	config.setBool("p5_Unlocked", p5_Unlocked);
        	config.setBool("extraRandomsUnlocked", extraRandomsUnlocked);
        	config.setInt("a20_heart_kills_standard_deck", a20_heart_kills_standard_deck);
        	config.setInt("a20_heart_kills_dragon_deck", a20_heart_kills_dragon_deck);
        	config.setInt("a20_heart_kills_nature_deck", a20_heart_kills_nature_deck);
        	config.setInt("a20_heart_kills_spellcaster_deck", a20_heart_kills_spellcaster_deck);
        	config.setInt("a20_heart_kills_toon_deck", a20_heart_kills_toon_deck);
        	config.setInt("a20_heart_kills_zombie_deck", a20_heart_kills_zombie_deck);
        	config.setInt("a20_heart_kills_aqua_deck", a20_heart_kills_aqua_deck);
        	config.setInt("a20_heart_kills_fiend_deck", a20_heart_kills_fiend_deck);
        	config.setInt("a20_heart_kills_machine_deck", a20_heart_kills_machine_deck);
        	config.setInt("a20_heart_kills_insect_deck", a20_heart_kills_insect_deck);
        	config.setInt("a20_heart_kills_plant_deck", a20_heart_kills_plant_deck);
        	config.setInt("a20_heart_kills_predaplant_deck", a20_heart_kills_predaplant_deck);
        	config.setInt("a20_heart_kills_warrior_deck", a20_heart_kills_warrior_deck);
        	config.setInt("a20_heart_kills_megatype_deck", a20_heart_kills_megatype_deck);
        	config.setInt("a20_heart_kills_increment_deck", a20_heart_kills_increment_deck);
        	config.setInt("a20_heart_kills_creator_deck", a20_heart_kills_creator_deck);
        	config.setInt("a20_heart_kills_ojama_deck", a20_heart_kills_ojama_deck);
        	config.setInt("a20_heart_kills_exodia_deck", a20_heart_kills_exodia_deck);
        	config.setInt("a20_heart_kills_giants_deck", a20_heart_kills_giants_deck);
        	config.setInt("a20_heart_kills_a1_deck", a20_heart_kills_a1_deck);
        	config.setInt("a20_heart_kills_a2_deck", a20_heart_kills_a2_deck);
        	config.setInt("a20_heart_kills_a3_deck", a20_heart_kills_a3_deck);
        	config.setInt("a20_heart_kills_p1_deck", a20_heart_kills_p1_deck);
        	config.setInt("a20_heart_kills_p2_deck", a20_heart_kills_p2_deck);
        	config.setInt("a20_heart_kills_p3_deck", a20_heart_kills_p3_deck);
        	config.setInt("a20_heart_kills_p4_deck", a20_heart_kills_p4_deck);
        	config.setInt("a20_heart_kills_p5_deck", a20_heart_kills_p5_deck);
        	config.setInt("a20_heart_kills_random_small_deck", a20_heart_kills_random_small_deck);
        	config.setInt("a20_heart_kills_random_big_deck", a20_heart_kills_random_big_deck);     
        	config.setInt("a20_heart_kills_upgrade_deck", a20_heart_kills_upgrade_deck);     
        	config.setInt("a20_heart_kills_metronome_deck", a20_heart_kills_metronome_deck);     
        	config.setInt("heartKillsRandomDecks", heartKillsRandomDecks);
        	config.setInt("heartKillsTotal", heartKillsTotal);
        	config.setBool("playOnce", playOnce);    
        	config.setInt("challengeLevel_standard", challengeLevel_standard);
        	config.setInt("challengeLevel_dragon", challengeLevel_dragon);
        	config.setInt("challengeLevel_naturia", challengeLevel_naturia);
        	config.setInt("challengeLevel_spellcaster", challengeLevel_spellcaster);
        	config.setInt("challengeLevel_toon", challengeLevel_toon);
        	config.setInt("challengeLevel_zombie", challengeLevel_zombie);
        	config.setInt("challengeLevel_aqua", challengeLevel_aqua);
        	config.setInt("challengeLevel_fiend", challengeLevel_fiend);
        	config.setInt("challengeLevel_machine", challengeLevel_machine);
        	config.setInt("challengeLevel_warrior", challengeLevel_warrior);
        	config.setInt("challengeLevel_megatype", challengeLevel_megatype);
        	config.setInt("challengeLevel_insect", challengeLevel_insect);
        	config.setInt("challengeLevel_plant", challengeLevel_plant);
        	config.setInt("challengeLevel_predaplant", challengeLevel_predaplant);
        	config.setInt("challengeLevel_giant", challengeLevel_giant);
        	config.setInt("challengeLevel_increment", challengeLevel_increment);
        	config.setInt("challengeLevel_creator", challengeLevel_creator);
        	config.setInt("challengeLevel_ojama", challengeLevel_ojama);
        	config.setInt("challengeLevel_exodia", challengeLevel_exodia);
        	config.setInt("challengeLevel_a1", challengeLevel_a1);
        	config.setInt("challengeLevel_a2", challengeLevel_a2);
        	config.setInt("challengeLevel_a3", challengeLevel_a3);
        	config.setInt("challengeLevel_p1", challengeLevel_p1);
        	config.setInt("challengeLevel_p2", challengeLevel_p2);
        	config.setInt("challengeLevel_p3", challengeLevel_p3);
        	config.setInt("challengeLevel_p4", challengeLevel_p4);
        	config.setInt("challengeLevel_p5", challengeLevel_p5);
        	config.setInt("challengeLevel_randomSmall", challengeLevel_randomSmall);
        	config.setInt("challengeLevel_randomBig", challengeLevel_randomBig);
        	config.setInt("challengeLevel_upgrade", challengeLevel_upgrade);
        	config.setInt("challengeLevel_metronome", challengeLevel_metronome);
			config.save();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void beatHeart()
	{
		if (!Settings.isStandardRun()) { Util.log("Non-standard run, not saving heart kill"); return; }
		if (Util.getChallengeLevel() > 0) { beatHeartC1 = true; }
		if (Util.getChallengeLevel() > 4) { beatHeartC5 = true; }
		if (Util.getChallengeLevel() > 9) { beatHeartC10 = true; }
		if (Util.getChallengeLevel() > 19) { beatHeartC20 = true; }
		if (AbstractDungeon.ascensionLevel > 9) { beatHeartA10 = true; }
		if (AbstractDungeon.ascensionLevel > 11) { beatHeartA15 = true; }
		if (AbstractDungeon.ascensionLevel > 19) { beatHeartA20 = true; }
		if (Util.deckIs("Ascended I")) { beatHeartAscendedI = true; }
		if (Util.deckIs("Ascended I")) { beatHeartAscendedII = true; }
		if (Util.deckIs("Ascended I")) { beatHeartAscendedIII = true; }
		if (Util.deckIs("Pharaoh I")) { beatHeartPharaohI = true; }
		if (Util.deckIs("Pharaoh II")) { beatHeartPharaohII = true;}
		if (Util.deckIs("Pharaoh III")) { beatHeartPharaohIII = true;}
		if (Util.deckIs("Pharaoh IV")) { beatHeartPharaohIV = true;}
		int currentDeck = StarterDeckSetup.getCurrentDeck().getIndex();
		if (currentDeck == 27 || currentDeck == 28)
		{
			heartKillsRandomDecks++;
		}		
		heartKillsTotal++;
		setupNumberLists();
		unlockDecks();
		unlockDecksGlobally();
		saveProperties();
	}
	
	public void beatHeartA20()
	{
		if (!Settings.isStandardRun()) { Util.log("Non-standard run, not saving heart kill"); return; }
		heartKillsTotal++;
		int cLvl = DuelistMod.challengeLevel;
		int currentDeck = StarterDeckSetup.getCurrentDeck().getIndex();
		switch (currentDeck)
		{
			case 0:
				a20_heart_kills_standard_deck++;
				if (cLvl >= challengeLevel_standard) { challengeLevel_standard++; }
				break;
			case 1:
				a20_heart_kills_dragon_deck++;
				if (cLvl >= challengeLevel_dragon) { challengeLevel_dragon++; }
				break;
			case 2:
				a20_heart_kills_nature_deck++;
				if (cLvl >= challengeLevel_naturia) { challengeLevel_naturia++; }
				break;
			case 3:
				a20_heart_kills_spellcaster_deck++;
				if (cLvl >= challengeLevel_spellcaster) { challengeLevel_spellcaster++; }
				break;
			case 4:
				a20_heart_kills_toon_deck++;
				if (cLvl >= challengeLevel_toon) { challengeLevel_toon++; }
				break;
			case 5:
				a20_heart_kills_zombie_deck++;
				if (cLvl >= challengeLevel_zombie) { challengeLevel_zombie++; }
				break;
			case 6:
				a20_heart_kills_aqua_deck++;
				if (cLvl >= challengeLevel_aqua) { challengeLevel_aqua++; }
				break;
			case 7:
				a20_heart_kills_fiend_deck++;
				if (cLvl >= challengeLevel_fiend) { challengeLevel_fiend++; }
				break;
			case 8:
				a20_heart_kills_machine_deck++;
				if (cLvl >= challengeLevel_machine) { challengeLevel_machine++; }
				break;
			case 9:
				a20_heart_kills_warrior_deck++;
				if (cLvl >= challengeLevel_warrior) { challengeLevel_warrior++; }
				break;
			case 10:
				a20_heart_kills_insect_deck++;
				if (cLvl >= challengeLevel_insect) { challengeLevel_insect++; }
				break;
			case 11:
				a20_heart_kills_plant_deck++;
				if (cLvl >= challengeLevel_plant) { challengeLevel_plant++; }
				break;
			case 12:
				a20_heart_kills_predaplant_deck++;
				if (cLvl >= challengeLevel_predaplant) { challengeLevel_predaplant++; }
				break;
			case 13:
				a20_heart_kills_megatype_deck++;
				if (cLvl >= challengeLevel_megatype) { challengeLevel_megatype++; }
				break;
			case 14:
				a20_heart_kills_increment_deck++;
				if (cLvl >= challengeLevel_increment) { challengeLevel_increment++; }
				break;
			case 15:
				a20_heart_kills_creator_deck++;
				if (cLvl >= challengeLevel_creator) { challengeLevel_creator++; }
				break;
			case 16:
				a20_heart_kills_ojama_deck++;
				if (cLvl >= challengeLevel_ojama) { challengeLevel_ojama++; }
				break;
			case 17:
				a20_heart_kills_exodia_deck++;
				if (cLvl >= challengeLevel_exodia) { challengeLevel_exodia++; }
				break;
			case 18:
				a20_heart_kills_giants_deck++;
				if (cLvl >= challengeLevel_giant) { challengeLevel_giant++; }
				break;
			case 19:
				a20_heart_kills_a1_deck++;
				if (cLvl >= challengeLevel_a1) { challengeLevel_a1++; }
				break;
			case 20:
				a20_heart_kills_a2_deck++;
				if (cLvl >= challengeLevel_a2) { challengeLevel_a2++; }
				break;
			case 21:
				a20_heart_kills_a3_deck++;
				if (cLvl >= challengeLevel_a3) { challengeLevel_a3++; }
				break;
			case 22:
				a20_heart_kills_p1_deck++;
				if (cLvl >= challengeLevel_p1) { challengeLevel_p1++; }
				break;
			case 23:
				a20_heart_kills_p2_deck++;
				if (cLvl >= challengeLevel_p2) { challengeLevel_p2++; }
				break;
			case 24:
				a20_heart_kills_p3_deck++;
				if (cLvl >= challengeLevel_p3) { challengeLevel_p3++; }
				break;
			case 25:
				a20_heart_kills_p4_deck++;
				if (cLvl >= challengeLevel_p4) { challengeLevel_p4++; }
				break;
			case 26:
				a20_heart_kills_p5_deck++;
				if (cLvl >= challengeLevel_p5) { challengeLevel_p5++; }
				break;
			case 27:
				a20_heart_kills_random_small_deck++;
				if (cLvl >= challengeLevel_randomSmall) { challengeLevel_randomSmall++; }
				heartKillsRandomDecks++;
				break;
			case 28:
				a20_heart_kills_random_big_deck++;
				if (cLvl >= challengeLevel_randomBig) { challengeLevel_randomBig++; }
				heartKillsRandomDecks++;
				break;
			case 29:
				a20_heart_kills_upgrade_deck++;
				if (cLvl >= challengeLevel_upgrade) { challengeLevel_upgrade++; }
				heartKillsRandomDecks++;
				break;
			case 30:
				a20_heart_kills_metronome_deck++;
				if (cLvl >= challengeLevel_metronome) { challengeLevel_metronome++; }
				heartKillsRandomDecks++;
				break;
		}
		saveProperties();
		setupNumberLists();
		unlockDecks();
		unlockDecksGlobally();
	}
	
	public static void loadProperties()
	{
		try 
		{
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",propertyList);
            config.load();
        	played_all_gods_in_combat = config.getBool("played_all_gods_in_combat");        	
        	a1_Unlocked = config.getBool("a1_Unlocked");
        	a2_Unlocked = config.getBool("a2_Unlocked");
        	a3_Unlocked = config.getBool("a3_Unlocked");
        	p1_Unlocked = config.getBool("p1_Unlocked");
        	p2_Unlocked = config.getBool("p2_Unlocked");
        	p3_Unlocked = config.getBool("p3_Unlocked");
        	p4_Unlocked = config.getBool("p4_Unlocked");
        	p5_Unlocked = config.getBool("p5_Unlocked");  
        	extraRandomsUnlocked = config.getBool("extraRandomsUnlocked"); 
        	a20_heart_kills_standard_deck = config.getInt("a20_heart_kills_standard_deck");
        	a20_heart_kills_dragon_deck = config.getInt("a20_heart_kills_dragon_deck");
        	a20_heart_kills_nature_deck = config.getInt("a20_heart_kills_nature_deck");
        	a20_heart_kills_spellcaster_deck = config.getInt("a20_heart_kills_spellcaster_deck");
        	a20_heart_kills_toon_deck = config.getInt("a20_heart_kills_toon_deck");
        	a20_heart_kills_zombie_deck = config.getInt("a20_heart_kills_zombie_deck");
        	a20_heart_kills_aqua_deck = config.getInt("a20_heart_kills_aqua_deck");
        	a20_heart_kills_fiend_deck = config.getInt("a20_heart_kills_fiend_deck");
        	a20_heart_kills_machine_deck = config.getInt("a20_heart_kills_machine_deck");
        	a20_heart_kills_insect_deck = config.getInt("a20_heart_kills_insect_deck");
        	a20_heart_kills_plant_deck = config.getInt("a20_heart_kills_plant_deck");
        	a20_heart_kills_predaplant_deck = config.getInt("a20_heart_kills_predaplant_deck");
        	a20_heart_kills_warrior_deck = config.getInt("a20_heart_kills_warrior_deck");
        	a20_heart_kills_megatype_deck = config.getInt("a20_heart_kills_megatype_deck");
        	a20_heart_kills_increment_deck = config.getInt("a20_heart_kills_increment_deck");
        	a20_heart_kills_creator_deck = config.getInt("a20_heart_kills_creator_deck");
        	a20_heart_kills_ojama_deck = config.getInt("a20_heart_kills_ojama_deck");
        	a20_heart_kills_exodia_deck = config.getInt("a20_heart_kills_exodia_deck");
        	a20_heart_kills_giants_deck = config.getInt("a20_heart_kills_giants_deck");
        	a20_heart_kills_a1_deck = config.getInt("a20_heart_kills_a1_deck");
        	a20_heart_kills_a2_deck = config.getInt("a20_heart_kills_a2_deck");
        	a20_heart_kills_a3_deck = config.getInt("a20_heart_kills_a3_deck");
        	a20_heart_kills_p1_deck = config.getInt("a20_heart_kills_p1_deck");
        	a20_heart_kills_p2_deck = config.getInt("a20_heart_kills_p2_deck");
        	a20_heart_kills_p3_deck = config.getInt("a20_heart_kills_p3_deck");
        	a20_heart_kills_p4_deck = config.getInt("a20_heart_kills_p4_deck");
        	a20_heart_kills_p5_deck = config.getInt("a20_heart_kills_p5_deck");
        	a20_heart_kills_random_small_deck = config.getInt("a20_heart_kills_random_small_deck");
        	a20_heart_kills_random_big_deck = config.getInt("a20_heart_kills_random_big_deck");   
        	a20_heart_kills_upgrade_deck = config.getInt("a20_heart_kills_upgrade_deck");
        	a20_heart_kills_metronome_deck = config.getInt("a20_heart_kills_metronome_deck");
        	heartKillsRandomDecks = config.getInt("heartKillsRandomDecks");
        	heartKillsTotal = config.getInt("heartKillsTotal");
        	playOnce = config.getBool("playOnce");
        	challengeLevel_standard = config.getInt("challengeLevel_standard");
        	challengeLevel_dragon = config.getInt("challengeLevel_dragon");
        	challengeLevel_naturia = config.getInt("challengeLevel_naturia");
        	challengeLevel_spellcaster = config.getInt("challengeLevel_spellcaster");
        	challengeLevel_toon = config.getInt("challengeLevel_toon");
        	challengeLevel_zombie = config.getInt("challengeLevel_zombie");
        	challengeLevel_aqua = config.getInt("challengeLevel_aqua");
        	challengeLevel_fiend = config.getInt("challengeLevel_fiend");
        	challengeLevel_machine = config.getInt("challengeLevel_machine");
        	challengeLevel_warrior = config.getInt("challengeLevel_warrior");
        	challengeLevel_megatype = config.getInt("challengeLevel_megatype");
        	challengeLevel_insect = config.getInt("challengeLevel_insect");
        	challengeLevel_plant = config.getInt("challengeLevel_plant");
        	challengeLevel_predaplant = config.getInt("challengeLevel_predaplant");
        	challengeLevel_giant = config.getInt("challengeLevel_giant");
        	challengeLevel_increment = config.getInt("challengeLevel_increment");
        	challengeLevel_creator = config.getInt("challengeLevel_creator");
        	challengeLevel_ojama = config.getInt("challengeLevel_ojama");
        	challengeLevel_exodia = config.getInt("challengeLevel_exodia");
        	challengeLevel_a1 = config.getInt("challengeLevel_a1");
        	challengeLevel_a2 = config.getInt("challengeLevel_a2");
        	challengeLevel_a3 = config.getInt("challengeLevel_a3");
        	challengeLevel_p1 = config.getInt("challengeLevel_p1");
        	challengeLevel_p2 = config.getInt("challengeLevel_p2");
        	challengeLevel_p3 = config.getInt("challengeLevel_p3");
        	challengeLevel_p4 = config.getInt("challengeLevel_p4");
        	challengeLevel_p5 = config.getInt("challengeLevel_p5");
        	challengeLevel_randomSmall = config.getInt("challengeLevel_randomSmall");
        	challengeLevel_randomBig = config.getInt("challengeLevel_randomBig");
        	challengeLevel_upgrade = config.getInt("challengeLevel_upgrade");
        	challengeLevel_metronome = config.getInt("challengeLevel_metronome");
        } 
		catch (Exception e) { e.printStackTrace(); }
	}
	
	private void setupProperties()
	{
		propertyList.setProperty("a20_heart_kills_any_deck", "0");
		propertyList.setProperty("a20_heart_kills_any_deck", "0");
		propertyList.setProperty("a20_heart_kills_pharaoh_deck", "0");
		propertyList.setProperty("beat_a20_heart_any_deck", "FALSE");
		propertyList.setProperty("beat_a20_heart_ascended_deck", "FALSE");
		propertyList.setProperty("beat_a20_heart_with_all_non_ascended_non_pharaoh", "FALSE");
		propertyList.setProperty("beat_a20_heart_with_all_ascended", "FALSE");
		propertyList.setProperty("played_all_gods_in_combat", "FALSE");
		propertyList.setProperty("beat_a20_heart_ten_times_any_deck", "FALSE");
		propertyList.setProperty("beat_a20_heart_any_pharaoh_deck", "FALSE");
		propertyList.setProperty("beat_a20_heart_twenty_times_all_decks", "FALSE");
		propertyList.setProperty("a20_heart_kills_standard_deck", "0");
		propertyList.setProperty("a20_heart_kills_dragon_deck", "0");
		propertyList.setProperty("a20_heart_kills_nature_deck", "0");
		propertyList.setProperty("a20_heart_kills_spellcaster_deck", "0");
		propertyList.setProperty("a20_heart_kills_toon_deck", "0");
		propertyList.setProperty("a20_heart_kills_zombie_deck", "0");
		propertyList.setProperty("a20_heart_kills_aqua_deck", "0");
		propertyList.setProperty("a20_heart_kills_fiend_deck", "0");
		propertyList.setProperty("a20_heart_kills_machine_deck", "0");
		propertyList.setProperty("a20_heart_kills_insect_deck", "0");
		propertyList.setProperty("a20_heart_kills_plant_deck", "0");
		propertyList.setProperty("a20_heart_kills_predaplant_deck", "0");
		propertyList.setProperty("a20_heart_kills_warrior_deck", "0");
		propertyList.setProperty("a20_heart_kills_megatype_deck", "0");
		propertyList.setProperty("a20_heart_kills_increment_deck", "0");
		propertyList.setProperty("a20_heart_kills_creator_deck", "0");
		propertyList.setProperty("a20_heart_kills_ojama_deck", "0");
		propertyList.setProperty("a20_heart_kills_exodia_deck", "0");
		propertyList.setProperty("a20_heart_kills_giants_deck", "0");
		propertyList.setProperty("a20_heart_kills_a1_deck", "0");
		propertyList.setProperty("a20_heart_kills_a2_deck", "0");
		propertyList.setProperty("a20_heart_kills_a3_deck", "0");
		propertyList.setProperty("a20_heart_kills_p1_deck", "0");
		propertyList.setProperty("a20_heart_kills_p2_deck", "0");
		propertyList.setProperty("a20_heart_kills_p3_deck", "0");
		propertyList.setProperty("a20_heart_kills_p4_deck", "0");
		propertyList.setProperty("a20_heart_kills_p5_deck", "0");
		propertyList.setProperty("a20_heart_kills_random_small_deck", "0");
		propertyList.setProperty("a20_heart_kills_random_big_deck", "0");
		propertyList.setProperty("a20_heart_kills_upgrade_deck", "0");
		propertyList.setProperty("a20_heart_kills_metronome_deck", "0");
		propertyList.setProperty("a1_Unlocked", "FALSE");
		propertyList.setProperty("a2_Unlocked", "FALSE");
		propertyList.setProperty("a3_Unlocked", "FALSE");
		propertyList.setProperty("p1_Unlocked", "FALSE");
		propertyList.setProperty("p2_Unlocked", "FALSE");
		propertyList.setProperty("p3_Unlocked", "FALSE");
		propertyList.setProperty("p4_Unlocked", "FALSE");
		propertyList.setProperty("p5_Unlocked", "FALSE");
		propertyList.setProperty("extraRandomsUnlocked", "FALSE");
		propertyList.setProperty("heartKillsRandomDecks", "0");
		propertyList.setProperty("heartKillsTotal", "0");
	}
}
