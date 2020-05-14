package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class MegatypeDeck extends DuelistCustomLoadout
{
    public MegatypeDeck()
    {
        this.ID = 13;
        this.Name = Strings.configMegatypeDeck;
        this.cardCount = "?? Cards";
        this.deckDesc = "This deck is probably a bit silly. Megatyped monsters are very strong, so a deck based entirely around this concept... yeah not exactly what you'd call balanced. Enjoy!";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
