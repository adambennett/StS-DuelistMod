package duelistmod.enums;

import duelistmod.DuelistMod;

public enum VinesLeavesMod {
    DO_NOTHING("---"),
    GAIN_THAT_MANY_VINES_INSTEAD("Gain that many Vines instead", true),
    GAIN_THAT_MANY_VINES_AS_WELL("Gain that many Vines as well", true),
    GAIN_HALF_THAT_MANY_VINES_INSTEAD("Gain half that many Vines instead", true),
    GAIN_HALF_THAT_MANY_VINES_AS_WELL("Gain half that many Vines as well", true),
    GAIN_TWICE_THAT_MANY_VINES_INSTEAD("Gain twice that many Vines instead", true),
    GAIN_TWICE_THAT_MANY_VINES_AS_WELL("Gain twice that many Vines as well", true),
    GAIN_THAT_MANY_LEAVES_INSTEAD("Gain that many Leaves instead", false),
    GAIN_THAT_MANY_LEAVES_AS_WELL("Gain that many Leaves as well", false),
    GAIN_HALF_THAT_MANY_LEAVES_INSTEAD("Gain half that many Leaves instead", false),
    GAIN_HALF_THAT_MANY_LEAVES_AS_WELL("Gain half that many Leaves as well", false),
    GAIN_TWICE_THAT_MANY_LEAVES_INSTEAD("Gain twice that many Leaves instead", false),
    GAIN_TWICE_THAT_MANY_LEAVES_AS_WELL("Gain twice that many Leaves as well", false),
    GAIN_TWICE_AS_MANY("Gain twice as many"),
    GAIN_HALF("Gain half as many"),
    GAIN_1_GOLD("Gain 1 Gold"),
    GAIN_5_GOLD("Gain 5 Gold"),
    GAIN_10_GOLD("Gain 10 Gold"),
    LOSE_ALL_TEMP_HP("Lose all Temp HP"),
    LOSE_1_HP("Lose 1 HP"),
    LOSE_5_HP("Lose 5 HP"),
    LOSE_1_BLOCK("Lose 1 Block"),
    LOSE_5_BLOCK("Lose 5 Block");

    private final String displayText;
    private final boolean forVines;
    private final boolean forLeaves;

    VinesLeavesMod(String displayText) {
        this(displayText, true, true);
    }

    VinesLeavesMod(String displayText, boolean forLeaves) {
        this(displayText, forLeaves, !forLeaves);
    }

    VinesLeavesMod(String displayText, boolean forLeaves, boolean forVines) {
        this.displayText = displayText;
        this.forLeaves = forLeaves;
        this.forVines = forVines;
    }

    public String displayText() { return this.displayText; }

    public boolean forLeaves() { return this.forLeaves; }

    public boolean forVines() { return this.forVines; }

    public static VinesLeavesMod leavesOption() {
        String mod = DuelistMod.getMonsterSetting(MonsterType.NATURIA, MonsterType.naturiaLeavesActionKey, MonsterType.naturiaDefaultLeavesAction);
        for (VinesLeavesMod vMod : VinesLeavesMod.values()) {
            if (vMod.displayText().equals(mod)) {
                return vMod;
            }
        }
        return DO_NOTHING;
    }

    public static VinesLeavesMod vinesOption() {
        String mod = DuelistMod.getMonsterSetting(MonsterType.NATURIA, MonsterType.naturiaVinesActionKey, MonsterType.naturiaDefaultVinesAction);
        for (VinesLeavesMod vMod : VinesLeavesMod.values()) {
            if (vMod.displayText().equals(mod)) {
                return vMod;
            }
        }
        return DO_NOTHING;
    }

}
