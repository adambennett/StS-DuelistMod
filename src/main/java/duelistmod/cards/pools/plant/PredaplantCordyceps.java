package duelistmod.cards.pools.plant;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class PredaplantCordyceps extends DuelistCard {
    public static final String ID = DuelistMod.makeID("PredaplantCordyceps");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PredaplantCordyceps.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public PredaplantCordyceps() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PREDAPLANT);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.PLANT);
        this.tags.add(Tags.GOOD_TRIB);
        this.baseSummons = this.summons = 1;
		this.originalName = this.name;
		this.baseBlock = this.block = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        block();
        ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
        if (mons.size() > 0) {
            for (AbstractMonster m : mons) {
                if (m != null && !m.isDead && !m.isDying && !m.halfDead && m.hasPower(ThornsPower.POWER_ID)) {
                    int amt = m.getPower(ThornsPower.POWER_ID).amount;
                    DuelistCard.removePower(m.getPower(ThornsPower.POWER_ID), m);
                    AnyDuelist duelist = AnyDuelist.from(this);
                    duelist.applyPower(m, duelist.creature(), new PoisonPower(m, duelist.creature(), amt));
                }
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new PredaplantCordyceps();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
