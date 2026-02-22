package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class HarmonicWavesPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("HarmonicWavesPower");
    private static final PowerStrings powerStrings =  CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG = DuelistMod.makePowerPath("PWavePower.png");
    private final AnyDuelist duelist;

    public HarmonicWavesPower(final AbstractCreature owner, final int vigorGain) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = vigorGain;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.img = new Texture(IMG);
        this.duelist = AnyDuelist.from(owner);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.duelist.applyPowerToSelf(new VigorPower(this.owner, this.amount));
        this.duelist.removePowerFromSelf(this);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
