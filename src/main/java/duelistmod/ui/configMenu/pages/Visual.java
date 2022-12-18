package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.DuelistMod;
import duelistmod.enums.CharacterModel;
import duelistmod.enums.DropdownMenuType;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;

public class Visual extends SpecificConfigMenuPage {

    public Visual() {
        super("Visual Settings");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        String cardTypeFlip = "Card Type Display Names";
        String characterModel = "Character Model";

        settingElements.add(new ModLabel(cardTypeFlip, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tagTypes = new ArrayList<>();
        tagTypes.add("Monster/Spell/Trap");
        tagTypes.add("Attack/Skill/Power");
        DuelistDropdown tagSelector = new DuelistDropdown(tagTypes, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.flipCardTags = i == 1;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_FLIP, DuelistMod.flipCardTags);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }
        });
        tagSelector.setSelectedIndex(DuelistMod.flipCardTags ? 1 : 0);

        lineBreak();
        lineBreak();

        settingElements.add(new ModLabel(characterModel, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> characterModels = new ArrayList<>();
        for (CharacterModel model : CharacterModel.values()) {
            characterModels.add(model.getDisplayName());
        }
        DuelistDropdown characterSelector = new DuelistDropdown(characterModels, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.selectedCharacterModel = CharacterModel.menuMappingReverse.get(i);
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_SELECTED_CHARACTER_MODEL, i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        characterSelector.setSelectedIndex(CharacterModel.menuMapping.get(DuelistMod.selectedCharacterModel));

        settingElements.add(characterSelector);
        settingElements.add(tagSelector);

        return settingElements;
    }
}
