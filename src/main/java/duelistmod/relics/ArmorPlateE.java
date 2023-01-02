package duelistmod.relics;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.*;

public class ArmorPlateE extends DuelistRelic implements MillenniumArmorPlate {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ArmorPlateE");
	public static final String IMG =  DuelistMod.makeRelicPath("ArmorPlateRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("ArmorPlateRelic_Outline.png");

	private static int plateSize = 0;

	public ArmorPlateE() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
		plateSize = ThreadLocalRandom.current().nextInt(1, 10);
		setDescription();
	}
	
	@Override
	public int getPrice()
	{
		return 20;
	}

	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MillenniumArmor.ID)) { return true; }
		else { return false; }
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
	public void instantObtain()
	{
		super.instantObtain();
		if (AbstractDungeon.player.hasRelic(MillenniumArmor.ID)) {
			MillenniumArmor ma = (MillenniumArmor) AbstractDungeon.player.getRelic(MillenniumArmor.ID);
			ma.pickupArmorPlate(plateSize);
			ma.flash();
		} 
	}

	@Override
	public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip)
	{
		super.instantObtain();
		if (AbstractDungeon.player.hasRelic(MillenniumArmor.ID)) {
			MillenniumArmor ma = (MillenniumArmor) AbstractDungeon.player.getRelic(MillenniumArmor.ID);
			ma.pickupArmorPlate(plateSize);
			ma.flash();
		} 
	}

	@Override
	public void obtain()
	{
		super.obtain();
		if (AbstractDungeon.player.hasRelic(MillenniumArmor.ID)) {
			MillenniumArmor ma = (MillenniumArmor) AbstractDungeon.player.getRelic(MillenniumArmor.ID);
			ma.pickupArmorPlate(plateSize);
			ma.flash();
		} 
	}


	// Description
	@Override
	public String getUpdatedDescription() {
		if (plateSize == 0) { return DESCRIPTIONS[2]; }
		else { return DESCRIPTIONS[0] + plateSize + DESCRIPTIONS[1]; }
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new ArmorPlateE();
	}
}
