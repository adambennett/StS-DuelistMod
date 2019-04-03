package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;

public class GenerationDeck extends DuelistCustomLoadout
{
    public GenerationDeck()
    {
        this.ID = 10;
        this.Name = "Generation Deck";
        this.cardCount = "16 Cards";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
