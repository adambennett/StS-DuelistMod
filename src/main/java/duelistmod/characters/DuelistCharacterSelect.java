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
        for (Map.Entry<String, Integer> entry : unlockOrderInfo.entrySet()) {
            firstUnlock = entry.getKey();
            break;
        }
        if (currentScore < unlockOrderInfo.get(firstUnlock)) {
            new LoadoutUnlockOrderInfo(firstUnlock, unlockOrderInfo.get(firstUnlock));
        }
        for (Map.Entry<String, Integer> entry : unlockOrderInfo.entrySet()) {
            if (entry.getValue() > currentScore) {
                return new LoadoutUnlockOrderInfo(entry.getKey(), entry.getValue());
            }
        }
        return null;
    }
    
    private static void loadSetup()
    {
    	if (DuelistMod.unlockAllDecks)
        {
        	int save = 0;
            AddLoadout(new StandardDeck(), save, "10 cards");  
            AddLoadout(new DragonDeck(), save, "10 cards"); 
            AddLoadout(new NaturiaDeck(), save, "10 cards"); 
            AddLoadout(new SpellcasterDeck(), save, "10 cards");
            AddLoadout(new ToonDeck(), save, "10 cards"); 
            AddLoadout(new ZombieDeck(), save, "10 cards"); 
            AddLoadout(new AquaDeck(), save, "10 cards");
            AddLoadout(new FiendDeck(), save, "10 cards");
            AddLoadout(new MachineDeck(), save, "10 cards"); 
            AddLoadout(new MagnetDeck(), save, "10 cards");
            AddLoadout(new InsectDeck(), save, "10 cards");
            AddLoadout(new PlantDeck(), save, "10 cards");
            AddLoadout(new PredaplantDeck(), save, "10 cards", DuelistMod.modMode == Mode.PROD);
            AddLoadout(new MegatypeDeck(), save, "10 cards");
            AddLoadout(new IncrementDeck(), save, "10 cards");  
            AddLoadout(new CreatorDeck(), save, "10 cards"); 
            AddLoadout(new OjamaDeck(), save, "12 cards");
            AddLoadout(new ExodiaDeck(), save, "60 cards"); 
            AddLoadout(new GiantsDeck(), save, "10 cards", DuelistMod.modMode == Mode.PROD);
            AddLoadout(new A1Deck(), save, "10 cards", !DuelistMod.isAscendedDeckOneUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new A2Deck(), save, "10 cards", !DuelistMod.isAscendedDeckTwoUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new A3Deck(), save, "10 cards", !DuelistMod.isAscendedDeckThreeUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P1Deck(), save, "10 cards", !DuelistMod.isPharaohDeckOneUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P2Deck(), save, "10 cards", !DuelistMod.isPharaohDeckTwoUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P3Deck(), save, "10 cards", !DuelistMod.isPharaohDeckThreeUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P4Deck(), save, "10 cards", !DuelistMod.isPharaohDeckFourUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P5Deck(), save, "10 cards", !DuelistMod.isPharaohDeckFiveUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 random Duelist cards"); 
            AddLoadout(new RandomDeckUpgrade(), save, "10 random Duelist cards");   
            AddLoadout(new RandomDeckMetronome(), save, "15 Metronomes");   
        }
    	
        else
        {
        	int save = 0;
            AddLoadout(new StandardDeck(), save, "10 cards");  
            AddLoadout(new DragonDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new NaturiaDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new SpellcasterDeck(), save, "10 cards"); save += 1500;
            AddLoadout(new ToonDeck(), save, "10 cards"); save += 1500;
            AddLoadout(new ZombieDeck(), save, "10 cards");  save += 2000;
            AddLoadout(new AquaDeck(), save, "10 cards"); save += 2000;
            AddLoadout(new FiendDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new MachineDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new MagnetDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new InsectDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new PlantDeck(), save, "10 cards"); save += 5000;
            AddLoadout(new PredaplantDeck(), save, "10 cards", DuelistMod.modMode == Mode.PROD);
            AddLoadout(new MegatypeDeck(), save, "10 cards"); save += 5000;
            AddLoadout(new IncrementDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new CreatorDeck(), save, "10 cards");  save += 2500;
            AddLoadout(new OjamaDeck(), save, "12 cards");  save += 5000;
            AddLoadout(new ExodiaDeck(), save, "60 cards");  save += 5000;
            AddLoadout(new GiantsDeck(), save, "10 cards", DuelistMod.modMode == Mode.PROD);  save = 0;
            AddLoadout(new A1Deck(), save, "10 cards", !DuelistMod.isAscendedDeckOneUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new A2Deck(), save, "10 cards", !DuelistMod.isAscendedDeckTwoUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new A3Deck(), save, "10 cards", !DuelistMod.isAscendedDeckThreeUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P1Deck(), save, "10 cards", !DuelistMod.isPharaohDeckOneUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P2Deck(), save, "10 cards", !DuelistMod.isPharaohDeckTwoUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P3Deck(), save, "10 cards", !DuelistMod.isPharaohDeckThreeUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P4Deck(), save, "10 cards", !DuelistMod.isPharaohDeckFourUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new P5Deck(), save, "10 cards", !DuelistMod.isPharaohDeckFiveUnlocked && DuelistMod.modMode != Mode.DEV);
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 random Duelist cards");  
            AddLoadout(new RandomDeckUpgrade(), save, "10 random Duelist cards");   
            AddLoadout(new RandomDeckMetronome(), save, "15 Metronomes");   
        }
    }

    static
    {
        index = 0;
        customLoadouts = new ArrayList<>();
        unlockOrderInfo = new LinkedHashMap<>();
        int save = 1000;
        unlockOrderInfo.put("Dragon Deck", save); save += 1000;
        unlockOrderInfo.put("Naturia Deck", save); save += 1000;
        unlockOrderInfo.put("Spellcaster Deck", save); save += 1500;
        unlockOrderInfo.put("Toon Deck", save); save += 1500;
        unlockOrderInfo.put("Zombie Deck", save); save += 2000;
        unlockOrderInfo.put("Aqua Deck", save); save += 2000;
        unlockOrderInfo.put("Fiend Deck", save); save += 2500;
        unlockOrderInfo.put("Machine Deck", save); save += 2500;
        unlockOrderInfo.put("Warrior Deck", save); save += 2500;
        unlockOrderInfo.put("Insect Deck", save); save += 2500;
        unlockOrderInfo.put("Plant Deck", save); save += 5000;
        unlockOrderInfo.put("Megatype Deck", save); save += 5000;
        unlockOrderInfo.put("Increment Deck", save); save += 2500;
        unlockOrderInfo.put("Creator Deck", save); save += 2500;
        unlockOrderInfo.put("Ojama Deck", save); save += 5000;
        unlockOrderInfo.put("Exodia Deck", save);
        loadSetup();
    }
}
