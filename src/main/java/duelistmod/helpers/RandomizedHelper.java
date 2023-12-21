package duelistmod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.RandomizedOptions;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class RandomizedHelper {

    private final AbstractCard card;
    public final AnyDuelist duelist;
    private boolean exhaustCheck;
    private boolean etherealCheck;
    private boolean costChangeCheck;
    private boolean upgradeCheck;
    private boolean summonCheck;
    private boolean tributeCheck;
    private boolean summonChangeCombatCheck;
    private boolean tributeChangeCombatCheck;
    private boolean dontTrigFromFairyBox;
    private int lowCostRoll;
    private int highCostRoll;
    private int lowSummonRoll;
    private int highSummonRoll;
    private int lowTributeRoll;
    private int highTributeRoll;

    public RandomizedHelper(AbstractCard input) {
        this.etherealCheck = true;
        this.exhaustCheck = true;
        this.costChangeCheck = true;
        this.summonCheck = false;
        this.tributeCheck = false;
        this.lowCostRoll = 1;
        this.highCostRoll = 3;
        this.lowTributeRoll = 0;
        this.highTributeRoll = 0;
        this.lowSummonRoll = 0;
        this.highSummonRoll = 0;
        this.summonChangeCombatCheck = false;
        this.tributeChangeCombatCheck = false;
        this.dontTrigFromFairyBox = false;
        this.upgradeCheck = false;
        this.card = input;
        this.duelist = AnyDuelist.from(this.card);
        this.checkFlags();
    }

    public RandomizedHelper(AbstractCard input, RandomizedOptions options) {
        this(input);
        if (options == null) return;

        this.exhaustCheck = options.isExhaustCheck();
        this.etherealCheck = options.isEtherealCheck();
        this.upgradeCheck = options.isUpgrade();
        this.costChangeCheck = options.isCostChangeCheck();
        this.summonCheck = options.isSummonCheck();
        this.tributeCheck = options.isTributeCheck();
        this.summonChangeCombatCheck = options.isSummonChangeCombatCheck();
        this.tributeChangeCombatCheck = options.isTributeChangeCombatCheck();
        this.dontTrigFromFairyBox = options.isDontTrig();
        this.lowCostRoll = options.getLowCostRoll();
        this.highCostRoll = options.getHighCostRoll();
        this.lowSummonRoll = options.getLowSummonRoll();
        this.highSummonRoll = options.getHighSummonRoll();
        this.lowTributeRoll = options.getLowTributeRoll();
        this.highTributeRoll = options.getHighTributeRoll();

        this.checkFlags();
    }

    private void checkFlags()
    {
        if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoCostChanges()) { this.costChangeCheck = false; }
        if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoTributeChanges()) { this.tributeCheck = false; }
        if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoSummonChanges()) { this.summonCheck = false; }
        if (DuelistMod.persistentDuelistData.RandomizedSettings.getAlwaysUpgrade()) { this.upgradeCheck = true; }
        if (DuelistMod.persistentDuelistData.RandomizedSettings.getNeverUpgrade()) { this.upgradeCheck = false; }
        if (!DuelistMod.persistentDuelistData.RandomizedSettings.getAllowEthereal()) { this.etherealCheck = false; }
        if (!DuelistMod.persistentDuelistData.RandomizedSettings.getAllowExhaust()) { this.exhaustCheck = false; }
    }

    public static AbstractCard randomize(AbstractCard input) {
        return new RandomizedHelper(input).randomizeLogic();
    }

    public static AbstractCard randomize(AbstractCard input, RandomizedOptions options) {
        return new RandomizedHelper(input, options).randomizeLogic();
    }

    public AbstractCard randomizeLogic() {
        AbstractCard c = this.card.makeStatEquivalentCopy();

        if (c.canUpgrade() && upgradeCheck) {
            c.upgrade();
        }

        if (!c.isEthereal && etherealCheck && !c.hasTag(Tags.NEVER_ETHEREAL) && !c.selfRetain) {
            c.isEthereal = true;
            c.rawDescription = Strings.etherealForCardText + c.rawDescription;
        }

        if (!c.exhaust && exhaustCheck && !c.hasTag(Tags.NEVER_EXHAUST) && !c.type.equals(AbstractCard.CardType.POWER)) {
            c.exhaust = true;
            c.rawDescription = c.rawDescription + DuelistMod.exhaustForCardText;
        }

        if (costChangeCheck && c.costForTurn >= 0 && c.cost >= 0) {
            int randomNum = AbstractDungeon.cardRandomRng.random(lowCostRoll, highCostRoll);
            if (DuelistMod.persistentDuelistData.RandomizedSettings.getOnlyCostDecreases()) {
                if (randomNum < c.cost) {
                    c.costForTurn = randomNum;
                    c.isCostModifiedForTurn = true;
                }
            } else {
                c.costForTurn = randomNum;
                c.isCostModifiedForTurn = true;
            }
        }

        if (summonCheck && c instanceof DuelistCard) {
            int randomNum = AbstractDungeon.cardRandomRng.random(lowSummonRoll, highSummonRoll);
            DuelistCard dC = (DuelistCard)c;
            if (DuelistMod.persistentDuelistData.RandomizedSettings.getOnlySummonIncreases()) {
                if (dC.baseSummons + randomNum > dC.baseSummons) {
                    if (summonChangeCombatCheck && dC.isSummonCard()) {
                        dC.modifySummons(randomNum);
                    } else if (dC.isSummonCard()) {
                        dC.modifySummonsForTurn(randomNum);
                    }
                }
            } else {
                if (summonChangeCombatCheck && dC.isSummonCard()) {
                    dC.modifySummons(randomNum);
                } else if (dC.isSummonCard()) {
                    dC.modifySummonsForTurn(randomNum);
                }
            }
        }

        if (tributeCheck && c instanceof DuelistCard) {
            int randomNum = AbstractDungeon.cardRandomRng.random(lowTributeRoll, highTributeRoll);
            DuelistCard dC = (DuelistCard)c;
            if (DuelistMod.persistentDuelistData.RandomizedSettings.getOnlyTributeDecreases()) {
                if (dC.baseTributes + randomNum < dC.baseTributes) {
                    if (tributeChangeCombatCheck && dC.isTributeCard()) {
                        dC.modifyTributes(-randomNum);
                    } else if (dC.isTributeCard()) {
                        dC.modifyTributesForTurn(-randomNum);
                    }
                }
            } else {
                if (tributeChangeCombatCheck && dC.isTributeCard()) {
                    dC.modifyTributes(-randomNum);
                } else if (dC.isTributeCard()) {
                    dC.modifyTributesForTurn(-randomNum);
                }
            }
        }

        if (dontTrigFromFairyBox) {
            c.dontTriggerOnUseCard = false;
        }

        if (c instanceof DuelistCard) {
            ((DuelistCard)c).fixUpgradeDesc();
        }

        c.initializeDescription();
        return c;
    }

    public AbstractCard getCard() {
        return card;
    }

    public AnyDuelist getDuelist() {
        return duelist;
    }

    public boolean isExhaustCheck() {
        return exhaustCheck;
    }

    public boolean isEtherealCheck() {
        return etherealCheck;
    }

    public boolean isCostChangeCheck() {
        return costChangeCheck;
    }

    public boolean isUpgradeCheck() {
        return upgradeCheck;
    }

    public boolean isSummonCheck() {
        return summonCheck;
    }

    public boolean isTributeCheck() {
        return tributeCheck;
    }

    public boolean isSummonChangeCombatCheck() {
        return summonChangeCombatCheck;
    }

    public boolean isTributeChangeCombatCheck() {
        return tributeChangeCombatCheck;
    }

    public boolean isDontTrigFromFairyBox() {
        return dontTrigFromFairyBox;
    }

    public int getLowCostRoll() {
        return lowCostRoll;
    }

    public int getHighCostRoll() {
        return highCostRoll;
    }

    public int getLowSummonRoll() {
        return lowSummonRoll;
    }

    public int getHighSummonRoll() {
        return highSummonRoll;
    }

    public int getLowTributeRoll() {
        return lowTributeRoll;
    }

    public int getHighTributeRoll() {
        return highTributeRoll;
    }
}
