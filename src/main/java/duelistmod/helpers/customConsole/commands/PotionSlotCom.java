package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import duelistmod.helpers.customConsole.CustomConsoleCommandHelper;

import java.util.ArrayList;

public class PotionSlotCom extends ConsoleCommand {

    public PotionSlotCom() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 1;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        String amt = tokens.length > 1 ? tokens[1] : "null";
        int amount = ConvertHelper.tryParseInt(amt, 1);
        CustomConsoleCommandHelper.gainPotionSlots(amount);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return smallNumbers();
    }
}
