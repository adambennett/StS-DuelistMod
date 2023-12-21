package duelistmod.events;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import basemod.IUIElement;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.relics.*;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.variables.Tags;

public class TombNamelessPuzzle extends DuelistEvent {
	public static final String ID = DuelistMod.makeID("TombNamelessPuzzle");
	public static final String IMG = DuelistMod.makeEventPath("TombNamelessD.png");
	private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
	private static final String NAME = eventStrings.NAME;
	private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
	private static final String[] OPTIONS = eventStrings.OPTIONS;

	public static final String DISABLE_NAMELESS_CARDS_KEY = "Disable Nameless Tomb Cards";

	public static boolean isNamelessCardsDisabled() {
		EventConfigData data = DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().getOrDefault(ID, null);
		if (data != null) {
			Object val = data.getProperties().getOrDefault(DISABLE_NAMELESS_CARDS_KEY, false);
			if (val instanceof Boolean) {
				return (boolean)val;
			}
		}
		return false;
	}

	private int screenNum = 0;
	private boolean magicAllowed = false;
	private boolean firstScreen = true;

	private final ArrayList<String> rewardsMagic = new ArrayList<>();
	private final ArrayList<String> rewardsGreed = new ArrayList<>();
	private final ArrayList<String> rewardsPower = new ArrayList<>();
	private final ArrayList<String> rewardsHunger = new ArrayList<>();
	private final ArrayList<String> rewardsWar = new ArrayList<>();

	private AbstractCard lvl4Magic;
	private AbstractCard lvl4Greed;
	private AbstractCard lvl4Power;
	private AbstractCard lvl4War;
	private AbstractCard randRarePower;
	private AbstractCard lvl2Greed;

	private AbstractRelic greed3R;
	private AbstractRelic power2R;
	private AbstractRelic power3R;
	private AbstractRelic hunger3R;
	private AbstractRelic war1R;
	private AbstractRelic war2R;
	private AbstractRelic war3R;

	private int greedInc = 0;
	private int warInc = 0;
	private int powerInc = 0;
	private int hungerInc = 0;
	private int magicInc = 0;
	private int points = 0;

	private String leave = "";
	private String btnMagic = "";
	private String btnPower = "";
	private String btnGreed = "";
	private String btnHunger = "";
	private String btnWar = "";
	private String magicR1 = "";
	private String magicR2 = "";
	private String magicR3 = "";
	private String magicR4 = "";
	private String hungerR1 = "";
	private String hungerR2 = "";
	private String hungerR3 = "";
	private String hungerR4 = "";
	private String powerR1 = "";
	private String powerR2 = "";
	private String powerR3 = "";
	private String powerR4 = "";
	private String warR1 = "";
	private String warR2 = "";
	private String warR3 = "";
	private String warR4 = "";
	private String greedR1 = "";
	private String greedR2 = "";
	private String greedR3 = "";
	private String greedR4 = "";

	// metrics vars
	private final HashMap<String, Object> metrics = new HashMap<>();
	private final List<String> obtainedCards = new ArrayList<>();
	private final List<String> obtainedRelics = new ArrayList<>();
	private int maxHPLoss = 0;
	private int maxHPGain = 0;
	private int hpLoss = 0;
	private int goldLoss = 0;
	private int goldGain = 0;

	public TombNamelessPuzzle() {
		super(ID, NAME, DESCRIPTIONS[0], IMG);
		this.spawnCondition = () -> !this.getActiveConfig().getIsDisabled();
		this.bonusCondition = () -> !this.getActiveConfig().getIsDisabled();
		if (AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null) {
			this.noCardsInRewards = true;
			leave = OPTIONS[0];
			calcPoints();
			checkMagicAllowed();
			setupRelics();
			resetOptions();
			setupRewardOptions();
		}
	}

