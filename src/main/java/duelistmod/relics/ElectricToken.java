package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.RelicConfigData;
import duelistmod.powers.duelistPowers.ElectricityPower;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.ArrayList;
import java.util.List;

public class ElectricToken extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("ElectricToken");
	public static final String IMG =  DuelistMod.makeRelicPath("ElectricToken.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("ElectricToken_Outline.png");

	private static final String electricKey = "Electricity Gain";
	private static final int defaultElectric = 2;
	private static final String strKey = "Strength Gain";
	private static final int defaultStr = 5;

	public ElectricToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
		this.configDescMaxLines = 1;
	}
	
	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return !AbstractDungeon.player.hasRelic(ElectricBurst.ID);
	}
	
	@Override
	public void atPreBattle() {
		int magic = (int)this.getConfig(electricKey, defaultElectric);
		int strLoss = (int)this.getConfig(strKey, defaultStr) * -1;
		DuelistCard.applyPowerToSelf(new ElectricityPower(magic));
		DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, strLoss));
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.getProperties().put(electricKey, defaultElectric);
		config.getProperties().put(strKey, defaultStr);
		return config;
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Electricity Gain", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> magicOptions = new ArrayList<>();
		for (int i = 0; i < 1001; i++) { magicOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the amount of #yElectricity you start each combat with. Set to #b" + this.getDefaultConfig(electricKey) + " by default.";
		DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(electricKey, i);
			this.updateConfigSettings(data);
		});
		magicSelector.setSelected(this.getConfig(electricKey, defaultElectric).toString());

		LINEBREAK(15);

		settingElements.add(new ModLabel("Strength Loss", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> strOptions = new ArrayList<>();
		for (int i = 0; i < 1001; i++) { strOptions.add(String.valueOf(i)); }
		tooltip = "Modify the amount of #yStrength lost at the start of each combat. Set to #b" + this.getDefaultConfig(strKey) + " by default.";
		DuelistDropdown strSelector = new DuelistDropdown(tooltip, strOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(strKey, i);
			this.updateConfigSettings(data);
		});
		strSelector.setSelected(this.getConfig(strKey, defaultStr).toString());

		dropdowns.add(strSelector);
		dropdowns.add(magicSelector);
		LINEBREAK(25);
		return dropdowns;
	}

	@Override
	public void callUpdateDesc() {
		if (AbstractDungeon.player != null) {
			this.updateDescription(AbstractDungeon.player.chosenClass);
		}
	}

	@Override
	public void updateDescription(final AbstractPlayer.PlayerClass c) {
		this.description = this.getUpdatedDescription();
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
		this.initializeTips();
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.getConfig(electricKey, defaultElectric) + DESCRIPTIONS[1] + this.getConfig(strKey, defaultStr) + DESCRIPTIONS[2];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ElectricToken();
	}
}
