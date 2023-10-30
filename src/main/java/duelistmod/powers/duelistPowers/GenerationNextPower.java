package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class GenerationNextPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("GenerationNextPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private final AnyDuelist duelist;

	public GenerationNextPower(AbstractCreature owner, AbstractCreature source, int turns) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = turns;
        this.duelist = AnyDuelist.from(this);
		updateDescription(); 
	}

    @Override
    public void atStartOfTurn() {
        if (this.amount < 1) {
            DuelistCard.removePower(this, this.owner);
            if (duelist.player()) {
                duelist.applyPowerToSelf(new IntangiblePlayerPower(duelist.creature(), 1));
            } else if (duelist.getEnemy() != null) {
                duelist.applyPowerToSelf(new IntangiblePower(duelist.creature(), 1));
            }
        }
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (this.amount > 0) {
            this.amount--;
            updateDescription();
        }
    }

	@Override
	public void updateDescription() {
        if (this.amount == 0) {
            this.description = DESCRIPTIONS[3];
            return;
        }
		this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
	}
}
