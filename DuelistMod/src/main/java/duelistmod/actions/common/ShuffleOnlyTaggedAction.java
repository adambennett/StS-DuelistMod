package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;

public class ShuffleOnlyTaggedAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{
	private static final TutorialStrings tutorialStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getTutorialString("Shuffle Tip");
	public static final String[] MSG = tutorialStrings.TEXT;
	public static final String[] LABEL = tutorialStrings.LABEL;

	private boolean shuffled = false; private boolean vfxDone = false;
	private int count = 0;
	private CardTags tag;


	public ShuffleOnlyTaggedAction(CardTags tag)
	{
		setValues(null, null, 0);
		this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SHUFFLE;

		if (!((Boolean)TipTracker.tips.get("SHUFFLE_TIP")).booleanValue()) {
			AbstractDungeon.ftue = new com.megacrit.cardcrawl.ui.FtueTip(LABEL[0], MSG[0], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, com.megacrit.cardcrawl.ui.FtueTip.TipType.SHUFFLE);
			TipTracker.neverShowAgain("SHUFFLE_TIP");
		}
		for (AbstractRelic r : AbstractDungeon.player.relics) {
			r.onShuffle();
		}
		
		this.tag = tag;
	}

	public void update()
	{
		DuelistMod.logger.info("initial discard pile group size: " + AbstractDungeon.player.discardPile.group.size());
		ArrayList<AbstractCard> taggedGroup = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.discardPile.group)
		{
			if (c.hasTag(this.tag))
			{
				taggedGroup.add(c);
			}
		}
		if (taggedGroup.size() > 0)
		{		
			Iterator<AbstractCard> ca = AbstractDungeon.player.discardPile.group.iterator();
			while (ca.hasNext())
			{
				AbstractCard ea = (AbstractCard)ca.next();
				if (ea.hasTag(tag)) { ca.remove(); }
			}
			DuelistMod.logger.info("discard pile group size after removal: " + AbstractDungeon.player.discardPile.group.size());
			CardGroup taggedPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : taggedGroup) { taggedPile.addToRandomSpot(c); }
			taggedPile.shuffle(AbstractDungeon.shuffleRng);
			DuelistMod.logger.info("taggedPile group size: " + taggedPile.group.size());
			
			if (!this.shuffled) {
				this.shuffled = true;
				
			}
	
			if (!this.vfxDone) {
				
				if (taggedPile.group.size() > 10)
				{
					for (AbstractCard c : taggedPile.group)
					{
						AbstractDungeon.getCurrRoom().souls.shuffle(c, true);
						DuelistMod.logger.info("shuffling " + c.name);
					}
				}
				else
				{
					for (AbstractCard c : taggedPile.group)
					{
						AbstractDungeon.getCurrRoom().souls.shuffle(c, false);
						DuelistMod.logger.info("shuffling " + c.name);
					}
				}
				
				/*
				Iterator<AbstractCard> c = taggedPile.group.iterator(); 
				while (c.hasNext()) 
				{
					this.count += 1;
					AbstractCard e = (AbstractCard)c.next();
					c.remove();
					if (this.count < 11) 
					{
						AbstractDungeon.getCurrRoom().souls.shuffle(e, false);
						DuelistMod.logger.info("shuffling " + e.name);
					} 
					else 
					{
						AbstractDungeon.getCurrRoom().souls.shuffle(e, true);
						DuelistMod.logger.info("shuffling " + e.name);
					}
				}
				*/
				this.vfxDone = true;
			}
		}
		this.isDone = true;
	}
}
