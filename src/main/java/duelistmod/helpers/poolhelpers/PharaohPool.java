package duelistmod.helpers.poolhelpers;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.cards.red.*;
import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.fiend.DarkBlade;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.*;

public class PharaohPool 
{
	public static ArrayList<AbstractCard> deck(int level)
	{
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		ArrayList<AbstractCard> cards;
		
		Map<Integer, ArrayList<AbstractCard>> decks = new HashMap<>();
		decks.put(1, pharaohOne());
		decks.put(2, pharaohTwo());
		decks.put(3, pharaohThree());
		decks.put(4, pharaohFour());
		decks.put(5, pharaohFive());

		cards = decks.get(level);
		
		// Any all-pharaoh-decks cards get added here
		
	/*
		// Pharaoh Cards
		cards.add(new PharaohBlessing());
		cards.add(new RainbowMedicine());
		
		// Megatype cards
		cards.add(new RainbowOverdragon());
		cards.add(new RainbowKuriboh());
		cards.add(new RainbowLife());
		cards.add(new RainbowGravity());
		cards.add(new RainbowJar());
		cards.add(new WingedKuriboh9());
		cards.add(new WingedKuriboh10());
		cards.add(new ClearKuriboh());
		cards.add(new RainbowBridge());		
		cards.add(new KamionTimelord());
		cards.add(new IrisEarthMother());
		cards.add(new RainbowRefraction());
		cards.add(new CrystalRaigeki());
		cards.add(new RainbowRuins());
		cards.add(new RainbowMagician());
		cards.add(new RainbowDarkDragon());
		cards.add(new MaleficRainbowDragon());
		cards.add(new RainbowDragon());
		//cards.add(new HourglassLife());
		cards.add(new Eva());
		cards.add(new HappyLover());
		cards.add(new DunamesDarkWitch());
		cards.add(new RainbowNeos());
		cards.add(new RainbowFlower());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			//cards.add(new Token());
		}*/

		deck.fillPoolCards(decks.get(1));
		deckB.fillPoolCards(decks.get(2));
		deckC.fillPoolCards(decks.get(3));
		deckD.fillPoolCards(decks.get(4));
		deckE.fillPoolCards(decks.get(5));
		return cards;
	}
	
	
	public static ArrayList<AbstractCard> pharaohOne() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		cards.add(new IrisEarthMother());
		cards.add(new RainbowRuins());
		cards.add(new LesserDragon());
		cards.add(new RevivalJam());
		cards.add(new Anthrosaurus());
		cards.add(new ArmorBreaker());
		cards.add(new DarkBlade());
		cards.add(new ArmoredBee());
		cards.add(new BasicInsect());
		cards.add(new GadgetSoldier());
		cards.add(new AngelTrumpeter());
		cards.add(new NaturiaRock());
		cards.add(new AncientElf());
		cards.add(new MoltenZombie());
		cards.add(new NaturiaBambooShoot());
		cards.add(new GravityAxe());
		cards.add(new Raigeki());
		cards.add(new FiendSkull());
		cards.add(new SuperheavySoulbuster());
		cards.add(new GlowUpBloom());
		cards.add(new ClearKuriboh());
		cards.add(new MetalDragon());
		cards.add(new FusionWeapon());
		cards.add(new PharaohBlessing());
		cards.add(new RainbowMedicine());

			/*cards.add(new Anger());
			cards.add(new Berserk());
			cards.add(new Brutality());
			cards.add(new Clash());
			cards.add(new Cleave());
			cards.add(new Combust());
			cards.add(new DoubleTap());
			cards.add(new FireBreathing());
			cards.add(new Flex());
			cards.add(new Headbutt());
			cards.add(new Hemokinesis());
			cards.add(new Inflame());
			cards.add(new IronWave());
			cards.add(new Rage());
			cards.add(new RecklessCharge());
			cards.add(new Rupture());
			cards.add(new SearingBlow());
			cards.add(new SeverSoul());
			cards.add(new SwordBoomerang());
			cards.add(new ThunderClap());
			cards.add(new TwinStrike());
			cards.add(new WildStrike());
			cards.add(new Metallicize());*/

		cards.add(new Armaments());
		cards.add(new Barricade());
		cards.add(new Bash());
		cards.add(new BattleTrance());
		cards.add(new BloodForBlood());
		cards.add(new Bloodletting());
		cards.add(new Bludgeon());
		cards.add(new BodySlam());
		cards.add(new BurningPact());
		cards.add(new Carnage());
		cards.add(new Clothesline());
		cards.add(new Corruption());
		cards.add(new DarkEmbrace());
		cards.add(new Defend_Red());
		cards.add(new DemonForm());
		cards.add(new Disarm());
		cards.add(new Dropkick());
		cards.add(new DualWield());
		cards.add(new Entrench());
		cards.add(new Evolve());
		cards.add(new Exhume());
		cards.add(new Feed());
		cards.add(new FeelNoPain());
		cards.add(new FiendFire());
		cards.add(new FlameBarrier());
		cards.add(new GhostlyArmor());
		cards.add(new Havoc());
		cards.add(new HeavyBlade());
		cards.add(new Immolate());
		cards.add(new Impervious());
		cards.add(new InfernalBlade());
		cards.add(new Intimidate());
		cards.add(new Juggernaut());
		cards.add(new LimitBreak());
		cards.add(new Offering());
		cards.add(new PerfectedStrike());
		cards.add(new PommelStrike());
		cards.add(new PowerThrough());
		cards.add(new Pummel());
		cards.add(new Rampage());
		cards.add(new Reaper());
		cards.add(new SecondWind());
		cards.add(new SeeingRed());
		cards.add(new Sentinel());
		cards.add(new Shockwave());
		cards.add(new ShrugItOff());
		cards.add(new SpotWeakness());
		cards.add(new Strike_Red());
		cards.add(new TrueGrit());
		cards.add(new Uppercut());
		cards.add(new Warcry());
		cards.add(new Whirlwind());
		return cards;
	}

	public static ArrayList<AbstractCard> pharaohTwo() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		return cards;
	}

	public static ArrayList<AbstractCard> pharaohThree() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		return cards;
	}

	public static ArrayList<AbstractCard> pharaohFour() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		return cards;
	}

	public static ArrayList<AbstractCard> pharaohFive() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		return cards;
	}
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pools = new ArrayList<>();		
		pools.addAll(GlobalPoolHelper.oneRandom());
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		deck.fillPoolCards(pools);
		
		deckB.fillPoolCards(pools);
		
		deckC.fillPoolCards(pools);
		
		deckD.fillPoolCards(pools);
		
		deckE.fillPoolCards(pools);
		return pools;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pools = new ArrayList<>();		
		pools.addAll(GlobalPoolHelper.twoRandom());
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		deck.fillPoolCards(pools);
		
		deckB.fillPoolCards(pools);
		
		deckC.fillPoolCards(pools);
		
		deckD.fillPoolCards(pools);
		
		deckE.fillPoolCards(pools);
		return pools;
	}
	
	public static ArrayList<AbstractCard> basic()
	{
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");

		boolean smallBasicSet = DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet();
		
		if (smallBasicSet) { deck.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deck.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (smallBasicSet) { deckB.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deckB.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (smallBasicSet) { deckC.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deckC.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (smallBasicSet) { deckD.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deckD.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (smallBasicSet) { deckE.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deckE.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (smallBasicSet) { return BasicPool.smallBasic(deckName); }
		else { return BasicPool.fullBasic(deckName); }
	}
}
