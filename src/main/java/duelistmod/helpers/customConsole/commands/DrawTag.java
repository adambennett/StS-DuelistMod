package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class DrawTag extends ConsoleCommand {

    public DrawTag() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 2;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        Integer cardsToDraw = null;
        String rarityName = null;
        String amtCardCheck = tokens.length > 1 ? tokens[1] : null;
        String cardAmtCheck = tokens.length > 2 ? tokens[2] : null;
        if (amtCardCheck != null) {
            try {
               cardsToDraw = Integer.parseInt(amtCardCheck);
               rarityName = cardAmtCheck != null ? cardAmtCheck : "Monster";
               try { Integer.parseInt(rarityName); return; } catch (NumberFormatException ignored) {}
            } catch (NumberFormatException ignore) {
                rarityName = amtCardCheck;
            }
        }
        if (cardAmtCheck != null && cardsToDraw == null) {
            try {
                cardsToDraw = Integer.parseInt(cardAmtCheck);
                rarityName = amtCardCheck != null ? amtCardCheck : "Monster";
                try { Integer.parseInt(rarityName); return; } catch (NumberFormatException ignored) {}
            } catch (NumberFormatException ignore) {}
        }

        if (cardsToDraw == null || cardsToDraw <= 0) {
            cardsToDraw = 1;
        }

        if (rarityName == null) {
            rarityName = "Monster";
        }

        CardTags tag = Tags.MONSTER;
        for (CardTags tg : CardTags.values()) {
            if (tg.name().trim().equalsIgnoreCase(rarityName.trim()) || tg.toString().trim().equalsIgnoreCase(rarityName.trim())) {
                tag = tg;
                break;
            }
        }
        AnyDuelist.from(AbstractDungeon.player).drawTag(cardsToDraw, tag);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = new ArrayList<>();
        for (CardTags rarity : CardTags.values()) {
            options.add(rarity.toString().trim().toLowerCase());
        }
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
        } else if(tokens.length > depth + 1) { // CardTag is not correct, but you're typing more parameters???
            tooManyTokensError();
        }
        return options;
    }
}
