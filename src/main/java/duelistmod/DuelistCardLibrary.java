package duelistmod;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.cards.curses.*;
import duelistmod.cards.holiday.birthday.*;
import duelistmod.cards.holiday.christmas.*;
import duelistmod.cards.holiday.fourtwenty.WeedOut;
import duelistmod.cards.holiday.halloween.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.metronomes.*;
import duelistmod.cards.metronomes.typed.*;
import duelistmod.cards.nameless.greed.*;
import duelistmod.cards.nameless.magic.*;
import duelistmod.cards.nameless.power.*;
import duelistmod.cards.nameless.war.*;
import duelistmod.cards.other.bookOfLifeOptions.CustomResummonCard;
import duelistmod.cards.other.orbCards.*;
import duelistmod.cards.other.statuses.*;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.other.tokens.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.aqua.KaiserSeaHorse;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.*;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.enums.Mode;
import duelistmod.helpers.*;
import duelistmod.helpers.crossover.*;
import duelistmod.helpers.poolhelpers.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.WonderGaragePower;
import duelistmod.variables.Tags;

public class DuelistCardLibrary
{

	// COMPENDIUM MANIPULATION FUNCTIONS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Add to totally random list
	private static void addToTotallyRandomList(AbstractCard c)
	{
		if (!DuelistMod.totallyRandomCardMap.containsKey(c.cardID)
				&& !c.type.equals(CardType.STATUS)
				&& !c.type.equals(CardType.CURSE)
				&& !c.rarity.equals(CardRarity.SPECIAL)
				&& !c.hasTag(Tags.NEVER_GENERATE))
		{
			DuelistMod.totallyRandomCardList.add(c.makeCopy());
		}
	}

	public static void checkNumsForMap(AbstractCard c)
	{
		if (c.magicNumber > 0 || c.baseMagicNumber > 0)
		{
			DuelistMod.magicNumberCards.put(c.cardID, "" + c.magicNumber);
		}

		if (c instanceof DuelistCard)
		{
			DuelistCard dc = ((DuelistCard)c);
			if (dc.baseSummons > 0)
			{
				DuelistMod.summonCards.put(c.cardID, dc.baseSummons);
				DuelistMod.summonCardNames.add(c.cardID);
			}

			if (dc.baseTributes > 0)
			{
				DuelistMod.tributeCards.put(c.cardID, dc.baseTributes);
			}

			if (dc.hasTag(Tags.MONSTER) || dc.hasTag(Tags.TOKEN)) {
				DuelistMod.monsterAndTokenCardNames.add(c.cardID);
			}

			DuelistConfigurationData configData = dc.getConfigurations();
			if (configData != null) {
				DuelistMod.cardConfigurations.add(configData);
			}
		}
	}

	private static int logLoadingCards(int counter, int size, int lastPercent) {
		double div = ((double)counter / (double)size) * (double)100;
		int percent = (int)Math.floor(div);
		if (percent >= 100) {
			Util.log("DuelistMod card set loading: 100%");
			Util.log("DuelistMod card set loading: done");
			return 100;
		}
		if (percent > lastPercent) {
			lastPercent = percent;
			Util.log("DuelistMod card set loading: " + percent + "%");
		}
		return lastPercent;
	}

	public static void addCardsToGame()
	{
		Util.log("Initializing Duelist card library, please wait...");
		long start = System.currentTimeMillis();
		ArrayList<AbstractCard> infiniteUpgradeCards = new ArrayList<>();
		int counter = 0;
		int size = DuelistMod.myCards.size() + DuelistMod.myNamelessCards.size() + DuelistMod.myStatusCards.size() + DuelistMod.curses.size();
		int lastPercent = 0;
		for (DuelistCard c : DuelistMod.myCards)
		{
			BaseMod.addCard(c.makeCopy());
			try { UnlockTracker.unlockCard(c.cardID); } catch (Exception e) { e.printStackTrace(); }
			DuelistMod.summonMap.put(c.cardID, (DuelistCard) c.makeCopy());
			DuelistMod.cardIdMap.put(c.cardID, (DuelistCard) c.makeCopy());
			if (!c.hasTag(Tags.EXEMPT)) {
				DuelistMod.nonExemptCardNames.add(c.cardID);
			}
			DuelistMod.mapForCardPoolSave.put(c.cardID, c.makeCopy());
			DuelistMod.mapForRunCardsLoading.put(c.cardID, c.makeCopy());
			if (c.hasTag(Tags.ARCANE)) { DuelistMod.arcaneCards.add(c.makeCopy()); }
			if (c.hasTag(Tags.AQUA) && c.rarity.equals(CardRarity.RARE) && c.hasTag(Tags.AQUA))
			{
				DuelistMod.waterHazardCards.add(c.makeCopy());
			}
			checkNumsForMap(c);
			if (DuelistMod.modMode == Mode.DEV) {
				AbstractCard infin = infiniteUpgradeCheck(c);
				if (!(infin instanceof CancelCard)) { infiniteUpgradeCards.add(infin); }
			}
			addToTotallyRandomList(c);
			counter++;
			lastPercent = logLoadingCards(counter, size, lastPercent);
		}

		long end;

		for (DuelistCard c : DuelistMod.myNamelessCards)
		{
			BaseMod.addCard(c);
			UnlockTracker.unlockCard(c.cardID);
			DuelistMod.mapForRunCardsLoading.put(c.cardID, c);
			checkNumsForMap(c);
			if (DuelistMod.modMode == Mode.DEV) {
				AbstractCard infin = infiniteUpgradeCheck(c);
				if (!(infin instanceof CancelCard)) { infiniteUpgradeCards.add(infin); }
			}
			counter++;
			lastPercent = logLoadingCards(counter, size, lastPercent);
		}

		for (DuelistCard c : DuelistMod.myStatusCards)
		{
			BaseMod.addCard(c);
			UnlockTracker.unlockCard(c.cardID);
			checkNumsForMap(c);
			counter++;
			lastPercent = logLoadingCards(counter, size, lastPercent);
		}

		for (DuelistCard c : DuelistMod.curses)
		{
			BaseMod.addCard(c);
			UnlockTracker.unlockCard(c.cardID);
			checkNumsForMap(c);
			counter++;
			lastPercent = logLoadingCards(counter, size, lastPercent);
		}

		DuelistCard cd = new CurseDarkness();
		DuelistCard da = new DuelistAscender();
		DuelistCard cc = new CustomResummonCard();
		DuelistCard zombieCorpse = new ZombieCorpse();
		BaseMod.addCard(cd);
		BaseMod.addCard(da);
		BaseMod.addCard(cc);
		BaseMod.addCard(zombieCorpse);
		checkNumsForMap(cd);
		checkNumsForMap(da);
		UnlockTracker.unlockCard(cd.cardID);
		UnlockTracker.unlockCard(da.cardID);
		UnlockTracker.unlockCard(zombieCorpse.cardID);
		DuelistMod.mapForCardPoolSave.put(cc.cardID, cc.makeCopy());
		DuelistMod.mapForRunCardsLoading.put(cc.cardID, cc.makeCopy());

		for (AbstractCard c : BaseGameHelper.getAllBaseGameCards())
		{
			DuelistMod.mapForCardPoolSave.put(c.cardID, c);
			checkNumsForMap(c);
			addToTotallyRandomList(c);
		}

		if (DuelistMod.isInfiniteSpire)
		{
			for (AbstractCard c : InfiniteSpireHelper.getAllBlackCards())
			{
				DuelistMod.mapForCardPoolSave.put(c.cardID, c);
				checkNumsForMap(c);
				addToTotallyRandomList(c);
			}
		}

		if (DuelistMod.isClockwork)
		{
			for (AbstractCard c : ClockworkHelper.getAllCards())
			{
				DuelistMod.mapForCardPoolSave.put(c.cardID, c);
				checkNumsForMap(c);
				addToTotallyRandomList(c);
			}
		}

		if (DuelistMod.isConspire)
		{
			for (AbstractCard c : ConspireHelper.getAllCards())
			{
				DuelistMod.mapForCardPoolSave.put(c.cardID, c);
				checkNumsForMap(c);
				addToTotallyRandomList(c);
			}
		}

		if (DuelistMod.isDisciple)
		{
			for (AbstractCard c : DiscipleHelper.getAllCards())
			{
				DuelistMod.mapForCardPoolSave.put(c.cardID, c);
				checkNumsForMap(c);
				addToTotallyRandomList(c);
			}
		}

		if (DuelistMod.isGatherer)
		{
			for (AbstractCard c : GathererHelper.getAllCards())
			{
				DuelistMod.mapForCardPoolSave.put(c.cardID, c);
				checkNumsForMap(c);
				addToTotallyRandomList(c);
			}
		}

		if (DuelistMod.isHubris)
		{
			for (AbstractCard c : HubrisHelper.getAllCards())
			{
				DuelistMod.mapForCardPoolSave.put(c.cardID, c);
				checkNumsForMap(c);
				addToTotallyRandomList(c);
			}
		}

		if (DuelistMod.isReplay)
		{
			for (AbstractCard c : ReplayHelper.getAllCards())
			{
				DuelistMod.mapForCardPoolSave.put(c.cardID, c);
				checkNumsForMap(c);
				addToTotallyRandomList(c);
			}
		}

		for (AbstractCard c : infiniteUpgradeCards)
		{
			Util.log("Infinite Upgrade Card! Card: " + c.name);
		}

		if (infiniteUpgradeCards.size() > 0) { throw new RuntimeException("DuelistMod found cards that were infinitely upgradeable!"); }

		end = System.currentTimeMillis();
		Util.log("Execution time to initialize all Duelist cards: " + (end - start) + "ms");
	}

	public static List<DuelistCard> getCardsToPrintImagesForModdedExporter() {
		List<DuelistCard> output = new ArrayList<>();
		output.add(new LightningDarts());
		output.add(new Mispolymerization());
		output.add(new CrystalEmeraldTortoise());
		output.addAll(DuelistMod.orbCards);
		output.addAll(DuelistMod.myCards);
		return output;
	}

