package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;

public class ShopToken extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ShopToken");
	public static final String IMG = DuelistMod.makeRelicPath("ShopRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Shop_Outline.png");
	
	public ShopToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		DuelistMod.hasShopBuffRelic = true;

	}
	
	@Override
	public void onUnequip()
	{
		DuelistMod.hasShopBuffRelic = false;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}


	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new ShopToken();
	}
	
	
	@Override
	public int getPrice()
	{
		return 50;
	}
	
}
