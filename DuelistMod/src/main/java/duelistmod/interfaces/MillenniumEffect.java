package duelistmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.actions.unique.TheCreatorAction;
import duelistmod.cards.*;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.relics.MillenniumPuzzle;

public class MillenniumEffect
{

	private static int baseSummons = 2;
	
	public static void runMillenniumEffect(int extra)
	{
		if (AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID))
		{
			MillenniumPuzzle puzzle = (MillenniumPuzzle) AbstractDungeon.player.getRelic(MillenniumPuzzle.ID);
			puzzle.flash();
		}
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			if (DuelistMod.fullDebug) { if (AbstractPlayer.customMods.size() < 1 && !DuelistMod.challengeMode) { runSpecialEffect(35 + extra); } else if (!DuelistMod.challengeMode) { DuelistCard.powerSummon(AbstractDungeon.player, 35, "Puzzle Token", false); } else { DuelistCard.summon(AbstractDungeon.player, 2, new ExplosiveToken("Exploding Token")); }}
			else
			{
				// Normal Runs
				if (AbstractPlayer.customMods.size() < 1 && !DuelistMod.challengeMode) { runSpecialEffect(extra); }
				
				// Custom Runs & No Challenge Mode
				else if (!DuelistMod.challengeMode) { DuelistCard.powerSummon(AbstractDungeon.player, 2 + extra, "Puzzle Token", false); }
				
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
            		floor += extra;
            		DuelistCard.heal(AbstractDungeon.player, floor + 1);
            	    break;
                case THE_SILENT:      
                	int floorB = AbstractDungeon.actNum;	
                	floorB += extra;
                	DuelistCard.draw(floorB);
                    break;
                case DEFECT:    
                	int floorC = AbstractDungeon.actNum;
                	floorC += extra;
                	int rolly = AbstractDungeon.cardRandomRng.random(1,4);
                	if (rolly == 1)
                	{
                		AbstractDungeon.player.increaseMaxOrbSlots(floorC + 1, true);
                	}
                	else if (rolly == 2 || rolly == 3)
                	{
                		AbstractDungeon.player.increaseMaxOrbSlots(1 + extra, true);
                	}
                	else 
                	{
                		DuelistCard.applyPowerToSelf(new FocusPower(AbstractDungeon.player, floorC + 1));
                	}
                    break;
                    
                // Modded Character
                default:
                	int floorD = AbstractDungeon.actNum;	
                	floorD += extra;
                	int roll = AbstractDungeon.cardRandomRng.random(1, 5);
                	switch (roll)
                	{
	                	case 1:
	                		DuelistCard.gainGold(floorD * 15, AbstractDungeon.player, true);
	                		break;
	                	case 2:
	                		DuelistCard.heal(AbstractDungeon.player, floorD);
	                		break;
	                	case 3:
	                		ArrayList<AbstractCard> modalCards = new ArrayList<AbstractCard>();
	                		for (int i = 0; i < floorD + 4; i++)
	                		{
	                			AbstractCard ref = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
	                			ref.baseDamage = ref.damage += floorD + 2;
	                			ref.isDamageModified = true;
	                			modalCards.add(ref);
	                		}
	                		new Token().openRandomCardChoiceAbstract(floorD + 2, modalCards);
	                		break;
	                	case 4:
	                		ArrayList<AbstractCard> modalCardsB = new ArrayList<AbstractCard>();
	                		for (int i = 0; i < floorD + 4; i++)
	                		{
	                			AbstractCard ref = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
	                			ref.upgrade();
	                			modalCardsB.add(ref);
	                		}
	                		new Token().openRandomCardChoiceAbstract(floorD + 2, modalCardsB);
	                		break;
	                	case 5:
	                		ArrayList<AbstractCard> modalCardsC = new ArrayList<AbstractCard>();
	                		for (int i = 0; i < floorD + 4; i++)
	                		{
	                			modalCardsC.add(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy());
	                		}
	                		new Token().openRandomCardChoiceAbstract(floorD + 2, modalCardsC);
	                		break;
	                	default:
	                		ArrayList<AbstractCard> modalCardsD = new ArrayList<AbstractCard>();
	                		for (int i = 0; i < floorD + 4; i++)
	                		{
	                			modalCardsD.add(AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy());
	                		}
	                		new Token().openRandomCardChoiceAbstract(floorD + 2, modalCardsD);
	                		break;
                	}
                    break;
			}
		}
	}
	
	private static void runSpecialEffect(int extra)
	{
		AbstractPlayer p = AbstractDungeon.player;
		switch (DuelistMod.getCurrentDeck().getIndex())
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
		    	DuelistCard.applyPowerToSelf(new NaturePower(p, p, 1));
				break;
	
			// Spellcaster Deck
			case 3:
				int rollS = AbstractDungeon.cardRandomRng.random(0, 2);
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra + rollS, "Spellcaster Token", false);
				break;
	
			// Creator Deck
			case 4:
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
					DuelistCard trap = new Jinzo();
					DuelistCard ultimate = new Jinzo();
					jinzo.costForTurn = 0; trap.costForTurn = 0; ultimate.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true; trap.isCostModifiedForTurn = true; ultimate.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo); DuelistCard.addCardToHand(trap); DuelistCard.addCardToHand(ultimate);
					
					// whenever you play an Ethereal card, gain 5 Gold and 2 HP
				}
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra, "Puzzle Token", false);
				break;
	
			// Random (Small) Deck
			case 5:
				// whenever you play a new card this run, gain 15 block
				int summonRollA = AbstractDungeon.cardRandomRng.random(2, 5);
				DuelistCard.powerSummon(AbstractDungeon.player, summonRollA + extra, "Puzzle Token", false);
				break;
	
			// Random (Big) Deck
			case 6:
				int summonRollB = AbstractDungeon.cardRandomRng.random(2, 4);
				DuelistCard.powerSummon(AbstractDungeon.player, summonRollB + extra, "Puzzle Token", false);
				ArrayList<DuelistCard> cardsToChooseFromB = new ArrayList<DuelistCard>();
				for (int i = 0; i < 4; i++)
				{
					DuelistCard randomToon = (DuelistCard) DuelistCard.returnTrulyRandomDuelistCard();
					while (cardsToChooseFromB.contains(randomToon))
					{
						randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.TOON);
					}
					cardsToChooseFromB.add(randomToon);
				}
				new Token().openRandomCardChoiceDuelist(3, cardsToChooseFromB, true);
				
				break;
	
			// Toon Deck
			case 7:		
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra, "Puzzle Token", false);
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
					new Token().openRandomCardChoiceDuelist(3, cardsToChooseFrom, false);
				}
				break;
	
			// Orb Deck
			case 8:
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Orb Token", false);
				new Token().openRandomOrbChoiceNoGlass(3);				
				break;
	
			// Resummon Deck
			case 9:				
				// whenever you resummon, gain 5 Block
				int rollR = AbstractDungeon.cardRandomRng.random(1, 2);
				if (rollR == 1)
				{
					DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Resummon Token", false);
				}				
				else
				{
					DuelistCard.powerSummon(AbstractDungeon.player, 2 + extra, "Resummon Token", false);
				}
				break;
				
			// Generation Deck
			case 10:
				// whenever you play a new card this run, add a random common duelist card to your hand
				int floorE = AbstractDungeon.actNum;
				for (int i = 0; i < 1 + floorE; i++)
				{
					DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomDuelistCard();
					AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
				}
				DuelistCard.powerSummon(AbstractDungeon.player, 5 + extra, "Bonanza Token", false);
				break;
	
			// Ojama Deck
			case 11:
				int floorD = AbstractDungeon.actNum;
				int rngTurnNum = AbstractDungeon.cardRandomRng.random(1, floorD + 1);
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Bonanza Token", false);
				DuelistCard.applyRandomBuffPlayer(p, rngTurnNum, false); 
				break;
	
			// Heal Deck
			case 12:
				int floorF = AbstractDungeon.actNum;
				int rng = AbstractDungeon.cardRandomRng.random(3, floorF + 4);
				DuelistCard.applyPowerToSelf(new OrbHealerPower(p, rng));
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;
	
			// Increment Deck
			case 13:
				int floorG = AbstractDungeon.actNum;
				DuelistCard.incMaxSummons(p, 1 + floorG);
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra + floorG, "Puzzle Token", false);
				break;
	
			// Exodia Deck
			case 14:
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra, "Exxod Token", false);
				break;
			// Superheavy Deck *
			case 15:
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra, "Puzzle Token", false);
				break;
				
			// Aqua Deck *
			case 16:
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra, "Puzzle Token", false);
				break;
				
			// Machine Deck *
			case 17:
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra, "Puzzle Token", false);
				break;
	
			// Generic
			default:
				DuelistCard.powerSummon(AbstractDungeon.player, baseSummons + extra, "Puzzle Token", false);
				break;
		}
	}

}
