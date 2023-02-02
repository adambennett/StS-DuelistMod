package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class MonsterEggRelicAction extends AbstractGameAction {
	ArrayList<AbstractCard> cardToModify;
	
	
	public MonsterEggRelicAction(ArrayList<AbstractCard> cards) {
		this.setValues(this.target, this.source, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = new ArrayList<AbstractCard>();
		this.cardToModify.addAll(cards);
	}
	
	@Override
	public void update() 
	{
		for (AbstractCard c : this.cardToModify)
		{
			c.unhover();
			c.stopGlowing();
			AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f, true));
		}
		this.isDone = true;
	}
	
}
