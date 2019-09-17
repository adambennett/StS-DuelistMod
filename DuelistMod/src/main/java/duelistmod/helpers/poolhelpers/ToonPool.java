package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class ToonPool 
{
	private static String deckName = "Toon Deck";
	
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
		pools.add(ZombiePool.deck());
		pools.add(RockPool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
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
		pools.add(ZombiePool.deck());
		pools.add(RockPool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
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
		StarterDeck toonDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> toonCards = new ArrayList<AbstractCard>();
		toonCards.add(new ToonWorld());
		toonCards.add(new ToonSummonedSkull());
		toonCards.add(new ToonRollback());
		toonCards.add(new ToonMermaid());
		toonCards.add(new ToonMask());
		toonCards.add(new ToonMagic());
		toonCards.add(new ToonKingdom());
		toonCards.add(new ToonGoblinAttack());
		toonCards.add(new ToonGeminiElf());
		toonCards.add(new ToonDarkMagicianGirl());
		toonCards.add(new ToonDarkMagician());
		//toonCards.add(new ToonCyberDragon());
		toonCards.add(new ToonAncientGear());
		toonCards.add(new RedEyesToon());
		toonCards.add(new MangaRyuRan());
		toonCards.add(new BlueEyesToon());
		toonCards.add(new ToonTable());
		toonCards.add(new ToonCannonSoldier());
		toonCards.add(new ToonDefense());
		toonCards.add(new ComicHand());
		toonCards.add(new ShadowToon());
		toonCards.add(new ToonBriefcase());
		toonCards.add(new HeartUnderdog());
		//toonCards.add(new MagicCylinder());
		toonCards.add(new MiniPolymerization());
		toonCards.add(new TributeDoomed());
		toonCards.add(new GiantTrunade());
		toonCards.add(new GracefulCharity());
		toonCards.add(new SwordsConcealing());
		toonCards.add(new TrapHole());
		toonCards.add(new CheerfulCoffin());
		toonCards.add(new ToonGoblinAttack());
		toonCards.add(new SpellShatteringArrow());
		toonCards.add(new FeatherShot());
		toonCards.add(new AncientGearFist());		
		toonCards.add(new FlyingSaucer());	
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			toonCards.add(new Entrench());
			toonCards.add(new Reaper());
			toonCards.add(new BurningPact());
			toonCards.add(new DemonForm());
			toonCards.add(new Uppercut());
			toonCards.add(new BattleTrance());
			toonCards.add(new Shockwave());
			toonCards.add(new SecondWind());
			toonCards.add(new Armaments());
			toonCards.add(new FlameBarrier());
			toonCards.add(new ShrugItOff());
			toonCards.add(new InfernalBlade());
			toonCards.add(new SpotWeakness());
			toonCards.add(new Disarm());
			toonCards.add(new Barricade());
			toonCards.add(new Juggernaut());
			toonCards.add(new Feed());
			toonCards.add(new Impervious());
			toonCards.add(new Metallicize());
			toonCards.add(new BodySlam());
			toonCards.add(new LimitBreak());
			toonCards.add(new DarkEmbrace());
			toonCards.add(new TrueGrit());
			toonCards.add(new Dropkick());
			toonCards.add(new DoubleTap());
			toonCards.add(new BloodForBlood());
			toonCards.add(new Anger());
			toonCards.add(new Rupture());
			toonCards.add(new ThunderClap());
			toonCards.add(new Backflip());
			toonCards.add(new Acrobatics());
			toonCards.add(new DodgeAndRoll());
			toonCards.add(new CalculatedGamble());
			toonCards.add(new WellLaidPlans());
			toonCards.add(new Blur());
			toonCards.add(new ToolsOfTheTrade());
			toonCards.add(new Adrenaline());
			toonCards.add(new Alchemize());
			toonCards.add(new BulletTime());
			toonCards.add(new Outmaneuver());
			toonCards.add(new AThousandCuts());
			toonCards.add(new Malaise());
			toonCards.add(new Burst());
			toonCards.add(new Predator());
			toonCards.add(new Terror());
			toonCards.add(new FlyingKnee());
			toonCards.add(new HeelHook());
			toonCards.add(new Distraction());
			toonCards.add(new Reboot());
			toonCards.add(new BeamCell());
			toonCards.add(new Amplify());
			toonCards.add(new Reprogram());
			toonCards.add(new Buffer());
			toonCards.add(new Recycle());
			toonCards.add(new HelloWorld());
			toonCards.add(new DoubleEnergy());
			toonCards.add(new MachineLearning());
			toonCards.add(new Storm());
			toonCards.add(new Equilibrium());
			toonCards.add(new ReinforcedBody());
			toonCards.add(new Heatsinks());			
		}
		toonDeck.fillPoolCards(toonCards);		
		toonDeck.fillArchetypeCards(toonCards);
		DuelistMod.archetypeCards.addAll(toonCards);
		return toonCards;
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
