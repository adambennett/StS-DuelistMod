package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

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
	public static final String ID = DuelistMod.makeID("RandomTributeMonsterRelic");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	//public boolean cardSelected = false;
	//public DuelistCard cardToAdd = null;

	public RandomTributeMonsterRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		DuelistCard deckUpgradedTribs = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER);
		while (deckUpgradedTribs.baseTributes < 1) { deckUpgradedTribs = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER); }
    	if (deckUpgradedTribs.baseTributes > 1) { deckUpgradedTribs.modifyTributesPerm(-deckUpgradedTribs.baseTributes + 1); }
    	AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(deckUpgradedTribs, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
    	//cardSelected = true;
    	//cardToAdd = deckUpgradedTribs;
    	//AbstractDungeon.player.masterDeck.addToRandomSpot(deckUpgradedTribs);   	
	}
	
	/*
	@Override
	public void update() 
	{
		super.update();
		if (cardSelected && cardToAdd != null) 
		{
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cardToAdd, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
		}
	}
	*/

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
