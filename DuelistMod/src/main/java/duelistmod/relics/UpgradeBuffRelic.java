package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;

public class UpgradeBuffRelic extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("UpgradeBuffRelic");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	
	public UpgradeBuffRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		return false;
	}
	
	@Override
	public void onEquip()
	{
		DuelistMod.hasUpgradeBuffRelic = true;
	}
	
	@Override
	public void onUnequip()
	{
		DuelistMod.hasUpgradeBuffRelic = false;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}


	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new UpgradeBuffRelic();
	}
}
