package duelistmod.patches;
import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;

import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import duelistmod.DuelistMod;
import duelistmod.enums.ColorlessShopSource;
import duelistmod.ui.configMenu.pages.ColorlessShop;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.*;


public class ShopScreenPatches {
	@SpirePatch(clz = ShopScreen.class,method = "rollRelicTier")
	public static class ShopRelicPatch {
		public static AbstractRelic.RelicTier Replace() {
            int roll = AbstractDungeon.merchantRng.random(99);
            if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST) && DuelistMod.hasShopBuffRelic) {
                return roll < 10 ? COMMON : roll < 50 ? UNCOMMON : RARE;
			} else {
                return roll < 48 ? COMMON : roll < 82 ? UNCOMMON : RARE;
			}
		}
	}

	@SpirePatch(clz = ShopScreen.class, method = "init")
	public static class TrackColorlessCardsPatch {

		public static void Prefix(ShopScreen __instance, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards) {
			if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
				ColorlessShopSource left = DuelistMod.colorlessShopLeftSlotSource;
				ColorlessShopSource right = DuelistMod.colorlessShopRightSlotSource;

				AbstractCard startingLeftCard = !colorlessCards.isEmpty() ? colorlessCards.get(0) : null;
				AbstractCard startingRightCard = colorlessCards.size() > 1 ? colorlessCards.get(1) : null;

				AbstractCard newLeftCard = startingLeftCard == null || !left.test(startingLeftCard) ? ColorlessShop.getCard(true) : startingLeftCard;
				AbstractCard newRightCard = startingRightCard == null || !right.test(startingRightCard) ? ColorlessShop.getCard(false) : startingRightCard;

				colorlessCards.clear();
				colorlessCards.add(newLeftCard);
				colorlessCards.add(newRightCard);
			}
		}

		public static void Postfix(ShopScreen __instance) {
			DuelistMod.colorlessShopCardUUIDs.clear();
			DuelistMod.colorlessShopSlotLeft = null;
			DuelistMod.colorlessShopSlotRight = null;
			for (AbstractCard c : __instance.colorlessCards) {
				DuelistMod.colorlessShopCardUUIDs.add(c.uuid);
				if (DuelistMod.colorlessShopSlotLeft == null) {
					DuelistMod.colorlessShopSlotLeft = c.uuid;
				} else if (DuelistMod.colorlessShopSlotRight == null) {
					DuelistMod.colorlessShopSlotRight = c.uuid;
				}
			}
		}
	}

	@SpirePatch(clz = ShopScreen.class, method = "purchaseCard")
	public static class PurchaseCardPrefixPatch {
		public static SpireReturn<Void> Prefix(ShopScreen __instance, AbstractCard hoveredCard) {
			if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST) &&
				AbstractDungeon.player.hasRelic("The Courier") &&
				AbstractDungeon.player.gold >= hoveredCard.price &&
				(hoveredCard.color == AbstractCard.CardColor.COLORLESS || DuelistMod.colorlessShopCardUUIDs.contains(hoveredCard.uuid))) {
					CardCrawlGame.metricData.addShopPurchaseData(hoveredCard.getMetricID());
					AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(hoveredCard, hoveredCard.current_x, hoveredCard.current_y));
					AbstractDungeon.player.loseGold(hoveredCard.price);
					CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1f);
					boolean leftSlot = hoveredCard.uuid.equals(DuelistMod.colorlessShopSlotLeft);
					final AbstractCard c = ColorlessShop.getCard(leftSlot);
					for (final AbstractRelic r : AbstractDungeon.player.relics) {
						r.onPreviewObtainCard(c);
					}
					c.current_x = hoveredCard.current_x;
					c.current_y = hoveredCard.current_y;
					c.target_x = c.current_x;
					c.target_y = c.current_y;
					setPrice(c);
					__instance.colorlessCards.set(__instance.colorlessCards.indexOf(hoveredCard), c);
					DuelistMod.colorlessShopCardUUIDs.remove(hoveredCard.uuid);
					DuelistMod.colorlessShopCardUUIDs.add(c.uuid);
					if (leftSlot) {
						DuelistMod.colorlessShopSlotLeft = c.uuid;
					} else {
						DuelistMod.colorlessShopSlotRight = c.uuid;
					}
					InputHelper.justClickedLeft = false;
					ReflectionHacks.setPrivate(__instance, ShopScreen.class, "notHoveredTimer", 1.0f);
					ReflectionHacks.setPrivate(__instance, ShopScreen.class, "speechTimer", MathUtils.random(40.0f, 60.0f));
					__instance.playBuySfx();
					__instance.createSpeech(ShopScreen.getBuyMsg());
					return SpireReturn.Return();
			} else {
				return SpireReturn.Continue();
			}
		}

		private static void setPrice(AbstractCard card) {
			float tmpPrice = AbstractCard.getPrice(card.rarity) * AbstractDungeon.merchantRng.random(0.9f, 1.1f);
			if (card.color == AbstractCard.CardColor.COLORLESS) {
				tmpPrice *= 1.2f;
			}
			if (AbstractDungeon.player.hasRelic("The Courier")) {
				tmpPrice *= 0.8f;
			}
			if (AbstractDungeon.player.hasRelic("Membership Card")) {
				tmpPrice *= 0.5f;
			}
			card.price = (int)tmpPrice;
		}
	}

	@SpirePatch(clz = AbstractCard.class, method = "getPrice")
	public static class GetPricePatches {
		public static int Postfix(int __result, CardRarity rarity) {
			switch (rarity) {
				case BASIC:
				case SPECIAL:
					return 50;
			}
			return __result;
		}
	}
}

