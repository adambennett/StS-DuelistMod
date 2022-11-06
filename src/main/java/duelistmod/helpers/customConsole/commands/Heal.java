package duelistmod.helpers.customConsole.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

import java.util.ArrayList;

public class Heal extends ConsoleCommand {

    public Heal() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 1;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        String amt = tokens.length > 1 ? tokens[1] : "null";
        int amount = ConvertHelper.tryParseInt(amt, 1000);

        if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null) {
            AbstractDungeon.player.heal(amount, true);
            MonsterGroup monsters = AbstractDungeon.getMonsters();
            if (monsters != null) {
                for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                    if (!(mon.isDead || mon.isDying || mon.escaped || mon.isDeadOrEscaped())) {
                        mon.heal(amount, true);
                    }
                }
            }
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return smallNumbers();
    }
}
