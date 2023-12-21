package duelistmod.events;

import java.util.ArrayList;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.cards.MonsterEggSuper;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.relics.*;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;

public class TombNameless extends DuelistEvent {


    public static final String ID = DuelistMod.makeID("TombNameless");
    public static final String IMG = DuelistMod.makeEventPath("TombNamelessB.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private int shopGoldGain = -1;
    private ArrayList<AbstractRelic> possibleOfferings;
    private AbstractRelic offering;
	private final int maxHpGain;
	private final int hpHeal;

	public static final String EFFECT_MAX_HP_KEY = "Max HP Gain";
	public static final int DEFAULT_EFFECT_MAX_HP = 12;
	public static final String MAGIC_HEAL_KEY = "Heal Amount";
	public static final int DEFAULT_MAGIC_HEAL = 25;
    
    public TombNameless() {
        super(ID, NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
        this.possibleOfferings = new ArrayList<>();
		this.spawnCondition = () -> !this.getActiveConfig().getIsDisabled();
		this.bonusCondition = () -> !this.getActiveConfig().getIsDisabled();
		this.maxHpGain = (int) this.getConfig(EFFECT_MAX_HP_KEY, DEFAULT_EFFECT_MAX_HP);
		this.hpHeal = (int) this.getConfig(MAGIC_HEAL_KEY, DEFAULT_MAGIC_HEAL);
		if (AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null) {
			AbstractPlayer p = AbstractDungeon.player;
			boolean hasMCoin = p.hasRelic(MillenniumCoin.ID);
			boolean hasShpTk = p.hasRelic(ShopToken.ID);
			boolean hasMEgg = p.hasRelic(MonsterEggRelic.ID);
			boolean hasTEgg = p.hasRelic(TributeEggRelic.ID);
			if (hasMCoin) { this.possibleOfferings.add(p.getRelic(MillenniumCoin.ID)); }
			if (hasShpTk) { this.possibleOfferings.add(p.getRelic(ShopToken.ID)); }
			if (hasMEgg)  { this.possibleOfferings.add(p.getRelic(MonsterEggRelic.ID)); }
			if (hasTEgg)  { this.possibleOfferings.add(p.getRelic(TributeEggRelic.ID)); }
			if (this.possibleOfferings.size() > 0)
			{
				this.offering = this.possibleOfferings.get(AbstractDungeon.cardRandomRng.random(this.possibleOfferings.size() - 1));
				if (this.offering instanceof MillenniumCoin)
				{
					imageEventText.setDialogOption(OPTIONS[0] + this.offering.name + OPTIONS[1] + OPTIONS[7], new DuelistCoin());
				}
				else if (this.offering instanceof ShopToken)
				{
					this.shopGoldGain = AbstractDungeon.cardRandomRng.random(65, 200);
					imageEventText.setDialogOption(OPTIONS[0] + this.offering.name + OPTIONS[1] + OPTIONS[10] + this.shopGoldGain + OPTIONS[11]);
				}
				else if (this.offering instanceof MonsterEggRelic)
				{
					imageEventText.setDialogOption(OPTIONS[0] + this.offering.name + OPTIONS[1] + OPTIONS[8], new MonsterEggSuper());
				}
				else if (this.offering instanceof TributeEggRelic)
				{
					imageEventText.setDialogOption(OPTIONS[0] + this.offering.name + OPTIONS[1] + OPTIONS[9]);
				}
			}
			else { imageEventText.setDialogOption(OPTIONS[6], true); }

			imageEventText.setDialogOption(OPTIONS[2], new CursedHealer());
			imageEventText.setDialogOption(OPTIONS[3]);
			imageEventText.setDialogOption(OPTIONS[4]);
			imageEventText.setDialogOption(OPTIONS[5]);
		}
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
	            	// Offering
	            	case 0:
	            		if (this.offering != null)
	            		{
	            			if (this.offering instanceof MillenniumCoin)
	                    	{
	            				AbstractDungeon.player.loseRelic(MillenniumCoin.ID);
	            				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new DuelistCoin());
	            				logDuelistMetric(NAME, "Offering - offered Millennium Coin");
	                    	}
	            			
	                    	else if (this.offering instanceof ShopToken)
	                    	{
	                    		AbstractDungeon.player.gainGold(this.shopGoldGain);
	                    		AbstractDungeon.player.loseRelic(ShopToken.ID);
	                    		logDuelistMetric(NAME, "Offering - offered Shop Token");
	                    	}
	            			
	                    	else if (this.offering instanceof MonsterEggRelic)
	                    	{
	                    		AbstractDungeon.player.loseRelic(MonsterEggRelic.ID);
	                    		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new MonsterEggSuper(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	                    		logDuelistMetric(NAME, "Offering - offered Monster Egg Relic");
	                    	}
	            			
	                    	else if (this.offering instanceof TributeEggRelic)
	                    	{
	                    		AbstractDungeon.player.loseRelic(TributeEggRelic.ID);
	                    		AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth/2.0f));
	                    		logDuelistMetric(NAME, "Offering - offered Tribute Egg");
	                    	}
	            		}
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Succumb - Gain 25 HP, obtain Cursed Relic
	            	case 1:
	            		AbstractDungeon.player.heal(this.hpHeal);
	            		AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new CursedHealer());
	            		logDuelistMetric(NAME, "Succumb - +" + this.hpHeal + "HP/Cursed Relic");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Worship - Gamble 20% roll to get 12 max hp, 100% chance to get 1 random duelist curse
	            	case 2:
	            		AbstractCard curse = DuelistCardLibrary.getRandomDuelistCurse();	
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		int rolly = AbstractDungeon.cardRandomRng.random(1, 10);
	            		if (rolly < 3) { AbstractDungeon.player.increaseMaxHp(this.maxHpGain, true); }
	            		logDuelistMetric(NAME, "Worship - 20% roll at +" + this.maxHpGain + " Max HP");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;
	
