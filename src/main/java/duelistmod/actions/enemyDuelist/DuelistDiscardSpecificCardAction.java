package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.dto.AnyDuelist;

public class DuelistDiscardSpecificCardAction extends AbstractGameAction {
    private final AnyDuelist duelist;
    private final AbstractCard targetCard;
    private CardGroup group;

    public DuelistDiscardSpecificCardAction(AbstractCard targetCard, AnyDuelist duelist) {
        this.targetCard = targetCard;
        this.actionType = ActionType.DISCARD;
        this.duration = Settings.ACTION_DUR_FAST;
        this.duelist = duelist;
    }

    public DuelistDiscardSpecificCardAction(AbstractCard targetCard, CardGroup group, AnyDuelist duelist) {
        this.targetCard = targetCard;
        this.group = group;
        this.actionType = ActionType.DISCARD;
        this.duration = Settings.ACTION_DUR_FAST;
        this.duelist = duelist;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.group == null) {
                this.group = this.duelist.handGroup();
            }

            if (this.group.contains(this.targetCard)) {
                this.group.moveToDiscardPile(this.targetCard);
                if (this.duelist.player()) {
                    GameActionManager.incrementDiscard(false);
                }
                this.targetCard.triggerOnManualDiscard();
            }
        }
        this.tickDuration();
    }
}
