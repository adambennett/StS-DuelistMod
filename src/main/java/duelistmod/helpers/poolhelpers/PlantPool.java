package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;

public class PlantPool 
{
	private static String deckName = "Plant Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(7));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(7));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
	
		cards.add(new AngelTrumpeter());
		cards.add(new FallenAngelRoses());
		cards.add(new Firegrass());
		cards.add(new Gigaplant());
		cards.add(new Invigoration());
		cards.add(new JerryBeansMan());
		//cards.add(new NaturiaGuardian());
		//cards.add(new NaturiaPineapple());
		//cards.add(new NaturiaPumpkin());
		//cards.add(new NaturiaRosewhip());
		//cards.add(new NaturiaStrawberry());
		cards.add(new PredaplantChimerafflesia());
		cards.add(new PredaplantChlamydosundew());
		cards.add(new PredaplantDrosophyllum());
		cards.add(new PredaplantFlytrap());
		cards.add(new PredaplantPterapenthes());
		cards.add(new PredaplantSarraceniant());
		cards.add(new PredaplantSpinodionaea());
		cards.add(new SuperSolarNutrient());
		cards.add(new WorldCarrot());
		cards.add(new BirdRoses());
		cards.add(new BlackRoseDragon());
		cards.add(new BlackRoseMoonlight());
		cards.add(new BlueRoseDragon());
		cards.add(new CopyPlant());
		cards.add(new FrozenRose());
		cards.add(new MarkRose());
		cards.add(new QueenAngelRoses());
		cards.add(new RagingMadPlants());
		cards.add(new RedRoseDragon());
		cards.add(new RevivalRose());
		cards.add(new RoseLover());
		cards.add(new RoseWitch());
		cards.add(new SplendidRose());
		cards.add(new Spore());
		cards.add(new TwilightRoseKnight());
		cards.add(new WhiteRoseDragon());
		cards.add(new AngelTrumpeter());
		cards.add(new RoseArcher());
		cards.add(new RosePaladin());
		cards.add(new WitchBlackRose());
		cards.add(new BloomingDarkestRose());
		cards.add(new Predaponics());
		cards.add(new Predapruning());
		cards.add(new VioletCrystal());
		cards.add(new BeastFangs());
		cards.add(new SangaEarth());
		cards.add(new GracefulCharity());
		cards.add(new HeartUnderdog());
		cards.add(new PotDuality());
		cards.add(new AcidTrapHole());
		cards.add(new BottomlessTrapHole());
		cards.add(new CheerfulCoffin());
		cards.add(new WorldTree());
		cards.add(new MiracleFertilizer());
		cards.add(new VenomShot());
		cards.add(new BlossomBombardment());
		cards.add(new ThornMalice());
		cards.add(new SilverApples());
		cards.add(new Reinforcements());
		cards.add(new KamionTimelord());
		cards.add(new WormApocalypse());
		cards.add(new CactusBouncer());
		cards.add(new Inmato());
		cards.add(new PlantFoodChain());
		cards.add(new SeedCannon());
		cards.add(new BotanicalLion());
		cards.add(new BotanicalGirl());
		cards.add(new LordPoison());
		cards.add(new DarkworldThorns());
		cards.add(new Mudora());
		cards.add(new MudGolem());
		cards.add(new CrystalRose());
		cards.add(new LonefireBlossom());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			//cards.add(new Token());
		}

		deck.fillPoolCards(cards);
		return cards;
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
