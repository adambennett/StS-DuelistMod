package duelistmod.actions.unique;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class CrashbugRoadAction extends AbstractGameAction {
	private AbstractCard cardToMake;
	private boolean randomSpot = false;
	private boolean cardOffset = false;
	
	private static int roll = 0;

	public CrashbugRoadAction(AbstractCreature target, AbstractCreature source, AbstractCard card,int amount, boolean randomSpot, boolean cardOffset) 
	{
		setValues(target, source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.10F;
		this.cardToMake = card;
		this.randomSpot = randomSpot;
		this.cardOffset = cardOffset;
	}

	@Override
	public void update() {
		if (this.duration == 0.10F) 
		{
			for (int i = 0; i < this.amount; i++) 
			{
				AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
				roll = AbstractDungeon.cardRandomRng.random(1, 10);
				if (roll <= 3 && c.cost > 0)
				{					
					c.modifyCostForCombat(-(c.cost - 1));
					c.isCostModified = true;					
				}
				AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, this.randomSpot, this.cardOffset));
			}
		}
		this.duration -= Gdx.graphics.getDeltaTime();
		tickDuration();
	}
}