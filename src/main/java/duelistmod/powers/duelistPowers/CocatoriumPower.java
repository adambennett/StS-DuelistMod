package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.actions.unique.CocatoriumAction;
import duelistmod.dto.AnyDuelist;

public class CocatoriumPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("CocatoriumPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private boolean damageBoostActive = false;

	public CocatoriumPower(AbstractCreature owner, AbstractCreature source, int beastsCheck) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = beastsCheck;
		updateDescription(); 
	}

    @Override
    public void atStartOfTurnPostDraw() {
        AnyDuelist duelist = AnyDuelist.from(this);
        this.addToBot(new CocatoriumAction(duelist, this));
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.damageBoostActive = false;
            this.updateDescription();
        }
    }

	@Override
	public void updateDescription() {
        if (this.damageBoostActive) {
            this.description = "Double the damage of the next Attack you play this turn.";
            return;
        }
		this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]) + DESCRIPTIONS[3];
	}

    public boolean isDamageBoostActive() {
        return damageBoostActive;
    }

    public void setDamageBoostActive(boolean damageBoostActive) {
        this.damageBoostActive = damageBoostActive;
    }
}