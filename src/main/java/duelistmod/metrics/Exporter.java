package duelistmod.metrics;
import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;


import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.variables.*;
import okhttp3.*;
import org.apache.logging.log4j.Logger;

public class Exporter {
    public static final Logger logger = DuelistMod.logger;

    public static void uploadInfoJSON() {
        String url = "https://sts-duelist-metrics.herokuapp.com/dataupload";
        //String url = "http://localhost:8080/dataupload";
        Map<String, Integer> dataMap = getInfoJSON();
        if (!dataMap.containsKey("ERROR") && dataMap.entrySet().iterator().hasNext()) {
            Map.Entry<String, Integer> dataEntry = dataMap.entrySet().iterator().next();
            String data = dataEntry.getKey();
            Integer amt = dataEntry.getValue();
            logger.info("UPLOADING INFO DATA FOR " + amt + " MODS TO: url=" + url + ",data=" + data);
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, data);
                Request request = new Request.Builder()
                        .url(url)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();
                logger.info("Metrics: http request response: " + response.body());
            } catch (Exception ex) {
                logger.error("Info upload error!", ex);
            }
        }
    }

    private static Map<String, Integer> getInfoJSON() {
        Map<String, Integer> tupleOutput = new HashMap<>();
        try {
            ExportHelper export = new ExportHelper();
            int num = export.collectAll();
            if (num > 0) {
                tupleOutput.put("{ \"info\":" + export.mods.toString() + "}", num);
            }
        } catch (Exception ex) {
            logger.error("Error during export", ex);
            tupleOutput.clear();
            tupleOutput.put("ERROR", 0);
        }
        return tupleOutput;
    }

    public static String duelistType(DuelistCard card) {
        if (card.hasTag(Tags.SPELL)) {
            return "Spell";
        } else  if (card.hasTag(Tags.TRAP)) {
            return "Trap";
        } else if (card.hasTag(Tags.MONSTER)) {
            return "Monster";
        }
        return "";
    }

    public static String typeString(AbstractCard.CardType type) {
        switch (type) {
            case ATTACK: {
                return AbstractCard.TEXT[0];
            }
            case SKILL: {
                return AbstractCard.TEXT[1];
            }
            case POWER: {
                return AbstractCard.TEXT[2];
            }
            case STATUS: {
                return AbstractCard.TEXT[7];
            }
            case CURSE: {
                return AbstractCard.TEXT[3];
            }
            default: {
                return AbstractCard.TEXT[5];
            }
        }
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }

    public static String rarityName(AbstractCard.CardRarity rarity) {
        return toTitleCase(rarity.toString()); // TODO: localize?
    }

    public static String rarityName(AbstractPotion.PotionRarity rarity) {
        return toTitleCase(rarity.toString()); // TODO: localize?
    }

    public static String tierName(AbstractRelic.RelicTier tier) {
        return toTitleCase(tier.toString()); // TODO: localize?
    }

    public static String colorName(AbstractCard.CardColor color) {
        return toTitleCase(color.toString()); // TODO: localize?
    }
}
