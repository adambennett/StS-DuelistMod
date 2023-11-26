package duelistmod.dto;

import com.google.gson.Gson;

import java.util.HashMap;

public class MonsterTypeConfigData {

    private HashMap<String, Object> properties;

    public MonsterTypeConfigData() {
        this(new HashMap<>());
    }

    public MonsterTypeConfigData(HashMap<String, Object> properties) {
        this.properties = properties;
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
