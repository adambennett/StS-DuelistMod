package duelistmod.patches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;

import duelistmod.DuelistMod;


@SpirePatch(
		clz = ShopScreen.class,
		method = "rollRelicTier"
		)

public class ShopRelicPatch 
{
	public static AbstractRelic.RelicTier Replace()
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST) && DuelistMod.hasShopBuffRelic)
		{
			int roll = AbstractDungeon.merchantRng.random(99);
			if (roll < 10) {
				return AbstractRelic.RelicTier.COMMON;
			}
			if (roll < 50) {
				return AbstractRelic.RelicTier.UNCOMMON;
			}

			return AbstractRelic.RelicTier.RARE;
		}
		else
		{
			int roll = AbstractDungeon.merchantRng.random(99);
			if (roll < 48) {
				return AbstractRelic.RelicTier.COMMON;
			}
			if (roll < 82) {
				return AbstractRelic.RelicTier.UNCOMMON;
			}

			return AbstractRelic.RelicTier.RARE;
		}
	}
}

