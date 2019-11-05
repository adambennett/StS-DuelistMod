package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.incomplete.HauntedDebuff;

public class HauntedRelic extends DuelistRelic 
{
    // ID, images, text.
    public static final String ID = DuelistMod.makeID("HauntedRelic");
    public static final String IMG = DuelistMod.makeRelicPath("HauntedRelic.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Haunted_Outline.png");

    public HauntedRelic() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
        setDescription();
    }

    @Override
    public void atBattleStart() 
    {
    	DuelistCard.applyPowerToSelf(new HauntedDebuff(AbstractDungeon.player, AbstractDungeon.player, 1));
    }
    
    @Override
	public void onEquip()
	{
    	setDescription();
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
    
    public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
       // tips.add(new PowerTip("Haunted", this.DESCRIPTIONS[1]));
        initializeTips();
	}

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new HauntedRelic();
    }
}
