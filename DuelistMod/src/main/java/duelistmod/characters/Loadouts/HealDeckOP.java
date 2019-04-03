package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;

public class HealDeckOP extends DuelistCustomLoadout
{
    public HealDeckOP()
    {
        this.ID = 24;
        this.Name = "Old Heal Deck";
        this.cardCount = "9 Cards";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
