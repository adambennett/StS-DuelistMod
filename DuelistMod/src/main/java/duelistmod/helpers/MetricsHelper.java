package duelistmod.helpers;

import java.util.HashMap;

import duelistmod.DuelistMod;

public class MetricsHelper 
{
	private static String getPoolFillType(int index)
	{
		switch (index)
		{
			case 0:
				return "Deck + Basic (Default)";
			case 1:
				return "Deck Only";
			case 2:
				return "Basic Only";
			case 3:
				return "Deck + Basic + 1 Random";
			case 4:
				return "Deck + 1 Random";
			case 5:
				return "Basic + 1 Random";
			case 6:
				return "Basic + Deck + 2 Random";
			case 7:
				return "2 Random";
			case 8:
				return "Deck + 2 Random";
			case 9:
				return "ALL Cards";
			default:
				return "unknown pool fill type";
		}
	}

	public static void setupCustomMetrics(HashMap<Object, Object> par)
	{
		par.put("starting_deck", StarterDeckSetup.getCurrentDeck().getSimpleName());
		par.put("allow_boosters", DuelistMod.allowBoosters);
		par.put("always_boosters", DuelistMod.alwaysBoosters);
		par.put("remove_toons", DuelistMod.toonBtnBool);
		par.put("remove_ojama", DuelistMod.ojamaBtnBool);
		par.put("remove_creator", DuelistMod.creatorBtnBool);
		par.put("remove_exodia", DuelistMod.exodiaBtnBool);
		par.put("add_base_game_cards", DuelistMod.baseGameCards);
		par.put("reduced_basic", DuelistMod.smallBasicSet);
		par.put("unlock_all_decks", DuelistMod.unlockAllDecks);
		par.put("remove_card_rewards", DuelistMod.removeCardRewards);
		par.put("encounter_duelist_enemies", DuelistMod.duelistMonsters);
		par.put("challenge_mode", DuelistMod.challengeMode);
		par.put("duelist_curses", DuelistMod.duelistCurses);
		par.put("bonus_puzzle_summons", DuelistMod.forcePuzzleSummons);
		par.put("pool_fill", getPoolFillType(DuelistMod.setIndex));
		par.put("number_of_spells", DuelistMod.spellsObtained);
		par.put("number_of_traps", DuelistMod.trapsObtained);
		par.put("number_of_monsters", DuelistMod.monstersObtained);
		par.put("total_synergy_tributes", DuelistMod.synergyTributesRan);
		par.put("highest_max_summons", DuelistMod.highestMaxSummonsObtained);
		par.put("number_of_resummons", DuelistMod.resummonsThisRun);	
		par.put("bonus_deck_stats", DuelistMod.bonusUnlockHelper.logMetrics());
		par.put("duelistmod_version", DuelistMod.version);	
	}
	
}
