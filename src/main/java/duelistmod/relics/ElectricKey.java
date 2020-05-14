package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.duelistPowers.ElectricityPower;
import duelistmod.variables.Strings;

public class ElectricKey extends DuelistRelic 
{
    // ID, images, text.
    public static final String ID = DuelistMod.makeID("ElectricKey");
    public static final String IMG = DuelistMod.makeRelicPath("ElectricKey.png");
    public static final String OUTLINE = DuelistMod.makePath(Strings.M_KEY_RELIC_OUTLINE);
    private boolean finished = false;

    public ElectricKey() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }
    
	@Override
	public boolean canSpawn()
	{
		if (AbstractDungeon.player.hasRelic(ElectricBurst.ID)) { return false; }
		return true;
	}

    @Override
    public void atTurnStart() 
    {
    	if (!finished) { DuelistCard.applyPowerToSelf(new ElectricityPower(-1)); finished = true; }
    }
    
    @Override
    public void onVictory() 
    {
    	finished = false;
    }
    
    @Override
	public void onEquip()
	{
		//DuelistMod.hasKey = true;
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
        return new ElectricKey();
    }
}
