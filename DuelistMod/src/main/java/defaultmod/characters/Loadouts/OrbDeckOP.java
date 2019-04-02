package defaultmod.characters.Loadouts;

import java.util.ArrayList;

import defaultmod.characters.DuelistCustomLoadout;

public class OrbDeckOP extends DuelistCustomLoadout
{
    public OrbDeckOP()
    {
        this.ID = 22;
        this.Name = "Old Orb Deck";
        this.cardCount = "12 Cards";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
