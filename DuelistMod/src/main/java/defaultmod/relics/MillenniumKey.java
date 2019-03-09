package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

public class MillenniumKey extends CustomRelic 
{
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("MillenniumKey");
    public static final String IMG = DefaultMod.makePath(DefaultMod.M_KEY_RELIC);
    public static final String OUTLINE = DefaultMod.makePath(DefaultMod.M_KEY_RELIC_OUTLINE);

    public MillenniumKey() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() 
    {
    	flash();
    	DuelistCard.setMaxSummons(AbstractDungeon.player, 4);
    	try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DefaultMod.duelistDefaults);
			config.setInt(DefaultMod.PROP_MAX_SUMMONS, 4);
			config.save();
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:setMaxSummons() ---> ran try block, lastMaxSummons: " + DefaultMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Override
	public void onEquip()
	{
		DefaultMod.hasKey = true;
		AbstractDungeon.player.energy.energyMaster++;
		DuelistCard.setMaxSummons(AbstractDungeon.player, 4);
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DefaultMod.duelistDefaults);
			config.setInt(DefaultMod.PROP_MAX_SUMMONS, 4);
			config.setBool(DefaultMod.PROP_HAS_KEY, DefaultMod.hasKey);
			config.save();
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:setMaxSummons() ---> ran try block, lastMaxSummons: " + DefaultMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onUnequip()
	{
		AbstractDungeon.player.energy.energyMaster--;
		DefaultMod.hasKey = false;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DefaultMod.duelistDefaults);
			config.setInt(DefaultMod.PROP_MAX_SUMMONS, DefaultMod.lastMaxSummons);
			config.setBool(DefaultMod.PROP_HAS_KEY, DefaultMod.hasKey);
			config.save();
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:setMaxSummons() ---> ran try block, lastMaxSummons: " + DefaultMod.lastMaxSummons); }
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
