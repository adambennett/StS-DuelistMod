package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import duelistmod.helpers.customConsole.CustomConsoleCommandHelper;

import java.util.ArrayList;

public class Channel extends ConsoleCommand {

    public Channel() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 2;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        String orb = tokens.length > 1 ? tokens[1] : "Random!";

        String amt = tokens.length > 2 ? tokens[2] : "";
        int amount = ConvertHelper.tryParseInt(amt, 1);

        CustomConsoleCommandHelper.channel(orb, amount);

    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = CustomConsoleCommandHelper.getOrbNames();

        if (tokens.length > depth + 1 && result.contains(tokens[depth])) {
            if (tokens[depth + 1].matches("\\d+")) {
                complete = true;
            }
            result = smallNumbers();
        }
        return result;
    }

}
