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
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.RelicConfigData;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.FangsPower;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class ClawedCodex extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("ClawedCodex");
	public static final String IMG =  DuelistMod.makeRelicPath("ClawedCodex.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("ClawedCodex_Outline.png");

	private static final String cardsKey = "Number of Cards to Trigger Effect";
	private static final int defaultCards = 10;
	private static final String fangsKey = "Number of Fangs to gain";
	private static final int defaultFangs = 3;

	public ClawedCodex() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.FLAT);
		this.counter = 0;
	}
	
	@Override
	public boolean canSpawn() {
        return super.canSpawn() && Util.deckIs("Beast Deck");
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) {
		int trigger = (int)this.getConfig(cardsKey, defaultCards);
		int gain = (int)this.getConfig(fangsKey, defaultFangs);
		if (card.hasTag(Tags.BEAST)) {
			++this.counter;
			if (this.counter % trigger == 0) {
				this.counter = 0;
				if (gain > 0) {
					this.stopPulse();
					this.flash();
					AnyDuelist duelist = AnyDuelist.from(this);
					this.addToBot(new RelicAboveCreatureAction(duelist.creature(), this));
					duelist.applyPowerToSelf(new FangsPower(duelist.creature(), duelist.creature(), gain));
				}
			} else if (this.counter + 1 == trigger && gain > 0) {
				this.beginLongPulse();
			}
		}
	}

	@Override
	public String getUpdatedDescription() {
		int gain = (int)this.getConfig(fangsKey, defaultFangs);
		int beasts = (int)this.getConfig(cardsKey, defaultCards);
		return DESCRIPTIONS[0] + beasts + (beasts == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]) + gain + (gain == 1 ? DESCRIPTIONS[3] : DESCRIPTIONS[4]);
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ClawedCodex();
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.getProperties().put(cardsKey, defaultCards);
		config.getProperties().put(fangsKey, defaultFangs);
		return config;
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> effectOptions = new ArrayList<>();
		for (int i = 1; i < 101; i++) { effectOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the amount of #yBeasts you need to play to trigger the effect. Set to #b" + this.getDefaultConfig(cardsKey) + " by default.";
		DuelistDropdown effectSelector = new DuelistDropdown(tooltip, effectOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(cardsKey, (i+1));
			this.updateConfigSettings(data);
		});
		effectSelector.setSelected(this.getConfig(cardsKey, defaultCards).toString());

		LINEBREAK(15);

		settingElements.add(new ModLabel("Fang Gain", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> magicOptions = new ArrayList<>();
		for (int i = 0; i < 101; i++) { magicOptions.add(String.valueOf(i)); }
		tooltip = "Modify the amount of #yFangs you gain when the effect is triggered. Set to #b" + this.getDefaultConfig(fangsKey) + " by default.";
		DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(fangsKey, i);
			this.updateConfigSettings(data);
		});
		magicSelector.setSelected(this.getConfig(fangsKey, defaultFangs).toString());

		dropdowns.add(magicSelector);
		dropdowns.add(effectSelector);
		LINEBREAK(25);
		return dropdowns;
	}
}
