package duelistmod.relics;

import java.lang.reflect.Type;
import java.util.concurrent.ThreadLocalRandom;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.BuffHelper;
import duelistmod.helpers.Util;
import duelistmod.powers.DummyPowerDoNotApply;
import duelistmod.variables.Strings;

public class NamelessPowerRelicB extends DuelistRelic implements CustomSavable<Integer> {

	public static final String ID = DuelistMod.makeID("NamelessPowerRelicB");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	
	private AbstractPower buff = new DummyPowerDoNotApply();
	private String buffName;
	private int buffRollID;
	
	public NamelessPowerRelicB() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.CLINK);
		this.buffRollID = ThreadLocalRandom.current().nextInt(1, 7);
		this.setBuffName();
		setDescription();
	}
	
	public void setDescription() {
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
	
	public void setDescOnEquip(int num) {
		description = DESCRIPTIONS[2] + num + " " + this.buffName + ".";
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
	
	@Override
    public void onEquip() {
		this.buff = BuffHelper.trapVortexB(this.buffRollID, 3);
		this.setBuffName();
		this.counter = 3;
		setDescOnEquip(3);
    }
	
	@Override
	public void atBattleStart() {
		this.buff = BuffHelper.trapVortexB(this.buffRollID, this.counter);
		if (!(this.buff instanceof DummyPowerDoNotApply)) { DuelistCard.applyPowerToSelf(buff); this.flash(); }
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.buffName + DESCRIPTIONS[1];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new NamelessPowerRelicB();
	}

	@Override
	public Integer onSave() {
		return this.buffRollID;
	}

	private void setBuffName() {
		this.buffName = BuffHelper.trapVortexBuffNameB(this.buffRollID);
	}

	@Override
	public void onLoad(Integer saved) {
		if (saved == null) return;

		try {
			this.buffRollID = saved;
			this.setBuffName();
			this.setDescOnEquip(this.counter);
		} catch (Exception ex) {
			Util.logError("Ascended Power relic got an exception while attempting to load the saved buff", ex);
		}
	}


	@Override
	public Type savedType() {
		return Integer.class;
	}
}
