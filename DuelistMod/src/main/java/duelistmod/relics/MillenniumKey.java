package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.patches.DuelistCard;

public class MillenniumKey extends CustomRelic 
{
    // ID, images, text.
    public static final String ID = DuelistMod.makeID("MillenniumKey");
    public static final String IMG = DuelistMod.makePath(Strings.M_KEY_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.M_KEY_RELIC_OUTLINE);

    public MillenniumKey() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() 
    {
    	flash();
    	DuelistCard.setMaxSummons(AbstractDungeon.player, 5);
    	try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_MAX_SUMMONS, 5);
			config.save();
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:setMaxSummons() ---> ran try block, lastMaxSummons: " + DuelistMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Override
	public void onEquip()
	{
		DuelistMod.hasKey = true;
		AbstractDungeon.player.energy.energyMaster++;
		DuelistCard.setMaxSummons(AbstractDungeon.player, 5);
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_MAX_SUMMONS, 5);
			config.setBool(DuelistMod.PROP_HAS_KEY, DuelistMod.hasKey);
			config.save();
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:setMaxSummons() ---> ran try block, lastMaxSummons: " + DuelistMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onUnequip()
	{
		AbstractDungeon.player.energy.energyMaster--;
		DuelistMod.hasKey = false;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_MAX_SUMMONS, DuelistMod.lastMaxSummons);
			config.setBool(DuelistMod.PROP_HAS_KEY, DuelistMod.hasKey);
			config.save();
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:setMaxSummons() ---> ran try block, lastMaxSummons: " + DuelistMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new MillenniumKey();
    }
}
