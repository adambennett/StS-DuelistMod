package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class RingOfEndurance extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("RingOfEndurance");
    public static final String IMG = DuelistMod.makeRelicPath("RingOfEndurance.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("RingOfEnduranceOutline.png");

    public RingOfEndurance() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        boolean superCheck = super.canSpawn();
        if (!superCheck) return false;
        return Util.deckIs("Warrior Deck");
    }

    @Override
    public void onPlayerEndTurn() {
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.creature().currentBlock <= 0) return;

        ArrayList<AbstractCard> warriorsInHand = new ArrayList<>();
        for (AbstractCard c : duelist.hand()) {
            if (c.hasTag(Tags.WARRIOR)) {
                warriorsInHand.add(c);
            }
        }

        if (warriorsInHand.isEmpty()) return;

        this.flash();
        AbstractCard chosen = warriorsInHand.get(AbstractDungeon.cardRandomRng.random(warriorsInHand.size() - 1));
        chosen.retain = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
