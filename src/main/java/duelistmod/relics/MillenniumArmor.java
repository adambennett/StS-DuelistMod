package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.MillenniumArmorPlate;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.interfaces.VisitFromAnubisRemovalFilter;

import java.util.HashSet;

public class MillenniumArmor extends DuelistRelic implements VisitFromAnubisRemovalFilter, MillenniumItem {

	public static final String ID = DuelistMod.makeID("MillenniumArmor");
	public static final String IMG =  DuelistMod.makeRelicPath("MillenniumArmor.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("MillenniumArmor_Outline.png");
    private int armorUp;
	private final HashSet<String> alreadyTriggeredForPlates;

	public MillenniumArmor() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.SOLID);
		this.alreadyTriggeredForPlates = new HashSet<>();
		this.armorUp = 8;
	}
	
	@Override
	public void atBattleStart() {
		this.flash();
		DuelistCard.gainTempHP(this.armorUp);
		this.grayscale = true;
	}
	
	public void pickupArmorPlate(int plateSize, AbstractRelic plate) {
		if (!this.alreadyTriggeredForPlates.contains(plate.relicId)) {
			this.armorUp += plateSize;
			setDescription();
			this.alreadyTriggeredForPlates.add(plate.relicId);
		}
	}
	
	public void setDescription() {
		this.description = getUpdatedDescription();
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
	}

	@Override
	public boolean canRemove() {
		boolean hasPlates = false;
		for (AbstractRelic rel : AbstractDungeon.player.relics) {
			if (rel instanceof MillenniumArmorPlate) {
				hasPlates = true;
				break;
			}
		}
		return !hasPlates;
	}
	
	@Override
    public void onVictory() {
		this.grayscale = false;
    }

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + armorUp + DESCRIPTIONS[1];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumArmor();
	}
}
