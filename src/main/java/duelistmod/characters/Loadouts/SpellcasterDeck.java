package duelistmod.characters.Loadouts;

import java.util.ArrayList;

import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCustomLoadout;

public class SpellcasterDeck extends DuelistCustomLoadout
{
    public SpellcasterDeck()
    {
        this.ID = 3;
        this.Name = DuelistMod.spellcasterDeckString;
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        // grab starter deck from defaultmod -> get arraylist of cards -> for each card in list add card.getID() to res
        return res;
    }
}
