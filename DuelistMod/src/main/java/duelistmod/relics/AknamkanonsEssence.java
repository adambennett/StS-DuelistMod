package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.TombLooterPower;
import duelistmod.variables.Strings;

public class AknamkanonsEssence extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("AknamkanonsEssence");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	
	public AknamkanonsEssence() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
		setDescription();
	}
	
	@Override
	public void atBattleStart()
	{
		DuelistCard.applyPowerToSelf(new TombLooterPower(AbstractDungeon.player, 10, 30, true));
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
        tips.add(new PowerTip("Tomb Looter", this.DESCRIPTIONS[1]));
        initializeTips();
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new AknamkanonsEssence();
	}
}
