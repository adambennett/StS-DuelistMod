package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import duelistmod.cards.pools.beast.TheBigMarchOfAnimals;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class TheBigMarchOfAnimalsAction extends AbstractGameAction {
    private final float startingDuration;
    private final int dexterityGain;
    private final AnyDuelist duelist;

    public TheBigMarchOfAnimalsAction(TheBigMarchOfAnimals caller) {
        this.amount = caller.secondMagic;
        this.dexterityGain = caller.magicNumber;
        this.duelist = AnyDuelist.from(caller);
        if (this.duelist.hasRelic("GoldenEye")) {
            this.duelist.getRelic("GoldenEye").flash();
            this.amount += 2;
        }
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        if (this.duration == this.startingDuration) {
            for (final AbstractPower p : this.duelist.powers()) {
                p.onScry();
            }
            if (this.duelist.drawPile().isEmpty()) {
                this.isDone = true;
                return;
            }
            final CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (this.amount != -1) {
                for (int i = 0; i < Math.min(this.amount, this.duelist.drawPile().size()); ++i) {
                    tmpGroup.addToTop(this.duelist.drawPile().get(this.duelist.drawPile().size() - i - 1));
                }
            }
            else {
                for (final AbstractCard c : this.duelist.drawPile()) {
                    tmpGroup.addToBottom(c);
                }
            }
            AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, ScryAction.TEXT[0]);
        }
        else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (final AbstractCard c2 : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.duelist.drawPileGroup().moveToDiscardPile(c2);
                if (c2.hasTag(Tags.BEAST)) {
                    this.duelist.applyPowerToSelf(new DexterityPower(duelist.creature(), this.dexterityGain));
                    this.duelist.applyPowerToSelf(new StrengthPower(duelist.creature(), this.dexterityGain));
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        for (final AbstractCard c2 : this.duelist.discardPile()) {
            c2.triggerOnScry();
        }
        this.tickDuration();
    }
}
