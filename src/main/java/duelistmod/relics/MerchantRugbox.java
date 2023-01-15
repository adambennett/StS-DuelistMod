package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;
import duelistmod.interfaces.ShopDupeRelic;
import duelistmod.variables.*;

public class MerchantRugbox extends DuelistRelic implements ShopDupeRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MerchantRugbox");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MerchantRugbox() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MarkExxod.ID)) return false;
		return Util.notHasShopDupeRelic();
	}
	
	@Override
	public void onEquip()
	{
		ArrayList<AbstractCard> spells = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.TRAP)) { spells.add(c); }}		
		for (AbstractCard c : spells) { AbstractDungeon.effectList.add(new FastCardObtainEffect(c.makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f)); }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() 
	{
		return new MerchantRugbox();
	}

	@Override
	public int getPrice()
	{
		return 300;
	}
	
}
