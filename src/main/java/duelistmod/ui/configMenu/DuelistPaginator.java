package duelistmod.ui.configMenu;


import basemod.IUIElement;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import duelistmod.DuelistMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuelistPaginator implements IUIElement
{
    private int page;
    private int elementsPerPage;
    private List<ConfigMenuPage> elements;
    private int width;
    private int height;
    private int rows;
    private int columns;
    private Map<String, Integer> pages;
    private Map<Integer, String> pageNumbers;
    private DuelistDropdown pageSelector;

    public DuelistPaginator(final int rows, final int columns, final int width, final int height, final List<ConfigMenuPage> elements, List<String> pages, DuelistDropdown pageSelector) {
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
        }
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
    }

    public void update() {
        this.elements.get(this.page).update();
    }

    public int renderLayer() {
        return 1;
    }

    public int updateOrder() {
        return 1;
    }

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
        if (this.page >= (this.elements == null ? 0 : this.elements.size())) {
            this.page = 0;
        }
        if (this.page < 0) {
            this.page = this.elements == null ? 0 : this.elements.size() - 1;
        }
        this.pageSelector.setSelectedIndex(this.page);
    }

}
