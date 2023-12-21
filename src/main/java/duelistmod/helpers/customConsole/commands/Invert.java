package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.customConsole.CustomConsoleCommandHelper;

import java.util.ArrayList;

public class Invert extends ConsoleCommand {

    public Invert() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 2;
    }

    @Override
    protected void execute(String[] tokens, int depth) {


        String amt = tokens.length > 1 ? tokens[1] : "";
        int amount = ConvertHelper.tryParseInt(amt, 1);

        String amt2 = tokens.length > 2 ? tokens[2] : "";
        int amount2 = ConvertHelper.tryParseInt(amt2, 0);

        if (amount2 > 0) {
            DuelistCard.invertMult(amount2, amount);
        } else {
            DuelistCard.invert(amount);
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return smallNumbers();
    }

}
