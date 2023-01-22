package duelistmod.dto.builders;

import duelistmod.dto.PuzzleConfigData;
import duelistmod.dto.StartingDeckStats;

public class PuzzleConfigDataBuilder {

    private String deck;
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
    private StartingDeckStats stats = new StartingDeckStatsBuilder().createStartingDeckStats();

    public PuzzleConfigDataBuilder setDeck(String deck) {
        this.deck = deck;
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
        this.stats = stats;
        return this;
    }

    public PuzzleConfigData createPuzzleConfigData() {
        return new PuzzleConfigData(deck, effectsChoices, effectsDisabled, effectsToRemove, gainBlur, randomBlockLow, randomBlockHigh, startingVines, startingLeaves, tokensToSummon, tokenType, applyToonWorld, channelShadow, overflowDrawPile, drawPileCardsToOverflow, damageBoost, randomTokenToHand, randomTokenAmount, vigorToGain, gainVigor, blurToGain, addBixi, applyConstricted, constrictedAmount, gainThorns, thornsAmount, addMonsterToHand, randomMonstersToAdd, increment, amountToIncrement, amountToIncrementMatchesAct, gainRandomBuff, applySoulbound, cannotObtainCards, drawExodiaHead, pharaohEffectDisabled, gainRandomBlock, randomSummonTokensLowEnd, randomSummonTokensHighEnd, stats);
    }
}
