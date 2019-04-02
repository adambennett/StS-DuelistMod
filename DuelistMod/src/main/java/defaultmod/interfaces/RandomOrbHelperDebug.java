package defaultmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import defaultmod.orbs.*;

public class RandomOrbHelperDebug 
{
	public static void channelRandomOrb()
	{
		
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		/*orbs.add(new Water());
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
		orbs.add(new Glitch());
		orbs.add(new Shadow());*/
		orbs.add(new FireOrb());
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
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
}
