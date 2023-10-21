package duelistmod.helpers;

import java.util.*;

import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.actions.common.CardSelectScreenTriggerOverflowAction;
import duelistmod.actions.common.IncrementAction;
import duelistmod.actions.unique.DragonPuzzleAction;
import duelistmod.actions.unique.DragonPuzzleRunActionsAction;
import duelistmod.cards.TrapHole;
import duelistmod.cards.UltimateOffering;
import duelistmod.cards.other.tempCards.PuzzleDragonBurning;
import duelistmod.cards.other.tempCards.PuzzleDragonRare;
import duelistmod.cards.other.tempCards.PuzzleDragonScales;
import duelistmod.cards.other.tempCards.PuzzleDragonStrength;
import duelistmod.cards.other.tempCards.PuzzleDragonTribute;
import duelistmod.cards.other.tempCards.PuzzleDragonWeak;
import duelistmod.cards.other.tokens.ExplosiveToken;
import duelistmod.cards.other.tokens.PuzzleToken;
import duelistmod.cards.other.tokens.SuperExplodingToken;
import duelistmod.cards.pools.insects.Bixi;
import duelistmod.cards.pools.insects.WeakBixi;
import duelistmod.cards.pools.machine.Jinzo;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.dto.TwoNums;
import duelistmod.enums.StartingDeck;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.powers.ToonKingdomPower;
import duelistmod.powers.ToonWorldPower;
import duelistmod.relics.MillenniumEye;
import duelistmod.relics.MillenniumPuzzle;
import duelistmod.relics.MillenniumSymbol;
import duelistmod.variables.Tags;

