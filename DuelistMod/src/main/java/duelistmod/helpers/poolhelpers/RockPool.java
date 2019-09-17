package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class RockPool 
{
	public static ArrayList<AbstractCard> deck()
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.add(new AlphaMagnet());
		cards.add(new BetaMagnet());
		cards.add(new GammaMagnet());
		cards.add(new BlockGolem());
		cards.add(new DeltaMagnet());
		cards.add(new DokiDoki());
		cards.add(new EarthquakeGiant());
		cards.add(new EvilswarmHeliotrope());
		cards.add(new GiantSoldier());
		cards.add(new GiantSoldierSteel());
		cards.add(new IronhammerGiant());
		cards.add(new LabyrinthWall());
		cards.add(new NaturiaCliff());
		cards.add(new NaturiaLandoise());
		cards.add(new PotForbidden());
		cards.add(new PowerGiant());
		cards.add(new ValkMagnet());
		cards.add(new MudGolem());
		return cards;
	}
}
