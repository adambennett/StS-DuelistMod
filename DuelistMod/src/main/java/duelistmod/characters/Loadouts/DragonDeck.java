package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;

public class DragonDeck extends DuelistCustomLoadout
{
    public DragonDeck()
    {
        this.ID = 1;
        this.Name = DuelistMod.dragonDeckString;
        this.cardCount = "10 Cards";
        this.deckDesc = "Unleash the power of Yu-Gi-Oh! dragon cards! Playing this deck will force you to play the run as Seto Kaiba instead of Yugi, even if you do not have the config option set to Play as Kaiba. The Dragon pool is vast, and filled with several other types of monsters besides just dragons - including Aqua, Dinosaurs, and Machines! Make use of flame-based orbs, massive damage cards and tribute reduction effects to successfully defeat the Heart. Summoning cards are premium in the dragon pool, so be sure to pick up strong summon monsters whenever possible, or watch out for some of the other summoning tools available in the pool.";
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
