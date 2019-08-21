package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.*;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.variables.Strings;

public class CardRewardRelicB extends CustomRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("CardRewardRelicB");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
    // /FIELDS

    public CardRewardRelicB() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
	public boolean canSpawn()
	{
    	if (DuelistMod.hasCardRewardRelic || DuelistMod.removeCardRewards) { return false; }
		else { return true; }
	}
    
    @Override
    public void onEquip()
    {
    	this.counter = 1;
        DuelistMod.hasCardRewardRelic = true;        
        try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setBool(DuelistMod.PROP_CARD_REWARD_RELIC, DuelistMod.hasCardRewardRelic);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
    }
    
    @Override
    public void onUnequip()
    {
        DuelistMod.hasCardRewardRelic = false;        
        try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setBool(DuelistMod.PROP_CARD_REWARD_RELIC, DuelistMod.hasCardRewardRelic);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
    }
    
    @Override
    public void onVictory() 
    {
    	if (this.counter > 0)
    	{
    		flash();
	    	for (int i = 0; i < 2; i++) { AbstractDungeon.getCurrRoom().addCardToRewards(); }
	    	setCounter(counter - 1);
    	}
    	
    	if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
        {
            flash();
            setCounter(counter + 1);
        }
    }
}
