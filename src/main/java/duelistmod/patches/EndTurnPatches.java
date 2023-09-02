package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.MonsterStartTurnAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(clz = AbstractRoom.class, method = "endTurn")
public class EndTurnPatches
{
    public static SpireReturn<Void> Prefix(AbstractRoom __instance) {
        if (!AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
            return SpireReturn.Continue();
        }

        AbstractDungeon.player.applyEndOfTurnTriggers();
        AbstractDungeon.actionManager.addToBottom(new ClearCardQueueAction());
        AbstractDungeon.actionManager.addToBottom(new DiscardAtEndOfTurnAction());
        for (final AbstractCard c : AbstractDungeon.player.drawPile.group) {
            c.resetAttributes();
        }
        for (final AbstractCard c : AbstractDungeon.player.discardPile.group) {
            c.resetAttributes();
        }
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            c.resetAttributes();
        }
        if (AbstractDungeon.player.hoveredCard != null) {
            AbstractDungeon.player.hoveredCard.resetAttributes();
        }
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                this.addToBot(new EndTurnAction());
                this.addToBot(new WaitAction(1.2f));
                if (!__instance.skipMonsterTurn) {
                    this.addToBot(new MonsterStartTurnAction());
                }
                AbstractDungeon.actionManager.monsterAttacksQueued = false;
                this.isDone = true;
            }
        });
        AbstractDungeon.player.isEndingTurn = false;

        return SpireReturn.Return();
    }
}
