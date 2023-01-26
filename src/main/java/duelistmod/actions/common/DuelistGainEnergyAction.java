package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistMonster;

import java.util.ArrayList;

public class DuelistGainEnergyAction extends AbstractGameAction {

    private final int energyGain;
    private final AbstractCreature duelist;
    private final DuelistMonster duelistMonster;

    public DuelistGainEnergyAction(int amount, DuelistMonster duelist) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.energyGain = amount;
        this.duelist = duelist != null ? duelist : AbstractDungeon.player;
        this.duelistMonster = duelist;
        this.setValues(duelist, duelist, 0);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (duelist instanceof AbstractPlayer) {
                AbstractDungeon.player.gainEnergy(this.energyGain);
                AbstractDungeon.actionManager.updateEnergyGain(this.energyGain);
            } else if (duelistMonster != null) {
                this.duelistMonster.gainEnergy(this.energyGain);
            }

            ArrayList<AbstractCard> group = this.duelist instanceof AbstractPlayer ? ((AbstractPlayer)this.duelist).hand.group : this.duelistMonster != null ? this.duelistMonster.getCardsForNextMove() : new ArrayList<>();
            for (AbstractCard c : group) {
                c.triggerOnGainEnergy(this.energyGain, true);
            }
        }

        this.tickDuration();
    }
}
