package duelistmod.metrics.builders;

import duelistmod.metrics.*;

public class MiniModBundleBuilder {
    private String id;
    private String modVersion;
    private String name;

    public MiniModBundleBuilder setID(String id) {
        this.id = id;
        return this;
    }

    public MiniModBundleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MiniModBundleBuilder setModVersion(String modVersion) {
        this.modVersion = modVersion;
        return this;
    }

    public MiniModBundle createMiniModBundle() {
        return new MiniModBundle(id, modVersion, name);
    }
}
