package duelistmod.helpers;

import java.util.*;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.orbs.*;
import duelistmod.relics.ZombieRelic;

public class RandomOrbHelperRep 
{
	public static void channelWater()
	{
		AbstractOrb water = new WaterOrb();
		AbstractDungeon.actionManager.addToTop(new ChannelAction(water));
	}
	
	public static boolean checkWater()
	{
		for (AbstractOrb o : AbstractDungeon.player.orbs)
		{
			if (o instanceof WaterOrb)
			{
				return true;
			}
			
			if (o instanceof Splash)
			{
				return true;
			}
			
			if (o instanceof Mist)
			{
				return true;
			}
			
			if (o instanceof Storm)
			{
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<AbstractOrb> channelStatue()
	{
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		orbs.add(new WaterOrb());
		orbs.add(new Lightning());
		orbs.add(new Plasma());
		orbs.add(new Dark());
		orbs.add(new Frost());
		orbs.add(new Summoner(1));
		orbs.add(new MonsterOrb());
		orbs.add(new DragonOrb());
		orbs.add(new ReducerOrb()); 
		orbs.add(new Air());
		orbs.add(new Earth());
		orbs.add(new FireOrb());
		orbs.add(new Shadow(AbstractDungeon.player.hasRelic(ZombieRelic.ID)));	
		orbs.add(new Black());
		orbs.add(new Gadget());
		orbs.add(new Metal());
		orbs.add(new Mist());
		orbs.add(new Mud());
		orbs.add(new Sand());
		orbs.add(new Storm());
		orbs.add(new DuelistHellfire());
		orbs.add(new DuelistCrystal());
		orbs.add(new TokenOrb());
		orbs.add(new WhiteOrb());
		orbs.add(new VoidOrb());
		orbs.add(new Surge());
		return orbs;
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
			orbs.add(new GlassOrb()); 
			orbs.add(new DuelistGlass());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava(2));
			orbs.add(new Metal());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			orbs.add(new Storm());
			orbs.add(new TokenOrb());
			orbs.add(new WhiteOrb());
			orbs.add(new VoidOrb());
			orbs.add(new Surge());
			orbs.add(new Alien());
			orbs.add(new Moon());
			orbs.add(new Sun());
		}
		else
		{
			orbs.add(new WaterOrb());
			orbs.add(new Lightning());
			orbs.add(new Plasma());
			orbs.add(new Dark());
			orbs.add(new DuelistHellfire());
			orbs.add(new Frost());
			orbs.add(new DuelistCrystal());
			orbs.add(new GlassOrb()); 
			orbs.add(new DuelistGlass());
			orbs.add(new Buffer());
			orbs.add(new Summoner(1));
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new DuelistLight());
			orbs.add(new Air());
			orbs.add(new Earth());
			orbs.add(new FireOrb());
			orbs.add(new Glitch());
			orbs.add(new Shadow(AbstractDungeon.player.hasRelic(ZombieRelic.ID)));
			orbs.add(new Splash());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava());
			orbs.add(new Metal());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			orbs.add(new Storm());
			orbs.add(new TokenOrb());
			orbs.add(new WhiteOrb());
			orbs.add(new VoidOrb());
			orbs.add(new Surge());
			orbs.add(new Alien());
			orbs.add(new Moon());
			orbs.add(new Sun());
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
			orbs.add(new Lava(2));
			orbs.add(new Metal());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			orbs.add(new Storm());
			orbs.add(new TokenOrb());
			orbs.add(new WhiteOrb());
			orbs.add(new VoidOrb());
			orbs.add(new Surge());
			orbs.add(new Alien());
			orbs.add(new Moon());
			orbs.add(new Sun());
		}
		else
		{
			orbs.add(new WaterOrb());
			orbs.add(new Lightning());
			orbs.add(new Plasma());
			orbs.add(new Dark());
			orbs.add(new DuelistHellfire());
			orbs.add(new Frost());
			orbs.add(new DuelistCrystal());
			orbs.add(new Buffer());
			orbs.add(new Summoner(1));
			orbs.add(new MonsterOrb());
			orbs.add(new DragonOrb());
			orbs.add(new ReducerOrb()); 
			orbs.add(new DuelistLight());
			orbs.add(new Air());
			orbs.add(new Earth());
			orbs.add(new FireOrb());
			orbs.add(new Glitch());
			orbs.add(new Shadow(AbstractDungeon.player.hasRelic(ZombieRelic.ID)));
			orbs.add(new Splash());
			orbs.add(new Black());
			orbs.add(new Blaze());
			orbs.add(new Consumer());
			orbs.add(new Gadget());
			orbs.add(new Lava(AbstractDungeon.cardRandomRng.random(1, 10)));
			orbs.add(new Metal());
			orbs.add(new Mist());
			orbs.add(new Mud());
			orbs.add(new Sand());
			orbs.add(new Smoke());
			orbs.add(new Storm());
			orbs.add(new TokenOrb());
			orbs.add(new WhiteOrb());
			orbs.add(new VoidOrb());
			orbs.add(new Surge());
			orbs.add(new Alien());
			orbs.add(new Moon());
			orbs.add(new Sun());
		}
		
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
	
