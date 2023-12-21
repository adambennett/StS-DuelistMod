package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

import static duelistmod.enums.StartingDeck.*;

public class ChronicleOfElders extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("ChronicleOfElders");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	private static final String cardsKey = "Number of Cards to Trigger Effect";
	private static final int defaultCards = 5;
	private static final String energyKey = "Amount of Energy to gain";
	private static final int defaultEnergy = 1;

	public ChronicleOfElders() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.FLAT);
		this.counter = 0;
	}
	
	@Override
	public boolean canSpawn() {
        return super.canSpawn() && Util.deckIs(PHARAOH_I.getDeckName(), PHARAOH_II.getDeckName(), PHARAOH_III.getDeckName(), PHARAOH_IV.getDeckName(), PHARAOH_V.getDeckName());
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) {
		if (!(card instanceof DuelistCard)) {
			int trigger = (int)this.getConfig(cardsKey, defaultCards);
			int gain = (int)this.getConfig(energyKey, defaultEnergy);
			++this.counter;
			if (this.counter % trigger == 0) {
				this.counter = 0;
				if (gain > 0) {
					this.stopPulse();
					this.flash();
					AnyDuelist duelist = AnyDuelist.from(this);
					this.addToBot(new RelicAboveCreatureAction(duelist.creature(), this));
					duelist.gainEnergy(gain);
				}
			} else if (this.counter + 1 == trigger && gain > 0) {
				this.beginLongPulse();
			}
		}
	}

	@Override
	public String getUpdatedDescription() {
		int gain = (int)this.getConfig(energyKey, defaultEnergy);
		int beasts = (int)this.getConfig(cardsKey, defaultCards);
		StringBuilder energy = new StringBuilder();
		for (int i = 0; i < gain; i++) {
			energy.append(" [E] ");
		}
		return DESCRIPTIONS[0] + beasts + (beasts == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]) + (gain == 1 ? DESCRIPTIONS[3] : energy.toString());
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ChronicleOfElders();
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.getProperties().put(cardsKey, defaultCards);
		config.getProperties().put(energyKey, defaultEnergy);
		return config;
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> effectOptions = new ArrayList<>();
		for (int i = 1; i < 101; i++) { effectOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the amount of #ynon-Duelist cards you need to play to trigger the effect. Set to #b" + this.getDefaultConfig(cardsKey) + " by default.";
		DuelistDropdown effectSelector = new DuelistDropdown(tooltip, effectOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(cardsKey, (i+1));
			this.updateConfigSettings(data);
		});
		effectSelector.setSelected(this.getConfig(cardsKey, defaultCards).toString());

		LINEBREAK(15);

		settingElements.add(new ModLabel("Energy Gain", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> magicOptions = new ArrayList<>();
		for (int i = 0; i < 101; i++) { magicOptions.add(String.valueOf(i)); }
		tooltip = "Modify the amount of #yEnergy you gain when the effect is triggered. Set to #b" + this.getDefaultConfig(energyKey) + " by default.";
		DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(energyKey, i);
			this.updateConfigSettings(data);
		});
		magicSelector.setSelected(this.getConfig(energyKey, defaultEnergy).toString());

		dropdowns.add(magicSelector);
		dropdowns.add(effectSelector);
		LINEBREAK(25);
		return dropdowns;
	}
}
