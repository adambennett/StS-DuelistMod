package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.MillenniumItem;

public class MillenniumNecklace extends DuelistRelic implements MillenniumItem
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("MillenniumNecklace");
    public static final String IMG = DuelistMod.makeRelicPath("NecklaceRelic.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Necklace_Outline.png");
    // /FIELDS

    public MillenniumNecklace() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }
    
    @Override
    public void onVictory() 
    {
		flash();
    	AbstractDungeon.getCurrRoom().addPotionToRewards(AbstractDungeon.returnRandomPotion());	
    }
}
