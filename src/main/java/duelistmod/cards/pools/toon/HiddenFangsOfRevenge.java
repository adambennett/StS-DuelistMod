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
import duelistmod.powers.duelistPowers.FangsPower;
import duelistmod.variables.Tags;
import java.util.List;

public class HiddenFangsOfRevenge extends DuelistCard implements RevengeCard {

    public static final String ID = DuelistMod.makeID("HiddenFangsOfRevenge");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("HiddenFangsOfRevenge.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    public HiddenFangsOfRevenge() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 12;
        this.baseMagicNumber = this.magicNumber = 4;
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return RevengeCard.super.isRevengeActive(card) ;
    }

    @Override
    public void triggerRevenge(AnyDuelist duelist) {
        // Deal damage to random enemy
        AbstractCreature target = null;
        if (duelist.player()) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                AbstractMonster random = AbstractDungeon.getMonsters().getRandomMonster(true);
                if (random != null) {
                    target = random;

                }
            }
        } else if (duelist.getEnemy() != null) {
            target = AbstractDungeon.player;
        }
        if (target != null) {
            attack(target);
        }

        // Gain Fangs
        if (this.magicNumber > 0) {
            duelist.applyPowerToSelf(new FangsPower(duelist.creature(), duelist.creature(), this.magicNumber));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        if (targets.size() > 0) {
            attack(targets.get(0));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new HiddenFangsOfRevenge();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
