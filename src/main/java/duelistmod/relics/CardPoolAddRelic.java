package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.*;
import duelistmod.helpers.crossover.*;
import duelistmod.interfaces.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.variables.Tags;

public class CardPoolAddRelic extends DuelistRelic implements ClickableRelic, VisitFromAnubisRemovalFilter
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("CardPoolAddRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolAddRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolASRelic_Outline.png");
	public CardGroup pool;

	public CardPoolAddRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
	}
	
	public void refreshPool()
	{
		pool.clear();
		pool.group.addAll(generateAddableCards());
		Collections.sort(pool.group);
	}
	
	private ArrayList<AbstractCard> generateAddableCards()
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		for (AbstractCard c : TheDuelist.cardPool.group) { if (!map.containsKey(c.name)) { map.put(c.name, c.name); }}
		for (AbstractCard c : AbstractDungeon.colorlessCardPool.group) { if (!map.containsKey(c.name)) { map.put(c.name, c.name); }}
		for (AbstractCard c : DuelistMod.myCards)
		{
			if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.hasTag(Tags.TOKEN) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
		}
		if (DuelistMod.baseGameCards)
		{
			for (AbstractCard c : BaseGameHelper.getAllBaseGameCards())
			{
				if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
			}
		}
		
		if (DuelistMod.isInfiniteSpire)
		{
			for (AbstractCard c : InfiniteSpireHelper.getAllBlackCards())
			{
				if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
			}
		}

		if (DuelistMod.isClockwork)
		{
			for (AbstractCard c : ClockworkHelper.getAllCards())
			{
				if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
			}
		}
		
		if (DuelistMod.isConspire)
		{
			for (AbstractCard c : ConspireHelper.getAllCards())
			{
				if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
			}
		}
		
		if (DuelistMod.isDisciple)
		{
			for (AbstractCard c : DiscipleHelper.getAllCards())
			{
				if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
			}
		}
		
		if (DuelistMod.isGatherer)
		{
			for (AbstractCard c : GathererHelper.getAllCards())
			{
				if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
			}
		}
		
		if (DuelistMod.isHubris)
		{
			for (AbstractCard c : HubrisHelper.getAllCards())
			{
				if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
			}
		}
		
		if (DuelistMod.isReplay)
		{
			for (AbstractCard c : ReplayHelper.getAllCards())
			{
				if (!c.type.equals(CardType.CURSE) && !c.type.equals(CardType.STATUS) && !map.containsKey(c.name) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cards.add(c.makeCopy()); map.put(c.name, c.name); }
			}
		}
		
		return cards;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new CardPoolAddRelic();
	}

	@Override
	public void onRightClick() 
	{
		if (this.pool.size() > 0)
		{
			DuelistMod.toReplacePoolWith.clear();
			DuelistMod.duelistCardSelectScreen.open(true, this.pool, this.pool.size(), "Add Cards into the Card Pool", this::confirmLogic);
		}
	}

	private void confirmLogic(List<AbstractCard> selectedCards) {
		if (selectedCards.size() != 0) {
			DuelistMod.coloredCards.clear();
			for (AbstractCard c : selectedCards) {
				DuelistMod.toReplacePoolWith.add(c.makeStatEquivalentCopy());
			}
			DuelistMod.toReplacePoolWith.addAll(TheDuelist.cardPool.group);
			DuelistMod.poolIsCustomized = true;
			DuelistMod.shouldReplacePool = true;
			DuelistMod.relicReplacement = true;
			if (AbstractDungeon.player.hasRelic(CardPoolRelic.ID)) {
				((CardPoolRelic)AbstractDungeon.player.getRelic(CardPoolRelic.ID)).setDescription();
			}
			CardCrawlGame.dungeon.initializeCardPools();
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				config.setBool("poolIsCustomized", DuelistMod.poolIsCustomized);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
