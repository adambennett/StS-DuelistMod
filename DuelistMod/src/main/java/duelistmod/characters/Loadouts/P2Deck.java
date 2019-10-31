package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class P2Deck extends DuelistCustomLoadout
{
    public P2Deck()
    {
        this.ID = 23;
        this.Name = Strings.configPharaoh2;
        this.cardCount = "?? Cards";
        this.longDesc = true;
        this.permaLockMessage = "Unavailable";
        this.unlockReq = "Deck currently unavailable! Defeat the Heart on Challenge 5+ and defeat the Heart with all three Ascended decks.";
       
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
