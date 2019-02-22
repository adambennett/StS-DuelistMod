package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

public class MillenniumKey extends CustomRelic 
{
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("MillenniumKey");
    public static final String IMG = DefaultMod.makePath(DefaultMod.M_KEY_RELIC);
    public static final String OUTLINE = DefaultMod.makePath(DefaultMod.M_KEY_RELIC_OUTLINE);

    public MillenniumKey() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() 
    {
    	flash();
    	DuelistCard.setMaxSummons(AbstractDungeon.player, 4);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new MillenniumKey();
    }
}
