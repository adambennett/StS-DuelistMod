package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.shop.ShopScreen;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;

public class MerchantNecklace extends DuelistRelic implements ClickableRelic 
{
	public static final String ID = DuelistMod.makeID("MerchantNecklace");
	public static final String IMG =  DuelistMod.makeRelicPath("MerchantNecklace.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("MerchantNecklace_Outline.png");
	public MerchantNecklace() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK); }
	
	@Override
	public void onRightClick()
	{
		if (AbstractDungeon.getCurrRoom() instanceof ShopRoom && this.counter > 0)
		{
			setCounter(this.counter - 1);
			ShopScreen shop = AbstractDungeon.shopScreen;
	    	if (shop == null) { return; }
	    	boolean remove = shop.purgeAvailable;
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

	    	// Refresh Shop
	    	shop.init(newColored, newColorless);
	    	shop.purgeAvailable = remove;
	    	
	    	// Upgrade Random Card Type
	    	int roll = AbstractDungeon.cardRandomRng.random(1, 3);
	    	if (roll == 1 || AbstractDungeon.player.hasRelic("Molten Egg 2")) { shop.applyUpgrades(CardType.ATTACK); }
	    	else if (roll == 2 || AbstractDungeon.player.hasRelic("Toxic Egg 2")) { shop.applyUpgrades(CardType.SKILL); }
	    	else if (roll == 3 || AbstractDungeon.player.hasRelic("Frozen Egg 2")) { shop.applyUpgrades(CardType.POWER); }

	    	// Update Shop
	    	shop.update();
		}
	}
	
	// 50% Chance to Increment counter on elite/boss victory
	@Override public void onVictory() { if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) { if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) { flash(); setCounter(this.counter + 1); }}}
	
	// Start with 2 Charges
	@Override public void onEquip() { setCounter(2); }
	
	@Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

	@Override public AbstractRelic makeCopy() { return new MerchantNecklace(); }	
}
