package duelistmod.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.dto.AnyDuelist;

import java.util.ArrayList;

public class MagnetEnergyGainAction extends AbstractGameAction {

    private final AbstractCreature owner;
    private final CardType type;

    public MagnetEnergyGainAction(AbstractCreature owner, CardType type) {
        this.owner = owner;
        this.type = type;
    }

    public void update() {
        ArrayList<AbstractCard> cardsPlayedThisCombat = this.owner instanceof AbstractEnemyDuelist ? ((AbstractEnemyDuelist)this.owner).cardsPlayedThisCombat : AbstractDungeon.actionManager.cardsPlayedThisCombat;
        if (cardsPlayedThisCombat.size() >= 2 && cardsPlayedThisCombat.get(cardsPlayedThisCombat.size() - 2).type == this.type) {
            AnyDuelist duelist = AnyDuelist.from(this.owner);
            duelist.gainEnergy(1);
            if (duelist.player()) {
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
