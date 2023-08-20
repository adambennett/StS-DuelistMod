package duelistmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

import duelistmod.DuelistMod;
import duelistmod.helpers.*;
import duelistmod.rewards.BoosterPack;
import duelistmod.rewards.boosterPacks.BadPack;

public class BoosterReplaceRewardsPatch 
{
	@SpirePatch(clz = CombatRewardScreen.class, method = "setupItemReward")
	public static class SetupItemReward {
		@SpirePostfixPatch
		public static void convertToBoosterReward(CombatRewardScreen __instance) 
		{
			if(AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST) && DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveCardRewards())
			{
				DuelistMod.currentBoosters.clear();		
				if (BoosterHelper.packPool == null || BoosterHelper.packPool.size() < 1) { BoosterHelper.refreshPool(); }
				if ((DuelistMod.persistentDuelistData.CardPoolSettings.getAllowBoosters() || DuelistMod.persistentDuelistData.CardPoolSettings.getAlwaysBoosters()) && BoosterHelper.packPool.size() > 0)
				{
					boolean boss = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
					boolean eliteVictory = AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite;
					//boolean linkedAlready = false;
					ArrayList<RewardItem> newRew = new ArrayList<>();
					for (RewardItem r : AbstractDungeon.combatRewardScreen.rewards)
					{						
						if (r.type.equals(RewardType.CARD))
						{
							RewardItem pack = DuelistMod.isSensoryStone
									? BoosterHelper.generateSpecificPackFromPool("Colorless Pack")
									: BoosterHelper.replaceCardReward(DuelistMod.lastPackRoll, eliteVictory, boss);
							if (pack != null && pack.cards.size() > 0 && !(pack instanceof BadPack))
							{ 
								newRew.add(pack);
								DuelistMod.onReceiveBoosterPack((BoosterPack)pack);
								Util.log("Generated " + ((BoosterPack)pack).packName + " (rarity=" + ((BoosterPack)pack).rarity + ")");
								DuelistMod.currentBoosters.add((BoosterPack) pack);
								//int linkedRoll = 1;
								//if ((linkedRoll == 1 || boss) && !linkedAlready) { linkedAlready = true; newRew.add(BoosterPackHelperRevamp.getLinked((BoosterPack) pack, eliteVictory, boss)); }
							}
							else { newRew.add(r); }
						}
						else
						{
							newRew.add(r);
						}
					}
					AbstractDungeon.combatRewardScreen.rewards.clear();
					AbstractDungeon.combatRewardScreen.rewards.addAll(newRew);					
				}
				else if (BoosterHelper.packPool.size() > 0)
				{
					ArrayList<RewardItem> newRew = new ArrayList<>();
					for (RewardItem r : AbstractDungeon.combatRewardScreen.rewards)
					{
						if (!r.type.equals(RewardType.CARD))
						{
							newRew.add(r);
						}
					}
					
					AbstractDungeon.combatRewardScreen.rewards.clear();
					AbstractDungeon.combatRewardScreen.rewards.addAll(newRew);
				}
			}
			AbstractDungeon.combatRewardScreen.positionRewards();
			DuelistMod.isSensoryStone = false;
		}

		/*private static class Locator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior ctMethod) throws Exception {

				Matcher matcher = new Matcher.FieldAccessMatcher(RewardItem.class, "cards");

				return LineFinder.findAllInOrder(ctMethod, matcher);
			}
		}*/
	}
}
