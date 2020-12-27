package duelistmod.metrics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import java.io.Serializable;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HerokuMetrics {//implements Runnable {
    private static final Logger logger = LogManager.getLogger(HerokuMetrics.class.getName());
    private final Gson gson = new Gson();

    public HerokuMetrics() {}

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
}

