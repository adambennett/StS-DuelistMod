package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import duelistmod.DuelistMod;
import duelistmod.enums.DropdownMenuType;
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
        DuelistDropdown tagSelector = new DuelistDropdown(tagTypes, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos + 22, DropdownMenuType.TAG_SELECTOR);
        tagSelector.setSelectedIndex(DuelistMod.flipCardTags ? 1 : 0);

        lineBreak();
        lineBreak();

        settingElements.add(new ModLabel(characterModel, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> characterModels = new ArrayList<>();
        characterModels.add("Yugi");
        characterModels.add("Yugi (Old Sprite)");
        characterModels.add("Kaiba");
        DuelistDropdown characterSelector = new DuelistDropdown(characterModels, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos + 22, DropdownMenuType.CHARACTER_MODEL);
        characterSelector.setSelectedIndex(DuelistMod.playAsKaiba ? 2 : DuelistMod.oldCharacter ? 1 : 0);

        settingElements.add(characterSelector);
        settingElements.add(tagSelector);

        return settingElements;
    }
}
