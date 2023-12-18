package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.enums.CardSource;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.*;

public class ShuffleOnlyCardSourceAction extends AbstractGameAction {
	private static final TutorialStrings tutorialStrings = languagePack.getTutorialString("Shuffle Tip");
	public static final String[] MSG = tutorialStrings.TEXT;
	public static final String[] LABEL = tutorialStrings.LABEL;
	private boolean shuffled = false; private boolean vfxDone = false;
	private final CardSource tag;
	private final DrawFromCardSourceAction parent;


	public ShuffleOnlyCardSourceAction(DrawFromCardSourceAction parent, CardSource tag) {
		setValues(null, null, 0);
		this.actionType = ActionType.SHUFFLE;
		this.parent = parent;

		if (!(Boolean) TipTracker.tips.get("SHUFFLE_TIP")) {
			AbstractDungeon.ftue = new com.megacrit.cardcrawl.ui.FtueTip(LABEL[0], MSG[0], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, com.megacrit.cardcrawl.ui.FtueTip.TipType.SHUFFLE);
			TipTracker.neverShowAgain("SHUFFLE_TIP");
		}
		for (AbstractRelic r : AbstractDungeon.player.relics) {
			r.onShuffle();
		}
		
		this.tag = tag;
	}

	public void update() {
		ArrayList<AbstractCard> taggedGroup = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
			if (this.parent.isMatchingCard(c, tag)) {
				taggedGroup.add(c);
			}
		}
		if (taggedGroup.size() > 0) {
            AbstractDungeon.player.discardPile.group.removeIf(ea -> this.parent.isMatchingCard(ea, tag));
			CardGroup taggedPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : taggedGroup) {
				taggedPile.addToRandomSpot(c);
			}
			taggedPile.shuffle(AbstractDungeon.shuffleRng);
			
			if (!this.shuffled) {
				this.shuffled = true;
				
			}
	
			if (!this.vfxDone) {
				if (taggedPile.group.size() > 10) {
					for (AbstractCard c : taggedPile.group) {
						AbstractDungeon.getCurrRoom().souls.shuffle(c, true);
						AbstractDungeon.player.masterDeck.shuffle();
					}
				} else {
					for (AbstractCard c : taggedPile.group) {
						AbstractDungeon.getCurrRoom().souls.shuffle(c, false);
						AbstractDungeon.player.masterDeck.shuffle();
					}
				}
				this.vfxDone = true;
			}
		}
		this.isDone = true;
	}
}
