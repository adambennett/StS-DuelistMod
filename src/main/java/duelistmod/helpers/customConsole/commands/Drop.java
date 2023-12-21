package duelistmod.helpers.customConsole.commands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rewards.RewardItem;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.BoosterHelper;
import duelistmod.rewards.BoosterPack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Drop extends ConsoleCommand {

    public Drop() {
        requiresPlayer = true;
        minExtraTokens = 1;
        maxExtraTokens = 2;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            DevConsole.log("Can only drop rewards during a reward screen");
            return;
        }
        String type = tokens.length > 1 ? tokens[1] : null;
        String value = tokens.length > 2 ? tokens[2] : null;
        if (type == null) {
            DevConsole.log("Invalid input.");
            return;
        }
        if (!type.toLowerCase().trim().equals("clear") && value == null) {
            DevConsole.log("Invalid input.");
            return;
        }

        boolean isAmountValue = type.equalsIgnoreCase("card_reward") || type.equalsIgnoreCase("gold");
        Integer amt = null;
        if (isAmountValue && value != null) {
            try {
                amt = Integer.parseInt(value);
            } catch (Exception ex) {
                amt = 1;
            }
        }
        if (amt == null) {
            amt = 1;
        }

        List<RewardItem> rewards = new ArrayList<>();
        List<Object> matches;
        switch (type.toLowerCase().trim()) {
            case "card_reward":
                for (int i = 0; i < amt; i++) {
                    rewards.add(new RewardItem());
                }
                break;
            case "pack":
                BoosterPack reward = BoosterHelper.generateSpecificPackFromPool(value);
                if (reward != null) {
                    DuelistMod.onReceiveBoosterPack(reward);
                    rewards.add(reward);
                }
                break;
            case "relic":
                matches = DuelistMod.allDuelistRelics.stream().filter(r -> r.relicId.equalsIgnoreCase(value)).collect(Collectors.toList());
                if (!matches.isEmpty()) {
                    DuelistRelic match = (DuelistRelic) matches.get(0);
                    rewards.add(new RewardItem(match));
                }
                break;
            case "potion":
                matches = DuelistMod.allDuelistPotions.stream().filter(p -> p.ID.equalsIgnoreCase(value)).collect(Collectors.toList());
                if (!matches.isEmpty()) {
                    AbstractPotion match = (AbstractPotion) matches.get(0);
                    rewards.add(new RewardItem(match));
                }
                break;
            case "gold":
                rewards.add(new RewardItem(amt));
                break;
            case "clear":
                AbstractDungeon.combatRewardScreen.rewards.clear();
                return;
        }

        if (!rewards.isEmpty()) {
            AbstractDungeon.combatRewardScreen.rewards.addAll(rewards);
            AbstractDungeon.combatRewardScreen.positionRewards();
        } else {
            DevConsole.log("Could not generate rewards");
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = new ArrayList<>();
        options.add("card_reward");
        options.add("pack");
        options.add("relic");
        options.add("potion");
        options.add("gold");
        options.add("clear");
        if (tokens.length == 1 || (tokens.length == 2 && !options.contains(tokens[1].trim()))) {
            return options;
        } else if (tokens.length < 4) {
            String type = tokens[1];
            if (type.toLowerCase().trim().equalsIgnoreCase("clear")) {
                complete = true;
                return new ArrayList<>();
            }
            boolean isAmountValue = type.equalsIgnoreCase("card_reward") || type.equalsIgnoreCase("gold");
            if (isAmountValue) {
                return smallNumbers();
            }
            options.clear();
            switch (type.toLowerCase().trim()) {
                case "pack":
                    if (BoosterHelper.packPool != null) {
                        HashSet<String> noDupes = new HashSet<>();
                        for (BoosterPack pack : BoosterHelper.packPool) {
                            noDupes.add(pack.packName.replaceAll(" ", "") + "-" + pack.rarity.toString().replaceAll(" ", ""));
                        }
                        options.addAll(noDupes);
                    }
                    if (options.isEmpty()) {
                        options.add("No packs available");
                    }
                    break;
                case "potion":
                    for (AbstractPotion pot : DuelistMod.allDuelistPotions) {
                        options.add(pot.ID);
                    }
                    break;
                case "relic":
                    for (DuelistRelic relic : DuelistMod.allDuelistRelics) {
                        options.add(relic.relicId);
                    }
                    break;
            }
            return options;
        }
        complete = true;
        return new ArrayList<>();
    }
}
