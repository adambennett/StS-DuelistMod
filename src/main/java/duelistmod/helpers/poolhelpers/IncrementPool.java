package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.beast.DoomstarUlka;
import duelistmod.cards.pools.beast.GladiatorBeastTygerius;
import duelistmod.cards.pools.beast.GuardDog;
import duelistmod.cards.pools.beast.KinkaByo;
import duelistmod.cards.pools.dragons.Scapegoat;
import duelistmod.cards.pools.increment.Ankuriboh;
import duelistmod.cards.pools.increment.AstralKuriboh;
import duelistmod.cards.pools.increment.FiveStarTwilight;
import duelistmod.cards.pools.increment.FlameKuriboh;
import duelistmod.cards.pools.increment.Galactikuriboh;
import duelistmod.cards.pools.increment.GenerationNext;
import duelistmod.cards.pools.increment.Kuribabylon;
import duelistmod.cards.pools.increment.Kuribah;
import duelistmod.cards.pools.increment.Kuribandit;
import duelistmod.cards.pools.increment.Kuribee;
import duelistmod.cards.pools.increment.Kuribeh;
import duelistmod.cards.pools.increment.Kuriboo;
import duelistmod.cards.pools.increment.Kuribot;
import duelistmod.cards.pools.increment.Kuriphoton;
import duelistmod.cards.pools.increment.Kurivolt;
import duelistmod.cards.pools.increment.Linguriboh;
import duelistmod.cards.pools.increment.Magikuriboh;
import duelistmod.cards.pools.increment.Multiply;
import duelistmod.cards.pools.increment.OneKuriWay;
import duelistmod.cards.pools.increment.PerformapalBarokuriboh;
import duelistmod.cards.pools.increment.PerformapalClassikuriboh;
import duelistmod.cards.pools.increment.PerformapalKuribohble;
import duelistmod.cards.pools.increment.Sabatiel;
import duelistmod.cards.pools.increment.SkilledBrownMagician;
import duelistmod.cards.pools.increment.TranscendentWings;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.DigitalBug;
import duelistmod.cards.pools.naturia.MultiplicationOfAnts;
import duelistmod.cards.pools.warrior.BrushfireKnight;
import duelistmod.cards.pools.warrior.WhiteHowling;
import duelistmod.cards.pools.zombies.PrematureBurial;

public class IncrementPool 
{
	private static final String deckName = "Increment Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.oneRandom(3));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.twoRandom(3));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck incrementDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> incCards = new ArrayList<>();
		incCards.add(new AmuletAmbition());
		incCards.add(new BeastFangs());
		incCards.add(new ClearKuriboh());
		incCards.add(new DarkFusion());
		incCards.add(new DarkSimorgh());
		incCards.add(new DarklordSuperbia());
		incCards.add(new DoomstarUlka());
		incCards.add(new FlameTiger());
		incCards.add(new GeminiElf());
		incCards.add(new GladiatorBeastTygerius());
		incCards.add(new GoblinRemedy());
		incCards.add(new GuardDog());
		incCards.add(new HammerShot());
		incCards.add(new HeavyStorm());
		incCards.add(new JunkKuriboh());
		incCards.add(new Kuriboh());
		incCards.add(new Kuribohrn());
		incCards.add(new LegendaryFlameLord());
		incCards.add(new LightningVortex());
		incCards.add(new MultiplicationOfAnts());
		incCards.add(new PotAvarice());
		incCards.add(new PotDichotomy());
		incCards.add(new PotGenerosity());
		incCards.add(new RadiantMirrorForce());
		incCards.add(new RainbowKuriboh());
		incCards.add(new Scapegoat());
		incCards.add(new SmashingGround());
		incCards.add(new SphereKuriboh());
		incCards.add(new StrayLambs());
		incCards.add(new SwordsBurning());
		incCards.add(new BerserkerCrush());
		incCards.add(new PrematureBurial());
		incCards.add(new KinkaByo());
		incCards.add(new BigWaveSmallWave());
		incCards.add(new UltimateOffering());
		incCards.add(new InariFire());
		incCards.add(new WhiteHowling());
		incCards.add(new Wingedtortoise());
		incCards.add(new UndergroundArachnid());
		incCards.add(new CrossSwordBeetle());
		incCards.add(new DarkSpider());
		incCards.add(new HunterSpider());
		incCards.add(new MotherSpider());
		incCards.add(new DigitalBug());
		incCards.add(new ManEaterBug());
		incCards.add(new DoomDonuts());
		incCards.add(new FlameSwordsman());
		incCards.add(new BrushfireKnight());
		incCards.add(new BigFire());
		incCards.add(new GiantTrunade());

		incCards.add(new Multiply());
		incCards.add(new SkilledBrownMagician());
		incCards.add(new Kuriboo());
		incCards.add(new Kuribeh());
		incCards.add(new Kuribee());
		incCards.add(new Kuribah());
		incCards.add(new Kuribabylon());
		incCards.add(new AstralKuriboh());
		incCards.add(new Galactikuriboh());
		incCards.add(new Magikuriboh());
		incCards.add(new PerformapalBarokuriboh());
		incCards.add(new PerformapalClassikuriboh());
		incCards.add(new PerformapalKuribohble());
		incCards.add(new Kuribot());
		incCards.add(new Linguriboh());
		incCards.add(new FiveStarTwilight());
		incCards.add(new OneKuriWay());
		incCards.add(new Kuribandit());
		incCards.add(new Ankuriboh());
		incCards.add(new FlameKuriboh());
		incCards.add(new GenerationNext());
		incCards.add(new Kuriphoton());
		incCards.add(new Kurivolt());
		incCards.add(new TranscendentWings());
		incCards.add(new Sabatiel());

		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
		{
			//incCards.add(new Token());
		}
		//incrementDeck.fillPoolCards(incCards);
		return incCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		//deck.fillPoolCards(pool); 
		return pool;
	}
}
