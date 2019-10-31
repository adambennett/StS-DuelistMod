package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;

import duelistmod.abstracts.DuelistStance;

public class DuelistChangeStanceAction extends AbstractGameAction
{
    private String id;
    
    public DuelistChangeStanceAction(final String stanceId) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.id = stanceId;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hasPower("CannotChangeStancePower")) {
                this.isDone = true;
                return;
            }
            final AbstractStance oldStance = AbstractDungeon.player.stance;
            if (!oldStance.ID.equals(this.id)) 
            {
                final AbstractStance newStance = DuelistStance.getStanceFromName(this.id);
                for (final AbstractPower p : AbstractDungeon.player.powers) {
                    p.onChangeStance(oldStance, newStance);
                }
                for (final AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onChangeStance(oldStance, newStance);
                }
                oldStance.onExitStance();
                (AbstractDungeon.player.stance = newStance).onEnterStance();
                AbstractDungeon.player.switchedStance();
                for (final AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    c.triggerExhaustedCardsOnStanceChange(newStance);
                }
                AbstractDungeon.player.onStanceChange(this.id);
            }
            AbstractDungeon.onModifyPower();
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
