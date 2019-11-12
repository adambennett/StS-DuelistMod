package duelistmod.relics;

import java.util.ArrayList;

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
				ShopScreen shop = AbstractDungeon.shopScreen;			
		    	if (shop == null) { return; }
		    	ArrayList<StoreRelic> relicRef = (ArrayList<StoreRelic>) ReflectionHacks.getPrivate(shop, ShopScreen.class, "relics");
				relicRef.clear();
		    	ArrayList<AbstractCard> newColored = new ArrayList<AbstractCard>();
		    	ArrayList<AbstractCard> newColorless = new ArrayList<AbstractCard>();
		    	
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

		    	shop.init(newColored, newColorless);
		    	
		    	int roll = AbstractDungeon.cardRandomRng.random(1, 3);
		    	if (roll == 1 || AbstractDungeon.player.hasRelic("Molten Egg 2")) { shop.applyUpgrades(CardType.ATTACK); }
		    	else if (roll == 2 || AbstractDungeon.player.hasRelic("Toxic Egg 2")) { shop.applyUpgrades(CardType.SKILL); }
		    	else if (roll == 3 || AbstractDungeon.player.hasRelic("Frozen Egg 2")) { shop.applyUpgrades(CardType.POWER); }
		    	
		    	int discountRoll = AbstractDungeon.cardRandomRng.random(5, 8);
		    	float disc = 0.1F * discountRoll;
		    	shop.applyDiscount(disc, true);
		    
		    	shop.update();
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
