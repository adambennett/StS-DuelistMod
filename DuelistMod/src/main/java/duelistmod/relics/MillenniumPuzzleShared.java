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
	
	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		this.flash();
		PuzzleHelper.atBattleStartHelper(SUMMONS, 0);
		getDeckDesc();
		/*if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			//getDeckDesc();
			if (DuelistMod.fullDebug) { if (AbstractPlayer.customMods.size() < 1 && !DuelistMod.challengeMode) { runSpecialEffect(35); } else if (!DuelistMod.challengeMode) { DuelistCard.powerSummon(AbstractDungeon.player, 35, "Puzzle Token", false); } else { DuelistCard.summon(AbstractDungeon.player, 2, new ExplosiveToken("Exploding Token")); }}
			else
			{
				// Normal Runs
				if (AbstractPlayer.customMods.size() < 1 && !DuelistMod.challengeMode) { runSpecialEffect(0); }
				
				// Custom Runs & No Challenge Mode
				else if (!DuelistMod.challengeMode) { DuelistCard.powerSummon(AbstractDungeon.player, 2, "Puzzle Token", false); }
				
				// Challenge Mode (anywhere)
				else { DuelistCard.summon(AbstractDungeon.player, 1, new ExplosiveToken("Exploding Token")); }
			}
		}
		else
		{
			switch (AbstractDungeon.player.chosenClass)
			{
            	case IRONCLAD:
            		int floor = AbstractDungeon.actNum;				
            		DuelistCard.heal(AbstractDungeon.player, floor);
            		//setDescription("IRONCLAD: At the start of combat, heal 1 HP for each Act.");
            	    break;
                case THE_SILENT:      
                	int floorB = AbstractDungeon.actNum;	
                	DuelistCard.draw(floorB);
                	//setDescription("SILENT: At the start of combat, draw 1 card for each Act.");
                    break;
                case DEFECT:    
                	int floorC = AbstractDungeon.actNum;
                	int rolly = AbstractDungeon.cardRandomRng.random(1,4);
                	if (rolly == 1) { AbstractDungeon.player.increaseMaxOrbSlots(floorC + 1, true); }
                	else if (rolly == 2 || rolly == 3) { AbstractDungeon.player.increaseMaxOrbSlots(1, true); }
                	else { DuelistCard.applyPowerToSelf(new FocusPower(AbstractDungeon.player, floorC + 1)); }
                	//setDescription("DEFECT: At the start of combat, increase your Orb slots by a random amount or gain Focus. Chosen randomly. The amount of Focus and Orb slots increases the more Acts you complete.");
                    break;
                    
                // Modded Character
                default:
                	//setDescription("MODDED CHAR: At the start of combat, randomly choose to heal or gain gold.");
                	int floorD = AbstractDungeon.actNum;	
                	int roll = AbstractDungeon.cardRandomRng.random(1, 2);
                	switch (roll)
                	{
	                	case 1:
	                		DuelistCard.gainGold(floorD * 15, AbstractDungeon.player, true);
	                		break;
	                	case 2:
	                		DuelistCard.heal(AbstractDungeon.player, floorD);
	                		break;	                	
	                	default:
	                		DuelistCard.gainGold(floorD * 15, AbstractDungeon.player, true);
	                		break;
                	}
                    break;
			}
		}
		*/
		
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

	/*
	private static void runSpecialEffect(int extra)
	{
		AbstractPlayer p = AbstractDungeon.player;
		switch (StarterDeckSetup.getCurrentDeck().getIndex())
		{
			// Standard Deck
			case 0:
				DuelistCard.powerSummon(AbstractDungeon.player, 2 + extra, "Puzzle Token", false);
				DuelistCard.applyPowerToSelf(new BlurPower(p, 1));
				DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, 10));
				break;
	
			// Dragon Deck
			case 1:
				int floor = AbstractDungeon.actNum;				
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				DuelistCard.applyPowerToSelf(new StrengthPower(p, floor));
				break;
	
			// Nature Deck
			case 2:				
				PuzzleHelper.natureDeckAction(extra);
		    	DuelistCard.applyPowerToSelf(new NaturePower(p, p, 1));
				break;
	
			// Spellcaster Deck
			case 3:
				int rollS = AbstractDungeon.cardRandomRng.random(0, 2);
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra + rollS, "Spellcaster Token", false);
				break;
				
			// Toon Deck
			case 4:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				PuzzleHelper.toonDeckAction(p, extra);
				break;
				
			// Zombie Deck
			case 5:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
				
			// Aqua Deck
			case 6:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;

			// Fiend Deck
			case 7:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;

			// Machine Deck
			case 8:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
				
			// Superheavy Deck
			case 9:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
				
			// Creator Deck
			case 10:
				PuzzleHelper.creatorDeckAction(extra);
				break;
			
			// Ojama Deck
			case 11:
				int floorD = AbstractDungeon.actNum;
				int rngTurnNum = AbstractDungeon.cardRandomRng.random(1, floorD + 1);
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Bonanza Token", false);
				DuelistCard.applyRandomBuffPlayer(p, rngTurnNum, false); 
				break;
	
			// Generation Deck
			case 12:
				// whenever you play a new card this run, add a random common duelist card to your hand
				int floorE = AbstractDungeon.actNum;
				for (int i = 0; i < 1 + floorE; i++)
				{
					DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomDuelistCard();
					AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
				}
				DuelistCard.powerSummon(AbstractDungeon.player, 5 + extra, "Bonanza Token", false);
				break;	
				
			// Orb Deck
			case 13:
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Orb Token", false);
				new Token().openRandomOrbChoiceNoGlass(3);				
				break;
			
			// Resummon Deck
			case 14:				
				DuelistCard.powerSummon(AbstractDungeon.player, 2 + extra, "Resummon Token", false);
				break;
					
			// Increment Deck
			case 15:
				int floorG = AbstractDungeon.actNum;
				DuelistCard.incMaxSummons(p, 1 + floorG);
				DuelistCard.powerSummon(AbstractDungeon.player, extra + floorG, "Puzzle Token", false);
				break;
				
			// Exodia Deck
			case 16:
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Exxod Token", false);
				DuelistCard.draw(2);
				break;	
			
			// Heal Deck
			case 17:
				int floorF = AbstractDungeon.actNum;
				int rng = AbstractDungeon.cardRandomRng.random(3, floorF + 4);
				DuelistCard.applyPowerToSelf(new OrbHealerPower(p, rng));
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;	

			// Random (Small) Deck
			case 18:
				// whenever you play a new card this run, gain 15 block
				int summonRollA = AbstractDungeon.cardRandomRng.random(2, 5);
				DuelistCard.powerSummon(AbstractDungeon.player, summonRollA + extra, "Puzzle Token", false);
				break;
	
			// Random (Big) Deck
			case 19:
				int summonRollB = AbstractDungeon.cardRandomRng.random(2, 4);
				DuelistCard.powerSummon(AbstractDungeon.player, summonRollB + extra, "Puzzle Token", false);
				break;

			// Generic
			default:
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
		}
	}
	*/
	
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
		switch (deck)
		{
		
		//Stanard Deck
		case 0:
			localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens, gain a random amount (0-10) of #yBlock and gain #b1 #yBlur.";
			break;
		// Dragon Deck
		case 1:
			localdesc = "At the start of combat, #ySummon #b1 Puzzle Token and gain #b1 #yStrength for each Act.";
			break;

		// Nature Deck
		case 2:				
			localdesc = "At the start of combat, #ySummon #b1 or #b2 Insect/Plant/Predaplant Tokens and gain Nature's Blessing.";
			break;

		// Spellcaster Deck
		case 3:
			localdesc = "At the start of combat, #ySummon #b2 Spellcaster Tokens.";
			break;
			
		// Toon Deck
		case 4:		
			localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens and play #b1 of #b3 random #yToon cards for free.";
			break;
			
		// Zombie Deck
		case 5:		
			localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens.";
			break;
			
		// Aqua Deck
		case 6:		
			localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens.";
			break;

		// Fiend Deck
		case 7:		
			localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens.";
			break;

		// Machine Deck
		case 8:		
			localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens.";
			break;
			
		// Superheavy Deck
		case 9:		
			localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens.";
			break;
			
		// Creator Deck
		case 10:
			localdesc = "At the start of combat, #ySummon #b2 Puzzle Tokens, and add a random combination of #yRandomized copies of Jinzo, Trap Hole and Ultimate Offering to your hand.";
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
			localdesc = "At the start of combat, #ySummon #2 Resummon Tokens.";
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
		setDescription(localdesc);
	}
}
