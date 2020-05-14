package duelistmod.speedster.mechanics;

import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.speedster.SpeedsterUtil.UC;

public abstract class AbstractSpeedTime {
    public static final float BLACKSCREEN_INTENSITY = 0.5f;
    protected Color blackScreenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    protected float blackScreenTarget;
    protected float startingDuration, duration;
    protected Consumer<AbstractMonster> effectAction;

    private static Color oscillatingColor = Color.GOLDENROD.cpy();
    private static float oscillatingTimer = 0.0f;
    private static float oscillatingFader = 0.0f;

    public boolean paused;
    public boolean isDone;

    public enum Location{
        COMPLETE, BEHIND_MONSTER
    }
    protected Location renderLocation;

    public AbstractSpeedTime(Location renderLocation, float duration, float blackscreenIntensity) {
        this.renderLocation = renderLocation;
        this.startingDuration = this.duration = duration;
        blackScreenTarget = blackscreenIntensity;
        isDone = false;
        hideElements();
    }

    public AbstractSpeedTime(Location renderLocation, float duration) {
        this(renderLocation, duration, BLACKSCREEN_INTENSITY);
    }

    public void renderController(SpriteBatch sb, Location location) {
        //Decide render method
        if(location == renderLocation) {
            renderBlackscreen(sb);
            renderEffects(sb);
            render(sb);
            renderTimer(sb);
        }
    }

    protected void renderBlackscreen(SpriteBatch sb) {
        if (!paused && blackScreenColor.a != 0.0F) {
            sb.setColor(this.blackScreenColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    protected void renderEffects(SpriteBatch sb) { }
    protected void renderTimer(SpriteBatch sb) {
        oscillatingFader += Gdx.graphics.getDeltaTime();
        if (oscillatingFader > 1.0F) {
            oscillatingFader = 1.0F;
            oscillatingTimer += Gdx.graphics.getDeltaTime() * 5.0F;
        }
        oscillatingColor.a = (0.33F + (MathUtils.cos(oscillatingTimer) + 1.0F) / 3.0F) * oscillatingFader;
        UC.displayTimer(sb, UC.get2DecString(duration), Settings.HEIGHT - (180.0f * Settings.scale), oscillatingColor);
    }

    public void render(SpriteBatch sb) {}

    public void update() {
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            closeInstantly();
            return;
        }

        duration -= UC.gt();	
        if(duration < 0) {
            close();
            if(blackScreenColor.a == 0.0f) {
                isDone = true;
            }
        } else {
            generalEffect();
            AbstractDungeon.player.hand.group.forEach(c -> c.target_y = -AbstractCard.IMG_HEIGHT);
        }
        updateBlackScreen();
    }

    protected void generalEffect() {}
    public void triggerEffect() {}

    protected void updateBlackScreen() {
        if (this.blackScreenColor.a != this.blackScreenTarget)
            if (this.blackScreenTarget > this.blackScreenColor.a) {
                this.blackScreenColor.a += Gdx.graphics.getRawDeltaTime() * 2.0F;
                if (this.blackScreenColor.a > this.blackScreenTarget)
                    this.blackScreenColor.a = this.blackScreenTarget;
            } else {
                this.blackScreenColor.a -= Gdx.graphics.getRawDeltaTime() * 2.0F;
                if (this.blackScreenColor.a < this.blackScreenTarget)
                    this.blackScreenColor.a = this.blackScreenTarget;
            }
    }

    public void close() {
        AbstractDungeon.overlayMenu.showCombatPanels();
        blackScreenTarget = 0.0f;
    }

    public void closeInstantly() {
        AbstractDungeon.overlayMenu.showCombatPanels();
        blackScreenColor.a = 0.0f;
        isDone = true;
    }


    public void unpause() { paused = false; }
    public void pause() { paused = true; }

    public void hideElements() {
        AbstractDungeon.overlayMenu.hideCombatPanels();
    }
}
