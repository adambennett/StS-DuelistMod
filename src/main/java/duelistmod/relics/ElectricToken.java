package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.RelicConfigData;
import duelistmod.powers.duelistPowers.ElectricityPower;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.ArrayList;

public class ElectricToken extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("ElectricToken");
	public static final String IMG =  DuelistMod.makeRelicPath("ElectricToken.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("ElectricToken_Outline.png");

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
		RelicConfigData config = this.getActiveConfig();
		int magic = config.getMagic();
		int strLoss = config.getStrengthLoss();
		DuelistCard.applyPowerToSelf(new ElectricityPower(magic));
		DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, strLoss));
	}

	@Override
	public RelicConfigData getDefaultConfig() { return new RelicConfigData(false, 2, 4); }

	@Override
	protected void configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		RelicConfigData onLoad = DuelistMod.relicCanSpawnConfigMap.getOrDefault(this.relicId, this.getDefaultConfig());

		settingElements.add(new ModLabel("Electricity Gain", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> magicOptions = new ArrayList<>();
		for (int i = 0; i < 1001; i++) { magicOptions.add(i+""); }
		String tooltip = "Modify the amount of #yElectricity you start each combat with. Set to #b2 by default.";
		DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = DuelistMod.relicCanSpawnConfigMap.getOrDefault(this.relicId, this.getDefaultConfig());
			data.setMagic(i);
			DuelistMod.relicCanSpawnConfigMap.put(this.relicId, data);
			try
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				String relicConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.relicCanSpawnConfigMap);
				config.setString("relicCanSpawnConfigMap", relicConfigMap);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
		});
		magicSelector.setSelectedIndex(onLoad.getMagic());

		settingElements.add(new ModLabel("Strength Loss", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> strOptions = new ArrayList<>();
		for (int i = 0; i < 1001; i++) { strOptions.add(i+""); }
		tooltip = "Modify the amount of #yStrength lost at the start of each combat. Set to #b5 by default.";
		DuelistDropdown strSelector = new DuelistDropdown(tooltip, strOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = DuelistMod.relicCanSpawnConfigMap.getOrDefault(this.relicId, this.getDefaultConfig());
			data.setStrengthLoss(i);
			DuelistMod.relicCanSpawnConfigMap.put(this.relicId, data);
			try
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				String relicConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.relicCanSpawnConfigMap);
				config.setString("relicCanSpawnConfigMap", relicConfigMap);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
		});
		strSelector.setSelectedIndex(onLoad.getStrengthLoss());

		settingElements.add(strSelector);
		settingElements.add(magicSelector);
		LINEBREAK(15);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ElectricToken();
	}
}
