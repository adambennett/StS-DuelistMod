package duelistmod.ui.configMenu;

import basemod.IUIElement;
import basemod.patches.com.megacrit.cardcrawl.helpers.TipHelper.HeaderlessTip;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import duelistmod.abstracts.DuelistPotion;

public class ModHoverImage implements IUIElement {

    private final Texture texture;
    private final float x;
    private final float y;
    private float w;
    private float h;
    private Hitbox hb;
    public String tooltip;
    private final DuelistPotion potion;

    public ModHoverImage(float x, float y, DuelistPotion potion, String tooltip) {
        this(x, y, null, potion, tooltip);
    }

    public ModHoverImage(float x, float y, Texture tex, String tooltip) {
        this(x, y, tex, null, tooltip);
    }

    public ModHoverImage(float x, float y, Texture tex, DuelistPotion potion, String tooltip) {
        this.texture = tex != null ? tex : potion != null ? potion.getContainerImage() : null;
        this.x = x * Settings.scale;
        this.y = y * Settings.scale;
        if (this.texture != null) {
            this.w = (float)this.texture.getWidth() * Settings.scale;
            this.h = (float)this.texture.getHeight() * Settings.scale;
            this.hb = new Hitbox(this.w, this.h);
            this.hb.move(this.x + (20 * Settings.scale), this.y + (20 * Settings.scale));
            this.tooltip = tooltip;
        }
        this.potion = potion;
    }

    public void render(SpriteBatch sb) {
        this.hb.render(sb);
        if (this.potion != null) {
            this.potion.posX = this.x;
            this.potion.posY = this.y;
            this.potion.labRender(sb);
        } else if (this.texture != null) {
            sb.setColor(Color.WHITE);
            sb.draw(this.texture, this.x, this.y, this.w, this.h);
        }

        if (this.hb.hovered && this.tooltip != null && (this.potion != null || this.texture != null)) {
            HeaderlessTip.renderHeaderlessTip((float) InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, this.tooltip);
        }
    }

    public void update() {
        this.hb.update();
    }

    public int renderLayer() {
        return 0;
    }

    public int updateOrder() {
        return 1;
    }
}
