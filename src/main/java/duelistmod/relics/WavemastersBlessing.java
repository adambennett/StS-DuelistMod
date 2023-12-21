package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.TsunamiAction;
import duelistmod.helpers.Util;

public class WavemastersBlessing extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("WavemastersBlessing");
	public static final String IMG = DuelistMod.makeRelicPath("AquaRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("AquaRelic.png");

	public WavemastersBlessing() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
    @Override
    public void atBattleStart() {
        this.counter = 0;
    }
    
    @Override
    public void atTurnStart() {
        if (!this.grayscale) {
            ++this.counter;
        }
        if (this.counter == 3) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new TsunamiAction(2));
            this.counter = -1;
            this.grayscale = true;
        }
    }
    
    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (Util.deckIs("Aqua Deck")) { return true; }
		else { return false; }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new WavemastersBlessing();
	}
}