	            	// Break - 3 random duelist curses, gain 12 max HP
	            	case 3:
	            		AbstractCard c = DuelistCardLibrary.getRandomDuelistCurse();	
	            		AbstractCard c2 = DuelistCardLibrary.getRandomDuelistCurse();
	            		AbstractCard c3 = DuelistCardLibrary.getRandomDuelistCurse(); 
	            		while (c2.name.equals(c.name)) { c2 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            		while (c3.name.equals(c.name) || c3.name.equals(c2.name)) { c3 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c3, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));	
	            		AbstractDungeon.player.increaseMaxHp(this.maxHpGain, true);
	            		logDuelistMetric(NAME, "Break - +" + this.maxHpGain + " Max HP");
						if (!config.getMultipleChoices()) {
							screenNum = 1;
							this.imageEventText.updateDialogOption(0, OPTIONS[5]);
							this.imageEventText.clearRemainingOptions();
						}
	            		break;

	            	
	            	// Leave
	            	case 4:
	            		//this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
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
	public EventConfigData getDefaultConfig() {
		EventConfigData config = new EventConfigData();
		config.put(EFFECT_MAX_HP_KEY, DEFAULT_EFFECT_MAX_HP);
		config.put(MAGIC_HEAL_KEY, DEFAULT_MAGIC_HEAL);
		return config;
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

		LINEBREAK(25);

		settingElements.add(new ModLabel("Heal Amount", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> healOptions = new ArrayList<>();
		for (int i = 0; i < 1001; i++) { healOptions.add(i+""); }
		tooltip = "Modify the amount of HP healed as a reward for option 2. Set to #b25 by default.";
		DuelistDropdown healSelector = new DuelistDropdown(tooltip, healOptions, Settings.scale * (DuelistMod.xLabPos + 650), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			EventConfigData data = this.getActiveConfig();
			data.put(MAGIC_HEAL_KEY, i);
			this.updateConfigSettings(data);
		});
		healSelector.setSelected(onLoad.getProperties().getOrDefault(MAGIC_HEAL_KEY, DEFAULT_MAGIC_HEAL).toString());

		LINEBREAK(25);

		settingElements.add(new ModLabel("Max HP Gain", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> maxHpOptions = new ArrayList<>();
		for (int i = 0; i < 1001; i++) { maxHpOptions.add(i+""); }
		tooltip = "Modify the amount of Max HP given as a reward for options 3 & 4. Set to #b12 by default.";
		DuelistDropdown maxHpSelector = new DuelistDropdown(tooltip, maxHpOptions, Settings.scale * (DuelistMod.xLabPos + 650), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			EventConfigData data = this.getActiveConfig();
			data.put(EFFECT_MAX_HP_KEY, i);
			this.updateConfigSettings(data);
		});
		maxHpSelector.setSelected(onLoad.getProperties().getOrDefault(EFFECT_MAX_HP_KEY, DEFAULT_EFFECT_MAX_HP).toString());

		settingElements.add(maxHpSelector);
		settingElements.add(healSelector);
		return new DuelistConfigurationData(this.title, settingElements, this);
	}
}

