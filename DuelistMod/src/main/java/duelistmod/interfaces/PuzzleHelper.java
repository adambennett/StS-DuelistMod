package duelistmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.actions.unique.TheCreatorAction;
import duelistmod.cards.*;
import duelistmod.cards.tokens.ExplosiveToken;
import duelistmod.patches.*;
import duelistmod.powers.*;

public class PuzzleHelper 
{
	
	public static void atBattleStartHelper(int summons, int extra)
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			//getDeckDesc();
			if (DuelistMod.fullDebug) { if (AbstractPlayer.customMods.size() < 1 && !DuelistMod.challengeMode) { runSpecialEffect(summons, 35 + extra); } else if (!DuelistMod.challengeMode) { DuelistCard.powerSummon(AbstractDungeon.player, 35, "Puzzle Token", false); } else { DuelistCard.summon(AbstractDungeon.player, 2, new ExplosiveToken("Exploding Token")); }}
			else
			{
				// Normal Runs
				if (AbstractPlayer.customMods.size() < 1 && !DuelistMod.challengeMode) { runSpecialEffect(summons, extra); }
				
				// Custom Runs & No Challenge Mode
				else if (!DuelistMod.challengeMode) { DuelistCard.powerSummon(AbstractDungeon.player, summons + extra, "Puzzle Token", false); }
				
				// Challenge Mode (anywhere)
				else { DuelistCard.summon(AbstractDungeon.player, summons - 1 + extra, new ExplosiveToken("Exploding Token")); }
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
	}

	public static void runSpecialEffect(int SUMMONS, int extra)
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
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Dragon Token", false);
				DuelistCard.applyPowerToSelf(new StrengthPower(p, floor));
				break;
	
			// Nature Deck
			case 2:				
				PuzzleHelper.natureDeckAction(extra);
		    	DuelistCard.applyPowerToSelf(new NaturePower(p, p, 1));
				break;
	
