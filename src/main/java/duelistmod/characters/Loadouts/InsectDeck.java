package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class InsectDeck extends DuelistCustomLoadout
{
    public InsectDeck()
    {
        this.ID = 10;
        this.Name = Strings.configInsectDeck;
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
