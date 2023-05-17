package duelistmod.abstracts;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public abstract class DynamicDamageCard extends DuelistCard {

    public int originalDamage = 0;

    public DynamicDamageCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public int damageFunction() { return 0; }

    public int damageFunctionMultiplier() { return 1; }

    public int extraFinalDamage() { return 0; }

    public void beforeApplyPowersInUpdate() {}

    public void afterApplyPowersInUpdate() {}

    @Override
    protected void upgradeDamage(int amount) {
        super.upgradeDamage(amount);
        this.originalDamage += amount;
    }

    @Override
    public void applyPowers() {
        int standardVal = (this.originalDamage + this.damageFunction()) * this.damageFunctionMultiplier();
        this.damage = this.baseDamage = standardVal;
        super.applyPowers();
        int diff = this.damage - standardVal;
        this.damage = ((this.originalDamage + this.damageFunction()) * this.damageFunctionMultiplier()) + diff + this.extraFinalDamage();
        this.isDamageModified = this.damage != standardVal;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int standardVal = (this.originalDamage + this.damageFunction()) * this.damageFunctionMultiplier();
        this.damage = this.baseDamage = standardVal;
        super.calculateCardDamage(mo);
        int diff = this.damage - standardVal;
        this.damage = ((this.originalDamage + this.damageFunction()) * this.damageFunctionMultiplier()) + diff + this.extraFinalDamage();
        this.isDamageModified = this.damage != standardVal;
        this.initializeDescription();
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT)) {
            this.beforeApplyPowersInUpdate();
            this.applyPowers();
            this.afterApplyPowersInUpdate();
        }
        this.fixUpgradeDesc();
        this.initializeDescription();
    }
}
