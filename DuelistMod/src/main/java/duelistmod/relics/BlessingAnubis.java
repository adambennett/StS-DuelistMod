package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.cards.WingedKuriboh9;
import duelistmod.variables.Strings;

public class BlessingAnubis extends DuelistRelic implements ClickableRelic 
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("BlessingAnubis");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
    private static int maxCharge = 3;
    private static int curTurn = 0;
    private static final int turnChk = 4;
    // /FIELDS

    public BlessingAnubis() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0]; }
    
    @Override
    public void onEquip()
    {
    	this.counter = 3;
    }
    
    @Override
    public void onPlayerEndTurn()
    {
    	curTurn++;
    	if (curTurn == turnChk)
    	{
    		curTurn = 0;
    		if (this.counter < maxCharge) { this.setCounter(this.counter + 1); }
    	}
    }

    @Override
    public void onRightClick() 
    {
    	if (this.counter > 0)
    	{
    		flash();
    		ArrayList<DuelistCard> types = DuelistCard.generateTypeForRelic(1, true, new WingedKuriboh9());
        	this.addToBot(new CardSelectScreenResummonAction(types, 1, false, false, false, true));
    		this.counter--;
    	}
    }
    
    @Override public AbstractRelic makeCopy() { return new BlessingAnubis(); }
}
