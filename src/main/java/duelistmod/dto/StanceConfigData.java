package duelistmod.dto;

import java.util.ArrayList;
import java.util.List;

public class StanceConfigData {

    private Boolean isDisabled;
    private Integer magic;
    private Integer strengthLoss;
    private Integer effect;
    private Integer misc;
    private String text;
    private String color;
    private List<String> data;
    private List<Integer> nums;
    private List<Boolean> flags;

    public StanceConfigData() {
        this(false);
    }

    public StanceConfigData(Boolean isDisabled) {
        this(isDisabled, 0);
    }

    public StanceConfigData(Boolean isDisabled, Integer magic) {
        this(isDisabled, magic, 0, 0, 0, "", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
    }

    public StanceConfigData(Boolean isDisabled, Integer magic, Integer strengthLoss) {
        this(isDisabled, magic, 0, 0, strengthLoss, "", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
    }

    public StanceConfigData(Boolean isDisabled, Integer magic, Integer effect, Integer misc, Integer strengthLoss, String text, String color, List<String> data, List<Integer> nums, List<Boolean> flags, Object other) {
        this.isDisabled = isDisabled;
        this.magic = magic;
        this.effect = effect;
        this.misc = misc;
        this.text = text;
        this.color = color;
        this.data = data;
        this.nums = nums;
        this.flags = flags;
        this.strengthLoss = strengthLoss;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    public Integer getMagic() {
        return magic;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
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

    public Integer getStrengthLoss() {
        return strengthLoss;
    }

    public void setStrengthLoss(Integer strengthLoss) {
        this.strengthLoss = strengthLoss;
    }
}
