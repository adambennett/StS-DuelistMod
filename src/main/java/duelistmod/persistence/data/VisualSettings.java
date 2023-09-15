package duelistmod.persistence.data;

import duelistmod.DuelistMod;
import duelistmod.enums.CharacterModel;
import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;
import duelistmod.ui.configMenu.pages.Visual;

import java.util.LinkedHashSet;

public class VisualSettings extends DataCategory {

    private Boolean replaceCommonKeywordsWithIcons = false;
    private Boolean flipCardTags = false;
    private String selectedCharacterModel = CharacterModel.ANIM_YUGI.getDisplayName();
    private Float animationSpeed = 0.6f;
    private Float enemyAnimationSpeed = 0.6f;

    public VisualSettings() {
        this.category = "Visual Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public VisualSettings(VisualSettings from) {
        this(from.replaceCommonKeywordsWithIcons,
                from.flipCardTags,
                from.selectedCharacterModel,
                from.animationSpeed,
                from.enemyAnimationSpeed);
    }

    public VisualSettings(Boolean replaceCommonKeywordsWithIcons,
                          Boolean flipCardTags,
                          String selectedCharacterModel,
                          Float animationSpeed,
                          Float enemyAnimationSpeed) {
        this();
        this.replaceCommonKeywordsWithIcons = PersistentDuelistData.validateBool(replaceCommonKeywordsWithIcons, false);
        this.flipCardTags = PersistentDuelistData.validateBool(flipCardTags, false);
        this.selectedCharacterModel = PersistentDuelistData.validate(selectedCharacterModel, CharacterModel.ANIM_YUGI.getDisplayName(), CharacterModel.displayNames);
        this.animationSpeed = PersistentDuelistData.validate(animationSpeed, 0.6f, Visual.animationSpeedOptions);
        this.enemyAnimationSpeed = PersistentDuelistData.validate(enemyAnimationSpeed, 0.6f, Visual.animationSpeedOptions);
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.VisualSettings.selectedCharacterModel.equals(playerSettings.VisualSettings.selectedCharacterModel)) {
            output.add(new DataDifferenceDTO<>(this, "Character Model", defaultSettings.VisualSettings.selectedCharacterModel, playerSettings.VisualSettings.selectedCharacterModel));
        }
        return output;
    }

    public Boolean getReplaceCommonKeywordsWithIcons() {
        return replaceCommonKeywordsWithIcons;
    }

    public void setReplaceCommonKeywordsWithIcons(Boolean replaceCommonKeywordsWithIcons) {
        this.replaceCommonKeywordsWithIcons = replaceCommonKeywordsWithIcons;
    }

    public Boolean getFlipCardTags() {
        return flipCardTags;
    }

    public void setFlipCardTags(Boolean flipCardTags) {
        this.flipCardTags = flipCardTags;
    }

    public String getSelectedCharacterModel() {
        return selectedCharacterModel;
    }

    public void setSelectedCharacterModel(String selectedCharacterModel) {
        this.selectedCharacterModel = selectedCharacterModel;
        DuelistMod.selectedCharacterModel = CharacterModel.displayNameMapping.getOrDefault(selectedCharacterModel, CharacterModel.ANIM_YUGI);
    }

    public Float getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(Float animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public Float getEnemyAnimationSpeed() {
        return enemyAnimationSpeed;
    }

    public void setEnemyAnimationSpeed(Float enemyAnimationSpeed) {
        this.enemyAnimationSpeed = enemyAnimationSpeed;
    }
}
