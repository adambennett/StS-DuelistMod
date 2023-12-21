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
import duelistmod.dto.DropdownSelection;
import duelistmod.helpers.Util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class DuelistDropdown extends DropdownMenu implements IUIElement {

    private final int xPos;
    private final int yPos;
    private final String tooltip;
    private final DropdownMenuListener onChange;
    private final Float widthModifier;
    private boolean wasOpen;
    private LinkedHashMap<String, String> optionsById;

    public DuelistDropdown(String tooltip, ArrayList<String> options, float xPos, float yPos, int maxRows, Float widthModifier, DropdownMenuListener listener) {
        super(DuelistMod.settingsPanel, options, FontHelper.tipBodyFont, Settings.CREAM_COLOR, maxRows);
        this.tooltip = tooltip;
        this.xPos = (int)xPos;
        this.yPos = (int)yPos;
        this.onChange = listener;
        this.widthModifier = widthModifier;
    }

    public DuelistDropdown(String tooltip, LinkedHashSet<String> options, float xPos, float yPos, int maxRows, Float widthModifier, DropdownMenuListener listener) {
        super(DuelistMod.settingsPanel, new ArrayList<>(options), FontHelper.tipBodyFont, Settings.CREAM_COLOR, maxRows);
        this.tooltip = tooltip;
        this.xPos = (int)xPos;
        this.yPos = (int)yPos;
        this.onChange = listener;
        this.widthModifier = widthModifier;
    }

    public DuelistDropdown(String tooltip, LinkedHashMap<String, String> options, float xPos, float yPos, int maxRows, Float widthModifier, DropdownMenuListener listener) {
        super(DuelistMod.settingsPanel, new ArrayList<>(options.values()), FontHelper.tipBodyFont, Settings.CREAM_COLOR, maxRows);
        this.optionsById = options;
        this.tooltip = tooltip;
        this.xPos = (int)xPos;
        this.yPos = (int)yPos;
        this.onChange = listener;
        this.widthModifier = widthModifier;
    }

    // Tooltip + Width modifier
    public DuelistDropdown(String tooltip, ArrayList<String> options, float xPos, float yPos, Float widthModifier, DropdownMenuListener listener) {
        this(tooltip, options, (int)xPos, (int)yPos, 15, widthModifier, listener);
    }

    // Tooltip
    public DuelistDropdown(String tooltip, ArrayList<String> options, float xPos, float yPos, DropdownMenuListener listener) {
        this(tooltip, options, (int)xPos, (int)yPos, 15, null, listener);
    }

    public DuelistDropdown(String tooltip, LinkedHashSet<String> options, float xPos, float yPos, DropdownMenuListener listener) {
        this(tooltip, options, (int)xPos, (int)yPos, 15, null, listener);
    }

    public DuelistDropdown(String tooltip, LinkedHashMap<String, String> options, float xPos, float yPos, DropdownMenuListener listener) {
        this(tooltip, options, (int)xPos, (int)yPos, 15, null, listener);
    }

    // Tooltip + max rows
    public DuelistDropdown(String tooltip, ArrayList<String> options, float xPos, float yPos, int maxRows, DropdownMenuListener listener) {
        this(tooltip, options, (int)xPos, (int)yPos, maxRows, null, listener);
    }

    // Width modifier
    public DuelistDropdown(ArrayList<String> options, float xPos, float yPos, Float widthModifier, DropdownMenuListener listener) {
        this(null, options, (int)xPos, (int)yPos, 15, widthModifier, listener);
    }

    public DuelistDropdown(ArrayList<String> options, float xPos, float yPos, DropdownMenuListener listener, int startingIndex) {
        this(null, options, (int)xPos, (int)yPos, 15, null, listener);
        this.setSelectedIndex(startingIndex);
    }

    // No extra settings
    public DuelistDropdown(ArrayList<String> options, float xPos, float yPos, DropdownMenuListener listener) {
        this(null, options, (int)xPos, (int)yPos, 15, null, listener);
    }

    public void change(String selectedText, int index) {
        if (this.hasOnChange()) {
            Util.log("Modifying dropdown and choosing: " + selectedText + " from index " + index);
            this.onChange.change(selectedText, index);
        }
    }

    public boolean hasOnChange() {
        return this.onChange != null;
    }

    @Override
    public void update() {
        if (!this.isOpen && DuelistMod.openDropdown != null) {
            return;
        }
        super.update();
        if (this.isOpen) {
            this.wasOpen = true;
            DuelistMod.openDropdown = this;
        } else if (this.wasOpen) {
            this.wasOpen = false;
            this.close();
        }
    }

    public void close() {
        if (DuelistMod.openDropdown == this) {
            DuelistMod.openDropdown = null;
            this.isOpen = false;
        }
    }

    public void setSelected(String text) {
        DropdownSelection selection = getSelectionBox();
        if (text != null && !selection.text().equals(text)) {
            try {
                Class<?> innerClazz = Class.forName("com.megacrit.cardcrawl.screens.options.DropdownMenu$DropdownRow");
                Field hbField2 = innerClazz.getDeclaredField("text");
                hbField2.setAccessible(true);
                int index = -1;
                if (this.optionsById != null) {
                    text = this.optionsById.getOrDefault(text, text);
                }
                for (int i = 0; i < this.rows.size(); i++) {
                    String rowText = (String) hbField2.get(this.rows.get(i));
                    Util.log("Checking if dropdown row value (" + rowText + ") matches check text (" + text + ")");
                    if (rowText.equals(text)) {
                        index = i;
                        break;
                    }
                }
                if (index > -1) {
                    this.setSelectedIndex(index);
                } else {
                    Util.log("Could not find index of option: " + text);
                }
            } catch (Exception ex) {
                Util.logError("Error setting selected dropdown by text value", ex);
            }
        }
    }

    @Override
    public float approximateOverallWidth() {
        float sup = super.approximateOverallWidth();
        return this.widthModifier != null ? sup + this.widthModifier : sup;
    }

    public DropdownSelection getSelectionBox() {
        try {
            Class<?> innerClazz = Class.forName("com.megacrit.cardcrawl.screens.options.DropdownMenu$DropdownRow");
            Class<?> thisClass = Class.forName("com.megacrit.cardcrawl.screens.options.DropdownMenu");
            Field sbField = thisClass.getDeclaredField("selectionBox");
            Field hbField = innerClazz.getDeclaredField("hb");
            Field hbField2 = innerClazz.getDeclaredField("text");
            Field hbField3 = innerClazz.getDeclaredField("index");
            hbField.setAccessible(true);
            hbField2.setAccessible(true);
            hbField3.setAccessible(true);
            sbField.setAccessible(true);
            Hitbox hb = (Hitbox) hbField.get(sbField.get(this));
            String text = (String) hbField2.get(sbField.get(this));
            int index = (int) hbField3.get(sbField.get(this));
            return new DropdownSelection(text, index, hb);
        } catch (Exception ex) {
            Util.logError("Exception while getting selection box from dropdown", ex);
        }
        return null;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        this.render(spriteBatch, this.xPos, this.yPos);
        if (!this.isOpen) {
            try {
                Class<?> innerClazz = Class.forName("com.megacrit.cardcrawl.screens.options.DropdownMenu$DropdownRow");
                Class<?> thisClass = Class.forName("com.megacrit.cardcrawl.screens.options.DropdownMenu");
                Field sbField = thisClass.getDeclaredField("selectionBox");
                Field hbField = innerClazz.getDeclaredField("hb");
                hbField.setAccessible(true);
                sbField.setAccessible(true);
                Hitbox hb = (Hitbox) hbField.get(sbField.get(this));
                if (hb.hovered && this.tooltip != null && !this.tooltip.equals("") && DuelistMod.openDropdown == null) {
                    HeaderlessTip.renderHeaderlessTip((float) InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, this.tooltip);
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
