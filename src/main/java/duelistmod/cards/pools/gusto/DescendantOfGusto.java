package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.actions.enemyDuelist.EnemyMakeTempCardInDrawPileAction;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.orbs.AirOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DescendantOfGusto extends DuelistCard {
    public static final String ID = DuelistMod.makeID("DescendantOfGusto");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DescendantOfGusto.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public DescendantOfGusto() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.SPELLCASTER);
        this.baseSummons = this.summons = 1;
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
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
        summon();
        if (this.magicNumber > 0) {
            Predicate<AbstractCard> gustoBeast = c -> c.hasTag(Tags.MONSTER) && c.hasTag(Tags.BEAST);
            ArrayList<AbstractCard> foundCards = CardFinderHelper.find(this.magicNumber, TheDuelist.cardPool.group, DuelistMod.myCards, gustoBeast);
            if (upgraded) {
                foundCards.forEach(AbstractCard::upgrade);
            }
            if (!foundCards.isEmpty()) {
                if (duelist.player()) {
                    addToBot(new MakeTempCardInDrawPileAction(foundCards.get(0).makeStatEquivalentCopy(), 1, true, false));
                } else if (duelist.getEnemy() != null) {
                    addToBot(new EnemyMakeTempCardInDrawPileAction(foundCards.get(0).makeStatEquivalentCopy(), 1, true, false));
                }
            }
        }
        duelist.channel(new AirOrb());
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgraded = true;
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
