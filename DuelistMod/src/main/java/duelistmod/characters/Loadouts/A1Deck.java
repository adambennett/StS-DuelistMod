package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;
import duelistmod.variables.Strings;

public class A1Deck extends DuelistCustomLoadout
{
    public A1Deck()
    {
        this.ID = 19;
        this.Name = Strings.configAscended1;
        this.cardCount = "?? Cards";
        this.longDesc = true;
        this.permaLockMessage = "Locked";
        this.unlockReq = "Defeat the Heart on Ascension 10+";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
