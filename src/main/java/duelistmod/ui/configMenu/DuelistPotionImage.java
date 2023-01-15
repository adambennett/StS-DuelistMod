package duelistmod.ui.configMenu;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.DuelistPotion;

public class DuelistPotionImage implements IUIElement {

    private final float x;
    private final float y;
    private final DuelistPotion potion;

    public DuelistPotionImage(float x, float y, DuelistPotion potion) {
        this.x = x * Settings.scale;
        this.y = y * Settings.scale;
        this.potion = potion;
    }

    public void render(SpriteBatch sb) {
        if (this.potion != null) {
            this.potion.posX = this.x;
            this.potion.posY = this.y;
            this.potion.labRender(sb);
        }
    }

    public void update() {}

    public int renderLayer() {
        return 0;
    }

    public int updateOrder() {
        return 1;
    }
}
