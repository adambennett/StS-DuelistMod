package duelistmod.metrics;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import java.io.Serializable;
import java.util.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import duelistmod.DuelistMod;
import duelistmod.patches.TheDuelistEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HerokuMetrics implements Runnable {
    private static final Logger logger = LogManager.getLogger(HerokuMetrics.class.getName());
    private final Gson gson = new Gson();
    private final HashMap<Object, Object> params;

    public HerokuMetrics(boolean victory) {
        this(victory, false, null);
    }

    public HerokuMetrics(boolean victory, boolean death, final MonsterGroup monsters) {
        final Metrics metrics = new Metrics();
        metrics.gatherAllDataAndSave(death, victory && !death, monsters);
        metrics.setValues(death, victory && !death, monsters, Metrics.MetricRequestType.UPLOAD_METRICS);
        this.params = ReflectionHacks.getPrivate(metrics, Metrics.class, "params");
        MetricsHelper.setupCustomMetrics(this.params, AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST);
        this.params.put("duelist_card_choices", getNamesForCardChoices());
    }

    @Override
    public void run() {
        this.uploadRun(this.params);
    }

    public void uploadRun(HashMap<Object, Object> par) {
        String url = MetricsHelper.ENDPOINT_RUN_UPLOAD;
        HashMap<String, Serializable> event = new HashMap<>();
        event.put("event", par);
        if (CardCrawlGame.playerName != null && !CardCrawlGame.playerName.equals(" ")) {
            event.put("host", CardCrawlGame.playerName);
        } else {
            event.put("host", CardCrawlGame.alias);
        }
        event.put("time", System.currentTimeMillis() / 1000L);
        String data = this.gson.toJson(event);
        logger.info("UPLOADING METRICS TO: url=" + url + ",data=" + data);
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        HttpRequest httpRequest = requestBuilder
                .newRequest()
                .method("POST").url(url)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", "curl/7.43.0")
                .build();
        httpRequest.setContent(data);
        Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
            public void handleHttpResponse(HttpResponse httpResponse) {
                logger.info("Metrics: http request response: " + httpResponse.getResultAsString());
            }

            public void failed(Throwable t) {
                logger.info("Metrics: http request failed: " + t.toString());
            }

            public void cancelled() {
                logger.info("Metrics: http request cancelled.");
            }
        });

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, Object> getNamesForCardChoices() {
        Map<String, Object> newCardChoices = new HashMap<>();
        for (HashMap map : CardCrawlGame.metricData.card_choices) {
            map.forEach((key, value) -> {
                String k  = key.toString();
                if (k.equals("picked")) {
                    String val = value.toString();
                    String cardName = DuelistMod.mapForCardPoolSave.containsKey(val) ? DuelistMod.mapForCardPoolSave.get(val).name : val;
                    newCardChoices.put("picked_name", cardName);
                } else if (k.equals("not_picked")) {
                    ArrayList<String> cards = (ArrayList<String>)value;
                    List<String> newNotPicked = new ArrayList<>();
                    for (String card : cards) {
                        String cardName = DuelistMod.mapForCardPoolSave.containsKey(card) ? DuelistMod.mapForCardPoolSave.get(card).name : card;
                        newNotPicked.add(cardName);
                    }
                    newCardChoices.put("not_picked_names", newNotPicked);
                }
                newCardChoices.put(k, value);
            });
        }
        return newCardChoices;
    }
}

