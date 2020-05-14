package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;

public class ZombieDeck extends DuelistCustomLoadout
{
    public ZombieDeck()
    {
        this.ID = 5;
        this.Name = DuelistMod.zombieDeckString;
        this.cardCount = "?? Cards";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
