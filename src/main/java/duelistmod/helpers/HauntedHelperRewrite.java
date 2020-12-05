package duelistmod.helpers;

import basemod.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.enums.*;

import java.util.*;

public class HauntedHelperRewrite {

    public static ArrayList<HauntedActions> actions = new ArrayList<>();
    public static int randomIndex = 0;

    public static String triggerRandomAction(int timesTriggered, AbstractCard triggerCard, boolean isDebuff) {
        initList(isDebuff);
        int randomActionNum = 0;
        StringBuilder lastAction = new StringBuilder();
        for (int i = 0; i < timesTriggered; i++)
        {
            randomActionNum = AbstractDungeon.cardRandomRng.random(actions.size() - 1);
            if (DuelistMod.debug) { System.out.println("theDuelist:HauntedHelper:runAction() ---> randomActionNum: " + randomActionNum); }
            if (i + 1 >= timesTriggered) { lastAction.append(runAction(actions.get(randomActionNum), triggerCard, isDebuff)); }
            else { lastAction.append(runAction(actions.get(randomActionNum), triggerCard, isDebuff)).append(", "); }
        }
        return lastAction.toString();
    }

    public static String runAction(HauntedActions action, AbstractCard triggerCard, boolean isDebuff) {
        initList(isDebuff);
        AbstractPlayer p = AbstractDungeon.player;
        switch (action) {
            case DISCARD_SMALL:
                break;
            case DICARD_BIG:
                break;
            case DISCARD_SMALL_RANDOM:
                break;
            case LOSE_RANDOM_GOLD:
                break;
            case LOSE_HP_SMALL:
                break;
            case LOSE_HP_BIG:
                break;
            case LOSE_HP_CARD:
                break;
            case TRIBUTE_ALL:
                break;
            case TRIBUTE_SMALL:
                break;
            case TRIBUTE_BIG:
                break;
            case TRIBUTE_INCREASE_SMALL:
                break;
            case TRIBUTE_INCREASE_MEDIUM:
                break;
            case TRIBUTE_INCREASE_BIG:
                break;
            case SUMMONS_REDUCE_SMALL:
                break;
            case SUMMONS_REDUCE_BIG:
                break;
            case LOSE_ENERGY_SMALL:
                break;
            case MAX_SUMMONS_REDUCE_SMALL:
                break;
            case GAIN_DEBUFF_SMALL:
                break;
            case GAIN_DEBUFF_BIG:
                break;
            case APPLY_STRENGTH_SMALL:
                break;
            case APPLY_THORNS_SMALL:
                break;
            case APPLY_THORNS_MEDIUM:
                break;
            case APPLY_THORNS_BIG:
                break;
            case APPLY_THORNS_GIANT:
                break;
            case APPLY_THORNS_ALL:
                break;
            case APPLY_STRENGTH_ALL_SMALL:
                break;
            case APPLY_STRENGTH_ALL_BIG:
                break;
            case INCREASE_HAUNTED_SMALL:
                break;
            case INCREASE_HAUNTED_BIG:
                break;
            case RANDOM_CURSE_DRAW_PILE:
                break;
            case BLOCK_ENEMIES_SMALL:
                break;
            case BLOCK_ENEMIES_BIG:
                break;
            case STATUS_WOUND_DISCARD_SMALL:
                break;
            case STATUS_SWARM_DISCARD_SMALL:
                break;
            case STATUS_COLD_BLOODED_DISCARD_SMALL:
                break;
            case STATUS_DAZED_DISCARD_SMALL:
                break;
            case STATUS_SLIMED_DISCARD_SMALL:
                break;
            case STATUS_BURN_DISCARD_SMALL:
                break;
            case STATUS_WOUND_DRAW_SMALL:
                break;
            case STATUS_SWARM_DRAW_SMALL:
                break;
            case STATUS_COLD_BLOODED_DRAW_SMALL:
                break;
            case STATUS_DAZED_DRAW_SMALL:
                break;
            case STATUS_SLIMED_DRAW_SMALL:
                break;
            case STATUS_BURN_DRAW_SMALL:
                break;
            case STATUS_WOUND_DISCARD_BIG:
                break;
            case STATUS_SWARM_DISCARD_BIG:
                break;
            case STATUS_COLD_BLOODED_DISCARD_BIG:
                break;
            case STATUS_DAZED_DISCARD_BIG:
                break;
            case STATUS_SLIMED_DISCARD_BIG:
                break;
            case STATUS_BURN_DISCARD_BIG:
                break;
            case STATUS_WOUND_DRAW_BIG:
                break;
            case STATUS_SWARM_DRAW_BIG:
                break;
            case STATUS_COLD_BLOODED_DRAW_BIG:
                break;
            case STATUS_DAZED_DRAW_BIG:
                break;
            case STATUS_SLIMED_DRAW_BIG:
                break;
            case STATUS_BURN_DRAW_BIG:
                break;
            case MAX_HP_LOSS_SMALL:
                break;
            case SUMMON_PLAGUE_SMALL:
                break;
            case SUMMON_PLAGUE_MEDIUM:
                break;
            case SUMMON_PLAGUE_BIG:
                break;
            case RESTRICT_RESUMMONS:
                break;
            case CAN_USE_NO_SPELLS:
                break;
            case CAN_USE_NO_TRAPS:
                break;
            case CAN_USE_MONSTER_RESTRICT:
                break;
        }
        return action.toString();
    }

    private static void initList(boolean isDebuff) {
        actions.addAll(Arrays.asList(HauntedActions.values()));
    }

    private static boolean runTributeIncreaseEffect(HauntedActions action, int amt, AbstractCard tc)
    {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<DuelistCard> handTribs = new ArrayList<>();
        for (AbstractCard c : p.hand.group)
        {
            if (c instanceof DuelistCard && !c.uuid.equals(tc.uuid))
            {
                DuelistCard dc = (DuelistCard)c;
                if (dc.isTributeCard()) { handTribs.add(dc); }
            }
        }
        if (handTribs.size() > 0)
        {
            handTribs.get(AbstractDungeon.cardRandomRng.random(handTribs.size() - 1)).modifyTributesForTurn(amt);
            DevConsole.log("Haunted: " + action.toString());
        }
        return handTribs.size() > 0;
    }

    private static boolean runSummonReduceEffect(HauntedActions action, int amt, AbstractCard tc)
    {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<DuelistCard> handTribs = new ArrayList<>();
        for (AbstractCard c : p.hand.group)
        {
            if (c instanceof DuelistCard && !c.uuid.equals(tc.uuid))
            {
                DuelistCard dc = (DuelistCard)c;
                if (dc.isSummonCard()) { handTribs.add(dc); }
            }
        }
        if (handTribs.size() > 0)
        {
            handTribs.get(AbstractDungeon.cardRandomRng.random(handTribs.size() - 1)).modifySummonsForTurn(-amt);
            DevConsole.log("Haunted: " + action.toString());
        }
        return handTribs.size() > 0;
    }
}
