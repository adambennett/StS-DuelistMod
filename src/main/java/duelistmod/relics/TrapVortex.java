package duelistmod.relics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.BuffHelper;
import duelistmod.helpers.Util;
import duelistmod.powers.DummyPowerDoNotApply;
import duelistmod.variables.Tags;

public class TrapVortex extends DuelistRelic implements CustomSavable<Integer> {

	public static final String ID = DuelistMod.makeID("TrapVortex");
    public static final String IMG = DuelistMod.makeRelicPath("TrapVortex.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("TrapVortexOutline.png");
	
	private AbstractPower buff = new DummyPowerDoNotApply();
	private String buffName;
	private int buffRollID;
	
	public TrapVortex() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
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
		int monsters = 0;
		ArrayList<AbstractCard> toKeep = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c.hasTag(Tags.TRAP)) {  monsters++; }
			else { toKeep.add(c); }
		}
		AbstractDungeon.player.masterDeck.group.clear();
		for (AbstractCard c : toKeep) { AbstractDungeon.player.masterDeck.addToTop(c); }
		this.buff = BuffHelper.trapVortex(this.buffRollID, monsters);
		this.setBuffName();
		this.counter = monsters;
		setDescOnEquip(monsters);
    }
	
	@Override
	public void atBattleStart() {
		this.buff = BuffHelper.trapVortex(this.buffRollID, this.counter);
		if (!(this.buff instanceof DummyPowerDoNotApply)) { DuelistCard.applyPowerToSelf(buff); this.flash(); }
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.buffName + DESCRIPTIONS[1];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new TrapVortex();
	}

	@Override
	public Integer onSave() {
		return this.buffRollID;
	}

	private void setBuffName() {
		this.buffName = BuffHelper.trapVortexBuffName(this.buffRollID);
	}

	@Override
	public void onLoad(Integer saved) {
		if (saved == null) return;

		try {
			this.buffRollID = saved;
			this.setBuffName();
			this.setDescOnEquip(this.counter);
		} catch (Exception ex) {
			Util.logError("Trap vortex got an exception while attempting to load the saved buff", ex);
		}
	}


	@Override
	public Type savedType() {
		return Integer.class;
	}
}
