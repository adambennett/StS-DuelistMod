package duelistmod.helpers;

import java.util.*;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.TutorialStrings;

public class DuelistTipHelper 
{
	public static final TutorialStrings tutorialStrings;
    public static final String[] LABEL;
    public ArrayList<String> tips;

    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("theDuelist:CustomTips");
        LABEL = tutorialStrings.LABEL;
    }
}
