package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class VendreadNightmareAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractCard> mutatePool;
	private final int mutateOptions;
	private final int zombieChoices;
	private final boolean allowNonZombs;
	private final AbstractMonster targ;
	private Map<UUID, UUID> mutateMap = new HashMap<>();

	public VendreadNightmareAction(AbstractMonster target, int amtOfOptions)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.mutateOptions = 3;
		this.allowNonZombs = false;
		this.targ = target;
		this.zombieChoices = amtOfOptions;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);	
			
			ArrayList<AbstractCard> list = DuelistCard.findAllOfTypeForResummon(Tags.VENDREAD, this.zombieChoices);
			this.mutatePool = new ArrayList<>();
			this.mutatePool.addAll(list);
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
			
			if (tmp.group.size() > 0)
			{
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose a Zombie for Vendread Nightmare", false, false, false, false); 
			}
			tickDuration();
			return;
			
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			this.addToBot(new VendreadNightmareActionStepTwo(this.mutateOptions, this.targ, AbstractDungeon.gridSelectScreen.selectedCards.get(0)));
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
