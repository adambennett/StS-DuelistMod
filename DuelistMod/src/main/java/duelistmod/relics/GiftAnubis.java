package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.cards.tokens.Token;
import duelistmod.interfaces.DuelistCard;

public class GiftAnubis extends CustomRelic implements ClickableRelic 
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
    	if (this.counter > 0)
    	{
    		flash();
    		DuelistCard.summon(AbstractDungeon.player, 1, new Token("Anubis Token"));
    		this.counter--;
    	}
    }
    
    @Override public AbstractRelic makeCopy() { return new GiftAnubis(); }
}
