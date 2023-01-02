package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class InsectRelic extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("InsectRelic");
	public static final String IMG = DuelistMod.makeRelicPath("InsectRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("InsectRelic_Outline.png");
	
	public InsectRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
		this.counter = 1;
		setDescription();
	}
	
	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Util.deckIs("Insect Deck");
	}
	
	@Override
	public void onEquip() {
		setDescription();
	}
	
	@Override
    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
        	int roll = AbstractDungeon.cardRandomRng.random(1, 1);
        	if (roll == 1)
        	{
        		flash();
                setCounter(counter + 1);
                setDescription();
        	}
        }
    }

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
	}
	
	public void setDescription() {
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	@Override
	public AbstractRelic makeCopy() {
		return new InsectRelic();
	}
}
