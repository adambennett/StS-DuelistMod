package duelistmod.characters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.megacrit.cardcrawl.core.CardCrawlGame;

import duelistmod.DuelistMod;
import duelistmod.characters.Loadouts.*;
import duelistmod.dto.LoadoutUnlockOrderInfo;
import duelistmod.enums.*;


//Copied from The Animator, then modified
public class DuelistCharacterSelect
{
    private static int index;
    private static ArrayList<DuelistCustomLoadout> customLoadouts;
    private static final LinkedHashMap<String, Integer> unlockOrderInfo;

    protected static final String[] uiText = CardCrawlGame.languagePack.getUIString("theDuelist:ConfigMenuText").TEXT;

    public static DuelistCustomLoadout GetSelectedLoadout()
    {
        return customLoadouts.get(index);
    }

    public static void NextLoadout()
    {
        index += 1;
        if (index >= customLoadouts.size())
        {
            index = 0;
        }
    }

    public static void PreviousLoadout()
    {
        index -= 1;
        if (index < 0)
        {
            index = customLoadouts.size() - 1;
        }
    }

    private static void AddLoadout(DuelistCustomLoadout loadout, int level, String description)
    {
        loadout.unlockLevel = level;
        loadout.description = description;
        customLoadouts.add(loadout);
    }
    
    private static void AddLoadout(DuelistCustomLoadout loadout, int level, String description, boolean permaLocked)
    {
        loadout.unlockLevel = level;
        loadout.permaLocked = permaLocked;
        loadout.description = description;
        customLoadouts.add(loadout);
    }
    
    public static int getIndex()
    {
    	return index;
    }
    
    public static void refreshCharacterDecks()
    {
    	customLoadouts = new ArrayList<>();
    	loadSetup();
    }

    public static LoadoutUnlockOrderInfo getNextUnlockDeckAndScore(int currentScore) {
        String firstUnlock = null;
        Integer secondUnlock = null;
        for (Map.Entry<String, Integer> entry : unlockOrderInfo.entrySet()) {
            if (firstUnlock == null) {
                firstUnlock = entry.getKey();
                continue;
            }
            secondUnlock = entry.getValue();
            break;
        }

        if (currentScore < unlockOrderInfo.get(firstUnlock)) {
            return new LoadoutUnlockOrderInfo(firstUnlock, unlockOrderInfo.get(firstUnlock), secondUnlock);
        }

        LoadoutUnlockOrderInfo ret = null;
        for (Map.Entry<String, Integer> entry : unlockOrderInfo.entrySet()) {
            if (ret != null) {
                ret.setNextCost(entry.getValue());
                return ret;
            }
            if (entry.getValue() > currentScore) {
                ret = new LoadoutUnlockOrderInfo(entry.getKey(), entry.getValue());
            }
        }
        return ret == null ? new LoadoutUnlockOrderInfo("ALL DECKS UNLOCKED", currentScore) : ret;
    }

