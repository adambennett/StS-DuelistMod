package duelistmod.events;

import basemod.IUIElement;
import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.relics.warrior.AmuletOfGlory;
import duelistmod.relics.warrior.CombatBracelet;
import duelistmod.relics.warrior.RegenBracelet;
import duelistmod.relics.warrior.RingOfRecoil;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;

import java.util.ArrayList;

public class EgyptVillage extends DuelistEvent {

	public static final String ID = DuelistMod.makeID("EgyptVillage");
	public static final String IMG = DuelistMod.makeEventPath("EgyptVillage.png");
	private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
	private static final String NAME = eventStrings.NAME;
	private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
	private static final String[] OPTIONS = eventStrings.OPTIONS;

	private static final int COST = 200;

	// option indexes
	private static final int OPT_REGEN = 0;
	private static final int OPT_AMULET = 1;
	private static final int OPT_COMBAT = 2;
	private static final int OPT_RECOIL = 3;
	private static final int OPT_LEAVE = 4;

	// UI suffixes
	private static final String NOT_ENOUGH_GOLD_SUFFIX = " [Not enough Gold]";
	private static final String PURCHASED_LOCK_TEXT = "[Locked] Purchased";

	private int screenNum = 0;

	// track purchases only when multiple rewards is enabled (locks per option)
	private final boolean[] purchased = new boolean[] { false, false, false, false };

	public EgyptVillage() {
		super(ID, NAME, DESCRIPTIONS[0], IMG);

		Condition enabled = () -> !this.getActiveConfig().getIsDisabled();
		Condition warriorDeck = () -> Util.deckIs("Warrior Deck");
		Condition act2Plus = () -> AbstractDungeon.actNum >= 2;

		Condition all = () -> enabled.test() && warriorDeck.test() && act2Plus.test();
		this.spawnCondition = all;
		this.bonusCondition = all;

		if (AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null) {
			// Preview relics in the UI
			imageEventText.setDialogOption(buildBuyOptionText(OPTIONS[OPT_REGEN], false), new RegenBracelet());
			imageEventText.setDialogOption(buildBuyOptionText(OPTIONS[OPT_AMULET], false), new AmuletOfGlory());
			imageEventText.setDialogOption(buildBuyOptionText(OPTIONS[OPT_COMBAT], false), new CombatBracelet());
			imageEventText.setDialogOption(buildBuyOptionText(OPTIONS[OPT_RECOIL], false), new RingOfRecoil());
			imageEventText.setDialogOption(OPTIONS[OPT_LEAVE]);
		}
	}

	private boolean canAfford() {
		return AbstractDungeon.player != null && AbstractDungeon.player.gold >= COST;
	}

	private String buildBuyOptionText(String baseOption, boolean notEnoughGold) {
		String text = baseOption + " (" + COST + " Gold)";
		if (notEnoughGold) text += NOT_ENOUGH_GOLD_SUFFIX;
		return text;
	}

	private AbstractRelic relicForOption(int optionIndex) {
		switch (optionIndex) {
			case OPT_REGEN: return new RegenBracelet();
			case OPT_AMULET: return new AmuletOfGlory();
			case OPT_COMBAT: return new CombatBracelet();
			case OPT_RECOIL: return new RingOfRecoil();
			default: return null;
		}
	}

	private String metricKeyForOption(int optionIndex) {
		switch (optionIndex) {
			case OPT_REGEN: return "Buy Regen Bracelet";
			case OPT_AMULET: return "Buy Amulet of Glory";
			case OPT_COMBAT: return "Buy Combat Bracelet";
			case OPT_RECOIL: return "Buy Ring of Recoil";
			default: return "Buy";
		}
	}

	private void completePurchase(int optionIndex) {
		if (AbstractDungeon.player == null) return;

		// Spend gold + grant relic
		AbstractDungeon.player.loseGold(COST);

		AbstractRelic relic = relicForOption(optionIndex);
		if (relic != null) {
			AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f, relic);
		}

		// Log
		logDuelistMetric(NAME, metricKeyForOption(optionIndex));

