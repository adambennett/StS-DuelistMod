package duelistmod.metrics;

import java.awt.*;
import java.net.URI;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import com.evacipated.cardcrawl.modthespire.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import duelistmod.DuelistMod;
import duelistmod.enums.*;
import duelistmod.helpers.*;
import duelistmod.metrics.builders.*;
import okhttp3.*;
import org.apache.logging.log4j.core.util.UuidUtil;

public class MetricsHelper 
{
	private static final boolean LOCAL = DuelistMod.metricsMode == MetricsMode.LOCAL;

	public static final String BASE_API_URL 	     = LOCAL        ? "http://localhost:8124/" : "https://www.server.duelistmetrics.com/";
	public static final String BASE_SITE_URL		 = LOCAL		? "http://localhost:4200/" : "https://www.duelistmetrics.com/";
	public static final String ENDPOINT_RUN_UPLOAD   = BASE_API_URL + "runupload-v4";
	public static final String ENDPOINT_MOD_UPLOAD   = BASE_API_URL + "dataupload";
	public static final String ENDPOINT_ORB_UPLOAD   = BASE_API_URL + "orbInfoUpload";
	public static final String ENDPOINT_MOD_VERSIONS = BASE_API_URL + "allModuleVersions";
	public static final String ENDPOINT_TIER_SCORES  = BASE_API_URL + "tierScores";
	public static final String ENDPOINT_CARDS 		 = BASE_SITE_URL + "cards/";
	public static final String ENDPOINT_PLAYER_RUNS  = BASE_SITE_URL + "#/runs/view-runs/";
	public static final String ENDPOINT_EXCEPTION_HANDLER = BASE_API_URL + "logException";
	public static final String ENDPOINT_LOCAL_DUELIST_VERSIONS_CHECK = BASE_API_URL + "allTrackedDuelistVersions";

