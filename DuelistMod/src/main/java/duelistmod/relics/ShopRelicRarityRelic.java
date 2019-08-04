package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.variables.Strings;

public class ShopRelicRarityRelic extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ShopRelicRarityRelic");
	public static final String IMG = DuelistMod.makeRelicPath("ShopRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Shop_Outline.png");
	
	public ShopRelicRarityRelic() {
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
		return new ShopRelicRarityRelic();
	}
}
