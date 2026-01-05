package duelistmod.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.dto.AnyDuelist;

import java.util.List;

public abstract class NimbleDuelistCard extends DuelistCard {

    public NimbleDuelistCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void postDuelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        super.postDuelistUseCard(owner, targets);
        if (isNimbleActive(this)) {
            triggerNimble(AnyDuelist.from(this), targets);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (isNimbleActive(this)) {
            this.glowColor = Color.GOLD;
        }
    }

    public abstract void onNimbleTriggered(AnyDuelist duelist, List<AbstractCreature> targets);

    private boolean isNimbleActive(DuelistCard card) {
        AnyDuelist duelist = AnyDuelist.from(card);
        return duelist.isNimble();
    }

    private void triggerNimble(AnyDuelist duelist, List<AbstractCreature> targets) {
        onNimbleTriggered(duelist, targets);
        if (duelist.powers() != null) {
            for (AbstractPower power : duelist.powers()) {
                if (power instanceof DuelistPower) {
                    DuelistPower dp =  (DuelistPower) power;
                    dp.onNimbleTriggered(this, targets);
                }
            }
            for (AbstractRelic relic : duelist.relics()) {
                if (relic instanceof DuelistRelic) {
                    DuelistRelic  duelistRelic = (DuelistRelic)relic;
                    duelistRelic.onNimbleTriggered(this, targets);
                }
            }
        }
    }

}
