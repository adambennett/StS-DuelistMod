package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.interfaces.*;

public class MillenniumArmor extends DuelistRelic implements VisitFromAnubisRemovalFilter, MillenniumItem {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumArmor");
	public static final String IMG =  DuelistMod.makeRelicPath("MillenniumArmor.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("MillenniumArmor_Outline.png");
    private static int armorUp = 8;

	public MillenniumArmor() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.SOLID);
	}
	
	@Override
	public void atBattleStart()
	{
		this.flash();
		DuelistCard.gainTempHP(armorUp);
		this.grayscale = true;
	}
	
	public void pickupArmorPlate(int plateSize)
	{
		armorUp += plateSize;
		setDescription();		
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        String header = name;
        tips.add(new PowerTip(header, description));
        initializeTips();
	}

	@Override
	public boolean canRemove() {
		boolean hasPlates = false;
		for (AbstractRelic rel : AbstractDungeon.player.relics) {
			if (rel instanceof MillenniumArmorPlate) {
				hasPlates = true;
				break;
			}
		}
		return !hasPlates;
	}
	
	@Override
    public void onVictory() 
    {
		this.grayscale = false;
    }

	// Description
	@Override
	public String getUpdatedDescription() 
	{
		return DESCRIPTIONS[0] + armorUp + DESCRIPTIONS[1];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumArmor();
	}
}
