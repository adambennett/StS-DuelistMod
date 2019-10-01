package duelistmod.helpers;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;

import duelistmod.DuelistMod;

public class BonusDeckUnlockHelper 
{
	private Properties propertyList = new Properties();
	public boolean played_all_gods_in_combat = false;
	private int a20_heart_kills_standard_deck = 0;
	private int a20_heart_kills_dragon_deck = 0;
	private int a20_heart_kills_nature_deck = 0;
	private int a20_heart_kills_spellcaster_deck = 0;
	private int a20_heart_kills_toon_deck = 0;
	private int a20_heart_kills_zombie_deck = 0;
	private int a20_heart_kills_aqua_deck = 0;
	private int a20_heart_kills_fiend_deck = 0;
	private int a20_heart_kills_machine_deck = 0;
	private int a20_heart_kills_insect_deck = 0;
	private int a20_heart_kills_plant_deck = 0;
	private int a20_heart_kills_predaplant_deck = 0;
	private int a20_heart_kills_warrior_deck = 0;
	private int a20_heart_kills_megatype_deck = 0;
	private int a20_heart_kills_increment_deck = 0;
	private int a20_heart_kills_creator_deck = 0;
	private int a20_heart_kills_ojama_deck = 0;
	private int a20_heart_kills_exodia_deck = 0;
	private int a20_heart_kills_giants_deck = 0;
	private int a20_heart_kills_a1_deck = 0;
	private int a20_heart_kills_a2_deck = 0;
	private int a20_heart_kills_a3_deck = 0;
	private int a20_heart_kills_p1_deck = 0;
	private int a20_heart_kills_p2_deck = 0;
	private int a20_heart_kills_p3_deck = 0;
	private int a20_heart_kills_p4_deck = 0;
	private int a20_heart_kills_p5_deck = 0;
	private int a20_heart_kills_random_small_deck = 0;
	private int a20_heart_kills_random_big_deck = 0;
	private int heartKillsRandomDecks = 0;
	private int heartKillsTotal = 0;
	private ArrayList<Integer> heartKills = new ArrayList<Integer>();
	private ArrayList<Integer> heartKillsForA1 = new ArrayList<Integer>();
	private ArrayList<Integer> heartKillsForP5 = new ArrayList<Integer>();
	
	private boolean a1_Unlocked = false;
	private boolean a2_Unlocked = false;
	private boolean a3_Unlocked = false;
	private boolean p1_Unlocked = false;
	private boolean p2_Unlocked = false;
	private boolean p3_Unlocked = false;
	private boolean p4_Unlocked = false;
	private boolean p5_Unlocked = false;
	private boolean extraRandomsUnlocked = false;
	private boolean playOnce = false;
	
