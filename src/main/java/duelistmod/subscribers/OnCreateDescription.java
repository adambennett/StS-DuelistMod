package duelistmod.subscribers;

import com.megacrit.cardcrawl.cards.*;

public class OnCreateDescription {

    public String receiveCreateCardDescription(String originalDescription, AbstractCard card) {
        return originalDescription;
    }

}
