package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class WarriorRing extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("WarriorRing");
    public static final String IMG = DuelistMod.makeRelicPath("WarriorRing.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("WarriorRingOutline.png");

    public WarriorRing() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public boolean canSpawn() {
        boolean superCheck = super.canSpawn();
        if (!superCheck) return false;
        return Util.deckIs("Warrior Deck");
    }

    @Override
    public void atBattleStart() {
        flash();
        AnyDuelist.from(this).drawTag(1, Tags.WARRIOR);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
