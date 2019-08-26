package duelistmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

import duelistmod.DuelistMod;
import duelistmod.helpers.*;
import javassist.CtBehavior;

public class BoosterReplaceRewardsPatch {

	@SpirePatch(clz = CombatRewardScreen.class, method = "setupItemReward")
	public static class SetupItemReward {
		@SpireInsertPatch(locator = Locator.class,
			localvars = {"cardReward"})
		public static void convertToBoosterReward(CombatRewardScreen __instance, @ByRef RewardItem[] cardReward) 
		{
			if(AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST) && DuelistMod.removeCardRewards && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Metronome Deck")) 
			{
				if (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters)
				{
					boolean eliteVictory = AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
					if (StarterDeckSetup.getCurrentDeck().getIndex() > 0 && StarterDeckSetup.getCurrentDeck().getIndex() < 14)
					{						
						cardReward[0] = BoosterPackHelper.replaceCardReward(DuelistMod.lastPackRoll, eliteVictory, StarterDeckSetup.getCurrentDeck().tagsThatMatchCards);
					}
					else
					{						
						cardReward[0] = BoosterPackHelper.replaceCardReward(DuelistMod.lastPackRoll, eliteVictory, null);
					}
				}
				else
				{
					RewardItem empty = new RewardItem();
					empty.cards = new ArrayList<AbstractCard>();
					cardReward[0] = empty;
				}
			}
			else if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Metronome Deck") && AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
			{
				cardReward[0] = BoosterPackHelper.replaceMetronomeReward();
			}
		}

		private static class Locator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior ctMethod) throws Exception {

				Matcher matcher = new Matcher.FieldAccessMatcher(RewardItem.class, "cards");

				return LineFinder.findAllInOrder(ctMethod, matcher);
			}
		}
	}
}
