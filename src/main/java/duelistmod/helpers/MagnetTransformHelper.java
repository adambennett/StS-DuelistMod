package duelistmod.helpers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import duelistmod.abstracts.GuardedMagnetCard;
import duelistmod.abstracts.MagnetCard;
import duelistmod.cards.pools.warrior.*;

public class MagnetTransformHelper {

    /** If the card should be an "electro form" (or later form), return a NEW proper instance and copy state over. */
    public static MagnetCard normalizeIfNeeded(MagnetCard c) {
        if (c == null) return null;

        MagnetCard replacement = determineReplacement(c);
        if (replacement == null) return c;

        copyRuntimeState(c, replacement);
        return replacement;
    }

    /**
     * Decide what the card SHOULD be, given its ID + upgrades.
     * This handles both:
     *  - "AlphaMagnet with timesUpgraded >= 2" -> AlphaElectro
     *  - "cardID already AlphaElectro but object is still AlphaMagnet" -> AlphaElectro
     */
    private static MagnetCard determineReplacement(MagnetCard c) {
        final String id = c.cardID;

        // If the ID already indicates the final form, but the object isn't that class, replace.
        if (id.equals(AlphaElectro.ID) && !(c instanceof AlphaElectro)) return new AlphaElectro();
        if (id.equals(EpsilonMagnet.ID) && !(c instanceof EpsilonMagnet)) return new EpsilonMagnet();
        if (id.equals(Berserkion.ID) && !(c instanceof Berserkion)) return new Berserkion();
        if (id.equals(ImperionSuperconductiveBattlebot.ID) && !(c instanceof ImperionSuperconductiveBattlebot)) return new ImperionSuperconductiveBattlebot();

        // Otherwise map "base form + upgrade count" -> final form.
        // Alpha / Beta / Gamma: base -> plus -> electro on 2nd upgrade
        if (id.equals(AlphaMagnet.ID) && c.timesUpgraded >= 2) return new AlphaElectro();
        if (id.equals(BetaMagnet.ID) && c.timesUpgraded >= 2) return new BetaElectro();
        if (id.equals(GammaMagnet.ID) && c.timesUpgraded >= 2) return new GammaElectro();
        if (id.equals(DeltaMagnet.ID) && c.timesUpgraded >= 2) return new EpsilonMagnet();
        if (id.equals(Valkyrion.ID) && c.timesUpgraded == 1) return new Berserkion();
        if (id.equals(Berserkion.ID) && c.timesUpgraded > 0) return new ImperionSuperconductiveBattlebot();
        if (id.equals(Valkyrion.ID) && c.timesUpgraded > 1) return new ImperionSuperconductiveBattlebot();

        return null;
    }

    /** Copy state that must persist (uuid, misc, cost modifiers, etc.) */
    private static void copyRuntimeState(MagnetCard src, MagnetCard dst) {
        dst.uuid = src.uuid;
        dst.misc = src.misc;

        // upgrade bookkeeping
        if (src.copyUpgradeStateOnTransform()) {
            dst.timesUpgraded = src.timesUpgraded;
            dst.upgraded = src.upgraded;
        } else {
            dst.timesUpgraded = 0;
            dst.upgraded = false;
        }

        // cost state
        dst.cost = src.cost;
        dst.costForTurn = src.costForTurn;
        dst.isCostModified = src.isCostModified;
        dst.isCostModifiedForTurn = src.isCostModifiedForTurn;

        // flags
        dst.freeToPlayOnce = src.freeToPlayOnce;
        dst.retain = src.retain;
        dst.selfRetain = src.selfRetain;
        dst.exhaust = src.exhaust;
        dst.purgeOnUse = src.purgeOnUse;
        dst.isEthereal = src.isEthereal;

        // some UIs rely on these
        dst.inBottleFlame = src.inBottleFlame;
        dst.inBottleLightning = src.inBottleLightning;
        dst.inBottleTornado = src.inBottleTornado;
        dst.drawScale = src.drawScale;
        dst.current_x = src.current_x;
        dst.current_y = src.current_y;
        dst.target_x = src.target_x;
        dst.target_y = src.target_y;
        dst.angle = src.angle;
        dst.targetAngle = src.targetAngle;
        dst.transparency = src.transparency;

        dst.tags.clear();
        dst.tags.addAll(src.tags);
        dst.baseSummons = src.baseSummons;
        dst.summons = src.baseSummons;
        dst.baseTributes = src.baseTributes;
        dst.tributes = src.baseTributes;
        dst.isSummon = src.isSummon;
        dst.enemyIntent = src.enemyIntent;
        dst.baseSecondMagic = src.baseSecondMagic;
        dst.baseThirdMagic = src.baseThirdMagic;
        dst.turnTributeChange = src.turnTributeChange;
        dst.giantTribChange = src.giantTribChange;
        dst.combatTributeChange = src.combatTributeChange;
        dst.combatSummonChange = src.combatSummonChange;
        dst.turnSummonChange = src.turnSummonChange;
        dst.permTribChange = src.permTribChange;
        dst.upgradedTributes = src.upgradedTributes;
        dst.permSummonChange = src.permSummonChange;
        dst.isMagicNumModifiedForTurn = src.isMagicNumModifiedForTurn;
        dst.originalMagicNumber = src.originalMagicNumber;
        dst.inDuelistBottle = src.inDuelistBottle;
        dst.originalDescription = src.originalDescription;
        dst.savedTypeMods = src.savedTypeMods;
        dst.cardsToPreview = src.cardsToPreview;
        dst.keywords.clear();
        dst.keywords.addAll(src.keywords);
        for (String mod : dst.savedTypeMods) {
            if (!mod.equals("default") && dst.notAddedTagToDescription(mod)) {
                dst.rawDescription = mod + " NL " + dst.rawDescription;
                dst.originalDescription = mod + " NL " + dst.originalDescription;
                dst.isTypeAddedPerm = true;
                dst.addTagToAddedTypeMods(mod);
            }
        }
        dst.addedSpecialSummonKeyword = src.addedSpecialSummonKeyword;
        if (src.permCostChange != 999) {
            dst.permUpdateCost(src.permCostChange);
        }

        dst.initTitle();
        dst.initializeDescription();
    }

