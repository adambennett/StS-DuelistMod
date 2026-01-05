package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.DynamicDamageRevengeCard;
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.powers.warrior.CombinationAttackPower;

public class RemoteRevengePower extends DuelistPower {

	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("RemoteRevengePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("RemoteRevengePower.png");

	public RemoteRevengePower(AbstractCreature owner, AbstractCreature source, int extraTriggers) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = extraTriggers;
		updateDescription();
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[this.amount == 1 ? 1 : 2];
	}

    @Override
    public void onRevengeTriggered(AnyRevengeCard card) {
        AnyDuelist duelist = AnyDuelist.from(card.get());
        DuelistMod.triggeringRemoteRevengeEffect = true;
        int triggers = this.amount;
        if (duelist.hasPower(CombinationAttackPower.POWER_ID)) {
            triggers += duelist.getPower(CombinationAttackPower.POWER_ID).amount;
        }
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
                rdc.triggerRevenge(duelist);
            } else if (ddrc != null) {
                ddrc.triggerRevenge(duelist);
            }
        }
        DuelistMod.triggeringRemoteRevengeEffect = false;
    }

}
