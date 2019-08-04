package duelistmod.actions.unique;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import duelistmod.*;
import duelistmod.variables.*;

public class TheCreatorAction extends AbstractGameAction {
	private ArrayList<AbstractCard> cardToMake;
	private boolean randomSpot = false;
	private boolean cardOffset = false;

	public TheCreatorAction(AbstractCreature target, AbstractCreature source, ArrayList<AbstractCard> card,int amount, boolean randomSpot, boolean cardOffset) 
	{
		setValues(target, source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.001F;
		this.cardToMake = card;
		this.randomSpot = randomSpot;
		this.cardOffset = cardOffset;
	}

	@Override
	public void update() {
		if (this.duration == 0.001F) 
		{
			for (int i = 0; i < this.amount; i++) 
			{
				for (AbstractCard card : this.cardToMake)
				{
					if (!card.hasTag(Tags.NO_CREATOR) && !card.hasTag(Tags.TOKEN) && !card.rarity.equals(CardRarity.SPECIAL)) 
					{
						AbstractCard c = card.makeStatEquivalentCopy();
						if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) 
						{
				            c.isEthereal = true;
				            c.rawDescription = Strings.etherealForCardText + c.rawDescription;
				            c.initializeDescription();
						}
						if (c.cost > 0)
						{
							c.modifyCostForCombat(-(c.cost - 1));
							c.isCostModified = true;
						}
						else if (c.cost == 0)
						{
							c.updateCost(1);
							c.isCostModified = true;
						}
						AbstractDungeon.player.drawPile.addToRandomSpot(c);
						//AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, this.randomSpot, this.cardOffset));
					}
				}
			}
		}
		this.duration -= Gdx.graphics.getDeltaTime();
		tickDuration();
	}
}