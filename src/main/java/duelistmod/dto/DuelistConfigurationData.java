package duelistmod.dto;

import basemod.IUIElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DuelistConfigurationData implements Comparable<DuelistConfigurationData> {

    private final String displayName;
    private final ArrayList<IUIElement> settingElements;

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements) {
        this.displayName = displayName;
        this.settingElements = settingElements;
    }

    public String displayName() { return this.displayName; }

    public ArrayList<IUIElement> settingElements() { return this.settingElements; }

    @Override
    public int compareTo(@NotNull DuelistConfigurationData o) {
        return this.displayName().compareTo(o.displayName());
    }
}
