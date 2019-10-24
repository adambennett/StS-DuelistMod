package duelistmod.ui;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.MasterDeckSortHeader;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButton;

public class DuelistMasterDeckSortHeader extends MasterDeckSortHeader
{
    private static final Color BAR_COLOR;
    private static final Color IRONCLAD_COLOR;
    private static final Color SILENT_COLOR;
    private static final Color DEFECT_COLOR;
    private static final Comparator<AbstractCard> BY_TYPE;
    private static final Comparator<AbstractCard> ALPHA;
    private static final Comparator<AbstractCard> BY_COST;
    private static final Comparator<AbstractCard> PURE_REVERSE;
    private DuelistMasterCardViewScreen masterDeckView;
    private float scrollY;
    
    public static Comparator<AbstractCard> getSortOrder()
    {
    	return ALPHA;
    }
    
    public DuelistMasterDeckSortHeader(final DuelistMasterCardViewScreen masterDeckView) {
        super(null);
        this.masterDeckView = masterDeckView;
        (this.buttons[0] = new SortHeaderButton(DuelistMasterDeckSortHeader.TEXT[5], DuelistMasterDeckSortHeader.START_X, 0.0f, this)).setActive(true);
        final float HB_W = this.buttons[0].hb.width;
        final float leftSideOffset = Settings.WIDTH / 2.0f - HB_W * this.buttons.length / 2.0f;
        for (int i = 0; i < this.buttons.length; ++i) {
            final SortHeaderButton button = this.buttons[i];
            button.hb.move(leftSideOffset + HB_W * i + HB_W / 2.0f, button.hb.cY);
        }
    }
    
    @Override
    public void didChangeOrder(final SortHeaderButton button, final boolean isAscending) {
        button.setActive(true);
        if (button == this.buttons[0]) {
            if (isAscending) {
                this.masterDeckView.setSortOrder(null);
            }
            else {
                this.masterDeckView.setSortOrder(DuelistMasterDeckSortHeader.PURE_REVERSE);
            }
            return;
        }
        Comparator<AbstractCard> order;
        if (button == this.buttons[1]) {
            order = DuelistMasterDeckSortHeader.BY_TYPE;
        }
        else if (button == this.buttons[2]) {
            order = DuelistMasterDeckSortHeader.BY_COST;
        }
        else {
            if (button != this.buttons[3]) {
                return;
            }
            order = DuelistMasterDeckSortHeader.ALPHA;
        }
        if (!isAscending) {
            order = order.reversed();
        }
        this.masterDeckView.setSortOrder(order);
    }
    
    @Override
    protected void updateScrollPositions() {
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        switch (AbstractDungeon.player.chosenClass) {
            case IRONCLAD: {
                sb.setColor(DuelistMasterDeckSortHeader.IRONCLAD_COLOR);
                break;
            }
            case THE_SILENT: {
                sb.setColor(DuelistMasterDeckSortHeader.SILENT_COLOR);
                break;
            }
            case DEFECT: {
                sb.setColor(DuelistMasterDeckSortHeader.DEFECT_COLOR);
                break;
            }
            default: {
                sb.setColor(DuelistMasterDeckSortHeader.BAR_COLOR);
                break;
            }
        }
        sb.draw(ImageMaster.COLOR_TAB_BAR, Settings.WIDTH / 2.0f - 667.0f, this.scrollY - 51.0f, 667.0f, 51.0f, 1334.0f, 102.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1334, 102, false, false);
        super.render(sb);
    }
    
    public void updateScrollPosition(final float y) {
        this.scrollY = y + 240.0f * Settings.scale;
        for (final SortHeaderButton button : this.buttons) {
            button.updateScrollPosition(this.scrollY);
        }
    }
    
    static {
        BAR_COLOR = new Color(0.4f, 0.4f, 0.4f, 1.0f);
        IRONCLAD_COLOR = new Color(0.5f, 0.1f, 0.1f, 1.0f);
        SILENT_COLOR = new Color(0.25f, 0.55f, 0.0f, 1.0f);
        DEFECT_COLOR = new Color(0.01f, 0.34f, 0.52f, 1.0f);
        BY_TYPE = ((a, b) -> (a.type.name() + a.name).compareTo(b.type.name() + b.name));
        ALPHA = ((a, b) -> a.name.compareTo(b.name));
        BY_COST = ((a, b) -> ("" + a.cost + a.name).compareTo("" + b.cost + b.name));
        PURE_REVERSE = ((a, b) -> a.cardID.equals(b.cardID) ? 0 : -1);
    }
}
