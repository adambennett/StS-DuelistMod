package defaultmod.characters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.CardCrawlGame;

import defaultmod.characters.Loadouts.*;

public class DuelistCharacterSelect
{
    private static int index;
    private static final ArrayList<DuelistCustomLoadout> customLoadouts;

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

    static
    {
        index = 0;
        customLoadouts = new ArrayList<>();

        /*String recommended = uiText[5];
        String balanced = uiText[6];
        String unbalanced = uiText[7];
        String veryUnbalanced = uiText[8];
        String special = uiText[9];*/
        int save = 0;
        AddLoadout(new StandardDeck(), save, "10 cards"); save++; save++;
        AddLoadout(new DragonDeck(), save, "10 cards"); save++; save++;
        AddLoadout(new NatureDeck(), save, "11 cards"); save++;
        AddLoadout(new SpellcasterDeck(), save, "10 cards");  save++;
        AddLoadout(new CreatorDeck(), save, "4 cards"); save++;
        AddLoadout(new RandomDeckSmall(), save, "10 random Duelist cards"); 
        AddLoadout(new RandomDeckBig(), save, "15 randnom Duelist cards"); save++; save++;
        AddLoadout(new ToonDeck(), save, "11 cards"); save++;
        AddLoadout(new OrbDeck(), save, "10 cards"); save++; save++;
        AddLoadout(new ResummonDeck(), save, "10 cards"); save++; save++;
        AddLoadout(new GenerationDeck(), save, "12 cards"); 
        AddLoadout(new OjamaDeck(), save, "12 cards"); save++; save++;
        AddLoadout(new HealDeck(), save, "9 cards"); save++; save++;
        AddLoadout(new IncrementDeck(), save, "14 cards"); save++; save++;
        AddLoadout(new ExodiaDeck(), save, "60 cards"); save++;
        //AddLoadout(new MagnetDeck(), save, "0 cards"); save++; save+;
        //AddLoadout(new AquaDeck(), save, "0 cards"); save++; save+;
        //AddLoadout(new MachineDeck(), save, "0 cards"); save++;
    }
}