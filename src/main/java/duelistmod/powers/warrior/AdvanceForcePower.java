package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class AdvanceForcePower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("AdvanceForcePower");
    public static final String IMG = DuelistMod.makePowerPath("AdvanceForcePower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public AdvanceForcePower(AbstractCreature owner) {
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
    public void onInitialApplication() {
        this.zeroizeHand();
    }

    private void zeroizeHand() {
        if (this.duelist == null || this.duelist.hand() == null) return;
        for (AbstractCard c : this.duelist.hand()) {
            if (c.hasTag(Tags.WARRIOR)) {
                c.setCostForTurn(0);
            }
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card != null && card.hasTag(Tags.WARRIOR)) {
            card.setCostForTurn(0);
        }
    }

    @Override
    public void onAddCardToHand(AbstractCard c) {
        if (c.hasTag(Tags.WARRIOR)) {
            reduceCard(c);
        }
    }

    private void reduceCard(AbstractCard card) {
        if (card.costForTurn > 0) {
            card.setCostForTurn(0);
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        DuelistCard.removePower(this, this.duelist.creature());
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
