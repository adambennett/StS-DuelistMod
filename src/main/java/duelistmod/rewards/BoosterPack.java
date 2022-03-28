package duelistmod.rewards;

import java.util.*;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import basemod.abstracts.*;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.*;
import duelistmod.patches.RewardItemTypeEnumPatch;
import duelistmod.relics.*;
import duelistmod.rewards.boosterPacks.OrbPack;
import duelistmod.variables.Tags;

public abstract class BoosterPack extends CustomReward implements CustomSavable <String>
{
	public String packName;
	public boolean obeyPackSize = true;
	public boolean allowBasics = true;
	public boolean onlyBasics = false;
	public boolean alwaysUpgrade = false;
	public boolean alwaysUpgradeAtk = false;
	public boolean alwaysUpgradeSkill = false;
	public boolean alwaysUpgradePower = false;
	public boolean alwaysUpgradeMon = false;
	public boolean alwaysUpgradeSpell = false;
	public boolean alwaysUpgradeTrap = false;
	public boolean isLinked = false;
	public ArrayList<AbstractCard> cardsInPack;
	public PackRarity rarity;
	private Map<String, String> dynMap = new HashMap<>();
	public Color textColor = Settings.CREAM_COLOR;
	
	public enum PackRarity
    {
        COMMON, 
        UNCOMMON,
        RARE,
        SUPER_RARE,
        SPECIAL;
    }
	
	public BoosterPack(String packName, String img)
	{
		super(new Texture("duelistModResources/images/ui/rewards/" + img + ".png"), packName, RewardItemTypeEnumPatch.DUELIST_PACK);
		type = RewardType.CARD;
		this.packName = packName;
		this.isDone = false;
		this.ignoreReward = false;
	}
	
	// Put stuff in here you want to globally override on all boosters (after constructors are called)
	// This works because I thought ahead and called this at the end of each booster pack's constructor
	public void checkRelics()
	{
		// ex: if (rarity.equals(PackRarity.COMMON) && p.hasRelic(iii.ID)) { alwaysUpgrade = true; }
		if (rarity.equals(PackRarity.COMMON)) { textColor = Settings.CREAM_COLOR; }
		else if (rarity.equals(PackRarity.UNCOMMON)) { textColor = Settings.BLUE_TEXT_COLOR; }
		else if (rarity.equals(PackRarity.RARE)) { textColor = Color.YELLOW; }
		else if (rarity.equals(PackRarity.SUPER_RARE)) { textColor = Color.PURPLE; }
		else { textColor = Color.GREEN; }
		
		boolean rel = false;
		if (AbstractDungeon.player != null)
		{
			rel = AbstractDungeon.player.hasRelic(BoosterPackHybridEgg.ID);
		}
		if (rarity.equals(PackRarity.COMMON) && rel)
		{
			alwaysUpgradeSpell = true;
			alwaysUpgradeTrap = true;
		}
		setupCards();
	}
	
	public abstract BoosterPack makeCopy();
	