			// Spellcaster Deck
			case 3:
				int rollS = AbstractDungeon.cardRandomRng.random(0 + extra, 2 + extra);
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra + rollS, "Spellcaster Token", false);
				break;
				
			// Toon Deck
			case 4:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Toon Token", false);
				PuzzleHelper.toonDeckAction(p, extra);
				break;
				
			// Zombie Deck
			case 5:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Zombie Token", false);
				break;
				
			// Aqua Deck
			case 6:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Aqua Token", false);
				break;

			// Fiend Deck
			case 7:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Fiend Token", false);
				break;

			// Machine Deck
			case 8:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Machine Token", false);
				break;
				
			// Superheavy Deck
			case 9:		
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Superheavy Token", false);
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
				if (DuelistMod.orbCards.size() > 3)
				{
					ArrayList<DuelistCard> orbs = new ArrayList<DuelistCard>();
					for (int i = 0; i < 3; i++)
					{
						DuelistCard random = DuelistMod.orbCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.orbCards.size() - 1));
						while (orbs.contains(random)) { random = DuelistMod.orbCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.orbCards.size() - 1)); }
						orbs.add((DuelistCard)random.makeCopy());
					}
					AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(orbs, 1, false, false, false));
				}
				//new Token().openRandomOrbChoiceNoGlass(3);				
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
	
	public static void natureDeckAction(int extra)
	{
		int rollN = AbstractDungeon.cardRandomRng.random(1, 6);
    	switch (rollN)
    	{
	    	case 1:
	    		DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Plant Token", false);
	    		break;
	    	case 2:
	    		DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Insect Token", false);
	    		break;
	    	case 3:
	    		DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Predaplant Token", false);
	    		break;
	    	case 4:
	    		DuelistCard.powerSummon(AbstractDungeon.player, 2 + extra, "Plant Token", false);
	    		break;
	    	case 5:
	    		DuelistCard.powerSummon(AbstractDungeon.player, 2 + extra, "Insect Token", false);
	    		break;
	    	case 6:
	    		DuelistCard.powerSummon(AbstractDungeon.player, 2 + extra, "Predaplant Token", false);			    		
	    		break;
	    	default:
	    		DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Insect Token", false);
	    		break;
    	
		}
	}
	
	public static void toonDeckAction(AbstractPlayer p, int extra)
	{
		if (!DuelistMod.toonBtnBool)
		{
			DuelistMod.toonWorldTemp = true;
			DuelistCard.applyPowerToSelf(new ToonWorldPower(p, p, 0, false));
			ArrayList<DuelistCard> cardsToChooseFrom = new ArrayList<DuelistCard>();
			for (int i = 0; i < 4; i++)
			{
				DuelistCard randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.TOON);
				while (cardsToChooseFrom.contains(randomToon) || randomToon.originalName.equals("Toon Mask"))
				{
					randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.TOON);
				}
				cardsToChooseFrom.add(randomToon);
			}
			AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(cardsToChooseFrom, 1, false, false, false));
			//new Token().openRandomCardChoiceDuelist(3, cardsToChooseFrom, false);
		}
	}
	
	public static void creatorDeckAction(int extra)
	{
		int roll = AbstractDungeon.cardRandomRng.random(1, 9);
		if (roll == 1)
		{
			DuelistCard jinzo = new Jinzo();
			DuelistCard.addCardToHand(jinzo);

			
			// whenever you play a trap card, gain 1 HP
		}
		else if (roll == 2)
		{
			DuelistCard jinzo = new TrapHole();
			jinzo.costForTurn = 0;
			jinzo.isCostModifiedForTurn = true;
			DuelistCard.addCardToHand(jinzo);

			
			// whenever you summon, gain 1 HP
		}
		else if (roll == 3)
		{
			DuelistCard jinzo = new Jinzo();
			jinzo.upgrade();
			jinzo.costForTurn = 0;
			jinzo.isCostModifiedForTurn = true;
			DuelistCard.addCardToHand(jinzo);

			
			// whenever you play an Ethereal card, gain 1 HP
		}
		else if (roll == 4)
		{
			DuelistCard jinzo = new TrapHole();
			jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
			if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
			DuelistCard.addCardToHand(jinzo);

			
			// whenever you play an Ethereal card, gain 1 HP
		}
		else if (roll == 5)
		{
			DuelistCard jinzo = new Jinzo();
			jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
			if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
			DuelistCard.addCardToHand(jinzo);

			
			// whenever you play an Ethereal card, gain 2 HP
		}
		else if (roll == 6)
		{
			DuelistCard jinzo = new Jinzo();
			jinzo.costForTurn = 0;
			jinzo.isCostModifiedForTurn = true;
			DuelistCard.addCardToHand(jinzo);

			
			// whenever you play an Ethereal card, gain 5 Gold
		}
		else if (roll == 7)
		{
			DuelistCard jinzo = new TrapHole();
			jinzo.upgrade();
			DuelistCard.addCardToHand(jinzo);

			// whenever you play an Ethereal card, gain 1 Block (affected by dexterity)
		}
		else if (roll == 8)
		{
			DuelistCard jinzo = new UltimateOffering();
			jinzo.costForTurn = 0;
			jinzo.isCostModifiedForTurn = true;
			DuelistCard.addCardToHand(jinzo);
			
			// whenever you increment, gain 4 HP
		}
		else if (roll == 9)
		{
			DuelistCard jinzo = new Jinzo();
			DuelistCard trap = new TrapHole();
			DuelistCard ultimate = new UltimateOffering();
			jinzo.costForTurn = 0; trap.costForTurn = 0; ultimate.costForTurn = 0;
			jinzo.isCostModifiedForTurn = true; trap.isCostModifiedForTurn = true; ultimate.isCostModifiedForTurn = true;
			DuelistCard.addCardToHand(jinzo); DuelistCard.addCardToHand(trap); DuelistCard.addCardToHand(ultimate);
			
			// whenever you play an Ethereal card, gain 5 Gold and 2 HP
		}
		DuelistCard.powerSummon(AbstractDungeon.player, 2 + extra, "Puzzle Token", false);
	}
}
