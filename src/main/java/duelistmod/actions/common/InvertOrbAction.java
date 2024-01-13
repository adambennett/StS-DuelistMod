package duelistmod.actions.common;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.helpers.customConsole.commands.Channel;
import duelistmod.relics.InversionEvokeRelic;
import duelistmod.relics.InversionRelic;

public class InvertOrbAction extends AbstractGameAction {
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(DuelistMod.makeID("InvertOrbAction"));
    public static final String[] TEXT = uiString.TEXT;
    private AbstractPlayer p;

    public InvertOrbAction(int amt) {
        duration = startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
        amount = amt;
        actionType = ActionType.DAMAGE;
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (duration == startDuration && p.hasOrb() && amount > 0) {
            int evokeCount = 1;
            if (p.hasRelic(InversionRelic.ID)) {
                p.getRelic(InversionRelic.ID).flash();
                evokeCount++;
            }
            if (p.hasRelic(InversionEvokeRelic.ID)) {
                p.getRelic(InversionEvokeRelic.ID).flash();
                evokeCount = 2;
            }
            DuelistCard.resetInvertStringMap();
            AbstractOrb orb = p.orbs.get(0).makeCopy();
            AbstractOrb inverse = null;

            if (DuelistMod.invertStringMap.containsKey(orb.name)) {
                inverse = DuelistMod.invertStringMap.get(orb.name);
            } else {
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, orb.name + TEXT[0], true));
                Util.log("Skipped inverting " + orb.name + " because we did not find an entry in the allowed invertable orbs names list");
                if (DuelistMod.debug)
                {
                    int counter = 0;
                    for (String s : DuelistMod.invertableOrbNames)
                    {
                        Util.log("Invert Names[" + counter++ + "]: " + s + " ||| vs. ||| " + orb.name);
                    }
                }
            }
            addToTop(new ChannelAction(amount % 2 > 0 && inverse != null ? inverse : orb));

            for (int i = amount; i > 1; i--) {
                addToTop(new ActivateOrbEvokeEffectAction(i % 2 == 0 && inverse != null ? inverse : orb, evokeCount));
            }
            if (evokeCount > 1) {
                addToTop(new EvokeWithoutRemovingOrbAction(evokeCount-1));
            }
            addToTop(new EvokeOrbAction(1));
        }
        tickDuration();
    }
}
