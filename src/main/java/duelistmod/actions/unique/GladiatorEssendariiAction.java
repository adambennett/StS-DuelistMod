package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.pools.beast.GladiatorBeastEssedarii;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class GladiatorEssendariiAction extends AbstractGameAction {
	private final GladiatorBeastEssedarii caller;
	private final AnyDuelist duelist;

	public GladiatorEssendariiAction(GladiatorBeastEssedarii caller) {
		this.duelist = AnyDuelist.from(caller);
		this.caller = caller;
	}
	
	public void update() {
		ArrayList<AbstractCard> list = DuelistCard.findAllOfType(Tags.BEAST, this.caller.magicNumber);
		while (list.size() < this.caller.magicNumber && list.size() > 0) {
			AbstractCard rand = list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1)).makeStatEquivalentCopy();
			list.add(rand);
		}
		this.duelist.addCardsToHand(list);
		this.isDone = true;
	}
}
