package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import duelistmod.DuelistMod;
import duelistmod.dto.LoggedException;
import duelistmod.helpers.Util;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

import static duelistmod.metrics.MetricsHelper.ENDPOINT_EXCEPTION_HANDLER;

public class ExceptionHandlerPatch {

    @SpirePatch(clz = ExceptionHandler.class, method = "handleException")
    public static class HandlerPatches {
        public static void Postfix(Exception e, Logger logger) {
            sendExceptionRequestToServer(e, "Game Crash");
        }

        public static void sendExceptionRequestToServer(Exception ex, String devMessage) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(5, TimeUnit.MINUTES)
                        .writeTimeout(5, TimeUnit.MINUTES)
                        .build();
                LoggedException exception = new LoggedException(
                        ex != null ? ExceptionUtils.getRootCauseMessage(ex) : "",
                        ex != null ? ExceptionUtils.getStackTrace(ex) : "",
                        DuelistMod.metricsUUID,
                        DuelistMod.version,
                        devMessage,
                        DuelistMod.runUUID
                );
                Gson gson = new Gson();
                String requestBody = gson.toJson(exception);
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(requestBody, mediaType);
                Request request = new Request.Builder()
                        .url(ENDPOINT_EXCEPTION_HANDLER)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();
                response.close();
                Util.log("Successfully sent exception to DuelistMetrics server");
            } catch (Exception exception) {
                Util.log("Error sending exception handler request: " + exception.getMessage());
            }
        }
    }
}
