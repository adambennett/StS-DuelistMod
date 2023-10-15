package duelistmod.persistence;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import duelistmod.DuelistMod;
import duelistmod.dto.OrbConfigData;
import duelistmod.dto.PotionConfigData;
import duelistmod.dto.RelicConfigData;
import duelistmod.enums.CardPoolType;
import duelistmod.enums.CharacterModel;
import duelistmod.enums.ColorlessShopSource;
import duelistmod.enums.DeckUnlockRate;
import duelistmod.enums.MenuCardRarity;
import duelistmod.enums.SpecialSparksStrategy;
import duelistmod.persistence.DataDifferenceDTO.SerializableDataDifferenceDTO;
import duelistmod.persistence.data.CardPoolSettings;
import duelistmod.persistence.data.ColorlessShopSettings;
import duelistmod.persistence.data.DeckUnlockSettings;
import duelistmod.persistence.data.GeneralSettings;
import duelistmod.persistence.data.GameplaySettings;
import duelistmod.persistence.data.MetricsSettings;
import duelistmod.persistence.data.OrbConfigurations;
import duelistmod.persistence.data.PotionConfigurations;
import duelistmod.persistence.data.RandomizedSettings;
import duelistmod.persistence.data.RelicConfigurations;
import duelistmod.persistence.data.VisualSettings;
import duelistmod.ui.configMenu.pages.General;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import static duelistmod.enums.CardPoolType.DECK_BASIC_DEFAULT;

public class PersistentDuelistData {

    public GeneralSettings GeneralSettings;
    public GameplaySettings GameplaySettings;
    public CardPoolSettings CardPoolSettings;
    public DeckUnlockSettings DeckUnlockSettings;
    public VisualSettings VisualSettings;
    public RandomizedSettings RandomizedSettings;
    public ColorlessShopSettings ColorlessShopSettings;
    public RelicConfigurations RelicConfigurations;
    public PotionConfigurations PotionConfigurations;
    public OrbConfigurations OrbConfigurations;
    public MetricsSettings MetricsSettings;

    public List<String> highlightedNodes;

    public PersistentDuelistData() {
        this.GeneralSettings = new GeneralSettings();
        this.GameplaySettings = new GameplaySettings();
        this.CardPoolSettings = new CardPoolSettings();
        this.DeckUnlockSettings = new DeckUnlockSettings();
        this.VisualSettings = new VisualSettings();
        this.RandomizedSettings = new RandomizedSettings();
        this.ColorlessShopSettings = new ColorlessShopSettings();
        this.RelicConfigurations = new RelicConfigurations();
        this.PotionConfigurations = new PotionConfigurations();
        this.OrbConfigurations = new OrbConfigurations();
        this.MetricsSettings = new MetricsSettings();
        this.highlightedNodes = new ArrayList<>();
    }

    public PersistentDuelistData(PersistentDuelistData loaded) {
        this.GeneralSettings = new GeneralSettings(loaded.GeneralSettings);
        this.GameplaySettings = new GameplaySettings(loaded.GameplaySettings);
        this.CardPoolSettings = new CardPoolSettings(loaded.CardPoolSettings);
        this.DeckUnlockSettings = new DeckUnlockSettings(loaded.DeckUnlockSettings);
        this.VisualSettings = new VisualSettings(loaded.VisualSettings);
        this.RandomizedSettings = new RandomizedSettings(loaded.RandomizedSettings);
        this.ColorlessShopSettings = new ColorlessShopSettings(loaded.ColorlessShopSettings);
        this.RelicConfigurations = new RelicConfigurations(loaded.RelicConfigurations);
        this.PotionConfigurations = new PotionConfigurations(loaded.PotionConfigurations);
        this.OrbConfigurations = new OrbConfigurations(loaded.OrbConfigurations);
        this.MetricsSettings = new MetricsSettings(loaded.MetricsSettings);
        this.highlightedNodes = loaded.highlightedNodes;
    }

