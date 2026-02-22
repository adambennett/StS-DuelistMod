package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;

public class AmuletOfGlory extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("AmuletOfGlory");
    public static final String IMG = DuelistMod.makeRelicPath("AmuletOfGlory.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("AmuletOfGloryOutline.png");

    public AmuletOfGlory() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new DexterityPower(duelist.creature(), 1));
        duelist.applyPowerToSelf(new VigorPower(duelist.creature(), 4));
    }

    @Override
    public boolean canSpawn() {
        boolean superCheck = super.canSpawn();
        if (!superCheck) return false;
        return Util.deckIs("Warrior Deck");
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
