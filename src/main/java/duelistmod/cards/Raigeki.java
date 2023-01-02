package duelistmod.cards;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
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

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.enums.Percentages;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.*;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.variables.*;

import java.util.ArrayList;

public class Raigeki extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Raigeki");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.RAIGEKI);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public Raigeki() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.isMultiDamage = true;
		this.originalName = this.name;
		this.baseDamage = this.damage = 10 + DuelistMod.raigekiBonusDamage;
		this.magicNumber = this.baseMagicNumber = 5;
		this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
    	AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05F));

        int dmg = this.damage + DuelistMod.raigekiBonusDamage;
        attackAll(AttackEffect.SLASH_DIAGONAL, new int[] { dmg, dmg, dmg, dmg, dmg, dmg, dmg, dmg, dmg, dmg }, DamageInfo.DamageType.NORMAL);

    	for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
    	{
    		int roll = AbstractDungeon.cardRandomRng.random(1, 100);
            int mag = DuelistMod.raigekiIncludeMagic ? this.magicNumber : 0;
            int mod = this.upgraded ? (mag + DuelistMod.raigekiBonusUpgradePercentage.value()) : (mag + DuelistMod.raigekiBonusPercentage.value());
            int check = 100 - (mod);
            if (DuelistMod.raigekiAlwaysStun || (DuelistMod.raigekiAlwaysStunUpgrade && this.upgraded)) {
                check = -1;
            }
            if (p.hasPower(ElectricityPower.POWER_ID)) {
                check -= p.getPower(ElectricityPower.POWER_ID).amount;
            }
    		if (check < 1 || roll > check)
    		{
    			AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(monster, p));
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Raigeki();
    }

    // Upgraded stats.
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
    public DuelistConfigurationData getConfigurations() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        RESET_Y();
        LINEBREAK();
        LINEBREAK();
        LINEBREAK();
        LINEBREAK();
        
        // Always Trigger
        String tooltip = "When enabled, #yRaigeki will always #yStun all enemies.";
        settingElements.add(new DuelistLabeledToggleButton("Always trigger Stun effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.raigekiAlwaysStun, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.raigekiAlwaysStun = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_RAIGEKI_ALWAYS_STUN, DuelistMod.raigekiAlwaysStun);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        // Always Trigger - when upgraded
        tooltip = "When enabled, #yRaigeki will always #yStun all enemies when it is #yUpgraded.";
        settingElements.add(new DuelistLabeledToggleButton("Always trigger Stun effect (When Upgraded)", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.raigekiAlwaysStunUpgrade, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.raigekiAlwaysStunUpgrade = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_RAIGEKI_ALWAYS_STUN_UPGRADED, DuelistMod.raigekiAlwaysStunUpgrade);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        // Include Magic Number
        tooltip = "When enabled, the chance to #yStun will consider the card's magic number. This means the chance to #yStun can be improved by effects such as #yElectricity and #ySoldering. NL NL #yUpgraded copies have a magic number of 45 (45% chance) and non-Upgraded copies have a magic number of 5 (5% chance).";
        settingElements.add(new DuelistLabeledToggleButton("Include Magic Number in Stun roll", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.raigekiIncludeMagic, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.raigekiIncludeMagic = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_RAIGEKI_INCLUDE_MAGIC, DuelistMod.raigekiIncludeMagic);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK(10);

        // Bonus Percentage
        ArrayList<String> bonusPercentages = new ArrayList<>();
        for (Percentages p : Percentages.values()) {
            bonusPercentages.add(p.displayName());
        }

        settingElements.add(new ModLabel("Bonus Stun Percentage", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Improves the chances that #yRaigeki will #yStun all enemies when not #yUpgraded.";
        DuelistDropdown bonusPercentageChance = new DuelistDropdown(tooltip, bonusPercentages, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + 70), Settings.scale * (DuelistMod.yPos + 22), 7, null,(s, i) -> {
            DuelistMod.raigekiBonusIndex = i;
            DuelistMod.raigekiBonusPercentage = Percentages.menuMapping.get(DuelistMod.raigekiBonusIndex);
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_RAIGEKI_BONUS_PERCENTAGE_INDEX, i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        bonusPercentageChance.setSelectedIndex(DuelistMod.raigekiBonusIndex);
        
        LINEBREAK(15);
        
        // Bonus Percentage - Upgraded
        ArrayList<String> bonusUpgradePercentages = new ArrayList<>();
        for (Percentages p : Percentages.values()) {
            bonusUpgradePercentages.add(p.displayName());
        }
        settingElements.add(new ModLabel("Bonus Stun Percentage (Upgraded)", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Improves the chances that #yRaigeki will #yStun all enemies when #yUpgraded.";
        DuelistDropdown bonusUpgradePercentageChance = new DuelistDropdown(tooltip, bonusUpgradePercentages, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + 70), Settings.scale * (DuelistMod.yPos + 22),7, null, (s, i) -> {
            DuelistMod.raigekiBonusUpgradeIndex = i;
            DuelistMod.raigekiBonusUpgradePercentage = Percentages.menuMapping.get(DuelistMod.raigekiBonusUpgradeIndex);
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_RAIGEKI_BONUS_UPGRADE_PERCENTAGE_INDEX, i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        bonusUpgradePercentageChance.setSelectedIndex(DuelistMod.raigekiBonusUpgradeIndex);

        LINEBREAK(15);

        // Bonus Damage
        ArrayList<String> bonusDamages = new ArrayList<>();
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
        bonusDamage.setSelectedIndex(DuelistMod.raigekiBonusDamage);

        // Add dropdowns to card
        settingElements.add(bonusDamage);
        settingElements.add(bonusUpgradePercentageChance);
        settingElements.add(bonusPercentageChance);

        return new DuelistConfigurationData(this.name, settingElements, this);
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
