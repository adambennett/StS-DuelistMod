package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

public class RandomTributeMonsterRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("RandomTributeMonsterRelic");
	public static final String IMG = DuelistMod.makePath(Strings.TRIBUTE_EGG_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TRIBUTE_EGG_RELIC_OUTLINE);

	public RandomTributeMonsterRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		DuelistCard deckUpgradedTribs = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.MONSTER);
		while (deckUpgradedTribs.baseTributes < 1) { deckUpgradedTribs = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.MONSTER); }
    	deckUpgradedTribs.modifyTributesPerm(-deckUpgradedTribs.baseTributes + 1);
    	AbstractDungeon.player.masterDeck.addToRandomSpot(deckUpgradedTribs);   	
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new RandomTributeMonsterRelic();
	}
}
