package duelistmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import duelistmod.DuelistMod;
import duelistmod.ui.CharacterSelectHelper;

//Copied from The Animator, then modified
public class CharacterSelectScreenPatches
{
    @SpirePatch(clz = CharacterSelectScreen.class, method = "initialize")
    public static class CharacterSelectScreenPatch_Initialize
    {
        @SpirePostfixPatch
        public static void Initialize(CharacterSelectScreen __instance)
        {
            CharacterSelectHelper.Initialize(__instance);
            DuelistMod.characterSelectScreen = __instance;
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class CharacterSelectScreenPatch_Render
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance, SpriteBatch sb)
        {
            CharacterSelectHelper.Render(__instance, sb);
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenPatch_Update
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance)
        {
            CharacterSelectHelper.Update(__instance);
            DuelistMod.characterSelectScreen = __instance;
        }
    }
}
