package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class ZombiePool 
{
	
	private static String deckName = "Zombie Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		pools.add(AquaPool.deck());
		//pools.add(CreatorPool.deck());
		pools.add(DragonPool.deck());
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturiaPool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(RockPool.deck());
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
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturiaPool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(RockPool.deck());
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
		StarterDeck zombieDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> zombieCards = new ArrayList<AbstractCard>();
		zombieCards.add(new ArmoredZombie());
		zombieCards.add(new ClownZombie());
		zombieCards.add(new DoubleCoston());
		zombieCards.add(new MoltenZombie());
		zombieCards.add(new RegenMummy());
		zombieCards.add(new PatricianDarkness());
		zombieCards.add(new RedEyesZombie());		
		zombieCards.add(new RyuKokki());
		zombieCards.add(new SoulAbsorbingBone());
		zombieCards.add(new VampireGenesis());
		zombieCards.add(new VampireLord());
		zombieCards.add(new Skelesaurus());
		zombieCards.add(new GatesDarkWorld());
		zombieCards.add(new CallGrave());
		zombieCards.add(new CardSafeReturn());
		zombieCards.add(new MonsterReborn());
		zombieCards.add(new ShallowGrave());
		zombieCards.add(new MiniPolymerization());
		zombieCards.add(new Pumpking());
		zombieCards.add(new Pumprincess());
		zombieCards.add(new TrapHole());
		zombieCards.add(new TributeDoomed());
		zombieCards.add(new PotDuality());
		zombieCards.add(new SwordsBurning());
		zombieCards.add(new LegendaryFlameLord());
		zombieCards.add(new AcidTrapHole());
		zombieCards.add(new BottomlessTrapHole());
		zombieCards.add(new DrivenDaredevil());
		zombieCards.add(new MonsterEgg());
		zombieCards.add(new Zombyra());
		zombieCards.add(new ImperialTomb());
		zombieCards.add(new ZombieMaster());
		zombieCards.add(new VampireGrace());
		zombieCards.add(new VampireFraulein());
		zombieCards.add(new ShadowVampire());
		zombieCards.add(new IlBlud());
		zombieCards.add(new CallMummy());		
		zombieCards.add(new ArchfiendZombieSkull());
		zombieCards.add(new CorrodingShark());
		zombieCards.add(new FlameGhost());
		zombieCards.add(new Gernia());
		zombieCards.add(new GoblinZombie());
		zombieCards.add(new Gozuki());
		zombieCards.add(new Kasha());
		zombieCards.add(new OniTankT34());
		zombieCards.add(new RedHeadedOni());		
		zombieCards.add(new ZombieWarrior());
		zombieCards.add(new BlueBloodedOni());
		zombieCards.add(new DesLacooda());
		zombieCards.add(new EndlessDecay());		
		zombieCards.add(new HauntedShrine());
		zombieCards.add(new OniGamiCombo());
		zombieCards.add(new PlaguespreaderZombie());
		zombieCards.add(new YellowBelliedOni());
		zombieCards.add(new ZombieWorld());
		zombieCards.add(new DespairFromDark());
		zombieCards.add(new SpiritPharaoh());
		zombieCards.add(new DarkAssailant());
		zombieCards.add(new FearFromDark());
		zombieCards.add(new BeserkDragon());
		zombieCards.add(new DoomkaiserDragon());
		zombieCards.add(new PainPainter());
		zombieCards.add(new ZombieMammoth());	
		zombieCards.add(new UnderworldCannon());
		zombieCards.add(new DragonZombie());
		zombieCards.add(new SilentDoom());
		zombieCards.add(new SpiralSpearStrike());
		zombieCards.add(new CemetaryBomb());
		zombieCards.add(new PurplePainOni());
		zombieCards.add(new GreenGraveOni());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			zombieCards.add(new Corruption());
			zombieCards.add(new BurningPact());
			zombieCards.add(new Exhume());
			zombieCards.add(new Sentinel());
			zombieCards.add(new SeeingRed());
			zombieCards.add(new FeelNoPain());
			zombieCards.add(new GhostlyArmor());
			zombieCards.add(new SecondWind());
			zombieCards.add(new Offering());
			zombieCards.add(new InfernalBlade());
			zombieCards.add(new Bloodletting());
			zombieCards.add(new FiendFire());
			zombieCards.add(new DarkEmbrace());
			zombieCards.add(new BloodForBlood());
			zombieCards.add(new TrueGrit());
			zombieCards.add(new SeverSoul());
			zombieCards.add(new Brutality());
			zombieCards.add(new Hemokinesis());
			zombieCards.add(new Combust());
			zombieCards.add(new Rupture());
		}
		
		//zombieCards.add(new WightLady());			
		//zombieCards.add(new Relinkuriboh());

		zombieDeck.fillPoolCards(zombieCards);		
		zombieDeck.fillArchetypeCards(zombieCards);
		DuelistMod.archetypeCards.addAll(zombieCards);
		return zombieCards;
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
