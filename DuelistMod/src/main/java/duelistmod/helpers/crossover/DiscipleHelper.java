package duelistmod.helpers.crossover;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import chronomuncher.cards.*;
import chronomuncher.relics.*;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;

public class DiscipleHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from The Disciple to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new BlueBox(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new PaperTurtyl(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SlipperyGoo(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new HangingClock(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new Metronome(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(BlueBox.ID);
		UnlockTracker.markRelicAsSeen(PaperTurtyl.ID);
		UnlockTracker.markRelicAsSeen(HangingClock.ID);
		UnlockTracker.markRelicAsSeen(SlipperyGoo.ID);
		UnlockTracker.markRelicAsSeen(Metronome.ID);
		
	}
	
	public static ArrayList<AbstractCard> getAllCards()
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		cards.add(new SwitchReapSow());
        cards.add(new SwitchExoVibe());
        cards.add(new SwitchGoo());
        cards.add(new SwitchSavings());
        // cards.add(new SwitchSharpShooter());
        // cards.add(new ASecondTooLate());
        cards.add(new Accelerando());
        cards.add(new Accruing());
        // cards.add(new AcidicGoo());
        cards.add(new Adagio());
        cards.add(new Allargando());
        cards.add(new Allegretto());
        cards.add(new Allegro());
        cards.add(new AlternateTimeline());
        cards.add(new Analog());
        cards.add(new BackFourSeconds());
        cards.add(new Backlash());
        cards.add(new BeatsPerMinute());
        cards.add(new BiteCommand());
        // cards.add(new BiteofTime());
        cards.add(new BlueShift());
        cards.add(new Break());
        cards.add(new Chronoelasticity());
        // cards.add(new CoatedVibrissa());
        // cards.add(new ClockandLoad());
        // cards.add(new CraftedGuard());
        cards.add(new CrunchTime());
        // cards.add(new OldCrunchTime());
        cards.add(new Cuckoo());
        cards.add(new Defend_Bronze());
        cards.add(new Echonomics());
        cards.add(new Echoward());
        cards.add(new Engulf());
        cards.add(new EscortCommand());
        // cards.add(new Exocoating());
        cards.add(new Facsimile());
        // cards.add(new FeedbackCycle());
        // cards.add(new Flashback());
        cards.add(new Flay());
        // cards.add(new FollowThrough());
        // cards.add(new Foolish());
        // cards.add(new ForgedGuard());
        cards.add(new FormalWear());
        cards.add(new Fragmentalize());
        cards.add(new Grave());
        // cards.add(new GuardCommand());
        cards.add(new HandsUp());
        cards.add(new Keepsakes());
        cards.add(new Largo());
        cards.add(new Lento());
        cards.add(new LockedAnchor());
        cards.add(new LockedAstrolabe());
        cards.add(new LockedBell());
        cards.add(new LockedBlood());
        // cards.add(new LockedCalipers());
        cards.add(new LockedCalendar());
        cards.add(new LockedTurtyl());
        cards.add(new LockedFlame());
        // cards.add(new LockedHand());
        // cards.add(new LockedIceCream());
        cards.add(new LockedLightning());
        cards.add(new LockedMawBank());
        cards.add(new LockedMedicine());
        cards.add(new LockedMercury());
        // cards.add(new LockedNitrogen());
        cards.add(new LockedOrichalcum());
        cards.add(new LockedPlans());
        cards.add(new LockedScales());
        cards.add(new LockedThread());
        cards.add(new LockedTornado());
        cards.add(new LockedUrn());
        cards.add(new LockedWarPaint());
        cards.add(new LockedWhetstone());
        cards.add(new MasterKey());
        // cards.add(new Maestoso());
        cards.add(new Misterioso());
        cards.add(new Moderato());
        // cards.add(new NaturalGuard());
        // cards.add(new Nibble());
        cards.add(new OldTimer());
        cards.add(new OracleForm());
        cards.add(new OverTime());
        cards.add(new Parity());
        cards.add(new PatternShift());
        cards.add(new Pendulum());
        // cards.add(new PreemptiveStrike());
        cards.add(new Presto());
        cards.add(new PrimeTime());
        cards.add(new RageCommand());
        cards.add(new Rallentando());
        // cards.add(new Reap());
        cards.add(new Recurrance());
        // cards.add(new Reiterate());
        cards.add(new ResonantCall());
        // cards.add(new Rewind());
        cards.add(new Ritenuto());
        cards.add(new SecondHand());
        // cards.add(new SenseKnock());
        cards.add(new ShortSighted());
        cards.add(new SlimeSpray());
        cards.add(new Sospirando());
        // cards.add(new Sow());
        // cards.add(new SpareTime());
        cards.add(new Stagnate());
        cards.add(new Strike_Bronze());
        cards.add(new Tempo());
        // cards.add(new ThickGoo());
        cards.add(new TickedOff());
        // cards.add(new TimeConsuming());
        // cards.add(new TimeDevouring());
        cards.add(new TiringSlam());
        cards.add(new VestedLegacy());
        // cards.add(new ViscousGoo());
        cards.add(new Vivace());
        cards.add(new WakeUpCall());
        // cards.add(new WastingAway());
        cards.add(new Ward());
        cards.add(new WatchCommand());
        cards.add(new WindUp());
		return cards; 
	}
}
