package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.cards.green.BouncingFlask;
import com.megacrit.cardcrawl.cards.green.Catalyst;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.cards.green.LegSweep;
import com.megacrit.cardcrawl.cards.green.PoisonedStab;
import com.megacrit.cardcrawl.cards.green.Terror;
import com.megacrit.cardcrawl.cards.red.BurningPact;
import com.megacrit.cardcrawl.cards.red.Corruption;
import com.megacrit.cardcrawl.cards.red.DarkEmbrace;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.cards.red.FeelNoPain;
import com.megacrit.cardcrawl.cards.red.FiendFire;
import com.megacrit.cardcrawl.cards.red.GhostlyArmor;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.cards.red.InfernalBlade;
import com.megacrit.cardcrawl.cards.red.SeeingRed;
import com.megacrit.cardcrawl.cards.red.Sentinel;
import com.megacrit.cardcrawl.cards.red.SeverSoul;
import com.megacrit.cardcrawl.cards.red.TrueGrit;
import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.dragons.FiendSkull;
import duelistmod.cards.pools.fiend.AlienTelepath;
import duelistmod.cards.pools.fiend.ArchfiendCommander;
import duelistmod.cards.pools.fiend.ArchfiendGeneral;
import duelistmod.cards.pools.fiend.ArchfiendHeiress;
import duelistmod.cards.pools.fiend.ArchfiendInterceptor;
import duelistmod.cards.pools.fiend.ArchfiendSoldier;
import duelistmod.cards.pools.fiend.AcidTrapHole;
import duelistmod.cards.pools.fiend.BeastTalwar;
import duelistmod.cards.pools.fiend.CheerfulCoffin;
import duelistmod.cards.pools.fiend.CosmicHorrorGangiel;
import duelistmod.cards.pools.fiend.DarkBlade;
import duelistmod.cards.pools.fiend.DarkCubicLord;
import duelistmod.cards.pools.fiend.DarkEnergy;
import duelistmod.cards.pools.fiend.DarkHunter;
import duelistmod.cards.pools.fiend.DarkMasterZorc;
import duelistmod.cards.pools.fiend.FiendMegacyber;
import duelistmod.cards.pools.fiend.FiendishChain;
import duelistmod.cards.pools.fiend.FiresOfDoomsday;
import duelistmod.cards.pools.fiend.KingYami;
import duelistmod.cards.pools.fiend.Lajinn;
import duelistmod.cards.pools.fiend.MsJudge;
import duelistmod.cards.pools.fiend.PutridPudding;
import duelistmod.cards.pools.fiend.SkullArchfiend;
import duelistmod.cards.pools.fiend.SlateWarrior;
import duelistmod.cards.pools.fiend.SummonedSkull;
import duelistmod.cards.pools.fiend.TranceArchfiend;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.zombies.*;

public class FiendPool 
{
	private static final String deckName = "Fiend Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.oneRandom(2));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.twoRandom(2));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		// Fiend Deck
		StarterDeck fiendDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> fiendCards = new ArrayList<>();
		fiendCards.add(new SummonedSkull());
		fiendCards.add(new FiendishChain());
		fiendCards.add(new DarkBlade());
		fiendCards.add(new DarkMasterZorc());
		fiendCards.add(new FiendMegacyber());
		fiendCards.add(new KingYami());
		fiendCards.add(new Lajinn());
		fiendCards.add(new MsJudge());
		fiendCards.add(new SkullArchfiend());
		fiendCards.add(new SlateWarrior());

