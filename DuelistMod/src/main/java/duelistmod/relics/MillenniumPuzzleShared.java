package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.interfaces.PuzzleHelper;
import duelistmod.patches.TheDuelistEnum;

public class MillenniumPuzzleShared extends CustomRelic {

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
			getDeckDesc();
		}
		else
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
		String localdesc = "empty";
		int deck = StarterDeckSetup.getCurrentDeck().getIndex();
		if (DuelistMod.challengeMode)
		{
			localdesc = "Challenge Mode: #ySummon #b1 [#FF5252]Exploding [#FF5252]Token at the start of combat. You start each combat with 4 max summons.";
		}
		else
		{
			switch (deck)
			{
			
			//Standard Deck
			case 0:
				localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens, gain a random amount (0-10) of #yBlock and gain #b1 #yBlur.";
				break;
			// Dragon Deck
			case 1:
				localdesc = "At the start of combat, #ySummon #b1 #yDragon Token and apply #b" + AbstractDungeon.actNum + " #yWeak or #yVulnerable to a random enemy.";
				break;
	
			// Nature Deck
			case 2:				
				localdesc = "At the start of combat, #ySummon #b1 or #b2 #yInsect/Plant/Predaplant Tokens and gain Nature's Blessing.";
				break;
	
			// Spellcaster Deck
			case 3:
				localdesc = "At the start of combat, #ySummon #b1 #ySpellcaster Token, and have a chance to #yChannel a random Orb.";
				break;
				
			// Toon Deck
			case 4:		
				localdesc = "At the start of combat, #ySummon #b2 #yToon Tokens and play #b1 of #b3 random #yToon cards for free.";
				break;
				
			// Zombie Deck
			case 5:		
				localdesc = "At the start of combat, #ySummon #b2 #yZombie Tokens and add a #yRandomized #yZombie to your hand.";
				break;
				
			// Aqua Deck
			case 6:		
				if (AbstractDungeon.actNum < 2) { localdesc = "At the start of combat, #ySummon #b1 #yAqua Token and have a chance to draw #b" + AbstractDungeon.actNum + " card."; }
				else { localdesc = "At the start of combat, #ySummon #b1 #yAqua Token and have a chance to draw #b" + AbstractDungeon.actNum + " cards."; }
				break;
	
			// Fiend Deck
			case 7:		
				localdesc = "At the start of combat, sum up the total #yTribute cost of ALL monsters in your draw pile and increase the damage of a random monster in you draw pile by that much for the rest of combat, and give that monster #yExhaust. Additonally, #ySummon #b1 #yFiend Token.";
				break;
	
			// Machine Deck
			case 8:		
				localdesc = "At the start of combat, #ySummon #b1 #yMachine Token and gain #b1 #yArtifact.";
				break;
				
			// Superheavy Deck
			case 9:		
				localdesc = "At the start of combat, #ySummon #b1 #ySuperheavy Token, gain a random amount (0-5) of #yBlock and gain #b2 #yBlur.";
				break;
				
			// Creator Deck
			case 10:
				localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens, and add a random combination of #yRandomized copies of #bJinzo, #bTrap Hole and #bUltimate #bOffering to your hand.";
				break;
			
			// Ojama Deck
			case 11:
				localdesc = "At the start of combat, #ySummon a random amount of Bonanza Tokens and gain a random #yBuff.";
				break;
	
			// Generation Deck
			case 12:
				localdesc = "At the start of combat, #ySummon #b5 Bonanza Tokens and add some random Duelist cards to your draw pile.";
				break;	
				
			// Orb Deck
			case 13:
				localdesc = "At the start of combat, #ySummon #b1 Orb Token and #yChannel #b1 of #b3 random Orbs.";
				break;
			
			// Resummon Deck
			case 14:				
				localdesc = "At the start of combat, #ySummon #2 #yResummon Tokens.";
				break;
					
			// Increment Deck
			case 15:
				localdesc = "At the start of combat, #yIncrement and #ySummon #b1 for each Act.";
				break;
				
			// Exodia Deck
			case 16:
				localdesc = "At the start of combat, #ySummon #b1 Exxod Token and draw #b2 extra cards.";
				break;	
			
			// Heal Deck
			case 17:
				localdesc = "At the start of combat, #ySummon #b1 Puzzle Token and gain Gaze of Anubis.";
				break;	
	
			// Random (Small) Deck
			case 18:
				localdesc = "At the start of combat, #ySummon a random amount #b(2-5) of Puzzle Tokens.";
				break;
	
			// Random (Big) Deck
			case 19:
				localdesc = "At the start of combat, #ySummon a random amount #b(2-4) of Puzzle Tokens.";
				break;
	
			// Generic
			default:
				localdesc = "Failed to find a deck string, unfortunately.";
				break;
			}
		}
		setDescription(localdesc);
	}
}
