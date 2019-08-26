package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class TributeEggRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("TributeEggRelic");
	public static final String IMG = DuelistMod.makeRelicPath("TributeMonsterEggRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Egg_Outline.png");
	//public boolean cardSelected = false;
	//public DuelistCard cardToAdd = null;

	public TributeEggRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void onObtainCard(AbstractCard c)
	{
		if (c instanceof DuelistCard)
		{
			DuelistCard dc = (DuelistCard)c;
			if (dc.tributes > 1 && dc.hasTag(Tags.MONSTER))
			{
				dc.modifyTributesPerm(-1);
				this.flash();
			}
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
		return new TributeEggRelic();
	}
}
