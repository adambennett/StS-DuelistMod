package duelistmod.metrics.builders;

import duelistmod.metrics.*;

public class MetricCardBuilder {
    private String gameName;
    private String gameID;
    private String desc;
    private int dmg;
    private int blk;
    private int mag;
    private int secondMag = 0;
    private int thirdMag = 0;
    private int summons = 0;
    private int tributes = 0;
    private int entomb = 0;

    public MetricCardBuilder setGameName(String gameName) {
        this.gameName = gameName;
        return this;
    }

    public MetricCardBuilder setGameID(String gameID) {
        this.gameID = gameID;
        return this;
    }

    public MetricCardBuilder setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public MetricCardBuilder setDmg(int dmg) {
        this.dmg = dmg;
        return this;
    }

    public MetricCardBuilder setBlk(int blk) {
        this.blk = blk;
        return this;
    }

    public MetricCardBuilder setMag(int mag) {
        this.mag = mag;
        return this;
    }

    public MetricCardBuilder setSecondMag(int secondMag) {
        this.secondMag = secondMag;
        return this;
    }

    public MetricCardBuilder setThirdMag(int thirdMag) {
        this.thirdMag = thirdMag;
        return this;
    }

    public MetricCardBuilder setSummons(int summons) {
        this.summons = summons;
        return this;
    }

    public MetricCardBuilder setTributes(int tributes) {
        this.tributes = tributes;
        return this;
    }

    public MetricCardBuilder setEntomb(int entomb) {
        this.entomb = entomb;
        return this;
    }

    public MetricCard createMetricCard() {
        return new MetricCard(gameName, gameID, desc, dmg, blk, mag, secondMag, thirdMag, summons, tributes, entomb);
    }
}
