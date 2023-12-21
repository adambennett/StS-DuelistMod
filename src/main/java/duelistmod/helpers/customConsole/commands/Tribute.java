package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistCard;

import java.util.ArrayList;

public class Tribute extends ConsoleCommand {

    public Tribute() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 1;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        String amt = tokens.length > 1 ? tokens[1] : "null";
        boolean tributeAll = amt.equalsIgnoreCase("all");
        int amount = tributeAll ? 0 : ConvertHelper.tryParseInt(amt, 1);
        if (amount > 0 || tributeAll) {
            DuelistCard.powerTribute(AbstractDungeon.player, amount, tributeAll, true);
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        result.add("All");

        int summons = DuelistCard.getSummons(AbstractDungeon.player);
        if (summons > 0) {
            for (int i = 1; i <= summons; i++) {
                result.add(String.valueOf(i));
            }
        }

        return result;
    }
}
