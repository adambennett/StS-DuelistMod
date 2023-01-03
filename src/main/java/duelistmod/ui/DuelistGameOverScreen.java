package duelistmod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.DuelistCharacterSelect;
import duelistmod.dto.LoadoutUnlockOrderInfo;
import duelistmod.dto.StatsData;
import duelistmod.enums.DeckUnlockRate;
import duelistmod.helpers.Util;
import duelistmod.vfx.DuelistUnlockEffect;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

public class DuelistGameOverScreen extends GameOverScreen {

    private final float progressBarX;
    private final float progressBarWidth;
    private int progressSoFar;
    private int initialScore;
    private boolean doneAnimating;
    private boolean setAllDecksVars;
    protected GameOverStat configGameOverStat;
    
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
        StatsData scoreData = this.calculateScore(DuelistGameOverScreen.isVictory);
        this.score = scoreData.points();
        try
        {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
            this.initialScore = config.getInt("duelistScore");
            int initialTrueScore = config.getInt("trueDuelistScore");
            int initialVersionScore = config.getInt("trueDuelistScore" + DuelistMod.trueVersion);
            LoadoutUnlockOrderInfo info = DuelistCharacterSelect.getNextUnlockDeckAndScore(this.initialScore);
            if (!info.deck().equals("ALL DECKS UNLOCKED")) {
                this.unlockProgress = this.initialScore;
                this.unlockTargetStart = this.unlockProgress;
                this.unlockCost = info.cost();
                this.unlockTargetProgress = this.unlockProgress + this.score;
                this.nextUnlockCost = info.nextCost() != null ? info.nextCost() : 0;
                if (this.unlockTargetProgress >= (float)this.unlockCost) {
                    this.unlockTargetProgress = (float)this.unlockCost;
                }
                this.progressPercent = this.unlockTargetStart / (float)this.unlockCost;
            } else {
                this.unlockProgress = this.initialScore;
                this.unlockTargetStart = this.unlockProgress;
                this.unlockCost = this.initialScore + this.score;
                this.unlockTargetProgress = this.unlockCost;
                this.setAllDecksVars = true;
            }
            config.setInt("duelistScore", this.initialScore + this.score);
            config.setInt("trueDuelistScore", initialTrueScore + scoreData.truePoints());
            config.setInt("trueDuelistScore" + DuelistMod.trueVersion, initialVersionScore + scoreData.truePoints());
            config.save();
            DuelistMod.duelistScore = this.initialScore + this.score;
            DuelistMod.trueDuelistScore = initialTrueScore + scoreData.truePoints();
            DuelistMod.trueVersionScore = initialVersionScore + scoreData.truePoints();
        } catch (Exception e) {
            Util.logError("Error saving updated duelistScore on GameOver", e);
        }
    }

    public static StatsData generateDuelistGameOverStats(final boolean isVictory) {
        ArrayList<GameOverStat> stats = new ArrayList<>();
        int points = 0;
        int truePoints;
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
            StringBuilder monsters = new StringBuilder();
            for (DuelistCard card : DuelistMod.uniqueMonstersThisRun) {
                monsters.append(card.name).append("\n");
            }
            stats.add(new GameOverStat("Unique monsters played (" + DuelistMod.uniqueMonstersThisRun.size() + ")", "You played at least 20 unique Monster cards.\n" + monsters.toString().trim(), Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.uniqueSpellsThisRun.size() > 20) {
            int value = 200;
            StringBuilder spells = new StringBuilder();
            for (DuelistCard card : DuelistMod.uniqueSpellsThisRun) {
                spells.append(card.name).append("\n");
            }
            stats.add(new GameOverStat("Unique spells played (" + DuelistMod.uniqueSpellsThisRun.size() + ")", "You played at least 20 unique Spell cards.\n" + spells.toString().trim(), Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.uniqueTrapsThisRun.size() > 15) {
            int value = 200;
            StringBuilder traps = new StringBuilder();
            for (DuelistCard card : DuelistMod.uniqueTrapsThisRun) {
                traps.append(card.name).append("\n");
            }
            stats.add(new GameOverStat("Unique traps played (" + DuelistMod.uniqueTrapsThisRun.size() + ")", "You played at least 15 unique Trap cards.\n" + traps.toString().trim(), Integer.toString(value)));
            points += value;
        }
        if (DuelistMod.boostersOpenedThisRun.size() > 0) {
            int value = DuelistMod.boostersOpenedThisRun.size() * 2;
            StringBuilder boostersDesc = new StringBuilder();
            for (Map.Entry<String, Integer> entry : DuelistMod.boostersOpenedThisRun.entrySet()) {
                boostersDesc.append(entry.getKey()).append("\n");
            }
            stats.add(new GameOverStat("Unique Boosters opened (" + DuelistMod.boostersOpenedThisRun.size() + ")", "", Integer.toString(value)));
            points += value;
        }
        if (isVictory) {
            if (DuelistMod.restrictSummonZones) {
                int value = 250;
                stats.add(new GameOverStat("Restricted summoning zones", "", Integer.toString(value)));
                points += value;

                boolean addedAsc = false;
                boolean addedChallenge = false;
                if (AbstractDungeon.isAscensionMode) {
                    int val = MathUtils.round(points * (0.05f * AbstractDungeon.ascensionLevel));
                    stats.add(new GameOverStat("Bonus Ascension Points", "Double ascension points due to enabling 'Restrict Summoning Zones' configuration.", Integer.toString(val)));
                    points += val;
                    addedAsc = true;
                }

                if (Util.getChallengeLevel() > 0) {
                    int val = Util.getChallengeLevel() * 100;
                    stats.add(new GameOverStat("Bonus Challenge Mode Points", "Double challenge points due to enabling 'Restrict Summoning Zones' configuration.", Integer.toString(val)));
                    points += val;
                    addedChallenge = true;
                } else if (Util.getChallengeLevel() == 0) {
                    int val = 100;
                    stats.add(new GameOverStat("Bonus Challenge Mode Points", "Double challenge points due to enabling 'Restrict Summoning Zones' configuration.", Integer.toString(val)));
                    points += val;
                    addedChallenge = true;
                }

                if (addedAsc && addedChallenge) {
                    int bonus = (AbstractDungeon.ascensionLevel * (Util.getChallengeLevel() > 0 ? Util.getChallengeLevel() : 1)) + 60;
                    stats.add(new GameOverStat("Difficulty Bonus", "Additional points awarded for combining several difficulty options: Restrict Summoning Zones, Ascension Mode, and Challenge Mode.", Integer.toString(bonus)));
                    points += bonus;
                }
            }
        }

        if (DuelistMod.forcePuzzleSummons && isVictory) {
            int value = ((int)Math.floor(points * 0.05)) * -1;
            if (value < 0) {
                stats.add(new GameOverStat("Bonus Puzzle Summons Penalty", "-5% Duelist points due to enabling 'Bonus Millennium Puzzle Summons' configuration.", Integer.toString(value)));
                points += value;
            }
        }
        truePoints = points;

        if (DuelistMod.currentUnlockRate == DeckUnlockRate.NO_DUELIST) {
            points = 0;
            stats.clear();
        }

        StatsData configModifications = modifyDuelistPoints(points);
        if (configModifications != null && DuelistMod.currentUnlockRate != DeckUnlockRate.NO_DUELIST) {
            stats.add(configModifications.stat());
            points += configModifications.points();
        }

        return new StatsData(points, truePoints, stats);
    }

    private static StatsData modifyDuelistPoints(int points) {
        double modified;
        boolean negative = false;
        switch (DuelistMod.currentUnlockRate) {
            case DOUBLE_DUELIST:
                modified = points * 2;
                break;
            case TRIPLE_DUELIST:
                modified = points * 3;
                break;
            case SIX_DUELIST:
                modified = points * 6;
                break;
            case HALF_DUELIST:
                modified = points * 0.5;
                negative = true;
                break;
            case QUARTER_DUELIST:
                modified = points * 0.25;
                negative = true;
                break;
            default:
                return null;
        }
        int value = (int)Math.floor(modified) - points;
        String text = negative ? "Lost Duelist " : "Received bonus Duelist ";
        String bonus = negative ? "Penalty" : "Bonus";
        if (value != 0) {
            return new StatsData(value, points, new GameOverStat("Configuration " + bonus, text + "points because your configuration for deck unlock rate was set to: NL " + DuelistMod.currentUnlockRate.displayText(), Integer.toString(value)));
        }
        return null;
    }

    private static StatsData modifyPoints(int points) {
        double modified;
        boolean negative = false;
        switch (DuelistMod.currentUnlockRate) {
            case DOUBLE:
                modified = points * 2;
                break;
            case TRIPLE:
                modified = points * 3;
                break;
            case SIX:
                modified = points * 6;
                break;
            case HALF:
                modified = points * 0.5;
                negative = true;
                break;
            case QUARTER:
                modified = points * 0.25;
                negative = true;
                break;
            default:
                return null;
        }
        int value = (int)Math.floor(modified) - points;
        String text = negative ? "Lost " : "Received bonus ";
        String bonus = negative ? "Penalty" : "Bonus";
        if (value != 0) {
            return new StatsData(value, points, new GameOverStat("Configuration " + bonus, text + "points because your configuration for deck unlock rate was set to: NL " + DuelistMod.currentUnlockRate.displayText(), Integer.toString(value)));
        }
        return null;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void renderStatsScreen(final SpriteBatch sb) {
        if (this.showingStats) {
            this.fadeBgColor.a = (1.0f - this.statsTimer) * 0.6f;
            sb.setColor(this.fadeBgColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, (float)Settings.WIDTH, (float)Settings.HEIGHT);
            float y = GameOverScreen.STAT_START_Y + this.stats.size() * GameOverScreen.STAT_OFFSET_Y / 2.0f;
            if (this.stats.size() >= 10) {
                y = GameOverScreen.STAT_START_Y + this.stats.size() / 2 * GameOverScreen.STAT_OFFSET_Y / 2.0f;
            }
            for (int i = 0; i < this.stats.size(); ++i) {
                if (this.stats.size() <= 10) {
                    if (i == this.stats.size() - 2) {
                        this.stats.get(i).renderLine(sb, false, y);
                    }
                    else {
                        this.stats.get(i).render(sb, Settings.WIDTH / 2.0f - 220.0f * Settings.scale, y);
                    }
                }
                else if (i != this.stats.size() - 1) {
                    if (i < (this.stats.size() - 1) / 2) {
                        this.stats.get(i).render(sb, 440.0f * Settings.xScale, y);
                    }
                    else {
                        this.stats.get(i).render(sb, 1050.0f * Settings.xScale, y + GameOverScreen.STAT_OFFSET_Y * ((this.stats.size() - 1) / 2));
                    }
                }
                else {
                    this.stats.get(i).renderLine(sb, true, y + GameOverScreen.STAT_OFFSET_Y * (this.stats.size() / 2));
                    this.stats.get(i).render(sb, 740.0f * Settings.xScale, y + GameOverScreen.STAT_OFFSET_Y * (this.stats.size() / 2 - 1));
                }
                y -= GameOverScreen.STAT_OFFSET_Y;
            }
            this.renderProgressBar(sb);
        }
    }

    @Override
    protected void renderProgressBar(final SpriteBatch sb) {
        if ((this.unlockProgress + this.progressSoFar) > (this.score + this.initialScore)) {
            int diff = (int) ((this.unlockProgress + this.progressSoFar) - (this.score + this.initialScore));
            this.progressSoFar -= diff;
        }
        LoadoutUnlockOrderInfo info = DuelistCharacterSelect.getNextUnlockDeckAndScore((int)this.unlockProgress + this.progressSoFar);
        String barText = null;
        if (info.deck().equals("ALL DECKS UNLOCKED")) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            int dubText = (int)(Math.floor(this.unlockProgress));
            barText = "Duelist Score: " + formatter.format(dubText);
        }
        this.whiteUiColor.a = this.progressBarAlpha * 0.3f;
        sb.setColor(this.whiteUiColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, this.progressBarX, Settings.HEIGHT * 0.2f, this.progressBarWidth, 14.0f * Settings.scale);
        sb.setColor(new Color(1.0f, 0.8f, 0.3f, this.progressBarAlpha * 0.9f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, this.progressBarX, Settings.HEIGHT * 0.2f, this.progressBarWidth * this.progressPercent, 14.0f * Settings.scale);
        sb.setColor(new Color(0.0f, 0.0f, 0.0f, this.progressBarAlpha * 0.25f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, this.progressBarX, Settings.HEIGHT * 0.2f, this.progressBarWidth * this.progressPercent, 4.0f * Settings.scale);
        String derp = barText != null ? barText : "[" + ((int)this.unlockProgress + this.progressSoFar) + "/" + info.cost() + "]";
        this.creamUiColor.a = this.progressBarAlpha * 0.9f;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, derp, 576.0f * Settings.xScale, Settings.HEIGHT * 0.2f - 12.0f * Settings.scale, this.creamUiColor);
        if (barText == null) {
            derp = "Next unlock: " + info.deck();
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, derp, 1344.0f * Settings.xScale, Settings.HEIGHT * 0.2f - 12.0f * Settings.scale, this.creamUiColor);
        }
    }

    @Override
    protected void animateProgressBar() {
        if (this.doneAnimating) {
            return;
        }
        if ((this.unlockProgress + this.progressSoFar) > (this.score + this.initialScore)) {
            int diff = (int) ((this.unlockProgress + this.progressSoFar) - (this.score + this.initialScore));
            this.progressSoFar -= diff;
            this.doneAnimating = true;
        }
        LoadoutUnlockOrderInfo info = DuelistCharacterSelect.getNextUnlockDeckAndScore((int)this.unlockProgress + this.progressSoFar);

        this.progressBarTimer -= Gdx.graphics.getDeltaTime();
        if (this.progressBarTimer < 0.0F) {
            this.progressBarTimer = 0.0F;
        }

        if (!(this.progressBarTimer > 2.0F)) {
            if (!info.deck().equals("ALL DECKS UNLOCKED")) {
                this.unlockProgress = Interpolation.pow2In.apply(this.unlockTargetProgress, this.unlockTargetStart, this.progressBarTimer / 2.0F);
                if (this.unlockProgress >= (float)this.unlockCost) {
                    if (info.nextCost() == null) {
                        this.progressSoFar = 0;
                        this.unlockProgress = info.cost();
                        AbstractDungeon.topLevelEffects.add(new DuelistUnlockEffect(info.deck()));
                    } else {
                        this.unlockTargetProgress = (info.nextCost() - info.cost());
                        this.progressBarTimer = 3.0F;
                        AbstractDungeon.topLevelEffects.add(new DuelistUnlockEffect(info.deck()));
                        this.progressSoFar += this.unlockCost;
                        this.unlockProgress = 0.0F;
                        this.unlockTargetStart = 0.0F;
                        this.unlockCost = (info.nextCost() - info.cost());
                    }
                }
                this.progressPercent = this.unlockProgress / (float)this.unlockCost;
                return;
            }

            if (!this.setAllDecksVars) {
                this.progressSoFar += this.unlockProgress;
                this.unlockProgress = this.initialScore;
                this.unlockTargetStart = this.unlockProgress;
                this.unlockCost = this.initialScore + this.score;
                this.unlockTargetProgress = this.unlockCost;
                this.setAllDecksVars = true;
                this.progressPercent = 0.0F;
                this.progressBarTimer = 6.0F;
                return;
            }

            if (!this.doneAnimating) {
                this.unlockProgress = Interpolation.pow2In.apply(this.unlockTargetProgress, this.unlockTargetStart, this.progressBarTimer / 2.0F);
                this.progressPercent = this.unlockProgress / (float)this.unlockCost;
                if (this.progressPercent >= 1.0F) {
                    this.unlockProgress = DuelistMod.duelistScore;
                    this.progressPercent = 1.0F;
                    this.doneAnimating = true;
                }
            }
        }
    }
    
    private StatsData calculateScore(final boolean victory) {
        DuelistGameOverScreen.bossPoints = 0;
        DuelistGameOverScreen.ascensionPoints = 0;
        int points = AbstractDungeon.floorNum * 5;
        int truePoints;
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
        truePoints = points;

        StatsData duelistPoints = generateDuelistGameOverStats(victory);
        points += duelistPoints.points();
        truePoints += duelistPoints.truePoints();

        StatsData configModifications = modifyPoints(points);
        if (configModifications != null) {
            configGameOverStat = configModifications.stat();
            points += configModifications.points();
        }
        return new StatsData(points, truePoints);
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
