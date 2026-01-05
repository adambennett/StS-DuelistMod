package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.DynamicDamageRevengeCard;
import duelistmod.abstracts.FirstStrikeDuelistCard;
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.powers.duelistPowers.RemoteRevengePower;

public class CombinationAttackPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("CombinationAttackPower");
    private static final String IMG = DuelistMod.makePowerPath("CommanderSwordsPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public CombinationAttackPower(final AbstractCreature owner, int triggers) {
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
    public void onRevengeTriggered(AnyRevengeCard card) {
        if (this.duelist.hasPower(RemoteRevengePower.POWER_ID) && this.duelist.getPower(RemoteRevengePower.POWER_ID).amount > 0) return;

        DuelistMod.triggeringCombinationAttackRevengeEffect = true;
        int triggers = this.amount;
        RevengeDuelistCard rdc = null;
        DynamicDamageRevengeCard ddrc = null;
        Object c = card.getCard();
        if (c instanceof RevengeDuelistCard) {
            rdc = (RevengeDuelistCard) c;
        } else if (c instanceof DynamicDamageRevengeCard) {
            ddrc = (DynamicDamageRevengeCard) c;
        }
        for (int i = 0; i < triggers; i++) {
            if (rdc != null) {
                rdc.triggerRevenge(this.duelist);
            } else if (ddrc != null) {
                ddrc.triggerRevenge(this.duelist);
            }
        }
        DuelistMod.triggeringCombinationAttackRevengeEffect = false;
    }

    @Override
    public void onFirstStrikeTriggered(FirstStrikeDuelistCard card, AbstractCreature target) {
        if (this.duelist.hasPower(DownbeatPower.POWER_ID) && this.duelist.getPower(DownbeatPower.POWER_ID).amount > 0) return;

        DuelistMod.triggeringCombinationAttackFirstStrikeEffect = true;
        int triggers = this.amount;
        if (this.duelist.hasPower(EgoBoostPower.POWER_ID)) {
            triggers += this.duelist.getPower(EgoBoostPower.POWER_ID).amount;
        }
        for (int i = 0; i < triggers; i++) {
            card.triggerFirstStrike(card, this.duelist, target);
        }
        DuelistMod.triggeringCombinationAttackFirstStrikeEffect = false;
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        this.addToBot(new RemoveSpecificPowerAction(duelist.creature(), duelist.creature(), this));
    }

    @Override
    public void updateDescription() {
        String s = this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        this.description = DESCRIPTIONS[0] + this.amount + s + DESCRIPTIONS[3];
    }
}
