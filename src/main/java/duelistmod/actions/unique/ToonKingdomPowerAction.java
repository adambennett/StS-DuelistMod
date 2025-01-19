package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.cards.pools.toon.ToonKingdom;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;
import java.util.HashMap;
import java.util.UUID;

public class ToonKingdomPowerAction extends AbstractGameAction {

    private final AnyDuelist duelist;
    private final ToonKingdom appliedBy;
    private final boolean isReduce;
    private final HashMap<UUID, Integer> reductionMap;
    private final AbstractCard specificCard;

    public ToonKingdomPowerAction(AnyDuelist duelist, ToonKingdom appliedBy, boolean isReduce, HashMap<UUID, Integer> reductionMap) {
        this(duelist, appliedBy, isReduce, reductionMap, null);
    }

    public ToonKingdomPowerAction(AnyDuelist duelist, ToonKingdom appliedBy, boolean isReduce, HashMap<UUID, Integer> reductionMap, AbstractCard specificCard) {
        this.duelist = duelist;
        this.appliedBy = appliedBy;
        this.isReduce = isReduce;
        this.reductionMap = reductionMap;
        this.specificCard = specificCard;
    }

    public void update() {
        if (this.specificCard != null) {
            if (this.isReduce) {
                this.reduceCard(this.specificCard);
            } else {
                this.increaseCard(this.specificCard);
            }
            this.isDone = true;
            return;
        }

        if (this.isReduce) {
            this.duelist.hand().stream().filter(c -> c.hasTag(Tags.TOON) && !c.uuid.equals(this.appliedBy.uuid)).forEach(this::reduceCard);
            this.duelist.drawPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::reduceCard);
            this.duelist.discardPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::reduceCard);
            this.duelist.exhaustPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::reduceCard);
            this.duelist.limbo().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::reduceCard);
            this.duelist.handGroup().glowCheck();
        } else {
            this.duelist.hand().stream().filter(c -> c.hasTag(Tags.TOON) && !c.uuid.equals(this.appliedBy.uuid)).forEach(this::increaseCard);
            this.duelist.drawPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::increaseCard);
            this.duelist.discardPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::increaseCard);
            this.duelist.exhaustPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::increaseCard);
            this.duelist.limbo().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::increaseCard);
            this.reductionMap.clear();
            this.duelist.handGroup().glowCheck();
        }
        this.isDone = true;
    }

    private void reduceCard(AbstractCard card) {
        if (!this.reductionMap.containsKey(card.uuid) && card.hasTag(Tags.TOON) && card.costForTurn > 0) {
            card.setCostForTurn(card.costForTurn - 1);
            this.reductionMap.put(card.uuid, 1);
        }
    }

    private void increaseCard(AbstractCard card) {
        if (this.reductionMap.containsKey(card.uuid)) {
            if (card.isCostModifiedForTurn) {
                int reduction = this.reductionMap.get(card.uuid);
                card.setCostForTurn(card.costForTurn + reduction);
                if (card.costForTurn == card.cost) {
                    card.isCostModifiedForTurn = false;
                }
            }
            this.reductionMap.remove(card.uuid);
        }
    }
}
