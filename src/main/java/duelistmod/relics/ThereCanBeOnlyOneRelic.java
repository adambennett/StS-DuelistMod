package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.RelicConfigData;
import duelistmod.powers.duelistPowers.ThereCanBeOnlyOnePower;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.variables.Strings;
import java.util.ArrayList;
import java.util.List;

public class ThereCanBeOnlyOneRelic extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("ThereCanBeOnlyOneRelic");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

    private static final String energyKey = "Energy Gain";
    private static final String defaultEnergy = "1";

    private int energyGain = 1;

    public ThereCanBeOnlyOneRelic() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
        setDescription();
    }

    @Override
    public void atBattleStart() {
    	DuelistCard.applyPowerToSelf(new ThereCanBeOnlyOnePower(AbstractDungeon.player, AbstractDungeon.player, 0, false));
    }
    
    @Override
	public void onEquip() {
    	setDescription();
		AbstractDungeon.player.energy.energyMaster += this.energyGain;
	}
	
	@Override
	public void onUnequip() {
		AbstractDungeon.player.energy.energyMaster -= this.energyGain;
	}

    @Override
    public String getUpdatedDescription() {
        StringBuilder energyString = new StringBuilder();
        for (int i = 0; i < this.energyGain; i++) {
            energyString.append(" [E] ");
        }
        return DESCRIPTIONS[0] + energyString + DESCRIPTIONS[1];
    }
    
    public void setDescription() {
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

    @Override
    public RelicConfigData getDefaultConfig() {
        RelicConfigData config = new RelicConfigData();
        config.getProperties().put(energyKey, defaultEnergy);
        return config;
    }

    @Override
    protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
        List<DuelistDropdown> dropdowns = new ArrayList<>();

        settingElements.add(new ModLabel("Energy Gain", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> magicOptions = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            magicOptions.add(i+"");
        }
        String tooltip = "Modify the amount of energy gain granted. Set to #b" + this.getDefaultConfig(energyKey) + " by default.";
        DuelistDropdown energySelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            RelicConfigData data = this.getActiveConfig();
            data.getProperties().put(energyKey, s);
            setEnergyGain(s);
            this.updateConfigSettings(data);
            this.setDescription();
        });
        String defaultEnergy = this.getConfig(energyKey, ThereCanBeOnlyOneRelic.defaultEnergy).toString();
        if (defaultEnergy == null || defaultEnergy.trim().equals("")) {
            defaultEnergy = "1";
        }
        energySelector.setSelected(defaultEnergy);
        setEnergyGain(defaultEnergy);
        dropdowns.add(energySelector);
        this.setDescription();
        return dropdowns;
    }

    private void setEnergyGain(String amount) {
        try {
            this.energyGain = Integer.parseInt(amount);
        } catch (Exception ignored) {}
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ThereCanBeOnlyOneRelic();
    }

}
