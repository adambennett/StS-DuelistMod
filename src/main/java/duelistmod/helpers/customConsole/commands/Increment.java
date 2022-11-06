package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import duelistmod.abstracts.DuelistCard;

import java.util.ArrayList;

public class Increment extends ConsoleCommand {

    public Increment() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 1;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        String amt = tokens.length > 1 ? tokens[1] : "null";
        int amount = ConvertHelper.tryParseInt(amt, 1);

        DuelistCard.incMaxSummons(amount);

    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        result.add("-1");
        for(int i = 1; i <= 100; ++i) {
            result.add(String.valueOf(i));
        }

        isNumber = true;
        return result;
    }
}
