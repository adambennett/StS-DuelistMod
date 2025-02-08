package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.fiend.AcidTrapHole;
import duelistmod.cards.pools.fiend.CheerfulCoffin;
import duelistmod.cards.pools.gusto.DustStormOfGusto;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.cards.pools.plant.GreedyVenomFusionDragon;
import duelistmod.cards.pools.plant.PredaplantAmbulomelides;
import duelistmod.cards.pools.plant.PredaplantBanksiogre;
import duelistmod.cards.pools.plant.PredaplantBufolicula;
import duelistmod.cards.pools.plant.PredaplantByblisp;
import duelistmod.cards.pools.plant.PredaplantChimerafflesia;
import duelistmod.cards.pools.plant.PredaplantChlamydosundew;
import duelistmod.cards.pools.plant.PredaplantCordyceps;
import duelistmod.cards.pools.plant.PredaplantDarlingtoniaCobra;
import duelistmod.cards.pools.plant.PredaplantDragostapelia;
import duelistmod.cards.pools.plant.PredaplantDrosophyllum;
import duelistmod.cards.pools.plant.PredaplantFlytrap;
import duelistmod.cards.pools.plant.PredaplantHeliamphorhynchus;
import duelistmod.cards.pools.plant.PredaplantMorayNepenthes;
import duelistmod.cards.pools.plant.PredaplantOphrysScorpio;
import duelistmod.cards.pools.plant.PredaplantPterapenthes;
import duelistmod.cards.pools.plant.PredaplantSarraceniant;
import duelistmod.cards.pools.plant.PredaplantSpiderOrchid;
import duelistmod.cards.pools.plant.PredaplantSpinodionaea;
import duelistmod.cards.pools.plant.PredaplantSquidDrosera;
import duelistmod.cards.pools.plant.PredaplantTriantis;
import duelistmod.cards.pools.plant.PredaplantTriphyoverutum;
import duelistmod.cards.pools.plant.PredaplantVerteAnaconda;
import duelistmod.cards.pools.plant.Predaponics;
import duelistmod.cards.pools.plant.Predapractice;
import duelistmod.cards.pools.plant.Predapruning;
import duelistmod.cards.pools.plant.StarvingVenomPredapowerFusionDragon;

public class PlantPool {
	public static ArrayList<AbstractCard> oneRandom() {
        return new ArrayList<>(GlobalPoolHelper.oneRandom(6));
	}
	
	public static ArrayList<AbstractCard> twoRandom() {
        return new ArrayList<>(GlobalPoolHelper.twoRandom(6));
	}
	
	public static ArrayList<AbstractCard> deck() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

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
		cards.add(new PredaplantVerteAnaconda());
		cards.add(new PredaplantCordyceps());
		cards.add(new GreedyVenomFusionDragon());
		cards.add(new PredaplantDarlingtoniaCobra());
		cards.add(new PredaplantHeliamphorhynchus());
		cards.add(new PredaplantDragostapelia());
		cards.add(new PredaplantTriphyoverutum());
		cards.add(new PredaplantTriantis());
		cards.add(new PredaplantSquidDrosera());
		cards.add(new PredaplantMorayNepenthes());
		cards.add(new PredaplantOphrysScorpio());
		cards.add(new StarvingVenomPredapowerFusionDragon());
		cards.add(new PredaplantSpiderOrchid());
		cards.add(new PredaplantByblisp());
		cards.add(new PredaplantBufolicula());
		cards.add(new PredaplantBanksiogre());
		cards.add(new Predapractice());
		cards.add(new PredaplantAmbulomelides());
		cards.add(new DustStormOfGusto());

		// 3 new plants
		//
		//
		//
		//

		// 2 new predaplants
		
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType()) {
			//cards.add(new Token());
		}
		return cards;
	}
	
	public static  ArrayList<AbstractCard> basic() {
		ArrayList<AbstractCard> pool = new ArrayList<>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) {
			pool.addAll(BasicPool.smallBasic(""));
		} else {
			pool.addAll(BasicPool.fullBasic(""));
		}
		return pool;
	}
}
