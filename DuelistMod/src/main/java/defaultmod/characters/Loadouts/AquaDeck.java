package defaultmod.characters.Loadouts;

import java.util.ArrayList;

import defaultmod.characters.DuelistCustomLoadout;

public class AquaDeck extends DuelistCustomLoadout
{
    public AquaDeck()
    {
        this.ID = 16;
        this.Name = "Aqua Deck";
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
