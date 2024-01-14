package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.CallAtlanteans;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.fiend.AlienTelepath;
import duelistmod.cards.pools.fiend.CosmicHorrorGangiel;
import duelistmod.cards.pools.fiend.FiresOfDoomsday;
import duelistmod.cards.pools.machine.FlyingSaucer;
import duelistmod.cards.pools.pharaoh.PlasmaBall;
import duelistmod.cards.pools.pharaoh.VoidApocalypse;
import duelistmod.cards.pools.spellcaster.MaskedSorcerer;
import duelistmod.cards.pools.spellcaster.PerfectSyncAUn;
import duelistmod.cards.pools.warrior.WhiteHowling;

public class SpellcasterPool {
	private static String deckName = "Spellcaster Deck";
	
	public static ArrayList<AbstractCard> oneRandom() {
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.oneRandom(7));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom() {
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.twoRandom(7));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck spellcasterDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> spellcasterCards = new ArrayList<AbstractCard>();
		spellcasterCards.add(new AncientElf());
		spellcasterCards.add(new WonderWand());
		spellcasterCards.add(new BadReaction());
		spellcasterCards.add(new BlizzardPrincess());
		spellcasterCards.add(new BookSecret());
		spellcasterCards.add(new DarkMagicianGirl());
		spellcasterCards.add(new DarkMagician());
		spellcasterCards.add(new FogKing());
		spellcasterCards.add(new GeminiElf());
		spellcasterCards.add(new GiantTrunade());
		spellcasterCards.add(new Graverobber());
		spellcasterCards.add(new GuardianAngel());
		spellcasterCards.add(new IcyCrevasse());
		spellcasterCards.add(new Illusionist());
		spellcasterCards.add(new InjectionFairy());
		spellcasterCards.add(new InvitationDarkSleep());
		spellcasterCards.add(new MysticalElf());
		spellcasterCards.add(new MythicalBeast());
		spellcasterCards.add(new NeoMagic());
		//spellcasterCards.add(new NutrientZ());
		spellcasterCards.add(new PowerKaishin());
		//spellcasterCards.add(new RainMercy());
		spellcasterCards.add(new Relinquished());
		spellcasterCards.add(new SangaEarth());
		spellcasterCards.add(new SangaThunder());
		spellcasterCards.add(new SangaWater());
		spellcasterCards.add(new GateGuardian());
		spellcasterCards.add(new FairyBox());
		spellcasterCards.add(new SwordsBurning());
		spellcasterCards.add(new Yami());
		spellcasterCards.add(new TimeWizard());
		spellcasterCards.add(new IceQueen());
		spellcasterCards.add(new ThousandEyesRestrict());
		spellcasterCards.add(new ThousandEyesIdol());
		spellcasterCards.add(new MindAir());
		spellcasterCards.add(new FrontierWiseman());
		spellcasterCards.add(new GoblinRemedy());
		spellcasterCards.add(new AltarTribute());
		spellcasterCards.add(new BlueDragonSummoner());
		spellcasterCards.add(new ApprenticeIllusionMagician());
		spellcasterCards.add(new DarkHorizon());
		spellcasterCards.add(new DarkPaladin());
		spellcasterCards.add(new WhiteMagicalHat());
		spellcasterCards.add(new LegendaryFlameLord());
		spellcasterCards.add(new BlizzardWarrior());
		spellcasterCards.add(new SwordsRevealing());
		spellcasterCards.add(new GrandSpellbookTower());
		spellcasterCards.add(new CallAtlanteans());
		spellcasterCards.add(new DoomShaman());
		spellcasterCards.add(new StatueAnguishPattern());
		spellcasterCards.add(new VoidVanishment());
		spellcasterCards.add(new VoidExpansion());
		spellcasterCards.add(new Mudballman());
		spellcasterCards.add(new MagicalBlast());
		spellcasterCards.add(new CosmoBrain());
		spellcasterCards.add(new DiffusionWaveMotion());
		spellcasterCards.add(new QueenDragunDjinn());
		spellcasterCards.add(new InariFire());
		spellcasterCards.add(new MagiciansRobe());
		spellcasterCards.add(new MagiciansRod());
		spellcasterCards.add(new RainbowMagician());
		spellcasterCards.add(new IrisEarthMother());
		spellcasterCards.add(new RainbowDarkDragon());
		spellcasterCards.add(new InfernoFireBlast());
		spellcasterCards.add(new SilentDoom());
		spellcasterCards.add(new SpellShatteringArrow());
		spellcasterCards.add(new Wildfire());
		spellcasterCards.add(new CharcoalInpachi());
		spellcasterCards.add(new FlyingSaucer());
		spellcasterCards.add(new AlienTelepath());
		spellcasterCards.add(new CosmicHorrorGangiel());
		spellcasterCards.add(new WitchBlackRose());
		spellcasterCards.add(new CrystalWingDragon());
		spellcasterCards.add(new SkilledDarkMagician());
		spellcasterCards.add(new CircleFireKings());
		spellcasterCards.add(new OnslaughtFireKings());
		spellcasterCards.add(new WhiteHowling());
		spellcasterCards.add(new FiresOfDoomsday());
		spellcasterCards.add(new PerfectSyncAUn());
		spellcasterCards.add(new PlasmaBall());
		spellcasterCards.add(new VoidApocalypse());
		spellcasterCards.add(new MaskedSorcerer());

