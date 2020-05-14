package duelistmod.helpers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.net.*;
import duelistmod.*;
import org.apache.logging.log4j.*;

import java.util.*;

public class MetricsService {

    public static final Logger logger = DuelistMod.logger;

    public static Map<String, List<String>> getAllModuleVersions() {
        List<String> out = getStrings("http://localhost:8080/allModuleVersions");
        Map<String, List<String>> output = new HashMap<>();
        for (String module : out) {
            String[] splice = module.split(",");
            String mod = splice[0];
            String ver = splice[1];
            List<String> newArr;
            if (output.containsKey(mod)) {
                newArr = output.get(mod);
            } else {
                newArr = new ArrayList<>();
                newArr.add(ver);
            }
            output.put(mod, newArr);
        }
        return output;
    }

    public static List<String> temp() {
        return getStrings("http://localhost:8080/allModuleVersions");
    }

    private static List<String> getStrings(String endpoint) {
        List<String> output = new ArrayList<>();
        String url = endpoint;
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder
                .newRequest()
                .method("GET").url(url)
                /* .header("Content-Type", "application/json")
                 .header("Accept", "application/json")
                 .header("User-Agent", "curl/7.43.0")*/
                .build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                logger.info("Metrics: http request response: " + httpResponse.getResultAsString());
                output.add(httpResponse.getResultAsString());
            }

            public void failed(Throwable t) {
                logger.info("Metrics: http request failed: " + t.toString());
            }

            public void cancelled() {
                logger.info("Metrics: http request cancelled.");
            }
        });
        return output;
    }


}
