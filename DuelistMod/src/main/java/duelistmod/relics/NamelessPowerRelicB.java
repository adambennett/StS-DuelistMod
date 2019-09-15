package duelistmod.relics;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.BuffHelper;
import duelistmod.powers.DummyPowerDoNotApply;
import duelistmod.variables.Strings;

public class NamelessPowerRelicB extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("NamelessPowerRelicB");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	
	private AbstractPower buff = new DummyPowerDoNotApply();
	private String buffName;
	private int buffRollID;
	private int trapsRemoved = 3;
	
	public NamelessPowerRelicB() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.CLINK);
		this.buffRollID = ThreadLocalRandom.current().nextInt(1, 8);
		this.buffName = BuffHelper.trapVortexBuffNameB(buffRollID);
		setDescription();
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
	
	public void setDescOnEquip(int num)
	{
		description = DESCRIPTIONS[2] + num + " " + this.buffName + ".";
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
	
	@Override
    public void onEquip()
    {
		this.buff = BuffHelper.trapVortexB(this.buffRollID, this.trapsRemoved);
		this.buffName = BuffHelper.trapVortexBuffNameB(this.buffRollID);
		this.counter = this.trapsRemoved;
		setDescOnEquip(this.trapsRemoved);
    }
	
	@Override
	public void atBattleStart()
	{
		this.buff = BuffHelper.trapVortexB(this.buffRollID, this.trapsRemoved);
		if (!(this.buff instanceof DummyPowerDoNotApply)) { DuelistCard.applyPowerToSelf(buff); this.flash(); }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.buffName + DESCRIPTIONS[1];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new NamelessPowerRelicB();
	}
}
