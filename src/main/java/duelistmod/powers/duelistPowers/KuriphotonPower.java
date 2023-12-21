package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class KuriphotonPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("KuriphotonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private final AnyDuelist duelist;

	public KuriphotonPower(AbstractCreature owner, AbstractCreature source, int electricGain) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = electricGain;
        this.duelist = AnyDuelist.from(this);
		updateDescription(); 
	}

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.hasTag(Tags.KURIBOH)) {
            this.duelist.applyPowerToSelf(new ElectricityPower(duelist.creature(), duelist.creature(), this.amount));
        }
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        DuelistCard.removePower(this, this.duelist.creature());
    }
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
