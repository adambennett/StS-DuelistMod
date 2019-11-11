package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;
import com.megacrit.cardcrawl.rooms.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class InsectRelic extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("InsectRelic");
	public static final String IMG = DuelistMod.makeRelicPath("NatureRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("NatureRelic.png");
	
	public InsectRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
		this.counter = 1;
		setDescription();
	}
	
	@Override
	public boolean canSpawn()
	{
		if (Util.deckIs("Insect Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void onEquip()
	{
		setDescription();
		DuelistMod.insectPoisonDmg++;
	}
	
	@Override
    public void onVictory()
    {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
        {
        	//int roll = AbstractDungeon.cardRandomRng.random(1, 4);
        	int roll = AbstractDungeon.cardRandomRng.random(1, 1);
        	if (roll == 1)
        	{
        		flash();
                setCounter(counter + 1);
                DuelistMod.insectPoisonDmg++;
                setDescription();
        	}
        }
    }

	@Override
	public void onUnequip()
	{
		DuelistMod.insectPoisonDmg--;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new InsectRelic();
	}
}
