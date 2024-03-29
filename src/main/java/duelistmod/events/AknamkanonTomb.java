package duelistmod.events;

import java.util.ArrayList;

import basemod.IUIElement;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.relics.*;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.variables.Tags;

public class AknamkanonTomb extends DuelistEvent {


    public static final String ID = DuelistMod.makeID("AknamkanonTomb");
    public static final String IMG = DuelistMod.makeEventPath("AknamkanonTomb.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public AknamkanonTomb() {
        super(ID, NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
		this.spawnCondition = () -> !this.getActiveConfig().getIsDisabled();
		this.bonusCondition = () -> !this.getActiveConfig().getIsDisabled();
        if (AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null && (AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID) || AbstractDungeon.player.hasRelic(MillenniumPuzzleShared.ID)))
        {
        	if (AbstractDungeon.ascensionLevel >= 15 || Util.getChallengeLevel() > -1)
        	{
        		if (AbstractDungeon.player.potionSlots < 9) { imageEventText.setDialogOption(OPTIONS[7]); }
            	else { imageEventText.setDialogOption(OPTIONS[6], true); }
        		imageEventText.setDialogOption(OPTIONS[8]);
                imageEventText.setDialogOption(OPTIONS[9]);
                imageEventText.setDialogOption(OPTIONS[10]);
                imageEventText.setDialogOption(OPTIONS[4]);
        		
        	}
        	else
        	{
        		if (AbstractDungeon.player.potionSlots < 9) { imageEventText.setDialogOption(OPTIONS[0]); }
            	else { imageEventText.setDialogOption(OPTIONS[6], true); }
                
            	imageEventText.setDialogOption(OPTIONS[1]);
                imageEventText.setDialogOption(OPTIONS[2]);
                imageEventText.setDialogOption(OPTIONS[3]);
                imageEventText.setDialogOption(OPTIONS[4]);
        	}
        }
        else
        {
        	imageEventText.setDialogOption(OPTIONS[5], true);
        	imageEventText.setDialogOption(OPTIONS[5], true);
        	imageEventText.setDialogOption(OPTIONS[5], true);
        	imageEventText.setDialogOption(OPTIONS[5], true);
            imageEventText.setDialogOption(OPTIONS[4]);
        }
    }

    @Override
    protected void buttonEffect(int i) 
    {
    	boolean a15 = AbstractDungeon.ascensionLevel >= 15 || Util.getChallengeLevel() > -1;
		EventConfigData config = this.getActiveConfig();
		if (screenNum == 0 && i < 4 && config.getMultipleChoices()) {
			this.imageEventText.updateDialogOption(i, "[Locked] Reward Received", true);
		}
        switch (screenNum) 
        {
            case 0:
            	switch (i) 
            	{
	            	// Brew - 2x potion slots - Lose 4(7) max hp
	            	case 0:
						if (!config.getMultipleChoices()) {
							this.imageEventText.updateDialogOption(0, OPTIONS[4]);
							this.imageEventText.clearRemainingOptions();
						}
	            		int initSlots = AbstractDungeon.player.potionSlots;
	            		AbstractDungeon.player.potionSlots = AbstractDungeon.player.potionSlots * 2;
	            		for (int j = 0; j < initSlots; j++) { AbstractDungeon.player.potions.add(new PotionSlot(initSlots + j)); }
	            		if (a15) { AbstractDungeon.player.decreaseMaxHealth(7); }
	            		else { AbstractDungeon.player.decreaseMaxHealth(4); }
	            		logDuelistMetric(NAME, "Brew - 2x Potion Slots");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
						}
	            		break;
	
	            	// Enrich - 2x Gold - get 2(3) random duelist curses
	            	case 1:
						if (!config.getMultipleChoices()) {
							this.imageEventText.updateDialogOption(0, OPTIONS[4]);
							this.imageEventText.clearRemainingOptions();
						}
	            		AbstractDungeon.player.gainGold(AbstractDungeon.player.gold);
	            		AbstractCard c = DuelistCardLibrary.getRandomDuelistCurse();	
	            		AbstractCard c2 = DuelistCardLibrary.getRandomDuelistCurse();
	            		while (c2.name.equals(c.name)) { c2 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		if (a15) 
	            		{
	            			AbstractCard c3 = DuelistCardLibrary.getRandomDuelistCurse(); 
	            			while (c3.name.equals(c.name) || c3.name.equals(c2.name)) { c3 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c3, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		}
	            		logDuelistMetric(NAME, "Enrich - 2x gold");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
						}
	            		break;
	
	            	// Intellect - dupe all spells - get 2 random curses
	            	case 2:
						if (!config.getMultipleChoices()) {
							this.imageEventText.updateDialogOption(0, OPTIONS[4]);
							this.imageEventText.clearRemainingOptions();
						}
	            		for (AbstractCard card : AbstractDungeon.player.masterDeck.group)  { if (card.hasTag(Tags.SPELL)) { AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card.makeStatEquivalentCopy(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2))); }}
	            		AbstractCard ca = CardLibrary.getCurse();	
	            		AbstractCard ca2 = CardLibrary.getCurse();
	            		while (ca2.name.equals(ca.name)) { ca2 = CardLibrary.getCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(ca, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(ca2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		logDuelistMetric(NAME, "Intellect - dupe all spells");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
						}
	            		break;
	
	            	// Dig - random Duelist relic - lose 6(8) HP
	            	case 3:
						if (!config.getMultipleChoices()) {
							this.imageEventText.updateDialogOption(0, OPTIONS[4]);
							this.imageEventText.clearRemainingOptions();
						}
            			boolean pass = false;
            			int loopCheck = 50;
            			while (!pass && loopCheck > 0)
            			{
	            			AbstractRelic.RelicTier rarityRoll = AbstractDungeon.returnRandomRelicTier();
	            			ArrayList<AbstractRelic> relicsOfRarity = new ArrayList<>();
	            			for (AbstractRelic t : DuelistMod.duelistRelicsForTombEvent) { if (!AbstractDungeon.player.hasRelic(t.relicId) && t.canSpawn() && t.tier.equals(rarityRoll)) { relicsOfRarity.add(t); }}
	            			if (relicsOfRarity.size() > 0)
	            			{
	            				pass = true;
	            				AbstractRelic r = relicsOfRarity.get(AbstractDungeon.cardRandomRng.random(relicsOfRarity.size() - 1));
								if (r != null) {
									AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
									Util.removeRelicFromPools(r.relicId);
									if (!a15) { AbstractDungeon.player.damage(new DamageInfo(null, 6, DamageInfo.DamageType.HP_LOSS)); }
									else { AbstractDungeon.player.damage(new DamageInfo(null, 8, DamageInfo.DamageType.HP_LOSS)); }
								}
	            			}
	            			else { loopCheck--; }
            			}
	            		logDuelistMetric(NAME, "Dig - random Duelist relic");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
						}
	            		break;

	            	
	            	// Leave
	            	case 4:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[4]);
	            		this.imageEventText.clearRemainingOptions();
	            		logDuelistMetric(NAME, "Leave");
	            		screenNum = 1;
	            		break;
            	}
            	break;
            case 1:
                openMap();
                break;
        }
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
			this.updateConfigSettings(data);
		}));

		LINEBREAK();

		tooltip = "When enabled, allows you to receive multiple rewards before you must leave the Tomb. Disabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Multiple Rewards", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, onLoad.getMultipleChoices(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			EventConfigData data = this.getActiveConfig();
			data.setMultipleChoices(button.enabled);
			this.updateConfigSettings(data);
		}));
		return new DuelistConfigurationData(this.title, settingElements, this);
	}
}

