package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.Slushy;
import duelistmod.cards.pools.beast.FlyingElephant;
import duelistmod.cards.pools.beast.UnicornBeacon;
import duelistmod.cards.pools.fiend.CheerfulCoffin;
import duelistmod.cards.pools.fiend.PutridPudding;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.pharaoh.PlasmaBall;
import duelistmod.cards.pools.toon.CreepyConey;
import duelistmod.cards.pools.toon.ToadallyAwesome;
import duelistmod.cards.pools.toon.ToonAncientGear;
import duelistmod.cards.pools.toon.ToonBarrelDragon;
import duelistmod.cards.pools.toon.ToonBriefcase;
import duelistmod.cards.pools.toon.ToonCannonSoldier;
import duelistmod.cards.pools.toon.ToonDarkMagician;
import duelistmod.cards.pools.toon.ToonDarkMagicianGirl;
import duelistmod.cards.pools.toon.ToonDefense;
import duelistmod.cards.pools.toon.ToonGeminiElf;
import duelistmod.cards.pools.toon.ToonGoblinAttack;
import duelistmod.cards.pools.toon.ToonKingdom;
import duelistmod.cards.pools.toon.ToonMagic;
import duelistmod.cards.pools.toon.ToonMask;
import duelistmod.cards.pools.toon.ToonMaskedSorcerer;
import duelistmod.cards.pools.toon.ToonMermaid;
import duelistmod.cards.pools.toon.ToonRollback;
import duelistmod.cards.pools.toon.ToonSummonedSkull;
import duelistmod.cards.pools.toon.ToonTable;
import duelistmod.cards.pools.toon.ToonWorld;
import duelistmod.cards.pools.zombies.HumptyGrumpty;
import duelistmod.cards.pools.zombies.TributeDoomed;

public class ToonPool {
	private static String deckName = "Toon Deck";
	
	public static ArrayList<AbstractCard> oneRandom() {
        return new ArrayList<>(GlobalPoolHelper.oneRandom(13));
	}
	
	public static ArrayList<AbstractCard> twoRandom() {
        return new ArrayList<>(GlobalPoolHelper.twoRandom(13));
	}
	
