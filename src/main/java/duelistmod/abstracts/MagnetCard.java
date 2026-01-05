package duelistmod.abstracts;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.MagnetTransformHelper;

import static duelistmod.helpers.MagnetTransformHelper.replaceInCardPopupIfViewing;

public abstract class MagnetCard extends DuelistCard {

    public MagnetCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public void transformIntoElectro(MagnetCard electro) {
        AnyDuelist p = AnyDuelist.from(this);

        // Preserve runtime flags/state from the current card onto the target form
        electro.uuid = this.uuid;
        electro.misc = this.misc;
        electro.cost = this.cost;
        electro.costForTurn = this.costForTurn;
        electro.isCostModified = this.isCostModified;
        electro.isCostModifiedForTurn = this.isCostModifiedForTurn;
        electro.freeToPlayOnce = this.freeToPlayOnce;
        electro.retain = this.retain;
        electro.selfRetain = this.selfRetain;
        electro.exhaust = this.exhaust;
        electro.purgeOnUse = this.purgeOnUse;
        electro.isEthereal = this.isEthereal;
        electro.inBottleFlame = this.inBottleFlame;
        electro.inBottleLightning = this.inBottleLightning;
        electro.inBottleTornado = this.inBottleTornado;

        // Preserve upgrade bookkeeping (helps some UIs / save semantics)
        if (copyUpgradeStateOnTransform()) {
            electro.timesUpgraded = this.timesUpgraded;
            electro.upgraded = this.upgraded;
        } else {
            electro.timesUpgraded = 0;
            electro.upgraded = false;
        }

        // No owner yet (upgrade previews, console-spawned cards before insertion, etc.)
        if (p.getPlayer() == null && p.getEnemy() == null) {
            become(electro);
            return;
        }

        // Owned card path: swap references in piles/groups
        electro.initializeTitle();
        electro.initializeDescription();
        if (p.creature() != null && AbstractDungeon.player != null && AbstractDungeon.currMapNode != null
                && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().monsters != null) {
            electro.applyPowers();
        }
        electro.fixUpgradeDesc();

        replaceInGroup(p.masterDeckGroup(), electro);
        replaceInGroup(p.handGroup(), electro);
        replaceInGroup(p.drawPileGroup(), electro);
        replaceInGroup(p.discardPileGroup(), electro);
        replaceInGroup(p.exhaustPileGroup(), electro);
        replaceInGroup(p.limboGroup(), electro);
        replaceInSelectScreen(electro);
        replaceInCardPopupIfViewing(this, electro);

        if (p.player() && p.getPlayer().cardInUse == this) {
            p.getPlayer().cardInUse = electro;
        }
    }

    private void become(MagnetCard src) {
        // Identity + presentation
        this.cardID = src.cardID;
        this.name = src.name;
        this.originalName = src.originalName;
        this.rawDescription = src.rawDescription;

        // Core card fields that matter for display
        this.type = src.type;
        this.target = src.target;
        this.rarity = src.rarity;
        this.color = src.color;

        // Image
        this.textureImg = src.textureImg;
        CustomCard.imgMap.remove(this.textureImg);
        this.loadCardImage(this.textureImg);

        // Numbers (copy base + current)
        this.baseDamage = src.baseDamage;
        this.damage = src.baseDamage;
        this.baseBlock = src.baseBlock;
        this.block = src.baseBlock;
        this.baseMagicNumber = src.baseMagicNumber;
        this.magicNumber = src.baseMagicNumber;
        this.isMultiDamage = src.isMultiDamage;

        // Tags
        this.tags.clear();
        this.tags.addAll(src.tags);
        this.baseSummons = src.baseSummons;
        this.summons = src.baseSummons;
        this.baseTributes = src.baseTributes;
        this.tributes = src.baseTributes;
        this.isSummon = src.isSummon;
        this.enemyIntent = src.enemyIntent;
        this.baseSecondMagic = src.baseSecondMagic;
        this.baseThirdMagic = src.baseThirdMagic;
        this.turnTributeChange = src.turnTributeChange;
        this.giantTribChange = src.giantTribChange;
        this.combatTributeChange = src.combatTributeChange;
        this.combatSummonChange = src.combatSummonChange;
        this.turnSummonChange = src.turnSummonChange;
        this.permTribChange = src.permTribChange;
        this.upgradedTributes = src.upgradedTributes;
        this.permSummonChange = src.permSummonChange;
        this.isMagicNumModifiedForTurn = src.isMagicNumModifiedForTurn;
        this.originalMagicNumber = src.originalMagicNumber;
        this.inDuelistBottle = src.inDuelistBottle;
        this.exhaust = src.exhaust;
        this.isEthereal = src.isEthereal;
        this.originalDescription = src.originalDescription;
        this.savedTypeMods = src.savedTypeMods;
        this.cardsToPreview = src.cardsToPreview;
        this.keywords.clear();
        this.keywords.addAll(src.keywords);
        for (String mod : this.savedTypeMods) {
            if (!mod.equals("default") && this.notAddedTagToDescription(mod)) {
                this.rawDescription = mod + " NL " + this.rawDescription;
                this.originalDescription = mod + " NL " + this.originalDescription;
                this.isTypeAddedPerm = true;
                this.addTagToAddedTypeMods(mod);
            }
        }
        this.addedSpecialSummonKeyword = src.addedSpecialSummonKeyword;
        if (src.permCostChange != 999) {
            this.permUpdateCost(src.permCostChange);
        }

        // Rebuild text
        this.initializeTitle();
        this.initializeDescription();
        this.displayUpgrades();
    }

    public void initTitle() {
        this.initializeTitle();
    }

    public boolean copyUpgradeStateOnTransform() {
        return true;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();
        if (copy instanceof MagnetCard) {
            MagnetCard normalized = MagnetTransformHelper.normalizeIfNeeded((MagnetCard) copy);
            return (normalized != null) ? normalized : copy;
        }
        return copy;
    }

    @Override
    public void upgrade() {}

    @Override
    public boolean canUpgrade() {
        return false;
    }

    private void replaceInGroup(CardGroup group, AbstractCard replacement) {
        if (group == null || group.group == null) return;

        for (int i = 0; i < group.group.size(); i++) {
            AbstractCard c = group.group.get(i);
            if (c == this) {
                group.group.set(i, replacement);
                return;
            }
            if (c.uuid.equals(this.uuid)) {
                group.group.set(i, replacement);
                return;
            }
        }
    }

    private void replaceInSelectScreen(AbstractCard replacement) {
        if (AbstractDungeon.gridSelectScreen != null && AbstractDungeon.gridSelectScreen.upgradePreviewCard != null && AbstractDungeon.gridSelectScreen.upgradePreviewCard.uuid.equals(this.uuid)) {
            replacement.displayUpgrades();
            replacement.drawScale = 0.875F;
            AbstractDungeon.gridSelectScreen.upgradePreviewCard = replacement;
        }
    }

}
