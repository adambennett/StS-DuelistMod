package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.dto.AnyDuelist;

import java.util.ArrayList;

public class HeroRingAction extends AbstractGameAction {

    private final AnyDuelist duelist;

    public HeroRingAction(AnyDuelist duelist, int cardsToShuffle) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = cardsToShuffle;
        this.duelist = duelist;
    }

    @Override
    public void update() {
        if (this.duelist == null || this.amount <= 0) {
            this.isDone = true;
            return;
        }

        CardGroup discard = duelist.discardPileGroup();
        CardGroup draw = duelist.drawPileGroup();
        if (discard == null || draw == null || discard.isEmpty()) {
            this.isDone = true;
            return;
        }

        ArrayList<AbstractCard> skills = new ArrayList<>();
        for (AbstractCard c : discard.group) {
            if (c.type == AbstractCard.CardType.SKILL) {
                skills.add(c);
            }
        }

        if (skills.isEmpty()) {
            this.isDone = true;
            return;
        }

        int moves = Math.min(this.amount, skills.size());
        for (int i = 0; i < moves; i++) {
            int idx = AbstractDungeon.cardRandomRng.random(skills.size() - 1);
            AbstractCard chosen = skills.remove(idx);

            discard.removeCard(chosen);
            draw.addToRandomSpot(chosen);

            chosen.unhover();
            chosen.stopGlowing();
            chosen.setAngle(0.0f);
        }
        this.isDone = true;
    }
}
