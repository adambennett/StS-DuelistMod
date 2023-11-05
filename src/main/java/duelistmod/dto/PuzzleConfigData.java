package duelistmod.dto;


import com.google.gson.Gson;
import duelistmod.cards.other.tokens.PuzzleToken;
import duelistmod.dto.builders.StartingDeckStatsBuilder;

import java.util.HashMap;

public class PuzzleConfigData {

    private String deck = "Standard Deck";
    private HashMap<String, Object> properties = new HashMap<>();
    /*private Integer effectsChoices = 1;
    private Boolean effectsDisabled = false;
    private Integer effectsToRemove = 2;
    private Boolean gainBlur = true;
    private Integer randomBlockLow = 0;
    private Integer randomBlockHigh = 10;
    private Integer startingVines = 1;
    private Integer startingLeaves = 1;
    private Integer tokensToSummon = 1;
    private String tokenType = "theDuelist:PuzzleToken";
    private Boolean applyToonWorld = true;
    private Boolean channelShadow = true;
    private Boolean overflowDrawPile = true;
    private Integer drawPileCardsToOverflow = 1;
    private Boolean damageBoost = true;
    private Boolean randomTokenToHand = true;
    private Integer randomTokenAmount = 1;
    private Integer vigorToGain = 3;
    private Boolean gainVigor = true;
    private Integer blurToGain = 1;
    private Boolean addBixi = true;
    private Boolean applyConstricted = true;
    private Integer constrictedAmount = 2;
    private Boolean gainThorns = true;
    private Integer thornsAmount = 2;
    private Boolean addMonsterToHand = true;
    private Integer randomMonstersToAdd = 1;
    private Boolean increment = true;
    private Integer amountToIncrement = 0;
    private Boolean amountToIncrementMatchesAct = true;
    private Boolean gainRandomBuff = true;
    private Boolean applySoulbound = true;
    private Boolean cannotObtainCards = true;
    private Boolean drawExodiaHead = true;
    private Boolean pharaohEffectDisabled = false;
    private Boolean gainRandomBlock = true;
    private Integer randomSummonTokensLowEnd = 1;
    private Integer randomSummonTokensHighEnd = 3;*/

    private StartingDeckStats stats = new StartingDeckStatsBuilder().createStartingDeckStats();

    public PuzzleConfigData() {}

    /*public PuzzleConfigData(String deck, Integer effectsChoices, Boolean effectsDisabled, Integer effectsToRemove, Boolean gainBlur, Integer randomBlockLow, Integer randomBlockHigh, Integer startingVines, Integer startingLeaves, Integer tokensToSummon, String tokenType, Boolean applyToonWorld, Boolean channelShadow, Boolean overflowDrawPile, Integer drawPileCardsToOverflow, Boolean damageBoost, Boolean randomTokenToHand, Integer randomTokenAmount, Integer vigorToGain, Boolean gainVigor, Integer blurToGain, Boolean addBixi, Boolean applyConstricted, Integer constrictedAmount, Boolean gainThorns, Integer thornsAmount, Boolean addMonsterToHand, Integer randomMonstersToAdd, Boolean increment, Integer amountToIncrement, Boolean amountToIncrementMatchesAct, Boolean gainRandomBuff, Boolean applySoulbound, Boolean cannotObtainCards, Boolean drawExodiaHead, Boolean pharaohEffectDisabled, Boolean gainRandomBlock, Integer randomSummonTokensLowEnd, Integer randomSummonTokensHighEnd, StartingDeckStats stats) {
        this.deck = deck;
        this.effectsChoices = effectsChoices;
        this.effectsDisabled = effectsDisabled;
        this.effectsToRemove = effectsToRemove;
        this.gainBlur = gainBlur;
        this.randomBlockLow = randomBlockLow;
        this.randomBlockHigh = randomBlockHigh;
        this.startingVines = startingVines;
        this.startingLeaves = startingLeaves;
        this.tokensToSummon = tokensToSummon;
        this.tokenType = tokenType;
        this.applyToonWorld = applyToonWorld;
        this.channelShadow = channelShadow;
        this.overflowDrawPile = overflowDrawPile;
        this.drawPileCardsToOverflow = drawPileCardsToOverflow;
        this.damageBoost = damageBoost;
        this.randomTokenToHand = randomTokenToHand;
        this.randomTokenAmount = randomTokenAmount;
        this.vigorToGain = vigorToGain;
        this.gainVigor = gainVigor;
        this.blurToGain = blurToGain;
        this.addBixi = addBixi;
        this.applyConstricted = applyConstricted;
        this.constrictedAmount = constrictedAmount;
        this.gainThorns = gainThorns;
        this.thornsAmount = thornsAmount;
        this.addMonsterToHand = addMonsterToHand;
        this.randomMonstersToAdd = randomMonstersToAdd;
        this.increment = increment;
        this.amountToIncrement = amountToIncrement;
        this.amountToIncrementMatchesAct = amountToIncrementMatchesAct;
        this.gainRandomBuff = gainRandomBuff;
        this.applySoulbound = applySoulbound;
        this.cannotObtainCards = cannotObtainCards;
        this.drawExodiaHead = drawExodiaHead;
        this.pharaohEffectDisabled = pharaohEffectDisabled;
        this.gainRandomBlock = gainRandomBlock;
        this.randomSummonTokensLowEnd = randomSummonTokensLowEnd;
        this.randomSummonTokensHighEnd = randomSummonTokensHighEnd;
        this.stats = stats;
    }*/

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public Integer getEffectsChoices() {
        return (int)this.properties.getOrDefault("effectsChoices", 0);
    }

