package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.EndureCard;
import duelistmod.powers.duelistPowers.BeastBattlefieldBarrierPower;
import duelistmod.powers.duelistPowers.DoubleAttackPower;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
            List<AbstractCard> retainOverflows = new ArrayList<>();
            while (c.hasNext()) {
                final AbstractCard e = c.next();
                if (isRetain(e)) {
                    AbstractDungeon.player.limbo.addToTop(e);
                    retainOverflows.add(e);
                    c.remove();
                }
            }
            if (AbstractDungeon.player.hasPower(DoubleAttackPower.POWER_ID)) {
                DoubleAttackPower power = (DoubleAttackPower) AbstractDungeon.player.getPower(DoubleAttackPower.POWER_ID);
                power.removeAfterRetain();
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
            for (final AbstractCard r2 : retainOverflows) {
                if (r2 instanceof DuelistCard) {
                    ((DuelistCard)r2).triggerOverflowEffects(null);
                }
            }
            for (DuelistCard enduring : DuelistMod.enduringCards) {
                if (enduring instanceof EndureCard) {
                    ((EndureCard)enduring).onEndure(AnyDuelist.from(AbstractDungeon.player));
                }
            }
            AnyDuelist.setPlayerGainedDexterityThisTurn(false);
            DuelistMod.enduringCards.clear();
            this.isDone = true;
        }
    }

    @SuppressWarnings("RedundantIfStatement")
    public static boolean isRetain(AbstractCard c) {
        if (c.retain || c.selfRetain) {
            return true;
        }

        AnyDuelist duelist = AnyDuelist.from(c);
        if (duelist.hasPower(DoubleAttackPower.POWER_ID)) {
            return true;
        }

        if (c.hasTag(Tags.BEAST) && duelist.hasPower(BeastBattlefieldBarrierPower.POWER_ID)) {
            return true;
        }

        return false;
    }

    static {
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}

