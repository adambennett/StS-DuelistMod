package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class SpellcasterPool 
{
	private static String deckName = "Spellcaster Deck";
	
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
		pools.add(NaturePool.deck());
		//pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
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
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturePool.deck());
		//pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
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
		StarterDeck spellcasterDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> spellcasterCards = new ArrayList<AbstractCard>();
		spellcasterCards.add(new AncientElf());
		spellcasterCards.add(new BadReaction());
		spellcasterCards.add(new BlizzardPrincess());
		spellcasterCards.add(new BookSecret());
		spellcasterCards.add(new DarkMagicianGirl());
		spellcasterCards.add(new DarkMagician());
		spellcasterCards.add(new FogKing());
		spellcasterCards.add(new GeminiElf());
		spellcasterCards.add(new GiantTrunade());
		spellcasterCards.add(new Graverobber());
		spellcasterCards.add(new GuardianAngel());
		spellcasterCards.add(new IcyCrevasse());
		spellcasterCards.add(new Illusionist());
		spellcasterCards.add(new InjectionFairy());
		spellcasterCards.add(new InvitationDarkSleep());
		spellcasterCards.add(new MysticalElf());
		spellcasterCards.add(new MythicalBeast());
		spellcasterCards.add(new NeoMagic());
		spellcasterCards.add(new NutrientZ());
		spellcasterCards.add(new PowerKaishin());
		spellcasterCards.add(new RainMercy());
		spellcasterCards.add(new Relinquished());
		spellcasterCards.add(new SangaEarth());
		spellcasterCards.add(new SangaThunder());
		spellcasterCards.add(new SangaWater());
		spellcasterCards.add(new GateGuardian());
		spellcasterCards.add(new FairyBox());
		spellcasterCards.add(new SpiritHarp());
		spellcasterCards.add(new SwordsBurning());
		spellcasterCards.add(new Yami());
		spellcasterCards.add(new TimeWizard());
		spellcasterCards.add(new IceQueen());
		spellcasterCards.add(new ThousandEyesRestrict());
		spellcasterCards.add(new ThousandEyesIdol());
		spellcasterCards.add(new MindAir());
		spellcasterCards.add(new FrontierWiseman());		
		spellcasterCards.add(new GoblinRemedy());
		spellcasterCards.add(new AltarTribute());
		spellcasterCards.add(new LordD());
		spellcasterCards.add(new BlueDragonSummoner());
		spellcasterCards.add(new ApprenticeIllusionMagician());
		spellcasterCards.add(new DarkHorizon());
		spellcasterCards.add(new MachineFactory());
		spellcasterCards.add(new DarkPaladin());
		spellcasterCards.add(new WhiteMagicalHat());
		spellcasterCards.add(new LegendaryFlameLord());
		spellcasterCards.add(new BlizzardWarrior());
		spellcasterCards.add(new SwordsRevealing());
		spellcasterCards.add(new GrandSpellbookTower());
		spellcasterCards.add(new CallAtlanteans());
		spellcasterCards.add(new DoomShaman());
		spellcasterCards.add(new StatueAnguishPattern());
		spellcasterCards.add(new VoidVanishment());
		spellcasterCards.add(new VoidExpansion());
		spellcasterCards.add(new Mudballman());
		spellcasterCards.add(new MagicalBlast());
		spellcasterCards.add(new CosmoBrain());
		spellcasterCards.add(new DiffusionWaveMotion());
		spellcasterCards.add(new QueenDragunDjinn());
		spellcasterCards.add(new InariFire());
		spellcasterCards.add(new MagiciansRobe());
		spellcasterCards.add(new MagiciansRod());
		spellcasterCards.add(new RainbowMagician());
		spellcasterCards.add(new IrisEarthMother());
		spellcasterCards.add(new RainbowDarkDragon());
		spellcasterCards.add(new InfernoFireBlast());
		spellcasterCards.add(new SilentDoom());
		spellcasterCards.add(new SparkBlaster());
		spellcasterCards.add(new SpellShatteringArrow());
		spellcasterCards.add(new Wildfire());
		spellcasterCards.add(new CharcoalInpachi());
		spellcasterCards.add(new FlyingSaucer());
		spellcasterCards.add(new AlienTelepath());
		spellcasterCards.add(new CosmicHorrorGangiel());
		spellcasterCards.add(new BlastMagician());
		spellcasterCards.add(new WitchBlackRose());
		if (!DuelistMod.exodiaBtnBool)
		{
			spellcasterCards.add(new ExodiaHead());
			spellcasterCards.add(new ExodiaLA());
			spellcasterCards.add(new ExodiaLL());
			spellcasterCards.add(new ExodiaRA());
			spellcasterCards.add(new ExodiaRL());
			//spellcasterCards.add(new ExxodMaster());
			spellcasterCards.add(new LegendaryExodia());
			spellcasterCards.add(new ContractExodia());
			//spellcasterCards.add(new LegendExodia());
			spellcasterCards.add(new ExodiaNecross());
		}
		if (!DuelistMod.ojamaBtnBool)
		{
			spellcasterCards.add(new OjamaRed());
			spellcasterCards.add(new OjamaBlue());
			spellcasterCards.add(new OjamaBlack());
			spellcasterCards.add(new OjamaGreen());
			spellcasterCards.add(new OjamaKnight());
			spellcasterCards.add(new OjamaDeltaHurricane());
			spellcasterCards.add(new Ojamatch());
			spellcasterCards.add(new OjamaYellow());
			spellcasterCards.add(new OjamaDuo());
			spellcasterCards.add(new OjamaCountry());
			spellcasterCards.add(new OjamaKing());
			spellcasterCards.add(new OjamaTrio());
			spellcasterCards.add(new Ojamuscle());
			
		}
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			spellcasterCards.add(new Chill());
			spellcasterCards.add(new BiasedCognition());			
			spellcasterCards.add(new ColdSnap());
			spellcasterCards.add(new Loop());
			spellcasterCards.add(new Electrodynamics());
			spellcasterCards.add(new Coolheaded());
			spellcasterCards.add(new Capacitor());
			spellcasterCards.add(new Rainbow());
			spellcasterCards.add(new Barrage());
			spellcasterCards.add(new Storm());
			spellcasterCards.add(new CompileDriver());
			spellcasterCards.add(new Consume());
			spellcasterCards.add(new Blizzard());
			spellcasterCards.add(new Fission());
			spellcasterCards.add(new BallLightning());
			spellcasterCards.add(new MeteorStrike());
			spellcasterCards.add(new Darkness());
			spellcasterCards.add(new MultiCast());
			spellcasterCards.add(new DoomAndGloom());
			spellcasterCards.add(new Tempest());
			spellcasterCards.add(new Recursion());
			spellcasterCards.add(new Fusion());
			spellcasterCards.add(new StaticDischarge());
			spellcasterCards.add(new ThunderStrike());	
			spellcasterCards.add(new MachineLearning());
			spellcasterCards.add(new WhiteNoise());
			spellcasterCards.add(new SelfRepair());
			spellcasterCards.add(new Fission());
			spellcasterCards.add(new CreativeAI());
			spellcasterCards.add(new Leap());
			spellcasterCards.add(new Overclock());	
		}
		spellcasterDeck.fillPoolCards(spellcasterCards);		
		spellcasterDeck.fillArchetypeCards(spellcasterCards);
		DuelistMod.archetypeCards.addAll(spellcasterCards);
		return spellcasterCards;
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
