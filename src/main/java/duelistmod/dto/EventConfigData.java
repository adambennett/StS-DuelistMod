package duelistmod.dto;

import com.google.gson.Gson;

import java.util.HashMap;

public class EventConfigData {

    private Boolean isDisabled;
    private Boolean multipleChoices;
    private HashMap<String, Object> properties;

    public EventConfigData() { this(false, false); }

    public EventConfigData(Boolean isDisabled, Boolean multipleChoices) { this(isDisabled, multipleChoices, new HashMap<>()); }

    public EventConfigData(Boolean isDisabled, Boolean multipleChoices, HashMap<String, Object> properties) {
        this.isDisabled = isDisabled;
        this.properties = properties;
        this.multipleChoices = multipleChoices;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    public Boolean getMultipleChoices() {
        return multipleChoices;
    }

    public void setMultipleChoices(Boolean multipleChoices) {
        this.multipleChoices = multipleChoices;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    public void put(String key, Object value) {
        if (this.properties != null) {
            this.properties.put(key, value);
        }
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
