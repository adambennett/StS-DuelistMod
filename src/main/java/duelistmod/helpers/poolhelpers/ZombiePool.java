package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.pools.insects.PoisonMummy;
import duelistmod.cards.pools.machine.OniTankT34;
import duelistmod.cards.pools.zombies.*;

public class ZombiePool 
{
	
	private static String deckName = "Zombie Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(11));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(11));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck zombieDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> zombieCards = new ArrayList<AbstractCard>();
		zombieCards.add(new AbsorbFusion());
		zombieCards.add(new AntiFusionDevice());
		zombieCards.add(new AshBlossom());		
		zombieCards.add(new AvendreadSavior());
		zombieCards.add(new BattleFusion());
		zombieCards.add(new BoneMouse());
		zombieCards.add(new Bonecrusher());				
		zombieCards.add(new BookEclipse());
		zombieCards.add(new BookLife());
		zombieCards.add(new BookMoon());
		zombieCards.add(new BrilliantFusion());
		zombieCards.add(new CallMummy());				
		zombieCards.add(new CardSafeReturn());
		zombieCards.add(new Chewbone());
		zombieCards.add(new Chopman());		
		zombieCards.add(new CrimsonKnightVampire());
		zombieCards.add(new Dakki());		
		zombieCards.add(new DarkDustSpirit());
		zombieCards.add(new DecayedCommander());
		zombieCards.add(new DesLacooda());
		zombieCards.add(new DespairFromDark());
		zombieCards.add(new DimensionBurial());
		zombieCards.add(new DimensionFusion());
		zombieCards.add(new DoomkingBalerdroch());
		zombieCards.add(new DoubleCoston());
		zombieCards.add(new EndlessDecay());		
		zombieCards.add(new FearFromDark());
		zombieCards.add(new FireReaper());
		zombieCards.add(new FossilSkullKing());
		zombieCards.add(new FossilSkullknight());
		zombieCards.add(new FragmentFusion());
		zombieCards.add(new FusionGuard());
		zombieCards.add(new FusionTag());
		zombieCards.add(new FusionWeapon());
		zombieCards.add(new Gashadokuro());		
		zombieCards.add(new Gernia());		
		zombieCards.add(new GhostBelle());
		zombieCards.add(new GhostOgre());
		zombieCards.add(new GhostReaper());
		zombieCards.add(new GhostSister());
		zombieCards.add(new GhostrickAlucard());
		zombieCards.add(new GhostrickBreak());		
		zombieCards.add(new GhostrickGhoul());	
		zombieCards.add(new GhostrickJackfrost());
		zombieCards.add(new GhostrickJiangshi());	
		zombieCards.add(new GhostrickMansion());		
		zombieCards.add(new GhostrickMummy());
		zombieCards.add(new GhostrickMuseum());		
		zombieCards.add(new GhostrickNight());
		zombieCards.add(new GhostrickParade());
		//zombieCards.add(new GhostrickRenovation());
		zombieCards.add(new SkullConductor());
		zombieCards.add(new GhostrickScare());
		zombieCards.add(new GhostrickSkeleton());
		zombieCards.add(new GhostrickSpecter());
		zombieCards.add(new GhostrickStein());
		zombieCards.add(new GhostrickVanish());
		zombieCards.add(new GhostrickWarwolf());
		zombieCards.add(new GhostrickYeti());
		zombieCards.add(new GiantAxeMummy());
		zombieCards.add(new GoblinZombie());
		zombieCards.add(new Gozuki());
		zombieCards.add(new Hajun());
		zombieCards.add(new HauntedShrine());		
		zombieCards.add(new IlBlud());
		zombieCards.add(new ImmortalRuler());		
		zombieCards.add(new InstantFusion());		
		zombieCards.add(new Kasha());
		zombieCards.add(new LichLord());				
		zombieCards.add(new MammothGraveyard());
		zombieCards.add(new Mausoleum());
		zombieCards.add(new MayakashiReturn());
		zombieCards.add(new MayakashiWinter());
		zombieCards.add(new MechMoleZombie());
		zombieCards.add(new Mezuki());
		zombieCards.add(new Miscellaneousaurus());		
		zombieCards.add(new Mispolymerization());
		zombieCards.add(new MoltenZombie());
		zombieCards.add(new MonsterReborn());
		zombieCards.add(new NecroFusion());
		zombieCards.add(new Necroface());
		zombieCards.add(new NecroworldBanshee());		
		zombieCards.add(new NightmareHorse());
		zombieCards.add(new OboroGuruma());
		zombieCards.add(new OniGamiCombo());
		zombieCards.add(new OniTankT34());		
		zombieCards.add(new OverpoweringEye());		
		zombieCards.add(new OvertexQoatlus());
		zombieCards.add(new PainPainter());		
		zombieCards.add(new ParallelWorldFusion());		
		zombieCards.add(new PhantomGhost());		
		zombieCards.add(new PoisonMummy());
		zombieCards.add(new Polymerization());
		zombieCards.add(new PrematureBurial());
		zombieCards.add(new Pumpking());
		zombieCards.add(new Pumprincess());
		zombieCards.add(new PyramidTurtle());
		zombieCards.add(new PyramidWonders());
		zombieCards.add(new RedEyesZombie());		
		zombieCards.add(new RegenMummy());
		zombieCards.add(new RelinquishedFusion());		
		zombieCards.add(new RevendreadEvolution());
		zombieCards.add(new RevendreadExecutor());
		zombieCards.add(new RevendreadOrigin());
		zombieCards.add(new RevendreadSlayer());		
		zombieCards.add(new RobbinZombie());
		zombieCards.add(new RyuKokki());
		zombieCards.add(new Scapeghost());
		zombieCards.add(new ShadowVampire());
		zombieCards.add(new Skelesaurus());
		zombieCards.add(new SkullServant());
		zombieCards.add(new SoulAbsorbingBone());
		zombieCards.add(new SoulRelease());
		zombieCards.add(new SouleatingOviraptor());
		zombieCards.add(new SpiritPharaoh());
		zombieCards.add(new SpiritReaper());		
		zombieCards.add(new SuperPolymerization());
		zombieCards.add(new Tengu());
		zombieCards.add(new TriWight());
		zombieCards.add(new TributeDoomed());
		zombieCards.add(new Tsukahagi());		
		zombieCards.add(new TyrantDinoFusion());
		zombieCards.add(new UltraPolymerization());
		zombieCards.add(new UnderworldCannon());
		zombieCards.add(new UniZombie());
		zombieCards.add(new VampireAwakening());
		zombieCards.add(new VampireBaby());
		zombieCards.add(new VampireDesire());
		zombieCards.add(new VampireDomain());
		zombieCards.add(new VampireDomination());
		zombieCards.add(new VampireDragon());
		zombieCards.add(new VampireDuke());
		zombieCards.add(new VampireFamiliar());
		zombieCards.add(new VampireFraulein());
		zombieCards.add(new VampireGenesis());
		zombieCards.add(new VampireGrace());
		zombieCards.add(new VampireGrimson());
		zombieCards.add(new VampireHunter());
		zombieCards.add(new VampireKingdom());
		zombieCards.add(new VampireLady());
		zombieCards.add(new VampireLord());
		zombieCards.add(new VampireRedBaron());
		zombieCards.add(new VampireRetainer());
		zombieCards.add(new VampireScarletScourge());
		zombieCards.add(new VampireSorcerer());
		zombieCards.add(new VampireSucker());
		zombieCards.add(new VampireTakeover());
		zombieCards.add(new VampireVamp());
		zombieCards.add(new VendreadAnima());
		zombieCards.add(new VendreadBattlelord());
		zombieCards.add(new VendreadCharge());
		zombieCards.add(new VendreadChimera());
		zombieCards.add(new VendreadCore());
		zombieCards.add(new VendreadDaybreak());
		zombieCards.add(new VendreadHoundhorde());
		zombieCards.add(new VendreadNightmare());
		zombieCards.add(new VendreadNights());
		zombieCards.add(new VendreadReorigin());
		zombieCards.add(new VendreadReunion());
		zombieCards.add(new VendreadRevenants());
		zombieCards.add(new VendreadRevolution());
		zombieCards.add(new VendreadStriges());
		zombieCards.add(new VisionFusion());
		zombieCards.add(new Wasteland());
		zombieCards.add(new WightLady());
		zombieCards.add(new Wightmare());
		zombieCards.add(new Wightprince());
		zombieCards.add(new Wightprincess());
		zombieCards.add(new Yasha());
		zombieCards.add(new Yoko());
		zombieCards.add(new YukiMusume());
		zombieCards.add(new YukiOnnaAbsolute());
		zombieCards.add(new YukiOnnaIce());
		zombieCards.add(new ZombieMammoth());	
		zombieCards.add(new ZombieMaster());
		zombieCards.add(new ZombieNecronize());
		zombieCards.add(new ZombiePowerStruggle());
		zombieCards.add(new ZombieTiger());
		zombieCards.add(new ZombieWarrior());
		zombieCards.add(new ZombieWorld());
		zombieCards.add(new Zombina());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			zombieCards.add(new BouncingFlask());
			zombieCards.add(new BurningPact());
			zombieCards.add(new Catalyst());
			zombieCards.add(new Corruption());
			zombieCards.add(new CripplingPoison());
			zombieCards.add(new DarkEmbrace());
			zombieCards.add(new DeadlyPoison());
			zombieCards.add(new Exhume());
			zombieCards.add(new FeelNoPain());
			zombieCards.add(new FiendFire());
			zombieCards.add(new GhostlyArmor());
			zombieCards.add(new Hemokinesis());
			zombieCards.add(new InfernalBlade());
			zombieCards.add(new LegSweep());
			zombieCards.add(new PoisonedStab());
			zombieCards.add(new SeeingRed());
			zombieCards.add(new Sentinel());
			zombieCards.add(new SeverSoul());
			zombieCards.add(new Terror());
			zombieCards.add(new TrueGrit());
		}

		zombieDeck.fillPoolCards(zombieCards);
		//DuelistMod.archetypeCards.addAll(zombieCards);
		return zombieCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
