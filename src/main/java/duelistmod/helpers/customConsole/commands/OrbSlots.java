package duelistmod.helpers.customConsole.commands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class OrbSlots extends ConsoleCommand {

    public OrbSlots() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 1;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        String amt = tokens.length > 1 ? tokens[1] : "";
        int amount = ConvertHelper.tryParseInt(amt, 7);

        if (amount > 0) {
            AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(amount));
            DevConsole.visible = false;
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return smallNumbers();
    }

}
