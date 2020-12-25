package duelistmod.ui;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.screens.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.characters.*;
import duelistmod.helpers.*;
import duelistmod.ui.buttons.*;

import java.util.*;

public class TierScoreRewardScreen {

    private static final List<TierScoreLabel> buttons = new ArrayList<>();
    private static boolean hasTierScore;

    public static void open(CardRewardScreen screen, List<AbstractCard> cards) {
        buttons.clear();
        int currentAct = AbstractDungeon.actNum;
        if (currentAct == 1 && AbstractDungeon.floorNum < 1) {
            currentAct = 0;
        }
        if (Util.inBattle() || !(AbstractDungeon.player instanceof TheDuelist)) {
            return;
        }

        int counter = 0;
        for (AbstractCard card : cards) {
            if (card instanceof DuelistCard) {
                DuelistCard dc = (DuelistCard)card;
                String cardId = dc.cardID;
                String pool = StarterDeckSetup.getCurrentDeck().getSimpleName();
                String[] splice = pool.split("Deck");
                String basePool = splice[0].trim();
                pool = splice[0].trim() + " Pool";
                if (DuelistMod.cardTierScores.containsKey(pool)) {
                    Map<String, Map<Integer, Integer>> inner = DuelistMod.cardTierScores.get(pool);
                    boolean found = inner.containsKey(cardId);
                    if (!found) {
                        for (String poolName : DuelistMod.secondaryTierScorePools) {
                            inner = DuelistMod.cardTierScores.get(poolName);
                            found = inner.containsKey(cardId);
                            if (found) {
                                break;
                            }
                        }
                        if (!found) {
                            for (String poolName : Util.fallbackTierScorePools()) {
                                inner = DuelistMod.cardTierScores.get(poolName);
                                found = inner.containsKey(cardId);
                                if (found) {
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
                        }
                        if (score != -1) {
                            TierScoreLabel label = new TierScoreLabel(dc, score, counter, basePool);
                            label.show();
                            Util.log("Showing label for " + dc.cardID);
                            buttons.add(label);
                            hasTierScore = true;
                        }
                    } else {
                        Util.log("Could not find score for card: " + dc.cardID);
                    }
                }
            }
            counter++;
        }
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
            button.setColor(Color.GOLD);
        }
    }

    public static void onClose(CardRewardScreen screen) {
        buttons.clear();
        hasTierScore = false;
    }

    public static void update(CardRewardScreen screen) {
        if (hasTierScore) {
            updateLabels();
        }
    }

    public static void preRender(CardRewardScreen screen, SpriteBatch sb) {
        if (hasTierScore) {
            for (TierScoreLabel label : buttons) {
                label.render(sb);
            }
        }
    }

    public static void updateLabels() {
        for (TierScoreLabel label : buttons) {
            label.update();
        }
    }

}
