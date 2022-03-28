package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.poolhelpers.GlobalPoolHelper;
import duelistmod.interfaces.*;
import duelistmod.ui.DuelistCardSelectScreen;

public class CardPoolMinusRelic extends DuelistRelic implements ClickableRelic, VisitFromAnubisRemovalFilter
{
	public static final String ID = DuelistMod.makeID("CardPoolMinusRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolMinusRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolASRelic_Outline.png");
	public CardGroup pool;

	public CardPoolMinusRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
	}
	
	public void refreshPool()
	{
		if (DuelistMod.duelistChar != null)
		{
			pool.clear();
			pool.group.addAll(TheDuelist.cardPool.group);
			Collections.sort(pool.group);
		}
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new CardPoolMinusRelic();
	}

	@Override
	public void onRightClick() 
	{
		DuelistMod.toReplacePoolWith.clear();
		DuelistMod.duelistCardSelectScreen.open(true, this.pool, this.pool.size(), "Remove Cards from the Card Pool", this::confirmLogic);
	}

	private void confirmLogic(List<AbstractCard> selectedCards) {
		if (selectedCards.size() != 0) {
			DuelistMod.coloredCards.clear();
			Map<String, String> removeCards = new HashMap<>();
			for (AbstractCard c : selectedCards) {
				c.unhover();
				removeCards.put(c.name, c.name);
			}
			for (AbstractCard c : TheDuelist.cardPool.group) {
				if (!removeCards.containsKey(c.name)) {
					DuelistMod.toReplacePoolWith.add(c.makeStatEquivalentCopy());
				}
			}
			DuelistMod.poolIsCustomized = true;
			DuelistMod.shouldReplacePool = true;
			DuelistMod.relicReplacement = true;
			if (AbstractDungeon.player.hasRelic(CardPoolRelic.ID)) {
				((CardPoolRelic)AbstractDungeon.player.getRelic(CardPoolRelic.ID)).setDescription();
			}
			CardCrawlGame.dungeon.initializeCardPools();
			GlobalPoolHelper.resetGlobalDeckFlags();
		}
	}
}
