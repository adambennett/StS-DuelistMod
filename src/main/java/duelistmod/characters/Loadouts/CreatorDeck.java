package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;

public class CreatorDeck extends DuelistCustomLoadout
{
    public CreatorDeck()
    {
        this.ID = 15;
        this.Name = DuelistMod.creatorDeckString;
        this.cardCount = "10 Cards";
        this.deckDesc = "This deck is all about the special card The Creator. The Creator exhausts all of your cards and generates a lot of random 1-cost, Ethereal cards in their place. So this deck is highly RNG dependent, and mostly just for fun. The cards that The Creator generates are based on the current card pool, so modifying the contents of the pool will also modify the effect of the card. Perhaps you can create some interesting runs by combining the card pool modification relics and this deck?";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
