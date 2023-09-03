package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.StrengthUpPower;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class HornOfThePhantomBeastPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("HornOfThePhantomBeastPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");


	public HornOfThePhantomBeastPower(AbstractCreature owner, AbstractCreature source, int strGain) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = strGain;
		updateDescription(); 
	}

    @Override
    public void atStartOfTurnPostDraw() {
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.hasPower(SummonPower.POWER_ID)) {
            SummonPower pow = (SummonPower)duelist.getPower(SummonPower.POWER_ID);
            int beasts = (int) pow.getCardsSummoned().stream().filter(c -> c.hasTag(Tags.BEAST)).count();
            if (beasts > 0) {
                duelist.applyPowerToSelf(new StrengthUpPower(duelist.creature(), duelist.creature(), this.amount * beasts));
            }
        }

    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
