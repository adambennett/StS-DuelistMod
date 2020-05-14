package duelistmod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import basemod.TopPanelItem;
import duelistmod.DuelistMod;

public class ChallengeIcon extends TopPanelItem {
    private static final Texture IMG = new Texture(DuelistMod.makeIconPath("challenge.png"));
    public static final String ID = "theDuelist:ChallengeIcon";

    public ChallengeIcon() { super(IMG, ID); }

    @Override
    protected void onClick() {
    	
    }
    
    @Override
    protected void onHover()
    {
    	super.onHover();
    	TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Challenge Mode", "Fill with Challenge Effects");
    }
    
    @Override
    protected void onUnhover()
    {
    	super.onUnhover();
    }
}
