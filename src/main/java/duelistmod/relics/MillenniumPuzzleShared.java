package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.PuzzleHelper;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.variables.Strings;

public class MillenniumPuzzleShared extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumPuzzleShared");
	public static final String IMG = DuelistMod.makePath(Strings.M_PUZZLE_RELC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_PUZZLE_RELIC_OUTLINE);
	private static int SUMMONS = 2;
	
	public MillenniumPuzzleShared() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		// Only spawn for non-Duelist characters
		if (AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) { return false; }
		else { return true; }
	}
	
	@Override
	public void onEquip()
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			setDescription("You shouldn't have this...");
		}
		else
		{
			switch (AbstractDungeon.player.chosenClass)
			{
				case IRONCLAD:
	        		setDescription("At the start of combat, heal 1 HP for each Act you have completed.");
	        	    break;
	            case THE_SILENT:            	
	            	setDescription("At the start of combat, draw 1 Rare.");
	                break;
	            case DEFECT:         	
	            	setDescription("At the start of combat, increase your Orb slots by a random amount or gain Focus. Chosen randomly. The amount of Focus and Orb slots increases for each Act you complete.");
	                break;            
	            default:
	            	setDescription("At the start of combat, either heal or gain gold (chosen randomly).");            	
	                break;
			}
		}
	}
	
	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		this.flash();
		PuzzleHelper.atBattleStartHelper(SUMMONS, 0);
		getDeckDesc();
	}

	@Override
	public void atTurnStart()
	{
		
	}
	
	@Override
	public void onVictory() 
	{
	}

	// Description
	@Override
	public String getUpdatedDescription() 
	{		
		return DESCRIPTIONS[0];		
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumPuzzleShared();
	}

	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
	
	public void setDescription(String desc)
	{
		description = desc;
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
	
	public void getDeckDesc()
	{
		switch (AbstractDungeon.player.chosenClass)
		{
			case IRONCLAD:
        		setDescription("At the start of combat, heal 1 HP for each Act you have completed.");
        	    break;
            case THE_SILENT:            	
            	setDescription("At the start of combat, draw 1 card for each Act you have completed.");
                break;
            case DEFECT:         	
            	setDescription("At the start of combat, increase your Orb slots by a random amount or gain Focus. Chosen randomly. The amount of Focus and Orb slots increases for each Act you complete.");
                break;            
            default:
            	setDescription("At the start of combat, either heal or gain gold (chosen randomly).");            	
                break;
		}
	}
}
