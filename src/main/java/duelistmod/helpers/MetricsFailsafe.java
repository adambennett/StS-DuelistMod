package duelistmod.helpers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import duelistmod.abstracts.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetricsFailsafe implements Runnable {
    private static final Logger logger = LogManager.getLogger(MetricsFailsafe.class.getName());
    public final HashMap<Object, Object> params = new HashMap();
    private final Gson gson = new Gson();
    private long lastPlaytimeEnd;
    public static final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
    public boolean death;
    public boolean trueVictory;
    public MonsterGroup monsters = null;
    public com.megacrit.cardcrawl.metrics.Metrics.MetricRequestType type;

    public MetricsFailsafe() {}

    public void sendCardsToServer(ArrayList<MetricCard> cards) {
        String url = "https://sts-duelist-metrics.herokuapp.com/carduploads";
        HashMap<String, Serializable> event = new HashMap<>();
        event.put("cards", cards);
        String data = this.gson.toJson(event);
        logger.info("UPLOADING CARD DATA TO: url=" + url + ",data=" + data);
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

    public void setupDataAndSend(HashMap<Object, Object> par) {
        String url = "https://sts-duelist-metrics.herokuapp.com/upload";
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
                .method("PUT").url(url)
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

    public void setValues(boolean death, boolean trueVictor, MonsterGroup monsters, com.megacrit.cardcrawl.metrics.Metrics.MetricRequestType type) {
        this.death = death;
        this.trueVictory = trueVictor;
        this.monsters = monsters;
        this.type = type;
    }

    private void sendPost(String fileName) {
        String url = "https://sts-duelist-metrics.herokuapp.com/upload";
        this.sendPost(url, fileName);
    }

    private void addData(Object key, Object value) {
        this.params.put(key, value);
    }

    private void sendPost(String url, final String fileToDelete) {
        HashMap<String, Serializable> event = new HashMap();
        event.put("event", this.params);
        if (Settings.isBeta) {
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
                if (fileToDelete != null) {
                    Gdx.files.local(fileToDelete).delete();
                }

            }

            public void failed(Throwable t) {
                logger.info("Metrics: http request failed: " + t.toString());
            }

            public void cancelled() {
                logger.info("Metrics: http request cancelled.");
            }
        });
    }

    private void gatherAllData(boolean death, boolean trueVictor, MonsterGroup monsters) {
        this.addData("play_id", UUID.randomUUID().toString());
        this.addData("build_version", CardCrawlGame.TRUE_VERSION_NUM);
        this.addData("seed_played", Settings.seed.toString());
        this.addData("chose_seed", Settings.seedSet);
        this.addData("seed_source_timestamp", Settings.seedSourceTimestamp);
        this.addData("is_daily", Settings.isDailyRun);
        this.addData("special_seed", Settings.specialSeed);
        if (ModHelper.enabledMods.size() > 0) {
            this.addData("daily_mods", ModHelper.getEnabledModIDs());
        }

        this.addData("is_trial", Settings.isTrial);
        this.addData("is_endless", Settings.isEndless);
        if (death) {
            AbstractPlayer player = AbstractDungeon.player;
            CardCrawlGame.metricData.current_hp_per_floor.add(player.currentHealth);
            CardCrawlGame.metricData.max_hp_per_floor.add(player.maxHealth);
            CardCrawlGame.metricData.gold_per_floor.add(player.gold);
        }

        this.addData("is_ascension_mode", AbstractDungeon.isAscensionMode);
        this.addData("ascension_level", AbstractDungeon.ascensionLevel);
        this.addData("neow_bonus", CardCrawlGame.metricData.neowBonus);
        this.addData("neow_cost", CardCrawlGame.metricData.neowCost);
        this.addData("is_beta", Settings.isBeta);
        this.addData("is_prod", Settings.isDemo);
        this.addData("victory", !death);
        this.addData("floor_reached", AbstractDungeon.floorNum);
        if (trueVictor) {
            this.addData("score", VictoryScreen.calcScore(!death));
        } else {
            this.addData("score", DeathScreen.calcScore(!death));
        }

        this.lastPlaytimeEnd = System.currentTimeMillis() / 1000L;
        this.addData("timestamp", this.lastPlaytimeEnd);
        this.addData("local_time", timestampFormatter.format(Calendar.getInstance().getTime()));
        this.addData("playtime", (long)CardCrawlGame.playtime);
        this.addData("player_experience", Settings.totalPlayTime);
        this.addData("master_deck", AbstractDungeon.player.masterDeck.getCardIdsForMetrics());
        this.addData("relics", AbstractDungeon.player.getRelicNames());
        this.addData("gold", AbstractDungeon.player.gold);
        this.addData("campfire_rested", CardCrawlGame.metricData.campfire_rested);
        this.addData("campfire_upgraded", CardCrawlGame.metricData.campfire_upgraded);
        this.addData("purchased_purges", CardCrawlGame.metricData.purchased_purges);
        this.addData("potions_floor_spawned", CardCrawlGame.metricData.potions_floor_spawned);
        this.addData("potions_floor_usage", CardCrawlGame.metricData.potions_floor_usage);
        this.addData("current_hp_per_floor", CardCrawlGame.metricData.current_hp_per_floor);
        this.addData("max_hp_per_floor", CardCrawlGame.metricData.max_hp_per_floor);
        this.addData("gold_per_floor", CardCrawlGame.metricData.gold_per_floor);
        this.addData("path_per_floor", CardCrawlGame.metricData.path_per_floor);
        this.addData("path_taken", CardCrawlGame.metricData.path_taken);
        this.addData("items_purchased", CardCrawlGame.metricData.items_purchased);
        this.addData("item_purchase_floors", CardCrawlGame.metricData.item_purchase_floors);
        this.addData("items_purged", CardCrawlGame.metricData.items_purged);
        this.addData("items_purged_floors", CardCrawlGame.metricData.items_purged_floors);
        this.addData("character_chosen", AbstractDungeon.player.chosenClass.name());
        this.addData("card_choices", CardCrawlGame.metricData.card_choices);
        this.addData("event_choices", CardCrawlGame.metricData.event_choices);
        this.addData("boss_relics", CardCrawlGame.metricData.boss_relics);
        this.addData("damage_taken", CardCrawlGame.metricData.damage_taken);
        this.addData("potions_obtained", CardCrawlGame.metricData.potions_obtained);
        this.addData("relics_obtained", CardCrawlGame.metricData.relics_obtained);
        this.addData("campfire_choices", CardCrawlGame.metricData.campfire_choices);
        this.addData("circlet_count", AbstractDungeon.player.getCircletCount());
        Prefs pref = AbstractDungeon.player.getPrefs();
        int numVictory = pref.getInteger("WIN_COUNT", 0);
        int numDeath = pref.getInteger("LOSE_COUNT", 0);
        if (numVictory <= 0) {
            this.addData("win_rate", 0.0F);
        } else {
            this.addData("win_rate", numVictory / (numDeath + numVictory));
        }

        if (death && monsters != null) {
            this.addData("killed_by", AbstractDungeon.lastCombatMetricKey);
        } else {
            this.addData("killed_by", (Object)null);
        }

    }

    private void gatherAllDataAndSend(boolean death, boolean trueVictor, MonsterGroup monsters) {
        if (DeathScreen.shouldUploadMetricData()) {
            this.gatherAllData(death, trueVictor, monsters);
            this.sendPost((String)null);
        }

    }

    public void gatherAllDataAndSave(boolean death, boolean trueVictor, MonsterGroup monsters) {
        this.gatherAllData(death, trueVictor, monsters);
        String data = this.gson.toJson(this.params);
        FileHandle file;
        String local_runs_save_path;
        if (!Settings.isDailyRun) {
            local_runs_save_path = "runs" + File.separator;
            switch(CardCrawlGame.saveSlot) {
                default:
                    local_runs_save_path = local_runs_save_path + CardCrawlGame.saveSlot + "_";
                case 0:
                    local_runs_save_path = local_runs_save_path + AbstractDungeon.player.chosenClass.name() + File.separator + this.lastPlaytimeEnd + ".run";
                    file = Gdx.files.local(local_runs_save_path);
            }
        } else {
            local_runs_save_path = "runs" + File.separator;
            switch(CardCrawlGame.saveSlot) {
                default:
                    local_runs_save_path = local_runs_save_path + CardCrawlGame.saveSlot + "_";
                case 0:
                    file = Gdx.files.local(local_runs_save_path + "DAILY" + File.separator + this.lastPlaytimeEnd + ".run");
            }
        }

        file.writeString(data, false);
        this.removeExcessRunFiles();
    }

    private void removeExcessRunFiles() {
        if (Settings.isConsoleBuild) {
            FileHandle fh = Gdx.files.local("runs");
            FileHandle[] allFolders = fh.list();
            HashMap<String, FileHandle> map = new HashMap();
            List<String> runNames = new ArrayList();
            FileHandle[] var5 = allFolders;
            int numFilesToDelete = allFolders.length;

            int i;
            for(i = 0; i < numFilesToDelete; ++i) {
                FileHandle ref = var5[i];
                FileHandle[] runs = ref.list("run");
                FileHandle[] var10 = runs;
                int var11 = runs.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    FileHandle j = var10[var12];
                    runNames.add(j.name());
                    map.put(j.name(), j);
                }
            }

            int excessFileThreshold = 500;
            numFilesToDelete = runNames.size() - excessFileThreshold;
            if (runNames.size() >= excessFileThreshold) {
                Collections.sort(runNames);

                for(i = 0; i < numFilesToDelete; ++i) {
                    if (map.containsKey(runNames.get(i))) {
                        logger.info("DELETING EXCESS RUN: " + map.get(((String)runNames.get(i)).toString()));
                        ((FileHandle)map.get(runNames.get(i))).delete();
                    }
                }

            }
        }
    }

    public void run() {
        switch(this.type) {
            case UPLOAD_CRASH:
                if (!Settings.isModded) {
                    this.gatherAllDataAndSend(this.death, false, this.monsters);
                }
                break;
            case UPLOAD_METRICS:
                if (!Settings.isModded) {
                    this.gatherAllDataAndSend(this.death, this.trueVictory, this.monsters);
                }
                break;
            default:
                logger.info("Unspecified MetricRequestType: " + this.type.name() + " in run()");
        }

    }

    public static enum MetricRequestType {
        UPLOAD_METRICS,
        UPLOAD_CRASH,
        NONE;

        private MetricRequestType() {
        }
    }
}

