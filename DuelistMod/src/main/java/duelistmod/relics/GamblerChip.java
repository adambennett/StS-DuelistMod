package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.helpers.StarterDeckSetup;

public class GamblerChip extends CustomRelic 
{
    // ID, images, text.
    public static final String ID = DuelistMod.makeID("GamblerChip");
    public static final String IMG = DuelistMod.makeRelicPath("GamblerChip.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("GamblerChip_Outline.png");

    public GamblerChip() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }
    
    @Override
    public boolean canSpawn()
    {
    	String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Exodia Deck")) { return false; }
		else { return true; }
    }

    @Override
	public void onEquip()
	{
		AbstractDungeon.player.energy.energyMaster++;
	}
	
	@Override
	public void onUnequip()
	{
		AbstractDungeon.player.energy.energyMaster--;
	}

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new GamblerChip();
    }
}
