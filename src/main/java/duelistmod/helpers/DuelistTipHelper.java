package duelistmod.helpers;

import java.util.*;

import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.ui.*;
import duelistmod.*;

public class DuelistTipHelper 
{
	public static final TutorialStrings tutorialStrings;
    public static final String[] LABEL;
    private static final String HEADER = "duelistTutorialTip";
    private static final List<String> tipsSeen = new ArrayList<>();
    public ArrayList<String> tips;

    public static void showTip(String key, String header, String body, DuelistTipType type, Object potionOrCard) {
        try {
            boolean hasTip = false;
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
            config.load();
            if (config.has(HEADER + key)) {
                hasTip = config.getBool(HEADER + key);
            }
            if (!hasTip) {
                //ReflectionHacks.setPrivate(AbstractDungeon.ftue, FtueTip.class, "m", AbstractDungeon.player);
                switch (type) {
                    case CARD:
                        if (potionOrCard instanceof AbstractCard) {
                            AbstractDungeon.ftue = new FtueTip(header, body, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, (AbstractCard)potionOrCard);
                        }
                        break;
                    case POTION:
                        if (potionOrCard instanceof AbstractPotion) {
                            AbstractDungeon.ftue = new FtueTip(header, body, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, (AbstractPotion)potionOrCard);
                        }
                        break;
                    case SHUFFLE:
                        AbstractDungeon.ftue = new FtueTip(header, body, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, FtueTip.TipType.SHUFFLE);
                        break;
                    case POWER:
                        AbstractDungeon.ftue = new FtueTip(header, body, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, FtueTip.TipType.POWER);
                        break;
                    case ENERGY:
                    default:
                        AbstractDungeon.ftue = new FtueTip(header, body, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, FtueTip.TipType.ENERGY);
                        break;
                }

                tipsSeen.add(HEADER + key);
            } else {
                Util.log("Already seen tutorial tip: " + key);
            }
            Util.log("Done showing tutorial tip: " + key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void showTip(String key, String header, String body, DuelistTipType type) {
        showTip(key, header, body, type, null);
    }

    public static void showTip(String key, String header, String body) {
        showTip(key, header, body, DuelistTipType.ENERGY, null);
    }

    public static void saveTips(SpireConfig config) {
        if (tipsSeen.size() > 0) {
            for (String key : tipsSeen) {
                config.setBool(key, true);
            }
            tipsSeen.clear();
        }
    }

    public enum DuelistTipType {
        ENERGY,
        CARD,
        POTION,
        SHUFFLE,
        POWER
    }

    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("theDuelist:CustomTips");
        LABEL = tutorialStrings.LABEL;
    }
}
