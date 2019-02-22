package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

public class MillenniumCoin extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = defaultmod.DefaultMod.makeID("MillenniumCoin");
	public static final String IMG = DefaultMod.makePath(DefaultMod.M_COIN_RELIC);
	public static final String OUTLINE = DefaultMod.makePath(DefaultMod.M_COIN_RELIC_OUTLINE);

	public MillenniumCoin() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		this.counter = 10;
	}

	@Override
	public void onEvokeOrb(AbstractOrb ammo) 
	{
		flash();
		DuelistCard.gainGold(this.counter, AbstractDungeon.player, true);
		this.counter += 5;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumCoin();
	}
}
