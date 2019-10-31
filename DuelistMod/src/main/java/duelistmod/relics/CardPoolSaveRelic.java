package duelistmod.relics;

import java.util.Collections;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.cards.tempCards.*;
import duelistmod.characters.TheDuelist;
import duelistmod.ui.DuelistCardSelectScreen;

public class CardPoolSaveRelic extends CustomRelic implements ClickableRelic
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("CardPoolSaveRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolSaveRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolSaveRelic_Outline.png");
	public CardGroup pool;
	private DuelistCardSelectScreen dcss;

	public CardPoolSaveRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.UNSPECIFIED);
		this.dcss = new DuelistCardSelectScreen(true);
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

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new CardPoolSaveRelic();
	}
	
	@Override
	public void update()
	{
		super.update();
		if (this.dcss != null && (this.dcss.selectedCards.size() != 0) && !DuelistMod.selectingCardPoolOptions)
		{
			for (AbstractCard c : this.dcss.selectedCards)
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
			this.dcss.selectedCards.clear();
			//AbstractDungeon.gridSelectScreen = new GridCardSelectScreen();
		}
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
		AbstractDungeon.gridSelectScreen = this.dcss;
		DuelistMod.wasViewingSelectScreen = true;
		DuelistMod.selectingCardPoolOptions = true;
		setupSaveSlots();
		((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(this.pool, 1, "Select an Save Slot");
	}
}
