package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.DoubleAttackPower;
import duelistmod.variables.Tags;
import java.util.List;

public class DoubleAttack extends DuelistCard implements RevengeCard {

    public static final String ID = DuelistMod.makeID("DoubleAttack");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DoubleAttack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 3;

    public DoubleAttack() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 16;
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return RevengeCard.super.isRevengeActive(card);
    }

    @Override
    public void triggerRevenge(AnyDuelist duelist) {
        duelist.applyPowerToSelf(new DoubleAttackPower(duelist.creature(), duelist.creature()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        if (targets.size() > 0) {
            AbstractMonster selectedLowest = null;
            AbstractMonster selectedSecond = null;
            Integer lowestHP = null;
            Integer secondLowestHP = null;
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                boolean deathChecks = !mon.isDead && !mon.isDying && !mon.halfDead && !mon.isDeadOrEscaped() && mon.currentHealth != 0;
                if ((lowestHP == null && deathChecks) || (lowestHP != null && deathChecks && mon.currentHealth < lowestHP)) {
                    selectedLowest = mon;
                    lowestHP = mon.currentHealth;
                }
            }
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                boolean deathChecks = !mon.isDead && !mon.isDying && !mon.halfDead && !mon.isDeadOrEscaped() && mon.currentHealth != 0;
                if (mon.equals(selectedLowest)) continue;

                if ((secondLowestHP == null && deathChecks) || (secondLowestHP != null && deathChecks && mon.currentHealth < secondLowestHP)) {
                    selectedSecond = mon;
                    secondLowestHP = mon.currentHealth;
                }
            }
            if (selectedLowest != null) {
                attack(selectedLowest, this.baseAFX, this.damage);
            }
            if (selectedSecond != null) {
                attack(selectedSecond, this.baseAFX, this.damage);
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new DoubleAttack();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
