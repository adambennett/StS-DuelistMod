package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;

public class RandomDeckBig extends DuelistCustomLoadout
{
    public RandomDeckBig()
    {
        this.ID = 19;
        this.Name = "Random Deck (Big)";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
