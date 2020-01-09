package duelistmod.helpers;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.regex.PatternSyntaxException;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.PlayRandomFromDiscardAction;
import duelistmod.cards.*;
import duelistmod.cards.holiday.birthday.*;
import duelistmod.cards.holiday.christmas.*;
import duelistmod.cards.holiday.fourtwenty.WeedOut;
import duelistmod.cards.holiday.halloween.*;
import duelistmod.cards.incomplete.HourglassLife;
import duelistmod.cards.nameless.greed.*;
import duelistmod.cards.nameless.magic.*;
import duelistmod.cards.nameless.power.*;
import duelistmod.cards.nameless.war.*;
import duelistmod.cards.other.bookOfLifeOptions.CustomResummonCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.cards.other.tokens.Token;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.warrior.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.powers.duelistPowers.zombiePowers.*;
import duelistmod.powers.enemyPowers.*;
import duelistmod.powers.incomplete.*;
import duelistmod.relics.*;
import duelistmod.variables.Tags;

public class Util
{
    public static final Logger Logger = LogManager.getLogger(Util.class.getName());
    
    public static void log()
    {
    	log("Generic Debug Statement");
    }
    
    public static void log(String s)
    {
    	if (DuelistMod.debug)
    	{
    		DuelistMod.logger.info(s);
    	}
    }
    
    public static String getDeck()
    {
    	return StarterDeckSetup.getCurrentDeck().getSimpleName();
    }
    
    public static boolean deckIs(String deckName)
    {
    	if (getDeck().equals("deckName")) { return true; }
    	else if (DuelistMod.addedAquaSet && deckName.equals("Aqua Deck")) { return true; }
    	else if (DuelistMod.addedDragonSet && deckName.equals("Dragon Deck")) { return true; }
    	else if (DuelistMod.addedFiendSet && deckName.equals("Fiend Deck")) { return true; }
    	else if (DuelistMod.addedIncrementSet && deckName.equals("Increment Deck")) { return true; }
    	else if (DuelistMod.addedInsectSet && deckName.equals("Insect Deck")) { return true; }
    	else if (DuelistMod.addedMachineSet && deckName.equals("Machine Deck")) { return true; }
    	else if (DuelistMod.addedNaturiaSet && deckName.equals("Naturia Deck")) { return true; }
    	else if (DuelistMod.addedOjamaSet && deckName.equals("Ojama Deck")) { return true; }
    	else if (DuelistMod.addedPlantSet && deckName.equals("Plant Deck")) { return true; }
    	else if (DuelistMod.addedSpellcasterSet && deckName.equals("Spellcaster Deck")) { return true; }
    	else if (DuelistMod.addedStandardSet && deckName.equals("Standard Deck")) { return true; }
    	else if (DuelistMod.addedToonSet && deckName.equals("Toon Deck")) { return true; }
    	else if (DuelistMod.addedWarriorSet && deckName.equals("Warrior Deck")) { return true; }
    	else if (DuelistMod.addedZombieSet && deckName.equals("Zombie Deck")) { return true; }
    	return getDeck().equals(deckName);
    }
    
