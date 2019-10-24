package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.naturia.*;

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
		cards.add(new NaturiaGaiastrio());
		cards.add(new NaturiaRock());
		cards.add(new NaturiaCliff());
		cards.add(new NaturiaLandoise());
		cards.add(new PotForbidden());
		cards.add(new PowerGiant());
		cards.add(new ValkMagnet());
		cards.add(new MudGolem());
		cards.add(new ChrysalisMole());
		cards.add(new Blockman());
		cards.add(new AttackTheMoon());
		cards.add(new Canyon());
		cards.add(new EarthEffigy());
		cards.add(new ElephantStatueBlessing());
		cards.add(new ElephantStatueDisaster());
		cards.add(new DestroyerGolem());
		cards.add(new DummyGolem());
		cards.add(new EvilswarmGolem());
		cards.add(new GateBlocker());
		cards.add(new GemElephant());
		cards.add(new GemArmadillo());
		cards.add(new CastleGate());
		cards.add(new FossilTusker());
		cards.add(new CatapultZone());
		cards.add(new GolemSentry());
		cards.add(new GraniteLoyalist());
		cards.add(new LostGuardian());
		cards.add(new MagicHoleGolem());
		cards.add(new MasterMagmaBlacksmith());
		cards.add(new MegarockDragon());
		cards.add(new MillenniumGolem());
		cards.add(new ObsidianDragon());
		cards.add(new ReleaseFromStone());
		cards.add(new RockstoneWarrior());
		cards.add(new StoneDragon());
		cards.add(new WeepingIdol());
		return cards;
	}
}
