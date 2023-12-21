package duelistmod.persistence.data;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.CardConfigData;
import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardConfigurations extends DataCategory {

    private HashMap<String, CardConfigData> cardConfigurations = new HashMap<>();
    private Boolean tokensPurgeAtEndOfTurn = true;
    private Integer explosiveDamageLow = 2;
    private Integer explosiveDamageHigh = 6;
    private Integer superExplosiveLowMultiplier = 3;
    private Integer superExplosiveHighMultiplier = 4;

    public CardConfigurations() {
        this.category = "Card Configuration Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public CardConfigurations(CardConfigurations from) {
        this(from.getCardConfigurations(), from.getTokensPurgeAtEndOfTurn(), from.getExplosiveDamageLow(), from.getExplosiveDamageHigh(), from.getSuperExplosiveLowMultiplier(), from.getSuperExplosiveHighMultiplier());
    }

    public CardConfigurations(CardConfigurations base, Boolean tokensPurgeAtEndOfTurn, Integer explosiveDamageLow, Integer explosiveDamageHigh, Integer superExplosiveLowMultiplier, Integer superExplosiveHighMultiplier) {
        this(base.getCardConfigurations(), tokensPurgeAtEndOfTurn, explosiveDamageLow, explosiveDamageHigh, superExplosiveLowMultiplier, superExplosiveHighMultiplier);
    }

    public CardConfigurations(HashMap<String, CardConfigData> cardConfigurations, Boolean tokensPurgeAtEndOfTurn, Integer explosiveDamageLow, Integer explosiveDamageHigh, Integer superExplosiveLowMultiplier, Integer superExplosiveHighMultiplier) {
        this();
        this.cardConfigurations = cardConfigurations;
        this.tokensPurgeAtEndOfTurn = tokensPurgeAtEndOfTurn;
        this.explosiveDamageLow = explosiveDamageLow;
        this.explosiveDamageHigh = explosiveDamageHigh;
        this.superExplosiveLowMultiplier = superExplosiveLowMultiplier;
        this.superExplosiveHighMultiplier = superExplosiveHighMultiplier;

        if (this.cardConfigurations == null) this.cardConfigurations = new HashMap<>();
        for (DuelistCard card : DuelistMod.myCards) {
            CardConfigData baseConfig = card.getDefaultConfig();
            CardConfigData activeConfig = this.cardConfigurations.getOrDefault(card.cardID, null);
            CardConfigData mergedConfig;
            if (activeConfig == null) {
                mergedConfig = baseConfig;
            } else {
                mergedConfig = activeConfig;
                for (Map.Entry<String, Object> entry : baseConfig.getProperties().entrySet()) {
                    if (!mergedConfig.getProperties().containsKey(entry.getKey())) {
                        mergedConfig.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            this.cardConfigurations.put(card.cardID, mergedConfig);
        }
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.CardConfigurations.getTokensPurgeAtEndOfTurn().equals(playerSettings.CardConfigurations.getTokensPurgeAtEndOfTurn())) {
            output.add(new DataDifferenceDTO<>(this, "Global Card Config: Tokens Purge at End of Turn", defaultSettings.CardConfigurations.getTokensPurgeAtEndOfTurn(), playerSettings.CardConfigurations.getTokensPurgeAtEndOfTurn()));
        }
        if (!defaultSettings.CardConfigurations.getExplosiveDamageLow().equals(playerSettings.CardConfigurations.getExplosiveDamageLow())) {
            output.add(new DataDifferenceDTO<>(this, "Global Card Config: Explosive Token Damage (Low)", defaultSettings.CardConfigurations.getExplosiveDamageLow(), playerSettings.CardConfigurations.getExplosiveDamageLow()));
        }
        if (!defaultSettings.CardConfigurations.getExplosiveDamageHigh().equals(playerSettings.CardConfigurations.getExplosiveDamageHigh())) {
            output.add(new DataDifferenceDTO<>(this, "Global Card Config: Explosive Token Damage (High)", defaultSettings.CardConfigurations.getExplosiveDamageHigh(), playerSettings.CardConfigurations.getExplosiveDamageHigh()));
        }
        if (!defaultSettings.CardConfigurations.getSuperExplosiveLowMultiplier().equals(playerSettings.CardConfigurations.getSuperExplosiveLowMultiplier())) {
            output.add(new DataDifferenceDTO<>(this, "Global Card Config: Super Explosive Token Damage Multiplier (Low)", defaultSettings.CardConfigurations.getSuperExplosiveLowMultiplier(), playerSettings.CardConfigurations.getSuperExplosiveLowMultiplier()));
        }
        if (!defaultSettings.CardConfigurations.getSuperExplosiveHighMultiplier().equals(playerSettings.CardConfigurations.getSuperExplosiveHighMultiplier())) {
            output.add(new DataDifferenceDTO<>(this, "Global Card Config: Super Explosive Token Damage Multiplier (High)", defaultSettings.CardConfigurations.getSuperExplosiveHighMultiplier(), playerSettings.CardConfigurations.getSuperExplosiveHighMultiplier()));
        }
        for (Map.Entry<String, CardConfigData> entry : this.cardConfigurations.entrySet()) {
            List<DuelistCard> cardMatches = DuelistMod.myCards.stream().filter(c -> c.cardID.equals(entry.getKey())).collect(Collectors.toList());

            if (!cardMatches.isEmpty()) {
                CardConfigData base = cardMatches.get(0).getDefaultConfig();
                CardConfigData active = entry.getValue();
                for (Map.Entry<String, Object> e : active.getProperties().entrySet()) {
                    if (base.getProperties().containsKey(e.getKey()) && !base.getProperties().get(e.getKey()).equals(e.getValue())) {
                        output.add(new DataDifferenceDTO<>(this, "Card Setting (" + cardMatches.get(0).name + "): " + e.getKey(), base.getProperties().get(e.getKey()), e.getValue()));
                    }
                }
            }
        }
        return output;
    }

    public HashMap<String, CardConfigData> getCardConfigurations() {
        if (cardConfigurations == null) {
            cardConfigurations = new HashMap<>();
        }
        if (cardConfigurations.isEmpty()) {
            for (DuelistCard card : DuelistMod.myCards) {
                CardConfigData baseConfig = card.getDefaultConfig();
                if (baseConfig != null) {
                    cardConfigurations.put(card.cardID, baseConfig);
                }
            }
        }
        return cardConfigurations;
    }

    public void setCardConfigurations(HashMap<String, CardConfigData> cardConfigurations) {
        this.cardConfigurations = cardConfigurations;
    }

    public Boolean getTokensPurgeAtEndOfTurn() {
        return tokensPurgeAtEndOfTurn;
    }

    public void setTokensPurgeAtEndOfTurn(Boolean tokensPurgeAtEndOfTurn) {
        this.tokensPurgeAtEndOfTurn = tokensPurgeAtEndOfTurn;
    }

    public Integer getExplosiveDamageLow() {
        return explosiveDamageLow;
    }

    public void setExplosiveDamageLow(Integer explosiveDamageLow) {
        this.explosiveDamageLow = explosiveDamageLow;
    }

    public Integer getExplosiveDamageHigh() {
        return explosiveDamageHigh;
    }

    public void setExplosiveDamageHigh(Integer explosiveDamageHigh) {
        this.explosiveDamageHigh = explosiveDamageHigh;
    }

    public Integer getSuperExplosiveLowMultiplier() {
        return superExplosiveLowMultiplier;
    }

    public void setSuperExplosiveLowMultiplier(Integer superExplosiveLowMultiplier) {
        this.superExplosiveLowMultiplier = superExplosiveLowMultiplier;
    }

    public Integer getSuperExplosiveHighMultiplier() {
        return superExplosiveHighMultiplier;
    }

    public void setSuperExplosiveHighMultiplier(Integer superExplosiveHighMultiplier) {
        this.superExplosiveHighMultiplier = superExplosiveHighMultiplier;
    }
}
