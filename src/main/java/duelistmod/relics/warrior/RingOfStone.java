package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyGuardedCard;
import duelistmod.helpers.Util;

import java.util.List;

public class RingOfStone extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("RingOfStone");
    public static final String IMG = DuelistMod.makeRelicPath("RingOfStone.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("RingOfStoneOutline.png");

    private boolean usedThisCombat = false;

    public RingOfStone() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        boolean superCheck = super.canSpawn();
        if (!superCheck) return false;
        return Util.deckIs("Warrior Deck");
    }

    @Override
    public void atPreBattle() {
        this.usedThisCombat = false;
        this.grayscale = false;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public int modifyGuardedRequirement(int requiredBlock) {
        if (this.usedThisCombat) {
            return requiredBlock;
        }
        return Math.max(0, requiredBlock - 5);
    }

    @Override
    public void onGuardedTrigger(AnyGuardedCard caller, List<AbstractCreature> targets) {
        if (!this.usedThisCombat) {
            this.usedThisCombat = true;
            this.grayscale = true;
            flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
