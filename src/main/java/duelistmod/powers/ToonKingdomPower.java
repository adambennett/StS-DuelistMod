package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.actions.unique.ToonKingdomPowerAction;
import duelistmod.cards.pools.toon.ToonKingdom;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;
import java.util.HashMap;
import java.util.UUID;

public class ToonKingdomPower extends NoStackDuelistPower {

    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToonKingdomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.TOON_WORLD_POWER);
    private final AnyDuelist duelist;
    private boolean effectUsed = false;
    private final ToonKingdom appliedBy;
    private final HashMap<UUID, Integer> reductionMap = new HashMap<>();
    
    public ToonKingdomPower(final AbstractCreature owner, final AbstractCreature source, ToonKingdom appliedBy) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.duelist = AnyDuelist.from(this);
        this.appliedBy = appliedBy;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.effectUsed = false;
        // Reduce
        this.addToBot(new ToonKingdomPowerAction(this.duelist, this.appliedBy, true, this.reductionMap));
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!this.effectUsed && card.hasTag(Tags.TOON) && !card.uuid.equals(this.appliedBy.uuid)) {
            this.effectUsed = true;
            // Increase
            this.addToBot(new ToonKingdomPowerAction(this.duelist, this.appliedBy, false, this.reductionMap));
        }
    }

    @Override
	public void atEndOfTurn(final boolean isPlayer) {
        // Increase
        this.addToBot(new ToonKingdomPowerAction(this.duelist, this.appliedBy, false, this.reductionMap));
	}

    @Override
    public void onInitialApplication() {
        if (this.duelist.getCardsPlayedThisTurn().stream().noneMatch(c -> c.hasTag(Tags.TOON) && !c.uuid.equals(this.appliedBy.uuid))) {
            // Reduce
            this.addToBot(new ToonKingdomPowerAction(this.duelist, this.appliedBy, true, this.reductionMap));
        }
    }

    @Override
    public void onAddCardToHand(AbstractCard card) {
        if (!this.effectUsed && card.hasTag(Tags.TOON)) {
            // Reduce
            this.addToBot(new ToonKingdomPowerAction(this.duelist, this.appliedBy, true, this.reductionMap, card));
        }
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}
