package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.FirstStrikeDuelistCard;
import duelistmod.dto.AnyDuelist;

public class EgoBoostPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("EgoBoostPower");
    public static final String IMG = DuelistMod.makePowerPath("IlBludPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public EgoBoostPower(AbstractCreature owner, int triggers) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.duelist = AnyDuelist.from(owner);
        this.amount = triggers;
        this.updateDescription();
    }

    @Override
    public void onFirstStrikeTriggered(FirstStrikeDuelistCard card, AbstractCreature target) {
        boolean hasDownbeat = this.duelist.hasPower(DownbeatPower.POWER_ID) && this.duelist.getPower(DownbeatPower.POWER_ID).amount > 0;
        boolean hasCombo = this.duelist.hasPower(CombinationAttackPower.POWER_ID) && this.duelist.getPower(CombinationAttackPower.POWER_ID).amount > 0;
        if (hasDownbeat || hasCombo) {
            this.addToBot(new RemoveSpecificPowerAction(duelist.creature(), duelist.creature(), this));
            return;
        }

        DuelistMod.triggeringEgoBoostFirstStrikeEffect = true;
        for (int i = 0; i < this.amount; i++) {
            card.triggerFirstStrike(card, this.duelist, target);
        }
        DuelistMod.triggeringEgoBoostFirstStrikeEffect = false;
        this.addToBot(new RemoveSpecificPowerAction(duelist.creature(), duelist.creature(), this));
    }

    @Override
    public void updateDescription() {
        String s = this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        this.description = DESCRIPTIONS[0] + this.amount + s;
    }
}
