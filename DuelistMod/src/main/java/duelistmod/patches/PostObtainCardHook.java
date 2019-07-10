package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.*;
import duelistmod.modules.VaseMod;

public class PostObtainCardHook 
{
	@SpirePatch(clz = SoulGroup.class, method = "obtain")
	public static class SoulGroupObtainPatch 
	{
		public static void Postfix(SoulGroup __instance, AbstractCard card, boolean obtainCard) 
		{
			VaseMod.publishPostObtainCard(card);
		}
	}
}
