package defaultmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;

import conspire.orbs.Water;
import defaultmod.DefaultMod;
import defaultmod.orbs.*;

public class RandomOrbHelperCon 
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
		}
		return returnOrbs;
	}
}
