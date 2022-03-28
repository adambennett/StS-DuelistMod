package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.cards.other.tempCards.*;
import duelistmod.characters.TheDuelist;
import duelistmod.interfaces.*;

public class CardPoolSaveRelic extends DuelistRelic implements ClickableRelic, VisitFromAnubisRemovalFilter
{
	public static final String ID = DuelistMod.makeID("CardPoolSaveRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolSaveRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolSaveRelic_Outline.png");
	public CardGroup pool;

	public CardPoolSaveRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.UNSPECIFIED);
	}
	
	public void refreshPool()
	{
		if (AbstractDungeon.player.hasRelic(CardPoolOptionsRelic.ID))
		{
			CardPoolOptionsRelic rel = (CardPoolOptionsRelic)AbstractDungeon.player.getRelic(CardPoolOptionsRelic.ID);
			pool.clear();
			for (AbstractCard c : rel.pool.group)
			{
				if (c instanceof CardPoolOptionSaveA) { pool.group.add(c.makeCopy()); }
				else if (c instanceof CardPoolOptionSaveB) { pool.group.add(c.makeCopy()); }
				else if (c instanceof CardPoolOptionSaveC) { pool.group.add(c.makeCopy()); }
			}			
			Collections.sort(pool.group);
		}
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new CardPoolSaveRelic();
	}

	private void setupSaveSlots()
	{
		for (AbstractCard c : this.pool.group)
		{
			if (c instanceof CardPoolOptionSaveA) { 
				CardPoolOptionSaveA ca = (CardPoolOptionSaveA)c;
				ca.loadCorrectDesc(true);
			}
			else if (c instanceof CardPoolOptionSaveB) { 
				CardPoolOptionSaveB ca = (CardPoolOptionSaveB)c;
				ca.loadCorrectDesc(true);
			}
			else if (c instanceof CardPoolOptionSaveC) { 
				CardPoolOptionSaveC ca = (CardPoolOptionSaveC)c;
				ca.loadCorrectDesc(true);
			}
		}
	}

	@Override
	public void onRightClick() 
	{
		refreshPool();
		setupSaveSlots();
		DuelistMod.duelistCardSelectScreen.open(true, this.pool, 1, "Select a Save Slot", this::confirmLogic);
	}

	private void confirmLogic(List<AbstractCard> selectedCards) {
		if (selectedCards.size() != 0)
		{
			for (AbstractCard c : selectedCards)
			{
				if (c instanceof CardPoolOptionSaveA) {
					CardPoolOptionSaveA ca = (CardPoolOptionSaveA)c;
					ca.setPool(TheDuelist.cardPool.group);
				}
				else if (c instanceof CardPoolOptionSaveB) {
					CardPoolOptionSaveB ca = (CardPoolOptionSaveB)c;
					ca.setPool(TheDuelist.cardPool.group);
				}
				else if (c instanceof CardPoolOptionSaveC) {
					CardPoolOptionSaveC ca = (CardPoolOptionSaveC)c;
					ca.setPool(TheDuelist.cardPool.group);
				}
			}
		}
	}
}