    public static PersistentDuelistData generateFromOldPropertiesFile() {
        try {
            PersistentDuelistData output = new PersistentDuelistData();
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
            config.load();

            int bday = config.getInt("birthdayDay");
            int counter = 0;
            String bdayStr = "---";
            for (String s : General.days) {
                if (counter == bday) {
                    bdayStr = s;
                }
                counter++;
            }
            output.GeneralSettings = new GeneralSettings(
                    getMonthFromIndex(config.getInt("birthdayMonth")),
                    bdayStr,
                    config.getBool("neverChangedBirthday")
            );

            String sparks = SpecialSparksStrategy.RANDOM.displayName();
            int sparksStrategy = config.getInt("sparksStrategy");
            if (sparksStrategy > -1) {
                sparks = SpecialSparksStrategy.menuMappingReverse.getOrDefault(sparksStrategy, SpecialSparksStrategy.RANDOM).displayName();
            }
            output.GameplaySettings = new GameplaySettings(
                    config.getBool("duelistMonsters"),
                    config.getBool("celebrateHolidays"),
                    config.getBool("allowDuelistEvents"),
                    config.getBool("unlockAllDecks"),
                    config.getBool("duelistCurses"),
                    config.getBool("isChallengeForceUnlocked"),
                    config.getBool("addOrbPotions"),
                    config.getBool("allowCardPoolRelics"),
                    config.getBool("quicktimeEventsAllowed"),
                    config.getBool("restrictSummoningZones"),
                    config.getBool("allowSpecialSparks"),
                    config.getBool("forceSpecialSparks"),
                    sparks,
                    config.getInt("bonusStartingOrbSlots")
            );

            String cardPoolType = DECK_BASIC_DEFAULT.getDisplay();
            int cardPool = config.getInt("cardPoolType");
            if (cardPool > -1) {
                cardPoolType = CardPoolType.menuMappingReverse.getOrDefault(cardPool, DECK_BASIC_DEFAULT).getDisplay();
            }
            output.CardPoolSettings = new CardPoolSettings(
                  config.getBool("allowBoosters"),
                  config.getBool("alwaysBoosters"),
                  config.getBool("removeCardRewards"),
                  config.getBool("baseGameCards"),
                  config.getBool("smallBasicSet"),
                  config.getBool("toonBtnBool"),
                  config.getBool("creatorBtnBool"),
                  config.getBool("exodiaBtnBool"),
                  config.getBool("ojamaBtnBool"),
                  config.getBool("isAllowStartingDeckCardsInPool"),
                  config.getBool("isRemoveDinosaursFromDragonPool"),
                  cardPoolType
            );

            int characterModelIndex = config.getInt("selectedCharacterModel");
            String characterModel = CharacterModel.ANIM_YUGI.getDisplayName();
            if (characterModelIndex > -1) {
                characterModel = CharacterModel.menuMappingReverse.getOrDefault(characterModelIndex, CharacterModel.ANIM_YUGI).getDisplayName();
            }

            output.VisualSettings = new VisualSettings(
                config.getBool("replaceCommonKeywordsWithIcon"),
                config.getBool("flipCardTags"),
                characterModel,
                (float) config.getInt("playerAnimationSpeed") / 10,
                (float) config.getInt("enemyAnimationSpeed") / 10
            );

            int deckUnlockIndex = config.getInt("deckUnlockRate");
            String deckUnlockRate = DeckUnlockRate.NORMAL.displayText();
            if (deckUnlockIndex > -1) {
                deckUnlockRate = DeckUnlockRate.menuMapping.getOrDefault(deckUnlockIndex, DeckUnlockRate.NORMAL).displayText();
            }
            output.DeckUnlockSettings = new DeckUnlockSettings(deckUnlockRate, config.getBool("hideUnlockAllDecksButton"));

            output.RandomizedSettings = new RandomizedSettings(
                    config.getBool("noCostChanges"),
                    config.getBool("onlyCostDecreases"),
                    config.getBool("noTributeChanges"),
                    config.getBool("onlyTributeDecreases"),
                    config.getBool("noSummonChanges"),
                    config.getBool("onlySummonIncreases"),
                    config.getBool("alwaysUpgrade"),
                    config.getBool("neverUpgrade"),
                    config.getBool("randomizeEthereal"),
                    config.getBool("randomizeExhaust")
            );

            output.MetricsSettings = new MetricsSettings(
                    config.getBool("tierScoresEnabled"),
                    config.getBool("webButtons)"),
                    config.getBool("allowLocaleUpload"),
                    config.getBool("logMetricsScoresToDevConsole")
            );

            String relicConfigMapJSON = config.getString("relicCanSpawnConfigMap");
            if (!relicConfigMapJSON.equals("")) {
                output.RelicConfigurations.setRelicConfigurations(new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .readValue(relicConfigMapJSON, new TypeReference<HashMap<String, RelicConfigData>>(){}));
                output.RelicConfigurations = new RelicConfigurations(output.RelicConfigurations,
                        config.getBool("disableAllCommonRelics"),
                        config.getBool("disableAllUncommonRelics"),
                        config.getBool("disableAllRareRelics"),
                        config.getBool("disableAllBossRelics"),
                        config.getBool("disableAllShopRelics"));
            }

            String potConfigMapJSON = config.getString("potionCanSpawnConfigMap");
            if (!potConfigMapJSON.equals("")) {
                output.PotionConfigurations.setPotionConfigurations(new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .readValue(potConfigMapJSON, new TypeReference<HashMap<String, PotionConfigData>>(){}));
                output.PotionConfigurations = new PotionConfigurations(output.PotionConfigurations,
                        config.getBool("disableAllCommonPotions"),
                        config.getBool("disableAllUncommonPotions"),
                        config.getBool("disableAllRarePotions"));
            }

            String orbConfigMapJSON = config.getString("orbConfigSettingsMap");
            if (!orbConfigMapJSON.equals("")) {
                output.OrbConfigurations.setOrbConfigurations(new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .readValue(orbConfigMapJSON, new TypeReference<HashMap<String, OrbConfigData>>(){}));
                output.OrbConfigurations = new OrbConfigurations(output.OrbConfigurations,
                        config.getBool("disableAllOrbPassives"),
                        config.getBool("disableAllOrbEvokes"));
            }


            int leftSource = config.getInt("colorlessShopLeftSlotSource");
            String colorlessShopLeftSlotSource = ColorlessShopSource.BASIC_COLORLESS.display();
            if (leftSource > -1) {
                colorlessShopLeftSlotSource = ColorlessShopSource.menuMappingReverse.getOrDefault(leftSource, ColorlessShopSource.BASIC_COLORLESS).display();
            }

            int leftLowRarity = config.getInt("colorlessShopLeftSlotLowRarity");
            String colorlessShopLeftSlotLowRarity = MenuCardRarity.COMMON.display();
            if (leftLowRarity > -1) {
                colorlessShopLeftSlotLowRarity = MenuCardRarity.menuMappingReverse.getOrDefault(leftLowRarity, MenuCardRarity.COMMON).display();
            }

            int leftHighRarity = config.getInt("colorlessShopLeftSlotHighRarity");
            String colorlessShopLeftSlotHighRarity = MenuCardRarity.UNCOMMON.display();
            if (leftHighRarity > -1) {
                colorlessShopLeftSlotHighRarity = MenuCardRarity.menuMappingReverse.getOrDefault(leftHighRarity, MenuCardRarity.UNCOMMON).display();
            }

            int rightSource = config.getInt("colorlessShopRightSlotSource");
            String colorlessShopRightSlotSource = ColorlessShopSource.BASIC_COLORLESS.display();
            if (rightSource > -1) {
                colorlessShopRightSlotSource = ColorlessShopSource.menuMappingReverse.getOrDefault(rightSource, ColorlessShopSource.BASIC_COLORLESS).display();
            }

            int rightLowRarity = config.getInt("colorlessShopRightSlotLowRarity");
            String colorlessShopRightSlotLowRarity = MenuCardRarity.RARE.display();
            if (rightLowRarity > -1) {
                colorlessShopRightSlotLowRarity = MenuCardRarity.menuMappingReverse.getOrDefault(rightLowRarity, MenuCardRarity.RARE).display();
            }

            int rightHighRarity = config.getInt("colorlessShopRightSlotHighRarity");
            String colorlessShopRightSlotHighRarity = MenuCardRarity.RARE.display();
            if (rightHighRarity > -1) {
                colorlessShopRightSlotHighRarity = MenuCardRarity.menuMappingReverse.getOrDefault(rightHighRarity, MenuCardRarity.RARE).display();
            }

            output.ColorlessShopSettings = new ColorlessShopSettings(
                    colorlessShopLeftSlotSource,
                    colorlessShopRightSlotSource,
                    colorlessShopLeftSlotLowRarity,
                    colorlessShopLeftSlotHighRarity,
                    colorlessShopRightSlotLowRarity,
                    colorlessShopRightSlotHighRarity
            );

            return output;
        } catch (Exception ignored) {
            return null;
        }
    }

