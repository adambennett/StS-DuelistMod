package duelistmod.relics;

import java.util.ArrayList;
import java.util.List;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.dto.RelicConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.variables.Tags;

public class FusionToken extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("FusionToken");
	public static final String IMG = DuelistMod.makeRelicPath("FusionToken.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("FusionToken_Outline.png");

	private static final String cardsKey = "Number of Cards Added to Hand";
	private static final int defaultCards = 1;
	
	public FusionToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn() {
        return super.canSpawn() && Util.deckIs("Zombie Deck");
	}
	
	@Override
	public void atTurnStart() {
		int cards = (int)this.getConfig(cardsKey, defaultCards);
		ArrayList<AbstractCard> list = DuelistCard.findAllOfType(Tags.FUSION, cards);
		for (AbstractCard c : list) {
			this.addToBot(new RandomizedHandAction(c, true, 0, 3));
		}
	}

	@Override
	public String getUpdatedDescription() {
		int cards = (int)this.getConfig(cardsKey, defaultCards);
		return DESCRIPTIONS[0] + cards + DESCRIPTIONS[cards == 1 ? 1 : 2];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new FusionToken();
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.getProperties().put(cardsKey, defaultCards);
		return config;
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Cards", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> effectOptions = new ArrayList<>();
		for (int i = 0; i < 11; i++) { effectOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the number of cards added to your hand at the start of turn. Set to #b" + this.getDefaultConfig(cardsKey) + " by default.";
		DuelistDropdown effectSelector = new DuelistDropdown(tooltip, effectOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(cardsKey, i);
			this.updateConfigSettings(data);
		});
		effectSelector.setSelected(this.getConfig(cardsKey, defaultCards).toString());

		dropdowns.add(effectSelector);
		LINEBREAK(25);
		return dropdowns;
	}
}
