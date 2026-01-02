package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class SogenPower extends DuelistPower {

    public AbstractCreature source;
    public static final String POWER_ID = duelistmod.DuelistMod.makeID("SogenPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("SogenPower.png");
    private final AnyDuelist duelist;

    public SogenPower(AnyDuelist duelist, int dmgMod) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = duelist.creature();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = duelist.creature();
        this.amount = dmgMod;
        this.duelist = duelist;
        this.updateDescription();
    }

    public AnyDuelist getDuelist() {
        return this.duelist;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (this.amount * 10) + DESCRIPTIONS[1];
    }
}
