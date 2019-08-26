package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.variables.Strings;

public class DuelistCoin extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("DuelistCoin");
	public static final String IMG = DuelistMod.makePath(Strings.M_COIN_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_COIN_RELIC_OUTLINE);


	
	public DuelistCoin() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public void onEquip()
	{
		for (AbstractRelic r : AbstractDungeon.player.relics)
		{
			if (!r.name.equals(this.name))
			{
				AbstractDungeon.player.gainGold(30);
			}
		}
	}
	
	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new DuelistCoin();
	}
}
