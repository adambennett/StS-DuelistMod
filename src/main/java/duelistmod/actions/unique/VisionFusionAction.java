package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.powers.duelistPowers.VisionFusionPower;
import duelistmod.variables.Tags;

public class VisionFusionAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private final int magic;
	private final int magic2;
	private final int magic3;

	public VisionFusionAction(int magic, int magic2, int magic3)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.magic = magic;
		this.magic2 = magic2;
		this.magic3 = magic3;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			ArrayList<AbstractCard> list = DuelistCard.findAllOfTypeForResummon(Tags.MONSTER, this.magic);
			tmp.group.addAll(list);
			if (tmp.group.size() > 0)
			{
				tmp.addToTop(new CancelCard()); 
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose a card for Vision Fusion", false, false, false, false);
				
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
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{	
					if (this.p.hasPower(VisionFusionPower.POWER_ID))
			    	{
						VisionFusionPower pow = (VisionFusionPower) p.getPower(VisionFusionPower.POWER_ID);
			    		pow.resummonCard = c;						
			    		pow.updateDescription();
			    	}
					else
					{
						DuelistCard.applyPowerToSelf(new VisionFusionPower(this.magic2, this.magic3, c));	
					}					
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
