package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class ComicHandPower extends DuelistPower {

    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ComicHandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private final AnyDuelist duelist;

    public ComicHandPower(final AbstractCreature owner, final AbstractCreature source, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.duelist = AnyDuelist.from(this);
        this.amount = turns;
        this.updateDescription();
    }

    @Override
	public void atEndOfTurn(final boolean isPlayer) {
        this.amount--;
        if (this.amount <= 0) {
            DuelistCard.removePower(this, this.duelist.creature());
            return;
        }
        this.updateDescription();
	}

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[this.amount == 1 ? 1 : 2] + DESCRIPTIONS[3];
    }
}
