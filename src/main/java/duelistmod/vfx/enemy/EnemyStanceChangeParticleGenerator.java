package duelistmod.vfx.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityStanceChangeParticle;

public class EnemyStanceChangeParticleGenerator extends AbstractGameEffect
{
    private float x;
    private float y;
    private String stanceId;

    public EnemyStanceChangeParticleGenerator(final float x, final float y, final String stanceId) {
        this.x = x;
        this.y = y;
        this.stanceId = stanceId;
    }

    public void update() {
        if (!this.stanceId.equals("Calm")) {
            if (this.stanceId.equals("Divinity")) {
                for (int i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.PINK, this.x, this.y));
                }
            }
            else if (this.stanceId.equals("Wrath")) {
                for (int i = 0; i < 10; ++i) {
                    AbstractDungeon.effectsQueue.add(new EnemyWrathStanceChangeParticle(this.x));
                }
            }
            else if (this.stanceId.equals("Neutral")) {
                for (int i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.WHITE, this.x, this.y));
                }
            }
            else {
                for (int i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.WHITE, this.x, this.y));
                }
            }
        }
        this.isDone = true;
    }

    public void render(final SpriteBatch sb) {
    }

    public void dispose() {
    }
}