    public void setEffectsChoices(Integer effectsChoices) {
        this.put("effectsChoices", effectsChoices);
    }

    public Boolean getEffectsDisabled() {
        return (boolean)this.properties.getOrDefault("effectsDisabled", false);
    }

    public void setEffectsDisabled(Boolean effectsDisabled) {
        this.put("effectsDisabled", effectsDisabled);
    }

    public Integer getEffectsToRemove() {
        return (int)this.properties.getOrDefault("effectsToRemove", 0);
    }

    public void setEffectsToRemove(Integer effectsToRemove) {
        this.put("effectsToRemove", effectsToRemove);
    }

    public Boolean getGainBlur() {
        return (boolean)this.properties.getOrDefault("gainBlur", false);
    }

    public void setGainBlur(Boolean gainBlur) {
        this.put("gainBlur", gainBlur);
    }

    public Integer getRandomBlockLow() {
        return (int)this.properties.getOrDefault("randomBlockLow", 0);
    }

    public void setRandomBlockLow(Integer randomBlockLow) {
        this.put("randomBlockLow", randomBlockLow);
    }

    public Integer getRandomBlockHigh() {
        return (int)this.properties.getOrDefault("randomBlockHigh", 0);
    }

    public void setRandomBlockHigh(Integer randomBlockHigh) {
        this.put("randomBlockHigh", randomBlockHigh);
    }

    public Integer getStartingVines() {
        return (int)this.properties.getOrDefault("startingVines", 0);
    }

    public void setStartingVines(Integer startingVines) {
        this.put("startingVines", startingVines);
    }

    public Integer getStartingLeaves() {
        return (int)this.properties.getOrDefault("startingLeaves", 0);
    }

    public void setStartingLeaves(Integer startingLeaves) {
        this.put("startingLeaves", startingLeaves);
    }

    public Integer getTokensToSummon() {
        return (int)this.properties.getOrDefault("tokensToSummon", 0);
    }

    public void setTokensToSummon(Integer tokensToSummon) {
        this.put("tokensToSummon", tokensToSummon);
    }

    public String getTokenType() {
        return (String)this.properties.getOrDefault("tokenType", new PuzzleToken().cardID);
    }

    public void setTokenType(String tokenType) {
        this.put("tokenType", tokenType);
    }

    public Boolean getApplyToonWorld() {
        return (boolean)this.properties.getOrDefault("applyToonWorld", false);
    }

    public void setApplyToonWorld(Boolean applyToonWorld) {
        this.put("applyToonWorld", applyToonWorld);
    }

