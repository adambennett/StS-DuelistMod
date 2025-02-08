package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.variables.Tags;

public class TributeEggRelic extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("TributeEggRelic");
    public static final String IMG = DuelistMod.makeRelicPath("TributeMonsterEggRelic.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Egg_Outline.png");

    public TributeEggRelic() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c instanceof DuelistCard) {
            DuelistCard dc = (DuelistCard) c;
            if (dc.tributes > 1 && dc.hasTag(Tags.MONSTER)) {
                dc.modifyTributesPermanent(-1);
                this.flash();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TributeEggRelic();
    }

}
