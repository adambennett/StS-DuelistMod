package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;

public class IncrementDeck extends DuelistCustomLoadout
{
    public IncrementDeck()
    {
        this.ID = 13;
        this.Name = "Increment Deck";
        this.cardCount = "14 Cards";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
