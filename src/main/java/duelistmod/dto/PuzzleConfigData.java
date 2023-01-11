package duelistmod.dto;


public class PuzzleConfigData {

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
    private Integer vigorToGain;
    private Boolean gainVigor;
    private Integer blurToGain;
    private Boolean addBixi;
    private Boolean applyConstricted;
    private Boolean gainThorns;
    private Boolean addMonsterToHand;
    private Boolean increment;
    private Integer amountToIncrement;
    private Boolean amountToIncrementMatchesAct;
    private Boolean gainRandomBuff;
    private Boolean applySoulbound;
    private Boolean drawExodiaHead;
    private Boolean pharaohEffectDisabled;
    private Boolean gainRandomBlock;


    public PuzzleConfigData(String deck, Integer effectsChoices, Boolean effectsDisabled, Integer effectsToRemove, Boolean gainBlur, Integer randomBlockLow, Integer randomBlockHigh, Integer startingVines, Integer startingLeaves, Integer tokensToSummon, String tokenType, Boolean applyToonWorld, Boolean channelShadow, Boolean overflowDrawPile, Integer drawPileCardsToOverflow, Boolean damageBoost, Boolean randomTokenToHand, Integer vigorToGain, Boolean gainVigor, Integer blurToGain, Boolean addBixi, Boolean applyConstricted, Boolean gainThorns, Boolean addMonsterToHand, Boolean increment, Integer amountToIncrement, Boolean amountToIncrementMatchesAct, Boolean gainRandomBuff, Boolean applySoulbound, Boolean drawExodiaHead, Boolean pharaohEffectDisabled, Boolean gainRandomBlock) {
        this.deck = deck;
        this.gainRandomBlock = gainRandomBlock;
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
        this.vigorToGain = vigorToGain;
        this.gainVigor = gainVigor;
        this.blurToGain = blurToGain;
        this.addBixi = addBixi;
        this.applyConstricted = applyConstricted;
        this.gainThorns = gainThorns;
        this.addMonsterToHand = addMonsterToHand;
        this.increment = increment;
        this.amountToIncrement = amountToIncrement;
        this.amountToIncrementMatchesAct = amountToIncrementMatchesAct;
        this.gainRandomBuff = gainRandomBuff;
        this.applySoulbound = applySoulbound;
        this.drawExodiaHead = drawExodiaHead;
        this.pharaohEffectDisabled = pharaohEffectDisabled;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public Integer getEffectsChoices() {
        return effectsChoices;
    }

    public void setEffectsChoices(Integer effectsChoices) {
        this.effectsChoices = effectsChoices;
    }

    public Boolean getEffectsDisabled() {
        return effectsDisabled;
    }

    public void setEffectsDisabled(Boolean effectsDisabled) {
        this.effectsDisabled = effectsDisabled;
    }

    public Integer getEffectsToRemove() {
        return effectsToRemove;
    }

    public void setEffectsToRemove(Integer effectsToRemove) {
        this.effectsToRemove = effectsToRemove;
    }

    public Boolean getGainBlur() {
        return gainBlur;
    }

    public void setGainBlur(Boolean gainBlur) {
        this.gainBlur = gainBlur;
    }

    public Integer getRandomBlockLow() {
        return randomBlockLow;
    }

    public void setRandomBlockLow(Integer randomBlockLow) {
        this.randomBlockLow = randomBlockLow;
    }

    public Integer getRandomBlockHigh() {
        return randomBlockHigh;
    }

    public void setRandomBlockHigh(Integer randomBlockHigh) {
        this.randomBlockHigh = randomBlockHigh;
    }

    public Integer getStartingVines() {
        return startingVines;
    }

    public void setStartingVines(Integer startingVines) {
        this.startingVines = startingVines;
    }

    public Integer getStartingLeaves() {
        return startingLeaves;
    }

    public void setStartingLeaves(Integer startingLeaves) {
        this.startingLeaves = startingLeaves;
    }

    public Integer getTokensToSummon() {
        return tokensToSummon;
    }

    public void setTokensToSummon(Integer tokensToSummon) {
        this.tokensToSummon = tokensToSummon;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Boolean getApplyToonWorld() {
        return applyToonWorld;
    }

    public void setApplyToonWorld(Boolean applyToonWorld) {
        this.applyToonWorld = applyToonWorld;
    }

    public Boolean getChannelShadow() {
        return channelShadow;
    }

    public void setChannelShadow(Boolean channelShadow) {
        this.channelShadow = channelShadow;
    }

    public Boolean getOverflowDrawPile() {
        return overflowDrawPile;
    }

    public void setOverflowDrawPile(Boolean overflowDrawPile) {
        this.overflowDrawPile = overflowDrawPile;
    }

    public Integer getDrawPileCardsToOverflow() {
        return drawPileCardsToOverflow;
    }

    public void setDrawPileCardsToOverflow(Integer drawPileCardsToOverflow) {
        this.drawPileCardsToOverflow = drawPileCardsToOverflow;
    }

    public Boolean getDamageBoost() {
        return damageBoost;
    }

    public void setDamageBoost(Boolean damageBoost) {
        this.damageBoost = damageBoost;
    }

    public Boolean getRandomTokenToHand() {
        return randomTokenToHand;
    }

    public void setRandomTokenToHand(Boolean randomTokenToHand) {
        this.randomTokenToHand = randomTokenToHand;
    }

    public Integer getVigorToGain() {
        return vigorToGain;
    }

    public void setVigorToGain(Integer vigorToGain) {
        this.vigorToGain = vigorToGain;
    }

    public Boolean getGainVigor() {
        return gainVigor;
    }

    public void setGainVigor(Boolean gainVigor) {
        this.gainVigor = gainVigor;
    }

    public Integer getBlurToGain() {
        return blurToGain;
    }

    public void setBlurToGain(Integer blurToGain) {
        this.blurToGain = blurToGain;
    }

    public Boolean getAddBixi() {
        return addBixi;
    }

    public void setAddBixi(Boolean addBixi) {
        this.addBixi = addBixi;
    }

    public Boolean getApplyConstricted() {
        return applyConstricted;
    }

    public void setApplyConstricted(Boolean applyConstricted) {
        this.applyConstricted = applyConstricted;
    }

    public Boolean getGainThorns() {
        return gainThorns;
    }

    public void setGainThorns(Boolean gainThorns) {
        this.gainThorns = gainThorns;
    }

    public Boolean getAddMonsterToHand() {
        return addMonsterToHand;
    }

    public void setAddMonsterToHand(Boolean addMonsterToHand) {
        this.addMonsterToHand = addMonsterToHand;
    }

    public Boolean getIncrement() {
        return increment;
    }

    public void setIncrement(Boolean increment) {
        this.increment = increment;
    }

    public Integer getAmountToIncrement() {
        return amountToIncrement;
    }

    public void setAmountToIncrement(Integer amountToIncrement) {
        this.amountToIncrement = amountToIncrement;
    }

    public Boolean getAmountToIncrementMatchesAct() {
        return amountToIncrementMatchesAct;
    }

    public void setAmountToIncrementMatchesAct(Boolean amountToIncrementMatchesAct) {
        this.amountToIncrementMatchesAct = amountToIncrementMatchesAct;
    }

    public Boolean getGainRandomBuff() {
        return gainRandomBuff;
    }

    public void setGainRandomBuff(Boolean gainRandomBuff) {
        this.gainRandomBuff = gainRandomBuff;
    }

    public Boolean getApplySoulbound() {
        return applySoulbound;
    }

    public void setApplySoulbound(Boolean applySoulbound) {
        this.applySoulbound = applySoulbound;
    }

    public Boolean getDrawExodiaHead() {
        return drawExodiaHead;
    }

    public void setDrawExodiaHead(Boolean drawExodiaHead) {
        this.drawExodiaHead = drawExodiaHead;
    }

    public Boolean getPharaohEffectDisabled() {
        return pharaohEffectDisabled;
    }

    public void setPharaohEffectDisabled(Boolean pharaohEffectDisabled) {
        this.pharaohEffectDisabled = pharaohEffectDisabled;
    }

    public Boolean getGainRandomBlock() {
        return gainRandomBlock;
    }

    public void setGainRandomBlock(Boolean gainRandomBlock) {
        this.gainRandomBlock = gainRandomBlock;
    }
}
