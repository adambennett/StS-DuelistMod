package duelistmod.helpers;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import duelistmod.*;
import okhttp3.*;
import org.apache.logging.log4j.*;

import java.util.*;

public class MetricsService {

    public static final Logger logger = DuelistMod.logger;

    public static Map<String, List<String>> getAllModuleVersions() {
        List<String> out = getStrings();
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

    private static List<String> getStrings() {
        List<String> output = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://localhost:8080/allModuleVersions")
                    .method("GET", null)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            ObjectMapper objectMapper = new ObjectMapper();
            output = objectMapper
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.body().string(), new TypeReference<List<String>>(){});
            logger.info("Got module versions from server properly.");
        } catch (Exception ex) {
            logger.error("Metrics module versions GET request error!", ex);
        }
        return output;
    }


}
