package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class Summon extends ConsoleCommand {

    public Summon() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 2;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        Integer tokenAmt = null;
        String tokenId = null;
        String amtCardCheck = tokens.length > 1 ? tokens[1] : null;
        String cardAmtCheck = tokens.length > 2 ? tokens[2] : null;
        if (amtCardCheck != null) {
            try {
               tokenAmt = Integer.parseInt(amtCardCheck);
               tokenId = cardAmtCheck != null ? cardAmtCheck : "theDuelist:Token";
               try { Integer.parseInt(tokenId); return; } catch (NumberFormatException ignored) {}
            } catch (NumberFormatException ignore) {
                tokenId = amtCardCheck;
            }
        }
        if (cardAmtCheck != null && tokenAmt == null) {
            try {
                tokenAmt = Integer.parseInt(cardAmtCheck);
                tokenId = amtCardCheck != null ? amtCardCheck : "theDuelist:Token";
                try { Integer.parseInt(tokenId); return; } catch (NumberFormatException ignored) {}
            } catch (NumberFormatException ignore) {}
        }

        if (tokenAmt == null || tokenAmt <= 0) {
            tokenAmt = 1;
        }

        if (tokenId == null) {
            tokenId = "theDuelist:Token";
        }

        DuelistCard card = DuelistMod.cardIdMap.getOrDefault(tokenId, null);
        if (card != null) {
            DuelistCard copy = (DuelistCard)card.makeCopy();
            if (copy.hasTag(Tags.MONSTER) || copy.hasTag(Tags.TOKEN)) {
                DuelistCard.summon(AbstractDungeon.player, tokenAmt, copy);
            } else {
                Util.log("Cannot summon non-Monster cards", true);
            }
        }
        Util.log("Executing summon command, amt=" + tokenAmt + ", cardId=" + tokenId + ", card=" + card);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = DuelistMod.monsterAndTokenCardNames;
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
