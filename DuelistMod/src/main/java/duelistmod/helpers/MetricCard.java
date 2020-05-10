package duelistmod.helpers;

import java.util.*;

public class MetricCard {

    private String gameName;
    private String gameID;
    private String card_desc;

    private int dmg;
    private int blk;
    private int mag;
    private int secondMag;
    private int thirdMag;
    private int summons;
    private int tributes;
    private int entomb;

    public MetricCard() {}

    public MetricCard(String gameName, String gameID, String card_desc, int dmg, int blk, int mag, int secondMag, int thirdMag, int summons, int tributes, int entomb) {
        this.gameName = gameName;
        this.gameID = gameID;
        this.card_desc = card_desc;
        this.dmg = dmg;
        this.blk = blk;
        this.mag = mag;
        this.secondMag = secondMag;
        this.thirdMag = thirdMag;
        this.summons = summons;
        this.tributes = tributes;
        this.entomb = entomb;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getCard_desc() {
        return card_desc;
    }

    public void setCard_desc(String card_desc) {
        this.card_desc = card_desc;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getBlk() {
        return blk;
    }

    public void setBlk(int blk) {
        this.blk = blk;
    }

    public int getMag() {
        return mag;
    }

    public void setMag(int mag) {
        this.mag = mag;
    }

    public int getSecondMag() {
        return secondMag;
    }

    public void setSecondMag(int secondMag) {
        this.secondMag = secondMag;
    }

    public int getThirdMag() {
        return thirdMag;
    }

    public void setThirdMag(int thirdMag) {
        this.thirdMag = thirdMag;
    }

    public int getSummons() {
        return summons;
    }

    public void setSummons(int summons) {
        this.summons = summons;
    }

    public int getTributes() {
        return tributes;
    }

    public void setTributes(int tributes) {
        this.tributes = tributes;
    }

    public int getEntomb() {
        return entomb;
    }

    public void setEntomb(int entomb) {
        this.entomb = entomb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetricCard)) return false;
        MetricCard that = (MetricCard) o;
        return Objects.equals(getGameID(), that.getGameID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameID());
    }
}
