package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class TriBrigadeBarrenBlossomPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("TriBrigadeBarrenBlossomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("TriBrigadeFerrijitPower.png");
    private final AnyDuelist duelist;

	public TriBrigadeBarrenBlossomPower(AbstractCreature owner, AbstractCreature source, int times, int cardsToFetch) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = times;
        this.amount2 = cardsToFetch;
		updateDescription(); 
	}

    public void trigger() {
        if (this.amount > 0 && this.amount2 > 0) {
            this.amount--;
            CardGroup beasts = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            duelist.drawPile().stream().filter(c -> c.hasTag(Tags.BEAST)).forEach(beasts::addToBottom);
            DuelistCard.fetch(this.amount2, beasts, false);
        }
        if (this.amount < 1) {
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractPower instance = duelist.getPower(TriBrigadeBarrenBlossomPower.POWER_ID);
            DuelistCard.removePower(instance, duelist.creature());
        }
        this.updateDescription();
    }

	@Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        this.description += this.amount;
        this.description += this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        this.description += DESCRIPTIONS[3];
        this.description += this.amount2;
        this.description += this.amount2 == 1 ? DESCRIPTIONS[4] : DESCRIPTIONS[5];
        this.description += DESCRIPTIONS[6];
    }
}
