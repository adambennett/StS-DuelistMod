package duelistmod.rewards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;

import basemod.abstracts.CustomReward;
import duelistmod.helpers.*;
import duelistmod.patches.RewardItemTypeEnumPatch;
import duelistmod.relics.*;

public class BoosterReward extends CustomReward 
{
	public String packName;
	public ArrayList<AbstractCard> booster;
	public int boosterID;
	public int goldCost;
	private boolean isBonus = false;

	// if id-200 > 0 then we had a bonus booster
	public BoosterReward(int id, int goldCost)
	{
		super(new Texture("duelistModResources/images/ui/rewards/" + BoosterPackHelper.getIMG(id, id-200>0) + ".png"), BoosterPackHelper.getPackName(id, id-200>0), RewardItemTypeEnumPatch.DUELIST_PACK);
		this.cards = BoosterPackHelper.getBoosterCardsFromID(id, id-200 > 0);
		this.booster = cards;
		this.type = RewardType.CARD;
		this.packName = BoosterPackHelper.getPackName(id, id-200>0);
		this.boosterID = id;
		this.goldCost = goldCost;
		this.isBonus = id-200>0;
		RewardItem gold = new RewardItem(69);
		this.relicLink = gold;
		gold.relicLink = this;
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
			
			else if (upgradeCheck())
			{
				c.upgrade();
				while (c.canUpgrade() && additionalUpgradeCheck())
				{
					c.upgrade();
					Util.log("Upgraded " + c.name + " more than once for a Booster Pack");
				}
			}
		}
	}
	
	public BoosterReward(ArrayList<AbstractCard> cards, String imgPath, String packName, int id, int goldCost) 
	{
		super(new Texture("duelistModResources/images/ui/rewards/" + imgPath + ".png"), packName, RewardItemTypeEnumPatch.DUELIST_PACK);
		this.cards = cards;
		this.booster = cards;
		this.packName = packName;
		this.boosterID = id;
		this.type = RewardType.CARD;
		this.goldCost = goldCost;
		this.isBonus = id-200>0;
		RewardItem gold = new RewardItem(69);
		this.relicLink = gold;
		gold.relicLink = this;
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
			
			else if (upgradeCheck())
			{
				c.upgrade();
				while (c.canUpgrade() && additionalUpgradeCheck())
				{
					c.upgrade();
					Util.log("Upgraded " + c.name + " more than once for a Booster Pack");
				}
			}
		}
	}

	@Override
	public boolean claimReward() 
	{
		if (isBonus && AbstractDungeon.player.hasRelic(QuestionCard.ID)) { AbstractDungeon.player.getRelic(QuestionCard.ID).flash(); }
		if (AbstractDungeon.player.hasRelic("Busted Crown")) { AbstractDungeon.player.getRelic("Busted Crown").flash(); }
		if (AbstractDungeon.player.hasRelic(BoosterBonusPackIncreaseRelic.ID) && this.boosterID > 200) { AbstractDungeon.player.getRelic(BoosterBonusPackIncreaseRelic.ID).flash(); }
		if (AbstractDungeon.player.hasRelic(BoosterPackEggRelic.ID)) { AbstractDungeon.player.getRelic(BoosterPackEggRelic.ID).flash(); }
		if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) 
		{
			//BoosterRewardScreen screen = new BoosterRewardScreen(this.goldCost);
			AbstractDungeon.cardRewardScreen.open(this.cards, this, "Keep 1 Card from the Pack");
			AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
		}
		return false;
	}
	
	private boolean upgradeCheck()
	{
		int upgradeRoll = AbstractDungeon.cardRandomRng.random(1, 100);
		if (Util.getChallengeLevel() > -1)
		{
			int act = AbstractDungeon.actNum;
			if (act <= 3)
			{
				switch (act)
				{
					case 1:
						return false;
					case 2:
						if (upgradeRoll <= 8) { return true; }
						else { return false; }
					case 3:
						if (upgradeRoll <= 14) { return true; }
						else { return false; }
					default:
						return false;
				}
			}
			else { return true; }
		}
		else if (AbstractDungeon.ascensionLevel > 11) 
		{ 
			int act = AbstractDungeon.actNum;
			if (act <= 3)
			{
				switch (act)
				{
					case 1:
						return false;
					case 2:
						if (upgradeRoll <= 12) { return true; }
						else { return false; }
					case 3:
						if (upgradeRoll <= 20) { return true; }
						else { return false; }
					default:
						return false;
				}
			}
			else { return true; }
		}
		else 
		{ 
			int act = AbstractDungeon.actNum;
			if (act <= 3)
			{
				switch (act)
				{
					case 1:
						return false;
					case 2:
						if (upgradeRoll <= 20) { return true; }
						else { return false; }
					case 3:
						if (upgradeRoll <= 36) { return true; }
						else { return false; }
					default:
						return false;
				}
			}
			else { return true; }			
		}
	}
	
	private boolean additionalUpgradeCheck()
	{
		int upgradeRoll = AbstractDungeon.cardRandomRng.random(1, 105);
		if (Util.getChallengeLevel() > -1)
		{
			int act = AbstractDungeon.actNum;
			if (act <= 3)
			{
				switch (act)
				{
					case 1:
						return false;
					case 2:
						return false;
					case 3:
						if (upgradeRoll <= 10) { return true; }
						else { return false; }
					default:
						return false;
				}
			}
			else { return true; }
		}
		else if (AbstractDungeon.ascensionLevel > 11) 
		{ 
			int act = AbstractDungeon.actNum;
			if (act <= 3)
			{
				switch (act)
				{
					case 1:
						return false;
					case 2:
						return false;
					case 3:
						if (upgradeRoll <= 15) { return true; }
						else { return false; }
					default:
						return false;
				}
			}
			else { return true; }
		}
		else 
		{ 
			int act = AbstractDungeon.actNum;
			if (act <= 3)
			{
				switch (act)
				{
					case 1:
						return false;
					case 2:
						return false;
					case 3:
						if (upgradeRoll <= 25) { return true; }
						else { return false; }
					default:
						return false;
				}
			}
			else { return true; }			
		}
	}
}
