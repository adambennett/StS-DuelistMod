package duelistmod.enums;

public enum DataCategoryType {
    Config_Setting(true);

    private final boolean trackingForMetrics;

    DataCategoryType(boolean trackingForMetrics) {
        this.trackingForMetrics = trackingForMetrics;
    }
    public boolean trackingForMetrics() { return this.trackingForMetrics; }

    @Override
    public String toString() {
        return "{ type: " + this.name() +
                ", trackingForMetrics: " + trackingForMetrics +
                " }";
    }
}