	private void setupCards()
	{
		cards = getCards();
		cardsInPack = cards;
		if (this.obeyPackSize)
		{
			while (this.cardsInPack.size() > BoosterHelper.getPackSize())
			{
				this.cardsInPack.remove(AbstractDungeon.cardRandomRng.random(this.cardsInPack.size() - 1));
			}
		}
		int maxUpgradeLoops = 999;
		for (AbstractCard c : cards) 
		{
			if (alwaysUpgrade)
			{
				c.upgrade();
			}
			else if ((c.type == AbstractCard.CardType.ATTACK) && ((AbstractDungeon.player.hasRelic(MoltenEgg2.ID)) || alwaysUpgradeAtk)) 
			{
				c.upgrade();
			} 
			
			else if ((c.type == AbstractCard.CardType.SKILL) && ((AbstractDungeon.player.hasRelic(ToxicEgg2.ID)) || alwaysUpgradeSkill))
			{
				c.upgrade();
			} 
			
			else if ((c.type == AbstractCard.CardType.POWER) && ((AbstractDungeon.player.hasRelic(FrozenEgg2.ID)) || alwaysUpgradePower)) 
			{
				c.upgrade();
			}
			
			else if (c.hasTag(Tags.MONSTER) && (alwaysUpgradeMon || AbstractDungeon.player.hasRelic(BoosterPackMonsterEgg.ID)))
			{
				c.upgrade();
			}
			
			else if (c.hasTag(Tags.SPELL) && (alwaysUpgradeSpell || AbstractDungeon.player.hasRelic(BoosterPackSpellEgg.ID)))
			{
				c.upgrade();
			}
			
			else if (c.hasTag(Tags.TRAP) && (alwaysUpgradeTrap || AbstractDungeon.player.hasRelic(BoosterPackTrapEgg.ID)))
			{
				c.upgrade();
			}

			else if (upgradeCheck())
			{
				c.upgrade();
			}
			
			while (c.canUpgrade() && additionalUpgradeCheck(c) && maxUpgradeLoops > 0)
			{
				c.upgrade();
				Util.log("Upgraded " + c.name + " more than once for a Booster Pack");
				maxUpgradeLoops--;
				if (maxUpgradeLoops < 10) { Util.log("SOMETHING IS BEING UPGRADED WAY TOO MUCH IN A BOOSTER PACK DUDE! CARD=" + c.cardID); }
			}
		}
	}
	
	private void loadPack(String saveString)
	{
		cardsInPack.clear();
		String[] splitt = saveString.split("~");
		for (String s : splitt)
		{
			Util.log(packName + " is loading " + s + " from save");
			if (DuelistMod.mapForRunCardsLoading.containsKey(s))
			{
				Util.log("Found " + s + " for " + packName + " in DuelistMod.mapForRunCardsLoading");
				AbstractCard rand = DuelistMod.mapForRunCardsLoading.get(s).makeCopy();
				cardsInPack.add(rand);
			}
			else if (DuelistMod.mapForRunCardsLoading.containsKey("theDuelist:" + s))
			{
				s = "theDuelist:" + s;
				Util.log("Found " + s + " for " + packName + " in DuelistMod.mapForRunCardsLoading");
				AbstractCard rand = DuelistMod.mapForRunCardsLoading.get(s).makeCopy();
				cardsInPack.add(rand);
			}
			else
			{
				Util.log("Did not find " + s + " in DuelistMod.mapForRunCardsLoading for " + packName);
			}
		}
	}
	
	public void onLoad(String loaded) {
		if (!loaded.equals(""))
		{
			loadPack(loaded);
		}
	}

	public String onSave() {
		String toRet = "";
		for (AbstractCard c : cardsInPack)
		{
			toRet += c.cardID + "~";
		}
		if (!toRet.equals("")) { return toRet; }
		return null;
	}
	
	public boolean canSpawn() { return true; }
	
	public ArrayList<AbstractCard> getCards()
	{
		return new ArrayList<AbstractCard>();
	}

