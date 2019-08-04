package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class P3Deck extends DuelistCustomLoadout
{
    public P3Deck()
    {
        this.ID = 24;
        this.Name = Strings.configPharaoh3;
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
