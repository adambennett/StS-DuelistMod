package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;
import duelistmod.patches.CharacterSelectScreenPatch;

import java.util.ArrayList;

public class DuelistScore extends ConsoleCommand {

    public DuelistScore() {
        requiresPlayer = false;
        minExtraTokens = 0;
        maxExtraTokens = 1;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        String amt = tokens.length > 1 ? tokens[1] : "null";
        int amount = ConvertHelper.tryParseInt(amt, 1);
        boolean result = Util.addDuelistScore(amount, false);
        if (result && CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CHAR_SELECT && DuelistMod.characterSelectScreen != null) {
            CharacterSelectScreenPatch.RefreshLoadout(DuelistMod.characterSelectScreen);
        }
    }


    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return smallNumbers();
    }
}
