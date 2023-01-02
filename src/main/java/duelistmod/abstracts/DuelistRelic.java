package duelistmod.abstracts;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModLabeledButton;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.helpers.Util;
import duelistmod.rewards.BoosterPack;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;

public abstract class DuelistRelic extends CustomRelic implements ClickableRelic
{
	
	public DuelistRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) 
	{
		super(id, texture, outline, tier, sfx);
	}
	
	public DuelistRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx, int counter) 
	{
		super(id, texture, outline, tier, sfx);
		this.setCounter(counter);
	}
	
	@Override
    public void onRightClick() 
    {
    	if (this.counter > 0)
    	{
    		flash();
    		this.counter--;
    	}
    }
	
	protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

	public DuelistConfigurationData getConfigurations() {
		if (this.tier == RelicTier.SPECIAL || this.tier == RelicTier.STARTER) {
			return null;
		}
		RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
		ArrayList<IUIElement> settingElements = new ArrayList<>();
		String tooltip = "When disabled, " + this.name + " will not spawn during runs. Enabled by default.";
		settingElements.add(new DuelistLabeledToggleButton("Disable " + this.name, tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.relicCanSpawnConfigMap.getOrDefault(this.relicId, true), DuelistMod.settingsPanel, (label) -> {}, (button) ->
		{
			DuelistMod.relicCanSpawnConfigMap.put(this.relicId, !button.enabled);
			try
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				String relicConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.relicCanSpawnConfigMap);
				config.setString("relicCanSpawnConfigMap", relicConfigMap);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		}));
		LINEBREAK(35);

		settingElements.add(new ModLabel("ID: " + this.relicId, (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		settingElements.add(new ModLabeledButton("Copy ID", DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol, DuelistMod.yPos - 25, DuelistMod.settingsPanel, (element)->
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.relicId), null)
		));
		LINEBREAK(25);

		Util.formatConfigMenuObjectDescription(settingElements, this.getUpdatedDescription(), -5, this::LINEBREAK);

		return new DuelistConfigurationData(this.name, settingElements, this);
	}

	@Override
	public boolean canSpawn() {
		boolean idCheck = DuelistMod.relicCanSpawnConfigMap.getOrDefault(this.relicId, true);
		if (!idCheck) return false;

		if (this.tier == RelicTier.COMMON && DuelistMod.disableAllCommonRelics) {
			return false;
		}
		if (this.tier == RelicTier.UNCOMMON && DuelistMod.disableAllUncommonRelics) {
			return false;
		}
		if (this.tier == RelicTier.RARE && DuelistMod.disableAllRareRelics) {
			return false;
		}
		if (this.tier == RelicTier.SHOP && DuelistMod.disableAllShopRelics) {
			return false;
		}
		if (this.tier == RelicTier.BOSS && DuelistMod.disableAllBossRelics) {
			return false;
		}
		return true;
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
    
    public void onReceiveBoosterPack(BoosterPack pack) { }
    
	public void onSoulChange(int newSouls, int change) { }
	
	public void onGainVines() { }
	
    public int modifyShadowDamage() { return 0; }
    
    public int modifyUndeadDamage() { return 0; }
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onResummon(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummon(resummoned); }}
	
	public int modifyResummonAmt(AbstractCard resummoningCard) { return 0; }
	
	public boolean allowResummon(AbstractCard resummoningCard) { return true; }
	
	public boolean allowRevive() { return true; }
	
	public int modifyReviveListSize() { return 0; }
	
	public boolean upgradeResummon(AbstractCard resummoningCard) { return false; }
	
	public int modifyReviveCost(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public void onSynergyTribute() { }
	
	public void onLoseArtifact() { }
	
	public void onOverflow(int amt) { }
	
	public void onFish(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
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

	public String cannotUseMessage(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) { return "Cannot use due to relic: " + this.name; }
}
