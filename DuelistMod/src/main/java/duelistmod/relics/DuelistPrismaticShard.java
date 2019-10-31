package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.helpers.poolhelpers.GlobalPoolHelper;

public class DuelistPrismaticShard extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("DuelistPrismaticShard");
    public static final String IMG = DuelistMod.makeRelicPath("DuelistPrismaticShard.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DuelistPrismaticShard_Outline.png");

	public DuelistPrismaticShard() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public void onEquip()
	{
		GlobalPoolHelper.addRandomSetToPool();
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new DuelistPrismaticShard();
	}
}
