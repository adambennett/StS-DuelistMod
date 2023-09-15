package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.SlowPower;
import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.enums.CharacterModel;
import duelistmod.persistence.data.VisualSettings;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Visual extends SpecificConfigMenuPage implements RefreshablePage {

    private boolean isRefreshing;
    public static LinkedHashSet<String> animationSpeedOptions;

    public Visual() {
        super("Visual Settings", "Visual");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        String cardTypeFlip = "Card Type Display Names";
        String characterModel = "Character Model";

        String tooltip = "When enabled, the following keywords will be removed from all card text and instead icons will be displayed on the card to indicate the effect: #yInnate, #yEthereal, #yRetain, #yPurge, #yExhaust. NL NL Thanks to the authors of #yStSLib for making this option possible!";
        settingElements.add(new DuelistLabeledToggleButton("Replace common keywords with icons", tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getReplaceCommonKeywordsWithIcons(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setReplaceCommonKeywordsWithIcons(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel(cardTypeFlip, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tagTypes = new ArrayList<>();
        tagTypes.add("Monster/Spell/Trap");
        tagTypes.add("Attack/Skill/Power");
        tooltip = "Modifies the tag that appears on cards. NL NL Base game card type can be determined by the shape of the card image, i.e. square images are Skills. NL NL Duelist type can be determined by card color, i.e. Monster cards are yellow.";
        DuelistDropdown tagSelector = new DuelistDropdown(tooltip, tagTypes, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            settings().setFlipCardTags(i == 1);
            DuelistMod.configSettingsLoader.save();
        });
        tagSelector.setSelectedIndex(settings().getFlipCardTags() ? 1 : 0);

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel(characterModel, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> characterModels = new ArrayList<>();
        for (CharacterModel model : CharacterModel.values()) {
            characterModels.add(model.getDisplayName());
        }
        DuelistDropdown characterSelector = new DuelistDropdown(characterModels, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            settings().setSelectedCharacterModel(s);
            DuelistMod.configSettingsLoader.save();
        });
        characterSelector.setSelected(settings().getSelectedCharacterModel());

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel("Animation Speed", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Determines the speed of your player's animation when playing as #yThe #yDuelist. Set to #b0.6 by default.";
        ArrayList<String> animationSpeeds = new ArrayList<>(animationSpeedOptions);
        DuelistDropdown animationSpeedSelector = new DuelistDropdown(tooltip, animationSpeeds, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            settings().setAnimationSpeed((float)i / 10f);
            boolean setSpeedFromSlow = false;
            if (AbstractDungeon.player != null && AbstractDungeon.player.powers != null && AbstractDungeon.player.hasPower(SlowPower.POWER_ID)) {
                int amt = AbstractDungeon.player.getPower(SlowPower.POWER_ID).amount;
                float mod = (float)amt / 10.0f;
                if (mod > 0) {
                    float newVal = mod >= 1.0f ? 0.9f : (settings().getAnimationSpeed() * mod);
                    TheDuelist.setAnimationSpeed(newVal);
                    setSpeedFromSlow = true;
                }
            }
            if (!setSpeedFromSlow) {
                TheDuelist.setAnimationSpeed(settings().getAnimationSpeed());
            }
            DuelistMod.configSettingsLoader.save();
        });
        animationSpeedSelector.setSelectedIndex((int) (settings().getAnimationSpeed() * 10));

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel("Enemy Duelist Animation Speed", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Determines the speed of enemy animations. Only applies to #yDuelist enemies. Set to #b0.6 by default.";
        ArrayList<String> enemyAnimationSpeeds = new ArrayList<>(animationSpeedOptions);
        DuelistDropdown enemyAnimationSpeedSelector = new DuelistDropdown(tooltip, enemyAnimationSpeeds, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), 10, (s, i) -> {
            settings().setEnemyAnimationSpeed((float)i / 10f);
            DuelistMod.configSettingsLoader.save();
        });
        enemyAnimationSpeedSelector.setSelectedIndex((int) (settings().getEnemyAnimationSpeed() * 10));

        settingElements.add(enemyAnimationSpeedSelector);
        settingElements.add(animationSpeedSelector);
        settingElements.add(characterSelector);
        settingElements.add(tagSelector);

        this.isRefreshing = false;
        return settingElements;
    }

    private static String formatFloatVal(int index) {
        String df = new DecimalFormat("#.00").format((float)index / 10f);
        if (df.startsWith(".")) {
            df = "0" + df;
        }
        if (df.endsWith("0")) {
            df = df.substring(0, df.length() - 1);
        }
        return df;
    }

    @Override
    public void refresh() {
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);
        }
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.VisualSettings = new VisualSettings();
    }

    private VisualSettings settings() {
        return DuelistMod.persistentDuelistData.VisualSettings;
    }

    static {
        animationSpeedOptions = new LinkedHashSet<>();
        for (int i = 0; i < 1001; i++) {
            animationSpeedOptions.add(formatFloatVal(i));
        }
    }
}