	public static void setupMyCards()
	{
		DuelistMod.myCards.add(new PowerWall());
		DuelistMod.myCards.add(new GiantSoldier());
		DuelistMod.myCards.add(new Ookazi());
		DuelistMod.myCards.add(new ScrapFactory());
		DuelistMod.myCards.add(new SevenColoredFish());
		DuelistMod.myCards.add(new SummonedSkull());
		DuelistMod.myCards.add(new ArmoredZombie());
		DuelistMod.myCards.add(new AxeDespair());
		DuelistMod.myCards.add(new BabyDragon());
		DuelistMod.myCards.add(new BadReaction());
		DuelistMod.myCards.add(new BigFire());
		DuelistMod.myCards.add(new BlizzardDragon());
		DuelistMod.myCards.add(new BlueEyes());
		DuelistMod.myCards.add(new BlueEyesUltimate());
		DuelistMod.myCards.add(new BusterBlader());
		DuelistMod.myCards.add(new CannonSoldier());
		DuelistMod.myCards.add(new CardDestruction());
		DuelistMod.myCards.add(new CastleDarkIllusions());
		DuelistMod.myCards.add(new CatapultTurtle());
		DuelistMod.myCards.add(new CaveDragon());
		DuelistMod.myCards.add(new CelticGuardian());
		DuelistMod.myCards.add(new ChangeHeart());
		DuelistMod.myCards.add(new DarkMagician());
		DuelistMod.myCards.add(new DarklordMarie());
		DuelistMod.myCards.add(new DianKeto());
		DuelistMod.myCards.add(new DragonCaptureJar());
		DuelistMod.myCards.add(new FiendMegacyber());
		DuelistMod.myCards.add(new Fissure());
		DuelistMod.myCards.add(new FluteSummoning());
		DuelistMod.myCards.add(new FortressWarrior());
		DuelistMod.myCards.add(new GaiaDragonChamp());
		DuelistMod.myCards.add(new GaiaFierce());
		DuelistMod.myCards.add(new GeminiElf());
		DuelistMod.myCards.add(new GracefulCharity());
		DuelistMod.myCards.add(new GravityAxe());
		DuelistMod.myCards.add(new HaneHane());
		DuelistMod.myCards.add(new Hinotama());
		DuelistMod.myCards.add(new ImperialOrder());
		DuelistMod.myCards.add(new InjectionFairy());
		DuelistMod.myCards.add(new InsectQueen());
		DuelistMod.myCards.add(new IslandTurtle());
		DuelistMod.myCards.add(new JamBreeding());
		DuelistMod.myCards.add(new JudgeMan());
		DuelistMod.myCards.add(new Kuriboh());
		DuelistMod.myCards.add(new LabyrinthWall());
		DuelistMod.myCards.add(new LesserDragon());
		DuelistMod.myCards.add(new LordD());
		DuelistMod.myCards.add(new MirrorForce());
		DuelistMod.myCards.add(new MonsterReborn());
		DuelistMod.myCards.add(new Mountain());
		DuelistMod.myCards.add(new MysticalElf());
		DuelistMod.myCards.add(new ObeliskTormentor());
		DuelistMod.myCards.add(new PotGenerosity());
		DuelistMod.myCards.add(new PotGreed());
		DuelistMod.myCards.add(new PreventRat());
		DuelistMod.myCards.add(new Pumpking());
		DuelistMod.myCards.add(new Raigeki());
		DuelistMod.myCards.add(new RainMercy());
		DuelistMod.myCards.add(new RedEyes());
		DuelistMod.myCards.add(new Relinquished());
		DuelistMod.myCards.add(new SangaEarth());
		DuelistMod.myCards.add(new SangaThunder());
		DuelistMod.myCards.add(new Scapegoat());
		DuelistMod.myCards.add(new SliferSky());
		DuelistMod.myCards.add(new SnowDragon());
		DuelistMod.myCards.add(new SnowdustDragon());
		DuelistMod.myCards.add(new SpiritHarp());
		DuelistMod.myCards.add(new SuperheavyBenkei());
		DuelistMod.myCards.add(new SuperheavyScales());
		DuelistMod.myCards.add(new SuperheavySwordsman());
		DuelistMod.myCards.add(new SuperheavyWaraji());
		DuelistMod.myCards.add(new ThunderDragon());
		DuelistMod.myCards.add(new WingedDragonRa());
		DuelistMod.myCards.add(new Yami());
		DuelistMod.myCards.add(new NeoMagic());
		DuelistMod.myCards.add(new GoldenApples());
		DuelistMod.myCards.add(new SphereKuriboh());
		DuelistMod.myCards.add(new Wiseman());
		DuelistMod.myCards.add(new Sparks());
		DuelistMod.myCards.add(new CastleWalls());
		DuelistMod.myCards.add(new Sangan());
		DuelistMod.myCards.add(new FlameSwordsman());
		DuelistMod.myCards.add(new BlastJuggler());
		DuelistMod.myCards.add(new MachineKing());
		DuelistMod.myCards.add(new BookSecret());
		DuelistMod.myCards.add(new HeavyStorm());
		DuelistMod.myCards.add(new FogKing());
		DuelistMod.myCards.add(new Lajinn());
		DuelistMod.myCards.add(new KingYami());
		DuelistMod.myCards.add(new BlacklandFireDragon());
		DuelistMod.myCards.add(new WhiteNightDragon());
		DuelistMod.myCards.add(new WhiteHornDragon());
		DuelistMod.myCards.add(new RevivalJam());
		DuelistMod.myCards.add(new StimPack());
		DuelistMod.myCards.add(new BottomlessTrapHole());
		DuelistMod.myCards.add(new SwordDeepSeated());
		DuelistMod.myCards.add(new MonsterEgg());
		DuelistMod.myCards.add(new SteamTrainKing());
		DuelistMod.myCards.add(new MachineFactory());
		DuelistMod.myCards.add(new TributeDoomed());
		DuelistMod.myCards.add(new PetitMoth());
		DuelistMod.myCards.add(new CocoonEvolution());
		DuelistMod.myCards.add(new CheerfulCoffin());
		DuelistMod.myCards.add(new TheCreator());
		DuelistMod.myCards.add(new Polymerization());
		DuelistMod.myCards.add(new VioletCrystal());
		DuelistMod.myCards.add(new Predaponics());
		DuelistMod.myCards.add(new MetalDragon());
		DuelistMod.myCards.add(new SuperSolarNutrient());
		DuelistMod.myCards.add(new Gigaplant());
		DuelistMod.myCards.add(new BasicInsect());
		DuelistMod.myCards.add(new BSkullDragon());
		DuelistMod.myCards.add(new DarkfireDragon());
		DuelistMod.myCards.add(new EmpressMantis());
		DuelistMod.myCards.add(new Grasschopper());
		DuelistMod.myCards.add(new Jinzo());
		DuelistMod.myCards.add(new LeviaDragon());
		DuelistMod.myCards.add(new ManEaterBug());
		DuelistMod.myCards.add(new OceanDragonLord());
		DuelistMod.myCards.add(new PredaplantChimerafflesia());
		DuelistMod.myCards.add(new PredaplantChlamydosundew());
		DuelistMod.myCards.add(new PredaplantDrosophyllum());
		DuelistMod.myCards.add(new PredaplantFlytrap());
		DuelistMod.myCards.add(new PredaplantPterapenthes());
		DuelistMod.myCards.add(new PredaplantSarraceniant());
		DuelistMod.myCards.add(new PredaplantSpinodionaea());
		DuelistMod.myCards.add(new Predapruning());
		DuelistMod.myCards.add(new TrihornedDragon());
		DuelistMod.myCards.add(new Wiretap());
		DuelistMod.myCards.add(new Reinforcements());
		DuelistMod.myCards.add(new UltimateOffering());
		DuelistMod.myCards.add(new JerryBeansMan());
		DuelistMod.myCards.add(new Illusionist());
		DuelistMod.myCards.add(new ExploderDragon());
		DuelistMod.myCards.add(new Invigoration());
		DuelistMod.myCards.add(new InvitationDarkSleep());
		DuelistMod.myCards.add(new AcidTrapHole());
		DuelistMod.myCards.add(new AltarTribute());
		DuelistMod.myCards.add(new BlizzardPrincess());
		DuelistMod.myCards.add(new CardSafeReturn());
		DuelistMod.myCards.add(new Cloning());
		DuelistMod.myCards.add(new ComicHand());
		DuelistMod.myCards.add(new ContractExodia());
		DuelistMod.myCards.add(new DarkCreator());
		DuelistMod.myCards.add(new DoubleCoston());
		DuelistMod.myCards.add(new GatesDarkWorld());
		DuelistMod.myCards.add(new HammerShot());
		DuelistMod.myCards.add(new HeartUnderdog());
		DuelistMod.myCards.add(new InsectKnight());
		DuelistMod.myCards.add(new JiraiGumo());
		DuelistMod.myCards.add(new MythicalBeast());
		DuelistMod.myCards.add(new PotForbidden());
		DuelistMod.myCards.add(new SmashingGround());
		DuelistMod.myCards.add(new Terraforming());
		DuelistMod.myCards.add(new TerraTerrible());
		DuelistMod.myCards.add(new IcyCrevasse());
		DuelistMod.myCards.add(new StrayLambs());
		DuelistMod.myCards.add(new GuardianAngel());
		DuelistMod.myCards.add(new ShadowToon());
		DuelistMod.myCards.add(new ShallowGrave());
		DuelistMod.myCards.add(new WorldCarrot());
		DuelistMod.myCards.add(new SoulAbsorbingBone());
		DuelistMod.myCards.add(new MsJudge());
		DuelistMod.myCards.add(new FiendishChain());
		DuelistMod.myCards.add(new Firegrass());
		DuelistMod.myCards.add(new PowerKaishin());
		DuelistMod.myCards.add(new AncientElf());
		DuelistMod.myCards.add(new FinalFlame());
		DuelistMod.myCards.add(new YamataDragon());
		DuelistMod.myCards.add(new TwinBarrelDragon());
		DuelistMod.myCards.add(new HundredFootedHorror());
		DuelistMod.myCards.add(new SlateWarrior());
		DuelistMod.myCards.add(new MotherSpider());
		DuelistMod.myCards.add(new MangaRyuRan());
		DuelistMod.myCards.add(new ToonAncientGear());
		DuelistMod.myCards.add(new ClownZombie());
		DuelistMod.myCards.add(new RyuKokki());
		DuelistMod.myCards.add(new GoblinRemedy());
		DuelistMod.myCards.add(new CallGrave());
		DuelistMod.myCards.add(new AllyJustice());
		DuelistMod.myCards.add(new Graverobber());
		DuelistMod.myCards.add(new DragonPiper());
		DuelistMod.myCards.add(new SwordsRevealing());
		DuelistMod.myCards.add(new TimeWizard());
		DuelistMod.myCards.add(new TrapHole());
		DuelistMod.myCards.add(new BlueEyesToon());
		//DuelistMod.myCards.add(new DragonMaster());
		//DuelistMod.myCards.add(new Gandora());
		DuelistMod.myCards.add(new LegendaryExodia());
		DuelistMod.myCards.add(new RadiantMirrorForce());
		DuelistMod.myCards.add(new RedEyesToon());
		DuelistMod.myCards.add(new SuperancientDinobeast());
		DuelistMod.myCards.add(new TokenVacuum());
		DuelistMod.myCards.add(new ToonBarrelDragon());
		DuelistMod.myCards.add(new ToonBriefcase());
		DuelistMod.myCards.add(new ToonDarkMagician());
		DuelistMod.myCards.add(new ToonGeminiElf());
		DuelistMod.myCards.add(new ToonMagic());
		DuelistMod.myCards.add(new ToonMask());
		DuelistMod.myCards.add(new ToonMermaid());
		DuelistMod.myCards.add(new ToonRollback());
		DuelistMod.myCards.add(new ToonSummonedSkull());
		DuelistMod.myCards.add(new ToonWorld());
		DuelistMod.myCards.add(new ToonKingdom());
		DuelistMod.myCards.add(new CurseDragon());
		DuelistMod.myCards.add(new CyberDragon());
		DuelistMod.myCards.add(new DarkFactory());
		DuelistMod.myCards.add(new FiendSkull());
		DuelistMod.myCards.add(new FiveHeaded());
		DuelistMod.myCards.add(new GiantTrunade());
		DuelistMod.myCards.add(new HarpieFeather());
		DuelistMod.myCards.add(new MoltenZombie());
		DuelistMod.myCards.add(new OjamaGreen());
		DuelistMod.myCards.add(new OjamaYellow());
		DuelistMod.myCards.add(new Ojamagic());
		DuelistMod.myCards.add(new Ojamuscle());
		DuelistMod.myCards.add(new PotAvarice());
		DuelistMod.myCards.add(new PotDichotomy());
		DuelistMod.myCards.add(new PotDuality());
		DuelistMod.myCards.add(new Pumprincess());
		DuelistMod.myCards.add(new RedEyesZombie());
		DuelistMod.myCards.add(new RedMedicine());
		DuelistMod.myCards.add(new ShardGreed());
		DuelistMod.myCards.add(new StormingMirrorForce());
		DuelistMod.myCards.add(new SuperheavyBlueBrawler());
		DuelistMod.myCards.add(new SuperheavyDaihachi());
		DuelistMod.myCards.add(new SuperheavyFlutist());
		DuelistMod.myCards.add(new SuperheavyGeneral());
		DuelistMod.myCards.add(new SuperheavyMagnet());
		DuelistMod.myCards.add(new SuperheavyOgre());
		DuelistMod.myCards.add(new SwordsBurning());
		DuelistMod.myCards.add(new SwordsConcealing());
		DuelistMod.myCards.add(new AlphaMagnet());
		DuelistMod.myCards.add(new AncientRules());
		DuelistMod.myCards.add(new BetaMagnet());
		DuelistMod.myCards.add(new DarkHole());
		DuelistMod.myCards.add(new DarkMagicianGirl());
		DuelistMod.myCards.add(new ExodiaHead());
		DuelistMod.myCards.add(new ExodiaLA());
		DuelistMod.myCards.add(new ExodiaLL());
		DuelistMod.myCards.add(new ExodiaRA());
		DuelistMod.myCards.add(new ExodiaRL());
		DuelistMod.myCards.add(new FeatherPho());
		DuelistMod.myCards.add(new GammaMagnet());
		DuelistMod.myCards.add(new Mausoleum());
		DuelistMod.myCards.add(new MillenniumShield());
		DuelistMod.myCards.add(new ValkMagnet());
		DuelistMod.myCards.add(new BarrelDragon());
		DuelistMod.myCards.add(new DarkMirrorForce());
		DuelistMod.myCards.add(new MagicCylinder());
		//DuelistMod.myCards.add(new NutrientZ());
		DuelistMod.myCards.add(new OjamaBlack());
		DuelistMod.myCards.add(new OjamaKing());
		DuelistMod.myCards.add(new OjamaKnight());
		DuelistMod.myCards.add(new Parasite());
		DuelistMod.myCards.add(new BeastFangs());
		DuelistMod.myCards.add(new BeaverWarrior());
		DuelistMod.myCards.add(new NaturiaBeast());
		DuelistMod.myCards.add(new NaturiaCliff());
		DuelistMod.myCards.add(new NaturiaDragonfly());
		DuelistMod.myCards.add(new NaturiaGuardian());
		DuelistMod.myCards.add(new NaturiaHorneedle());
		DuelistMod.myCards.add(new NaturiaLandoise());
		DuelistMod.myCards.add(new NaturiaMantis());
		DuelistMod.myCards.add(new NaturiaPineapple());
		DuelistMod.myCards.add(new NaturiaPumpkin());
		DuelistMod.myCards.add(new NaturiaRosewhip());
		DuelistMod.myCards.add(new NaturiaSacredTree());
		DuelistMod.myCards.add(new NaturiaStrawberry());
		DuelistMod.myCards.add(new IceQueen());
		DuelistMod.myCards.add(new ThousandEyesIdol());
		DuelistMod.myCards.add(new ThousandEyesRestrict());
		DuelistMod.myCards.add(new Zombyra());
		DuelistMod.myCards.add(new TwinHeadedFire());
		DuelistMod.myCards.add(new MindAir());
		DuelistMod.myCards.add(new FrontierWiseman());
		DuelistMod.myCards.add(new OjamaRed());
		DuelistMod.myCards.add(new OjamaBlue());
		DuelistMod.myCards.add(new OjamaDeltaHurricane());
		DuelistMod.myCards.add(new OjamaCountry());
		DuelistMod.myCards.add(new OjamaDuo());
		DuelistMod.myCards.add(new OjamaEmperor());
		DuelistMod.myCards.add(new OjamaPajama());
		DuelistMod.myCards.add(new Ojamassimilation());
		DuelistMod.myCards.add(new Ojamatch());
		DuelistMod.myCards.add(new OjamaTrio());
		DuelistMod.myCards.add(new Mathematician());
		DuelistMod.myCards.add(new ToonDarkMagicianGirl());
		DuelistMod.myCards.add(new GateGuardian());
		DuelistMod.myCards.add(new SangaWater());
		DuelistMod.myCards.add(new LegendaryFisherman());
		//DuelistMod.myCards.add(new DarkMimicLv1());
		//DuelistMod.myCards.add(new DarkMimicLv3());
		DuelistMod.myCards.add(new FairyBox());
		DuelistMod.myCards.add(new ToonTable());
		DuelistMod.myCards.add(new ToonDefense());
		DuelistMod.myCards.add(new ToonCannonSoldier());
		DuelistMod.myCards.add(new AngelTrumpeter());
		DuelistMod.myCards.add(new ArmoredBee());
		DuelistMod.myCards.add(new DarkBlade());
		DuelistMod.myCards.add(new DarkMasterZorc());
		DuelistMod.myCards.add(new SuperheavySoulbeads());
		DuelistMod.myCards.add(new SuperheavySoulbuster());
		//DuelistMod.myCards.add(new SuperheavySoulclaw());
		DuelistMod.myCards.add(new SuperheavySoulhorns());
		DuelistMod.myCards.add(new SuperheavySoulpiercer());
		DuelistMod.myCards.add(new SuperheavySoulshield());
		DuelistMod.myCards.add(new AquaSpirit());
		DuelistMod.myCards.add(new Umi());
		DuelistMod.myCards.add(new OhFish());
		DuelistMod.myCards.add(new DarkEnergy());
		DuelistMod.myCards.add(new SkullArchfiend());
		DuelistMod.myCards.add(new VanityFiend());
		DuelistMod.myCards.add(new RegenMummy());
		DuelistMod.myCards.add(new ArchfiendSoldier());
		DuelistMod.myCards.add(new FabledAshenveil());
		DuelistMod.myCards.add(new FabledGallabas());
		DuelistMod.myCards.add(new BattleOx());
		DuelistMod.myCards.add(new DarkstormDragon());
		DuelistMod.myCards.add(new GrossGhost());
		DuelistMod.myCards.add(new PortraitSecret());
		DuelistMod.myCards.add(new RedSprinter());
		DuelistMod.myCards.add(new Tierra());
		DuelistMod.myCards.add(new PatricianDarkness());
		DuelistMod.myCards.add(new WingedKuriboh());
		DuelistMod.myCards.add(new JunkKuriboh());
		DuelistMod.myCards.add(new FluteKuriboh());
		DuelistMod.myCards.add(new ApprenticeIllusionMagician());
		DuelistMod.myCards.add(new BlueDragonSummoner());
		DuelistMod.myCards.add(new DarkHorizon());
		DuelistMod.myCards.add(new Skelesaurus());
		DuelistMod.myCards.add(new RedGadget());
		DuelistMod.myCards.add(new GreenGadget());
		DuelistMod.myCards.add(new YellowGadget());
		DuelistMod.myCards.add(new BlizzardWarrior());
		DuelistMod.myCards.add(new DarkPaladin());
		DuelistMod.myCards.add(new BigKoala());
		DuelistMod.myCards.add(new AncientGearChimera());
		DuelistMod.myCards.add(new AncientGearGadjiltron());
		DuelistMod.myCards.add(new AncientGearGolem());
		DuelistMod.myCards.add(new WhiteMagicalHat());
		DuelistMod.myCards.add(new BattleguardKing());
		DuelistMod.myCards.add(new BattleFootballer());
		DuelistMod.myCards.add(new EarthquakeGiant());
		DuelistMod.myCards.add(new EvilswarmHeliotrope());
		DuelistMod.myCards.add(new GadgetSoldier());
		DuelistMod.myCards.add(new LegendaryFlameLord());
		DuelistMod.myCards.add(new TurretWarrior());
		DuelistMod.myCards.add(new WormApocalypse());
		DuelistMod.myCards.add(new WormBarses());
		DuelistMod.myCards.add(new WormWarlord());
		DuelistMod.myCards.add(new WormKing());
		DuelistMod.myCards.add(new ArmorBreaker());
		DuelistMod.myCards.add(new GauntletWarrior());
		DuelistMod.myCards.add(new CommandKnight());
		DuelistMod.myCards.add(new GaiaMidnight());
		DuelistMod.myCards.add(new DrivenDaredevil());
		DuelistMod.myCards.add(new GilfordLegend());
		DuelistMod.myCards.add(new ReinforcementsArmy());
		DuelistMod.myCards.add(new BlockGolem());
		DuelistMod.myCards.add(new DokiDoki());
		DuelistMod.myCards.add(new GiantSoldierSteel());
		DuelistMod.myCards.add(new Doomdog());
		DuelistMod.myCards.add(new RedMirror());
		DuelistMod.myCards.add(new LostBlueBreaker());
		DuelistMod.myCards.add(new Wingedtortoise());
		DuelistMod.myCards.add(new GemKnightAmethyst());
		DuelistMod.myCards.add(new ToadallyAwesome());
		DuelistMod.myCards.add(new DarknessNeosphere());
		DuelistMod.myCards.add(new RainbowJar());
		DuelistMod.myCards.add(new WingedKuriboh9());
		DuelistMod.myCards.add(new WingedKuriboh10());
		DuelistMod.myCards.add(new SpikedGillman());
		DuelistMod.myCards.add(new MagicalStone());
		DuelistMod.myCards.add(new Kuribohrn());
		DuelistMod.myCards.add(new BigWaveSmallWave());
		DuelistMod.myCards.add(new DropOff());
		DuelistMod.myCards.add(new GraydleSlimeJr());
		DuelistMod.myCards.add(new FrillerRabca());
		DuelistMod.myCards.add(new GiantRex());
		DuelistMod.myCards.add(new IronhammerGiant());
		DuelistMod.myCards.add(new GiantOrc());
		DuelistMod.myCards.add(new ChaosAncientGearGiant());
		DuelistMod.myCards.add(new PowerGiant());
		DuelistMod.myCards.add(new DarkFusion());
		DuelistMod.myCards.add(new WorldTree());
		DuelistMod.myCards.add(new TyrantWing());
		//DuelistMod.myCards.add(new WhitefishSalvage());
		DuelistMod.myCards.add(new SwampFrog());
		DuelistMod.myCards.add(new SharkStickers());
		DuelistMod.myCards.add(new RageDeepSea());
		DuelistMod.myCards.add(new SpearfishSoldier());
		DuelistMod.myCards.add(new HydraViper());
		DuelistMod.myCards.add(new AbyssWarrior());
		DuelistMod.myCards.add(new AmphibiousBugroth());
		DuelistMod.myCards.add(new BlizzardDefender());
		DuelistMod.myCards.add(new Boneheimer());
		DuelistMod.myCards.add(new CannonballSpearShellfish());
		//DuelistMod.myCards.add(new CrystalEmeraldTortoise());
		DuelistMod.myCards.add(new DeepDiver());
		DuelistMod.myCards.add(new CatShark());
		DuelistMod.myCards.add(new BigWhale());
		DuelistMod.myCards.add(new BlizzardThunderbird());
		DuelistMod.myCards.add(new DiamondDust());
		DuelistMod.myCards.add(new GoldenFlyingFish());
		DuelistMod.myCards.add(new RainbowBridge());
		DuelistMod.myCards.add(new Monokeros());
		DuelistMod.myCards.add(new EarthGiant());
		DuelistMod.myCards.add(new RainbowKuriboh());
		DuelistMod.myCards.add(new ClearKuriboh());
		DuelistMod.myCards.add(new Linkuriboh());
		DuelistMod.myCards.add(new FishSwaps());
		DuelistMod.myCards.add(new FishKicks());
		DuelistMod.myCards.add(new FishRain());
		DuelistMod.myCards.add(new PoseidonWave());
		DuelistMod.myCards.add(new AncientGearBox());
		DuelistMod.myCards.add(new Deskbot001());
		DuelistMod.myCards.add(new Deskbot002());
		DuelistMod.myCards.add(new Deskbot009());
		DuelistMod.myCards.add(new GrandSpellbookTower());
		DuelistMod.myCards.add(new SpellbookPower());
		DuelistMod.myCards.add(new SpellbookLife());
		DuelistMod.myCards.add(new SpellbookMiracle());
		DuelistMod.myCards.add(new SpellbookKnowledge());
		DuelistMod.myCards.add(new DrillBarnacle());
		DuelistMod.myCards.add(new ImperialTomb());
		DuelistMod.myCards.add(new ZombieMaster());
		DuelistMod.myCards.add(new GiantTrapHole());
		DuelistMod.myCards.add(new GravityBlaster());
		DuelistMod.myCards.add(new FlyingPegasus());
		DuelistMod.myCards.add(new CyberneticFusion());
		DuelistMod.myCards.add(new ReadyForIntercepting());
		DuelistMod.myCards.add(new BigEye());
		DuelistMod.myCards.add(new IronCall());
		DuelistMod.myCards.add(new IronDraw());
		DuelistMod.myCards.add(new LimiterRemoval());
		DuelistMod.myCards.add(new MachineDuplication());
		DuelistMod.myCards.add(new VampireLord());
		DuelistMod.myCards.add(new VampireGenesis());
		DuelistMod.myCards.add(new VampireGrace());
		DuelistMod.myCards.add(new VampireFraulein());
		DuelistMod.myCards.add(new ShadowVampire());
		DuelistMod.myCards.add(new Mixeroid());
		DuelistMod.myCards.add(new Oilman());
		DuelistMod.myCards.add(new IlBlud());
		DuelistMod.myCards.add(new OutriggerExtension());
		DuelistMod.myCards.add(new JumboDrill());
		DuelistMod.myCards.add(new YamiForm());
		DuelistMod.myCards.add(new DestructPotion());
		DuelistMod.myCards.add(new CallMummy());
		DuelistMod.myCards.add(new Biofalcon());
		DuelistMod.myCards.add(new Deskbot004());
		DuelistMod.myCards.add(new Deskbot005());
		DuelistMod.myCards.add(new Hayate());
		DuelistMod.myCards.add(new Spore());
		DuelistMod.myCards.add(new RainbowOverdragon());
		DuelistMod.myCards.add(new RainbowGravity());
		DuelistMod.myCards.add(new RainbowLife());
		DuelistMod.myCards.add(new SilverApples());
		DuelistMod.myCards.add(new Uminotaurus());
		DuelistMod.myCards.add(new BigDesFrog());
		DuelistMod.myCards.add(new AtlanteanAttackSquad());
		DuelistMod.myCards.add(new DarklordSuperbia());
		DuelistMod.myCards.add(new ToonGoblinAttack());
		DuelistMod.myCards.add(new ToonMaskedSorcerer());
		DuelistMod.myCards.add(new MillenniumSpellbook());
		DuelistMod.myCards.add(new LightningVortex());
		DuelistMod.myCards.add(new ArmageddonDragonEmp());
		DuelistMod.myCards.add(new BackgroundDragon());
		DuelistMod.myCards.add(new BoosterDragon());
		DuelistMod.myCards.add(new Carboneddon());
		DuelistMod.myCards.add(new BusterBladerDDS());
		DuelistMod.myCards.add(new SilverDragon());
		DuelistMod.myCards.add(new AmuletDragon());
		DuelistMod.myCards.add(new EyeTimaeus());
		DuelistMod.myCards.add(new DragonShield());
		DuelistMod.myCards.add(new SpiritPharaoh());
		DuelistMod.myCards.add(new DarkAssailant());
		DuelistMod.myCards.add(new CallAtlanteans());
		DuelistMod.myCards.add(new DoomShaman());
		DuelistMod.myCards.add(new BlackPendant());
		DuelistMod.myCards.add(new LightningRodLord());
		DuelistMod.myCards.add(new MiracleFertilizer());
		DuelistMod.myCards.add(new MiraculousDescent());
		DuelistMod.myCards.add(new StatueAnguishPattern());
		DuelistMod.myCards.add(new MudGolem());
		DuelistMod.myCards.add(new Mudora());
		DuelistMod.myCards.add(new MudragonSwamp());
		DuelistMod.myCards.add(new Mudballman());
		DuelistMod.myCards.add(new VoidExpansion());
		DuelistMod.myCards.add(new VoidVanishment());
		DuelistMod.myCards.add(new LavaDragon());
		DuelistMod.myCards.add(new FlameTiger());
		DuelistMod.myCards.add(new Reload());
		DuelistMod.myCards.add(new ArchfiendCommander());
		DuelistMod.myCards.add(new ArchfiendGeneral());
		DuelistMod.myCards.add(new ArchfiendHeiress());
		DuelistMod.myCards.add(new ArchfiendInterceptor());
		DuelistMod.myCards.add(new BeastTalwar());
		DuelistMod.myCards.add(new DarkSimorgh());
		DuelistMod.myCards.add(new DarkTinker());
		DuelistMod.myCards.add(new DoomcaliberKnight());
		DuelistMod.myCards.add(new DoomsdayHorror());
		DuelistMod.myCards.add(new EvilswarmNightmare());
		DuelistMod.myCards.add(new ForbiddenLance());
		DuelistMod.myCards.add(new GarbageLord());
		DuelistMod.myCards.add(new MagicalBlast());
		DuelistMod.myCards.add(new MaskedDragon());
		DuelistMod.myCards.add(new MirageDragon());
		DuelistMod.myCards.add(new StarBlast());
		DuelistMod.myCards.add(new StardustDragon());
		DuelistMod.myCards.add(new TwinHeadedBehemoth());
		DuelistMod.myCards.add(new TwinHeadedWolf());
		DuelistMod.myCards.add(new ViceDragon());
		DuelistMod.myCards.add(new WanderingKing());
		DuelistMod.myCards.add(new AmbitiousGofer());
		DuelistMod.myCards.add(new CosmoBrain());
		DuelistMod.myCards.add(new DiffusionWaveMotion());
		DuelistMod.myCards.add(new ForbiddenChalice());
		DuelistMod.myCards.add(new GoblinKing());
		DuelistMod.myCards.add(new HorusServant());
		DuelistMod.myCards.add(new PutridPudding());
		DuelistMod.myCards.add(new QueenDragunDjinn());
		DuelistMod.myCards.add(new FutureFusion());
		DuelistMod.myCards.add(new DoomDonuts());
		DuelistMod.myCards.add(new InariFire());
		DuelistMod.myCards.add(new MagiciansRobe());
		DuelistMod.myCards.add(new MagiciansRod());
		DuelistMod.myCards.add(new TotemDragon());
		DuelistMod.myCards.add(new WonderWand());
		DuelistMod.myCards.add(new KamionTimelord());
		DuelistMod.myCards.add(new IrisEarthMother());
		DuelistMod.myCards.add(new RainbowRefraction());
		DuelistMod.myCards.add(new CrystalRaigeki());
		DuelistMod.myCards.add(new RainbowRuins());
		DuelistMod.myCards.add(new RainbowMagician());
		DuelistMod.myCards.add(new RainbowDarkDragon());
		DuelistMod.myCards.add(new MaleficRainbowDragon());
		DuelistMod.myCards.add(new RainbowDragon());
		DuelistMod.myCards.add(new HourglassLife());
		DuelistMod.myCards.add(new Eva());
		DuelistMod.myCards.add(new HappyLover());
		DuelistMod.myCards.add(new DunamesDarkWitch());
		DuelistMod.myCards.add(new RainbowNeos());
		DuelistMod.myCards.add(new RainbowFlower());
		DuelistMod.myCards.add(new RainbowKuribohBasic());
		DuelistMod.myCards.add(new ElectromagneticShield());
		DuelistMod.myCards.add(new Electrowhip());
		DuelistMod.myCards.add(new QueenAngelRoses());
		DuelistMod.myCards.add(new CyberArchfiend());
		DuelistMod.myCards.add(new BusterDrake());
		DuelistMod.myCards.add(new RedRisingDragon());
		DuelistMod.myCards.add(new AncientGearFist());
		DuelistMod.myCards.add(new BerserkerCrush());
		DuelistMod.myCards.add(new CombinationAttack());
		DuelistMod.myCards.add(new CoreBlaster());
		DuelistMod.myCards.add(new FeatherShot());
		DuelistMod.myCards.add(new FuryFire());
		DuelistMod.myCards.add(new InfernoFireBlast());
		DuelistMod.myCards.add(new MeteorDestruction());
		DuelistMod.myCards.add(new SilentDoom());
		DuelistMod.myCards.add(new SparkBlaster());
		DuelistMod.myCards.add(new SpellShatteringArrow());
		DuelistMod.myCards.add(new SpiralSpearStrike());
		DuelistMod.myCards.add(new VenomShot());
		DuelistMod.myCards.add(new Wildfire());
		DuelistMod.myCards.add(new CharcoalInpachi());
		DuelistMod.myCards.add(new WaterDragon());
		DuelistMod.myCards.add(new AmuletAmbition());
		DuelistMod.myCards.add(new ArchfiendZombieSkull());
		DuelistMod.myCards.add(new CorrodingShark());
		DuelistMod.myCards.add(new FlameGhost());
		DuelistMod.myCards.add(new Gernia());
		DuelistMod.myCards.add(new GoblinZombie());
		DuelistMod.myCards.add(new Gozuki());
		DuelistMod.myCards.add(new Kasha());
		DuelistMod.myCards.add(new OniTankT34());
		DuelistMod.myCards.add(new RedHeadedOni());
		DuelistMod.myCards.add(new ZombieWarrior());
		DuelistMod.myCards.add(new BlueBloodedOni());
		DuelistMod.myCards.add(new DesLacooda());
		DuelistMod.myCards.add(new DespairFromDark());
		DuelistMod.myCards.add(new EndlessDecay());
		DuelistMod.myCards.add(new HauntedShrine());
		DuelistMod.myCards.add(new OniGamiCombo());
		DuelistMod.myCards.add(new PlaguespreaderZombie());
		DuelistMod.myCards.add(new YellowBelliedOni());
		DuelistMod.myCards.add(new ZombieWorld());
		DuelistMod.myCards.add(new FearFromDark());
		DuelistMod.myCards.add(new BeserkDragon());
		DuelistMod.myCards.add(new PainPainter());
		DuelistMod.myCards.add(new DoomkaiserDragon());
		DuelistMod.myCards.add(new UnderworldCannon());
		DuelistMod.myCards.add(new DragonZombie());
		DuelistMod.myCards.add(new ZombieMammoth());
		DuelistMod.myCards.add(new FlyingSaucer());
		DuelistMod.myCards.add(new Relinkuriboh());
		DuelistMod.myCards.add(new GoldenBlastJuggler());
		DuelistMod.myCards.add(new CosmicHorrorGangiel());
		DuelistMod.myCards.add(new AlienTelepath());
		DuelistMod.myCards.add(new BlastAsmodian());
		DuelistMod.myCards.add(new BlastHeldTribute());
		DuelistMod.myCards.add(new BlastMagician());
		DuelistMod.myCards.add(new BlastWithChain());
		DuelistMod.myCards.add(new ExploderDragonwing());
		DuelistMod.myCards.add(new OrbitalBombardment());
		DuelistMod.myCards.add(new AncientGearExplosive());
		DuelistMod.myCards.add(new BlasterDragonInfernos());
		DuelistMod.myCards.add(new BlastingRuins());
		DuelistMod.myCards.add(new BlossomBombardment());
		DuelistMod.myCards.add(new BlastingFuse());
		DuelistMod.myCards.add(new CemetaryBomb());
		DuelistMod.myCards.add(new RockBombardment());
		DuelistMod.myCards.add(new Obliterate());
		DuelistMod.myCards.add(new ExodiaNecross());
		DuelistMod.myCards.add(new AlphaElectro());
		DuelistMod.myCards.add(new DeltaMagnet());
		DuelistMod.myCards.add(new Berserkion());
		DuelistMod.myCards.add(new BetaElectro());
		DuelistMod.myCards.add(new GammaElectro());
		DuelistMod.myCards.add(new MagneticField());
		DuelistMod.myCards.add(new UmbralHorrorGhoul());
		DuelistMod.myCards.add(new RagingMadPlants());
		DuelistMod.myCards.add(new ThornMalice());
		DuelistMod.myCards.add(new ArsenalBug());
		DuelistMod.myCards.add(new CrossSwordBeetle());
		DuelistMod.myCards.add(new MultiplicationOfAnts());
		DuelistMod.myCards.add(new DarkSpider());
		DuelistMod.myCards.add(new CocoonUltraEvolution());
		DuelistMod.myCards.add(new PinchHopper());
		DuelistMod.myCards.add(new UndergroundArachnid());
		DuelistMod.myCards.add(new RockSunrise());
		DuelistMod.myCards.add(new BlackRoseDragon());
		DuelistMod.myCards.add(new BlackRoseMoonlight());
		DuelistMod.myCards.add(new FallenAngelRoses());
		DuelistMod.myCards.add(new RedRoseDragon());
		DuelistMod.myCards.add(new RosePaladin());
		DuelistMod.myCards.add(new TwilightRoseKnight());
		DuelistMod.myCards.add(new BirdRoses());
		DuelistMod.myCards.add(new BlockSpider());
		DuelistMod.myCards.add(new BlueRoseDragon());
		DuelistMod.myCards.add(new ClearWingDragon());
		DuelistMod.myCards.add(new CopyPlant());
		DuelistMod.myCards.add(new CrystalWingDragon());
		DuelistMod.myCards.add(new FrozenRose());
		DuelistMod.myCards.add(new MarkRose());
		DuelistMod.myCards.add(new RevivalRose());
		DuelistMod.myCards.add(new RoseArcher());
		DuelistMod.myCards.add(new RoseLover());
		DuelistMod.myCards.add(new RoseWitch());
		DuelistMod.myCards.add(new UltraPolymerization());
		DuelistMod.myCards.add(new WhiteRoseDragon());
		DuelistMod.myCards.add(new WitchBlackRose());
		DuelistMod.myCards.add(new BloomingDarkestRose());
		DuelistMod.myCards.add(new SplendidRose());
		DuelistMod.myCards.add(new CactusBouncer());
		DuelistMod.myCards.add(new Inmato());
		DuelistMod.myCards.add(new PlantFoodChain());
		DuelistMod.myCards.add(new SeedCannon());
		DuelistMod.myCards.add(new BotanicalLion());
		DuelistMod.myCards.add(new BotanicalGirl());
		DuelistMod.myCards.add(new DarkworldThorns());
		DuelistMod.myCards.add(new LordPoison());
		DuelistMod.myCards.add(new MillenniumScorpion());
		DuelistMod.myCards.add(new UmbralHorrorGhost());
		DuelistMod.myCards.add(new UmbralHorrorWilloWisp());
		DuelistMod.myCards.add(new SkilledDarkMagician());
		DuelistMod.myCards.add(new UmbralHorrorUniform());
		DuelistMod.myCards.add(new DarkHunter());
		DuelistMod.myCards.add(new Slushy());
		DuelistMod.myCards.add(new PharaohBlessing());
		DuelistMod.myCards.add(new OneForOne());
		DuelistMod.myCards.add(new MonsterEggSpecial());
		DuelistMod.myCards.add(new RainbowMedicine());
		DuelistMod.myCards.add(new MonsterEggSpecial());
		DuelistMod.myCards.add(new DarkCubicLord());
		DuelistMod.myCards.add(new Overworked());
		DuelistMod.myCards.add(new GoldenSparks());
		DuelistMod.myCards.add(new BloodSparks());
		DuelistMod.myCards.add(new MagicSparks());
		DuelistMod.myCards.add(new StormSparks());
		DuelistMod.myCards.add(new DarkSparks());
		DuelistMod.myCards.add(new ChrysalisMole());
		DuelistMod.myCards.add(new AssaultArmor());
		DuelistMod.myCards.add(new BambooSwordBroken());
		DuelistMod.myCards.add(new BambooSwordBurning());
		DuelistMod.myCards.add(new BambooSwordCursed());
		DuelistMod.myCards.add(new BambooSwordGolden());
		DuelistMod.myCards.add(new BambooSwordSoul());
		DuelistMod.myCards.add(new BattleWarrior());
		DuelistMod.myCards.add(new BladeArmorNinja());
		DuelistMod.myCards.add(new BladeKnight());
		DuelistMod.myCards.add(new BrushfireKnight());
		DuelistMod.myCards.add(new BullBlader());
		DuelistMod.myCards.add(new ColossalFighter());
		DuelistMod.myCards.add(new ComboFighter());
		DuelistMod.myCards.add(new ComboMaster());
		DuelistMod.myCards.add(new CrossAttack());
		DuelistMod.myCards.add(new DarkGrepher());
		DuelistMod.myCards.add(new DustKnight());
		DuelistMod.myCards.add(new FeedbackWarrior());
		DuelistMod.myCards.add(new GoyoChaser());
		DuelistMod.myCards.add(new GoyoKing());
		DuelistMod.myCards.add(new GravityWarrior());
		DuelistMod.myCards.add(new JunkWarrior());
		DuelistMod.myCards.add(new LegendarySword());
		DuelistMod.myCards.add(new LightningBlade());
		DuelistMod.myCards.add(new MightyWarrior());
		DuelistMod.myCards.add(new NinjaGrandmaster());
		DuelistMod.myCards.add(new NitroWarrior());
		DuelistMod.myCards.add(new SoldierLady());
		DuelistMod.myCards.add(new SwordDragonSoul());
		DuelistMod.myCards.add(new AdvanceForce());
		DuelistMod.myCards.add(new AfterGenocide());
		DuelistMod.myCards.add(new AfterTheStorm());
		DuelistMod.myCards.add(new AgainstTheWind());
		DuelistMod.myCards.add(new BattleguardHowling());
		DuelistMod.myCards.add(new BattleguardRage());
		DuelistMod.myCards.add(new ChaosSeed());
		DuelistMod.myCards.add(new CrystalBlessing());
		DuelistMod.myCards.add(new CrystalTree());
		DuelistMod.myCards.add(new CubicWave());
		DuelistMod.myCards.add(new DarkBurningAttack());
		DuelistMod.myCards.add(new DarkBurningMagic());
		DuelistMod.myCards.add(new DarkCrusader());
		DuelistMod.myCards.add(new DarkOccultism());
		DuelistMod.myCards.add(new DawnKnight());
		DuelistMod.myCards.add(new DeltaAttacker());
		DuelistMod.myCards.add(new Downbeat());
		DuelistMod.myCards.add(new EgoBoost());
		DuelistMod.myCards.add(new ElfLight());
		DuelistMod.myCards.add(new EulerCircuit());
		//DuelistMod.myCards.add(new FengshengMirror());
		DuelistMod.myCards.add(new GladiatorReturn());
		DuelistMod.myCards.add(new GlowingCrossbow());
		DuelistMod.myCards.add(new GoyoDefender());
		DuelistMod.myCards.add(new GoyoEmperor());
		DuelistMod.myCards.add(new GravityLash());
		DuelistMod.myCards.add(new GuardianOrder());
		DuelistMod.myCards.add(new HardArmor());
		DuelistMod.myCards.add(new HarmonicWaves());
		DuelistMod.myCards.add(new HerculeanPower());
		DuelistMod.myCards.add(new HeroRing());
		DuelistMod.myCards.add(new HiddenArmory());
		DuelistMod.myCards.add(new LegendHeart());
		DuelistMod.myCards.add(new LegendaryBlackBelt());
		DuelistMod.myCards.add(new LightLaser());
		DuelistMod.myCards.add(new MagnumShield());
		DuelistMod.myCards.add(new WarriorReturningAlive());
		DuelistMod.myCards.add(new CommanderSwords());
		DuelistMod.myCards.add(new CubicKarma());
		DuelistMod.myCards.add(new FightingSpirit());
		DuelistMod.myCards.add(new Flint());
		DuelistMod.myCards.add(new GridRod());
		DuelistMod.myCards.add(new ReinforceTruth());
		DuelistMod.myCards.add(new SpiritForce());
		DuelistMod.myCards.add(new WeaponChange());
		DuelistMod.myCards.add(new Sogen());
		DuelistMod.myCards.add(new GreenGraveOni());
		DuelistMod.myCards.add(new PurplePainOni());
		DuelistMod.myCards.add(new GreyGreedOni());
		DuelistMod.myCards.add(new BrilliantSpark());
		DuelistMod.myCards.add(new WhiteNinja());

		DuelistMod.myCards.add(new CyberRaider());
		DuelistMod.myCards.add(new SatelliteCannon());
		DuelistMod.myCards.add(new MaxWarrior());
		DuelistMod.myCards.add(new CircleFireKings());
		DuelistMod.myCards.add(new OnslaughtFireKings());
		DuelistMod.myCards.add(new WhiteHowling());
		DuelistMod.myCards.add(new Alpacaribou());
		DuelistMod.myCards.add(new Anteater());
		DuelistMod.myCards.add(new AttackTheMoon());
		DuelistMod.myCards.add(new BarkionBark());
		DuelistMod.myCards.add(new Blockman());
		DuelistMod.myCards.add(new BrainCrusher());
		DuelistMod.myCards.add(new Canyon());
		DuelistMod.myCards.add(new CastleGate());
		DuelistMod.myCards.add(new GolemSentry());
		DuelistMod.myCards.add(new ClosedForest());
		DuelistMod.myCards.add(new CrystalRose());
		DuelistMod.myCards.add(new DestroyerGolem());
		DuelistMod.myCards.add(new DigitalBug());
		DuelistMod.myCards.add(new DummyGolem());
		DuelistMod.myCards.add(new EarthEffigy());
		DuelistMod.myCards.add(new ElephantStatueBlessing());
		DuelistMod.myCards.add(new ElephantStatueDisaster());
		DuelistMod.myCards.add(new EvilswarmGolem());
		DuelistMod.myCards.add(new ExterioFang());
		DuelistMod.myCards.add(new FossilDig());
		DuelistMod.myCards.add(new FossilExcavation());
		DuelistMod.myCards.add(new FossilTusker());
		DuelistMod.myCards.add(new GateBlocker());
		DuelistMod.myCards.add(new GemArmadillo());
		DuelistMod.myCards.add(new GemElephant());
		DuelistMod.myCards.add(new GemKnightAlexandrite());
		DuelistMod.myCards.add(new GemKnightCrystal());
		DuelistMod.myCards.add(new GemKnightEmerald());
		DuelistMod.myCards.add(new GemKnightLapis());
		DuelistMod.myCards.add(new GemKnightLazuli());
		DuelistMod.myCards.add(new GemKnightMasterDiamond());
		DuelistMod.myCards.add(new GemKnightObsidian());
		DuelistMod.myCards.add(new GemKnightPearl());
		DuelistMod.myCards.add(new GemKnightQuartz());
		DuelistMod.myCards.add(new GemKnightZirconia());
		DuelistMod.myCards.add(new HuntingInstinct());
		DuelistMod.myCards.add(new LairWire());
		DuelistMod.myCards.add(new LeodrakeMane());
		DuelistMod.myCards.add(new LonefireBlossom());
		DuelistMod.myCards.add(new LuminousMoss());
		DuelistMod.myCards.add(new NaturalDisaster());
		DuelistMod.myCards.add(new NatureReflection());
		DuelistMod.myCards.add(new NaturiaAntjaw());
		DuelistMod.myCards.add(new NaturiaBambooShoot());
		DuelistMod.myCards.add(new NaturiaBarkion());
		DuelistMod.myCards.add(new NaturiaBeans());
		DuelistMod.myCards.add(new NaturiaBeetle());
		DuelistMod.myCards.add(new NaturiaBrambi());
		DuelistMod.myCards.add(new NaturiaButterfly());
		DuelistMod.myCards.add(new NaturiaCherries());
		DuelistMod.myCards.add(new NaturiaCosmobeet());
		DuelistMod.myCards.add(new NaturiaEggplant());
		DuelistMod.myCards.add(new NaturiaExterio());
		DuelistMod.myCards.add(new NaturiaGaiastrio());
		DuelistMod.myCards.add(new NaturiaHydrangea());
		DuelistMod.myCards.add(new NaturiaLadybug());
		DuelistMod.myCards.add(new NaturiaLeodrake());
		DuelistMod.myCards.add(new NaturiaMarron());
		DuelistMod.myCards.add(new NaturiaMosquito());
		DuelistMod.myCards.add(new NaturiaParadizo());
		DuelistMod.myCards.add(new NaturiaRagweed());
		DuelistMod.myCards.add(new NaturiaRock());
		DuelistMod.myCards.add(new NaturiaSpiderfang());
		DuelistMod.myCards.add(new NaturiaStagBeetle());
		DuelistMod.myCards.add(new NaturiaStinkbug());
		DuelistMod.myCards.add(new NaturiaStrawberry());
		DuelistMod.myCards.add(new NaturiaSunflower());
		DuelistMod.myCards.add(new NaturiaTulip());
		DuelistMod.myCards.add(new NaturiaVein());
		DuelistMod.myCards.add(new NaturiaWhiteOak());
		DuelistMod.myCards.add(new SeedSacredTree());
		DuelistMod.myCards.add(new SeismicShockwave());
		DuelistMod.myCards.add(new SpacetimeTranscendence());
		DuelistMod.myCards.add(new SummoningSwarm());
		DuelistMod.myCards.add(new WildNatureRelease());
		DuelistMod.myCards.add(new WormBait());
		DuelistMod.myCards.add(new CorrosiveScales());
		DuelistMod.myCards.add(new Pollinosis());
		DuelistMod.myCards.add(new DemiseLand());
		DuelistMod.myCards.add(new SurvivalInstinct());
		DuelistMod.myCards.add(new ConvulsionNature());
		DuelistMod.myCards.add(new NaturiaForest());
		DuelistMod.myCards.add(new CatapultZone());
		DuelistMod.myCards.add(new GolemSentry());
		DuelistMod.myCards.add(new GraniteLoyalist());
		DuelistMod.myCards.add(new LostGuardian());
		DuelistMod.myCards.add(new MagicHoleGolem());
		DuelistMod.myCards.add(new MasterMagmaBlacksmith());
		DuelistMod.myCards.add(new MegarockDragon());
		DuelistMod.myCards.add(new MillenniumGolem());
		DuelistMod.myCards.add(new ObsidianDragon());
		DuelistMod.myCards.add(new ReleaseFromStone());
		DuelistMod.myCards.add(new RockstoneWarrior());
		DuelistMod.myCards.add(new StoneDragon());
		DuelistMod.myCards.add(new WeepingIdol());
		DuelistMod.myCards.add(new Solidarity());
		DuelistMod.myCards.add(new PoisonChain());
		DuelistMod.myCards.add(new SlotMachine());
		DuelistMod.myCards.add(new BeeListSoldier());
		DuelistMod.myCards.add(new BiteBug());
		DuelistMod.myCards.add(new DragonDowser());
		DuelistMod.myCards.add(new HunterSpider());
		DuelistMod.myCards.add(new NeoBug());
		DuelistMod.myCards.add(new RazorLizard());
		DuelistMod.myCards.add(new TornadoDragon());
		DuelistMod.myCards.add(new AtomicFirefly());
		DuelistMod.myCards.add(new CobraJar());
		DuelistMod.myCards.add(new DarkSpider());
		DuelistMod.myCards.add(new FirestormProminence());
		DuelistMod.myCards.add(new Gagagigo());
		DuelistMod.myCards.add(new GroundSpider());
		DuelistMod.myCards.add(new KarakuriSpider());
		DuelistMod.myCards.add(new Lightserpent());
		DuelistMod.myCards.add(new MetalArmoredBug());
		DuelistMod.myCards.add(new RelinquishedSpider());
		DuelistMod.myCards.add(new ZefraMetaltron());
		DuelistMod.myCards.add(new ReptiliannePoison());
		DuelistMod.myCards.add(new SpiderWeb());
		DuelistMod.myCards.add(new BugEmergency());
		DuelistMod.myCards.add(new BeakedSnake());
		DuelistMod.myCards.add(new BigInsect());
		DuelistMod.myCards.add(new BlazewingButterfly());
		DuelistMod.myCards.add(new DrillBug());
		DuelistMod.myCards.add(new GiantPairfish());
		DuelistMod.myCards.add(new HerculesBeetle());
		DuelistMod.myCards.add(new Suanni());
		DuelistMod.myCards.add(new Yazi());
		DuelistMod.myCards.add(new Aztekipede());
		DuelistMod.myCards.add(new BirdParadise());
		DuelistMod.myCards.add(new Chiwen());
		DuelistMod.myCards.add(new PoseidonBeetle());
		DuelistMod.myCards.add(new Greatfly());
		DuelistMod.myCards.add(new InsectPrincess());
		DuelistMod.myCards.add(new Inzektron());
		DuelistMod.myCards.add(new MareMare());
		DuelistMod.myCards.add(new PoisonMummy());
		DuelistMod.myCards.add(new ResonanceInsect());
		DuelistMod.myCards.add(new PoisonOldMan());
		DuelistMod.myCards.add(new BugSignal());
		DuelistMod.myCards.add(new BugMatrix());
		DuelistMod.myCards.add(new Forest());
		DuelistMod.myCards.add(new Denglong());
		DuelistMod.myCards.add(new DestructionCyclone());
		DuelistMod.myCards.add(new GigaCricket());
		DuelistMod.myCards.add(new GigaMantis());
		DuelistMod.myCards.add(new HowlingInsect());
		DuelistMod.myCards.add(new LinkSpider());
		DuelistMod.myCards.add(new Taotie());
		DuelistMod.myCards.add(new Zektahawk());
		DuelistMod.myCards.add(new Zektarrow());
		DuelistMod.myCards.add(new Zektkaliber());
		DuelistMod.myCards.add(new DarkBug());
		DuelistMod.myCards.add(new InsectKing());
		DuelistMod.myCards.add(new MetamorphInsectQueen());
		DuelistMod.myCards.add(new MirrorLadybug());
		DuelistMod.myCards.add(new PoisonousMayakashi());
		DuelistMod.myCards.add(new SkullMarkLadybug());
		DuelistMod.myCards.add(new PoisonousWinds());
		DuelistMod.myCards.add(new SpiderEgg());
		DuelistMod.myCards.add(new IgnisHeat());
		DuelistMod.myCards.add(new PoisonFangs());
		DuelistMod.myCards.add(new SpiritualForest());
		DuelistMod.myCards.add(new WallThorns());
		DuelistMod.myCards.add(new Bixi());
		DuelistMod.myCards.add(new ArmedDragon10());
		DuelistMod.myCards.add(new ArmedDragon7());
		DuelistMod.myCards.add(new AtomicScrapDragon());
		DuelistMod.myCards.add(new Beatraptor());
		DuelistMod.myCards.add(new BlackBrachios());
		DuelistMod.myCards.add(new BlackBrutdrago());
		DuelistMod.myCards.add(new BlackPtera());
		DuelistMod.myCards.add(new BlackStego());
		DuelistMod.myCards.add(new BlackTyranno());
		DuelistMod.myCards.add(new BlackVeloci());
		DuelistMod.myCards.add(new BurstBreath());
		DuelistMod.myCards.add(new ChimeratechOverdragon());
		DuelistMod.myCards.add(new CyberDinosaur());
		DuelistMod.myCards.add(new CyberDragonInfinity());
		//DuelistMod.myCards.add(new CyberDragonNova());
		DuelistMod.myCards.add(new CyberEndDragon());
		DuelistMod.myCards.add(new CyberTwinDragon());
		DuelistMod.myCards.add(new CyberValley());
		DuelistMod.myCards.add(new DarkDriceratops());
		DuelistMod.myCards.add(new DarkHorus());
		DuelistMod.myCards.add(new Destroyersaurus());
		DuelistMod.myCards.add(new Earthquake());
		DuelistMod.myCards.add(new EvolutionBurst());
		DuelistMod.myCards.add(new FireDarts());
		DuelistMod.myCards.add(new GenesisDragon());
		DuelistMod.myCards.add(new Hydrogeddon());
		DuelistMod.myCards.add(new InfernityDoomDragon());
		DuelistMod.myCards.add(new Kabazauls());
		DuelistMod.myCards.add(new Lancephorhynchus());
		DuelistMod.myCards.add(new MadFlameKaiju());
		DuelistMod.myCards.add(new MadSwordBeast());
		//DuelistMod.myCards.add(new Megalosmasher());
		DuelistMod.myCards.add(new MythicWaterDragon());
		DuelistMod.myCards.add(new Pyrorex());
		DuelistMod.myCards.add(new Sabersaurus());
		DuelistMod.myCards.add(new SpiralFlameStrike());
		DuelistMod.myCards.add(new StampingDestruction());
		DuelistMod.myCards.add(new TailSwipe());
		//DuelistMod.myCards.add(new TidalWaterfall());
		//DuelistMod.myCards.add(new UltimateTyranno());
		DuelistMod.myCards.add(new VoidOgreDragon());
		DuelistMod.myCards.add(new AncientDragon());
		DuelistMod.myCards.add(new AncientPixieDragon());
		DuelistMod.myCards.add(new Anthrosaurus());
		DuelistMod.myCards.add(new AquaDolphin());
		DuelistMod.myCards.add(new ArmedDragon3());
		DuelistMod.myCards.add(new ArmedDragon5());
		DuelistMod.myCards.add(new ArmedProtectorDragon());
		DuelistMod.myCards.add(new ArtifactIgnition());
		DuelistMod.myCards.add(new Babycerasaurus());
		DuelistMod.myCards.add(new BackupSoldier());
		DuelistMod.myCards.add(new BerserkerSoul());
		DuelistMod.myCards.add(new BlueDuston());
		DuelistMod.myCards.add(new ClawHermos());
		DuelistMod.myCards.add(new CloudianGhost());
		DuelistMod.myCards.add(new CoralDragon());
		DuelistMod.myCards.add(new CyberDragonCore());
		DuelistMod.myCards.add(new CyberDragonDrei());
		DuelistMod.myCards.add(new CyberDragonNachster());
		DuelistMod.myCards.add(new CyberDragonVier());
		DuelistMod.myCards.add(new CyberEltanin());
		//DuelistMod.myCards.add(new CyberPharos());
		//DuelistMod.myCards.add(new CyberPhoenix());
		DuelistMod.myCards.add(new CyberRevsystem());
		//DuelistMod.myCards.add(new CyberloadFusion());
		DuelistMod.myCards.add(new CyberneticOverflow());
		DuelistMod.myCards.add(new CyberneticRevolution());
		DuelistMod.myCards.add(new DefenseDraw());
		DuelistMod.myCards.add(new DefenseZone());
		DuelistMod.myCards.add(new DefensiveTactics());
		DuelistMod.myCards.add(new Dracocension());
		//DuelistMod.myCards.add(new DragonShrine());
		DuelistMod.myCards.add(new DreadnoughtDreadnoid());
		DuelistMod.myCards.add(new Duoterion());
		DuelistMod.myCards.add(new EvilMind());
		DuelistMod.myCards.add(new FangCritias());
		DuelistMod.myCards.add(new Freezadon());
		DuelistMod.myCards.add(new FrostflameDragon());
		DuelistMod.myCards.add(new Frostosaurus());
		DuelistMod.myCards.add(new FrozenFitzgerald());
		DuelistMod.myCards.add(new GalaxyTyranno());
		DuelistMod.myCards.add(new GoldSarcophagus());
		DuelistMod.myCards.add(new GuardragonJusticia());
		DuelistMod.myCards.add(new HazyFlameHydra());
		DuelistMod.myCards.add(new HymnOfLight());
		DuelistMod.myCards.add(new MegafleetDragon());
		DuelistMod.myCards.add(new MoltenDestruction());
		DuelistMod.myCards.add(new OneDayPeace());
		DuelistMod.myCards.add(new RedDragonArchfiend());
		DuelistMod.myCards.add(new SafeZone());
		DuelistMod.myCards.add(new SauropodBrachion());
		DuelistMod.myCards.add(new ScrapIronScarecrow());
		DuelistMod.myCards.add(new SoulCharge());
		DuelistMod.myCards.add(new SphereChaos());
		DuelistMod.myCards.add(new StarlightRoad());
		DuelistMod.myCards.add(new SuperStridentBlaze());
		DuelistMod.myCards.add(new SurvivalFittest());
		DuelistMod.myCards.add(new ThreateningRoar());
		DuelistMod.myCards.add(new UltraEvolutionPill());
		DuelistMod.myCards.add(new VeilDarkness());
		DuelistMod.myCards.add(new VolcanicEruption());
		DuelistMod.myCards.add(new WaterDragonCluster());
		DuelistMod.myCards.add(new ArtifactSanctum());
		DuelistMod.myCards.add(new AutorokketDragon());
		DuelistMod.myCards.add(new BerserkScales());
		DuelistMod.myCards.add(new Berserking());
		DuelistMod.myCards.add(new CastleDragonSouls());
		//DuelistMod.myCards.add(new CyberDragonHerz());
		DuelistMod.myCards.add(new CyberDragonSieger());
		DuelistMod.myCards.add(new CyberEmergency());
		DuelistMod.myCards.add(new CyberLaserDragon());
		//DuelistMod.myCards.add(new CyberRepairPlant());
		DuelistMod.myCards.add(new DragonMastery());
		DuelistMod.myCards.add(new DragonMirror());
		DuelistMod.myCards.add(new DragonRavine());
		DuelistMod.myCards.add(new GalaxySoldier());
		//DuelistMod.myCards.add(new IronChainDragon());
		DuelistMod.myCards.add(new JurassicImpact());
		//DuelistMod.myCards.add(new LivingFossil());
		DuelistMod.myCards.add(new LostWorld());
		//DuelistMod.myCards.add(new MetalReflectSlime());
		//DuelistMod.myCards.add(new PowerBond());
		//DuelistMod.myCards.add(new RisingEnergy());
		DuelistMod.myCards.add(new SilverWing());
		DuelistMod.myCards.add(new SystemDown());
		DuelistMod.myCards.add(new Vandalgyon());
		DuelistMod.myCards.add(new BreakDraw());
		DuelistMod.myCards.add(new ElectromagneticTurtle());
		DuelistMod.myCards.add(new Geargiauger());
		DuelistMod.myCards.add(new GoldGadget());
		DuelistMod.myCards.add(new PlatinumGadget());
		DuelistMod.myCards.add(new PsychicShockwave());
		DuelistMod.myCards.add(new JinzoLord());
		DuelistMod.myCards.add(new JadeKnight());
		DuelistMod.myCards.add(new HeavyMetalRaiders());
		DuelistMod.myCards.add(new HeavyMechSupportArmor());
		DuelistMod.myCards.add(new HeavyMechSupportPlatform());
		DuelistMod.myCards.add(new AllySalvo());
		DuelistMod.myCards.add(new DoubleTool());
		DuelistMod.myCards.add(new FrontlineObserver());
		DuelistMod.myCards.add(new HeavyFreightTrainDerricane());
		DuelistMod.myCards.add(new CardsFromTheSky());
		DuelistMod.myCards.add(new SevenCompleted());
		DuelistMod.myCards.add(new AirCrackingStorm());
		DuelistMod.myCards.add(new Submarineroid());
		DuelistMod.myCards.add(new GearGigant());
		DuelistMod.myCards.add(new SolarWindJammer());
		DuelistMod.myCards.add(new AbyssDweller());
		DuelistMod.myCards.add(new Deskbot003());
		DuelistMod.myCards.add(new Deskbot006());
		DuelistMod.myCards.add(new Deskbot007());
		DuelistMod.myCards.add(new Deskbot008());
		DuelistMod.myCards.add(new GenexAllyBirdman());
		DuelistMod.myCards.add(new GenexNeutron());
		DuelistMod.myCards.add(new MachineKingPrototype());
		DuelistMod.myCards.add(new PerfectMachineKing());
		DuelistMod.myCards.add(new Tuningware());
		DuelistMod.myCards.add(new MessengerPeace());
		DuelistMod.myCards.add(new WonderGarage());
		DuelistMod.myCards.add(new RoboticKnight());
		DuelistMod.myCards.add(new Factory100Machines());
		DuelistMod.myCards.add(new Geartown());
		DuelistMod.myCards.add(new UnionHangar());
		DuelistMod.myCards.add(new AncientGearWorkshop());
		DuelistMod.myCards.add(new RevolvingSwitchyard());
		DuelistMod.myCards.add(new ScrapBeast());
		DuelistMod.myCards.add(new AncientGearReactorDragon());
		DuelistMod.myCards.add(new MetalholdMovingBlockade());
		DuelistMod.myCards.add(new MachinaCannon());
		DuelistMod.myCards.add(new TimeSeal());
		DuelistMod.myCards.add(new OrcustCrescendo());
		DuelistMod.myCards.add(new ParallelPortArmor());
		DuelistMod.myCards.add(new PineappleBlast());
		DuelistMod.myCards.add(new BlackSalvo());
		DuelistMod.myCards.add(new Flashbang());
		DuelistMod.myCards.add(new BlindDestruction());
		DuelistMod.myCards.add(new NightmareWheel());
		DuelistMod.myCards.add(new PortableBatteryPack());
		DuelistMod.myCards.add(new QuillboltHedgehog());
		DuelistMod.myCards.add(new MetalDetector());
		DuelistMod.myCards.add(new SolemnStrike());
		DuelistMod.myCards.add(new SolemnWarning());
		DuelistMod.myCards.add(new QuickCharger());
		DuelistMod.myCards.add(new ZONE());
		DuelistMod.myCards.add(new Apoqliphort());
		DuelistMod.myCards.add(new Shekhinaga());
		DuelistMod.myCards.add(new Quariongandrax());
		DuelistMod.myCards.add(new JunkSpeeder());
		DuelistMod.myCards.add(new AbyssDweller());
		DuelistMod.myCards.add(new AbyssSoldier());
		DuelistMod.myCards.add(new AbyssalKingshark());
		DuelistMod.myCards.add(new AegisOceanDragon());
		DuelistMod.myCards.add(new Akihiron());
		DuelistMod.myCards.add(new Ameba());
		DuelistMod.myCards.add(new AquaSnake());
		DuelistMod.myCards.add(new AquaactressArowana());
		DuelistMod.myCards.add(new AquaactressGuppy());
		DuelistMod.myCards.add(new AquaactressTetra());
		DuelistMod.myCards.add(new AquamirrorCycle());
		//DuelistMod.myCards.add(new AquariumLighting());
		DuelistMod.myCards.add(new ArmoredStarfish());
		DuelistMod.myCards.add(new AtlanteanHeavyInfantry());
		DuelistMod.myCards.add(new BarrierStatue());
		DuelistMod.myCards.add(new BoulderTortoise());
		DuelistMod.myCards.add(new BubbleBringer());
		DuelistMod.myCards.add(new ChrysalisDolphin());
		DuelistMod.myCards.add(new CitadelWhale());
		DuelistMod.myCards.add(new ColdEnchanter());
		DuelistMod.myCards.add(new ColdFeet());
		DuelistMod.myCards.add(new ColdWave());
		DuelistMod.myCards.add(new CraniumFish());
		DuelistMod.myCards.add(new Cryomancer());
		DuelistMod.myCards.add(new CyberShark());
		DuelistMod.myCards.add(new DeepSweeper());
		DuelistMod.myCards.add(new DeepseaShark());
		DuelistMod.myCards.add(new DepthShark());
		DuelistMod.myCards.add(new Dewdark());
		DuelistMod.myCards.add(new DewlorenTigerKing());
		DuelistMod.myCards.add(new Dorover());
		DuelistMod.myCards.add(new DupeFrog());
		DuelistMod.myCards.add(new ElementalBurst());
		DuelistMod.myCards.add(new EnchantingMermaid());
		DuelistMod.myCards.add(new EvigishkiGustkraken());
		DuelistMod.myCards.add(new EvigishkiLevianima());
		DuelistMod.myCards.add(new EvigishkiMerrowgeist());
		DuelistMod.myCards.add(new EvigishkiMindAugus());
		DuelistMod.myCards.add(new EvigishkiSoulOgre());
		DuelistMod.myCards.add(new EvigishkiTetrogre());
		DuelistMod.myCards.add(new FishDepthCharge());
		DuelistMod.myCards.add(new FishborgArcher());
		DuelistMod.myCards.add(new FishborgBlaster());
		DuelistMod.myCards.add(new FishborgDoctor());
		DuelistMod.myCards.add(new FishborgLauncher());
		DuelistMod.myCards.add(new FishborgPlanter());
		//DuelistMod.myCards.add(new ForgottenCity());
		DuelistMod.myCards.add(new FreezingBeast());
		DuelistMod.myCards.add(new GamecieltheSeaTurtleKaiju());
		DuelistMod.myCards.add(new GeneralGantal());
		DuelistMod.myCards.add(new GiantPairfish());
		DuelistMod.myCards.add(new GiantRedSeasnake());
		DuelistMod.myCards.add(new GiantTurtle());
		DuelistMod.myCards.add(new GishkiAquamirror());
		DuelistMod.myCards.add(new GishkiAriel());
		DuelistMod.myCards.add(new GishkiAvance());
		DuelistMod.myCards.add(new GishkiBeast());
		DuelistMod.myCards.add(new GishkiEmilia());
		DuelistMod.myCards.add(new GishkiMarker());
		DuelistMod.myCards.add(new GishkiMollusk());
		DuelistMod.myCards.add(new GishkiNoellia());
		DuelistMod.myCards.add(new GishkiPsychelone());
		DuelistMod.myCards.add(new GishkiReliever());
		DuelistMod.myCards.add(new GishkiShadow());
		DuelistMod.myCards.add(new GishkiZielgigas());
		DuelistMod.myCards.add(new GoraTurtle());
		DuelistMod.myCards.add(new GraydleAlligator());
		DuelistMod.myCards.add(new GraydleCobra());
		DuelistMod.myCards.add(new GraydleCombat());
		DuelistMod.myCards.add(new GraydleDragon());
		DuelistMod.myCards.add(new GraydleEagle());
		DuelistMod.myCards.add(new GraydleImpact());
		DuelistMod.myCards.add(new GraydleParasite());
		DuelistMod.myCards.add(new GraydleSlime());
		DuelistMod.myCards.add(new HighTideGyojin());
		DuelistMod.myCards.add(new HumanoidSlime());
		DuelistMod.myCards.add(new Hyosube());
		DuelistMod.myCards.add(new IceHand());
		DuelistMod.myCards.add(new ImperialCustom());
		DuelistMod.myCards.add(new KaiserSeaSnake());
		DuelistMod.myCards.add(new KoakiMeiruIce());
		//DuelistMod.myCards.add(new LadyOfTheLake());
		DuelistMod.myCards.add(new LeviairSeaDragon());
		DuelistMod.myCards.add(new LiquidBeast());
		DuelistMod.myCards.add(new MadLobster());
		DuelistMod.myCards.add(new MermaidKnight());
		DuelistMod.myCards.add(new MermailAbyssalacia());
		DuelistMod.myCards.add(new MorayGreed());
		DuelistMod.myCards.add(new NightmarePenguin());
		DuelistMod.myCards.add(new NimbleAngler());
		DuelistMod.myCards.add(new NimbleManta());
		DuelistMod.myCards.add(new NimbleSunfish());
		DuelistMod.myCards.add(new OldWhiteTurtle());
		DuelistMod.myCards.add(new Salvage());
		DuelistMod.myCards.add(new SeaLordAmulet());
		DuelistMod.myCards.add(new Skystarray());
		DuelistMod.myCards.add(new StarBoy());
		DuelistMod.myCards.add(new TerrorkingSalmon());
		DuelistMod.myCards.add(new ThunderSeaHorse());
		DuelistMod.myCards.add(new TorrentialReborn());
		DuelistMod.myCards.add(new TorrentialTribute());
		DuelistMod.myCards.add(new Unifrog());
		DuelistMod.myCards.add(new WaterSpirit());
		DuelistMod.myCards.add(new FuriousSeaKing());
		DuelistMod.myCards.add(new LegendaryOcean());
		DuelistMod.myCards.add(new SplashCapture());
		//DuelistMod.myCards.add(new Umiiruka());
		DuelistMod.myCards.add(new Wetlands());
		DuelistMod.myCards.add(new WhiteAuraWhale());
		//DuelistMod.myCards.add(new FieldBarrier());
		DuelistMod.myCards.add(new HyperancientShark());
		DuelistMod.myCards.add(new KaiserSeaHorse());
		DuelistMod.myCards.add(new TripodFish());
		DuelistMod.myCards.add(new UnshavenAngler());
		DuelistMod.myCards.add(new WaterHazard());
		DuelistMod.myCards.add(new EagleShark());
		DuelistMod.myCards.add(new BookLife());
		DuelistMod.myCards.add(new CrimsonKnightVampire());
		DuelistMod.myCards.add(new VampireBaby());
		DuelistMod.myCards.add(new VampireDesire());
		DuelistMod.myCards.add(new VampireDomain());
		DuelistMod.myCards.add(new VampireDomination());
		DuelistMod.myCards.add(new VampireDragon());
		DuelistMod.myCards.add(new VampireDuke());
		DuelistMod.myCards.add(new VampireFamiliar());
		DuelistMod.myCards.add(new VampireGrimson());
		DuelistMod.myCards.add(new VampireHunter());
		DuelistMod.myCards.add(new VampireKingdom());
		DuelistMod.myCards.add(new VampireLady());
		DuelistMod.myCards.add(new VampireRedBaron());
		DuelistMod.myCards.add(new VampireRetainer());
		DuelistMod.myCards.add(new VampireScarletScourge());
		DuelistMod.myCards.add(new VampireSorcerer());
		DuelistMod.myCards.add(new VampireSucker());
		DuelistMod.myCards.add(new VampireTakeover());
		DuelistMod.myCards.add(new VampireVamp());
		DuelistMod.myCards.add(new SuperPolymerization());
		DuelistMod.myCards.add(new VampireAwakening());
		DuelistMod.myCards.add(new AbsorbFusion());
		DuelistMod.myCards.add(new AntiFusionDevice());
		DuelistMod.myCards.add(new AshBlossom());
		DuelistMod.myCards.add(new BattleFusion());
		DuelistMod.myCards.add(new BloodSucker());
		DuelistMod.myCards.add(new BoneMouse());
		DuelistMod.myCards.add(new Bonecrusher());
		DuelistMod.myCards.add(new BookEclipse());
		DuelistMod.myCards.add(new BookMoon());
		DuelistMod.myCards.add(new BookTaiyou());
		DuelistMod.myCards.add(new BrilliantFusion());
		DuelistMod.myCards.add(new BurningSkullHead());
		DuelistMod.myCards.add(new CallHaunted());
		DuelistMod.myCards.add(new CalledByGrave());
		DuelistMod.myCards.add(new Chewbone());
		DuelistMod.myCards.add(new Chopman());
		DuelistMod.myCards.add(new Dakki());
		DuelistMod.myCards.add(new DarkDustSpirit());
		DuelistMod.myCards.add(new DecayedCommander());
		DuelistMod.myCards.add(new DimensionBurial());
		DuelistMod.myCards.add(new DimensionFusion());
		DuelistMod.myCards.add(new DoomkingBalerdroch());
		DuelistMod.myCards.add(new EnmaJudgement());
		DuelistMod.myCards.add(new FinalFusion());
		DuelistMod.myCards.add(new FireReaper());
		DuelistMod.myCards.add(new FlashFusion());
		DuelistMod.myCards.add(new FossilSkullConvoy());
		DuelistMod.myCards.add(new FossilSkullKing());
		DuelistMod.myCards.add(new FossilSkullbuggy());
		DuelistMod.myCards.add(new FossilSkullknight());
		DuelistMod.myCards.add(new FragmentFusion());
		DuelistMod.myCards.add(new FusionDevourer());
		DuelistMod.myCards.add(new FusionGuard());
		DuelistMod.myCards.add(new FusionTag());
		DuelistMod.myCards.add(new FusionWeapon());
		DuelistMod.myCards.add(new FusionFire());
		DuelistMod.myCards.add(new Gashadokuro());
		DuelistMod.myCards.add(new GhostBelle());
		DuelistMod.myCards.add(new GhostOgre());
		DuelistMod.myCards.add(new GhostReaper());
		DuelistMod.myCards.add(new GhostSister());
		DuelistMod.myCards.add(new GhostrickAlucard());
		DuelistMod.myCards.add(new GhostrickAngel());
		DuelistMod.myCards.add(new GhostrickBreak());
		DuelistMod.myCards.add(new GhostrickDoll());
		DuelistMod.myCards.add(new GhostrickDullahan());
		DuelistMod.myCards.add(new GhostrickFairy());
		DuelistMod.myCards.add(new GhostrickGhoul());
		DuelistMod.myCards.add(new GhostrickJackfrost());
		DuelistMod.myCards.add(new GhostrickGoRound());
		DuelistMod.myCards.add(new GhostrickJiangshi());
		DuelistMod.myCards.add(new GhostrickLantern());
		DuelistMod.myCards.add(new GhostrickMansion());
		DuelistMod.myCards.add(new GhostrickMary());
		DuelistMod.myCards.add(new GhostrickMummy());
		DuelistMod.myCards.add(new GhostrickMuseum());
		DuelistMod.myCards.add(new GhostrickNight());
		DuelistMod.myCards.add(new GhostrickParade());
		DuelistMod.myCards.add(new GhostrickRenovation());
		DuelistMod.myCards.add(new GhostrickScare());
		DuelistMod.myCards.add(new GhostrickSkeleton());
		DuelistMod.myCards.add(new GhostrickStein());
		DuelistMod.myCards.add(new GhostrickVanish());
		DuelistMod.myCards.add(new GhostrickWarwolf());
		DuelistMod.myCards.add(new GhostrickYeti());
		DuelistMod.myCards.add(new GhostrickNekomusume());
		DuelistMod.myCards.add(new GhostrickSocuteboss());
		DuelistMod.myCards.add(new GhostrickSpecter());
		DuelistMod.myCards.add(new GhostrickWitch());
		DuelistMod.myCards.add(new GhostrickYukiOnna());
		DuelistMod.myCards.add(new GigastoneOmega());
		DuelistMod.myCards.add(new Hajun());
		DuelistMod.myCards.add(new ImmortalRuler());
		DuelistMod.myCards.add(new InstantFusion());
		DuelistMod.myCards.add(new LichLord());
		DuelistMod.myCards.add(new MagicalGhost());
		DuelistMod.myCards.add(new MagicalizedFusion());
		DuelistMod.myCards.add(new MammothGraveyard());
		DuelistMod.myCards.add(new MayakashiReturn());
		DuelistMod.myCards.add(new MayakashiWinter());
		DuelistMod.myCards.add(new MechMoleZombie());
		DuelistMod.myCards.add(new Mezuki());
		DuelistMod.myCards.add(new MiracleFusion());
		DuelistMod.myCards.add(new Miscellaneousaurus());
		DuelistMod.myCards.add(new Mispolymerization());
		DuelistMod.myCards.add(new NecroFusion());
		DuelistMod.myCards.add(new Necroface());
		DuelistMod.myCards.add(new NecroworldBanshee());
		DuelistMod.myCards.add(new NightmareHorse());
		DuelistMod.myCards.add(new OboroGuruma());
		DuelistMod.myCards.add(new OverpoweringEye());
		DuelistMod.myCards.add(new OvertexQoatlus());
		DuelistMod.myCards.add(new PMCaptor());
		DuelistMod.myCards.add(new ParallelWorldFusion());
		DuelistMod.myCards.add(new PhantomGhost());
		DuelistMod.myCards.add(new PrematureBurial());
		DuelistMod.myCards.add(new PyramidTurtle());
		DuelistMod.myCards.add(new RelinquishedFusion());
		DuelistMod.myCards.add(new RobbinZombie());
		DuelistMod.myCards.add(new ShiftingShadows());
		DuelistMod.myCards.add(new SkullFlame());
		DuelistMod.myCards.add(new SkullServant());
		DuelistMod.myCards.add(new SoulRelease());
		DuelistMod.myCards.add(new SouleatingOviraptor());
		DuelistMod.myCards.add(new SpiritReaper());
		DuelistMod.myCards.add(new SupersonicSkullFlame());
		DuelistMod.myCards.add(new SynchroFusionist());
		DuelistMod.myCards.add(new Tengu());
		DuelistMod.myCards.add(new TimeFusion());
		DuelistMod.myCards.add(new TriWight());
		DuelistMod.myCards.add(new Tsukahagi());
		DuelistMod.myCards.add(new TutanMask());
		DuelistMod.myCards.add(new TyrantDinoFusion());
		DuelistMod.myCards.add(new UniZombie());
		DuelistMod.myCards.add(new VisionFusion());
		DuelistMod.myCards.add(new Wasteland());
		DuelistMod.myCards.add(new WightLady());
		DuelistMod.myCards.add(new Wightmare());
		DuelistMod.myCards.add(new Wightprince());
		DuelistMod.myCards.add(new Wightprincess());
		DuelistMod.myCards.add(new Yasha());
		DuelistMod.myCards.add(new Yoko());
		DuelistMod.myCards.add(new YukiMusume());
		DuelistMod.myCards.add(new YukiOnnaAbsolute());
		DuelistMod.myCards.add(new YukiOnnaIce());
		DuelistMod.myCards.add(new ZombieNecronize());
		DuelistMod.myCards.add(new ZombiePowerStruggle());
		DuelistMod.myCards.add(new ZombieTiger());
		DuelistMod.myCards.add(new GiantAxeMummy());
		DuelistMod.myCards.add(new PyramidWonders());
		DuelistMod.myCards.add(new PyramidLight());
		DuelistMod.myCards.add(new FossilKnight());
		DuelistMod.myCards.add(new Scapeghost());
		DuelistMod.myCards.add(new AvendreadSavior());
		DuelistMod.myCards.add(new BaconSaver());
		DuelistMod.myCards.add(new BeastPharaoh());
		DuelistMod.myCards.add(new FossilDragon());
		DuelistMod.myCards.add(new FossilKnight());
		DuelistMod.myCards.add(new GlowUpBloom());
		DuelistMod.myCards.add(new HardSellinZombie());
		DuelistMod.myCards.add(new HumptyGrumpty());
		DuelistMod.myCards.add(new RebornZombie());
		DuelistMod.myCards.add(new ReturnToDoomed());
		DuelistMod.myCards.add(new RevendreadEvolution());
		DuelistMod.myCards.add(new RevendreadExecutor());
		DuelistMod.myCards.add(new RevendreadOrigin());
		DuelistMod.myCards.add(new RevendreadSlayer());
		DuelistMod.myCards.add(new SeaMonsterTheseus());
		DuelistMod.myCards.add(new ShiranuiSamurai());
		DuelistMod.myCards.add(new ShiranuiSamuraisaga());
		DuelistMod.myCards.add(new ShiranuiShogunsaga());
		DuelistMod.myCards.add(new ShiranuiSkillsagaSupremacy());
		DuelistMod.myCards.add(new ShiranuiSmith());
		DuelistMod.myCards.add(new ShiranuiSolitaire());
		DuelistMod.myCards.add(new ShiranuiSpectralsword());
		DuelistMod.myCards.add(new ShiranuiSpectralswordShade());
		DuelistMod.myCards.add(new ShiranuiSpiritmaster());
		DuelistMod.myCards.add(new ShiranuiSquire());
		DuelistMod.myCards.add(new ShiranuiSquiresaga());
		DuelistMod.myCards.add(new ShiranuiSunsaga());
		DuelistMod.myCards.add(new ShiranuiSwordmaster());
		DuelistMod.myCards.add(new ShiranuiSwordsaga());
		DuelistMod.myCards.add(new Skullgios());
		DuelistMod.myCards.add(new VendreadAnima());
		DuelistMod.myCards.add(new VendreadBattlelord());
		DuelistMod.myCards.add(new VendreadCharge());
		DuelistMod.myCards.add(new VendreadChimera());
		DuelistMod.myCards.add(new VendreadCore());
		DuelistMod.myCards.add(new VendreadDaybreak());
		DuelistMod.myCards.add(new VendreadHoundhorde());
		DuelistMod.myCards.add(new VendreadNightmare());
		DuelistMod.myCards.add(new VendreadNights());
		DuelistMod.myCards.add(new VendreadReorigin());
		DuelistMod.myCards.add(new VendreadReunion());
		DuelistMod.myCards.add(new VendreadRevenants());
		DuelistMod.myCards.add(new VendreadRevolution());
		DuelistMod.myCards.add(new VendreadStriges());
		DuelistMod.myCards.add(new Zombina());
		DuelistMod.myCards.add(new SkullConductor());
		DuelistMod.myCards.add(new DragonTreasure());
		DuelistMod.myCards.add(new LightningDarts());

		DuelistMod.myCards.add(new OjamaMetronome());
		DuelistMod.myCards.add(new AncientMetronome());
		DuelistMod.myCards.add(new AquaMetronome());
		DuelistMod.myCards.add(new ArcaneMetronome());
		DuelistMod.myCards.add(new ArcaneMonsterMetronome());
		DuelistMod.myCards.add(new ArcaneSpellMetronome());
		DuelistMod.myCards.add(new AttackMetronome());
		DuelistMod.myCards.add(new AttackTrapMetronome());
		DuelistMod.myCards.add(new BlockMetronome());
		DuelistMod.myCards.add(new BlockSpellMetronome());
		DuelistMod.myCards.add(new BugMetronome());
		DuelistMod.myCards.add(new CombatMetronome());
		DuelistMod.myCards.add(new CommonSpireMetronome());
		DuelistMod.myCards.add(new CyberMetronome());
		DuelistMod.myCards.add(new DeskbotMetronome());
		DuelistMod.myCards.add(new DinosaurMetronome());
		DuelistMod.myCards.add(new DragonMetronome());
		DuelistMod.myCards.add(new FieldMetronome());
		DuelistMod.myCards.add(new FiendMetronome());
		DuelistMod.myCards.add(new GemKnightMetronome());
		DuelistMod.myCards.add(new GhostrickMetronome());
		DuelistMod.myCards.add(new InsectMetronome());
		DuelistMod.myCards.add(new LabyrinthNightmareMetronome());
		DuelistMod.myCards.add(new LegendBlueEyesMetronome());
		DuelistMod.myCards.add(new MachineMetronome());
		DuelistMod.myCards.add(new MachineMonsterMetronome());
		DuelistMod.myCards.add(new MagnetMetronome());
		DuelistMod.myCards.add(new MaliciousMetronome());
		DuelistMod.myCards.add(new MayakashiMetronome());
		DuelistMod.myCards.add(new MegatypeMetronome());
		DuelistMod.myCards.add(new MetalRaidersMetronome());
		DuelistMod.myCards.add(new MillenniumMetronome());
		DuelistMod.myCards.add(new MonsterMetronome());
		DuelistMod.myCards.add(new NaturiaMetronome());
		DuelistMod.myCards.add(new OrbMetronome());
		DuelistMod.myCards.add(new OverflowMetronome());
		DuelistMod.myCards.add(new PelagicMetronome());
		DuelistMod.myCards.add(new PlantMetronome());
		DuelistMod.myCards.add(new PotMetronome());
		DuelistMod.myCards.add(new PowerMetronome());
		DuelistMod.myCards.add(new PredaplantMetronome());
		DuelistMod.myCards.add(new RareAttackMetronome());
		DuelistMod.myCards.add(new RareBlockMetronome());
		DuelistMod.myCards.add(new RareDragonMetronome());
		DuelistMod.myCards.add(new RareMetronome());
		DuelistMod.myCards.add(new RareMonsterMetronome());
		DuelistMod.myCards.add(new RareNatureMetronome());
		DuelistMod.myCards.add(new RarePowerMetronome());
		DuelistMod.myCards.add(new RareSkillMetronome());
		DuelistMod.myCards.add(new RareSpireMetronome());
		DuelistMod.myCards.add(new RecklessMetronome());
		DuelistMod.myCards.add(new RockMetronome());
		DuelistMod.myCards.add(new RoseMetronome());
		DuelistMod.myCards.add(new ShiranuiMetronome());
		DuelistMod.myCards.add(new SkillMetronome());
		DuelistMod.myCards.add(new SpellMetronome());
		DuelistMod.myCards.add(new SpellcasterMetronome());
		DuelistMod.myCards.add(new SpiderMetronome());
		DuelistMod.myCards.add(new SpireMetronome());
		DuelistMod.myCards.add(new StampedingMetronome());
		DuelistMod.myCards.add(new StartingDeckMetronome());
		DuelistMod.myCards.add(new SuperheavyMetronome());
		DuelistMod.myCards.add(new ThalassicMetronome());
		DuelistMod.myCards.add(new TidalMetronome());
		DuelistMod.myCards.add(new TokenMetronome());
		DuelistMod.myCards.add(new TrapMetronome());
		DuelistMod.myCards.add(new UncommonAttackMetronome());
		DuelistMod.myCards.add(new UncommonMetronome());
		DuelistMod.myCards.add(new UncommonMonsterMetronome());
		DuelistMod.myCards.add(new UncommonSpireMetronome());
		DuelistMod.myCards.add(new UndeadMetronome());
		DuelistMod.myCards.add(new VampireMetronome());
		DuelistMod.myCards.add(new WarriorMetronome());
		DuelistMod.myCards.add(new WyrmMetronome());
		DuelistMod.myCards.add(new ZombieMetronome());


		//DuelistMod.myCards.add(new AncientFairyDragon());
		//DuelistMod.myCards.add(new ElementSaurus());
		//DuelistMod.myCards.add(new HyperHammerhead());
		//DuelistMod.myCards.add(new Gilasaurus());
		//DuelistMod.myCards.add(new TyrannoInfinity());
		//DuelistMod.myCards.add(new SuperconductorTyranno());

		if (Util.halloweenCheck())
		{
			DuelistMod.myCards.add(new Hallohallo());
			DuelistMod.myCards.add(new PumpkinCarriage());
			DuelistMod.myCards.add(new HalloweenManor());
		}

		if (Util.birthdayCheck())
		{
			DuelistMod.myCards.add(new BalloonParty());
			DuelistMod.myCards.add(new CocoonParty());
			DuelistMod.myCards.add(new DinnerParty());
		}

		if (Util.xmasCheck())
		{
			DuelistMod.myCards.add(new ElephantGift());
			DuelistMod.myCards.add(new GiftCard());
			DuelistMod.myCards.add(new FairyGift());
			DuelistMod.myCards.add(new HeroicGift());
		}

		if (Util.weedCheck())
		{
			DuelistMod.myCards.add(new WeedOut());
		}

		// NAMELESS TOMB CARDS
		DuelistMod.myNamelessCards.add(new AllyJusticeNameless());
		DuelistMod.myNamelessCards.add(new DragonTreasureNameless());
		DuelistMod.myNamelessCards.add(new AncientGearBoxNameless());
		DuelistMod.myNamelessCards.add(new AssaultArmorNameless());
		DuelistMod.myNamelessCards.add(new AxeDespairNameless());
		DuelistMod.myNamelessCards.add(new BerserkerCrushNameless());
		DuelistMod.myNamelessCards.add(new BigWhaleNameless());
		DuelistMod.myNamelessCards.add(new DarkworldThornsNameless());
		DuelistMod.myNamelessCards.add(new ForbiddenLanceNameless());
		DuelistMod.myNamelessCards.add(new GoldenApplesNameless());
		DuelistMod.myNamelessCards.add(new GracefulCharityNameless());
		DuelistMod.myNamelessCards.add(new GravityLashNameless());
		DuelistMod.myNamelessCards.add(new GridRodNameless());
		DuelistMod.myNamelessCards.add(new HappyLoverNameless());
		DuelistMod.myNamelessCards.add(new ImperialOrderNameless());
		DuelistMod.myNamelessCards.add(new InsectQueenNameless());
		DuelistMod.myNamelessCards.add(new KamionTimelordNameless());
		DuelistMod.myNamelessCards.add(new MagnumShieldNameless());
		DuelistMod.myNamelessCards.add(new MaskedDragonNameless());
		DuelistMod.myNamelessCards.add(new ObeliskTormentorNameless());
		DuelistMod.myNamelessCards.add(new OilmanNameless());
		DuelistMod.myNamelessCards.add(new PotDualityNameless());
		DuelistMod.myNamelessCards.add(new PotGenerosityNameless());
		DuelistMod.myNamelessCards.add(new PredaplantSarraceniantNameless());
		DuelistMod.myNamelessCards.add(new SpiralSpearStrikeNameless());
		DuelistMod.myNamelessCards.add(new YamiFormNameless());
		DuelistMod.myNamelessCards.add(new AllyJusticeNamelessPower());
		DuelistMod.myNamelessCards.add(new AssaultArmorNamelessPower());
		DuelistMod.myNamelessCards.add(new BeatraptorNamelessPower());
		DuelistMod.myNamelessCards.add(new BerserkerCrushNamelessPower());
		DuelistMod.myNamelessCards.add(new ForbiddenLanceNamelessPower());
		DuelistMod.myNamelessCards.add(new KamionTimelordNamelessPower());
		DuelistMod.myNamelessCards.add(new MaskedDragonNamelessPower());
		DuelistMod.myNamelessCards.add(new SpiralSpearStrikeNamelessPower());
		DuelistMod.myNamelessCards.add(new AncientGearBoxNamelessGreed());
		DuelistMod.myNamelessCards.add(new BerserkerCrushNamelessGreed());
		DuelistMod.myNamelessCards.add(new GracefulCharityNamelessGreed());
		DuelistMod.myNamelessCards.add(new MagnumShieldNamelessGreed());
		DuelistMod.myNamelessCards.add(new AllyJusticeNamelessWar());
		DuelistMod.myNamelessCards.add(new AssaultArmorNamelessWar());
		DuelistMod.myNamelessCards.add(new BerserkerCrushNamelessWar());
		DuelistMod.myNamelessCards.add(new ForbiddenLanceNamelessWar());
		DuelistMod.myNamelessCards.add(new MaskedDragonNamelessWar());
		DuelistMod.myNamelessCards.add(new SpiralSpearStrikeNamelessWar());
		DuelistMod.myNamelessCards.add(new FortressWarriorNamelessWar());
		DuelistMod.myNamelessCards.add(new BlueEyesNamelessWar());
		DuelistMod.myNamelessCards.add(new NaturalDisasterNameless());
		// NAMELESS TOMB CARDS

		// STATUS
		DuelistMod.myStatusCards.add(new ColdBlooded());
		DuelistMod.myStatusCards.add(new Swarm());
		// STATUS

		if (DuelistMod.duelistCurses)
		{
			DuelistMod.curses.add(new GravekeeperCurse());
			DuelistMod.curses.add(new CurseAnubis());
			DuelistMod.curses.add(new CurseArmaments());
			DuelistMod.curses.add(new CursedBill());
			DuelistMod.curses.add(new CurseAging());
			DuelistMod.curses.add(new SummoningCurse());
			DuelistMod.curses.add(new VampireCurse());
			DuelistMod.curses.add(new PsiCurse());
			DuelistMod.curses.add(new CurseRoyal());
		}

		for (CardTags t : DuelistMod.monsterTypes)
		{
			String ID = DuelistMod.typeCardMap_ID.get(t);
			CardStrings localCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			DuelistMod.typeCardMap_NAME.put(t, localCardStrings.NAME);
			DuelistMod.typeCardMap_NameToString.put(localCardStrings.NAME, t);
			DuelistMod.typeCardMap_DESC.put(t, localCardStrings.DESCRIPTION);
		}

		ArrayList<CardTags> extraTags = new ArrayList<>();
		extraTags.add(Tags.ROSE);
		extraTags.add(Tags.ARCANE);
		extraTags.add(Tags.MEGATYPED);
		extraTags.add(Tags.OJAMA);
		extraTags.add(Tags.MONSTER);
		extraTags.add(Tags.SPELL);
		extraTags.add(Tags.TRAP);
		extraTags.add(Tags.GIANT);
		extraTags.add(Tags.MAGNET);

		for (CardTags t : extraTags)
		{
			String ID = DuelistMod.typeCardMap_ID.get(t);
			CardStrings localCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			DuelistMod.typeCardMap_NAME.put(t, localCardStrings.NAME);
			DuelistMod.typeCardMap_NameToString.put(localCardStrings.NAME, t);
			DuelistMod.typeCardMap_DESC.put(t, localCardStrings.DESCRIPTION);
		}

		DuelistMod.cardCount = 0;
		for (int i = 0; i < DuelistMod.myCards.size(); i++)
		{
			if (!DuelistMod.myCards.get(i).color.equals(AbstractCardEnum.DUELIST_SPECIAL))
			{
				DuelistMod.cardCount++;
			}
		}

		// Add tokens to 'The Duelist' section of compendium
		if (!DuelistMod.addTokens) {
			DuelistMod.myCards.addAll(getAllDuelistTokens());
		}

		if (DuelistMod.addTokens)
		{
			DuelistMod.myCards.addAll(getAllDuelistTokens());
			DuelistMod.myCards.add(new BadToken());
			DuelistMod.myCards.add(new GreatMoth());
			DuelistMod.myCards.add(new HeartUnderspell());
			DuelistMod.myCards.add(new HeartUndertrap());
			DuelistMod.myCards.add(new HeartUndertribute());
			DuelistMod.myCards.addAll(DuelistMod.orbCards);
		}
		// END DEBUG CARD STUFF
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (!c.color.equals(AbstractCardEnum.DUELIST_SPECIAL))
			{
				if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.RARE) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON_POOL) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
				{
					DuelistMod.rareCards.add((DuelistCard) c.makeStatEquivalentCopy());
					if (!c.type.equals(CardType.POWER)) { DuelistMod.rareNonPowers.add((DuelistCard) c.makeStatEquivalentCopy()); }
				}

