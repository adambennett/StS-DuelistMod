package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.powers.BlurPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;

public class ObsidianCape extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("ObsidianCape");
    public static final String IMG = DuelistMod.makeRelicPath("ObsidianCape.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("ObsidianCapeOutline.png");

    public ObsidianCape() {
        super(ID,  new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        boolean superCheck = super.canSpawn();
        if (!superCheck) return false;
        return Util.deckIs("Warrior Deck");
    }

    @Override
    public void atTurnStart() {
        AnyDuelist duelist = AnyDuelist.from(this);
        if (GameActionManager.turn == 3) {
            this.flash();
            duelist.applyPowerToSelf(new BlurPower(duelist.creature(), 2));
            this.grayscale = true;
        }
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
