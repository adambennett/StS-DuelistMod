package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.DestructPotion;
import duelistmod.relics.*;
import duelistmod.rewards.*;
import duelistmod.variables.Tags;

public class BoosterPackHelper 
{
	public static int commonPackCost = 130;
	public static int spellPackCost = 150;
	public static int trapPackCost = 150;
	public static int summonPackCost = 150;
	public static int rareDragPackCost = 200;
	public static int rareSpellcasterPackCost = 180;
	public static int dragPackCost = 175;
	public static int deckPackCost = 150;
	public static int specialPackCost = 100;
	public static int uncommonPackCost = 140;
	public static int skillPackCost = 145;
	public static int attackPackCost = 145;
	public static int allUncommonPackCost = 150;
	public static int rarePackCost = 175;
	public static int multitypePackCost = 160;
	public static int powerPackCost = 165;
	public static int rareAttackPackCost = 180;
	public static int rareSkillPackCost = 170;
	public static int rarePowerPackCost = 185;
	public static int sillyPackCost = 155;
	public static int orbPackCost = 75;
	public static int exodiaPackCost = 120;
	public static int allRarePackCost = 250;
	public static int normalPackSize = 5;
	public static int fatPackSize = 7;
	public static int bonusPackSize = 3;
	public static ArrayList<AbstractCard> commons = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> uncommons = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rares = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> nonRares = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> nonCommon = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> orbs = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> spells = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareSpells = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> traps = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareTraps = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> monsters = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> powers = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> attacks = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> skills = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareSpellcasters = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> commonSkills = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> commonAttacks = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> commonPowers = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> nonCommonSkills = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> nonCommonAttacks = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> nonCommonPowers = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> nonRareSkills = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> nonRareAttacks = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> nonRarePowers = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rarePowers = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> summons = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> deckTyped = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareSummons = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareDeckTyped = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> dragons = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareDragons = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> exodia = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareSkills = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareAttacks = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> special = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> fullPool = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> fullPoolNonCommon = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> metronomes = new ArrayList<AbstractCard>();
	private static ArrayList<ArrayList<AbstractCard>> lists = new ArrayList<ArrayList<AbstractCard>>();
	
	public static void resetPackSizes()
	{
		normalPackSize = 5;
		fatPackSize = 7;
		bonusPackSize = 3;
	}
	
	private static void setupListList()
	{
		lists.clear();
		lists.add(commons);		// 0
		lists.add(uncommons);
		lists.add(rares);
		lists.add(nonRares);
		lists.add(nonCommon);
		lists.add(orbs);		// 5
		lists.add(spells);
		lists.add(traps);
		lists.add(monsters);
		lists.add(powers);
		lists.add(attacks);		// 10
		lists.add(skills);
		lists.add(commonSkills);
		lists.add(commonAttacks);
		lists.add(commonPowers);	// 14
		lists.add(nonCommonSkills);
		lists.add(nonCommonAttacks);
		lists.add(nonCommonPowers);
		lists.add(nonRareSkills);
		lists.add(nonRareAttacks);
		lists.add(nonRarePowers);	// 20
		lists.add(rarePowers);
		lists.add(summons);
		lists.add(deckTyped);		// 23
		lists.add(dragons);			//
		lists.add(rareDragons);		//
		lists.add(exodia);			//
		lists.add(rareSkills);		//
		lists.add(rareAttacks);		//
		lists.add(special);			//
		lists.add(fullPool);		//
		lists.add(fullPoolNonCommon);
		lists.add(rareSpellcasters);
		lists.add(rareDeckTyped);
		lists.add(rareSummons);
	}
	
