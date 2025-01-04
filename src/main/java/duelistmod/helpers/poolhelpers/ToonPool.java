package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.beast.FlyingElephant;
import duelistmod.cards.pools.dragons.SpiralFlameStrike;
import duelistmod.cards.pools.gusto.ContactWithGusto;
import duelistmod.cards.pools.toon.*;
import duelistmod.cards.pools.warrior.CrossAttack;

public class ToonPool {

	public static ArrayList<AbstractCard> oneRandom() {
        return new ArrayList<>(GlobalPoolHelper.oneRandom(13));
	}
	
	public static ArrayList<AbstractCard> twoRandom() {
        return new ArrayList<>(GlobalPoolHelper.twoRandom(13));
	}
	
	public static ArrayList<AbstractCard> deck() {
		ArrayList<AbstractCard> toonCards = new ArrayList<>();

		toonCards.add(new Bagooska());
		toonCards.add(new BannerOfCourage());
		toonCards.add(new BlueEyesToon());
		toonCards.add(new Bunilla());
		toonCards.add(new CardOfLastWill());
		toonCards.add(new ChainDog());
		toonCards.add(new ComicHand());
		toonCards.add(new ContactWithGusto());
		toonCards.add(new CreepyConey());
		toonCards.add(new CrossAttack());
		toonCards.add(new DBoyz());
		toonCards.add(new DarkBribe());
		toonCards.add(new DarkToonBlast());
		toonCards.add(new DoubleAttack());
		toonCards.add(new FlyingElephant());
		toonCards.add(new GaiaTheToonKnight());
		toonCards.add(new HungryBurger());
		toonCards.add(new JarRobber());
		toonCards.add(new MangaMonsterReborn());
		toonCards.add(new MangaRyuRan());
		toonCards.add(new MasterOfOz());
		toonCards.add(new Mimiclay());
		toonCards.add(new MineMole());
		toonCards.add(new Oops());
		toonCards.add(new PotGreed());
		toonCards.add(new RedEyesToon());
		toonCards.add(new ShadowToon());
		toonCards.add(new SpiralFlameStrike());
		toonCards.add(new SwiftBirdmanJoe());
		toonCards.add(new TardyOrc());
		toonCards.add(new Tatsunootoshigo());
		toonCards.add(new ThereCanBeOnlyOne());
		toonCards.add(new TimeWizardOfTomorrow());
		toonCards.add(new ToadallyAwesome());
		toonCards.add(new ToonAlligator());
		toonCards.add(new ToonAncientGear());
		toonCards.add(new ToonAppleMagicianGirl());
		toonCards.add(new ToonBabyDragon());
		toonCards.add(new ToonBarrelDragon());
		toonCards.add(new ToonBerryMagicianGirl());
		toonCards.add(new ToonBlackLusterSoldier());
		toonCards.add(new ToonBlueEyesUltimate());
		toonCards.add(new ToonBookmark());
		toonCards.add(new ToonBriefcase());
		toonCards.add(new ToonBusterBlader());
		toonCards.add(new ToonCannonSoldier());
		toonCards.add(new ToonChocolateMagicianGirl());
		toonCards.add(new ToonCyberDragon());
		toonCards.add(new ToonDarkMagician());
		toonCards.add(new ToonDarkMagicianGirl());
		toonCards.add(new ToonDefense());
		toonCards.add(new ToonExodiaIncarnate());
		toonCards.add(new ToonExplosion());
		toonCards.add(new ToonGeminiElf());
		toonCards.add(new ToonGoblinAttackForce());
		toonCards.add(new ToonGodStrike());
		toonCards.add(new ToonHarpieLady());
		toonCards.add(new ToonKingdom());
		toonCards.add(new ToonKiwiMagicianGirl());
		toonCards.add(new ToonKuriboh());
		toonCards.add(new ToonLegendaryFisherman());
		toonCards.add(new ToonLemonMagicianGirl());
		toonCards.add(new ToonMagic());
		toonCards.add(new ToonMask());
		toonCards.add(new ToonMaskedSorcerer());
		toonCards.add(new ToonMermaid());
		toonCards.add(new ToonPageFlip());
		toonCards.add(new ToonRelinquished());
		toonCards.add(new ToonRollback());
		toonCards.add(new ToonTable());
		toonCards.add(new ToonTerror());
		toonCards.add(new ToonWorld());
		toonCards.add(new TransmissionGear());
		toonCards.add(new RemoteRevenge());
		// toonCards.add(new RevengeRally());

		//toonCards.add(new PotProsperity());
		//toonCards.add(new PotDesires());
		//toonCards.add(new PotAcquisitiveness());
		//toonCards.add(new PotRiches());
		//toonCards.add(new PotBenevloence());
		//toonCards.add(new PotExtravagance());

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
