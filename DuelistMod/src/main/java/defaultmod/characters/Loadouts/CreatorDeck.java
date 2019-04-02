package defaultmod.characters.Loadouts;

import java.util.ArrayList;

import defaultmod.characters.DuelistCustomLoadout;

public class CreatorDeck extends DuelistCustomLoadout
{
    public CreatorDeck()
    {
        this.ID = 4;
        this.Name = "Creator Deck";
        this.cardCount = "10 Cards";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
