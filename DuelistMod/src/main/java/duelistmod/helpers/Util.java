package duelistmod.helpers;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.Gandora;
import duelistmod.cards.fourthWarriors.*;
import duelistmod.cards.incomplete.HourglassLife;
import duelistmod.cards.nameless.greed.*;
import duelistmod.cards.nameless.magic.*;
import duelistmod.cards.nameless.power.*;
import duelistmod.cards.nameless.war.*;
import duelistmod.cards.tempCards.*;
import duelistmod.cards.tokens.Token;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.enemyPowers.ResistNatureEnemyPower;
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
	
	public static boolean tokenRoll()
	{
		int roll = AbstractDungeon.cardRandomRng.random(1,2);
    	if (roll == 1) { return true; }
    	else { return false; }
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
		if (includePuzzle) { items.add(new MillenniumPuzzle().name); }
		if (items.contains(r.name)) { return true; }
		else { return false; }		
	}
	
	public static ArrayList<AbstractRelic> getAllMillenniumItems(boolean includePuzzle)
	{
		ArrayList<AbstractRelic> items = new ArrayList<AbstractRelic>();
		items.add(new MillenniumCoin());
		items.add(new MillenniumRing());
		items.add(new MillenniumRod());
		//items.add(new MillenniumKey());
		items.add(new MillenniumEye());
		items.add(new ResummonBranch());
		items.add(new MillenniumScale());
		items.add(new MillenniumNecklace());
		items.add(new MillenniumToken());
		items.add(new MillenniumSymbol());
		if (includePuzzle) { items.add(new MillenniumPuzzle()); }
		return items;
	}

	public static AbstractRelic getRandomMillenniumItem()
	{
		ArrayList<AbstractRelic> items = new ArrayList<AbstractRelic>();
		items.add(new MillenniumCoin());
		items.add(new MillenniumRing());
		items.add(new MillenniumRod());
		//items.add(new MillenniumKey());
		items.add(new MillenniumEye());
		items.add(new ResummonBranch());
		items.add(new MillenniumScale());
		items.add(new MillenniumNecklace());
		items.add(new MillenniumToken());
		items.add(new MillenniumSymbol());
		return items.get(AbstractDungeon.relicRng.random(items.size() - 1));
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
	
	public static AbstractCard getSpecialMagicCardForNamelessTomb()
	{
		ArrayList<DuelistCard> specialCards = getSpecialMagicCardsForNamelessTomb();
		return specialCards.get(AbstractDungeon.cardRandomRng.random(specialCards.size() - 1));
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
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Naturia Deck")) 
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
	
	public static void removeRelicFromPools(AbstractRelic relic)
	{
		AbstractDungeon.commonRelicPool.remove(relic.relicId);
		AbstractDungeon.uncommonRelicPool.remove(relic.relicId);
		AbstractDungeon.rareRelicPool.remove(relic.relicId);
		AbstractDungeon.shopRelicPool.remove(relic.relicId);
		AbstractDungeon.bossRelicPool.remove(relic.relicId);
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
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Naturia Deck")) { naturia = true; }
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}
		
		// For Naturia deck or if player has Naturia cards in deck
		if (wasBossCombat && naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (mons.type.equals(EnemyType.BOSS))
				{
					int roll = AbstractDungeon.actNum + 3;
					if (AbstractDungeon.ascensionLevel > 16 || DuelistMod.challengeMode) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
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
		if (AbstractDungeon.ascensionLevel < 17) { return; }
		boolean naturia = false;
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Naturia Deck")) { naturia = true; }
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}
		
		// For Naturia deck or if player has Naturia cards in deck
		if (wasEliteCombat && naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (mons.type.equals(EnemyType.ELITE))
				{
					int roll = AbstractDungeon.actNum + 1;
					if (AbstractDungeon.ascensionLevel > 19 || DuelistMod.challengeMode) { roll += AbstractDungeon.cardRandomRng.random(1, 2); }
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
		if (AbstractDungeon.ascensionLevel < 19) { return; }
		boolean naturia = false;
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Naturia Deck")) { naturia = true; }
		if (!naturia) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.NATURIA) && !c.hasTag(Tags.MEGATYPED)) { naturia = true; break; }}}
		
		// For Naturia deck or if player has Naturia cards in deck
		if (naturia)
		{
			for (AbstractMonster mons : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (AbstractDungeon.ascensionLevel > 19)
				{
					int roll = AbstractDungeon.cardRandomRng.random(1, AbstractDungeon.actNum);
					if (DuelistMod.challengeMode) { roll++; }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
				else if (AbstractDungeon.cardRandomRng.random(1, 4) == 1)
				{
					int roll = AbstractDungeon.cardRandomRng.random(1, AbstractDungeon.actNum);
					if (DuelistMod.challengeMode) { roll++; }
					DuelistCard.applyPower(new ResistNatureEnemyPower(mons, mons, roll), mons);
				}
			}
		}
	}
	
}
