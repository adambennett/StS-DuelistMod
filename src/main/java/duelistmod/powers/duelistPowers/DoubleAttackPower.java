package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.NoStackDuelistPower;

public class DoubleAttackPower extends NoStackDuelistPower {

	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("DoubleAttackPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public DoubleAttackPower(AbstractCreature owner, AbstractCreature source) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.loadRegion("retain");
        this.source = source;
		updateDescription();
	}

    public void removeAfterRetain() {
        DuelistCard.removePower(this, this.owner);
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}

}
