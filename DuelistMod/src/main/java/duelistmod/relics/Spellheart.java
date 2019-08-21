package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.variables.Tags;

public class Spellheart extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("Spellheart");
	public static final String IMG =  DuelistMod.makeRelicPath("AeroRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("AeroRelic_Outline.png");

	public Spellheart() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
    public void onEquip()
    {
		int monsters = 0;
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c.hasTag(Tags.SPELL))
			{
				 monsters++;
				 AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F)); 				
			}
		}
		
		AbstractDungeon.player.increaseMaxHp(monsters * 5, true);
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
