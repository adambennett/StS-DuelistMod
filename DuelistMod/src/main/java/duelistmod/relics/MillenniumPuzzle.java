package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.*;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.variables.*;

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
	public void atPreBattle()
	{
		getDeckDesc();
		if (StarterDeckSetup.getCurrentDeck().getIndex() != DuelistMod.normalSelectDeck && DuelistMod.normalSelectDeck > -1)
		{
			DuelistMod.deckIndex = DuelistMod.normalSelectDeck;
			getDeckDesc();
		}
		PuzzleHelper.atBattleStartHelper(summons, extra);
		getDeckDesc();
		PuzzleHelper.spellcasterChannelAction();
		PuzzleHelper.zombieChannel();
	}
	
	@Override
	public void onUnequip()
	{
		DuelistMod.hasPuzzle = false;
	}
	
	@Override
	public void onEquip()
	{
		DuelistMod.hasPuzzle = true;
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
		/*
		getDeckDesc();
		if (StarterDeckSetup.getCurrentDeck().getIndex() != DuelistMod.normalSelectDeck && DuelistMod.normalSelectDeck > -1)
		{
			DuelistMod.deckIndex = DuelistMod.normalSelectDeck;
			getDeckDesc();
		}
		PuzzleHelper.atBattleStartHelper(summons, extra);
		getDeckDesc();
		*/
	}

	@Override
	public void atTurnStart()
	{
		if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck"))
		{
			DuelistCard.drawTag(1, Tags.EXODIA_HEAD);
			this.flash();
		}
	}
	
	@Override
	public void onVictory() 
	{
		boolean eliteVictory = false;
		if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) { eliteVictory = true; }
		if (!StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Metronome Deck"))
		{
			if (StarterDeckSetup.getCurrentDeck().getIndex() > 0 && StarterDeckSetup.getCurrentDeck().getIndex() < 14) { BoosterPackHelper.generateBoosterOnVictory(DuelistMod.lastPackRoll, eliteVictory, StarterDeckSetup.getCurrentDeck().tagsThatMatchCards); }
			else { BoosterPackHelper.generateBoosterOnVictory(DuelistMod.lastPackRoll, eliteVictory, null); }
		}
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
				if (AbstractDungeon.actNum == 0) { localdesc = DESCRIPTIONS[3] + 1 + DESCRIPTIONS[4]; }
				else { localdesc = DESCRIPTIONS[3] + AbstractDungeon.actNum + DESCRIPTIONS[4];}
				break;
	
			// Nature Deck
			case 2:			
				if (AbstractDungeon.actNum == 0) { localdesc = DESCRIPTIONS[5] + 1 + DESCRIPTIONS[6]; }
				else { localdesc = DESCRIPTIONS[5] + AbstractDungeon.actNum + DESCRIPTIONS[6]; }
				break;
	
			// Spellcaster Deck
			case 3:
				localdesc = DESCRIPTIONS[7] + DuelistMod.currentSpellcasterOrbChance + DESCRIPTIONS[8];
				break;
				
			// Toon Deck
			case 4:		
				localdesc = DESCRIPTIONS[9];
				break;
				
			// Zombie Deck
			case 5:		
				localdesc = DESCRIPTIONS[10];
				break;
				
			// Aqua Deck
			case 6:		
				localdesc = DESCRIPTIONS[11];
				break;
	
			// Fiend Deck
			case 7:		
				localdesc = DESCRIPTIONS[12];
				break;
	
			// Machine Deck
			case 8:		
				localdesc = DESCRIPTIONS[13];
				break;
				
			// Warrior Deck
			case 9:		
				localdesc = DESCRIPTIONS[14];
				break;
				
			// Insect Deck
			case 10:
				localdesc = DESCRIPTIONS[15];
				break;
			
			// Plant Deck
			case 11:
				localdesc = DESCRIPTIONS[16];
				break;
	
			// Predaplant Deck
			case 12:
				localdesc = DESCRIPTIONS[17];
				break;	
				
			// Megatype Deck
			case 13:
				localdesc = DESCRIPTIONS[18];
				break;
			
			// Increment Deck
			case 14:				
				localdesc = DESCRIPTIONS[19];
				break;
					
			// Creator Deck
			case 15:
				localdesc = DESCRIPTIONS[20];
				break;
				
			// Ojama Deck
			case 16:
				localdesc = DESCRIPTIONS[21];
				break;	
			
			// Exodia Deck
			case 17:
				localdesc = DESCRIPTIONS[22];
				break;	
	
			// Giants Deck
			case 18:
				localdesc = DESCRIPTIONS[23];
				break;
	
			// A1 Deck
			case 19:
				localdesc = DESCRIPTIONS[24];
				break;
				
			// A2 Deck	
			case 20:
				localdesc = DESCRIPTIONS[25];
				break;
				
			// A3 Deck	
			case 21:
				localdesc = DESCRIPTIONS[26];
				break;
				
			// P1 Deck
			case 22:
				localdesc = DESCRIPTIONS[27];
				break;
			
			// P2 Deck
			case 23:
				localdesc = DESCRIPTIONS[28];
				break;
				
			// P3 Deck
			case 24:
				localdesc = DESCRIPTIONS[29];
				break;
				
			// P4 Deck
			case 25:
				localdesc = DESCRIPTIONS[30];
				break;
				
			// P5 Deck
			case 26:
				localdesc = DESCRIPTIONS[31];
				break;
				
			// Random (Small) Deck
			case 27:
				localdesc = DESCRIPTIONS[32];
				break;
			
			// Random (Big) Deck
			case 28:
				localdesc = DESCRIPTIONS[33];
				break;
				
			// Random (Upgrade) Deck
			case 29:
				localdesc = DESCRIPTIONS[34];
				break;
			
			// Metronome Deck
			case 30:
				localdesc = DESCRIPTIONS[35];
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
