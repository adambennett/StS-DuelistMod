package duelistmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCharacterSelect;
import duelistmod.dto.LoadoutUnlockOrderInfo;
import duelistmod.dto.StatsData;
import duelistmod.helpers.Util;

import java.util.ArrayList;

public class DuelistGameOverScreen extends GameOverScreen {

    private final float progressBarX;
    private final float progressBarWidth;
    private int currentScore;
    
    public DuelistGameOverScreen() {
        this.unlockBundle = null;
        this.stats = new ArrayList<>();
        this.fadeBgColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        this.whiteUiColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
        this.creamUiColor = Settings.CREAM_COLOR.cpy();
        this.progressBarX = 576.0f * Settings.xScale;
        this.progressBarWidth = 768.0f * Settings.xScale;
        this.statsTimer = 0.0f;
        this.statAnimateTimer = 0.0f;
        this.progressBarTimer = 2.0f;
        this.progressBarAlpha = 0.0f;
        this.maxLevel = false;
        this.score = 0;
        this.unlockLevel = 0;
        this.playedWhir = false;
    }

    @Override
    protected void calculateUnlockProgress() {
        this.score = calculateScore(DuelistGameOverScreen.isVictory);
        
        UnlockTracker.addScore(AbstractDungeon.player.chosenClass, this.score);
        try
        {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
            this.currentScore = config.getInt("duelistScore");
            this.unlockProgress = this.currentScore + this.score;
            config.setInt("duelistScore", (int)this.unlockProgress);
            config.save();
            DuelistMod.duelistScore = this.currentScore + this.score;
        } catch (Exception e) {
            Util.logError("Error saving updated duelistScore on GameOver", e);
        }
    }

