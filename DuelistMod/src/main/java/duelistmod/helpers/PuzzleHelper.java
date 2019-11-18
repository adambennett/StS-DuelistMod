package duelistmod.helpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.*;
import duelistmod.actions.unique.DragonPuzzleAction;
import duelistmod.cards.*;
import duelistmod.cards.other.tempCards.*;
import duelistmod.cards.other.tokens.*;
import duelistmod.cards.other.tokens.MachineToken;
import duelistmod.cards.other.tokens.SpellcasterToken;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.Jinzo;
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
			// Normal Runs
			if (!DuelistMod.playingChallenge) { runSpecialEffect(summons, extra, false); }

			// Challenge Mode (anywhere)
			else { runChallengeEffect(summons, extra); }
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
			int rollSp = 0;		
			if (Util.getChallengeLevel() > 0) { rollSp = AbstractDungeon.cardRandomRng.random(0, 3); }
			else { rollSp = AbstractDungeon.cardRandomRng.random(DuelistMod.spellcasterRandomOrbsChanneled, 3); }
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
			int cap = 6;
			if (Util.getChallengeLevel() > 0) { cap++; }
			if (AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) { if (AbstractDungeon.cardRandomRng.random(1, cap) == 1) { DuelistCard.zombieLavaChannel(); }}
		}
		
		else if (StarterDeckSetup.getCurrentDeck().getIndex() == 20)
		{
			int cap = 2;
			if (Util.getChallengeLevel() > 0) { cap++; }
			int roll = AbstractDungeon.miscRng.random(1, cap);
			if (roll == 1) { DuelistCard.zombieLavaChannel(); }
		}
	}
	
	public static void runSpecialEffect(int summons, int extra, boolean fromOrb)
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
					DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					DuelistCard.applyPowerToSelf(new BlurPower(p, blur));
					DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, 10));
					break;
		
				// Dragon Deck
				case 1:
					int floor = AbstractDungeon.actNum;
					tok = DuelistCardLibrary.getTokenInCombat(new DragonToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);					
					int effects = 2;
					if (bonusy) { effects++; }
					ArrayList<DuelistCard> choices = new ArrayList<DuelistCard>();
					choices.add(new PuzzleDragonRare());
					choices.add(new PuzzleDragonScales());
					choices.add(new PuzzleDragonStrength());
					choices.add(new PuzzleDragonTribute());
					choices.add(new PuzzleDragonVulnerable());
					choices.add(new PuzzleDragonWeak(floor));
					AbstractDungeon.actionManager.addToTop(new DragonPuzzleAction(choices, effects));
					break;
		
				// Naturia Deck
				case 2:	
					int vineLeaf = 1;
					if (bonusy) { vineLeaf += 2; }
					tok = DuelistCardLibrary.getTokenInCombat(new NatureToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					DuelistCard.applyPowerToSelf(new VinesPower(vineLeaf));
					DuelistCard.applyPowerToSelf(new LeavesPower(vineLeaf));
					break;
		
				// Spellcaster Deck
				case 3:
					tok = DuelistCardLibrary.getTokenInCombat(new SpellcasterToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					if (fromOrb) { spellcasterChannelAction(); }
					break;
					
				// Toon Deck
				case 4:		
					tok = DuelistCardLibrary.getTokenInCombat(new ToonToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					if (bonusy) { DuelistCard.applyPowerToSelf(new ToonKingdomPower(p, p, 2)); }
					else if (!AbstractDungeon.player.hasRelic(MillenniumEye.ID) && !AbstractDungeon.player.hasPower(ToonWorldPower.POWER_ID)) { DuelistCard.applyPowerToSelf(new ToonWorldPower(p, p, 1)); }
					break;
					
				// Zombie Deck
				case 5:		
					tok = DuelistCardLibrary.getTokenInCombat(new ZombieToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					if (fromOrb) { zombieChannel(); }
					break;
					
				// Aqua Deck
				case 6:		
					tok = DuelistCardLibrary.getTokenInCombat(new AquaToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
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
								if (dC.isSummonCard())
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
					tok = DuelistCardLibrary.getTokenInCombat(new FiendToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
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
								if (dC.isTributeCard())
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
					tok = DuelistCardLibrary.getTokenInCombat(new MachineToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					if (bonusy)
					{
						ArrayList<AbstractCard> tokens = new ArrayList<AbstractCard>();
						ArrayList<String> tokensAdded = new ArrayList<String>();
						while (tokens.size() < 5)
						{
							AbstractCard randomToken = DuelistCardLibrary.getRandomTokenForCombat();
							while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCardLibrary.getRandomTokenForCombat(); }
							tokens.add(randomToken);
							tokensAdded.add(randomToken.name);
						}
						AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, 1, tokens));
					}
					else
					{
						DuelistCard randomToken = DuelistCardLibrary.getRandomTokenForCombat();
						DuelistCard.addCardToHand(randomToken);
					}
					break;
					
				// Superheavy Deck
				case 9:		
					tok = DuelistCardLibrary.getTokenInCombat(new StanceToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					DuelistCard.applyPowerToSelf(new VigorPower(p, 3));
					DuelistCard.applyPowerToSelf(new BlurPower(p, 2));
					if (bonusy) { DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, 10)); }
					break;
					
				// Insect Deck
				case 10:
					tok = DuelistCardLibrary.getTokenInCombat(new InsectToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					if (!bonusy) { DuelistCard.addCardToHand(new Bixi()); }
					else { DuelistCard bix = new Bixi(); bix.upgrade(); DuelistCard.addCardToHand(bix); }
					break;
					
				// Plant Deck
				case 11:
					int constr = 2;
					if (bonusy) { constr += 3; }
					tok = DuelistCardLibrary.getTokenInCombat(new PlantToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					DuelistCard.constrictAllEnemies(p, constr);
					break;
				
				// Predaplant Deck
				case 12:
					int floorA = AbstractDungeon.actNum;
					tok = DuelistCardLibrary.getTokenInCombat(new PredaplantToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					DuelistCard.applyPowerToSelf(new ThornsPower(p, floorA));
					break;
					
				// Megatype Deck
				case 13:
					tok = DuelistCardLibrary.getTokenInCombat(new MegatypeToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					if (bonusy)
					{
						ArrayList<AbstractCard> tokens = new ArrayList<AbstractCard>();
						ArrayList<String> tokensAdded = new ArrayList<String>();
						while (tokens.size() < 3)
						{
							AbstractCard randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false);
							while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false); }
							tokens.add(randomToken);
							tokensAdded.add(randomToken.name);
						}
						AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, 1, tokens, 0));
					}
					else
					{
						AbstractCard randomMon = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false);
						randomMon.setCostForTurn(-randomMon.cost); randomMon.isCostModifiedForTurn = true;
						DuelistCard.addCardToHand(randomMon);
					}
					break;
					
				// Increment Deck
				case 14:
					int floorG = AbstractDungeon.actNum;
					AbstractDungeon.actionManager.addToBottom(new IncrementAction(floorG));
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, extra + floorG, tok);
					break;
					
				// Creator Deck
				case 15:
					PuzzleHelper.creatorDeckAction(extra);
					break;
					
				// Ojama Deck
				case 16:
					int floorD = AbstractDungeon.actNum;
					int rngTurnNum = AbstractDungeon.cardRandomRng.random(1, floorD + 1);
					tok = DuelistCardLibrary.getTokenInCombat(new BonanzaToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					BuffHelper.resetBuffPool();
					DuelistCard.applyRandomBuffPlayer(p, rngTurnNum, false); 
					break;
					
				// Exodia Deck
				case 17:
					tok = DuelistCardLibrary.getTokenInCombat(new ExodiaToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Giants Deck
				case 18:
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Ascended I
				case 19:
					tok = DuelistCardLibrary.getTokenInCombat(new MegatypeToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Ascended II
				case 20:
					tok = DuelistCardLibrary.getTokenInCombat(new MegatypeToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Ascended III
				case 21:
					tok = DuelistCardLibrary.getTokenInCombat(new MegatypeToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Pharaoh I
				case 22:
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Pharaoh II
				case 23:
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Pharaoh III
				case 24:
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Pharaoh IV
				case 25:
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Pharaoh V
				case 26:
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, 1 + extra, tok);
					break;
					
				// Random (Small) Deck
				case 27:
					// whenever you play a new card this run, gain 15 block
					int summonRollA = AbstractDungeon.cardRandomRng.random(1, 3);
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, summonRollA + extra, tok);
					break;
		
				// Random (Big) Deck
				case 28:
					int summonRollB = AbstractDungeon.cardRandomRng.random(1, 3);
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, summonRollB + extra, tok);
					break;
				
				// Generic
				default:
					tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
					DuelistCard.summon(AbstractDungeon.player, summons + extra, tok);
					break;
			}
		}
		else
		{
			Util.log("Puzzle Effect skipped due to missing the Millennium Puzzle relic");
		}
	}

	public static void runChallengeEffect(int SUMMONS, int extra)
	{
		AbstractPlayer p = AbstractDungeon.player;
		boolean bonusy = p.hasRelic(MillenniumSymbol.ID);
		boolean typedTokens = Util.getChallengeLevel() < 0;
		boolean weakEffects = Util.getChallengeLevel() > 0 && Util.getChallengeLevel() < 7;
		boolean effectsEnabled = Util.getChallengeLevel() < 7;
		boolean explosiveTokens = Util.getChallengeLevel() > 8 && Util.getChallengeLevel() < 16;
		boolean supeExplosive = Util.getChallengeLevel() > 15;
		if (DuelistMod.forcePuzzleSummons) { extra += 1; }
		switch (StarterDeckSetup.getCurrentDeck().getIndex())
		{
			// Standard Deck
			case 0:
				int blur = 1;
				if (bonusy) { blur++; }
				if (!explosiveTokens && !supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				if (effectsEnabled)
				{
					DuelistCard.applyPowerToSelf(new BlurPower(p, blur));
					if (!weakEffects) { DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, 10)); }
				}
				break;
	
			// Dragon Deck
			case 1:
				int floor = AbstractDungeon.actNum;
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Dragon Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				int effects = 2;
				if (bonusy) { effects++; }
				if (weakEffects) { effects--; }
				if (effectsEnabled)
				{
					ArrayList<DuelistCard> choices = new ArrayList<DuelistCard>();
					choices.add(new PuzzleDragonRare());
					choices.add(new PuzzleDragonScales());
					choices.add(new PuzzleDragonStrength());
					choices.add(new PuzzleDragonTribute());
					choices.add(new PuzzleDragonVulnerable());
					choices.add(new PuzzleDragonWeak(floor));
					if (weakEffects) { choices.remove(ThreadLocalRandom.current().nextInt(choices.size())); }
					AbstractDungeon.actionManager.addToTop(new DragonPuzzleAction(choices, effects));
				}
				break;
	
			// Naturia Deck
			case 2:				
				int vineLeaf = 1;
				if (bonusy) { vineLeaf += 2; }
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Nature Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				if (effectsEnabled)
				{
					DuelistCard.applyPowerToSelf(new VinesPower(vineLeaf));
					if (!weakEffects) { DuelistCard.applyPowerToSelf(new LeavesPower(vineLeaf)); }
				}
				break;
	
			// Spellcaster Deck
			case 3:
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Spellcaster Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				break;
				
			// Toon Deck
			case 4:		
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Toon Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				int val = 2;
				if (weakEffects) { val++; }
				if (bonusy && effectsEnabled) { DuelistCard.applyPowerToSelf(new ToonKingdomPower(p, p, val)); }
				else if (!AbstractDungeon.player.hasRelic(MillenniumEye.ID) && !AbstractDungeon.player.hasPower(ToonWorldPower.POWER_ID) && effectsEnabled) { DuelistCard.applyPowerToSelf(new ToonWorldPower(p, p, val - 1)); }
				break;
				
			// Zombie Deck
			case 5:		
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Zombie Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				break;
				
			// Aqua Deck
			case 6:		
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Aqua Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
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
							if (dC.isSummonCard())
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
				if (totalSumms > 0 && blkCards.size() > 0 && effectsEnabled)
				{
					if (weakEffects) { totalSumms = (int)(totalSumms / 2.0f); }
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
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Fiend Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
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
							if (dC.isTributeCard())
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
				if (totalTribs > 0 && dmgCards.size() > 0 && effectsEnabled)
				{
					if (weakEffects) { totalTribs = (int)(totalTribs / 2.0f); }
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
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Machine Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				if (bonusy && effectsEnabled)
				{
					ArrayList<AbstractCard> tokens = new ArrayList<AbstractCard>();
					ArrayList<String> tokensAdded = new ArrayList<String>();
					int tok = 5;
					if (weakEffects) { tok -= 2; }
					while (tokens.size() < tok)
					{
						AbstractCard randomToken = DuelistCardLibrary.getRandomTokenForCombat();
						while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCardLibrary.getRandomTokenForCombat(); }
						tokens.add(randomToken);
						tokensAdded.add(randomToken.name);
					}
					AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, 1, tokens));
				}
				else if (effectsEnabled)
				{
					DuelistCard randomToken = DuelistCardLibrary.getRandomTokenForCombat();
					if (weakEffects && AbstractDungeon.cardRandomRng.random(1, 2) == 1)
					{
						DuelistCard.addCardToHand(randomToken);
					}
					else
					{
						DuelistCard.addCardToHand(randomToken);
					}
				}
				break;
				
			// Superheavy Deck
			case 9:		
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Stance Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				if (effectsEnabled)
				{
					int vig = 3;
					int blr = 2;
					int blkCap = 10;
					if (weakEffects)
					{
						vig = 2;
						blr = 1;
						blkCap = 8;
					}
					DuelistCard.applyPowerToSelf(new VigorPower(p, vig));
					DuelistCard.applyPowerToSelf(new BlurPower(p, blr));
					if (bonusy) { DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, blkCap)); }
				}
				break;
				
			// Insect Deck
			case 10:
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Insect Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				if (!bonusy && effectsEnabled) 
				{
					if (weakEffects) { DuelistCard.addCardToHand(new WeakBixi()); }
					else { DuelistCard.addCardToHand(new Bixi());  }
				}
				else if (effectsEnabled) 
				{ 
					if (weakEffects) { DuelistCard bix = new WeakBixi(); bix.upgrade(); DuelistCard.addCardToHand(bix); }
					else { DuelistCard bix = new Bixi(); bix.upgrade(); DuelistCard.addCardToHand(bix); }
				}				
				break;
				
			// Plant Deck
			case 11:
				int constr = 2;
				if (bonusy) { constr += 3; }
				if (weakEffects) { constr--; }
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Plant Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				if (effectsEnabled) { DuelistCard.constrictAllEnemies(p, constr); }
				break;
			
			// Predaplant Deck
			case 12:
				int act = AbstractDungeon.actNum;
				if (weakEffects && act > 2) { act = 2; }
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Predaplant Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				if (effectsEnabled) { DuelistCard.applyPowerToSelf(new ThornsPower(p, act)); }
				break;
				
			// Megatype Deck
			case 13:
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				if (bonusy && effectsEnabled)
				{
					ArrayList<AbstractCard> tokens = new ArrayList<AbstractCard>();
					ArrayList<String> tokensAdded = new ArrayList<String>();
					int choic = 3;
					if (weakEffects) { choic = 2; }
					while (tokens.size() < choic)
					{
						AbstractCard randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false);
						while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false); }
						tokens.add(randomToken);
						tokensAdded.add(randomToken.name);
					}
					AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, 1, tokens, 0));
				}
				else if (effectsEnabled)
				{
					AbstractCard randomMon = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false);
					if (!weakEffects) { randomMon.setCostForTurn(-randomMon.cost); randomMon.isCostModifiedForTurn = true; }
					DuelistCard.addCardToHand(randomMon);
				}
				break;
				
			// Increment Deck
			case 14:
				int floorG = AbstractDungeon.actNum;
				if (weakEffects && floorG > 1) { floorG--; }
				if (effectsEnabled) { DuelistCard.incMaxSummons(p, floorG); }
				if (!explosiveTokens && !supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				break;
				
			// Creator Deck
			case 15:
				PuzzleHelper.creatorDeckAction(extra);
				break;
				
			// Ojama Deck
			case 16:
				int floorD = AbstractDungeon.actNum;
				int rngTurnNum = AbstractDungeon.cardRandomRng.random(1, floorD + 1);
				if (weakEffects && rngTurnNum > 1) { rngTurnNum--; }
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Bonanza Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				if (effectsEnabled)
				{
					BuffHelper.resetBuffPool();
					DuelistCard.applyRandomBuffPlayer(p, rngTurnNum, false); 
				}
				break;
				
			// Exodia Deck
			case 17:
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exodia Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				break;
				
			// Giants Deck
			case 18:
				if (!explosiveTokens && !supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				break;
				
			// Ascended I
			case 19:
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				break;
				
			// Ascended II
			case 20:
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				break;
				
			// Ascended III
			case 21:
				if (typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Megatype Token", false); }
				else if (!explosiveTokens && !supeExplosive && !typedTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				break;
				
			// Pharaoh I
			case 22:
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				break;
				
			// Pharaoh II
			case 23:
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				break;
				
			// Pharaoh III
			case 24:
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				break;
				
			// Pharaoh IV
			case 25:
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				break;
				
			// Pharaoh V
			case 26:
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, 1 + extra, "S. Exploding Token", false); }
				
				break;
				
			// Random (Small) Deck
			case 27:
				// whenever you play a new card this run, gain 15 block
				int summonRollA = AbstractDungeon.cardRandomRng.random(1, 3);
				if (weakEffects && summonRollA > 2) { summonRollA = 2; }
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollA + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollA + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollA + extra, "S. Exploding Token", false); }
				
				break;
	
			// Random (Big) Deck
			case 28:
				int summonRollB = AbstractDungeon.cardRandomRng.random(1, 3);
				if (weakEffects && summonRollB > 2) { summonRollB = 2; }
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollB + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollB + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollB + extra, "S. Exploding Token", false); }
				
				break;
			
			// Random (Upgrade) Deck
			case 29:
				int summonRollC = AbstractDungeon.cardRandomRng.random(1, 3);
				if (weakEffects && summonRollC > 2) { summonRollC = 2; }
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollC + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollC + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollC + extra, "S. Exploding Token", false); }
				
				break;
				
			// Metronome Deck
			case 30:
				int summonRollD = AbstractDungeon.cardRandomRng.random(1, 3);
				if (weakEffects && summonRollD > 2) { summonRollD = 2; }
				if (!explosiveTokens && !supeExplosive ) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollD + extra, "Puzzle Token", false); }
				else if (explosiveTokens) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollD + extra, "Exploding Token", false); }
				else if (supeExplosive) { DuelistCard.puzzleSummon(AbstractDungeon.player, summonRollD + extra, "S. Exploding Token", false); }
				
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
		boolean weakEffects = Util.getChallengeLevel() > 0 && Util.getChallengeLevel() < 7;
		boolean effectsEnabled = Util.getChallengeLevel() < 7;
		boolean explosiveTokens = Util.getChallengeLevel() > 8 && Util.getChallengeLevel() < 16;
		boolean supeExplosive = Util.getChallengeLevel() > 15;
		if (effectsEnabled)
		{
			if (roll == 1)
			{
				DuelistCard jinzo = new Jinzo();
				DuelistCard.addCardToHand(jinzo);
				// whenever you play a trap card, gain 1 HP
			}
			else if (roll == 2)
			{
				DuelistCard jinzo = new TrapHole();
				if (!weakEffects)
				{
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo);
	
				
				// whenever you summon, gain 1 HP
			}
			else if (roll == 3)
			{
				DuelistCard jinzo = new Jinzo();
				if (!weakEffects)
				{
					jinzo.upgrade();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo);
	
				
				// whenever you play an Ethereal card, gain 1 HP
			}
			else if (roll == 4)
			{
				DuelistCard jinzo = new TrapHole();
				if (!weakEffects)
				{
					jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
					if (jinzo.costForTurn != jinzo.cost) { jinzo.isCostModifiedForTurn = true; }
				}
				DuelistCard.addCardToHand(jinzo);
	
				
				// whenever you play an Ethereal card, gain 1 HP
			}
			else if (roll == 5)
			{
				DuelistCard jinzo = new Jinzo();
				if (!weakEffects)
				{
					jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
					if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
				}
				DuelistCard.addCardToHand(jinzo);
	
				
				// whenever you play an Ethereal card, gain 2 HP
			}
			else if (roll == 6)
			{
				DuelistCard jinzo = new Jinzo();
				if (!weakEffects)
				{
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo);
	
				
				// whenever you play an Ethereal card, gain 5 Gold
			}
			else if (roll == 7)
			{
				DuelistCard jinzo = new TrapHole();
				if (!weakEffects) { jinzo.upgrade(); }
				DuelistCard.addCardToHand(jinzo);
	
				// whenever you play an Ethereal card, gain 1 Block (affected by dexterity)
			}
			else if (roll == 8)
			{
				DuelistCard jinzo = new UltimateOffering();
				if (!weakEffects)
				{
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo);
				
				// whenever you increment, gain 4 HP
			}
			else if (roll == 9)
			{
				DuelistCard jinzo = new Jinzo();
				DuelistCard trap = new TrapHole();
				DuelistCard ultimate = new UltimateOffering();
				if (!weakEffects)
				{
					jinzo.costForTurn = 0; trap.costForTurn = 0; ultimate.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true; trap.isCostModifiedForTurn = true; ultimate.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo); DuelistCard.addCardToHand(trap); DuelistCard.addCardToHand(ultimate);
				
				// whenever you play an Ethereal card, gain 5 Gold and 2 HP
			}
		}
		int summ = 2;
		if (!explosiveTokens && !supeExplosive ) 
		{ 
	    	DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new PuzzleToken());
			DuelistCard.summon(AbstractDungeon.player, summ + extra, tok); 
		}
		else if (explosiveTokens) 
		{ 
			DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new ExplosiveToken());
			DuelistCard.summon(AbstractDungeon.player, summ + extra, tok);
		}
		else if (supeExplosive) 
		{ 
			DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new SuperExplodingToken());
			DuelistCard.summon(AbstractDungeon.player, summ + extra, tok);
		}
		
	}
}
