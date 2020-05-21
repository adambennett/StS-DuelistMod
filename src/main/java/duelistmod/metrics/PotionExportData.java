package duelistmod.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import duelistmod.metrics.builders.*;

public class PotionExportData implements Comparable<PotionExportData> {

    @JsonIgnore
    public ModExportData mod;

    public AbstractPotion potion;
    public String id, name, rarity;
    public String description, descriptionHTML, descriptionPlain;
    public String playerClass;

    public PotionExportData(Exporter export, AbstractPotion potion, AbstractPlayer.PlayerClass cls) {
        this.potion = potion;
        this.mod = export.findMod(potion.getClass());
        this.mod.potions.add(this);
        this.id = potion.ID;
        this.name = potion.name;
        this.description = potion.description;
        this.descriptionHTML = RelicExportData.smartTextToHTML(potion.description,true,true);
        this.descriptionPlain = RelicExportData.smartTextToPlain(potion.description,true,true);
        this.rarity = ExportUploader.rarityName(potion.rarity);
        this.playerClass = cls == null ? "All Characters" : cls.toString();
    }

    public static ArrayList<PotionExportData> exportAllPotions(Exporter export) {
        ArrayList<PotionExportData> potions = new ArrayList<>();
        for (HashMap.Entry<String,AbstractPlayer.PlayerClass> potionID : getAllPotionIds().entrySet()) {
            potions.add(new PotionExportData(export, PotionHelper.getPotion(potionID.getKey()), potionID.getValue()));
        }
        Collections.sort(potions);
        return potions;
    }

    public static HashMap<String,AbstractPlayer.PlayerClass> getAllPotionIds() {
        HashMap<String,AbstractPlayer.PlayerClass> potions = new HashMap<>();
        for (AbstractPlayer.PlayerClass playerClass : AbstractPlayer.PlayerClass.values()) {
            PotionHelper.initialize(playerClass);
            for (String potionID : PotionHelper.potions) {
                if (potions.containsKey(potionID)) {
                    potions.put(potionID, null);
                } else {
                    potions.put(potionID, playerClass);
                }
            }
        }
        return potions;
    }

    @Override
    public int compareTo(PotionExportData that) {
        if (potion.rarity != that.potion.rarity) return potion.rarity.compareTo(that.potion.rarity);
        return name.compareTo(that.name);
    }

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("potion_id", id);
        builder.append("name", name);
        builder.append("rarity", rarity);
        builder.append("description", description);
        builder.append("descriptionPlain", descriptionPlain);
        builder.append("playerClass", playerClass);
        return builder.build();
    }
}
