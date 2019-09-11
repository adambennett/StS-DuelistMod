package duelistmod.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

import basemod.ReflectionHacks;
import duelistmod.abstracts.DuelistCard;

public class PuzzleCardGlowEffect {
	@SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez={ AbstractCard.class})
	public static class CardGlowPatch {
		public static void Postfix(CardGlowBorder __instance, AbstractCard c) 
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dc = (DuelistCard)c;
				if (dc.fiendDeckDmgMod)
				{
					if (dc.aquaDeckEffect)
					{
						Color color = Color.NAVY.cpy();
						ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "color", color);
					}
					else
					{
						Color color = Color.RED.cpy();
						ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "color", color);
					}
				}
			}
		}
	}
}