	public static AbstractOrb spellcasterPuzzleChannel()
	{
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		orbs.add(new WaterOrb());
		orbs.add(new Lightning());
		orbs.add(new Plasma());
		orbs.add(new Dark());
		orbs.add(new DuelistHellfire());
		orbs.add(new Frost());
		orbs.add(new DragonOrb());
		orbs.add(new ReducerOrb()); 
		orbs.add(new Earth());
		orbs.add(new Gadget());
		orbs.add(new Mud());
		orbs.add(new Sand());
		orbs.add(new Storm());
		orbs.add(new WhiteOrb());
		orbs.add(new Surge());	
		orbs.add(new Moon());
		orbs.add(new Sun());
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractOrb orb = orbs.get(randomOrb);
		AbstractDungeon.player.channelOrb(orb);
		if (orb instanceof DuelistOrb)
		{
			DuelistOrb duelOrb = (DuelistOrb)orb;
			if (duelOrb.triggersOnSpellcasterPuzzle) { orb.onStartOfTurn(); }
			else { Util.log("Did not trigger onStartOfTurn() for " + orb.name); }
		}
		else if (orb instanceof Lightning || orb instanceof Dark || orb instanceof Frost)
		{
			orb.onEndOfTurn();
			Util.log("Trigger onEndOfTurn() for " + orb.name);
		}
		
		return orb.makeCopy();
	}
	
