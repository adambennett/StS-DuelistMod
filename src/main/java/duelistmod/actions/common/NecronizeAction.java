package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.helpers.SelectScreenHelper;
import duelistmod.variables.Tags;

public class NecronizeAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private final ArrayList<AbstractCard> mutatePool;
	private final int amtToMutate;
	private final int mutateOptions;
	private final boolean allowNonZombs;
	private final boolean ghostrick;
	private final boolean vampire;
	private Map<UUID, UUID> mutateMap = new HashMap<>();

	public NecronizeAction(int amtToMutate)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amtToMutate = amtToMutate;
		this.mutateOptions = 3;
		this.allowNonZombs = false;
		this.mutatePool = this.p.hand.group;
		this.ghostrick = false;
		this.vampire = false;
	}
	
	public NecronizeAction(ArrayList<AbstractCard> toMutateFrom, int amtToMutate, int mutateOptions, boolean allowNonZombies)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amtToMutate = amtToMutate;
		this.mutateOptions = mutateOptions;
		this.allowNonZombs = allowNonZombies;
		this.mutatePool = toMutateFrom;
		this.ghostrick = false;
		this.vampire = false;
	}
	
	public NecronizeAction(ArrayList<AbstractCard> toMutateFrom, int amtToMutate, int mutateOptions, boolean allowNonZombies, boolean ghostrick)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amtToMutate = amtToMutate;
		this.mutateOptions = mutateOptions;
		this.allowNonZombs = allowNonZombies;
		this.mutatePool = toMutateFrom;
		this.ghostrick = true;
		this.vampire = false;
	}
	
	public NecronizeAction(ArrayList<AbstractCard> toMutateFrom, int amtToMutate, int mutateOptions, boolean allowNonZombies, boolean ghostrick, boolean vampire)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amtToMutate = amtToMutate;
		this.mutateOptions = mutateOptions;
		this.allowNonZombs = allowNonZombies;
		this.mutatePool = toMutateFrom;
		this.ghostrick = false;
		this.vampire = true;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			if (this.ghostrick)
			{
				for (AbstractCard c : this.mutatePool)
				{
					c.unhover();
					c.stopGlowing();
					c.isSelected = false;
					if (c.hasTag(Tags.GHOSTRICK) && !c.hasTag(Tags.NO_MUTATE))
					{
						AbstractCard ref = c.makeStatEquivalentCopy();
						tmp.addToBottom(ref);
						mutateMap.put(ref.uuid, c.uuid);
					}
				}
			}
			else if (this.vampire)
			{
				for (AbstractCard c : this.mutatePool)
				{
					c.unhover();
					c.stopGlowing();
					c.isSelected = false;
					if (c.hasTag(Tags.VAMPIRE) && !c.hasTag(Tags.NO_MUTATE))
					{
						AbstractCard ref = c.makeStatEquivalentCopy();
						tmp.addToBottom(ref);
						mutateMap.put(ref.uuid, c.uuid);
					}
				}
			}
			else
			{
				for (AbstractCard c : this.mutatePool)
				{
					
					c.unhover();
					c.stopGlowing();
					c.isSelected = false;
					if ((c.hasTag(Tags.ZOMBIE) || this.allowNonZombs) && !c.hasTag(Tags.NO_MUTATE))
					{
						AbstractCard ref = c.makeStatEquivalentCopy();
						tmp.addToBottom(ref);
						mutateMap.put(ref.uuid, c.uuid);
					}
				}
			}

			//Collections.sort(tmp.group, GridSort.getComparator());
			int realSize = tmp.group.size();
			if (realSize > 0)
			{
				if (realSize < this.amtToMutate) 
				{ 
					//if (this.canCancel) { for (int i = 0; i < realSize; i++) { tmp.addToTop(new CancelCard()); }}
					if (this.amtToMutate != 1) { SelectScreenHelper.open(tmp, realSize, "Choose " + realSize + " cards to Mutate"); 	 }
					else { SelectScreenHelper.open(tmp, 1, "Choose a card to Mutate"); 	 }
				}
				else
				{
					//if (this.canCancel) { for (int i = 0; i < this.amtToMutate; i++) { tmp.addToTop(new CancelCard()); }}
					if (this.amtToMutate != 1) { SelectScreenHelper.open(tmp, this.amtToMutate, "Choose " + this.amtToMutate + " cards to Mutate"); 	 }
					else { SelectScreenHelper.open(tmp, 1, "Choose a card to Mutate"); 	 }
				}
				tickDuration();
				return;
			}
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			ArrayList<AbstractCard> ref = new ArrayList<>();
			ref.addAll(AbstractDungeon.gridSelectScreen.selectedCards);
			this.addToBot(new NecronizeStepTwo(ref, this.mutateOptions, mutateMap));
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
