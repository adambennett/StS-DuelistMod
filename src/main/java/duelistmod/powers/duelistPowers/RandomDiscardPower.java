package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.actions.enemyDuelist.DuelistDiscardSpecificCardAction;
import duelistmod.dto.AnyDuelist;

public class RandomDiscardPower extends DuelistPower {

	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("RandomDiscardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("RandomDiscardPower.png");
    private final AnyDuelist duelist;

	public RandomDiscardPower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
        this.duelist = AnyDuelist.from(this);
		updateDescription();
	}

    @Override
    public void atStartOfTurnPostDraw() {
        if (this.amount < 1) {
            DuelistCard.removePower(this, this.owner);
            return;
        }
        if (this.duelist.hand().isEmpty()) return;

        AbstractCard randomCard = this.duelist.handGroup().getRandomCard(true);
        this.addToBot(new DuelistDiscardSpecificCardAction(randomCard, this.duelist));
        this.amount--;
        if (this.amount < 1) {
            DuelistCard.removePower(this, this.owner);
            return;
        }
        this.updateDescription();
    }

	@Override
	public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
	}

}
