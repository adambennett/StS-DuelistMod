package defaultmod.patches;

import java.util.ArrayList;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.orbs.*;
import conspire.orbs.Water;
import defaultmod.orbs.*;

public class RandomOrbHelperDualMod 
{
	public static void channelRandomOrb()
	{
		
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		// Have both extra mods
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod"))
		{
			orbs.add(new Water());
			orbs.add(new Lightning());
			orbs.add(new Plasma());
			orbs.add(new Dark());
			orbs.add(new HellFireOrb());
			orbs.add(new Frost());
			orbs.add(new CrystalOrb());
			orbs.add(new GlassOrb()); 
			orbs.add(new Gate()); 
			orbs.add(new Buffer());
			orbs.add(new Summoner());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new ReplayLightOrb());
		}
		
		// Have only conspire and not Replay
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod"))
		{
			orbs.add(new Water());
			orbs.add(new Lightning());
			orbs.add(new Plasma());
			orbs.add(new Dark());
			orbs.add(new Frost());
			orbs.add(new Gate()); 
			orbs.add(new Buffer());
			orbs.add(new Summoner());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
		}
		
		// Have only Replay and not conspire
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire"))
		{
			orbs.add(new Lightning());
			orbs.add(new Plasma());
			orbs.add(new Dark());
			orbs.add(new HellFireOrb());
			orbs.add(new Frost());
			orbs.add(new CrystalOrb());
			orbs.add(new GlassOrb()); 
			orbs.add(new Gate()); 
			orbs.add(new Buffer());
			orbs.add(new Summoner());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new ReplayLightOrb());
		}
		
		// Have no mods
		else
		{
			orbs.add(new Lightning());
			orbs.add(new Plasma());
			orbs.add(new Dark());
			orbs.add(new Frost());
			orbs.add(new Gate()); 
			orbs.add(new Buffer());
			orbs.add(new Summoner());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
		}
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
}
