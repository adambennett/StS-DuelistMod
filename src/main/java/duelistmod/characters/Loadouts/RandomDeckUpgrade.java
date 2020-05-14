package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.characters.DuelistCustomLoadout;

public class RandomDeckUpgrade extends DuelistCustomLoadout
{
    public RandomDeckUpgrade()
    {
        this.ID = 29;
        this.Name = "Upgrade Deck";
        this.longDesc = true;
        this.unlockReq = "Defeat the Heart with a Random Deck";
        this.permaLockMessage = "Locked";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
