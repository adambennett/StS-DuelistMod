package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.interfaces.PuzzleHelper;
import duelistmod.patches.TheDuelistEnum;

public class MillenniumPuzzle extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumPuzzle");
	public static final String IMG = DuelistMod.makePath(Strings.M_PUZZLE_RELC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_PUZZLE_RELIC_OUTLINE);
	private static int summons = 2;
	public int extra = 0;
	
	public MillenniumPuzzle() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			getDeckDesc();
		}
		else
		{
			switch (AbstractDungeon.player.chosenClass)
			{
				case IRONCLAD:
	        		setDescription("At the start of combat, heal 1 HP for each Act.");
	        	    break;
	            case THE_SILENT:            	
	            	setDescription("At the start of combat, draw 1 card for each Act.");
	                break;
	            case DEFECT:         	
	            	setDescription("At the start of combat, increase your Orb slots by a random amount or gain Focus. Chosen randomly. The amount of Focus and Orb slots increases the more Acts you complete.");
	                break;            
	            default:
	            	setDescription("At the start of combat, randomly choose to heal or gain gold.");            	
	                break;
			}
		}
	}
	
	public void setExtra(int amount)
	{
		this.extra = amount;
	}

	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		this.flash();
		getDeckDesc();
		if (StarterDeckSetup.getCurrentDeck().getIndex() != DuelistMod.normalSelectDeck && DuelistMod.normalSelectDeck > -1)
		{
			DuelistMod.deckIndex = DuelistMod.normalSelectDeck;
			getDeckDesc();
		}
		PuzzleHelper.atBattleStartHelper(summons, extra);
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
		return new MillenniumPuzzle();
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
		String localdesc = "empty";
		int deck = DuelistMod.deckIndex;
		if (DuelistMod.challengeMode)
		{
			localdesc = DESCRIPTIONS[1];
		}
		else
		{
			switch (deck)
			{
			
			//Standard Deck
			case 0:
				localdesc = DESCRIPTIONS[2];
				break;
			// Dragon Deck
			case 1:
				localdesc = DESCRIPTIONS[3] + AbstractDungeon.actNum + DESCRIPTIONS[4];
				break;
	
			// Nature Deck
			case 2:				
				localdesc = DESCRIPTIONS[5];
				break;
	
			// Spellcaster Deck
			case 3:
				localdesc = DESCRIPTIONS[6];
				break;
				
			// Toon Deck
			case 4:		
				localdesc = DESCRIPTIONS[7];
				break;
				
			// Zombie Deck
			case 5:		
				localdesc = DESCRIPTIONS[8];
				break;
				
			// Aqua Deck
			case 6:		
				if (AbstractDungeon.actNum < 2) { localdesc = DESCRIPTIONS[9] + AbstractDungeon.actNum + DESCRIPTIONS[10]; }
				else { localdesc = DESCRIPTIONS[9] + AbstractDungeon.actNum + DESCRIPTIONS[11]; }
				break;
	
			// Fiend Deck
			case 7:		
				localdesc = DESCRIPTIONS[12];
				break;
	
			// Machine Deck
			case 8:		
				localdesc = DESCRIPTIONS[13];
				break;
				
			// Superheavy Deck
			case 9:		
				localdesc = DESCRIPTIONS[14];
				break;
				
			// Creator Deck
			case 10:
				localdesc = DESCRIPTIONS[15];
				break;
			
			// Ojama Deck
			case 11:
				localdesc = DESCRIPTIONS[16];
				break;
	
			// Generation Deck
			case 12:
				localdesc = DESCRIPTIONS[17];
				break;	
				
			// Orb Deck
			case 13:
				localdesc = DESCRIPTIONS[18];
				break;
			
			// Resummon Deck
			case 14:				
				localdesc = DESCRIPTIONS[19];
				break;
					
			// Increment Deck
			case 15:
				localdesc = DESCRIPTIONS[20];
				break;
				
			// Exodia Deck
			case 16:
				localdesc = DESCRIPTIONS[21];
				break;	
			
			// Heal Deck
			case 17:
				localdesc = DESCRIPTIONS[22];
				break;	
	
			// Random (Small) Deck
			case 18:
				localdesc = DESCRIPTIONS[23];
				break;
	
			// Random (Big) Deck
			case 19:
				localdesc = DESCRIPTIONS[24];
				break;
	
			// Generic
			default:
				localdesc = "Failed to find a deck string.";
				break;
			}
		}
		setDescription(localdesc);
	}
}
