package duelistmod.helpers.crossover;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import the_gatherer.cards.*;
import the_gatherer.relics.*;

public class GathererHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from The Gatherer to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new MistGenerator(), AbstractCardEnum.DUELIST);	
		BaseMod.addRelicToCustomPool(new FlyingFruit(), AbstractCardEnum.DUELIST);		
		
		UnlockTracker.markRelicAsSeen(MistGenerator.ID);		
		UnlockTracker.markRelicAsSeen(FlyingFruit.ID);				
	}
	
	public static ArrayList<AbstractCard> getAllCards()
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		cards.add(new Strike_Gatherer());
		cards.add(new Defend_Gatherer());
		cards.add(new FlowerWhip());
		cards.add(new Centralize());
		cards.add(new SpareBottle());
		cards.add(new AcidicSpray());
		cards.add(new AlchemyTrial());
		cards.add(new AromaProtection());
		cards.add(new BalancedGrowth());
		cards.add(new BambuSword());
		cards.add(new BigPouch());
		cards.add(new BlackTea());
		cards.add(new BomberForm());
		cards.add(new Bulldoze());
		cards.add(new CarefulStrike());
		cards.add(new CollectorsShot());
		cards.add(new ColorfulGarden());
		cards.add(new Convert());
		cards.add(new CoupDeGrace());
		cards.add(new CursedBlade());
		cards.add(new DrugPower());
		cards.add(new Duality());
		cards.add(new Duplicator());
		cards.add(new EchoOfNature());
		cards.add(new Enchant());
		cards.add(new EnergyBasket());
		cards.add(new Examine());
		cards.add(new FeelingFine());
		cards.add(new FlamingBottle());
		cards.add(new FlowerBeam());
		cards.add(new FlowerFalling());
		cards.add(new FlowerPower());
		cards.add(new FlowerShield());
		cards.add(new Frenzy());
		cards.add(new FruitForce());
		cards.add(new GatherMaterial());
		cards.add(new Glitched());
		cards.add(new GlowingPlant());
		cards.add(new GrassHammer());
		cards.add(new GrowBook());
		cards.add(new HeartToFruit());
		cards.add(new Herbalism());
		cards.add(new Light());
		cards.add(new Liquidism());
		cards.add(new Liquidism());
		cards.add(new LuckyClover());
		cards.add(new MindSearch());
		cards.add(new MiningStrike());
		cards.add(new Misfortune());
		cards.add(new Nutrients());
		cards.add(new OddSpoils());
		cards.add(new Overflowing());
		cards.add(new Photoscythe());
		cards.add(new PoisonMastery());
		cards.add(new Pollute());
		cards.add(new Polymorphism());
		cards.add(new QuickSynthesis());
		cards.add(new RecipeChange());
		cards.add(new RecoveryHerb());
		cards.add(new RottenStipe());
		cards.add(new SaveValuables());
		cards.add(new ScentOfRosmari());
		cards.add(new ScherryThrow());
		cards.add(new ScrollOfPurity());
		cards.add(new ScrollOfWall());
		cards.add(new SealedBomb());
		cards.add(new Shadow());
		cards.add(new SmartManeuver());
		cards.add(new Snatch());
		cards.add(new Solidify());
		cards.add(new StarFruit());
		cards.add(new StoneFence());
		cards.add(new TacticalStrike());
		cards.add(new Thrower());
		cards.add(new Transmute());
		cards.add(new TreeGrowth());
		cards.add(new UpgradeBag());
		cards.add(new Uplift());
		cards.add(new VenomBarrier());
		cards.add(new WitheringStrike());
		cards.add(new WoolGloves());
		return cards; 
	}
}
