package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import duelistmod.abstracts.MutateCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class NecronizeStepTwo extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private final ArrayList<AbstractCard> mutatePool;
	private final int mutateOptions;
	private final Map<UUID, UUID> mutateMap;

	public NecronizeStepTwo(ArrayList<AbstractCard> mutateCards, int mutateOptions, Map<UUID, UUID> mutateMap)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.mutateOptions = mutateOptions;
		this.mutatePool = mutateCards;
		this.mutateMap = mutateMap;
		Util.log("Mutate Step two mutation pool size: "+ this.mutatePool.size());
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			ArrayList<MutateCard> mcards = Util.getMutateOptions(this.mutateOptions, this.mutatePool);
			for (MutateCard m : mcards) { tmp.addToTop(m); }
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < 1; i++) { tmp.addToTop(new CancelCard()); }}
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Mutate", false, false, false, false); 
			}
			else
			{
				Util.log("MutateStepTwoAction got an empty card group for Mutate options when attempting to open the Mutate options screen");
			}
			tickDuration();
			return;
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				if (c instanceof MutateCard)
				{
					MutateCard mc = (MutateCard)c;
					for (AbstractCard mut : this.mutatePool)
					{
						UUID ref = this.mutateMap.get(mut.uuid);
						for (final AbstractCard abstractCard2 : GetAllInBattleInstances.get(ref)) {
							mc.runMutation(abstractCard2);
							if (!abstractCard2.hasTag(Tags.UNDEAD)) { abstractCard2.tags.add(Tags.UNDEAD); }
							Util.log("Mutation running on " + abstractCard2.cardID);
							Util.log("Necronized!");
						}
					}
				}				
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
