package duelistmod.orbs.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.actions.enemyDuelist.EnemyDarkOrbEvokeAction;

public class EnemyDark extends DuelistOrb
{
    public static final String[] DESC;
    private static final OrbStrings orbString;
    private float vfxTimer;

    public EnemyDark() {
        this.vfxTimer = 0.5f;
        this.ID = "Dark";
        this.img = ImageMaster.ORB_DARK;
        this.name = EnemyDark.orbString.NAME;
        this.baseEvokeAmount = 6;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 6;
        this.passiveAmount = this.basePassiveAmount;
        originalEvoke = this.baseEvokeAmount;
        originalPassive = this.basePassiveAmount;
        this.updateDescription();
        this.channelAnimTimer = 0.5f;
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = EnemyDark.DESC[0] + this.passiveAmount + EnemyDark.DESC[1] + this.evokeAmount + EnemyDark.DESC[2];
    }

    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new EnemyDarkOrbEvokeAction(new DamageInfo(AbstractEnemyDuelist.enemyDuelist, this.evokeAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }

    public void onEndOfTurn() {
        float speedTime = 0.6f / AbstractEnemyDuelist.enemyDuelist.orbs.size();
        if (Settings.FAST_MODE) {
            speedTime = 0.0f;
        }
        AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), speedTime));
        this.evokeAmount += this.passiveAmount;
        this.updateDescription();
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1f);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 120.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = 0.25f;
        }
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        this.shineColor.a = this.c.a / 3.0f;
        sb.setColor(this.shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * 1.2f, this.scale * 1.2f, this.angle / 1.2f, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * 1.5f, this.scale * 1.5f, this.angle / 1.4f, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    protected void renderText(final SpriteBatch sb) {

            Color niceCalmBlue = new Color(0.2f, 1.0f, 1.0f, this.c.a);
            if (this.showEvokeValue) {
                niceCalmBlue = Color.RED.cpy();
            }
            if (this.showEvokeValue) {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + EnemyDark.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + EnemyDark.NUM_Y_OFFSET, niceCalmBlue, this.fontScale);
            }
            else {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + EnemyDark.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + EnemyDark.NUM_Y_OFFSET - 4.0f * Settings.scale, niceCalmBlue, this.fontScale);
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + EnemyDark.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + EnemyDark.NUM_Y_OFFSET + 20.0f * Settings.scale, this.c, this.fontScale);
            }

    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1f);
    }

    public AbstractOrb makeCopy() {
        return new EnemyDark();
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
        orbString = CardCrawlGame.languagePack.getOrbString("Dark");
        DESC = EnemyDark.orbString.DESCRIPTION;
    }
}
