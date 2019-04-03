package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.*;

import basemod.abstracts.CustomRelic;
import duelistmod.*;

public class CardRewardRelicA extends CustomRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("CardRewardRelicA");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
    // /FIELDS

    public CardRewardRelicA() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public void onEquip()
    {
    	this.counter = 2;
    }
    
    @Override
    public void onVictory() 
    {
    	if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
        {
            flash();
            setCounter(counter + 1);
        }
    	
    	if (this.counter > 0)
    	{
    		flash();
	    	for (int i = 0; i < 5; i++) { AbstractDungeon.getCurrRoom().addCardToRewards(); }
	    	setCounter(counter - 1);
    	}
    }
}
