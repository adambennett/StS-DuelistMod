package defaultmod.characters.Loadouts;

import java.util.ArrayList;

import defaultmod.characters.DuelistCustomLoadout;

public class MagnetDeck extends DuelistCustomLoadout
{
    public MagnetDeck()
    {
        this.ID = 15;
        this.Name = "Magnet Deck";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
