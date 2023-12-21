package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.enums.ColorlessShopSource;
import duelistmod.enums.MenuCardRarity;
import duelistmod.helpers.BaseGameHelper;
import duelistmod.helpers.Util;
import duelistmod.persistence.data.ColorlessShopSettings;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ColorlessShop extends SpecificConfigMenuPage implements RefreshablePage {

    private boolean isRefreshing;

    public ColorlessShop() {
        super("Colorless Shop Settings", "Colorless Shop");
    }

    public ArrayList<IUIElement> getElements() {

        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabel("Left Slot", (DuelistMod.xLabPos), (DuelistMod.yPos + 15), DuelistMod.settingsPanel,(me)->{}));

        LINEBREAK(15);

        settingElements.add(new ModLabel("Source", DuelistMod.xLabPos, DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));

        ArrayList<String> leftSources = new ArrayList<>(ColorlessShopSource.displayNames);
        String tooltip = "Determines where the card in the bottom-left slot of the Merchant's shop comes from. Defaults to: NL #bBasic #bPool #band #bColorless #bPool.";
        DuelistDropdown leftSourceSelector = new DuelistDropdown(tooltip, leftSources, Settings.scale * (DuelistMod.xLabPos + 195),Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setLeftSlotSource(s);
            DuelistMod.configSettingsLoader.save();
        });
        leftSourceSelector.setSelectedIndex(ColorlessShopSource.menuMapping.get(DuelistMod.colorlessShopLeftSlotSource));

        LINEBREAK();

        settingElements.add(new ModLabel("Rarity", DuelistMod.xLabPos, DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));

        ArrayList<String> leftLowRarities = new ArrayList<>(MenuCardRarity.displayNames);
        tooltip = "The lowest rarity possible to drop in the bottom-left card slot in the Merchant's shop. Defaults to #bCommon.";
        DuelistDropdown leftLowRaritySelector = new DuelistDropdown(tooltip, leftLowRarities, Settings.scale * (DuelistMod.xLabPos + 195),Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setLeftSlotLowRarity(s);
            DuelistMod.configSettingsLoader.save();
        });
        leftLowRaritySelector.setSelectedIndex(MenuCardRarity.menuMapping.get(DuelistMod.colorlessShopLeftSlotLowRarity));

        ArrayList<String> leftHighRarities = new ArrayList<>(MenuCardRarity.displayNames);
        tooltip = "The highest rarity possible to drop in the bottom-left card slot in the Merchant's shop. Defaults to #bUncommon.";
        DuelistDropdown leftHighRaritySelector = new DuelistDropdown(tooltip, leftHighRarities, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol - 135 - 350), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setLeftSlotHighRarity(s);
            DuelistMod.configSettingsLoader.save();
        });
        leftHighRaritySelector.setSelectedIndex(MenuCardRarity.menuMapping.get(DuelistMod.colorlessShopLeftSlotHighRarity));

        LINEBREAK(60);

        settingElements.add(new ModLabel("Right Slot", (DuelistMod.xLabPos), (DuelistMod.yPos + 15), DuelistMod.settingsPanel,(me)->{}));

        LINEBREAK(15);

        settingElements.add(new ModLabel("Source", DuelistMod.xLabPos, DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));

        ArrayList<String> rightSources = new ArrayList<>(ColorlessShopSource.displayNames);
        tooltip = "Determines where the card in the bottom-right slot of the Merchant's shop comes from. Defaults to: NL #bBasic #bPool #band #bColorless #bPool.";
        DuelistDropdown rightSourceSelector = new DuelistDropdown(tooltip, rightSources, Settings.scale * (DuelistMod.xLabPos + 195),Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setRightSlotSource(s);
            DuelistMod.configSettingsLoader.save();
        });
        rightSourceSelector.setSelectedIndex(ColorlessShopSource.menuMapping.get(DuelistMod.colorlessShopRightSlotSource));

        LINEBREAK();

        settingElements.add(new ModLabel("Rarity", DuelistMod.xLabPos, DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));

        ArrayList<String> rightLowRarities = new ArrayList<>(MenuCardRarity.displayNames);
        tooltip = "The lowest rarity possible to drop in the bottom-right card slot in the Merchant's shop. Defaults to #bRare.";
        DuelistDropdown rightLowRaritySelector = new DuelistDropdown(tooltip, rightLowRarities, Settings.scale * (DuelistMod.xLabPos + 195),Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setRightSlotLowRarity(s);
            DuelistMod.configSettingsLoader.save();
        });
        rightLowRaritySelector.setSelectedIndex(MenuCardRarity.menuMapping.get(DuelistMod.colorlessShopRightSlotLowRarity));

        ArrayList<String> rightHighRarities = new ArrayList<>(MenuCardRarity.displayNames);
        tooltip = "The highest rarity possible to drop in the bottom-right card slot in the Merchant's shop. Defaults to #bRare.";
        DuelistDropdown rightHighRaritySelector = new DuelistDropdown(tooltip, rightHighRarities, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol - 135 - 350), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setRightSlotHighRarity(s);
            DuelistMod.configSettingsLoader.save();
        });
        rightHighRaritySelector.setSelectedIndex(MenuCardRarity.menuMapping.get(DuelistMod.colorlessShopRightSlotHighRarity));

        settingElements.add(rightHighRaritySelector);
        settingElements.add(rightLowRaritySelector);
        settingElements.add(rightSourceSelector);
        settingElements.add(leftHighRaritySelector);
        settingElements.add(leftLowRaritySelector);
        settingElements.add(leftSourceSelector);

        this.isRefreshing = false;
        return settingElements;
    }

    @FunctionalInterface
    private interface ListFilterer {
        List<AbstractCard> filter(ArrayList<AbstractCard> group, Predicate<AbstractCard> test);
    }

    @FunctionalInterface
    private interface Returner {
        AbstractCard check(List<AbstractCard> matches);
    }

    @FunctionalInterface
    private interface LoopChecker {
        AbstractCard check(ArrayList<AbstractCard> matches);
    }

    public static AbstractCard getCard(boolean leftSlot) {
        ColorlessShopSource source = leftSlot ? DuelistMod.colorlessShopLeftSlotSource : DuelistMod.colorlessShopRightSlotSource;
        RarityResult rarity = determineRarity(leftSlot);
        CardRarity cardRarityPrimary = convert(rarity.primary(), source);
        CardRarity cardRaritySecondary = convert(rarity.secondary(), source);
        CardRarity cardRarityTertiary = convert(rarity.tertiary(), source);
        Predicate<AbstractCard> fullTestPrimary = (c) -> (cardRarityPrimary != null && c.rarity == cardRarityPrimary) && source.test(c);
        Predicate<AbstractCard> fullTestSecondary = (c) -> (cardRaritySecondary != null && c.rarity == cardRaritySecondary) && source.test(c);
        Predicate<AbstractCard> fullTestTertiary = (c) -> (cardRarityTertiary != null && c.rarity == cardRarityTertiary) && source.test(c);
        Predicate<AbstractCard> anyRarityTest = (c) -> ((cardRarityPrimary != null && c.rarity == cardRarityPrimary) ||
                                                        (cardRaritySecondary != null && c.rarity == cardRaritySecondary) ||
                                                        (cardRarityTertiary != null && c.rarity == cardRarityTertiary)) && source.test(c);
        ListFilterer listFilterer = (l, t) -> l.stream().filter(t).collect(Collectors.toList());
        Returner returner = (m) -> {
            if (m.size() == 1) {
                return m.get(0);
            } else if (m.size() > 1) {
                CardGroup shuffleGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : m) {
                    shuffleGroup.addToBottom(c);
                }
                shuffleGroup.shuffle(AbstractDungeon.merchantRng);
                int randomIndex = AbstractDungeon.merchantRng.random(0, shuffleGroup.group.size() - 1);
                return shuffleGroup.group.get(randomIndex);
            }
            return null;
        };
        LoopChecker loopChecker = (m) -> {
            if (listFilterer.filter(m, anyRarityTest).size() < 5) {
                return null;
            }
            List<AbstractCard> matches = listFilterer.filter(m, fullTestPrimary);
            AbstractCard output = returner.check(matches);
            if (output != null) {
                return output.makeCopy();
            }
            if (cardRaritySecondary != null) {
                matches = listFilterer.filter(m, fullTestSecondary);
                output = returner.check(matches);
                if (output != null) {
                    return output.makeCopy();
                }
            }
            if (cardRarityTertiary != null) {
                matches = listFilterer.filter(m, fullTestTertiary);
                output = returner.check(matches);
                if (output != null) {
                    return output.makeCopy();
                }
            }
            return null;
        };

        AbstractCard output = loopChecker.check(source.startingList());
        if (output != null) {
            return output;
        }
        output = loopChecker.check(AbstractDungeon.colorlessCardPool.group);
        if (output != null) {
            return output;
        }
        output = loopChecker.check(TheDuelist.cardPool.group);
        if (output != null) {
            return output;
        }
        output = loopChecker.check(new ArrayList<>(DuelistMod.myCards));
        if (output != null) {
            return output;
        }
        output = loopChecker.check(BaseGameHelper.getAllBaseGameCards(true));
        if (output != null) {
            return output;
        }
        output = loopChecker.check(new ArrayList<>(DuelistCardLibrary.getTokensForCombat()));
        if (output != null) {
            return output;
        }
        output = loopChecker.check(CardLibrary.getAllCards());
        if (output != null) {
            return output;
        }

        Util.log("Did not generate colorless shop card via Duelist logic. Falling back to base game logic to find a card for sale.\n{\nSlot=" + (leftSlot ? "Left" : "Right" + ",\nSource=" + source.display() + ",\nRarity=" + rarity + "\n}"));
        output = AbstractDungeon.getColorlessCardFromPool(cardRarityPrimary != null && cardRarityPrimary != CardRarity.COMMON ? cardRarityPrimary : CardRarity.UNCOMMON);
        if (output != null) {
            return output.makeCopy();
        }
        return new Madness();
    }

    private static CardRarity convert(MenuCardRarity rarity, ColorlessShopSource source) {
        if (source.isSpecialRarityOnly()) return CardRarity.SPECIAL;
        if (rarity == null) return null;
        switch (rarity) {
            default: return CardRarity.COMMON;
            case UNCOMMON: return CardRarity.UNCOMMON;
            case RARE: return CardRarity.RARE;
        }
    }

    private static class RarityResult {
        private final MenuCardRarity primary;
        private final MenuCardRarity secondary;
        private final MenuCardRarity tertiary;
        public RarityResult(MenuCardRarity primary, MenuCardRarity secondary, MenuCardRarity tertiary) {
            this.primary = primary;
            this.secondary = secondary;
            this.tertiary = tertiary;
        }
        public RarityResult(MenuCardRarity primary, MenuCardRarity secondary) {
            this(primary, secondary, null);
        }
        public RarityResult(MenuCardRarity primary) {
            this(primary, null, null);
        }
        public MenuCardRarity primary() { return this.primary; }
        public MenuCardRarity secondary() { return this.secondary; }
        public MenuCardRarity tertiary() { return this.tertiary; }

        @Override
        public String toString() {
            return "RarityResult{" +
                    "primary=" + primary +
                    ", secondary=" + secondary +
                    ", tertiary=" + tertiary +
                    '}';
        }
    }

    private static RarityResult determineRarity(boolean leftSlot) {
        MenuCardRarity lowConfig = leftSlot ? DuelistMod.colorlessShopLeftSlotLowRarity : DuelistMod.colorlessShopRightSlotLowRarity;
        MenuCardRarity highConfig = leftSlot ? DuelistMod.colorlessShopLeftSlotHighRarity : DuelistMod.colorlessShopRightSlotHighRarity;

        if (lowConfig == MenuCardRarity.COMMON) {
            if (highConfig == MenuCardRarity.RARE) {
                if (rollBoolean()) {
                    return rollBoolean() ? new RarityResult(MenuCardRarity.RARE, MenuCardRarity.UNCOMMON, MenuCardRarity.COMMON) : new RarityResult(MenuCardRarity.UNCOMMON, MenuCardRarity.COMMON, MenuCardRarity.RARE);
                } else {
                    return rollBoolean() ? new RarityResult(MenuCardRarity.UNCOMMON, MenuCardRarity.COMMON, MenuCardRarity.RARE) : new RarityResult(MenuCardRarity.COMMON, MenuCardRarity.UNCOMMON, MenuCardRarity.RARE);
                }
            } else if (highConfig == MenuCardRarity.UNCOMMON) {
                return rollBoolean() ? new RarityResult(MenuCardRarity.UNCOMMON, MenuCardRarity.COMMON) : new RarityResult(MenuCardRarity.COMMON, MenuCardRarity.UNCOMMON);
            } else {
                return new RarityResult(MenuCardRarity.COMMON);
            }
        } else if (lowConfig == MenuCardRarity.UNCOMMON) {
            if (highConfig == MenuCardRarity.RARE) {
                return rollBoolean() ? new RarityResult(MenuCardRarity.RARE, MenuCardRarity.UNCOMMON) : new RarityResult(MenuCardRarity.UNCOMMON, MenuCardRarity.RARE);
            } else if (highConfig == MenuCardRarity.UNCOMMON) {
                return new RarityResult(MenuCardRarity.UNCOMMON);
            } else {
                return rollBoolean() ? new RarityResult(MenuCardRarity.UNCOMMON, MenuCardRarity.COMMON) : new RarityResult(MenuCardRarity.COMMON, MenuCardRarity.UNCOMMON);
            }
        } else {
            if (highConfig == MenuCardRarity.RARE) {
                return new RarityResult(MenuCardRarity.RARE);
            } else if (highConfig == MenuCardRarity.UNCOMMON) {
                return rollBoolean() ? new RarityResult(MenuCardRarity.RARE, MenuCardRarity.UNCOMMON) : new RarityResult(MenuCardRarity.UNCOMMON, MenuCardRarity.RARE);
            } else {
                if (rollBoolean()) {
                    return rollBoolean() ? new RarityResult(MenuCardRarity.RARE, MenuCardRarity.UNCOMMON, MenuCardRarity.COMMON) : new RarityResult(MenuCardRarity.UNCOMMON, MenuCardRarity.COMMON, MenuCardRarity.RARE);
                } else {
                    return rollBoolean() ? new RarityResult(MenuCardRarity.UNCOMMON, MenuCardRarity.COMMON, MenuCardRarity.RARE) : new RarityResult(MenuCardRarity.COMMON, MenuCardRarity.UNCOMMON, MenuCardRarity.RARE);
                }
            }
        }
    }

    private static boolean rollBoolean() {
        try {
            return AbstractDungeon.merchantRng.random() < AbstractDungeon.colorlessRareChance;
        } catch (Exception ex) {
            return ThreadLocalRandom.current().nextBoolean();
        }
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.ColorlessShopSettings = new ColorlessShopSettings();
    }

    @Override
    public void refresh() {
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);
        }
    }

    private ColorlessShopSettings settings() {
        return DuelistMod.persistentDuelistData.ColorlessShopSettings;
    }


}
