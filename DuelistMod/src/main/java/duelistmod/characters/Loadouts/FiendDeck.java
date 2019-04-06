package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;

public class FiendDeck extends DuelistCustomLoadout
{
    public FiendDeck()
    {
        this.ID = 7;
        this.Name = "Fiend Deck";
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
