package duelistmod.dto;

import duelistmod.DuelistMod;

public class RandomizedOptions {

    private boolean upgrade;
    private boolean etherealCheck = false;
    private boolean exhaustCheck = false;
    private boolean costChangeCheck = false;
    private boolean summonCheck = false;
    private boolean tributeCheck = false;
    private boolean summonChangeCombatCheck = false;
    private boolean tributeChangeCombatCheck = false;
    private boolean costChangeCombatCheck = false;
    private int lowCostRoll = 1;
    private int highCostRoll = 4;
    private int lowSummonRoll = 0;
    private int highSummonRoll = 0;
    private int lowTributeRoll = 0;
    private int highTributeRoll = 0;
    private boolean damageBlockRandomize = false;
    private boolean dontTrig = false;

    public RandomizedOptions(boolean upgrade, boolean etherealCheck, boolean exhaustCheck, boolean costChangeCheck, boolean summonCheck, boolean tributeCheck, boolean summonChangeCombatCheck, boolean tributeChangeCombatCheck, boolean costChangeCombatCheck, int lowCostRoll, int highCostRoll, int lowSummonRoll, int highSummonRoll, int lowTributeRoll, int highTributeRoll, boolean damageBlockRandomize, boolean dontTrig) {
        this.upgrade = upgrade;
        this.etherealCheck = etherealCheck;
        this.exhaustCheck = exhaustCheck;
        this.costChangeCheck = costChangeCheck;
        this.summonCheck = summonCheck;
        this.tributeCheck = tributeCheck;
        this.summonChangeCombatCheck = summonChangeCombatCheck;
        this.tributeChangeCombatCheck = tributeChangeCombatCheck;
        this.costChangeCombatCheck = costChangeCombatCheck;
        this.lowCostRoll = lowCostRoll;
        this.highCostRoll = highCostRoll;
        this.lowSummonRoll = lowSummonRoll;
        this.highSummonRoll = highSummonRoll;
        this.lowTributeRoll = lowTributeRoll;
        this.highTributeRoll = highTributeRoll;
        this.damageBlockRandomize = damageBlockRandomize;
        this.dontTrig = dontTrig;
        if (DuelistMod.noCostChanges) { this.costChangeCheck = false; }
        if (DuelistMod.noTributeChanges) { this.tributeCheck = false; }
        if (DuelistMod.noSummonChanges) { this.summonCheck = false; }
        if (DuelistMod.alwaysUpgrade) { this.upgrade = true; }
        if (DuelistMod.neverUpgrade) { this.upgrade = false; }
        if (!DuelistMod.randomizeEthereal) { this.etherealCheck = false; }
        if (!DuelistMod.randomizeExhaust) { this.exhaustCheck = false; }
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    public boolean isEtherealCheck() {
        return etherealCheck;
    }

    public void setEtherealCheck(boolean etherealCheck) {
        this.etherealCheck = etherealCheck;
    }

    public boolean isExhaustCheck() {
        return exhaustCheck;
    }

    public void setExhaustCheck(boolean exhaustCheck) {
        this.exhaustCheck = exhaustCheck;
    }

    public boolean isCostChangeCheck() {
        return costChangeCheck;
    }

    public void setCostChangeCheck(boolean costChangeCheck) {
        this.costChangeCheck = costChangeCheck;
    }

    public boolean isSummonCheck() {
        return summonCheck;
    }

    public void setSummonCheck(boolean summonCheck) {
        this.summonCheck = summonCheck;
    }

    public boolean isTributeCheck() {
        return tributeCheck;
    }

    public void setTributeCheck(boolean tributeCheck) {
        this.tributeCheck = tributeCheck;
    }

    public boolean isSummonChangeCombatCheck() {
        return summonChangeCombatCheck;
    }

    public void setSummonChangeCombatCheck(boolean summonChangeCombatCheck) {
        this.summonChangeCombatCheck = summonChangeCombatCheck;
    }

    public boolean isTributeChangeCombatCheck() {
        return tributeChangeCombatCheck;
    }

    public void setTributeChangeCombatCheck(boolean tributeChangeCombatCheck) {
        this.tributeChangeCombatCheck = tributeChangeCombatCheck;
    }

    public boolean isCostChangeCombatCheck() {
        return costChangeCombatCheck;
    }

    public void setCostChangeCombatCheck(boolean costChangeCombatCheck) {
        this.costChangeCombatCheck = costChangeCombatCheck;
    }

    public int getLowCostRoll() {
        return lowCostRoll;
    }

    public void setLowCostRoll(int lowCostRoll) {
        this.lowCostRoll = lowCostRoll;
    }

    public int getHighCostRoll() {
        return highCostRoll;
    }

    public void setHighCostRoll(int highCostRoll) {
        this.highCostRoll = highCostRoll;
    }

    public int getLowSummonRoll() {
        return lowSummonRoll;
    }

    public void setLowSummonRoll(int lowSummonRoll) {
        this.lowSummonRoll = lowSummonRoll;
    }

    public int getHighSummonRoll() {
        return highSummonRoll;
    }

    public void setHighSummonRoll(int highSummonRoll) {
        this.highSummonRoll = highSummonRoll;
    }

    public int getLowTributeRoll() {
        return lowTributeRoll;
    }

    public void setLowTributeRoll(int lowTributeRoll) {
        this.lowTributeRoll = lowTributeRoll;
    }

    public int getHighTributeRoll() {
        return highTributeRoll;
    }

    public void setHighTributeRoll(int highTributeRoll) {
        this.highTributeRoll = highTributeRoll;
    }

    public boolean isDamageBlockRandomize() {
        return damageBlockRandomize;
    }

    public void setDamageBlockRandomize(boolean damageBlockRandomize) {
        this.damageBlockRandomize = damageBlockRandomize;
    }

    public boolean isDontTrig() {
        return dontTrig;
    }

    public void setDontTrig(boolean dontTrig) {
        this.dontTrig = dontTrig;
    }
}
