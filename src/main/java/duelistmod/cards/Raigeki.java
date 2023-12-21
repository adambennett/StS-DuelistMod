package duelistmod.cards;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.CardConfigData;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.enums.Percentage;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.ElectricityPower;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class Raigeki extends DuelistCard {
    public static final String ID = DuelistMod.makeID("Raigeki");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.RAIGEKI);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    private static final String alwaysStunKey = "Always trigger Stun effect";
    private static final boolean defaultAlwaysStun = false;
    private static final String alwaysStunUpgradeKey = "Always trigger Stun effect (When Upgraded)";
    private static final boolean defaultAlwaysStunUpgrade = false;
    private static final String includeMagicKey = "Include Magic Number in Stun roll";
    private static final boolean defaultIncludeMagic = true;
    private static final String bonusIndexKey = "Bonus Stun Percentage";
    private static final int defaultBonusIndex = 0;
    private static final String bonusIndexUpgradeKey = "Bonus Stun Percentage (Upgraded)";
    private static final int defaultBonusUpgradeIndex = 0;

    public Raigeki() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.isMultiDamage = true;
		this.originalName = this.name;
		this.baseDamage = this.damage = 10;
		this.magicNumber = this.baseMagicNumber = 5;
		this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
    	AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05F));
        attackAll(AttackEffect.SLASH_DIAGONAL, this.multiDamage, DamageInfo.DamageType.NORMAL);

    	for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
    		int roll = AbstractDungeon.cardRandomRng.random(1, 100);
            int mag = (boolean)this.getConfig(includeMagicKey, defaultIncludeMagic) ? this.magicNumber : 0;
            int mod = this.upgraded ? (mag + DuelistMod.raigekiBonusUpgradePercentage.value()) : (mag + DuelistMod.raigekiBonusPercentage.value());
            int check = 100 - (mod);
            if ((boolean)this.getConfig(alwaysStunKey, defaultAlwaysStun) || ((boolean)this.getConfig(alwaysStunUpgradeKey, defaultAlwaysStunUpgrade) && this.upgraded)) {
                check = -1;
            }
            if (p.hasPower(ElectricityPower.POWER_ID)) {
                check -= p.getPower(ElectricityPower.POWER_ID).amount;
            }
    		if (check < 1 || roll > check) {
    			AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(monster, p));
    		}
    	}
    }

    @Override
    public AbstractCard makeCopy() {
        return new Raigeki();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(40);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public CardConfigData getDefaultConfig() {
        CardConfigData config = new CardConfigData();
        config.put(alwaysStunKey, defaultAlwaysStun);
        config.put(alwaysStunUpgradeKey, defaultAlwaysStunUpgrade);
        config.put(includeMagicKey, defaultIncludeMagic);
        config.put(bonusIndexKey, defaultBonusIndex);
        config.put(bonusIndexUpgradeKey, defaultBonusUpgradeIndex);
        return config;
    }

    @Override
    public DuelistConfigurationData getConfigurations() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        RESET_Y();
        LINEBREAK();
        LINEBREAK();
        LINEBREAK();
        LINEBREAK();
        
        // Always Trigger
        String tooltip = "When enabled, #yRaigeki will always #yStun all enemies.";
        settingElements.add(new DuelistLabeledToggleButton("Always trigger Stun effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, (boolean)this.getConfig(alwaysStunKey, defaultAlwaysStun), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            CardConfigData data = this.getActiveConfig();
            data.put(alwaysStunKey, button.enabled);
            this.updateConfigSettings(data);
        }));

        LINEBREAK();

        // Always Trigger - when upgraded
        tooltip = "When enabled, #yRaigeki will always #yStun all enemies when it is #yUpgraded.";
        settingElements.add(new DuelistLabeledToggleButton("Always trigger Stun effect (When Upgraded)", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, (boolean)this.getConfig(alwaysStunUpgradeKey, defaultAlwaysStunUpgrade), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            CardConfigData data = this.getActiveConfig();
            data.put(alwaysStunUpgradeKey, button.enabled);
            this.updateConfigSettings(data);
        }));

        LINEBREAK();

        // Include Magic Number
        tooltip = "When enabled, the chance to #yStun will consider the card's magic number. This means the chance to #yStun can be improved by effects such as #yElectricity and #ySoldering. NL NL #yUpgraded copies have a magic number of 45 (45% chance) and non-Upgraded copies have a magic number of 5 (5% chance).";
        settingElements.add(new DuelistLabeledToggleButton("Include Magic Number in Stun roll", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, (boolean)this.getConfig(includeMagicKey, defaultIncludeMagic), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            CardConfigData data = this.getActiveConfig();
            data.put(includeMagicKey, button.enabled);
            this.updateConfigSettings(data);
        }));

        LINEBREAK(10);

        // Bonus Percentage
        ArrayList<String> bonusPercentages = new ArrayList<>();
        for (Percentage p : Percentage.values()) {
            bonusPercentages.add(p.displayName());
        }

        settingElements.add(new ModLabel("Bonus Stun Percentage", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Improves the chances that #yRaigeki will #yStun all enemies when not #yUpgraded.";
        DuelistDropdown bonusPercentageChance = new DuelistDropdown(tooltip, bonusPercentages, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + 70), Settings.scale * (DuelistMod.yPos + 22), 7, null,(s, i) -> {
            CardConfigData data = this.getActiveConfig();
            data.put(bonusIndexKey, i);
            DuelistMod.raigekiBonusPercentage = Percentage.menuMapping.getOrDefault(i, Percentage.ZERO);
            this.updateConfigSettings(data);
        });
        bonusPercentageChance.setSelected(this.getConfig(bonusIndexKey, defaultBonusIndex).toString());
        
        LINEBREAK(15);
        
        // Bonus Percentage - Upgraded
        ArrayList<String> bonusUpgradePercentages = new ArrayList<>();
        for (Percentage p : Percentage.values()) {
            bonusUpgradePercentages.add(p.displayName());
        }
        settingElements.add(new ModLabel("Bonus Stun Percentage (Upgraded)", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Improves the chances that #yRaigeki will #yStun all enemies when #yUpgraded.";
        DuelistDropdown bonusUpgradePercentageChance = new DuelistDropdown(tooltip, bonusUpgradePercentages, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + 70), Settings.scale * (DuelistMod.yPos + 22),7, null, (s, i) -> {
            CardConfigData data = this.getActiveConfig();
            data.put(bonusIndexUpgradeKey, i);
            DuelistMod.raigekiBonusUpgradePercentage = Percentage.menuMapping.getOrDefault(i, Percentage.ZERO);
            this.updateConfigSettings(data);
        });
        bonusUpgradePercentageChance.setSelected(this.getConfig(bonusIndexUpgradeKey, defaultBonusUpgradeIndex).toString());

        LINEBREAK(15);

        // Bonus Damage
        /*ArrayList<String> bonusDamages = new ArrayList<>();
        for (int i = 0; i < 1001; i++) {
            bonusDamages.add(i+"");
        }
        settingElements.add(new ModLabel("Bonus Damage", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Increases the base damage of #yRaigeki.";
        DuelistDropdown bonusDamage = new DuelistDropdown(tooltip, bonusDamages, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + 70), Settings.scale * (DuelistMod.yPos + 22),7,null, (s, i) -> {
            DuelistMod.raigekiBonusDamage = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_RAIGEKI_BONUS_DAMAGE, i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        bonusDamage.setSelectedIndex(DuelistMod.raigekiBonusDamage);*/

        // Add dropdowns to card
        //settingElements.add(bonusDamage);
        settingElements.add(bonusUpgradePercentageChance);
        settingElements.add(bonusPercentageChance);

        return new DuelistConfigurationData(this.name, settingElements, this);
    }

}
