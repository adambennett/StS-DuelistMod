package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyGuardedCard;

import java.util.List;

public class BattleguardHowlingPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("BattleguardHowlingPower");
    public static final String IMG = DuelistMod.makePowerPath("BoosterDragonPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public BattleguardHowlingPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.duelist = AnyDuelist.from(owner);
        this.updateDescription();
    }

    @Override
    public void onGuardedTrigger(AnyGuardedCard caller, List<AbstractCreature> targets) {
        if (caller.get().costForTurn > 0) {
            this.duelist.applyPowerToSelf(new StrengthPower(duelist.creature(), caller.get().costForTurn));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
