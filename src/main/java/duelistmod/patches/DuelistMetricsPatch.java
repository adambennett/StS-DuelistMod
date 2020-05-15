package duelistmod.patches;

import java.lang.reflect.*;
import java.util.HashMap;

import duelistmod.metrics.*;
import org.apache.logging.log4j.*;

import com.badlogic.gdx.Net;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;

import basemod.ReflectionHacks;
import javassist.CtBehavior;


/*

This class sends metrics data to my custom metrics processing site from runs completed with DuelistMod installed.
If you look at the code here, you will see two separate HTTP posts - one is being sent to the heroku metrics site
that is publicly viewable, and the other is a duplicate that is sent to HostGator where I am storing a backup
of all tracked runs since 10/31/2019.

Source code for immediate processing of this data is available here: https://github.com/adambennett/StS-Metrics-Server
You can view the frontend output of all metrics processing here:     https://sts-metrics-site.herokuapp.com/

 */
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

        @SuppressWarnings("unchecked")
		public static void Postfix(Metrics metrics) {
            if (metrics.type == Metrics.MetricRequestType.UPLOAD_METRICS && AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
            	HashMap<Object, Object> par = (HashMap<Object, Object>) ReflectionHacks.getPrivate(metrics, Metrics.class, "params");
            	MetricsHelper.setupCustomMetrics(par);
            	HerokuMetrics server = new HerokuMetrics();
            	server.uploadRun(par);
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
