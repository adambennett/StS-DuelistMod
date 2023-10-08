package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.cards.other.tokens.RelicToken;
import duelistmod.dto.RelicConfigData;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.ArrayList;
import java.util.List;

public class DuelistOrichalcum extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("DuelistOrichalcum");
	public static final String IMG = DuelistMod.makeRelicPath("DuelistOrichalcum.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DuelistOrichalcum_Outline.png");

	private static final String summonKey = "Number of Summons";
	private static final int defaultSummons = 3;
	private static final String triggerKey = "Number of Summons to Trigger On";
	private static final int defaultTrigger = 0;

	public DuelistOrichalcum() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.HEAVY);
	}
	
	@Override
	public void update() {
		super.update();
		if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT)) {
			this.animatePulse();
		}
	}

	@Override
	public void onPlayerEndTurn() {
		AbstractPlayer p = AbstractDungeon.player;
		if (DuelistCard.getSummons(AbstractDungeon.player) <= this.getTrigger()) {
			DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new RelicToken());
			DuelistCard.summon(p, (int)this.getConfig(summonKey, defaultSummons), tok);
			this.stopPulse();
			this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
	}
	
	@Override
	public void onTribute(DuelistCard c, DuelistCard d) {
		this.animatePulse();
	}

	@Override
	public void onSummon(DuelistCard card, int amt) {
		this.animatePulse();
	}

	private void animatePulse() {
		if (DuelistCard.getSummons(AbstractDungeon.player) <= this.getTrigger()) {
			this.beginLongPulse();
		} else {
			this.stopPulse();
		}
	}

	private int getTrigger() {
		return (int)this.getConfig(triggerKey, defaultTrigger);
	}

	@Override
	public String getUpdatedDescription() {
		int summons = (int)this.getConfig(summonKey, defaultSummons);
		int trigger = (int)this.getConfig(triggerKey, defaultTrigger);
		if (trigger == 0) {
			return DESCRIPTIONS[0] + summons + DESCRIPTIONS[summons == 1 ? 1 : 2];
		} else {
			return DESCRIPTIONS[3] + trigger + DESCRIPTIONS[trigger == 1 ? 4 : 5] + DESCRIPTIONS[6] + summons + DESCRIPTIONS[summons == 1 ? 1 : 2];
		}
	}

	@Override
	public AbstractRelic makeCopy() {
		return new DuelistOrichalcum();
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.put(summonKey, defaultSummons);
		config.put(triggerKey, defaultTrigger);
		return config;
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();

		settingElements.add(new ModLabel("Summons", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> effectOptions = new ArrayList<>();
		for (int i = 1; i < 101; i++) { effectOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the amount of #yTokens summoned when the effect is triggered. Set to #b" + this.getDefaultConfig(summonKey) + " by default.";
		DuelistDropdown effectSelector = new DuelistDropdown(tooltip, effectOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(summonKey, (i+1));
			this.updateConfigSettings(data);
		});
		effectSelector.setSelected(this.getConfig(summonKey, defaultSummons).toString());

		LINEBREAK(15);

		settingElements.add(new ModLabel("Trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> magicOptions = new ArrayList<>();
		for (int i = 0; i < 101; i++) { magicOptions.add(String.valueOf(i)); }
		tooltip = "Trigger the effect whenever you have the selected amount of #ySummons or less. Set to #b" + this.getDefaultConfig(triggerKey) + " by default.";
		DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.getProperties().put(triggerKey, i);
			this.updateConfigSettings(data);
		});
		magicSelector.setSelected(this.getConfig(triggerKey, defaultTrigger).toString());

		dropdowns.add(magicSelector);
		dropdowns.add(effectSelector);
		LINEBREAK(25);
		return dropdowns;
	}
}
