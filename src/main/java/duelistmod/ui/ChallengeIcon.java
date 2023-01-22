package duelistmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import basemod.TopPanelItem;
import com.megacrit.cardcrawl.localization.*;
import duelistmod.DuelistMod;
import duelistmod.enums.ConfigOpenSource;
import duelistmod.helpers.*;

import java.util.*;

public class ChallengeIcon extends TopPanelItem {
    private static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString("theDuelist:ChallengeModeText");
    private static final Texture IMG = new Texture(DuelistMod.makeIconPath("PuzzleIcon.png"));
    private static final String[] DESCRIPTIONS = UIStrings.TEXT;
    public static final String ID = "theDuelist:ChallengeIcon";
    public static String header;
    public static String body;

    public ChallengeIcon() { super(IMG, ID); }

    @Override
    public void render(SpriteBatch sb, Color color) {
        if (Util.canOpenModSettings(ConfigOpenSource.MID_RUN)) {
            super.render(sb, color);
        }
    }

    @Override
    protected void renderHitbox(SpriteBatch sb) {
        if (Util.canOpenModSettings(ConfigOpenSource.MID_RUN)) {
            super.renderHitbox(sb);
        }
    }

    @Override
    public boolean isClickable() {
        return Util.canOpenModSettings(ConfigOpenSource.MID_RUN) && super.isClickable();
    }

    @Override
    protected void onClick() {
        Util.openModSettings(ConfigOpenSource.MID_RUN);
    }
    
    @Override
    protected void onHover() {
        if (Util.canOpenModSettings(ConfigOpenSource.MID_RUN)) {
            super.onHover();
            TipHelper.renderGenericTip((float) InputHelper.mX - 300.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale, "DuelistMod", "Modify configuration settings. NL Some settings will not be applied until after your run is completed.");
        }
    }
    
    @Override
    protected void onUnhover() {
    	super.onUnhover();
    }

    private String getBody(int level) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < level + 1; i++) {
            output.append(i).append(": ").append(((i == 4) ? getC4String() : DESCRIPTIONS[i+2])).append("\n");
        }
        return output.toString();
    }

    private String getC4String() {
        StringBuilder result = new StringBuilder();
        ArrayList<String> results = new ArrayList<>();
        int counter = 0;
        if (Util.deckIs("Standard Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Dragon Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Naturia Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Spellcaster Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Toon Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Zombie Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Aqua Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Fiend Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Machine Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Warrior Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Insect Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Plant Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Megatype Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Increment Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Creator Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Exodia Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Ojama Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Giant Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Ascended I")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Ascended II")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Ascended III")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Pharaoh I")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Pharaoh II")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Pharaoh III")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Pharaoh IV")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Pharaoh V")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Random Deck (Small)")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Random Deck (Big)")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Upgrade Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
        if (Util.deckIs("Metronome Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;

        if (results.size() == 1) { return results.get(0); }
        else
        {
            for (int i = 0; i < results.size(); i++)
            {
                if (i + 1 >= results.size()) { result.append(results.get(i)); }
                else { result.append(results.get(i)).append(" NL NL "); }
            }
            return result.toString();
        }
    }
}
