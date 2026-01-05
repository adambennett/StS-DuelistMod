package duelistmod.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyGuardedCard;
import duelistmod.dto.GuardedState;
import duelistmod.interfaces.GuardedCard;
import duelistmod.interfaces.HasGuardedState;

import java.util.List;

public abstract class GuardedMagnetCard extends MagnetCard implements GuardedCard, HasGuardedState {

    private final GuardedState guarded = new GuardedState(0);

    public GuardedMagnetCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public GuardedState guarded() {
        return guarded;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        guarded.endOfTurnReset();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();
        if (copy instanceof HasGuardedState) {
            GuardedState dst = ((HasGuardedState) copy).guarded();
            dst.setBase(guarded.getBase());
            dst.setCurrent(guarded.getCurrent());
            dst.setForTurn(guarded.getForTurn());
            dst.setUpgraded(guarded.isUpgraded());
            dst.setModified(guarded.isModified());
            dst.setModifiedForTurn(guarded.isModifiedForTurn());
        }
        return copy;
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (isGuardedActive(this, getGuardedCheck())) {
            this.glowColor = Color.GOLD;
        }
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.guarded.resetForTurn();
    }

    public abstract void onGuardedTriggered(AnyDuelist duelist, List<AbstractCreature> targets);

    protected final void triggerGuarded(AnyDuelist duelist, List<AbstractCreature> targets) {
        triggerGuarded(AnyGuardedCard.from(this), duelist, targets);
    }

    @Override
    public final void triggerGuarded(AnyGuardedCard caller, AnyDuelist duelist, List<AbstractCreature> targets) {
        onGuardedTriggered(duelist, targets);
        GuardedCard.super.triggerGuarded(caller, duelist, targets);
    }

    public void setBaseGuardedCheck(int v) {
        guarded.setBase(v);
        guarded.setCurrent(v);
        guarded.setForTurn(v);
        guarded.setModified(false);
        guarded.setModifiedForTurn(false);
    }

    public int getBaseGuardedCheck() {
        return guarded.getBase();
    }

    public int getGuardedCheck() {
        return guarded.getCheck();
    }

    public int getGuardedCheckForTurn() {
        return guarded.getForTurn();
    }

    public boolean isGuardedCheckModified() {
        return guarded.isModified();
    }

    public boolean isGuardedCheckModifiedForTurn() {
        return guarded.isModifiedForTurn();
    }

    public boolean getUpgradedGuardedCheck() {
        return guarded.isUpgraded();
    }

    public void upgradeGuardedCheck(int add) {
        guarded.upgradeCheck(add);
    }

    public void setGuardedCheck(int guardedCheck) {
        guarded.setCurrent(guardedCheck);
    }

    public void setGuardedCheckModified(boolean guardedCheckModified) {
        guarded.setModified(guardedCheckModified);
    }

    public void setUpgradedGuardedCheck(boolean upgradedGuardedCheck) {
        guarded.setUpgraded(upgradedGuardedCheck);
    }

    public void setGuardedCheckModifiedForTurn(boolean guardedCheckModifiedForTurn) {
        guarded.setModifiedForTurn(guardedCheckModifiedForTurn);
    }

    public void setGuardedCheckForTurn(int guardedCheckForTurn) {
        guarded.setForTurn(guardedCheckForTurn);
    }

}
