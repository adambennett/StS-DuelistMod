package duelistmod.ui.configMenu;

import basemod.ModLabeledButton;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.helpers.TipHelper.HeaderlessTip;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import duelistmod.DuelistMod;

import java.util.function.Consumer;

public class DuelistLabeledButton extends ModLabeledButton {

    private final String tooltip;
    private final Texture textureLeftOuter;
    private final Texture textureRightOuter;
    private final Texture textureMiddleOuter;
    private final float x;
    private final float y;
    private final float middle_width;
    private final float h;

    public DuelistLabeledButton(String label, String tooltip, float xPos, float yPos, ModPanel p, Consumer<ModLabeledButton> c) {
        super(label, xPos, yPos, p, c);
        this.tooltip = tooltip;
        this.textureLeftOuter = ImageMaster.loadImage("img/ButtonLeft.png");
        this.textureRightOuter = ImageMaster.loadImage("img/ButtonRight.png");
        this.textureMiddleOuter = ImageMaster.loadImage("img/ButtonMiddle.png");
        this.middle_width = Math.max(0.0F, FontHelper.getSmartWidth(font, label, 9999.0F, 0.0F) - 18.0F * Settings.scale);
        this.x = xPos * Settings.scale;
        this.y = yPos * Settings.scale;
        this.h = (float)this.textureLeftOuter.getHeight() * Settings.scale;
    }

    public DuelistLabeledButton(String label, String tooltip, float xPos, float yPos, Color textColor, Color textColorHover, ModPanel p, Consumer<ModLabeledButton> c) {
        super(label, xPos, yPos, textColor, textColorHover, p, c);
        this.tooltip = tooltip;
        this.textureLeftOuter = ImageMaster.loadImage("img/ButtonLeft.png");
        this.textureRightOuter = ImageMaster.loadImage("img/ButtonRight.png");
        this.textureMiddleOuter = ImageMaster.loadImage("img/ButtonMiddle.png");
        this.middle_width = Math.max(0.0F, FontHelper.getSmartWidth(font, label, 9999.0F, 0.0F) - 18.0F * Settings.scale);
        this.x = xPos * Settings.scale;
        this.y = yPos * Settings.scale;
        this.h = (float)this.textureLeftOuter.getHeight() * Settings.scale;
    }

    @Override
    public void render(SpriteBatch sb) {
        Hitbox hb = ReflectionHacks.getPrivate(this, ModLabeledButton.class, "hb");
        sb.draw(this.textureLeftOuter, this.x, this.y, (float)this.textureLeftOuter.getWidth() * Settings.scale, this.h);
        sb.draw(this.textureMiddleOuter, this.x + (float)this.textureLeftOuter.getWidth() * Settings.scale, this.y, this.middle_width, this.h);
        sb.draw(this.textureRightOuter, this.x + (float)this.textureLeftOuter.getWidth() * Settings.scale + this.middle_width, this.y, (float)this.textureRightOuter.getWidth() * Settings.scale, this.h);
        hb.render(sb);
        sb.setColor(Color.WHITE);
        if (hb.hovered) {
            FontHelper.renderFontCentered(sb, this.font, this.label, hb.cX, hb.cY, this.colorHover);
            if (this.tooltip != null && DuelistMod.openDropdown == null) {
                HeaderlessTip.renderHeaderlessTip((float) InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, this.tooltip);
            }
        } else {
            FontHelper.renderFontCentered(sb, this.font, this.label, hb.cX, hb.cY, this.color);
        }
    }
}
