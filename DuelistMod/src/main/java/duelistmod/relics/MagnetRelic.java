package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.variables.Strings;

public class MagnetRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MagnetRelic");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MagnetRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
    public void onEquip()
    {
		int upgrades = 0;
		ArrayList<AbstractCard> magnets = new ArrayList<AbstractCard>();
		magnets.add(new AlphaMagnet());
		magnets.add(new BetaMagnet());
		magnets.add(new GammaMagnet());
		magnets.add(new ValkMagnet());
		for (AbstractCard c : magnets)
		{
			while (upgradeChecker(c, upgrades)) { c.upgrade(); upgrades++; }
			AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
		}
		
		AbstractDungeon.player.decreaseMaxHealth(10);
    }
	
	public boolean upgradeChecker(AbstractCard c, int upgrades)
	{
		if (upgrades > 1) { return false; }
		else if (c instanceof ValkMagnet) { return false; }
		else 
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, 3 + upgrades);
			if (roll == 1) { return true; }
			else { return false; }
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
		return new MagnetRelic();
	}
	
	@Override
	public int getPrice()
	{
		return 400;
	}
	
}