	public static HashMap<Object, Object> setupCustomMetrics(HashMap<Object, Object> par, boolean duelist) {
		List<MiniModBundle> playerModList = new ArrayList<>();
		for (ModInfo modInfo : Loader.MODINFOS) {
			MiniModBundle bndle = new MiniModBundleBuilder()
					.setID(modInfo.ID)
					.setModVersion(modInfo.ModVersion.getValue())
					.setName(modInfo.Name)
					.setAuthors(Arrays.asList(modInfo.Authors))
					.createMiniModBundle();
			playerModList.add(bndle);
		}
		if (DuelistMod.persistentDuelistData.MetricsSettings.getAllowLocaleUpload()) {
			par.put("country", Locale.getDefault().getCountry());
			par.put("lang", Locale.getDefault().getLanguage());
		}

		setupUUID();

		par.put("modList", playerModList);
		par.put("unique_player_id", DuelistMod.metricsUUID);
		par.put("duelistmod_version", DuelistMod.version);
		par.put("duelist_score", DuelistMod.trueDuelistScore);
		par.put("duelist_score_current_version", DuelistMod.trueVersionScore);
		par.put("run_uuid", DuelistMod.runUUID);
		if (duelist) {
			par.put("starting_deck", StartingDeck.currentDeck.getDeckName());
			par.put("challenge_mode", DuelistMod.playingChallenge);
			par.put("number_of_spells", DuelistMod.spellsObtained);
			par.put("number_of_traps", DuelistMod.trapsObtained);
			par.put("number_of_monsters", DuelistMod.monstersObtained);
			par.put("total_synergy_tributes", DuelistMod.synergyTributesRan);
			par.put("highest_max_summons", DuelistMod.highestMaxSummonsObtained);
			par.put("number_of_resummons", DuelistMod.resummonsThisRun);
			par.put("number_of_megatype_tributes", DuelistMod.megatypeTributesThisRun);
			par.put("number_of_summons", DuelistMod.summonRunCount);
			par.put("number_of_tributes", DuelistMod.tribRunCount);
			par.put("playing_as_kaiba", !DuelistMod.selectedCharacterModel.isYugi());
			par.put("customized_card_pool", DuelistMod.poolIsCustomized);
			par.put("challenge_level", DuelistMod.challengeLevel);
			par.put("character_model", DuelistMod.selectedCharacterModel.getDisplayName());
			par.put("duelist_card_choices", getNamesForCardChoices());
		}
		return par;
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

	private static void setupUUID() {
		setupUUID(null);
	}

	public static void setupUUID(SpireConfig config) {
		if (DuelistMod.metricsUUID == null || DuelistMod.metricsUUID.equals("")) {
			DuelistMod.metricsUUID = UuidUtil.getTimeBasedUuid().toString();
			try {
				config = config == null
						? new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults)
						: config;
				config.setString(DuelistMod.PROP_METRICS_UUID, DuelistMod.metricsUUID);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static List<String> getAllTrackedDuelistVersions() {
		List<String> output = new ArrayList<>();
		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					.connectTimeout(5, TimeUnit.MINUTES)
					.readTimeout(5, TimeUnit.MINUTES)
					.writeTimeout(5, TimeUnit.MINUTES)
					.build();
			Request request = new Request.Builder()
					.url(ENDPOINT_LOCAL_DUELIST_VERSIONS_CHECK)
					.method("GET", null)
					.addHeader("Content-Type", "application/json;charset=UTF-8")
					.build();
			Response response = client.newCall(request).execute();
			ObjectMapper objectMapper = new ObjectMapper();
			output = objectMapper
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.readValue(response.body().string(), new TypeReference<List<String>>(){});
			Util.log("Got forced list of properly-tracked DuelistMod versions.");
			response.close();
		} catch (Exception ignored) {}
		return output;
	}

	public static Map<String, List<String>> getAllModuleVersions() {

		long start = System.currentTimeMillis();
		if (!ModuleVersionCacheService.needToCheckServerForModules()) {
			Map<String, List<String>> output = ModuleVersionCacheService.deserializeModulesFromCache();
			if (output != null) {
				long end = System.currentTimeMillis();
				Util.log("Loaded modules from disk");
				Util.log("Execution time to retrieve modules from disk: " + (end - start) + "ms");
				return output;
			} else {
				ModuleVersionCacheService.flushModuleCache();
			}
		}

		List<String> out = getStrings();
		Map<String, List<String>> output = new HashMap<>();
		if (out.size() == 1 && out.get(0).equals("SERVER IS DOWN")) {
			output.put("SERVER IS DOWN", new ArrayList<>());
			return output;
		}
		for (String module : out) {
			String[] splice = module.split(",");
			String mod = splice[0];
			String ver = splice[1];
			List<String> newArr;
			if (output.containsKey(mod)) {
				newArr = output.get(mod);
			} else {
				newArr = new ArrayList<>();
			}
			newArr.add(ver);
			output.put(mod, newArr);
		}

		String cacheJson = ModuleVersionCacheService.serializeModules(output);
		boolean updatedCache = ModuleVersionCacheService.updateModuleCacheData(cacheJson);
		Util.log("Updated module version cache: " + updatedCache);

		long end = System.currentTimeMillis();
		Util.log("Execution time to retrieve modules from server: " + (end - start) + "ms");
		return output;
	}

	public static Map<String, Map<String, Map<Integer, Integer>>> getTierScores() {
		Map<String, Map<String, Map<Integer, Integer>>> out;
		long start = System.currentTimeMillis();

		if (!TierScoreCacheService.needToCheckServerForScores()) {
			out = TierScoreCacheService.deserializeTierScoresFromCache();
			if (out != null) {
				long end = System.currentTimeMillis();
				Util.log("Loaded tier scores from disk");
				Util.log("Execution time to retrieve scores from disk: " + (end - start) + "ms");
				return out;
			} else {
				TierScoreCacheService.flushTierScoreCache();
			}
		}

		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					.connectTimeout(5, TimeUnit.MINUTES)
					.readTimeout(5, TimeUnit.MINUTES)
					.writeTimeout(5, TimeUnit.MINUTES)
					.build();
			Request request = new Request.Builder()
					.url(ENDPOINT_TIER_SCORES)
					.method("GET", null)
					.addHeader("Content-Type", "application/json;charset=UTF-8")
					.build();
			Response response = client.newCall(request).execute();
			if (response.code() == 503) {
				DuelistMod.logger.error("Tier scores GET request error! Code 503. It seems the Heroku metrics server is down at the moment. Check back at the beginning of next month.");
				return new HashMap<>();
			}
			ObjectMapper objectMapper = new ObjectMapper();
			out = objectMapper
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.readValue(response.body().string(), new TypeReference<Map<String, Map<String, Map<Integer, Integer>>>>(){});
			Util.log("Got tier scores from server properly.");
			response.close();
			try {
				String scoreJson = TierScoreCacheService.serializeTierScores(out);
				boolean updated = TierScoreCacheService.updateTierScoreCacheData(scoreJson);
				Util.log("Updated local tier score cache: " + updated);
			} catch (Exception ignored) {}
			long end = System.currentTimeMillis();
			Util.log("Execution time to retrieve scores from the server: " + (end - start) + "ms");
			return out;
		} catch (Exception ex) {
			DuelistMod.logger.error("Tier scores GET request error! Falling back to attempt to load scores from disk...\n" + Arrays.toString(ex.getStackTrace()));
			try {
				out = TierScoreCacheService.deserializeTierScoresFromCache();
				if (out != null) {
					Util.log("Tier scores were able to be loaded from the disk.");
					return out;
				}
				Util.log("Tier scores could not be loaded from the server or from the disk. Scores will not be enabled.");
			} catch (Exception e) {
				Util.log("Tier scores could not be loaded from the server or from the disk. Scores will not be enabled.");
			}
			return new HashMap<>();
		}
	}

	public static void openPlayerRuns(boolean playSound) {
		if (playSound) {
			CardCrawlGame.sound.play("UI_CLICK_1");
		}
		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) && DuelistMod.metricsUUID != null) {
				Desktop.getDesktop().browse(new URI(MetricsHelper.ENDPOINT_PLAYER_RUNS + DuelistMod.metricsUUID));
			} else {
				Util.log("Unknown error trying to open URL");
			}
		} catch (Exception ex) {
			Util.log("Could not open default system browser.");
		}
	}

	private static List<String> getStrings() {
		List<String> output = new ArrayList<>();
		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					.connectTimeout(5, TimeUnit.MINUTES)
					.readTimeout(5, TimeUnit.MINUTES)
					.writeTimeout(5, TimeUnit.MINUTES)
					.build();
			Request request = new Request.Builder()
					.url(ENDPOINT_MOD_VERSIONS)
					.method("GET", null)
					.addHeader("Content-Type", "application/json;charset=UTF-8")
					.build();
			Response response = client.newCall(request).execute();
			if (response.code() == 503) {
				DuelistMod.logger.error("Metrics module versions GET request error! Code 503. It seems the Heroku metrics server is down at the moment. Check back at the beginning of next month.");
				output.add("SERVER IS DOWN");
				return output;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			output = objectMapper
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.readValue(response.body().string(), new TypeReference<List<String>>(){});
			Util.log("Got module versions from server properly.");
			response.close();
		} catch (Exception ex) {
			DuelistMod.logger.error("Metrics module versions GET request error!", ex);
			output.add("SERVER IS DOWN");
			return output;
		}
		return output;
	}

	private static String getPoolFillType(int index)
	{
		switch (index)
		{
			case 0:
				return "Deck + Basic (Default)";
			case 1:
				return "Deck Only";
			case 2:
				return "Basic Only";
			case 3:
				return "Deck + Basic + 1 Random";
			case 4:
				return "Deck + 1 Random";
			case 5:
				return "Basic + 1 Random";
			case 6:
				return "Basic + Deck + 2 Random";
			case 7:
				return "2 Random";
			case 8:
				return "Deck + 2 Random";
			case 9:
				return "ALL Cards";
			default:
				return "unknown pool fill type";
		}
	}
}
