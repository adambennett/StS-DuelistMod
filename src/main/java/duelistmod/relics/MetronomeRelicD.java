package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.RandomizedMetronomeAction;
import duelistmod.variables.Strings;

public class MetronomeRelicD extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MetronomeRelicD");
    public static final String IMG = DuelistMod.makeRelicPath("RainbowMetronome.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Metronome_Outline.png");

	public MetronomeRelicD() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.SOLID);
	}
	
	public void addMetToHand()
	{
		this.flash();
		AbstractCard met = DuelistCard.returnRandomMetronome();
		AbstractDungeon.actionManager.addToBottom(new RandomizedMetronomeAction(met, AbstractDungeon.cardRandomRng.random(1, 5) == 1));
	}

	// Description
	@Override
	public String getUpdatedDescription() 
	{
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MetronomeRelicD();
	}
}
