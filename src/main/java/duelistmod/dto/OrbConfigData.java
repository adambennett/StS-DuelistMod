package duelistmod.dto;

import com.google.gson.Gson;

import java.util.HashMap;

public class OrbConfigData {

    private Integer defaultPassive;
    private Integer defaultEvoke;
    private Integer configPassive;
    private Integer configEvoke;
    private Boolean passiveDisabled;
    private Boolean evokeDisabled;
    private HashMap<String, Object> properties;

    public OrbConfigData(int defaultPassive, int defaultEvoke) {
        this(defaultPassive, defaultEvoke, defaultPassive, defaultEvoke, false, false);
    }

    public OrbConfigData(int defaultPassive, int defaultEvoke, int configPassive, int configEvoke, boolean passiveDisabled, boolean evokeDisabled) {
        this(defaultPassive, defaultEvoke, configPassive, configEvoke, passiveDisabled, evokeDisabled, new HashMap<>());
    }

    public OrbConfigData(Integer defaultPassive, Integer defaultEvoke, Integer configPassive, Integer configEvoke, Boolean passiveDisabled, Boolean evokeDisabled, HashMap<String, Object> properties) {
        this.defaultPassive = defaultPassive;
        this.defaultEvoke = defaultEvoke;
        this.configPassive = configPassive;
        this.configEvoke = configEvoke;
        this.passiveDisabled = passiveDisabled;
        this.evokeDisabled = evokeDisabled;
        this.properties = properties;
    }

    public Integer getDefaultPassive() {
        return defaultPassive;
    }

    public void setDefaultPassive(Integer defaultPassive) {
        this.defaultPassive = defaultPassive;
    }

    public Integer getDefaultEvoke() {
        return defaultEvoke;
    }

    public void setDefaultEvoke(Integer defaultEvoke) {
        this.defaultEvoke = defaultEvoke;
    }

    public Integer getConfigPassive() {
        return configPassive;
    }

    public void setConfigPassive(Integer configPassive) {
        this.configPassive = configPassive;
    }

    public Integer getConfigEvoke() {
        return configEvoke;
    }

    public void setConfigEvoke(Integer configEvoke) {
        this.configEvoke = configEvoke;
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

    public Boolean getPassiveDisabled() {
        return passiveDisabled;
    }

    public void setPassiveDisabled(Boolean passiveDisabled) {
        this.passiveDisabled = passiveDisabled;
    }

    public Boolean getEvokeDisabled() {
        return evokeDisabled;
    }

    public void setEvokeDisabled(Boolean evokeDisabled) {
        this.evokeDisabled = evokeDisabled;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
