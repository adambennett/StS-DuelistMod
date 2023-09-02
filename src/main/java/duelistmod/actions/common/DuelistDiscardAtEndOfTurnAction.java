package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.duelistPowers.BeastBattlefieldBarrierPower;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DuelistDiscardAtEndOfTurnAction extends AbstractGameAction {
    private static final float DURATION;

    public DuelistDiscardAtEndOfTurnAction() {
        this.duration = DURATION;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update() {
        if (this.duration == DURATION) {
            final Iterator<AbstractCard> c = AbstractDungeon.player.hand.group.iterator();
            while (c.hasNext()) {
                final AbstractCard e = c.next();
                if (isRetain(e)) {
                    AbstractDungeon.player.limbo.addToTop(e);
                    c.remove();
                }
            }
            this.addToTop(new DuelistRestoreRetainedCardsAction(AbstractDungeon.player.limbo));
            if (!AbstractDungeon.player.hasRelic("Runic Pyramid") && !AbstractDungeon.player.hasPower("Equilibrium")) {
                for (int tempSize = AbstractDungeon.player.hand.size(), i = 0; i < tempSize; ++i) {
                    this.addToTop(new DiscardAction(AbstractDungeon.player, null, AbstractDungeon.player.hand.size(), true, true));
                }
            }
            final ArrayList<AbstractCard> cards = (ArrayList<AbstractCard>)AbstractDungeon.player.hand.group.clone();
            Collections.shuffle(cards);
            for (final AbstractCard c2 : cards) {
                c2.triggerOnEndOfPlayerTurn();
            }
            for (DuelistCard enduring : DuelistMod.enduringCards) {
                enduring.onEndure();
            }
            DuelistMod.enduringCards.clear();
            this.isDone = true;
        }
    }

    @SuppressWarnings("RedundantIfStatement")
    public static boolean isRetain(AbstractCard c) {
        if (c.retain || c.selfRetain) {
            return true;
        }

        if (c.hasTag(Tags.BEAST) && AnyDuelist.from(c).hasPower(BeastBattlefieldBarrierPower.POWER_ID)) {
            return true;
        }

        return false;
    }

    static {
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}