	public static ArrayList<AbstractCard> deck() {
		ArrayList<AbstractCard> toonCards = new ArrayList<>();

		// Starting deck
		// Toon Alligator - Summon 1, cost 1, deal 7 damage, revenge: gain 3 block
		// Toon Alligator - Summon 1, cost 1, deal 7 damage, revenge: gain 3 block
		// Sparks
		// Sparks
		// Aitsu - Summon 1, cost 1, gain 7 block, revenge: deal 2 damage to a random enemy
		// Koitsu - Summon 1, cost 1, gain 7 block, revenge: gain 1 dexterity for the turn
		// Doitsu - Summon 1, cost 1, gain 7 block, revenge: heal 1 hp
		// Soitsu - Summon 1, cost 1, gain 7 block, revenge: gain 1 strength for the turn
		// Castle walls
		// Castle walls

		toonCards.add(new ToadallyAwesome());
		toonCards.add(new ToonWorld());
		toonCards.add(new ToonBarrelDragon());
		toonCards.add(new ToonMaskedSorcerer());
		toonCards.add(new ToonSummonedSkull());
		toonCards.add(new ToonRollback());
		toonCards.add(new ToonMermaid());
		toonCards.add(new ToonMask());
		toonCards.add(new ToonMagic());
		toonCards.add(new ToonKingdom());
		toonCards.add(new ToonGoblinAttack());
		toonCards.add(new ToonGeminiElf());
		toonCards.add(new ToonDarkMagicianGirl());
		toonCards.add(new ToonDarkMagician());
		toonCards.add(new ToonAncientGear());
		toonCards.add(new RedEyesToon());
		toonCards.add(new MangaRyuRan());
		toonCards.add(new BlueEyesToon());
		toonCards.add(new ToonTable());
		toonCards.add(new ToonCannonSoldier());
		toonCards.add(new ToonDefense());
		toonCards.add(new ComicHand());
		toonCards.add(new ShadowToon());
		toonCards.add(new ToonBriefcase());
		toonCards.add(new HeartUnderdog());
		toonCards.add(new MagicCylinder());
		toonCards.add(new GracefulCharity());
		toonCards.add(new SpellShatteringArrow());
		toonCards.add(new FeatherShot());
		toonCards.add(new PotGreed());
		toonCards.add(new HumptyGrumpty());
		toonCards.add(new Slushy());
		toonCards.add(new FlyingElephant());
		toonCards.add(new CreepyConey());
		//toonCards.add(new ToonCyberDragon());
		//toonCards.add(new BagooskaTheTerribleTiredTapir());
		//toonCards.add(new ThereCanOnlyBeOne());
		//toonCards.add(new ToonBookmark());
		//toonCards.add(new ToonBusterBlader());
		//toonCards.add(new ToonExplosion());
		//toonCards.add(new ToonGiant());
		//toonCards.add(new ToonHarpieLady());
		//toonCards.add(new ToonPageFlip());
		//toonCards.add(new ToonTerror());
		//toonCards.add(new ToonZombie());
		//toonCards.add(new PotProsperity());
		//toonCards.add(new PotDesires());
		//toonCards.add(new PotAcquisitiveness());
		//toonCards.add(new PotRiches());
		//toonCards.add(new PotBenevloence());
		//toonCards.add(new PotExtravagance());
		//toonCards.add(new JarRobber());
		//toonCards.add(new GoblinOutOfTheFryingPan());
		//toonCards.add(new GoblinThief());
		//toonCards.add(new TardyOrc());
		//toonCards.add(new MineMole()); // Beast
		//toonCards.add(new Bunilla()); // Beast
		//toonCards.add(new DBoyz());
		//toonCards.add(new HungryBurger());
		//toonCards.add(new Tatsunootoshigo());
		//toonCards.add(new ChainDog()); // Beast
		//toonCards.add(new MasterOfOz()); // Beast
		//toonCards.add(new InterplanetarypurplythornyDragon()); // Beast
		//toonCards.add(new TransmissionGear());
		//toonCards.add(new Mimiclay());
		//toonCards.add(new TerribleDeal());
		//toonCards.add(new Oops());
		//toonCards.add(new DarkBribe());
		//toonCards.add(new CardOfLastWill());
		//toonCards.add(new TimeMagicHammer());

		//toonCards.add(new FlyingPenguin());
		//toonCards.add(new GagagaCowboy());

		// Temp cards until pool is fixed
		toonCards.add(new TributeDoomed());
		toonCards.add(new GiantTrunade());
		toonCards.add(new SwordsConcealing());
		toonCards.add(new TrapHole());
		toonCards.add(new CheerfulCoffin());
		toonCards.add(new AncientGearFist());
		toonCards.add(new FlyingSaucer());
		toonCards.add(new UnicornBeacon());
		toonCards.add(new PutridPudding());
		toonCards.add(new PlasmaBall());

		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
		{
			/*toonCards.add(new Entrench());
			toonCards.add(new Reaper());
			toonCards.add(new BurningPact());
			toonCards.add(new DemonForm());
			toonCards.add(new Uppercut());
			toonCards.add(new BattleTrance());
			toonCards.add(new Shockwave());
			toonCards.add(new SecondWind());
			toonCards.add(new Armaments());
			toonCards.add(new FlameBarrier());
			toonCards.add(new ShrugItOff());
			toonCards.add(new InfernalBlade());
			toonCards.add(new SpotWeakness());
			toonCards.add(new Disarm());
			toonCards.add(new Barricade());
			toonCards.add(new Juggernaut());
			toonCards.add(new Feed());
			toonCards.add(new Impervious());
			toonCards.add(new Metallicize());
			toonCards.add(new BodySlam());
			toonCards.add(new LimitBreak());
			toonCards.add(new DarkEmbrace());
			toonCards.add(new TrueGrit());
			toonCards.add(new Dropkick());
			toonCards.add(new DoubleTap());
			toonCards.add(new BloodForBlood());
			toonCards.add(new Anger());
			toonCards.add(new Rupture());
			toonCards.add(new ThunderClap());
			toonCards.add(new Backflip());
			toonCards.add(new Acrobatics());
			toonCards.add(new DodgeAndRoll());
			toonCards.add(new CalculatedGamble());
			toonCards.add(new WellLaidPlans());
			toonCards.add(new Blur());
			toonCards.add(new ToolsOfTheTrade());
			toonCards.add(new Adrenaline());
			toonCards.add(new Alchemize());
			toonCards.add(new BulletTime());
			toonCards.add(new Outmaneuver());
			toonCards.add(new AThousandCuts());
			toonCards.add(new Malaise());
			toonCards.add(new Burst());
			toonCards.add(new Predator());
			toonCards.add(new Terror());
			toonCards.add(new FlyingKnee());
			toonCards.add(new HeelHook());
			toonCards.add(new Distraction());
			toonCards.add(new Reboot());
			toonCards.add(new BeamCell());
			toonCards.add(new Amplify());
			toonCards.add(new Reprogram());
			toonCards.add(new Buffer());
			toonCards.add(new Recycle());
			toonCards.add(new HelloWorld());
			toonCards.add(new DoubleEnergy());
			toonCards.add(new MachineLearning());
			toonCards.add(new Storm());
			toonCards.add(new Equilibrium());
			toonCards.add(new ReinforcedBody());
			toonCards.add(new Heatsinks());		*/
		}
		return toonCards;
	}
	
	public static  ArrayList<AbstractCard> basic() {
		return DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()
				? new ArrayList<>(BasicPool.smallBasic("Toon Deck"))
				: new ArrayList<>(BasicPool.fullBasic("Toon Deck"));
	}
}
