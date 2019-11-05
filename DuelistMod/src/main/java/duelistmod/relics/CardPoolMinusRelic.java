package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.poolhelpers.GlobalPoolHelper;
import duelistmod.ui.DuelistCardSelectScreen;

public class CardPoolMinusRelic extends DuelistRelic implements ClickableRelic
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("CardPoolMinusRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolMinusRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolASRelic_Outline.png");
	public CardGroup pool;
	private DuelistCardSelectScreen dcss;

	public CardPoolMinusRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
		this.dcss = new DuelistCardSelectScreen(true);
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

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new CardPoolMinusRelic();
	}
	
	@Override
	public void update()
	{
		super.update();
		if (this.dcss != null && (this.dcss.selectedCards.size() != 0) && !DuelistMod.selectingForRelics)
		{
			DuelistMod.coloredCards.clear();
			Map<String, String> removeCards = new HashMap<>();
			for (AbstractCard c : this.dcss.selectedCards) { c.unhover(); removeCards.put(c.name, c.name); }
			for (AbstractCard c : TheDuelist.cardPool.group) { if (!removeCards.containsKey(c.name)) { DuelistMod.toReplacePoolWith.add(c.makeStatEquivalentCopy()); }}
			DuelistMod.poolIsCustomized = true;
			DuelistMod.shouldReplacePool = true;
			if (AbstractDungeon.player.hasRelic(CardPoolRelic.ID)) { ((CardPoolRelic)AbstractDungeon.player.getRelic(CardPoolRelic.ID)).setDescription(); }
			this.dcss.selectedCards.clear();
			//AbstractDungeon.gridSelectScreen = new GridCardSelectScreen();
			CardCrawlGame.dungeon.initializeCardPools();
			GlobalPoolHelper.resetGlobalDeckFlags();

		}
	}

	@Override
	public void onRightClick() 
	{
		AbstractDungeon.gridSelectScreen = this.dcss;
		DuelistMod.toReplacePoolWith.clear();
		DuelistMod.selectingForRelics = true;
		DuelistMod.wasViewingSelectScreen = true;
		((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(this.pool, this.pool.size(), "Remove Cards from the Card Pool");
		
	}
}
