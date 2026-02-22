package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;

public class RoseWarriorOfRevenge extends DuelistCard {

    public static final String ID = DuelistMod.makeID("RoseWarriorOfRevenge");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RoseWarriorOfRevenge.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public RoseWarriorOfRevenge() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 10;
        this.summons = this.baseSummons = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
        this.tags.add(Tags.ROSE);
        this.misc = 0;
        this.originalName = this.name;
    }

    @Override
    public void update() {
        super.update();
        if (playedRevengeCardInWindow()) {
            this.target = CardTarget.ALL_ENEMY;
            this.isMultiDamage = true;
        } else {
            this.target = CardTarget.ENEMY;
            this.isMultiDamage = false;
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
        if (targets.size() > 0) {
            if (playedRevengeCardInWindow()) {
                this.isMultiDamage = true;
                normalMultidmg();
            } else {
                this.isMultiDamage = false;
                attack(targets.get(0));
            }
        }
        postDuelistUseCard(owner, targets);
    }

    private boolean playedRevengeCardInWindow() {
        AnyDuelist duelist = AnyDuelist.from(this);
        int total = 0;
        int downCounter = this.magicNumber;
        int currentTurn = GameActionManager.turn;

        long revengeCardsPlayedThisTurn = duelist.getCardsPlayedThisTurn().stream().filter(c -> c instanceof RevengeCard).count();
        total += (int) revengeCardsPlayedThisTurn;
        currentTurn--;
        downCounter--;

        while (currentTurn > 0 && downCounter > 0) {
            long revengeCardsPlayedOnTurn = duelist.getCardsPlayedByTurnThisCombat().getOrDefault(currentTurn, new ArrayList<>()).stream().filter(c -> c instanceof RevengeCard).count();
            total += (int) revengeCardsPlayedOnTurn;
            currentTurn--;
            downCounter--;
        }
        return total > 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new RoseWarriorOfRevenge();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
