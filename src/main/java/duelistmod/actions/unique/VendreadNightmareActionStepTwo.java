package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.*;
import duelistmod.actions.utility.AddCardToHandAndMutate;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.helpers.Util;

public class VendreadNightmareActionStepTwo extends AbstractGameAction
{
	private AbstractPlayer p;
	private final int mutateOptions;
	private final AbstractMonster target;
	private final AbstractCard ref;

	public VendreadNightmareActionStepTwo(int mutateOptions,AbstractMonster targ, AbstractCard cardRef)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.mutateOptions = mutateOptions;
		this.target = targ;
		this.ref = cardRef;
		
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			ArrayList<AbstractCard> mutatePool = new ArrayList<AbstractCard>();
			mutatePool.add(this.ref);
			ArrayList<MutateCard> mcards = Util.getMutateOptions(this.mutateOptions, mutatePool);
			for (MutateCard m : mcards) { tmp.addToTop(m); }
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				//tmp.addToTop(new CancelCard());
				SelectScreenHelper.open(tmp, 1, "Mutate for Vendread Nightmare");
			}
			tickDuration();
			return;
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				if (c instanceof MutateCard)
				{
					MutateCard mc = (MutateCard)c;
					if (this.ref != null)
					{
						mc.runMutation(this.ref);
						this.addToBot(new AddCardToHandAndMutate(this.ref, false, mc));
						if (this.target != null)
						{
							DuelistCard.resummon(this.ref, this.target);
						}
						else
						{
							AbstractMonster rand = AbstractDungeon.getRandomMonster();
							if (rand != null) { DuelistCard.resummon(this.ref, rand); }
						}
					}
				}	
				else
				{
					if (this.ref != null)
					{
						DuelistCard.addCardToHand(this.ref.makeStatEquivalentCopy());
						if (this.target != null)
						{
							DuelistCard.resummon(this.ref, this.target);
						}
						else
						{
							AbstractMonster rand = AbstractDungeon.getRandomMonster();
							if (rand != null) { DuelistCard.resummon(this.ref, rand); }
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