public class PuzzleHelper 
{
	public static void atBattleStartHelper() {
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
			runStartOfBattleEffect(false);
		} else {
			switch (AbstractDungeon.player.chosenClass) {
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
					if (rolly == 1) { AbstractDungeon.player.increaseMaxOrbSlots(floorC + 1, true); } else if (rolly == 2 || rolly == 3) { AbstractDungeon.player.increaseMaxOrbSlots(1, true); } else { DuelistCard.applyPowerToSelf(new FocusPower(AbstractDungeon.player, floorC + 1)); }
					//setDescription("DEFECT: At the start of combat, increase your Orb slots by a random amount or gain Focus. Chosen randomly. The amount of Focus and Orb slots increases the more Acts you complete.");
					break;

				// Modded Character
				default:
					//setDescription("MODDED CHAR: At the start of combat, randomly choose to heal or gain gold.");
					int floorD = AbstractDungeon.actNum;
					int roll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (roll == 2) {
						DuelistCard.heal(AbstractDungeon.player, floorD);
					} else {
						DuelistCard.gainGold(floorD * 15, AbstractDungeon.player, true);
					}
					break;
			}
		}
	}

	public static void runStartOfBattleEffect(boolean fromOrb) {
		AbstractPlayer p = AbstractDungeon.player;
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		boolean typedTokens = Util.getChallengeLevel() < 0;
		boolean bonus = isBonusEffects();
		boolean weakEffects = isWeakEffects();
		boolean effectsEnabled = isEffectsEnabled();
		boolean explosiveTokens = Util.getChallengeLevel() > 8 && Util.getChallengeLevel() < 16;
		boolean supeExplosive = Util.getChallengeLevel() > 15;
		if (p.hasRelic(MillenniumPuzzle.ID)) {
			if (!isCurrentDeckUniqueSummoningEffectDeck() && config.getTokensToSummon() > 0) {
				int sms = config.getTokensToSummon();
				DuelistCard token = StartingDeck.getTokenFromID(config.getTokenType());
				if (supeExplosive) {
					token = new SuperExplodingToken();
				} else if (explosiveTokens) {
					token = new ExplosiveToken();
				} else if (!typedTokens) {
					token = new PuzzleToken();
				}
				DuelistCard.summon(AbstractDungeon.player, sms, token);
			}
			if (effectsEnabled) {
				switch (StartingDeck.currentDeck) {
					case STANDARD:
						if (config.getGainBlur() != null && config.getGainBlur()) {
							int blur = config.getBlurToGain();
							if (bonus) blur++;
							if (blur > 0) DuelistCard.applyPowerToSelf(new BlurPower(p, blur));
						}
						if (config.getRandomBlockHigh() != null && config.getRandomBlockLow() != null) {
							int low = config.getRandomBlockLow();
							int high = config.getRandomBlockHigh();
							TwoNums highLow = Util.getLowHigh(low, high);
							int high2 = highLow.high() - (weakEffects ? 2 : 0);
							if (high2 > 0 && highLow.low() > -1) {
								DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(highLow.low(), high2));
							}
						}
						break;
					case DRAGON:
						if (fromOrb) {
							dragonEffects();
						}
						break;
					case SPELLCASTER:
						if (fromOrb) {
							spellcasterEffects();
						}
						break;
					case AQUA:
						if (fromOrb) {
							aquaEffects();
						}
						break;
					case FIEND:
						if (config.getDamageBoost() != null && config.getDamageBoost()) {
							ArrayList<DuelistCard> candid = new ArrayList<>();
							// Keep track of total numbers of tributes in deck
							int totalTribs = 0;

							// Keep track of all cards that deal damage in deck
							ArrayList<AbstractCard> dmgCards = new ArrayList<>();

							// Find all cards that deal damage and count tributes
							for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
								if (c instanceof DuelistCard) {
									DuelistCard dC = (DuelistCard)c;
									if (dC.hasTag(Tags.MONSTER)) {
										if (dC.isTributeCard()) {
											totalTribs += dC.tributes;
											candid.add(dC);
										}

										if (c.damage > 0) {
											dmgCards.add(c);
										}
									}
								}
							}

							if (candid.size() > 0 && bonus) {
								DuelistCard rand = candid.get(AbstractDungeon.cardRandomRng.random(candid.size() - 1));
								totalTribs += rand.tributes;
								totalTribs += rand.tributes;
							}

							// Increase damage of a random damage card by totalTribs
							if (totalTribs > 0 && dmgCards.size() > 0) {
								if (weakEffects) { totalTribs = (int)(totalTribs / 2.0f); }
								AbstractCard randomDmg = dmgCards.get(AbstractDungeon.cardRandomRng.random(dmgCards.size() - 1));
								DuelistCard dC = (DuelistCard)randomDmg;
								dC.fiendDeckDmgMod = true;
								dC.originalDamage = dC.damage;
								DuelistMod.lastFiendBonus = totalTribs;
								AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(randomDmg.uuid, totalTribs));
							}
						}
						break;
					case ASCENDED_II:
					case ZOMBIE:
						if (fromOrb) {
							zombieEffects();
						}
						break;
					case MACHINE:
						if (config.getRandomTokenAmount() != null && config.getRandomTokenToHand() && config.getRandomTokenAmount() > 0) {
							if (bonus) {
								ArrayList<AbstractCard> tokens = new ArrayList<>();
								ArrayList<String> tokensAdded = new ArrayList<>();
								int sizeInc = weakEffects ? 2 : 4;
								int tokenSize = config.getRandomTokenAmount() + sizeInc;
								while (tokens.size() < tokenSize) {
									AbstractCard randomToken = DuelistCardLibrary.getRandomTokenForCombat(tokensAdded);
									while (tokensAdded.contains(randomToken.cardID)) { randomToken = DuelistCardLibrary.getRandomTokenForCombat(); }
									tokens.add(randomToken);
									tokensAdded.add(randomToken.cardID);
								}
								AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(false, true, config.getRandomTokenAmount(), tokens));
							} else {
								for (int i = 0; i < config.getRandomTokenAmount(); i++) {
									if (!weakEffects || AbstractDungeon.cardRandomRng.random(1, 2) == 1) {
										DuelistCard randomToken = DuelistCardLibrary.getRandomTokenForCombat();
										DuelistCard.addCardToHand(randomToken);
									}
								}
							}
						}
						break;
					case INSECT:
						if (config.getAddBixi() != null && config.getAddBixi()) {
							AbstractCard bixi = weakEffects ? new WeakBixi() : new Bixi();
							if (bonus) {
								bixi.upgrade();
							}
							DuelistCard.addCardToHand(bixi);
						}
						break;
					case PLANT:
						if (config.getApplyConstricted() != null && config.getApplyConstricted() && config.getConstrictedAmount() > 0) {
							int constr = config.getConstrictedAmount();
							if (bonus) {
								constr += 2;
							}
							if (weakEffects) {
								constr--;
							}
							if (constr > 0) {
								DuelistCard.constrictAllEnemies(AnyDuelist.from(AbstractDungeon.player), constr);
							}
						}
						break;
					case NATURIA:
						AnyDuelist duelist = AnyDuelist.from(AbstractDungeon.player);
						if (!weakEffects && config.getStartingVines() != null && config.getStartingVines() > 0) {
							int amt = config.getStartingVines() + (bonus ? 2 : 0);
							DuelistCard.applyPowerToSelf(Util.vinesPower(amt, duelist));
						}
						if (config.getStartingLeaves() != null && config.getStartingLeaves() > 0) {
							int amt = config.getStartingLeaves() + (bonus ? 2 : 0);
							DuelistCard.applyPowerToSelf(Util.leavesPower(amt, duelist));
						}
						break;
					case WARRIOR:
						if (config.getGainVigor() != null && config.getGainVigor() && config.getVigorToGain() > 0) {
							int vigor = config.getVigorToGain();
							if (weakEffects) vigor--;

							if (vigor > 0) {
								DuelistCard.applyPowerToSelf(new VigorPower(p, config.getVigorToGain()));
							}

						}
						if (config.getGainBlur() != null && config.getGainBlur() && config.getBlurToGain() > 0) {
							int blur = config.getBlurToGain();
							if (weakEffects) blur--;

							if (blur > 0) {
								DuelistCard.applyPowerToSelf(new BlurPower(p, config.getBlurToGain()));
							}
						}
						if (bonus) {
							int cap = weakEffects ? 6 : 10;
							DuelistCard.staticBlock(AbstractDungeon.cardRandomRng.random(0, cap));
						}
						break;
					case MEGATYPE:
						if (config.getAddMonsterToHand() != null && config.getAddMonsterToHand() && config.getRandomMonstersToAdd() > 0) {
							if (bonus) {
								ArrayList<AbstractCard> tokens = new ArrayList<>();
								ArrayList<String> tokensAdded = new ArrayList<>();
								int sizeInc = weakEffects ? 1 : 2;
								int monstersToChooseFrom = config.getRandomMonstersToAdd() + sizeInc;
								while (tokens.size() < monstersToChooseFrom) {
									AbstractCard randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false);
									while (tokensAdded.contains(randomToken.name)) { randomToken = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false); }
									tokens.add(randomToken);
									tokensAdded.add(randomToken.name);
								}
								AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(true, config.getRandomMonstersToAdd(), tokens, 0));
							} else {
								for (int i = 0; i < config.getRandomMonstersToAdd(); i++) {
									AbstractCard randomMon = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER, false);
									if (!weakEffects) {
										randomMon.setCostForTurn(-randomMon.cost);
										randomMon.isCostModifiedForTurn = true;
									}
									DuelistCard.addCardToHand(randomMon);
								}
							}
						}
						break;
					case INCREMENT:
					case BEAST:
						if (fromOrb) {
							incrementEffects();
						}
						break;
					case CREATOR:
						PuzzleHelper.creatorEffects();
						break;
					case TOON:
						if (config.getApplyToonWorld() != null && config.getApplyToonWorld()) {
							int amt = bonus ? 2 : 1;
							if (weakEffects) amt++;

							if (bonus) {
								DuelistCard.applyPowerToSelf(new ToonKingdomPower(p, p, amt));
							} else if (!AbstractDungeon.player.hasRelic(MillenniumEye.ID) && !AbstractDungeon.player.hasPower(ToonWorldPower.POWER_ID)) {
								DuelistCard.applyPowerToSelf(new ToonWorldPower(p, p, amt));
							}
						}
						break;
					case RANDOM_SMALL:
					case RANDOM_BIG:
					case RANDOM_UPGRADE:
					case METRONOME:
						int low = config.getRandomSummonTokensLowEnd();
						int high = config.getRandomSummonTokensHighEnd();
						TwoNums highLow = Util.getLowHigh(low, high);
						if (highLow.high() >= 1 && highLow.low() >= 0) {
							int summonRollA = AbstractDungeon.cardRandomRng.random(highLow.low(), highLow.high());
							if (weakEffects && summonRollA > highLow.low()) {
								summonRollA--;
							}
							DuelistCard token = StartingDeck.getTokenFromID(config.getTokenType());
							if (supeExplosive) {
								token = new SuperExplodingToken();
							} else if (explosiveTokens) {
								token = new ExplosiveToken();
							} else if (!typedTokens) {
								token = new PuzzleToken();
							}
							DuelistCard.summon(AbstractDungeon.player, summonRollA, token);
						}
						break;
				}
			}
		}
	}

	public static void runStartOfBattlePostDrawEffects() {
		if (!DuelistMod.puzzleEffectRanThisCombat) {
			switch (StartingDeck.currentDeck) {
				case DRAGON:
					AbstractDungeon.actionManager.addToBottom(new DragonPuzzleRunActionsAction());
					break;
				case AQUA:
					PuzzleHelper.aquaEffects();
					break;
				case INCREMENT:
				case BEAST:
					PuzzleHelper.incrementEffects();
					break;
			}
			DuelistMod.puzzleEffectRanThisCombat = true;
		}
	}

	public static boolean isWeakEffects() {
		return Util.getChallengeLevel() > 0 && Util.getChallengeLevel() < 7;
	}

	public static boolean isEffectsEnabled() {
		return Util.getChallengeLevel() < 7;
	}

	public static boolean isBonusEffects() {
		return AbstractDungeon.player != null && AbstractDungeon.player.relics != null && AbstractDungeon.player.hasRelic(MillenniumSymbol.ID);
	}

	private static boolean isCurrentDeckUniqueSummoningEffectDeck() {
		switch (StartingDeck.currentDeck) {
			case RANDOM_SMALL:
			case RANDOM_BIG:
			case RANDOM_UPGRADE:
			case METRONOME:
			case CREATOR:
				return true;
			default:
				return false;
		}
	}

	public static void aquaEffects() {
		PuzzleConfigData config = StartingDeck.AQUA.getActiveConfig();
		boolean bonus = isBonusEffects();
		boolean weakEffects = isWeakEffects();
		boolean effectsEnabled = isEffectsEnabled();
		if (!effectsEnabled) {
			return;
		}

		if (config.getOverflowDrawPile() != null && config.getOverflowDrawPile() && config.getDrawPileCardsToOverflow() > 0) {
			ArrayList<AbstractCard> overflowCards = new ArrayList<>();
			HashSet<String> added = new HashSet<>();

			// Find all cards that Overflow
			for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
				if (c instanceof DuelistCard && !added.contains(c.cardID)) {
					DuelistCard dC = (DuelistCard)c;
					if (dC.hasTag(Tags.IS_OVERFLOW)) {
						overflowCards.add(dC);
						added.add(c.cardID);
					}
				}
			}

			if (weakEffects && overflowCards.size() > 2) {
				int half = overflowCards.size() / 2;
				HashSet<Integer> indices = new HashSet<>();
				while (indices.size() < half) {
					indices.add(AbstractDungeon.cardRandomRng.random(0, overflowCards.size() - 1));
				}
				for (Integer index : indices) {
					overflowCards.remove((int)index);
				}
			}

			if (overflowCards.size() > 0) {
				int overflows = bonus ? 2 : 1;
				AbstractDungeon.actionManager.addToBottom(new CardSelectScreenTriggerOverflowAction(overflowCards, config.getDrawPileCardsToOverflow(), overflows, true));
			}
		}
	}

	public static void dragonEffects() {
		int floor = AbstractDungeon.actNum;
		PuzzleConfigData config = StartingDeck.DRAGON.getActiveConfig();
		boolean bonus = isBonusEffects();
		boolean weakEffects = isWeakEffects();
		boolean effectsEnabled = isEffectsEnabled() && config.getEffectsDisabled() != null && !config.getEffectsDisabled();
		if (!effectsEnabled) return;

		if (!DuelistMod.playingChallenge) {
			int effects = config.getEffectsChoices();
			if (bonus) { effects++; }
			if (effects < 0) {
				effects = 0;
			}
			if (effects > 0) {
				ArrayList<DuelistCard> choices = new ArrayList<>();
				choices.add(new PuzzleDragonRare());
				choices.add(new PuzzleDragonScales());
				choices.add(new PuzzleDragonStrength());
				choices.add(new PuzzleDragonTribute());
				choices.add(new PuzzleDragonBurning());
				choices.add(new PuzzleDragonWeak(floor));
				if (config.getEffectsToRemove() > 0) {
					int counter = config.getEffectsToRemove();
					while (counter > 0 && choices.size() > 1) {
						choices.remove(AbstractDungeon.cardRandomRng.random(0, choices.size() - 1));
						counter--;
					}
				}
				if (effects > choices.size()) {
					effects = choices.size();
				}
				if (choices.size() > 0) {
					AbstractDungeon.actionManager.addToTop(new DragonPuzzleAction(choices, effects));
				}
			}
		} else {
			int effects = config.getEffectsChoices();
			if (bonus) { effects++; }
			if (weakEffects) { effects--; }
			if (effects < 0) {
				effects = 0;
			}
			if (effects > 0) {
				ArrayList<DuelistCard> choices = new ArrayList<>();
				choices.add(new PuzzleDragonRare());
				choices.add(new PuzzleDragonScales());
				choices.add(new PuzzleDragonStrength());
				choices.add(new PuzzleDragonTribute());
				choices.add(new PuzzleDragonBurning());
				choices.add(new PuzzleDragonWeak(floor));
				if (config.getEffectsToRemove() > 0) {
					int counter = config.getEffectsToRemove();
					while (counter > 0 && choices.size() > 1) {
						choices.remove(AbstractDungeon.cardRandomRng.random(0, choices.size() - 1));
						counter--;
					}
				}
				if (weakEffects && choices.size() > 1) {
					choices.remove(AbstractDungeon.cardRandomRng.random(0, choices.size() - 1));
				}
				if (effects > choices.size()) {
					effects = choices.size();
				}
				if (choices.size() > 0) {
					AbstractDungeon.actionManager.addToTop(new DragonPuzzleAction(choices, effects));
				}
			}
		}
	}

	public static void spellcasterEffects() {
		PuzzleConfigData config = StartingDeck.SPELLCASTER.getActiveConfig();
		boolean weakEffects = isWeakEffects();
		boolean effectsEnabled = isEffectsEnabled();
		if (!effectsEnabled) return;

		if (StartingDeck.currentDeck == StartingDeck.SPELLCASTER && config.getChannelShadow() != null && config.getChannelShadow()) {
			int topRoll = weakEffects ? 4 : 3;
			int rollSp = Util.getChallengeLevel() > 0
					? AbstractDungeon.cardRandomRng.random(0, topRoll)
					: AbstractDungeon.cardRandomRng.random(DuelistMod.spellcasterRandomOrbsChanneled, topRoll);
			if (rollSp == topRoll) {
				DuelistCard.spellcasterPuzzleChannel();
				DuelistMod.currentSpellcasterOrbChance = 25;
				DuelistMod.spellcasterDidChannel = true;
			} else {
				DuelistMod.spellcasterDidChannel = false;
				int check = DuelistMod.spellcasterRandomOrbsChanneled;
				if (check == 0) { DuelistMod.currentSpellcasterOrbChance = 33;
				} else if (check == 1) { DuelistMod.currentSpellcasterOrbChance = 50;  } else if (check == 2) { DuelistMod.currentSpellcasterOrbChance = 100; }
			}
		}
	}

	public static void incrementEffects() {
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		boolean weakEffects = isWeakEffects();
		if (config.getIncrement() != null && config.getIncrement()) {
			int incAmt = config.getAmountToIncrement() != null ? config.getAmountToIncrement() : 0;
			if (config.getAmountToIncrementMatchesAct() != null && config.getAmountToIncrementMatchesAct()) {
				incAmt += AbstractDungeon.actNum;
			}
			if (weakEffects) {
				incAmt--;
			}
			if (incAmt > 0) {
				AbstractDungeon.actionManager.addToBottom(new IncrementAction(incAmt, AnyDuelist.from(AbstractDungeon.player)));
			}
		}
	}

	public static void zombieEffects() {
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		boolean bonus = isBonusEffects();
		boolean weakEffects = isWeakEffects();
		boolean effectsEnabled = isEffectsEnabled();
		if (!effectsEnabled) return;

		if (StartingDeck.currentDeck == StartingDeck.ZOMBIE && config.getChannelShadow() != null && config.getChannelShadow()) {
			DuelistCard.zombieShadowChannel();
			int cap = 6;
			if (weakEffects) { cap++; }
			if (bonus) { if (AbstractDungeon.cardRandomRng.random(1, cap) == 1) { DuelistCard.zombieShadowChannel(); }}
		} else if (StartingDeck.currentDeck == StartingDeck.ASCENDED_II && config.getChannelShadow() != null && config.getChannelShadow()) {
			int cap = 2;
			if (weakEffects) { cap++; }
			int roll = AbstractDungeon.miscRng.random(1, cap);
			if (roll == 1) { DuelistCard.zombieShadowChannel(); }
		}
	}

	private static void creatorEffects() {
		int roll = AbstractDungeon.cardRandomRng.random(1, 9);
		boolean weakEffects = Util.getChallengeLevel() > 0 && Util.getChallengeLevel() < 7;
		boolean effectsEnabled = Util.getChallengeLevel() < 7;
		boolean explosiveTokens = Util.getChallengeLevel() > 8 && Util.getChallengeLevel() < 16;
		boolean supeExplosive = Util.getChallengeLevel() > 15;
		if (effectsEnabled) {
			if (roll == 1) {
				DuelistCard jinzo = new Jinzo();
				DuelistCard.addCardToHand(jinzo);
				// whenever you play a trap card, gain 1 HP
			} else if (roll == 2) {
				DuelistCard jinzo = new TrapHole();
				if (!weakEffects) {
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo);


				// whenever you summon, gain 1 HP
			} else if (roll == 3) {
				DuelistCard jinzo = new Jinzo();
				if (!weakEffects) {
					jinzo.upgrade();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo);


				// whenever you play an Ethereal card, gain 1 HP
			} else if (roll == 4) {
				DuelistCard jinzo = new TrapHole();
				if (!weakEffects) {
					jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
					if (jinzo.costForTurn != jinzo.cost) { jinzo.isCostModifiedForTurn = true; }
				}
				DuelistCard.addCardToHand(jinzo);


				// whenever you play an Ethereal card, gain 1 HP
			} else if (roll == 5) {
				DuelistCard jinzo = new Jinzo();
				if (!weakEffects) {
					jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
					if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
				}
				DuelistCard.addCardToHand(jinzo);


				// whenever you play an Ethereal card, gain 2 HP
			} else if (roll == 6) {
				DuelistCard jinzo = new Jinzo();
				if (!weakEffects) {
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo);


				// whenever you play an Ethereal card, gain 5 Gold
			} else if (roll == 7) {
				DuelistCard jinzo = new TrapHole();
				if (!weakEffects) { jinzo.upgrade(); }
				DuelistCard.addCardToHand(jinzo);

				// whenever you play an Ethereal card, gain 1 Block (affected by dexterity)
			} else if (roll == 8) {
				DuelistCard jinzo = new UltimateOffering();
				if (!weakEffects) {
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo);

				// whenever you increment, gain 4 HP
			} else if (roll == 9) {
				DuelistCard jinzo = new Jinzo();
				DuelistCard trap = new TrapHole();
				DuelistCard ultimate = new UltimateOffering();
				if (!weakEffects) {
					jinzo.costForTurn = 0; trap.costForTurn = 0; ultimate.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true; trap.isCostModifiedForTurn = true; ultimate.isCostModifiedForTurn = true;
				}
				DuelistCard.addCardToHand(jinzo); DuelistCard.addCardToHand(trap); DuelistCard.addCardToHand(ultimate);

				// whenever you play an Ethereal card, gain 5 Gold and 2 HP
			}
		}
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		if (config.getTokensToSummon() > 0) {
			if (!explosiveTokens && !supeExplosive) {
				DuelistCard.summon(AbstractDungeon.player, config.getTokensToSummon(), StartingDeck.getTokenFromID(config.getTokenType()));
			} else if (explosiveTokens) {
				DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new ExplosiveToken());
				DuelistCard.summon(AbstractDungeon.player, config.getTokensToSummon(), tok);
			} else {
				DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new SuperExplodingToken());
				DuelistCard.summon(AbstractDungeon.player, config.getTokensToSummon(), tok);
			}
		}
	}

}