	@Override
	public boolean claimReward() 
	{
		//if (!this.isDone && !this.ignoreReward)
		//{
			if (AbstractDungeon.player.hasRelic(BustedCrown.ID)) { AbstractDungeon.player.getRelic(BustedCrown.ID).flash(); }
			if (AbstractDungeon.player.hasRelic(BoosterPackMonsterEgg.ID)) { AbstractDungeon.player.getRelic(BoosterPackMonsterEgg.ID).flash(); }
			if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) 
			{
				//BoosterRewardScreen screen = new BoosterRewardScreen(this.goldCost);
				AbstractDungeon.cardRewardScreen.open(this.cards, this, "Keep 1 Card from the Pack");
				AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
			}
		//}
		return false;
	}
	
	protected boolean upgradeCheck()
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
	
	protected boolean additionalUpgradeCheck(AbstractCard c)
	{
		if (!(this instanceof OrbPack)) 
		{ 
			if ((c instanceof DuelistCard || c instanceof SearingBlow))
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
			else
			{
				return false;
			}
		}
		else { return false; }
	}
	
	public ArrayList<AbstractCard> findAllCards(CardType type, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(null, type, null, null, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(toFindFrom, type, null, null, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	
	public ArrayList<AbstractCard> findAllCards(CardType type, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(null, type, null, null, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(toFindFrom, type, null, null, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardType type, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(null, type, null, null, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(toFindFrom, type, null, null, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(null, null, null, rare, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(toFindFrom, null, null, rare, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(null, null, null, rare, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(toFindFrom, null, null, rare, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(null, null, null, rare, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(toFindFrom, null, null, rare, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(null, null, tag, null, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(toFindFrom, null, tag, null, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(null, null, tag, rare, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(toFindFrom, null, tag, rare, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(null, null, tag, rare, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(toFindFrom, null, tag, rare, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(null, null, tag, rare, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, CardRarity rare, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(toFindFrom, null, tag, rare, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(null, type, tag, null, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(toFindFrom, type, tag, null, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(null, type, tag, null, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(toFindFrom, type, tag, null, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(null, type, tag, null, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, CardType type, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(toFindFrom, type, tag, null, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(null, null, tag, null, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(toFindFrom, null, tag, null, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(CardTags tag, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(null, null, tag, null, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardTags tag, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(toFindFrom, null, tag, null, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(null, null, null, null, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, int amt, ArrayList<AbstractCard> cardsSoFar)
	{
		return findAllCards(toFindFrom, null, null, null, amt, cardsSoFar, null, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(null, null, null, null, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity)
	{
		return findAllCards(toFindFrom, null, null, null, amt, cardsSoFar, excludeRarity, null, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(null, null, null, null, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarity, CardRarity excludeRarityB)
	{
		return findAllCards(toFindFrom, null, null, null, amt, cardsSoFar, excludeRarity, excludeRarityB, null, null, null, null);
	}
	
	public ArrayList<AbstractCard> findAllCards(ArrayList<AbstractCard> toFindFrom, CardType type, CardTags tag, CardRarity rarity, int amt, ArrayList<AbstractCard> cardsSoFar, CardRarity excludeRarityA, CardRarity excludeRarityB, CardTags excludeTagA, CardTags excludeTagB, CardType excludeTypeA, CardType excludeTypeB)
	{
		boolean useOtherSet = toFindFrom != null && toFindFrom.size() >= 1;
		if (!allowBasics && onlyBasics) { allowBasics = true; }
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		dynMap.clear();
		for (AbstractCard c : cardsSoFar) { dynMap.put(c.cardID, c.cardID); }
		if (useOtherSet)
		{
			for (AbstractCard c : toFindFrom)
			{
				if (!dynMap.containsKey(c.cardID))
				{
					boolean allowRare = rarity == null || c.rarity.equals(rarity);
					boolean secondaryRare = ((!c.rarity.equals(excludeRarityA) || excludeRarityA == null) && (!c.rarity.equals(excludeRarityB) || excludeRarityB == null));
					boolean allowType = type == null || c.type.equals(type);
					boolean secondaryType = ((!c.type.equals(excludeTypeA) || excludeTypeA == null) && (!c.type.equals(excludeTypeB) || excludeTypeB == null));
					boolean megaTypeFilter = tag == null || tag == Tags.MEGATYPED || !c.hasTag(Tags.MEGATYPED);
					boolean allowTag = tag == null || c.hasTag(tag);
					boolean secondaryTag = ((!c.hasTag(excludeTagA) || excludeTagA == null) && (!c.hasTag(excludeTagB) || excludeTagB == null));
					if (allowRare && secondaryRare)
					{
						if (allowTag && secondaryTag && megaTypeFilter)
						{
							if (allowType && secondaryType)
							{
								boolean allow = true;
								if (c instanceof DuelistCard)
								{
									DuelistCard dc = (DuelistCard)c;
									allow = dc.canSpawnInBooster(this);
								}
								if (allow) { toRet.add(c.makeStatEquivalentCopy()); }
							}
						}
					}
				}
			}
			
			while (toRet.size() > amt) {
				toRet.remove(AbstractDungeon.cardRandomRng.random(toRet.size() - 1));
			}
		}
		else
		{
			if (!onlyBasics)
			{
				for (AbstractCard c : TheDuelist.cardPool.group)
				{
					if (!dynMap.containsKey(c.cardID))
					{
						boolean allowRare = rarity == null || c.rarity.equals(rarity);
						boolean secondaryRare = ((!c.rarity.equals(excludeRarityA) || excludeRarityA == null) && (!c.rarity.equals(excludeRarityB) || excludeRarityB == null));
						boolean allowType = type == null || c.type.equals(type);
						boolean secondaryType = ((!c.type.equals(excludeTypeA) || excludeTypeA == null) && (!c.type.equals(excludeTypeB) || excludeTypeB == null));
						boolean megaTypeFilter = tag == null || tag == Tags.MEGATYPED || !c.hasTag(Tags.MEGATYPED);
						boolean allowTag = tag == null || (c.hasTag(tag) && (tag != Tags.MEGATYPED || !c.hasTag(Tags.MEGATYPED)));
						boolean secondaryTag = ((!c.hasTag(excludeTagA) || excludeTagA == null) && (!c.hasTag(excludeTagB) || excludeTagB == null));
						if (allowRare && secondaryRare)
						{
							if (allowTag && secondaryTag && megaTypeFilter)
							{
								if (allowType && secondaryType)
								{
									boolean allow = true;
									if (c instanceof DuelistCard)
									{
										DuelistCard dc = (DuelistCard)c;
										allow = dc.canSpawnInBooster(this);
									}
									if (allow) { toRet.add(c.makeStatEquivalentCopy()); }
								}
							}
						}
					}
				}
				
				while (toRet.size() > amt) {
					toRet.remove(AbstractDungeon.cardRandomRng.random(toRet.size() - 1));
				}
			}
			if (toRet.size() < amt && allowBasics)
			{
				for (AbstractCard c : DuelistMod.duelColorlessCards)
				{
					if (!dynMap.containsKey(c.cardID))
					{
						boolean allowRare = rarity == null || c.rarity.equals(rarity);
						boolean secondaryRare = ((!c.rarity.equals(excludeRarityA) || excludeRarityA == null) && (!c.rarity.equals(excludeRarityB) || excludeRarityB == null));
						boolean allowType = type == null || c.type.equals(type);
						boolean secondaryType = ((!c.type.equals(excludeTypeA) || excludeTypeA == null) && (!c.type.equals(excludeTypeB) || excludeTypeB == null));
						boolean megaTypeFilter = tag == null || tag == Tags.MEGATYPED || !c.hasTag(Tags.MEGATYPED);
						boolean allowTag = tag == null || c.hasTag(tag);
						boolean secondaryTag = ((!c.hasTag(excludeTagA) || excludeTagA == null) && (!c.hasTag(excludeTagB) || excludeTagB == null));
						if (allowRare && secondaryRare)
						{
							if (allowTag && secondaryTag && megaTypeFilter)
							{
								if (allowType && secondaryType)
								{
									boolean allow = true;
									if (c instanceof DuelistCard)
									{
										DuelistCard dc = (DuelistCard)c;
										allow = dc.canSpawnInBooster(this);
									}
									if (allow) { toRet.add(c.makeStatEquivalentCopy()); }
								}
							}
						}
					}
				}
				
				while (toRet.size() > amt) {
					toRet.remove(AbstractDungeon.cardRandomRng.random(toRet.size() - 1));
				}
			}
		}
		return toRet;
	}
	
	@Override
	public void render(final SpriteBatch sb) {
        if (this.hb.hovered) {
            sb.setColor(new Color(0.4f, 0.6f, 0.6f, 1.0f));
        }
        else {
            sb.setColor(new Color(0.5f, 0.6f, 0.6f, 0.8f));
        }
        if (this.hb.clickStarted) {
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, this.y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.scale * 0.98f, Settings.scale * 0.98f, 0.0f, 0, 0, 464, 98, false, false);
        }
        else {
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, this.y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 464, 98, false, false);
        }
        if (this.flashTimer != 0.0f) {
            sb.setColor(0.6f, 1.0f, 1.0f, this.flashTimer * 1.5f);
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, this.y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.scale * 1.03f, Settings.scale * 1.15f, 0.0f, 0, 0, 464, 98, false, false);
            sb.setBlendFunction(770, 771);
        }
        sb.setColor(Color.WHITE);
        switch (this.type) {
            case CARD: {
                sb.draw(ImageMaster.REWARD_CARD_NORMAL, RewardItem.REWARD_ITEM_X - 32.0f, this.y - 32.0f - 2.0f * Settings.scale, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
                break;
            }
            case GOLD: {
                sb.draw(ImageMaster.UI_GOLD, RewardItem.REWARD_ITEM_X - 32.0f, this.y - 32.0f - 2.0f * Settings.scale, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
                break;
            }
            case STOLEN_GOLD: {
                sb.draw(ImageMaster.UI_GOLD, RewardItem.REWARD_ITEM_X - 32.0f, this.y - 32.0f - 2.0f * Settings.scale, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
                break;
            }
            case RELIC: {
                this.relic.renderWithoutAmount(sb, new Color(0.0f, 0.0f, 0.0f, 0.25f));
                if (!this.hb.hovered) {
                    break;
                }
                if (this.relicLink != null) {
                    final ArrayList<PowerTip> tips = new ArrayList<PowerTip>();
                    tips.add(new PowerTip(this.relic.name, this.relic.description));
                    if (this.relicLink.type == RewardType.SAPPHIRE_KEY) {
                        tips.add(new PowerTip(RewardItem.TEXT[7], RewardItem.TEXT[8] + FontHelper.colorString(RewardItem.TEXT[6] + RewardItem.TEXT[9], "y")));
                    }
                    TipHelper.queuePowerTips(360.0f * Settings.scale, InputHelper.mY + 50.0f * Settings.scale, tips);
                    break;
                }
                this.relic.renderTip(sb);
                break;
            }
            case SAPPHIRE_KEY: {               
                break;
            }
            case EMERALD_KEY: {                
                break;
            }
            case POTION: {
                this.potion.renderLightOutline(sb);
                this.potion.render(sb);
                this.potion.generateSparkles(this.potion.posX, this.potion.posY, true);
                break;
            }
        }
        Color color;
        if (this.hb.hovered) {
            color = Settings.GOLD_COLOR;
        }
        else {
            color = this.textColor;
        }
        if (this.redText) {
            color = Settings.RED_TEXT_COLOR;
        }
        float xAdd = 60.0f * Settings.scale;
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, this.text, RewardItem.REWARD_ITEM_X + xAdd, this.y + 5.0f * Settings.scale, 1000.0f * Settings.scale, 0.0f, color);
        if (!this.hb.hovered) {
            for (final AbstractGameEffect e : this.effects) {
                e.render(sb);
            }
        }
        if (Settings.isControllerMode) {
            this.renderReticle(sb, this.hb);
        }
        if (isLinked && !this.isDone && !this.ignoreReward)
        {
        	this.renderRelicLink(sb);
        }
        this.hb.render(sb);
    }
	
    private void renderRelicLink(final SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.RELIC_LINKED, this.hb.cX - 64.0f, this.y - 64.0f + 52.0f * Settings.scale, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 128, 128, false, false);
    }
}
