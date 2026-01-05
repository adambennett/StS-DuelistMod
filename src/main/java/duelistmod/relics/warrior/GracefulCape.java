package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.abstracts.FirstStrikeDuelistCard;
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.helpers.Util;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GracefulCape extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("GracefulCape");
    public static final String IMG = DuelistMod.makeRelicPath("GracefulCape.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("GracefulCapeOutline.png");

    public GracefulCape() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.CLINK);
        this.tips.add(new PowerTip("First Strike", DuelistMod.firstStrikeKeywordDescription));
        this.tips = this.tips.stream().filter(t -> !"Strike".equals(t.header)).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean canSpawn() {
        boolean superCheck = super.canSpawn();
        if (!superCheck) return false;
        return Util.deckIs("Warrior Deck");
    }

    @Override
    public void onRevengeTriggered(AnyRevengeCard duelistCard) {
        AnyDuelist duelist = AnyDuelist.from(duelistCard.get());
        onRevengeOrFirstStrikeTriggered(duelist);
    }

    @Override
    public void onFirstStrikeTriggered(FirstStrikeDuelistCard card, AbstractCreature target) {
        AnyDuelist duelist = AnyDuelist.from(card);
        onRevengeOrFirstStrikeTriggered(duelist);
    }

    private void onRevengeOrFirstStrikeTriggered(AnyDuelist duelist) {
        flash();
        duelist.applyPowerToSelf(new DexterityPower(duelist.creature(), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
