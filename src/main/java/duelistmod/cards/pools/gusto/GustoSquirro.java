package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class GustoSquirro extends DuelistCard {
    public static final String ID = DuelistMod.makeID("GustoSquirro");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoSquirro.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public GustoSquirro() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 2;
        this.baseDamage = this.damage = 4;
        this.baseSummons = this.summons = 1;
        this.baseMagicNumber = this.magicNumber = 2;
        this.originalName = this.name;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
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
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        super.customOnTribute(tc);
        if(tc == null || (!tc.hasTag(Tags.SPELLCASTER) && !upgraded)) return;
        if (AnyDuelist.from(this).player()) {
            addToBot(new ScryAction(this.magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeDamage(2);
        this.upgradeBlock(2);
        this.upgradeName();
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.fixUpgradeDesc();
        this.initializeDescription();
    }
}
