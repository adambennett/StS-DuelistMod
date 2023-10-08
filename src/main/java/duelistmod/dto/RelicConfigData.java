package duelistmod.dto;

import com.google.gson.Gson;

import java.util.HashMap;

public class RelicConfigData {

    private Boolean isDisabled;
    private HashMap<String, Object> properties;

    public RelicConfigData() {
        this(false);
    }

    public RelicConfigData(Boolean isDisabled) {
        this(isDisabled, new HashMap<>());
    }

    public RelicConfigData(Boolean isDisabled, HashMap<String, Object> properties) {
        this.isDisabled = isDisabled;
        this.properties = properties;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean disabled) {
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
