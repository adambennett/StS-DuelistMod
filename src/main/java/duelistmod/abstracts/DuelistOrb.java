package duelistmod.abstracts;

import java.util.ArrayList;
import java.util.List;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;

import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.OrbConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;

@SuppressWarnings("unused")
public abstract class DuelistOrb extends AbstractOrb {

	protected int originalPassive = 0;
	protected int originalEvoke = 0;
	public boolean showInvertValue = false;
	public String inversion = "";
	public boolean triggersOnSpellcasterPuzzle = false;
	public boolean allowNegativeFocus = false;

	public boolean configShouldModifyPassive = false;
	public boolean configShouldModifyEvoke = false;
	public boolean configShouldAllowPassiveDisable = false;
	public boolean configShouldAllowEvokeDisable = false;

	public Texture getImage() {
		return this.img;
	}
	
	public boolean renderInvertText(SpriteBatch sb, boolean top)
	{
		if (this.inversion.equals("")) { this.inversion = "???"; }
		if (this.showInvertValue)
		{
			if (top) { FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.inversion, this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET + 20.0f * Settings.scale, new Color(0.2f, 1.0f, 1.0f, this.c.a), this.fontScale); }
			else { FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.inversion, this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET, new Color(0.2f, 1.0f, 1.0f, this.c.a), this.fontScale); }
			return true;
		}
		else { return false; }
	}
	
	public void setID(String id)
	{
		this.ID = id;
	}

	public void fixFocusOnConfigChanges() {
		OrbConfigData data = Util.getOrbConfiguration(this.name);
		this.originalPassive = data.getConfigPassive();
		this.originalEvoke = data.getConfigEvoke();
		this.checkFocus();
	}

	public void checkOrbsOnConfigChanges() {
		if (AbstractDungeon.player != null && AbstractDungeon.player.orbs != null) {
			for (AbstractOrb o : AbstractDungeon.player.orbs) {
				if (o.name.equals(this.name) && o instanceof DuelistOrb) {
					((DuelistOrb)o).fixFocusOnConfigChanges();
					o.updateDescription();
				}
			}
		}
	}
	
	protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) { return new ArrayList<>(); }

	protected List<DuelistDropdown> configAddAfterDescription(ArrayList<IUIElement> settingElements) { return new ArrayList<>(); }

	public OrbConfigData getDefaultConfig() { return new OrbConfigData(0, 0); }

	public OrbConfigData getActiveConfig() { return DuelistMod.orbConfigSettingsMap.getOrDefault(this.ID, this.getDefaultConfig()); }

	public DuelistConfigurationData getConfigurations() {
		if (configShouldAllowEvokeDisable || configShouldAllowPassiveDisable || configShouldModifyPassive || configShouldModifyEvoke) {
			RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
			ArrayList<IUIElement> settingElements = new ArrayList<>();

			List<DuelistDropdown> dropdownsPre = this.configAddAfterDisabledBox(settingElements);

			if (configShouldAllowPassiveDisable) {
				String tooltip = "When disabled, #y" + this.name + " will not trigger the passive effect. Enabled by default.";
				settingElements.add(new DuelistLabeledToggleButton("Enable Passive Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.orbConfigSettingsMap.getOrDefault(this.name, new OrbConfigData(0, 0)).getPassiveDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
				{
					OrbConfigData data = DuelistMod.orbConfigSettingsMap.getOrDefault(this.name, new OrbConfigData(0, 0));
					OrbConfigData newData = new OrbConfigData(data.getDefaultPassive(), data.getDefaultEvoke(), data.getConfigPassive(), data.getConfigEvoke(), !button.enabled, data.getEvokeDisabled());
					DuelistMod.orbConfigSettingsMap.put(this.name, newData);
					try
					{
						SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
						String orbConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.orbConfigSettingsMap);
						config.setString("orbConfigSettingsMap", orbConfigMap);
						config.save();
					} catch (Exception e) { e.printStackTrace(); }

				}));
				LINEBREAK(25);
			}

			if (configShouldAllowEvokeDisable) {
				String tooltip = "When disabled, #y" + this.name + " will not trigger the #yEvoke effect. Enabled by default.";
				settingElements.add(new DuelistLabeledToggleButton("Enable Evoke Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.orbConfigSettingsMap.getOrDefault(this.name, new OrbConfigData(0, 0)).getEvokeDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
				{
					OrbConfigData data = DuelistMod.orbConfigSettingsMap.getOrDefault(this.name, new OrbConfigData(0, 0));
					OrbConfigData newData = new OrbConfigData(data.getDefaultPassive(), data.getDefaultEvoke(), data.getConfigPassive(), data.getConfigEvoke(), data.getPassiveDisabled(), !button.enabled);
					DuelistMod.orbConfigSettingsMap.put(this.name, newData);
					try
					{
						SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
						String orbConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.orbConfigSettingsMap);
						config.setString("orbConfigSettingsMap", orbConfigMap);
						config.save();
					} catch (Exception e) { e.printStackTrace(); }

				}));
				LINEBREAK(25);
			}

			DuelistDropdown passiveSelector = null;
			DuelistDropdown evokeSelector = null;
			if (configShouldModifyPassive) {
				settingElements.add(new ModLabel("Base Passive Value", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
				ArrayList<String> passiveOptions = new ArrayList<>();
				for (int i = 0; i < 1001; i++) { passiveOptions.add(i+""); }
				OrbConfigData dataOnLoad = DuelistMod.orbConfigSettingsMap.getOrDefault(this.name, new OrbConfigData(0, 0));
				int defaultPassive = dataOnLoad.getDefaultPassive();
				String tooltip = "Modify the base value for this orb's passive effect. Set to #b" + defaultPassive + " by default.";
				passiveSelector = new DuelistDropdown(tooltip, passiveOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
					try {
						OrbConfigData data = DuelistMod.orbConfigSettingsMap.getOrDefault(this.name, new OrbConfigData(0, 0));
						OrbConfigData newData = new OrbConfigData(data.getDefaultPassive(), data.getDefaultEvoke(), i, data.getConfigEvoke(), data.getPassiveDisabled(), data.getEvokeDisabled());
						DuelistMod.orbConfigSettingsMap.put(this.name, newData);
						this.checkOrbsOnConfigChanges();
						try {
							SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
							String orbConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.orbConfigSettingsMap);
							config.setString("orbConfigSettingsMap", orbConfigMap);
							config.save();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception ex) {
						Util.logError("Exception somewhere in dropdown click for PASSIVE", ex);
					}
				});
				passiveSelector.setSelectedIndex(dataOnLoad.getConfigPassive());
				LINEBREAK(25);
			}

			if (configShouldModifyEvoke) {
				settingElements.add(new ModLabel("Base Evoke Value", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
				ArrayList<String> evokeOptions = new ArrayList<>();
				for (int i = 0; i < 1001; i++) { evokeOptions.add(i+""); }
				OrbConfigData dataOnLoad = DuelistMod.orbConfigSettingsMap.getOrDefault(this.name, new OrbConfigData(0, 0));
				int defaultEvoke = dataOnLoad.getDefaultEvoke();
				String tooltip = "Modify the base value for this orb's #yEvoke effect. Set to #b" + defaultEvoke + " by default.";
				evokeSelector = new DuelistDropdown(tooltip, evokeOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
					OrbConfigData data = DuelistMod.orbConfigSettingsMap.getOrDefault(this.name, new OrbConfigData(0, 0));
					OrbConfigData newData = new OrbConfigData(data.getDefaultPassive(), data.getDefaultEvoke(), data.getConfigPassive(), i, data.getPassiveDisabled(), data.getEvokeDisabled());
					DuelistMod.orbConfigSettingsMap.put(this.name, newData);
					this.checkOrbsOnConfigChanges();
					try {
						SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
						String orbConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.orbConfigSettingsMap);
						config.setString("orbConfigSettingsMap", orbConfigMap);
						config.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				evokeSelector.setSelectedIndex(dataOnLoad.getConfigEvoke());

			}

			if (configShouldModifyPassive && configShouldModifyEvoke) {
				settingElements.add(evokeSelector);
				settingElements.add(passiveSelector);
			} else if (configShouldModifyPassive) {
				settingElements.add(passiveSelector);
			} else if (configShouldModifyEvoke) {
				settingElements.add(evokeSelector);
			}

			List<DuelistDropdown> dropdownsPost = this.configAddAfterDescription(settingElements);

			settingElements.addAll(dropdownsPost);
			settingElements.addAll(dropdownsPre);

			return new DuelistConfigurationData(this.name, settingElements, this);
		}
		return null;
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
    
	public void onSoulChange(int newSouls, int change) { }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onLoseArtifact() { }
	
	public void onExhaust(AbstractCard c) { }
	
	public void onAddCardToHand(AbstractCard c) { }
	
	public void onShuffle() { }
	
    public int modifyShadowDamage() { return 0; }
    
    public int modifyUndeadDamage() { return 0; }
	
	public void onLoseBlock(int amt) { }
	
	public void onGainDex(int amount) { }
	
	public void onOverflow(int amt) { }
	
	public void onFish(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	
	public void onPowerApplied(AbstractPower pow) { }
	
	public void onChangeStance() { }
	
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
	
	public void onDrawCard(AbstractCard drawnCard) { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
	public int modifySummons(int magicAmt) { return magicAmt; }
	
	public int modifyTributes(int magicAmt) { return magicAmt; }
	
	public int modifySummons(int magicAmt, AbstractCard card) { return this.modifySummons(magicAmt); }
	
	public int modifyTributes(int magicAmt, AbstractCard card) { return this.modifyTributes(magicAmt); }

	public float modifyMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyMagicNumber(float magicAmt, AbstractCard card) { return this.modifyMagicNumber(magicAmt); }

	public float modifySecondMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifySecondMagicNumber(float magicAmt, AbstractCard card) { return this.modifySecondMagicNumber(magicAmt); }
	
	public float modifyThirdMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyThirdMagicNumber(float magicAmt, AbstractCard card) { return this.modifyThirdMagicNumber(magicAmt); }
	
	public float modifyEntomb(float magicAmt) { return magicAmt; }
	
	public float modifyEntomb(float magicAmt, AbstractCard card) { return this.modifyEntomb(magicAmt); }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }

	public boolean modifyCanUse(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) { return true; }

	public String cannotUseMessage(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) { return "Cannot use due to an Orb: " + this.name; }
	
	public void showInvertValue() {
        this.showInvertValue = true;
    }
    
    public void hideInvertValues() {
        this.showInvertValue = false;
    }
    
    @Override
    public void showEvokeValue() {
        this.showEvokeValue = true;
    }
    
    @Override
    public void hideEvokeValues() {
        this.showEvokeValue = false;
    }
	
	public void lightOrbEnhance(int amt) {
		this.passiveAmount = this.originalPassive = this.originalPassive + amt;
		this.evokeAmount = this.originalEvoke = this.originalEvoke + amt;
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
			this.passiveAmount += AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		}
	}
	
	public boolean gpcCheck() {
		return AbstractDungeon.player.orbs.get(0).equals(this) && AbstractDungeon.player.hasRelic(GoldPlatedCables.ID);
	}
	
	public boolean doesNotHaveNegativeFocus() {
		return !AbstractDungeon.player.hasPower(FocusPower.POWER_ID) || AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount >= 0;
	}
	
	public int getCurrentFocus() {
		return AbstractDungeon.player.hasPower(FocusPower.POWER_ID) ? AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount : 0;
	}
	
	@Override
	public AbstractOrb makeCopy() {
		return null;
	}

	@Override
	public void onEvoke() {}

	@Override
	public void playChannelSFX() {}

	@Override
	public void render(SpriteBatch arg0) {}

	@Override
	public void updateDescription() {}

	public void checkFocus()
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
			if ((AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0) || (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0) || this.allowNegativeFocus) {
				this.basePassiveAmount = this.originalPassive + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			} else {
				this.basePassiveAmount = 0;
			}
		} else {
			this.basePassiveAmount = this.originalPassive;
		}
		applyFocus();
		updateDescription();
	}
}
