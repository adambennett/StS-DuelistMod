package duelistmod.dto.builders;

import duelistmod.dto.PuzzleConfigData;
import duelistmod.dto.StartingDeckStats;

public class PuzzleConfigDataBuilder {

    private Integer effectsChoices;
    private Boolean effectsDisabled;
    private Integer effectsToRemove;
    private Boolean gainBlur;
    private Integer randomBlockLow;
    private Integer randomBlockHigh;
    private Integer startingVines;
    private Integer startingLeaves;
    private Integer tokensToSummon;
    private String tokenType;
    private Boolean applyToonWorld;
    private Boolean channelShadow;
    private Boolean overflowDrawPile;
    private Integer drawPileCardsToOverflow;
    private Boolean damageBoost;
    private Boolean randomTokenToHand;
    private Integer randomTokenAmount;
    private Integer vigorToGain;
    private Boolean gainVigor;
    private Integer blurToGain;
    private Boolean addBixi;
    private Boolean applyConstricted;
    private Integer constrictedAmount;
    private Boolean gainThorns;
    private Integer thornsAmount;
    private Boolean addMonsterToHand;
    private Integer randomMonstersToAdd;
    private Boolean increment;
    private Integer amountToIncrement;
    private Boolean amountToIncrementMatchesAct;
    private Boolean gainRandomBuff;
    private Boolean applySoulbound;
    private Boolean drawExodiaHead;
    private Boolean pharaohEffectDisabled;
    private Boolean gainRandomBlock;
    private Integer randomSummonTokensLowEnd;
    private Integer randomSummonTokensHighEnd;
    private Boolean cannotObtainCards;
    private final PuzzleConfigData output;

    public PuzzleConfigDataBuilder() {
        this.output = new PuzzleConfigData();
    }

    public PuzzleConfigDataBuilder setDeck(String deck) {
        this.output.setDeck(deck);
        return this;
    }

    public PuzzleConfigDataBuilder setEffectsChoices(Integer effectsChoices) {
        this.effectsChoices = effectsChoices;
        return this;
    }

    public PuzzleConfigDataBuilder setEffectsDisabled(Boolean effectsDisabled) {
        this.effectsDisabled = effectsDisabled;
        return this;
    }

    public PuzzleConfigDataBuilder setEffectsToRemove(Integer effectsToRemove) {
        this.effectsToRemove = effectsToRemove;
        return this;
    }

    public PuzzleConfigDataBuilder setGainBlur(Boolean gainBlur) {
        this.gainBlur = gainBlur;
        return this;
    }

    public PuzzleConfigDataBuilder setRandomBlockLow(Integer randomBlockLow) {
        this.randomBlockLow = randomBlockLow;
        return this;
    }

    public PuzzleConfigDataBuilder setRandomBlockHigh(Integer randomBlockHigh) {
        this.randomBlockHigh = randomBlockHigh;
        return this;
    }

    public PuzzleConfigDataBuilder setStartingVines(Integer startingVines) {
        this.startingVines = startingVines;
        return this;
    }

    public PuzzleConfigDataBuilder setStartingLeaves(Integer startingLeaves) {
        this.startingLeaves = startingLeaves;
        return this;
    }

    public PuzzleConfigDataBuilder setTokensToSummon(Integer tokensToSummon) {
        this.tokensToSummon = tokensToSummon;
        return this;
    }

    public PuzzleConfigDataBuilder setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public PuzzleConfigDataBuilder setApplyToonWorld(Boolean applyToonWorld) {
        this.applyToonWorld = applyToonWorld;
        return this;
    }

    public PuzzleConfigDataBuilder setChannelShadow(Boolean channelShadow) {
        this.channelShadow = channelShadow;
        return this;
    }

    public PuzzleConfigDataBuilder setOverflowDrawPile(Boolean overflowDrawPile) {
        this.overflowDrawPile = overflowDrawPile;
        return this;
    }

    public PuzzleConfigDataBuilder setDrawPileCardsToOverflow(Integer drawPileCardsToOverflow) {
        this.drawPileCardsToOverflow = drawPileCardsToOverflow;
        return this;
    }

    public PuzzleConfigDataBuilder setDamageBoost(Boolean damageBoost) {
        this.damageBoost = damageBoost;
        return this;
    }

    public PuzzleConfigDataBuilder setRandomTokenToHand(Boolean randomTokenToHand) {
        this.randomTokenToHand = randomTokenToHand;
        return this;
    }

    public PuzzleConfigDataBuilder setRandomTokenAmount(Integer randomTokenAmount) {
        this.randomTokenAmount = randomTokenAmount;
        return this;
    }

    public PuzzleConfigDataBuilder setVigorToGain(Integer vigorToGain) {
        this.vigorToGain = vigorToGain;
        return this;
    }

    public PuzzleConfigDataBuilder setGainVigor(Boolean gainVigor) {
        this.gainVigor = gainVigor;
        return this;
    }

    public PuzzleConfigDataBuilder setBlurToGain(Integer blurToGain) {
        this.blurToGain = blurToGain;
        return this;
    }

    public PuzzleConfigDataBuilder setAddBixi(Boolean addBixi) {
        this.addBixi = addBixi;
        return this;
    }

    public PuzzleConfigDataBuilder setApplyConstricted(Boolean applyConstricted) {
        this.applyConstricted = applyConstricted;
        return this;
    }

    public PuzzleConfigDataBuilder setConstrictedAmount(Integer constrictedAmount) {
        this.constrictedAmount = constrictedAmount;
        return this;
    }

    public PuzzleConfigDataBuilder setGainThorns(Boolean gainThorns) {
        this.gainThorns = gainThorns;
        return this;
    }

