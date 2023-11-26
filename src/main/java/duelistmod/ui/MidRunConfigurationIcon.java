package duelistmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import basemod.TopPanelItem;
import duelistmod.DuelistMod;
import duelistmod.enums.ConfigOpenSource;
import duelistmod.helpers.Util;

public class MidRunConfigurationIcon extends TopPanelItem {
    private static final Texture IMG = new Texture(DuelistMod.makeIconPath("PuzzleIcon.png"));
    public static final String ID = "theDuelist:ChallengeIcon";
    public static String body;

    public MidRunConfigurationIcon() { super(IMG, ID); }

    @Override
    public void render(SpriteBatch sb, Color color) {
        if (Util.canOpenModSettings(ConfigOpenSource.MID_RUN)) {
            super.render(sb, color);
        }
    }

    @Override
    protected void renderHitbox(SpriteBatch sb) {
        if (Util.canOpenModSettings(ConfigOpenSource.MID_RUN)) {
            super.renderHitbox(sb);
        }
    }

    @Override
    public boolean isClickable() {
        return Util.canOpenModSettings(ConfigOpenSource.MID_RUN) && super.isClickable();
    }

    @Override
    protected void onClick() {
        Util.openModSettings(ConfigOpenSource.MID_RUN);
    }
    
    @Override
    protected void onHover() {
        if (Util.canOpenModSettings(ConfigOpenSource.MID_RUN)) {
            super.onHover();
            TipHelper.renderGenericTip((float) InputHelper.mX - 300.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale, "DuelistMod", "Modify configuration settings. NL Some settings will not be applied until after your run is completed.");
        }
    }
    
    @Override
    protected void onUnhover() {
    	super.onUnhover();
    }

}
