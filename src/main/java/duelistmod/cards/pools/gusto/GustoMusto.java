package duelistmod.cards.pools.gusto;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.common.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GustoMusto extends DuelistCard
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoMusto");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoMusto.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;


    // /STAT DECLARATION/

    public GustoMusto() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 7;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.SPELLCASTER);
        this.baseSummons = this.summons = 1;
        this.originalName = this.name;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        CardGroup gustosInDiscardPile = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
        gustosInDiscardPile.group = new ArrayList<AbstractCard>(p.discardPile.group.stream()
                .filter(c -> c.hasTag(Tags.BEAST))
                .collect(Collectors.toList()));

        Consumer<ArrayList<AbstractCard>> shuffleIntoDeckAndStun = group -> {
            if (group.isEmpty()) return;
            for (AbstractCard c : group) {
                c.unhover();
                AbstractDungeon.player.discardPile.removeCard(c);
                if (this.upgraded) {
                    c.upgrade();
                }
                addToBot(new MakeTempCardInDrawPileAction(c, 1, true, false));
            }
            addToBot(new StunMonsterAction(m, p));
        };

        summon(p, this.summons, this);
        attack(m);
        if (gustosInDiscardPile.isEmpty()) return;
        SelectScreenHelper.open(gustosInDiscardPile, 1, "Shuffle a Beast card", true, shuffleIntoDeckAndStun);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void customOnTribute(DuelistCard tributingMon)
    {
        block(1);
    }
}

