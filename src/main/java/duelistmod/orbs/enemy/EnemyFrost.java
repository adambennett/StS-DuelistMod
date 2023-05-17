package duelistmod.orbs.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyFrost extends DuelistOrb
{
    private static final OrbStrings orbString;
    private final boolean hFlip1;
    private final boolean hFlip2;
    private float vfxTimer;
    private final float vfxIntervalMin;
    private final float vfxIntervalMax;

    public EnemyFrost() {
        this.hFlip1 = MathUtils.randomBoolean();
        this.hFlip2 = MathUtils.randomBoolean();
        this.vfxTimer = 1.0f;
        this.vfxIntervalMin = 0.15f;
        this.vfxIntervalMax = 0.8f;
        this.ID = "Frost";
        this.name = EnemyFrost.orbString.NAME;
        this.baseEvokeAmount = 5;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        originalEvoke = this.baseEvokeAmount;
        originalPassive = this.basePassiveAmount;
        this.updateDescription();
        this.channelAnimTimer = 0.5f;
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = EnemyFrost.orbString.DESCRIPTION[0] + this.passiveAmount + EnemyFrost.orbString.DESCRIPTION[1] + this.evokeAmount + EnemyFrost.orbString.DESCRIPTION[2];
    }

    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractEnemyDuelist.enemyDuelist,AbstractEnemyDuelist.enemyDuelist, this.evokeAmount));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            }
            this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
        }
    }

    public void onEndOfTurn() {
        float speedTime = 0.6f / AbstractEnemyDuelist.enemyDuelist.orbs.size();
        if (Settings.FAST_MODE) {
            speedTime = 0.0f;
        }
        AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), speedTime));
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist, this.passiveAmount, true));
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1f);
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(this.cX, this.cY));
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(ImageMaster.FROST_ORB_RIGHT, this.cX - 48.0f + this.bobEffect.y / 4.0f, this.cY - 48.0f + this.bobEffect.y / 4.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, this.hFlip1, false);
        sb.draw(ImageMaster.FROST_ORB_LEFT, this.cX - 48.0f + this.bobEffect.y / 4.0f, this.cY - 48.0f - this.bobEffect.y / 4.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, this.hFlip1, false);
        sb.draw(ImageMaster.FROST_ORB_MIDDLE, this.cX - 48.0f - this.bobEffect.y / 4.0f, this.cY - 48.0f + this.bobEffect.y / 2.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, this.hFlip2, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1f);
    }

    public AbstractOrb makeCopy() {
        return new EnemyFrost();
    }

    @Override
    public void checkFocus()
    {
        if (this.owner != null && this.owner.hasPower(FocusPower.POWER_ID)) {
            if ((this.owner.getPower(FocusPower.POWER_ID).amount > 0) || (this.owner.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0)) {
                this.basePassiveAmount = this.originalPassive + this.owner.getPower(FocusPower.POWER_ID).amount;
            } else {
                this.basePassiveAmount = 0;
            }
        } else {
            this.basePassiveAmount = this.originalPassive;
            this.baseEvokeAmount = this.originalEvoke;
        }
        applyFocus();
        updateDescription();
    }


    @Override
    public void applyFocus() {
        this.passiveAmount = this.basePassiveAmount;
        this.evokeAmount = this.baseEvokeAmount;
    }

    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Frost");
    }
}
