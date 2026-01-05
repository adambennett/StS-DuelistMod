package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class CubicKarmaPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("CubicKarmaPower");
    private static final String IMG = DuelistMod.makePowerPath("CubicKarmaPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;
    private boolean triggeredThisTurn = false;

    public CubicKarmaPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.duelist = AnyDuelist.from(owner);
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.triggeredThisTurn = false;
    }

    public boolean canTrigger() {
        return !this.triggeredThisTurn;
    }

    public void disableTrigger() {
        this.triggeredThisTurn = true;
    }

    @Override
    public void onAfterCardPlayed(AbstractCard c) {
        if (c.type == AbstractCard.CardType.ATTACK && canTrigger()) {
            disableTrigger();
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
