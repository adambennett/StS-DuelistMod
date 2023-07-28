package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.insects.VenomShot;
import duelistmod.cards.pools.naturia.*;
import duelistmod.cards.pools.warrior.AfterGenocide;

public class NaturiaPool 
{
	private static String deckName = "Naturia Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(6));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(6));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck natureDeck = DuelistMod.starterDeckNamesMap.get(deckName);		
		ArrayList<AbstractCard> naturiaCards = new ArrayList<AbstractCard>();
		naturiaCards.add(new NaturiaCliff());
		naturiaCards.add(new NaturiaDragonfly());
		naturiaCards.add(new NaturiaGuardian());
		naturiaCards.add(new NaturiaHorneedle());
		naturiaCards.add(new NaturiaLandoise());
		naturiaCards.add(new NaturiaMantis()); 
		naturiaCards.add(new NaturiaPineapple());
		naturiaCards.add(new NaturiaPumpkin());
		naturiaCards.add(new NaturiaRosewhip());
		naturiaCards.add(new NaturiaSacredTree());
		naturiaCards.add(new NaturiaStrawberry());
		naturiaCards.add(new GracefulCharity());
		naturiaCards.add(new HeartUnderdog());
		naturiaCards.add(new PotDuality());
		naturiaCards.add(new AcidTrapHole());
		naturiaCards.add(new BottomlessTrapHole());
		naturiaCards.add(new Spore());
		naturiaCards.add(new VenomShot());
		naturiaCards.add(new MillenniumScorpion());
		naturiaCards.add(new Wildfire());
		naturiaCards.add(new AfterGenocide());
		naturiaCards.add(new MultiplicationOfAnts());
		naturiaCards.add(new Wiretap());
		naturiaCards.add(new Alpacaribou());	
		naturiaCards.add(new Anteater());	
		naturiaCards.add(new BarkionBark());	
		naturiaCards.add(new BrainCrusher());	
		naturiaCards.add(new Canyon());	
		naturiaCards.add(new ClosedForest());	
		naturiaCards.add(new CrystalRose());	
		naturiaCards.add(new DigitalBug());	
		naturiaCards.add(new ExterioFang());	
		naturiaCards.add(new FossilDig());	
		naturiaCards.add(new FossilExcavation());	
		naturiaCards.add(new HuntingInstinct());
		naturiaCards.add(new LairWire());
		naturiaCards.add(new LeodrakeMane());
		naturiaCards.add(new LonefireBlossom());
		naturiaCards.add(new LuminousMoss());
		naturiaCards.add(new NaturalDisaster());
		naturiaCards.add(new NatureReflection());
		naturiaCards.add(new NaturiaAntjaw());
		naturiaCards.add(new NaturiaBambooShoot());
		naturiaCards.add(new NaturiaBarkion());
		naturiaCards.add(new NaturiaBeans());
		naturiaCards.add(new NaturiaBeetle());
		naturiaCards.add(new NaturiaBrambi());
		naturiaCards.add(new NaturiaButterfly());
		naturiaCards.add(new NaturiaCherries());
		naturiaCards.add(new NaturiaCosmobeet());
		naturiaCards.add(new NaturiaEggplant());
		naturiaCards.add(new NaturiaExterio());
		naturiaCards.add(new NaturiaGaiastrio());
		naturiaCards.add(new NaturiaHydrangea());
		naturiaCards.add(new NaturiaLadybug());
		naturiaCards.add(new NaturiaLeodrake());
		naturiaCards.add(new NaturiaMarron());
		naturiaCards.add(new NaturiaMosquito());
		naturiaCards.add(new NaturiaParadizo());
		naturiaCards.add(new NaturiaRagweed());
		naturiaCards.add(new NaturiaRock());
		naturiaCards.add(new NaturiaSpiderfang());
		naturiaCards.add(new NaturiaStagBeetle());
		naturiaCards.add(new NaturiaStinkbug());
		naturiaCards.add(new NaturiaStrawberry());
		naturiaCards.add(new NaturiaSunflower());
		naturiaCards.add(new NaturiaTulip());
		naturiaCards.add(new NaturiaVein());
		naturiaCards.add(new NaturiaWhiteOak());
		naturiaCards.add(new SeedSacredTree());
		naturiaCards.add(new SeismicShockwave());
		naturiaCards.add(new SpacetimeTranscendence());
		naturiaCards.add(new SummoningSwarm());
		naturiaCards.add(new WildNatureRelease());
		naturiaCards.add(new WormBait());
		naturiaCards.add(new Pollinosis());
		naturiaCards.add(new DemiseLand());
		naturiaCards.add(new SurvivalInstinct());
		naturiaCards.add(new ConvulsionNature());
		naturiaCards.add(new NaturiaForest());
		naturiaCards.add(new FuryFire());
		naturiaCards.add(new CopyPlant());
		naturiaCards.add(new FrozenRose());
		//naturiaCards.add(new DeepDarkTrapHole());
		//naturiaCards.add(new DoubleTrapHole());
		//naturiaCards.add(new TraptrixTrapHole());

		if (DuelistMod.baseGameCards && DuelistMod.isNotAllCardsPoolType())
		{
						
		}
		
		natureDeck.fillPoolCards(naturiaCards);
		//DuelistMod.archetypeCards.addAll(naturiaCards);
		return naturiaCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic("Naturia Deck")); }
		else { pool.addAll(BasicPool.fullBasic("Naturia Deck")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
