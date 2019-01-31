package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.powers.SummonPower;

public class MillenniumPuzzle extends CustomRelic {
    
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * 
     * Summon 1 on combat start
     */

    // ID, images, text.
    public static final String ID = defaultmod.DefaultMod.makeID("MillenniumPuzzle");
    public static final String IMG = DefaultMod.makePath(DefaultMod.PLACEHOLDER_RELIC);
    public static final String OUTLINE = DefaultMod.makePath(DefaultMod.PLACEHOLDER_RELIC_OUTLINE);
    private int SUMMONS = 1;

    public MillenniumPuzzle() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Summon 1 on turn start
    @Override
    public void atBattleStart() 
    {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS), SUMMONS));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new MillenniumPuzzle();
    }
}
