package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class FiendPool 
{
	private static String deckName = "Fiend Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		pools.add(AquaPool.deck());
		//pools.add(CreatorPool.deck());
		pools.add(DragonPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
		if (!DuelistMod.toonBtnBool) { pools.add(ToonPool.deck()); }
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size()) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		ArrayList<AbstractCard> random = pools.get(DuelistMod.archRoll1);
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		Util.log(deckName + " was filled with random cards from the pool with index of " + DuelistMod.archRoll1);
		deck.fillPoolCards(random);	
		return random;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		pools.add(AquaPool.deck());
		//pools.add(CreatorPool.deck());
		pools.add(DragonPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
		if (!DuelistMod.toonBtnBool) { pools.add(ToonPool.deck()); }
		ArrayList<AbstractCard> random = new ArrayList<AbstractCard>();
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size() || DuelistMod.archRoll2 > pools.size())
		{
			DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size());
			DuelistMod.archRoll2 = ThreadLocalRandom.current().nextInt(pools.size());
			while (DuelistMod.archRoll1 == DuelistMod.archRoll2) { DuelistMod.archRoll2 = ThreadLocalRandom.current().nextInt(pools.size()); }
		}
		ArrayList<AbstractCard> randomA = pools.get(DuelistMod.archRoll1);
		ArrayList<AbstractCard> randomB = pools.get(DuelistMod.archRoll2);
		random.addAll(randomA); random.addAll(randomB);
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(random);	
		Util.log(deckName + " was filled with random cards from the pool with index of " + DuelistMod.archRoll1 + " and " + DuelistMod.archRoll2);
		return random;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		// Fiend Deck
		StarterDeck fiendDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> fiendCards = new ArrayList<AbstractCard>();
		fiendCards.add(new SummonedSkull());
		fiendCards.add(new GatesDarkWorld());
		fiendCards.add(new FiendishChain());
		//fiendCards.add(new DarkMimicLv1());
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
		fiendCards.add(new MonsterReborn());
		fiendCards.add(new CallGrave());
		fiendCards.add(new MiniPolymerization());
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
		fiendCards.add(new SpiralSpearStrike());
		fiendCards.add(new MeteorDestruction());
		fiendCards.add(new FeatherShot());
		fiendCards.add(new FlyingSaucer());
		fiendCards.add(new Relinkuriboh());
		fiendCards.add(new CosmicHorrorGangiel());	
		fiendCards.add(new AlienTelepath());	
		fiendCards.add(new BlastAsmodian());
		fiendCards.add(new CemetaryBomb());
		fiendCards.add(new UmbralHorrorGhoul());
		fiendCards.add(new UmbralHorrorGhost());
		fiendCards.add(new UmbralHorrorWilloWisp());
		fiendCards.add(new UmbralHorrorUniform());
		fiendCards.add(new DarkHunter());
		fiendDeck.fillPoolCards(fiendCards);		
		fiendDeck.fillArchetypeCards(fiendCards);
		DuelistMod.archetypeCards.addAll(fiendCards);
		return fiendCards;
	}

	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		pool.addAll(DuelistMod.basicCards);
		deck.fillPoolCards(pool); 
		return pool;
	}
}
