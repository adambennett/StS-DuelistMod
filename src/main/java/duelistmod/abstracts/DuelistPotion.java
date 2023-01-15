package duelistmod.abstracts;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModLabeledButton;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.PotionConfigData;
import duelistmod.helpers.Util;
import duelistmod.rewards.BoosterPack;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;

public abstract class DuelistPotion extends AbstractPotion
{
	protected boolean showIdInConfig;
	protected boolean showDescriptionInConfig;
	protected boolean showRarityInConfig;
	protected int configDescMaxLines;
	protected int configDescMaxWidth;

	public DuelistPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid) 
	{
		this(name, id, rarity, size, pfx, liquid, null, null);
	}
	
	public DuelistPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid, Color hybrid) 
	{
		this(name, id, rarity, size, pfx, liquid, hybrid, null);
	}
	
	public DuelistPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid, Color hybrid, Color spots) 
	{
		super(name, id, rarity, size, pfx, liquid, hybrid, spots);
		this.showDescriptionInConfig = true;
		this.showIdInConfig = true;
		this.showRarityInConfig = true;
		this.configDescMaxLines = 4;
		this.configDescMaxWidth = 70;
	}
	
	protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) { return new ArrayList<>(); }

	protected List<DuelistDropdown> configAddAfterDescription(ArrayList<IUIElement> settingElements) { return new ArrayList<>(); }

	public PotionConfigData getDefaultConfig() { return new PotionConfigData(); }

	public PotionConfigData getActiveConfig() { return DuelistMod.potionCanSpawnConfigMap.getOrDefault(this.ID, this.getDefaultConfig()); }

	public DuelistConfigurationData getConfigurations() {
		RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
		ArrayList<IUIElement> settingElements = new ArrayList<>();

		String tooltip = "When enabled, " + this.name + " will not spawn during runs. Disabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Disable " + this.name, tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.potionCanSpawnConfigMap.getOrDefault(this.ID, this.getDefaultConfig()).getDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			PotionConfigData data = this.getActiveConfig();
			data.setDisabled(button.enabled);
			DuelistMod.potionCanSpawnConfigMap.put(this.ID, data);
			try
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				String potConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.potionCanSpawnConfigMap);
				config.setString("potionCanSpawnConfigMap", potConfigMap);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		}));

		LINEBREAK(35);

		List<DuelistDropdown> dropdownsPre = this.configAddAfterDisabledBox(settingElements);

		if (this.showIdInConfig) {
			settingElements.add(new ModLabel("ID: " + this.ID, (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
			settingElements.add(new ModLabeledButton("Copy ID", DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol, DuelistMod.yPos - 25, DuelistMod.settingsPanel, (element)->
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.ID), null)
			));
			LINEBREAK(!this.showRarityInConfig ? 25 : 0);
		}

		if (this.showRarityInConfig) {
			settingElements.add(new ModLabel("Rarity: " + this.getRarityDisplayName(), (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
			LINEBREAK(15);
		}

		if (this.showDescriptionInConfig) {
			Util.formatConfigMenuObjectDescription(settingElements, this.description, -5, this.configDescMaxWidth, this.configDescMaxLines, this::LINEBREAK);
		}

		List<DuelistDropdown> dropdownsPost = this.configAddAfterDescription(settingElements);

		settingElements.addAll(dropdownsPost);
		settingElements.addAll(dropdownsPre);

		return new DuelistConfigurationData(this.name, settingElements, this);
	}

	public void LINEBREAK() {
		DuelistMod.linebreak();
	}

	public void LINEBREAK(int extra) {
		DuelistMod.linebreak(extra);
	}

	public void RESET_Y() {
		DuelistMod.yPos = DuelistMod.startingYPos;
	}

	public String getRarityDisplayName() {
		switch (this.rarity) {
			case COMMON:
				return "Common";
			case UNCOMMON:
				return "Uncommon";
			case RARE:
				return "Rare";
			default:
				return "Unknown";
		}
	}
    
    public void onReceiveBoosterPack(BoosterPack pack) { }
    
	public void onSoulChange(int newSouls, int change) { }
	
	public void onEndOfBattle() { }
	
    public int modifyShadowDamage() { return 0; }
    
    public int modifyUndeadDamage() { return 0; }
	
	public void onLoseArtifact() { }
	
	public void onChangeStance() { }
	
	public void onOverflow(int amt) { }
	
	public void onFish(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onResummon(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummon(resummoned); }}
	
	public int modifyResummonAmt(AbstractCard resummoningCard) { return 0; }
	
	public int modifyReviveCost(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public boolean allowResummon(AbstractCard resummoningCard) { return true; }
	
	public boolean allowRevive() { return true; }
	
	public int modifyReviveListSize() { return 0; }
	
	public boolean upgradeResummon(AbstractCard resummoningCard) { return false; }
	
	public void onSynergyTribute() { }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
	public boolean canSpawn() {
		boolean idCheck = DuelistMod.potionCanSpawnConfigMap.getOrDefault(this.ID, this.getDefaultConfig()).getDisabled();
		if (idCheck) return false;

		if (this.rarity == PotionRarity.COMMON && DuelistMod.disableAllCommonPotions) {
			return false;
		}

		if (this.rarity == PotionRarity.UNCOMMON && DuelistMod.disableAllUncommonPotions) {
			return false;
		}

		if (this.rarity == PotionRarity.RARE && DuelistMod.disableAllRarePotions) {
			return false;
		}
		return true;
	}
	
	public int modifySummons(int magicAmt) { return magicAmt; }
	
	public int modifyTributes(int magicAmt) { return magicAmt; }
	
	public int modifySummons(int magicAmt, AbstractCard card) { return this.modifySummons(magicAmt); }
	
	public int modifyTributes(int magicAmt, AbstractCard card) { return this.modifyTributes(magicAmt); }

	public float modifyEntomb(float magicAmt) { return magicAmt; }
	
	public float modifyEntomb(float magicAmt, AbstractCard card) { return this.modifyEntomb(magicAmt); }
	
	public float modifyMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyMagicNumber(float magicAmt, AbstractCard card) { return this.modifyMagicNumber(magicAmt); }

	public float modifySecondMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifySecondMagicNumber(float magicAmt, AbstractCard card) { return this.modifySecondMagicNumber(magicAmt); }
	
	public float modifyThirdMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyThirdMagicNumber(float magicAmt, AbstractCard card) { return this.modifyThirdMagicNumber(magicAmt); }

	public boolean modifyCanUse(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) { return true; }

	public String cannotUseMessage(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) { return "Cannot use due to a potion: " + this.name; }
	
}
