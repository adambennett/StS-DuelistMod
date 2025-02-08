package duelistmod.actions.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;

import java.util.ArrayList;
import java.util.Iterator;

public class ShuffleOnlyTypedAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{
	private static final TutorialStrings tutorialStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getTutorialString("Shuffle Tip");
	public static final String[] MSG = tutorialStrings.TEXT;
	public static final String[] LABEL = tutorialStrings.LABEL;

	private boolean shuffled = false; private boolean vfxDone = false;
	private int count = 0;
	private CardType tag;


	public ShuffleOnlyTypedAction(CardType tag)
	{
		setValues(null, null, 0);
		this.actionType = ActionType.SHUFFLE;

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
			if (c.type.equals(this.tag))
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
				if (ea.type.equals(tag)) { ca.remove(); }
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
						AbstractDungeon.player.masterDeck.shuffle();
						DuelistMod.logger.info("shuffling " + c.name);
					}
				}
				else
				{
					for (AbstractCard c : taggedPile.group)
					{
						AbstractDungeon.getCurrRoom().souls.shuffle(c, false);
						AbstractDungeon.player.masterDeck.shuffle();
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
