package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.powers.duelistPowers.VampireRetainerPower;
import duelistmod.variables.Tags;

public class DuelistEndOfTurnDiscardAction extends AbstractGameAction
{
    private static final float DURATION;
    
    public DuelistEndOfTurnDiscardAction() {
        this.duration = DuelistEndOfTurnDiscardAction.DURATION;
    }
    
    @Override
    public void update() {
        if (this.duration == DuelistEndOfTurnDiscardAction.DURATION) {
            final Iterator<AbstractCard> c = AbstractDungeon.player.hand.group.iterator();
            while (c.hasNext()) {
                final AbstractCard e = c.next();
                if (e.retain || e.selfRetain) {
                    AbstractDungeon.player.limbo.addToTop(e);
                    c.remove();
                }
                else if (e.hasTag(Tags.VAMPIRE) && AbstractDungeon.player.hasPower(VampireRetainerPower.POWER_ID))
                {
                	AbstractDungeon.player.limbo.addToTop(e);
                    c.remove();
                }
            }
            this.addToTop(new RestoreRetainedCardsAction(AbstractDungeon.player.limbo));
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
            this.isDone = true;
        }
    }
    
    static {
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
