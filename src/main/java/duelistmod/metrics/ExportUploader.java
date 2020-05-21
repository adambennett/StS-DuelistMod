package duelistmod.metrics;
import java.util.*;
import java.util.concurrent.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;


import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.variables.*;
import okhttp3.*;
import org.apache.logging.log4j.Logger;


/*

This class sends information about all game objects to my metrics server, mainly to be used to format
displays of object information. For example, cards are tracked during run processing by cardIDs,
but ideally I want to show the in-game card name on the frontend, along with other card information. Same goes for
relics/potions/etc. This class uploads information about any mods that my server is
not currently tracking (new mods or untracked versions of tracked mods).

Source code for immediate processing of this data is available here:  https://github.com/adambennett/StS-Metrics-Server
You can view the frontend output of all metrics processing here:      https://sts-metrics-site.herokuapp.com/
Initial export logic for game objects derived from:                   https://github.com/twanvl/sts-exporter
Export logic modified in part using:                                  https://github.com/Alchyr/sts-exporter

 */
public class ExportUploader {
    public static final Logger logger = DuelistMod.logger;

    public static void uploadInfoJSON() {
        String url = MetricsHelper.dataUploadURL;
        Map<String, Integer> dataMap = getInfoJSON();
        if (!dataMap.containsKey("ERROR") && dataMap.entrySet().iterator().hasNext()) {
            Map.Entry<String, Integer> dataEntry = dataMap.entrySet().iterator().next();
            String data = dataEntry.getKey();
            Integer amt = dataEntry.getValue();
            logger.info("UPLOADING INFO DATA FOR " + amt + " MODS TO: url=" + url + ",data=" + data);
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(5, TimeUnit.MINUTES)
                        .writeTimeout(5, TimeUnit.MINUTES)
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, data);
                Request request = new Request.Builder()
                        .url(url)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();
                logger.info("Metrics: http request response: " + response.body() + ", and CODE=" + response.code());
                response.close();
            } catch (Exception ex) {
                logger.error("Info upload error!", ex);
            }
        }
    }

    private static Map<String, Integer> getInfoJSON() {
        Map<String, Integer> tupleOutput = new HashMap<>();
        try {
            Exporter export = new Exporter();
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
