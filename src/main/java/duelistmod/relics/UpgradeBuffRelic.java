package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.variables.Strings;

public class UpgradeBuffRelic extends DuelistRelic 
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
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
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