    public LinkedHashSet<SerializableDataDifferenceDTO> generateMetricsDifferences() {
        PersistentDuelistData defaultSettings = new PersistentDuelistData();
        PersistentDuelistData playerSettings = DuelistMod.persistentDuelistData;
        LinkedHashSet<SerializableDataDifferenceDTO> output = new LinkedHashSet<>();
        output.addAll(DataDifferenceDTO.serialize(this.GeneralSettings.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.GameplaySettings.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.CardPoolSettings.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.DeckUnlockSettings.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.VisualSettings.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.RandomizedSettings.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.RelicConfigurations.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.PotionConfigurations.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.OrbConfigurations.generateMetricsDifferences(defaultSettings, playerSettings)));
        output.addAll(DataDifferenceDTO.serialize(this.ColorlessShopSettings.generateMetricsDifferences(defaultSettings, playerSettings)));
        return output;
    }

    public static boolean validateBool(Boolean in) {
        return validateBool(in, true);
    }

    public static boolean validateBool(Boolean in, boolean fallback) {
        return in == null ? fallback : in;
    }

    public static <T> T validate(T in, T fallback, LinkedHashSet<String> options) {
        if (options != null) {
            return in != null && options.contains(in.toString()) ? in : fallback;
        }
        return nullCheck(in, fallback);
    }

    public static <T> T validate(T in, T fallback, LinkedHashMap<String, String> options) {
        if (options != null) {
            return in != null && options.containsKey(in.toString()) ? in : fallback;
        }
        return nullCheck(in, fallback);
    }

    public static <T> T nullCheck(T in, T fallback) {
        return in != null ? in : fallback;
    }

    private static String getMonthFromIndex(int index) {
        int counter = 0;
        for (String month : General.months) {
            if (counter == index) {
                return month;
            }
            counter++;
        }
        return "---";
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
