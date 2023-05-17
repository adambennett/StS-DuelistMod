package duelistmod.orbs.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.actions.enemyDuelist.EnemyLightningOrbEvokeAction;
import duelistmod.actions.enemyDuelist.EnemyLightningOrbPassiveAction;

public class EnemyLightning extends DuelistOrb
{
    private static final OrbStrings orbString;
    private float vfxTimer;

    public EnemyLightning() {
        this.vfxTimer = 1.0f;
        this.ID = "Lightning";
        this.img = ImageMaster.ORB_LIGHTNING;
        this.name = EnemyLightning.orbString.NAME;
        this.baseEvokeAmount = 8;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 3;
        this.passiveAmount = this.basePassiveAmount;
        originalEvoke = this.baseEvokeAmount;
        originalPassive = this.basePassiveAmount;
        this.updateDescription();
        this.angle = MathUtils.random(360.0f);
        this.channelAnimTimer = 0.5f;
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = EnemyLightning.orbString.DESCRIPTION[0] + this.passiveAmount + EnemyLightning.orbString.DESCRIPTION[1] + this.evokeAmount + EnemyLightning.orbString.DESCRIPTION[2];
    }

    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new EnemyLightningOrbEvokeAction(new DamageInfo(AbstractEnemyDuelist.enemyDuelist, this.evokeAmount, DamageInfo.DamageType.THORNS), false));
    }

    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToTop(new EnemyLightningOrbPassiveAction(new DamageInfo(AbstractEnemyDuelist.enemyDuelist, this.passiveAmount, DamageInfo.DamageType.THORNS), this, false));
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1f);
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            }
            this.vfxTimer = MathUtils.random(0.15f, 0.8f);
        }
    }

    public void render(final SpriteBatch sb) {
        this.shineColor.a = this.c.a / 2.0f;
        sb.setColor(this.shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.05f + 0.19634955f, this.scale * 1.2f, this.angle, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * 1.2f, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.05f + 0.19634955f, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle / 12.0f, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1f);
    }

    public AbstractOrb makeCopy() {
        return new EnemyLightning();
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
        orbString = CardCrawlGame.languagePack.getOrbString("Lightning");
    }
}
