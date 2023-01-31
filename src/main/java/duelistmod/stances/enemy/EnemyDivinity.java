package duelistmod.stances.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyStance;
import duelistmod.actions.enemyDuelist.EnemyGainEnergyAction;
import duelistmod.vfx.enemy.EnemyDivinityParticleEffect;
import duelistmod.vfx.enemy.EnemyStanceAuraEffect;

public class EnemyDivinity extends AbstractEnemyStance
{
    public static final String STANCE_ID = "Divinity";
    private static final StanceStrings stanceString;
    private static long sfxId;

    public EnemyDivinity() {
        this.ID = "Divinity";
        this.name = EnemyDivinity.stanceString.NAME;
        this.updateDescription();
    }

    @Override
    public void updateAnimation() {
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            if (!Settings.DISABLE_EFFECTS) {
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0f) {
                    this.particleTimer = 0.2f;
                    AbstractDungeon.effectsQueue.add(new EnemyDivinityParticleEffect());
                }
            }
            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer2 < 0.0f) {
                this.particleTimer2 = MathUtils.random(0.45f, 0.55f);
                AbstractDungeon.effectsQueue.add(new EnemyStanceAuraEffect("Divinity"));
            }
        }
    }

    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        return (type == DamageInfo.DamageType.NORMAL) ? (damage * 3.0f) : damage;
    }

    @Override
    public void updateDescription() {
        this.description = EnemyDivinity.stanceString.DESCRIPTION[0];
    }

    @Override
    public void onEnterStance() {
        if (EnemyDivinity.sfxId != -1L) {
            this.stopIdleSfx();
        }
        CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
        EnemyDivinity.sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_DIVINITY");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PINK, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractEnemyDuelist.enemyDuelist.hb.cX, AbstractEnemyDuelist.enemyDuelist.hb.cY, "Divinity"));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new EnemyGainEnergyAction(3));
    }

    @Override
    public void onExitStance() {
        this.stopIdleSfx();
    }

    @Override
    public void stopIdleSfx() {
        if (EnemyDivinity.sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_DIVINITY", EnemyDivinity.sfxId);
            EnemyDivinity.sfxId = -1L;
        }
    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString("Divinity");
        EnemyDivinity.sfxId = -1L;
    }
}
