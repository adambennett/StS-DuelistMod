package duelistmod.helpers.crossover;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.*;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.DualPolarity;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import replayTheSpire.replayxover.chronobs;

public class ReplayHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from Replay the Spire to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new ByrdSkull(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new OozeArmor(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SecondSwordRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SnakeBasket(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SneckoScales(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ReplaySpearhead(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new VampiricSpirits(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new FrozenProgram(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new KingOfHearts(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new Carrot(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SolarPanel(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ByrdFeeder(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ElectricBlood(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new RaidersMask(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(ByrdSkull.ID);
		UnlockTracker.markRelicAsSeen(OozeArmor.ID);
		UnlockTracker.markRelicAsSeen(SecondSwordRelic.ID);
		UnlockTracker.markRelicAsSeen(SnakeBasket.ID);
		UnlockTracker.markRelicAsSeen(SneckoScales.ID);
		UnlockTracker.markRelicAsSeen(ReplaySpearhead.ID);
		UnlockTracker.markRelicAsSeen(VampiricSpirits.ID);
		UnlockTracker.markRelicAsSeen(FrozenProgram.ID);
		UnlockTracker.markRelicAsSeen(KingOfHearts.ID);
		UnlockTracker.markRelicAsSeen(Carrot.ID);
		UnlockTracker.markRelicAsSeen(SolarPanel.ID);
		UnlockTracker.markRelicAsSeen(ByrdFeeder.ID);
		UnlockTracker.markRelicAsSeen(ElectricBlood.ID);
		UnlockTracker.markRelicAsSeen(RaidersMask.ID);
	}
	
	public static ArrayList<AbstractCard> getAllCards()
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		cards.add(new Abandon());
		cards.add(new Hemogenesis());
		cards.add(new LifeLink());
		cards.add(new LimbFromLimb());
		cards.add(new Massacre());
		cards.add(new RunThrough());
		cards.add(new DefyDeath());
		cards.add(new DemonicInfusion());
		cards.add(new Eclipse());
		cards.add(new StrikeFromHell());
		cards.add(new LeadingStrike());
		cards.add(new ReplayReversal());
		cards.add(new ReplayStacked());
		cards.add(new MuscleTraining());
		cards.add(new CorrosionCurse());
		cards.add(new UndeathsTouch());
		cards.add(new ManyHands());
		cards.add(new AtomBomb());
		cards.add(new DrainingMist());
		cards.add(new FluidMovement());
		cards.add(new PoisonDarts());
		cards.add(new Necrosis());
		cards.add(new ToxinWave());
		cards.add(new HiddenBlade());
		cards.add(new SneakUp());
		cards.add(new ScrapShanks());
		cards.add(new TheWorks());
		cards.add(new FromAllSides());
		cards.add(new ExploitWeakness());
		cards.add(new ShivToss());
		cards.add(new SpeedTraining());
		cards.add(new TripWire());
		cards.add(new BagOfTricks());
		cards.add(new PoisonSmokescreen());
		cards.add(new com.megacrit.cardcrawl.mod.replay.cards.blue.PanicButton());
		cards.add(new MirrorShield());
		cards.add(new BasicCrystalCard());
		cards.add(new TimeBomb());
		cards.add(new ReplayRepulse());
		cards.add(new ReplayGoodbyeWorld());
		cards.add(new ReplayGash());
		cards.add(new ReplayOmegaCannon());
		cards.add(new ReplayRNGCard());
		cards.add(new FIFOQueue());
		cards.add(new ReplaySort());
		cards.add(new SystemScan());
		cards.add(new SolidLightProjector());
		cards.add(new CalculationTraining());
		cards.add(new ReflectiveLens());
		cards.add(new Crystallizer());
		cards.add(new ReroutePower());
		cards.add(new Improvise());
		cards.add(new PoisonedStrike());
		cards.add(new PrivateReserves());
		cards.add(new Specialist());
		cards.add(new AwakenedRitual());
		cards.add(new SurveyOptions());
		cards.add(new ReplayUltimateDefense());
		cards.add(new MidasTouch());
		cards.add(new Trickstab());
		cards.add(new ReplayBrewmasterCard());
		cards.add(new UncannyAura());
		cards.add(new Sssssssssstrike());
		cards.add(new Sssssssssshield());
		cards.add(new Necrogeddon());
		if (Loader.isModLoaded("Friendly_Minions_0987678")) {
			cards.add(new GrembosGang());
		}
		cards.add(new CommonCold());
		cards.add(new Hallucinations());
		cards.add(new Languid());
		cards.add(new Sickly());
		cards.add(new Delirium());
		cards.add(new Voices());
		cards.add(new LoomingEvil());
		cards.add(new Amnesia());
		cards.add(new SpreadingInfection());
		cards.add(new AbeCurse());
		cards.add(new Overencumbered());
		cards.add(new RestlessDread());
		cards.add(new Splinters());
		cards.add(new Doomed());
		cards.add(new DebtCurseIOU());
		cards.add(new FaultyEquipment());
		cards.add(new PotOfGreed());
		cards.add(new GhostDefend());
		cards.add(new GhostSwipe());
		cards.add(new GhostFetch());
		cards.add(new RitualComponent());
		cards.add(new DarkEchoRitualCard());
		cards.add(new IC_ScorchingBeam());
		cards.add(new IC_FireWall());
		cards.add(new IC_BasicHellfireCard());
		cards.add(new WeaponsOverheat());
		if (DuelistMod.isConspire) {
			cards.add(new DualPolarity());
		}
		if (DuelistMod.isDisciple) {
			chronobs.addCards();
		}
		return cards; 
	}
}
