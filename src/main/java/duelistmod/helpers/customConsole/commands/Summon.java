package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

import java.util.ArrayList;

public class Summon extends ConsoleCommand {

    public Summon() {
        requiresPlayer = true;
        minExtraTokens = 2;
        maxExtraTokens = 2;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        String amt = tokens.length > 2 ? tokens[2] : "null";
        int amount = ConvertHelper.tryParseInt(amt, 1);

        String cardId = tokens.length > 1 ? tokens[1] : "Token";

        DuelistCard card = DuelistMod.cardIdMap.getOrDefault(cardId, null);
        if (card != null) {
            DuelistCard copy = (DuelistCard)card.makeCopy();
            DuelistCard.summon(AbstractDungeon.player, amount, copy);
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = DuelistMod.summonCardNames;
        if(options.contains(tokens[depth])) { // Input cardID is correct
            if(tokens.length > depth + 1 && tokens[depth + 1].matches("\\d*")) {
                if(tokens.length > depth + 2) {
                    if(tokens[depth + 2].matches("\\d+")) {
                        complete = true;
                    } else if(tokens[depth + 2].length() > 0) {
                        tooManyTokensError();
                    }
                }
                return smallNumbers();
            } else if(tokens.length > depth + 1) {
                tooManyTokensError();
            }
        } else if(tokens.length > depth + 1) { // CardID is not correct, but you're typing more parameters???
            tooManyTokensError();
        }
        return options;
    }
}
