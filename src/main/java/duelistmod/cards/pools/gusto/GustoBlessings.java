package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import eatyourbeets.actions.handSelection.ExhaustFromHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GustoBlessings extends DuelistCard
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoBlessings");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoBlessings.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;


    // /STAT DECLARATION/

    public GustoBlessings() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.TRAP);
        this.originalName = this.name;
        this.exhaust = true;
    }

    // Add 1 of 3 random spellcaster or beasts in discard pile to hand, shuffle rest into draw pile, draw a card
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        final CardGroup cardsToPickFrom = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
        List<AbstractCard> spellcastersAndBeasts = player().discardPile.group.stream()
                .filter(c -> (c.hasTag(Tags.SPELLCASTER) || c.hasTag(Tags.BEAST)) && c.hasTag(Tags.MONSTER))
                .collect(Collectors.toList());
        if(spellcastersAndBeasts.size() < 3)
        {
            player().draw();
            return;
        }
        Collections.shuffle(spellcastersAndBeasts);
        cardsToPickFrom.group = new ArrayList<AbstractCard>(spellcastersAndBeasts.stream()
                .limit(3)
                .collect(Collectors.toList()));
        Consumer<ArrayList<AbstractCard>> fetch = pile -> {
            pile.forEach(c -> {
                if(upgraded) c.upgrade();
                player().hand.addToHand(c);
                cardsToPickFrom.removeCard(c);
                player().discardPile.removeCard(c);
            });
            cardsToPickFrom.group.forEach(c -> {
                player().discardPile.removeCard(c);
                addToBot(new MakeTempCardInDrawPileAction(c, 1, true, false));
            });
        };
        SelectScreenHelper.open(cardsToPickFrom, 1, "Add a Spellcaster or Beast monster to your hand", true, fetch);
        player().draw();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
