package duelistmod.abstracts;

import java.util.ArrayList;
import java.util.List;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;

import duelistmod.DuelistMod;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.OrbConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPageWithJson;

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

	public AnyDuelist owner;

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
		this.owner = AbstractDungeon.player != null ? AnyDuelist.from(AbstractDungeon.player) : null;
	}

	public void fixFocusOnConfigChanges() {
		OrbConfigData data = Util.getOrbConfiguration(this.name);
		this.originalPassive = data.getConfigPassive();
		this.originalEvoke = data.getConfigEvoke();
		this.checkFocus();
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

	public OrbConfigData getActiveConfig() { return DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().getOrDefault(this.ID, this.getDefaultConfig()); }

	public Object getConfig(String key, Object defaultVal) {
		return this.getActiveConfig().getProperties().getOrDefault(key, this.getDefaultConfig().getProperties().getOrDefault(key, defaultVal));
	}

	public DuelistConfigurationData getConfigurations() {
		if (configShouldAllowEvokeDisable || configShouldAllowPassiveDisable || configShouldModifyPassive || configShouldModifyEvoke) {
			RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
			ArrayList<IUIElement> settingElements = new ArrayList<>();

			List<DuelistDropdown> dropdownsPre = this.configAddAfterDisabledBox(settingElements);

			if (configShouldAllowPassiveDisable) {
				String tooltip = "When disabled, #y" + this.name + " will not trigger the passive effect. Enabled by default.";
				settingElements.add(new DuelistLabeledToggleButton("Enable Passive Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().getOrDefault(this.name, new OrbConfigData(0, 0)).getPassiveDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
				{
					OrbConfigData data = this.getActiveConfig();
					data.setPassiveDisabled(!button.enabled);
					this.updateConfigSettings(data);
				}));
				LINEBREAK(25);
			}

			if (configShouldAllowEvokeDisable) {
				String tooltip = "When disabled, #y" + this.name + " will not trigger the #yEvoke effect. Enabled by default.";
				settingElements.add(new DuelistLabeledToggleButton("Enable Evoke Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().getOrDefault(this.name, new OrbConfigData(0, 0)).getEvokeDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
				{
					OrbConfigData data = this.getActiveConfig();
					data.setEvokeDisabled(!button.enabled);
					this.updateConfigSettings(data);

				}));
				LINEBREAK(25);
			}

			DuelistDropdown passiveSelector = null;
			DuelistDropdown evokeSelector = null;
			if (configShouldModifyPassive) {
				settingElements.add(new ModLabel("Base Passive Value", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
				ArrayList<String> passiveOptions = new ArrayList<>();
				for (int i = 0; i < 1001; i++) { passiveOptions.add(i+""); }
				OrbConfigData dataOnLoad = DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().getOrDefault(this.name, new OrbConfigData(0, 0));
				int defaultPassive = dataOnLoad.getDefaultPassive();
				String tooltip = "Modify the base value for this orb's passive effect. Set to #b" + defaultPassive + " by default.";
				passiveSelector = new DuelistDropdown(tooltip, passiveOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), 10, (s, i) -> {
					try {
						OrbConfigData data = this.getActiveConfig();
						data.setConfigPassive(i);
						this.updateConfigSettings(data);
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
				OrbConfigData dataOnLoad = DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().getOrDefault(this.name, new OrbConfigData(0, 0));
				int defaultEvoke = dataOnLoad.getDefaultEvoke();
				String tooltip = "Modify the base value for this orb's #yEvoke effect. Set to #b" + defaultEvoke + " by default.";
				evokeSelector = new DuelistDropdown(tooltip, evokeOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), 10, (s, i) -> {
					OrbConfigData data = this.getActiveConfig();
					data.setConfigEvoke(i);
					this.updateConfigSettings(data);
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

	public void updateConfigSettings(OrbConfigData data) {
		DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().put(this.ID, data);
		this.updateDescription();
		if (this.owner != null && this.owner.orbs() != null) {
			for (AbstractOrb o : this.owner.orbs()) {
				if (o.name.equals(this.name) && o instanceof DuelistOrb) {
					((DuelistOrb)o).fixFocusOnConfigChanges();
					o.updateDescription();
				}
			}
		}
		for (RefreshablePage page : DuelistMod.refreshablePages) {
			if (page instanceof SpecificConfigMenuPageWithJson) {
				SpecificConfigMenuPageWithJson pageWithJson = (SpecificConfigMenuPageWithJson)page;
				DuelistOrb configRef = pageWithJson.config.orb();
				if (configRef != null) {
					configRef.fixFocusOnConfigChanges();
					configRef.updateDescription();
					if (pageWithJson.image != null) {
						pageWithJson.image.tooltip = configRef.description;
					}
				}
			}
		}
		DuelistMod.configSettingsLoader.save();
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

	public int modifyTributeCost(AnyDuelist duelist, DuelistCard card, boolean summonChallenge, int current) {
		return 0;
	}

	public float modifyMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyMagicNumber(float magicAmt, AbstractCard card) { return this.modifyMagicNumber(magicAmt); }

	public float modifySecondMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifySecondMagicNumber(float magicAmt, AbstractCard card) { return this.modifySecondMagicNumber(magicAmt); }
	
	public float modifyThirdMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyThirdMagicNumber(float magicAmt, AbstractCard card) { return this.modifyThirdMagicNumber(magicAmt); }
	
	public float modifyEntomb(float magicAmt) { return magicAmt; }
	
	public float modifyEntomb(float magicAmt, AbstractCard card) { return this.modifyEntomb(magicAmt); }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }

	public boolean modifyCanUse(final AbstractCreature p, final DuelistCard card) { return true; }

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
		if (this.owner.hasPower(FocusPower.POWER_ID)) {
			this.passiveAmount += this.owner.getPower(FocusPower.POWER_ID).amount;
		}
	}
	
	public boolean gpcCheck() {
		return this.owner.orbs().get(0).equals(this) && this.owner.hasRelic(GoldPlatedCables.ID);
	}
	
	public boolean doesNotHaveNegativeFocus() {
		return !this.owner.hasPower(FocusPower.POWER_ID) || this.owner.getPower(FocusPower.POWER_ID).amount >= 0;
	}
	
	public int getCurrentFocus() {
		return this.owner.hasPower(FocusPower.POWER_ID) ? this.owner.getPower(FocusPower.POWER_ID).amount : 0;
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
	public void update() {
		if (this.owner != null && this.owner.getEnemy() != null) {
			this.hb.update();
			if (this.hb.hovered) {
				if (InputHelper.mX < 1400.0f * Settings.scale) {
					TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, this.name, this.description);
				}
				else {
					TipHelper.renderGenericTip(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, this.name, this.description);
				}
			}
			this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7f);
			return;
		}
		super.update();
	}

	@Override
	public void render(SpriteBatch arg0) {}

	@Override
	public void setSlot(int slotNum, int maxOrbs) {
		if (this.owner != null && this.owner.getEnemy() != null) {
			final float dist = 160.0f * Settings.scale + maxOrbs * 10.0f * Settings.scale;
			float angle = 100.0f + maxOrbs * 12.0f;
			final float offsetAngle = angle / 2.0f;
			angle *= slotNum / (maxOrbs - 1.0f);
			angle += 90.0f - offsetAngle;
			this.tX = dist * MathUtils.cosDeg(angle) + AbstractEnemyDuelist.enemyDuelist.drawX;
			this.tY = -80.0f * Settings.scale + dist * MathUtils.sinDeg(angle) + AbstractEnemyDuelist.enemyDuelist.drawY + AbstractEnemyDuelist.enemyDuelist.hb_h / 2.0f;
			if (maxOrbs == 1) {
				this.tX = AbstractEnemyDuelist.enemyDuelist.drawX;
				this.tY = 160.0f * Settings.scale + AbstractEnemyDuelist.enemyDuelist.drawY + AbstractEnemyDuelist.enemyDuelist.hb_h / 2.0f;
			}
			this.hb.move(this.tX, this.tY);
			return;
		}
		super.setSlot(slotNum, maxOrbs);
	}

	@Override
	public void updateAnimation() {
		if (this.owner != null && this.owner.getEnemy() != null) {
			this.bobEffect.update();
			if (AbstractEnemyDuelist.enemyDuelist != null) {
				this.cX = MathHelper.orbLerpSnap(this.cX, AbstractEnemyDuelist.enemyDuelist.animX + this.tX);
				this.cY = MathHelper.orbLerpSnap(this.cY, AbstractEnemyDuelist.enemyDuelist.animY + this.tY);
				if (this.channelAnimTimer != 0.0f) {
					this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
					if (this.channelAnimTimer < 0.0f) {
						this.channelAnimTimer = 0.0f;
					}
				}
				this.c.a = Interpolation.pow2In.apply(1.0f, 0.01f, this.channelAnimTimer / 0.5f);
				this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01f, this.channelAnimTimer / 0.5f);
			}
			return;
		}
		super.updateAnimation();
	}

	@Override
	public void updateDescription() {}

	public void checkFocus()
	{
		if (this.owner != null && this.owner.hasPower(FocusPower.POWER_ID)) {
			if ((this.owner.getPower(FocusPower.POWER_ID).amount > 0) || (this.owner.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0) || this.allowNegativeFocus) {
				this.basePassiveAmount = this.originalPassive + this.owner.getPower(FocusPower.POWER_ID).amount;
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
