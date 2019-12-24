package duelistmod.patches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import duelistmod.actions.unique.DuelistEndOfTurnDiscardAction;


@SpirePatch(
		clz = AbstractRoom.class,
		method = "endTurn"
		)

public class VampireRetainPatch 
{
	public static void Replace(AbstractRoom __instance)
	{
		AbstractDungeon.player.applyEndOfTurnTriggers();
        AbstractDungeon.actionManager.addToBottom(new ClearCardQueueAction());
        AbstractDungeon.actionManager.addToBottom(new DuelistEndOfTurnDiscardAction());
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
	}
}

