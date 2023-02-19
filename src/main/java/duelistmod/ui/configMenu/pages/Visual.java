package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.SlowPower;
import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.enums.CharacterModel;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Visual extends SpecificConfigMenuPage {

    public Visual() {
        super("Visual Settings", "Visual");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        String cardTypeFlip = "Card Type Display Names";
        String characterModel = "Character Model";

        String tooltip = "When enabled, the following keywords will be removed from all card text and instead icons will be displayed on the card to indicate the effect: #yInnate, #yEthereal, #yRetain, #yPurge, #yExhaust. NL NL Thanks to the authors of #yStSLib for making this option possible!";
        settingElements.add(new DuelistLabeledToggleButton("Replace common keywords with icons", tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.isReplaceCommonKeywordsWithIcons, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.isReplaceCommonKeywordsWithIcons = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_REPLACE_COMMON_KEYWORDS_WITH_ICON, DuelistMod.isReplaceCommonKeywordsWithIcons);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel(cardTypeFlip, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tagTypes = new ArrayList<>();
        tagTypes.add("Monster/Spell/Trap");
        tagTypes.add("Attack/Skill/Power");
        tooltip = "Modifies the tag that appears on cards. NL NL Base game card type can be determined by the shape of the card image, i.e. square images are Skills. NL NL Duelist type can be determined by card color, i.e. Monster cards are yellow.";
        DuelistDropdown tagSelector = new DuelistDropdown(tooltip, tagTypes, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            DuelistMod.flipCardTags = i == 1;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_FLIP, DuelistMod.flipCardTags);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }
        });
        tagSelector.setSelectedIndex(DuelistMod.flipCardTags ? 1 : 0);

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel(characterModel, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> characterModels = new ArrayList<>();
        for (CharacterModel model : CharacterModel.values()) {
            characterModels.add(model.getDisplayName());
        }
        DuelistDropdown characterSelector = new DuelistDropdown(characterModels, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
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

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel("Animation Speed", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Determines the speed of your player's animation when playing as #yThe #yDuelist. Set to #b0.6 by default.";
        ArrayList<String> animationSpeeds = new ArrayList<>();
        for (int i = 0; i < 1001; i++) {
            animationSpeeds.add(formatFloatVal(i));
        }
        DuelistDropdown animationSpeedSelector = new DuelistDropdown(tooltip, animationSpeeds, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            DuelistMod.playerAnimationSpeed = (float)i / 10f;
            boolean setSpeedFromSlow = false;
            if (AbstractDungeon.player != null && AbstractDungeon.player.powers != null && AbstractDungeon.player.hasPower(SlowPower.POWER_ID)) {
                int amt = AbstractDungeon.player.getPower(SlowPower.POWER_ID).amount;
                float mod = (float)amt / 10.0f;
                if (mod > 0) {
                    float newVal = mod >= 1.0f ? 0.9f : (DuelistMod.playerAnimationSpeed * mod);
                    TheDuelist.setAnimationSpeed(newVal);
                    setSpeedFromSlow = true;
                }
            }
            if (!setSpeedFromSlow) {
                TheDuelist.setAnimationSpeed(DuelistMod.playerAnimationSpeed);
            }
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("playerAnimationSpeed", i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        animationSpeedSelector.setSelectedIndex((int) (DuelistMod.playerAnimationSpeed * 10));

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel("Enemy Duelist Animation Speed", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Determines the speed of enemy animations. Only applies to #yDuelist enemies. Set to #b0.6 by default.";
        ArrayList<String> enemyAnimationSpeeds = new ArrayList<>();
        for (int i = 0; i < 1001; i++) {
            enemyAnimationSpeeds.add(formatFloatVal(i));
        }
        DuelistDropdown enemyAnimationSpeedSelector = new DuelistDropdown(tooltip, enemyAnimationSpeeds, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), 10, (s, i) -> {
            DuelistMod.enemyAnimationSpeed = (float)i / 10f;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("enemyAnimationSpeed", i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        enemyAnimationSpeedSelector.setSelectedIndex((int) (DuelistMod.enemyAnimationSpeed * 10));


        settingElements.add(enemyAnimationSpeedSelector);
        settingElements.add(animationSpeedSelector);
        settingElements.add(characterSelector);
        settingElements.add(tagSelector);

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
    };
}