	@Override
	protected void buttonEffect(int i) {
		// If attempting to Leave
		if (screenNum == -10) {
			finishLoggingMetrics();
			openMap();
		}

		// Otherwise
		else {
			// If points remaining, handle enhance reward options/go to rewards option/leave
			if (points > 0) {
				// Enhance Reward Options
				if (i < 5) {
					points--;
					nextScreen(i);
				}

				// Go to Rewards Option
				else if (i == 5) {
					points = 0;
					nextScreen(i);
				}

				// Leave
				else {
					this.imageEventText.updateDialogOption(0, leave);
					this.imageEventText.clearRemainingOptions();
					this.screenNum = -10;
				}
			}

			// No points remaining - handling rewards
			else {
				// Reward Options
				if (i < 5) {
					this.imageEventText.updateDialogOption(i, "[Locked] Reward Received", true);
					receiveRewards(i);
					if (!this.getActiveConfig().getMultipleChoices()) {
						this.imageEventText.updateDialogOption(0, leave);
						this.imageEventText.clearRemainingOptions();
						this.screenNum = -10;
					}
				}

				// Leave
				else if (i == 6) {
					this.imageEventText.updateDialogOption(0, leave);
					this.imageEventText.clearRemainingOptions();
					String dynamicInputKey = "spent_points";
					Map<String, Integer> dynamicInputKeys = new HashMap<>();
					dynamicInputKeys.put("magic", magicInc);
					dynamicInputKeys.put("power", powerInc);
					dynamicInputKeys.put("greed", greedInc);
					dynamicInputKeys.put("war", warInc);
					dynamicInputKeys.put("hunger", hungerInc);
					logDuelistMetric(dynamicInputKey, dynamicInputKeys);
					screenNum = -10;
				}
			}
		}
	}

