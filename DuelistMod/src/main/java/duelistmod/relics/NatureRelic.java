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

public class NatureRelic extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("NatureRelic");
	public static final String IMG = DuelistMod.makeRelicPath("NatureRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("NatureRelic.png");
	
	public NatureRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
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
	}
	
	@Override
    public void onVictory()
    {
        if (DuelistMod.poisonAppliedThisCombat > 34) 
        {
        	if (DuelistMod.removeCardRewards && (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters)) 
        	{
        		boolean eliteVictory = AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite;
        		boolean boss = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
        		if (!StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Metronome Deck") && !boss)
        		{
        			if (StarterDeckSetup.getCurrentDeck().getIndex() > 0 && StarterDeckSetup.getCurrentDeck().getIndex() < 14) { BoosterPackHelper.generateBoosterOnVictory(DuelistMod.lastPackRoll, eliteVictory, StarterDeckSetup.getCurrentDeck().tagsThatMatchCards); }
        			else { BoosterPackHelper.generateBoosterOnVictory(DuelistMod.lastPackRoll, eliteVictory, null); }
        		}
        	}
        	else if (!DuelistMod.removeCardRewards) { AbstractDungeon.getCurrRoom().addCardToRewards(); }	
        }
    }

	@Override
	public void onUnequip()
	{
		
	}

	// Description
	@Override
	public String getUpdatedDescription() 
	{
		if (DuelistMod.removeCardRewards && (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters)) { return DESCRIPTIONS[1]; }
		else { return DESCRIPTIONS[0]; }
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
		return new NatureRelic();
	}
}