		EventConfigData config = this.getActiveConfig();
		if (config.getMultipleChoices()) {
			// Lock only the option you bought, stay on main screen
			purchased[optionIndex] = true;
			imageEventText.updateDialogOption(optionIndex, PURCHASED_LOCK_TEXT, true);

			// After purchase, gold changed, so refresh affordability for remaining options
			updateBuyLocksForGoldAndPurchases();
		} else {
			// Single purchase path: go to purchased screen
			screenNum = 1;
			imageEventText.updateBodyText(DESCRIPTIONS[1]);
			imageEventText.updateDialogOption(0, OPTIONS[OPT_LEAVE]);
			imageEventText.clearRemainingOptions();
		}
	}

	private void updateBuyLocksForGoldAndPurchases() {
		boolean afford = canAfford();

		for (int idx = 0; idx < 4; idx++) {
			// Already purchased? hard-lock
			if (purchased[idx]) {
				imageEventText.updateDialogOption(idx, PURCHASED_LOCK_TEXT, true);
				continue;
			}

			// Not purchased: show price, and if player can't afford show suffix + disable
			boolean disable = !afford;
			String text = buildBuyOptionText(OPTIONS[idx], !afford);
			imageEventText.updateDialogOption(idx, text, disable);
		}
	}

	@Override
	protected void buttonEffect(int i) {
		switch (screenNum) {
			case 0: {
				EventConfigData config = this.getActiveConfig();

				// Always keep UI up to date (gold can change in weird modded situations)
				updateBuyLocksForGoldAndPurchases();

				// Buy buttons
				if (i >= 0 && i <= 3) {
					// If multiple choices is enabled, purchased options are already disabled,
					// but just in case:
					if (purchased[i]) return;

					if (!canAfford()) return;

					completePurchase(i);

					// If multiple choices is enabled and all 4 are purchased, auto-finish to purchased text
					if (config.getMultipleChoices()
							&& purchased[0] && purchased[1] && purchased[2] && purchased[3]) {
						screenNum = 1;
						imageEventText.updateBodyText(DESCRIPTIONS[1]);
						imageEventText.updateDialogOption(0, OPTIONS[OPT_LEAVE]);
						imageEventText.clearRemainingOptions();
					}

					return;
				}

				// Leave
				if (i == OPT_LEAVE) {
					imageEventText.updateBodyText(DESCRIPTIONS[2]);
					imageEventText.updateDialogOption(0, OPTIONS[OPT_LEAVE]);
					imageEventText.clearRemainingOptions();
					logDuelistMetric(NAME, "Leave");
					screenNum = 2;
				}
				break;
			}

			case 1:
			case 2:
				openMap();
				break;
		}
	}

	@Override
	public void onEnterRoom() {
		super.onEnterRoom();
		if (screenNum == 0) {
			updateBuyLocksForGoldAndPurchases();
		}
	}

	@Override
	public DuelistConfigurationData getConfigurations() {
		RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
		ArrayList<IUIElement> settingElements = new ArrayList<>();
		EventConfigData onLoad = this.getActiveConfig();

		String tooltip = "When enabled, allows you encounter this event during runs. Enabled by default.";
		settingElements.add(new DuelistLabeledToggleButton(
				"Event Enabled",
				tooltip,
				DuelistMod.xLabPos,
				DuelistMod.yPos,
				Settings.CREAM_COLOR,
				FontHelper.charDescFont,
				!onLoad.getIsDisabled(),
				DuelistMod.settingsPanel,
				(label) -> {},
				(button) -> {
					EventConfigData data = this.getActiveConfig();
					data.setIsDisabled(!button.enabled);
					this.updateConfigSettings(data);
				}
		));

		LINEBREAK();

		tooltip = "When enabled, allows you to buy multiple relics before leaving the Village. Disabled by default.";
		settingElements.add(new DuelistLabeledToggleButton(
				"Multiple Rewards",
				tooltip,
				DuelistMod.xLabPos,
				DuelistMod.yPos,
				Settings.CREAM_COLOR,
				FontHelper.charDescFont,
				onLoad.getMultipleChoices(),
				DuelistMod.settingsPanel,
				(label) -> {},
				(button) -> {
					EventConfigData data = this.getActiveConfig();
					data.setMultipleChoices(button.enabled);
					this.updateConfigSettings(data);
				}
		));

		return new DuelistConfigurationData(this.title, settingElements, this);
	}
}