package duelistmod.relics;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.shop.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.*;

public class MerchantNecklace extends DuelistRelic implements ClickableRelic 
{
	public static final String ID = DuelistMod.makeID("MerchantNecklace");
	public static final String IMG =  DuelistMod.makeRelicPath("MerchantNecklace.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("MerchantNecklace_Outline.png");
	public MerchantNecklace() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK); }

	private AbstractCard getRandomCard(boolean power) {
		if (TheDuelist.cardPool != null) {
			if (power) {
				return  TheDuelist.cardPool.getRandomCard(CardType.POWER, true);
			}
			return TheDuelist.cardPool.getRandomCard(true);
		} else {
			List<AbstractCard> list = DuelistMod.myCards.stream().filter(c -> !power || c.type == CardType.POWER).collect(Collectors.toList());
			int index = ThreadLocalRandom.current().nextInt(0, list.size());
			return list.get(index);
		}
	}

	@Override
	public void onRightClick() {
		if (AbstractDungeon.getCurrRoom() instanceof ShopRoom && this.counter > 0)
		{
			setCounter(this.counter - 1);
			ShopScreen shopScreen = AbstractDungeon.shopScreen;
	    	if (shopScreen == null) { return; }
	    	boolean remove = shopScreen.purgeAvailable;
	    	ArrayList<AbstractCard> newColored = new ArrayList<AbstractCard>();
	    	ArrayList<AbstractCard> newColorless = new ArrayList<AbstractCard>();
	    	
	    	// Regular Card Slots
	    	for (int i = 0; i < 4; i++)
	    	{
				AbstractCard c = getRandomCard(false);
	    		while (c.type.equals(CardType.POWER)) { c = getRandomCard(false); }
	    		newColored.add(c.makeCopy());
	    	}
	    	
	    	// Power Slot
	    	AbstractCard c = getRandomCard(true);
			if (c == null) {
				c = getRandomCard(false);
			}
	    	newColored.add(c.makeCopy());
	    	
	    	// Colorless Slots
	    	for (int i = 0; i < 2; i++) {
				AbstractCard.CardRarity tempRarity = AbstractCard.CardRarity.UNCOMMON;
				if (AbstractDungeon.merchantRng.random() < AbstractDungeon.colorlessRareChance) {
					tempRarity = AbstractCard.CardRarity.RARE;
				}
    			AbstractCard card = AbstractDungeon.getColorlessCardFromPool(tempRarity).makeCopy();
	    		newColorless.add(card.makeCopy());
    		}

			try {
				Field colorlessCards = ShopScreen.class.getDeclaredField("colorlessCards");
				colorlessCards.setAccessible(true);
				colorlessCards.set(shopScreen, newColorless);
				Field coloredCards = ShopScreen.class.getDeclaredField("coloredCards");
				coloredCards.setAccessible(true);
				coloredCards.set(shopScreen, newColored);
				Method initCards = ShopScreen.class.getDeclaredMethod("initCards");
				initCards.setAccessible(true);
				initCards.invoke(shopScreen, new Object[] {});
				Field shopRelics = ShopScreen.class.getDeclaredField("relics");
				shopRelics.setAccessible(true);
				ArrayList<StoreRelic> relics = new ArrayList<>((ArrayList<StoreRelic>) shopRelics.get(shopScreen));
				// Add rerolled Items back to relicPool and shuffle them
				for (StoreRelic sr : relics) {
					AbstractRelic relic = sr.relic;
					if (relic != null && !AbstractDungeon.player.hasRelic(relic.relicId)) {
						ArrayList<String> tmp = new ArrayList<>();
						switch (relic.tier) {
							case COMMON:
								tmp.add(relic.relicId);
								tmp.addAll(AbstractDungeon.commonRelicPool);
								AbstractDungeon.commonRelicPool = tmp;
								Collections.shuffle(AbstractDungeon.commonRelicPool);
								break;
							case UNCOMMON:
								tmp.add(relic.relicId);
								tmp.addAll(AbstractDungeon.uncommonRelicPool);
								AbstractDungeon.uncommonRelicPool = tmp;
								Collections.shuffle(AbstractDungeon.uncommonRelicPool);
								break;
							case RARE:
								tmp.add(relic.relicId);
								tmp.addAll(AbstractDungeon.rareRelicPool);
								AbstractDungeon.rareRelicPool = tmp;
								Collections.shuffle(AbstractDungeon.rareRelicPool);
								break;
							case SHOP:
								tmp.add(relic.relicId);
								tmp.addAll(AbstractDungeon.shopRelicPool);
								AbstractDungeon.shopRelicPool = tmp;
								Collections.shuffle(AbstractDungeon.shopRelicPool);
								break;
							default:
								Util.log("Unexpected Relic Tier: " + relic.tier);
								break;
						}
					}
				}
				Method initRelics = ShopScreen.class.getDeclaredMethod("initRelics");
				initRelics.setAccessible(true);
				initRelics.invoke(shopScreen);

				Method potions = ShopScreen.class.getDeclaredMethod("initPotions");
				potions.setAccessible(true);
				potions.invoke(shopScreen);

				int discountRoll = AbstractDungeon.cardRandomRng.random(5, 8);
				float disc = 0.1F * discountRoll;
				shopScreen.applyDiscount(disc, true);
				shopScreen.update();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

	    	shopScreen.purgeAvailable = remove;
		}
	}

	// 50% Chance to increment counter on elite/boss victory
	@Override public void onVictory() { if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) { if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) { flash(); setCounter(this.counter + 1); }}}
	
	// Start with 2 Charges
	@Override public void onEquip() { setCounter(2); }
	
	@Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

	@Override public AbstractRelic makeCopy() { return new MerchantNecklace(); }	
}
