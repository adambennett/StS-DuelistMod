package duelistmod.dto;

public final class GuardedState {
    private int base;
    private int current;
    private int forTurn;
    private boolean upgraded;
    private boolean modified;
    private boolean modifiedForTurn;

    public GuardedState(int initial) {
        this.base = initial;
        this.current = initial;
        this.forTurn = initial;
    }

    public int getBase() {
        return base;
    }

    public int getCurrent() {
        return current;
    }

    public int getForTurn() {
        return forTurn;
    }

    public void setBase(int v) {
        base = v;
    }

    public void setCurrent(int v) {
        current = v;
    }

    public void setForTurn(int v) {
        forTurn = v;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean v) {
        upgraded = v;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean v) {
        modified = v;
    }

    public boolean isModifiedForTurn() {
        return modifiedForTurn;
    }

    public void setModifiedForTurn(boolean v) {
        modifiedForTurn = v;
    }

    public int getCheck() {
        return modifiedForTurn ? forTurn : current;
    }

    public void resetForTurn() {
        this.forTurn = this.getCurrent();
        this.modifiedForTurn = false;
    }

    public void endOfTurnReset() {
        this.modifiedForTurn = false;
    }

    public void upgradeCheck(int add) {
        int diff = this.forTurn - this.getCheck();
        int newVal = this.getCheck() + add;
        if (newVal < 0) newVal = 0;

        this.current = newVal;
        this.base = newVal;

        if (this.forTurn > 0) {
            this.forTurn = newVal + diff;
        }
        if (this.forTurn < 0) this.forTurn = 0;

        this.upgraded = true;
    }
}

