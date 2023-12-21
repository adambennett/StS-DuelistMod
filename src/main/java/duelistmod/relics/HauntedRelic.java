package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.RelicConfigData;
import duelistmod.enums.BadlogicColors;
import duelistmod.powers.incomplete.HauntedDebuff;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.ArrayList;
import java.util.List;

public class HauntedRelic extends DuelistRelic {
    public static final String ID = DuelistMod.makeID("HauntedRelic");
    public static final String IMG = DuelistMod.makeRelicPath("HauntedRelic2.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Haunted_Outline.png");

    private static final String colorKey = "Card Glow Color";
    private static final String defaultColor = "PURPLE";

    public HauntedRelic() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
        setDescription();
    }

    @Override
    public void atBattleStart() {
    	DuelistCard.applyPowerToSelf(new HauntedDebuff(AbstractDungeon.player, AbstractDungeon.player, 1));
    }
    
    @Override
	public void onEquip() {
    	setDescription();
		AbstractDungeon.player.energy.energyMaster++;
	}
	
	@Override
	public void onUnequip() {
		AbstractDungeon.player.energy.energyMaster--;
	}

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
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
        config.getProperties().put(colorKey, defaultColor);
        return config;
    }

    @Override
    protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
        List<DuelistDropdown> dropdowns = new ArrayList<>();

        settingElements.add(new ModLabel("Haunted Card Glow Color", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> magicOptions = new ArrayList<>();
        for (BadlogicColors color : BadlogicColors.values()) {
            magicOptions.add(color.name());
        }
        String tooltip = "Modify the card glow color that is applied to #yHaunted cards. Set to #b" + this.getDefaultConfig(colorKey) + " by default.";
        DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            RelicConfigData data = this.getActiveConfig();
            data.getProperties().put(colorKey, s);
            setCardGlowColor(s);
            this.updateConfigSettings(data);
        });
        String defColor = this.getConfig(colorKey, defaultColor).toString();
        if (defColor == null || defColor.trim().equals("")) {
            defColor = "PURPLE";
        }
        magicSelector.setSelected(defColor);
        setCardGlowColor(defColor);
        dropdowns.add(magicSelector);
        return dropdowns;
    }

    private void setCardGlowColor(String color) {
        Color previousColor = DuelistMod.hauntedGlowColor;
        DuelistMod.hauntedGlowColor = null;
        for (BadlogicColors c : BadlogicColors.values()) {
            if (c.name().equalsIgnoreCase(color)) {
                DuelistMod.hauntedGlowColor = c.getColor();
            }
        }
        if (DuelistMod.hauntedGlowColor == null) {
            DuelistMod.hauntedGlowColor = previousColor == null ? Color.PURPLE : previousColor;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HauntedRelic();
    }
}
