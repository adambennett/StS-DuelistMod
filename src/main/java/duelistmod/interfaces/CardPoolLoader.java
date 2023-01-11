package duelistmod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

@FunctionalInterface
public interface CardPoolLoader {

    ArrayList<AbstractCard> pool();

}
