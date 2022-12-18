package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.Token;
import duelistmod.helpers.customConsole.CustomConsoleCommandHelper;

import java.util.ArrayList;

public class Setup extends ConsoleCommand {

    public Setup() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 0;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        DuelistCard.incMaxSummons(95);
        DuelistCard.summon(AbstractDungeon.player, 99, new Token());
        AbstractDungeon.player.gainEnergy(9999);
        AbstractDungeon.player.gainGold(9900);
        AbstractDungeon.player.increaseMaxHp(9940, true);
        CustomConsoleCommandHelper.healAll(9999);
        CustomConsoleCommandHelper.gainPotionSlots(6);

        DuelistMod.currentZombieSouls += 99;

        // Discard hand
        for (AbstractCard c : new ArrayList<>(AbstractDungeon.player.hand.group)) {
            AbstractDungeon.player.hand.moveToDiscardPile(c);
            c.triggerOnManualDiscard();
            GameActionManager.incrementDiscard(false);
        }

        Settings.isFinalActAvailable = true;
        if(!Settings.hasRubyKey) {
            AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.RED));
        }
        if(!Settings.hasSapphireKey) {
            AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
        }
        if(!Settings.hasEmeraldKey) {
            AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.GREEN));
        }

    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return super.extraOptions(tokens, depth);
    }
}
