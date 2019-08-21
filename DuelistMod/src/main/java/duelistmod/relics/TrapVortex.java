package duelistmod.relics;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.BuffHelper;
import duelistmod.powers.DummyPowerDoNotApply;
import duelistmod.variables.Tags;

public class TrapVortex extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("TrapVortex");
	public static final String IMG =  DuelistMod.makeRelicPath("AeroRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("AeroRelic_Outline.png");
	
	private AbstractPower buff = new DummyPowerDoNotApply();
	private String buffName;
	private int buffRollID;
	private int trapsRemoved = 0;
	
	public TrapVortex() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
		this.buffRollID = ThreadLocalRandom.current().nextInt(1, 8);
		this.buffName = BuffHelper.trapVortexBuffName(buffRollID);
	}
	
	@Override
    public void onEquip()
    {
		int monsters = 0;
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c.hasTag(Tags.TRAP))
			{
				 monsters++;
				 AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F)); 				
			}
		}
		this.buff = BuffHelper.trapVortex(this.buffRollID, monsters);
		this.buffName = BuffHelper.trapVortexBuffName(this.buffRollID);
		this.trapsRemoved = monsters;
		this.counter = monsters;
    }
	
	@Override
	public void atBattleStart()
	{
		this.buff = BuffHelper.trapVortex(this.buffRollID, this.trapsRemoved);
		if (!(this.buff instanceof DummyPowerDoNotApply)) { DuelistCard.applyPowerToSelf(buff); }
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
