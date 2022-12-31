package duelistmod.ui.configMenu;

import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ModToggleButton;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.helpers.TipHelper.HeaderlessTip;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import duelistmod.DuelistMod;

import java.util.function.Consumer;

public class DuelistLabeledToggleButton extends ModLabeledToggleButton {

    public DuelistLabeledToggleButton(String labelText, float xPos, float yPos, Color color, BitmapFont font, boolean enabled, ModPanel p, Consumer<ModLabel> labelUpdate, Consumer<ModToggleButton> c) {
        super(labelText, xPos, yPos, color, font, enabled, p, labelUpdate, c);
    }

    public DuelistLabeledToggleButton(String labelText, String tooltipText, float xPos, float yPos, Color color, BitmapFont font, boolean enabled, ModPanel p, Consumer<ModLabel> labelUpdate, Consumer<ModToggleButton> c) {
        super(labelText, tooltipText, xPos, yPos, color, font, enabled, p, labelUpdate, c);
    }

    @Override
    public void render(SpriteBatch sb) {
        this.toggle.render(sb);
        this.text.render(sb);
        Hitbox hb = ReflectionHacks.getPrivate(this.toggle, ModToggleButton.class, "hb");
        if (this.tooltip != null && hb.hovered && DuelistMod.openDropdown == null) {
            HeaderlessTip.renderHeaderlessTip((float) InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, this.tooltip);
        }

    }
}
