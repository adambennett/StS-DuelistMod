package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class BeastBattlefieldBarrierPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("BeastBattlefieldBarrierPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");

	public BeastBattlefieldBarrierPower(AbstractCreature owner, AbstractCreature source, int turns) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = turns;
		updateDescription(); 
	}

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        this.amount--;
        if (this.amount < 1) {
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractPower instance = duelist.getPower(BeastBattlefieldBarrierPower.POWER_ID);
            DuelistCard.removePower(instance, duelist.creature());
        }
    }
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
	}
}
