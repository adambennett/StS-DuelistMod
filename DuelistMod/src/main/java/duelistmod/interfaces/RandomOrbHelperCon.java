package duelistmod.interfaces;

import java.util.*;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import conspire.orbs.Water;
import duelistmod.DuelistMod;
import duelistmod.orbs.*;

public class RandomOrbHelperCon 
{
	public static void channelWater()
	{
		AbstractOrb water = new Water();
		AbstractDungeon.actionManager.addToTop(new ChannelAction(water));
	}
	
	public static void channelRandomOrb()
	{
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		if (DuelistMod.challengeMode)
		{
			orbs.add(new Lightning());
			orbs.add(new Dark());
			orbs.add(new Frost());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new Earth());
			orbs.add(new Splash());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava());
			orbs.add(new Metal());
			//orbs.add(new MillenniumOrb());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			orbs.add(new Storm());
		}
		else
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
			//orbs.add(new MillenniumOrb());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			orbs.add(new Storm());
		}
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
	
	public static void channelRandomOrbNoGlassOrGate()
	{
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		if (DuelistMod.challengeMode)
		{
			orbs.add(new Lightning());
			orbs.add(new Dark());
			orbs.add(new Frost());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new Earth());
			orbs.add(new Splash());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava());
			orbs.add(new Metal());
			//orbs.add(new MillenniumOrb());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			orbs.add(new Storm());
		}
		else
		{
			orbs.add(new Water());
			orbs.add(new Lightning());
			orbs.add(new Plasma());
			orbs.add(new Dark());
			orbs.add(new Frost());
			orbs.add(new Buffer());
			orbs.add(new Summoner());
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
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
			//orbs.add(new MillenniumOrb());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			orbs.add(new Storm());
		}
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
	
	public static ArrayList<AbstractOrb> returnOrbList()
	{
		ArrayList<AbstractOrb> returnOrbs = new ArrayList<AbstractOrb>();
		if (DuelistMod.challengeMode)
		{
			returnOrbs.add(new Lightning());
			returnOrbs.add(new Dark());
			returnOrbs.add(new Frost());
			returnOrbs.add(new MonsterOrb());
			returnOrbs.add(new DragonOrb());
			returnOrbs.add(new ReducerOrb()); 
			returnOrbs.add(new Earth());
			returnOrbs.add(new Splash());
			returnOrbs.add(new Black());
			returnOrbs.add(new Blaze());
			returnOrbs.add(new Consumer());
			returnOrbs.add(new Gadget());
			returnOrbs.add(new Lava());
			returnOrbs.add(new Metal());
			//returnOrbs.add(new MillenniumOrb());
			returnOrbs.add(new Mist());
			returnOrbs.add(new Mud());
			returnOrbs.add(new Sand());
			returnOrbs.add(new Smoke());
			returnOrbs.add(new Storm());
		}
		else
		{
			returnOrbs.add(new Water());
			returnOrbs.add(new Lightning());
			returnOrbs.add(new Plasma());
			returnOrbs.add(new Dark());
			returnOrbs.add(new Frost());
			returnOrbs.add(new Gate()); 
			returnOrbs.add(new Buffer());
			returnOrbs.add(new Summoner());
			returnOrbs.add(new MonsterOrb());
			returnOrbs.add(new DragonOrb());
			returnOrbs.add(new ReducerOrb()); 
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
			//returnOrbs.add(new MillenniumOrb());
			returnOrbs.add(new Mist());
			returnOrbs.add(new Mud());
			returnOrbs.add(new Sand());
			returnOrbs.add(new Smoke());
			returnOrbs.add(new Storm());
		}
		return returnOrbs;
	}
	
	public static void resetOrbStringMap()
	{
		DuelistMod.invertStringMap = new HashMap<String, AbstractOrb>();
		DuelistMod.invertStringMap.put(new Air().name, new Storm());
		DuelistMod.invertStringMap.put(new Storm().name, new Air());		
		DuelistMod.invertStringMap.put(new Water().name, new FireOrb());
		DuelistMod.invertStringMap.put(new FireOrb().name, new Water());		
		DuelistMod.invertStringMap.put(new Splash().name, new Lava());
		DuelistMod.invertStringMap.put(new Lava().name, new Splash());		
		DuelistMod.invertStringMap.put(new Buffer().name, new ReducerOrb());
		DuelistMod.invertStringMap.put(new ReducerOrb().name, new Buffer());		
		DuelistMod.invertStringMap.put(new DragonOrb().name, new MonsterOrb());
		DuelistMod.invertStringMap.put(new MonsterOrb().name, new DragonOrb());		
		DuelistMod.invertStringMap.put(new DragonPlusOrb().name, new Gate());
		DuelistMod.invertStringMap.put(new Gate().name, new DragonPlusOrb());		
		DuelistMod.invertStringMap.put(new Shadow().name, new Frost());
		DuelistMod.invertStringMap.put(new Frost().name, new Shadow());		
		DuelistMod.invertStringMap.put(new MillenniumOrb(2).name, new Sand());
		DuelistMod.invertStringMap.put(new Sand().name, new MillenniumOrb(2));		
		DuelistMod.invertStringMap.put(new Summoner().name, new Consumer());
		DuelistMod.invertStringMap.put(new Consumer().name, new Summoner());		
		DuelistMod.invertStringMap.put(new Glitch().name, new Gadget());
		DuelistMod.invertStringMap.put(new Gadget().name, new Glitch());		
		DuelistMod.invertStringMap.put(new Lightning().name, new Earth());
		DuelistMod.invertStringMap.put(new Earth().name, new Lightning());		
		DuelistMod.invertStringMap.put(new Plasma().name, new Dark());
		DuelistMod.invertStringMap.put(new Dark().name, new Plasma());			
		DuelistMod.invertStringMap.put(new Black().name, new Metal());
		DuelistMod.invertStringMap.put(new Metal().name, new Black());		
		DuelistMod.invertStringMap.put(new Blaze().name, new Mist());
		DuelistMod.invertStringMap.put(new Mist().name, new Blaze());		
		DuelistMod.invertStringMap.put(new Smoke().name, new Mud());
		DuelistMod.invertStringMap.put(new Mud().name, new Smoke());		

	}
}