	private void receiveRewards(int optionIndex) {
		switch (optionIndex) {
			// Magic
			case 0:
				if (magicInc == 1) {
					Util.log("Got Magic Reward #1");
					AbstractCard randMagic = this.lvl4Magic;
					AbstractCard randCurse = CardLibrary.getCurse();
					AbstractCard randCurseB = CardLibrary.getCurse();
					while (randCurseB.name.equals(randCurse.name)) {
						randCurseB = CardLibrary.getCurse();
					}
					AbstractCard randCurseC = CardLibrary.getCurse();
					while (randCurseC.name.equals(randCurseB.name) || randCurseC.name.equals(randCurse.name)) {
						randCurseC = CardLibrary.getCurse();
					}
					if (randMagic == null) {
						randMagic = Util.getSpecialMagicCardForNamelessTomb();
					}
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randMagic, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					AbstractDungeon.player.masterDeck.addToTop(randCurse);
					AbstractDungeon.player.masterDeck.addToTop(randCurseB);
					AbstractDungeon.player.masterDeck.addToTop(randCurseC);
					obtainedCards.add(randMagic.cardID);
					obtainedCards.add(randCurse.cardID);
					obtainedCards.add(randCurseB.cardID);
					obtainedCards.add(randCurseC.cardID);
					logOneRewardNum( "magic", magicInc);

				} else if (magicInc == 2) {
					Util.log("Got Magic Reward #2");
					AbstractCard randMagic = this.lvl4Magic;
					AbstractCard randCurse = CardLibrary.getCurse();
					AbstractCard randCurseB = CardLibrary.getCurse();
					while (randCurseB.name.equals(randCurse.name)) {
						randCurseB = CardLibrary.getCurse();
					}
					if (randMagic == null) {
						randMagic = Util.getSpecialMagicCardForNamelessTomb();
					}
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randCurse, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randCurseB, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randMagic, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					obtainedCards.add(randMagic.cardID);
					obtainedCards.add(randCurse.cardID);
					obtainedCards.add(randCurseB.cardID);
					logOneRewardNum( "magic", magicInc);
				} else if (magicInc == 3) {
					Util.log("Got Magic Reward #3");
					AbstractCard randMagic = this.lvl4Magic;
					AbstractCard randCurse = CardLibrary.getCurse();
					if (randMagic == null) {
						randMagic = Util.getSpecialMagicCardForNamelessTomb();
					}
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randCurse, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randMagic, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					obtainedCards.add(randMagic.cardID);
					obtainedCards.add(randCurse.cardID);
					logOneRewardNum("magic", magicInc);
				} else {
					Util.log("Got Magic Reward #4");
					AbstractCard randMagic = this.lvl4Magic;
					if (randMagic == null) {
						randMagic = Util.getSpecialMagicCardForNamelessTomb();
					}
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randMagic, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					obtainedCards.add(randMagic.cardID);
					logOneRewardNum("magic", magicInc);
				}
				break;

			// Power
			case 1:
				if (powerInc == 1) {
					Util.log("Got Power Reward #1");
					AbstractCard randMagic = this.randRarePower;
					if (randMagic == null) {
						randMagic = Util.getRandomRarePowerForNamelessTomb();
					}
					while (randMagic.canUpgrade()) {
						randMagic.upgrade();
					}
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randMagic, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					AbstractDungeon.player.damage(new DamageInfo(null, 8, DamageInfo.DamageType.HP_LOSS));
					obtainedCards.add(randMagic.cardID);
					hpLoss += 8;
					logOneRewardNum("power", powerInc);
				} else if (powerInc == 2) {
					Util.log("Got Power Reward #2");
					AbstractRelic r = this.power2R;
					if (r == null) {
						r = new NamelessPowerRelicA();
					}
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
					int goldLossAmt = AbstractDungeon.cardRandomRng.random(10, 75);
					AbstractDungeon.player.loseGold(goldLossAmt);
					obtainedRelics.add(r.relicId);
					goldLoss += goldLossAmt;
					logOneRewardNum("power", powerInc);
				} else if (powerInc == 3) {
					Util.log("Got Power Reward #3");
					AbstractRelic r = this.power3R;
					if (r == null) {
						r = new NamelessPowerRelicB();
					}
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
					AbstractDungeon.player.decreaseMaxHealth(4);
					obtainedRelics.add(r.relicId);
					maxHPLoss += 4;
					logOneRewardNum("power", powerInc);
				} else {
					Util.log("Got Power Reward #4");
					AbstractCard randMagic = this.lvl4Power;
					if (randMagic == null) {
						randMagic = Util.getSpecialPowerCardForNamelessTomb();
					}
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randMagic, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					obtainedCards.add(randMagic.cardID);
					logOneRewardNum("power", powerInc);
				}
				break;

			// Greed
			case 2:
				if (greedInc == 1) {
					Util.log("Got Greed Reward #1");
					AbstractDungeon.player.gainGold(250);
					AbstractDungeon.player.decreaseMaxHealth(6);
					goldGain += 250;
					maxHPLoss += 6;
					logOneRewardNum("greed", greedInc);
				} else if (greedInc == 2) {
					Util.log("Got Greed Reward #2");
					duelistGreedReward();
					// TODO
					logOneRewardNum("greed", greedInc);
				} else if (greedInc == 3) {
					Util.log("Got Greed Reward #3");
					AbstractRelic r = this.greed3R;
					if (r == null) {
						r = new NamelessGreedRelic();
					}
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
					AbstractCard randCurse = CardLibrary.getCurse();
					AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(randCurse, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					obtainedRelics.add(r.relicId);
					obtainedCards.add(randCurse.cardID);
					logOneRewardNum("greed", greedInc);
				} else {
					Util.log("Got Greed Reward #4");
					AbstractCard randMagic = this.lvl4Greed;
					if (randMagic == null) {
						randMagic = Util.getSpecialGreedCardForNamelessTomb();
					}
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randMagic, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					obtainedCards.add(randMagic.cardID);
					logOneRewardNum("greed", greedInc);
				}
				break;

			// Hunger
			case 3:
				if (hungerInc == 1) {
					Util.log("Got Hunger Reward #1");
					int maxHPRoll = AbstractDungeon.cardRandomRng.random(1, 15);
					int cursesRoll = AbstractDungeon.cardRandomRng.random(1, 3);
					Util.log("Nameless Tomb : Pharaoh Hunger : MaxHP=" + maxHPRoll + ", Curses=" + cursesRoll);
					AbstractDungeon.player.increaseMaxHp(maxHPRoll, true);
					maxHPGain += maxHPRoll;
					for (int i = 0; i < cursesRoll; i++) {
						AbstractCard randCurse = CardLibrary.getCurse();
						AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(randCurse, (float) Settings.WIDTH / 2.0f, (float) Settings.HEIGHT / 2.0f));
						obtainedCards.add(randCurse.cardID);
					}
					logOneRewardNum("hunger", hungerInc);
				} else if (hungerInc == 2) {
					Util.log("Got Hunger Reward #2");
					AbstractDungeon.player.increaseMaxHp(20, true);
					AbstractDungeon.player.damage(new DamageInfo(null, 45, DamageInfo.DamageType.HP_LOSS));
					maxHPGain += 20;
					hpLoss += 45;
					logOneRewardNum("hunger", hungerInc);
				} else if (hungerInc == 3) {
					Util.log("Got Hunger Reward #3");
					AbstractRelic r = this.hunger3R;
					if (r == null) {
						r = new NamelessHungerRelic();
					}
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
					AbstractDungeon.player.damage(new DamageInfo(null, 10, DamageInfo.DamageType.HP_LOSS));
					obtainedRelics.add(r.relicId);
					hpLoss += 10;
					logOneRewardNum("hunger", hungerInc);
				} else {
					Util.log("Got Hunger Reward #4");
					int maxHPRoll = AbstractDungeon.cardRandomRng.random(1, 20);
					Util.log("Nameless Tomb : Pharaoh Hunger : MaxHP=" + maxHPRoll);
					AbstractDungeon.player.increaseMaxHp(maxHPRoll, true);
					maxHPGain += maxHPRoll;
					logOneRewardNum("hunger", hungerInc);
				}
				break;

			// War
			case 4:
				if (warInc == 1) {
					Util.log("Got War Reward #1");
					AbstractRelic r = this.war1R;
					if (r == null) {
						r = new NamelessWarRelicA();
					}
					int dmgAmt = (int) (AbstractDungeon.player.maxHealth * 0.15);
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
					AbstractDungeon.player.damage(new DamageInfo(null, dmgAmt, DamageInfo.DamageType.HP_LOSS));
					obtainedRelics.add(r.relicId);
					hpLoss += dmgAmt;
					logOneRewardNum("war", warInc);
				} else if (warInc == 2) {
					Util.log("Got War Reward #2");
					AbstractRelic r = this.war2R;
					if (r == null) {
						r = new NamelessWarRelicB();
					}
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
					float percentage = AbstractDungeon.cardRandomRng.random(3, 7);
					int origi = (int) percentage;
					percentage = (0.1f * percentage);
					int goldLose = (int) (AbstractDungeon.player.gold * percentage);
					AbstractDungeon.player.loseGold(goldLose);
					Util.log("War Reward #2 - percentage=" + percentage + ", roll=" + origi);
					obtainedRelics.add(r.relicId);
					goldLoss += goldLose;
					logOneRewardNum("war", warInc);
				} else if (warInc == 3) {
					Util.log("Got War Reward #3");
					AbstractRelic r = this.war3R;
					if (r == null) {
						r = new NamelessWarRelicC();
					}
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
					AbstractDungeon.player.decreaseMaxHealth(10);
					obtainedRelics.add(r.relicId);
					maxHPLoss += 10;
					logOneRewardNum("war", warInc);
				} else {
					Util.log("Got War Reward #4");
					AbstractCard randMagic = this.lvl4War;
					if (randMagic == null) {
						randMagic = Util.getSpecialWarCardForNamelessTomb();
					}
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randMagic, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					obtainedCards.add(randMagic.cardID);
					logOneRewardNum("war", warInc);
				}
				break;
		}
	}

