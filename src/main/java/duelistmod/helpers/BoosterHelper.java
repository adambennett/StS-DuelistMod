package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import com.megacrit.cardcrawl.rewards.RewardItem;

import duelistmod.DuelistMod;
import duelistmod.relics.*;
import duelistmod.rewards.*;
import duelistmod.rewards.BoosterPack.PackRarity;
import duelistmod.rewards.boosterPacks.*;

public class BoosterHelper 
{
	private static int packSize = 5;
	private static int actualPackSize = 5;
	public static ArrayList<BoosterPack> packPool;
		
	public static void modifyPackSize(int add)
	{
		int maxPackSize = 5;
		if (Util.getChallengeLevel() > 13) { maxPackSize = 4; }
		actualPackSize += add;
		packSize = actualPackSize;
		if (actualPackSize > maxPackSize) { packSize = maxPackSize; }
		else if (actualPackSize < 1) { packSize = 1; }
		if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null)
		{
			refreshPool();
		}		
	}
	
	public static void setPackSize(int set)
	{
		int maxPackSize = 5;
		if (Util.getChallengeLevel() > 13) { maxPackSize = 4; }
		actualPackSize = set;
		packSize = actualPackSize;
		if (actualPackSize > maxPackSize) { packSize = maxPackSize; }
		else if (actualPackSize < 1) { packSize = 1; }
		if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null) {
			refreshPool();
		}
	}
	
	public static int getPackSize() 
	{ 
		if (packSize > 1 && Util.getChallengeLevel() > 13) {
			return packSize - 1;
		}
		return packSize; 
	}
	
	public static int getActualPackSize() { return actualPackSize; }
	
	public static LinkedReward getLinked(BoosterPack pack, boolean eliteVictory, boolean bossVictory)
	{
		
		int roll = AbstractDungeon.cardRandomRng.random(1, 100);
		if (roll < 60)
		{
			int goldRoll = AbstractDungeon.cardRandomRng.random(10, 35);
			if (eliteVictory) { goldRoll += AbstractDungeon.cardRandomRng.random(5, 20); }
			if (bossVictory) { goldRoll += AbstractDungeon.cardRandomRng.random(10, 30); }
			return new LinkedReward(pack, goldRoll);
		}
		else if (roll >= 95)
		{
			int tierRoll = AbstractDungeon.cardRandomRng.random(1, 25);
			if (eliteVictory) { tierRoll += AbstractDungeon.cardRandomRng.random(1, 4); }
			if (bossVictory) { tierRoll += AbstractDungeon.cardRandomRng.random(2, 5); }
			if (tierRoll == 1) { return new LinkedReward(pack, RelicTier.COMMON); }
			else if (tierRoll < 25) { return new LinkedReward(pack, RelicTier.UNCOMMON); }
			else { return new LinkedReward(pack, RelicTier.RARE); }
		}
		else
		{
			int rareRoll = AbstractDungeon.cardRandomRng.random(1, 15);
			if (eliteVictory) { rareRoll += AbstractDungeon.cardRandomRng.random(1, 3); }
			if (bossVictory) { rareRoll += AbstractDungeon.cardRandomRng.random(2, 5); }
			if (rareRoll == 1) { return new LinkedReward(pack, PotionRarity.COMMON); }
			else if (rareRoll < 15) { return new LinkedReward(pack, PotionRarity.UNCOMMON); }
			else { return new LinkedReward(pack, PotionRarity.RARE); }
		}
	}
	
	public static RewardItem getBooster(boolean bossVictory, boolean eliteVictory)
	{
		if (packPool == null || packPool.size() < 1 || DuelistMod.replacedCardPool || DuelistMod.boosterDeath) 
		{ 
			DuelistMod.replacedCardPool = false; 
			DuelistMod.boosterDeath = false;
			refreshPool(); 
			Util.log("For some reason, when we went to generate a Booster Pack, we needed to refresh to booster pool. Either: you've restarted a new run since starting up the game, the pool was null, the pool was empty, or we are detecting changes to the card pool (from card pool relics) since the last booster pool refresh");
		}
		ArrayList<BoosterPack> tempPool = new ArrayList<>();
		ArrayList<PackRarity> rarities = new ArrayList<>();
		boolean incRarityForRelic = false;
		if (AbstractDungeon.player != null)
		{
			if (AbstractDungeon.player.hasRelic(BoosterBetterBoostersRelic.ID))
			{
				incRarityForRelic = true;
			}
		}
		for (BoosterPack b : packPool) 
		{ 
			if (!rarities.contains(b.rarity) && b.canSpawn()) 
			{ 
				if (b.rarity.equals(PackRarity.COMMON)) 
				{
					rarities.add(b.rarity); 
					rarities.add(b.rarity);
					rarities.add(b.rarity);
					rarities.add(b.rarity);
					rarities.add(b.rarity);
					if (AbstractDungeon.actNum < 2) { rarities.add(b.rarity); rarities.add(b.rarity); }
					if (AbstractDungeon.actNum < 3) { rarities.add(b.rarity); rarities.add(b.rarity); }
					if (AbstractDungeon.actNum < 4) { rarities.add(b.rarity);  rarities.add(b.rarity); }
					if (Util.getChallengeLevel() > 0) 
					{
						rarities.add(b.rarity); 
					}
					if (!incRarityForRelic)
					{
						rarities.add(b.rarity); 
						rarities.add(b.rarity); 
						rarities.add(b.rarity); 
						rarities.add(b.rarity); 
					}
				}
				else if (b.rarity.equals(PackRarity.UNCOMMON))
				{
					rarities.add(b.rarity); 
					rarities.add(b.rarity); 
					rarities.add(b.rarity);
					rarities.add(b.rarity);		
					rarities.add(b.rarity); 
					rarities.add(b.rarity); 
					if (AbstractDungeon.actNum > 1)
					{
						rarities.add(b.rarity);
					}
					if (AbstractDungeon.actNum > 2)
					{
						rarities.add(b.rarity);
						rarities.add(b.rarity);
					}
					if (AbstractDungeon.actNum > 3)
					{
						rarities.add(b.rarity);
						rarities.add(b.rarity);
					}
					if (!incRarityForRelic)
					{
						rarities.add(b.rarity); 
						rarities.add(b.rarity);
						rarities.add(b.rarity); 
					}
				}
				
				else if (b.rarity.equals(PackRarity.RARE))
				{
					rarities.add(b.rarity); 
					if (Util.getChallengeLevel() < 0) 
					{
						rarities.add(b.rarity); 
					}
					if (incRarityForRelic)
					{
						rarities.add(b.rarity); 
					}
					if (AbstractDungeon.actNum > 1)
					{
						rarities.add(b.rarity);
					}
					if (AbstractDungeon.actNum > 2)
					{
						rarities.add(b.rarity);
					}
					if (AbstractDungeon.actNum > 3)
					{
						rarities.add(b.rarity);
					}
				}
				
				else if (b.rarity.equals(PackRarity.SUPER_RARE))
				{
					rarities.add(b.rarity); 
					if (incRarityForRelic)
					{
						rarities.add(b.rarity); 
					}
					if (AbstractDungeon.actNum > 2)
					{
						rarities.add(b.rarity);
					}
					if (AbstractDungeon.actNum > 3)
					{
						rarities.add(b.rarity);
					}
				}
				else 
				{ 
					rarities.add(b.rarity); 
					rarities.add(b.rarity); 
					rarities.add(b.rarity); 
					if (Util.getChallengeLevel() < 0) 
					{
						rarities.add(b.rarity); 
					}
					if (incRarityForRelic)
					{
						rarities.add(b.rarity); 
						rarities.add(b.rarity); 
					}
					if (AbstractDungeon.actNum > 1)
					{
						rarities.add(b.rarity);
						rarities.add(b.rarity);
					}
					if (AbstractDungeon.actNum > 2)
					{
						rarities.add(b.rarity);
						rarities.add(b.rarity);
					}
					if (AbstractDungeon.actNum > 3)
					{
						rarities.add(b.rarity);
						rarities.add(b.rarity);
					}
				}
			}
		}
		
		/*for (PackRarity r : rarities)
		{
			if (r.equals(PackRarity.COMMON)) { Util.log("Common rarity entry in rollPool"); }
			else if (r.equals(PackRarity.UNCOMMON)) { Util.log("Uncommon rarity entry in rollPool"); }
			else if (r.equals(PackRarity.RARE)) { Util.log("Rare rarity entry in rollPool"); }
			else if (r.equals(PackRarity.SUPER_RARE)) { Util.log("Super rare rarity entry in rollPool"); }
			else { Util.log("Special rarity entry in rollPool"); }
		}*/
		
		if (rarities.contains(PackRarity.RARE) && bossVictory)
		{
			if (rarities.contains(PackRarity.SUPER_RARE))
			{
				if (rarities.contains(PackRarity.SPECIAL))
				{
					rarities.clear();
					//rarities.add(PackRarity.RARE);
					rarities.add(PackRarity.SUPER_RARE);
					//rarities.add(PackRarity.SPECIAL);
				}
				else
				{
					rarities.clear();
					//rarities.add(PackRarity.RARE);
					rarities.add(PackRarity.SUPER_RARE);
				}
			}
			else
			{
				rarities.clear();
				rarities.add(PackRarity.RARE);
			}
		}

		PackRarity rollRare = null;
		if (rarities.size() > 0) {
			rollRare = rarities.get(AbstractDungeon.cardRandomRng.random(rarities.size() - 1));
		}
		if (rollRare != null)
		{
			for (BoosterPack b : packPool) { if (b.rarity.equals(rollRare) && b.canSpawn()) { tempPool.add(b); }}
			if (tempPool.size() > 0) {
				return tempPool.get(AbstractDungeon.cardRandomRng.random(tempPool.size() - 1)).makeCopy();
			}
			else
			{
				return new BadPack();
			}
		}
		else
		{
			Util.log("Got a null for randomized pack rarity, so we generated a bad pack.");
			return new BadPack();
		}
	}

	public static void refreshPool()
	{
		packPool = initPackPool();
		Util.log("Refreshing booster pack pool!");
		for (BoosterPack b : packPool)
		{
			Util.log("Added " + b.packName + " to pool of boosters");
		}
		if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.player != null)
		{
			if (AbstractDungeon.player.hasRelic(BoosterPackPoolRelic.ID)) { ((BoosterPackPoolRelic)AbstractDungeon.player.getRelic(BoosterPackPoolRelic.ID)).refreshPool(); }
		}
	}

	public static ArrayList<BoosterPack> initPackPool()
	{
		DuelistMod.badBoosterSituation = false;
		ArrayList<BoosterPack> temp = new ArrayList<>();
		ArrayList<BoosterPack> toRet = new ArrayList<>();
		boolean addSpecialRelicPacks = false;
		if (AbstractDungeon.player != null)
		{
			if (AbstractDungeon.player.hasRelic(BoosterExtraAllRaresRelic.ID))
			{
				addSpecialRelicPacks = true;
			}
		}
		if (Util.deckIs("Metronome Deck"))
		{
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new MetronomeBooster());
			temp.add(new BasicPack());
			temp.add(new BasicPackU());
			temp.add(new BasicPackR());
			temp.add(new BasicAttackPack());
			temp.add(new BasicAttackPackU());
			temp.add(new BasicAttackPackR());
		}
		else
		{
			// Standard Packs
			temp.add(new StandardPack());
			temp.add(new StandardPack());
			temp.add(new StandardPack());
			temp.add(new StandardPackU());
			temp.add(new StandardPackU());
			temp.add(new StandardPackU());
			temp.add(new StandardPackR());
			temp.add(new StandardPackR());
			temp.add(new StandardPackR());
			temp.add(new CommonPack());
			temp.add(new CommonPack());
			temp.add(new CommonPackR());
			temp.add(new CommonPackR());
			temp.add(new CommonPackU());
			temp.add(new CommonPackU());
			temp.add(new AttackPack());
			temp.add(new AttackPack());
			temp.add(new BasicPack());
			
			// Other Packs
			temp.add(new ArcanePack());
			temp.add(new ArcanePackR());
			temp.add(new AttackPackR());
			temp.add(new AttackPackU());
			if (!DuelistMod.smallBasicSet) {
				temp.add(new BasicAttackPack());
				temp.add(new BasicAttackPackR());
				temp.add(new BasicAttackPackU());
			}
			temp.add(new BasicPackR());
			temp.add(new BasicPackU());
			temp.add(new DragonPack());
			temp.add(new DragonPackR());			
			temp.add(new ExodiaPack());			
			temp.add(new MaliciousPack());
			temp.add(new MegatypePack());
			temp.add(new MonsterPack());
			temp.add(new MonsterPackR());
			temp.add(new MonsterPackU());
			temp.add(new OrbPack());
			temp.add(new OverflowPack());
			temp.add(new OverflowPackR());
			temp.add(new PelagicPack());
			temp.add(new PowerPack());
			temp.add(new PowerPackC());
			temp.add(new PowerPackR());
			temp.add(new RecklessPack());
			temp.add(new RockPack());
			temp.add(new RockPackR());
			temp.add(new SkillPack());
			temp.add(new SpellPack());
			temp.add(new SpellPackR());
			temp.add(new SpellPackU());
			temp.add(new SpellPackUPow());
			temp.add(new SpellcasterPack());
			temp.add(new SpellcasterPackR());
			temp.add(new SpirePack());			
			temp.add(new StampedingPack());
			temp.add(new SuperPack());
			temp.add(new ThalassicPack());
			temp.add(new TidalPack());
			temp.add(new TrapPack());
			temp.add(new TrapPackR());
			temp.add(new TrapPackU());
			temp.add(new TriAttackPack());
			temp.add(new TriAttackPackR());
			temp.add(new TriAttackPackRB());
			temp.add(new TriRarePack());
			temp.add(new TokenPack());
			temp.add(new PlayedMonstersPack());
			temp.add(new PlayedSpellsPack());
			temp.add(new DuplicatesPack());
			temp.add(new ColorlessPack());
			temp.add(new DinosaurPack());
			temp.add(new DinosaurPackR());			
			temp.add(new HybridZombiePack());
			temp.add(new HybridZombiePackB());
			temp.add(new HybridZombiePackU());
			temp.add(new HybridZombiePackR());
			temp.add(new SpecialZombiePack());
			temp.add(new GhostrickPack());
			temp.add(new ShiranuiPack());
			temp.add(new VampirePack());
			temp.add(new VendreadPack());
			temp.add(new MayakashiPack());
			
			// Special Packs
			if (addSpecialRelicPacks)
			{
				temp.add(new RelicPackA());
				temp.add(new RelicPackB());
				temp.add(new RelicPackC());
			}
		}

		for (BoosterPack b : temp) 
		{ 
			if (b.canSpawn()) 
			{ 
				if (b.obeyPackSize)
				{
					while (b.cardsInPack.size() > getPackSize())
					{
						b.cardsInPack.remove(AbstractDungeon.cardRandomRng.random(b.cardsInPack.size() - 1));
					}
					if (b.cardsInPack.size() == getPackSize())
					{
						toRet.add(b); 
						Util.log("Adding " + b.packName + " to Booster Pool. (Obeying pack size limits)");
					}
				}
				else if (b.cardsInPack.size() > 0)
				{
					toRet.add(b); 
					Util.log("Adding " + b.packName + " to Booster Pool. (Disobeying pack size limits)");
				}
				else {
					Util.log("Booster pack was not added to the pool due to size issues: " + b.packName);
				}
			} else {
				Util.log("Booster pack was not allowed to spawn: " + b.packName);
			}
		}
		if (toRet.size() < 1)
		{
			DuelistMod.badBoosterSituation = true;
			Util.log("Bad booster situation.. small card pool?", true);
			toRet.add(new FallbackPack());
		}
		return toRet; 
	}
	
	// ------------------------------------------------------------------------------------------- //
	
	
	public static void generateBoosterOnVictory(int lastPackRoll, boolean eliteVictory, boolean bossVictory)
	{
		// If player allows booster packs or always wants them to appear, but doesn't have card rewards removed
		if ((DuelistMod.allowBoosters || DuelistMod.alwaysBoosters) && !DuelistMod.removeCardRewards)
		{
			// If always appearing, force the roll
			if (DuelistMod.alwaysBoosters) { lastPackRoll = 7; }
			
			// Rolling to see if we receive a booster pack this combat
			if (lastPackRoll <= 6)
			{
				int packRoll = AbstractDungeon.cardRandomRng.random(lastPackRoll, 6);
				if (packRoll == 6)
				{
					RewardItem pack = getBooster(bossVictory, eliteVictory);
					AbstractDungeon.getCurrRoom().rewards.add(pack);
					DuelistMod.onReceiveBoosterPack((BoosterPack)pack);
					if (AbstractDungeon.player.hasRelic(PrayerWheel.ID)) 
					{ 
						RewardItem packB = getBooster(bossVictory, eliteVictory);
						AbstractDungeon.getCurrRoom().rewards.add(packB);
						DuelistMod.onReceiveBoosterPack((BoosterPack)packB);
					}
					
					DuelistMod.lastPackRoll = 0;
					Util.log("Rolled and added a booster pack reward");
					
				}
				else
				{
					int incRoll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (incRoll == 1 || eliteVictory) { DuelistMod.lastPackRoll++; }
				}
			}
			
			// Forced booster roll - same as above just in a different order and doesn't roll to see if we get a booster, always gives one
			// Bonus Booster chance is decreased
			else
			{
				RewardItem pack = getBooster(bossVictory, eliteVictory);
				AbstractDungeon.getCurrRoom().rewards.add(pack);
				DuelistMod.onReceiveBoosterPack((BoosterPack)pack);
				if (AbstractDungeon.player.hasRelic(PrayerWheel.ID)) 
				{ 
					RewardItem packB = getBooster(bossVictory, eliteVictory);
					AbstractDungeon.getCurrRoom().rewards.add(packB); 
					DuelistMod.onReceiveBoosterPack((BoosterPack)packB);
				}
				DuelistMod.lastPackRoll = 0;
				Util.log("Force-adding a booster pack reward");
				DuelistMod.lastPackRoll = 0;
			}
		}
	}
	
	public static RewardItem replaceCardReward(int lastPackRoll, boolean eliteVictory, boolean bossVictory)
	{
		if (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters)
		{
			// If always appearing, force the roll
			if (DuelistMod.alwaysBoosters) { lastPackRoll = 7; }
			
			// Rolling to see if we receive a booster pack this combat
			if (lastPackRoll <= 6)
			{
				int packRoll = AbstractDungeon.cardRandomRng.random(lastPackRoll, 6);
				// We rolled a booster reward
				if (packRoll == 6)
				{
					BoosterPack pack = (BoosterPack) getBooster(bossVictory, eliteVictory);					
					DuelistMod.lastPackRoll = 0;
					Util.log("Rolled and added a booster pack reward");
					return pack;					
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
			else
			{
				BoosterPack pack = (BoosterPack) getBooster(bossVictory, eliteVictory);				
				DuelistMod.lastPackRoll = 0;
				Util.log("Force-adding a booster pack reward");
				return pack;
			}
		}
		else
		{
			RewardItem empty = new RewardItem();
			empty.cards = new ArrayList<AbstractCard>();
			return empty;
		}
	}
	
	public static BoosterPack getPackFromSave(String saveString)
	{
		if (packPool == null) { refreshPool(); }
		for (BoosterPack b : packPool)
		{
			if (b.packName.equals(saveString))
			{
				Util.log("Found and loaded the correct booster? We tried to load the string: " + saveString + ", and we found the booster: " + b.packName);
				return b;
			}
		}
		Util.log("Did not find any boosters called " + saveString + " in the booster pool, so we are just loading a common Standard Booster");
		return new StandardPack();
	}

}
