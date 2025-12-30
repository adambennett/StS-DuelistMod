package duelistmod.abstracts;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.dto.AnyDuelist;

public abstract class MagnetCard extends DuelistCard {

    public MagnetCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public MagnetCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, CardRarity.SPECIAL, TARGET);
    }

    public void transformIntoElectro(MagnetCard electro) {
        AnyDuelist p = AnyDuelist.from(this);
        if (p.getPlayer() == null && p.getEnemy() == null) return;

        // Preserve identity/state where it matters
        electro.uuid = this.uuid;                 // keep same identity
        electro.misc = this.misc;                 // if you use misc for anything
        electro.cost = this.cost;
        electro.costForTurn = this.costForTurn;
        electro.isCostModified = this.isCostModified;
        electro.isCostModifiedForTurn = this.isCostModifiedForTurn;
        electro.freeToPlayOnce = this.freeToPlayOnce;
        electro.retain = this.retain;
        electro.selfRetain = this.selfRetain;
        electro.exhaust = this.exhaust;
        electro.purgeOnUse = this.purgeOnUse;

        // Visual / description init
        electro.initializeDescription();
        electro.applyPowers();
        electro.fixUpgradeDesc();

        // Replace in all relevant groups
        replaceInGroup(p.masterDeckGroup(), electro);
        replaceInGroup(p.handGroup(), electro);
        replaceInGroup(p.drawPileGroup(), electro);
        replaceInGroup(p.discardPileGroup(), electro);
        replaceInGroup(p.exhaustPileGroup(), electro);
        replaceInGroup(p.limboGroup(), electro);
        replaceInSelectScreen(electro);

        // If this card is currently queued/in use, also swap references where possible
        if (p.player()) {
            if (p.getPlayer().cardInUse == this) {
                p.getPlayer().cardInUse = electro;
            }
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();
        if (this.timesUpgraded >= 1) {
            copy.upgrade();
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
