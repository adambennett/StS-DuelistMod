package defaultmod.patches;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.orbs.*;

import defaultmod.orbs.*;

public class RandomOrbHelperRep 
{
	public static void channelRandomOrb()
	{
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
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
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
}
