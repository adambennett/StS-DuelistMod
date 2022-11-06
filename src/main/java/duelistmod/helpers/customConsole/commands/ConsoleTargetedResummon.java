package duelistmod.helpers.customConsole.commands;

import basemod.BaseMod;
import basemod.DevConsole;
import basemod.interfaces.PostUpdateSubscriber;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.abstracts.DuelistCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.badlogic.gdx.graphics.Color;

public class ConsoleTargetedResummon implements RenderSubscriber, PostUpdateSubscriber {

    public static final Logger logger = LogManager.getLogger(DevConsole.class.getName());

    private final AbstractCard cardToResummon;

    public ConsoleTargetedResummon(AbstractCard card, int amount)
    {
        this.cardToResummon = card;
        this.amount = amount;
        BaseMod.subscribe(this);
        this.isHidden = false;
        com.megacrit.cardcrawl.core.GameCursor.hidden = true;
        for (int i = 0; i < this.points.length; i++) {
            this.points[i] = new Vector2();
        }
    }

    private AbstractCreature hoveredCreature;
    private Vector2 controlPoint;
    private float arrowScaleTimer;
    private final Vector2[] points = new Vector2[20];
    private boolean isHidden;
    private final int amount;

    private void close()
    {
        this.isHidden = true;
    }

    private void updateTargetMode()
    {
        if ((InputHelper.justClickedRight) || (AbstractDungeon.isScreenUp) || (InputHelper.mY > Settings.HEIGHT - 80.0F * Settings.scale) || (AbstractDungeon.player.hoveredCard != null) || (InputHelper.mY < 140.0F * Settings.scale)) {
            com.megacrit.cardcrawl.core.GameCursor.hidden = false;
            close();
        }

        this.hoveredCreature = null;
        for (AbstractCreature m : AbstractDungeon.getMonsters().monsters) {
            if ((m.hb.hovered) && (!m.isDying)) {
                this.hoveredCreature = m;
                break;
            }
        }

        if (InputHelper.justClickedLeft) {
            InputHelper.justClickedLeft = false;
            AbstractMonster mon = null;
            if (this.hoveredCreature != null && this.hoveredCreature != AbstractDungeon.player) {
                mon = (AbstractMonster) this.hoveredCreature;
            }
            DevConsole.visible = false;
            DuelistCard.resummon(this.cardToResummon, mon, amount);
            com.megacrit.cardcrawl.core.GameCursor.hidden = false;
            close();
        }
    }

    @Override
    public void receiveRender(SpriteBatch sb)
    {
        render(sb);

    }

    public void render(SpriteBatch sb)
    {
        if (!this.isHidden) {
            renderTargetingUi(sb);
            if (this.hoveredCreature != null && this.hoveredCreature != AbstractDungeon.player) {
                this.hoveredCreature.renderReticle(sb);
            }
        }
    }

    public void renderTargetingUi(SpriteBatch sb)
    {
        float x = InputHelper.mX;
        float y = InputHelper.mY;
        this.controlPoint = new Vector2(AbstractDungeon.player.animX - (x - AbstractDungeon.player.animX) / 4.0F, AbstractDungeon.player.animY + (y - AbstractDungeon.player.animY - 40.0F * Settings.scale) / 2.0F);
        float arrowScale;
        if (this.hoveredCreature == null || this.hoveredCreature == AbstractDungeon.player) {
            arrowScale = Settings.scale;
            this.arrowScaleTimer = 0.0F;
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
        } else {
            this.arrowScaleTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            if (this.arrowScaleTimer > 1.0F) {
                this.arrowScaleTimer = 1.0F;
            }

            arrowScale = com.badlogic.gdx.math.Interpolation.elasticOut.apply(Settings.scale, Settings.scale * 1.2F, this.arrowScaleTimer);
            sb.setColor(new Color(1.0F, 0.2F, 0.3F, 1.0F));
        }
        Vector2 tmp = new Vector2(this.controlPoint.x - x, this.controlPoint.y - y);
        tmp.nor();

        drawCurvedLine(sb, new Vector2(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY - 40.0F * Settings.scale), new Vector2(x, y), this.controlPoint);

        sb.draw(ImageMaster.TARGET_UI_ARROW, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, arrowScale, arrowScale, tmp.angle() + 90.0F, 0, 0, 256, 256, false, false);

    }

    private void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control)
    {
        float radius = 7.0F * Settings.scale;

        for (int i = 0; i < this.points.length - 1; i++) {
            this.points[i] = com.badlogic.gdx.math.Bezier.quadratic(this.points[i], i / 20.0F, start, control, end, new Vector2());
            radius += 0.4F * Settings.scale;

            float angle;

            if (i != 0) {
                Vector2 tmp = new Vector2(this.points[(i - 1)].x - this.points[i].x, this.points[(i - 1)].y - this.points[i].y);
                angle = tmp.nor().angle() + 90.0F;
            } else {
                Vector2 tmp = new Vector2(this.controlPoint.x - this.points[i].x, this.controlPoint.y - this.points[i].y);
                angle = tmp.nor().angle() + 270.0F;
            }

            sb.draw(ImageMaster.TARGET_UI_CIRCLE, this.points[i].x - 64.0F, this.points[i].y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, radius / 18.0F, radius / 18.0F, angle, 0, 0, 128, 128, false, false);
        }
    }

    @Override
    public void receivePostUpdate()
    {
        if (!this.isHidden) {
            updateTargetMode();
        }
    }

}
