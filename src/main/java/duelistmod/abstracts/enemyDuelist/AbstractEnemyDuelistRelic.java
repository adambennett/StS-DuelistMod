package duelistmod.abstracts.enemyDuelist;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import duelistmod.abstracts.DuelistRelic;

public class AbstractEnemyDuelistRelic extends DuelistRelic {

    public AbstractEnemyDuelistRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx, null);
        this.refreshDescription();
    }

    public void refreshDescription() {
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
}