    // The math performed on save below should match EXACTLY what is performed in the static block as well
    // Modify this block to actually modify the unlock logic, static block handles logic for the progress bar to know when decks get unlocked
    private static void loadSetup()
    {
        int save = 0;
        AddLoadout(new StandardDeck(), save, "10 cards"); save += 500;
        AddLoadout(new DragonDeck(), save, "10 cards"); save += 1000;
        AddLoadout(new NaturiaDeck(), save, "10 cards"); save += 1000;
        AddLoadout(new SpellcasterDeck(), save, "10 cards"); save += 1000;
        AddLoadout(new ToonDeck(), save, "10 cards"); save += 1500;
        AddLoadout(new ZombieDeck(), save, "10 cards");  save += 1500;
        AddLoadout(new AquaDeck(), save, "10 cards"); save += 1500;
        AddLoadout(new FiendDeck(), save, "10 cards"); save += 2000;
        AddLoadout(new MachineDeck(), save, "10 cards"); save += 2000;
        AddLoadout(new MagnetDeck(), save, "10 cards"); save += 2000;
        AddLoadout(new InsectDeck(), save, "10 cards"); save += 2000;
        AddLoadout(new PlantDeck(), save, "10 cards"); save += 4000;
        AddLoadout(new PredaplantDeck(), save, "10 cards", DuelistMod.modMode != Mode.DEV);
        AddLoadout(new MegatypeDeck(), save, "10 cards"); save += 5000;
        AddLoadout(new IncrementDeck(), save, "10 cards"); save += 2500;
        AddLoadout(new CreatorDeck(), save, "10 cards");  save += 2500;
        AddLoadout(new OjamaDeck(), save, "12 cards");  save += 5000;
        AddLoadout(new ExodiaDeck(), save, "60 cards");  save += 5000;
        AddLoadout(new GiantsDeck(), save, "10 cards", DuelistMod.modMode != Mode.DEV);  save = 0;
        AddLoadout(new A1Deck(), save, "10 cards", !DuelistMod.isAscendedDeckOneUnlocked && DuelistMod.modMode != Mode.DEV);
        AddLoadout(new A2Deck(), save, "10 cards", !DuelistMod.isAscendedDeckTwoUnlocked && DuelistMod.modMode != Mode.DEV);
        AddLoadout(new A3Deck(), save, "10 cards", !DuelistMod.isAscendedDeckThreeUnlocked && DuelistMod.modMode != Mode.DEV);
        AddLoadout(new P1Deck(), save, "10 cards", !DuelistMod.isPharaohDeckOneUnlocked && DuelistMod.modMode != Mode.DEV);
        AddLoadout(new P2Deck(), save, "10 cards", !DuelistMod.isPharaohDeckTwoUnlocked && DuelistMod.modMode != Mode.DEV);
        AddLoadout(new P3Deck(), save, "10 cards", !DuelistMod.isPharaohDeckThreeUnlocked && DuelistMod.modMode != Mode.DEV);
        AddLoadout(new P4Deck(), save, "10 cards", !DuelistMod.isPharaohDeckFourUnlocked && DuelistMod.modMode != Mode.DEV);
        AddLoadout(new P5Deck(), save, "10 cards", !DuelistMod.isPharaohDeckFiveUnlocked && DuelistMod.modMode != Mode.DEV);
        AddLoadout(new RandomDeckSmall(), 1000, "10 random Duelist cards");
        AddLoadout(new RandomDeckBig(), 1000, "15 random Duelist cards");
        AddLoadout(new RandomDeckUpgrade(), 0, "10 random Duelist cards", !DuelistMod.isExtraRandomDecksUnlocked);
        AddLoadout(new RandomDeckMetronome(), 0, "15 Metronomes", !DuelistMod.isExtraRandomDecksUnlocked);
    }

    static
    {
        index = 0;
        customLoadouts = new ArrayList<>();
        unlockOrderInfo = new LinkedHashMap<>();
        int save = 500;
        unlockOrderInfo.put("Dragon Deck", save); save += 1000;
        unlockOrderInfo.put("Naturia Deck", save); save += 1000;
        unlockOrderInfo.put("Spellcaster Deck", save); save += 1000;
        unlockOrderInfo.put("Toon Deck", save); save += 1500;
        unlockOrderInfo.put("Zombie Deck", save); save += 1500;
        unlockOrderInfo.put("Aqua Deck", save); save += 1500;
        unlockOrderInfo.put("Fiend Deck", save); save += 2000;
        unlockOrderInfo.put("Machine Deck", save); save += 2000;
        unlockOrderInfo.put("Warrior Deck", save); save += 2000;
        unlockOrderInfo.put("Insect Deck", save); save += 2000;
        unlockOrderInfo.put("Plant Deck", save); save += 4000;
        unlockOrderInfo.put("Megatype Deck", save); save += 5000;
        unlockOrderInfo.put("Increment Deck", save); save += 2500;
        unlockOrderInfo.put("Creator Deck", save); save += 2500;
        unlockOrderInfo.put("Ojama Deck", save); save += 5000;
        unlockOrderInfo.put("Exodia Deck", save);
        loadSetup();
    }
}
