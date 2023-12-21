package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.RelicConfigData;
import duelistmod.helpers.PowHelper;
import duelistmod.powers.SummonPower;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.ArrayList;
import java.util.List;

public class ModdedDuelDisk extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("ModdedDuelDisk");
	public static final String IMG = DuelistMod.makeRelicPath("ModdedDuelDiskOrange.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("ModdedDuelDisk_Outline.png");
	private boolean rolledCombat = false;
	private static final ArrayList<String> messages = new ArrayList<>();

	private static final String energyKey = "Energy Gain";
	private static final int defaultEnergy = 2;
	private static final String summonKey = "Max Summon Loss";
	private static final int defaultSummons = 1;

	public ModdedDuelDisk() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
		this.configDescMaxLines = 1;
	}

	private void refreshMessages() {
		messages.clear();
		messages.add("What's wrong with my duel disk?");
		messages.add("This thing keeps taking away my Summons!");
		messages.add("I miss my old duel disk");
		SummonPower pow = PowHelper.getPower(SummonPower.POWER_ID);
		if (pow != null) {
			int maxSummons = pow.getMaxSummons();
			messages.add("How can I win with only " + maxSummons + " monster zone" + (maxSummons == 1 ? "?" : "s?"));
		}
	}

	@Override
	public void atBattleStart() {
		this.rolledCombat = false;
	}

	@Override
	public void atTurnStartPostDraw() {
		boolean even = GameActionManager.turn % 2 == 0;
		SummonPower pow = PowHelper.getPower(SummonPower.POWER_ID);
		int energy = (int)this.getConfig(energyKey, defaultEnergy);
		int lossSummons = (int)this.getConfig(summonKey, defaultSummons);
		if (even) {
			DuelistCard.gainEnergy(energy);
			this.flash();
		} else if (pow != null && pow.getMaxSummons() > 1) {
			DuelistCard.decMaxSummons(lossSummons);
			this.flash();
			if (!rolledCombat && AbstractDungeon.cardRandomRng.random(1, 100) <= 5) {
				rolledCombat = true;
				refreshMessages();
				int messageIndex = AbstractDungeon.cardRandomRng.random(0, messages.size() - 1);
				if (pow.getMaxSummons() == 1) {
					messageIndex = messages.size() - 1;
				}
				AbstractDungeon.actionManager.addToBottom(new TalkAction(true, messages.get(messageIndex), 3.5F, 3.0F));
			}
		}
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.put(energyKey, defaultEnergy);
		config.put(summonKey, defaultSummons);
		return config;
	}

	@Override
	public void callUpdateDesc() {
		this.setDescription();
	}

	@SuppressWarnings("StringConcatenationInLoop")
	@Override
	public String getUpdatedDescription() {
		int energy = (int)this.getConfig(energyKey, defaultEnergy);
		String energyTxt = energy > 0 ? "At the start of even turns, gain " : null;
		for (int i = 0; i < energy; i++) {
			if (i + 1 >= energy) {
				energyTxt += "[E]";
			} else {
				energyTxt += "[E] ";
			}
		}
		int lossSummons = (int)this.getConfig(summonKey, defaultSummons);
		String s = lossSummons == 1 ? "" : "s";
		String lossSummonsTxt = lossSummons > 0 ? "At the start of odd turns, lose #b" + lossSummons + " #yMax #ySummon" + s + "." : null;
		String output = (lossSummonsTxt != null ? lossSummonsTxt : "") + " " + (energyTxt != null ? energyTxt : "");
		return output.equals(" ") ? "All effects disabled" : output;
	}

	public void setDescription() {
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Energy Gain", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> magicOptions = new ArrayList<>();
		for (int i = 0; i < 51; i++) { magicOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the amount of #yEnergy you gain on even turns. Set to #b" + this.getDefaultConfig(energyKey) + " by default.";
		DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.put(energyKey, i);
			this.updateConfigSettings(data);
		});
		magicSelector.setSelected(this.getConfig(energyKey, defaultEnergy).toString());

		LINEBREAK(15);

		settingElements.add(new ModLabel("Max Summon Loss", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> strOptions = new ArrayList<>();
		for (int i = 0; i < 51; i++) { strOptions.add(String.valueOf(i)); }
		tooltip = "Modify the amount of #yMax #ySummons lost at the start of odd turns. Set to #b" + this.getDefaultConfig(summonKey) + " by default.";
		DuelistDropdown strSelector = new DuelistDropdown(tooltip, strOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.put(summonKey, i);
			this.updateConfigSettings(data);
		});
		strSelector.setSelected(this.getConfig(summonKey, defaultSummons).toString());

		dropdowns.add(strSelector);
		dropdowns.add(magicSelector);
		LINEBREAK(25);
		return dropdowns;
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ModdedDuelDisk();
	}
}
