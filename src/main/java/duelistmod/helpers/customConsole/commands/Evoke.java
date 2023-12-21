package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.customConsole.CustomConsoleCommandHelper;

import java.util.ArrayList;

public class Evoke extends ConsoleCommand {

    public Evoke() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 2;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        String orbsToEvokeToken = tokens.length > 1 ? tokens[1] : "";
        String numberOfEvokesToken = tokens.length > 2 ? tokens[2] : "";

        int orbsToEvoke = ConvertHelper.tryParseInt(orbsToEvokeToken, 1);
        int numberOfEvokes = ConvertHelper.tryParseInt(numberOfEvokesToken, 1);

        boolean all = orbsToEvokeToken.equalsIgnoreCase("all");

        if (all && numberOfEvokes == 1) {
            DuelistCard.evokeAll();
            return;
        }

        if (all) {
            int activeOrbs = 0;
            if (AbstractDungeon.player != null && AbstractDungeon.player.orbs != null) {
                for (AbstractOrb o : AbstractDungeon.player.orbs) {
                    if (!(o instanceof EmptyOrbSlot)) {
                        activeOrbs++;
                    }
                }
            }
            orbsToEvoke = activeOrbs;
        }

        if (orbsToEvoke > 0) {
            if (numberOfEvokes > 1) {
                for (int i = 0; i < orbsToEvoke; i++) {
                    DuelistCard.evokeMult(numberOfEvokes);
                }
            } else if (numberOfEvokes == 1) {
                DuelistCard.evoke(orbsToEvoke);
            }
        }
    }


    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        result.add("All");

        int orbs = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.orbs != null) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (!(o instanceof EmptyOrbSlot)) {
                    orbs++;
                }
            }
        }
        if (orbs > 0) {
            for (int i = 1; i <= orbs; i++) {
                result.add(String.valueOf(i));
            }
        }

        return result;
    }

}
