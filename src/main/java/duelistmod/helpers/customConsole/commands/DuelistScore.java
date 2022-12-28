package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import duelistmod.helpers.Util;

import java.util.ArrayList;

public class DuelistScore extends ConsoleCommand {

    public DuelistScore() {
        requiresPlayer = true;
        minExtraTokens = 1;
        maxExtraTokens = 1;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        String amt = tokens.length > 2 ? tokens[1] : "null";
        int amount = ConvertHelper.tryParseInt(amt, 1);
        Util.addDuelistScore(amount);
    }


    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return smallNumbers();
    }
}
