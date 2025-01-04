package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;

public class RemoteRevengePower extends NoStackDuelistPower {

	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("RemoteRevengePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("RemoteRevengePower.png");
    private boolean isUpgraded;

	public RemoteRevengePower(AbstractCreature owner, AbstractCreature source) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
		updateDescription();
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[this.isUpgraded ? 1 : 0];
	}

    public void setUpgraded(boolean upgraded) {
        this.isUpgraded = upgraded;
        this.updateDescription();
    }

    @Override
    public void onRevengeTriggered(RevengeCard revengeCard, DuelistCard duelistCard) {
        DuelistMod.triggeringRemoteRevenge = !this.isUpgraded;
        DuelistMod.triggeringRemoteRevengeUpgrade = true;
        revengeCard.triggerRevenge(AnyDuelist.from(duelistCard));
    }

}
