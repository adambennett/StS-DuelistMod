package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class P4Deck extends DuelistCustomLoadout
{
    public P4Deck()
    {
        this.ID = 25;
        this.Name = Strings.configPharaoh4;
        this.cardCount = "?? Cards";
        this.longDesc = true;
        this.permaLockMessage = "Unavailable";
        this.unlockReq = "Deck currently unavailable! Defeat the Heart on Challenge 20 and defeat the Heart with all three Ascended decks.";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
