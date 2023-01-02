package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;

public class MillenniumPeriapt extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumPeriapt");
	public static final String IMG =  DuelistMod.makeRelicPath("DuelistDarkstone.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("DuelistDarkstone_Outline.png");

	public MillenniumPeriapt() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
    public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
	
	@Override
    public void onObtainCard(final AbstractCard card) {
       if (card.color == AbstractCard.CardColor.CURSE) {
           	DuelistMod.defaultMaxSummons += 2;
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
		return new MillenniumPeriapt();
	}
}
