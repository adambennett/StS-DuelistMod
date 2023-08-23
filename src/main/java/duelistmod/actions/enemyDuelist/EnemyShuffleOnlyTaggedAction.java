package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.dto.AnyDuelist;

import java.util.ArrayList;

public class EnemyShuffleOnlyTaggedAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {

	private boolean shuffled = false;
	private boolean vfxDone = false;
	private final CardTags tag;
	private final AnyDuelist duelist;

	public EnemyShuffleOnlyTaggedAction(CardTags tag, AnyDuelist duelist) {
		this.duelist = duelist;
		setValues(null, null, 0);
		this.actionType = ActionType.SHUFFLE;

		for (AbstractRelic r : this.duelist.relics()) {
			r.onShuffle();
		}

		this.tag = tag;
	}

	public void update() {
		ArrayList<AbstractCard> taggedGroup = new ArrayList<>();
		for (AbstractCard c : this.duelist.discardPile()) {
			if (c.hasTag(this.tag)) {
				taggedGroup.add(c);
			}
		}
		if (taggedGroup.size() > 0) {
			this.duelist.discardPile().removeIf(ea -> ea.hasTag(tag));
			CardGroup taggedPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : taggedGroup) { taggedPile.addToRandomSpot(c); }
			taggedPile.shuffle(AbstractDungeon.shuffleRng);

			if (!this.shuffled) {
				this.shuffled = true;
				
			}

			if (!this.vfxDone) {
				taggedPile.group.forEach(c -> this.duelist.drawPileGroup().addToBottom(c));
				this.duelist.drawPileGroup().shuffle();
				this.vfxDone = true;
			}
		}
		this.isDone = true;
	}
}
