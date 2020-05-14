package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tokens.AnubisToken;
import duelistmod.variables.Strings;

public class GiftAnubis extends DuelistRelic implements ClickableRelic 
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("GiftAnubis");
    public static final String IMG = DuelistMod.makePath(Strings.GIFT_ANUBIS_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.GIFT_ANUBIS_RELIC_OUTLINE);
    // /FIELDS

    public GiftAnubis() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0]; }
    
    @Override
    public void onEquip()
    {
    	this.counter = 1;
    }
    
    @Override
    public void onVictory()
    {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
        {
            flash();
            setCounter(counter + 2);
        }
    }
    
    @Override
    public void atBattleStart() 
    {
       
    }

    @Override
    public void onRightClick() 
    {
    	if (this.counter > 0 && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
    	{
    		flash();
    		DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new AnubisToken());
			DuelistCard.summon(AbstractDungeon.player, 1, tok);
    		this.counter--;
    	}
    }
    
    @Override public AbstractRelic makeCopy() { return new GiftAnubis(); }
}
