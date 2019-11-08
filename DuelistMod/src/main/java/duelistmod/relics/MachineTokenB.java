package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.StarterDeckSetup;

public class MachineTokenB extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("MachineTokenB");
	public static final String IMG = DuelistMod.makeRelicPath("MachineRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("MachineRelic.png");

	public MachineTokenB() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		DuelistMod.explosiveDmgLow += 2;
		DuelistMod.explosiveDmgHigh += 3;
		try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt("explosiveDmgLow", DuelistMod.explosiveDmgLow);
			config.setInt("explosiveDmgHigh", DuelistMod.explosiveDmgHigh);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	@Override
	public void onUnequip()
	{
		DuelistMod.explosiveDmgLow -= 2;
		DuelistMod.explosiveDmgHigh -= 3;
		try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt("explosiveDmgLow", DuelistMod.explosiveDmgLow);
			config.setInt("explosiveDmgHigh", DuelistMod.explosiveDmgHigh);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Machine Deck")) { return true; }
		else { return false; }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MachineTokenB();
	}
}
