package duelistmod.events;

import java.util.ArrayList;

import basemod.IUIElement;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.metronomes.MillenniumMetronome;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.interfaces.MillenniumArmorPlate;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.potions.MillenniumElixir;
import duelistmod.relics.ArmorPlateA;
import duelistmod.relics.ArmorPlateB;
import duelistmod.relics.ArmorPlateC;
import duelistmod.relics.ArmorPlateD;
import duelistmod.relics.ArmorPlateE;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;

public class MillenniumItems extends DuelistEvent {


    public static final String ID = DuelistMod.makeID("MillenniumItems");
    public static final String IMG = DuelistMod.makeEventPath("MillenniumItems.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
	private final MillenniumItem reward;

    public MillenniumItems() {
        super(ID, NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
		this.spawnCondition = () -> !this.getActiveConfig().getIsDisabled();
		this.bonusCondition = () -> !this.getActiveConfig().getIsDisabled();
		this.reward = getReward();
		if (this.reward instanceof AbstractCard) {
			imageEventText.setDialogOption(OPTIONS[0], (AbstractCard) this.reward);
		} else if (this.reward instanceof AbstractRelic) {
			imageEventText.setDialogOption(OPTIONS[0], (AbstractRelic) this.reward);
		} else {
			imageEventText.setDialogOption(OPTIONS[0]);
		}
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                    	String nameOfItem = "";
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
						if (this.reward instanceof DuelistCard) {
							DuelistCard rewardCard = (DuelistCard)this.reward;
							AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
							AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH * 0.6F, Settings.HEIGHT / 2.0F));
							AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewardCard, Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
							nameOfItem = rewardCard.name;
						} else if (this.reward instanceof DuelistRelic) {
							DuelistRelic rewardRelic = (DuelistRelic)this.reward;
							AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
							AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
							AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rewardRelic);
							Util.removeRelicFromPools(rewardRelic.relicId);
							nameOfItem = rewardRelic.name;
						} else if (this.reward instanceof DuelistPotion) {
							DuelistPotion rewardPotion = (DuelistPotion) this.reward;
							AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
							AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
							AbstractDungeon.player.obtainPotion(rewardPotion);
							nameOfItem = rewardPotion.name;
						}
                        logDuelistMetric(NAME, "Took item - Received: " + nameOfItem);
                        screenNum = 1;
                        break;
                    case 1:
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        logDuelistMetric(NAME, "Leave");
                        screenNum = 1;
                }
                break;
            case 1:
                openMap();
                break;

        }
    }

	private MillenniumItem getReward() {
		if (AbstractDungeon.player == null || AbstractDungeon.player.relics == null) return null;

		ArrayList<MillenniumItem> possibleRelicRewards = new ArrayList<>();
		for (AbstractRelic t : Util.getMillenniumItemsForEvent(false)) {
			if (!AbstractDungeon.player.hasRelic(t.relicId)) {
				if (t instanceof MillenniumItem) {
					possibleRelicRewards.add((MillenniumItem)t);
				}
			}
		}

		ArrayList<MillenniumItem> possibleRewards = new ArrayList<>(possibleRelicRewards);
		ArrayList<MillenniumArmorPlate> plates = new ArrayList<>();
		plates.add(new ArmorPlateA());
		plates.add(new ArmorPlateB());
		plates.add(new ArmorPlateC());
		plates.add(new ArmorPlateD());
		plates.add(new ArmorPlateE());
		for (MillenniumArmorPlate plate : plates) {
			if (plate instanceof DuelistRelic && ((DuelistRelic)plate).canSpawn()) {
				possibleRewards.add(plate);
			}
		}
		if (possibleRewards.size() < 1) {
			possibleRewards.add(new MillenniumElixir());
		}
		if (AbstractDungeon.ascensionLevel > 4) {
			possibleRewards.add(new MillenniumSpellbook());
		}
		if (Util.getChallengeLevel() > -1) {
			possibleRewards.add(new MillenniumMetronome());
		}
		if (possibleRewards.size() == 1) {
			return possibleRewards.get(0);
		}
		return possibleRewards.get(AbstractDungeon.cardRandomRng.random(0, possibleRewards.size() - 1));
	}

	@Override
	public DuelistConfigurationData getConfigurations() {
		RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
		ArrayList<IUIElement> settingElements = new ArrayList<>();
		EventConfigData onLoad = this.getActiveConfig();
		String tooltip = "When enabled, allows you encounter this event during runs. Enabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Event Enabled", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !onLoad.getIsDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			EventConfigData data = this.getActiveConfig();
			data.setIsDisabled(!button.enabled);
			DuelistMod.eventConfigSettingsMap.put(this.duelistEventId, data);
			try
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				String eventConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.eventConfigSettingsMap);
				config.setString("eventConfigSettingsMap", eventConfigMap);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
		}));
		return new DuelistConfigurationData(this.title, settingElements, this);
	}
}

