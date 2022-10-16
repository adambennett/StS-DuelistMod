package duelistmod.ui.configMenu;


import basemod.IUIElement;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMenuPaginator implements IUIElement
{
    private int page;
    private int elementsPerPage;
    private List<ConfigMenuPage> elements;
    private int width;
    private int height;
    private int rows;
    private int columns;
    private Map<String, Integer> pages;
    private DuelistDropdown pageSelector;

    public ConfigMenuPaginator(final int rows, final int columns, final int width, final int height, final List<ConfigMenuPage> elements, List<String> pages, DuelistDropdown pageSelector) {
        this.page = 0;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
        this.elements = new ArrayList<>();
        this.elementsPerPage = rows * columns;
        this.pageSelector = pageSelector;
        this.pages = new HashMap<>();
        int counter = 0;
        for (String page : pages) {
            this.pages.put(page, counter);
            counter++;
        }
        for (int i = 0; i < elements.size(); ++i) {
            final ConfigMenuPage element = elements.get(i);
            final ConfigMenuPage newElement = new ConfigMenuPage(element.x + width * (i % columns), element.y - height * ((i % this.elementsPerPage - i % columns) / columns), width, height, element.elements);
            this.elements.add(newElement);
        }
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

    private void afterPageChange() {
        if (this.page >= (this.elements == null ? 0 : this.elements.size())) {
            this.page = 0;
        }
        if (this.page < 0) {
            this.page = this.elements == null ? 0 : this.elements.size() - 1;
        }
        this.pageSelector.setSelectedIndex(this.page);
    }

    public int page() {
        return this.page;
    }

    public int lastPage() {
        return this.elements.size();
    }


}
