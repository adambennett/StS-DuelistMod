package duelistmod.events;

import java.util.*;

import basemod.IUIElement;
import basemod.eventUtil.util.Condition;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.cards.*;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;

public class EgyptVillage extends DuelistEvent {


    public static final String ID = DuelistMod.makeID("EgyptVillage");
    public static final String IMG = DuelistMod.makeEventPath("EgyptVillage.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private boolean alphaUpgrade = false;
    private boolean betaUpgrade = false;
    private boolean gammaUpgrade = false;
    private boolean valkUpgrade = false;
   // private boolean relicSelected = true;
   // private RelicSelectScreen relicSelectScreen;

    public EgyptVillage() {
        super(ID, NAME, DESCRIPTIONS[0], IMG);
		Condition bothConditions = () -> !this.getActiveConfig().getIsDisabled() && Util.deckIs("Warrior Deck");
		this.spawnCondition = bothConditions;
		this.bonusCondition = bothConditions;
        //this.noCardsInRewards = true;
		if (AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null) {
			boolean hasAlpha = false;
			boolean hasBeta = false;
			boolean hasGamma = false;
			boolean hasValk = false;

			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c instanceof AlphaMagnet) { hasAlpha = true; if (c.upgraded) { this.alphaUpgrade = true; }}
				if (c instanceof BetaMagnet)  { hasBeta = true; if (c.upgraded) { this.betaUpgrade = true; }}
				if (c instanceof GammaMagnet) { hasGamma = true; if (c.upgraded) { this.gammaUpgrade = true; }}
				if (c instanceof ValkMagnet)  { hasValk = true; if (c.upgraded) { this.valkUpgrade = true; }}
			}

			// Receive Alpha Electro - 0
			if (hasAlpha && !alphaUpgrade) { imageEventText.setDialogOption(OPTIONS[0], new AlphaElectro()); }
			else if (hasAlpha) { AbstractCard a = new AlphaElectro(); a.upgrade(); imageEventText.setDialogOption(OPTIONS[0], a);}
			else { imageEventText.setDialogOption(OPTIONS[6], true); }

			// Receive Beta Electro - 1
			if (hasBeta && !betaUpgrade)  { imageEventText.setDialogOption(OPTIONS[1], new BetaElectro()); }
			else if (hasBeta) { AbstractCard a = new BetaElectro(); a.upgrade(); imageEventText.setDialogOption(OPTIONS[1], a);}
			else { imageEventText.setDialogOption(OPTIONS[7], true); }

			// Receive Gamma Electro - 2
			if (hasGamma && !gammaUpgrade) { imageEventText.setDialogOption(OPTIONS[2], new GammaElectro()); }
			else if (hasGamma) { AbstractCard a = new GammaElectro(); a.upgrade(); imageEventText.setDialogOption(OPTIONS[2], a);}
			else { imageEventText.setDialogOption(OPTIONS[8], true); }

			// Receive Delta Magnet - 3
			imageEventText.setDialogOption(OPTIONS[3], new DeltaMagnet());

			// Receive Beserkion - 4
			if (hasValk && !valkUpgrade)  { imageEventText.setDialogOption(OPTIONS[4], new Berserkion());  }
			else if (hasValk) { AbstractCard a = new Berserkion(); a.upgrade(); imageEventText.setDialogOption(OPTIONS[4], a);}
			else { imageEventText.setDialogOption(OPTIONS[9], true); }


			// Leave - 6
			imageEventText.setDialogOption(OPTIONS[5]);
		}
    }

    @Override
    protected void buttonEffect(int i) 
    {
    	AbstractCard holder = null;
		EventConfigData config = this.getActiveConfig();
		if (screenNum == 0 && i < 5 && config.getMultipleChoices()) {
			this.imageEventText.updateDialogOption(i, "[Locked] Reward Received", true);
		}
        switch (screenNum) 
        {
            case 0:
            	switch (i) 
            	{
	            	// Alpha Electro
	            	case 0:
	            		holder = new AlphaElectro();
	            		if (this.alphaUpgrade) { holder.upgrade(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(holder, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardList = new ArrayList<String>();
	            		cardList.add("Alpha Electromagnet Warrior");
	            		logDuelistMetric(NAME, "Alpha Electro", cardList, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Beta Electro
	            	case 1:
	            		holder = new BetaElectro();
	            		if (this.betaUpgrade) { holder.upgrade(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(holder, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardListB = new ArrayList<String>();
	            		cardListB.add("Beta Electromagnet Warrior");
	            		logDuelistMetric(NAME, "Beta Electro", cardListB, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Gamma Electro
	            	case 2:
	            		holder = new GammaElectro();
	            		if (this.gammaUpgrade) { holder.upgrade(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(holder, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardListC = new ArrayList<String>();
	            		cardListC.add("Gamma Electromagnet Warrior");
	            		logDuelistMetric(NAME, "Gamma Electro", cardListC, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Delta Magnet
	            	case 3:
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new DeltaMagnet(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardListD = new ArrayList<String>();
	            		cardListD.add("Delta Electromagnet Warrior");
	            		logDuelistMetric(NAME, "Delta Electro", cardListD, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	            	
	            	// Berserkion
	            	case 4:
	            		holder = new Berserkion();
	            		if (this.valkUpgrade) { holder.upgrade(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(holder, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardListE = new ArrayList<String>();
	            		cardListE.add("Berserkion Electromagna");
	            		logDuelistMetric(NAME, "Berserkion", cardListE, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;

	            	// Leave
	            	case 5:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
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

		tooltip = "When enabled, allows you to receive multiple rewards before you must leave the Village. Disabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Multiple Rewards", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, onLoad.getMultipleChoices(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			EventConfigData data = this.getActiveConfig();
			data.setMultipleChoices(button.enabled);
			this.updateConfigSettings(data);
		}));
		return new DuelistConfigurationData(this.title, settingElements, this);
	}
}

