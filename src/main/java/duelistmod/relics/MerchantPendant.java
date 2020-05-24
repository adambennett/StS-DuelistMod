package duelistmod.relics;

import java.lang.reflect.*;
import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.*;

import basemod.ReflectionHacks;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.*;

@SuppressWarnings("unchecked")
public class MerchantPendant extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MerchantPendant");
	public static final String IMG =  DuelistMod.makeRelicPath("MerchantPendant.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("MerchantPendant_Outline.png");
	private boolean run = false;

	public MerchantPendant() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
    public void onEquip()
    {
		run = true;
    }
	
	@Override
	public void update()
	{
		super.update();
		if (run)
		{
			if (AbstractDungeon.getCurrRoom() instanceof ShopRoom)
			{			
				ShopScreen shopScreen = AbstractDungeon.shopScreen;
				if (shopScreen == null) { return; }
		    	ArrayList<AbstractCard> newColored = new ArrayList<>();
		    	ArrayList<AbstractCard> newColorless = new ArrayList<>();

		    	// Regular Card Slots
		    	for (int i = 0; i < 4; i++)
		    	{
		    		AbstractCard c = TheDuelist.cardPool.getRandomCard(true);
		    		while (c.type.equals(CardType.POWER)) { c = TheDuelist.cardPool.getRandomCard(true); }
		    		newColored.add(c.makeCopy());
		    	}

		    	// Power Slot
		    	AbstractCard c = TheDuelist.cardPool.getRandomCard(CardType.POWER, true);
		    	newColored.add(c.makeCopy());

		    	// Colorless Slots
		    	for (int i = 0; i < 2; i++)
	    		{
	    			AbstractCard card = AbstractDungeon.getColorlessCardFromPool(CardRarity.RARE).makeCopy();
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

					shopScreen.purgeAvailable = true;

					int discountRoll = AbstractDungeon.cardRandomRng.random(5, 8);
					float disc = 0.1F * discountRoll;
					shopScreen.applyDiscount(disc, true);
					shopScreen.update();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			run = false;
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MerchantPendant();
	}
	
	@Override
	public int getPrice()
	{
		return 0;
	}
	
}
