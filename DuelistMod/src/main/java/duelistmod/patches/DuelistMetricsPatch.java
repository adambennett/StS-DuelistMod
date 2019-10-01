package duelistmod.patches;

import java.lang.reflect.*;
import java.util.HashMap;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.Net;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;

import basemod.ReflectionHacks;
import duelistmod.helpers.MetricsHelper;
import javassist.CtBehavior;

public class DuelistMetricsPatch {

    private static final Logger logger = LogManager.getLogger(DuelistMetricsPatch.class);

    @SpirePatch(clz = Metrics.class, method = "sendPost", paramtypez = {String.class, String.class})
    public static class SendPostPatch {
        public static void Prefix(Metrics metrics, @ByRef String[] url, String fileName) {
            if (AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
                url[0] = "http://softwaredev.site/Metrics/";
            }
        }

    }

    @SpirePatch(clz = Metrics.class, method = "sendPost", paramtypez = {String.class, String.class})
    public static class SendPutInsteadOfPostPatch {

        @SpireInsertPatch(locator = Locator.class, localvars = "httpRequest")
        public static void Insert(Metrics metrics, String url, String fileName, Net.HttpRequest httpRequest) {
            if (AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
                httpRequest.setMethod("PUT");
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior method) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(Net.HttpRequest.class, "setContent");
                return LineFinder.findInOrder(method, matcher);
            }
        }

    }


    @SpirePatch(clz = DeathScreen.class, method = "shouldUploadMetricData")
    public static class ShouldUploadMetricData {

        public static boolean Postfix(boolean returnValue) {
            if (AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
                returnValue = Settings.UPLOAD_DATA;
            }
            return returnValue;
        }

    }

    @SpirePatch(clz = Metrics.class, method = "run")
    public static class RunPatch {

        public static void Postfix(Metrics metrics) {
            if (metrics.type == Metrics.MetricRequestType.UPLOAD_METRICS && AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
            	HashMap<Object, Object> par = (HashMap<Object, Object>) ReflectionHacks.getPrivate(metrics, Metrics.class, "params");
            	MetricsHelper.setupCustomMetrics(par);
            	try {
                    Method m = Metrics.class.getDeclaredMethod("gatherAllDataAndSend", boolean.class, boolean.class, MonsterGroup.class);
                    m.setAccessible(true);
                    m.invoke(metrics, metrics.death, metrics.trueVictory, metrics.monsters);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    logger.error("Exception while sending metrics", e);
                }
            }
        }

    }
}