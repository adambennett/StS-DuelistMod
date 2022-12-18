package duelistmod.ui.configMenu;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import duelistmod.DuelistMod;
import duelistmod.enums.DropdownMenuType;

import java.util.ArrayList;

public class DuelistDropdown extends DropdownMenu implements IUIElement {

    private final int xPos;
    private final int yPos;
    private final DropdownMenuListener onChange;

    public DuelistDropdown(ArrayList<String> options, int xPos, int yPos, DropdownMenuListener listener) {
        super(DuelistMod.settingsPanel, options, FontHelper.tipBodyFont, Settings.CREAM_COLOR);
        this.xPos = xPos;
        this.yPos = yPos;
        this.onChange = listener;
    }

    public DuelistDropdown(ArrayList<String> options, float xPos, float yPos, DropdownMenuListener listener) {
        this(options, (int)xPos, (int)yPos, listener);
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
