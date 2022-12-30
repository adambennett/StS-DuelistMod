package duelistmod.ui.configMenu;

import basemod.IUIElement;
import basemod.patches.com.megacrit.cardcrawl.helpers.TipHelper.HeaderlessTip;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import duelistmod.DuelistMod;
import duelistmod.enums.DropdownMenuType;
import duelistmod.helpers.Util;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DuelistDropdown extends DropdownMenu implements IUIElement {

    private final int xPos;
    private final int yPos;
    private String tooltip;
    private final DropdownMenuListener onChange;

    public DuelistDropdown(String tooltip, ArrayList<String> options, int xPos, int yPos, DropdownMenuListener listener, int maxRows) {
        this(options, xPos, yPos, listener, maxRows);
        this.tooltip = tooltip;
    }

    public DuelistDropdown(ArrayList<String> options, int xPos, int yPos, DropdownMenuListener listener, int maxRows) {
        super(DuelistMod.settingsPanel, options, FontHelper.tipBodyFont, Settings.CREAM_COLOR, maxRows);
        this.xPos = xPos;
        this.yPos = yPos;
        this.onChange = listener;
    }

    public DuelistDropdown(String tooltip, ArrayList<String> options, float xPos, float yPos, DropdownMenuListener listener) {
        this(options, xPos, yPos, listener);
        this.tooltip = tooltip;
    }

    public DuelistDropdown(ArrayList<String> options, float xPos, float yPos, DropdownMenuListener listener) {
        this(options, (int)xPos, (int)yPos, listener, 15);
    }

    public DuelistDropdown(String tooltip, ArrayList<String> options, float xPos, float yPos, int maxRows, DropdownMenuListener listener) {
        this(options, xPos, yPos, maxRows, listener);
        this.tooltip = tooltip;
    }

    public DuelistDropdown(ArrayList<String> options, float xPos, float yPos, int maxRows, DropdownMenuListener listener) {
        this(options, (int)xPos, (int)yPos, listener, maxRows);
    }

    public void change(String selectedText, int index) {
        if (this.hasOnChange()) {
            this.onChange.change(selectedText, index);
        }
    }

    public boolean hasOnChange() {
        return this.onChange != null;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        this.render(spriteBatch, this.xPos, this.yPos);
        if (!this.isOpen && this.rows != null && this.rows.size() > 0) {
            try {
                Class<?> innerClazz = Class.forName("com.megacrit.cardcrawl.screens.options.DropdownMenu$DropdownRow");
                Field hbField = innerClazz.getDeclaredField("hb");
                hbField.setAccessible(true);
                Hitbox hb = (Hitbox) hbField.get(this.rows.get(0));
                if (hb.hovered && this.tooltip != null && !this.tooltip.equals("")) {
                    Util.log("Rendering dropdown tip?");
                    HeaderlessTip.renderHeaderlessTip((float) InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, this.tooltip);
                } else {
                    Util.log("Not rendering dropdown tip - data: { hb.hovered=" + hb.hovered + ", tooltip=" + this.tooltip + ", mX=" + InputHelper.mX + ", mY=" + InputHelper.mY + " }");
                }
            } catch (Exception ex) {
                Util.logError("Error while attempting to render tips on DuelistDropdown", ex);
            }
        }
    }

    @Override
    public int renderLayer() {
        return -5;
    }

    @Override
    public int updateOrder() {
        return 1;
    }
}
