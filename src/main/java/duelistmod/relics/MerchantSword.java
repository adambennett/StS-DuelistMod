package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.variables.*;

public class MerchantSword extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MerchantSword");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MerchantSword() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public boolean canSpawn()
	{
		if (DuelistMod.hasShopDupeRelic) { return false; }
		else { return true; }
	}
	
	@Override
	public void onEquip()
	{
		ArrayList<AbstractCard> spells = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.MONSTER)) { spells.add(c); }}		
		for (AbstractCard c : spells) { AbstractDungeon.effectList.add(new FastCardObtainEffect(c.makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f)); }
		DuelistMod.hasShopDupeRelic = true;
		try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setBool(DuelistMod.PROP_SHOP_DUPE_RELIC, DuelistMod.hasShopDupeRelic);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
	}
	 
    @Override
    public void onUnequip()
    {
        DuelistMod.hasShopDupeRelic = false;        
        try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setBool(DuelistMod.PROP_SHOP_DUPE_RELIC, DuelistMod.hasShopDupeRelic);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
    }

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() 
	{
		return new MerchantSword();
	}

	@Override
	public int getPrice()
	{
		return 400;
	}
	
}
