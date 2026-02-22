package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class DarkCrusader extends RevengeDuelistCard {

    public static final String ID = DuelistMod.makeID("DarkCrusader");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkCrusader.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;

    public DarkCrusader() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
        this.tags.add(Tags.WARRIOR_DECK);
        this.superheavyDeckCopies = 1;
        this.baseDamage = this.damage = 5;
        this.summons = this.baseSummons = 1;
        this.baseMagicNumber = this.magicNumber = 2;
        this.originalName = this.name;
        this.isSummon = true;
        this.enemyIntent = AbstractMonster.Intent.ATTACK;
        this.setupStartingCopies();
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return super.isRevengeActive(card) && this.magicNumber > 0;
    }

    @Override
    public void onRevengeTriggered(AnyDuelist duelist) {
        if (this.magicNumber > 0) {
            duelist.applyPowerToSelf(new VigorPower(duelist.creature(), this.magicNumber));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        if (!targets.isEmpty()) {
            attack(targets.get(0), AFX, this.damage);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() { return new DarkCrusader(); }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
