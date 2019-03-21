package defaultmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.actions.unique.TheCreatorAction;
import defaultmod.cards.*;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class MillenniumPuzzle extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DefaultMod.makeID("MillenniumPuzzle");
	public static final String IMG = DefaultMod.makePath(DefaultMod.M_PUZZLE_RELC);
	public static final String OUTLINE = DefaultMod.makePath(DefaultMod.M_PUZZLE_RELIC_OUTLINE);
	private static int SUMMONS = 2;

	public MillenniumPuzzle() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
	}

	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		this.flash();
		
		if (DefaultMod.debug)
		{
			if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
			{
				System.out.println("theDuelist:MillenniumPuzzle:atBattleStart() ---> correctly identified the duelist character");
			}
			else
			{
				System.out.println("theDuelist:MillenniumPuzzle:atBattleStart() ---> THIS SHOULD PRINT IF IT IS NOT THE DUELIST CHARACTER");
			}
		}
		
		//if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		//{
		
			if (DefaultMod.fullDebug)
			{
				
				// Normal Runs
				if (AbstractPlayer.customMods.size() < 1 && !DefaultMod.challengeMode) 
				{ 
					//DuelistCard.powerSummon(AbstractDungeon.player, 35, "Puzzle Token", false);
					runSpecialEffect(35); 
				}
				
				// Custom Runs & No Challenge Mode
				else if (!DefaultMod.challengeMode)
				{
					DuelistCard.powerSummon(AbstractDungeon.player, 35, "Puzzle Token", false);
				}
				
				// Challenge Mode (anywhere)
				else
				{
					DuelistCard.summon(AbstractDungeon.player, 2, new ExplosiveToken("Exploding Token"));
				}
			}
			else
			{
				// Normal Runs
				if (AbstractPlayer.customMods.size() < 1 && !DefaultMod.challengeMode) { runSpecialEffect(0); }
				
				// Custom Runs & No Challenge Mode
				else if (!DefaultMod.challengeMode)
				{
					DuelistCard.powerSummon(AbstractDungeon.player, 2, "Puzzle Token", false);
				}
				
				// Challenge Mode (anywhere)
				else
				{
					DuelistCard.summon(AbstractDungeon.player, 2, new ExplosiveToken("Exploding Token"));
				}
			}
		//}
		//else
		//{
			// Modded character
			// {
			// 	give a random buff
			// 	or add random cards to deck
			// 	or gain gold
			// 	or gain HP
			// }
			// Base Game Character
			// {
			//	special ironclad action
			//  special defect action
			//  special silent action
			// }
		//}
	}

	@Override
	public void atTurnStart()
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

	private static void runSpecialEffect(int extra)
	{
		AbstractPlayer p = AbstractDungeon.player;
		switch (DefaultMod.getCurrentDeck().getIndex())
		{
			// Standard Deck
			case 0:
				DuelistCard.powerSummon(AbstractDungeon.player, 3 + extra, "Puzzle Token", false);
				DuelistCard.applyPowerToSelf(new BlurPower(p, 1));
				DuelistCard.staticBlock(10);
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
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra + rollS, "Spellcaster Token", false);
				break;
	
			// Creator Deck
			case 4:
				int roll = AbstractDungeon.cardRandomRng.random(1, 9);
				if (roll == 1)
				{
					DuelistCard jinzo = new Jinzo();
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(15);
					
					// whenever you play a trap card, gain 1 HP
				}
				else if (roll == 2)
				{
					DuelistCard jinzo = new TrapHole();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(10);
					
					// whenever you summon, gain 1 HP
				}
				else if (roll == 3)
				{
					DuelistCard jinzo = new Jinzo();
					jinzo.upgrade();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(5);
					
					// whenever you play an Ethereal card, gain 1 HP
				}
				else if (roll == 4)
				{
					DuelistCard jinzo = new TrapHole();
					jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
					if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(15);
					
					// whenever you play an Ethereal card, gain 1 HP
				}
				else if (roll == 5)
				{
					DuelistCard jinzo = new Jinzo();
					jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
					if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(10);
					
					// whenever you play an Ethereal card, gain 2 HP
				}
				else if (roll == 6)
				{
					DuelistCard jinzo = new Jinzo();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(5);
					
					// whenever you play an Ethereal card, gain 5 Gold
				}
				else if (roll == 7)
				{
					DuelistCard jinzo = new TrapHole();
					jinzo.upgrade();
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(20);
					
					// whenever you play an Ethereal card, gain 1 Block (affected by dexterity)
				}
				else if (roll == 8)
				{
					DuelistCard jinzo = new UltimateOffering();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(15);
					
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
					DuelistCard.staticBlock(25);
					
					// whenever you play an Ethereal card, gain 5 Gold and 2 HP
				}
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
	
			// Random (Small) Deck
			case 5:
				// whenever you play a new card this run, gain 15 block
				int summonRollA = AbstractDungeon.cardRandomRng.random(2, 5);
				DuelistCard.powerSummon(AbstractDungeon.player, summonRollA + extra, "Puzzle Token", false);
				break;
	
			// Random (Big) Deck
			case 6:
				int summonRollB = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.powerSummon(AbstractDungeon.player, summonRollB + extra, "Puzzle Token", false);
				ArrayList<DuelistCard> cardsToChooseFromB = new ArrayList<DuelistCard>();
				for (int i = 0; i < 4; i++)
				{
					DuelistCard randomToon = (DuelistCard) DuelistCard.returnTrulyRandomDuelistCard();
					while (cardsToChooseFromB.contains(randomToon))
					{
						randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.TOON);
					}
					cardsToChooseFromB.add(randomToon);
				}
				new Token().openRandomCardChoice(3, cardsToChooseFromB);
				
				break;
	
			// Toon Deck
			case 7:				
				ArrayList<DuelistCard> cardsToChooseFrom = new ArrayList<DuelistCard>();
				for (int i = 0; i < 4; i++)
				{
					DuelistCard randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.TOON);
					while (cardsToChooseFrom.contains(randomToon))
					{
						randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.TOON);
					}
					cardsToChooseFrom.add(randomToon);
				}
				new Token().openRandomCardChoice(3, cardsToChooseFrom);
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
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
				for (int i = 0; i < 1 + floorD; i++)
				{
					DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.OJAMA);
					AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
				}
				DuelistCard.powerSummon(AbstractDungeon.player, 3 + extra, "Bonanza Token", false);
				// gain a random buff
				// apply a random debuff to all enemies
				break;
	
			// Heal Deck
			case 12:
				DuelistCard.applyPowerToSelf(new OrbHealerPower(p, 3));
				DuelistCard.powerSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;
	
			// Increment Deck
			case 13:
				DuelistCard.incMaxSummons(p, 2);
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
	
			// Exodia Deck
			case 14:
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Exxod Token", false);
				break;
				
			// Superheavy Deck *
			case 15:
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
				
			// Aqua Deck *
			case 16:
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
				
			// Machine Deck *
			case 17:
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
	
			// Generic
			default:
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
		}
	}
}
