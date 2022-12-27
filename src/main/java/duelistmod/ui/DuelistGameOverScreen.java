package duelistmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import duelistmod.DuelistMod;
import duelistmod.characters.DuelistCharacterSelect;
import duelistmod.dto.LoadoutUnlockOrderInfo;
import duelistmod.helpers.Util;

import java.util.ArrayList;

public class DuelistGameOverScreen extends GameOverScreen {

    protected static int challengePoints;
    private final float progressBarX;
    private final float progressBarWidth;
    private static final String[] TEXT;
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
        } catch (Exception e) { e.printStackTrace(); }
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
        DuelistGameOverScreen.floorPoints = 0;
        DuelistGameOverScreen.monsterPoints = 0;
        DuelistGameOverScreen.elite1Points = 0;
        DuelistGameOverScreen.elite2Points = 0;
        DuelistGameOverScreen.elite3Points = 0;
        DuelistGameOverScreen.bossPoints = 0;
        DuelistGameOverScreen.ascensionPoints = 0;
        int points = AbstractDungeon.floorNum * 5;
        DuelistGameOverScreen.floorPoints = AbstractDungeon.floorNum * 5;
        DuelistGameOverScreen.monsterPoints = CardCrawlGame.monstersSlain * 2;
        DuelistGameOverScreen.elite1Points = CardCrawlGame.elites1Slain * 10;
        DuelistGameOverScreen.elite2Points = CardCrawlGame.elites2Slain * 20;
        DuelistGameOverScreen.elite3Points = CardCrawlGame.elites3Slain * 30;
        DuelistGameOverScreen.bossPoints = 0;
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
        if (Util.getChallengeLevel() > 0) {
            points += Util.getChallengeLevel() * 3;
        }
        points += DuelistMod.summonRunCount;
        points += (DuelistMod.tribRunCount * 2);
        points += (DuelistMod.megatypeTributesThisRun * 3);
        points += DuelistMod.resummonsThisRun;
        if (DuelistMod.uniqueMonstersThisRun.size() > 20) {
            points += 100;
        }
        if (DuelistMod.uniqueSpellsThisRun.size() > 20) {
            points += 100;
        }
        if (DuelistMod.uniqueTrapsThisRun.size() > 15) {
            points += 100;
        }

        
        points += checkScoreBonus(victory);
        return points;
    }

    private static int checkDuelistScoreBonuses(final boolean victory) {
        int points = 0;
        if (AbstractDungeon.player.hasRelic("Spirit Poop")) {
            DuelistGameOverScreen.IS_POOPY = true;
            --points;
        }

        if (AbstractDungeon.player.relics.size() >= 25) {
            DuelistGameOverScreen.IS_SHINY = true;
            points += 50;
        }
        if (AbstractDungeon.player.masterDeck.size() >= 50) {
            DuelistGameOverScreen.IS_ENCYCLOPEDIA = true;
            points += 50;
        }
        else if (AbstractDungeon.player.masterDeck.size() >= 35) {
            DuelistGameOverScreen.IS_LIBRARY = true;
            points += 25;
        }
        final int tmpDiff = AbstractDungeon.player.maxHealth - AbstractDungeon.player.startingMaxHP;
        if (tmpDiff >= 30) {
            DuelistGameOverScreen.IS_STUFFED = true;
            points += 50;
        }
        else if (tmpDiff >= 15) {
            DuelistGameOverScreen.IS_WELL_FED = true;
            points += 25;
        }

        if (AbstractDungeon.player.masterDeck.cursedCheck()) {
            DuelistGameOverScreen.IS_CURSES = true;
            points += 100;
        }
        if (CardCrawlGame.goldGained >= 3000) {
            DuelistGameOverScreen.IS_I_LIKE_GOLD = true;
            points += 75;
        }
        else if (CardCrawlGame.goldGained >= 2000) {
            DuelistGameOverScreen.IS_RAINING_MONEY = true;
            points += 50;
        }
        else if (CardCrawlGame.goldGained >= 1000) {
            DuelistGameOverScreen.IS_MONEY_MONEY = true;
            points += 25;
        }

        if (CardCrawlGame.mysteryMachine >= 15) {
            DuelistGameOverScreen.IS_MYSTERY_MACHINE = true;
            points += 25;
        }

        if (victory) {
            if ((long)CardCrawlGame.playtime <= 2700L) {
                DuelistGameOverScreen.IS_LIGHT_SPEED = true;
                points += 50;
            }
            else if ((long)CardCrawlGame.playtime <= 3600L) {
                DuelistGameOverScreen.IS_SPEEDSTER = true;
                points += 25;
            }
            if (AbstractDungeon.player.masterDeck.highlanderCheck()) {
                DuelistGameOverScreen.IS_HIGHLANDER = true;
                points += 100;
            }
            if (AbstractDungeon.player.masterDeck.pauperCheck()) {
                DuelistGameOverScreen.IS_PAUPER = true;
                points += 50;
            }
            if (DuelistGameOverScreen.isVictory && CardCrawlGame.dungeon instanceof TheEnding) {
                points += 250;
            }
            if (DuelistMod.restrictSummonZones) {
                points += 250;
            }
        }

        return points;
    }
    
    static {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DeathScreen");
        TEXT = uiStrings.TEXT;
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