	private void nextScreen(int optionIndex) {
		this.imageEventText.clearAllDialogs();
		switch (optionIndex) {
			case 0:
				magicInc++;
				break;
			case 1:
				powerInc++;
				break;
			case 2:
				greedInc++;
				break;
			case 3:
				hungerInc++;
				break;
			case 4:
				warInc++;
				break;
		}
		resetOptions();
	}

	private void calcPoints() {
		//points = DuelistMod.namelessTombPoints;
		points = ThreadLocalRandom.current().nextInt(1, 20);
		if (points > 8) {
			points = ThreadLocalRandom.current().nextInt(2, 20);
		}
		if (points == 20) {
			points = ThreadLocalRandom.current().nextInt(10, 20);
		}
		//if (points > 20) { points = 20; }
		if (points > 10 && Util.getChallengeLevel() > 0) {
			points = ThreadLocalRandom.current().nextInt(6, 10);
		}
		logOneNum("points", points);
	}

	private int checkMagicAllowed() {
		int totalUpgrades = 0;
		int arcaneCards = 0;
		int cards = 0;
		int duelistCards = 0;
		int totalMagicNum = 0;
		int totalTribCost = 0;
		int totalSummons = 0;
		int spells = 0;
		int traps = 0;
		int curses = 0;
		boolean hasDragon = false;
		boolean hasMoreTrapsThanSpells = false;
		boolean hasExemptCard = false;
		boolean hasLOBECard = false;
		int totalScore = 0;
		String lobeCard = "";
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			cards++;
			if (c.hasTag(Tags.ARCANE)) {
				arcaneCards++;
			}
			if (c.hasTag(Tags.SPELL)) {
				spells++;
			}
			if (c.hasTag(Tags.TRAP)) {
				traps++;
			}
			if (c instanceof DuelistCard) {
				duelistCards++;
				DuelistCard dc = (DuelistCard) c;
				if (dc.isTributeCard()) {
					totalTribCost += dc.tributes;
				}
				if (dc.isSummonCard()) {
					totalSummons += dc.summons;
				}
			}
			totalUpgrades += c.timesUpgraded;
			if (c.magicNumber > 0) {
				totalMagicNum += c.magicNumber;
			}
			if (c.hasTag(Tags.DRAGON)) {
				hasDragon = true;
			}
			if (Util.isExempt(c)) {
				hasExemptCard = true;
			}
			if (c.hasTag(Tags.LEGEND_BLUE_EYES)) {
				hasLOBECard = true;
				lobeCard = c.originalName;
			}
			if (c.type.equals(CardType.CURSE)) {
				curses++;
			}
		}