    public Boolean getChannelShadow() {
        return (boolean)this.properties.getOrDefault("channelShadow", false);
    }

    public void setChannelShadow(Boolean channelShadow) {
        this.put("channelShadow", channelShadow);
    }

    public Boolean getOverflowDrawPile() {
        return (boolean)this.properties.getOrDefault("overflowDrawPile", false);
    }

    public void setOverflowDrawPile(Boolean overflowDrawPile) {
        this.put("overflowDrawPile", overflowDrawPile);
    }

    public Integer getDrawPileCardsToOverflow() {
        return (int)this.properties.getOrDefault("drawPileCardsToOverflow", 0);
    }

    public void setDrawPileCardsToOverflow(Integer drawPileCardsToOverflow) {
        this.put("drawPileCardsToOverflow", drawPileCardsToOverflow);
    }

    public Boolean getDamageBoost() {
        return (boolean)this.properties.getOrDefault("damageBoost", false);
    }

    public void setDamageBoost(Boolean damageBoost) {
        this.put("damageBoost", damageBoost);
    }

    public Boolean getRandomTokenToHand() {
        return (boolean)this.properties.getOrDefault("randomTokenToHand", false);
    }

    public void setRandomTokenToHand(Boolean randomTokenToHand) {
        this.put("randomTokenToHand", randomTokenToHand);
    }

    public Integer getVigorToGain() {
        return (int)this.properties.getOrDefault("vigorToGain", 0);
    }

    public void setVigorToGain(Integer vigorToGain) {
        this.put("vigorToGain", vigorToGain);
    }

    public Boolean getGainVigor() {
        return (boolean)this.properties.getOrDefault("gainVigor", false);
    }

    public void setGainVigor(Boolean gainVigor) {
        this.put("gainVigor", gainVigor);
    }

    public Integer getBlurToGain() {
        return (int)this.properties.getOrDefault("blurToGain", 0);
    }

    public void setBlurToGain(Integer blurToGain) {
        this.put("blurToGain", blurToGain);
    }

    public Boolean getAddBixi() {
        return (boolean)this.properties.getOrDefault("addBixi", false);
    }

    public void setAddBixi(Boolean addBixi) {
        this.put("addBixi", addBixi);
    }

    public Boolean getApplyConstricted() {
        return (boolean)this.properties.getOrDefault("applyConstricted", false);
    }

    public void setApplyConstricted(Boolean applyConstricted) {
        this.put("applyConstricted", applyConstricted);
    }

    public Boolean getGainThorns() {
        return (boolean)this.properties.getOrDefault("gainThorns", false);
    }

    public void setGainThorns(Boolean gainThorns) {
        this.put("gainThorns", gainThorns);
    }

    public Boolean getAddMonsterToHand() {
        return (boolean)this.properties.getOrDefault("addMonsterToHand", false);
    }

    public void setAddMonsterToHand(Boolean addMonsterToHand) {
        this.put("addMonsterToHand", addMonsterToHand);
    }

    public Boolean getIncrement() {
        return (boolean)this.properties.getOrDefault("increment", false);
    }

    public void setIncrement(Boolean increment) {
        this.put("increment", increment);
    }

    public Integer getAmountToIncrement() {
        return (int)this.properties.getOrDefault("amountToIncrement", 0);
    }

    public void setAmountToIncrement(Integer amountToIncrement) {
        this.put("amountToIncrement", amountToIncrement);
    }

    public Boolean getAmountToIncrementMatchesAct() {
        return (boolean)this.properties.getOrDefault("amountToIncrementMatchesAct", false);
    }

    public void setAmountToIncrementMatchesAct(Boolean amountToIncrementMatchesAct) {
        this.put("amountToIncrementMatchesAct", amountToIncrementMatchesAct);
    }

    public Boolean getGainRandomBuff() {
        return (boolean)this.properties.getOrDefault("gainRandomBuff", false);
    }

    public void setGainRandomBuff(Boolean gainRandomBuff) {
        this.put("gainRandomBuff", gainRandomBuff);
    }

