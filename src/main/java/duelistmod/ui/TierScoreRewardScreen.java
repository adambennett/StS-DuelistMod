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
import duelistmod.ui.buttons.*;

import java.util.*;

public class TierScoreRewardScreen {

    private static final List<TierScoreLabel> buttons = new ArrayList<>();
    private static final Color highlightColor = Color.GOLD;
    private static boolean hasTierScore;

    public static void onClose() {
        buttons.clear();
        hasTierScore = false;
    }

    public static void update() {
        if (hasTierScore) {
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
        String pool = StarterDeckSetup.getCurrentDeck().getSimpleName();
        String[] splice = pool.split("Deck");
        String basePool = splice[0].trim();
        pool = basePool + " Pool";
        String poolSet = "";
        boolean isOverallScore = false;
        if (DuelistMod.cardTierScores.containsKey(pool)) {
            Map<String, Map<Integer, Integer>> inner = DuelistMod.cardTierScores.get(pool);
            boolean found = inner.containsKey(cardId);
            if (found) {
                poolSet = pool;
            } else {
                for (String poolName : DuelistMod.secondaryTierScorePools) {
                    inner = DuelistMod.cardTierScores.get(poolName);
                    found = inner.containsKey(cardId);
                    if (found) {
                        poolSet = poolName;
                        break;
                    }
                }
                if (!found) {
                    for (String poolName : Util.fallbackTierScorePools()) {
                        inner = DuelistMod.cardTierScores.get(poolName);
                        found = inner.containsKey(cardId);
                        if (found) {
                            poolSet = poolName;
                            break;
                        }
                    }
                }
            }
            if (inner.containsKey(cardId)) {
                Map<Integer, Integer> actToScore = inner.get(cardId);
                int score = -1;
                if (actToScore.containsKey(currentAct)) {
                    score = actToScore.get(currentAct);

                } else if (actToScore.containsKey(-1)) {
                    score = actToScore.get(-1);
                    isOverallScore = true;
                }
                if (score != -1 || DuelistMod.modMode == Mode.DEV) {
                    TierScoreLabel label = new TierScoreLabel(cardToScore, score, counter, basePool);
                    label.show();
                    Util.log("Showing label for " + cardToScore.cardID);
                    buttons.add(label);
                    hasTierScore = true;
                    Util.log("Scored " + cardToScore.name + " by " + (isOverallScore ? "Overall Score in " : "Act " + currentAct + " Score in ") + poolSet, true);
                }
            } else {
                Util.log("Could not find score for card: " + cardToScore.cardID);
            }
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
