package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.variables.Tags;

public class BrilliantFusionAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private final ArrayList<AbstractCard> mutatePool;
	private final int amtToMutate;
	private final int mutateOptions;
	private final int copiesRes;
	private final boolean allowNonZombs;
	private final AbstractMonster targ;
	private Map<UUID, UUID> mutateMap = new HashMap<>();

	public BrilliantFusionAction(int amtToMutate, int copiesToResummon, AbstractMonster target)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amtToMutate = amtToMutate;
		this.mutateOptions = 3;
		this.allowNonZombs = false;
		this.mutatePool = this.p.hand.group;
		this.targ = target;
		this.copiesRes = copiesToResummon;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);			
			for (AbstractCard c : this.mutatePool)
			{
				c.unhover();
				c.isSelected = false;
				if ((c.hasTag(Tags.ZOMBIE) || this.allowNonZombs) && !c.hasTag(Tags.NO_MUTATE))
				{
					AbstractCard ref = c.makeStatEquivalentCopy();
					tmp.addToBottom(ref);
					mutateMap.put(ref.uuid, c.uuid);
				}
			}
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			int realSize = tmp.group.size();
			if (realSize > 0)
			{
				if (realSize < this.amtToMutate) 
				{ 
					if (this.canCancel) { for (int i = 0; i < realSize; i++) { tmp.addToTop(new CancelCard()); }}
					if (this.amtToMutate != 1) { AbstractDungeon.gridSelectScreen.open(tmp, realSize, "Choose " + realSize + " cards for Brilliant Fusion", false, false, false, false); 	 }
					else { AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose a card for Brilliant Fusion", false, false, false, false); 	 }
				}
				else
				{
					if (this.canCancel) { for (int i = 0; i < this.amtToMutate; i++) { tmp.addToTop(new CancelCard()); }}
					if (this.amtToMutate != 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amtToMutate, "Choose " + this.amtToMutate + " cards for Brilliant Fusion", false, false, false, false); 	 }
					else { AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose a card for Brilliant Fusion", false, false, false, false); 	 }
				}
				tickDuration();
				return;
			}
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			ArrayList<AbstractCard> ref = new ArrayList<>();
			ref.addAll(AbstractDungeon.gridSelectScreen.selectedCards);
			this.addToBot(new BrilliantFusionStepTwo(ref, this.mutateOptions, mutateMap, this.copiesRes, this.targ));
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
