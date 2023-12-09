package duelistmod.relics;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.relics.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.helpers.*;
import duelistmod.variables.*;

public class PointPass extends DuelistRelic {

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("PointPass");
    public static final String IMG = DuelistMod.makeRelicPath("MillenniumMembershipCard.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Shop_Outline.png");

	public PointPass() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}

	@Override
    public void onEquip()
    {
		AbstractDungeon.player.gainGold(200);
		Util.addDuelistScore(2000, false);
    }

	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return !DuelistMod.allDecksUnlocked(false);
	}

	@Override
	public boolean modifyCanUse(final AbstractCreature p, final DuelistCard card) {
		return !card.hasTag(Tags.SPELL) || (GameActionManager.turn % 2 != 0);
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new PointPass();
	}
	
	@Override
	public int getPrice()
	{
		return 0;
	}

}
