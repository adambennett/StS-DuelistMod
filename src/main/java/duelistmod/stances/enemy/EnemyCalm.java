package duelistmod.stances.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyStance;
import duelistmod.actions.enemyDuelist.EnemyGainEnergyAction;
import duelistmod.vfx.enemy.EnemyCalmParticleEffect;
import duelistmod.vfx.enemy.EnemyStanceAuraEffect;

public class EnemyCalm extends AbstractEnemyStance {

    public static final String STANCE_ID = "Calm";
    private static final StanceStrings stanceString;
    private static long sfxId;

    public EnemyCalm() {
        this.ID = "Calm";
        this.name = EnemyCalm.stanceString.NAME;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = EnemyCalm.stanceString.DESCRIPTION[0];
    }

    @Override
    public void updateAnimation() {
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            if (!Settings.DISABLE_EFFECTS) {
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0f) {
                    this.particleTimer = 0.04f;
                    AbstractDungeon.effectsQueue.add(new EnemyCalmParticleEffect());
                }
            }
            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer2 < 0.0f) {
                this.particleTimer2 = MathUtils.random(0.45f, 0.55f);
                AbstractDungeon.effectsQueue.add(new EnemyStanceAuraEffect("Calm"));
            }
        }
    }

    @Override
    public void onEnterStance() {
        if (EnemyCalm.sfxId != -1L) {
            this.stopIdleSfx();
        }
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        EnemyCalm.sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY, true));
    }

    @Override
    public void onExitStance() {
        AbstractDungeon.actionManager.addToBottom(new EnemyGainEnergyAction(2));
        this.stopIdleSfx();
    }

    @Override
    public void stopIdleSfx() {
        if (EnemyCalm.sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", EnemyCalm.sfxId);
            EnemyCalm.sfxId = -1L;
        }
    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString("Calm");
        EnemyCalm.sfxId = -1L;
    }
}
