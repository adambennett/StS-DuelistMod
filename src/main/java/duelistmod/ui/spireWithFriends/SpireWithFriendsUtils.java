package duelistmod.ui.spireWithFriends;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import duelistmod.patches.CharacterSelectScreenPatch;
import duelistmod.patches.SpireWithFriendsPatches;

public class SpireWithFriendsUtils {

    public static Hitbox startingCardsSelectedHb;
    public static Hitbox startingCardsLeftHb;
    public static Hitbox startingCardsRightHb;

    public static void constructor() {
        startingCardsSelectedHb = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
        startingCardsLeftHb = new Hitbox(70.0f * Settings.scale, 70.0f * Settings.scale);
        startingCardsRightHb = new Hitbox(70.0f * Settings.scale, 70.0f * Settings.scale);
        startingCardsLeftHb.move((-122 * Settings.scale) + SpireWithFriendsPatches.TOGGLE_X_RIGHT + startingCardsSelectedHb.width * 1.5f, Settings.HEIGHT * 0.51025F);
        startingCardsSelectedHb.move((85 * Settings.scale) + SpireWithFriendsPatches.TOGGLE_X_RIGHT, Settings.HEIGHT * 0.51725F);
        startingCardsRightHb.move((168 * Settings.scale) + SpireWithFriendsPatches.TOGGLE_X_RIGHT + startingCardsSelectedHb.width * 1.5f, Settings.HEIGHT * 0.51025F);
    }

    public static void update() {
        startingCardsSelectedHb.update();
        startingCardsLeftHb.update();
        startingCardsRightHb.update();

        if (startingCardsRightHb.hovered) {
            startingCardsRightHb.clickStarted = true;
        } else if (startingCardsLeftHb.hovered) {
            startingCardsLeftHb.clickStarted = true;
        }

        if (startingCardsLeftHb.clicked) {
            CharacterSelectScreenPatch.leftClickStartingDeck(startingCardsLeftHb, null);
        } else if (startingCardsRightHb.clicked) {
            CharacterSelectScreenPatch.rightClickStartingDeck(startingCardsRightHb, null);
        } else if (startingCardsSelectedHb.hovered) {
            TipHelper.renderGenericTip(startingCardsSelectedHb.cX * 1.03f, startingCardsSelectedHb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, "DuelistMod Deck", "Select a starting deck to use for The Duelist.");
        }
    }

    public static void render(SpriteBatch sb) {
        startingCardsSelectedHb.render(sb);
        startingCardsLeftHb.render(sb);
        startingCardsRightHb.render(sb);
    }

    public static String getDeckDisplayName(String name) {
        if (name.contains("Random Deck")) {
            if (name.contains("Small")) {
                return "Random Deck S";
            } else {
                return "Random Deck B";
            }
        }
        return name;
    }

}
