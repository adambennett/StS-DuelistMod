package duelistmod.ui.gameOver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AscensionLevelUpTextEffect;
import com.megacrit.cardcrawl.vfx.AscensionUnlockedTextEffect;
import com.megacrit.cardcrawl.vfx.DeathScreenFloatyEffect;
import duelistmod.DuelistMod;
import duelistmod.enums.DeathType;
import duelistmod.helpers.Util;
import duelistmod.ui.DuelistGameOverScreen;
import com.badlogic.gdx.graphics.Color;
import duelistmod.variables.VictoryDeathScreens;

import java.util.ArrayList;
import java.util.Iterator;

public class DuelistDeathScreen extends DuelistGameOverScreen {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final MonsterGroup monsters;
    private final String deathText;
    private final ArrayList<DeathScreenFloatyEffect> particles;
    private float deathAnimWaitTimer;
    private float deathTextTimer;
    private final Color defeatTextColor;
    private final Color deathTextColor;
    private static final float DEATH_TEXT_Y;
    private boolean defectUnlockedThisRun;
    private DeathType type;
    
    public DuelistDeathScreen(final MonsterGroup m, DeathType type) {
        this.particles = new ArrayList<DeathScreenFloatyEffect>();
        this.type = type;
        this.deathAnimWaitTimer = 1.0f;
        this.deathTextTimer = 5.0f;
        this.defeatTextColor = Color.WHITE.cpy();
        this.deathTextColor = Settings.BLUE_TEXT_COLOR.cpy();
        this.defectUnlockedThisRun = false;
        this.playtime = (long)CardCrawlGame.playtime;
        if (this.playtime < 0L) {
            this.playtime = 0L;
        }
        AbstractDungeon.getCurrRoom().clearEvent();
        resetScoreChecks();
        AbstractDungeon.is_victory = false;
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            c.unhover();
        }
        if (AbstractDungeon.player.stance != null) {
            AbstractDungeon.player.stance.stopIdleSfx();
        }
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.screen = VictoryDeathScreens.DUELIST_DEATH;
        AbstractDungeon.overlayMenu.showBlackScreen(1.0f);
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        AbstractDungeon.isScreenUp = true;
        this.deathText = this.getDeathText();
        this.monsters = m;
        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
            ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
        }
        if (!(DuelistDeathScreen.isVictory = (AbstractDungeon.getCurrRoom() instanceof VictoryRoom))) {
            final PublisherIntegration pubInteg = CardCrawlGame.publisherIntegration;
            String winStreakStatId;
            if (Settings.isBeta) {
                winStreakStatId = AbstractDungeon.player.getWinStreakKey() + "_BETA";
            }
            else {
                winStreakStatId = AbstractDungeon.player.getWinStreakKey();
            }
            pubInteg.setStat(winStreakStatId, 0);
        }
        this.showingStats = false;
        (this.returnButton = new ReturnToMenuButton()).appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[0]);
        if (AbstractDungeon.getCurrRoom() instanceof VictoryRoom) {
            AbstractDungeon.dynamicBanner.appear(DuelistDeathScreen.TEXT[1]);
        }
        else {
            AbstractDungeon.dynamicBanner.appear(this.getDeathBannerText());
        }
        if (Settings.isStandardRun()) {
            if (AbstractDungeon.floorNum >= 16) {
                CardCrawlGame.playerPref.putInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 1);
            }
            else {
                CardCrawlGame.playerPref.putInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 0);
                AbstractDungeon.bossCount = 0;
            }
        }
        CardCrawlGame.music.dispose();
        CardCrawlGame.sound.play("DEATH_STINGER", true);
        String bgmKey = null;
        switch (MathUtils.random(0, 3)) {
            case 0: {
                bgmKey = "STS_DeathStinger_1_v3_MUSIC.ogg";
                break;
            }
            case 1: {
                bgmKey = "STS_DeathStinger_2_v3_MUSIC.ogg";
                break;
            }
            case 2: {
                bgmKey = "STS_DeathStinger_3_v3_MUSIC.ogg";
                break;
            }
            case 3: {
                bgmKey = "STS_DeathStinger_4_v3_MUSIC.ogg";
                break;
            }
        }
        CardCrawlGame.music.playTempBgmInstantly(bgmKey, false);
        if (DuelistDeathScreen.isVictory) {
            this.submitVictoryMetrics();
            if (this.playtime != 0L) {
                StatsScreen.updateVictoryTime(this.playtime);
            }
            StatsScreen.incrementVictory(AbstractDungeon.player.getCharStat());
        }
        else {
            this.submitDefeatMetrics(m);
            StatsScreen.incrementDeath(AbstractDungeon.player.getCharStat());
        }
        if (Settings.isStandardRun() && AbstractDungeon.actNum > 3) {
            StatsScreen.incrementVictoryIfZero(AbstractDungeon.player.getCharStat());
        }
        this.defeatTextColor.a = 0.0f;
        this.deathTextColor.a = 0.0f;
        if (this.playtime != 0L) {
            StatsScreen.incrementPlayTime(this.playtime);
        }
        if (Settings.isStandardRun()) {
            StatsScreen.updateFurthestAscent(AbstractDungeon.floorNum);
        }
        else if (Settings.isDailyRun) {
            StatsScreen.updateHighestDailyScore(AbstractDungeon.floorNum);
        }
        if (SaveHelper.shouldDeleteSave()) {
            SaveAndContinue.deleteSave(AbstractDungeon.player);
        }
        this.calculateUnlockProgress();
        this.createGameOverStats();
        CardCrawlGame.playerPref.flush();
    }

    private void createGameOverStats() {
        this.stats.clear();
        this.stats.add(new GameOverStat(DuelistDeathScreen.TEXT[2] + " (" + AbstractDungeon.floorNum + ")", null, Integer.toString(DuelistDeathScreen.floorPoints)));
        this.stats.add(new GameOverStat(DuelistDeathScreen.TEXT[43] + " (" + CardCrawlGame.monstersSlain + ")", null, Integer.toString(DuelistDeathScreen.monsterPoints)));
        this.stats.add(new GameOverStat(DuelistDeathScreen.EXORDIUM_ELITE.NAME + " (" + CardCrawlGame.elites1Slain + ")", null, Integer.toString(DuelistDeathScreen.elite1Points)));
        if (Settings.isEndless) {
            if (CardCrawlGame.elites2Slain > 0) {
                this.stats.add(new GameOverStat(DuelistDeathScreen.CITY_ELITE.NAME + " (" + CardCrawlGame.elites2Slain + ")", null, Integer.toString(DuelistDeathScreen.elite2Points)));
            }
        }
        else if (CardCrawlGame.dungeon instanceof TheCity || CardCrawlGame.dungeon instanceof TheBeyond || CardCrawlGame.dungeon instanceof TheEnding) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.CITY_ELITE.NAME + " (" + CardCrawlGame.elites2Slain + ")", null, Integer.toString(DuelistDeathScreen.elite2Points)));
        }
        if (Settings.isEndless) {
            if (CardCrawlGame.elites3Slain > 0) {
                this.stats.add(new GameOverStat(DuelistDeathScreen.BEYOND_ELITE.NAME + " (" + CardCrawlGame.elites3Slain + ")", null, Integer.toString(DuelistDeathScreen.elite3Points)));
            }
        }
        else if (CardCrawlGame.dungeon instanceof TheBeyond || CardCrawlGame.dungeon instanceof TheEnding) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.BEYOND_ELITE.NAME + " (" + CardCrawlGame.elites3Slain + ")", null, Integer.toString(DuelistDeathScreen.elite3Points)));
        }
        this.stats.add(new GameOverStat(DuelistDeathScreen.BOSSES_SLAIN.NAME + " (" + AbstractDungeon.bossCount + ")", null, Integer.toString(DuelistDeathScreen.bossPoints)));
        if (DuelistDeathScreen.IS_POOPY) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.POOPY.NAME, DuelistDeathScreen.POOPY.DESCRIPTIONS[0], Integer.toString(-1)));
        }
        if (DuelistDeathScreen.IS_SPEEDSTER) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.SPEEDSTER.NAME, DuelistDeathScreen.SPEEDSTER.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistDeathScreen.IS_LIGHT_SPEED) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.LIGHT_SPEED.NAME, DuelistDeathScreen.LIGHT_SPEED.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (DuelistDeathScreen.IS_HIGHLANDER) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.HIGHLANDER.NAME, DuelistDeathScreen.HIGHLANDER.DESCRIPTIONS[0], Integer.toString(100)));
        }
        if (DuelistDeathScreen.IS_SHINY) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.SHINY.NAME, DuelistDeathScreen.SHINY.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (DuelistDeathScreen.IS_I_LIKE_GOLD) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.I_LIKE_GOLD.NAME + " (" + CardCrawlGame.goldGained + ")", DuelistDeathScreen.I_LIKE_GOLD.DESCRIPTIONS[0], Integer.toString(75)));
        }
        else if (DuelistDeathScreen.IS_RAINING_MONEY) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.RAINING_MONEY.NAME + " (" + CardCrawlGame.goldGained + ")", DuelistDeathScreen.RAINING_MONEY.DESCRIPTIONS[0], Integer.toString(50)));
        }
        else if (DuelistDeathScreen.IS_MONEY_MONEY) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.MONEY_MONEY.NAME + " (" + CardCrawlGame.goldGained + ")", DuelistDeathScreen.MONEY_MONEY.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistDeathScreen.IS_MYSTERY_MACHINE) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.MYSTERY_MACHINE.NAME + " (" + CardCrawlGame.mysteryMachine + ")", DuelistDeathScreen.MYSTERY_MACHINE.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistDeathScreen.IS_FULL_SET > 0) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.COLLECTOR.NAME + " (" + DuelistDeathScreen.IS_FULL_SET + ")", DuelistDeathScreen.COLLECTOR.DESCRIPTIONS[0], Integer.toString(25 * DuelistDeathScreen.IS_FULL_SET)));
        }
        if (DuelistDeathScreen.IS_PAUPER) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.PAUPER.NAME, DuelistDeathScreen.PAUPER.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (DuelistDeathScreen.IS_LIBRARY) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.LIBRARIAN.NAME, DuelistDeathScreen.LIBRARIAN.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistDeathScreen.IS_ENCYCLOPEDIA) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.ENCYCLOPEDIAN.NAME, DuelistDeathScreen.ENCYCLOPEDIAN.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (DuelistDeathScreen.IS_STUFFED) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.STUFFED.NAME, DuelistDeathScreen.STUFFED.DESCRIPTIONS[0], Integer.toString(50)));
        }
        else if (DuelistDeathScreen.IS_WELL_FED) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.WELL_FED.NAME, DuelistDeathScreen.WELL_FED.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistDeathScreen.IS_CURSES) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.CURSES.NAME, DuelistDeathScreen.CURSES.DESCRIPTIONS[0], Integer.toString(100)));
        }
        if (DuelistDeathScreen.IS_ON_MY_OWN) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.ON_MY_OWN_TERMS.NAME, DuelistDeathScreen.ON_MY_OWN_TERMS.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (CardCrawlGame.champion > 0) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.CHAMPION.NAME + " (" + CardCrawlGame.champion + ")", DuelistDeathScreen.CHAMPION.DESCRIPTIONS[0], Integer.toString(25 * CardCrawlGame.champion)));
        }
        if (CardCrawlGame.perfect >= 3) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.BEYOND_PERFECT.NAME, DuelistDeathScreen.BEYOND_PERFECT.DESCRIPTIONS[0], Integer.toString(200)));
        }
        else if (CardCrawlGame.perfect > 0) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.PERFECT.NAME + " (" + CardCrawlGame.perfect + ")", DuelistDeathScreen.PERFECT.DESCRIPTIONS[0], Integer.toString(50 * CardCrawlGame.perfect)));
        }
        if (CardCrawlGame.overkill) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.OVERKILL.NAME, DuelistDeathScreen.OVERKILL.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (CardCrawlGame.combo) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.COMBO.NAME, DuelistDeathScreen.COMBO.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (AbstractDungeon.isAscensionMode) {
            this.stats.add(new GameOverStat(DuelistDeathScreen.ASCENSION.NAME + " (" + AbstractDungeon.ascensionLevel + ")", DuelistDeathScreen.ASCENSION.DESCRIPTIONS[0], Integer.toString(DuelistDeathScreen.ascensionPoints)));
        }
        if (Util.getChallengeLevel() > 0) {
            this.stats.add(new GameOverStat("Challenge (" + Util.getChallengeLevel() + ")", "", Integer.toString(Util.getChallengeLevel() * 3)));
        }
        if (DuelistMod.summonRunCount > 0) {
            this.stats.add(new GameOverStat("Summons: " + DuelistMod.summonRunCount, "", Integer.toString(DuelistMod.summonRunCount)));
        }
        if (DuelistMod.tribRunCount > 0) {
            this.stats.add(new GameOverStat("Tributes: " + DuelistMod.tribRunCount, "", Integer.toString(DuelistMod.tribRunCount * 2)));
        }
        if (DuelistMod.megatypeTributesThisRun > 0) {
            this.stats.add(new GameOverStat("Megatype Tributes: " + DuelistMod.megatypeTributesThisRun, "", Integer.toString(DuelistMod.megatypeTributesThisRun * 3)));
        }
        if (DuelistMod.resummonsThisRun > 0) {
            this.stats.add(new GameOverStat("Resummons: " + DuelistMod.resummonsThisRun, "", Integer.toString(DuelistMod.resummonsThisRun)));
        }
        if (DuelistMod.uniqueMonstersThisRun.size() > 20) {
            this.stats.add(new GameOverStat("Unique monsters: " + DuelistMod.uniqueMonstersThisRun.size(), "Your deck contains at least 20 unique Monsters.", Integer.toString(100)));
        }
        if (DuelistMod.uniqueSpellsThisRun.size() > 20) {
            this.stats.add(new GameOverStat("Unique spells: " + DuelistMod.uniqueMonstersThisRun.size(), "Your deck contains at least 20 unique Spells.", Integer.toString(100)));
        }
        if (DuelistMod.uniqueTrapsThisRun.size() > 15) {
            this.stats.add(new GameOverStat("Unique traps: " + DuelistMod.uniqueMonstersThisRun.size(), "Your deck contains at least 20 unique Traps.", Integer.toString(100)));
        }
        this.stats.add(new GameOverStat());
        this.stats.add(new GameOverStat(DuelistDeathScreen.TEXT[6], null, Integer.toString(this.score)));
    }

    private void submitDefeatMetrics(final MonsterGroup m) {
        if (m != null && !m.areMonstersDead() && !m.areMonstersBasicallyDead()) {
            CardCrawlGame.metricData.addEncounterData();
        }
        final Metrics metrics = new Metrics();
        metrics.gatherAllDataAndSave(true, false, m);
        metrics.setValues(true, false, m, Metrics.MetricRequestType.UPLOAD_METRICS);
        final Thread t = new Thread(metrics);
        t.setName("Metrics");
        t.start();
    }

    @Override
    protected void submitVictoryMetrics() {
        final Metrics metrics = new Metrics();
        metrics.gatherAllDataAndSave(false, false, null);
        metrics.setValues(false, false, null, Metrics.MetricRequestType.UPLOAD_METRICS);
        final Thread t = new Thread(metrics);
        t.start();

        if (Settings.isStandardRun()) {
            StatsScreen.updateFurthestAscent(AbstractDungeon.floorNum);
        }
        if (SaveHelper.shouldDeleteSave()) {
            SaveAndContinue.deleteSave(AbstractDungeon.player);
        }
    }

    private String getDeathBannerText() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add(DuelistDeathScreen.TEXT[7]);
        list.add(DuelistDeathScreen.TEXT[8]);
        list.add(DuelistDeathScreen.TEXT[9]);
        list.add(DuelistDeathScreen.TEXT[10]);
        list.add(DuelistDeathScreen.TEXT[11]);
        list.add(DuelistDeathScreen.TEXT[12]);
        list.add(DuelistDeathScreen.TEXT[13]);
        list.add(DuelistDeathScreen.TEXT[14]);
        return list.get(MathUtils.random(list.size() - 1));
    }

    private String getDeathText() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add(DuelistDeathScreen.TEXT[15]);
        list.add(DuelistDeathScreen.TEXT[16]);
        list.add(DuelistDeathScreen.TEXT[17]);
        list.add(DuelistDeathScreen.TEXT[18]);
        list.add(DuelistDeathScreen.TEXT[19]);
        list.add(DuelistDeathScreen.TEXT[20]);
        list.add(DuelistDeathScreen.TEXT[21]);
        list.add(DuelistDeathScreen.TEXT[22]);
        list.add(DuelistDeathScreen.TEXT[23]);
        list.add(DuelistDeathScreen.TEXT[24]);
        list.add(DuelistDeathScreen.TEXT[25]);
        list.add(DuelistDeathScreen.TEXT[26]);
        list.add(DuelistDeathScreen.TEXT[27]);
        list.add(DuelistDeathScreen.TEXT[28]);
        list.add(DuelistDeathScreen.TEXT[29]);
        if (AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
            list.add("...");
        }
        return list.get(MathUtils.random(list.size() - 1));
    }

    public void hide() {
        this.returnButton.hide();
        AbstractDungeon.dynamicBanner.hide();
    }

    public void reopen() {
        this.reopen(false);
    }

    public void reopen(final boolean fromVictoryUnlock) {
        AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_DEATH;
        this.statsTimer = 0.5f;
        if (DuelistDeathScreen.isVictory) {
            AbstractDungeon.dynamicBanner.appearInstantly(DuelistDeathScreen.TEXT[1]);
        }
        else {
            AbstractDungeon.dynamicBanner.appearInstantly(DuelistDeathScreen.TEXT[30]);
        }
        AbstractDungeon.overlayMenu.showBlackScreen(1.0f);
        if (fromVictoryUnlock) {
            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[31]);
        }
        else if (!this.showingStats) {
            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[32]);
        }
        else if (this.unlockBundle == null) {
            if (!DuelistDeathScreen.isVictory) {
                if (UnlockTracker.isCharacterLocked("The Silent") || (UnlockTracker.isCharacterLocked("Defect") && AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) || this.willWatcherUnlock()) {
                    this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[40]);
                }
                else {
                    this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[34]);
                }
            }
            else {
                this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[35]);
            }
        }
        else {
            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[36]);
        }
    }

    private boolean willWatcherUnlock() {
        return !this.defectUnlockedThisRun && UnlockTracker.isCharacterLocked("Watcher") && !UnlockTracker.isCharacterLocked("Defect") && (UnlockTracker.isAchievementUnlocked("RUBY") || UnlockTracker.isAchievementUnlocked("EMERALD") || UnlockTracker.isAchievementUnlocked("SAPPHIRE"));
    }

    public void update() {
        if (Settings.isDebug && InputHelper.justClickedRight) {
            UnlockTracker.resetUnlockProgress(AbstractDungeon.player.chosenClass);
        }
        this.updateControllerInput();
        this.returnButton.update();
        if (this.returnButton.hb.clicked || (this.returnButton.show && CInputActionSet.select.isJustPressed())) {
            CInputActionSet.topPanel.unpress();
            if (Settings.isControllerMode) {
                Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            }
            this.returnButton.hb.clicked = false;
            if (!this.showingStats) {
                this.showingStats = true;
                this.statsTimer = 0.5f;
                this.returnButton = new ReturnToMenuButton();
                this.updateAscensionProgress();
                if (this.unlockBundle == null) {
                    if (!DuelistDeathScreen.isVictory) {
                        if (UnlockTracker.isCharacterLocked("The Silent") || (UnlockTracker.isCharacterLocked("Defect") && AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) || this.willWatcherUnlock()) {
                            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[40]);
                        }
                        else {
                            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[37]);
                        }
                    }
                    else {
                        this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[39]);
                    }
                }
                else {
                    this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[40]);
                }
            }
            else if (this.unlockBundle != null) {
                AbstractDungeon.gUnlockScreen.open(this.unlockBundle, false);
                AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_DEATH;
                AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
                this.unlockBundle = null;
                if (UnlockTracker.isCharacterLocked("The Silent")) {
                    this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistDeathScreen.TEXT[40]);
                }
                else {
                    this.returnButton.label = DuelistDeathScreen.TEXT[37];
                }
            }
            else if (DuelistDeathScreen.isVictory) {
                this.returnButton.hide();
                if (AbstractDungeon.unlocks.isEmpty() || Settings.isDemo) {
                    if (Settings.isDemo || Settings.isDailyRun) {
                        CardCrawlGame.startOver();
                    }
                    else {
                        CardCrawlGame.playCreditsBgm = false;
                        CardCrawlGame.startOverButShowCredits();
                    }
                }
                else {
                    AbstractDungeon.unlocks.clear();
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                    CardCrawlGame.playCreditsBgm = false;
                    CardCrawlGame.startOverButShowCredits();
                }
            }
            else {
                this.returnButton.hide();
                if (AbstractDungeon.unlocks.isEmpty() || Settings.isDemo || Settings.isDailyRun || Settings.isTrial) {
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                    CardCrawlGame.startOver();
                }
                else {
                    AbstractDungeon.unlocks.clear();
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                    CardCrawlGame.playCreditsBgm = false;
                    CardCrawlGame.startOverButShowCredits();
                }
            }
        }
        this.updateStatsScreen();
        if (this.deathAnimWaitTimer != 0.0f) {
            this.deathAnimWaitTimer -= Gdx.graphics.getDeltaTime();
            if (this.deathAnimWaitTimer < 0.0f) {
                this.deathAnimWaitTimer = 0.0f;
                AbstractDungeon.player.playDeathAnimation();
            }
        }
        else {
            this.deathTextTimer -= Gdx.graphics.getDeltaTime();
            if (this.deathTextTimer < 0.0f) {
                this.deathTextTimer = 0.0f;
            }
            this.deathTextColor.a = Interpolation.fade.apply(0.0f, 1.0f, 1.0f - this.deathTextTimer / 5.0f);
            this.defeatTextColor.a = Interpolation.fade.apply(0.0f, 1.0f, 1.0f - this.deathTextTimer / 5.0f);
        }
        if (this.monsters != null) {
            this.monsters.update();
            this.monsters.updateAnimations();
        }
        if (this.particles.size() < 50.0f) {
            this.particles.add(new DeathScreenFloatyEffect());
        }
    }

    private void updateControllerInput() {
        if (!Settings.isControllerMode || AbstractDungeon.topPanel.selectPotionMode || !AbstractDungeon.topPanel.potionUi.isHidden || AbstractDungeon.player.viewingRelics) {
            return;
        }
        boolean anyHovered = false;
        int index = 0;
        if (this.stats != null) {
            for (final GameOverStat s : this.stats) {
                if (s.hb.hovered) {
                    anyHovered = true;
                    break;
                }
                ++index;
            }
        }
        if (!anyHovered) {
            index = -1;
        }
        if (CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) {
            --index;
            if (this.stats.size() > 10) {
                int numItemsInRightColumn = (this.stats.size() - 2) / 2;
                if (this.stats.size() % 2 == 0) {
                    --numItemsInRightColumn;
                }
                if (index == numItemsInRightColumn) {
                    index = this.stats.size() - 1;
                }
                else if (index < 0) {
                    index = this.stats.size() - 1;
                }
                else if (index == this.stats.size() - 2) {
                    --index;
                }
            }
            else if (index < 0) {
                index = this.stats.size() - 1;
            }
            else if (index == this.stats.size() - 2) {
                --index;
            }
            CInputHelper.setCursor(this.stats.get(index).hb);
        }
        else if (CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) {
            if (index == -1) {
                index = 0;
                CInputHelper.setCursor(this.stats.get(index).hb);
                return;
            }
            ++index;
            if (this.stats.size() > 10) {
                int numItemsInLeftColumn = (this.stats.size() - 2) / 2;
                if (this.stats.size() % 2 != 0) {
                    ++numItemsInLeftColumn;
                }
                if (index == numItemsInLeftColumn) {
                    index = this.stats.size() - 1;
                }
            }
            else {
                if (index > this.stats.size() - 1) {
                    index = 0;
                }
                if (index == this.stats.size() - 2) {
                    ++index;
                }
            }
            if (index > this.stats.size() - 3) {
                index = this.stats.size() - 1;
            }
            CInputHelper.setCursor(this.stats.get(index).hb);
        }
        else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
            if (this.stats.size() > 10) {
                int numItemsInLeftColumn = (this.stats.size() - 2) / 2;
                if (this.stats.size() % 2 != 0) {
                    ++numItemsInLeftColumn;
                }
                if (index < numItemsInLeftColumn - 1) {
                    index += numItemsInLeftColumn;
                }
                else if (index == numItemsInLeftColumn - 1) {
                    if (this.stats.size() % 2 != 0) {
                        index += numItemsInLeftColumn - 1;
                    }
                    else {
                        index += numItemsInLeftColumn;
                    }
                }
                else if (index >= numItemsInLeftColumn && index < this.stats.size() - 2) {
                    index -= numItemsInLeftColumn;
                }
            }
            if (index > this.stats.size() - 1) {
                index = this.stats.size() - 1;
            }
            if (index != -1) {
                CInputHelper.setCursor(this.stats.get(index).hb);
            }
        }
    }

    private void updateAscensionProgress() {
        if ((DuelistDeathScreen.isVictory || AbstractDungeon.actNum >= 4) && AbstractDungeon.isAscensionMode && Settings.isStandardRun() && AbstractDungeon.ascensionLevel < 20 && StatsScreen.isPlayingHighestAscension(AbstractDungeon.player.getPrefs())) {
            StatsScreen.incrementAscension(AbstractDungeon.player.getCharStat());
            AbstractDungeon.topLevelEffects.add(new AscensionLevelUpTextEffect());
        }
        else if (!AbstractDungeon.ascensionCheck && UnlockTracker.isAscensionUnlocked(AbstractDungeon.player) && !Settings.seedSet) {
            AbstractDungeon.topLevelEffects.add(new AscensionUnlockedTextEffect());
        }
    }

    private void updateStatsScreen() {
        if (this.showingStats) {
            this.progressBarAlpha = MathHelper.slowColorLerpSnap(this.progressBarAlpha, 1.0f);
            this.statsTimer -= Gdx.graphics.getDeltaTime();
            if (this.statsTimer < 0.0f) {
                this.statsTimer = 0.0f;
            }
            this.returnButton.y = Interpolation.pow3In.apply(Settings.HEIGHT * 0.1f, Settings.HEIGHT * 0.15f, this.statsTimer / 0.5f);
            AbstractDungeon.dynamicBanner.y = Interpolation.pow3In.apply(Settings.HEIGHT / 2.0f + 320.0f * Settings.scale, DynamicBanner.Y, this.statsTimer / 0.5f);
            for (final GameOverStat i : this.stats) {
                i.update();
            }
            if (this.statAnimateTimer < 0.0f) {
                boolean allStatsShown = true;
                for (final GameOverStat j : this.stats) {
                    if (j.hidden) {
                        j.hidden = false;
                        this.statAnimateTimer = 0.1f;
                        allStatsShown = false;
                        break;
                    }
                }
                if (allStatsShown) {
                    this.animateProgressBar();
                }
            }
            else {
                this.statAnimateTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void render(final SpriteBatch sb) {
        final Iterator<DeathScreenFloatyEffect> i = this.particles.iterator();
        while (i.hasNext()) {
            final DeathScreenFloatyEffect e = i.next();
            if (e.renderBehind) {
                e.render(sb);
            }
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }
        AbstractDungeon.player.render(sb);
        if (this.monsters != null) {
            this.monsters.render(sb);
        }
        sb.setBlendFunction(770, 1);
        for (DeathScreenFloatyEffect e : this.particles) {
            if (!e.renderBehind) {
                e.render(sb);
            }
        }
        sb.setBlendFunction(770, 771);
        this.renderStatsScreen(sb);
        if (!this.showingStats && !DuelistDeathScreen.isVictory) {
            FontHelper.renderFontCentered(sb, FontHelper.topPanelInfoFont, this.deathText, Settings.WIDTH / 2.0f, DuelistDeathScreen.DEATH_TEXT_Y, this.deathTextColor);
        }
        this.returnButton.render(sb);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DeathScreen");
        TEXT = DuelistDeathScreen.uiStrings.TEXT;
        DEATH_TEXT_Y = Settings.HEIGHT - 360.0f * Settings.scale;
    }
}
