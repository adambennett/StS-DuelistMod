package duelistmod.relics;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.BuffHelper;
import duelistmod.powers.DummyPowerDoNotApply;
import duelistmod.variables.*;

public class TrapVortex extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("TrapVortex");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	
	private AbstractPower buff = new DummyPowerDoNotApply();
	private String buffName;
	private int buffRollID;
	private int trapsRemoved = 0;
	
	public TrapVortex() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
		this.buffRollID = ThreadLocalRandom.current().nextInt(1, 8);
		this.buffName = BuffHelper.trapVortexBuffName(buffRollID);
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
		int monsters = 0;
		ArrayList<AbstractCard> toKeep = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c.hasTag(Tags.TRAP)) {  monsters++; }
			else { toKeep.add(c); }
		}
		AbstractDungeon.player.masterDeck.group.clear();
		for (AbstractCard c : toKeep) { AbstractDungeon.player.masterDeck.addToTop(c); }
		this.buff = BuffHelper.trapVortex(this.buffRollID, monsters);
		this.buffName = BuffHelper.trapVortexBuffName(this.buffRollID);
		this.trapsRemoved = monsters;
		this.counter = monsters;
		setDescOnEquip(monsters);
    }
	
	@Override
	public void atBattleStart()
	{
		this.buff = BuffHelper.trapVortex(this.buffRollID, this.counter);
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
		return new TrapVortex();
	}
}