		fiendCards.add(new AcidTrapHole());
		fiendCards.add(new AlienTelepath());
		fiendCards.add(new AmbitiousGofer());
		fiendCards.add(new ArchfiendCommander());
		fiendCards.add(new ArchfiendGeneral());
		fiendCards.add(new ArchfiendHeiress());
		fiendCards.add(new ArchfiendInterceptor());
		fiendCards.add(new ArchfiendSoldier());
		fiendCards.add(new ArchfiendZombieSkull());
		fiendCards.add(new BeastTalwar());
		fiendCards.add(new BerserkerCrush());
		fiendCards.add(new BlastAsmodian());
		fiendCards.add(new BottomlessTrapHole());
		fiendCards.add(new CallGrave());
		fiendCards.add(new CheerfulCoffin());
		fiendCards.add(new CosmicHorrorGangiel());
		fiendCards.add(new CyberArchfiend());
		fiendCards.add(new DarkCubicLord());
		fiendCards.add(new DarkEnergy());
		fiendCards.add(new DarkFusion());
		fiendCards.add(new DarkHunter());
		fiendCards.add(new DarkTinker());
		fiendCards.add(new DarklordMarie());
		fiendCards.add(new DarknessNeosphere());
		fiendCards.add(new DoomDonuts());
		fiendCards.add(new DoomcaliberKnight());
		fiendCards.add(new Doomdog());
		fiendCards.add(new DoomsdayHorror());
		fiendCards.add(new DrivenDaredevil());
		fiendCards.add(new EvilswarmNightmare());
		fiendCards.add(new FabledAshenveil());
		fiendCards.add(new FabledGallabas());
		fiendCards.add(new FeatherShot());
		fiendCards.add(new FiendSkull());
		fiendCards.add(new FlyingSaucer());
		fiendCards.add(new GarbageLord());
		fiendCards.add(new GiantOrc());
		fiendCards.add(new GoblinKing());
		fiendCards.add(new GracefulCharity());
		fiendCards.add(new GrossGhost());
		fiendCards.add(new PortraitSecret());
		fiendCards.add(new PotDuality());
		fiendCards.add(new Pumpking());
		fiendCards.add(new Pumprincess());
		fiendCards.add(new PutridPudding());
		fiendCards.add(new RedMirror());
		fiendCards.add(new RedSprinter());
		fiendCards.add(new Relinkuriboh());
		fiendCards.add(new TerraTerrible());
		fiendCards.add(new Tierra());
		fiendCards.add(new TributeDoomed());
		fiendCards.add(new TwinHeadedWolf());
		fiendCards.add(new UmbralHorrorGhost());
		fiendCards.add(new UmbralHorrorGhoul());
		fiendCards.add(new UmbralHorrorUniform());
		fiendCards.add(new UmbralHorrorWilloWisp());
		fiendCards.add(new VanityFiend());
		fiendCards.add(new WanderingKing());
		fiendCards.add(new Wildfire());
		fiendCards.add(new Zombyra());
		fiendCards.add(new TranceArchfiend());
		//fiendCards.add(new DarkArmedDragon());
		//fiendCards.add(new ChaosHunter());
		//fiendCards.add(new GiantGerm());
		// other new card, was FiresOfDoomsday but got used elsewhere
		//fiendCards.add(new SteelswarmRoach());
		//fiendCards.add(new VanguardOfDarkWorld());
		//fiendCards.add(new DarkWorldDealings());
		//fiendCards.add(new DarkWorldLightning());

		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
		{
			fiendCards.add(new BouncingFlask());
			fiendCards.add(new BurningPact());
			fiendCards.add(new Catalyst());
			fiendCards.add(new Corruption());
			fiendCards.add(new CripplingPoison());
			fiendCards.add(new DarkEmbrace());
			fiendCards.add(new DeadlyPoison());
			fiendCards.add(new Exhume());
			fiendCards.add(new FeelNoPain());
			fiendCards.add(new FiendFire());
			fiendCards.add(new GhostlyArmor());
			fiendCards.add(new Hemokinesis());
			fiendCards.add(new InfernalBlade());
			fiendCards.add(new LegSweep());
			fiendCards.add(new PoisonedStab());
			fiendCards.add(new SeeingRed());
			fiendCards.add(new Sentinel());
			fiendCards.add(new SeverSoul());
			fiendCards.add(new Terror());
			fiendCards.add(new TrueGrit());
		}

		//fiendDeck.fillPoolCards(fiendCards);
		return fiendCards;
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
