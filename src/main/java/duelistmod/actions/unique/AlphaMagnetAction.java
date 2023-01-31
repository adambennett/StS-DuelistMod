package duelistmod.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import duelistmod.abstracts.DuelistMonster;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.actions.common.DuelistGainEnergyAction;

import java.util.ArrayList;

public class AlphaMagnetAction extends AbstractGameAction {

    private final AbstractCreature owner;

    public AlphaMagnetAction(AbstractCreature owner) {
        this.owner = owner;
    }

    public void update() {
        ArrayList<AbstractCard> cardsPlayedThisCombat = this.owner instanceof AbstractEnemyDuelist ? ((AbstractEnemyDuelist)this.owner).cardsPlayedThisCombat : AbstractDungeon.actionManager.cardsPlayedThisCombat;
        if (cardsPlayedThisCombat.size() >= 2 && cardsPlayedThisCombat.get(cardsPlayedThisCombat.size() - 2).type == AbstractCard.CardType.ATTACK) {
            this.addToTop(new DuelistGainEnergyAction(1, this.owner instanceof DuelistMonster ? (DuelistMonster)this.owner : null));
            if (!(this.owner instanceof DuelistMonster)) {
                if (Settings.FAST_MODE) {
                    this.addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.0F));
                } else {
                    this.addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.3F));
                }
            }
        }

        this.isDone = true;
    }
}
