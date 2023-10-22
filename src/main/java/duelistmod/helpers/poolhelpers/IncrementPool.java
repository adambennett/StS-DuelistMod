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
import duelistmod.cards.pools.beast.SeaKoala;
import duelistmod.cards.pools.dragons.Scapegoat;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.DigitalBug;
import duelistmod.cards.pools.naturia.MultiplicationOfAnts;
import duelistmod.cards.pools.warrior.WhiteHowling;
import duelistmod.cards.pools.zombies.Relinkuriboh;

public class IncrementPool 
{
	private static String deckName = "Increment Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(3));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(3));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck incrementDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> incCards = new ArrayList<AbstractCard>();
		incCards.add(new AmuletAmbition());
		incCards.add(new ArsenalBug());
		incCards.add(new BeastFangs());
		incCards.add(new ClearKuriboh());
		incCards.add(new CosmoBrain());
		incCards.add(new CrossSwordBeetle());
		incCards.add(new DarkFusion());
		incCards.add(new DarkSimorgh());
		incCards.add(new DarkSpider());
		incCards.add(new DarklordSuperbia());
		incCards.add(new DigitalBug());
		incCards.add(new DoomstarUlka());
		incCards.add(new FlameTiger());
		incCards.add(new Forest());
		incCards.add(new GemKnightAmethyst());
		incCards.add(new GeminiElf());
		incCards.add(new GladiatorBeastTygerius());
		incCards.add(new GoblinRemedy());
		incCards.add(new GoldenApples());
		incCards.add(new GuardDog());
		incCards.add(new HammerShot());
		incCards.add(new HeavyStorm());
		incCards.add(new HunterSpider());
		incCards.add(new InariFire());
		incCards.add(new JunkKuriboh());
		incCards.add(new Kuriboh());
		incCards.add(new Kuribohrn());
		incCards.add(new LegendaryFlameLord());
		incCards.add(new LightningVortex());
		incCards.add(new LostBlueBreaker());
		incCards.add(new ManEaterBug());
		incCards.add(new MotherSpider());
		incCards.add(new MultiplicationOfAnts());
		incCards.add(new PotAvarice());
		incCards.add(new PotDichotomy());
		incCards.add(new PotGenerosity());
		incCards.add(new RadiantMirrorForce());
		incCards.add(new RainbowKuriboh());
		incCards.add(new Scapegoat());
		incCards.add(new SeaKoala());
		incCards.add(new SilverApples());
		incCards.add(new SmashingGround());
		incCards.add(new SphereKuriboh());
		incCards.add(new Spore());
		incCards.add(new StrayLambs());
		incCards.add(new SwordsBurning());
		incCards.add(new UltimateOffering());
		incCards.add(new UndergroundArachnid());
		incCards.add(new WhiteHowling());
		incCards.add(new Wingedtortoise());

		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
		{
			//incCards.add(new Token());
		}
		incrementDeck.fillPoolCards(incCards);
		return incCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
