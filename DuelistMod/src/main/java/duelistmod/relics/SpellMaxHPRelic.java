package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class SpellMaxHPRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("SpellMaxHPRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("NatureRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicPath("NatureRelic.png");

	public SpellMaxHPRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		if (AbstractDungeon.ascensionLevel < 16 && Util.getChallengeLevel() < 16)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void onObtainCard(AbstractCard c) 
	{
		if (c.hasTag(Tags.SPELL))
		{
			this.flash();
			AbstractDungeon.player.increaseMaxHp(1, true);
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new SpellMaxHPRelic();
	}
}
