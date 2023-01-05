package duelistmod.dto;

import java.util.ArrayList;
import java.util.List;

public class OrbConfigData {

    private Integer defaultPassive;
    private Integer defaultEvoke;
    private Integer configPassive;
    private Integer configEvoke;
    private Integer bonus;
    private Integer penalty;
    private Integer effect;
    private Integer misc;
    private String text;
    private String color;
    private List<String> data;
    private List<Integer> nums;
    private List<Boolean> flags;
    private Object other;
    private Boolean passiveDisabled;
    private Boolean evokeDisabled;

    public OrbConfigData(int defaultPassive, int defaultEvoke) {
        this(defaultPassive, defaultEvoke, defaultPassive, defaultEvoke, false, false);
    }

    public OrbConfigData(int defaultPassive, int defaultEvoke, int configPassive, int configEvoke, boolean passiveDisabled, boolean evokeDisabled) {
        this(defaultPassive, defaultEvoke, configPassive, configEvoke, 0, 0, 0, 0, "", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, passiveDisabled, evokeDisabled);
    }

    public OrbConfigData(Integer defaultPassive, Integer defaultEvoke, Integer configPassive, Integer configEvoke, Integer bonus, Integer penalty, Integer effect, Integer misc, String text, String color, List<String> data, List<Integer> nums, List<Boolean> flags, Object other, Boolean passiveDisabled, Boolean evokeDisabled) {
        this.defaultPassive = defaultPassive;
        this.defaultEvoke = defaultEvoke;
        this.configPassive = configPassive;
        this.configEvoke = configEvoke;
        this.bonus = bonus;
        this.penalty = penalty;
        this.effect = effect;
        this.misc = misc;
        this.text = text;
        this.color = color;
        this.data = data;
        this.nums = nums;
        this.flags = flags;
        this.other = other;
        this.passiveDisabled = passiveDisabled;
        this.evokeDisabled = evokeDisabled;
    }

    public Integer getDefaultPassive() {
        return defaultPassive;
    }

    public void setDefaultPassive(Integer defaultPassive) {
        this.defaultPassive = defaultPassive;
    }

    public Integer getDefaultEvoke() {
        return defaultEvoke;
    }

    public void setDefaultEvoke(Integer defaultEvoke) {
        this.defaultEvoke = defaultEvoke;
    }

    public Integer getConfigPassive() {
        return configPassive;
    }

    public void setConfigPassive(Integer configPassive) {
        this.configPassive = configPassive;
    }

    public Integer getConfigEvoke() {
        return configEvoke;
    }

    public void setConfigEvoke(Integer configEvoke) {
        this.configEvoke = configEvoke;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    public Integer getEffect() {
        return effect;
    }

    public void setEffect(Integer effect) {
        this.effect = effect;
    }

    public Integer getMisc() {
        return misc;
    }

    public void setMisc(Integer misc) {
        this.misc = misc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<Integer> getNums() {
        return nums;
    }

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }

    public List<Boolean> getFlags() {
        return flags;
    }

    public void setFlags(List<Boolean> flags) {
        this.flags = flags;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public Boolean getPassiveDisabled() {
        return passiveDisabled;
    }

    public void setPassiveDisabled(Boolean passiveDisabled) {
        this.passiveDisabled = passiveDisabled;
    }

    public Boolean getEvokeDisabled() {
        return evokeDisabled;
    }

    public void setEvokeDisabled(Boolean evokeDisabled) {
        this.evokeDisabled = evokeDisabled;
    }
}
