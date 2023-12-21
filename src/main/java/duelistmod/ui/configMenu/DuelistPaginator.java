package duelistmod.ui.configMenu;

import basemod.IUIElement;
import basemod.patches.com.megacrit.cardcrawl.helpers.TipHelper.HeaderlessTip;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import duelistmod.DuelistMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuelistPaginator implements IUIElement, HitboxListener {
    private int page;
    private final int elementsPerPage;
    public List<ConfigMenuPage> elements;
    public List<SpecificConfigMenuPage> elementDetails;
    public ConfigMenuPage currentPage;
    public SpecificConfigMenuPage currentPageDetails;
    private int width;
    private int height;
    private int rows;
    private int columns;
    private final Map<String, Integer> pages;
    private final Map<Integer, String> pageNumbers;
    private final Hitbox resetButtonHb;
    private final Hitbox resetSubPageButtonHb;
    private final DuelistDropdown pageSelector;

    private static final float RESET_BUTTON_CY = (Settings.isSixteenByTen ? 150.0f : 120.0f) * Settings.scale;

    public DuelistPaginator(final int rows, final int columns, final int width, final int height, final List<ConfigMenuPage> elements, List<SpecificConfigMenuPage> elementDetails, List<String> pages, DuelistDropdown pageSelector) {
        this.page = 0;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
        this.elements = new ArrayList<>();
        this.elementsPerPage = rows * columns;
        this.pageSelector = pageSelector;
        this.pages = new HashMap<>();
        this.pageNumbers = new HashMap<>();
        this.elementDetails = elementDetails;
        this.currentPageDetails = elementDetails.isEmpty() ? null : elementDetails.get(0);
        int counter = 0;
        for (String page : pages) {
            this.pages.put(page, counter);
            this.pageNumbers.put(counter, page);
            counter++;
        }
        for (int i = 0; i < elements.size(); ++i) {
            final ConfigMenuPage element = elements.get(i);
            final ConfigMenuPage newElement = new ConfigMenuPage(element.name, element.x + width * (i % columns), element.y - height * ((i % this.elementsPerPage - i % columns) / columns), width, height, element.elements);
            this.elements.add(newElement);
            if (i == 0) {
                this.currentPage = newElement;
            }
        }
        this.resetButtonHb = new Hitbox(300.0f * Settings.scale, 72.0f * Settings.scale);
        this.resetSubPageButtonHb = new Hitbox(300.0f * Settings.scale, 72.0f * Settings.scale);
        this.resetButtonHb.move(Settings.WIDTH * 0.25f, RESET_BUTTON_CY);
        this.resetSubPageButtonHb.move(Settings.WIDTH * 0.75f, RESET_BUTTON_CY);
    }

    public void refreshPage(SpecificConfigMenuPage page) {
        List<ConfigMenuPage> newElements = new ArrayList<>();
        ConfigMenuPage generatedPage = page.generatePage();
        String pageName = page.getHeader();
        for (ConfigMenuPage cPage : this.elements) {
            if (cPage.name.equals(pageName)) {
                newElements.add(generatedPage);
            } else {
                newElements.add(cPage);
            }
        }
        this.elements = newElements;
    }

    public void render(final SpriteBatch spriteBatch) {
        this.elements.get(this.page).render(spriteBatch);
        this.renderResetDefaultButton(spriteBatch);
        this.renderResetSubPageDefaultButton(spriteBatch);
    }

    private void renderResetDefaultButton(final SpriteBatch spriteBatch) {
        Color color = this.resetButtonHb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR;
        FontHelper.renderFontCentered(spriteBatch, FontHelper.panelEndTurnFont, "Reset Page to Default", this.resetButtonHb.cX, this.resetButtonHb.cY, color);
        this.resetButtonHb.render(spriteBatch);
        if (this.resetButtonHb.hovered) {
            String pageName = this.currentPageDetails == null ? "this" : this.currentPageDetails.getHeader();
            if (this.currentPageDetails != null) {
                pageName = "#y" + pageName;
                pageName = pageName.replaceAll(" ", " #y");
            }
            String tooltip = "Reset all " + pageName + " to default.";
            if (this.currentPageDetails instanceof SubMenuPage) {
                tooltip += " Includes sub-menu settings.";
            }
            HeaderlessTip.renderHeaderlessTip((float) InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY + 50.0F * Settings.scale, tooltip);
        }
    }

    private void renderResetSubPageDefaultButton(final SpriteBatch spriteBatch) {
        if (this.currentPageDetails instanceof SubMenuPage) {
            SubMenuPage subMenuPage = (SubMenuPage)this.currentPageDetails;
            if (subMenuPage.hasSubMenuPageSettings()) {
                Color color = this.resetSubPageButtonHb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR;
                FontHelper.renderFontCentered(spriteBatch, FontHelper.panelEndTurnFont, "Reset Sub-Page to Default", this.resetSubPageButtonHb.cX, this.resetSubPageButtonHb.cY, color);
                this.resetSubPageButtonHb.render(spriteBatch);
                if (this.resetSubPageButtonHb.hovered) {
                    String pageName = ("#y" + subMenuPage.getSubMenuPageName()).replaceAll(" ", " #y");
                    String outerPageName = ("#y" + this.currentPageDetails.getHeader()).replaceAll(" ", " #y");
                    String tooltip = "Reset " + pageName + " settings to default. No other " + outerPageName + " will be modified.";
                    HeaderlessTip.renderHeaderlessTip((float) InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY + 50.0F * Settings.scale, tooltip);
                }
            }
        }
    }

    public void update() {
        this.elements.get(this.page).update();
        this.resetButtonHb.encapsulatedUpdate(this);
        if (this.currentPageDetails instanceof SubMenuPage) {
            this.resetSubPageButtonHb.encapsulatedUpdate(this);
        }
    }

    public int renderLayer() {
        return 1;
    }

    public int updateOrder() {
        return 1;
    }

    public ConfigMenuPage getPage() { return this.elements.get(this.page); }

    public void nextPage() {
        ++this.page;
        this.afterPageChange();
    }

    public void prevPage() {
        --this.page;
        this.afterPageChange();
    }

    public void setPage(String name) {
        Integer page = this.pages.getOrDefault(name, null);
        if (page != null) {
            this.page = page;
            this.afterPageChange();
        }
    }

    public void resetToPageOne() {
        for (RefreshablePage page : DuelistMod.refreshablePages) {
            page.refresh();
        }
        String page = this.pageNumbers.getOrDefault(0, null);
        if (page != null) {
            this.setPage(page);
        }
    }

    private void afterPageChange() {
        if (DuelistMod.openDropdown != null) {
            DuelistMod.openDropdown.close();
        }
        if (this.page >= (this.elements == null ? 0 : this.elements.size())) {
            this.page = 0;
        }
        if (this.page < 0) {
            this.page = this.elements == null ? 0 : this.elements.size() - 1;
        }
        this.currentPage = this.elements != null ? this.elements.get(this.page) : null;
        this.currentPageDetails = this.elementDetails != null ? this.elementDetails.get(this.page) : null;
        this.pageSelector.setSelectedIndex(this.page);
    }

    @Override
    public void hoverStarted(Hitbox hitbox) {
        CardCrawlGame.sound.play("UI_HOVER");
    }

    @Override
    public void startClicking(Hitbox hitbox) {
        CardCrawlGame.sound.play("UI_CLICK_1");
    }

    @Override
    public void clicked(Hitbox hitbox) {
        if (hitbox == this.resetButtonHb) {
            CardCrawlGame.sound.play("END_TURN");
            if (this.currentPageDetails != null) {
                this.currentPageDetails.resetToDefault();
                DuelistMod.configSettingsLoader.save();
                if (this.currentPageDetails instanceof RefreshablePage) {
                    ((RefreshablePage)this.currentPageDetails).refresh();
                }
            }
        } else if (hitbox == this.resetSubPageButtonHb && this.currentPageDetails instanceof SubMenuPage) {
            CardCrawlGame.sound.play("END_TURN");
            SubMenuPage subMenuPage = (SubMenuPage) this.currentPageDetails;
            subMenuPage.resetSubPageToDefault();
            DuelistMod.configSettingsLoader.save();
            subMenuPage.refreshAfterReset();
        }
    }

    public void resetAllPagesToDefault() {
        for (SpecificConfigMenuPage page : this.elementDetails) {
            page.resetToDefault();
            if (page instanceof RefreshablePage) {
                ((RefreshablePage)page).refresh();
            }
        }
        DuelistMod.configSettingsLoader.save();
    }
}
