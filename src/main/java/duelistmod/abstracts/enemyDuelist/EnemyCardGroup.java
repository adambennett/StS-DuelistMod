package duelistmod.abstracts.enemyDuelist;

import com.megacrit.cardcrawl.cards.CardGroup;

public class EnemyCardGroup extends CardGroup {

    public AbstractEnemyDuelist owner;

    public EnemyCardGroup(CardGroupType type) {
        this(type, AbstractEnemyDuelist.enemyDuelist);
    }

    public EnemyCardGroup(CardGroupType type, AbstractEnemyDuelist enemyDuelist) {
        super(type);
        this.owner = enemyDuelist;
    }

    public EnemyCardGroup(CardGroup group, CardGroupType type) {
        this(group, type, AbstractEnemyDuelist.enemyDuelist);
    }

    public EnemyCardGroup(CardGroup group, CardGroupType type, AbstractEnemyDuelist enemyDuelist) {
        super(group, type);
        this.owner = enemyDuelist;
    }
}
