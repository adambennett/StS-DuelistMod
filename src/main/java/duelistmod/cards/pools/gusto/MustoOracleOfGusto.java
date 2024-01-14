package duelistmod.cards.pools.gusto;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.enemyDuelist.EnemyMakeTempCardInDrawPileAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MustoOracleOfGusto extends DuelistCard {
    public static final String ID = DuelistMod.makeID("MustoOracleOfGusto");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MustoOracleOfGusto.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public MustoOracleOfGusto() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 13;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.SPELLCASTER);
        this.baseSummons = this.summons = 1;
        this.originalName = this.name;
        this.exhaust = true;
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
            attack(targets.get(0), this.baseAFX, this.damage);
        }

        AnyDuelist duelist = AnyDuelist.from(this);
        CardGroup beastsInDiscardPile = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
        beastsInDiscardPile.group = duelist.discardPile().stream()
                .filter(c -> c.hasTag(Tags.BEAST) && c.hasTag(Tags.MONSTER))
                .collect(Collectors.toCollection(ArrayList::new));
        if (!beastsInDiscardPile.isEmpty() && duelist.player()) {
            Consumer<ArrayList<AbstractCard>> shuffleIntoDeckAndStun = group -> {
                if (group.isEmpty()) return;
                for (AbstractCard c : group) {
                    c.unhover();
                    duelist.discardPileGroup().removeCard(c);
                    if (duelist.player()) {
                        addToBot(new MakeTempCardInDrawPileAction(c, 1, true, false));
                    } else if (duelist.getEnemy() != null) {
                        addToBot(new EnemyMakeTempCardInDrawPileAction(c, 1, true, false));
                    }
                }
                if (duelist.player() && targets.size() > 0) {
                    AbstractCreature target = targets.get(0);
                    addToBot(new StunMonsterAction((AbstractMonster)target, AbstractDungeon.player));
                }
                this.exhaust = true;
            };
            SelectScreenHelper.open(beastsInDiscardPile, 1, "Shuffle a Beast card", true, shuffleIntoDeckAndStun);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSummons(1);
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}

