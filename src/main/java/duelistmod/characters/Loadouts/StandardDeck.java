package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;

public class StandardDeck extends DuelistCustomLoadout
{
    public StandardDeck()
    {
        this.ID = 0;
        this.Name = DuelistMod.standardDeckString;
        this.deckDesc = "The default starting deck for The Duelist. The card pool mainly consists of Spells, with very few monster tools. Use this deck to get a little more familiar with the mechanics of DuelistMod and how they interact with the base game. Enjoy!";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
