package duelistmod.subscribers;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;

public class PostCreateDeck {

    // CustomMods can have different impacts:
        // The cardGroup is still the standard deck (referring to the base game, not the duelist standard deck)
        // The cardGroup is empty
        // The cardGroup has been edited
    // CustomMods can also add or remove cards later
    public void receivePostCreateStartingDeck(AbstractPlayer.PlayerClass playerClass, CardGroup deck) {

    }

}
