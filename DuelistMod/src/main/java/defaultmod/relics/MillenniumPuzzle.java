package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

public class MillenniumPuzzle extends CustomRelic {
    
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * 
     * Summon 1 on combat start
     */

    // ID, images, text.
    public static final String ID = defaultmod.DefaultMod.makeID("MillenniumPuzzle");
    public static final String IMG = DefaultMod.makePath(DefaultMod.M_PUZZLE_RELC);
    public static final String OUTLINE = DefaultMod.makePath(DefaultMod.M_PUZZLE_RELIC_OUTLINE);
    //private int SUMMONS = 1;

    public MillenniumPuzzle() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Summon 1 on turn start
    @Override
    public void atBattleStart() 
    {
        this.flash();
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, "Puzzle Token", "#b1 monster summoned. Maximum of 5 Summons."), SUMMONS));
        DuelistCard.powerSummon(AbstractDungeon.player, 2, "Puzzle Token");
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