		// Exodia
		if (!DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveExodia())
		{
			spellcasterCards.add(new ExodiaHead());
			spellcasterCards.add(new ExodiaLA());
			spellcasterCards.add(new ExodiaLL());
			spellcasterCards.add(new ExodiaRA());
			spellcasterCards.add(new ExodiaRL());
			//spellcasterCards.add(new ExxodMaster());
			spellcasterCards.add(new LegendaryExodia());
			spellcasterCards.add(new ContractExodia());
			//spellcasterCards.add(new LegendExodia());
			spellcasterCards.add(new ExodiaNecross());
		}

		// Base Game
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType()) {
			spellcasterCards.add(new Chill());
			spellcasterCards.add(new BiasedCognition());			
			spellcasterCards.add(new ColdSnap());
			spellcasterCards.add(new Loop());
			spellcasterCards.add(new Electrodynamics());
			spellcasterCards.add(new Coolheaded());
			spellcasterCards.add(new Capacitor());
			spellcasterCards.add(new Rainbow());
			spellcasterCards.add(new Barrage());
			spellcasterCards.add(new Storm());
			spellcasterCards.add(new CompileDriver());
			spellcasterCards.add(new Consume());
			spellcasterCards.add(new Blizzard());
			spellcasterCards.add(new Fission());
			spellcasterCards.add(new BallLightning());
			spellcasterCards.add(new MeteorStrike());
			spellcasterCards.add(new Darkness());
			spellcasterCards.add(new MultiCast());
			spellcasterCards.add(new DoomAndGloom());
			spellcasterCards.add(new Tempest());
			spellcasterCards.add(new Recursion());
			spellcasterCards.add(new Fusion());
			spellcasterCards.add(new StaticDischarge());
			spellcasterCards.add(new ThunderStrike());	
			spellcasterCards.add(new MachineLearning());
			spellcasterCards.add(new WhiteNoise());
			spellcasterCards.add(new SelfRepair());
			spellcasterCards.add(new Fission());
			spellcasterCards.add(new CreativeAI());
			spellcasterCards.add(new Leap());
			spellcasterCards.add(new Overclock());	
		}
		//spellcasterDeck.fillPoolCards(spellcasterCards);
		return spellcasterCards;
	}
	
	public static  ArrayList<AbstractCard> basic() {
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		//deck.fillPoolCards(pool); 
		return pool;
	}
}
