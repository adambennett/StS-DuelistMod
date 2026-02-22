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
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.orbs.FireOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.BurningDebuff;
import duelistmod.variables.Tags;

import java.util.List;

public class Salamandra extends RevengeDuelistCard {
    public static final String ID = DuelistMod.makeID("Salamandra");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Salamandra.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public Salamandra() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 10;
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return super.isRevengeActive(card) ;
    }

    @Override
    public void onRevengeTriggered(AnyDuelist duelist) {
        duelist.draw(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);

        if (targets.size() > 0) {
            AnyDuelist duelist = AnyDuelist.from(this);
            if (duelist.player()) {
                boolean anyBurning = false;
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                        if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead && monster.hasPower(BurningDebuff.POWER_ID) && monster.getPower(BurningDebuff.POWER_ID).amount > 0) {
                            anyBurning = true;
                            break;
                        }
                    }
                }
                if (!anyBurning) {
                    anyBurning = duelist.orbsChanneledThisCombat().stream().anyMatch(o -> o instanceof FireOrb);
                }
                if (anyBurning) {
                    attack(targets.get(0));
                }
            } else if (duelist.getEnemy() != null) {
                boolean anyBurning = targets.get(0).hasPower(BurningDebuff.POWER_ID) && targets.get(0).getPower(BurningDebuff.POWER_ID).amount > 0;
                if (!anyBurning) {
                    anyBurning = duelist.orbsChanneledThisCombat().stream().anyMatch(o -> o instanceof FireOrb);
                }
                if (anyBurning) {
                    attack(targets.get(0));
                }
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new Salamandra();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