    public Boolean getApplySoulbound() {
        return (boolean)this.properties.getOrDefault("applySoulbound", false);
    }

    public void setApplySoulbound(Boolean applySoulbound) {
        this.put("applySoulbound", applySoulbound);
    }

    public Boolean getDrawExodiaHead() {
        return (boolean)this.properties.getOrDefault("drawExodiaHead", false);
    }

    public void setDrawExodiaHead(Boolean drawExodiaHead) {
        this.put("drawExodiaHead", drawExodiaHead);
    }

    public Boolean getPharaohEffectDisabled() {
        return (boolean)this.properties.getOrDefault("pharaohEffectDisabled", false);
    }

    public void setPharaohEffectDisabled(Boolean pharaohEffectDisabled) {
        this.put("pharaohEffectDisabled", pharaohEffectDisabled);
    }

    public Boolean getGainRandomBlock() {
        return (boolean)this.properties.getOrDefault("gainRandomBlock", false);
    }

    public void setGainRandomBlock(Boolean gainRandomBlock) {
        this.put("gainRandomBlock", gainRandomBlock);
    }

    public Integer getRandomTokenAmount() {
        return (int)this.properties.getOrDefault("randomTokenAmount", 0);
    }

    public void setRandomTokenAmount(Integer randomTokenAmount) {
        this.put("randomTokenAmount", randomTokenAmount);
    }

    public Integer getConstrictedAmount() {
        return (int)this.properties.getOrDefault("constrictedAmount", 0);
    }

    public void setConstrictedAmount(Integer constrictedAmount) {
        this.put("constrictedAmount", constrictedAmount);
    }

    public Integer getThornsAmount() {
        return (int)this.properties.getOrDefault("thornsAmount", 0);
    }

    public void setThornsAmount(Integer thornsAmount) {
        this.put("thornsAmount", thornsAmount);
    }

    public Integer getRandomMonstersToAdd() {
        return (int)this.properties.getOrDefault("randomMonstersToAdd", 0);
    }

    public void setRandomMonstersToAdd(Integer randomMonstersToAdd) {
        this.put("randomMonstersToAdd", randomMonstersToAdd);
    }

    public Integer getRandomSummonTokensLowEnd() {
        return (int)this.properties.getOrDefault("randomSummonTokensLowEnd", 0);
    }

    public void setRandomSummonTokensLowEnd(Integer randomSummonTokensLowEnd) {
        this.put("randomSummonTokensLowEnd", randomSummonTokensLowEnd);
    }

    public Integer getRandomSummonTokensHighEnd() {
        return (int)this.properties.getOrDefault("randomSummonTokensHighEnd", 0);
    }

    public void setRandomSummonTokensHighEnd(Integer randomSummonTokensHighEnd) {
        this.put("randomSummonTokensHighEnd", randomSummonTokensHighEnd);
    }

    public Boolean getCannotObtainCards() {
        return (boolean)this.properties.getOrDefault("cannotObtainCards", false);
    }

    public void setCannotObtainCards(Boolean cannotObtainCards) {
        this.put("cannotObtainCards", cannotObtainCards);
    }

    public StartingDeckStats getStats() {
        return stats;
    }

    public void setStats(StartingDeckStats stats) {
        this.stats = stats;
    }

    public boolean getFangTriggerEffect() {
        return (boolean)this.properties.getOrDefault("triggerFangEffect", true);
    }

    public void setFangTriggerEffect(boolean trigger) {
        this.put("triggerFangEffect", trigger);
    }

    public int getAmountOfBeastsToTrigger() {
        return (int)this.properties.getOrDefault("amountOfBeastsToTrigger", 10);
    }

    public void setAmountOfBeastsToTrigger(int amt) {
        this.put("amountOfBeastsToTrigger", amt);
    }

    public int getFangsToGain() {
        return (int)this.properties.getOrDefault("fangsToGain", 3);
    }

    public void setFangsToGain(int fangs) {
        this.put("fangsToGain", fangs);
    }

    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    public void put(String key, Object value) {
        if (this.properties != null) {
            this.properties.put(key, value);
        }
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
