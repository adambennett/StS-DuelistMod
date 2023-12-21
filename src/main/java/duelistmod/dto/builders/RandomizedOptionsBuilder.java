package duelistmod.dto.builders;

import duelistmod.dto.RandomizedOptions;

public class RandomizedOptionsBuilder {
    private boolean upgrade;
    private boolean etherealCheck;
    private boolean exhaustCheck;
    private boolean costChangeCheck;
    private boolean summonCheck;
    private boolean tributeCheck;
    private boolean summonChangeCombatCheck;
    private boolean tributeChangeCombatCheck;
    private boolean costChangeCombatCheck;
    private int lowCostRoll;
    private int highCostRoll;
    private int lowSummonRoll;
    private int highSummonRoll;
    private int lowTributeRoll;
    private int highTributeRoll;
    private boolean damageBlockRandomize;
    private boolean dontTrig;

    public RandomizedOptionsBuilder setDontTrig(boolean dontTrig) {
        this.dontTrig = dontTrig;
        return this;
    }

    public RandomizedOptionsBuilder setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
        return this;
    }

    public RandomizedOptionsBuilder setEtherealCheck(boolean etherealCheck) {
        this.etherealCheck = etherealCheck;
        return this;
    }

    public RandomizedOptionsBuilder setExhaustCheck(boolean exhaustCheck) {
        this.exhaustCheck = exhaustCheck;
        return this;
    }

    public RandomizedOptionsBuilder setCostChangeCheck(boolean costChangeCheck) {
        this.costChangeCheck = costChangeCheck;
        return this;
    }

    public RandomizedOptionsBuilder setSummonCheck(boolean summonCheck) {
        this.summonCheck = summonCheck;
        return this;
    }

    public RandomizedOptionsBuilder setTributeCheck(boolean tributeCheck) {
        this.tributeCheck = tributeCheck;
        return this;
    }

    public RandomizedOptionsBuilder setSummonChangeCombatCheck(boolean summonChangeCombatCheck) {
        this.summonChangeCombatCheck = summonChangeCombatCheck;
        return this;
    }

    public RandomizedOptionsBuilder setTributeChangeCombatCheck(boolean tributeChangeCombatCheck) {
        this.tributeChangeCombatCheck = tributeChangeCombatCheck;
        return this;
    }

    public RandomizedOptionsBuilder setCostChangeCombatCheck(boolean costChangeCombatCheck) {
        this.costChangeCombatCheck = costChangeCombatCheck;
        return this;
    }

    public RandomizedOptionsBuilder setLowCostRoll(int lowCostRoll) {
        this.lowCostRoll = lowCostRoll;
        return this;
    }

    public RandomizedOptionsBuilder setHighCostRoll(int highCostRoll) {
        this.highCostRoll = highCostRoll;
        return this;
    }

    public RandomizedOptionsBuilder setLowSummonRoll(int lowSummonRoll) {
        this.lowSummonRoll = lowSummonRoll;
        return this;
    }

    public RandomizedOptionsBuilder setHighSummonRoll(int highSummonRoll) {
        this.highSummonRoll = highSummonRoll;
        return this;
    }

    public RandomizedOptionsBuilder setLowTributeRoll(int lowTributeRoll) {
        this.lowTributeRoll = lowTributeRoll;
        return this;
    }

    public RandomizedOptionsBuilder setHighTributeRoll(int highTributeRoll) {
        this.highTributeRoll = highTributeRoll;
        return this;
    }

    public RandomizedOptionsBuilder setDamageBlockRandomize(boolean damageBlockRandomize) {
        this.damageBlockRandomize = damageBlockRandomize;
        return this;
    }

    public RandomizedOptions createRandomizedOptions() {
        return new RandomizedOptions(upgrade, etherealCheck, exhaustCheck, costChangeCheck, summonCheck, tributeCheck, summonChangeCombatCheck, tributeChangeCombatCheck, costChangeCombatCheck, lowCostRoll, highCostRoll, lowSummonRoll, highSummonRoll, lowTributeRoll, highTributeRoll, damageBlockRandomize, dontTrig);
    }
}
