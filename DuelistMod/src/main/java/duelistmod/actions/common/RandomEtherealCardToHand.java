package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.*;

public class RandomEtherealCardToHand extends AbstractGameAction {

	private AbstractCard.CardTags randomTag;
	
    public RandomEtherealCardToHand(CardTags tagToReturnFrom) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.randomTag = tagToReturnFrom;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = DuelistCard.returnTrulyRandomFromSet(randomTag).makeStatEquivalentCopy();
            if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) 
            {
                c.isEthereal = true;
                c.rawDescription = Strings.etherealForCardText + c.rawDescription;
            }              
            c.initializeDescription();
            AbstractDungeon.actionManager.addToBottom(new MakeStatEquivalentLocal(c));
            this.tickDuration();
        }
        this.isDone = true;
    }

    public class MakeStatEquivalentLocal extends AbstractGameAction {
        private AbstractCard c;

        public MakeStatEquivalentLocal(AbstractCard c) {
            this.actionType = ActionType.CARD_MANIPULATION;
            this.duration = Settings.ACTION_DUR_FAST;
            this.c = c;

        }

        public void update() {
            if (this.duration == Settings.ACTION_DUR_FAST) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
                tickDuration();
                this.isDone = true;
            }
        }
    }

}
