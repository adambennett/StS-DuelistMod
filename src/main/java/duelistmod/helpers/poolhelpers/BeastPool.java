package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.pools.beast.*;
import duelistmod.cards.pools.insects.Forest;
import duelistmod.cards.pools.insects.SpiritualForest;

public class BeastPool {
	private static final String deckName = "Beast Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.oneRandom(12));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.twoRandom(12));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck beastDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> beastCards = new ArrayList<>();

		beastCards.add(new AndroSphinx());
		beastCards.add(new ArmoredRat());
		beastCards.add(new ArmoredWhiteBear());
		beastCards.add(new AssaultDog());
		beastCards.add(new BattleInstinct());
		beastCards.add(new BeastBattlefieldBarrier());
		beastCards.add(new BeastRage());
		beastCards.add(new BeastRising());
		beastCards.add(new BeastSoulSwap());
		beastCards.add(new BehemothKingOfAllAnimals());
		beastCards.add(new BerserkGorilla());
		beastCards.add(new BigWingedBerfomet());
		beastCards.add(new BrigrandTheGloryDragon());
		beastCards.add(new Caninetaur());
		beastCards.add(new CatnippedKitty());
		beastCards.add(new CattleCall());
		beastCards.add(new ChimeraFusion());
		beastCards.add(new ChowChowChan());
		beastCards.add(new Cocatorium());
		beastCards.add(new DoomstarUlka());
		beastCards.add(new DreamTowerOfNemleria());
		beastCards.add(new EnragedBattleOx());
		beastCards.add(new FencingFireFerret());
		beastCards.add(new FenrirTheNordicWolf());
		beastCards.add(new FierceTigerMonghu());
		beastCards.add(new FireFightingDarumaDoll());
		beastCards.add(new FirewingPegasus());
		beastCards.add(new FlowerWolf());
		beastCards.add(new Forest());
		beastCards.add(new FrekiTheRunickFangs());
		beastCards.add(new GiantRat());
		beastCards.add(new GladiatorBeastAlexander());
		beastCards.add(new GladiatorBeastDimacari());
		beastCards.add(new GladiatorBeastEssedarii());
		beastCards.add(new GladiatorBeastSamnite());
		beastCards.add(new GladiatorBeastTygerius());
		beastCards.add(new GravityBehemoth());
		beastCards.add(new GuardDog());
		beastCards.add(new GuardianChimera());
		beastCards.add(new HornOfThePhantomBeast());
		beastCards.add(new HowlOfTheWild());
		beastCards.add(new KingOfTheBeasts());
		beastCards.add(new LightningTricorn());
		beastCards.add(new ManticoreOfDarkness());
		beastCards.add(new NinjitsuArtOfTransformation());
		beastCards.add(new NeoSpacianDarkPanther());
		beastCards.add(new NohPunkFoxyTune());
		beastCards.add(new ObedienceSchooled());
		beastCards.add(new PaleBeast());
		beastCards.add(new PhantomBeastRockLizard());
		beastCards.add(new PhotonCerberus());
		beastCards.add(new PhotonLeo());
		beastCards.add(new PhotonSabreTiger());
		beastCards.add(new PropaGandake());
		beastCards.add(new RoaringEarth());
		beastCards.add(new RushRecklessly());
		beastCards.add(new SpiritualForest());
		beastCards.add(new SuperRushRecklessly());
		beastCards.add(new TheBigCattleDrive());
		beastCards.add(new NemleriaRepette());
		beastCards.add(new ThousandNeedles());
		beastCards.add(new ThreeThousandNeedles());
		beastCards.add(new ThunderUnicorn());
		beastCards.add(new TriBrigadeArmsBucephalus());
		beastCards.add(new TriBrigadeBarrenBlossom());
		beastCards.add(new TriBrigadeFraktall());
		beastCards.add(new TriBrigadeKerass());
		beastCards.add(new TriBrigadeKitt());
		beastCards.add(new TriBrigadeOminousOmen());
		beastCards.add(new TriBrigadeRampantRampager());
		beastCards.add(new TriBrigadeSilverSheller());
		beastCards.add(new TwinHeadedBeast());
		beastCards.add(new TwoThousandNeedles());
		beastCards.add(new UnicornBeacon());
		beastCards.add(new ZemanTheApeKing());

		// Base game cards
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType()) {

		}

		beastDeck.fillPoolCards(beastCards);
		return beastCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) { pool.addAll(BasicPool.smallBasic("Beast Deck")); }
		else { pool.addAll(BasicPool.fullBasic("Beast Deck")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
