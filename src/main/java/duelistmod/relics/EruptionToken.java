package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.RelicConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.variables.Strings;

import java.util.ArrayList;
import java.util.List;

public class EruptionToken extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("EruptionToken");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	private static final String blockKey = "Amount of Block to Gain";
	private static final int defaultBlock = 10;

	public EruptionToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
        return Util.deckIs("Increment Deck");
	}

	@Override
	public void onEruption(DuelistCard erupted) {
		int block = (int)this.getConfig(blockKey, defaultBlock);
		AnyDuelist.from(erupted).block(block);
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.getProperties().put(blockKey, defaultBlock);
		return config;
	}

	@Override
	public String getUpdatedDescription() {
		int block = (int)this.getConfig(blockKey, defaultBlock);
		return DESCRIPTIONS[0] + block + DESCRIPTIONS[1];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new EruptionToken();
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Block", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> effectOptions = new ArrayList<>();
		for (int i = 1; i < 101; i++) { effectOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the amount of #yBlock you gain when the effect is triggered. Set to #b" + this.getDefaultConfig(blockKey) + " by default.";
		DuelistDropdown effectSelector = new DuelistDropdown(tooltip, effectOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(blockKey, (i+1));
			this.updateConfigSettings(data);
		});
		effectSelector.setSelected(this.getConfig(blockKey, defaultBlock).toString());

		dropdowns.add(effectSelector);
		LINEBREAK(25);
		return dropdowns;
	}
}
