package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.cards.*;
import duelistmod.cards.insects.Bixi;
import duelistmod.cards.tokens.ExplosiveToken;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.relics.*;
import duelistmod.variables.Tags;

public class PuzzleHelper 
{
	
	public static void atBattleStartHelper(int summons, int extra)
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			if (DuelistMod.fullDebug) 
			{
				if (AbstractPlayer.customMods.size() < 1 && !DuelistMod.challengeMode) { runSpecialEffect(summons, 35 + extra); }
				else if (!DuelistMod.challengeMode) { DuelistCard.puzzleSummon(AbstractDungeon.player, 35, "Puzzle Token", false); }
				else if (!AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) { DuelistCard.summon(AbstractDungeon.player, 2, new ExplosiveToken()); }
			}
			else
			{
				// Normal Runs
				if (AbstractPlayer.customMods.size() < 1 && !DuelistMod.challengeMode) { runSpecialEffect(summons, extra); }
				
				// Custom Runs & No Challenge Mode
				else if (!DuelistMod.challengeMode) { DuelistCard.puzzleSummon(AbstractDungeon.player, summons + extra, "Puzzle Token", false); }
				
				// Challenge Mode (anywhere)
				else if (!AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) { DuelistCard.summon(AbstractDungeon.player, summons - 1 + extra, new ExplosiveToken()); }
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
                	DuelistCard.drawRare(1, CardRarity.RARE);
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
	
	public static void spellcasterChannelAction()
	{
		// for debug - always channel
		//DuelistCard.spellcasterPuzzleChannel();
		//DuelistMod.spellcasterDidChannel = true;
		//DuelistMod.currentSpellcasterOrbChance = 100;
		if (StarterDeckSetup.getCurrentDeck().getIndex() == 3)
		{
			int rollSp = AbstractDungeon.cardRandomRng.random(DuelistMod.spellcasterRandomOrbsChanneled, 3);				
			if (rollSp == 3) 
			{ 
				DuelistCard.spellcasterPuzzleChannel();
				DuelistMod.currentSpellcasterOrbChance = 25;
				DuelistMod.spellcasterDidChannel = true;
			}
			else 
			{
				DuelistMod.spellcasterDidChannel = false;
				int check = DuelistMod.spellcasterRandomOrbsChanneled;
				if (check == 0) 		{ DuelistMod.currentSpellcasterOrbChance = 33;	}
				else if (check == 1) 	{ DuelistMod.currentSpellcasterOrbChance = 50;  }
				else if (check == 2) 	{ DuelistMod.currentSpellcasterOrbChance = 100; }
			}
		}
	}
	
	public static void zombieChannel()
	{
		if (StarterDeckSetup.getCurrentDeck().getIndex() == 5)
		{
			DuelistCard.zombieLavaChannel();
			if (AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) { if (AbstractDungeon.cardRandomRng.random(1, 6) == 1) { DuelistCard.zombieLavaChannel(); }}
		}
		
		else if (StarterDeckSetup.getCurrentDeck().getIndex() == 20)
		{
			int roll = AbstractDungeon.miscRng.random(1, 2);
			if (roll == 1) { DuelistCard.zombieLavaChannel(); }
		}
	}
	
	public static void runEffectForMillenniumOrb(int summons, int extra)
	{
		AbstractPlayer p = AbstractDungeon.player;
		boolean bonusy = false;
		if (p.hasRelic(MillenniumSymbol.ID)) { bonusy = true; }
		if (p.hasRelic(MillenniumPuzzle.ID))
		{
			if (DuelistMod.forcePuzzleSummons) { extra += 1; }
			switch (StarterDeckSetup.getCurrentDeck().getIndex())
			{
				// Standard Deck
				case 0:
					int blur = 1;
					if (bonusy) { blur++; }
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
					DuelistCard.applyPowerToSelf(new BlurPower(p, blur));
					DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, 10));
					break;
		
				// Dragon Deck
				case 1:
					int floor = AbstractDungeon.actNum;
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Dragon Token", false);
					if (bonusy)
					{
						int roll = AbstractDungeon.cardRandomRng.random(1, 2);
						if (roll == 1) { DuelistCard.weakAllEnemies(floor); }
						else { DuelistCard.vulnAllEnemies(floor); }
					}
					else
					{
						AbstractMonster randy = AbstractDungeon.getRandomMonster();
						if (randy != null)
						{
							int roll = AbstractDungeon.cardRandomRng.random(1, 2);
							if (roll == 1) { DuelistCard.applyPower(new WeakPower(randy, floor, false), randy); }
							else { DuelistCard.applyPower(new VulnerablePower(randy, floor, false), randy); }
						}
					}
					break;
		
				// Naturia Deck
				case 2:	
					int vineLeaf = 1;
					if (bonusy) { vineLeaf += 2; }
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Nature Token", false);
					DuelistCard.applyPowerToSelf(new VinesPower(vineLeaf));
					DuelistCard.applyPowerToSelf(new LeavesPower(vineLeaf));
					break;
		
				// Spellcaster Deck
				case 3:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Spellcaster Token", false);
					spellcasterChannelAction();
					break;
					
				// Toon Deck
				case 4:		
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Toon Token", false);
					if (bonusy) { DuelistCard.applyPowerToSelf(new ToonKingdomPower(p, p, 2)); }
					else if (!AbstractDungeon.player.hasRelic(MillenniumEye.ID) && !AbstractDungeon.player.hasPower(ToonWorldPower.POWER_ID)) { DuelistCard.applyPowerToSelf(new ToonWorldPower(p, p, 1)); }
					break;
					
				// Zombie Deck
				case 5:		
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Zombie Token", false);
					zombieChannel();
					break;
					
				// Aqua Deck
				case 6:		
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Aqua Token", false);
					ArrayList<DuelistCard> cand = new ArrayList<DuelistCard>();
					// Keep track of total numbers of summons in deck
					int totalSumms = 0;
					
					// Keep track of all cards that block in deck
					ArrayList<AbstractCard> blkCards = new ArrayList<AbstractCard>();
					
					// Find all cards that block and count summons
					for (AbstractCard c : AbstractDungeon.player.drawPile.group)
					{
						if (c instanceof DuelistCard)
						{
							DuelistCard dC = (DuelistCard)c;
							if (dC.hasTag(Tags.MONSTER))
							{
								if (dC.summons > 0)
								{
									totalSumms += dC.summons;
									cand.add(dC);
								}
								
								if (c.block > 0)
								{
									blkCards.add(c);
								}
							}
						}
					}
					
					if (cand.size() > 0 && bonusy)
					{
						DuelistCard rand = cand.get(AbstractDungeon.cardRandomRng.random(cand.size() - 1));
						totalSumms += rand.summons;
						totalSumms += rand.summons;
						Util.log("AQUA PUZZLE BONUS: " + rand.name + " got added 3 times to the block bonus effect for the Millennium Puzzle.");
					}
					
					// Increase block of a random damage card by totalSumms
					if (totalSumms > 0 && blkCards.size() > 0)
					{
						if (DuelistMod.debug) { DuelistMod.logger.info("Puzzle Helper::Aqua Deck Effect: total tributes in deck: " + totalSumms); }
						AbstractCard randomDmg = blkCards.get(AbstractDungeon.cardRandomRng.random(blkCards.size() - 1));
						DuelistCard dC = (DuelistCard)randomDmg;
						dC.fiendDeckDmgMod = true;
						dC.aquaDeckEffect = true;
						dC.originalBlock = dC.block;
						AbstractDungeon.actionManager.addToTop(new ModifyBlockAction(randomDmg.uuid, totalSumms));
						//AbstractDungeon.actionManager.addToTop(new ModifyExhaustAction(randomDmg));
					}
					break;
	
				// Fiend Deck
				case 7:		
					// Summon Fiend Token
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Fiend Token", false);
					ArrayList<DuelistCard> candid = new ArrayList<DuelistCard>();
					// Keep track of total numbers of tributes in deck
					int totalTribs = 0;
					
					// Keep track of all cards that deal damage in deck
					ArrayList<AbstractCard> dmgCards = new ArrayList<AbstractCard>();
					
					// Find all cards that deal damage and count tributes
					for (AbstractCard c : AbstractDungeon.player.drawPile.group)
					{
						if (c instanceof DuelistCard)
						{
							DuelistCard dC = (DuelistCard)c;
							if (dC.hasTag(Tags.MONSTER))
							{
								if (dC.tributes > 0)
								{
									totalTribs += dC.tributes;
									candid.add(dC);
								}
								
								if (c.damage > 0)
								{
									dmgCards.add(c);
								}
							}
						}
					}
					
					if (candid.size() > 0 && bonusy)
					{
						DuelistCard rand = candid.get(AbstractDungeon.cardRandomRng.random(candid.size() - 1));
						totalTribs += rand.tributes;
						totalTribs += rand.tributes;
						Util.log("FIEND PUZZLE BONUS: " + rand.name + " got added 3 times to the damage bonus effect for the Millennium Puzzle.");
					}
					
					// Increase damage of a random damage card by totalTribs
					if (totalTribs > 0 && dmgCards.size() > 0)
					{
						if (DuelistMod.debug) { DuelistMod.logger.info("Puzzle Helper::Fiend Deck Effect: total tributes in deck: " + totalTribs); }
						AbstractCard randomDmg = dmgCards.get(AbstractDungeon.cardRandomRng.random(dmgCards.size() - 1));
						DuelistCard dC = (DuelistCard)randomDmg;
						dC.fiendDeckDmgMod = true;					
						dC.originalDamage = dC.damage;
						DuelistMod.lastFiendBonus = totalTribs;
						AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(randomDmg.uuid, totalTribs));
						//AbstractDungeon.actionManager.addToTop(new ModifyExhaustAction(randomDmg));
					}
					break;
	
				// Machine Deck
				case 8:		
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Machine Token", false);
					if (bonusy)
					{
						ArrayList<AbstractCard> tokens = new ArrayList<AbstractCard>();
						ArrayList<String> tokensAdded = new ArrayList<String>();
						while (tokens.size() < 5)
						{
							AbstractCard randomToken = DuelistCardLibrary.getTokensForCombat().get(AbstractDungeon.cardRandomRng.random(DuelistCardLibrary.getTokensForCombat().size() - 1)).makeStatEquivalentCopy();
							while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCardLibrary.getTokensForCombat().get(AbstractDungeon.cardRandomRng.random(DuelistCardLibrary.getTokensForCombat().size() - 1)).makeStatEquivalentCopy(); }
							tokens.add(randomToken);
							tokensAdded.add(randomToken.name);
						}
						AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, 1, tokens));
					}
					else
					{
						DuelistCard randomToken = (DuelistCard) DuelistCardLibrary.getTokensForCombat().get(AbstractDungeon.cardRandomRng.random(DuelistCardLibrary.getTokensForCombat().size() - 1)).makeStatEquivalentCopy();
						DuelistCard.addCardToHand(randomToken);
					}
					break;
					
				// Superheavy Deck
				case 9:		
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
					//DuelistCard.applyPowerToSelf(new VigorPower(p, 3));
					DuelistCard.applyPowerToSelf(new BlurPower(p, 2));
					if (bonusy) { DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, 10)); }
					break;
					
				// Insect Deck
				case 10:
					int pois = 2;
					if (bonusy) { pois += 2; }
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Insect Token", false);
					if (!bonusy) { DuelistCard.addCardToHand(new Bixi()); }
					else { DuelistCard bix = new Bixi(); bix.upgrade(); DuelistCard.addCardToHand(bix); }
					break;
					
				// Plant Deck
				case 11:
					int constr = 2;
					if (bonusy) { constr += 3; }
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Plant Token", false);
					DuelistCard.constrictAllEnemies(p, constr);
					break;
				
				// Predaplant Deck
				case 12:
					int floorA = AbstractDungeon.actNum;
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Predaplant Token", false);
					DuelistCard.applyPowerToSelf(new ThornsPower(p, floorA));
					break;
					
				// Megatype Deck
				case 13:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false);
					if (bonusy)
					{
						ArrayList<AbstractCard> tokens = new ArrayList<AbstractCard>();
						ArrayList<String> tokensAdded = new ArrayList<String>();
						while (tokens.size() < 3)
						{
							AbstractCard randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER);
							while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER); }
							tokens.add(randomToken);
							tokensAdded.add(randomToken.name);
						}
						AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, 1, tokens, 0));
					}
					else
					{
						AbstractCard randomMon = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER);
						randomMon.setCostForTurn(-randomMon.cost); randomMon.isCostModifiedForTurn = true;
						DuelistCard.addCardToHand(randomMon);
					}
					break;
					
				// Increment Deck
				case 14:
					int floorG = AbstractDungeon.actNum;
					DuelistCard.incMaxSummons(p, floorG);
					DuelistCard.puzzleSummon(AbstractDungeon.player, extra + floorG, "Puzzle Token", false);
					DuelistCard.gainTempHP(DuelistCard.getMaxSummons(p));
					break;
					
				// Creator Deck
				case 15:
					PuzzleHelper.creatorDeckAction(extra);
					break;
					
				// Ojama Deck
				case 16:
					int floorD = AbstractDungeon.actNum;
					int rngTurnNum = AbstractDungeon.cardRandomRng.random(1, floorD + 1);
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Bonanza Token", false);
					BuffHelper.resetBuffPool();
					DuelistCard.applyRandomBuffPlayer(p, rngTurnNum, false); 
					break;
					
				// Exodia Deck
				case 17:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exodia Token", false);
					break;
					
				// Giants Deck
				case 18:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
					break;
					
				// Ascended I
				case 19:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false);
					/*AbstractMonster randyA = AbstractDungeon.getRandomMonster();			
					if (randyA != null)
					{
						int rollA = AbstractDungeon.cardRandomRng.random(1, 2);
						if (rollA == 1) { DuelistCard.applyPower(new WeakPower(randyA, 2, false), randyA); }
						else { DuelistCard.applyPower(new VulnerablePower(randyA, 2, false), randyA); }
					}*/
					break;
					
				// Ascended II
				case 20:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false);
					break;
					
				// Ascended III
				case 21:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false);
					break;
					
				// Pharaoh I
				case 22:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
					break;
					
				// Pharaoh II
				case 23:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
					break;
					
				// Pharaoh III
				case 24:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
					break;
					
				// Pharaoh IV
				case 25:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
					break;
					
				// Pharaoh V
				case 26:
					DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
					break;
					
				// Random (Small) Deck
				case 27:
					// whenever you play a new card this run, gain 15 block
					int summonRollA = AbstractDungeon.cardRandomRng.random(1, 3);
					DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollA + extra, "Puzzle Token", false);
					break;
		
				// Random (Big) Deck
				case 28:
					int summonRollB = AbstractDungeon.cardRandomRng.random(1, 3);
					DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollB + extra, "Puzzle Token", false);
					break;
				
				// Generic
				default:
					DuelistCard.puzzleSummon(AbstractDungeon.player, summons + extra, "Puzzle Token", false);
					break;
			}
		}
		else
		{
			Util.log("Puzzle Effect skipped due to missing the Millennium Puzzle relic");
		}
	}

	public static void runSpecialEffect(int SUMMONS, int extra)
	{
		AbstractPlayer p = AbstractDungeon.player;
		boolean bonusy = p.hasRelic(MillenniumSymbol.ID);
		if (DuelistMod.forcePuzzleSummons) { extra += 1; }
		switch (StarterDeckSetup.getCurrentDeck().getIndex())
		{
			// Standard Deck
			case 0:
				int blur = 1;
				if (bonusy) { blur++; }
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				DuelistCard.applyPowerToSelf(new BlurPower(p, blur));
				DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, 10));
				break;
	
			// Dragon Deck
			case 1:
				int floor = AbstractDungeon.actNum;
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Dragon Token", false);
				if (bonusy)
				{
					int roll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (roll == 1) { DuelistCard.weakAllEnemies(floor); }
					else { DuelistCard.vulnAllEnemies(floor); }
				}
				else
				{
					AbstractMonster randy = AbstractDungeon.getRandomMonster();
					if (randy != null)
					{
						int roll = AbstractDungeon.cardRandomRng.random(1, 2);
						if (roll == 1) { DuelistCard.applyPower(new WeakPower(randy, floor, false), randy); }
						else { DuelistCard.applyPower(new VulnerablePower(randy, floor, false), randy); }
					}
				}
				break;
	
			// Naturia Deck
			case 2:				
				int vineLeaf = 1;
				if (bonusy) { vineLeaf += 2; }
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Nature Token", false);
				DuelistCard.applyPowerToSelf(new VinesPower(vineLeaf));
				DuelistCard.applyPowerToSelf(new LeavesPower(vineLeaf));
				break;
	
			// Spellcaster Deck
			case 3:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Spellcaster Token", false);
				break;
				
			// Toon Deck
			case 4:		
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Toon Token", false);
				if (bonusy) { DuelistCard.applyPowerToSelf(new ToonKingdomPower(p, p, 2)); }
				else if (!AbstractDungeon.player.hasRelic(MillenniumEye.ID) && !AbstractDungeon.player.hasPower(ToonWorldPower.POWER_ID)) { DuelistCard.applyPowerToSelf(new ToonWorldPower(p, p, 1)); }
				break;
				
			// Zombie Deck
			case 5:		
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Zombie Token", false);
				break;
				
			// Aqua Deck
			case 6:		
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Aqua Token", false);
				ArrayList<DuelistCard> cand = new ArrayList<DuelistCard>();
				// Keep track of total numbers of summons in deck
				int totalSumms = 0;
				
				// Keep track of all cards that block in deck
				ArrayList<AbstractCard> blkCards = new ArrayList<AbstractCard>();
				
				// Find all cards that block and count summons
				for (AbstractCard c : AbstractDungeon.player.drawPile.group)
				{
					if (c instanceof DuelistCard)
					{
						DuelistCard dC = (DuelistCard)c;
						if (dC.hasTag(Tags.MONSTER))
						{
							if (dC.summons > 0)
							{
								totalSumms += dC.summons;
								cand.add(dC);
							}
							
							if (c.block > 0)
							{
								blkCards.add(c);
							}
						}
					}
				}
				
				if (cand.size() > 0 && bonusy)
				{
					DuelistCard rand = cand.get(AbstractDungeon.cardRandomRng.random(cand.size() - 1));
					totalSumms += rand.summons;
					totalSumms += rand.summons;
					Util.log("AQUA PUZZLE BONUS: " + rand.name + " got added 3 times to the block bonus effect for the Millennium Puzzle.");
				}
				
				// Increase block of a random damage card by totalSumms
				if (totalSumms > 0 && blkCards.size() > 0)
				{
					if (DuelistMod.debug) { DuelistMod.logger.info("Puzzle Helper::Aqua Deck Effect: total tributes in deck: " + totalSumms); }
					AbstractCard randomDmg = blkCards.get(AbstractDungeon.cardRandomRng.random(blkCards.size() - 1));
					DuelistCard dC = (DuelistCard)randomDmg;
					dC.fiendDeckDmgMod = true;
					dC.aquaDeckEffect = true;
					dC.originalBlock = dC.block;
					AbstractDungeon.actionManager.addToTop(new ModifyBlockAction(randomDmg.uuid, totalSumms));
					//AbstractDungeon.actionManager.addToTop(new ModifyExhaustAction(randomDmg));
				}
				break;

			// Fiend Deck
			case 7:		
				// Summon Fiend Token
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Fiend Token", false);
				ArrayList<DuelistCard> candid = new ArrayList<DuelistCard>();
				// Keep track of total numbers of tributes in deck
				int totalTribs = 0;
				
				// Keep track of all cards that deal damage in deck
				ArrayList<AbstractCard> dmgCards = new ArrayList<AbstractCard>();
				
				// Find all cards that deal damage and count tributes
				for (AbstractCard c : AbstractDungeon.player.drawPile.group)
				{
					if (c instanceof DuelistCard)
					{
						DuelistCard dC = (DuelistCard)c;
						if (dC.hasTag(Tags.MONSTER))
						{
							if (dC.tributes > 0)
							{
								totalTribs += dC.tributes;
								candid.add(dC);
							}
							
							if (c.damage > 0)
							{
								dmgCards.add(c);
							}
						}
					}
				}
				
				if (candid.size() > 0 && bonusy)
				{
					DuelistCard rand = candid.get(AbstractDungeon.cardRandomRng.random(candid.size() - 1));
					totalTribs += rand.tributes;
					totalTribs += rand.tributes;
					Util.log("FIEND PUZZLE BONUS: " + rand.name + " got added 3 times to the damage bonus effect for the Millennium Puzzle.");
				}
				
				// Increase damage of a random damage card by totalTribs
				if (totalTribs > 0 && dmgCards.size() > 0)
				{
					if (DuelistMod.debug) { DuelistMod.logger.info("Puzzle Helper::Fiend Deck Effect: total tributes in deck: " + totalTribs); }
					AbstractCard randomDmg = dmgCards.get(AbstractDungeon.cardRandomRng.random(dmgCards.size() - 1));
					DuelistCard dC = (DuelistCard)randomDmg;
					dC.fiendDeckDmgMod = true;					
					dC.originalDamage = dC.damage;
					DuelistMod.lastFiendBonus = totalTribs;
					AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(randomDmg.uuid, totalTribs));
					//AbstractDungeon.actionManager.addToTop(new ModifyExhaustAction(randomDmg));
				}
				break;

			// Machine Deck
			case 8:		
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Machine Token", false);
				if (bonusy)
				{
					ArrayList<AbstractCard> tokens = new ArrayList<AbstractCard>();
					ArrayList<String> tokensAdded = new ArrayList<String>();
					while (tokens.size() < 5)
					{
						AbstractCard randomToken = DuelistCardLibrary.getTokensForCombat().get(AbstractDungeon.cardRandomRng.random(DuelistCardLibrary.getTokensForCombat().size() - 1)).makeStatEquivalentCopy();
						while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCardLibrary.getTokensForCombat().get(AbstractDungeon.cardRandomRng.random(DuelistCardLibrary.getTokensForCombat().size() - 1)).makeStatEquivalentCopy(); }
						tokens.add(randomToken);
						tokensAdded.add(randomToken.name);
					}
					AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, 1, tokens));
				}
				else
				{
					DuelistCard randomToken = (DuelistCard) DuelistCardLibrary.getTokensForCombat().get(AbstractDungeon.cardRandomRng.random(DuelistCardLibrary.getTokensForCombat().size() - 1)).makeStatEquivalentCopy();
					DuelistCard.addCardToHand(randomToken);
				}
				break;
				
			// Superheavy Deck
			case 9:		
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				//DuelistCard.applyPowerToSelf(new VigorPower(p, 3));
				DuelistCard.applyPowerToSelf(new BlurPower(p, 2));
				if (bonusy) { DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, 10)); }
				break;
				
			// Insect Deck
			case 10:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Insect Token", false);
				if (!bonusy) { DuelistCard.addCardToHand(new Bixi()); }
				else { DuelistCard bix = new Bixi(); bix.upgrade(); DuelistCard.addCardToHand(bix); }
				break;
				
			// Plant Deck
			case 11:
				int constr = 2;
				if (bonusy) { constr += 3; }
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Plant Token", false);
				DuelistCard.constrictAllEnemies(p, constr);
				break;
			
			// Predaplant Deck
			case 12:
				int act = AbstractDungeon.actNum;
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Predaplant Token", false);
				DuelistCard.applyPowerToSelf(new ThornsPower(p, act));
				break;
				
			// Megatype Deck
			case 13:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false);
				if (bonusy)
				{
					ArrayList<AbstractCard> tokens = new ArrayList<AbstractCard>();
					ArrayList<String> tokensAdded = new ArrayList<String>();
					while (tokens.size() < 3)
					{
						AbstractCard randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER);
						while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER); }
						tokens.add(randomToken);
						tokensAdded.add(randomToken.name);
					}
					AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, 1, tokens, 0));
				}
				else
				{
					AbstractCard randomMon = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER);
					randomMon.setCostForTurn(-randomMon.cost); randomMon.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(randomMon);
				}
				break;
				
			// Increment Deck
			case 14:
				int floorG = AbstractDungeon.actNum;
				DuelistCard.incMaxSummons(p, floorG);
				DuelistCard.puzzleSummon(AbstractDungeon.player, extra + floorG, "Puzzle Token", false);
				DuelistCard.gainTempHP(DuelistCard.getMaxSummons(p));
				break;
				
			// Creator Deck
			case 15:
				PuzzleHelper.creatorDeckAction(extra);
				break;
				
			// Ojama Deck
			case 16:
				int floorD = AbstractDungeon.actNum;
				int rngTurnNum = AbstractDungeon.cardRandomRng.random(1, floorD + 1);
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Bonanza Token", false);
				BuffHelper.resetBuffPool();
				DuelistCard.applyRandomBuffPlayer(p, rngTurnNum, false); 
				break;
				
			// Exodia Deck
			case 17:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exodia Token", false);
				break;
				
			// Giants Deck
			case 18:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;
				
			// Ascended I
			case 19:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false);
				/*AbstractMonster randyA = AbstractDungeon.getRandomMonster();	
				if (randyA != null)
				{
					int rollA = AbstractDungeon.cardRandomRng.random(1, 2);
					if (rollA == 1) { DuelistCard.applyPower(new WeakPower(randyA, 2, false), randyA); }
					else { DuelistCard.applyPower(new VulnerablePower(randyA, 2, false), randyA); }
				}*/
				break;
				
			// Ascended II
			case 20:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false);
				break;
				
			// Ascended III
			case 21:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false);
				break;
				
			// Pharaoh I
			case 22:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;
				
			// Pharaoh II
			case 23:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;
				
			// Pharaoh III
			case 24:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;
				
			// Pharaoh IV
			case 25:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;
				
			// Pharaoh V
			case 26:
				DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false);
				break;
				
			// Random (Small) Deck
			case 27:
				// whenever you play a new card this run, gain 15 block
				int summonRollA = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollA + extra, "Puzzle Token", false);
				break;
	
			// Random (Big) Deck
			case 28:
				int summonRollB = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollB + extra, "Puzzle Token", false);
				break;
			
			// Random (Upgrade) Deck
			case 29:
				int summonRollC = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollC + extra, "Puzzle Token", false);
				break;
				
			// Metronome Deck
			case 30:
				int summonRollD = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollD + extra, "Puzzle Token", false);
				break;
			// Generic
			default:
				DuelistCard.puzzleSummon(AbstractDungeon.player, SUMMONS + extra, "Puzzle Token", false);
				break;
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
		DuelistCard.puzzleSummon(AbstractDungeon.player, 2 + extra, "Puzzle Token", false);
	}
}