    public static boolean isCustomModActive(String ID) {
        return (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ID)) || ModHelper.isModEnabled(ID);
    }
    
    public static int factorial(int n) 
    {
    	if (n > 19) { n = 19; }
    	DuelistMod.logger.info("Factorial iteration value: " + n);
    	return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);
    } 

    public static <T> T SafeCast(Object o, Class<T> type)
    {
        return type.isInstance(o) ? type.cast(o) : null;
    }

    public static <T> T GetRandomElement(ArrayList<T> list, com.megacrit.cardcrawl.random.Random rng)
    {
        int size = list.size();
        if (size > 0)
        {
            return list.get(rng.random(list.size() - 1));
        }

        return null;
    }

    public static <T> T GetRandomElement(ArrayList<T> list)
    {
        int size = list.size();
        if (size > 0)
        {
            return list.get(MathUtils.random(list.size() - 1));
        }

        return null;
    }

    public static <T> ArrayList<T> Where(ArrayList<T> list, Predicate<T> predicate)
    {
        ArrayList<T> res = new ArrayList<>();
        for (T t : list)
        {
            if (predicate.test(t))
            {
                res.add(t);
            }
        }

        return res;
    }

	public static String titleCase(String text) {
	    if (text == null || text.isEmpty()) {
	        return text;
	    }
	
	    StringBuilder converted = new StringBuilder();
	
	    boolean convertNext = true;
	    for (char ch : text.toCharArray()) {
	        if (Character.isSpaceChar(ch)) {
	            convertNext = true;
	        } else if (convertNext) {
	            ch = Character.toTitleCase(ch);
	            convertNext = false;
	        } else {
	            ch = Character.toLowerCase(ch);
	        }
	        converted.append(ch);
	    }
	
	    return converted.toString();
	}
	
	public static void handleZombSubTypes(AbstractCard playedCard)
	{
		if (playedCard.hasTag(Tags.VAMPIRE)) { DuelistMod.vampiresPlayed++; }
		if (playedCard.hasTag(Tags.GHOSTRICK)) {  DuelistMod.ghostrickPlayed++; }
		if (playedCard.hasTag(Tags.MAYAKASHI)) {  DuelistMod.mayakashiPlayed++; }
		if (playedCard.hasTag(Tags.VENDREAD)) {  DuelistMod.vendreadPlayed++; }
		if (playedCard.hasTag(Tags.SHIRANUI)) {  DuelistMod.shiranuiPlayed++; }
		
		if (DuelistMod.ghostrickPlayed >= 10)
		{
			DuelistMod.ghostrickPlayed = 0;
			triggerGhostrick(playedCard);
		}
		
		if (DuelistMod.mayakashiPlayed >= 3)
		{
			DuelistMod.mayakashiPlayed = 0;
			triggerMayakashi();
		}
		
		if (DuelistMod.shiranuiPlayed >= 5)
		{
			DuelistMod.shiranuiPlayed = 0;
			triggerShiranui();
		}
		
		if (DuelistMod.vampiresPlayed >= 10)
		{
			DuelistMod.vampiresPlayed = 0;
			triggerVampire();
		}
		
		if (DuelistMod.vendreadPlayed >= 5)
		{
			DuelistMod.vendreadPlayed = 0;
			triggerVendread();
		}
	}
	
	public static void triggerVampire()
	{
		DuelistCard.siphonAllEnemies(5);
	}
	
	public static void triggerGhostrick(AbstractCard lastPlayed)
	{
		if (AbstractDungeon.player.discardPile.size() > 0)
		{
			AbstractDungeon.actionManager.addToBottom(new PlayRandomFromDiscardAction(1, lastPlayed.uuid));
		}
	}
	
	public static void triggerVendread()
	{
		DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 1));
	}
	
	public static void triggerMayakashi()
	{
		AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
		if (targetMonster != null)
		{
			AbstractPower debuff = DebuffHelper.getRandomDebuff(AbstractDungeon.player, targetMonster, 2);
			DuelistCard.applyPower(debuff, targetMonster);
		}		
	}
	
	public static void triggerShiranui()
	{
		DuelistCard.applyPowerToSelf(new DexterityPower(AbstractDungeon.player, 1));
	}
	
	public static void modifySouls(int add)
	{
		if (add > 0 && AbstractDungeon.player.hasPower(NoSoulGainPower.POWER_ID)) { add = 0; }
		DuelistMod.currentZombieSouls += add;
		if (DuelistMod.currentZombieSouls < 0) { DuelistMod.currentZombieSouls = 0; }
		DuelistCard.handleOnSoulChangeForAllAbstracts(DuelistMod.currentZombieSouls, add);
		Util.log("Modified zombie souls! Added: " + add);
	}
	
	public static void setSouls(int set)
	{
		if (set > DuelistMod.currentZombieSouls && AbstractDungeon.player.hasPower(NoSoulGainPower.POWER_ID))
		{
			return;
		}
		int change = set - DuelistMod.currentZombieSouls;
		modifySouls(change);
	}
	
	public static boolean checkSouls(int lossAmt)
	{
		if (DuelistMod.currentZombieSouls >= lossAmt) { return true; }
		return false;
	}
		
	public static ArrayList<MutateCard> getMutateOptions(int optionsNeeded, ArrayList<AbstractCard> mutatePool)
	{
		ArrayList<MutateCard> mcards = new ArrayList<>();
		ArrayList<MutateCard> options = new ArrayList<>();
		MutateCard dmgA = new MutateDamage(3, CardRarity.COMMON);
		MutateCard dmgB = new MutateDamage(5, CardRarity.UNCOMMON);
		MutateCard dmgC = new MutateDamage(10, CardRarity.RARE);
		MutateCard blkA = new MutateBlock(3, CardRarity.COMMON);
		MutateCard blkB = new MutateBlock(5, CardRarity.UNCOMMON);
		MutateCard blkC = new MutateBlock(10, CardRarity.RARE);
		MutateCard magA = new MutateMagic(1, CardRarity.UNCOMMON);
		MutateCard magB = new MutateMagic(2, CardRarity.RARE);
		MutateCard costA = new MutateCost(1, CardRarity.UNCOMMON);
		MutateCard costB = new MutateCost(2, CardRarity.RARE);
		MutateCard tribA = new MutateTrib(1, CardRarity.COMMON);
		MutateCard tribB = new MutateTrib(2, CardRarity.UNCOMMON);
		MutateCard tribC = new MutateTrib(3, CardRarity.RARE);
		MutateCard dupeA = new MutateDupeA(CardRarity.UNCOMMON);
		MutateCard dupeB = new MutateDupeB(CardRarity.RARE);
		if (dmgA.canSpawnInOptions(mutatePool)) { mcards.add(dmgA); }
		if (dmgB.canSpawnInOptions(mutatePool)) { mcards.add(dmgB); }
		if (dmgC.canSpawnInOptions(mutatePool)) { mcards.add(dmgC); }
		if (blkA.canSpawnInOptions(mutatePool)) { mcards.add(blkA); }
		if (blkB.canSpawnInOptions(mutatePool)) { mcards.add(blkB); }
		if (blkC.canSpawnInOptions(mutatePool)) { mcards.add(blkC); }
		if (magA.canSpawnInOptions(mutatePool)) { mcards.add(magA); }
		if (magB.canSpawnInOptions(mutatePool)) { mcards.add(magB); }
		if (costA.canSpawnInOptions(mutatePool)) { mcards.add(costA); }
		if (costB.canSpawnInOptions(mutatePool)) { mcards.add(costB); }
		if (tribA.canSpawnInOptions(mutatePool)) { mcards.add(tribA); }
		if (tribB.canSpawnInOptions(mutatePool)) { mcards.add(tribB); }
		if (tribC.canSpawnInOptions(mutatePool)) { mcards.add(tribC); }
		mcards.add(dupeA);
		mcards.add(dupeB);
		if (optionsNeeded >= mcards.size()) { return mcards; }
		else
		{
			boolean loopAllowed = true;
			while (options.size() < optionsNeeded && loopAllowed)
			{
				boolean commons = false;
				boolean uncommons = false;
				boolean rares = false;
				
				for (MutateCard m : mcards)
				{
					if (m.rarity.equals(CardRarity.COMMON)) { commons = true; }
					else if (m.rarity.equals(CardRarity.UNCOMMON)) { uncommons = true; }
					else if (m.rarity.equals(CardRarity.RARE)) { rares = true; }
				}
				
				if (!commons && !uncommons && !rares)
				{
					loopAllowed = false;
					Util.log("Mutate Options generation couldn't find anymore options");
				}
				else
				{
					CardRarity roll = getRarity(commons, uncommons, rares);
					if (!roll.equals(CardRarity.SPECIAL)) 
					{ 
						int index = AbstractDungeon.cardRandomRng.random(mcards.size() - 1);
						while (!mcards.get(index).rarity.equals(roll)) 
						{
							index = AbstractDungeon.cardRandomRng.random(mcards.size() - 1);
						}
						
						options.add(mcards.get(index));
						mcards.remove(index);
						Util.log("Mutate Options generation added a new option");
					}
					else { Util.log("Generating mutate effects is returning a Special card rarity for rarityRoll.. bad"); }
				}
			}
		}
		return options;
	}
	
	private static CardRarity getRarity(boolean c, boolean u, boolean r)
	{
		if (!c && !u && r) { return CardRarity.RARE; }
		else if (!c && u && !r) { return CardRarity.UNCOMMON; }
		else if (c && !u && !r) { return CardRarity.COMMON; }
		else if (c && u && !r) 
		{ 
			if (AbstractDungeon.cardRandomRng.random(1, 100) < 40) { return CardRarity.UNCOMMON; }
			else { return CardRarity.COMMON; }
		}
		else if (c && !u && r)
		{
			if (AbstractDungeon.cardRandomRng.random(1, 100) < 10) { return CardRarity.RARE; }
			else { return CardRarity.COMMON; }
		}
		else if (!c && u && r)
		{
			if (AbstractDungeon.cardRandomRng.random(1, 100) < 10) { return CardRarity.RARE; }
			else { return CardRarity.UNCOMMON; }
		}
		else if (c && u && r)
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, 100);
			if (roll < 10) { return CardRarity.RARE; }
			else if (roll < 40) { return CardRarity.UNCOMMON; }
			else { return CardRarity.UNCOMMON; }
		}
		else if (!c && !u && !r)
		{
			return CardRarity.SPECIAL;
		}
		Util.log("Somehow this Util.getRarity(bool c, bool u, bool r) is returning not from any of the conditional checks.. logic?");
		return CardRarity.SPECIAL;
	}
	
	public static int getChallengeLevel()
	{
		if (DuelistMod.playingChallenge) { return DuelistMod.challengeLevel; }
		else { return -1; }
	}

	public static boolean isMillenniumItem(AbstractRelic r, boolean includePuzzle)
	{
		ArrayList<String> items = new ArrayList<String>();
		items.add(new MillenniumCoin().name);
		items.add(new MillenniumRing().name);
		items.add(new MillenniumRod().name);
		items.add(new MillenniumKey().name);
		items.add(new MillenniumEye().name);
		items.add(new ResummonBranch().name);
		items.add(new MillenniumScale().name);
		items.add(new MillenniumNecklace().name);
		items.add(new MillenniumToken().name);
		items.add(new MillenniumSymbol().name);
		items.add(new MillenniumPeriapt().name);
		items.add(new MillenniumPrayerbook().name);
		items.add(new MillenniumArmor().name);
		if (includePuzzle) { items.add(new MillenniumPuzzle().name); }
		if (items.contains(r.name)) { return true; }
		else { return false; }		
	}
	
	public static ArrayList<AbstractRelic> getMillenniumItemsForEvent(boolean includePuzzle)
	{
		ArrayList<AbstractRelic> items = new ArrayList<AbstractRelic>();
		items.add(new MillenniumCoin());
		items.add(new MillenniumRing());
		items.add(new MillenniumRod());
		items.add(new MillenniumSymbol());
		items.add(new ResummonBranch());
		items.add(new MillenniumScale());
		items.add(new MillenniumNecklace());
		items.add(new MillenniumPeriapt());
		items.add(new MillenniumPrayerbook());
		items.add(new MillenniumArmor());
		if (includePuzzle) { items.add(new MillenniumPuzzle()); }
		//Collections.shuffle(items);
		return items;
	}

	public static AbstractCard getRandomBambooSword()
	{
		ArrayList<AbstractCard> swords = new ArrayList<AbstractCard>();
		swords.add(new BambooSwordBroken());
		swords.add(new BambooSwordBurning());
		swords.add(new BambooSwordCursed());
		swords.add(new BambooSwordGolden());
		swords.add(new BambooSwordSoul());
		return swords.get(AbstractDungeon.cardRandomRng.random(swords.size() - 1));
	}
	
	public static AbstractCard getRandomBambooSword(boolean upgraded)
	{
		ArrayList<AbstractCard> swords = new ArrayList<AbstractCard>();
		swords.add(new BambooSwordBroken());
		swords.add(new BambooSwordBurning());
		swords.add(new BambooSwordCursed());
		swords.add(new BambooSwordGolden());
		swords.add(new BambooSwordSoul());
		AbstractCard sword = swords.get(AbstractDungeon.cardRandomRng.random(swords.size() - 1));
		if (upgraded && sword.canUpgrade()) { sword.upgrade(); }
		return sword;
	}
	
	public static AbstractCard getSpecialGreedCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialGreedCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}
	
	public static ArrayList<DuelistCard> getSpecialGreedCardsForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<DuelistCard>();	
		specialCards.add(new AncientGearBoxNamelessGreed());		
		specialCards.add(new BerserkerCrushNamelessGreed());		
		specialCards.add(new GracefulCharityNamelessGreed());	
		specialCards.add(new MagnumShieldNamelessGreed());	
		return specialCards;
	}
	
	public static AbstractCard getSpecialPowerCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialPowerCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}
	
	public static ArrayList<DuelistCard> getSpecialPowerCardsForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<DuelistCard>();
		specialCards.add(new AllyJusticeNamelessPower());		
		specialCards.add(new AssaultArmorNamelessPower());	
		specialCards.add(new BerserkerCrushNamelessPower());		
		specialCards.add(new ForbiddenLanceNamelessPower());	
		specialCards.add(new ForbiddenLanceNamelessPower());
		specialCards.add(new KamionTimelordNamelessPower());	
		specialCards.add(new MaskedDragonNamelessPower());		
		specialCards.add(new SpiralSpearStrikeNamelessPower());	
		return specialCards;
	}
	
	public static AbstractCard getSpecialWarCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialWarCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}
	
	public static ArrayList<DuelistCard> getSpecialWarCardsForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<DuelistCard>();
		specialCards.add(new AllyJusticeNamelessWar());		
		specialCards.add(new AssaultArmorNamelessWar());	
		specialCards.add(new BerserkerCrushNamelessWar());		
		specialCards.add(new ForbiddenLanceNamelessWar());
		specialCards.add(new ForbiddenLanceNamelessWar());	
		specialCards.add(new MaskedDragonNamelessWar());	
		specialCards.add(new SpiralSpearStrikeNamelessWar());
		specialCards.add(new FortressWarriorNamelessWar());	
		specialCards.add(new BlueEyesNamelessWar());	
		specialCards.add(new Gandora());	
		return specialCards;
	}
	
	public static AbstractCard getRandomRarePowerForNamelessTomb()
	{
		ArrayList<AbstractCard> rarePow = new ArrayList<AbstractCard>();
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (c.rarity.equals(CardRarity.RARE) && c.type.equals(CardType.POWER) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL))
			{
				while (c.canUpgrade()) { c.upgrade(); }
				rarePow.add(c.makeStatEquivalentCopy());
			}
		}
		
		if (rarePow.size() > 0) { return rarePow.get(AbstractDungeon.cardRandomRng.random(rarePow.size() - 1)); }
		else { return new Token(); }
	}

	public static AbstractCard getSpecialCardForMiracleDescent()
	{
		ArrayList<DuelistCard> specialCards = getSpecialCardsForMiracleDescent();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}
	
	public static AbstractCard getSpecialMagicCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialMagicCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}
	
	public static ArrayList<DuelistCard> getSpecialCardsForMiracleDescent()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<DuelistCard>();
		specialCards.add(new MDSpecialCardA());	
		specialCards.add(new MDSpecialCardB());	
		specialCards.add(new MDSpecialCardC());	
		specialCards.add(new MDSpecialCardD());	
		specialCards.add(new MDSpecialCardE());	
		return specialCards;
	}
	
	public static AbstractCard getSpecialSparksCard()
	{
		ArrayList<DuelistCard> specialCards = getSpecialSparks();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
	}
	
	
	public static ArrayList<DuelistCard> getSpecialSparks()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<DuelistCard>();
		specialCards.add(new GoldenSparks());	
		specialCards.add(new BloodSparks());	
		specialCards.add(new MagicSparks());	
		specialCards.add(new StormSparks());	
		specialCards.add(new DarkSparks());	
		return specialCards;
	}
	
	public static AbstractCard getAnyNamelessTombCard(boolean rng)
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<DuelistCard>();
		specialCards.addAll(getSpecialMagicCardsForNamelessTomb());
		specialCards.addAll(getSpecialGreedCardsForNamelessTomb());
		specialCards.addAll(getSpecialPowerCardsForNamelessTomb());
		specialCards.addAll(getSpecialWarCardsForNamelessTomb());
		if (rng) { return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1)); }
		else { return specialCards.get(ThreadLocalRandom.current().nextInt(specialCards.size())); }
	}
	
	public static ArrayList<DuelistCard> getSpecialMagicCardsForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = new ArrayList<DuelistCard>();
		specialCards.add(new AllyJusticeNameless());
		specialCards.add(new AncientGearBoxNameless());
		specialCards.add(new AssaultArmorNameless());
		specialCards.add(new AxeDespairNameless());
		specialCards.add(new BerserkerCrushNameless());
		specialCards.add(new BigWhaleNameless());
		specialCards.add(new DarkworldThornsNameless());
		specialCards.add(new ForbiddenLanceNameless());
		specialCards.add(new GoldenApplesNameless());
		specialCards.add(new GracefulCharityNameless());
		specialCards.add(new GravityLashNameless());
		specialCards.add(new GridRodNameless());
		specialCards.add(new HappyLoverNameless());
		specialCards.add(new ImperialOrderNameless());
		specialCards.add(new InsectQueenNameless());
		specialCards.add(new KamionTimelordNameless());
		specialCards.add(new MagnumShieldNameless());
		//specialCards.add(new MaskedDragonNameless());
		specialCards.add(new ObeliskTormentorNameless());
		specialCards.add(new OilmanNameless());
		specialCards.add(new PotDualityNameless());
		specialCards.add(new PotGenerosityNameless());
		specialCards.add(new PredaplantSarraceniantNameless());
		specialCards.add(new SpiralSpearStrikeNameless());
		specialCards.add(new YamiFormNameless());	
		specialCards.add(new HourglassLife());
		if (Util.deckIs("Naturia Deck")) 
		{ 
			specialCards.add(new NaturalDisasterNameless()); 
			specialCards.add(new NaturalDisasterNameless());
		}
		return specialCards;
	}
	
	public static ArrayList<DuelistCard> getStanceChoices(boolean allowMeditative, boolean allowDivinity, boolean allowChaotic, boolean allowDuelist, boolean allowBaseGame)
	{
		ArrayList<DuelistCard> stances = new ArrayList<DuelistCard>();
		if (allowDuelist)
		{
			stances.add(new ChooseSpectralCard());
			stances.add(new ChooseSamuraiCard());
			stances.add(new ChooseGuardedCard());
			stances.add(new ChooseForsakenCard());
			stances.add(new ChooseEntrenchedCard());
			stances.add(new ChooseNimbleCard());
			if (allowMeditative) { stances.add(new ChooseMeditativeCard()); }
			if (allowChaotic) { stances.add(new ChooseChaoticCard()); }
		}
		if (allowBaseGame)
		{
			stances.add(new ChooseWrathCard());
			stances.add(new ChooseCalmCard());			
			if (allowDivinity) { stances.add(new ChooseDivinityCard()); }
		}
		return stances;
	}
	
	public static ArrayList<DuelistCard> getStanceChoices(boolean allowMeditative, boolean allowDivinity, boolean allowChaotic)
	{
		return getStanceChoices(allowMeditative, allowDivinity, allowChaotic, true, true);
	}
	
	public static ArrayList<DuelistCard> getStanceChoices(boolean allowDivinity, boolean allowChaotic)
	{
		return getStanceChoices(true, allowDivinity, allowChaotic, true, true);
	}
	
	public static ArrayList<DuelistCard> getStanceChoices()
	{
		return getStanceChoices(true, false, false, true, true);
	}

	public static void genesisDragonHelper()
	{
		ArrayList<AbstractCard> genesisDragsToAdd = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof GenesisDragon)
			{
				int genesisRoll = AbstractDungeon.cardRandomRng.random(1, 10);
				//int genesisRoll = 1;
				if (genesisRoll < 4 && c.upgraded) { genesisDragsToAdd.add(c.makeStatEquivalentCopy()); }
				else if (genesisRoll == 1) { genesisDragsToAdd.add(c.makeStatEquivalentCopy()); }
			}
		}
		if (genesisDragsToAdd.size() > 0) { AbstractDungeon.player.masterDeck.group.addAll(genesisDragsToAdd); }
	}
	
	public static void unlockAllRelics(ArrayList<AbstractRelic> relics)
	{
		for (AbstractRelic r : relics) { UnlockTracker.markRelicAsSeen(r.relicId); }
		/*UnlockTracker.markRelicAsSeen(MillenniumPuzzle.ID);
		UnlockTracker.markRelicAsSeen(MillenniumRing.ID);
		UnlockTracker.markRelicAsSeen(MillenniumKey.ID);
		UnlockTracker.markRelicAsSeen(MillenniumRod.ID);
		UnlockTracker.markRelicAsSeen(MillenniumCoin.ID);
		UnlockTracker.markRelicAsSeen(ResummonBranch.ID);
		UnlockTracker.markRelicAsSeen(AeroRelic.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicA.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicB.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicC.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicD.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicE.ID);
		UnlockTracker.markRelicAsSeen(InversionRelic.ID);
		UnlockTracker.markRelicAsSeen(InversionEvokeRelic.ID);
		UnlockTracker.markRelicAsSeen(InsectRelic.ID);
		UnlockTracker.markRelicAsSeen(NaturiaRelic.ID);
		UnlockTracker.markRelicAsSeen(MachineToken.ID);
		UnlockTracker.markRelicAsSeen(StoneExxod.ID);
		UnlockTracker.markRelicAsSeen(GiftAnubis.ID);
		UnlockTracker.markRelicAsSeen(DragonRelic.ID);
		UnlockTracker.markRelicAsSeen(SummonAnchor.ID);
		UnlockTracker.markRelicAsSeen(SpellcasterToken.ID);
		UnlockTracker.markRelicAsSeen(SpellcasterOrb.ID);
		UnlockTracker.markRelicAsSeen(AquaRelic.ID);
		UnlockTracker.markRelicAsSeen(AquaRelicB.ID);
		UnlockTracker.markRelicAsSeen(NatureRelic.ID);
		UnlockTracker.markRelicAsSeen(ZombieRelic.ID);
		UnlockTracker.markRelicAsSeen(DragonRelicB.ID);
		UnlockTracker.markRelicAsSeen(ShopToken.ID);
		UnlockTracker.markRelicAsSeen(MillenniumScale.ID);
		UnlockTracker.markRelicAsSeen(MachineTokenB.ID);
		UnlockTracker.markRelicAsSeen(MillenniumNecklace.ID);
		UnlockTracker.markRelicAsSeen(MillenniumToken.ID);
		UnlockTracker.markRelicAsSeen(DragonRelicC.ID);
		//UnlockTracker.markRelicAsSeen(RandomTributeMonsterRelic.ID);
		UnlockTracker.markRelicAsSeen(YugiMirror.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicF.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicG.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicH.ID);
		UnlockTracker.markRelicAsSeen(TributeEggRelic.ID);
		UnlockTracker.markRelicAsSeen(ZombieResummonBuffRelic.ID);
		UnlockTracker.markRelicAsSeen(ToonRelic.ID);
		UnlockTracker.markRelicAsSeen(HauntedRelic.ID);
		UnlockTracker.markRelicAsSeen(SpellcasterStone.ID);
		UnlockTracker.markRelicAsSeen(OrbCardRelic.ID);		
		UnlockTracker.markRelicAsSeen(BoosterAlwaysBonusRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterAlwaysSillyRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterBetterBoostersRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterExtraAllRaresRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterBonusPackIncreaseRelic.ID);
		UnlockTracker.markRelicAsSeen(BoosterPackEggRelic.ID);
		UnlockTracker.markRelicAsSeen(SpellMaxHPRelic.ID);
		UnlockTracker.markRelicAsSeen(WhiteBowlRelic.ID);
		UnlockTracker.markRelicAsSeen(SummonAnchorRare.ID);
		UnlockTracker.markRelicAsSeen(GamblerChip.ID);
		UnlockTracker.markRelicAsSeen(MerchantPendant.ID);
		UnlockTracker.markRelicAsSeen(MerchantSword.ID);
		UnlockTracker.markRelicAsSeen(MerchantTalisman.ID);
		UnlockTracker.markRelicAsSeen(MerchantRugbox.ID);
		UnlockTracker.markRelicAsSeen(Monsterbox.ID);
		//UnlockTracker.markRelicAsSeen(Spellbox.ID);
		//UnlockTracker.markRelicAsSeen(Trapbox.ID);
		UnlockTracker.markRelicAsSeen(Spellheart.ID);
		UnlockTracker.markRelicAsSeen(TrapVortex.ID);
		UnlockTracker.markRelicAsSeen(MonsterEggRelic.ID);
		UnlockTracker.markRelicAsSeen(MagnetRelic.ID);
		UnlockTracker.markRelicAsSeen(MerchantNecklace.ID);
		UnlockTracker.markRelicAsSeen(KaibaToken.ID);
		UnlockTracker.markRelicAsSeen(AknamkanonsEssence.ID);
		UnlockTracker.markRelicAsSeen(MarkExxod.ID);
		UnlockTracker.markRelicAsSeen(DuelistCoin.ID);
		UnlockTracker.markRelicAsSeen(MetronomeRelicA.ID);
		UnlockTracker.markRelicAsSeen(MetronomeRelicB.ID);
		UnlockTracker.markRelicAsSeen(MetronomeRelicC.ID);
		UnlockTracker.markRelicAsSeen(MetronomeRelicD.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicI.ID);
		UnlockTracker.markRelicAsSeen(NamelessPowerRelicA.ID);
		UnlockTracker.markRelicAsSeen(NamelessPowerRelicB.ID);
		UnlockTracker.markRelicAsSeen(NamelessGreedRelic.ID);
		UnlockTracker.markRelicAsSeen(NamelessHungerRelic.ID);
		UnlockTracker.markRelicAsSeen(NamelessWarRelicA.ID);
		UnlockTracker.markRelicAsSeen(NamelessWarRelicB.ID);
		UnlockTracker.markRelicAsSeen(NamelessWarRelicC.ID);
		UnlockTracker.markRelicAsSeen(Leafblower.ID);
		UnlockTracker.markRelicAsSeen(NatureOrb.ID);
		UnlockTracker.markRelicAsSeen(MarkOfNature.ID);
		UnlockTracker.markRelicAsSeen(CursedHealer.ID);
		UnlockTracker.markRelicAsSeen(MillenniumSymbol.ID);
		UnlockTracker.markRelicAsSeen(DragonBurnRelic.ID);
		UnlockTracker.markRelicAsSeen(GoldenScale.ID);
		UnlockTracker.markRelicAsSeen(ConfusionGoldRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolAddRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolMinusRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolSaveRelic.ID);
		UnlockTracker.markRelicAsSeen(CardPoolOptionsRelic.ID);*/
	}
	
	public static void setupDuelistTombRelics()
	{
		DuelistMod.duelistRelicsForTombEvent.add(new AeroRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicA());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicB());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicC());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicD());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicE());
		DuelistMod.duelistRelicsForTombEvent.add(new InversionRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new InversionEvokeRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new InsectRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new NaturiaRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new MachineToken());
		DuelistMod.duelistRelicsForTombEvent.add(new MachineOrb());
		DuelistMod.duelistRelicsForTombEvent.add(new Wirebundle());
		DuelistMod.duelistRelicsForTombEvent.add(new Fluxrod());
		DuelistMod.duelistRelicsForTombEvent.add(new DragonRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new SummonAnchor());
		DuelistMod.duelistRelicsForTombEvent.add(new SpellcasterToken());
		DuelistMod.duelistRelicsForTombEvent.add(new SpellcasterOrb());
		DuelistMod.duelistRelicsForTombEvent.add(new AquaRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new AquaRelicB());
		DuelistMod.duelistRelicsForTombEvent.add(new NatureRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new ZombieRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new DragonRelicB());
		DuelistMod.duelistRelicsForTombEvent.add(new ShopToken());
		DuelistMod.duelistRelicsForTombEvent.add(new StoneExxod());	
		DuelistMod.duelistRelicsForTombEvent.add(new DragonRelicC());
		DuelistMod.duelistRelicsForTombEvent.add(new YugiMirror());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicF());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicG());
		DuelistMod.duelistRelicsForTombEvent.add(new CardRewardRelicH());
		DuelistMod.duelistRelicsForTombEvent.add(new TributeEggRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new ZombieResummonBuffRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new ToonRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new SpellcasterStone());
		DuelistMod.duelistRelicsForTombEvent.add(new OrbCardRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterBetterBoostersRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterPackHealer());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterExtraAllRaresRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterPackMonsterEgg());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterPackSpellEgg());
		DuelistMod.duelistRelicsForTombEvent.add(new BoosterPackTrapEgg());
		DuelistMod.duelistRelicsForTombEvent.add(new SpellMaxHPRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new WhiteBowlRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new SummonAnchorRare());
		DuelistMod.duelistRelicsForTombEvent.add(new MonsterEggRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new MerchantNecklace());
		DuelistMod.duelistRelicsForTombEvent.add(new KaibaToken());
		DuelistMod.duelistRelicsForTombEvent.add(new AknamkanonsEssence());
		DuelistMod.duelistRelicsForTombEvent.add(new MetronomeRelicA());
		DuelistMod.duelistRelicsForTombEvent.add(new MetronomeRelicB());
		DuelistMod.duelistRelicsForTombEvent.add(new MetronomeRelicC());
		DuelistMod.duelistRelicsForTombEvent.add(new MetronomeRelicD());
		DuelistMod.duelistRelicsForTombEvent.add(new Leafblower());
		DuelistMod.duelistRelicsForTombEvent.add(new NatureOrb());
		DuelistMod.duelistRelicsForTombEvent.add(new MarkOfNature());
		DuelistMod.duelistRelicsForTombEvent.add(new MillenniumSymbol());
		DuelistMod.duelistRelicsForTombEvent.add(new DragonBurnRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new GoldenScale());
		DuelistMod.duelistRelicsForTombEvent.add(new TokenUpgradeRelic());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistPrismaticShard());
		DuelistMod.duelistRelicsForTombEvent.add(new BlessingAnubis());
		DuelistMod.duelistRelicsForTombEvent.add(new MillenniumPeriapt());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistTeaSet());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistOrichalcum());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistLetterOpener());
		DuelistMod.duelistRelicsForTombEvent.add(new DuelistUrn());
		DuelistMod.duelistRelicsForTombEvent.add(new MillenniumPrayerbook());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageA());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageB());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageC());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageD());
		DuelistMod.duelistRelicsForTombEvent.add(new PrayerPageE());
		DuelistMod.duelistRelicsForTombEvent.add(new MillenniumArmor());
		DuelistMod.duelistRelicsForTombEvent.add(new TokenArmor());
		DuelistMod.duelistRelicsForTombEvent.add(new BrazeToken());
		DuelistMod.duelistRelicsForTombEvent.add(new RouletteWheel());
		DuelistMod.duelistRelicsForTombEvent.add(new EngineeringToken());
		DuelistMod.duelistRelicsForTombEvent.add(new Bombchain());
		DuelistMod.duelistRelicsForTombEvent.add(new LoadedDice());
		DuelistMod.duelistRelicsForTombEvent.add(new TokenfestPendant());
		DuelistMod.duelistRelicsForTombEvent.add(new ZoneToken());
		DuelistMod.duelistRelicsForTombEvent.add(new SolderToken());
		DuelistMod.duelistRelicsForTombEvent.add(new ElectricToken());
		DuelistMod.duelistRelicsForTombEvent.add(new SailingToken());
		DuelistMod.duelistRelicsForTombEvent.add(new Flowstate());
		DuelistMod.duelistRelicsForTombEvent.add(new NileToken());
		DuelistMod.duelistRelicsForTombEvent.add(new FlowToken());
		DuelistMod.duelistRelicsForTombEvent.add(new WavemastersBlessing());
		DuelistMod.duelistRelicsForTombEvent.add(new GoldenSail());
		DuelistMod.duelistRelicsForTombEvent.add(new Splashbox());
		DuelistMod.duelistRelicsForTombEvent.add(new CoralToken());
		
		if (DuelistMod.debug)
		{
			ArrayList<AbstractRelic> comm = new ArrayList<>();
			ArrayList<AbstractRelic> uncomm = new ArrayList<>();
			ArrayList<AbstractRelic> rare = new ArrayList<>();
			ArrayList<AbstractRelic> shop = new ArrayList<>();
			ArrayList<AbstractRelic> boss = new ArrayList<>();
			ArrayList<AbstractRelic> special = new ArrayList<>();
			ArrayList<AbstractRelic> other = new ArrayList<>();
			for (AbstractRelic r : DuelistMod.duelistRelicsForTombEvent)
			{
				if (r.tier.equals(RelicTier.COMMON)) { comm.add(r); }
				else if (r.tier.equals(RelicTier.UNCOMMON)) { uncomm.add(r); }
				else if (r.tier.equals(RelicTier.RARE)) { rare.add(r); }
				else if (r.tier.equals(RelicTier.SHOP)) { shop.add(r); }
				else if (r.tier.equals(RelicTier.BOSS)) { boss.add(r); }
				else if (r.tier.equals(RelicTier.SPECIAL)) { special.add(r); }
				else  { other.add(r); }
			}
			
			Util.log("DUELIST TOMB EVENT DEBUG LOGGER");
			Util.log("Common Relics in Tomb Pool: " + comm.size());
			Util.log("Uncommon Relics in Tomb Pool: " + uncomm.size());
			Util.log("Rare Relics in Tomb Pool: " + rare.size());
			Util.log("Shop Relics in Tomb Pool: " + shop.size());
			Util.log("Boss Relics in Tomb Pool: " + boss.size());
			Util.log("Special Relics in Tomb Pool: " + special.size());
			Util.log("Other? Relics in Tomb Pool: " + other.size());
			
			if (comm.size() > 0)
			{
				Util.log("--- COMMON ---");
				for (AbstractRelic r : comm) { Util.log(r.name); }
			}
			
			if (uncomm.size() > 0)
			{
				Util.log("--- UNCOMMON ---");
				for (AbstractRelic r : uncomm) { Util.log(r.name); }
			}
			
			if (rare.size() > 0)
			{
				Util.log("--- RARE ---");
				for (AbstractRelic r : rare) { Util.log(r.name); }
			}
			
			if (shop.size() > 0)
			{
				Util.log("--- SHOP ---");
				for (AbstractRelic r : shop) { Util.log(r.name); }
			}

			if (boss.size() > 0)
			{
				Util.log("--- BOSS ---");
				for (AbstractRelic r : boss) { Util.log(r.name); }
			}
			
			if (special.size() > 0)
			{
				Util.log("--- SPECIAL ---");
				for (AbstractRelic r : special) { Util.log(r.name); }
			}
			
			if (other.size() > 0)
			{
				Util.log("--- OTHER? ---");
				for (AbstractRelic r : other) { Util.log(r.name); }
			}
		}
	}
	
	public static ArrayList<AbstractCard> allHolidayCardsNoDateCheck()
	{
		ArrayList<AbstractCard> holidayCards = new ArrayList<>();
		holidayCards.add(new Hallohallo());
		holidayCards.add(new PumpkinCarriage());
		holidayCards.add(new HalloweenManor());
		holidayCards.add(new BalloonParty());	
		holidayCards.add(new CocoonParty());
		holidayCards.add(new DinnerParty());
		holidayCards.add(new ElephantGift());
		holidayCards.add(new FairyGift());
		holidayCards.add(new GiftCard());
		holidayCards.add(new HeroicGift());
		holidayCards.add(new WeedOut());
		return holidayCards;
	}
	
	public static ArrayList<AbstractCard> holidayCardRandomizedList()
	{
		ArrayList<AbstractCard> holidayCards = new ArrayList<>();
		if (Util.halloweenCheck()) 
		{
			holidayCards.add(new Hallohallo());
			holidayCards.add(new PumpkinCarriage());
			holidayCards.add(new HalloweenManor());
			DuelistMod.addedHalloweenCards = true;
		}
		else { DuelistMod.addedHalloweenCards = false; }
		if (Util.birthdayCheck())
		{
			holidayCards.add(new BalloonParty());	
			holidayCards.add(new CocoonParty());
			holidayCards.add(new DinnerParty());			
			DuelistMod.addedBirthdayCards = true;
		}
		else { DuelistMod.addedBirthdayCards = false; }
		if (Util.xmasCheck())
		{
			holidayCards.add(new ElephantGift());
			holidayCards.add(new FairyGift());
			holidayCards.add(new GiftCard());
			holidayCards.add(new HeroicGift());
			DuelistMod.addedXmasCards = true;
		}
		else { DuelistMod.addedXmasCards = false; }		
		if (Util.weedCheck())
		{
			holidayCards.add(new WeedOut());
			DuelistMod.addedWeedCards = true;
		}
		else { DuelistMod.addedWeedCards = false; }
		Collections.shuffle(holidayCards);
		return holidayCards;
	}
	
	public static boolean weedCheck()
	{
		boolean isXmas = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2019, 3, 20);
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isXmas = true; }
		if (isXmas) { Util.log("Duelistmod is detecting 420 dude!"); }
		else 
		{ 
			Util.log("420 Check : cal1.dayOfMonth=" + cal1.get(Calendar.DAY_OF_MONTH) + ", and cal2.dayOfMonth=" + cal2.get(Calendar.DAY_OF_MONTH));
			Util.log("420 Check : cal1.Month=" + cal1.get(Calendar.MONTH) + ", and cal2.Month=" + cal2.get(Calendar.MONTH));
		}
		return isXmas;
	}
	
	public static boolean xmasCheck()
	{
		boolean isXmas = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2019, 11, 25);
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isXmas = true; }
		if (isXmas) { Util.log("Duelistmod is detecting Christmas!"); }
		else 
		{ 
			Util.log("Christmas Check : cal1.dayOfMonth=" + cal1.get(Calendar.DAY_OF_MONTH) + ", and cal2.dayOfMonth=" + cal2.get(Calendar.DAY_OF_MONTH));
			Util.log("Christmas Check : cal1.Month=" + cal1.get(Calendar.MONTH) + ", and cal2.Month=" + cal2.get(Calendar.MONTH));
		}
		return isXmas;
	}
	
	public static boolean halloweenCheck()
	{
		boolean isHalloween = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2019, 9, 31);
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isHalloween = true; }
		if (isHalloween) { Util.log("Duelistmod is detecting Halloween!"); }
		else 
		{ 
			Util.log("Halloween Check : cal1.dayOfMonth=" + cal1.get(Calendar.DAY_OF_MONTH) + ", and cal2.dayOfMonth=" + cal2.get(Calendar.DAY_OF_MONTH));
			Util.log("Halloween Check : cal1.Month=" + cal1.get(Calendar.MONTH) + ", and cal2.Month=" + cal2.get(Calendar.MONTH));
		}
		return isHalloween;
	}
	
	public static int whichBirthday()
	{
		if (!birthdayCheck()) { return -1; }
		else
		{
			boolean isNyoxideBirthday = false;
			boolean playerBirthday = false;
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			Calendar cal3 = Calendar.getInstance();
			cal2.set(2019, 9, 3);
			cal3.set(2019, DuelistMod.birthdayMonth - 1, DuelistMod.birthdayDay);
			if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isNyoxideBirthday = true; }
			else if (cal1.get(Calendar.DAY_OF_MONTH) == cal3.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal3.get(Calendar.MONTH)) { playerBirthday = true; }
			if (isNyoxideBirthday) { return 1; }
			else if (playerBirthday && !DuelistMod.neverChangedBirthday) { return 2; }
			else { return 3; }
		}
	}
	
	public static boolean birthdayCheck()
	{
		boolean isBirthday = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		Calendar cal4 = Calendar.getInstance();
		cal2.set(2019, 9, 3);
		cal3.set(2019, 2, 4);
		cal4.set(2019, DuelistMod.birthdayMonth - 1, DuelistMod.birthdayDay);
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) { isBirthday = true; }
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal3.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal3.get(Calendar.MONTH)) { isBirthday = true; }
		if (cal1.get(Calendar.DAY_OF_MONTH) == cal4.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.MONTH) == cal4.get(Calendar.MONTH)) { isBirthday = true; }
		if (isBirthday) { Util.log("Duelistmod is detecting Birthday!"); }
		else 
		{ 
			Util.log("Birthday Check : cal1.dayOfMonth=" + cal1.get(Calendar.DAY_OF_MONTH) + ", and cal2.dayOfMonth=" + cal2.get(Calendar.DAY_OF_MONTH));
			Util.log("Birthday Check : cal1.dayOfMonth=" + cal1.get(Calendar.DAY_OF_MONTH) + ", and cal3.dayOfMonth=" + cal3.get(Calendar.DAY_OF_MONTH));
			Util.log("Birthday Check : cal1.dayOfMonth=" + cal1.get(Calendar.DAY_OF_MONTH) + ", and cal4.dayOfMonth=" + cal4.get(Calendar.DAY_OF_MONTH));
			Util.log("Birthday Check : cal1.Month=" + cal1.get(Calendar.MONTH) + ", and cal2.Month=" + cal2.get(Calendar.MONTH));
			Util.log("Birthday Check : cal1.Month=" + cal1.get(Calendar.MONTH) + ", and cal3.Month=" + cal3.get(Calendar.MONTH));
			Util.log("Birthday Check : cal1.Month=" + cal1.get(Calendar.MONTH) + ", and cal4.Month=" + cal3.get(Calendar.MONTH));
		}
		return isBirthday;
	}
	
	public static String getChallengeDifficultyDesc()
	{
		if (Util.getChallengeLevel() == 0) { return "#bMillennium #bPuzzle: Typed tokens become Puzzle Tokens."; }
		else if (Util.getChallengeLevel() == 1) { return "#bMillennium #bPuzzle: Deck effects are weakened or reduced."; }
		else if (Util.getChallengeLevel() == 2) { return "Start each combat with #b4 #yMax #ySummons."; }
		else if (Util.getChallengeLevel() == 3) { return "All Elites start combat with a random #yBuff."; }
		else if (Util.getChallengeLevel() == 4) 
		{ 
			if (Util.deckIs("Standard Deck")) { return "Standard: #b50% chance to randomize the cost of Spells when drawn."; }
			else if (Util.deckIs("Dragon Deck")) { return "Dragon: [PLACEHOLDER]"; }
			else if (Util.deckIs("Naturia Deck")) { return "Naturia: resistance to #yVines is increased."; }
			else if (Util.deckIs("Spellcaster Deck")) { return "Spellcaster: start combat with #b2 orb slots."; }
			else if (Util.deckIs("Toon Deck")) { return "Toon: [PLACEHOLDER]"; }
			else if (Util.deckIs("Zombie Deck")) { return "Zombie: start each combat with restricted #yResummoning."; }
			else if (Util.deckIs("Aqua Deck")) { return "Aqua: #yAqua tribute synergy effect only triggers #b50% of the time."; }
			else if (Util.deckIs("Fiend Deck")) { return "Fiend: When triggered, the #yFiend tribute synergy effect increases the cost of all cards in discard by #b1 for the turn."; }
			else if (Util.deckIs("Machine Deck")) { return "Machine: [PLACEHOLDER]"; }
			else if (Util.deckIs("Warrior Deck")) { return "Warrior: [PLACEHOLDER]"; }
			else if (Util.deckIs("Insect Deck")) { return "Insect: [PLACEHOLDER]"; }
			else if (Util.deckIs("Plant Deck")) { return "Plant: [PLACEHOLDER]"; }
			else if (Util.deckIs("Megatype Deck")) { return "Megatype: [PLACEHOLDER]"; }
			else if (Util.deckIs("Increment Deck")) { return "Increment: Whenever you #yIncrement, take #b1 damage."; }
			else if (Util.deckIs("Creator Deck")) { return "Creator: [PLACEHOLDER]"; }
			else if (Util.deckIs("Exodia Deck")) { return "Exodia: [PLACEHOLDER]"; }
			else if (Util.deckIs("Ojama Deck")) { return "Ojama: [PLACEHOLDER]"; }
			else if (Util.deckIs("Giant Deck")) { return "Giant: [PLACEHOLDER]"; }
			else if (Util.deckIs("Ascended I")) { return "Ascended I: [PLACEHOLDER]"; }
			else if (Util.deckIs("Ascended II")) { return "Ascended II: [PLACEHOLDER]"; }
			else if (Util.deckIs("Ascended III")) { return "Ascended III: [PLACEHOLDER]"; }
			else if (Util.deckIs("Pharaoh I")) { return "Pharaoh I: [PLACEHOLDER]"; }
			else if (Util.deckIs("Pharaoh II")) { return "Pharaoh II: [PLACEHOLDER]"; }
			else if (Util.deckIs("Pharaoh III")) { return "Pharaoh III: [PLACEHOLDER]"; }
			else if (Util.deckIs("Pharaoh IV")) { return "Pharaoh IV: [PLACEHOLDER]"; }
			else if (Util.deckIs("Pharaoh V")) { return "Pharaoh V: [PLACEHOLDER]"; }
			else if (Util.deckIs("Random Deck (Small)")) { return "Random (Small): [PLACEHOLDER]"; }
			else if (Util.deckIs("Random Deck (Big)")) { return "Random (Big): [PLACEHOLDER]"; }
			else if (Util.deckIs("Upgrade Deck")) { return "Upgrade: [PLACEHOLDER]"; }
			else if (Util.deckIs("Metronome Deck")) { return "Metronome: [PLACEHOLDER]"; }
			else { return "???"; }
		}
		else if (Util.getChallengeLevel() == 5) { return "Start each run with #b0 #yGold."; }
		else if (Util.getChallengeLevel() == 6) { return "At the start of each of your turns, all enemies have a chance to gain #yBlock."; }
		else if (Util.getChallengeLevel() == 7) { return "#bMillennium #bPuzzle: NL No deck effects."; }
		else if (Util.getChallengeLevel() == 8) { return "Whenever you open a non-Boss chest, lose all of your Potions."; }
		else if (Util.getChallengeLevel() == 9) { return "#bMillennium #bPuzzle: NL Puzzle Tokens become #rExplosive Tokens."; }
		else if (Util.getChallengeLevel() == 10) { return "Start each run #rCursed."; }
		else if (Util.getChallengeLevel() == 11) { return "#b1% chance to become #rFrozen at the start of each turn."; }
		else if (Util.getChallengeLevel() == 12) { return "Start each combat with #b3 #rBurning."; }
		else if (Util.getChallengeLevel() == 13) { return "#y? rooms are more likely to contain fights."; }
		else if (Util.getChallengeLevel() == 14) { return "Booster pack rewards contain #b1 less card."; }
		else if (Util.getChallengeLevel() == 15) { return "Whenever you #ySmith, lose #b35 #yGold."; }
		else if (Util.getChallengeLevel() == 16) { return "#bMillennium #bPuzzle: NL #rExplosive Tokens become #rSuper #rExplosive Tokens."; }
		else if (Util.getChallengeLevel() == 17) { return "At most Rest Sites, you may only #yRest or #ySmith."; }
		else if (Util.getChallengeLevel() == 18) { return "At the start of Elite combats, add a random #rCurse into your discard pile."; }
		else if (Util.getChallengeLevel() == 19) { return "All Bosses and Elites start combat with a random #yBuff."; }
		else if (Util.getChallengeLevel() == 20) { return "#ySummoning is restricted."; }
		else { return "Challenge Mode disabled!"; }
	}
	
	public static void resetCardsPlayedThisRunLists()
	{
		DuelistMod.loadedUniqueMonstersThisRunList = "";
		DuelistMod.loadedSpellsThisRunList = "";
		DuelistMod.loadedTrapsThisRunList = "";
		DuelistMod.entombedCardsThisRunList = "";
		DuelistMod.uniqueMonstersThisRun.clear();
		DuelistMod.uniqueSpellsThisRun.clear();
		DuelistMod.uniqueTrapsThisRun.clear();
		DuelistMod.entombedCards.clear();
	}
	
	public static void fillCardsPlayedThisRunLists()
	{
		if (!DuelistMod.loadedUniqueMonstersThisRunList.equals(""))
		{
			DuelistMod.uniqueMonstersThisRun.clear();
			String[] savedStrings = DuelistMod.loadedUniqueMonstersThisRunList.split("~");
			//Map<String, String> map = new HashMap<>();
			List<String> cards = Arrays.asList(savedStrings);
			for (String s : cards) {
				if (DuelistMod.mapForRunCardsLoading.containsKey(s))
				{
					if (DuelistMod.mapForRunCardsLoading.get(s) instanceof DuelistCard) 
					{ 
						DuelistMod.uniqueMonstersThisRunMap.put(DuelistMod.mapForRunCardsLoading.get(s).cardID, DuelistMod.mapForRunCardsLoading.get(s));
						DuelistMod.uniqueMonstersThisRun.add((DuelistCard) DuelistMod.mapForRunCardsLoading.get(s).makeStatEquivalentCopy()); 
					}
					else { Util.log("fillCardsPlayedThisRunLists found " + s + " in the map, but it was not a DuelistCard!"); }
				}
				else
				{
					Util.log("fillCardsPlayedThisRunLists did not find " + s + " in the map!");
				}
			}
		}
		
		if (!DuelistMod.loadedSpellsThisRunList.equals(""))
		{
			DuelistMod.uniqueSpellsThisRun.clear();
			String[] savedStrings = DuelistMod.loadedSpellsThisRunList.split("~");
			//Map<String, String> map = new HashMap<>();
			List<String> cards = Arrays.asList(savedStrings);
			for (String s : cards) {
				if (DuelistMod.mapForRunCardsLoading.containsKey(s))
				{
					if (DuelistMod.mapForRunCardsLoading.get(s) instanceof DuelistCard)
					{ 
						DuelistMod.uniqueSpellsThisRunMap.put(DuelistMod.mapForRunCardsLoading.get(s).cardID, DuelistMod.mapForRunCardsLoading.get(s));
						DuelistMod.uniqueSpellsThisRun.add((DuelistCard) DuelistMod.mapForRunCardsLoading.get(s).makeStatEquivalentCopy());
					}
					else { Util.log("fillCardsPlayedThisRunLists found " + s + " in the map, but it was not a DuelistCard!"); }
				}
				else
				{
					Util.log("fillCardsPlayedThisRunLists did not find " + s + " in the map!");
				}
			}
		}
		
		if (!DuelistMod.loadedTrapsThisRunList.equals(""))
		{
			DuelistMod.uniqueTrapsThisRun.clear();
			String[] savedStrings = DuelistMod.loadedTrapsThisRunList.split("~");
			//Map<String, String> map = new HashMap<>();
			List<String> cards = Arrays.asList(savedStrings);
			for (String s : cards) {
				if (DuelistMod.mapForRunCardsLoading.containsKey(s))
				{
					if (DuelistMod.mapForRunCardsLoading.get(s) instanceof DuelistCard) 
					{
						DuelistMod.uniqueTrapsThisRunMap.put(DuelistMod.mapForRunCardsLoading.get(s).cardID, DuelistMod.mapForRunCardsLoading.get(s));
						DuelistMod.uniqueTrapsThisRun.add((DuelistCard) DuelistMod.mapForRunCardsLoading.get(s).makeStatEquivalentCopy()); 
					}
					else { Util.log("fillCardsPlayedThisRunLists found " + s + " in the map, but it was not a DuelistCard!"); }
				}
				else
				{
					Util.log("fillCardsPlayedThisRunLists did not find " + s + " in the map!");
				}
			}
		}
		
		if (!DuelistMod.entombedCardsThisRunList.equals(""))
		{
			DuelistMod.entombedCards.clear();
			try
			{
				String[] savedStrings = DuelistMod.entombedCardsThisRunList.split("~");
				Map<String, Integer> mapp = new HashMap<>();
				for (String s : savedStrings)
				{
					try
					{
						String[] splitt = s.split("@");
						try
						{
							Integer i = Integer.parseInt(splitt[1]);
							mapp.put(splitt[0], i);
						} catch (NumberFormatException e) { e.printStackTrace(); Util.log("Util.fillCardsPlayedThisRunLists() is getting a NumberFormatException. Entombed cards probably are not loading properly."); }
					} catch (PatternSyntaxException e) { e.printStackTrace(); Util.log("Util.fillCardsPlayedThisRunLists() is getting a PatternSyntaxException. Entombed cards probably are not loading properly."); }
				}
				for (Entry<String, Integer> i : mapp.entrySet())
				{
					if (DuelistMod.mapForRunCardsLoading.containsKey(i.getKey()))
					{
						AbstractCard ra = DuelistMod.mapForRunCardsLoading.get(i.getKey()).makeStatEquivalentCopy();
						for (int j = 0; j < i.getValue(); j++)
						{
							if (ra.canUpgrade()) { ra.upgrade(); }
						}
						DuelistMod.entombedCards.add(ra);
					}
					else
					{
						Util.log("Entombed Cards Load skipped " + i.getKey() + " because it was not found in the map!");
					}
				}
			} catch (PatternSyntaxException e) { e.printStackTrace(); Util.log("Util.fillCardsPlayedThisRunLists() is getting a PatternSyntaxException for the entire string of Entombed cards. Entombed cards probably are not loading properly."); }
		}
	}
	
	public static boolean canEntomb(AbstractCard c)
	{
		if (!c.hasTag(Tags.EXEMPT) && c.hasTag(Tags.ZOMBIE))
		{
			return true;
		}
		else if (c instanceof CustomResummonCard)
		{
			return true;
		}
		
		return false;
	}

	public static void entombCard(AbstractCard c)
	{
		if (!c.hasTag(Tags.EXEMPT) && c.hasTag(Tags.ZOMBIE))
		{
			DuelistMod.entombedCardsThisRunList += c.cardID + "@" + c.timesUpgraded + "~";
			DuelistMod.entombedCards.add(c.makeStatEquivalentCopy());
			AbstractDungeon.player.masterDeck.removeCard(c);
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				config.setString("entombed", DuelistMod.entombedCardsThisRunList);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
		}
		else if (c instanceof CustomResummonCard)
		{
			DuelistMod.entombedCardsThisRunList += c.cardID + "@" + c.timesUpgraded + "~";
			DuelistMod.entombedCards.add(c.makeStatEquivalentCopy());
			AbstractDungeon.player.masterDeck.removeCard(c);
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				config.setString("entombed", DuelistMod.entombedCardsThisRunList);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
		}
		else { Util.log("Attempted to Entomb a non-Zombie or an Exempt card, so we skipped it."); }
	}
	
	public static void removeRelicFromPools(String relicID)
	{
		AbstractDungeon.commonRelicPool.remove(relicID);
		AbstractDungeon.uncommonRelicPool.remove(relicID);
		AbstractDungeon.rareRelicPool.remove(relicID);
		AbstractDungeon.shopRelicPool.remove(relicID);
		AbstractDungeon.bossRelicPool.remove(relicID);
	}	
	
	public static boolean refreshShop()
	{
		if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() instanceof ShopRoom)
		{
			ShopScreen shop = AbstractDungeon.shopScreen;
			if (shop == null) { return false; }
			boolean remove = shop.purgeAvailable;
	    	ArrayList<AbstractCard> newColored = new ArrayList<AbstractCard>();
	    	ArrayList<AbstractCard> newColorless = new ArrayList<AbstractCard>();
	    	
	    	// 4 Regular Card Slots
	    	if (DuelistMod.nonPowers.size() > 0) { for (int i = 0; i < 4; i++) { newColored.add(DuelistMod.nonPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.nonPowers.size() - 1)).makeCopy()); }}
	    	else { for (int i = 0; i < 4; i++) { newColored.add(DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)).makeCopy()); }}
	    	
	    	// Power Slot
	    	if (DuelistMod.merchantPendantPowers.size() > 0) { newColored.add(DuelistMod.merchantPendantPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.merchantPendantPowers.size() - 1)).makeCopy()); }
	    	else { newColored.add(DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)).makeCopy()); }
	
	    	// Colorless Slots
			for (int i = 0; i < 2; i++)
			{
				AbstractCard c = AbstractDungeon.getColorlessCardFromPool(CardRarity.RARE).makeCopy();
	    		newColorless.add(c.makeCopy());
			}
	    	
	    	// Refresh Shop
	    	shop.init(newColored, newColorless);
	    	shop.purgeAvailable = remove;
	    	return true;
		}
		else { return false; }
	}
	
	// NATURIA - Resistance to Vines Helpers
	// These functions are run at the start of each battle
	// Nothing should be applied unless the player is using Naturia deck or has Naturia cards
	
	// Bosses
	// Always get resistance to Vines
	// On A17+, 10 or 20% extra resistance is added on to each combat
	// Otherwise, resistance percentage is (act num * 10) + 30
	public static void handleBossResistNature(boolean wasBossCombat)
	{
		boolean naturia = false;
		if (Util.deckIs("Naturia Deck")) { naturia = true; }
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}
		
		// For Naturia deck or if player has Naturia cards in deck
		if (wasBossCombat && naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (mons.type.equals(EnemyType.BOSS))
				{
					int roll = AbstractDungeon.actNum + 3;
					if (AbstractDungeon.ascensionLevel > 16) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
					if (Util.getChallengeLevel() > 3) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
				else if (!mons.hasPower(MinionPower.POWER_ID)) { Util.log("Found non-minion, non-boss enemy in a boss room. Should this have Resistance? Enemy=" + mons.name); }
			}
		}
	}
	
	// Elites
	// Can get Resistance to Vines on A17+
	// On A20, 10 or 20% extra resistance is added on to each combat
	// Otherwise, resistance percentage is (act num * 10) + 10
	public static void handleEliteResistNature(boolean wasEliteCombat)
	{
		if (AbstractDungeon.ascensionLevel < 17 && Util.getChallengeLevel() < 0) { return; }
		boolean naturia = false;		
		if (Util.deckIs("Naturia Deck")) { naturia = true; }
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}
		
		// For Naturia deck or if player has Naturia cards in deck
		if (wasEliteCombat && naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (mons.type.equals(EnemyType.ELITE))
				{
					int roll = AbstractDungeon.actNum + 1;
					if (AbstractDungeon.ascensionLevel > 19) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
					if (Util.getChallengeLevel() > 3) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
			}
		}
	}
	
	// Hallways
	// Can get Resistance to Vines on A19+
	// On A20, Resistance is always applied
	// Otherwise, resistance is applied to 25% of all non-Elite/non-Boss room enemies
	// Resistance percentage for hallways is randomly chosen to be 10, 20, or 30%
	public static void handleHallwayResistNature()
	{
		if (AbstractDungeon.ascensionLevel < 19 && Util.getChallengeLevel() < 0) { return; }
		boolean naturia = false;		
		if (Util.deckIs("Naturia Deck")) { naturia = true; }
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}
		
		// For Naturia deck or if player has Naturia cards in deck
		if (naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (AbstractDungeon.ascensionLevel > 19)
				{
					int roll = AbstractDungeon.cardRandomRng.random(1, AbstractDungeon.actNum);
					if (Util.getChallengeLevel() > 3) { roll++; }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
				else if (AbstractDungeon.cardRandomRng.random(1, 4) == 1)
				{
					int roll = AbstractDungeon.cardRandomRng.random(1, AbstractDungeon.actNum);
					if (Util.getChallengeLevel() > 3) { roll++; }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
			}
		}
	}
	
	public static void registerCustomPowers()
	{
		BaseMod.addPower(AerodynamicsPower.class, AerodynamicsPower.POWER_ID);
		BaseMod.addPower(AlphaMagPower.class, AlphaMagPower.POWER_ID);
		BaseMod.addPower(AnteaterPower.class, AnteaterPower.POWER_ID);
		BaseMod.addPower(ArtifactSanctumPower.class, ArtifactSanctumPower.POWER_ID);
		BaseMod.addPower(AutorokketDragonPower.class, AutorokketDragonPower.POWER_ID);
		BaseMod.addPower(BadReactionPower.class, BadReactionPower.POWER_ID);
		BaseMod.addPower(BerserkScalesPower.class, BerserkScalesPower.POWER_ID);
		BaseMod.addPower(BeserkingDebuffA.class, BeserkingDebuffA.POWER_ID);
		BaseMod.addPower(BeserkingDebuffB.class, BeserkingDebuffB.POWER_ID);
		BaseMod.addPower(BeserkingDebuffC.class, BeserkingDebuffC.POWER_ID);
		BaseMod.addPower(BetaMagPower.class, BetaMagPower.POWER_ID);
		BaseMod.addPower(BlackPendantPower.class, BlackPendantPower.POWER_ID);
		BaseMod.addPower(BlastingFusePower.class, BlastingFusePower.POWER_ID);
		BaseMod.addPower(BlizzardDragonPower.class, BlizzardDragonPower.POWER_ID);
		BaseMod.addPower(BloomingDarkRosePower.class, BloomingDarkRosePower.POWER_ID);
		BaseMod.addPower(BoosterDragonPower.class, BoosterDragonPower.POWER_ID);
		BaseMod.addPower(BugMatrixPower.class, BugMatrixPower.POWER_ID);
		BaseMod.addPower(BurningDebuff.class, BurningDebuff.POWER_ID);
		BaseMod.addPower(CallGravePower.class, CallGravePower.POWER_ID);
		BaseMod.addPower(CannonPower.class, CannonPower.POWER_ID);
		BaseMod.addPower(CanyonPower.class, CanyonPower.POWER_ID);
		BaseMod.addPower(CardSafePower.class, CardSafePower.POWER_ID);
		BaseMod.addPower(CastleDragonSoulsPower.class, CastleDragonSoulsPower.POWER_ID);
		BaseMod.addPower(CastlePower.class, CastlePower.POWER_ID);
		BaseMod.addPower(CatapultPower.class, CatapultPower.POWER_ID);
		BaseMod.addPower(CatapultZonePower.class, CatapultZonePower.POWER_ID);
		BaseMod.addPower(CocoonPower.class, CocoonPower.POWER_ID);
		BaseMod.addPower(CommanderSwordsPower.class, CommanderSwordsPower.POWER_ID);
		BaseMod.addPower(ConvulsionNaturePower.class, ConvulsionNaturePower.POWER_ID);
		BaseMod.addPower(CorrosiveScalesPower.class, CorrosiveScalesPower.POWER_ID);
		BaseMod.addPower(CrystalForestPower.class, CrystalForestPower.POWER_ID);
		BaseMod.addPower(CrystalTreePower.class, CrystalTreePower.POWER_ID);
		BaseMod.addPower(CubicKarmaPower.class, CubicKarmaPower.POWER_ID);
		BaseMod.addPower(CyberDragonSiegerPower.class, CyberDragonSiegerPower.POWER_ID);
		BaseMod.addPower(CyberEltaninPower.class, CyberEltaninPower.POWER_ID);
		BaseMod.addPower(CyberLaserDragonPower.class, CyberLaserDragonPower.POWER_ID);
		BaseMod.addPower(CyberneticOverflowDebuffA.class, CyberneticOverflowDebuffA.POWER_ID);
		BaseMod.addPower(CyberneticOverflowDebuffB.class, CyberneticOverflowDebuffB.POWER_ID);
		BaseMod.addPower(DarkMirrorPower.class, DarkMirrorPower.POWER_ID);
		BaseMod.addPower(DarknessNeospherePower.class, DarknessNeospherePower.POWER_ID);
		BaseMod.addPower(DemiseLandPower.class, DemiseLandPower.POWER_ID);
		BaseMod.addPower(DepoweredPower.class, DepoweredPower.POWER_ID);
		BaseMod.addPower(DespairPower.class, DespairPower.POWER_ID);
		BaseMod.addPower(DoomDonutsPower.class, DoomDonutsPower.POWER_ID);
		BaseMod.addPower(DoomdogPower.class, DoomdogPower.POWER_ID);
		BaseMod.addPower(DragonMasteryPower.class, DragonMasteryPower.POWER_ID);
		BaseMod.addPower(DragonMirrorPower.class, DragonMirrorPower.POWER_ID);
		BaseMod.addPower(DragonRavinePower.class, DragonRavinePower.POWER_ID);
		BaseMod.addPower(DragonTreasurePower.class, DragonTreasurePower.POWER_ID);
		BaseMod.addPower(Dragonscales.class, Dragonscales.POWER_ID);
		BaseMod.addPower(DrillBarnaclePower.class, DrillBarnaclePower.POWER_ID);
		BaseMod.addPower(EmperorPower.class, EmperorPower.POWER_ID);
		BaseMod.addPower(EnemyBoosterDragonPower.class, EnemyBoosterDragonPower.POWER_ID);
		BaseMod.addPower(EnemyEnergyPower.class, EnemyEnergyPower.POWER_ID);
		BaseMod.addPower(EnemyExodiaPower.class, EnemyExodiaPower.POWER_ID);
		BaseMod.addPower(EnemyHandPower.class, EnemyHandPower.POWER_ID);
		BaseMod.addPower(EnemyMiraclePower.class, EnemyMiraclePower.POWER_ID);
		BaseMod.addPower(EnemySummonsPower.class, EnemySummonsPower.POWER_ID);
		BaseMod.addPower(EnemyTotemPower.class, EnemyTotemPower.POWER_ID);
		BaseMod.addPower(EvokeSicknessPower.class, EvokeSicknessPower.POWER_ID);
		BaseMod.addPower(ExodiaPower.class, ExodiaPower.POWER_ID);
		BaseMod.addPower(ExodiaRenewalPower.class, ExodiaRenewalPower.POWER_ID);
		BaseMod.addPower(FightingSpiritPower.class, FightingSpiritPower.POWER_ID);
		BaseMod.addPower(FlameTigerPower.class, FlameTigerPower.POWER_ID);
		BaseMod.addPower(FocusDownPower.class, FocusDownPower.POWER_ID);
		BaseMod.addPower(FocusLossPower.class, FocusLossPower.POWER_ID);
		BaseMod.addPower(ForestPower.class, ForestPower.POWER_ID);
		BaseMod.addPower(FrozenDebuff.class, FrozenDebuff.POWER_ID);
		BaseMod.addPower(FrozenRosePower.class, FrozenRosePower.POWER_ID);
		BaseMod.addPower(FutureFusionPower.class, FutureFusionPower.POWER_ID);
		BaseMod.addPower(GalaxySoldierPower.class, GalaxySoldierPower.POWER_ID);
		BaseMod.addPower(GammaMagPower.class, GammaMagPower.POWER_ID);
		BaseMod.addPower(GatesDarkPower.class, GatesDarkPower.POWER_ID);
		BaseMod.addPower(GoblinRemedyPower.class, GoblinRemedyPower.POWER_ID);
		BaseMod.addPower(GravityAxePower.class, GravityAxePower.POWER_ID);
		BaseMod.addPower(GreedShardPower.class, GreedShardPower.POWER_ID);
		BaseMod.addPower(GridRodPower.class, GridRodPower.POWER_ID);
		BaseMod.addPower(HauntedDebuff.class, HauntedDebuff.POWER_ID);
		BaseMod.addPower(HauntedPower.class, HauntedPower.POWER_ID);
		BaseMod.addPower(HealGoldPower.class, HealGoldPower.POWER_ID);
		BaseMod.addPower(HeartUnderdogPower.class, HeartUnderdogPower.POWER_ID);
		BaseMod.addPower(HeartUnderspellPower.class, HeartUnderspellPower.POWER_ID);
		BaseMod.addPower(HeartUndertrapPower.class, HeartUndertrapPower.POWER_ID);
		BaseMod.addPower(HeartUndertributePower.class, HeartUndertributePower.POWER_ID);
		BaseMod.addPower(IgnisHeatPower.class, IgnisHeatPower.POWER_ID);
		BaseMod.addPower(IlBludPower.class, IlBludPower.POWER_ID);
		BaseMod.addPower(ImperialPower.class, ImperialPower.POWER_ID);
		BaseMod.addPower(InariFirePower.class, InariFirePower.POWER_ID);
		BaseMod.addPower(InfernityDoomPower.class, InfernityDoomPower.POWER_ID);
		BaseMod.addPower(JinzoPower.class, JinzoPower.POWER_ID);
		BaseMod.addPower(JumboDrillPower.class, JumboDrillPower.POWER_ID);
		BaseMod.addPower(JurassicImpactPower.class, JurassicImpactPower.POWER_ID);
		BaseMod.addPower(LairWirePower.class, LairWirePower.POWER_ID);
		BaseMod.addPower(LeavesPower.class, LeavesPower.POWER_ID);
		BaseMod.addPower(LeodrakeManePower.class, LeodrakeManePower.POWER_ID);
		BaseMod.addPower(LostWorldPower.class, LostWorldPower.POWER_ID);
		BaseMod.addPower(MagiciansRobePower.class, MagiciansRobePower.POWER_ID);
		BaseMod.addPower(MagiciansRodPower.class, MagiciansRodPower.POWER_ID);
		BaseMod.addPower(MagickaPower.class, MagickaPower.POWER_ID);
		BaseMod.addPower(MagneticFieldPower.class, MagneticFieldPower.POWER_ID);
		BaseMod.addPower(MillenniumSpellbookPower.class, MillenniumSpellbookPower.POWER_ID);
		BaseMod.addPower(MiraculousDescentPower.class, MillenniumSpellbookPower.POWER_ID);
		BaseMod.addPower(MirrorForcePower.class, MirrorForcePower.POWER_ID);
		BaseMod.addPower(MortalityPower.class, MortalityPower.POWER_ID);
		BaseMod.addPower(MountainPower.class, MountainPower.POWER_ID);
		BaseMod.addPower(NaturalDisasterPower.class, NaturalDisasterPower.POWER_ID);
		BaseMod.addPower(NatureReflectionPower.class, NatureReflectionPower.POWER_ID);
		BaseMod.addPower(NaturiaForestPower.class, NaturiaForestPower.POWER_ID);
		BaseMod.addPower(NaturiaLeodrakePower.class, NaturiaLeodrakePower.POWER_ID);
		BaseMod.addPower(NaturiaStagBeetlePower.class, NaturiaStagBeetlePower.POWER_ID);
		BaseMod.addPower(NaturiaVeinPower.class, NaturiaVeinPower.POWER_ID);
		BaseMod.addPower(ObeliskPower.class, ObeliskPower.POWER_ID);
		BaseMod.addPower(OniPower.class, OniPower.POWER_ID);
		BaseMod.addPower(OrbEvokerPower.class, OrbEvokerPower.POWER_ID);
		BaseMod.addPower(OrbHealerPower.class, OrbHealerPower.POWER_ID);
		BaseMod.addPower(OutriggerExtensionPower.class, OutriggerExtensionPower.POWER_ID);
		BaseMod.addPower(ParasitePower.class, ParasitePower.POWER_ID);
		BaseMod.addPower(PoseidonWavePower.class, PoseidonWavePower.POWER_ID);
		BaseMod.addPower(PotGenerosityPower.class, PotGenerosityPower.POWER_ID);
		BaseMod.addPower(RadiantMirrorPower.class, RadiantMirrorPower.POWER_ID);
		BaseMod.addPower(RainbowCapturePower.class, RainbowCapturePower.POWER_ID);
		BaseMod.addPower(RainbowRefractionPower.class, RainbowRefractionPower.POWER_ID);
		BaseMod.addPower(RainbowRuinsPower.class, RainbowRuinsPower.POWER_ID);
		BaseMod.addPower(RedMirrorPower.class, RedMirrorPower.POWER_ID);
		BaseMod.addPower(ReducerPower.class, ReducerPower.POWER_ID);
		BaseMod.addPower(ReinforceTruthPower.class, ReinforceTruthPower.POWER_ID);
		BaseMod.addPower(ReinforcementsPower.class, ReinforcementsPower.POWER_ID);
		BaseMod.addPower(ReleaseFromStonePower.class, ReleaseFromStonePower.POWER_ID);
		BaseMod.addPower(ResistNatureEnemyPower.class, ResistNatureEnemyPower.POWER_ID);		
		BaseMod.addPower(ResummonBonusPower.class, ResummonBonusPower.POWER_ID);
		BaseMod.addPower(RetainForTurnsPower.class, RetainForTurnsPower.POWER_ID);
		BaseMod.addPower(SacredTreePower.class, SacredTreePower.POWER_ID);
		BaseMod.addPower(SarraceniantPower.class, SarraceniantPower.POWER_ID);
		BaseMod.addPower(SeedCannonPower.class, SeedCannonPower.POWER_ID);
		BaseMod.addPower(SilverWingPower.class, SilverWingPower.POWER_ID);
		BaseMod.addPower(SliferSkyPower.class, SliferSkyPower.POWER_ID);
		BaseMod.addPower(SogenPower.class, SogenPower.POWER_ID);
		BaseMod.addPower(SolidarityDiscardPower.class, SolidarityDiscardPower.POWER_ID);
		BaseMod.addPower(SolidarityExhaustPower.class, SolidarityExhaustPower.POWER_ID);
		BaseMod.addPower(SoulBonePower.class, SoulBonePower.POWER_ID);
		BaseMod.addPower(SpellbookKnowledgePower.class, SpellbookKnowledgePower.POWER_ID);
		BaseMod.addPower(SpellbookLifePower.class, SpellbookLifePower.POWER_ID);
		BaseMod.addPower(SpellbookMiraclePower.class, SpellbookMiraclePower.POWER_ID);
		BaseMod.addPower(SpellbookPowerPower.class, SpellbookPowerPower.POWER_ID);
		BaseMod.addPower(SphereKuribohPower.class, SphereKuribohPower.POWER_ID);
		BaseMod.addPower(SpikedGillmanPower.class, SpikedGillmanPower.POWER_ID);
		BaseMod.addPower(SpiritForcePower.class, SpiritForcePower.POWER_ID);
		BaseMod.addPower(SpiritualForestPower.class, SpiritualForestPower.POWER_ID);
		BaseMod.addPower(SplendidRosePower.class, SplendidRosePower.POWER_ID);
		BaseMod.addPower(StatueAnguishPatternPower.class, StatueAnguishPatternPower.POWER_ID);
		BaseMod.addPower(StormingMirrorPower.class, StormingMirrorPower.POWER_ID);
		BaseMod.addPower(StrengthDownPower.class, StrengthDownPower.POWER_ID);
		BaseMod.addPower(SummonPower.class, SummonPower.POWER_ID);
		BaseMod.addPower(SummonSicknessPower.class, SummonSicknessPower.POWER_ID);
		BaseMod.addPower(SuperheavyDexGainPower.class, SuperheavyDexGainPower.POWER_ID);
		BaseMod.addPower(SurvivalFittestPower.class, SurvivalFittestPower.POWER_ID);
		BaseMod.addPower(SwordDeepPower.class, SwordDeepPower.POWER_ID);
		BaseMod.addPower(SwordsBurnPower.class, SwordsBurnPower.POWER_ID);
		BaseMod.addPower(SwordsConcealPower.class, SwordsConcealPower.POWER_ID);
		BaseMod.addPower(SwordsRevealPower.class, SwordsRevealPower.POWER_ID);
		BaseMod.addPower(TimeWizardPower.class, TimeWizardPower.POWER_ID);
		BaseMod.addPower(TombLooterPower.class, TombLooterPower.POWER_ID);
		BaseMod.addPower(ToonBriefcasePower.class, ToonBriefcasePower.POWER_ID);
		BaseMod.addPower(ToonCannonPower.class, ToonCannonPower.POWER_ID);
		BaseMod.addPower(ToonKingdomPower.class, ToonKingdomPower.POWER_ID);
		BaseMod.addPower(ToonRollbackPower.class, ToonRollbackPower.POWER_ID);
		BaseMod.addPower(ToonWorldPower.class, ToonWorldPower.POWER_ID);
		BaseMod.addPower(TotemDragonPower.class, TotemDragonPower.POWER_ID);
		BaseMod.addPower(TrapHolePower.class, TrapHolePower.POWER_ID);
		BaseMod.addPower(TributeSicknessPower.class, TributeSicknessPower.POWER_ID);
		BaseMod.addPower(TributeToonPower.class, TributeToonPower.POWER_ID);
		BaseMod.addPower(TributeToonPowerB.class, TributeToonPowerB.POWER_ID);
		BaseMod.addPower(TurretWarriorPower.class, TurretWarriorPower.POWER_ID);
		BaseMod.addPower(TwoJamPower.class, TwoJamPower.POWER_ID);
		BaseMod.addPower(TyrantWingPower.class, TyrantWingPower.POWER_ID);
		BaseMod.addPower(UltimateOfferingPower.class, UltimateOfferingPower.POWER_ID);
		BaseMod.addPower(UmiPower.class, UmiPower.POWER_ID);
		BaseMod.addPower(VanDragPower.class, VanDragPower.POWER_ID);
		BaseMod.addPower(VinesPower.class, VinesPower.POWER_ID);
		BaseMod.addPower(VioletCrystalPower.class, VioletCrystalPower.POWER_ID);
		BaseMod.addPower(VoidExpansionPower.class, VoidExpansionPower.POWER_ID);
		BaseMod.addPower(VoidVanishmentPower.class, VoidVanishmentPower.POWER_ID);
		BaseMod.addPower(WallThornsPower.class, WallThornsPower.POWER_ID);
		BaseMod.addPower(WeaponChangePower.class, WeaponChangePower.POWER_ID);
		BaseMod.addPower(WhiteHornDragonPower.class, WhiteHornDragonPower.POWER_ID);
		BaseMod.addPower(WhiteHowlingPower.class, WhiteHowlingPower.POWER_ID);
		BaseMod.addPower(WonderWandPower.class, WonderWandPower.POWER_ID);
		BaseMod.addPower(WorldTreePower.class, WorldTreePower.POWER_ID);
		BaseMod.addPower(YamiFormPower.class, YamiFormPower.POWER_ID);		
		BaseMod.addPower(YamiPower.class, YamiPower.POWER_ID);
		BaseMod.addPower(FluxPower.class, FluxPower.POWER_ID);
		BaseMod.addPower(DoublePlayFirstCardPower.class, DoublePlayFirstCardPower.POWER_ID);
		BaseMod.addPower(GeartownPower.class, GeartownPower.POWER_ID);
		BaseMod.addPower(GreasedDebuff.class, GreasedDebuff.POWER_ID);
		BaseMod.addPower(JinzoLordPower.class, JinzoLordPower.POWER_ID);
		BaseMod.addPower(MetalholdMovingBlockadePower.class, MetalholdMovingBlockadePower.POWER_ID);
		BaseMod.addPower(OverworkedPower.class, OverworkedPower.POWER_ID);
		BaseMod.addPower(RevolvingSwitchyardPower.class, RevolvingSwitchyardPower.POWER_ID);
		BaseMod.addPower(RoboticKnightPower.class, RoboticKnightPower.POWER_ID);
		BaseMod.addPower(UnionHangarPower.class, UnionHangarPower.POWER_ID);
		BaseMod.addPower(WonderGaragePower.class, WonderGaragePower.POWER_ID);
		BaseMod.addPower(MagicCylinderPower.class, MagicCylinderPower.POWER_ID);
		BaseMod.addPower(ElectricityPower.class, ElectricityPower.POWER_ID);
		BaseMod.addPower(FishscalesPower.class, FishscalesPower.POWER_ID);
		BaseMod.addPower(BloodPower.class, BloodPower.POWER_ID);
		BaseMod.addPower(FocusUpPower.class, FocusUpPower.POWER_ID);
		BaseMod.addPower(StrengthUpPower.class, StrengthUpPower.POWER_ID);
		BaseMod.addPower(TricksPower.class, TricksPower.POWER_ID);
		BaseMod.addPower(ArcanaPower.class, ArcanaPower.POWER_ID);
		BaseMod.addPower(MegaconfusionPower.class, MegaconfusionPower.POWER_ID);
		BaseMod.addPower(SeaDwellerPower.class, SeaDwellerPower.POWER_ID);
		BaseMod.addPower(ZONEPower.class, ZONEPower.POWER_ID);
		BaseMod.addPower(BookTaiyouPower.class, BookTaiyouPower.POWER_ID);
	}
	
}
