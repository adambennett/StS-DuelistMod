package duelistmod.intents;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.*;

import basemod.ReflectionHacks;
import duelistmod.DuelistMod;
import razintent.abstracts.CustomIntent;

public class PlayCardsIntent extends CustomIntent {
    //That's just how I do IDs, you might do it differently.
    public static final String ID = DuelistMod.makeID("PlayCardsIntent");

    //For Localization. See https://github.com/daviscook477/BaseMod/wiki/Custom-Strings for more infos on that.
    private static final UIStrings uiStrings;
    private static final String[] TEXT;

    /**
     * First parameter (Intent) is your Intent from the class above
     * Second one (String) is the headline of your intent in its tooltip window when you hover over the enemy
     * Third one (String) contains the path to the image you want floating above the enemy head (128x128 with plenty of spacing, just like with custom relics)
     * Fourth one (String) contains the path to the image you want in the tooltip window (64x64, no spacing needed)
     * Fifth one (Optional String) is a String that is displayed in the tooltip window if you don't override the description() Method.
    */
    public PlayCardsIntent() {
        super(YourCustomIntent.PLAY_CARDS, TEXT[0],
                DuelistMod.makePath("images/ui/intent/bigger.png"), //These are just the paths to my images.
                DuelistMod.makePath("images/ui/intent/smaller.png"),  //Will differ for yours, of course.
                "OPTIONAL STRING HERE");
    }
    
    /**
     * (Optional)
     * The return value of this function is displayed in the tooltip window.
     * If you do not override this, the fifth parameter of CustomIntent's constructor will be displayed instead.
    */
    @Override
    public String description(AbstractMonster mo) {
        String result = TEXT[1];
        result += ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentDmg");
        result += TEXT[2];
        result += TEXT[3];

        return result;
    }

    /**
     * (Optional)
     * If you want custom strings to display where the damage number usually is, you can use this method.
     * It adjusts for stringlengths above 5 by displaying the message to the left, so it stays centered on your intent.
     * You can put in newlines like in the example with " NL ", if you need that.
    */
    @Override
    public String damageNumber(AbstractMonster am) {
        return "whatever you want NL me to be, baby";
    }

    /**
     * (Optional)
     * This allows you to add visual effects like the little green swirls on the debuff intent.
     * The return value determins the interval how often it is called, in seconds.
    */
    @Override
    public float updateVFXInInterval(AbstractMonster mo, ArrayList<AbstractGameEffect> intentVfx) {
        AbstractGameEffect sb = new DebuffParticleEffect(mo.intentHb.cX, mo.intentHb.cY);
        sb.renderBehind = false; //True means it's rendered behind the intent icon, if possible.

        intentVfx.add(sb); //The important part.
        //intentVfx is a private field in mo, I added it to the methodparams so people wouldn't have to add Reflectionhacks themselves.

        return 2.0F; //Executed every 2 seconds.
    }

    //If you do not like this method of updating VFX for whatever reason, you can also override
    /*
    @Override
    public void updateVFX(AbstractMonster mo) {
        ...
    }
    */
    //But why would you? The one above does all the work for you.

    //Standard procedure for getting localization strings.
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}