	public static ArrayList<AbstractCard> getRandomCards(ArrayList<AbstractCard> fillFrom, int amountToPull)
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		toReturn.addAll(fillFrom);
		if (amountToPull < 0) { amountToPull = 0; }
		while (toReturn.size() > amountToPull) { toReturn.remove(AbstractDungeon.cardRandomRng.random(toReturn.size() - 1)); }
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> findNonMatching(ArrayList<AbstractCard> pool, ArrayList<AbstractCard> filledSoFar)
	{
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		for (AbstractCard c : filledSoFar)
		{
			names.add(c.name);
		}
		for (AbstractCard c : pool)
		{
			if (!names.contains(c.name)) { toReturn.add(c); }
		}
		
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> skillBoosterPack()
	{
		Util.log("Skill Booster Pack -- commonSkills.size()==" + commonSkills.size() + ", nonCommonSkills.size()==" + nonCommonSkills.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(commonSkills, normalPackSize - 1);		
		toReturn.addAll(getRandomCards(nonCommonSkills, 1));
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> attackBoosterPack()
	{
		Util.log("Attack Booster Pack -- commonAttacks.size()==" + commonAttacks.size() + ", nonCommonAttacks.size()==" + nonCommonAttacks.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(commonAttacks, normalPackSize - 1);		
		toReturn.addAll(getRandomCards(nonCommonAttacks, 1));
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> rareSkillBoosterPack()
	{
		Util.log("Rare Skill Booster Pack -- nonRareSkills.size()==" + nonRareSkills.size() + ", rareSkills.size()==" + rareSkills.size());
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		if (AbstractDungeon.player.hasRelic(NlothsGift.ID))
		{
			toReturn.addAll(getRandomCards(nonRareSkills, normalPackSize - 3));		
			toReturn.addAll(getRandomCards(rareSkills, 3));
		}
		else
		{
			toReturn.addAll(getRandomCards(nonRareSkills, normalPackSize - 2));		
			toReturn.addAll(getRandomCards(rareSkills, 2));
		}
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> rareAttackBoosterPack()
	{
		Util.log("Rare Attack Booster Pack -- nonRareAttacks.size()==" + nonRareAttacks.size() + ", rareAttacks.size()==" + rareAttacks.size());
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		if (AbstractDungeon.player.hasRelic(NlothsGift.ID))
		{
			toReturn.addAll(getRandomCards(nonRareAttacks, normalPackSize - 3));		
			toReturn.addAll(getRandomCards(rareAttacks, 3));
		}
		else
		{
			toReturn.addAll(getRandomCards(nonRareAttacks, normalPackSize - 2));		
			toReturn.addAll(getRandomCards(rareAttacks, 2));
		}
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> commonBoosterPack()
	{
		Util.log("Common Booster Pack -- commons.size()==" + commons.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(commons, normalPackSize);
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> commonMetronomePack()
	{
		Util.log("Common Metronome Pack -- commons.size()==" + commons.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(commons, normalPackSize);
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> orbBoosterPack()
	{
		Util.log("Orb/Exodia Booster Pack -- exodia.size()==" + exodia.size() + ", orbs.size()==" + orbs.size());
		int roll = AbstractDungeon.cardRandomRng.random(1, 2);
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		if (roll == 1 && !DuelistMod.exodiaBtnBool) { toReturn = getRandomCards(exodia, normalPackSize); }
		else { toReturn = getRandomCards(orbs, normalPackSize); }
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> uncommonBoosterPack()
	{
		Util.log("Uncommon Booster Pack -- commons.size()==" + commons.size() + ", uncommons.size()==" + uncommons.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(commons, normalPackSize - 1);		
		toReturn.addAll(getRandomCards(uncommons, 1));
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> uncommonBoosterPackB()
	{
		Util.log("Debug uncommon pack..");
		return new ArrayList<AbstractCard>();
	}
	
	public static ArrayList<AbstractCard> allUncommonBoosterPack()
	{
		Util.log("All Uncommon Booster Pack -- uncommons.size()==" + uncommons.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(uncommons, normalPackSize);
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> multitypeBoosterPack()
	{
		Util.log("Multitype Booster Pack -- monsters.size()==" + monsters.size() + ", spells.size()==" + spells.size() + ", traps.size()==" + traps.size());
		int extra = normalPackSize - 5;
		if (extra < 1) { extra = 0; }
 		ArrayList<AbstractCard> toReturn = getRandomCards(monsters, 2 + extra);	// 2 monsters + any extra cards that got added to the pack size
		toReturn.addAll(getRandomCards(spells, 2));								// 2 spells
		toReturn.addAll(getRandomCards(traps, 1));								// 1 trap
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> rareBoosterPack()
	{
		Util.log("Rare Booster Pack -- commons.size()==" + commons.size() + ", rares.size()==" + rares.size() + ", nonCommon.size()==" + nonCommon.size());
		int extra = normalPackSize - 5;
		if (extra < 1) { extra = 0; }
		if (AbstractDungeon.player.hasRelic(NlothsGift.ID))
		{
			ArrayList<AbstractCard> toReturn = getRandomCards(commons, 1 + extra);	// 1 common + any extra cards that got added to the pack size
			toReturn.addAll(getRandomCards(nonCommon, 3));							// 2 uncommon/rare
			ArrayList<AbstractCard> newRares = findNonMatching(rares, toReturn);
			toReturn.addAll(getRandomCards(newRares, 1));							// 1 rare (making sure to elimate possible crossover between non-common and rares to give 1 unique rare for sure)
			return toReturn;
		}
		else
		{
	 		ArrayList<AbstractCard> toReturn = getRandomCards(commons, 3 + extra);	// 3 commons + any extra cards that got added to the pack size
			toReturn.addAll(getRandomCards(nonCommon, 1));							// 1 uncommon/rare
			ArrayList<AbstractCard> newRares = findNonMatching(rares, toReturn);
			toReturn.addAll(getRandomCards(newRares, 1));							// 1 rare (making sure to elimate possible crossover between non-common and rares to give 1 unique rare for sure)
			return toReturn;
		}
	}
	
	
	public static ArrayList<AbstractCard> powerBoosterPack()
	{
		Util.log("Power Booster Pack -- powers.size()==" + powers.size() + ", rarePowers.size()==" + rarePowers.size());
 		ArrayList<AbstractCard> toReturn = getRandomCards(powers, normalPackSize);							
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> rarePowerBoosterPack()
	{
		Util.log("Rare Power Booster Pack -- rarePowers.size()==" + rarePowers.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(rarePowers, normalPackSize);		
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> sillyBoosterPack()
	{
		Util.log("Silly Booster Pack -- fullPool.size()==" + fullPool.size());
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();

		// No commons appear with N'loth's Gift
		if (AbstractDungeon.player.hasRelic(NlothsGift.ID))
		{
			ArrayList<AbstractCard> pullFrom = fullPoolNonCommon;
			// 5 Totally Random
			for (int i = 0; i < normalPackSize; i++)
			{
				int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
				AbstractCard randCard = pullFrom.get(randIndex);
				while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
				picked.add(randCard.name); toReturn.add(randCard);
			}
			return toReturn;
		}
		else
		{
			ArrayList<AbstractCard> pullFrom = fullPool;
			// 5 Totally Random
			for (int i = 0; i < normalPackSize; i++)
			{
				int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
				AbstractCard randCard = pullFrom.get(randIndex);
				while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
				picked.add(randCard.name); toReturn.add(randCard);
			}
			
			return toReturn;
		}
		
	}
	

	public static ArrayList<AbstractCard> allRaresBoosterPack()
	{
		Util.log("All Rares Booster Pack -- rares.size()==" + rares.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(rares, normalPackSize);		
		return toReturn;
	}

	
	// Bonus
	public static ArrayList<AbstractCard> spellsBoosterPack()
	{
		Util.log("Spells Bonus Booster Pack -- spells.size()==" + spells.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(spells, bonusPackSize - 1);	
		ArrayList<AbstractCard> newRares = findNonMatching(rareSpells, toReturn);
		toReturn.addAll(getRandomCards(newRares, 1));							
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> trapsBoosterPack()
	{
		Util.log("Traps Bonus Booster Pack -- traps.size()==" + traps.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(traps, bonusPackSize - 1);	
		ArrayList<AbstractCard> newRares = findNonMatching(rareTraps, toReturn);
		toReturn.addAll(getRandomCards(newRares, 1));							
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> summonsBoosterPack()
	{
		Util.log("Summons Bonus Booster Pack -- summons.size()==" + summons.size() + ", rareSummons.size()==" + rareSummons.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(summons, bonusPackSize - 1);	
		ArrayList<AbstractCard> newRares = findNonMatching(rareSummons, toReturn);
		toReturn.addAll(getRandomCards(newRares, 1));							
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> dragonsBoosterPack()
	{
		Util.log("Dragons Bonus Booster Pack -- dragons.size()==" + dragons.size() + ", rareDragons.size()==" + rareDragons.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(dragons, bonusPackSize - 1);	
		ArrayList<AbstractCard> newRares = findNonMatching(rareDragons, toReturn);
		toReturn.addAll(getRandomCards(newRares, 1));							
		return toReturn;
	}

	// Bonus
	public static ArrayList<AbstractCard> rareDragonsBoosterPack()
	{
		Util.log("Rare Dragon Booster Pack -- rareDragons.size()==" + rareDragons.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(rareDragons, bonusPackSize);		
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> rareSpellcasterBoosterPack()
	{
		Util.log("Rare Spellcaster Booster Pack -- rareSpellcasters.size()==" + rareSpellcasters.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(rareSpellcasters, bonusPackSize);		
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> specialBoosterPack()
	{
		int loops = 10;
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		for (int i = 0; i < bonusPackSize; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(special.size() - 1);
			AbstractCard randCard = special.get(randIndex);
			while (picked.contains(randCard.name) && loops > 0) { loops--; randIndex = AbstractDungeon.cardRandomRng.random(special.size() - 1); randCard = special.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to special booster");
		}
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> deckBoosterPack()
	{
		Util.log("Deck Bonus Booster Pack -- deckTyped.size()==" + deckTyped.size() + ", rareDeckTyped.size()==" + rareDeckTyped.size());
		ArrayList<AbstractCard> toReturn = getRandomCards(deckTyped, bonusPackSize - 1);	
		ArrayList<AbstractCard> newRares = findNonMatching(rareDeckTyped, toReturn);
		toReturn.addAll(getRandomCards(newRares, 1));							
		return toReturn;
	}

	private static void setupOrbsPack()
	{
		orbs.clear();
		orbs.addAll(DuelistCardLibrary.orbCardsForGeneration());
	}
	
	private static void clearLists()
	{
		commons = new ArrayList<AbstractCard>();
		uncommons = new ArrayList<AbstractCard>();
		rares = new ArrayList<AbstractCard>();
		nonRares = new ArrayList<AbstractCard>();
		nonCommon = new ArrayList<AbstractCard>();
		spells = new ArrayList<AbstractCard>();
		traps = new ArrayList<AbstractCard>();
		monsters = new ArrayList<AbstractCard>();
		powers = new ArrayList<AbstractCard>();
		rarePowers = new ArrayList<AbstractCard>();
		summons = new ArrayList<AbstractCard>();
		dragons = new ArrayList<AbstractCard>();
		rareDragons = new ArrayList<AbstractCard>();
		exodia = new ArrayList<AbstractCard>();
		rareSkills = new ArrayList<AbstractCard>();
		rareAttacks = new ArrayList<AbstractCard>();
		special = new ArrayList<AbstractCard>();
		skills = new ArrayList<AbstractCard>();
		attacks = new ArrayList<AbstractCard>();
		nonRarePowers = new ArrayList<AbstractCard>();
		nonRareSkills = new ArrayList<AbstractCard>();
		nonRareAttacks = new ArrayList<AbstractCard>();
		nonCommonPowers = new ArrayList<AbstractCard>();
		nonCommonSkills = new ArrayList<AbstractCard>();
		nonCommonAttacks = new ArrayList<AbstractCard>();
		commonPowers = new ArrayList<AbstractCard>();
		commonSkills = new ArrayList<AbstractCard>();
		commonAttacks = new ArrayList<AbstractCard>();
		rareSpellcasters = new ArrayList<AbstractCard>();
		rareDeckTyped = new ArrayList<AbstractCard>();
		rareSummons = new ArrayList<AbstractCard>();
		fullPool = new ArrayList<AbstractCard>();
		fullPoolNonCommon = new ArrayList<AbstractCard>();
	}
	
	private static void setupSpecialList()
	{
		special.add(new Raigeki());
		special.add(new IceQueen());
		special.add(new BlueEyesUltimate());
		special.add(new SuperheavyGeneral());
		special.add(new CardDestruction());
		special.add(new Gandora());
		special.add(new Wiseman());
		special.add(new DestructPotion());
		special.add(new DarkMasterZorc());
		special.add(new FiveHeaded());
		special.add(new SteamTrainKing());
		special.add(new GateGuardian());
		special.add(new HeartUnderdog());
		special.add(new Polymerization());
		special.add(new MonsterReborn());
		special.add(new ObeliskTormentor());
		special.add(new SliferSky());
		special.add(new WingedDragonRa());
		special.add(new PotGreed());
		special.add(new ValkMagnet());
		special.add(new YamiForm());
	}
	
	public static void debugCheckLists()
	{
		setupListList();
		Util.log("Debug Printing: Booster Pack Lists Size Check");
		int counter = 0;
		for (ArrayList<AbstractCard> list : lists)
		{
			if (list.size() < 1) { Util.log("Got an empty list! List Index: " + counter); }
			counter++;
		}
	}
	
	private static void setupDeckPool(ArrayList<CardTags> decktags)
	{
		deckTyped = new ArrayList<AbstractCard>();
		ArrayList<String> added = new ArrayList<String>();
		for (AbstractCard c : DuelistMod.coloredCards)
		{
			for (CardTags t : decktags)
			{
				if (c.hasTag(t) && !added.contains(c.name))
				{
					deckTyped.add(c.makeStatEquivalentCopy());
					added.add(c.name);
					if (c.rarity.equals(CardRarity.RARE)) { rareDeckTyped.add(c.makeStatEquivalentCopy()); Util.log("Added " + c.name + " to Rare Deck Booster");}
					Util.log("Added " + c.name + " to Deck Booster");
				}
			}
		}
	}
	
	public static void setupMetronomePools()
	{
		metronomes.clear();
		for (AbstractCard c : DuelistMod.myCards)
		{
			if (c.hasTag(Tags.METRONOME))
			{
				metronomes.add(c.makeStatEquivalentCopy());
			}			
		}
	}
	
	public static void setupPoolsForPacks()
	{
		setupOrbsPack();
		clearLists();
		setupSpecialList();
		Util.log("Resetting booster pack pools. Colored cards size: " + DuelistMod.coloredCards.size());
		for (AbstractCard c : DuelistMod.myCards)
		{
			boolean okToAdd = true;
			if (c.rarity.equals(CardRarity.BASIC) || c.rarity.equals(CardRarity.SPECIAL)) { okToAdd = false; }
			if (okToAdd)
			{
				if (c.hasTag(Tags.DRAGON))
				{
					dragons.add(c.makeStatEquivalentCopy());
					if (c.rarity.equals(CardRarity.RARE))
					{
						rareDragons.add(c.makeStatEquivalentCopy());
					}
				}
				
				if (c.hasTag(Tags.SPELLCASTER) && c.rarity.equals(CardRarity.RARE))
				{
					rareSpellcasters.add(c.makeStatEquivalentCopy());
				}
				
				if (c.hasTag(Tags.EXODIA))
				{
					exodia.add(c.makeStatEquivalentCopy());
				}
				
				fullPool.add(c.makeStatEquivalentCopy()); 
				if (!c.rarity.equals(CardRarity.COMMON)) { fullPoolNonCommon.add(c.makeStatEquivalentCopy());  }
			}
		}
		
		for (AbstractCard c : DuelistMod.coloredCards)
		{
			boolean okToAdd = true;
			if (c.rarity.equals(CardRarity.BASIC) || c.rarity.equals(CardRarity.SPECIAL)) { okToAdd = false; }
			if (okToAdd)
			{
				if (c.rarity.equals(CardRarity.COMMON))
				{
					commons.add(c.makeStatEquivalentCopy());
					nonRares.add(c.makeStatEquivalentCopy());
					if (c.type.equals(CardType.SKILL))
					{
						skills.add(c.makeStatEquivalentCopy());						
						nonRareSkills.add(c.makeStatEquivalentCopy());						
						commonSkills.add(c.makeStatEquivalentCopy());						
					}
					if (c.type.equals(CardType.POWER))
					{
						powers.add(c.makeStatEquivalentCopy());						
						nonRarePowers.add(c.makeStatEquivalentCopy());						
						commonPowers.add(c.makeStatEquivalentCopy());						
					}
					if (c.type.equals(CardType.ATTACK))
					{
						attacks.add(c.makeStatEquivalentCopy());						
						nonRareAttacks.add(c.makeStatEquivalentCopy());						
						commonAttacks.add(c.makeStatEquivalentCopy());						
					}
					
					if (c.hasTag(Tags.SPELL))
					{
						spells.add(c.makeStatEquivalentCopy());
					}
					
					if (c.hasTag(Tags.TRAP))
					{
						traps.add(c.makeStatEquivalentCopy());
					}
					
					if (c.hasTag(Tags.MONSTER))
					{
						monsters.add(c.makeStatEquivalentCopy());
						DuelistCard dc = (DuelistCard)c;
						if (dc.summons > 0)
						{
							summons.add(c.makeStatEquivalentCopy());
						}
					}
				}
				
				if (c.rarity.equals(CardRarity.UNCOMMON))
				{
					uncommons.add(c.makeStatEquivalentCopy());
					nonCommon.add(c.makeStatEquivalentCopy());
					nonRares.add(c.makeStatEquivalentCopy());
					if (c.type.equals(CardType.POWER))
					{
						powers.add(c.makeStatEquivalentCopy());						
						nonRarePowers.add(c.makeStatEquivalentCopy());						
						nonCommonPowers.add(c.makeStatEquivalentCopy());						
					}
					if (c.type.equals(CardType.SKILL))
					{
						skills.add(c.makeStatEquivalentCopy());						
						nonRareSkills.add(c.makeStatEquivalentCopy());					
						nonCommonSkills.add(c.makeStatEquivalentCopy());						
					}
					if (c.type.equals(CardType.ATTACK))
					{
						attacks.add(c.makeStatEquivalentCopy());						
						nonRareAttacks.add(c.makeStatEquivalentCopy());						
						nonCommonAttacks.add(c.makeStatEquivalentCopy());											
					}
					
					if (c.hasTag(Tags.SPELL))
					{
						spells.add(c.makeStatEquivalentCopy());
					}
					
					if (c.hasTag(Tags.TRAP))
					{
						traps.add(c.makeStatEquivalentCopy());
					}
					
					if (c.hasTag(Tags.MONSTER))
					{
						monsters.add(c.makeStatEquivalentCopy());
						DuelistCard dc = (DuelistCard)c;
						if (dc.summons > 0)
						{
							summons.add(c.makeStatEquivalentCopy());
						}
					}
				}
				
				if (c.rarity.equals(CardRarity.RARE))
				{
					rares.add(c.makeStatEquivalentCopy());
					nonCommon.add(c.makeStatEquivalentCopy());
					if (c.type.equals(CardType.POWER))
					{
						powers.add(c.makeStatEquivalentCopy());	
						rarePowers.add(c.makeStatEquivalentCopy());						
						nonCommonPowers.add(c.makeStatEquivalentCopy());						
					}
					if (c.type.equals(CardType.SKILL))
					{
						skills.add(c.makeStatEquivalentCopy());						
						rareSkills.add(c.makeStatEquivalentCopy());						
						nonCommonSkills.add(c.makeStatEquivalentCopy());						
					}
					if (c.type.equals(CardType.ATTACK))
					{
						attacks.add(c.makeStatEquivalentCopy());						
						rareAttacks.add(c.makeStatEquivalentCopy());						
						nonCommonAttacks.add(c.makeStatEquivalentCopy());						
					}
					
					if (c.hasTag(Tags.SPELL))
					{
						spells.add(c.makeStatEquivalentCopy());
						rareSpells.add(c.makeStatEquivalentCopy());
					}
					
					if (c.hasTag(Tags.TRAP))
					{
						traps.add(c.makeStatEquivalentCopy());
						rareTraps.add(c.makeStatEquivalentCopy());
					}
					
					if (c.hasTag(Tags.MONSTER))
					{
						monsters.add(c.makeStatEquivalentCopy());
						DuelistCard dc = (DuelistCard)c;
						if (dc.summons > 0)
						{
							summons.add(c.makeStatEquivalentCopy());
							rareSummons.add(c.makeStatEquivalentCopy());
						}
					}
				}
			}
		}
	}
	
	public static RewardItem getBonusBoosterDebug(boolean archetypeDeck)
	{
		RewardItem toReturn = new BoosterReward(deckBoosterPack(), "DeckBooster", "Archetype Booster", 6000, 0);
		if (toReturn.cards.size() < bonusPackSize) { return getBonusBooster(false, archetypeDeck); }
		return toReturn;
	}
	
	public static RewardItem getBonusBooster(boolean special, boolean archetypeDeck)
	{
		int roll = AbstractDungeon.cardRandomRng.random(1, 100);
		RewardItem toReturn = new BoosterReward(uncommonBoosterPackB(), "UncommonBooster", "Uncommon Booster", roll, 0);
		if (special) { roll = 101; }
		if (!archetypeDeck)
		{
			if (roll <= 25)
			{
				toReturn = new BoosterReward(spellsBoosterPack(), "SpellBooster", "Spell Booster", roll + 200, spellPackCost);
			}
			else if (roll <= 44)
			{
				toReturn = new BoosterReward(trapsBoosterPack(), "TrapBooster", "Trap Booster", roll + 200, trapPackCost);
			}
			else if (roll <= 72)
			{
				toReturn = new BoosterReward(summonsBoosterPack(), "SummonBooster", "Summons Booster", roll + 200, summonPackCost);
			}
			else if (roll <= 79)
			{
				toReturn = new BoosterReward(rareDragonsBoosterPack(), "RareDragons", "Rare Dragon Booster", roll + 200, rareDragPackCost);
			}
			else if (roll <= 86)
			{
				toReturn = new BoosterReward(rareSpellcasterBoosterPack(), "RareDragons", "Rare Spellcaster Booster", roll + 200, rareSpellcasterPackCost);
			}
			else if (roll <= 100)
			{
				toReturn = new BoosterReward(dragonsBoosterPack(), "DragonBooster", "Dragon Booster", roll + 200, dragPackCost);
			}
			else
			{
				toReturn = new BoosterReward(specialBoosterPack(), "SpecialBooster", "Special Booster", roll + 200, specialPackCost);
			}
		}
		else
		{
			if (roll <= 21)
			{
				toReturn = new BoosterReward(spellsBoosterPack(), "SpellBooster", "Spell Booster", roll + 200, spellPackCost);
			}
			else if (roll <= 36)
			{
				toReturn = new BoosterReward(trapsBoosterPack(), "TrapBooster", "Trap Booster", roll + 200, trapPackCost);
			}
			else if (roll <= 60)
			{
				toReturn = new BoosterReward(summonsBoosterPack(), "SummonBooster", "Summons Booster", roll + 200, summonPackCost);
			}
			else if (roll <= 65)
			{
				toReturn = new BoosterReward(rareDragonsBoosterPack(), "RareDragons", "Rare Dragon Booster", roll + 200, rareDragPackCost);
			}
			else if (roll <= 70)
			{
				toReturn = new BoosterReward(rareSpellcasterBoosterPack(), "RareDragons", "Rare Spellcaster Booster", roll + 200, rareSpellcasterPackCost);
			}
			else if (roll <= 80)
			{
				toReturn = new BoosterReward(dragonsBoosterPack(), "DragonBooster", "Dragon Booster", roll + 200, dragPackCost);
			}
			else if (roll <= 100)
			{
				toReturn = new BoosterReward(deckBoosterPack(), "DeckBooster", "Archetype Booster", roll + 200, deckPackCost);
			}
			else
			{
				toReturn = new BoosterReward(specialBoosterPack(), "SpecialBooster", "Special Booster", roll + 200, specialPackCost);
			}
		}
		
		if (toReturn.cards.size() < bonusPackSize) { Util.log("bonus booster for roll " + roll + " in getBonusBooster() was too small, so we got a new onetossed booster size: " + toReturn.cards.size()); return getBonusBooster(false, false); }
		else { Util.log("bonus booster for roll " + roll + " was all good on size. booster size: " + toReturn.cards.size()); }
		return toReturn;
	}
	
	public static RewardItem getMetronomeBooster()
	{
		setupMetronomePools();
		ArrayList<AbstractCard> mets = getRandomCards(metronomes, 2);
		RewardItem toReturn = new MetronomeReward(mets, "SillyPack", "Metronome Booster", 0);
		return toReturn;
	}
	
	public static RewardItem getRandomBooster(boolean spellcasterDeck, int roll)
	{
		setupPoolsForPacks();
		if (AbstractDungeon.player.hasRelic(NlothsGift.ID)) { roll+= 36; }
		RewardItem toReturn = new BoosterReward(uncommonBoosterPackB(), "UncommonBooster", "Uncommon Booster", roll, 0);
		if (spellcasterDeck)
		{
			// Common - 3%
			if (roll <= 3)
			{
				toReturn = new BoosterReward(commonBoosterPack(), "CommonBooster", "Common Booster", roll, commonPackCost);
			}
			
			// Orb/Exodia - 3%
			else if (roll <= 6)
			{
				toReturn = new BoosterReward(orbBoosterPack(), "OrbBooster", "Orb Booster", roll, orbPackCost);
				boolean exodia = false;
				for (AbstractCard c : toReturn.cards) { if (c.hasTag(Tags.EXODIA)) { exodia = true; }}
				if (exodia) { toReturn = new BoosterReward(toReturn.cards, "OrbBooster", "Exodia Booster", roll, exodiaPackCost); }
			}
			
			// Uncommon - 30%
			else if (roll <= 36)
			{
				toReturn = new BoosterReward(uncommonBoosterPack(), "UncommonBooster", "Uncommon Booster", roll, uncommonPackCost);
			}
			
			// Skill - 10%
			else if (roll <= 46)
			{
				toReturn = new BoosterReward(skillBoosterPack(), "SkillBooster", "Skill Booster", roll, skillPackCost);
			}
			
			// Attack - 16%
			else if (roll <= 62)
			{
				toReturn = new BoosterReward(attackBoosterPack(), "AttackBooster", "Attack Booster", roll, attackPackCost);
			}
			
			// All Uncommon - 10%
			else if (roll <= 72)
			{
				toReturn = new BoosterReward(allUncommonBoosterPack(), "AllUncommonBooster", "All Uncommon Booster", roll, allUncommonPackCost);
			}
			
			// Rare - 8%
			else if (roll <= 80)
			{
				toReturn = new BoosterReward(rareBoosterPack(), "RareBooster", "Rare Booster", roll, rarePackCost);
				
			}
			
			// Multi-Type - 7%
			else if (roll <= 87)
			{
				toReturn = new BoosterReward(multitypeBoosterPack(), "MultiTypeBooster", "Multi-Type Booster", roll, multitypePackCost);
			}
			
			// Power - 4%
			else if (roll <= 91)
			{
				toReturn = new BoosterReward(powerBoosterPack(), "PowerBooster", "Power Booster", roll, powerPackCost);
			}
			
			// Rare Skill - 2%
			else if (roll <= 93)
			{
				toReturn = new BoosterReward(rareSkillBoosterPack(), "RareSkills", "Rare Skill Booster", roll, rareSkillPackCost);
			}
			
			// Rare Attack - 2%
			else if (roll <= 95)
			{
				toReturn = new BoosterReward(rareAttackBoosterPack(), "RareAttacks", "Rare Attack Booster", roll, rareAttackPackCost);
			}
			
			// Rare Power - 2%
			else if (roll <= 97)
			{
				toReturn = new BoosterReward(rarePowerBoosterPack(), "RarePowerBooster", "Rare Power Booster", roll, rarePowerPackCost);
			}
			
			// Silly Pack - 2%
			else if (roll <= 99)
			{
				int gcRoll = AbstractDungeon.cardRandomRng.random(100, 200);
				toReturn = new BoosterReward(sillyBoosterPack(), "SillyPack", "Bonanza Booster", roll, gcRoll);
			}
			
			// All Rares - 1%
			else if (roll >= 100)
			{
				toReturn = new BoosterReward(allRaresBoosterPack(), "AllRaresBooster", "All Rare Booster", roll, allRarePackCost);
			}
		}
		else 
		{
			// Common - 4%
			if (roll <= 4)
			{
				toReturn = new BoosterReward(commonBoosterPack(), "CommonBooster", "Common Booster", roll, commonPackCost);
			}
			
			// Uncommon - 32%
			else if (roll <= 32)
			{
				toReturn = new BoosterReward(uncommonBoosterPack(), "UncommonBooster", "Uncommon Booster", roll, uncommonPackCost);
			}
			
			// Skill - 10%
			else if (roll <= 46)
			{
				toReturn = new BoosterReward(skillBoosterPack(), "SkillBooster", "Skill Booster", roll, skillPackCost);
			}
			
			// Attack - 16%
			else if (roll <= 62)
			{
				toReturn = new BoosterReward(attackBoosterPack(), "AttackBooster", "Attack Booster", roll, attackPackCost);
			}
			
			// All Uncommon - 10%
			else if (roll <= 72)
			{
				toReturn = new BoosterReward(allUncommonBoosterPack(), "AllUncommonBooster", "All Uncommon Booster", roll, allUncommonPackCost);
			}
			
			// Rare - 8%
			else if (roll <= 80)
			{
				toReturn = new BoosterReward(rareBoosterPack(), "RareBooster", "Rare Booster", roll, rarePackCost);
				
			}
			
			// Multi-Type - 7%
			else if (roll <= 87)
			{
				toReturn = new BoosterReward(multitypeBoosterPack(), "MultiTypeBooster", "Multi-Type Booster", roll, multitypePackCost);
			}
			
			// Power - 4%
			else if (roll <= 91)
			{
				toReturn = new BoosterReward(powerBoosterPack(), "PowerBooster", "Power Booster", roll, powerPackCost);
			}
			
			// Rare Skill - 2%
			else if (roll <= 93)
			{
				toReturn = new BoosterReward(rareSkillBoosterPack(), "RareSkills", "Rare Skill Booster", roll, rareSkillPackCost);
			}
			
			// Rare Attack - 2%
			else if (roll <= 95)
			{
				toReturn = new BoosterReward(rareAttackBoosterPack(), "RareAttacks", "Rare Attack Booster", roll, rareAttackPackCost);
			}
			
			// Rare Power - 2%
			else if (roll <= 97)
			{
				toReturn = new BoosterReward(rarePowerBoosterPack(), "RarePowerBooster", "Rare Power Booster", roll, rarePowerPackCost);
			}
			
			// Silly Pack - 2%
			else if (roll <= 99)
			{
				int gcRoll = AbstractDungeon.cardRandomRng.random(100, 200);
				toReturn = new BoosterReward(sillyBoosterPack(), "SillyPack", "Bonanza Booster", roll, gcRoll);
			}
			
			// All Rares - 1%
			else if (roll >= 100)
			{
				toReturn = new BoosterReward(allRaresBoosterPack(), "AllRaresBooster", "All Rare Booster", roll, allRarePackCost);
			}
		}
		
		if (toReturn.cards.size() < normalPackSize) { Util.log("booster for roll " + roll + " in getRandomBooster() was too small, so we got a new one. tossed booster size: " + toReturn.cards.size());  return getRandomBooster(spellcasterDeck, AbstractDungeon.cardRandomRng.random(1, 100)); }
		else { Util.log("booster for roll " + roll + " in getRandomBooster() was all good on size. booster size: " + toReturn.cards.size());  }
		return toReturn;
	}

	public static void generateBonusBooster(boolean special)
	{
		AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getBonusBooster(special, false));
	}
	
	public static void generateBoosterOnVictory(int lastPackRoll, ArrayList<CardTags> deckType)
	{
		generateBoosterOnVictory(lastPackRoll, false, deckType);
	}
	
	
	public static void generateBoosterOnVictory(int lastPackRoll, boolean eliteVictory, ArrayList<CardTags> deckType)
	{
		boolean archetypeDeck = deckType != null;
		if (archetypeDeck) { setupDeckPool(deckType); }
		
		// If player allows booster packs or always wants them to appear, but doesn't have card rewards removed
		if ((DuelistMod.allowBoosters || DuelistMod.alwaysBoosters) && !DuelistMod.removeCardRewards)
		{
			// If always appearing, force the roll
			if (DuelistMod.alwaysBoosters) { lastPackRoll = 7; }
			
			// Rolling to see if we receive a booster pack this combat
			if (lastPackRoll <= 6)
			{
				int packRoll = AbstractDungeon.cardRandomRng.random(lastPackRoll, 6);
				
				// Roll for bonus Spell/Trap only booster - guarantee if elite victory
				int bonusPackRoll = AbstractDungeon.cardRandomRng.random(1, 25);
				if (AbstractDungeon.player.hasRelic(BoosterAlwaysBonusRelic.ID) || eliteVictory) { bonusPackRoll = 1; }
				
				// We rolled a booster reward
				if (packRoll == 6)
				{
					// Roll to see which booster pack we will receive
					int roll = AbstractDungeon.cardRandomRng.random(1, 100);
					
					// Manipulate roll if we have extra percent chance at All Rares Boosters
					if (AbstractDungeon.player.hasRelic(BoosterExtraAllRaresRelic.ID)) 
					{
						BoosterExtraAllRaresRelic rel = (BoosterExtraAllRaresRelic) AbstractDungeon.player.getRelic(BoosterExtraAllRaresRelic.ID);
						int secondRoll = AbstractDungeon.cardRandomRng.random(1,2);
						if (secondRoll == 1) { roll = 101; rel.flash(); }
						
						// If we miss the guarantee All Rares booster, check for other relics that may change roll
						else
						{
							if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID)) { roll+=30; }
							if (AbstractDungeon.player.hasRelic(BoosterAlwaysSillyRelic.ID)) { roll = 99; }
						}
					}
					
					// Manipulate roll if we have better boosters or always silly boosters
					else
					{
						if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID)) { roll+=30; }
						if (AbstractDungeon.player.hasRelic(BoosterAlwaysSillyRelic.ID)) { roll = 99; }
					}
					
					// Manipulate booster roll if elite victory
					// 50% chance to roll over Rare Booster, or otherwise no Common Boosters
					if (roll < 80 && eliteVictory) 
					{ 
						int eliteRoll = AbstractDungeon.cardRandomRng.random(1, 2);
						if (eliteRoll == 1)
						{
							roll = AbstractDungeon.cardRandomRng.random(80, 100);
						}
						else if (roll < 4)
						{
							roll = AbstractDungeon.cardRandomRng.random(10, 100);
						}
					}
					
					// Check if we are playing Spellcasters, if so we can roll Orb Boosters
					boolean spellcasterDeck = (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Spellcaster Deck"));
					
					// Receive random booster based on deck type and our roll
					AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getRandomBooster(spellcasterDeck, roll));
					
					// Add a 2nd Booster for Prayer Wheel
					if (AbstractDungeon.player.hasRelic(PrayerWheel.ID)) 
					{
						AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getRandomBooster(spellcasterDeck, roll));
					}
					
					// Reset roll chances for next combat
					DuelistMod.lastPackRoll = 0;
					
					// Debug log
					Util.log("Rolled and added a booster pack reward");
				}
				
				// We did not roll a booster reward
				// Roll to see if we increment booster roll chances for next combat, or just increment them if elite victory
				else
				{
					int incRoll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (incRoll == 1 || eliteVictory) { DuelistMod.lastPackRoll++; }
				}
				
				// Receive bonus Spell/Trap pack if we got one
				if ((bonusPackRoll == 1 || eliteVictory) && packRoll != 6)
				{
					AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getBonusBooster(false, archetypeDeck));
					Util.log("Rolled and added a BONUS booster pack reward!");
				}
			}
			
			// Forced booster roll - same as above just in a different order and doesn't roll to see if we get a booster, always gives one
			// Bonus Booster chance is decreased
			else
			{
				int roll = AbstractDungeon.cardRandomRng.random(1, 100);
				int bonusPackRoll = AbstractDungeon.cardRandomRng.random(1, 50);
				if (AbstractDungeon.player.hasRelic(BoosterAlwaysBonusRelic.ID) && !eliteVictory) { bonusPackRoll = 1; }
				if (AbstractDungeon.player.hasRelic(BoosterExtraAllRaresRelic.ID)) 
				{
					BoosterExtraAllRaresRelic rel = (BoosterExtraAllRaresRelic) AbstractDungeon.player.getRelic(BoosterExtraAllRaresRelic.ID);
					int secondRoll = AbstractDungeon.cardRandomRng.random(1,2);
					if (secondRoll == 1) { roll = 101; rel.flash(); }
					else
					{
						if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID)) { roll+=30; }
						if (AbstractDungeon.player.hasRelic(BoosterAlwaysSillyRelic.ID)) { roll = 99; }
					}
				}
				else
				{
					if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID)) { roll+=30; }
					if (AbstractDungeon.player.hasRelic(BoosterAlwaysSillyRelic.ID)) { roll = 99; }
				}
				if (roll < 80 && eliteVictory) 
				{ 
					int eliteRoll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (eliteRoll == 1)
					{
						roll = AbstractDungeon.cardRandomRng.random(80, 100);
					}
					else
					{
						roll = AbstractDungeon.cardRandomRng.random(10, 100);
					}
				}
				boolean spellcasterDeck = (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Spellcaster Deck"));
				AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getRandomBooster(spellcasterDeck, roll));
				if (AbstractDungeon.player.hasRelic(PrayerWheel.ID)) { AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getRandomBooster(spellcasterDeck, roll)); }
				Util.log("Rolled and added a booster pack reward");
				DuelistMod.lastPackRoll = 0;
				if (bonusPackRoll == 1 && !eliteVictory)
				{
					AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getBonusBooster(false, archetypeDeck));
					Util.log("Rolled and added a BONUS booster pack reward!");
				}
			}
		}
		
		// Player has removed card rewards, so we just need to do a Bonus Booster roll
		if (DuelistMod.removeCardRewards)
		{
			if (DuelistMod.alwaysBoosters)
			{
				int bonusPackRoll = AbstractDungeon.cardRandomRng.random(1, 50);
				if (AbstractDungeon.player.hasRelic(BoosterAlwaysBonusRelic.ID) && !eliteVictory) { bonusPackRoll = 1; }
				if (bonusPackRoll == 1 && !eliteVictory)
				{
					AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getBonusBooster(false, archetypeDeck));
					Util.log("Rolled and added a BONUS booster pack reward!");
				}
			}
			else if (DuelistMod.allowBoosters)
			{
				int bonusPackRoll = AbstractDungeon.cardRandomRng.random(1, 25);
				if (AbstractDungeon.player.hasRelic(BoosterAlwaysBonusRelic.ID) && !eliteVictory) { bonusPackRoll = 1; }
				if ((bonusPackRoll == 1 || eliteVictory) && DuelistMod.lastPackRoll != 6)
				{
					AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getBonusBooster(false, archetypeDeck));
					Util.log("Rolled and added a BONUS booster pack reward!");
				}
			}
			
			// Check for Prayer Wheel if removed card rewards
			if (AbstractDungeon.player.hasRelic(PrayerWheel.ID)) 
			{ 
				boolean spellcasterDeck = (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Spellcaster Deck"));
				int roll = AbstractDungeon.cardRandomRng.random(1, 100);
				AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getRandomBooster(spellcasterDeck, roll)); 
			}			
		}
	}
	
	public static RewardItem replaceMetronomeReward()
	{
		return BoosterPackHelper.getMetronomeBooster();
	}
	
	public static RewardItem replaceCardReward(int lastPackRoll, boolean eliteVictory, ArrayList<CardTags> deckType)
	{
		if (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters)
		{
			boolean archetypeDeck = deckType != null;
			if (archetypeDeck) { setupDeckPool(deckType); }
			
			// If always appearing, force the roll
			if (DuelistMod.alwaysBoosters) { lastPackRoll = 7; }
			
			// Rolling to see if we receive a booster pack this combat
			if (lastPackRoll <= 6)
			{
				int packRoll = AbstractDungeon.cardRandomRng.random(lastPackRoll, 6);
			
				// We rolled a booster reward
				if (packRoll == 6)
				{
					// Roll to see which booster pack we will receive
					int roll = AbstractDungeon.cardRandomRng.random(1, 100);
					
					// Manipulate roll if we have extra percent chance at All Rares Boosters
					if (AbstractDungeon.player.hasRelic(BoosterExtraAllRaresRelic.ID)) 
					{
						BoosterExtraAllRaresRelic rel = (BoosterExtraAllRaresRelic) AbstractDungeon.player.getRelic(BoosterExtraAllRaresRelic.ID);
						int secondRoll = AbstractDungeon.cardRandomRng.random(1,2);
						if (secondRoll == 1) { roll = 101; rel.flash(); }
						
						// If we miss the guarantee All Rares booster, check for other relics that may change roll
						else
						{
							if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID)) { roll+=30; }
							if (AbstractDungeon.player.hasRelic(BoosterAlwaysSillyRelic.ID)) { roll = 99; }
						}
					}
					
					// Manipulate roll if we have better boosters or always silly boosters
					else
					{
						if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID)) { roll+=30; }
						if (AbstractDungeon.player.hasRelic(BoosterAlwaysSillyRelic.ID)) { roll = 99; }
					}
					
					// Manipulate booster roll if elite victory
					// 50% chance to roll over Rare Booster, or otherwise no Common Boosters
					if (roll < 80 && eliteVictory) 
					{ 
						int eliteRoll = AbstractDungeon.cardRandomRng.random(1, 2);
						if (eliteRoll == 1)
						{
							roll = AbstractDungeon.cardRandomRng.random(80, 100);
						}
						else if (roll < 4)
						{
							roll = AbstractDungeon.cardRandomRng.random(10, 100);
						}
					}
					
					// Check if we are playing Spellcasters, if so we can roll Orb Boosters
					boolean spellcasterDeck = (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Spellcaster Deck"));
					
					// Reset roll chances for next combat
					DuelistMod.lastPackRoll = 0;
					
					// Debug log
					Util.log("Rolled and added a booster pack reward");
					
					// Receive random booster based on deck type and our roll
					return BoosterPackHelper.getRandomBooster(spellcasterDeck, roll);
				}
				else
				{
					int incRoll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (incRoll == 1 || eliteVictory) { DuelistMod.lastPackRoll++; }
					RewardItem empty = new RewardItem();
					empty.cards = new ArrayList<AbstractCard>();
					return empty;
				}
			}
			
			// Forced booster roll - same as above just in a different order and doesn't roll to see if we get a booster, always gives one
			// Bonus Booster chance is decreased
			else
			{
				int roll = AbstractDungeon.cardRandomRng.random(1, 100);
				if (AbstractDungeon.player.hasRelic(BoosterExtraAllRaresRelic.ID)) 
				{
					BoosterExtraAllRaresRelic rel = (BoosterExtraAllRaresRelic) AbstractDungeon.player.getRelic(BoosterExtraAllRaresRelic.ID);
					int secondRoll = AbstractDungeon.cardRandomRng.random(1,2);
					if (secondRoll == 1) { roll = 101; rel.flash(); }
					else
					{
						if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID)) { roll+=30; }
						if (AbstractDungeon.player.hasRelic(BoosterAlwaysSillyRelic.ID)) { roll = 99; }
					}
				}
				else
				{
					if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID)) { roll+=30; }
					if (AbstractDungeon.player.hasRelic(BoosterAlwaysSillyRelic.ID)) { roll = 99; }
				}
				if (roll < 80 && eliteVictory) 
				{ 
					int eliteRoll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (eliteRoll == 1)
					{
						roll = AbstractDungeon.cardRandomRng.random(80, 100);
					}
					else
					{
						roll = AbstractDungeon.cardRandomRng.random(10, 100);
					}
				}
				DuelistMod.lastPackRoll = 0;
				boolean spellcasterDeck = (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Spellcaster Deck"));
				return BoosterPackHelper.getRandomBooster(spellcasterDeck, roll);
			}
		}
		else
		{
			RewardItem empty = new RewardItem();
			empty.cards = new ArrayList<AbstractCard>();
			return empty;
		}
	}
	
	public static String getPackName(int id, boolean bonus)
	{
		String toReturn = "";
		if (bonus)
		{
			id -= 200;
			if (id <= 15)
			{
				toReturn = "Spell Booster";
			}
			else if (id <= 28)
			{
				toReturn = "Trap Booster";
			}
			else if (id <= 48)
			{
				toReturn = "Summons Booster";
			}
			else if (id <= 53)
			{
				toReturn = "Rare Dragon Booster";
			}
			else if (id <= 58)
			{
				toReturn = "Rare Spellcaster Booster";
			}
			else if (id <= 80)
			{
				toReturn = "Dragon Booster";
			}
			else if (id <= 100)
			{
				toReturn = "Archetype Booster";
			}
			else
			{
				toReturn = "Special Booster";
			}
		}
		else
		{
			// Common - 4%
			if (id <= 4)
			{
				toReturn = "Common Booster";
			}
			
			// Uncommon - 32%
			else if (id <= 32)
			{
				toReturn = "Uncommon Booster";
			}
			
			// Skill - 10%
			else if (id <= 46)
			{
				toReturn = "Skill Booster";
			}
			
			// Attack - 16%
			else if (id <= 62)
			{
				toReturn = "Attack Booster";
			}
			
			// All Uncommon - 10%
			else if (id <= 72)
			{
				toReturn = "All Uncommon Booster";
			}
			
			// Rare - 8%
			else if (id <= 80)
			{
				toReturn = "Rare Booster";
				
			}
			
			// Multi-Type - 7%
			else if (id <= 87)
			{
				toReturn = "Multi-Type Booster";
			}
			
			// Power - 4%
			else if (id <= 91)
			{
				toReturn = "Power Booster";
			}
			
			// Rare Skill - 2%
			else if (id <= 93)
			{
				toReturn = "Rare Skill Booster";
			}
			
			// Rare Attack - 2%
			else if (id <= 95)
			{
				toReturn = "Rare Attack Booster";
			}
			
			// Rare Power - 2%
			else if (id <= 97)
			{
				toReturn = "Rare Power Booster";
			}
			
			// Silly Pack - 2%
			else if (id <= 99)
			{
				toReturn = "Bonanza Booster";
			}
			
			// All Rares - 1%
			else if (id >= 100)
			{
				toReturn = "All Rare Booster";
			}
		}
		return toReturn;
	}
	
	public static String getIMG(int id, boolean bonus)
	{
		String toReturn = "";
		if (bonus)
		{
			id -= 200;
			if (id <= 15)
			{
				toReturn = "SpellBooster";
			}
			else if (id <= 28)
			{
				toReturn = "TrapBooster";
			}
			else if (id <= 48)
			{
				toReturn = "SummonBooster";
			}
			else if (id <= 53)
			{
				toReturn = "RareDragons";
			}
			else if (id <= 58)
			{
				toReturn = "RareDragons";
			}
			else if (id <= 80)
			{
				toReturn = "DragonBooster";
			}
			else if (id <= 100)
			{
				toReturn = "DeckBooster";
			}
			else
			{
				toReturn = "SpecialBooster";
			}
		}
		else
		{
			// Common - 4%
			if (id <= 4)
			{
				toReturn = "CommonBooster";
			}
			
			// Uncommon - 32%
			else if (id <= 32)
			{
				toReturn = "UncommonBooster";
			}
			
			// Skill - 10%
			else if (id <= 46)
			{
				toReturn = "SkillBooster";
			}
			
			// Attack - 16%
			else if (id <= 62)
			{
				toReturn = "AttackBooster";
			}
			
			// All Uncommon - 10%
			else if (id <= 72)
			{
				toReturn = "AllUncommonBooster";
			}
			
			// Rare - 8%
			else if (id <= 80)
			{
				toReturn = "RareBooster";
				
			}
			
			// Multi-Type - 7%
			else if (id <= 87)
			{
				toReturn = "MultiTypeBooster";
			}
			
			// Power - 4%
			else if (id <= 91)
			{
				toReturn = "PowerBooster";
			}
			
			// Rare Skill - 2%
			else if (id <= 93)
			{
				toReturn = "RareSkills";
			}
			
			// Rare Attack - 2%
			else if (id <= 95)
			{
				toReturn = "RareAttacks";
			}
			
			// Rare Power - 2%
			else if (id <= 97)
			{
				toReturn = "RarePowerBooster";
			}
			
			// Silly Pack - 2%
			else if (id <= 99)
			{
				toReturn = "SillyPack";
			}
			
			// All Rares - 1%
			else if (id >= 100)
			{
				toReturn = "AllRaresBooster";
			}
		}
		return toReturn;
	}
	
	public static  ArrayList<AbstractCard> getMetronomeCardsFromID()
	{
		ArrayList<AbstractCard> mets = getRandomCards(metronomes, normalPackSize);
		return mets;
	}

	public static ArrayList<AbstractCard> getBoosterCardsFromID(int id, boolean bonus) 
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		Util.log("Loaded a new booster from ID: " + id + ", bonus=" + bonus);
		if (bonus)
		{
			id -= 200;
			if (id <= 15)
			{
				toReturn = spellsBoosterPack();
			}
			else if (id <= 28)
			{
				toReturn = trapsBoosterPack();
			}
			else if (id <= 48)
			{
				toReturn = summonsBoosterPack();
			}
			else if (id <= 53)
			{
				toReturn = rareDragonsBoosterPack();
			}
			else if (id <= 58)
			{
				toReturn = rareSpellcasterBoosterPack();
			}
			else if (id <= 80)
			{
				toReturn = dragonsBoosterPack();
			}
			else if (id <= 100)
			{
				toReturn = deckBoosterPack();
			}
			else
			{
				toReturn = specialBoosterPack();
			}
		}
		else
		{
			// Common - 4%
			if (id <= 4)
			{
				toReturn = commonBoosterPack();
			}
			
			// Uncommon - 32%
			else if (id <= 32)
			{
				toReturn = uncommonBoosterPack();
			}
			
			// Skill - 10%
			else if (id <= 46)
			{
				toReturn = skillBoosterPack();
			}
			
			// Attack - 16%
			else if (id <= 62)
			{
				toReturn = attackBoosterPack();
			}
			
			// All Uncommon - 10%
			else if (id <= 72)
			{
				toReturn = allUncommonBoosterPack();
			}
			
			// Rare - 8%
			else if (id <= 80)
			{
				toReturn = rareBoosterPack();
				
			}
			
			// Multi-Type - 7%
			else if (id <= 87)
			{
				toReturn = multitypeBoosterPack();
			}
			
			// Power - 4%
			else if (id <= 91)
			{
				toReturn = powerBoosterPack();
			}
			
			// Rare Skill - 2%
			else if (id <= 93)
			{
				toReturn = rareSkillBoosterPack();
			}
			
			// Rare Attack - 2%
			else if (id <= 95)
			{
				toReturn = rareAttackBoosterPack();
			}
			
			// Rare Power - 2%
			else if (id <= 97)
			{
				toReturn = rarePowerBoosterPack();
			}
			
			// Silly Pack - 2%
			else if (id <= 99)
			{
				toReturn = sillyBoosterPack();
			}
			
			// All Rares - 1%
			else if (id >= 100)
			{
				toReturn = allRaresBoosterPack();
			}
		}
		return toReturn;
	}
	
	public static int getGoldCostFromID(int id)
	{
		if (id > 200)
		{
			id -= 200;
			if (id <= 15)
			{
				return spellPackCost;
			}
			else if (id <= 28)
			{
				return trapPackCost;
			}
			else if (id <= 48)
			{
				return summonPackCost;
			}
			else if (id <= 53)
			{
				return rareDragPackCost;
			}
			else if (id <= 58)
			{
				return rareSpellcasterPackCost;
			}
			else if (id <= 80)
			{
				return dragPackCost;
			}
			else if (id <= 100)
			{
				return deckPackCost;
			}
			else
			{
				return specialPackCost;
			}
		}
		else
		{
			// Common - 4%
			if (id <= 4)
			{
				return commonPackCost;
			}
			
			// Uncommon - 32%
			else if (id <= 32)
			{
				return uncommonPackCost;
			}
			
			// Skill - 10%
			else if (id <= 46)
			{
				return skillPackCost;
			}
			
			// Attack - 16%
			else if (id <= 62)
			{
				return attackPackCost;
			}
			
			// All Uncommon - 10%
			else if (id <= 72)
			{
				return allUncommonPackCost;
			}
			
			// Rare - 8%
			else if (id <= 80)
			{
				return rarePackCost;
			}
			
			// Multi-Type - 7%
			else if (id <= 87)
			{
				return multitypePackCost;
			}
			
			// Power - 4%
			else if (id <= 91)
			{
				return powerPackCost;
			}
			
			// Rare Skill - 2%
			else if (id <= 93)
			{
				return rareSkillPackCost;
			}
			
			// Rare Attack - 2%
			else if (id <= 95)
			{
				return rareAttackPackCost;
			}
			
			// Rare Power - 2%
			else if (id <= 97)
			{
				return rarePowerPackCost;
			}
			
			// Silly Pack - 2%
			else if (id <= 99)
			{
				return sillyPackCost;
			}
			
			// All Rares - 1%
			else if (id >= 100)
			{
				return allRarePackCost;
			}
		}
		
		return 120;
	}
}
