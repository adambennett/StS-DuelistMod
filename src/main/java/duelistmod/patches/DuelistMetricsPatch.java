package duelistmod.patches;

import java.util.*;

import duelistmod.*;
import duelistmod.enums.Mode;
import duelistmod.metrics.*;
import org.apache.logging.log4j.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import basemod.ReflectionHacks;

/*

This class sends metrics data to my custom metrics processing site from runs completed with DuelistMod installed.
If you look at the code here, you will see two separate HTTP posts - one is being sent to the heroku metrics site
that is publicly viewable, and the other is a duplicate that is sent to HostGator where I am storing a backup
of all tracked runs since 10/31/2019. EDIT: This is no longer accurate, HostGator is deprecated, run data is only being
sent to Heroku/AWS.

Source code for immediate processing of this data is available here: https://github.com/adambennett/StS-Metrics-Server
You can view the frontend output of all metrics processing here:     https://sts-metrics-site.herokuapp.com/

 */
public class DuelistMetricsPatch {

    private static final Logger logger = LogManager.getLogger(DuelistMetricsPatch.class);

    @SpirePatch(clz = Metrics.class, method = "run")
    public static class RunPatch {
		public static void Postfix(Metrics metrics) {
            if (AbstractDungeon.player.chosenClass != TheDuelistEnum.THE_DUELIST && DuelistMod.modMode != Mode.NIGHTLY) {
                HashMap<Object, Object> par = ReflectionHacks.getPrivate(metrics, Metrics.class, "params");
                MetricsHelper.setupCustomMetrics(par, AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST);
                par.put("duelist_card_choices", HerokuMetrics.getNamesForCardChoices());
                HerokuMetrics server = new HerokuMetrics(metrics.trueVictory);
                server.uploadRun(par);
            }
        }
    }
}
