package duelistmod.abstracts.enemyDuelist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;
import duelistmod.DuelistMod;
import duelistmod.vfx.enemy.EnemyRefreshEnergyEffect;

public class EnemyEnergyPanel extends AbstractPanel {

    public static final String MSG;
    public static final String LABEL;
    private static final Color ENERGY_TEXT_COLOR;
    public static float fontScale;
    public static int totalCount;
    public static float energyVfxTimer;
    public BitmapFont energyNumFont;
    private final Hitbox tipHitbox;
    public Texture gainEnergyImg;
    private float energyVfxAngle;
    private float energyVfxScale;
    private final Color energyVfxColor;
    private final AbstractEnemyDuelist owner;

    public EnemyEnergyPanel(final AbstractEnemyDuelist owner) {
        super(Settings.WIDTH - 198.0f * Settings.scale, Settings.HEIGHT - 190.0f * Settings.scale, -480.0f * Settings.scale, 200.0f * Settings.scale, 12.0f * Settings.scale, -12.0f * Settings.scale, (Texture)null, false);
        this.tipHitbox = new Hitbox(0.0f, 0.0f, 120.0f * Settings.scale, 120.0f * Settings.scale);
        this.energyVfxAngle = 0.0f;
        this.energyVfxScale = Settings.scale;
        this.energyVfxColor = Color.WHITE.cpy();
        this.owner = owner;
        if (DuelistMod.monsterIsKaiba) {
            this.gainEnergyImg = ImageMaster.BLUE_ORB_FLASH_VFX;
            this.energyNumFont = FontHelper.energyNumFontBlue;
        } else {
            this.gainEnergyImg = ImageMaster.PURPLE_ORB_FLASH_VFX;
            this.energyNumFont = FontHelper.energyNumFontPurple;
        }
    }

    public static void setEnergy(final int energy) {
        EnemyEnergyPanel.totalCount = energy;
        AbstractDungeon.effectsQueue.add(new RefreshEnergyEffect());
        EnemyEnergyPanel.energyVfxTimer = 2.0f;
        EnemyEnergyPanel.fontScale = 2.0f;
    }

    public static void addEnergy(final int e) {
        EnemyEnergyPanel.totalCount += e;
        if (EnemyEnergyPanel.totalCount >= 9) {
            UnlockTracker.unlockAchievement("ADRENALINE");
        }
        if (EnemyEnergyPanel.totalCount > 999) {
            EnemyEnergyPanel.totalCount = 999;
        }
        AbstractDungeon.effectsQueue.add(new EnemyRefreshEnergyEffect());
        EnemyEnergyPanel.fontScale = 2.0f;
        EnemyEnergyPanel.energyVfxTimer = 2.0f;
    }

    public static void useEnergy(final int e) {
        EnemyEnergyPanel.totalCount -= e;
        if (EnemyEnergyPanel.totalCount < 0) {
            EnemyEnergyPanel.totalCount = 0;
        }
        if (e != 0) {
            EnemyEnergyPanel.fontScale = 2.0f;
        }
    }

    public int getCurrentEnergy() {
        if (AbstractDungeon.player == null) {
            return 0;
        }
        return EnemyEnergyPanel.totalCount;
    }

    public void update() {
        this.owner.energyOrb.updateOrb(EnemyEnergyPanel.totalCount);
        this.updateVfx();
        if (EnemyEnergyPanel.fontScale != 1.0f) {
            EnemyEnergyPanel.fontScale = MathHelper.scaleLerpSnap(EnemyEnergyPanel.fontScale, 1.0f);
        }
        this.tipHitbox.update();
        if (this.tipHitbox.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
        if (Settings.isDebug) {
            if (InputHelper.scrolledDown) {
                addEnergy(1);
            }
            else if (InputHelper.scrolledUp && EnemyEnergyPanel.totalCount > 0) {
                useEnergy(1);
            }
        }
    }

    private void updateVfx() {
        if (EnemyEnergyPanel.energyVfxTimer != 0.0f) {
            this.energyVfxColor.a = Interpolation.exp10In.apply(0.5f, 0.0f, 1.0f - EnemyEnergyPanel.energyVfxTimer / 2.0f);
            this.energyVfxAngle += Gdx.graphics.getDeltaTime() * -30.0f;
            this.energyVfxScale = Settings.scale * Interpolation.exp10In.apply(1.0f, 0.1f, 1.0f - EnemyEnergyPanel.energyVfxTimer / 2.0f);
            EnemyEnergyPanel.energyVfxTimer -= Gdx.graphics.getDeltaTime();
            if (EnemyEnergyPanel.energyVfxTimer < 0.0f) {
                EnemyEnergyPanel.energyVfxTimer = 0.0f;
                this.energyVfxColor.a = 0.0f;
            }
        }
    }

    public void render(final SpriteBatch sb) {
        this.tipHitbox.move(this.current_x, this.current_y);
        this.renderOrb(sb);
        this.renderVfx(sb);
        final String energyMsg = EnemyEnergyPanel.totalCount + "/" + this.owner.energy.energy;
        AbstractDungeon.player.getEnergyNumFont().getData().setScale(EnemyEnergyPanel.fontScale);
        FontHelper.renderFontCentered(sb, this.energyNumFont, energyMsg, this.current_x, this.current_y, EnemyEnergyPanel.ENERGY_TEXT_COLOR);
        this.tipHitbox.render(sb);
        if (this.tipHitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(1550.0f * Settings.scale, 750.0f * Settings.scale, EnemyEnergyPanel.LABEL, EnemyEnergyPanel.MSG);
        }
    }

    private void renderOrb(final SpriteBatch sb) {
        this.owner.energyOrb.renderOrb(sb, EnemyEnergyPanel.totalCount != 0, this.current_x, this.current_y);
    }

    private void renderVfx(final SpriteBatch sb) {
        if (EnemyEnergyPanel.energyVfxTimer != 0.0f) {
            sb.setBlendFunction(770, 1);
            sb.setColor(this.energyVfxColor);
            sb.draw(this.gainEnergyImg, this.current_x - 128.0f, this.current_y - 128.0f, 128.0f, 128.0f, 256.0f, 256.0f, this.energyVfxScale, this.energyVfxScale, -this.energyVfxAngle + 50.0f, 0, 0, 256, 256, true, false);
            sb.draw(this.gainEnergyImg, this.current_x - 128.0f, this.current_y - 128.0f, 128.0f, 128.0f, 256.0f, 256.0f, this.energyVfxScale, this.energyVfxScale, this.energyVfxAngle, 0, 0, 256, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    static {
        MSG = "Enemy's current energy count. Cards require energy to play.";
        LABEL = "Energy";
        ENERGY_TEXT_COLOR = new Color(1.0f, 1.0f, 0.86f, 1.0f);
        EnemyEnergyPanel.fontScale = 1.0f;
        EnemyEnergyPanel.totalCount = 0;
        EnemyEnergyPanel.energyVfxTimer = 0.0f;
    }
}
