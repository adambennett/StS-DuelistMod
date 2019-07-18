package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.DestructPotion;
import duelistmod.relics.*;
import duelistmod.rewards.BoosterReward;
import duelistmod.variables.Tags;

public class BoosterPackHelper 
{
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
	public static ArrayList<AbstractCard> traps = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> monsters = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> powers = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> attacks = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> skills = new ArrayList<AbstractCard>();
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
	public static ArrayList<AbstractCard> dragons = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareDragons = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> exodia = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareSkills = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareAttacks = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> special = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> fullPool = new ArrayList<AbstractCard>();
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
	}
	
	public static ArrayList<AbstractCard> skillBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = commonSkills;
		for (int i = 0; i < normalPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		int randIndex = AbstractDungeon.cardRandomRng.random(nonCommonSkills.size() - 1);
		AbstractCard randCard = nonCommonSkills.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(nonCommonSkills.size() - 1); randCard = nonCommonSkills.get(randIndex); }
		picked.add(randCard.name); toReturn.add(randCard);
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> attackBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = commonAttacks;
		for (int i = 0; i < normalPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		int randIndex = AbstractDungeon.cardRandomRng.random(nonCommonAttacks.size() - 1);
		AbstractCard randCard = nonCommonAttacks.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(nonCommonAttacks.size() - 1); randCard = nonCommonAttacks.get(randIndex); }
		picked.add(randCard.name); toReturn.add(randCard);
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> rareSkillBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = nonRareSkills;
		for (int i = 0; i < normalPackSize - 2; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		int randIndex = AbstractDungeon.cardRandomRng.random(rareSkills.size() - 1);
		AbstractCard randCard = rareSkills.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(rareSkills.size() - 1); randCard = rareSkills.get(randIndex); }
		picked.add(randCard.name); toReturn.add(randCard);
		
		randIndex = AbstractDungeon.cardRandomRng.random(rareSkills.size() - 1);
		randCard = rareSkills.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(rareSkills.size() - 1); randCard = rareSkills.get(randIndex); }
		picked.add(randCard.name); toReturn.add(randCard);
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> rareAttackBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = nonRareAttacks;
		for (int i = 0; i < normalPackSize - 2; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		int randIndex = AbstractDungeon.cardRandomRng.random(rareAttacks.size() - 1);
		AbstractCard randCard = rareAttacks.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(rareAttacks.size() - 1); randCard = rareAttacks.get(randIndex); }
		picked.add(randCard.name); toReturn.add(randCard);
		
		randIndex = AbstractDungeon.cardRandomRng.random(rareAttacks.size() - 1);
		randCard = rareAttacks.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(rareAttacks.size() - 1); randCard = rareAttacks.get(randIndex); }
		picked.add(randCard.name); toReturn.add(randCard);
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> commonBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = commons;
		for (int i = 0; i < normalPackSize; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> orbBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom;
		int roll = AbstractDungeon.cardRandomRng.random(1, 2);
		if (roll == 1 || !DuelistMod.exodiaBtnBool) { pullFrom = exodia; }
		else { pullFrom = orbs; }
		for (int i = 0; i < normalPackSize; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> uncommonBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = commons;
		
		// Commons - 4
		for (int i = 0; i < 4; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		// Uncommon - 1
		int randIndex = AbstractDungeon.cardRandomRng.random(uncommons.size() - 1);
		AbstractCard randCard = uncommons.get(randIndex);
		toReturn.add(randCard); picked.add(randCard.name);

		return toReturn;
	}
	
	
	public static ArrayList<AbstractCard> allUncommonBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = uncommons;
		for (int i = 0; i < normalPackSize; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to all uncommons booster");
		}
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> multitypeBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		
		// Monsters - 2
		int randIndex = AbstractDungeon.cardRandomRng.random(monsters.size() - 1);
		AbstractCard randCard = monsters.get(randIndex);
		toReturn.add(randCard); picked.add(randCard.name);
		
		randIndex = AbstractDungeon.cardRandomRng.random(monsters.size() - 1);
		randCard = monsters.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(monsters.size() - 1); randCard = monsters.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		
		// Spells - 2
		randIndex = AbstractDungeon.cardRandomRng.random(spells.size() - 1);
		randCard = spells.get(randIndex);
		toReturn.add(randCard); picked.add(randCard.name);
		
		randIndex = AbstractDungeon.cardRandomRng.random(spells.size() - 1);
		randCard = spells.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(spells.size() - 1); randCard = spells.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		
		// Trap - 1
		randIndex = AbstractDungeon.cardRandomRng.random(traps.size() - 1);
		randCard = traps.get(randIndex);
		toReturn.add(randCard); picked.add(randCard.name);
		
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> rareBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = commons;
		
		// Common - 3
		for (int i = 0; i < 3; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		// Rare - 1
		int randIndex = AbstractDungeon.cardRandomRng.random(rares.size() - 1);
		AbstractCard randCard = rares.get(randIndex);
		toReturn.add(randCard); picked.add(randCard.name);
		
		// Uncommon/Rare - 1
		randIndex = AbstractDungeon.cardRandomRng.random(nonCommon.size() - 1);
		randCard = nonCommon.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(nonCommon.size() - 1); randCard = nonCommon.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		
		return toReturn;
	}
	
	
	public static ArrayList<AbstractCard> powerBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = powers;
		for (int i = 0; i < normalPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		// 1 Rare Power
		int randIndex = AbstractDungeon.cardRandomRng.random(rarePowers.size() - 1);
		AbstractCard randCard = rarePowers.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(rarePowers.size() - 1); randCard = rarePowers.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> rarePowerBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = rarePowers;
		for (int i = 0; i < normalPackSize; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> sillyBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
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
	

	public static ArrayList<AbstractCard> allRaresBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = rares;
		for (int i = 0; i < normalPackSize; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		return toReturn;
	}

	
	public static ArrayList<AbstractCard> fatUncommonBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = nonRares;
		for (int i = 0; i < fatPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		int randIndex = AbstractDungeon.cardRandomRng.random(uncommons.size() - 1);
		AbstractCard randCard = uncommons.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(uncommons.size() - 1); randCard = uncommons.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		return toReturn;
	}
	

	public static ArrayList<AbstractCard> fatRareBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		ArrayList<AbstractCard> pullFrom = nonRares;
		
		// Uncommon/Common - 3
		for (int i = 0; i < 3; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			AbstractCard randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		// Uncommon - 1
		int randIndex = AbstractDungeon.cardRandomRng.random(uncommons.size() - 1);
		AbstractCard randCard = uncommons.get(randIndex);
		while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(uncommons.size() - 1); randCard = uncommons.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		
		// Rare - 1
		randIndex = AbstractDungeon.cardRandomRng.random(rares.size() - 1);
		randCard = rares.get(randIndex);
		toReturn.add(randCard); picked.add(randCard.name);
		
		// Uncommon/Rare - 2
		pullFrom = nonCommon;
		for (int i = 0; i < 2; i++)
		{
			randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1);
			randCard = pullFrom.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(pullFrom.size() - 1); randCard = pullFrom.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
		}
		
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> spellsBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		for (int i = 0; i < bonusPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(spells.size() - 1);
			AbstractCard randCard = spells.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(spells.size() - 1); randCard = spells.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to spells booster in loop");
		}
		
		// 1 Rare Spell
		int randIndex = AbstractDungeon.cardRandomRng.random(spells.size() - 1);
		AbstractCard randCard = spells.get(randIndex);
		while (picked.contains(randCard.name) || !randCard.rarity.equals(CardRarity.RARE)) { randIndex = AbstractDungeon.cardRandomRng.random(spells.size() - 1); randCard = spells.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		Util.log("Added " + randCard.name + " to spells booster");
		
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> trapsBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		for (int i = 0; i < bonusPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(traps.size() - 1);
			AbstractCard randCard = traps.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(traps.size() - 1); randCard = traps.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to traps booster in loop");
		}
		
		// 1 Rare Trap
		int randIndex = AbstractDungeon.cardRandomRng.random(traps.size() - 1);
		AbstractCard randCard = traps.get(randIndex);
		while (picked.contains(randCard.name) || !randCard.rarity.equals(CardRarity.RARE)) { randIndex = AbstractDungeon.cardRandomRng.random(traps.size() - 1); randCard = traps.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		Util.log("Added " + randCard.name + " to traps booster");
		
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> summonsBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		for (int i = 0; i < bonusPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(summons.size() - 1);
			AbstractCard randCard = summons.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(summons.size() - 1); randCard = summons.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to summons booster");
		}
		
		// 1 Rare Summons Card
		int randIndex = AbstractDungeon.cardRandomRng.random(summons.size() - 1);
		AbstractCard randCard = summons.get(randIndex);
		while (picked.contains(randCard.name) || !randCard.rarity.equals(CardRarity.RARE)) { randIndex = AbstractDungeon.cardRandomRng.random(summons.size() - 1); randCard = summons.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		Util.log("Added " + randCard.name + " to summons booster");
		
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> dragonsBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		for (int i = 0; i < bonusPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(dragons.size() - 1);
			AbstractCard randCard = dragons.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(dragons.size() - 1); randCard = dragons.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to dragons booster");
		}
		
		// 1 Rare Dragon
		int randIndex = AbstractDungeon.cardRandomRng.random(dragons.size() - 1);
		AbstractCard randCard = dragons.get(randIndex);
		while (picked.contains(randCard.name) || !randCard.rarity.equals(CardRarity.RARE)) { randIndex = AbstractDungeon.cardRandomRng.random(dragons.size() - 1); randCard = dragons.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		Util.log("Added " + randCard.name + " to dragons booster");
		
		return toReturn;
	}

	// Bonus
	public static ArrayList<AbstractCard> rareDragonsBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		for (int i = 0; i < bonusPackSize; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(rareDragons.size() - 1);
			AbstractCard randCard = rareDragons.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(rareDragons.size() - 1); randCard = rareDragons.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to rare dragons booster");
		}
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> specialBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		for (int i = 0; i < bonusPackSize; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(special.size() - 1);
			AbstractCard randCard = special.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(special.size() - 1); randCard = special.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to special booster");
		}
		return toReturn;
	}
	
	// Bonus
	public static ArrayList<AbstractCard> deckBoosterPack()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		ArrayList<String> picked = new ArrayList<String>();
		for (int i = 0; i < bonusPackSize - 1; i++)
		{
			int randIndex = AbstractDungeon.cardRandomRng.random(deckTyped.size() - 1);
			AbstractCard randCard = deckTyped.get(randIndex);
			while (picked.contains(randCard.name)) { randIndex = AbstractDungeon.cardRandomRng.random(deckTyped.size() - 1); randCard = deckTyped.get(randIndex); }
			picked.add(randCard.name); toReturn.add(randCard);
			Util.log("Added " + randCard.name + " to deck booster");
		}
		
		// 1 Rare Deck Type Card
		int randIndex = AbstractDungeon.cardRandomRng.random(deckTyped.size() - 1);
		AbstractCard randCard = deckTyped.get(randIndex);
		while (picked.contains(randCard.name) || !randCard.rarity.equals(CardRarity.RARE)) { randIndex = AbstractDungeon.cardRandomRng.random(deckTyped.size() - 1); randCard = traps.get(randIndex); }
		toReturn.add(randCard); picked.add(randCard.name);
		Util.log("Added " + randCard.name + " to deck booster");
		
		return toReturn;
	}

	private static void setupOrbsPack()
	{
		orbs = new ArrayList<AbstractCard>();
		for (AbstractCard c : DuelistMod.orbCards) { orbs.add(c.makeCopy()); }
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
				}
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
				
				if (c.hasTag(Tags.EXODIA))
				{
					exodia.add(c.makeStatEquivalentCopy());
				}
				
				fullPool.add(c.makeStatEquivalentCopy()); 
			}
		}
		
		for (AbstractCard c : DuelistMod.coloredCards)
		{
			boolean okToAdd = true;
			int counter = 1;
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
				}
				
				if (c.rarity.equals(CardRarity.UNCOMMON))
				{
					uncommons.add(c.makeStatEquivalentCopy()); Util.log("Added [" + counter + "]: " + c.name + " to uncommons"); counter++;
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
						commonAttacks.add(c.makeStatEquivalentCopy());						
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
		}
	}
	
	
	public static RewardItem getBonusBooster(boolean special, boolean archetypeDeck)
	{
		RewardItem toReturn = new BoosterReward(spellsBoosterPack(), "SpellBooster", "Spell Booster");
		int roll = AbstractDungeon.cardRandomRng.random(1, 100);
		if (!archetypeDeck && roll > 80) { roll = AbstractDungeon.cardRandomRng.random(1, 80); }
		if (special) { roll = 101; }
		if (roll <= 15)
		{
			toReturn = new BoosterReward(spellsBoosterPack(), "SpellBooster", "Spell Booster");
			for (AbstractCard c : spells)
			{
				Util.log("Rolled Spells, printing spells: " + c.name);
			}
		}
		else if (roll <= 28)
		{
			toReturn = new BoosterReward(trapsBoosterPack(), "TrapBooster", "Trap Booster");
			for (AbstractCard c : traps)
			{
				Util.log("Rolled Traps, printing traps: " + c.name);
			}
		}
		else if (roll <= 48)
		{
			toReturn = new BoosterReward(summonsBoosterPack(), "SummonBooster", "Summons Booster");
			for (AbstractCard c : summons)
			{
				Util.log("Rolled Summons, printing summons: " + c.name);
			}
		}
		else if (roll <= 58)
		{
			toReturn = new BoosterReward(rareDragonsBoosterPack(), "RareDragons", "Rare Dragon Booster");
			for (AbstractCard c : rareDragons)
			{
				Util.log("Rolled Rare Dragons, printing rareDragons: " + c.name);
			}
		}
		else if (roll <= 80)
		{
			toReturn = new BoosterReward(dragonsBoosterPack(), "DragonBooster", "Dragon Booster");
			for (AbstractCard c : dragons)
			{
				Util.log("Rolled Dragons, printing dragons: " + c.name);
			}
		}
		else if (roll <= 100)
		{
			toReturn = new BoosterReward(deckBoosterPack(), "DeckBooster", "Archetype Booster");
			for (AbstractCard c : deckTyped)
			{
				Util.log("Rolled Deck, printing deck: " + c.name);
			}
		}
		else
		{
			toReturn = new BoosterReward(specialBoosterPack(), "SpecialBooster", "Special Booster");
			for (AbstractCard c : BoosterPackHelper.special)
			{
				Util.log("Rolled Special, printing special: " + c.name);
			}
		}
		
		if (toReturn.cards.size() < bonusPackSize) { Util.log("bonus booster for roll " + roll + " in getBonusBooster() was too small, so we got a new onetossed booster size: " + toReturn.cards.size()); return getBonusBooster(false, false); }
		else { Util.log("bonus booster for roll " + roll + " was all good on size. booster size: " + toReturn.cards.size()); }
		return toReturn;
	}
	
	
	public static RewardItem getRandomBooster(boolean spellcasterDeck, int roll)
	{
		RewardItem toReturn = new BoosterReward(uncommonBoosterPack(), "UncommonBooster", "Uncommon Booster");
		if (spellcasterDeck)
		{
			// Common - 3%
			if (roll <= 3)
			{
				toReturn = new BoosterReward(commonBoosterPack(), "CommonBooster", "Common Booster");
			}
			
			// Orb/Exodia - 3%
			else if (roll <= 6)
			{
				toReturn = new BoosterReward(orbBoosterPack(), "OrbBooster", "Orb Booster");
			}
			
			// Uncommon - 30%
			else if (roll <= 36)
			{
				toReturn = new BoosterReward(uncommonBoosterPack(), "UncommonBooster", "Uncommon Booster");
			}
			
			// Skill - 13%
			else if (roll <= 49)
			{
				toReturn = new BoosterReward(skillBoosterPack(), "SkillBooster", "Skill Booster");
			}
			
			// Attack - 13%
			else if (roll <= 62)
			{
				toReturn = new BoosterReward(attackBoosterPack(), "AttackBooster", "Attack Booster");
			}
			
			// All Uncommon - 10%
			else if (roll <= 72)
			{
				toReturn = new BoosterReward(allUncommonBoosterPack(), "AllUncommonBooster", "All Uncommon Booster");
			}
			
			// Rare - 8%
			else if (roll <= 80)
			{
				toReturn = new BoosterReward(rareBoosterPack(), "RareBooster", "Rare Booster");
				
			}
			
			// Multi-Type - 7%
			else if (roll <= 87)
			{
				toReturn = new BoosterReward(multitypeBoosterPack(), "MultiTypeBooster", "Multi-Type Booster");
			}
			
			// Power - 4%
			else if (roll <= 91)
			{
				toReturn = new BoosterReward(powerBoosterPack(), "PowerBooster", "Power Booster");
			}
			
			// Rare Skill - 2%
			else if (roll <= 93)
			{
				toReturn = new BoosterReward(rareSkillBoosterPack(), "RareSkills", "Rare Skill Booster");
			}
			
			// Rare Attack - 2%
			else if (roll <= 95)
			{
				toReturn = new BoosterReward(rareAttackBoosterPack(), "RareAttacks", "Rare Attack Booster");
			}
			
			// Rare Power - 2%
			else if (roll <= 97)
			{
				toReturn = new BoosterReward(rarePowerBoosterPack(), "RarePowerBooster", "Rare Power Booster");
			}
			
			// Silly Pack - 2%
			else if (roll <= 99)
			{
				toReturn = new BoosterReward(sillyBoosterPack(), "SillyPack", "Bonanza Booster");
			}
			
			// All Rares - 1%
			else if (roll >= 100)
			{
				toReturn = new BoosterReward(allRaresBoosterPack(), "AllRaresBooster", "All Rare Booster");
			}
		}
		else
		{
			// Common - 4%
			if (roll <= 4)
			{
				toReturn = new BoosterReward(commonBoosterPack(), "CommonBooster", "Common Booster");
			}
			
			// Uncommon - 32%
			else if (roll <= 32)
			{
				toReturn = new BoosterReward(uncommonBoosterPack(), "UncommonBooster", "Uncommon Booster");
			}
			
			// Skill - 13%
			else if (roll <= 49)
			{
				toReturn = new BoosterReward(skillBoosterPack(), "SkillBooster", "Skill Booster");
			}
			
			// Attack - 13%
			else if (roll <= 62)
			{
				toReturn = new BoosterReward(attackBoosterPack(), "AttackBooster", "Attack Booster");
			}
			
			// All Uncommon - 10%
			else if (roll <= 72)
			{
				toReturn = new BoosterReward(allUncommonBoosterPack(), "AllUncommonBooster", "All Uncommon Booster");
			}
			
			// Rare - 8%
			else if (roll <= 80)
			{
				toReturn = new BoosterReward(rareBoosterPack(), "RareBooster", "Rare Booster");
				
			}
			
			// Multi-Type - 7%
			else if (roll <= 87)
			{
				toReturn = new BoosterReward(multitypeBoosterPack(), "MultiTypeBooster", "Multi-Type Booster");
			}
			
			// Power - 4%
			else if (roll <= 91)
			{
				toReturn = new BoosterReward(powerBoosterPack(), "PowerBooster", "Power Booster");
			}
			
			// Rare Skill - 2%
			else if (roll <= 93)
			{
				toReturn = new BoosterReward(rareSkillBoosterPack(), "RareSkills", "Rare Skill Booster");
			}
			
			// Rare Attack - 2%
			else if (roll <= 95)
			{
				toReturn = new BoosterReward(rareAttackBoosterPack(), "RareAttacks", "Rare Attack Booster");
			}
			
			// Rare Power - 2%
			else if (roll <= 97)
			{
				toReturn = new BoosterReward(rarePowerBoosterPack(), "RarePowerBooster", "Rare Power Booster");
			}
			
			// Silly Pack - 2%
			else if (roll <= 99)
			{
				toReturn = new BoosterReward(sillyBoosterPack(), "SillyPack", "Bonanza Booster");
			}
			
			// All Rares - 1%
			else if (roll >= 100)
			{
				toReturn = new BoosterReward(allRaresBoosterPack(), "AllRaresBooster", "All Rare Booster");
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
		
		// If player allows booster packs or always wants them to appear
		if (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters)
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
					
					// Reset roll chances for next combat
					lastPackRoll = 0;
					
					// Debug log
					Util.log("Rolled and added a booster pack reward");
				}
				
				// We did not roll a booster reward
				// Roll to see if we increment booster roll chances for next combat, or just increment them if elite victory
				else
				{
					int incRoll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (incRoll == 1 || eliteVictory) { lastPackRoll++; }
				}
				
				// Receive bonus Spell/Trap pack if we got one
				if (bonusPackRoll == 1)
				{
					AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getBonusBooster(false, false));
					Util.log("Rolled and added a BONUS booster pack reward!");
				}
			}
			
			// Forced booster roll - same as above just in a different order and doesn't roll to see if we get a booster, always gives one
			else
			{
				int roll = AbstractDungeon.cardRandomRng.random(1, 100);
				int bonusPackRoll = AbstractDungeon.cardRandomRng.random(1, 25);
				if (AbstractDungeon.player.hasRelic(BoosterAlwaysBonusRelic.ID) || eliteVictory) { bonusPackRoll = 1; }
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
				lastPackRoll = 0;
				Util.log("Rolled and added a booster pack reward");
				
				if (bonusPackRoll == 1)
				{
					AbstractDungeon.getCurrRoom().rewards.add(BoosterPackHelper.getBonusBooster(false, false));
					Util.log("Rolled and added a BONUS booster pack reward!");
				}
			}
		}
	}
}
