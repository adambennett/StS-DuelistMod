package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.shop.ShopScreen;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.variables.Strings;

public class MerchantNecklace extends DuelistRelic implements ClickableRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MerchantNecklace");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MerchantNecklace() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);
	}
	
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
	    	
	    	// 4 Regular Card Slots
	    	if (DuelistMod.nonPowers.size() > 0) { for (int i = 0; i < 4; i++) { newColored.add(DuelistMod.nonPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.nonPowers.size() - 1)).makeCopy()); }}
	    	else { for (int i = 0; i < 4; i++) { newColored.add(DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)).makeCopy()); }}
	    	
	    	// Power Slot
	    	if (DuelistMod.merchantPendantPowers.size() > 0) { newColored.add(DuelistMod.merchantPendantPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.merchantPendantPowers.size() - 1)).makeCopy()); }
	    	else { newColored.add(DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)).makeCopy()); }
	    	
	    	// Colorless Slots - check for Courier to avoid colorless card crashes
	    	if (AbstractDungeon.player.hasRelic(Courier.ID)) 
	    	{ 
	    		for (int i = 0; i < 2; i++)
	    		{
	    			AbstractCard c = AbstractDungeon.getColorlessCardFromPool(CardRarity.RARE).makeCopy();
		    		newColorless.add(c.makeCopy());
	    		}
	    	}
	    	else
	    	{
		    	if (DuelistMod.rareNonPowers.size() > 0) { for (int i = 0; i < 2; i++) { newColorless.add(DuelistMod.rareNonPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.rareNonPowers.size() - 1)).makeCopy()); }}
		    	else { for (int i = 0; i < 2; i++) { newColorless.add(DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)).makeCopy()); }}
	    	}
	    	// Refresh Shop
	    	shop.init(newColored, newColorless);
	    	shop.purgeAvailable = remove;
	    	
	    	// Upgrade Random Card Type
	    	int roll = AbstractDungeon.cardRandomRng.random(1, 3);
	    	if (roll == 1 || AbstractDungeon.player.hasRelic("Molten Egg 2")) { shop.applyUpgrades(CardType.ATTACK); }
	    	else if (roll == 2 || AbstractDungeon.player.hasRelic("Toxic Egg 2")) { shop.applyUpgrades(CardType.SKILL); }
	    	else if (roll == 3 || AbstractDungeon.player.hasRelic("Frozen Egg 2")) { shop.applyUpgrades(CardType.POWER); }
	    	
	    	// Discount Costs
	    	/*int discountRoll = AbstractDungeon.cardRandomRng.random(1, 4);
	    	float disc = 0.1F * discountRoll;
	    	shop.applyDiscount(disc, true);*/
	    	shop.update();
		}
	}
	
	@Override
    public void onVictory() 
    {
    	if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
        {
    		if (AbstractDungeon.cardRandomRng.random(1, 2) == 1)
    		{
	            flash();
	            setCounter(this.counter + 1);
    		}
        }
    }
	
	@Override
    public void onEquip()
    {
		setCounter(2);
		//setCounter(600);
    }

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MerchantNecklace();
	}	
}