				else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.UNCOMMON) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON_POOL) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
				{
					DuelistMod.uncommonCards.add((DuelistCard) c.makeStatEquivalentCopy());
					DuelistMod.nonRareCards.add((DuelistCard) c.makeStatEquivalentCopy());
				}

				else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.COMMON) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON_POOL) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
				{
					DuelistMod.commonCards.add((DuelistCard) c.makeStatEquivalentCopy());
					DuelistMod.nonRareCards.add((DuelistCard) c.makeStatEquivalentCopy());
				}

				if (c.type.equals(CardType.POWER))
				{
					DuelistMod.allPowers.add((DuelistCard)c.makeStatEquivalentCopy());
					if (!c.color.equals(AbstractCardEnum.DUELIST_SPECIAL) && !c.rarity.equals(CardRarity.SPECIAL) && !c.hasTag(Tags.NO_MERCHANT_PENDANT) && !c.hasTag(Tags.NEVER_GENERATE))
					{
						DuelistMod.merchantPendantPowers.add((DuelistCard)c.makeStatEquivalentCopy());
					}
				}
				else if (!c.color.equals(AbstractCardEnum.DUELIST_SPECIAL) && !c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON_POOL) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
				{
					DuelistMod.nonPowers.add((DuelistCard)c.makeStatEquivalentCopy());
				}

				if (c.hasTag(Tags.METRONOME))
				{
					DuelistMod.metronomes.add(c.makeStatEquivalentCopy());
				}
			}
		}

		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_CARDS, DuelistMod.cardCount);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<AbstractCard> orbCardsForGeneration()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		toReturn.add(new CrystalOrbCard());
		toReturn.add(new HellfireOrbCard());
		toReturn.add(new AirOrbCard());
		toReturn.add(new DarkOrbCard());
		toReturn.add(new DragonOrbCard());
		toReturn.add(new EarthOrbCard());
		toReturn.add(new FireOrbCard());
		toReturn.add(new FrostOrbCard());
		toReturn.add(new GlitchOrbCard());
		toReturn.add(new LightningOrbCard());
		toReturn.add(new MonsterOrbCard());
		toReturn.add(new PlasmaOrbCard());
		toReturn.add(new ReducerOrbCard());
		toReturn.add(new ShadowOrbCard());
		toReturn.add(new SplashOrbCard());
		toReturn.add(new SummonerOrbCard());
		toReturn.add(new BlackOrbCard());
		toReturn.add(new GadgetOrbCard());
		toReturn.add(new LavaOrbCard());
		toReturn.add(new MetalOrbCard());
		toReturn.add(new MistOrbCard());
		toReturn.add(new MudOrbCard());
		toReturn.add(new SandOrbCard());
		toReturn.add(new SmokeOrbCard());
		toReturn.add(new StormOrbCard());
		toReturn.add(new WaterOrbCard());
		toReturn.add(new TokenOrbCard());
		toReturn.add(new VoidOrbCard());
		toReturn.add(new WhiteOrbCard());
		toReturn.add(new SurgeOrbCard());
		toReturn.add(new AlienOrbCard());
		//toReturn.add(new BloodOrbCard()); 
		toReturn.add(new MoonOrbCard());
		toReturn.add(new SunOrbCard());
		return toReturn;
	}

	public static void setupOrbCards()
	{

		DuelistMod.orbCards.add(new CrystalOrbCard());
		DuelistMod.orbCards.add(new GlassOrbCard());
		DuelistMod.orbCards.add(new HellfireOrbCard());
		DuelistMod.orbCards.add(new LightOrbCard());
		DuelistMod.orbCards.add(new AirOrbCard());
		DuelistMod.orbCards.add(new BufferOrbCard());
		DuelistMod.orbCards.add(new DarkOrbCard());
		DuelistMod.orbCards.add(new DragonOrbCard());
		DuelistMod.orbCards.add(new EarthOrbCard());
		DuelistMod.orbCards.add(new FireOrbCard());
		DuelistMod.orbCards.add(new FrostOrbCard());
		DuelistMod.orbCards.add(new GateOrbCard());
		DuelistMod.orbCards.add(new GlitchOrbCard());
		DuelistMod.orbCards.add(new LightningOrbCard());
		DuelistMod.orbCards.add(new MonsterOrbCard());
		DuelistMod.orbCards.add(new PlasmaOrbCard());
		DuelistMod.orbCards.add(new ReducerOrbCard());
		DuelistMod.orbCards.add(new ShadowOrbCard());
		DuelistMod.orbCards.add(new SplashOrbCard());
		DuelistMod.orbCards.add(new SummonerOrbCard());
		DuelistMod.orbCards.add(new BlackOrbCard());
		DuelistMod.orbCards.add(new BlazeOrbCard());
		DuelistMod.orbCards.add(new ConsumerOrbCard());
		DuelistMod.orbCards.add(new GadgetOrbCard());
		DuelistMod.orbCards.add(new LavaOrbCard());
		DuelistMod.orbCards.add(new MetalOrbCard());
		DuelistMod.orbCards.add(new MillenniumOrbCard());
		DuelistMod.orbCards.add(new MistOrbCard());
		DuelistMod.orbCards.add(new MudOrbCard());
		DuelistMod.orbCards.add(new SandOrbCard());
		DuelistMod.orbCards.add(new SmokeOrbCard());
		DuelistMod.orbCards.add(new StormOrbCard());
		DuelistMod.orbCards.add(new WaterOrbCard());
		DuelistMod.orbCards.add(new TokenOrbCard());
		DuelistMod.orbCards.add(new VoidOrbCard());
		DuelistMod.orbCards.add(new WhiteOrbCard());
		DuelistMod.orbCards.add(new SurgeOrbCard());
		DuelistMod.orbCards.add(new AlienOrbCard());
		//DuelistMod.orbCards.add(new BloodOrbCard()); 
		DuelistMod.orbCards.add(new MoonOrbCard());
		DuelistMod.orbCards.add(new SunOrbCard());
		DuelistMod.orbCards.add(new LightMillenniumOrbCard());
		DuelistMod.orbCards.add(new DarkMillenniumOrbCard());
		DuelistMod.orbCards.add(new AnticrystalOrbCard());

		for (DuelistCard o : DuelistMod.orbCards)
		{
			DuelistMod.orbCardMap.put(o.name, o);
			DuelistMod.invertableOrbNames.add(o.name);
		}
	}

	public static void fillSummonMap(ArrayList<DuelistCard> cards)
	{
		for (DuelistCard c : cards)
		{
			DuelistMod.summonMap.put(c.originalName, c);
		}

		DuelistMod.summonMap.put("Great Moth", new GreatMoth());
		DuelistMod.summonMap.put("Summoner Token", new Token());
		DuelistMod.summonMap.put("Buffer Token", new Token());
		DuelistMod.summonMap.put("Tribute Token", new Token());
		DuelistMod.summonMap.put("Summon Token", new Token());
		DuelistMod.summonMap.put("Nature Token", new NatureToken());
		DuelistMod.summonMap.put("Warrior Token", new WarriorToken());
		DuelistMod.summonMap.put("Stance Token", new StanceToken());
		DuelistMod.summonMap.put("Forsaken Token", new ForsakenToken());
		DuelistMod.summonMap.put("Token", new Token());
		DuelistMod.summonMap.put("Jam Token", new JamToken());
		DuelistMod.summonMap.put("Castle Token", new CastleToken());
		DuelistMod.summonMap.put("Storm Token", new StormToken());
		DuelistMod.summonMap.put("Puzzle Token", new PuzzleToken());
		DuelistMod.summonMap.put("Relic Token", new RelicToken());
		DuelistMod.summonMap.put("Bonanza Token", new BonanzaToken());
		DuelistMod.summonMap.put("Spellcaster Token", new SpellcasterToken());
		DuelistMod.summonMap.put("Predaplant Token", new PredaplantToken());
		DuelistMod.summonMap.put("Kuriboh Token", new KuribohToken());
		DuelistMod.summonMap.put("Exploding Token", new ExplosiveToken());
		DuelistMod.summonMap.put("Explosive Token", new ExplosiveToken());
		DuelistMod.summonMap.put("Shadow Token", new ShadowToken());
		DuelistMod.summonMap.put("Insect Token", new InsectToken());
		DuelistMod.summonMap.put("Plant Token", new PlantToken());
		DuelistMod.summonMap.put("Dragon Token", new DragonToken());
		DuelistMod.summonMap.put("Dragonic Token", new DragonicToken());
		DuelistMod.summonMap.put("Fiend Token", new FiendToken());
		DuelistMod.summonMap.put("Machine Token", new MachineToken());
		DuelistMod.summonMap.put("Superheavy Token", new SuperheavyToken());
		DuelistMod.summonMap.put("Toon Token", new ToonToken());
		DuelistMod.summonMap.put("Zombie Token", new ZombieToken());
		DuelistMod.summonMap.put("Aqua Token", new AquaToken());
		DuelistMod.summonMap.put("Exodia Token", new ExodiaToken());
		DuelistMod.summonMap.put("Damage Token", new DamageToken());
		DuelistMod.summonMap.put("Gold Token", new GoldToken());
		DuelistMod.summonMap.put("Orb Token", new OrbToken());
		DuelistMod.summonMap.put("Underdog Token", new UnderdogToken());
		DuelistMod.summonMap.put("Magnet Token", new MagnetToken());
		DuelistMod.summonMap.put("Cocoon Token", new CocoonToken());
		DuelistMod.summonMap.put("Potion Token", new PotionToken());
		DuelistMod.summonMap.put("Pot Token", new PotionToken());
		DuelistMod.summonMap.put("Glitch Token", new GlitchToken());
		DuelistMod.summonMap.put("Anubis Token", new AnubisToken());
		DuelistMod.summonMap.put("Blood Token", new BloodToken());
		DuelistMod.summonMap.put("Hane Token", new HaneToken());
		DuelistMod.summonMap.put("Stim Token", new StimToken());
		DuelistMod.summonMap.put("Megatype Token", new MegatypeToken());
		DuelistMod.summonMap.put("S. Exploding Token", new SuperExplodingToken());
		DuelistMod.summonMap.put("Naturia Token", new NaturiaToken());
		DuelistMod.summonMap.put("Rock Token", new RockToken());
		DuelistMod.summonMap.put("Blast Token", new BlastToken());
		DuelistMod.summonMap.put("Time Token", new TimeToken());
		DuelistMod.summonMap.put("Entrench Token", new EntrenchToken());
		DuelistMod.summonMap.put("Flux Token", new FluxToken());
		DuelistMod.summonMap.put("Megaglitch Token", new MegaGlitchToken());
		DuelistMod.summonMap.put("Burn Token", new BurnToken());
		DuelistMod.summonMap.put("Robot Token", new RobotToken());
		DuelistMod.summonMap.put("Grease Token", new GreaseToken());
		DuelistMod.summonMap.put("Freeze Token", new FreezeToken());
		DuelistMod.summonMap.put("Armored Token", new ArmoredToken());
		DuelistMod.summonMap.put("Metallic Token", new MetallicToken());
		DuelistMod.summonMap.put("Ancient Token", new AncientToken());
		DuelistMod.summonMap.put("Focus Token", new FocusToken());
		DuelistMod.summonMap.put("Trap Token", new TrapToken());
		DuelistMod.summonMap.put("Electric Token", new ElectricToken());
		DuelistMod.summonMap.put("Bomb Casing", new BombCasing());
		DuelistMod.summonMap.put("Spirit Token", new SpiritToken());
		DuelistMod.summonMap.put("Ghostrick Token", new GhostrickToken());
		DuelistMod.summonMap.put("Mayakashi Token", new MayakashiToken());
		DuelistMod.summonMap.put("Vendread Token", new VendreadToken());
		DuelistMod.summonMap.put("Vampire Token", new VampireToken());
		DuelistMod.summonMap.put("Shiranui Token", new ShiranuiToken());
		DuelistMod.summonMap.put("Entomb Token", new EntombToken());
		DuelistMod.summonMap.put("Mutate Token", new MutateToken());
		DuelistMod.summonMap.put("Undead Token", new UndeadToken());
	}

	// MUST INSERT NEW TOKENS AT END OF LIST - due to puzzle configurations using the position to display in menu dropdowns
	public static ArrayList<DuelistCard> getAllDuelistTokens()
	{
		ArrayList<DuelistCard> tokens = new ArrayList<DuelistCard>();
		tokens.add(new AquaToken());
		tokens.add(new DragonToken());
		tokens.add(new ExodiaToken());
		tokens.add(new SpellcasterToken());
		tokens.add(new PredaplantToken());
		tokens.add(new KuribohToken());
		tokens.add(new ExplosiveToken());
		tokens.add(new ShadowToken());
		tokens.add(new InsectToken());
		tokens.add(new PlantToken());
		tokens.add(new FiendToken());
		tokens.add(new MachineToken());
		tokens.add(new SuperheavyToken());
		tokens.add(new ToonToken());
		tokens.add(new ZombieToken());
		tokens.add(new JamToken());
		tokens.add(new Token());
		tokens.add(new DamageToken());
		tokens.add(new CastleToken());
		tokens.add(new StormToken());
		tokens.add(new RelicToken());
		tokens.add(new PuzzleToken());
		tokens.add(new BonanzaToken());
		tokens.add(new OrbToken());
		tokens.add(new UnderdogToken());
		tokens.add(new GoldToken());
		tokens.add(new MagnetToken());
		tokens.add(new CocoonToken());
		tokens.add(new PotionToken());
		tokens.add(new GlitchToken());
		tokens.add(new AnubisToken());
		tokens.add(new BloodToken());
		tokens.add(new HaneToken());
		tokens.add(new StimToken());
		tokens.add(new PlagueToken());
		tokens.add(new SummonToken());
		tokens.add(new TributeToken());
		tokens.add(new SuperExplodingToken());
		tokens.add(new MegatypeToken());
		tokens.add(new ForsakenToken());
		tokens.add(new WarriorToken());
		tokens.add(new StanceToken());
		tokens.add(new NatureToken());
		tokens.add(new NaturiaToken());
		tokens.add(new RockToken());
		tokens.add(new DragonicToken());
		tokens.add(new BlastToken());
		tokens.add(new TimeToken());
		tokens.add(new EntrenchToken());
		tokens.add(new FluxToken());
		tokens.add(new MegaGlitchToken());
		tokens.add(new FreezeToken());
		tokens.add(new BurnToken());
		tokens.add(new RobotToken());
		tokens.add(new GreaseToken());
		tokens.add(new ArmoredToken());
		tokens.add(new MetallicToken());
		tokens.add(new AncientToken());
		tokens.add(new FocusToken());
		tokens.add(new TrapToken());
		tokens.add(new ElectricToken());
		tokens.add(new BombCasing());
		tokens.add(new SpiritToken());
		tokens.add(new GhostrickToken());
		tokens.add(new MayakashiToken());
		tokens.add(new VendreadToken());
		tokens.add(new VampireToken());
		tokens.add(new ShiranuiToken());
		tokens.add(new EntombToken());
		tokens.add(new MutateToken());
		tokens.add(new UndeadToken());
		return tokens;
	}

	public static DuelistCard getTokenInCombat(DuelistCard token)
	{
		DuelistCard tk = (DuelistCard)token.makeCopy();
		if (AbstractDungeon.player.hasPower(WonderGaragePower.POWER_ID) && tk.canUpgrade()) { tk.upgrade(); }
		return tk;
	}

	public static ArrayList<DuelistCard> getTokensForCombat()
	{
		return getTokensForCombat(false, false, true, false, false, true, new ArrayList<>());
	}

	private static ArrayList<DuelistCard> getTokensForCombat(boolean potion, boolean relic, boolean badTokens, boolean exodia, boolean toon, boolean superRare, ArrayList<String> exclude)
	{
		ArrayList<DuelistCard> tokens = new ArrayList<>();
		ArrayList<DuelistCard> superRareTokens = new ArrayList<>();
		if (Util.deckIs("Machine Deck"))
		{
			superRareTokens.add(new RobotToken());
			superRareTokens.add(new AncientToken());
		}
		if (Util.deckIs("Zombie Deck"))
		{
			superRareTokens.add(new EntombToken());
		}
		superRareTokens.add(new ElectricToken());
		tokens.add(new AnubisToken());
		tokens.add(new BloodToken());
		tokens.add(new BonanzaToken());
		tokens.add(new DamageToken());
		tokens.add(new DragonToken());
		tokens.add(new DragonicToken());
		tokens.add(new HaneToken());
		tokens.add(new KuribohToken());
		tokens.add(new MegatypeToken());
		tokens.add(new OrbToken());
		tokens.add(new PuzzleToken());
		tokens.add(new RockToken());
		tokens.add(new SpellcasterToken());
		tokens.add(new StimToken());
		tokens.add(new StormToken());
		tokens.add(new SummonToken());
		tokens.add(new SuperheavyToken());
		tokens.add(new Token());
		tokens.add(new TributeToken());
		tokens.add(new UnderdogToken());
		tokens.add(new ZombieToken());
		tokens.add(new EntrenchToken());
		tokens.add(new FreezeToken());
		tokens.add(new BurnToken());
		tokens.add(new ArmoredToken());
		tokens.add(new MetallicToken());
		tokens.add(new FocusToken());
		tokens.add(new TrapToken());
		tokens.add(new JamToken());
		if (Util.deckIs("Aqua Deck") || Util.deckIs("Machine Deck"))
		{
			tokens.add(new AquaToken());
		}
		if (Util.deckIs("Fiend Deck"))
		{
			tokens.add(new FiendToken());
			tokens.add(new CastleToken());
		}
		if (Util.deckIs("Naturia Deck") || Util.deckIs("Insect Deck") || Util.deckIs("Plant Deck"))
		{
			tokens.add(new NatureToken());
			tokens.add(new NaturiaToken());
			tokens.add(new PlantToken());
			tokens.add(new PredaplantToken());
			tokens.add(new InsectToken());
			tokens.add(new CocoonToken());
		}
		if (Util.deckIs("Warrior Deck"))
		{
			tokens.add(new StanceToken());
			tokens.add(new WarriorToken());
			tokens.add(new MagnetToken());
			tokens.add(new ForsakenToken());
		}
		if (Util.deckIs("Machine Deck"))
		{
			tokens.add(new GlitchToken());
			tokens.add(new BlastToken());
			tokens.add(new FluxToken());
			tokens.add(new GreaseToken());
			tokens.add(new MachineToken());
			tokens.add(new TimeToken());
			if (DuelistMod.quicktimeEventsAllowed)
			{
				tokens.add(new MegaGlitchToken());
			}
		}
		if (Util.deckIs("Zombie Deck"))
		{
			tokens.add(new ShadowToken());
			tokens.add(new SpiritToken());
			tokens.add(new GhostrickToken());
			tokens.add(new MayakashiToken());
			tokens.add(new VendreadToken());
			tokens.add(new VampireToken());
			tokens.add(new ShiranuiToken());
			tokens.add(new MutateToken());
			tokens.add(new UndeadToken());
		}

		if (!DuelistMod.exodiaBtnBool || exodia) { tokens.add(new ExodiaToken()); }
		if (!DuelistMod.toonBtnBool || toon) { tokens.add(new ToonToken()); }
		if (relic) { tokens.add(new RelicToken()); }
		if (potion) { tokens.add(new PotionToken()); }
		if (badTokens)
		{
			tokens.add(new ExplosiveToken());
			tokens.add(new SuperExplodingToken());
			if (Util.deckIs("Zombie Deck"))
			{
				tokens.add(new PlagueToken());
			}
		}
		if (superRare)
		{
			int superRoll = AbstractDungeon.cardRandomRng.random(1, 15);
			if (superRoll == 1) { tokens.addAll(superRareTokens); }
		}
		if (AbstractDungeon.player.hasPower(WonderGaragePower.POWER_ID) || AbstractDungeon.player.hasPower(MasterRealityPower.POWER_ID))
		{
			for (AbstractCard c : tokens) { c.upgrade(); }
		}
		ArrayList<DuelistCard> filteredTokens = new ArrayList<>();
		for (DuelistCard token : tokens) {
			if (!exclude.contains(token.cardID)) {
				filteredTokens.add(token);
			}
		}
		return filteredTokens;
	}

	public static DuelistCard getRandomTokenForCombat(ArrayList<String> excludedTokenIds) {
		return getRandomTokenForCombat(false, false, true, false, false, true, excludedTokenIds);
	}

	public static DuelistCard getRandomTokenForCombat()
	{
		return getRandomTokenForCombat(new ArrayList<>());
	}

	public static DuelistCard getRandomTokenForCombat(boolean potion, boolean relic, boolean badTokens, boolean exodia, boolean toon, boolean superRare, ArrayList<String> excludedTokenIds)
	{
		ArrayList<DuelistCard> tokens = getTokensForCombat(potion, relic, badTokens, exodia, toon, superRare, excludedTokenIds);
		if (tokens.size() > 1) {
			return tokens.get(AbstractDungeon.cardRandomRng.random(tokens.size() - 1));
		} else if (tokens.size() == 1) {
			return tokens.get(0);
		}
		return new Token();
	}

	public static AbstractCard getRandomDuelistCurseUnseeded()
	{
		if (DuelistMod.curses.size() > 0)
		{
			return DuelistMod.curses.get(ThreadLocalRandom.current().nextInt(0, DuelistMod.curses.size())).makeCopy();
		}
		else
		{
			return CardLibrary.getCurse();
		}
	}

	public static AbstractCard getRandomDuelistCurse()
	{
		if (DuelistMod.curses.size() > 0)
		{
			return DuelistMod.curses.get(AbstractDungeon.cardRandomRng.random(DuelistMod.curses.size() - 1)).makeCopy();
		}
		else
		{
			return CardLibrary.getCurse();
		}
	}

	public static AbstractCard getRandomMetronome()
	{
		ArrayList<AbstractCard> mets = new ArrayList<AbstractCard>();
		for (AbstractCard c : DuelistMod.myCards)
		{
			if (c instanceof MetronomeCard)
			{
				mets.add(c.makeCopy());
			}
		}
		return mets.get(AbstractDungeon.cardRandomRng.random(mets.size() - 1));
	}

	public static void setupMyCardsDebug(String poolName)
	{
		switch (poolName)
		{
		case "Standard":
			for (AbstractCard c : StandardPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Dragon":
			for (AbstractCard c : DragonPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Spellcaster":
			for (AbstractCard c : SpellcasterPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Nature":
			for (AbstractCard c : NaturiaPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Aqua":
			for (AbstractCard c : AquaPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Ascended One":
			for (AbstractCard c : AscendedOnePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Ascended Two":
			for (AbstractCard c : AscendedTwoPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Ascended Three":
			for (AbstractCard c : AscendedThreePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Pharaoh":
			//for (AbstractCard c : PharaohPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Basic":
			for (AbstractCard c : BasicPool.fullBasic("Debug")) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Creator":
			for (AbstractCard c : CreatorPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Exodia":
			for (AbstractCard c : ExodiaPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Fiend":
			for (AbstractCard c : FiendPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Giant":
			for (AbstractCard c : GiantPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Increment":
			for (AbstractCard c : IncrementPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Insect":
			for (AbstractCard c : InsectPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Machine":
			for (AbstractCard c : MachinePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Megatype":
			for (AbstractCard c : MegatypePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Ojama":
			for (AbstractCard c : OjamaPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Plant":
			for (AbstractCard c : PlantPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Predaplant":
			for (AbstractCard c : PredaplantPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Toon":
			for (AbstractCard c : ToonPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Warrior":
			for (AbstractCard c : WarriorPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		case "Zombie":
			for (AbstractCard c : ZombiePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
			break;
		}
		DuelistMod.curses.add(new GravekeeperCurse());
		DuelistMod.curses.add(new CurseAnubis());
		DuelistMod.curses.add(new CurseArmaments());
		DuelistMod.curses.add(new CursedBill());
		DuelistMod.curses.add(new CurseAging());
		DuelistMod.curses.add(new SummoningCurse());
		DuelistMod.curses.add(new VampireCurse());
		DuelistMod.curses.add(new PsiCurse());
		for (CardTags t : DuelistMod.monsterTypes)
		{
			String ID = DuelistMod.typeCardMap_ID.get(t);
			CardStrings localCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			DuelistMod.typeCardMap_NAME.put(t, localCardStrings.NAME);
			DuelistMod.typeCardMap_NameToString.put(localCardStrings.NAME, t);
			DuelistMod.typeCardMap_DESC.put(t, localCardStrings.DESCRIPTION);
		}

		DuelistMod.typeCardMap_NAME.put(Tags.MEGATYPED, "Megatyped");
		DuelistMod.typeCardMap_NameToString.put("Megatyped", Tags.MEGATYPED);

		DuelistMod.cardCount = 0;
		for (int i = 0; i < DuelistMod.myCards.size(); i++)
		{
			DuelistMod.cardCount++;
		}

		// Add tokens to 'The Duelist' section of compendium
		if (!DuelistMod.addTokens) { for (DuelistCard c : getAllDuelistTokens()) { if (c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.myCards.add(c); }}}


		// DEBUG CARD STUFF
		if (DuelistMod.debug)
		{
			Debug.printCardSetsForGithubReadme(DuelistMod.myCards);
			//Debug.printTextForTranslation();
			for (DuelistCard c : DuelistMod.myCards)
			{
				if (c.tributes != c.baseTributes || c.summons != c.baseSummons)
				{
					if (c.hasTag(Tags.MONSTER))
					{
						DuelistMod.logger.info("something didn't match for " + c.originalName + " Base/Current Tributes: " + c.baseTributes + "/" + c.tributes + " :: Base/Current Summons: " + c.baseSummons + "/" + c.summons);
					}
					else
					{
						DuelistMod.logger.info("something didn't match for " + c.originalName + " but this card is a spell or trap");
					}
				}
			}
		}

		if (DuelistMod.addTokens)
		{
			DuelistMod.myCards.addAll(getAllDuelistTokens());
			DuelistMod.myCards.add(new BadToken());
			DuelistMod.myCards.add(new GreatMoth());
			DuelistMod.myCards.add(new HeartUnderspell());
			DuelistMod.myCards.add(new HeartUndertrap());
			DuelistMod.myCards.add(new HeartUndertribute());
			for (DuelistCard orbCard : DuelistMod.orbCards) { DuelistMod.myCards.add(orbCard); }
		}
		// END DEBUG CARD STUFF

		for (DuelistCard c : DuelistMod.myCards)
		{
			if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.RARE) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON_POOL) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.rareCards.add((DuelistCard) c.makeStatEquivalentCopy());
			}

			else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.UNCOMMON) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON_POOL) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.uncommonCards.add((DuelistCard) c.makeStatEquivalentCopy());
				DuelistMod.nonRareCards.add((DuelistCard) c.makeStatEquivalentCopy());
			}

			else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.COMMON) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON_POOL) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.commonCards.add((DuelistCard) c.makeStatEquivalentCopy());
				DuelistMod.nonRareCards.add((DuelistCard) c.makeStatEquivalentCopy());
			}
		}

		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_CARDS, DuelistMod.cardCount);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AbstractCard infiniteUpgradeCheck(AbstractCard c)
	{
		if (c instanceof ExodiaHead) { return new CancelCard(); }
		AbstractCard test = c.makeCopy();
		while (test.canUpgrade())
		{
			test.upgrade();
			if (test.timesUpgraded > 1000)
			{
				Util.log("Infinite Upgrade Check - " + test.name);
				return test;
			}
		}
		return new CancelCard();
	}



}
