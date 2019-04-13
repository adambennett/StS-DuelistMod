package duelistmod.characters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.CardCrawlGame;

import duelistmod.DuelistMod;
import duelistmod.characters.Loadouts.*;


//Copied from The Animator, then modified
public class DuelistCharacterSelect
{
    private static int index;
    private static ArrayList<DuelistCustomLoadout> customLoadouts;

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
    
    public static int getIndex()
    {
    	return index;
    }
    
    public static void refreshCharacterDecks()
    {
    	customLoadouts = new ArrayList<>();
    	
    	if (DuelistMod.unlockAllDecks)
        {
        	int save = 0;
            AddLoadout(new StandardDeck(), save, "10 cards");  
            AddLoadout(new DragonDeck(), save, "10 cards"); 
            AddLoadout(new NatureDeck(), save, "10 cards"); 
            AddLoadout(new SpellcasterDeck(), save, "10 cards");
            AddLoadout(new ToonDeck(), save, "10 cards"); 
            AddLoadout(new ZombieDeck(), save, "10 cards"); 
            AddLoadout(new AquaDeck(), save, "10 cards");
            AddLoadout(new FiendDeck(), save, "10 cards");
            AddLoadout(new MachineDeck(), save, "10 cards"); 
            AddLoadout(new MagnetDeck(), save, "10 cards");
            AddLoadout(new CreatorDeck(), save, "10 cards"); 
            AddLoadout(new OjamaDeck(), save, "12 cards");
            AddLoadout(new GenerationDeck(), save, "16 cards"); 
            AddLoadout(new OrbDeck(), save, "12 cards");  
            AddLoadout(new ResummonDeck(), save, "10 cards");  
            AddLoadout(new IncrementDeck(), save, "14 cards");  
            AddLoadout(new ExodiaDeck(), save, "60 cards"); 
            AddLoadout(new HealDeck(), save, "12 cards");  
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 random Duelist cards");   
        }
    	
        else
        {
        	int save = 0;
        	AddLoadout(new StandardDeck(), save, "10 cards");
            AddLoadout(new DragonDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new NatureDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new SpellcasterDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new ToonDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new ZombieDeck(), save, "10 cards");  save += 5000;
            AddLoadout(new AquaDeck(), save, "10 cards"); save += 5000;
            AddLoadout(new FiendDeck(), save, "10 cards"); save += 5000;
            AddLoadout(new MachineDeck(), save, "10 cards"); save += 5000;
            AddLoadout(new MagnetDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new CreatorDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new OjamaDeck(), save, "12 cards"); save += 1000;
            AddLoadout(new GenerationDeck(), save, "16 cards"); save += 10000;
            AddLoadout(new OrbDeck(), save, "12 cards");  save += 10000;
            AddLoadout(new ResummonDeck(), save, "10 cards");  save += 10000;
            AddLoadout(new IncrementDeck(), save, "14 cards");  save += 20000;
            AddLoadout(new ExodiaDeck(), save, "60 cards"); save += 10000;
            AddLoadout(new HealDeck(), save, "12 cards"); save += 2500;
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 random Duelist cards");   
        }
    }

    static
    {
        index = 0;
        customLoadouts = new ArrayList<>();
        
        if (DuelistMod.unlockAllDecks)
        {
        	int save = 0;
            AddLoadout(new StandardDeck(), save, "10 cards");  
            AddLoadout(new DragonDeck(), save, "10 cards"); 
            AddLoadout(new NatureDeck(), save, "10 cards"); 
            AddLoadout(new SpellcasterDeck(), save, "10 cards");
            AddLoadout(new ToonDeck(), save, "10 cards"); 
            AddLoadout(new ZombieDeck(), save, "10 cards"); 
            AddLoadout(new AquaDeck(), save, "10 cards");
            AddLoadout(new FiendDeck(), save, "10 cards");
            AddLoadout(new MachineDeck(), save, "10 cards"); 
            AddLoadout(new MagnetDeck(), save, "10 cards");
            AddLoadout(new CreatorDeck(), save, "10 cards"); 
            AddLoadout(new OjamaDeck(), save, "12 cards");
            AddLoadout(new GenerationDeck(), save, "16 cards"); 
            AddLoadout(new OrbDeck(), save, "12 cards");  
            AddLoadout(new ResummonDeck(), save, "10 cards");  
            AddLoadout(new IncrementDeck(), save, "14 cards");  
            AddLoadout(new ExodiaDeck(), save, "60 cards"); 
            AddLoadout(new HealDeck(), save, "12 cards");  
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 random Duelist cards");
        }
        
        else
        {
        	int save = 0;
        	AddLoadout(new StandardDeck(), save, "10 cards");
            AddLoadout(new DragonDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new NatureDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new SpellcasterDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new ToonDeck(), save, "10 cards"); save += 2500;
            AddLoadout(new ZombieDeck(), save, "10 cards");  save += 5000;
            AddLoadout(new AquaDeck(), save, "10 cards"); save += 5000;
            AddLoadout(new FiendDeck(), save, "10 cards"); save += 5000;
            AddLoadout(new MachineDeck(), save, "10 cards"); save += 5000;
            AddLoadout(new MagnetDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new CreatorDeck(), save, "10 cards"); save += 1000;
            AddLoadout(new OjamaDeck(), save, "12 cards"); save += 1000;
            AddLoadout(new GenerationDeck(), save, "16 cards"); save += 10000;
            AddLoadout(new OrbDeck(), save, "12 cards");  save += 10000;
            AddLoadout(new ResummonDeck(), save, "10 cards");  save += 10000;
            AddLoadout(new IncrementDeck(), save, "14 cards");  save += 20000;
            AddLoadout(new ExodiaDeck(), save, "60 cards"); save += 10000;
            AddLoadout(new HealDeck(), save, "12 cards"); save += 2500;
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 random Duelist cards");  
        }
        
    }
}