package duelistmod.ui.configMenu;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class Pager implements IUIElement
{
    private Texture image;
    private int x;
    private int y;
    private int w;
    private int h;
    private Hitbox hitbox;
    private DuelistPaginator paginator;
    private boolean isNext;

    public Pager(final String url, final int x, final int y, final int width, final int height, boolean isNext, DuelistPaginator paginator) {
        this.image = new Texture(url);
        this.x = (int)(Settings.scale * x);
        this.y = (int)(Settings.scale * y);
        this.w = (int)(Settings.scale * width);
        this.h = (int)(Settings.scale * height);
        this.hitbox = new Hitbox((float)this.x, (float)this.y, (float)this.w, (float)this.h);
        this.isNext = isNext;
        this.paginator = paginator;
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.image, (float)this.x, (float)this.y, (float)this.w, (float)this.h);
        this.hitbox.render(sb);
    }

    public void update() {
        this.hitbox.update();
        if (this.hitbox.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.play("UI_CLICK_1");
            if (this.isNext) {
                this.paginator.nextPage();
            } else {
                this.paginator.prevPage();
            }
        }
    }

    public int renderLayer() {
        return 1;
    }

    public int updateOrder() {
        return 1;
    }
}

