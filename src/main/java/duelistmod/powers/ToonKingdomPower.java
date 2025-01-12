package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;
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
    private final HashMap<UUID, Integer> reductionMap = new HashMap<>();
    
    public ToonKingdomPower(final AbstractCreature owner, final AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.duelist = AnyDuelist.from(this);
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.effectUsed = false;
        reduceToonCardCosts();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster) {
        if (!this.effectUsed && card.hasTag(Tags.TOON)) {
            this.effectUsed = true;
            restoreToonCardCosts();
        }
    }

    @Override
	public void atEndOfTurn(final boolean isPlayer) {
        restoreToonCardCosts();
		updateDescription();    	
	}

    @Override
    public void onInitialApplication() {
        if (this.duelist.getCardsPlayedThisTurn().stream().noneMatch(c -> c.hasTag(Tags.TOON))) {
            reduceToonCardCosts();
        }
    }

    @Override
    public void onAddCardToHand(AbstractCard card) {
        if (!this.effectUsed && card.hasTag(Tags.TOON)) {
            reduceCard(card);
        }
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    private void reduceCard(AbstractCard card) {
        if (card.hasTag(Tags.TOON) && card.costForTurn > 0) {
            card.setCostForTurn(card.costForTurn - 1);
            this.reductionMap.put(card.uuid, 1);
        }
    }

    private void increaseCard(AbstractCard card) {
        if (this.reductionMap.containsKey(card.uuid)) {
            int reduction = this.reductionMap.get(card.uuid);
            card.setCostForTurn(card.costForTurn + reduction);
        }
    }

    private void reduceToonCardCosts() {
        this.duelist.hand().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::reduceCard);
        this.duelist.drawPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::reduceCard);
        this.duelist.discardPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::reduceCard);
        this.duelist.exhaustPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::reduceCard);
        this.duelist.handGroup().glowCheck();
    }

    private void restoreToonCardCosts() {
        this.duelist.hand().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::increaseCard);
        this.duelist.drawPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::increaseCard);
        this.duelist.discardPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::increaseCard);
        this.duelist.exhaustPile().stream().filter(c -> c.hasTag(Tags.TOON)).forEach(this::increaseCard);
        this.reductionMap.clear();
        this.duelist.handGroup().glowCheck();
    }

}
