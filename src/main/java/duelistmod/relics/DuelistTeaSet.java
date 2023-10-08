package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.IncrementAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.RelicConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.ArrayList;
import java.util.List;

public class DuelistTeaSet extends DuelistRelic {

	public static final String ID = duelistmod.DuelistMod.makeID("DuelistTeaSet");
	public static final String IMG = DuelistMod.makeRelicPath("DuelistTeaSet.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DuelistTeaSet_Outline.png");

	private static final String incrementKey = "Increment Amount on Trigger";
	private static final int defaultIncrement = 5;

	public DuelistTeaSet() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.SOLID);
	}

	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (Util.deckIs("Beast Deck")) return false;
		return Settings.isEndless || AbstractDungeon.floorNum <= 48;
	}

	@Override
	public void atBattleStart() {
		if (this.counter == -2) {
			this.pulse = false;
			this.counter = -1;
			this.flash();
			this.addToTop(new IncrementAction((int)this.getConfig(incrementKey, defaultIncrement), AnyDuelist.from(this)));
			this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
	}

	@Override
	public void onEnterRestRoom() {
		this.flash();
		this.counter = -2;
		this.pulse = true;
	}

	@Override
	public String getUpdatedDescription() {
		int inc = (int)this.getConfig(incrementKey, defaultIncrement);
		return DESCRIPTIONS[0] + inc + DESCRIPTIONS[inc == 1 ? 1 : 2];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new DuelistTeaSet();
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.getProperties().put(incrementKey, defaultIncrement);
		return config;
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Increment", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> effectOptions = new ArrayList<>();
		for (int i = 0; i < 1000; i++) { effectOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the number of #yMax #ySummons to grant when the effect is triggered. Set to #b" + this.getDefaultConfig(incrementKey) + " by default.";
		DuelistDropdown effectSelector = new DuelistDropdown(tooltip, effectOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(incrementKey, i);
			this.updateConfigSettings(data);
		});
		effectSelector.setSelected(this.getConfig(incrementKey, defaultIncrement).toString());

		dropdowns.add(effectSelector);
		LINEBREAK(25);
		return dropdowns;
	}
}
