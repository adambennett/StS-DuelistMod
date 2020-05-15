package duelistmod.metrics;

public class MiniModBundle {

    public String ID;
    public String ModVersion;

    public MiniModBundle(String ID, String modVersion) {
        this.ID = ID;
        ModVersion = modVersion;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getModVersion() {
        return ModVersion;
    }

    public void setModVersion(String modVersion) {
        ModVersion = modVersion;
    }
}
