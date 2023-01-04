package duelistmod.ui.gameOver;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.AscensionLevelUpTextEffect;
import com.megacrit.cardcrawl.vfx.AscensionUnlockedTextEffect;
import com.megacrit.cardcrawl.vfx.scene.DefectVictoryEyesEffect;
import com.megacrit.cardcrawl.vfx.scene.DefectVictoryNumberEffect;
import com.megacrit.cardcrawl.vfx.scene.IroncladVictoryFlameEffect;
import com.megacrit.cardcrawl.vfx.scene.SilentVictoryStarEffect;
import com.megacrit.cardcrawl.vfx.scene.SlowFireParticleEffect;
import com.megacrit.cardcrawl.vfx.scene.WatcherVictoryEffect;
import duelistmod.DuelistMod;
import duelistmod.metrics.HerokuMetrics;
import duelistmod.ui.DuelistGameOverScreen;
import duelistmod.variables.VictoryDeathScreens;

import java.util.ArrayList;
import java.util.Iterator;

public class DuelistVictoryScreen extends DuelistGameOverScreen {

    private final MonsterGroup monsters;
    private final ArrayList<AbstractGameEffect> effect;
    private float effectTimer;
    private float effectTimer2;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public DuelistVictoryScreen(final MonsterGroup monsters) {
        this.effect = new ArrayList<>();
        this.effectTimer = 0.0f;
        this.effectTimer2 = 0.0f;
        DuelistVictoryScreen.isVictory = true;
        this.playtime = (long) CardCrawlGame.playtime;
        if (this.playtime < 0L) {
            this.playtime = 0L;
        }
        AbstractDungeon.getCurrRoom().clearEvent();
        resetScoreChecks();
        AbstractDungeon.is_victory = true;
        AbstractDungeon.player.drawX = Settings.WIDTH / 2.0f;
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.screen = VictoryDeathScreens.DUELIST_VICTORY;
        AbstractDungeon.overlayMenu.showBlackScreen(1.0f);
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        AbstractDungeon.isScreenUp = true;
        this.monsters = monsters;
        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
            ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
        }
        this.showingStats = false;
        (this.returnButton = new ReturnToMenuButton()).appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistVictoryScreen.TEXT[0]);
        AbstractDungeon.dynamicBanner.appear(DuelistVictoryScreen.TEXT[12]);
        if (Settings.isStandardRun()) {
            CardCrawlGame.playerPref.putInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 1);
        }
        this.submitVictoryMetrics();
        if (this.playtime != 0L) {
            StatsScreen.updateVictoryTime(this.playtime);
        }
        StatsScreen.incrementVictory(AbstractDungeon.player.getCharStat());
        StatsScreen.incrementAscension(AbstractDungeon.player.getCharStat());
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
        if (!Settings.isEndless) {
            this.uploadToSteamLeaderboards();
        }
        this.createGameOverStats();
        CardCrawlGame.playerPref.flush();;
    }


    private void createGameOverStats() {
        this.stats.clear();
        this.stats.add(new GameOverStat(DuelistVictoryScreen.TEXT[1] + " (" + AbstractDungeon.floorNum + ")", null, Integer.toString(DuelistVictoryScreen.floorPoints)));
        this.stats.add(new GameOverStat(DuelistVictoryScreen.TEXT[8] + " (" + CardCrawlGame.monstersSlain + ")", null, Integer.toString(DuelistVictoryScreen.monsterPoints)));
        this.stats.add(new GameOverStat(DuelistVictoryScreen.EXORDIUM_ELITE.NAME + " (" + CardCrawlGame.elites1Slain + ")", null, Integer.toString(DuelistVictoryScreen.elite1Points)));
        if (Settings.isEndless) {
            if (CardCrawlGame.elites2Slain > 0) {
                this.stats.add(new GameOverStat(DuelistVictoryScreen.CITY_ELITE.NAME + " (" + CardCrawlGame.elites2Slain + ")", null, Integer.toString(DuelistVictoryScreen.elite2Points)));
            }
        }
        else if (CardCrawlGame.dungeon instanceof TheCity || CardCrawlGame.dungeon instanceof TheBeyond || CardCrawlGame.dungeon instanceof TheEnding) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.CITY_ELITE.NAME + " (" + CardCrawlGame.elites2Slain + ")", null, Integer.toString(DuelistVictoryScreen.elite2Points)));
        }
        if (Settings.isEndless) {
            if (CardCrawlGame.elites3Slain > 0) {
                this.stats.add(new GameOverStat(DuelistVictoryScreen.BEYOND_ELITE.NAME + " (" + CardCrawlGame.elites3Slain + ")", null, Integer.toString(DuelistVictoryScreen.elite3Points)));
            }
        }
        else if (CardCrawlGame.dungeon instanceof TheBeyond || CardCrawlGame.dungeon instanceof TheEnding) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.BEYOND_ELITE.NAME + " (" + CardCrawlGame.elites3Slain + ")", null, Integer.toString(DuelistVictoryScreen.elite3Points)));
        }
        this.stats.add(new GameOverStat(DuelistVictoryScreen.BOSSES_SLAIN.NAME + " (" + AbstractDungeon.bossCount + ")", null, Integer.toString(DuelistVictoryScreen.bossPoints)));
        if (DuelistVictoryScreen.IS_POOPY) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.POOPY.NAME, DuelistVictoryScreen.POOPY.DESCRIPTIONS[0], Integer.toString(-1)));
        }
        if (DuelistVictoryScreen.IS_SPEEDSTER) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.SPEEDSTER.NAME, DuelistVictoryScreen.SPEEDSTER.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistVictoryScreen.IS_LIGHT_SPEED) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.LIGHT_SPEED.NAME, DuelistVictoryScreen.LIGHT_SPEED.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (DuelistVictoryScreen.IS_HIGHLANDER) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.HIGHLANDER.NAME, DuelistVictoryScreen.HIGHLANDER.DESCRIPTIONS[0], Integer.toString(100)));
        }
        if (DuelistVictoryScreen.IS_SHINY) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.SHINY.NAME, DuelistVictoryScreen.SHINY.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (DuelistVictoryScreen.IS_I_LIKE_GOLD) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.I_LIKE_GOLD.NAME + " (" + CardCrawlGame.goldGained + ")", DuelistVictoryScreen.I_LIKE_GOLD.DESCRIPTIONS[0], Integer.toString(75)));
        }
        else if (DuelistVictoryScreen.IS_RAINING_MONEY) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.RAINING_MONEY.NAME + " (" + CardCrawlGame.goldGained + ")", DuelistVictoryScreen.RAINING_MONEY.DESCRIPTIONS[0], Integer.toString(50)));
        }
        else if (DuelistVictoryScreen.IS_MONEY_MONEY) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.MONEY_MONEY.NAME + " (" + CardCrawlGame.goldGained + ")", DuelistVictoryScreen.MONEY_MONEY.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistVictoryScreen.IS_MYSTERY_MACHINE) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.MYSTERY_MACHINE.NAME + " (" + CardCrawlGame.mysteryMachine + ")", DuelistVictoryScreen.MYSTERY_MACHINE.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistVictoryScreen.IS_FULL_SET > 0) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.COLLECTOR.NAME + " (" + DuelistVictoryScreen.IS_FULL_SET + ")", DuelistVictoryScreen.COLLECTOR.DESCRIPTIONS[0], Integer.toString(25 * DuelistVictoryScreen.IS_FULL_SET)));
        }
        if (DuelistVictoryScreen.IS_PAUPER) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.PAUPER.NAME, DuelistVictoryScreen.PAUPER.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (DuelistVictoryScreen.IS_LIBRARY) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.LIBRARIAN.NAME, DuelistVictoryScreen.LIBRARIAN.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistVictoryScreen.IS_ENCYCLOPEDIA) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.ENCYCLOPEDIAN.NAME, DuelistVictoryScreen.ENCYCLOPEDIAN.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (DuelistVictoryScreen.IS_STUFFED) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.STUFFED.NAME, DuelistVictoryScreen.STUFFED.DESCRIPTIONS[0], Integer.toString(50)));
        }
        else if (DuelistVictoryScreen.IS_WELL_FED) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.WELL_FED.NAME, DuelistVictoryScreen.WELL_FED.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (DuelistVictoryScreen.IS_CURSES) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.CURSES.NAME, DuelistVictoryScreen.CURSES.DESCRIPTIONS[0], Integer.toString(100)));
        }
        if (DuelistVictoryScreen.IS_ON_MY_OWN) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.ON_MY_OWN_TERMS.NAME, DuelistVictoryScreen.ON_MY_OWN_TERMS.DESCRIPTIONS[0], Integer.toString(50)));
        }
        if (CardCrawlGame.champion > 0) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.CHAMPION.NAME + " (" + CardCrawlGame.champion + ")", DuelistVictoryScreen.CHAMPION.DESCRIPTIONS[0], Integer.toString(25 * CardCrawlGame.champion)));
        }
        if (CardCrawlGame.perfect >= 3) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.BEYOND_PERFECT.NAME, DuelistVictoryScreen.BEYOND_PERFECT.DESCRIPTIONS[0], Integer.toString(200)));
        }
        else if (CardCrawlGame.perfect > 0) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.PERFECT.NAME + " (" + CardCrawlGame.perfect + ")", DuelistVictoryScreen.PERFECT.DESCRIPTIONS[0], Integer.toString(50 * CardCrawlGame.perfect)));
        }
        if (CardCrawlGame.overkill) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.OVERKILL.NAME, DuelistVictoryScreen.OVERKILL.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (CardCrawlGame.combo) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.COMBO.NAME, DuelistVictoryScreen.COMBO.DESCRIPTIONS[0], Integer.toString(25)));
        }
        if (AbstractDungeon.isAscensionMode) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.ASCENSION.NAME + " (" + AbstractDungeon.ascensionLevel + ")", DuelistVictoryScreen.ASCENSION.DESCRIPTIONS[0], Integer.toString(DuelistVictoryScreen.ascensionPoints)));
        }
        if (CardCrawlGame.dungeon instanceof TheEnding) {
            this.stats.add(new GameOverStat(DuelistVictoryScreen.HEARTBREAKER.NAME, DuelistVictoryScreen.HEARTBREAKER.DESCRIPTIONS[0], Integer.toString(250)));
        }
        this.stats.addAll(DuelistGameOverScreen.generateDuelistGameOverStats(true).stats());
        if (this.configGameOverStat != null) {
            this.stats.add(this.configGameOverStat);
        }
        this.stats.add(new GameOverStat());
        this.stats.add(new GameOverStat(DuelistVictoryScreen.TEXT[4], null, Integer.toString(this.score)));
    }
    
    @Override
    protected void submitVictoryMetrics() {
        HerokuMetrics metrics = new HerokuMetrics(true);
        final Thread t = new Thread(metrics);
        t.start();

        if (Settings.isStandardRun()) {
            StatsScreen.updateFurthestAscent(AbstractDungeon.floorNum);
        }
        if (SaveHelper.shouldDeleteSave()) {
            SaveAndContinue.deleteSave(AbstractDungeon.player);
        }
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
        AbstractDungeon.dynamicBanner.appearInstantly(DuelistVictoryScreen.TEXT[12]);
        AbstractDungeon.overlayMenu.showBlackScreen(1.0f);
        if (fromVictoryUnlock) {
            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistVictoryScreen.TEXT[0]);
        }
        else if (!this.showingStats) {
            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistVictoryScreen.TEXT[0]);
        }
        else if (this.unlockBundle == null) {
            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistVictoryScreen.TEXT[0]);
        }
        else {
            this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistVictoryScreen.TEXT[5]);
        }
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
                this.updateAscensionAndBetaArtProgress();
                if (this.unlockBundle == null) {
                    this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistVictoryScreen.TEXT[0]);
                }
                else {
                    this.returnButton.appear(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.15f, DuelistVictoryScreen.TEXT[5]);
                }
            }
            else if (this.unlockBundle != null) {
                AbstractDungeon.gUnlockScreen.open(this.unlockBundle, true);
                AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_VICTORY;
                AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
                this.unlockBundle = null;
                this.returnButton.label = DuelistVictoryScreen.TEXT[5];
            }
            else {
                this.returnButton.hide();
                if (!AbstractDungeon.unlocks.isEmpty() && !Settings.isDemo) {
                    AbstractDungeon.unlocks.clear();
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                }
                CardCrawlGame.playCreditsBgm = true;
                CardCrawlGame.startOverButShowCredits();
            }
        }
        this.updateStatsScreen();
        if (this.monsters != null) {
            this.monsters.update();
            this.monsters.updateAnimations();
        }
        this.updateVfx();
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

    private void updateAscensionAndBetaArtProgress() {
        if (AbstractDungeon.isAscensionMode && !Settings.seedSet && !Settings.isTrial && AbstractDungeon.ascensionLevel < 20 && StatsScreen.isPlayingHighestAscension(AbstractDungeon.player.getPrefs())) {
            StatsScreen.incrementAscension(AbstractDungeon.player.getCharStat());
            AbstractDungeon.topLevelEffects.add(new AscensionLevelUpTextEffect());
        }
        else if (!AbstractDungeon.ascensionCheck && UnlockTracker.isAscensionUnlocked(AbstractDungeon.player)) {
            AbstractDungeon.topLevelEffects.add(new AscensionUnlockedTextEffect());
        }
    }

    private void updateVfx() {
        switch (AbstractDungeon.player.chosenClass) {
            case IRONCLAD: {
                this.effectTimer -= Gdx.graphics.getDeltaTime();
                if (this.effectTimer < 0.0f) {
                    this.effect.add(new SlowFireParticleEffect());
                    this.effect.add(new SlowFireParticleEffect());
                    this.effect.add(new IroncladVictoryFlameEffect());
                    this.effect.add(new IroncladVictoryFlameEffect());
                    this.effect.add(new IroncladVictoryFlameEffect());
                    this.effectTimer = 0.1f;
                    break;
                }
                break;
            }
            case THE_SILENT: {
                this.effectTimer -= Gdx.graphics.getDeltaTime();
                if (this.effectTimer < 0.0f) {
                    if (this.effect.size() < 100) {
                        this.effect.add(new SilentVictoryStarEffect());
                        this.effect.add(new SilentVictoryStarEffect());
                        this.effect.add(new SilentVictoryStarEffect());
                        this.effect.add(new SilentVictoryStarEffect());
                    }
                    this.effectTimer = 0.1f;
                }
                this.effectTimer2 += Gdx.graphics.getDeltaTime();
                if (this.effectTimer2 > 1.0f) {
                    this.effectTimer2 = 1.0f;
                    break;
                }
                break;
            }
            case DEFECT: {
                boolean foundEyeVfx = false;
                for (final AbstractGameEffect e : this.effect) {
                    if (e instanceof DefectVictoryEyesEffect) {
                        foundEyeVfx = true;
                        break;
                    }
                }
                if (!foundEyeVfx) {
                    this.effect.add(new DefectVictoryEyesEffect());
                }
                if (this.effect.size() < 15) {
                    this.effect.add(new DefectVictoryNumberEffect());
                    break;
                }
                break;
            }
            case WATCHER: {
                boolean createdEffect = false;
                for (final AbstractGameEffect e2 : this.effect) {
                    if (e2 instanceof WatcherVictoryEffect) {
                        createdEffect = true;
                        break;
                    }
                }
                if (!createdEffect) {
                    this.effect.add(new WatcherVictoryEffect());
                    break;
                }
                break;
            }
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
        sb.setBlendFunction(770, 1);
        final Iterator<AbstractGameEffect> i = this.effect.iterator();
        while (i.hasNext()) {
            final AbstractGameEffect e = i.next();
            if (e.renderBehind) {
                e.render(sb);
            }
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }
        sb.setBlendFunction(770, 771);
        if (AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
            sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.effectTimer2));
            AbstractDungeon.player.renderShoulderImg(sb);
        }
        sb.setBlendFunction(770, 1);
        for (AbstractGameEffect e : this.effect) {
            if (!e.renderBehind) {
                e.render(sb);
            }
        }
        sb.setBlendFunction(770, 771);
        this.renderStatsScreen(sb);
        this.returnButton.render(sb);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("VictoryScreen");
        TEXT = DuelistVictoryScreen.uiStrings.TEXT;
    }
}
