package duelistmod.dto;

import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistCard;

public class FromCardResult {

    private final boolean isNew;
    private final AbstractEnemyDuelistCard cardHolder;

    public FromCardResult(boolean isNew, AbstractEnemyDuelistCard cardHolder) {
        this.isNew = isNew;
        this.cardHolder = cardHolder;
    }

    public boolean isNew() { return this.isNew; }

    public AbstractEnemyDuelistCard cardHolder() { return this.cardHolder; }

    @Override
    public String toString() {
        return "FromCardResult{" +
                "isNew=" + isNew +
                ", cardHolder=" + cardHolder +
                '}';
    }
}


