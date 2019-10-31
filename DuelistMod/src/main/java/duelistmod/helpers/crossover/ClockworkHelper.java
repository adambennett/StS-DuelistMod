package duelistmod.helpers.crossover;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.BaseMod;
import clockworkmod.cards.*;
import clockworkmod.relics.RubberBall;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;

public class ClockworkHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from The Clockwork to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new RubberBall(), AbstractCardEnum.DUELIST);	
	}
	
	public static ArrayList<AbstractCard> getAllCards()
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		cards.add(new Shield());
        cards.add(new Strike());
        cards.add(new Tinker());
        cards.add(new WarmUp());
        cards.add(new AdaptiveCannon());
        cards.add(new ChargingBlade());
        cards.add(new CogToss());
        cards.add(new ForgeStrike());
        cards.add(new Friction());
        cards.add(new Pluck());
        cards.add(new SnapKeep());
        cards.add(new Redundancy());
        cards.add(new Roundhouse());
        cards.add(new AdaptiveShield());
        cards.add(new ChargingShield());
        cards.add(new CogBarrier());
        cards.add(new CogShield());
        cards.add(new Enhance());
        cards.add(new Improve());
        cards.add(new Minimalism());
        cards.add(new ReserveSystems());
        cards.add(new SystemHardening());
        cards.add(new SystemScan());
        cards.add(new Efficiency());
        cards.add(new EnthusiasticStrike());
        cards.add(new FalseStart());
        cards.add(new HeadStart());
        cards.add(new HoardingStrike());
        cards.add(new MechanicalMass());
        cards.add(new PerfectedStrike_Clockwork());
        cards.add(new QuantumStrike());
        cards.add(new RitualDaggerMk0());
        cards.add(new SearchingStrike());
        cards.add(new SearingStrike());
        cards.add(new SmithsMallet());
        cards.add(new SpurOn());
        cards.add(new StasisBreak());
        cards.add(new TiltAWhirl());
        cards.add(new Clone());
        cards.add(new CraftBloodPhial());
        cards.add(new CraftCopperScales());
        cards.add(new CraftSomewhatSmoothStone());
        cards.add(new CraftVarja());
        cards.add(new Downsize());
        cards.add(new InertialShield());
        cards.add(new MechanicalBulk());
        cards.add(new PerfectedShield());
        cards.add(new RampUp());
        cards.add(new Scour());
        cards.add(new Selectivity());
        cards.add(new SpringShield());
        cards.add(new TestingEnvironment());
        cards.add(new TimeOut());
        cards.add(new AssemblyLine());
        cards.add(new ElasticNanites());
        cards.add(new LiteraryArmor());
        cards.add(new LogisticRegression());
        cards.add(new OccamsRazor());
        cards.add(new RevUp());
        cards.add(new CraftQuicksilverHourglass());
        cards.add(new KaliMa());
        cards.add(new Randomization());
        cards.add(new SiegeStrike());
        cards.add(new Strangle());
        cards.add(new WindupStrike());
        cards.add(new AeonShield());
        cards.add(new CraftMomentumEngine());
        cards.add(new Halt());
        cards.add(new Inspiration());
        cards.add(new TimeWalk());
        cards.add(new DefectiveParts());
        cards.add(new PerfectForm());
        cards.add(new ReactiveNanites());
        cards.add(new UnlimitedCogworks());
        cards.add(new ZeroPointEnergy());
		return cards; 
	}
}
