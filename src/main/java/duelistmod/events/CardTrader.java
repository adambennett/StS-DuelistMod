package duelistmod.events;

import java.util.ArrayList;

import basemod.IUIElement;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.other.tokens.Token;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.variables.Tags;

public class CardTrader extends DuelistEvent {


    public static final String ID = DuelistMod.makeID("CardTrader");
    public static final String IMG = DuelistMod.makeEventPath("CardTrader.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private AbstractCard offerCardFromDeck;
    private AbstractCard offerCardRandom;
    private AbstractCard offerObtainCardDeck;
    private AbstractCard offerObtainCardRandom;
    private AbstractCard sellCardFromDeck;
    private AbstractCard purchaseCard;
    private int randomPurchaseIndex;
    private int randomGoldGain;
    private int randomGoldLoss;
    private int leave = 12;
    
    
    public CardTrader() {
        super(ID, NAME, DESCRIPTIONS[0], IMG);
		this.spawnCondition = () -> !this.getActiveConfig().getIsDisabled();
		this.bonusCondition = () -> !this.getActiveConfig().getIsDisabled();
		try {
			if (AbstractDungeon.player != null) {
				AbstractPlayer p = AbstractDungeon.player;

				boolean hadCards = true;
				// Setup cards/gold amounts/which purchase option
				if (p.masterDeck.group.size() > 0)
				{
					ArrayList<AbstractCard> deck = new ArrayList<>();
					for (AbstractCard c : p.masterDeck.group) { if (!c.type.equals(CardType.CURSE) && !c.rarity.equals(CardRarity.SPECIAL)) { deck.add(c); }}
					offerCardFromDeck = deck.get(AbstractDungeon.cardRandomRng.random(deck.size() - 1));
					CardRarity newRare = offerCardFromDeck.rarity;
					if (newRare.equals(CardRarity.SPECIAL) || newRare.equals(CardRarity.BASIC)) { newRare = CardRarity.COMMON; }
					offerObtainCardDeck = TheDuelist.cardPool.getRandomCard(true, newRare);
					sellCardFromDeck = deck.get(AbstractDungeon.cardRandomRng.random(deck.size() - 1));
				}
				else
				{
					hadCards = false;
					offerCardFromDeck = new Token();
					sellCardFromDeck = new Token();
					offerObtainCardDeck = TheDuelist.cardPool.getRandomCard(true, CardRarity.COMMON);
				}
				offerCardRandom = TheDuelist.cardPool.getRandomCard(true);
				offerObtainCardRandom = Util.getAnyNamelessTombCard(true);
				int allowed = 1;
				if (Util.getChallengeLevel() > -1) { allowed = 2; }
				randomPurchaseIndex = AbstractDungeon.cardRandomRng.random(allowed, 4);

				if (randomPurchaseIndex == 1) { purchaseCard = TheDuelist.cardPool.getRandomCard(true, CardRarity.RARE); }
				else if (randomPurchaseIndex == 2)
				{
					CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
					for (AbstractCard c : TheDuelist.cardPool.group) { if (c.hasTag(Tags.SPELL)) { tmp.group.add(c.makeCopy()); }}
					purchaseCard = tmp.getRandomCard(true);
				}
				else if (randomPurchaseIndex == 3)
				{
					CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
					for (AbstractCard c : TheDuelist.cardPool.group) { if (c.hasTag(Tags.TRAP)) { tmp.group.add(c.makeCopy()); }}
					purchaseCard = tmp.getRandomCard(true);
				}
				else
				{
					CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
					for (AbstractCard c : TheDuelist.cardPool.group) { if (c.hasTag(Tags.MONSTER)) { tmp.group.add(c.makeCopy()); }}
					purchaseCard = tmp.getRandomCard(true);
				}

				int lossCap = 90;
				int gainLow = 15;
				int gainHigh = 35;
				if (sellCardFromDeck.rarity.equals(CardRarity.UNCOMMON)) { gainLow += 15; gainHigh += 10; }
				else if (sellCardFromDeck.rarity.equals(CardRarity.RARE)) { gainLow += 30; gainHigh += 65; }
				if (Util.getChallengeLevel() > -1) { gainLow -= 10; gainHigh -= 15; }
				if (randomPurchaseIndex == 1) { lossCap += 100; }
				int lossLow = 60;
				if (Util.getChallengeLevel() > -1) { lossLow = 25; }
				randomGoldGain = AbstractDungeon.cardRandomRng.random(gainLow, gainHigh);
				int rgl = AbstractDungeon.cardRandomRng.random(lossLow, lossCap);
				if (rgl > AbstractDungeon.player.gold) { randomGoldLoss = AbstractDungeon.player.gold; }
				else { randomGoldLoss = rgl; }

				// Options text setup
				if (hadCards)
				{
					boolean disableSecondOffer = true;
					AbstractCard ref = new CancelCard();
					for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.cardID.equals(offerCardRandom.cardID)) { ref = c; break; }}
					if (!(ref instanceof CancelCard)) { disableSecondOffer = false; }
					imageEventText.setDialogOption(OPTIONS[0] + offerCardFromDeck.name + OPTIONS[1] + offerObtainCardDeck.name + OPTIONS[2], offerObtainCardDeck);
					if (disableSecondOffer)
					{
						imageEventText.setDialogOption("[Locked] The Card Trader was looking for " + offerCardRandom.name, true);
					}
					else
					{
						imageEventText.setDialogOption(OPTIONS[0] + offerCardRandom.name + OPTIONS[1] + offerObtainCardRandom.name + OPTIONS[2], offerObtainCardRandom);
					}
				}
				if (randomPurchaseIndex == 1) { imageEventText.setDialogOption(OPTIONS[3] + purchaseCard.name + OPTIONS[4] + randomGoldLoss + OPTIONS[8], purchaseCard); }
				else { imageEventText.setDialogOption(OPTIONS[3] + OPTIONS[randomPurchaseIndex + 3] + randomGoldLoss + OPTIONS[8], purchaseCard); }
				if (hadCards) { imageEventText.setDialogOption(OPTIONS[9] + sellCardFromDeck.name + OPTIONS[10] + randomGoldGain + OPTIONS[11]); }
				imageEventText.setDialogOption(OPTIONS[12]);
			}
		} catch (Exception ignored) {}
    }

    @Override
    protected void buttonEffect(int i) 
    {
		EventConfigData config = this.getActiveConfig();
		if (screenNum == 0 && i < 4 && config.getMultipleChoices()) {
			this.imageEventText.updateDialogOption(i, "[Locked] Reward Received", true);
		}
        switch (screenNum) 
        {
            case 0:
            	switch (i) 
            	{
	            	// Lose offerCardFromDeck, obtain offerCardRandom
	            	case 0:
	            		AbstractDungeon.player.masterDeck.removeCard(offerCardFromDeck);
	            		AbstractDungeon.player.masterDeck.addToBottom(offerObtainCardDeck);
	            		AbstractDungeon.effectList.add(new PurgeCardEffect(offerCardFromDeck));
	            		logDuelistMetric(NAME, "Traded " + offerCardFromDeck.name + " for " + offerObtainCardDeck.name);
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText("A pleasure doing business...");
							this.imageEventText.updateDialogOption(0, OPTIONS[leave]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Lose offerCardRandom, obtain offerObtainCardRandom
	            	case 1:
	            		AbstractCard ref = new CancelCard();
	            		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
	            		{
	            			if (c.cardID.equals(offerCardRandom.cardID))
	            			{
	            				ref = c;
	            				break;
	            			}
	            		}
	            		if (!(ref instanceof CancelCard))
	            		{
		            		AbstractDungeon.player.masterDeck.removeCard(ref);
		            		AbstractDungeon.player.masterDeck.addToBottom(offerObtainCardRandom);
		            		AbstractDungeon.effectList.add(new PurgeCardEffect(ref));
	            		}
	            		logDuelistMetric(NAME, "Traded " + offerCardRandom.name + " for " + offerObtainCardRandom.name);
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText("A pleasure doing business...");
							this.imageEventText.updateDialogOption(0, OPTIONS[leave]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Obtain the purchaseCard, lose randomGoldLoss gold
	            	case 2:
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(purchaseCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.player.loseGold(randomGoldLoss);
	            		logDuelistMetric(NAME, "Purchased " + purchaseCard.name + " for " + randomGoldLoss + " Gold.");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText("A pleasure doing business...");
							this.imageEventText.updateDialogOption(0, OPTIONS[leave]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Lose the sellCardFromDeck, gain randomGoldGain gold
	            	case 3:
	            		AbstractDungeon.player.masterDeck.removeCard(sellCardFromDeck);
	            		AbstractDungeon.effectList.add(new PurgeCardEffect(sellCardFromDeck));
	            		AbstractDungeon.player.gainGold(randomGoldGain);
	            		logDuelistMetric(NAME, "Sold " + sellCardFromDeck.name + " for " + randomGoldGain + " Gold.");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateBodyText("A pleasure doing business...");
							this.imageEventText.updateDialogOption(0, OPTIONS[leave]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;

	            	
	            	// Leave
	            	case 4:
	            		this.imageEventText.updateBodyText("Maybe next time...");
	            		this.imageEventText.updateDialogOption(0, OPTIONS[leave]);
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

		tooltip = "When enabled, allows you to receive multiple rewards before you must leave the Card Trader's shop. Disabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Multiple Rewards", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, onLoad.getMultipleChoices(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			EventConfigData data = this.getActiveConfig();
			data.setMultipleChoices(button.enabled);
			this.updateConfigSettings(data);
		}));
		return new DuelistConfigurationData(this.title, settingElements, this);
	}
}