    public static StatsData generateDuelistGameOverStats(final boolean isVictory) {
        ArrayList<GameOverStat> stats = new ArrayList<>();
        int points = 0;
        if (Util.getChallengeLevel() > 0) {
            int value = Util.getChallengeLevel() * 100;
            stats.add(new GameOverStat("Challenge (" + Util.getChallengeLevel() + ")", "", Integer.toString(value)));
            points += value;
        } else if (Util.getChallengeLevel() == 0) {
            int value = 100;
            stats.add(new GameOverStat("Challenge Mode", "", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.summonRunCount > 0) {
            int value = DuelistMod.summonRunCount;
            stats.add(new GameOverStat("Summons (" + DuelistMod.summonRunCount + ")", "", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.tribRunCount > 0) {
            int value = DuelistMod.tribRunCount;
            stats.add(new GameOverStat("Tributes (" + DuelistMod.tribRunCount + ")", "", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.synergyTributesRan > 0) {
            int value = DuelistMod.synergyTributesRan * 2;
            stats.add(new GameOverStat("Synergy Tributes (" + DuelistMod.synergyTributesRan + ")", "", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.megatypeTributesThisRun > 0) {
            int value = DuelistMod.megatypeTributesThisRun * 2;
            stats.add(new GameOverStat("Megatype Tributes (" + DuelistMod.megatypeTributesThisRun + ")", "", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.resummonsThisRun > 0) {
            int value = DuelistMod.resummonsThisRun;
            stats.add(new GameOverStat("Resummons (" + DuelistMod.resummonsThisRun + ")", "", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.uniqueMonstersThisRun.size() > 20) {
            int value = 200;
            stats.add(new GameOverStat("Unique monsters played (" + DuelistMod.uniqueMonstersThisRun.size() + ")", "You played at least 20 unique Monster cards.", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.uniqueSpellsThisRun.size() > 20) {
            int value = 200;
            stats.add(new GameOverStat("Unique spells played (" + DuelistMod.uniqueMonstersThisRun.size() + ")", "You played at least 20 unique Spell cards.", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.uniqueTrapsThisRun.size() > 15) {
            int value = 200;
            stats.add(new GameOverStat("Unique traps played (" + DuelistMod.uniqueMonstersThisRun.size() + ")", "You played at least 15 unique Trap cards.", Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.boostersOpenedThisRun.size() > 0) {
            int value = DuelistMod.boostersOpenedThisRun.size() * 2;
            stats.add(new GameOverStat("Unique Boosters opened (" + DuelistMod.boostersOpenedThisRun.size() + ")", "", Integer.toString(value)));
            points += value;
        }
        if (isVictory) {
            if (DuelistMod.restrictSummonZones) {
                int value = 250;
                stats.add(new GameOverStat("Restricted summoning zones", "", Integer.toString(value)));
                points += value;
            }
        }
        return new StatsData(points, stats);
    }

    @Override
    protected void renderProgressBar(final SpriteBatch sb) {
        LoadoutUnlockOrderInfo info = DuelistCharacterSelect.getNextUnlockDeckAndScore((int)this.unlockProgress);
        if (info == null) {
           return;
        }
        this.unlockCost = info.cost();
        this.progressPercent = (float)this.currentScore / info.cost();
        this.whiteUiColor.a = this.progressBarAlpha * 0.3f;
        sb.setColor(this.whiteUiColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, this.progressBarX, Settings.HEIGHT * 0.2f, this.progressBarWidth, 14.0f * Settings.scale);
        sb.setColor(new Color(1.0f, 0.8f, 0.3f, this.progressBarAlpha * 0.9f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, this.progressBarX, Settings.HEIGHT * 0.2f, this.progressBarWidth * this.progressPercent, 14.0f * Settings.scale);
        sb.setColor(new Color(0.0f, 0.0f, 0.0f, this.progressBarAlpha * 0.25f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, this.progressBarX, Settings.HEIGHT * 0.2f, this.progressBarWidth * this.progressPercent, 4.0f * Settings.scale);
        String derp = "[" + (int)this.unlockProgress + "/" + this.unlockCost + "]";
        this.creamUiColor.a = this.progressBarAlpha * 0.9f;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, derp, 576.0f * Settings.xScale, Settings.HEIGHT * 0.2f - 12.0f * Settings.scale, this.creamUiColor);
        derp = "Next unlock: " + info.deck();
        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, derp, 1344.0f * Settings.xScale, Settings.HEIGHT * 0.2f - 12.0f * Settings.scale, this.creamUiColor);
    }
    
    private static int calculateScore(final boolean victory) {
        DuelistGameOverScreen.bossPoints = 0;
        DuelistGameOverScreen.ascensionPoints = 0;
        int points = AbstractDungeon.floorNum * 5;
        DuelistGameOverScreen.floorPoints = AbstractDungeon.floorNum * 5;
        DuelistGameOverScreen.monsterPoints = CardCrawlGame.monstersSlain * 2;
        DuelistGameOverScreen.elite1Points = CardCrawlGame.elites1Slain * 10;
        DuelistGameOverScreen.elite2Points = CardCrawlGame.elites2Slain * 20;
        DuelistGameOverScreen.elite3Points = CardCrawlGame.elites3Slain * 30;
        int bossMultiplier = 50;
        for (int i = 0; i < AbstractDungeon.bossCount; ++i) {
            DuelistGameOverScreen.bossPoints += bossMultiplier;
            bossMultiplier += 50;
        }
        points += DuelistGameOverScreen.monsterPoints;
        points += DuelistGameOverScreen.elite1Points;
        points += DuelistGameOverScreen.elite2Points;
        points += DuelistGameOverScreen.elite3Points;
        points += DuelistGameOverScreen.bossPoints;
        points += CardCrawlGame.champion * 25;
        if (CardCrawlGame.perfect >= 3) {
            points += 200;
        }
        else {
            points += CardCrawlGame.perfect * 50;
        }
        if (CardCrawlGame.overkill) {
            points += 25;
        }
        if (CardCrawlGame.combo) {
            points += 25;
        }
        if (AbstractDungeon.isAscensionMode) {
            DuelistGameOverScreen.ascensionPoints = MathUtils.round(points * (0.05f * AbstractDungeon.ascensionLevel));
            points += DuelistGameOverScreen.ascensionPoints;
        }
        points += checkScoreBonus(victory);
        points += generateDuelistGameOverStats(victory).points();
        return points;
    }

    static {
        DuelistGameOverScreen.IS_POOPY = false;
        DuelistGameOverScreen.IS_SPEEDSTER = false;
        DuelistGameOverScreen.IS_LIGHT_SPEED = false;
        DuelistGameOverScreen.IS_HIGHLANDER = false;
        DuelistGameOverScreen.IS_FULL_SET = 0;
        DuelistGameOverScreen.IS_SHINY = false;
        DuelistGameOverScreen.IS_PAUPER = false;
        DuelistGameOverScreen.IS_LIBRARY = false;
        DuelistGameOverScreen.IS_ENCYCLOPEDIA = false;
        DuelistGameOverScreen.IS_WELL_FED = false;
        DuelistGameOverScreen.IS_STUFFED = false;
        DuelistGameOverScreen.IS_CURSES = false;
        DuelistGameOverScreen.IS_ON_MY_OWN = false;
        DuelistGameOverScreen.IS_MONEY_MONEY = false;
        DuelistGameOverScreen.IS_RAINING_MONEY = false;
        DuelistGameOverScreen.IS_I_LIKE_GOLD = false;
        DuelistGameOverScreen.IS_MYSTERY_MACHINE = false;
    }

}