	public static void channelRandomOffense()
	{
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();		
		orbs.add(new Lightning());
		orbs.add(new Dark());
		orbs.add(new DuelistHellfire());
		orbs.add(new Lava());
		orbs.add(new Mud());
		orbs.add(new VoidOrb());
		orbs.add(new FireOrb());
		orbs.add(new Blaze());
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
	
	public static void channelRandomDefense()
	{
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();		
		orbs.add(new Frost());
		orbs.add(new Gadget());
		orbs.add(new Metal());
		orbs.add(new VoidOrb());
		orbs.add(new Surge());
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
	
	public static ArrayList<AbstractOrb> returnOrbList()
	{
		ArrayList<AbstractOrb> returnOrbs = new ArrayList<AbstractOrb>();
		returnOrbs.add(new WaterOrb());
		returnOrbs.add(new Lightning());
		returnOrbs.add(new Plasma());
		returnOrbs.add(new Dark());
		returnOrbs.add(new DuelistHellfire());
		returnOrbs.add(new Frost());
		returnOrbs.add(new DuelistCrystal());
		returnOrbs.add(new GlassOrb()); 
		returnOrbs.add(new Gate()); 
		returnOrbs.add(new Buffer());
		returnOrbs.add(new Summoner(1));
		returnOrbs.add(new MonsterOrb());
		returnOrbs.add(new DragonOrb());
		returnOrbs.add(new ReducerOrb()); 
		returnOrbs.add(new DuelistLight());
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
		returnOrbs.add(new Mist());
		returnOrbs.add(new Mud());
		returnOrbs.add(new Sand());
		returnOrbs.add(new Smoke());
		returnOrbs.add(new Storm());
		returnOrbs.add(new DuelistGlass());
		returnOrbs.add(new TokenOrb());
		returnOrbs.add(new DarkMillenniumOrb());
		returnOrbs.add(new WhiteOrb());
		returnOrbs.add(new VoidOrb());
		returnOrbs.add(new Surge());
		returnOrbs.add(new Anticrystal());
		returnOrbs.add(new Alien());
		//returnOrbs.add(new Blood());
		returnOrbs.add(new Moon());
		returnOrbs.add(new Sun());
		returnOrbs.add(new LightMillenniumOrb());
		return returnOrbs;
	}
	
	public static void resetOrbStringMap()
	{
		DuelistMod.invertStringMap = new HashMap<String, AbstractOrb>();	
		DuelistMod.invertStringMap.put(new WaterOrb().name, new FireOrb());
		DuelistMod.invertStringMap.put(new FireOrb().name, new WaterOrb());			// non-conspire only
		DuelistMod.invertStringMap.put(new Splash().name, new DuelistHellfire());			
		DuelistMod.invertStringMap.put(new DuelistHellfire().name, new Splash());
		DuelistMod.invertStringMap.put(new HellFireOrb().name, new Splash());		// replay only
		DuelistMod.invertStringMap.put(new Buffer().name, new ReducerOrb());
		DuelistMod.invertStringMap.put(new ReducerOrb().name, new Buffer());
		DuelistMod.invertStringMap.put(new Blaze().name, new Mist());
		DuelistMod.invertStringMap.put(new Mist().name, new Blaze());			
		DuelistMod.invertStringMap.put(new Lava().name, new Frost());
		DuelistMod.invertStringMap.put(new Frost().name, new Lava());
		DuelistMod.invertStringMap.put(new Shadow().name, new DuelistLight());		
		DuelistMod.invertStringMap.put(new DuelistLight().name, new Shadow());
		DuelistMod.invertStringMap.put(new ReplayLightOrb().name, new Shadow());	// replay only		
		DuelistMod.invertStringMap.put(new DuelistGlass().name, new Sand());
		DuelistMod.invertStringMap.put(new Sand().name, new DuelistGlass());
		DuelistMod.invertStringMap.put(new GlassOrb().name, new Sand());			// replay only		
		DuelistMod.invertStringMap.put(new Summoner(1).name, new Consumer());
		DuelistMod.invertStringMap.put(new Consumer().name, new Summoner(1));		
		DuelistMod.invertStringMap.put(new Glitch().name, new Gadget());
		DuelistMod.invertStringMap.put(new Gadget().name, new Glitch());				
		DuelistMod.invertStringMap.put(new Plasma().name, new Dark());
		DuelistMod.invertStringMap.put(new Dark().name, new Plasma());	
		DuelistMod.invertStringMap.put(new Smoke().name, new Air());
		DuelistMod.invertStringMap.put(new Air().name, new Smoke());	
		DuelistMod.invertStringMap.put(new Earth().name, new Mud());
		DuelistMod.invertStringMap.put(new Mud().name, new Earth());
		DuelistMod.invertStringMap.put(new Storm().name, new Lightning());
		DuelistMod.invertStringMap.put(new Lightning().name, new Storm());
		DuelistMod.invertStringMap.put(new MonsterOrb().name, new TokenOrb());
		DuelistMod.invertStringMap.put(new TokenOrb().name, new MonsterOrb());
		DuelistMod.invertStringMap.put(new DragonOrb().name, new DragonPlusOrb());
		DuelistMod.invertStringMap.put(new DragonPlusOrb().name, new DragonOrb());		
		DuelistMod.invertStringMap.put(new Gate().name, new MillenniumOrb(2));
		DuelistMod.invertStringMap.put(new DuelistCrystal().name, new Anticrystal());
		DuelistMod.invertStringMap.put(new Anticrystal().name, new DuelistCrystal());
		DuelistMod.invertStringMap.put(new CrystalOrb().name, new Anticrystal());			// replay only
		DuelistMod.invertStringMap.put(new MillenniumOrb(2).name, new DarkMillenniumOrb());
		DuelistMod.invertStringMap.put(new DarkMillenniumOrb().name, new LightMillenniumOrb());
		DuelistMod.invertStringMap.put(new LightMillenniumOrb().name, new DarkMillenniumOrb());
		DuelistMod.invertStringMap.put(new Alien().name, new VoidOrb());
		DuelistMod.invertStringMap.put(new VoidOrb().name, new Alien());
		DuelistMod.invertStringMap.put(new Sun().name, new Moon());
		DuelistMod.invertStringMap.put(new Moon().name, new Sun());
		DuelistMod.invertStringMap.put(new Black().name, new WhiteOrb());
		DuelistMod.invertStringMap.put(new WhiteOrb().name, new Black());
		DuelistMod.invertStringMap.put(new Metal().name, new Surge());
		DuelistMod.invertStringMap.put(new Surge().name, new Metal());
	}
}
