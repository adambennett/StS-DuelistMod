package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.Util;

public class BrilliantFusionStepTwo extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private final ArrayList<AbstractCard> mutatePool;
	private final int mutateOptions;
	private final int copiesRes;
	private final AbstractMonster target;
	private final Map<UUID, UUID> mutateMap;

	public BrilliantFusionStepTwo(ArrayList<AbstractCard> mutateCards, int mutateOptions, Map<UUID, UUID> mutateMap, int copies, AbstractMonster targ)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.mutateOptions = mutateOptions;
		this.mutatePool = mutateCards;
		this.mutateMap = mutateMap;
		this.copiesRes = copies;
		this.target = targ;
		Util.log("Brilliant Fusion Step two mutation pool size: "+ this.mutatePool.size());
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
				if (this.copiesRes == 1) { AbstractDungeon.gridSelectScreen.open(tmp, 1, "Mutate and Resummon 1 copy", false, false, false, false); }
				else { AbstractDungeon.gridSelectScreen.open(tmp, 1, "Mutate and Resummon " + this.copiesRes + " copies", false, false, false, false); }
			}
			else
			{
				Util.log("BrilliantFusionStepTwoAction got an empty card group for Mutate options when attempting to open the Mutate options screen");
			}
			tickDuration();
			return;
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			ArrayList<AbstractCard> reverseList = new ArrayList<>();
			for (int i = AbstractDungeon.gridSelectScreen.selectedCards.size() - 1; i > -1; i--)
			{
				reverseList.add(AbstractDungeon.gridSelectScreen.selectedCards.get(i));
			}
			for (AbstractCard c : reverseList)
			{
				c.unhover();
				if (c instanceof MutateCard)
				{
					MutateCard mc = (MutateCard)c;
					for (AbstractCard mut : this.mutatePool)
					{
						UUID ref = this.mutateMap.get(mut.uuid);
						AbstractCard forRes = new CancelCard();
						for (final AbstractCard abstractCard2 : GetAllInBattleInstances.get(ref)) {
							mc.runMutation(abstractCard2);
							if (abstractCard2 != null && forRes instanceof CancelCard)
							{
								Util.log("BrilliantFusionStepTwo: Found a good version of the mutated card to copy with a CancelCard still up, so we are saving this one as a reference.");
								forRes = abstractCard2;
							}
							Util.log("Mutation running on " + abstractCard2.cardID);
						}
						
						if (!(forRes instanceof CancelCard) && forRes != null) 
						{ 
							Util.log("BrilliantFusionAction all good on finding a card to Resummon, should be resummoning: " + forRes.cardID);
							if (this.target != null)
							{
								DuelistCard.resummon(forRes, this.target, this.copiesRes);
							}
							else
							{
								AbstractMonster rand = AbstractDungeon.getRandomMonster();
								if (rand != null) { DuelistCard.resummon(forRes, rand, this.copiesRes); }
							}
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
