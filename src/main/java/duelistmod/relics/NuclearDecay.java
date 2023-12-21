package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class NuclearDecay extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("NuclearDecay");
	public static final String IMG = DuelistMod.makeRelicPath("ZombieRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("ZombieRelic.png");
	
	public NuclearDecay() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (Util.deckIs("Zombie Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void atTurnStart()
	{
		ArrayList<AbstractCard> toReduce = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.hand.group) { if (c.hasTag(Tags.FUSION)) { toReduce.add(c); }}
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) { if (c.hasTag(Tags.FUSION)) { toReduce.add(c); }}
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) { if (c.hasTag(Tags.FUSION)) { toReduce.add(c); }}
		for (AbstractCard c : toReduce) { if (c.cost > 0) { c.modifyCostForCombat(-1); }}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new NuclearDecay();
	}
}
