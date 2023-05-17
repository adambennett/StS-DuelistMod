package duelistmod.stances.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.StanceStrings;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyStance;

public class EnemyNeutralStance extends AbstractEnemyStance
{
    public static final String STANCE_ID = "Neutral";
    private static final StanceStrings stanceString;

    public EnemyNeutralStance() {
        this.ID = "Neutral";
        this.img = null;
        this.name = null;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = EnemyNeutralStance.stanceString.DESCRIPTION[0];
    }

    @Override
    public void render(final SpriteBatch sb) {
    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString("Neutral");
    }
}
