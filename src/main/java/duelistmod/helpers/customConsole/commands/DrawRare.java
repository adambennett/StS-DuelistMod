package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.dto.AnyDuelist;

import java.util.ArrayList;

public class DrawRare extends ConsoleCommand {

    public DrawRare() {
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
               rarityName = cardAmtCheck != null ? cardAmtCheck : "Common";
               try { Integer.parseInt(rarityName); return; } catch (NumberFormatException ignored) {}
            } catch (NumberFormatException ignore) {
                rarityName = amtCardCheck;
            }
        }
        if (cardAmtCheck != null && cardsToDraw == null) {
            try {
                cardsToDraw = Integer.parseInt(cardAmtCheck);
                rarityName = amtCardCheck != null ? amtCardCheck : "Common";
                try { Integer.parseInt(rarityName); return; } catch (NumberFormatException ignored) {}
            } catch (NumberFormatException ignore) {}
        }

        if (cardsToDraw == null || cardsToDraw <= 0) {
            cardsToDraw = 1;
        }

        if (rarityName == null) {
            rarityName = "Common";
        }

        CardRarity rarity = CardRarity.COMMON;
        for (CardRarity rar : CardRarity.values()) {
            if (rar.name().trim().equalsIgnoreCase(rarityName.trim()) || rar.toString().trim().equalsIgnoreCase(rarityName.trim())) {
                rarity = rar;
                break;
            }
        }
        AnyDuelist.from(AbstractDungeon.player).drawRare(cardsToDraw, rarity);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = new ArrayList<>();
        for (CardRarity rarity : CardRarity.values()) {
            options.add(rarity.name().trim().toLowerCase());
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
        } else if(tokens.length > depth + 1) { // Card Rarity is not correct, but you're typing more parameters???
            tooManyTokensError();
        }
        return options;
    }
}
