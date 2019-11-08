package duelistmod.speedster.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

import duelistmod.DuelistMod;
import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.SpeedsterVFX.general.BeginSpeedModeEffect;
import duelistmod.speedster.mechanics.AbstractSpeedTime;

public class BeginSpeedModeAction extends AbstractGameAction {
    AbstractSpeedTime instance;
    private static final float START_DURATION = 1.5f;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(DuelistMod.makeID("SpeedModePrepText"));
    private static int textIndex = 0;
    
    public BeginSpeedModeAction(AbstractSpeedTime instance, int txtInd) {
        this.instance = instance;
        textIndex = txtInd;
    }

    public void update() {
    	String text = uiStrings.TEXT[textIndex];
        UC.doVfx(new BorderLongFlashEffect(Color.WHITE.cpy()));
        UC.doVfx(new BeginSpeedModeEffect(Color.FIREBRICK, text, START_DURATION, instance));
        isDone = true;
    }
}
