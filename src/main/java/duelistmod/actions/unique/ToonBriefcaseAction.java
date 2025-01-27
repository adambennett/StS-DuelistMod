package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.duelistPowers.ArcanaPower;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class ToonBriefcaseAction extends AbstractGameAction {

    private final AnyDuelist duelist;
    private final HashSet<AbstractCard> toonsPlayedThisTurn;

    public ToonBriefcaseAction(AnyDuelist duelist, HashSet<AbstractCard> toonsPlayedThisTurn) {
        this.duelist = duelist;
        this.toonsPlayedThisTurn = toonsPlayedThisTurn;
    }

    public void update() {

        HashMap<UUID, CardGroup> groups = new HashMap<>();
        HashMap<UUID, AbstractCard> cardByUUID = new HashMap<>();
        for (AbstractCard toon : this.toonsPlayedThisTurn) {
            if (this.duelist.hand().stream().anyMatch(c -> c.uuid.equals(toon.uuid))) {
                groups.put(toon.uuid, this.duelist.handGroup());
                cardByUUID.put(toon.uuid, toon);
                continue;
            }
            if (this.duelist.drawPile().stream().anyMatch(c -> c.uuid.equals(toon.uuid))) {
                groups.put(toon.uuid, this.duelist.drawPileGroup());
                cardByUUID.put(toon.uuid, toon);
                continue;
            }
            if (this.duelist.discardPile().stream().anyMatch(c -> c.uuid.equals(toon.uuid))) {
                groups.put(toon.uuid, this.duelist.discardPileGroup());
                cardByUUID.put(toon.uuid, toon);
                continue;
            }
            if (this.duelist.limbo().stream().anyMatch(c -> c.uuid.equals(toon.uuid))) {
                groups.put(toon.uuid, this.duelist.limboGroup());
                cardByUUID.put(toon.uuid, toon);
            }
        }
        for (Map.Entry<UUID, CardGroup> entry : groups.entrySet()) {
            if (cardByUUID.containsKey(entry.getKey())) {
                this.addToBot(new ExhaustSpecificCardAction(cardByUUID.get(entry.getKey()), entry.getValue(), true));
            }
        }
        this.toonsPlayedThisTurn.clear();


        if (!this.duelist.hasPower(ArcanaPower.POWER_ID) || this.duelist.getPower(ArcanaPower.POWER_ID).amount < 1) {
            this.isDone = true;
            return;
        }
        int amount = this.duelist.getPower(ArcanaPower.POWER_ID).amount;
        this.duelist.applyPowerToSelf(new ArcanaPower(this.duelist.creature(), this.duelist.creature(), amount));
        this.isDone = true;
    }

}