    public PuzzleConfigDataBuilder setThornsAmount(Integer thornsAmount) {
        this.thornsAmount = thornsAmount;
        return this;
    }

    public PuzzleConfigDataBuilder setAddMonsterToHand(Boolean addMonsterToHand) {
        this.addMonsterToHand = addMonsterToHand;
        return this;
    }

    public PuzzleConfigDataBuilder setRandomMonstersToAdd(Integer randomMonstersToAdd) {
        this.randomMonstersToAdd = randomMonstersToAdd;
        return this;
    }

    public PuzzleConfigDataBuilder setIncrement(Boolean increment) {
        this.increment = increment;
        return this;
    }

    public PuzzleConfigDataBuilder setAmountToIncrement(Integer amountToIncrement) {
        this.amountToIncrement = amountToIncrement;
        return this;
    }

    public PuzzleConfigDataBuilder setAmountToIncrementMatchesAct(Boolean amountToIncrementMatchesAct) {
        this.amountToIncrementMatchesAct = amountToIncrementMatchesAct;
        return this;
    }

    public PuzzleConfigDataBuilder setGainRandomBuff(Boolean gainRandomBuff) {
        this.gainRandomBuff = gainRandomBuff;
        return this;
    }

    public PuzzleConfigDataBuilder setApplySoulbound(Boolean applySoulbound) {
        this.applySoulbound = applySoulbound;
        return this;
    }

    public PuzzleConfigDataBuilder setDrawExodiaHead(Boolean drawExodiaHead) {
        this.drawExodiaHead = drawExodiaHead;
        return this;
    }

    public PuzzleConfigDataBuilder setPharaohEffectDisabled(Boolean pharaohEffectDisabled) {
        this.pharaohEffectDisabled = pharaohEffectDisabled;
        return this;
    }

    public PuzzleConfigDataBuilder setGainRandomBlock(Boolean gainRandomBlock) {
        this.gainRandomBlock = gainRandomBlock;
        return this;
    }

    public PuzzleConfigDataBuilder setRandomSummonTokensLowEnd(Integer randomSummonTokensLowEnd) {
        this.randomSummonTokensLowEnd = randomSummonTokensLowEnd;
        return this;
    }

    public PuzzleConfigDataBuilder setRandomSummonTokensHighEnd(Integer randomSummonTokensHighEnd) {
        this.randomSummonTokensHighEnd = randomSummonTokensHighEnd;
        return this;
    }

    public PuzzleConfigDataBuilder setCannotObtainCards(Boolean cannotObtainCards) {
        this.cannotObtainCards = cannotObtainCards;
        return this;
    }

    public PuzzleConfigDataBuilder setStats(StartingDeckStats stats) {
        if (stats == null) {
            stats = new StartingDeckStatsBuilder().createStartingDeckStats();
        }
        this.output.setStats(stats);
        return this;
    }

    public void setFangsToGain(int fangs) {
        this.put("fangsToGain", fangs);
    }

    public void setAmountOfBeastsToTrigger(int amt) {
        this.put("amountOfBeastsToTrigger", amt);
    }

    public void setFangTriggerEffect(boolean trigger) {
        this.put("triggerFangEffect", trigger);
    }

    public void setPharaohAmount1(int amount) {
        this.put("pharaohAmount1", amount);
    }

    public void setPharaohAmount2(int amount) {
        this.put("pharaohAmount2", amount);
    }

    public void setPharaohPercent(String percentage) {
        this.put("pharaohPercent", percentage);
    }

    public void put(String key, Object value) {
        if (value != null && !value.toString().trim().equals("")) {
            this.output.put(key, value);
        }
    }

    public PuzzleConfigData createPuzzleConfigData() {
        put("effectsChoices", effectsChoices);
        put("effectsDisabled", effectsDisabled);
        put("effectsToRemove", effectsToRemove);
        put("gainBlur", gainBlur);
        put("randomBlockLow", randomBlockLow);
        put("randomBlockHigh", randomBlockHigh);
        put("startingVines", startingVines);
        put("startingLeaves", startingLeaves);
        put("tokensToSummon", tokensToSummon);
        put("tokenType", tokenType);
        put("applyToonWorld", applyToonWorld);
        put("channelShadow", channelShadow);
        put("overflowDrawPile", overflowDrawPile);
        put("drawPileCardsToOverflow", drawPileCardsToOverflow);
        put("damageBoost", damageBoost);
        put("randomTokenToHand", randomTokenToHand);
        put("randomTokenAmount", randomTokenAmount);
        put("vigorToGain", vigorToGain);
        put("gainVigor", gainVigor);
        put("blurToGain", blurToGain);
        put("addBixi", addBixi);
        put("applyConstricted", applyConstricted);
        put("constrictedAmount", constrictedAmount);
        put("gainThorns", gainThorns);
        put("thornsAmount", thornsAmount);
        put("addMonsterToHand", addMonsterToHand);
        put("randomMonstersToAdd", randomMonstersToAdd);
        put("increment", increment);
        put("amountToIncrement", amountToIncrement);
        put("amountToIncrementMatchesAct", amountToIncrementMatchesAct);
        put("gainRandomBuff", gainRandomBuff);
        put("applySoulbound", applySoulbound);
        put("cannotObtainCards", cannotObtainCards);
        put("drawExodiaHead", drawExodiaHead);
        put("pharaohEffectDisabled", pharaohEffectDisabled);
        put("gainRandomBlock", gainRandomBlock);
        put("randomSummonTokensLowEnd", randomSummonTokensLowEnd);
        put("randomSummonTokensHighEnd", randomSummonTokensHighEnd);
        return output;
    }
}
