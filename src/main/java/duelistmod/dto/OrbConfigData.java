package duelistmod.dto;

public class OrbConfigData {

    private int defaultPassive;
    private int defaultEvoke;
    private int configPassive;
    private int configEvoke;
    private boolean passiveDisabled;
    private boolean evokeDisabled;

    public OrbConfigData(int defaultPassive, int defaultEvoke, int configPassive, int configEvoke, boolean passiveDisabled, boolean evokeDisabled) {
        this.defaultPassive = defaultPassive;
        this.defaultEvoke = defaultEvoke;
        this.configPassive = configPassive;
        this.configEvoke = configEvoke;
        this.passiveDisabled = passiveDisabled;
        this.evokeDisabled = evokeDisabled;
    }

    public OrbConfigData(int defaultPassive, int defaultEvoke) {
        this(defaultPassive, defaultEvoke, defaultPassive, defaultEvoke, false, false);
    }

    public int getDefaultPassive() {
        return this.defaultPassive;
    }

    public int getDefaultEvoke() {
        return this.defaultEvoke;
    }

    public int getConfigPassive() {
        return this.configPassive;
    }

    public int getConfigEvoke() {
        return this.configEvoke;
    }

    public boolean getPassiveDisabled() {
        return this.passiveDisabled;
    }

    public boolean getEvokeDisabled() {
        return this.evokeDisabled;
    }

    // Setters needed for proper serialization!
    public void setDefaultPassive(int defaultPassive) {
        this.defaultPassive = defaultPassive;
    }

    // Setters needed for proper serialization!
    public void setDefaultEvoke(int defaultEvoke) {
        this.defaultEvoke = defaultEvoke;
    }

    // Setters needed for proper serialization!
    public void setConfigPassive(int configPassive) {
        this.configPassive = configPassive;
    }

    // Setters needed for proper serialization!
    public void setConfigEvoke(int configEvoke) {
        this.configEvoke = configEvoke;
    }

    // Setters needed for proper serialization!
    public void setPassiveDisabled(boolean passiveDisabled) {
        this.passiveDisabled = passiveDisabled;
    }

    // Setters needed for proper serialization!
    public void setEvokeDisabled(boolean evokeDisabled) {
        this.evokeDisabled = evokeDisabled;
    }

    @Override
    public String toString() {
        return "{" +
                "defaultPassive:" + defaultPassive +
                ", defaultEvoke:" + defaultEvoke +
                ", configPassive:" + configPassive +
                ", configEvoke:" + configEvoke +
                ", passiveDisabled:" + passiveDisabled +
                ", evokeDisabled:" + evokeDisabled +
                '}';
    }
}