		if (spells < traps) {
			hasMoreTrapsThanSpells = true;
		}

		totalScore = totalUpgrades + arcaneCards + totalMagicNum + spells;
		totalScore -= curses * 2;
		Util.log("Nameless Tomb - Magic Scoring: +" + totalUpgrades + " for number of upgrades");
		Util.log("Nameless Tomb - Magic Scoring: +" + arcaneCards + " for number of Arcane cards");
		Util.log("Nameless Tomb - Magic Scoring: +" + totalMagicNum + " for sum of all magic numbers in your deck");
		Util.log("Nameless Tomb - Magic Scoring: +" + spells + " for number of Spells");
		Util.log("Nameless Tomb - Magic Scoring: -" + curses * 2 + " for number of Curses");
		if (hasDragon) {
			totalScore += 10;
			Util.log("Nameless Tomb - Magic Scoring: +10 for having a dragon!");
		}
		if (hasExemptCard) {
			totalScore += 8;
			Util.log("Nameless Tomb - Magic Scoring: +8 for having an Exempt card!");
		}
		if (hasLOBECard) {
			totalScore += 10;
			Util.log("Nameless Tomb - Magic Scoring: +10 for having a 'Legend of Blue Eyes' card! (" + lobeCard + ")");
		}
		if (hasMoreTrapsThanSpells) {
			totalScore += 15;
			Util.log("Nameless Tomb - Magic Scoring: +15 for having more traps than spells!");
		}
		if (totalSummons == totalTribCost) {
			totalScore += 15;
			Util.log("Nameless Tomb - Magic Scoring: +15 for having an equal total tribute cost and total summons!");
		}
		if (cards == duelistCards) {
			totalScore += 5;
			Util.log("Nameless Tomb - Magic Scoring: +5 for having all Duelist cards!");
		}
		Util.log("Nameless Tomb - Magic Total Score: " + totalScore + " -- You need a 26 or higher to enable the Magic Option");
		this.magicAllowed = totalScore > 40;
		logOneNum("total_magic_score", totalScore);
		return totalScore;
	}

	private void resetOptions() {
		btnMagic = OPTIONS[1] + (magicInc) + OPTIONS[6];
		btnPower = OPTIONS[2] + (powerInc) + OPTIONS[6];
		btnGreed = OPTIONS[3] + (greedInc) + OPTIONS[6];
		btnHunger = OPTIONS[4] + (hungerInc) + OPTIONS[6];
		btnWar = OPTIONS[5] + (warInc) + OPTIONS[6];

		if (this.points > 0) {
			// Magic
			if (this.magicAllowed) {
				imageEventText.setDialogOption(btnMagic, this.magicInc > 3);
			} else {
				imageEventText.setDialogOption("[Locked] Requires Arcane Magic", true);
			}

			// Power
			imageEventText.setDialogOption(btnPower, this.powerInc > 3);

			// Greed
			if (AbstractDungeon.player.gold > 200) {
				imageEventText.setDialogOption(btnGreed, this.greedInc > 3);
			} else {
				imageEventText.setDialogOption("[Locked] Requires 200+ Gold", true);
			}

			// Hunger
			imageEventText.setDialogOption(btnHunger, this.hungerInc > 3);

			// War
			if (Util.getChallengeLevel() > -1 || AbstractDungeon.ascensionLevel > 4) {
				imageEventText.setDialogOption(btnWar, this.warInc > 3);
			} else {
				imageEventText.setDialogOption("[Locked] Requires Challenge Mode or Ascension 5+", true);
			}

			if (points == 1) {
				imageEventText.setDialogOption("#gGo #gto #gRewards #r(" + points + " #rPoint #rRemaining)");
			} else {
				imageEventText.setDialogOption("#gGo #gto #gRewards #g(" + points + " #gPoints #gRemaining)");
			}


			// Leave
			if (firstScreen) {
				imageEventText.setDialogOption(leave);
				firstScreen = false;
			} else {
				imageEventText.setDialogOption(leave, true);
			}
		} else {
			//modifyIncrementers();
			if (this.magicAllowed) {
				handleMagicReward();
			} else {
				imageEventText.setDialogOption("[Locked] Requires Arcane Magic", true);
			}
			handlePowerReward();
			if (AbstractDungeon.player.gold > 120) {
				handleGreedReward();
			} else {
				imageEventText.setDialogOption("[Locked] Requires 120+ Gold", true);
			}
			handleHungerReward();
			if (Util.getChallengeLevel() > -1 || AbstractDungeon.ascensionLevel > 4) {
				handleWarReward();
			} else {
				imageEventText.setDialogOption("[Locked] Requires Ascension 5+", true);
			}
			imageEventText.setDialogOption("[Locked] Reward Screen", true);
			imageEventText.setDialogOption(leave);
		}
	}

	private void setupRewardOptions() {
		magicR1 = OPTIONS[7];
		magicR2 = OPTIONS[8];
		magicR3 = OPTIONS[9];
		magicR4 = OPTIONS[10];
		greedR1 = OPTIONS[11];
		greedR2 = OPTIONS[12];
		greedR3 = OPTIONS[13];
		greedR4 = OPTIONS[14];
		powerR1 = OPTIONS[15];
		powerR2 = OPTIONS[16];
		powerR3 = OPTIONS[17];
		powerR4 = OPTIONS[18];
		hungerR1 = OPTIONS[19];
		hungerR2 = OPTIONS[20];
		hungerR3 = OPTIONS[21];
		hungerR4 = OPTIONS[22];
		warR1 = OPTIONS[23];
		warR2 = OPTIONS[24];
		warR3 = OPTIONS[25];
		warR4 = OPTIONS[26];

		rewardsMagic.add(magicR1);
		rewardsMagic.add(magicR2);
		rewardsMagic.add(magicR3);
		rewardsMagic.add(magicR4);

		rewardsPower.add(powerR1);
		rewardsPower.add(powerR2);
		rewardsPower.add(powerR3);
		rewardsPower.add(powerR4);

		rewardsGreed.add(greedR1);
		rewardsGreed.add(greedR2);
		rewardsGreed.add(greedR3);
		rewardsGreed.add(greedR4);

		rewardsHunger.add(hungerR1);
		rewardsHunger.add(hungerR2);
		rewardsHunger.add(hungerR3);
		rewardsHunger.add(hungerR4);

		rewardsWar.add(warR1);
		rewardsWar.add(warR2);
		rewardsWar.add(warR3);
		rewardsWar.add(warR4);
	}

	private void handleMagicReward() {
		if (magicInc == 0) {
			imageEventText.setDialogOption("[Locked] Low Mana", true);
		} else {
			this.lvl4Magic = Util.getSpecialMagicCardForNamelessTomb();
			//log(lvl4Magic);
			imageEventText.setDialogOption(rewardsMagic.get(magicInc - 1), this.lvl4Magic);
		}
	}

	private void handleGreedReward() {
		if (greedInc == 0) {
			imageEventText.setDialogOption("[Locked] Generous", true);
		} else if (greedInc == 1) {
			imageEventText.setDialogOption(rewardsGreed.get(greedInc - 1));
		} else if (greedInc == 2) {
			this.lvl2Greed = Util.getSpecialSparksCardForNamelessTomb();
			imageEventText.setDialogOption(rewardsGreed.get(greedInc - 1) + getSparksName() + OPTIONS[27], this.lvl2Greed);
		} else if (greedInc == 3) {
			imageEventText.setDialogOption(rewardsGreed.get(greedInc - 1), this.greed3R);
		} else if (greedInc == 4) {
			this.lvl4Greed = Util.getSpecialGreedCardForNamelessTomb();
			//log(lvl4Greed);
			imageEventText.setDialogOption(rewardsGreed.get(greedInc - 1), this.lvl4Greed);
		}
	}

	private void handlePowerReward() {
		if (powerInc == 0) {
			imageEventText.setDialogOption("[Locked] Weak", true);
			return;
		}
		if (powerInc == 1) {
			this.randRarePower = Util.getRandomRarePowerForNamelessTomb();
			//log(randRarePower);
			imageEventText.setDialogOption(rewardsPower.get(powerInc - 1), this.randRarePower);
		} else if (powerInc == 2) {
			imageEventText.setDialogOption(rewardsPower.get(powerInc - 1), this.power2R);
		} else if (powerInc == 3) {
			imageEventText.setDialogOption(rewardsPower.get(powerInc - 1), this.power3R);
		} else if (powerInc == 4) {
			this.lvl4Power = Util.getSpecialPowerCardForNamelessTomb();
			//log(lvl4Power);
			imageEventText.setDialogOption(rewardsPower.get(powerInc - 1), this.lvl4Power);
		}
	}

	private void handleHungerReward() {
		if (hungerInc == 0) {
			imageEventText.setDialogOption("[Locked] Well-Fed", true);
		} else if (hungerInc == 3) {
			imageEventText.setDialogOption(rewardsHunger.get(hungerInc - 1), this.hunger3R);
		} else {
			imageEventText.setDialogOption(rewardsHunger.get(hungerInc - 1));
		}
	}

	private void handleWarReward() {
		if (warInc == 0) {
			imageEventText.setDialogOption("[Locked] Peaceful", true);
		} else if (warInc == 1) {
			imageEventText.setDialogOption(rewardsWar.get(warInc - 1), this.war1R);
		} else if (warInc == 2) {
			imageEventText.setDialogOption(rewardsWar.get(warInc - 1), this.war2R);
		} else if (warInc == 3) {
			imageEventText.setDialogOption(rewardsWar.get(warInc - 1), this.war3R);
		} else if (warInc == 4) {
			this.lvl4War = Util.getSpecialWarCardForNamelessTomb();
			//log(lvl4War);
			imageEventText.setDialogOption(rewardsWar.get(warInc - 1), this.lvl4War);
		}
	}

	private void setupRelics() {
		this.greed3R = new NamelessGreedRelic();
		this.power2R = new NamelessPowerRelicA();
		this.power3R = new NamelessPowerRelicB();
		this.hunger3R = new NamelessHungerRelic();
		this.war1R = new NamelessWarRelicA();
		this.war2R = new NamelessWarRelicB();
		this.war3R = new NamelessWarRelicC();
	}

	private String getSparksName() {
		if (this.lvl2Greed instanceof GoldenSparks) {
			return "#gGolden #gSparks.";
		} else if (this.lvl2Greed instanceof DarkSparks) {
			return "#gDark #gSparks.";
		} else if (this.lvl2Greed instanceof StormSparks) {
			return "#gStorm #gSparks.";
		} else if (this.lvl2Greed instanceof MagicSparks) {
			return "#gMagic #gSparks.";
		} else if (this.lvl2Greed instanceof BloodSparks) {
			return "#gBlood #gSparks.";
		} else {
			return "error";
		}
	}

	private void duelistGreedReward() {
		AbstractDungeon.player.loseGold((int) (AbstractDungeon.player.gold / 2.0f));
		int monsters = 0;
		int upgrades = 0;
		ArrayList<AbstractCard> toKeep = new ArrayList<>();
		ArrayList<AbstractCard> toKeepFromDeck = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (c.hasTag(Tags.SPARKS)) {
				monsters++;
				upgrades += c.timesUpgraded;
			} else {
				toKeepFromDeck.add(c);
			}
		}

		for (int i = 0; i < monsters; i++) {
			AbstractCard c = this.lvl2Greed.makeCopy();
			while (upgrades > 0 && c.canUpgrade()) {
				c.upgrade();
				upgrades--;
			}
			toKeep.add(c.makeStatEquivalentCopy());
		}

		for (AbstractCard c : toKeep) {
			obtainedCards.add(c.cardID);
		}

		AbstractDungeon.player.masterDeck.group.clear();
		if (AbstractDungeon.player.hasRelic(MarkExxod.ID)) {
			for (AbstractCard c : toKeepFromDeck) {
				AbstractDungeon.player.masterDeck.addToTop(c);
			}
			for (AbstractCard c : toKeep) {
				AbstractDungeon.player.masterDeck.addToBottom(c);
			}
		} else {
			for (AbstractCard c : toKeepFromDeck) {
				AbstractDungeon.player.masterDeck.addToRandomSpot(c);
			}
			for (AbstractCard c : toKeep) {
				AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, (float) Settings.WIDTH / 2.0f, (float) Settings.HEIGHT / 2.0f));
			}
		}
	}

	public void logOneRewardNum(String numKey, int num) {
		metrics.put(numKey + "_reward", num);
	}

	public void logOneNum(String key, int num) {
		metrics.put(key, num);
	}

	public void logDuelistMetric(String dynamicInputKey, Map<String, Integer> dynamicInput) {
		metrics.put(dynamicInputKey, dynamicInput);
	}

	public void finishLoggingMetrics() {
		metrics.put("event_name", NAME);
		metrics.put("floor", AbstractDungeon.floorNum);
		metrics.put("duelist", true);
		metrics.put("max_hp_loss", maxHPLoss);
		metrics.put("max_hp_gain", maxHPGain);
		metrics.put("cards_obtained", obtainedCards);
		metrics.put("relics_obtained", obtainedRelics);
		metrics.put("gold_loss", goldLoss);
		metrics.put("gold_gain", goldGain);
		metrics.put("damage_taken", hpLoss);
		CardCrawlGame.metricData.event_choices.add(metrics);
	}

	@Override
	public DuelistConfigurationData getConfigurations() {
		RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
		ArrayList<IUIElement> settingElements = new ArrayList<>();
		EventConfigData onLoad = this.getActiveConfig();

		String tooltip = "When enabled, allows you encounter this event during runs. Enabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Event Enabled", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !onLoad.getIsDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			EventConfigData data = this.getActiveConfig();
			data.setIsDisabled(!button.enabled);
			this.updateConfigSettings(data);
		}));

		LINEBREAK();

		tooltip = "When enabled, allows you to receive multiple rewards before you must leave the Tomb. Disabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Multiple Rewards", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, onLoad.getMultipleChoices(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			EventConfigData data = this.getActiveConfig();
			data.setMultipleChoices(button.enabled);
			this.updateConfigSettings(data);
		}));

		LINEBREAK();

		tooltip = "When enabled, the powerful cards received from the Nameless Tomb event will be replaced by the standard versions of the cards instead. Disabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Replace reward cards with standard copies", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, TombNamelessPuzzle.isNamelessCardsDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			EventConfigData data = this.getActiveConfig();
			data.put(DISABLE_NAMELESS_CARDS_KEY, button.enabled);
			this.updateConfigSettings(data);

		}));
		return new DuelistConfigurationData(this.title, settingElements, this);
	}
}
