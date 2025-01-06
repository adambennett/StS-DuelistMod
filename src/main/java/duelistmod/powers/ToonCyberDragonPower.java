package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

import java.util.HashMap;
import java.util.UUID;

public class ToonCyberDragonPower extends DuelistPower {

    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToonCyberDragonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private final AnyDuelist duelist;
    private final HashMap<UUID, Integer> reductionMap = new HashMap<>();

    public ToonCyberDragonPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.duelist = AnyDuelist.from(this);
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        reduceMachineCardCosts();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster) {
        if (card.hasTag(Tags.MACHINE)) {
            restoreMachineCardCosts();
            DuelistCard.removePower(this, this.owner);
        }
    }

    @Override
    public void onInitialApplication() {
        reduceMachineCardCosts();
    }

    @Override
    public void onAddCardToHand(AbstractCard card) {
        if (card.hasTag(Tags.MACHINE)) {
            reduceCard(card);
        }
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    private void reduceCard(AbstractCard card) {
        if (card.hasTag(Tags.MACHINE) && card.costForTurn > 0) {
            int originalCostForTurn = card.costForTurn;
            card.setCostForTurn(card.costForTurn - this.amount);
            int finalCostForTurn = card.costForTurn;
            if (finalCostForTurn != originalCostForTurn) {
                this.reductionMap.put(card.uuid, -this.amount);
            }
        }
    }

    private void increaseCard(AbstractCard card) {
        if (this.reductionMap.containsKey(card.uuid)) {
            int reduction = this.reductionMap.get(card.uuid);
            card.setCostForTurn(card.costForTurn + reduction);
        }
    }

    private void reduceMachineCardCosts() {
        this.duelist.hand().stream().filter(c -> c.hasTag(Tags.MACHINE)).forEach(this::reduceCard);
        this.duelist.drawPile().stream().filter(c -> c.hasTag(Tags.MACHINE)).forEach(this::reduceCard);
        this.duelist.discardPile().stream().filter(c -> c.hasTag(Tags.MACHINE)).forEach(this::reduceCard);
        this.duelist.exhaustPile().stream().filter(c -> c.hasTag(Tags.MACHINE)).forEach(this::reduceCard);
        this.duelist.handGroup().glowCheck();
    }

    private void restoreMachineCardCosts() {
        this.duelist.hand().stream().filter(c -> c.hasTag(Tags.MACHINE)).forEach(this::increaseCard);
        this.duelist.drawPile().stream().filter(c -> c.hasTag(Tags.MACHINE)).forEach(this::increaseCard);
        this.duelist.discardPile().stream().filter(c -> c.hasTag(Tags.MACHINE)).forEach(this::increaseCard);
        this.duelist.exhaustPile().stream().filter(c -> c.hasTag(Tags.MACHINE)).forEach(this::increaseCard);
        this.reductionMap.clear();
        this.duelist.handGroup().glowCheck();
    }

}
