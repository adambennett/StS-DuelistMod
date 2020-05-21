package duelistmod.metrics;

public class MiniModBundle {

    public String modID;
    public String modVersion;
    public String name;

    public MiniModBundle(String modID, String modVersion, String name) {
        this.modID = modID;
        this.name = name;
        this.modVersion = modVersion;
    }

    public String getModID() {
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getModVersion() {
        return modVersion;
    }

    public void setModVersion(String modVersion) {
        this.modVersion = modVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
