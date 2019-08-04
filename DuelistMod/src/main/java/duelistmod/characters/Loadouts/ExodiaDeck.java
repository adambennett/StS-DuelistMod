package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;

public class ExodiaDeck extends DuelistCustomLoadout
{
    public ExodiaDeck()
    {
        this.ID = 17;
        this.Name = DuelistMod.exodiaDeckString;
        this.cardCount = "60 Cards";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
