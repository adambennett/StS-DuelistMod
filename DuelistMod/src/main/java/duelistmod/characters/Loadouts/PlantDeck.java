package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class PlantDeck extends DuelistCustomLoadout
{
    public PlantDeck()
    {
        this.ID = 11;
        this.Name = Strings.configPlantDeck;
        this.cardCount = "?? Cards";
        this.permaLockMessage = "Unavailable";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
