package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class BrilliantSparkPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("BrilliantSparkPower");
    public static final String IMG = DuelistMod.makePowerPath("OniGamiComboPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public BrilliantSparkPower(AbstractCreature owner, int vigorGain) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.duelist = AnyDuelist.from(owner);
        this.amount = vigorGain;
        this.updateDescription();
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        int currentBlock = this.duelist.currentBlock();
        if (blockAmount > 0f && blockAmount > currentBlock) {
            this.duelist.applyPowerToSelf(new VigorPower(this.owner, this.amount));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
