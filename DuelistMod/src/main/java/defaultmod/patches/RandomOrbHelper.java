package defaultmod.patches;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;

import defaultmod.orbs.*;

public class RandomOrbHelper
{
	public static void channelRandomOrb()
	{
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
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
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.size() - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs.get(randomOrb)));
	}
}
