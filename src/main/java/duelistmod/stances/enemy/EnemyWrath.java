package duelistmod.stances.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyStance;
import duelistmod.vfx.enemy.EnemyStanceAuraEffect;
import duelistmod.vfx.enemy.EnemyStanceChangeParticleGenerator;
import duelistmod.vfx.enemy.EnemyWrathParticleEffect;

public class EnemyWrath extends AbstractEnemyStance
{
    public static final String STANCE_ID = "Wrath";
    private static final StanceStrings stanceString;
    private static long sfxId;

    public EnemyWrath() {
        this.ID = "Wrath";
        this.name = EnemyWrath.stanceString.NAME;
        this.updateDescription();
    }

    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        return (type == DamageInfo.DamageType.NORMAL) ? (damage * 1.5f) : damage;
    }

    @Override
    public void updateAnimation() {
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            if (!Settings.DISABLE_EFFECTS) {
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0f) {
                    this.particleTimer = 0.05f;
                    AbstractDungeon.effectsQueue.add(new EnemyWrathParticleEffect());
                }
            }
            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer2 < 0.0f) {
                this.particleTimer2 = MathUtils.random(0.3f, 0.4f);
                AbstractDungeon.effectsQueue.add(new EnemyStanceAuraEffect("Wrath"));
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = EnemyWrath.stanceString.DESCRIPTION[0];
    }

    @Override
    public void onEnterStance() {
        if (EnemyWrath.sfxId != -1L) {
            this.stopIdleSfx();
        }
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        EnemyWrath.sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new EnemyStanceChangeParticleGenerator(AbstractEnemyDuelist.enemyDuelist.hb.cX, AbstractEnemyDuelist.enemyDuelist.hb.cY, "Wrath"));
    }

    @Override
    public void onExitStance() {
        this.stopIdleSfx();
    }

    @Override
    public void stopIdleSfx() {
        if (EnemyWrath.sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", EnemyWrath.sfxId);
            EnemyWrath.sfxId = -1L;
        }
    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString("Wrath");
        EnemyWrath.sfxId = -1L;
    }
}
