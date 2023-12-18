package duelistmod.powers.duelistPowers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class DuelistDrawCardNextTurnPower extends DuelistPower {
    public static final String POWER_ID = DuelistMod.makeID("DuelistDrawCardNextTurnPower");
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private final AnyDuelist duelist;

    public DuelistDrawCardNextTurnPower(AbstractCreature owner, int drawAmount) {
        this.name = NAME;
        this.ID = "Draw Card";
        this.owner = owner;
        this.duelist = AnyDuelist.from(this);
        this.amount = drawAmount;
        this.updateDescription();
        this.loadRegion("carddraw");
        this.priority = 20;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[this.amount > 1 ? 1 : 2];
    }

    public void atStartOfTurnPostDraw() {
        this.flash();
        this.duelist.draw(this.amount);
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Draw Card"));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Draw Card");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
