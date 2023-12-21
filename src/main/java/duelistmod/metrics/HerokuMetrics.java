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
    private final boolean isDuelist;

    public HerokuMetrics(boolean victory) {
        this(victory, false, null);
    }

    public HerokuMetrics(boolean victory, boolean death, final MonsterGroup monsters) {
        final Metrics metrics = new Metrics();
        this.isDuelist = AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST;
        metrics.gatherAllDataAndSave(death, victory && !death, monsters);
        metrics.setValues(death, victory && !death, monsters, Metrics.MetricRequestType.UPLOAD_METRICS);
        HashMap<Object,Object> baseGameRunData = ReflectionHacks.getPrivate(metrics, Metrics.class, "params");
        this.params = MetricsHelper.setupCustomMetrics(baseGameRunData, this.isDuelist);
    }

    @Override
    public void run() {
        this.uploadRun(this.params);
    }

    public void uploadRun(HashMap<Object, Object> par) {
        String url = MetricsHelper.ENDPOINT_RUN_UPLOAD;
        HashMap<String, Serializable> event = new HashMap<>();
        HashMap<String, Serializable> runBundle = new HashMap<>();
        runBundle.put("event", par);
        if (CardCrawlGame.playerName != null && !CardCrawlGame.playerName.equals(" ")) {
            runBundle.put("host", CardCrawlGame.playerName);
        } else {
            runBundle.put("host", CardCrawlGame.alias);
        }
        runBundle.put("time", System.currentTimeMillis() / 1000L);
        event.put("runBundle", runBundle);
        if (this.isDuelist) {
            event.put("configDifferences", DuelistMod.persistentDuelistData.generateMetricsDifferences());
        }
        String data = this.gson.toJson(event);
        logger.info("UPLOADING METRICS TO: url=" + url + ",data=" + data);
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        HttpRequest httpRequest = requestBuilder
                .newRequest()
                .method("POST").url(url)
                .header("Content-Type", "application/json;charset=UTF-8")
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
}

