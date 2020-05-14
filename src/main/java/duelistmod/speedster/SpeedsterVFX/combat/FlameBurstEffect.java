package duelistmod.speedster.SpeedsterVFX.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import duelistmod.speedster.SpeedsterVFX.general.RedFireBurstParticleEffect;

public class FlameBurstEffect extends AbstractGameEffect {
    private float x, y;
    private int amount;

    public FlameBurstEffect(float x, float y, int amt) {
        this.x = x;
        this.y = y;
        this.amount = amt;
    }

    @Override
    public void update() {
        for (int i = 0; i < amount; ++i) {
            AbstractDungeon.effectsQueue.add(new RedFireBurstParticleEffect(x, y));
        }
        this.isDone = true;
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }
}
