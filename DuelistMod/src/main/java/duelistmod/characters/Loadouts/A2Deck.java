package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class A2Deck extends DuelistCustomLoadout
{
    public A2Deck()
    {
        this.ID = 20;
        this.Name = Strings.configAscended2;
        this.cardCount = "?? Cards";
        this.permaLockMessage = "Defeat the Heart on Ascension 20 (Ascended I)";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