	public String logMetrics()
	{
		loadProperties();
		String toLog = " {";
		toLog += "(played_all_gods=" + played_all_gods_in_combat + "), ";
		toLog += "(a20_heart_kills_standard_deck=" + a20_heart_kills_standard_deck + "), ";
		toLog += "(a20_heart_kills_dragon_deck=" + a20_heart_kills_dragon_deck + "), ";
		toLog += "(a20_heart_kills_nature_deck=" + a20_heart_kills_nature_deck + "), ";
		toLog += "(a20_heart_kills_spellcaster_deck=" + a20_heart_kills_spellcaster_deck + "), ";
		toLog += "(a20_heart_kills_toon_deck=" + a20_heart_kills_toon_deck + "), ";
		toLog += "(a20_heart_kills_zombie_deck=" + a20_heart_kills_zombie_deck + "), ";
		toLog += "(a20_heart_kills_aqua_deck=" + a20_heart_kills_aqua_deck + "), ";
		toLog += "(a20_heart_kills_fiend_deck=" + a20_heart_kills_fiend_deck + "), ";
		toLog += "(a20_heart_kills_machine_deck=" + a20_heart_kills_machine_deck + "), ";
		toLog += "(a20_heart_kills_insect_deck=" + a20_heart_kills_insect_deck + "), ";
		toLog += "(a20_heart_kills_plant_deck=" + a20_heart_kills_plant_deck + "), ";
		toLog += "(a20_heart_kills_predaplant_deck=" + a20_heart_kills_predaplant_deck + "), ";
		toLog += "(a20_heart_kills_warrior_deck=" + a20_heart_kills_warrior_deck + "), ";
		toLog += "(a20_heart_kills_megatype_deck=" + a20_heart_kills_megatype_deck + "), ";
		toLog += "(a20_heart_kills_increment_deck=" + a20_heart_kills_increment_deck + "), ";
		toLog += "(a20_heart_kills_creator_deck=" + a20_heart_kills_creator_deck + "), ";
		toLog += "(a20_heart_kills_ojama_deck=" + a20_heart_kills_ojama_deck + "), ";
		toLog += "(a20_heart_kills_exodia_deck=" + a20_heart_kills_exodia_deck + "), ";
		toLog += "(a20_heart_kills_giants_deck=" + a20_heart_kills_giants_deck + "), ";
		toLog += "(a20_heart_kills_a1_deck=" + a20_heart_kills_a1_deck + "), ";
		toLog += "(a20_heart_kills_a2_deck=" + a20_heart_kills_a2_deck + "), ";
		toLog += "(a20_heart_kills_a3_deck=" + a20_heart_kills_a3_deck + "), ";
		toLog += "(a20_heart_kills_p1_deck=" + a20_heart_kills_p1_deck + "), ";
		toLog += "(a20_heart_kills_p2_deck=" + a20_heart_kills_p2_deck + "), ";
		toLog += "(a20_heart_kills_p3_deck=" + a20_heart_kills_p3_deck + "), ";
		toLog += "(a20_heart_kills_p4_deck=" + a20_heart_kills_p4_deck + "), ";
		toLog += "(a20_heart_kills_p5_deck=" + a20_heart_kills_p5_deck + "), ";
		toLog += "(a20_heart_kills_random_small_deck=" + a20_heart_kills_random_small_deck + "), ";
		toLog += "(a20_heart_kills_random_big_deck=" + a20_heart_kills_random_big_deck + "), ";
		toLog += "(heartKills=" + heartKillsTotal + "), ";
		toLog += "(heartKillsRandomDecks=" + heartKillsRandomDecks + ")} ";
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
		if (a3_Unlocked && DuelistMod.allowBonusDeckUnlocks) { DuelistMod.isAscendedDeckThreeUnlocked = true; }
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
		for (Integer i : heartKillsForA1) { if (i > 0) { a1_Unlocked = true; break; }}
		
		// Ascended 2
		if (a20_heart_kills_a1_deck > 0) { a2_Unlocked = true; }
		else { a2_Unlocked = false; }
		
		// Ascended 3
		boolean allGood = true;
		for (int i = 0; i < heartKills.size(); i++) { if (heartKills.get(i) < 1) { allGood = false; }}
		if (allGood) { a3_Unlocked = true; }
		else { a3_Unlocked = false; }
		
		// Pharaoh 1
		boolean a1 = a20_heart_kills_a1_deck > 0;
		boolean a2 = a20_heart_kills_a2_deck > 0;
		boolean a3 = a20_heart_kills_a3_deck > 0;
		//if (a1 && a2 && a3) { p1_Unlocked = true; }
		//else { p1_Unlocked = false; }
		
		// Pharaoh 2
		//if (played_all_gods_in_combat) { p2_Unlocked = true; }
		//else { p2_Unlocked = false; }
		
		// Pharaoh 3
		//for (Integer i : heartKills) { if (i > 9) { p3_Unlocked = true;}}
		
		// Pharaoh 4
		boolean p1 = a20_heart_kills_p1_deck > 0;
		boolean p2 = a20_heart_kills_p2_deck > 0;
		boolean p3 = a20_heart_kills_p3_deck > 0;
		//if (p1) { p4_Unlocked = true; }
		//if (p2) { p4_Unlocked = true; }
		//if (p3) { p4_Unlocked = true; }
		
		// Pharaoh 5
		//boolean allGoodP = true;
		//for (Integer i : heartKillsForP5) { if (i < 10) { allGoodP = false; }}
		//if (allGoodP) { p5_Unlocked = true; }
		//else { p5_Unlocked = false; }
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
        	config.setInt("heartKillsRandomDecks", heartKillsRandomDecks);
        	config.setInt("heartKillsTotal", heartKillsTotal);
        	config.setBool("playOnce", playOnce);    
			config.save();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void beatHeart()
	{
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
		heartKillsTotal++;
		int currentDeck = StarterDeckSetup.getCurrentDeck().getIndex();
		switch (currentDeck)
		{
			case 0:
				a20_heart_kills_standard_deck++;
				break;
			case 1:
				a20_heart_kills_dragon_deck++;
				break;
			case 2:
				a20_heart_kills_nature_deck++;
				break;
			case 3:
				a20_heart_kills_spellcaster_deck++;
				break;
			case 4:
				a20_heart_kills_toon_deck++;
				break;
			case 5:
				a20_heart_kills_zombie_deck++;
				break;
			case 6:
				a20_heart_kills_aqua_deck++;
				break;
			case 7:
				a20_heart_kills_fiend_deck++;
				break;
			case 8:
				a20_heart_kills_machine_deck++;
				break;
			case 9:
				a20_heart_kills_warrior_deck++;
				break;
			case 10:
				a20_heart_kills_insect_deck++;
				break;
			case 11:
				a20_heart_kills_plant_deck++;
				break;
			case 12:
				a20_heart_kills_predaplant_deck++;
				break;
			case 13:
				a20_heart_kills_megatype_deck++;
				break;
			case 14:
				a20_heart_kills_increment_deck++;
				break;
			case 15:
				a20_heart_kills_creator_deck++;
				break;
			case 16:
				a20_heart_kills_ojama_deck++;
				break;
			case 17:
				a20_heart_kills_exodia_deck++;
				break;
			case 18:
				a20_heart_kills_giants_deck++;
				break;
			case 19:
				a20_heart_kills_a1_deck++;
				break;
			case 20:
				a20_heart_kills_a2_deck++;
				break;
			case 21:
				a20_heart_kills_a3_deck++;
				break;
			case 22:
				a20_heart_kills_p1_deck++;
				break;
			case 23:
				a20_heart_kills_p2_deck++;
				break;
			case 24:
				a20_heart_kills_p3_deck++;
				break;
			case 25:
				a20_heart_kills_p4_deck++;
				break;
			case 26:
				a20_heart_kills_p5_deck++;
				break;
			case 27:
				a20_heart_kills_random_small_deck++;
				heartKillsRandomDecks++;
				break;
			case 28:
				a20_heart_kills_random_big_deck++;
				heartKillsRandomDecks++;
				break;
		}
		saveProperties();
		setupNumberLists();
		unlockDecks();
		unlockDecksGlobally();
	}
	
	private void loadProperties()
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
        	heartKillsRandomDecks = config.getInt("heartKillsRandomDecks");
        	heartKillsTotal = config.getInt("heartKillsTotal");
        	playOnce = config.getBool("playOnce");
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
