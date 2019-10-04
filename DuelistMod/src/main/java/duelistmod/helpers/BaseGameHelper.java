package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BaseGameHelper 
{
	public static ArrayList<AbstractCard> getAllBaseGameCards()
	{
		return getAllBaseGameCards(false);
	}
	
	public static ArrayList<AbstractCard> getAllBaseGameCards(boolean colorless)
	{
		ArrayList<AbstractCard> toFill = new ArrayList<AbstractCard>();
		toFill.addAll(getAllIroncladCards());
		toFill.addAll(getAllSilentCards());
		toFill.addAll(getAllDefectCards());
		if (colorless) { toFill.addAll(getAllColorlessCards()); }
		return toFill;
	}
	
	public static AbstractCard getColorlessCard()
	{
		ArrayList<AbstractCard> colorless = new ArrayList<AbstractCard>();
		for (AbstractCard c : getAllColorlessCards()){ colorless.add(c); }		
		AbstractCard card = colorless.get(AbstractDungeon.cardRandomRng.random(colorless.size() - 1));
		return card;
	}

	public static AbstractCard getRedCard()
	{
		ArrayList<AbstractCard> reds = new ArrayList<AbstractCard>();
		for (AbstractCard c : getAllIroncladCards()){ reds.add(c); }		
		AbstractCard card = reds.get(AbstractDungeon.cardRandomRng.random(reds.size() - 1));
		return card;
	}
	
	public static AbstractCard getBlueCard()
	{
		ArrayList<AbstractCard> blues = new ArrayList<AbstractCard>();
		for (AbstractCard c : getAllDefectCards()){ blues.add(c); }		
		AbstractCard card = blues.get(AbstractDungeon.cardRandomRng.random(blues.size() - 1));
		return card;
	}
	
	public static AbstractCard getGreenCard()
	{
		ArrayList<AbstractCard> greens = new ArrayList<AbstractCard>();
		for (AbstractCard c : getAllSilentCards()){ greens.add(c); }		
		AbstractCard card = greens.get(AbstractDungeon.cardRandomRng.random(greens.size() - 1));
		return card;
	}
	
	public static ArrayList<AbstractCard> getAllColorlessCards()
	{
		ArrayList<AbstractCard> toFill = new ArrayList<AbstractCard>();
		toFill.add(new Apotheosis());
        toFill.add(new BandageUp());
        toFill.add(new Blind());
        toFill.add(new Chrysalis());
        toFill.add(new DarkShackles());
        toFill.add(new DeepBreath());
        toFill.add(new Discovery());
        toFill.add(new DramaticEntrance());
        toFill.add(new Enlightenment());
        toFill.add(new Finesse());
        toFill.add(new FlashOfSteel());
        toFill.add(new Forethought());
        toFill.add(new GoodInstincts());
        toFill.add(new HandOfGreed());
        toFill.add(new Impatience());
        toFill.add(new JackOfAllTrades());
        toFill.add(new Madness());
        toFill.add(new Magnetism());
        toFill.add(new MasterOfStrategy());
        toFill.add(new Mayhem());
        toFill.add(new Metamorphosis());
        toFill.add(new MindBlast());
        toFill.add(new Panacea());
        toFill.add(new Panache());
        toFill.add(new PanicButton());
        toFill.add(new Purity());
        toFill.add(new SadisticNature());
        toFill.add(new SecretTechnique());
        toFill.add(new SecretWeapon());
        toFill.add(new SwiftStrike());
        toFill.add(new TheBomb());
        toFill.add(new ThinkingAhead());
        toFill.add(new Transmutation());
        toFill.add(new Trip());
        toFill.add(new Violence());
        toFill.add(new Burn());
        toFill.add(new Dazed());
        toFill.add(new Slimed());
        toFill.add(new VoidCard());
        toFill.add(new Wound());
        toFill.add(new Apparition());
        toFill.add(new Bite());
        toFill.add(new JAX());
        toFill.add(new RitualDagger());
        toFill.add(new Shiv());
		return toFill;
	}
	
	
	
	public static ArrayList<AbstractCard> getAllIroncladCards()
	{
		ArrayList<AbstractCard> toFill = new ArrayList<AbstractCard>();
		toFill.add(new Anger());
		toFill.add(new Armaments());
		toFill.add(new Barricade());
		toFill.add(new Bash());
		toFill.add(new BattleTrance());
		toFill.add(new Berserk());
		toFill.add(new BloodForBlood());
		toFill.add(new Bloodletting());
		toFill.add(new Bludgeon());
		toFill.add(new BodySlam());
		toFill.add(new Brutality());
		toFill.add(new BurningPact());
		toFill.add(new Carnage());
		toFill.add(new Clash());
		toFill.add(new Cleave());
		toFill.add(new Clothesline());
		toFill.add(new Combust());
		toFill.add(new Corruption());
		toFill.add(new DarkEmbrace());
		toFill.add(new Defend_Red());
		toFill.add(new DemonForm());
		toFill.add(new Disarm());
		toFill.add(new DoubleTap());
		toFill.add(new Dropkick());
		toFill.add(new DualWield());
		toFill.add(new Entrench());
		toFill.add(new Evolve());
		toFill.add(new Exhume());
		toFill.add(new Feed());
		toFill.add(new FeelNoPain());
		toFill.add(new FiendFire());
		toFill.add(new FireBreathing());
		toFill.add(new FlameBarrier());
		toFill.add(new Flex());
		toFill.add(new GhostlyArmor());
		toFill.add(new Havoc());
		toFill.add(new Headbutt());
		toFill.add(new HeavyBlade());
		toFill.add(new Hemokinesis());
		toFill.add(new Immolate());
		toFill.add(new Impervious());
		toFill.add(new InfernalBlade());
		toFill.add(new Inflame());
		toFill.add(new Intimidate());
		toFill.add(new IronWave());
		toFill.add(new Juggernaut());
		toFill.add(new LimitBreak());
		toFill.add(new Metallicize());
		toFill.add(new Offering());
		toFill.add(new PerfectedStrike());
		toFill.add(new PommelStrike());
		toFill.add(new PowerThrough());
		toFill.add(new Pummel());
		toFill.add(new Rage());
		toFill.add(new Rampage());
		toFill.add(new Reaper());
		toFill.add(new RecklessCharge());
		toFill.add(new Rupture());
		toFill.add(new SearingBlow());
		toFill.add(new SecondWind());
		toFill.add(new SeeingRed());
		toFill.add(new Sentinel());
		toFill.add(new SeverSoul());
		toFill.add(new Shockwave());
		toFill.add(new ShrugItOff());
		toFill.add(new SpotWeakness());
		toFill.add(new Strike_Red());
		toFill.add(new SwordBoomerang());
		toFill.add(new ThunderClap());
		toFill.add(new TrueGrit());
		toFill.add(new TwinStrike());
		toFill.add(new Uppercut());
		toFill.add(new Warcry());
		toFill.add(new Whirlwind());
		toFill.add(new WildStrike());
		return toFill;
	}
	
	public static ArrayList<AbstractCard> getAllSilentCards()
	{
		ArrayList<AbstractCard> toFill = new ArrayList<AbstractCard>();
		toFill.add(new Accuracy());
		toFill.add(new Acrobatics());
		toFill.add(new Adrenaline());
		toFill.add(new AfterImage());
		toFill.add(new Alchemize());
		toFill.add(new AllOutAttack());
		toFill.add(new AThousandCuts());
		toFill.add(new Backflip());
		toFill.add(new Backstab());
		toFill.add(new Bane());
		toFill.add(new BladeDance());
		toFill.add(new Blur());
		toFill.add(new BouncingFlask());
		toFill.add(new BulletTime());
		toFill.add(new Burst());
		toFill.add(new CalculatedGamble());
		toFill.add(new Caltrops());
		toFill.add(new Catalyst());
		toFill.add(new Choke());
		toFill.add(new CloakAndDagger());
		toFill.add(new Concentrate());
		toFill.add(new CorpseExplosion());
		toFill.add(new CripplingPoison());
		toFill.add(new DaggerSpray());
		toFill.add(new DaggerThrow());
		toFill.add(new Dash());
		toFill.add(new DeadlyPoison());
		toFill.add(new Defend_Green());
		toFill.add(new Deflect());
		toFill.add(new DieDieDie());
		toFill.add(new Distraction());
		toFill.add(new DodgeAndRoll());
		toFill.add(new Doppelganger());
		toFill.add(new EndlessAgony());
		toFill.add(new Envenom());
		toFill.add(new EscapePlan());
		toFill.add(new Eviscerate());
		toFill.add(new Expertise());
		toFill.add(new Finisher());
		toFill.add(new Flechettes());
		toFill.add(new FlyingKnee());
		toFill.add(new Footwork());
		toFill.add(new GlassKnife());
		toFill.add(new GrandFinale());
		toFill.add(new HeelHook());
		toFill.add(new InfiniteBlades());
		toFill.add(new LegSweep());
		toFill.add(new Malaise());
		toFill.add(new MasterfulStab());
		toFill.add(new Neutralize());
		toFill.add(new Nightmare());
		toFill.add(new NoxiousFumes());
		toFill.add(new Outmaneuver());
		toFill.add(new PhantasmalKiller());
		toFill.add(new PiercingWail());
		toFill.add(new PoisonedStab());
		toFill.add(new Predator());
		toFill.add(new Prepared());
		toFill.add(new QuickSlash());
		toFill.add(new Reflex());
		toFill.add(new RiddleWithHoles());
		toFill.add(new Setup());
		toFill.add(new Skewer());
		toFill.add(new Slice());
		toFill.add(new StormOfSteel());
		toFill.add(new Strike_Green());
		toFill.add(new SuckerPunch());
		toFill.add(new Survivor());
		toFill.add(new Tactician());
		toFill.add(new Terror());
		toFill.add(new ToolsOfTheTrade());
		toFill.add(new SneakyStrike());
		toFill.add(new Unload());
		toFill.add(new WellLaidPlans());
		toFill.add(new WraithForm());
		return toFill;
	}
	
	public static ArrayList<AbstractCard> getAllDefectCards()
	{
		ArrayList<AbstractCard> toFill = new ArrayList<AbstractCard>();
		toFill.add(new Aggregate());
		toFill.add(new AllForOne());
		toFill.add(new Amplify());
		toFill.add(new AutoShields());
		toFill.add(new BallLightning());
		toFill.add(new Barrage());
		toFill.add(new BeamCell());
		toFill.add(new BiasedCognition());
		toFill.add(new Blizzard());
		toFill.add(new BootSequence());
		toFill.add(new Buffer());
		toFill.add(new Capacitor());
		toFill.add(new Chaos());
		toFill.add(new Chill());
		toFill.add(new Claw());
		toFill.add(new ColdSnap());
		toFill.add(new CompileDriver());
		toFill.add(new ConserveBattery());
		toFill.add(new Consume());
		toFill.add(new Coolheaded());
		toFill.add(new CoreSurge());
		toFill.add(new CreativeAI());
		toFill.add(new Darkness());
		toFill.add(new Defend_Blue());
		toFill.add(new Defragment());
		toFill.add(new DoomAndGloom());
		toFill.add(new DoubleEnergy());
		toFill.add(new Dualcast());
		toFill.add(new EchoForm());
		toFill.add(new Electrodynamics());
		toFill.add(new Fission());
		toFill.add(new ForceField());
		toFill.add(new FTL());
		toFill.add(new Fusion());
		toFill.add(new GeneticAlgorithm());
		toFill.add(new Glacier());
		toFill.add(new GoForTheEyes());
		toFill.add(new Heatsinks());
		toFill.add(new HelloWorld());
		toFill.add(new Hologram());
		toFill.add(new Hyperbeam());
		toFill.add(new Leap());
		toFill.add(new LockOn());
		toFill.add(new Loop());
		toFill.add(new MachineLearning());
		toFill.add(new Melter());
		toFill.add(new MeteorStrike());
		toFill.add(new MultiCast());
		toFill.add(new Overclock());
		toFill.add(new Rainbow());
		toFill.add(new Reboot());
		toFill.add(new Rebound());
		toFill.add(new Recursion());
		toFill.add(new Recycle());
		toFill.add(new ReinforcedBody());
		toFill.add(new Reprogram());
		toFill.add(new RipAndTear());
		toFill.add(new Scrape());
		toFill.add(new Seek());
		toFill.add(new SelfRepair());
		toFill.add(new Skim());
		toFill.add(new Stack());
		toFill.add(new StaticDischarge());
		toFill.add(new SteamBarrier());
		toFill.add(new Storm());
		toFill.add(new Streamline());
		toFill.add(new Strike_Blue());
		toFill.add(new Sunder());
		toFill.add(new SweepingBeam());
		toFill.add(new Tempest());
		toFill.add(new ThunderStrike());
		toFill.add(new Turbo());
		toFill.add(new Equilibrium());
		toFill.add(new WhiteNoise());
		toFill.add(new Zap());
		return toFill;
	}
	

}
