package duelistmod.dto;

public class LavaOrbEruptionResult {

    private final boolean erupted;
    private final int extraDamage;

    public LavaOrbEruptionResult() {
        this(true, 0);
    }

    public LavaOrbEruptionResult(boolean erupted, int extraDamage) {
        this.erupted = erupted;
        this.extraDamage = extraDamage;
    }

    public boolean erupted() {
        return erupted;
    }

    public int extraDamage() {
        return extraDamage;
    }
}
