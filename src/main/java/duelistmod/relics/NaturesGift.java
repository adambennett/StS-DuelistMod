package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.RelicConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.ArrayList;
import java.util.List;

public class NaturesGift extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("NaturesGift");
	public static final String IMG = DuelistMod.makeRelicPath("NatureRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("NatureRelic.png");

	private static final String incrementKey = "Increment Amount";
	private static final int defaultIncrement = 1;

	public NaturesGift() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Util.deckIs("Naturia Deck");
	}
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.getConfig(incrementKey, defaultIncrement) + DESCRIPTIONS[1];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new NaturesGift();
	}

	public int getIncrementAmount() {
		return (int)this.getConfig(incrementKey, defaultIncrement);
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.put(incrementKey, defaultIncrement);
		return config;
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Increment", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> magicOptions = new ArrayList<>();
		for (int i = 0; i < 51; i++) { magicOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the amount of #yMax #ySummons you gain when the effect is triggered. Set to #b" + this.getDefaultConfig(incrementKey) + " by default.";
		DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.put(incrementKey, i);
			this.updateConfigSettings(data);
		});
		magicSelector.setSelected(this.getConfig(incrementKey, defaultIncrement).toString());

		dropdowns.add(magicSelector);
		LINEBREAK(25);
		return dropdowns;
	}
}
