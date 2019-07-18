package duelistmod.rewards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;

import basemod.abstracts.CustomReward;
import duelistmod.patches.RewardItemTypeEnumPatch;
import duelistmod.relics.BoosterPackEggRelic;

public class BoosterReward extends CustomReward 
{
	public String packName;
	public ArrayList<AbstractCard> booster;

	public BoosterReward(ArrayList<AbstractCard> cards, String imgPath, String packName) 
	{
		super(new Texture("duelistModResources/images/ui/rewards/" + imgPath + ".png"), packName, RewardItemTypeEnumPatch.DUELIST_PACK);
		this.cards = cards;
		this.booster = cards;
		this.packName = packName;

		for (AbstractCard c : this.cards) 
		{
			if ((c.type == AbstractCard.CardType.ATTACK) && (AbstractDungeon.player.hasRelic(MoltenEgg2.ID))) 
			{
				c.upgrade();
			} 
			
			else if ((c.type == AbstractCard.CardType.SKILL) && (AbstractDungeon.player.hasRelic(ToxicEgg2.ID)))
			{
				c.upgrade();
			} 
			
			else if ((c.type == AbstractCard.CardType.POWER) && (AbstractDungeon.player.hasRelic(FrozenEgg2.ID))) 
			{
				c.upgrade();
			}
			
			else if ((AbstractDungeon.player.hasRelic(BoosterPackEggRelic.ID))) 
			{
				c.upgrade();
			}
		}
	}

	@Override
	public boolean claimReward() 
	{
		if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) 
		{
			AbstractDungeon.cardRewardScreen.open(this.cards, this, "Keep 1 Card from the Pack");
			AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
		}
		return false;
	}
}
