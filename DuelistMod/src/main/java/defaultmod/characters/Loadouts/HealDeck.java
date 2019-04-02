package defaultmod.characters.Loadouts;

import java.util.ArrayList;

import defaultmod.characters.DuelistCustomLoadout;

public class HealDeck extends DuelistCustomLoadout
{
    public HealDeck()
    {
        this.ID = 12;
        this.Name = "Heal Deck";
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
