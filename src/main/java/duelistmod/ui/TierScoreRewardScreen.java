package duelistmod.ui;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.screens.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.characters.*;
import duelistmod.enums.*;
import duelistmod.helpers.*;
import duelistmod.metrics.tierScoreDTO.Score;
import duelistmod.ui.buttons.*;

import java.util.*;

public class TierScoreRewardScreen {

    private static final List<TierScoreLabel> buttons = new ArrayList<>();
    private static final HashSet<String> badChecks = new HashSet<>();
    private static final Color highlightColor = Color.GOLD;
    private static boolean hasTierScore;

    public static void onClose() {
        buttons.clear();
        hasTierScore = false;
    }

    public static void update() {
        if (hasTierScore && DuelistMod.persistentDuelistData.MetricsSettings.getTierScoresEnabled()) {
            updateLabels();
        }
    }

    private static Boolean canSetupScores(List<AbstractCard> cards) {
        if (cards.size() <= 0) {
            return false;
        }
        for (AbstractCard card : cards) {
            if (card.target_x <= 0.0) {
                return false;
            }
        }
        return true;
    }

    public static void preRender(CardRewardScreen screen, SpriteBatch sb) {
        if (hasTierScore) {
            for (TierScoreLabel label : buttons) {
                label.render(sb);
            }
        } else if (canSetupScores(screen.rewardGroup)) {
            setupScores(screen.rewardGroup);
        }
    }

    public static void updateLabels() {
        for (TierScoreLabel label : buttons) {
            label.update();
        }
    }

    private static void setupScores(List<AbstractCard> cards) {
        buttons.clear();
        if (Util.inBattle() || !(AbstractDungeon.player instanceof TheDuelist)) {
            return;
        }
        int currentAct = (AbstractDungeon.actNum == 1 && AbstractDungeon.floorNum < 1) ? 0 : AbstractDungeon.actNum;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof DuelistCard) {
                scoreCard((DuelistCard) cards.get(i), currentAct, i);
            }
        }
        highlightHighestScore();
    }

    private static void scoreCard(DuelistCard cardToScore, int currentAct, int counter) {
        String cardId = cardToScore.cardID;
        String pool = StartingDeck.currentDeck.getDeckName();
        String badCheckKey = cardToScore.cardID + "~~" + pool;
        if (badChecks.contains(badCheckKey)) {
            return;
        }
        String[] splice = pool.split("Deck");
        String basePool = splice[0].trim();
        pool = basePool + " Pool";
        boolean isOverallScore = false;
        Score checkScore = DuelistMod.cardTierScores.score(pool, cardId, currentAct);
        int score = -1;
        if (checkScore == null && DuelistMod.secondaryTierScorePools.size() > 0) {
            for (String s : DuelistMod.secondaryTierScorePools) {
                checkScore = DuelistMod.cardTierScores.score(s, cardId, currentAct);
                if (checkScore != null) {
                    pool = s;
                    break;
                }
            }
        }
        if (checkScore != null) {
            isOverallScore = checkScore.isOverall();
            score = checkScore.score();
        }
        if (score != -1 || DuelistMod.modMode == Mode.DEV) {
            TierScoreLabel label = new TierScoreLabel(cardToScore, score, counter, basePool);
            label.show();
            Util.log("Showing label for " + cardToScore.cardID);
            buttons.add(label);
            hasTierScore = true;
            Util.log("Scored " + cardToScore.name + " by " + (isOverallScore ? "Overall Score in " : "Act " + currentAct + " Score in ") + pool, DuelistMod.persistentDuelistData.MetricsSettings.getLogMetricsScoresToDevConsole());
        } else {
            Util.log("Could not find score for card: " + cardToScore.cardID + ", using pool: " + pool);
            badChecks.add(badCheckKey);
        }
    }

    private static void highlightHighestScore() {
        int score = -1;
        List<TierScoreLabel> highButtons = new ArrayList<>();
        for (TierScoreLabel button : buttons) {
            if (button.tierScore > score) {
                score = button.tierScore;
                highButtons.clear();
                highButtons.add(button);
            } else if (button.tierScore == score) {
                highButtons.add(button);
            }
        }
        for (TierScoreLabel button : highButtons) {
            button.setColor(highlightColor);
        }
    }

}
