package duelistmod.persistence.data;

import duelistmod.DuelistMod;
import duelistmod.enums.ColorlessShopSource;
import duelistmod.enums.DataCategoryType;
import duelistmod.enums.MenuCardRarity;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.LinkedHashSet;

public class ColorlessShopSettings extends DataCategory {


    private String leftSlotSource = ColorlessShopSource.BASIC_COLORLESS.display();
    private String rightSlotSource = ColorlessShopSource.BASIC_COLORLESS.display();
    private String leftSlotLowRarity = MenuCardRarity.COMMON.display();
    private String leftSlotHighRarity = MenuCardRarity.UNCOMMON.display();
    private String rightSlotLowRarity = MenuCardRarity.RARE.display();
    private String rightSlotHighRarity = MenuCardRarity.RARE.display();

    public ColorlessShopSettings() {
        this.category = "Colorless Shop Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public ColorlessShopSettings(ColorlessShopSettings from) {
        this(from.getLeftSlotSource(), from.getRightSlotSource(), from.getLeftSlotLowRarity(), from.getLeftSlotHighRarity(), from.getRightSlotLowRarity(), from.getRightSlotHighRarity());
    }

    public ColorlessShopSettings(String leftSlotSource, String rightSlotSource, String leftSlotLowRarity, String leftSlotHighRarity, String rightSlotLowRarity, String rightSlotHighRarity) {
        this();
        this.leftSlotSource = leftSlotSource;
        this.rightSlotSource = rightSlotSource;
        this.leftSlotLowRarity = leftSlotLowRarity;
        this.leftSlotHighRarity = leftSlotHighRarity;
        this.rightSlotLowRarity = rightSlotLowRarity;
        this.rightSlotHighRarity = rightSlotHighRarity;
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.ColorlessShopSettings.leftSlotSource.equals(playerSettings.ColorlessShopSettings.leftSlotSource)) {
            output.add(new DataDifferenceDTO<>(this, "Left Slot Source", defaultSettings.ColorlessShopSettings.leftSlotSource, playerSettings.ColorlessShopSettings.leftSlotSource));
        }
        if (!defaultSettings.ColorlessShopSettings.rightSlotSource.equals(playerSettings.ColorlessShopSettings.rightSlotSource)) {
            output.add(new DataDifferenceDTO<>(this, "Right Slot Source", defaultSettings.ColorlessShopSettings.rightSlotSource, playerSettings.ColorlessShopSettings.rightSlotSource));
        }
        if (!defaultSettings.ColorlessShopSettings.leftSlotLowRarity.equals(playerSettings.ColorlessShopSettings.leftSlotLowRarity)) {
            output.add(new DataDifferenceDTO<>(this, "Left Slot: Low Rarity", defaultSettings.ColorlessShopSettings.leftSlotLowRarity, playerSettings.ColorlessShopSettings.leftSlotLowRarity));
        }
        if (!defaultSettings.ColorlessShopSettings.leftSlotHighRarity.equals(playerSettings.ColorlessShopSettings.leftSlotHighRarity)) {
            output.add(new DataDifferenceDTO<>(this, "Left Slot: High Rarity", defaultSettings.ColorlessShopSettings.leftSlotHighRarity, playerSettings.ColorlessShopSettings.leftSlotHighRarity));
        }
        if (!defaultSettings.ColorlessShopSettings.rightSlotLowRarity.equals(playerSettings.ColorlessShopSettings.rightSlotLowRarity)) {
            output.add(new DataDifferenceDTO<>(this, "Right Slot: Low Rarity", defaultSettings.ColorlessShopSettings.rightSlotLowRarity, playerSettings.ColorlessShopSettings.rightSlotLowRarity));
        }
        if (!defaultSettings.ColorlessShopSettings.rightSlotHighRarity.equals(playerSettings.ColorlessShopSettings.rightSlotHighRarity)) {
            output.add(new DataDifferenceDTO<>(this, "Right Slot: High Rarity", defaultSettings.ColorlessShopSettings.rightSlotHighRarity, playerSettings.ColorlessShopSettings.rightSlotHighRarity));
        }
        return output;
    }

    public String getLeftSlotSource() {
        return leftSlotSource;
    }
    public String getRightSlotSource() {
        return rightSlotSource;
    }
    public String getLeftSlotLowRarity() {
        return leftSlotLowRarity;
    }
    public String getLeftSlotHighRarity() {
        return leftSlotHighRarity;
    }
    public String getRightSlotLowRarity() {
        return rightSlotLowRarity;
    }
    public String getRightSlotHighRarity() {
        return rightSlotHighRarity;
    }

    public void setLeftSlotSource(String leftSlotSource) {
        this.leftSlotSource = leftSlotSource;
        DuelistMod.colorlessShopLeftSlotSource = ColorlessShopSource.displayNameMapping.getOrDefault(leftSlotSource, ColorlessShopSource.BASIC_COLORLESS);
    }

    public void setRightSlotSource(String rightSlotSource) {
        this.rightSlotSource = rightSlotSource;
        DuelistMod.colorlessShopRightSlotSource = ColorlessShopSource.displayNameMapping.getOrDefault(rightSlotSource, ColorlessShopSource.BASIC_COLORLESS);
    }

    public void setLeftSlotLowRarity(String leftSlotLowRarity) {
        this.leftSlotLowRarity = leftSlotLowRarity;
        DuelistMod.colorlessShopLeftSlotLowRarity = MenuCardRarity.displayNameMapping.getOrDefault(leftSlotLowRarity, MenuCardRarity.COMMON);
    }

    public void setLeftSlotHighRarity(String leftSlotHighRarity) {
        this.leftSlotHighRarity = leftSlotHighRarity;
        DuelistMod.colorlessShopLeftSlotHighRarity = MenuCardRarity.displayNameMapping.getOrDefault(leftSlotHighRarity, MenuCardRarity.UNCOMMON);
    }

    public void setRightSlotLowRarity(String rightSlotLowRarity) {
        this.rightSlotLowRarity = rightSlotLowRarity;
        DuelistMod.colorlessShopRightSlotLowRarity = MenuCardRarity.displayNameMapping.getOrDefault(rightSlotLowRarity, MenuCardRarity.RARE);
    }

    public void setRightSlotHighRarity(String rightSlotHighRarity) {
        this.rightSlotHighRarity = rightSlotHighRarity;
        DuelistMod.colorlessShopRightSlotHighRarity = MenuCardRarity.displayNameMapping.getOrDefault(rightSlotHighRarity, MenuCardRarity.RARE);
    }
}
