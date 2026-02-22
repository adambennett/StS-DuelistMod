package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.FirstStrikeDuelistCard;
import duelistmod.dto.AnyDuelist;

public class DownbeatPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("DownbeatPower");
    private static final String IMG = DuelistMod.makePowerPath("DownbeatPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public DownbeatPower(final AbstractCreature owner, int additionalTriggers) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.duelist = AnyDuelist.from(owner);
        this.amount = additionalTriggers;
        this.updateDescription();
    }

    @Override
    public void onFirstStrikeTriggered(FirstStrikeDuelistCard card, AbstractCreature target) {
        DuelistMod.triggeringDownbeatFirstStrikeEffect = true;
        int triggers = this.amount;
        if (this.duelist.hasPower(CombinationAttackPower.POWER_ID)) {
            triggers += this.duelist.getPower(CombinationAttackPower.POWER_ID).amount;
        }
        if (this.duelist.hasPower(EgoBoostPower.POWER_ID)) {
            triggers += this.duelist.getPower(EgoBoostPower.POWER_ID).amount;
        }
        for (int i = 0; i < triggers; i++) {
            card.triggerFirstStrike(card, this.duelist, target);
        }
        DuelistMod.triggeringDownbeatFirstStrikeEffect = false;
    }

    @Override
    public void updateDescription() {
        String s = this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        this.description = DESCRIPTIONS[0] + this.amount + s;
    }
}
