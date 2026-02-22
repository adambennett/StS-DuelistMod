package duelistmod.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.powers.ReducerPower;
import duelistmod.variables.Colors;

public class ReducerPotion extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("ReducerPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ReducerPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOLT, PotionEffect.NONE, Colors.TEAL, Colors.TEAL, Colors.BLACK);
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.isThrown = false;
    }

    @Override
    public boolean canUse() {
        return AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT);
    }

    @Override
    public void use(AbstractCreature target) {
        DuelistCard.applyPowerToSelf(new ReducerPower(AbstractDungeon.player, this.potency + 1));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ReducerPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 3;
    }

    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Reductionist", "At the start of turn, reduce the cost of #b" + this.potency + " cards in your hand by #b1. Number of cards decreases at the end of each turn."));
    }

    public void upgradePotion() {
        this.potency += 2;
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Reductionist", "At the start of turn, reduce the cost of #b" + this.potency + " cards in your hand by #b1. Number of cards decreases at the end of each turn."));
    }
}
