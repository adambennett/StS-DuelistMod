package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.variables.*;

public class Spellheart extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("Spellheart");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public Spellheart() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
    public void onEquip()
    {
		int monsters = 0;
		ArrayList<AbstractCard> toKeep = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c.hasTag(Tags.SPELL)) {  monsters++; }
			else { toKeep.add(c); }
		}
		AbstractDungeon.player.masterDeck.group.clear();
		for (AbstractCard c : toKeep) { AbstractDungeon.player.masterDeck.addToTop(c); }
		AbstractDungeon.player.increaseMaxHp(monsters * 4, true);
		setCounter(monsters * 4);
    }
	
	@Override
	public void onUnequip()
	{
		if (this.counter > 0) { AbstractDungeon.player.decreaseMaxHealth(this.counter); }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new Spellheart();
	}
}
