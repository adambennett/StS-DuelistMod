package duelistmod.relics;

import com.badlogic.gdx.graphics.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.*;
import duelistmod.variables.*;

import java.io.*;
import java.util.*;

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
		score(2000);
    }

	public boolean modifyCanUse(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) {
		return !card.hasTag(Tags.SPELL) || (GameActionManager.turn % 2 != 0);
	}

	public String cannotUseMessage(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) { return "Cannot use due to relic: " + this.name; }

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

	private void score(int amt) {
		Util.addDuelistScore(amt);
	}
	
}
