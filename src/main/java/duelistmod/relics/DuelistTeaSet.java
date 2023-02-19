package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.IncrementAction;
import duelistmod.dto.AnyDuelist;

public class DuelistTeaSet extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("DuelistTeaSet");
	public static final String IMG = DuelistMod.makeRelicPath("DuelistTeaSet.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DuelistTeaSet_Outline.png");

	public DuelistTeaSet() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.SOLID);
	}

	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Settings.isEndless || AbstractDungeon.floorNum <= 48;
	}


	@Override
	public void atBattleStart() 
	{
		if (this.counter == -2)
		{
			this.pulse = false;
			this.counter = -1;
			this.flash();
			this.addToTop(new IncrementAction(5, AnyDuelist.from(this)));
			this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
	}

	@Override
	public void onEnterRestRoom() {
		this.flash();
		this.counter = -2;
		this.pulse = true;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new DuelistTeaSet();
	}
}
