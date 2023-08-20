package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.dragons.FiendSkull;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.zombies.*;

public class FiendPool 
{
	private static String deckName = "Fiend Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(2));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(2));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		// Fiend Deck
		StarterDeck fiendDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> fiendCards = new ArrayList<AbstractCard>();
		fiendCards.add(new SummonedSkull());
		fiendCards.add(new FiendishChain());
		fiendCards.add(new DarkBlade());
		fiendCards.add(new DarkMasterZorc());
		fiendCards.add(new FiendMegacyber());
		fiendCards.add(new KingYami());
		fiendCards.add(new Lajinn());
		fiendCards.add(new MsJudge());
		fiendCards.add(new SkullArchfiend());
		fiendCards.add(new SlateWarrior());
		fiendCards.add(new TerraTerrible());
		fiendCards.add(new DarkEnergy());
		fiendCards.add(new VanityFiend());
		fiendCards.add(new ArchfiendSoldier());
		fiendCards.add(new FabledAshenveil());
		fiendCards.add(new FabledGallabas());
		fiendCards.add(new GrossGhost());
		fiendCards.add(new PortraitSecret());
		fiendCards.add(new FiendSkull());
		fiendCards.add(new RedSprinter());
		fiendCards.add(new Tierra());
		fiendCards.add(new GracefulCharity());
		fiendCards.add(new CallGrave());
		fiendCards.add(new TributeDoomed());
		fiendCards.add(new Pumpking());
		fiendCards.add(new Pumprincess());
		fiendCards.add(new PotDuality());
		fiendCards.add(new Doomdog());
		fiendCards.add(new RedMirror());
		fiendCards.add(new DarknessNeosphere());
		fiendCards.add(new AcidTrapHole());
		fiendCards.add(new BottomlessTrapHole());
		fiendCards.add(new CheerfulCoffin());
		fiendCards.add(new DrivenDaredevil());
		fiendCards.add(new Zombyra());
		fiendCards.add(new GiantOrc());
		fiendCards.add(new DarkFusion());
		fiendCards.add(new DarklordMarie());
		fiendCards.add(new CyberArchfiend());
		fiendCards.add(new ArchfiendCommander());
		fiendCards.add(new ArchfiendGeneral());
		fiendCards.add(new ArchfiendHeiress());
		fiendCards.add(new ArchfiendInterceptor());
		fiendCards.add(new BeastTalwar());
		fiendCards.add(new DarkTinker());
		fiendCards.add(new DoomcaliberKnight());
		fiendCards.add(new DoomsdayHorror());
		fiendCards.add(new EvilswarmNightmare());
		fiendCards.add(new GarbageLord());
		fiendCards.add(new TwinHeadedWolf());
		fiendCards.add(new WanderingKing());
		fiendCards.add(new AmbitiousGofer());
		fiendCards.add(new GoblinKing());
		fiendCards.add(new PutridPudding());
		fiendCards.add(new DoomDonuts());		
		fiendCards.add(new Wildfire());
		fiendCards.add(new BerserkerCrush());
		fiendCards.add(new FeatherShot());
		fiendCards.add(new FlyingSaucer());
		fiendCards.add(new Relinkuriboh());
		fiendCards.add(new CosmicHorrorGangiel());	
		fiendCards.add(new AlienTelepath());	
		fiendCards.add(new BlastAsmodian());
		fiendCards.add(new UmbralHorrorGhoul());
		fiendCards.add(new UmbralHorrorGhost());
		fiendCards.add(new UmbralHorrorWilloWisp());
		fiendCards.add(new UmbralHorrorUniform());
		fiendCards.add(new DarkHunter());
		fiendCards.add(new DarkCubicLord());
		//fiendCards.add(new DarkArmedDragon());
		//fiendCards.add(new ChaosHunter());
		//fiendCards.add(new TranceArchfiend());
		//fiendCards.add(new GiantGerm());
		//fiendCards.add(new FiresOfDoomsday());
		//fiendCards.add(new SteelswarmRoach());
		//fiendCards.add(new VanguardOfDarkWorld());
		//fiendCards.add(new DarkWorldDealings());
		//fiendCards.add(new DarkWorldLightning());
		fiendDeck.fillPoolCards(fiendCards);
		//DuelistMod.archetypeCards.addAll(fiendCards);
		return fiendCards;
	}

	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
