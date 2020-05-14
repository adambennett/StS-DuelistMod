package duelistmod.speedster.speedsterUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

import duelistmod.DuelistMod;
import duelistmod.ui.TextureLoader;

public class ExhaustionPanel extends AbstractPanel {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(DuelistMod.makeID("ExhaustionPanel"));
    private static final Color ENERGY_TEXT_COLOR = new Color(1.0F, 1.0F, 0.86F, 1.0F);
    ;
    public static float fontScale = 1.0f;
    public static final float FONT_POP_SCALE = 2.0F;
    private Hitbox tipHitbox;
    private float energyVfxAngle;
    private float energyVfxScale;
    private Color energyVfxColor;
    public static float energyVfxTimer = 0.0f;
    public static final float ENERGY_VFX_TIME = 3.0F;
    public static final float ENERGY_VFX_HALF_TIME = ENERGY_VFX_TIME/2.0f;
    private static final float VFX_ROTATE_SPEED = -30.0F;

    private static final float COOLDOWN_AMT = 1F;
    private float cooldown = COOLDOWN_AMT;

    public ExhaustionPanel() {
        super(96.0F * Settings.scale, 320.0F * Settings.scale, Settings.WIDTH + 480.0F * Settings.scale, Settings.HEIGHT / 2.0f, 12.0F * Settings.scale, -12.0F * Settings.scale, TextureLoader.getTexture(DuelistMod.makeSpeedsterPath("orb/vfx.png")), false);
        tipHitbox = new Hitbox(0, 0, 120.0F * Settings.scale, 120.0F * Settings.scale);
        energyVfxAngle = 0.0F;
        energyVfxScale = Settings.scale;
        energyVfxColor = Color.WHITE.cpy();
    }

    public void update() {
        this.updateVfx();
        if (fontScale != 1.0F) {
            fontScale = MathHelper.scaleLerpSnap(fontScale, FONT_POP_SCALE);
        }

        tipHitbox.update();
        if (tipHitbox.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
    }

    private void updateVfx() {
        if (!isHidden) { //shouldRedner check here
            cooldown -= Gdx.graphics.getRawDeltaTime();
            if (cooldown < 0.0f) {
                cooldown = (10f / (1 + 1)); //Change first 1 to whatever
                energyVfxTimer = ENERGY_VFX_TIME;
            }
            if (energyVfxTimer != 0.0F) {
                energyVfxColor.a = Interpolation.exp10In.apply(0.5F, 0.0F, 1.0F - energyVfxTimer / ENERGY_VFX_TIME);
                energyVfxAngle += Gdx.graphics.getRawDeltaTime() * VFX_ROTATE_SPEED;
                if(energyVfxTimer > ENERGY_VFX_HALF_TIME) {
                    energyVfxScale = Settings.scale * Interpolation.exp10In.apply(1.0F, 0.1F, 1.0F - (1.0f - (energyVfxTimer - ENERGY_VFX_HALF_TIME) / ENERGY_VFX_HALF_TIME));
                } else {
                    energyVfxScale = Settings.scale * Interpolation.exp10In.apply(1.0F, 0.1F, 1.0F - energyVfxTimer / ENERGY_VFX_HALF_TIME);
                }
                energyVfxTimer -= Gdx.graphics.getRawDeltaTime();
                if (energyVfxTimer < 0.0F) {
                    energyVfxTimer = 0.0F;
                    energyVfxColor.a = 0.0F;
                }
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (!isHidden) { //shouldRedner check here
            tipHitbox.move(current_x, current_y);
            renderVfx(sb);
            String text = Integer.toString(1); //Change this as well
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, text, current_x, current_y, ENERGY_TEXT_COLOR);
            tipHitbox.render(sb);
            if (tipHitbox.hovered && !AbstractDungeon.isScreenUp) {
                TipHelper.renderGenericTip(current_x + ((img.getWidth() / 2.0f) * Settings.scale), current_y + ((img.getHeight() / 2.0f) * Settings.scale), uiStrings.TEXT[0], uiStrings.TEXT[1]);
            }
        }
    }

    private void renderVfx(SpriteBatch sb) {
        if (energyVfxTimer != 0.0F) {
            sb.setBlendFunction(770, 1);
            sb.setColor(this.energyVfxColor);
            sb.draw(img, this.current_x - 128.0F, this.current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.energyVfxScale, this.energyVfxScale, -this.energyVfxAngle + 50.0F, 0, 0, 256, 256, true, false);
            sb.draw(img, this.current_x - 128.0F, this.current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.energyVfxScale, this.energyVfxScale, this.energyVfxAngle, 0, 0, 256, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
    }
}