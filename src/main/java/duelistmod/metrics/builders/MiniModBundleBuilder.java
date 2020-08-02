package duelistmod.metrics.builders;

import duelistmod.metrics.*;

import java.util.*;

public class MiniModBundleBuilder {
    private String id;
    private String modVersion;
    private String name;
    private List<String> authors;

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

    public MiniModBundleBuilder setAuthors(List<String> authors) {
        this.authors = authors;
        return this;
    }

    public MiniModBundle createMiniModBundle() {
        return new MiniModBundle(id, modVersion, name, authors);
    }
}
