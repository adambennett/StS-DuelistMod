package defaultmod.characters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.CardCrawlGame;

import defaultmod.DefaultMod;
import defaultmod.characters.Loadouts.*;


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
    	if (DefaultMod.unlockAllDecks)
        {
        	int save = 0;
            AddLoadout(new StandardDeck(), save, "10 cards");  
            AddLoadout(new DragonDeck(), save, "10 cards"); 
            AddLoadout(new NatureDeck(), save, "11 cards"); 
            AddLoadout(new SpellcasterDeck(), save, "9 cards");  
            AddLoadout(new CreatorDeck(), save, "10 cards"); 
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 random Duelist cards");  
            AddLoadout(new ToonDeck(), save, "10 cards"); 
            AddLoadout(new OrbDeck(), save, "12 cards");  
            AddLoadout(new ResummonDeck(), save, "10 cards");  
            AddLoadout(new GenerationDeck(), save, "16 cards"); 
            AddLoadout(new OjamaDeck(), save, "12 cards");  
            AddLoadout(new HealDeck(), save, "10 cards");  
            AddLoadout(new IncrementDeck(), save, "14 cards");  
            AddLoadout(new ExodiaDeck(), save, "60 cards"); 
            //AddLoadout(new MagnetDeck(), save, "0 cards");
            //AddLoadout(new AquaDeck(), save, "0 cards");
            //AddLoadout(new MachineDeck(), save, "0 cards"); 
            AddLoadout(new OriginalDeck(), save, "10 cards"); 
            AddLoadout(new DragonDeckOP(), save, "10 cards"); 
            AddLoadout(new NatureDeckOP(), save, "11 cards"); 
            AddLoadout(new SpellcasterDeckOP(), save, "10 cards");             
        }
        else
        {
        	int save = 0;
            AddLoadout(new StandardDeck(), save, "10 cards"); save += 500;
            AddLoadout(new DragonDeck(), save, "10 cards"); save += 1500;
            AddLoadout(new NatureDeck(), save, "11 cards"); save += 1500;
            AddLoadout(new SpellcasterDeck(), save, "9 cards"); save += 2000;
            AddLoadout(new CreatorDeck(), save, "10 cards"); save += 500;
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 random Duelist cards"); save += 1000;
            AddLoadout(new ToonDeck(), save, "10 cards"); save += 2000;
            AddLoadout(new OrbDeck(), save, "12 cards"); save += 3000;
            AddLoadout(new ResummonDeck(), save, "10 cards"); 
            AddLoadout(new GenerationDeck(), save, "16 cards"); save += 3000;
            AddLoadout(new OjamaDeck(), save, "12 cards"); save += 3000;
            AddLoadout(new HealDeck(), save, "10 cards"); save += 3000;
            AddLoadout(new IncrementDeck(), save, "14 cards"); save += 4000;
            AddLoadout(new ExodiaDeck(), save, "60 cards"); save = 0; //save += 4000;
            //AddLoadout(new MagnetDeck(), save, "0 cards"); save += 5000;
            //AddLoadout(new AquaDeck(), save, "0 cards"); save += 5000;
            //AddLoadout(new MachineDeck(), save, "0 cards"); save = 0;
            AddLoadout(new OriginalDeck(), save, "10 cards"); save += 500;
            AddLoadout(new DragonDeckOP(), save, "10 cards"); save += 1500;
            AddLoadout(new NatureDeckOP(), save, "11 cards"); save += 1500;
            AddLoadout(new SpellcasterDeckOP(), save, "10 cards"); save += 2000;
            
        }
    }

    static
    {
        index = 0;
        customLoadouts = new ArrayList<>();

        /*String recommended = uiText[5];
        String balanced = uiText[6];
        String unbalanced = uiText[7];
        String veryUnbalanced = uiText[8];
        String special = uiText[9];*/
        if (DefaultMod.unlockAllDecks)
        {
        	int save = 0;
            AddLoadout(new StandardDeck(), save, "10 cards");  
            AddLoadout(new DragonDeck(), save, "10 cards");  
            AddLoadout(new NatureDeck(), save, "11 cards"); 
            AddLoadout(new SpellcasterDeck(), save, "10 cards");  
            AddLoadout(new CreatorDeck(), save, "4 cards"); 
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 randnom Duelist cards");  
            AddLoadout(new ToonDeck(), save, "11 cards"); 
            AddLoadout(new OrbDeck(), save, "10 cards");  
            AddLoadout(new ResummonDeck(), save, "10 cards");  
            AddLoadout(new GenerationDeck(), save, "12 cards"); 
            AddLoadout(new OjamaDeck(), save, "12 cards");  
            AddLoadout(new HealDeck(), save, "9 cards");  
            AddLoadout(new IncrementDeck(), save, "14 cards");  
            AddLoadout(new ExodiaDeck(), save, "60 cards"); 
            //AddLoadout(new MagnetDeck(), save, "0 cards");
            //AddLoadout(new AquaDeck(), save, "0 cards");
            //AddLoadout(new MachineDeck(), save, "0 cards"); 
        }
        else
        {
        	int save = 0;
            AddLoadout(new StandardDeck(), save, "10 cards"); save += 500;
            AddLoadout(new DragonDeck(), save, "10 cards"); save += 1500;
            AddLoadout(new NatureDeck(), save, "11 cards"); save += 1500;
            AddLoadout(new SpellcasterDeck(), save, "10 cards"); save += 2000;
            AddLoadout(new CreatorDeck(), save, "4 cards"); save += 500;
            AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
            AddLoadout(new RandomDeckBig(), save, "15 randnom Duelist cards"); save += 1000;
            AddLoadout(new ToonDeck(), save, "11 cards"); save += 2000;
            AddLoadout(new OrbDeck(), save, "10 cards"); save += 3000;
            AddLoadout(new ResummonDeck(), save, "10 cards"); 
            AddLoadout(new GenerationDeck(), save, "12 cards"); save += 3000;
            AddLoadout(new OjamaDeck(), save, "12 cards"); save += 3000;
            AddLoadout(new HealDeck(), save, "9 cards"); save += 3000;
            AddLoadout(new IncrementDeck(), save, "14 cards"); save += 4000;
            AddLoadout(new ExodiaDeck(), save, "60 cards"); save += 4000;
            //AddLoadout(new MagnetDeck(), save, "0 cards"); save += 5000;
            //AddLoadout(new AquaDeck(), save, "0 cards"); save += 5000;
            //AddLoadout(new MachineDeck(), save, "0 cards"); save += 5000;
        }
        
    }
}