package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class A3Deck extends DuelistCustomLoadout
{
    public A3Deck()
    {
        this.ID = 21;
        this.Name = Strings.configAscended3;
        this.cardCount = "?? Cards";
        this.permaLockMessage = "Defeat the Heart on Ascension 20 (All Decks)";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
