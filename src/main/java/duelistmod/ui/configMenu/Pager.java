package duelistmod.ui.configMenu;

import basemod.IUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.util.function.Supplier;

public class Pager implements IUIElement {
    private final Texture image;
    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private final Hitbox hitbox;
    private final DuelistPaginator paginator;
    private final GeneralPager generalPaginator;
    private final Supplier<Boolean> arrowKeyPressed;
    private final Supplier<Boolean> arrowKeyPressedWithShift;
    private final boolean isNext;

    public Pager(final String url, final int x, final int y, final int width, final int height, boolean isNext, DuelistPaginator duelistPaginator, GeneralPager generalPaginator) {
        this.image = new Texture(url);
        this.x = (int)(Settings.scale * x);
        this.y = (int)(Settings.scale * y);
        this.w = (int)(Settings.scale * width);
        this.h = (int)(Settings.scale * height);
        this.hitbox = new Hitbox((float)this.x, (float)this.y, (float)this.w, (float)this.h);
        this.isNext = isNext;
        this.arrowKeyPressed = () -> (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) && Gdx.input.isKeyJustPressed(this.isNext ? Input.Keys.RIGHT : Input.Keys.LEFT);
        this.arrowKeyPressedWithShift = () -> (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) && Gdx.input.isKeyJustPressed(this.isNext ? Input.Keys.RIGHT : Input.Keys.LEFT);
        this.paginator = duelistPaginator;
        this.generalPaginator = generalPaginator;
    }

    public Pager(final String url, final int x, final int y, final int width, final int height, boolean isNext, DuelistPaginator paginator) {
        this(url, x, y, width, height, isNext, paginator, null);
    }

    public Pager(final String url, final int x, final int y, final int width, final int height, boolean isNext, GeneralPager paginator) {
        this(url, x, y, width, height, isNext, null, paginator);
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.image, (float)this.x, (float)this.y, (float)this.w, (float)this.h);
        this.hitbox.render(sb);
    }

    public void update() {
        this.hitbox.update();
        boolean arrowPressed = this.paginator != null && this.arrowKeyPressed.get();
        boolean arrowPressedWithShift = this.generalPaginator != null && this.arrowKeyPressedWithShift.get();
        boolean handleArrowKey = arrowPressed || arrowPressedWithShift;
        if (handleArrowKey || (this.hitbox.hovered && InputHelper.justClickedLeft)) {
            CardCrawlGame.sound.play("UI_CLICK_1");
            if (this.isNext) {
                this.nextPage();
            } else {
                this.prevPage();
            }
        }
    }

    private void nextPage() {
        if (this.paginator != null) {
            this.paginator.nextPage();
        } else if (this.generalPaginator != null) {
            this.generalPaginator.nextPage();
        }
    }

    private void prevPage() {
        if (this.paginator != null) {
            this.paginator.prevPage();
        } else if (this.generalPaginator != null) {
            this.generalPaginator.prevPage();
        }
    }

    public int renderLayer() {
        return 1;
    }

    public int updateOrder() {
        return 1;
    }
}

