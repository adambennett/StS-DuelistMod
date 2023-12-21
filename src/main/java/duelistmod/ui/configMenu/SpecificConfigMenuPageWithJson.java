package duelistmod.ui.configMenu;

import duelistmod.dto.DuelistConfigurationData;

import java.util.ArrayList;

public abstract class SpecificConfigMenuPageWithJson extends SpecificConfigMenuPage {

    public DuelistConfigurationData config;
    public ArrayList<DuelistConfigurationData> configs;
    public ModHoverImage image;

    public SpecificConfigMenuPageWithJson(String header, String pageName) {
        super(header, pageName);
    }
}
