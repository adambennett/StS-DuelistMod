package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;

public class ExodiaDeck extends DuelistCustomLoadout
{
    public ExodiaDeck()
    {
        this.ID = 17;
        this.Name = DuelistMod.exodiaDeckString;
        this.cardCount = "60 Cards";
        this.deckDesc = "While playing with the Exodia deck, you cannot add or remove cards from your deck. You must slay the spire with just the 60 starting cards you begin the run with. Summon the legendary Exodia, the Forbidden One to defeat powerful foes and conquer the Heart!";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
