package duelistmod.persistence.data;

import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.LinkedHashSet;
import java.util.Objects;

public abstract class DataCategory {
    public String category;
    public DataCategoryType type;
    public DataCategory() {}
    public String getCategory() { return category; }
    public DataCategoryType getType() { return type; }
    public void setCategory(String category) { this.category = category; }
    public void setType(DataCategoryType type) { this.type = type; }
    public abstract LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings);

    @Override
    public String toString() {
        return "{" +
                " category: '" + category + '\'' +
                ", type: " + type +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataCategory)) return false;
        DataCategory category1 = (DataCategory) o;
        return Objects.equals(getCategory(), category1.getCategory()) && getType() == category1.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory(), getType());
    }
}
