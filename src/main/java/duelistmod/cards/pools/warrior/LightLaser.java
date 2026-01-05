package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.LightLaserPower;
import duelistmod.variables.Tags;

import java.util.List;

public class LightLaser extends RevengeDuelistCard {

    public static final String ID = DuelistMod.makeID("LightLaser");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("LightLaser.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 3;

    public LightLaser() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 3;
        this.isMultiDamage = true;
        this.tags.add(Tags.X_COST);
        this.tags.add(Tags.SPELL);
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        int xTributes = xCostTribute();
        if (duelist.player()) {
            this.addToBot(new DamageAllEnemiesAction(owner, this.multiDamage, DamageInfo.DamageType.NORMAL, this.baseAFX));
            this.addToBot(new DamageAllEnemiesAction(owner, this.multiDamage, DamageInfo.DamageType.NORMAL, this.baseAFX));
        } else if (duelist.getEnemy() != null && targets != null && !targets.isEmpty()) {
            attack(targets.get(0));
            attack(targets.get(0));
        }
        if (xTributes > 0) {
            duelist.applyPowerToSelf(new LightLaserPower(owner, xTributes), owner);
            if (isRevengeActive(this)) {
                duelist.applyPowerToSelf(new VigorPower(duelist.creature(), xTributes));
                super.trigger(AnyRevengeCard.from(this), duelist);
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return super.isRevengeActive(card);
    }

    @Override
    public void onRevengeTriggered(AnyDuelist duelist) {
        // requires Tribute X, handled inside use()
        super.trigger(AnyRevengeCard.from(this), duelist);
    }

    @Override
    public AbstractCard makeCopy() {
        return new LightLaser();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
