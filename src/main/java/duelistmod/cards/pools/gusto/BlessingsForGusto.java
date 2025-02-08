package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.enemyDuelist.EnemyMakeTempCardInDrawPileAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BlessingsForGusto extends DuelistCard {
    public static final String ID = DuelistMod.makeID("BlessingsForGusto");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BlessingsForGusto.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;


    public BlessingsForGusto() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.TRAP);
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
        AnyDuelist duelist = AnyDuelist.from(this);

        final CardGroup cardsToPickFrom = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
        ArrayList<AbstractCard> spellcastersAndBeasts = duelist.discardPile().stream()
                .filter(c -> (c.hasTag(Tags.SPELLCASTER) || c.hasTag(Tags.BEAST)) && c.hasTag(Tags.MONSTER))
                .collect(Collectors.toCollection(ArrayList::new));

        if (spellcastersAndBeasts.size() < 3) {
            duelist.draw(1);
        } else {
            Collections.shuffle(spellcastersAndBeasts);

            cardsToPickFrom.group = spellcastersAndBeasts.stream()
                    .limit(3)
                    .collect(Collectors.toCollection(ArrayList::new));

            Consumer<ArrayList<AbstractCard>> fetch = pile -> {
                pile.forEach(c -> {
                    if(upgraded) c.upgrade();
                    cardsToPickFrom.removeCard(c);
                    duelist.addCardToHand(c);
                    duelist.discardPileGroup().removeCard(c);
                });
                cardsToPickFrom.group.forEach(c -> {
                    duelist.discardPileGroup().removeCard(c);
                    if (duelist.player()) {
                        addToBot(new MakeTempCardInDrawPileAction(c, 1, true, false));
                    } else if (duelist.getEnemy() != null) {
                        addToBot(new EnemyMakeTempCardInDrawPileAction(c, 1, true, false));
                    }
                });
            };
            if (duelist.player()) {
                SelectScreenHelper.open(cardsToPickFrom, 1, "Add a Spellcaster or Beast monster to your hand", true, fetch);
            } else if (duelist.getEnemy() != null && cardsToPickFrom.size() > 0) {
                duelist.addCardToHand(cardsToPickFrom.getRandomCard(AbstractDungeon.cardRandomRng));
            }
            duelist.draw(1);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
