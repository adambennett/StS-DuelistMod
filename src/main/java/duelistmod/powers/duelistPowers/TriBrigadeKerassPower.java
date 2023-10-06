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

public class TriBrigadeKerassPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("TriBrigadeKerassPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("TriBrigadeKerassPower.png");
    private final AnyDuelist duelist;

	public TriBrigadeKerassPower(AbstractCreature owner, AbstractCreature source, int turns, int tempHP) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = turns;
        this.amount2 = tempHP;
		updateDescription(); 
	}

    public void trigger() {
        if (this.amount > 0 && this.amount2 > 0) {
            this.amount--;
            DuelistCard.gainTempHP(duelist.creature(), duelist.creature(), this.amount2);
        }
        if (this.amount < 1) {
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractPower instance = duelist.getPower(TriBrigadeKerassPower.POWER_ID);
            DuelistCard.removePower(instance, duelist.creature());
        }
        this.updateDescription();
    }
	
	@Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        this.description += this.amount;
        this.description += this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        this.description += DESCRIPTIONS[3];
        this.description += this.amount2;
        this.description += DESCRIPTIONS[4];
	}
}
