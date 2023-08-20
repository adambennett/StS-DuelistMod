package duelistmod.persistence;

import duelistmod.persistence.data.DataCategory;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataDifferenceDTO<T> {

    public final DataCategory category;
    public final String setting;
    public final T defaultValue;
    public final T playerValue;

    public DataDifferenceDTO(DataCategory category, String setting, T defaultValue, T playerValue) {
        this.category = category;
        this.setting = setting;
        this.defaultValue = defaultValue;
        this.playerValue = playerValue;
    }

    @Override
    public String toString() {
        return "{ category: " + category + ", setting: '" + setting + '\'' +
                ", defaultValue: " + defaultValue +
                ", playerValue: " + playerValue +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataDifferenceDTO)) return false;
        DataDifferenceDTO<?> that = (DataDifferenceDTO<?>) o;
        return Objects.equals(category, that.category) && Objects.equals(setting, that.setting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, setting);
    }

    public static LinkedHashSet<SerializableDataDifferenceDTO> serialize(LinkedHashSet<DataDifferenceDTO<?>> from) {
        return from.stream().map(SerializableDataDifferenceDTO::new).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static class SerializableDataDifferenceDTO {
        private DataCategory category;
        private String setting;
        private Object defaultValue;
        private Object playerValue;

        public SerializableDataDifferenceDTO() {}

        public SerializableDataDifferenceDTO(DataDifferenceDTO<?> from) {
            this.category = from.category;
            this.setting = from.setting;
            this.defaultValue = from.defaultValue;
            this.playerValue = from.playerValue;
        }

        @Override
        public String toString() {
            return "{ category: " + category + ", setting: '" + setting + '\'' +
                    ", defaultValue: " + defaultValue +
                    ", playerValue: " + playerValue +
                    " }";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SerializableDataDifferenceDTO)) return false;
            SerializableDataDifferenceDTO that = (SerializableDataDifferenceDTO) o;
            return Objects.equals(getCategory(), that.getCategory()) && Objects.equals(getSetting(), that.getSetting());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getCategory(), getSetting());
        }

        public DataCategory getCategory() {
            return category;
        }

        public void setCategory(DataCategory category) {
            this.category = category;
        }

        public String getSetting() {
            return setting;
        }

        public void setSetting(String setting) {
            this.setting = setting;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        public Object getPlayerValue() {
            return playerValue;
        }

        public void setPlayerValue(Object playerValue) {
            this.playerValue = playerValue;
        }
    }
}
