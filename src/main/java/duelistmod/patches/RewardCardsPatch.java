package duelistmod.patches;
import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;
import duelistmod.relics.TributeEggRelic;
import duelistmod.variables.Tags;

/*
@SpirePatch(
		clz = AbstractDungeon.class,
		method = "getRewardCards"
		)

public class RewardCardsPatch 
{
	public static ArrayList<AbstractCard> PostFix(ArrayList<AbstractCard> __result) 
	{
		if (AbstractDungeon.player instanceof TheDuelist)
		{
			if (AbstractDungeon.player.hasRelic(TributeEggRelic.ID))
			{
				for (AbstractCard c : (ArrayList<AbstractCard>)__result) 
				{
					if (c instanceof DuelistCard)
					{
						DuelistCard dc = (DuelistCard)c;
						if (dc.tributes > 1 && dc.hasTag(Tags.MONSTER))
						{
							dc.modifyTributesPerm(-1);
							Util.log("Ran tribute egg patch on getRewardCards()");
						}
					}
				}
			}
		}
		else
		{
			for (AbstractCard c : (ArrayList<AbstractCard>)__result) 
			{
				if (c instanceof DuelistCard && DuelistMod.rewardCardsReachedEnd < 5)
				{
					DuelistMod.rewardCardsReachedEnd++;
					return AbstractDungeon.getRewardCards();
				}
			}
		}
		
		DuelistMod.rewardCardsReachedEnd = 0;
		return (ArrayList<AbstractCard>)__result;
	}
}
*/
