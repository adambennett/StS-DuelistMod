package defaultmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.orbs.*;

import conspire.orbs.Water;
import defaultmod.DefaultMod;
import defaultmod.orbs.*;

public class RandomOrbHelperDualMod 
{
	public static void channelRandomOrb()
	{
		
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		if (DefaultMod.challengeMode)
		{
			orbs.add(new Lightning());
			orbs.add(new Dark());
			orbs.add(new Frost());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new Earth());
			orbs.add(new Splash());
			orbs.add(new GlassOrb()); 
			orbs.add(new CrystalOrb());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava());
			orbs.add(new Metal());
			orbs.add(new MillenniumOrb());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			//orbs.add(new Storm());
		}
		else
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
			orbs.add(new Air());
			orbs.add(new Earth());
			orbs.add(new FireOrb());
			orbs.add(new Glitch());
			orbs.add(new Shadow());
			orbs.add(new Splash());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava());
			orbs.add(new Metal());
			orbs.add(new MillenniumOrb());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			//orbs.add(new Storm());
		}
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
	
	public static void channelRandomOrbNoGlassOrGate()
	{
		
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		if (DefaultMod.challengeMode)
		{
			orbs.add(new Lightning());
			orbs.add(new Dark());
			orbs.add(new Frost());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new Earth());
			orbs.add(new Splash());
			orbs.add(new CrystalOrb());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava());
			orbs.add(new Metal());
			orbs.add(new MillenniumOrb());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			//orbs.add(new Storm());
		}
		else
		{
			orbs.add(new Water());
			orbs.add(new Lightning());
			orbs.add(new Plasma());
			orbs.add(new Dark());
			orbs.add(new HellFireOrb());
			orbs.add(new Frost());
			orbs.add(new CrystalOrb());
			orbs.add(new GlassOrb()); 
			orbs.add(new Buffer());
			orbs.add(new Summoner());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new ReplayLightOrb());
			orbs.add(new Air());
			orbs.add(new Earth());
			orbs.add(new FireOrb());
			orbs.add(new Glitch());
			orbs.add(new Shadow());
			orbs.add(new Splash());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava());
			orbs.add(new Metal());
			orbs.add(new MillenniumOrb());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			//orbs.add(new Storm());
		}
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
	
	public static ArrayList<AbstractOrb> returnOrbList()
	{
		ArrayList<AbstractOrb> returnOrbs = new ArrayList<AbstractOrb>();
		if (DefaultMod.challengeMode)
		{
			returnOrbs.add(new Lightning());
			returnOrbs.add(new Dark());
			returnOrbs.add(new Frost());
			returnOrbs.add(new MonsterOrb());
			returnOrbs.add(new DragonOrb());
			returnOrbs.add(new ReducerOrb()); 
			returnOrbs.add(new Earth());
			returnOrbs.add(new Splash());
			returnOrbs.add(new CrystalOrb());
			returnOrbs.add(new GlassOrb()); 
			returnOrbs.add(new Black());
			returnOrbs.add(new Blaze());
			returnOrbs.add(new Consumer());
			returnOrbs.add(new Gadget());
			returnOrbs.add(new Lava());
			returnOrbs.add(new Metal());
			returnOrbs.add(new MillenniumOrb());
			returnOrbs.add(new Mist());
			returnOrbs.add(new Mud());
			returnOrbs.add(new Sand());
			returnOrbs.add(new Smoke());
			//returnOrbs.add(new Storm());
		}
		else
		{
			returnOrbs.add(new Water());
			returnOrbs.add(new Lightning());
			returnOrbs.add(new Plasma());
			returnOrbs.add(new Dark());
			returnOrbs.add(new HellFireOrb());
			returnOrbs.add(new Frost());
			returnOrbs.add(new CrystalOrb());
			returnOrbs.add(new GlassOrb()); 
			returnOrbs.add(new Gate()); 
			returnOrbs.add(new Buffer());
			returnOrbs.add(new Summoner());
			returnOrbs.add(new MonsterOrb());
			returnOrbs.add(new DragonOrb());
			returnOrbs.add(new ReducerOrb()); 
			returnOrbs.add(new ReplayLightOrb());
			returnOrbs.add(new Air());
			returnOrbs.add(new Earth());
			returnOrbs.add(new FireOrb());
			returnOrbs.add(new Glitch());
			returnOrbs.add(new Shadow());
			returnOrbs.add(new Splash());
			returnOrbs.add(new Black());
			returnOrbs.add(new Blaze());
			returnOrbs.add(new Consumer());
			returnOrbs.add(new Gadget());
			returnOrbs.add(new Lava());
			returnOrbs.add(new Metal());
			returnOrbs.add(new MillenniumOrb());
			returnOrbs.add(new Mist());
			returnOrbs.add(new Mud());
			returnOrbs.add(new Sand());
			returnOrbs.add(new Smoke());
			//returnOrbs.add(new Storm());
		}
		return returnOrbs;
	}
}
