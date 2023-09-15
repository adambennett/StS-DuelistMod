package duelistmod.persistence;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.google.gson.Gson;
import duelistmod.DuelistMod;
import duelistmod.enums.CardPoolType;
import duelistmod.enums.CharacterModel;
import duelistmod.enums.DeckUnlockRate;
import duelistmod.enums.SpecialSparksStrategy;
import duelistmod.persistence.DataDifferenceDTO.SerializableDataDifferenceDTO;
import duelistmod.persistence.data.CardPoolSettings;
import duelistmod.persistence.data.DeckUnlockSettings;
import duelistmod.persistence.data.GeneralSettings;
import duelistmod.persistence.data.GameplaySettings;
import duelistmod.persistence.data.VisualSettings;
import duelistmod.ui.configMenu.pages.General;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import static duelistmod.enums.CardPoolType.DECK_BASIC_DEFAULT;

public class PersistentDuelistData {

    public GeneralSettings GeneralSettings;
    public GameplaySettings GameplaySettings;
    public CardPoolSettings CardPoolSettings;
    public DeckUnlockSettings DeckUnlockSettings;
    public VisualSettings VisualSettings;

    public PersistentDuelistData() {
        this.GeneralSettings = new GeneralSettings();
        this.GameplaySettings = new GameplaySettings();
        this.CardPoolSettings = new CardPoolSettings();
        this.DeckUnlockSettings = new DeckUnlockSettings();
        this.VisualSettings = new VisualSettings();
    }

    public PersistentDuelistData(PersistentDuelistData loaded) {
        this.GeneralSettings = new GeneralSettings(loaded.GeneralSettings);
        this.GameplaySettings = new GameplaySettings(loaded.GameplaySettings);
        this.CardPoolSettings = new CardPoolSettings(loaded.CardPoolSettings);
        this.DeckUnlockSettings = new DeckUnlockSettings(loaded.DeckUnlockSettings);
        this.VisualSettings = new VisualSettings(loaded.VisualSettings);
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
