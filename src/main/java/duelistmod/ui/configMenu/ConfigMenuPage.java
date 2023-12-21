package duelistmod.ui.configMenu;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.List;

public class ConfigMenuPage implements IUIElement
{
    public float x;
    public float y;
    public float w;
    public float h;
    public List<IUIElement> elements;
    public String name;

    public ConfigMenuPage(String name, final float x, final float y, final float width, final float height, final List<IUIElement> elements) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.elements = elements;
        this.name = name;
    }

    public void render(final SpriteBatch sb) {
        for (IUIElement element : this.elements) {
            element.render(sb);
        }
    }

    public void update() {
        for (IUIElement element : this.elements) {
            element.update();
        }
    }

    public int renderLayer() {
        return 1;
    }

    public int updateOrder() {
        return 1;
    }
}