    /** Replace card instance inside a CardGroup by UUID match. */
    public static boolean replaceInGroup(CardGroup group, MagnetCard original, MagnetCard replacement) {
        if (group == null || group.group == null) return false;
        for (int i = 0; i < group.group.size(); i++) {
            AbstractCard g = group.group.get(i);
            if (g == original || g.uuid.equals(original.uuid)) {
                group.group.set(i, replacement);
                return true;
            }
        }
        return false;
    }

    /** Try to replace in all player groups; returns true if found anywhere. */
    public static boolean replaceInAllPlayerGroups(MagnetCard original, MagnetCard replacement) {
        if (AbstractDungeon.player == null) return false;

        boolean replaced = false;
        replaced |= replaceInGroup(AbstractDungeon.player.hand, original, replacement);
        replaced |= replaceInGroup(AbstractDungeon.player.drawPile, original, replacement);
        replaced |= replaceInGroup(AbstractDungeon.player.discardPile, original, replacement);
        replaced |= replaceInGroup(AbstractDungeon.player.exhaustPile, original, replacement);
        replaced |= replaceInGroup(AbstractDungeon.player.limbo, original, replacement);
        replaced |= replaceInGroup(AbstractDungeon.player.masterDeck, original, replacement);

        if (AbstractDungeon.player.cardInUse != null && AbstractDungeon.player.cardInUse.uuid.equals(original.uuid)) {
            AbstractDungeon.player.cardInUse = replacement;
            replaced = true;
        }
        return replaced;
    }

    public static void replaceInCardPopupIfViewing(MagnetCard original, MagnetCard replacement) {
        if (CardCrawlGame.cardPopup == null) return;

        AbstractCard popupCard = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
        if (popupCard == null) return;

        if (popupCard.uuid != null && popupCard.uuid.equals(original.uuid)) {
            if (replacement instanceof Berserkion) {
                replacement.upgradedDamage = true;
            }
            if (replacement instanceof ImperionSuperconductiveBattlebot) {
                ((GuardedMagnetCard)replacement).setUpgradedGuardedCheck(true);
            }
            if (replacement instanceof AlphaElectro) {
                replacement.upgradedDamage = true;
            }
            if (replacement instanceof BetaElectro) {
                replacement.upgradedBlock = true;
            }
            if (replacement instanceof GammaElectro) {
                replacement.upgradedMagicNumber = true;
            }
            if (replacement instanceof EpsilonMagnet) {
                replacement.upgradedSummons = true;
            }
            replacement.displayUpgrades();
            replacement.initTitle();
            replacement.initializeDescription();
            ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card", replacement);
            ReflectionHacks.privateMethod(SingleCardViewPopup.class, "loadPortraitImg").invoke(CardCrawlGame.cardPopup);
        }
    }
}

